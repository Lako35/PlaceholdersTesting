package org.example;


import org.bukkit.Color;
import org.bukkit.plugin.PluginManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;


//ONLY CUBE AND TEXT
/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "CallToPrintStackTrace", "TextBlockMigration", "unused"})
public class ExampleExpansion extends PlaceholderExpansion {



    private boolean WorldEdit_Installed = false;
    private boolean WorldGuard_Installed = false;
    private boolean LuckPerms_Installed = false;
    private boolean ProtocolLib_Installed = false;




    private static final boolean viewChestDebugLogging = false;



    private static final Map<String, CachedParticleData> memoryCache = new HashMap<>();
    private static final long EXPIRY_TIME = 1000 * 60 * 60; // 1 hour
    private static final File PARTICLE_DIR = new File("plugins/Archistructures/particles");


    private final boolean trialVersion;
    private int trialNumber;
    private final Map<Location, BukkitTask> visualBreakTimers = new HashMap<>();

    private final File entityStorageDir;
    private final File backpackDir;
    private final File databaseFile;
    private final File doubleDatabaseFile;
    private final File viewOnlyChestDatabaseFile;
    private final YamlConfiguration doubleDatabaseConfig;
    private final YamlConfiguration databaseConfig;
    private final Map<Location, BlockData> trackedBlockData = new HashMap<>();
    private final YamlConfiguration viewOnlyChestConfig;



    private final File ftLeaderboardFile;
    private final Map<UUID, Integer> ftLeaderboard;
    private final LuckPerms luckPerms;

    private final Map<UUID, Set<Location>> trackedBlocks = new HashMap<>();
    private final Set<Material> oresAndImportantBlocks = Set.of(
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS);


    public ExampleExpansion() {
        trialVersion = false;
        trialNumber = 500;



        PluginManager pm = Bukkit.getPluginManager();

        if (pm.getPlugin("WorldEdit") != null && Objects.requireNonNull(pm.getPlugin("WorldEdit")).isEnabled()) {
            WorldEdit_Installed = true;
        }

        if (pm.getPlugin("WorldGuard") != null && Objects.requireNonNull(pm.getPlugin("WorldGuard")).isEnabled()) {
            WorldGuard_Installed = true;
        }

        if (pm.getPlugin("LuckPerms") != null && Objects.requireNonNull(pm.getPlugin("LuckPerms")).isEnabled()) {
            LuckPerms_Installed = true;
        }

        if (pm.getPlugin("ProtocolLib") != null && Objects.requireNonNull(pm.getPlugin("ProtocolLib")).isEnabled()) {
            ProtocolLib_Installed = true;
        }

        File viewOnlyChestDir = new File("plugins/Archistructures/viewonlychests/");
        if (!viewOnlyChestDir.exists()) {
            viewOnlyChestDir.mkdirs();
        }

        if (!PARTICLE_DIR.exists()) PARTICLE_DIR.mkdirs();


        this.viewOnlyChestDatabaseFile = new File(viewOnlyChestDir, "viewonlychests.yml");
        if (!viewOnlyChestDatabaseFile.exists()) {
            try {
                viewOnlyChestDatabaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.viewOnlyChestConfig = YamlConfiguration.loadConfiguration(viewOnlyChestDatabaseFile);
        this.backpackDir = new File("plugins/Archistructures/backpacks/");
        if (!backpackDir.exists()) {
            backpackDir.mkdirs();
        }
        this.databaseFile = new File(backpackDir, "database.yml");
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);

        // Double chest database
        this.doubleDatabaseFile = new File(backpackDir, "database2.yml");
        if (!doubleDatabaseFile.exists()) {
            try {
                doubleDatabaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.doubleDatabaseConfig = YamlConfiguration.loadConfiguration(doubleDatabaseFile);
        
        entityStorageDir = new File("plugins/Archistructures/saved-entities/");
        if (!entityStorageDir.exists()) {
            entityStorageDir.mkdirs();
        }

        ftLeaderboardFile = new File("plugins/Archistructures/FTLeaderboard.txt");
        ftLeaderboard = new LinkedHashMap<>();
        this.luckPerms = LuckPermsProvider.get();
        luckPerms.getUserManager();

    }

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     * This expansion does not require a dependency so we will always return true
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here
     */
    @Override
    public @NotNull String getAuthor() {
        return "Archistructure";
    }

    /**
     * The placeholder identifier should go here
     * This is what tells PlaceholderAPI to call our opener method to obtain
     * a value if a placeholder starts with our identifier.
     * This must be unique and can not contain % or _
     */
    @Override
    public @NotNull String getIdentifier() {
        return "Archistructure";
    }


    /**
     * This is the version of this expansion
     */
    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    /**
     * if an expansion requires another plugin as a dependency, the proper name of the dependency should
     * go here. Set this to null if your placeholders do not require another plugin be installed on the server
     * for them to work
     */
    @Override
    @SuppressWarnings({"Overridden", "UnstableApiUsage", "deprecation"})
    public String getPlugin() {
        return null;
    }

    /**
     * Displays a particle cube at the specified location to all players
     * within 'radius' blocks of the cube’s center.
     *
     * If `particleType` begins with "DUST-", it must follow the pattern:
     *   "DUST-<6hexcolor><size>"
     * where <6hexcolor> is a hex color string (e.g. "FF00AA") and <size> is an integer.
     * In that case, a dust particle with the given color and size is used.
     * Otherwise, `particleType` is treated as a standard Particle enum name.
     */
    public void displayCubeWithParticles(World world,
                                         double x, double y, double z,
                                         String particleType,
                                         double width,
                                         boolean force,
                                         int density,
                                         int radius)
    {
        double halfWidth = width / 2.0;
        Location center = new Location(world, x, y, z);
        double radiusSq = (double) radius * radius;

        // Determine Particle enum and, if needed, DustOptions
        Particle particleEnum;
        Particle.DustOptions dustOpts = null;

        String upper = particleType.toUpperCase(Locale.ROOT);
        if (upper.startsWith("DUST-")) {
            // Format: "DUST-<HEX><SIZE>"
            String remainder = upper.substring("DUST-".length());
            if (remainder.length() < 7) {
                // Must have at least 6 hex chars + 1 digit size
                return;
            }
            String hexPart = remainder.substring(0, 6);
            String sizePart = remainder.substring(6);

            int rgb;
            int sizeInt;
            try {
                rgb = Integer.parseInt(hexPart, 16);
                sizeInt = Integer.parseInt(sizePart);
            } catch (NumberFormatException ex) {
                return;
            }
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8)  & 0xFF;
            int b =  rgb        & 0xFF;
            // DustOptions uses BGR order
            dustOpts = new Particle.DustOptions(Color.fromBGR(b, g, r), (float) sizeInt);
            particleEnum = Particle.DUST;
        } else {
            try {
                particleEnum = Particle.valueOf(upper);
            } catch (IllegalArgumentException ex) {
                return;
            }
        }

        // Collect all viewers within 'radius' of center (or all if force == true)
        List<Player> viewers = new ArrayList<>();
        for (Player viewer : world.getPlayers()) {
            if (force || viewer.getLocation().distanceSquared(center) <= radiusSq) {
                viewers.add(viewer);
            }
        }
        if (viewers.isEmpty()) return;

        // Calculate the 8 corners of the cube
        Location[] corners = new Location[8];
        corners[0] = new Location(world, x - halfWidth, y - halfWidth, z - halfWidth); // Bottom NW
        corners[1] = new Location(world, x + halfWidth, y - halfWidth, z - halfWidth); // Bottom NE
        corners[2] = new Location(world, x - halfWidth, y - halfWidth, z + halfWidth); // Bottom SW
        corners[3] = new Location(world, x + halfWidth, y - halfWidth, z + halfWidth); // Bottom SE
        corners[4] = new Location(world, x - halfWidth, y + halfWidth, z - halfWidth); // Top NW
        corners[5] = new Location(world, x + halfWidth, y + halfWidth, z - halfWidth); // Top NE
        corners[6] = new Location(world, x - halfWidth, y + halfWidth, z + halfWidth); // Top SW
        corners[7] = new Location(world, x + halfWidth, y + halfWidth, z + halfWidth); // Top SE

        // Render cube edges
        displayLine(viewers, particleEnum, dustOpts, corners[0], corners[1], density, force, center, radiusSq); // Bottom North
        displayLine(viewers, particleEnum, dustOpts, corners[0], corners[2], density, force, center, radiusSq); // Bottom West
        displayLine(viewers, particleEnum, dustOpts, corners[1], corners[3], density, force, center, radiusSq); // Bottom East
        displayLine(viewers, particleEnum, dustOpts, corners[2], corners[3], density, force, center, radiusSq); // Bottom South
        displayLine(viewers, particleEnum, dustOpts, corners[4], corners[5], density, force, center, radiusSq); // Top North
        displayLine(viewers, particleEnum, dustOpts, corners[4], corners[6], density, force, center, radiusSq); // Top West
        displayLine(viewers, particleEnum, dustOpts, corners[5], corners[7], density, force, center, radiusSq); // Top East
        displayLine(viewers, particleEnum, dustOpts, corners[6], corners[7], density, force, center, radiusSq); // Top South
        displayLine(viewers, particleEnum, dustOpts, corners[0], corners[4], density, force, center, radiusSq); // Vertical NW
        displayLine(viewers, particleEnum, dustOpts, corners[1], corners[5], density, force, center, radiusSq); // Vertical NE
        displayLine(viewers, particleEnum, dustOpts, corners[2], corners[6], density, force, center, radiusSq); // Vertical SW
        displayLine(viewers, particleEnum, dustOpts, corners[3], corners[7], density, force, center, radiusSq); // Vertical SE

        // Render cube faces
        renderFace(viewers, particleEnum, dustOpts, corners[0], corners[1], corners[4], corners[5], density, force, center, radiusSq); // North Face
        renderFace(viewers, particleEnum, dustOpts, corners[2], corners[3], corners[6], corners[7], density, force, center, radiusSq); // South Face
        renderFace(viewers, particleEnum, dustOpts, corners[0], corners[2], corners[4], corners[6], density, force, center, radiusSq); // West Face
        renderFace(viewers, particleEnum, dustOpts, corners[1], corners[3], corners[5], corners[7], density, force, center, radiusSq); // East Face
        renderFace(viewers, particleEnum, dustOpts, corners[4], corners[5], corners[6], corners[7], density, force, center, radiusSq); // Top Face
        renderFace(viewers, particleEnum, dustOpts, corners[0], corners[1], corners[2], corners[3], density, force, center, radiusSq); // Bottom Face
    }

    private void renderFace(List<Player> viewers,
                            Particle particle,
                            Particle.DustOptions dustOpts,
                            Location corner1,
                            Location corner2,
                            Location corner3,
                            Location corner4,
                            int density,
                            boolean force,
                            Location center,
                            double radiusSq)
    {
        for (int i = 1; i < density; i++) {
            double t = (double) i / density;

            // Create intermediate lines between edges
            Location start = interpolate(corner1, corner2, t);
            Location end   = interpolate(corner3, corner4, t);
            displayLine(viewers, particle, dustOpts, start, end, density, force, center, radiusSq);

            start = interpolate(corner1, corner3, t);
            end   = interpolate(corner2, corner4, t);
            displayLine(viewers, particle, dustOpts, start, end, density, force, center, radiusSq);
        }
    }

    private Location interpolate(Location start, Location end, double t) {
        double x = start.getX() + (end.getX() - start.getX()) * t;
        double y = start.getY() + (end.getY() - start.getY()) * t;
        double z = start.getZ() + (end.getZ() - start.getZ()) * t;
        return new Location(start.getWorld(), x, y, z);
    }

    private void displayLine(List<Player> viewers,
                             Particle particle,
                             Particle.DustOptions dustOpts,
                             Location start,
                             Location end,
                             int density,
                             boolean force,
                             Location center,
                             double radiusSq)
    {
        World world = start.getWorld();
        if (world == null) return;

        for (int i = 0; i <= density; i++) {
            double t = (double) i / density;
            double px = start.getX() + (end.getX()   - start.getX()) * t;
            double py = start.getY() + (end.getY()   - start.getY()) * t;
            double pz = start.getZ() + (end.getZ()   - start.getZ()) * t;
            Location point = new Location(world, px, py, pz);

            for (Player viewer : viewers) {
                // If not forced, skip viewer if outside radius from center
                if (!force && viewer.getLocation().distanceSquared(center) > radiusSq) {
                    continue;
                }
                if (particle == Particle.DUST && dustOpts != null) {
                    viewer.spawnParticle(particle, point, 1, 0, 0, 0, 0, dustOpts);
                } else {
                    viewer.spawnParticle(particle, point, 1, 0, 0, 0, 0);
                }
            }
        }
    }
    
    
    
    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @SuppressWarnings({"UnstableApiUsage", "deprecation", "ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        
        if(trialVersion && trialNumber < 0) {
            p.sendMessage("§c§lYou have exceeded the limit of the free trial. Consider purchasing the full pack from ZestyBuffalo or do /papi reload to stick with the trial version");
            return null;
        }
        if(trialVersion) p.sendMessage("§e§lYou have " + (trialNumber-1) + " of credits left");
        trialNumber--;

// SINGLY NESTED PLACEHOLDER SUPPORT - MUST BE FIRST

        boolean parseNested = false;

        // Check if the identifier starts with "parseNested_"
        if (identifier.startsWith("parseNested_")) {
            parseNested = true;
            identifier = identifier.substring("parseNested_".length());
        }

        // If nested parsing is enabled, resolve all nested placeholders
        if (parseNested) {
            while (identifier.contains("{") && identifier.contains("}")) {
                int start = identifier.indexOf("{");
                int end = identifier.indexOf("}", start);

                if (start < end) {
                    String nestedPlaceholder = identifier.substring(start + 1, end);
                    String resolvedNested = PlaceholderAPI.setPlaceholders(p, "%" + nestedPlaceholder + "%");

                    if (resolvedNested != null && !resolvedNested.equalsIgnoreCase("%" + nestedPlaceholder + "%")) {
                        // Replace the nested placeholder with its resolved value
                        identifier = identifier.substring(0, start) + resolvedNested + identifier.substring(end + 1);
                    } else {
                        // If unresolved, replace with an empty string to avoid infinite loop
                        identifier = identifier.substring(0, start) + identifier.substring(end + 1);
                    }
                } else {
                    // Break out if no valid placeholder found
                    break;
                }
            }
        }
        
        // INSERT HERE 


        if (identifier.startsWith("PTFXCUBE_")) {
            // Expected format: %Archistructure_PTFXCUBE_world,x,y,z,particleType,width,normal/force,density%
            String params = identifier.substring("PTFXCUBE_".length());
            String[] parts = params.split(",");

            if (parts.length != 9) {
                return "Invalid format!";
            }

            // Parse the parameters
            String worldName = parts[0];
            double x, y, z, width;
            int density;
            boolean force;
            int radius;

            try {
                x = Double.parseDouble(parts[1]);
                y = Double.parseDouble(parts[2]);
                z = Double.parseDouble(parts[3]);
                width = Double.parseDouble(parts[5]);
                density = Integer.parseInt(parts[7]);
                force = parts[6].equalsIgnoreCase("force");
                radius = Integer.parseInt(parts[8]);
            } catch (NumberFormatException e) {
                return "Invalid numerical value!";
            }

            String particleType = parts[4];
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                return "World not found!";
            }

            // Display the particle cube
            displayCubeWithParticles(world, x, y, z, particleType, width, force, density, radius);
            return "Cube displayed";
        }



        if (identifier.startsWith("repeatingParticleText_")) {
            boolean particleDebugEnabled = true;
            try {
                String params = identifier.substring("repeatingParticleText_".length());
                String[] parts = params.split(",", 13);
                if (parts.length != 13) return "Invalid format";

                String worldName = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);

                // ─────── UPDATED: parse parts[4] for "DUST-<HEX><SIZE>" ───────
                Particle particle;
                Particle.DustOptions dustOpts = null;
                String rawType = parts[4].toUpperCase(Locale.ROOT);
                if (rawType.startsWith("DUST-")) {
                    // Expected form: "DUST-<6hexcolor><size>"
                    String remainder = rawType.substring("DUST-".length());
                    if (remainder.length() < 7) {
                        return "Invalid dust format"; // must have 6 hex + at least 1 digit
                    }
                    String hexPart  = remainder.substring(0, 6);
                    String sizePart = remainder.substring(6);
                    int rgb;
                    int sizeInt;
                    try {
                        rgb     = Integer.parseInt(hexPart, 16);
                        sizeInt = Integer.parseInt(sizePart);
                    } catch (NumberFormatException ex) {
                        return "Invalid dust color/size";
                    }
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >>  8) & 0xFF;
                    int b =  (rgb)       & 0xFF;
                    // DustOptions wants a Bukkit Color in BGR order
                    dustOpts = new Particle.DustOptions(Color.fromBGR(b, g, r), (float) sizeInt);
                    particle = Particle.DUST;
                } else {
                    try {
                        particle = Particle.valueOf(rawType);
                    } catch (IllegalArgumentException ex) {
                        return "Invalid particle type";
                    }
                }
                // ─────────────────────────────────────────────────────────────────────

                int density = Math.max(1, Integer.parseInt(parts[5]));
                String viewDistance = parts[6];
                double size = Double.parseDouble(parts[7]);
                double rotation = Math.toRadians(Double.parseDouble(parts[8]));
                long durationTicks = Long.parseLong(parts[9]);
                long intervalTicks = Math.max(1, Long.parseLong(parts[10]));
                String text = parts[12];
                if (parts[11].equalsIgnoreCase("false")) particleDebugEnabled = false;

                Location center = new Location(Bukkit.getWorld(worldName), x, y, z);
                String hashKey = hashSHA256(identifier);

                List<Location> worldLocs;

                // === RAM Cache Check ===
                if (memoryCache.containsKey(identifier)) {
                    if (particleDebugEnabled) p.sendMessage("§7[Cache] RAM hit for: " + identifier);
                    worldLocs = memoryCache.get(identifier).locations();
                    refreshCacheExpiry(identifier);
                }
                // === Disk Cache Check ===
                else {
                    File cacheFile = new File(PARTICLE_DIR, hashKey + ".txt");
                    if (cacheFile.exists()) {
                        if (particleDebugEnabled) p.sendMessage("§7[Cache] Disk hit for: " + identifier);
                        List<Vector> vectors = loadFromDisk(cacheFile);
                        worldLocs = applyToWorld(center, vectors);

                        memoryCache.put(identifier, new CachedParticleData(worldLocs, System.currentTimeMillis()));
                        refreshCacheExpiry(identifier);
                    }
                    // === No Cache: Generate ===
                    else {
                        if (particleDebugEnabled) p.sendMessage("§7[Cache] No cache found. Generating new data.");
                        boolean[][] matrix = renderTextToMatrix(text);
                        List<Vector> vectors = matrixToVectors(matrix, density, size, rotation);
                        worldLocs = applyToWorld(center, vectors);

                        saveToDisk(cacheFile, vectors);
                        memoryCache.put(identifier, new CachedParticleData(worldLocs, System.currentTimeMillis()));
                        refreshCacheExpiry(identifier);
                    }
                }

                // === Repeating Display ===
                if (particleDebugEnabled) {
                    p.sendMessage("§7[Debug] Scheduling display for: " + text);
                }

                Particle.DustOptions finalDustOpts = dustOpts;
                new BukkitRunnable() {
                    long elapsedTicks = 0;

                    @Override
                    public void run() {
                        if (elapsedTicks >= durationTicks) {
                            this.cancel();
                            return;
                        }

                        try {
                            // ─── UPDATED: pass finalDustOpts into displayToNearby ───
                            displayToNearby(center, worldLocs, particle, viewDistance, finalDustOpts);
                        } catch (Exception ex) {
                          
                            this.cancel();
                        }

                        elapsedTicks += intervalTicks;
                    }
                }.runTaskTimerAsynchronously(
                        Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                        0L,
                        intervalTicks
                );

                return "Scheduled " + text;

            } catch (Exception e) {
                if (particleDebugEnabled) {
                    p.sendMessage("§c[Debug Error] " + e.getMessage());
                }
                return "§cError: " + e.getMessage();
            }
        }


        return null;

    }


    private List<Vector> matrixToVectors(boolean[][] matrix, int density, double size, double rotationRadians) {
        List<Vector> vectors = new ArrayList<>();

        int height = matrix.length;
        int width = Arrays.stream(matrix).mapToInt(row -> row.length).max().orElse(0);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (!matrix[row][col]) continue;

                for (int dy = 0; dy < density; dy++) {
                    for (int dx = 0; dx < density; dx++) {
                        double offsetZ = (col + dx / (double) density - width / 2.0) * size;
                        double offsetY = (-row - dy / (double) density + height / 2.0) * size;

                        // The text is vertical (YZ), and rotated around Y
                        double rotatedX = offsetZ * Math.sin(rotationRadians);
                        double rotatedZ = offsetZ * Math.cos(rotationRadians);

                        vectors.add(new Vector(rotatedX, offsetY, rotatedZ));
                    }
                }
            }
        }

        return vectors;
    }




    private static boolean[][] renderTextToMatrix(String text) {
        Font font = new Font("Dialog", Font.PLAIN, 16);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        g.setFont(font);
        g.setColor(java.awt.Color.WHITE);
        g.drawString(text, 0, fm.getAscent());
        g.dispose();

        boolean[][] matrix = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = (img.getRGB(x, y) >> 24) != 0;
            }
        }
        return matrix;
    }

    private static List<Location> applyToWorld(Location center, List<Vector> offsets) {
        List<Location> result = new ArrayList<>();
        for (Vector v : offsets) {
            result.add(center.clone().add(v));
        }
        return result;
    }
    
    
    private void displayToNearby(Location center,
                                 List<Location> locs,
                                 Particle particle,
                                 String mode,
                                 Particle.DustOptions dustOpts)
    {
        boolean isForce = mode.equalsIgnoreCase("force");
        double maxDistanceSq = 64 * 64;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isForce && player.getLocation().getWorld() != center.getWorld()) continue;
            if (!isForce && player.getLocation().distanceSquared(center) > maxDistanceSq) continue;

            for (Location loc : locs) {
                if (dustOpts != null && particle == Particle.DUST) {
                    player.spawnParticle(particle, loc, 1, 0, 0, 0, 0, dustOpts);
                } else {
                    player.spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                }
            }
        }
    }


    private static void saveToDisk(File file, List<Vector> vectors) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Vector v : vectors) {
                writer.write(v.getX() + "," + v.getY() + "," + v.getZ());
                writer.newLine();
            }
        }
    }

    private static List<Vector> loadFromDisk(File file) throws IOException {
        List<Vector> vectors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                double z = Double.parseDouble(parts[2]);
                vectors.add(new Vector(x, y, z));
            }
        }
        return vectors;
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(input.hashCode()); // Fallback
        }
    }
    

    private static String sanitize(String text) {
        return Base64.getUrlEncoder().encodeToString(text.getBytes());
    }

    private record CachedParticleData(List<Location> locations, long timestamp) {}

    private void refreshCacheExpiry(String fullKey) {
        // Cancel and reschedule the cache removal in 1 minute
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                () -> memoryCache.remove(fullKey), 20L * 60); // 60 seconds
    }


    
}