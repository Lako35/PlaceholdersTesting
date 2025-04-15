package org.example;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.ssomar.score.utils.emums.VariableType;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.VariableForEnum;
import com.ssomar.score.variables.manager.VariablesManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Comparator;
import java.util.List;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "CallToPrintStackTrace", "TextBlockMigration", "unused"})
public class ExampleExpansion extends PlaceholderExpansion {
    private static final boolean viewChestDebugLogging = false;

    private static final boolean particleDebugEnabled = true;


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
        loadFTLeaderboard();
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

    private void incrementFTScore(UUID playerUUID) {
        if (!ftLeaderboard.containsKey(playerUUID)) {
            ftLeaderboard.put(playerUUID, 1); // First-time entry
        } else {
            ftLeaderboard.put(playerUUID, ftLeaderboard.get(playerUUID) + 1); // Increment existing score
        }
        saveFTLeaderboard();
    }


    private void saveFTLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ftLeaderboardFile))) {
            for (Map.Entry<UUID, Integer> entry : ftLeaderboard.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String processBook(Player player) {
        // Ensure player is holding a written book
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.WRITABLE_BOOK && item.getType() != Material.WRITTEN_BOOK || !item.hasItemMeta()) {
            return ChatColor.RED + "You must hold a writable book!";
        }

        BookMeta bookMeta = (BookMeta) item.getItemMeta();
        assert bookMeta != null;
        List<String> pages = bookMeta.getPages();
        if (pages.isEmpty()) {
            return ChatColor.RED + "Nothing written!";
        }

        String targetName = null;
        int pageIndex = -1;
        String modifiedPage = null;

        // Find the first non-strikethrough word
        for (int i = 0; i < pages.size(); i++) {
            String page = pages.get(i);
            String[] words = page.split("\\s+"); // Split while keeping formatting

            for (String word : words) {
                if (!word.contains("§m")) {
                    if (targetName != null) {
                        return ChatColor.RED + "You may only add ONE entry at a time!";
                    }
                    targetName = ChatColor.stripColor(word);
                    String strikethroughWord = "§m" + word + "§r "; // Apply strikethrough
                    modifiedPage = page.replaceFirst("\\b" + word + "\\b", strikethroughWord); // Replace only the first occurrence
                    pageIndex = i;
                }
            }
        }

        if (targetName == null) {
            return ChatColor.RED + "Nothing written!";
        }

        // Find the player or entity
        Player target = Bukkit.getPlayerExact(targetName);
        Entity entity = null;
        try {
            entity = Bukkit.getEntity(UUID.fromString(targetName));
        } catch (IllegalArgumentException ignored) {
            // Ignore UUID parsing errors, means it's not a UUID
        }

        if (target == null && entity == null) {
            return ChatColor.RED + "Entity " + targetName + " is not online!";
        }

        // Perform action
        if (target != null) {
            target.setHealth(0);
        } else if (entity instanceof Damageable damageableEntity) {
            damageableEntity.setHealth(0);
        } else {
            return ChatColor.RED + "Entity " + targetName + " is not damageable!";
        }

        // Log debug information
        Bukkit.getLogger().info("Target eliminated: " + (target != null ? target.getName() : entity.getName()));

        // Ensure book updates properly
        List<String> updatedPages = new ArrayList<>(pages);
        updatedPages.set(pageIndex, modifiedPage);
        bookMeta.setPages(updatedPages);
        item.setItemMeta(bookMeta);

        // Log return statement
        Bukkit.getLogger().info("Returning success message...");

        // Success message
        return target != null ? "&6&lYou have eliminated " + target.getName() : "&6&lYou have eliminated " + entity.getName();
    }



    /**
     * Processes item folder permissions and assigns "ei.item.<filename>" for each found file.
     */
    public void processItemFolderPermissions(User user, List<String> itemFolderPermissions) {
        Set<String> totalPerms = new HashSet<>();
        String basePath = "plugins/ExecutableItems/items/";

        for (String itemFolderPath : itemFolderPermissions) {
            File folder = new File(basePath + itemFolderPath);
            if (folder.exists() && folder.isDirectory()) {
                findYamlFiles(folder, totalPerms);
            }
        }

        // Assign new item permissions
        totalPerms.forEach(itemName -> {
            Node node = Node.builder("ei.item." + itemName).value(true).build();
            user.data().add(node);
        });
    }



    /**
     * Recursively finds .yml files, extracts names (excluding .yml), and adds them to totalPerms.
     */
    private void findYamlFiles(File directory, Set<String> totalPerms) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findYamlFiles(file, totalPerms);
            } else if (file.getName().endsWith(".yml")) {
                totalPerms.add(file.getName().replace(".yml", ""));
            }
        }
    }


    /**
     * Get the next available coordinates in a chunk-efficient manner
     */
    private int[] getNextAvailableCoordinates(World world) {
        int chunkX = 5;  // Fixed X-coordinate (do not change)
        int chunkZ = databaseConfig.getInt("last_chunk_z", 0);
        int xIndex = 0;
        int zIndex = 0;
        int yIndex = databaseConfig.getInt("last_y", world.getMinHeight());
        int chunkHeight = world.getMaxHeight(); // Typically 256
        int chunkMin = world.getMinHeight();    // Typically -64

        while (true) {
            int x = (chunkX * 16) + xIndex;  // x-coordinate within the chunk (0-15)
            int z = (chunkZ * 16) + zIndex;  // z-coordinate within the chunk (0-15)
            int y = yIndex;

            Location loc = new Location(world, x, y, z);
            if (loc.getBlock().getType() == Material.AIR) {
                // Save the new chunk coordinates
                databaseConfig.set("last_chunk_z", chunkZ);
                databaseConfig.set("last_y", yIndex);
                saveDatabaseConfig();
                return new int[]{x, y, z};
            }

            // Increment Y first (fill from bottom to top)
            yIndex++;
            if (yIndex >= chunkHeight) {
                yIndex = chunkMin;
                zIndex++;
                if (zIndex >= 16) {
                    zIndex = 0;
                    xIndex++;
                    if (xIndex >= 16) {
                        xIndex = 0;
                        chunkZ++;  // Move to the next chunk in the Z direction
                    }
                }
            }
        }
    }

    /**
     * Save the database configuration
     */
    private void saveDatabaseConfig() {
        try {
            databaseConfig.save(databaseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Save the database configuration for double chests
     */
    private void saveDoubleDatabaseConfig() {
        try {
            doubleDatabaseConfig.save(doubleDatabaseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the contents of the backpack chest
     */
    private void saveChestContents(String chestID, ItemStack[] contents) {
        File file = new File(backpackDir, chestID + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        for (int i = 0; i < contents.length; i++) {
            config.set("slot" + i, contents[i]);
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the contents of the backpack chest
     */
    private ItemStack[] loadChestContents(String chestID) {
        File file = new File(backpackDir, chestID + ".yml");
        if (!file.exists()) return null;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ItemStack[] contents = new ItemStack[27];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = config.getItemStack("slot" + i);
        }
        return contents;
    }


    /**
     * Get the next available coordinates in a chunk-efficient manner for double chests
     */
    private int[] getNextAvailableCoordinatesForDoubleChest(World world) {
        int chunkX = 6;  // Fixed X-coordinate (do not change)
        int chunkZ = databaseConfig.getInt("last_chunk_z", 0);
        int xIndex = 0;
        int zIndex = 0;
        int yIndex = databaseConfig.getInt("last_y", world.getMinHeight());
        int chunkHeight = world.getMaxHeight(); // Typically 256
        int chunkMin = world.getMinHeight();    // Typically -64

        while (true) {
            int x = (chunkX * 16) + xIndex;  // Absolute X-coordinate
            int z = (chunkZ * 16) + zIndex;  // Absolute Z-coordinate
            int y = yIndex;

            Location loc1 = new Location(world, x, y, z);
            Location loc2 = new Location(world, x + 1, y, z);  // Adjacent block for double chest

            // Check if both locations are available and form a valid double chest
            if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
                // Save the new chunk coordinates
                databaseConfig.set("last_chunk_z", chunkZ);
                databaseConfig.set("last_y", yIndex);
                saveDatabaseConfig();
                return new int[]{x, y, z};
            }

            // Increment Y first (fill from bottom to top)
            yIndex++;
            if (yIndex >= chunkHeight) {
                yIndex = chunkMin;
                zIndex++;  // Move to next Z (within chunk)
                if (zIndex >= 16) {
                    zIndex = 0;
                    xIndex++;
                    if (xIndex >= 16) {
                        xIndex = 0;
                        chunkZ++;  // Move to the next chunk in the Z direction
                    }
                }
            }
        }
    }


    /**
     * Save the contents of the double chest
     */
    private void saveDoubleChestContents(String chestID, ItemStack[] contents) {
        File file = new File(backpackDir, chestID + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        for (int i = 0; i < contents.length; i++) {
            config.set("slot" + i, contents[i]);
        }
        try {
            config.save(file);
            Bukkit.getLogger().info("Double chest contents saved for chest ID: " + chestID);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save double chest contents for chest ID: " + chestID);
            e.printStackTrace();
        }
    }

    /**
     * Load the contents of the double chest
     */
    private ItemStack[] loadDoubleChestContents(String chestID) {
        File file = new File(backpackDir, chestID + ".yml");
        if (!file.exists()) return null;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ItemStack[] contents = new ItemStack[54]; // Double chest size
        for (int i = 0; i < contents.length; i++) {
            contents[i] = config.getItemStack("slot" + i);
        }
        return contents;
    }

    /**
     * Convert all blocks within the radius to falling entities while preserving block states and orientations
     */
    private void convertBlocksToFallingEntities(World world, Location center, int radius) {
        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int y = cy - radius; y <= cy + radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    Location loc = new Location(world, x, y, z);
                    double distance = loc.distance(center);

                    // Check if within radius
                    if (distance <= radius) {
                        Block block = loc.getBlock();
                        if (block.getType() != Material.AIR) {
                            try {
                                // Get the block data (preserves state and orientation)
                                BlockData blockData = block.getBlockData();

                                // Spawn the falling block with the correct data and state
                                FallingBlock fallingBlock = world.spawnFallingBlock(loc, blockData);
                                fallingBlock.setDropItem(true); // Prevent block drops

                                // Set fall damage immunity for specific block types (like Anvils)
                                if (fallingBlock.getType().toString().contains("ANVIL")) {
                                    fallingBlock.setHurtEntities(false);
                                }

                                // Remove the original block after spawning the falling entity
                                block.setType(Material.AIR);

                            } catch (IllegalArgumentException e) {
                                Bukkit.getLogger().warning("Failed to convert block at " + loc + ": " + e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Attract entities towards the black hole center
     */
    private void attractEntities(World world, Location center, int radius) {


        for (Entity entity : world.getNearbyEntities(center, radius, radius, radius)) {
            if (entity.getCustomName() != null && entity.getCustomName().equals("BlackHolev2")) {
                continue; // Skip entities named "BlackHolev2"
            }

            double distance = entity.getLocation().distance(center);

            if (distance > 0 && distance <= radius) {
                // Calculate the attraction strength (1 to 4) with linear falloff
                double strength = 4 - 3 * (distance / radius);

                // Calculate the vector towards the center
                Vector direction = center.toVector().subtract(entity.getLocation().toVector()).normalize();
                Vector velocity = direction.multiply(strength);

                // Apply the velocity to the entity
                entity.setVelocity(velocity);
            }
        }
    }


    /**
     * Modifies the given ItemStack so that its PublicBukkitValues section:
     * - Replaces any "executableblocks:eb-id" with "executableitems:ei-id"
     * - Replaces any existing "executableitems:ei-id" value with "GUINoClick"
     * - If missing, adds a meta section (if necessary) with:
     * ==: ItemMeta
     * meta-type: UNSPECIFIC
     * display-name: '{"text":"","extra":[{"text":"","obfuscated":false,"italic":false,"underlined":false,"strikethrough":false,"color":"white","bold":true}]}'
     * PublicBukkitValues: |-
     * {
     * "executableitems:ei-id": "GUINoClick",
     * "executableitems:ei-disablestack": "a3b73c3d-deb4-4708-95a3-dac9ec7d4733",
     * "score:usage": 1
     * }
     * <p>
     * Debug messages are sent to the player.
     */
    private ItemStack modifyItemForView(ItemStack item) {
        YamlConfiguration config = new YamlConfiguration();
        // Place the item under a known section "slot0"
        config.set("slot0", item);
        String yaml = config.saveToString();

        // Replace any occurrence of "executableblocks:eb-id" with "executableitems:ei-id"
        yaml = yaml.replaceAll("(?i)\"executableblocks:eb-id\"", "\"executableitems:ei-id\"");

        // Replace any existing "executableitems:ei-id" value with "GUINoClick"
        yaml = yaml.replaceAll("(?i)(\"executableitems:ei-id\"\\s*:\\s*\")[^\"]+\"", "$1GUINoClick\"");

        // Ensure that a meta section exists in slot0.
        // We'll check for "slot0:" followed by a newline and two spaces then "meta:"
        if (!yaml.contains("meta:")) {
            @SuppressWarnings("TextBlockMigration") String metaBlock =
                    "\n  meta:\n" +
                            "    ==: ItemMeta\n" +
                            "    meta-type: UNSPECIFIC\n" +
                            "    PublicBukkitValues: |-\n" +
                            "      {\n" +
                            "          \"executableitems:ei-id\": \"GUINoClick\",\n" +
                            "          \"score:usage\": 1\n" +
                            "      }";
            // Append the meta block to the slot0 section.
            yaml += metaBlock;
        } else if (!yaml.contains("PublicBukkitValues: |-")) //noinspection GrazieInspection
        {
            // Meta exists but PublicBukkitValues is missing.
            // Insert PublicBukkitValues before the closing of meta.
            // This regex finds the last line in the meta section that is just whitespace followed by a "}".
            //noinspection TextBlockMigration
            yaml = yaml.replaceFirst(" {2}meta:\n" +
                    " {4}==:", "  meta:\n" +
                    "    PublicBukkitValues: |-\n" +
                    "      {\n" +
                    "          \"executableitems:ei-id\": \"GUINoClick\",\n" +
                    "          \"score:usage\": 1\n" +
                    "      }\n" +
                    "    ==:");
        }

        try {
            config.loadFromString(yaml);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        ItemStack newItem = config.getItemStack("slot0");
        if (newItem == null) {
            newItem = item;
        } 

        return newItem;
    }




    private ItemStack getDefaultGlassPane() {
        YamlConfiguration config = new YamlConfiguration();


        try {
            config.loadFromString("slot0:\n" +
                    "  ==: org.bukkit.inventory.ItemStack\n" +
                    "  v: 3955\n" +
                    "  type: LIGHT_GRAY_STAINED_GLASS_PANE\n" +
                    "  meta:\n" +
                    "    ==: ItemMeta\n" +
                    "    meta-type: UNSPECIFIC\n" +
                    "    display-name: '{\"text\":\"\",\"extra\":[{\"text\":\"\",\"obfuscated\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"color\":\"white\",\"bold\":true}]}'\n" +
                    "    PublicBukkitValues: |-\n" +
                    "      {\n" +
                    "          \"executableitems:ei-id\": \"GUINoClick\",\n" +
                    "          \"score:usage\": 1\n" +
                    "      }");
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        ItemStack pane = config.getItemStack("slot0");

        if (pane == null) {
            pane = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        }

        return pane;
    }


    private String findNextInChain(String chainType, int radius, List<UUID> uuids) {
        // Step 1: Get the last UUID from the list
        UUID lastUUID = uuids.getLast();
        Entity lastEntity = Bukkit.getEntity(lastUUID);

        // If the last entity is null, return '?'
        if (lastEntity == null) {
            return "x";
        }

        Location lastLocation = lastEntity.getLocation();
        double closestDistance = Double.MAX_VALUE;
        Entity closestEntity = null;
        
        
        try {
            // Step 2: Find nearby entities based on chain type
            for (Entity entity : Objects.requireNonNull(lastLocation.getWorld()).getNearbyEntities(lastLocation, radius, radius, radius)) {
                // Skip entities already in the chain
                if (uuids.contains(entity.getUniqueId())) {
                    continue;
                }

                // Check entity AI capability
                if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).hasAI()) {
                    continue;
                }

                // Apply chain type filtering
                switch (chainType.toUpperCase()) {
                    case "ENTITY":
                        if (entity instanceof Player) continue;

                        break;
                    case "PLAYER":
                        if (!(entity instanceof Player)) continue;
                        break;
                    case "BOTH":
                        break;
                    default:
                        continue;
                }

                double distance = entity.getLocation().distance(lastLocation);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        } catch (NullPointerException e) {
            return "x";
        }
    

        // Step 3: Return the UUID of the closest entity, or '?' if not found
        return (closestEntity != null) ? closestEntity.getUniqueId().toString() : "x";
    }

    /**
     * Displays a particle cube at the specified location.
     */
    public void displayCubeWithParticles(World world, double x, double y, double z, String particleType, double width, boolean force, int density, Player p) {
        double halfWidth = width / 2.0;
        Particle particle = Particle.valueOf(particleType.toUpperCase());

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
        displayLine(p, particle, corners[0], corners[1], density, force); // Bottom North
        displayLine(p, particle, corners[0], corners[2], density, force); // Bottom West
        displayLine(p, particle, corners[1], corners[3], density, force); // Bottom East
        displayLine(p, particle, corners[2], corners[3], density, force); // Bottom South
        displayLine(p, particle, corners[4], corners[5], density, force); // Top North
        displayLine(p, particle, corners[4], corners[6], density, force); // Top West
        displayLine(p, particle, corners[5], corners[7], density, force); // Top East
        displayLine(p, particle, corners[6], corners[7], density, force); // Top South
        displayLine(p, particle, corners[0], corners[4], density, force); // Vertical NW
        displayLine(p, particle, corners[1], corners[5], density, force); // Vertical NE
        displayLine(p, particle, corners[2], corners[6], density, force); // Vertical SW
        displayLine(p, particle, corners[3], corners[7], density, force); // Vertical SE

        // Render cube faces
        renderFace(p, particle, corners[0], corners[1], corners[4], corners[5], density, force); // North Face
        renderFace(p, particle, corners[2], corners[3], corners[6], corners[7], density, force); // South Face
        renderFace(p, particle, corners[0], corners[2], corners[4], corners[6], density, force); // West Face
        renderFace(p, particle, corners[1], corners[3], corners[5], corners[7], density, force); // East Face
        renderFace(p, particle, corners[4], corners[5], corners[6], corners[7], density, force); // Top Face
        renderFace(p, particle, corners[0], corners[1], corners[2], corners[3], density, force); // Bottom Face
    }

    private void renderFace(Player p, Particle particle, Location corner1, Location corner2, Location corner3, Location corner4, int density, boolean force) {
        for (int i = 1; i < density; i++) {
            double t = (double) i / density;

            // Create intermediate lines between edges
            Location start = interpolate(corner1, corner2, t);
            Location end = interpolate(corner3, corner4, t);
            displayLine(p, particle, start, end, density, force);

            start = interpolate(corner1, corner3, t);
            end = interpolate(corner2, corner4, t);
            displayLine(p, particle, start, end, density, force);
        }
    }

    private Location interpolate(Location start, Location end, double t) {
        double x = start.getX() + (end.getX() - start.getX()) * t;
        double y = start.getY() + (end.getY() - start.getY()) * t;
        double z = start.getZ() + (end.getZ() - start.getZ()) * t;
        return new Location(start.getWorld(), x, y, z);
    }

    private void displayLine(Player p, Particle particle, Location start, Location end, int density, boolean force) {
        for (int i = 0; i <= density; i++) {
            double t = (double) i / density;
            double x = start.getX() + (end.getX() - start.getX()) * t;
            double y = start.getY() + (end.getY() - start.getY()) * t;
            double z = start.getZ() + (end.getZ() - start.getZ()) * t;
            Location loc = new Location(start.getWorld(), x, y, z);
            p.spawnParticle(particle, loc, 0, 0, 0, 0, 0, null, force);
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

        if (identifier.startsWith("particleText_")) {
            try {
                String params = identifier.substring("particleText_".length());

                // Parse identifier: world,x,y,z,particle,density,viewDistance,size,rotation,text
                String[] parts = params.split(",", 10);
                if (parts.length != 10) return "Invalid format";

                String worldName = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                Particle particle = Particle.valueOf(parts[4].toUpperCase());
                int density = Math.max(1, Integer.parseInt(parts[5]));
                String viewDistance = parts[6];
                double size = Double.parseDouble(parts[7]);
                double rotation = Math.toRadians(Double.parseDouble(parts[8]));
                String text = parts[9];

                Location center = new Location(Bukkit.getWorld(worldName), x, y, z);
                long now = System.currentTimeMillis();

                if (particleDebugEnabled) {
                    p.sendMessage("§7[Debug] world=" + worldName + " x=" + x + " y=" + y + " z=" + z);
                    p.sendMessage("§7[Debug] particle=" + particle + " density=" + density + " size=" + size + " rotation=" + rotation);
                    p.sendMessage("§7[Debug] text=\"" + text + "\" view=" + viewDistance);
                }

                // 1. RAM cache
                CachedParticleData cached = memoryCache.get(identifier);
                if (cached != null && now - cached.timestamp < EXPIRY_TIME) {
                    if (particleDebugEnabled) p.sendMessage("§7[Debug] RAM cache hit.");
                    displayToNearby(center, cached.locations, particle, viewDistance);
                    return "";
                }

                // 2. Disk cache using hashed identifier
                String hash = hashSHA256(identifier);
                String safeText = text.replaceAll("[^a-zA-Z0-9]", "");
                File diskFile = new File(PARTICLE_DIR, hash + "_" + safeText + ".dat");

                if (diskFile.exists()) {
                    if (particleDebugEnabled) p.sendMessage("§7[Debug] Disk cache hit: " + diskFile.getName());
                    List<Vector> vectors = loadFromDisk(diskFile);
                    List<Location> worldLocs = applyToWorld(center, vectors);
                    memoryCache.put(identifier, new CachedParticleData(worldLocs, now));
                    displayToNearby(center, worldLocs, particle, viewDistance);
                    return "";
                }

                // 3. Compute fresh
                if (particleDebugEnabled) p.sendMessage("§7[Debug] Generating new particle text mesh...");
                boolean[][] matrix = renderTextToMatrix(text);
                List<Vector> vectors = matrixToVectors(matrix, density, size, rotation);
                List<Location> worldLocs = applyToWorld(center, vectors);

                saveToDisk(diskFile, vectors);
                memoryCache.put(identifier, new CachedParticleData(worldLocs, now));
                displayToNearby(center, worldLocs, particle, viewDistance);
                return "";

            } catch (Exception e) {
                if (particleDebugEnabled) {
                    p.sendMessage("§c[Debug Error] Failed: " + identifier);
                    p.sendMessage("§c[Debug Error] " + e.getClass().getSimpleName() + ": " + e.getMessage());
                    for (StackTraceElement el : e.getStackTrace()) p.sendMessage("§7" + el.toString());
                }
                return "§cFailed! " + e.getMessage();
            }
        }


        if (identifier.startsWith("nearestPlayerNotTeam2_")) {
            String params = identifier.substring("nearestPlayerNotTeam2_".length());
            String[] parts = params.split(",");

            if (parts.length != 6) {
                return "?";
            }

            String teamName = parts[0];
            int radius;
            String worldName = parts[2];
            double x, y, z;

            try {
                radius = Integer.parseInt(parts[1]);
                x = Double.parseDouble(parts[3]);
                y = Double.parseDouble(parts[4]);
                z = Double.parseDouble(parts[5]);
            } catch (NumberFormatException e) {
                return "?";
            }

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return "?";
            }

            Location origin = new Location(world, x, y, z);

            // Get the specified team from the scoreboard
            Team team = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeam(teamName);
            if (team == null) {
                return "?";
            }

            Player nearestPlayer = null;
            double nearestDistance = Double.MAX_VALUE;

            for (Player target : world.getPlayers()) {
                // Skip players on the specified team
                if (team.hasEntry(target.getName())) {
                    continue;
                }

                // Check if player is inside a region that includes "criminalbase" in its ID
                Location loc = target.getLocation();
                com.sk89q.worldedit.util.Location wgLoc = BukkitAdapter.adapt(loc);
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regions = container.get(BukkitAdapter.adapt(world));

                if (regions == null) {
                    continue;
                }

                ApplicableRegionSet set = regions.getApplicableRegions(wgLoc.toVector().toBlockPoint());
                boolean inCriminalBase = set.getRegions().stream()
                        .anyMatch(r -> r.getId().toLowerCase().contains("criminalbase"));

                if (!inCriminalBase) {
                    continue;
                }

                // Calculate distance
                double distance = origin.distance(loc);
                if (distance <= radius && distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = target;
                }
            }

            if (nearestPlayer != null) {
                return nearestPlayer.getUniqueId().toString();
            }

            return "?";
        }


        if (identifier.startsWith("visualBreak_")) {
            // Expected format: %Archistructure_visualBreak_STAGE,world,x,y,z%
            String params = identifier.substring("visualBreak_".length());
            String[] parts = params.split(",");

            if (parts.length != 5) {
                return "Invalid format!" + identifier;
            }

            int stage;
            String worldName = parts[1];
            int x, y, z;

            try {
                stage = Integer.parseInt(parts[0]); // 1-10
                if (stage < 1 || stage > 10) return "Stage must be 1–10";

                x = Integer.parseInt(parts[2]);
                y = Integer.parseInt(parts[3]);
                z = Integer.parseInt(parts[4]);
            } catch (NumberFormatException e) {
                return "Invalid number!";
            }

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return "World not found!";
            }

            Location loc = new Location(world, x, y, z);
            int breakStage = stage - 1;

            // Send animation to player
            try {
                PacketContainer packet = ProtocolLibrary.getProtocolManager()
                        .createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

                int animationId = loc.hashCode();
                packet.getIntegers().write(0, animationId);
                packet.getBlockPositionModifier().write(0, new BlockPosition(x, y, z));
                packet.getIntegers().write(1, breakStage);

                ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
            } catch (Exception e) {
                e.printStackTrace();
                return "Animation error";
            }

            // Reset any previous task
            if (visualBreakTimers.containsKey(loc)) {
                visualBreakTimers.get(loc).cancel();
            }

            // Schedule removal after 2 seconds (40 ticks)
            BukkitTask task = Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), () -> {
                if (loc.getBlock().getType() != Material.AIR) {
                    try {
                        PacketContainer resetPacket = ProtocolLibrary.getProtocolManager()
                                .createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

                        int animationId = loc.hashCode();
                        resetPacket.getIntegers().write(0, animationId);
                        resetPacket.getBlockPositionModifier().write(0, new BlockPosition(x, y, z));
                        resetPacket.getIntegers().write(1, -1); // remove animation

                        ProtocolLibrary.getProtocolManager().sendServerPacket(p, resetPacket);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                visualBreakTimers.remove(loc);
            }, 40L); // 40 ticks = 2 seconds

            visualBreakTimers.put(loc, task);

            return "Visual break stage " + breakStage + " set with reset";
        }

        if (identifier.startsWith("PTFXCUBE_")) {
            // Expected format: %Archistructure_PTFXCUBE_world,x,y,z,particleType,width,normal/force,density%
            String params = identifier.substring("PTFXCUBE_".length());
            String[] parts = params.split(",");

            if (parts.length != 8) {
                return "Invalid format!";
            }

            // Parse the parameters
            String worldName = parts[0];
            double x, y, z, width;
            int density;
            boolean force;

            try {
                x = Double.parseDouble(parts[1]);
                y = Double.parseDouble(parts[2]);
                z = Double.parseDouble(parts[3]);
                width = Double.parseDouble(parts[5]);
                density = Integer.parseInt(parts[7]);
                force = parts[6].equalsIgnoreCase("force");
            } catch (NumberFormatException e) {
                return "Invalid numerical value!";
            }

            String particleType = parts[4];
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                return "World not found!";
            }

            // Display the particle cube
            displayCubeWithParticles(world, x, y, z, particleType, width, force, density, p);
            return "Cube displayed";
        }


        
        
        if (identifier.startsWith("viewChest2_")) {
            // Expected format: %Archistructure_viewChest2_sourceWorld,x,y,z%
            String params = identifier.substring("viewChest2_".length());
            String[] parts = params.split(",");
            if (parts.length != 4) {
                return "Invalid format! Use: %Archistructure_viewChest2_sourceWorld,x,y,z%";
            }
            String sourceWorldName = parts[0];
            int srcX, srcY, srcZ;
            try {
                srcX = Integer.parseInt(parts[1]);
                srcY = Integer.parseInt(parts[2]);
                srcZ = Integer.parseInt(parts[3]);
            } catch (NumberFormatException e) {
                return "Invalid coordinates!";
            }
            World sourceWorld = Bukkit.getWorld(sourceWorldName);
            if (sourceWorld == null) return "Source world not found!";
            Location sourceLoc = new Location(sourceWorld, srcX, srcY, srcZ);
            Block sourceBlock = sourceLoc.getBlock();
            if (sourceBlock.getType() != Material.CHEST) {
                return "No chest found at source location!";
            }
            Chest sourceChest = (Chest) sourceBlock.getState();

            boolean isDouble = sourceChest.getInventory().getSize() > 27;
            if (viewChestDebugLogging) {
                p.sendMessage("DEBUG: Source chest at " + srcX + " " + srcY + " " + srcZ +
                        " detected. It is " + (isDouble ? "double" : "single") + ".");
            }

            int destSize = isDouble ? 54 : 27;

            Inventory fakeInventory = Bukkit.createInventory(null, destSize, "Fake Chest");


            Inventory sourceInv = sourceChest.getInventory();
            int sourceSize = sourceInv.getSize();

            ItemStack[] newContents = new ItemStack[destSize];
            for (int i = 0; i < destSize; i++) {
                if (i < sourceSize && sourceInv.getItem(i) != null) {
                    newContents[i] = modifyItemForView(Objects.requireNonNull(sourceInv.getItem(i)));
                } else {
                    newContents[i] = getDefaultGlassPane();
                }
            }
            // Update destination chest inventory without an extra clear call (setContents overrides existing items)
            try {

                fakeInventory.setContents(newContents);
            } catch (Exception ex) {
                return "Error updating destination chest inventory.";
            }

            // Return the destination coordinates.
            if (viewChestDebugLogging) {
                p.sendMessage("FakeChest");
            }
            
            return "done";
        }
        

        if (identifier.startsWith("repeat_")) return identifier.substring("repeat".length());
        if (identifier.startsWith("chain_")) {
            // Expected format: %Archistructure_chain_ENTITY/PLAYER/BOTH,RADIUS,[uuid1, uuid2, uuid3...],lastuuid%
            String params = identifier.substring("chain_".length());
            String[] parts = params.split(",");

            String chainType = parts[0].toUpperCase();
            int radius;
            try {
                radius = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return "x";
            }

            // Parse the UUID list from the format [uuid1, uuid2, uuid3...]
            List<UUID> uuids = new ArrayList<>();
            for (int i = 2; i < parts.length; i++) {
                try {
                    uuids.add(UUID.fromString(parts[i]));
                } catch (IllegalArgumentException e) {
                    return "x" + parts[i];
                }
            }


            return findNextInChain(chainType, radius, uuids);
        }



        if (identifier.startsWith("viewChest_")) {
            // Expected format: %Archistructure_viewChest_sourceWorld,x,y,z%
            String params = identifier.substring("viewChest_".length());
            String[] parts = params.split(",");
            if (parts.length != 4) {
                return "Invalid format! Use: %Archistructure_viewChest_sourceWorld,x,y,z%";
            }
            String sourceWorldName = parts[0];
            int srcX, srcY, srcZ;
            try {
                srcX = Integer.parseInt(parts[1]);
                srcY = Integer.parseInt(parts[2]);
                srcZ = Integer.parseInt(parts[3]);
            } catch (NumberFormatException e) {
                return "Invalid coordinates!";
            }
            World sourceWorld = Bukkit.getWorld(sourceWorldName);
            if (sourceWorld == null) return "Source world not found!";
            Location sourceLoc = new Location(sourceWorld, srcX, srcY, srcZ);
            Block sourceBlock = sourceLoc.getBlock();
            if (sourceBlock.getType() != Material.CHEST) {
                return "No chest found at source location!";
            }
            Chest sourceChest = (Chest) sourceBlock.getState();

            boolean isDouble = sourceChest.getInventory().getSize() > 27;
            if (viewChestDebugLogging) {
                p.sendMessage("DEBUG: Source chest at " + srcX + " " + srcY + " " + srcZ +
                        " detected. It is " + (isDouble ? "double" : "single") + ".");
            }

            // Use a fixed destination in the "mcydatabase" world.
            World destWorld = Bukkit.getWorld("mcydatabase");
            if (destWorld == null) {
                destWorld = Bukkit.createWorld(new org.bukkit.WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info("Created mcydatabase world.");
            }


            int destX, destY, destZ;
            String playerKey = p.getUniqueId().toString();
            String storedCoords = viewOnlyChestConfig.getString(playerKey);
            if (storedCoords != null) {
                // An entry exists for this player; use it.
                String[] coords = storedCoords.split(" ");
                destX = Integer.parseInt(coords[0]);
                destY = Integer.parseInt(coords[1]);
                destZ = Integer.parseInt(coords[2]);
            } else {
                // No entry exists, so compute new coordinates.
                // Use the "last" entry in viewOnlyChestConfig; if missing, use defaults.
                String lastEntry = viewOnlyChestConfig.getString("last");
                int lastX, lastY, lastZ;
                if (lastEntry == null) {
                    // No previous entries: default to chunk X = 9, chunk Z = 9, relative X = 0, relative Z = 0, Y = world minimum.
                    lastX = 9 * 16;
                    lastZ = 9 * 16;
                    assert destWorld != null;
                    lastY = destWorld.getMinHeight();
                } else {
                    String[] lastParts = lastEntry.split(" ");
                    lastX = Integer.parseInt(lastParts[0]);
                    lastY = Integer.parseInt(lastParts[1]);
                    lastZ = Integer.parseInt(lastParts[2]);
                }
                // Step 1: Add 2 to the last entry's z.
                destX = lastX;
                destY = lastY;
                destZ = lastZ + 2;
                // Step 2: If new z >= 16 (relative to the chunk), reset z to 0 and increment x by 2.
                int relZ = destZ % 16;
                if (relZ >= 16) { // In practice, since we add 2, check if relZ >= 16.
                    destZ = (destZ / 16) * 16; // reset relative z to 0
                    destX = lastX + 2;
                }
                // Step 3: If new x's relative value is >= 16, set x=0 and increment y by 1.
                int relX = destX % 16;
                if (relX >= 16) {
                    destX = (destX / 16) * 16;
                    destY = lastY + 1;
                }
                // Step 4: If y is past the build limit, set y to the minimum and increment x by 16.
                assert destWorld != null;
                if (destY > destWorld.getMaxHeight()) {
                    destY = destWorld.getMinHeight();
                    destX = lastX + 16;
                }
                // Store the computed coordinates under the player's UUID and update the "last" entry.
                String newCoords = destX + " " + destY + " " + destZ;
                viewOnlyChestConfig.set(playerKey, newCoords);
                viewOnlyChestConfig.set("last", newCoords);
                try {
                    viewOnlyChestConfig.save(viewOnlyChestDatabaseFile);
                } catch (IOException ignored) {
                }
            }

            // Now, place chests at the computed coordinates—but only if we computed them now.
            // If the player's entry already existed, we skip placement.
            if (storedCoords == null) {
                // Place a single chest at (destX, destY, destZ)
                Location singleLoc = new Location(destWorld, destX, destY, destZ);
                Block singleBlock = singleLoc.getBlock();
                if (singleBlock.getType() != Material.CHEST) {
                    singleBlock.setType(Material.CHEST);
                } 
                // Place a double chest at (destX, destY, destZ+1) and (destX+1, destY, destZ+1)
                Location doubleLoc1 = new Location(destWorld, destX, destY, destZ + 1);
                Location doubleLoc2 = new Location(destWorld, destX + 1, destY, destZ + 1);
                Block doubleBlock1 = doubleLoc1.getBlock();
                Block doubleBlock2 = doubleLoc2.getBlock();
                if (doubleBlock1.getType() != Material.CHEST || doubleBlock2.getType() != Material.CHEST) {
                    doubleBlock1.setType(Material.CHEST);
                    doubleBlock2.setType(Material.CHEST);
                    // Link the double chest halves
                    Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), () -> {
                        try {
                            Chest chestA = (Chest) doubleBlock1.getState();
                            Chest chestB = (Chest) doubleBlock2.getState();
                            org.bukkit.block.data.type.Chest dataA = (org.bukkit.block.data.type.Chest) chestA.getBlockData();
                            org.bukkit.block.data.type.Chest dataB = (org.bukkit.block.data.type.Chest) chestB.getBlockData();
                            dataA.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
                            dataB.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
                            dataA.setFacing(BlockFace.NORTH);
                            dataB.setFacing(BlockFace.NORTH);
                            chestA.setBlockData(dataA);
                            chestB.setBlockData(dataB);
                            String commonName = p.getUniqueId().toString();
                            chestA.setCustomName(commonName);
                            chestB.setCustomName(commonName);
                            chestA.update(true);
                            chestB.update(true);
                        } catch (Exception ignored) {
                        }
                    }, 1L);
                } 
            } 


            Location destLocation = isDouble ? new Location(destWorld, destX, destY, destZ + 1) : new Location(destWorld, destX, destY, destZ);

            Chest destChest = (Chest) (destLocation.getBlock().getState());


            // Build a modified inventory: copy items from the source chest (modifying the ei-id) and fill any missing slots with a default pane.
            Inventory sourceInv = sourceChest.getInventory();
            int sourceSize = sourceInv.getSize();
            int destSize = isDouble ? 54 : 27;

            ItemStack[] newContents = new ItemStack[destSize];
            for (int i = 0; i < destSize; i++) {
                if (i < sourceSize && sourceInv.getItem(i) != null) {
                    newContents[i] = modifyItemForView(Objects.requireNonNull(sourceInv.getItem(i)));
                } else {
                    newContents[i] = getDefaultGlassPane();
                }
            }
            // Update destination chest inventory without an extra clear call (setContents overrides existing items)
            try {
                destChest.update();

                destChest.getInventory().setContents(newContents);
            } catch (Exception ex) {
                return "Error updating destination chest inventory.";
            }

            // Return the destination coordinates.
            if (viewChestDebugLogging) {
                p.sendMessage("DEBUG: viewChest processing complete. Returning coordinates: " + destX + " " + destY + " " + destZ);
            }
            return isDouble ? destX + " " + destY + " " + (destZ + 1 ): destX + " " + destY + " " + destZ;
        }




        if (identifier.startsWith("blackHole_")) {
            try {
                String[] parts = identifier.substring("blackHole_".length()).split(",");
                if (parts.length != 5) {
                    return "Invalid format!";
                }

                String worldName = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int z = Integer.parseInt(parts[3]);
                int radius = Integer.parseInt(parts[4]);

                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    return "World not found!";
                }

                Location center = new Location(world, x, y, z);

                // Convert blocks to falling blocks
                convertBlocksToFallingEntities(world, center, radius);

                // Attract entities to the center
                attractEntities(world, center, radius);

                return x + " " + y + " " + z;
            } catch (Exception e) {
                e.printStackTrace();
                return "x" + e.getMessage();
            }
        }


        if (identifier.startsWith("x_")) {
            String uuidString = identifier.substring("x_".length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    double x = entity.getLocation().getX();
                    return String.format("%.3f", x);
                }
            } catch (IllegalArgumentException e) {
                return "x";
            }
            return "x";
        }

        if (identifier.startsWith("y_")) {
            String uuidString = identifier.substring("y_".length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    double y = entity.getLocation().getY();
                    return String.format("%.3f", y);
                }
            } catch (IllegalArgumentException e) {
                return "x";
            }
            return "x";
        }

        if (identifier.startsWith("z_")) {
            String uuidString = identifier.substring("z_".length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    double z = entity.getLocation().getZ();
                    return String.format("%.3f", z);
                }
            } catch (IllegalArgumentException e) {
                return "x";
            }
            return "x";
        }



        if (identifier.startsWith("openBackpack2_")) {
            long id = Long.parseLong(identifier.substring("openBackpack2_".length()));

            // Get or create the world
            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(new org.bukkit.WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(org.bukkit.WorldType.FLAT));
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }

            // Get player UUID
            String uuid = p.getUniqueId().toString();

            // Load or assign coordinates from the double chest database
            int x, y, z;
            String coordinates = doubleDatabaseConfig.getString(uuid);
            if (coordinates != null) {
                String[] coords = coordinates.split(" ");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1]);
                z = Integer.parseInt(coords[2]);
            } else {
                // Assign new coordinates in a chunk-efficient way
                assert world != null;
                int[] newCoords = getNextAvailableCoordinatesForDoubleChest(world);
                x = newCoords[0];
                y = newCoords[1];
                z = newCoords[2];

                // Save the assigned coordinates to the double chest database
                String newCoordinates = x + " " + y + " " + z;
                doubleDatabaseConfig.set(uuid, newCoordinates);
                saveDoubleDatabaseConfig();
            }

            // Create the double chest at the given coordinates if not already present
            Location loc1 = new Location(world, x, y, z);
            Location loc2 = new Location(world, x + 1, y, z); // Adjacent chest for double chest pair

            Block block1 = loc1.getBlock();
            Block block2 = loc2.getBlock();

            if (!(block1.getState() instanceof Chest) || !(block2.getState() instanceof Chest)) {
                block1.setType(Material.CHEST);
                block2.setType(Material.CHEST);

                // Wait for the world to register the chest placement
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), () -> {
                    Chest chest1 = (Chest) block1.getState();
                    Chest chest2 = (Chest) block2.getState();

                    // Update the block data to link them as a double chest
                    chest1.setCustomName(String.valueOf(id));
                    chest1.update(true);
                    chest2.setCustomName(String.valueOf(id));
                    chest2.update(true);

                    Bukkit.getLogger().info("Double chest created at " + x + ", " + y + ", " + z);
                }, 1L);
                }

            Chest chest1 = (Chest) block1.getState();

            // Save current chest contents if the chest is already named
            String currentChestName = chest1.getCustomName();
            if (currentChestName != null && !currentChestName.isEmpty()) {
                saveDoubleChestContents(currentChestName, chest1.getInventory().getContents());
            }

            // Set the new chest name and update it
            chest1.setCustomName(String.valueOf(id));
            chest1.update(true);
            chest1.getInventory().clear();

            // Load the new chest contents from the file if available
            ItemStack[] savedContents = loadDoubleChestContents(String.valueOf(id));
            if (savedContents != null) {
                chest1.getInventory().setContents(savedContents);
                Bukkit.getLogger().info("Loaded double chest contents for chest ID: " + id);
            } else {
                Bukkit.getLogger().info("No previous contents found for double chest ID: " + id);
            }

            return x + " " + y + " " + z;
        }

        
        
        if (identifier.startsWith("openBackpack_")) {
            long id = Long.parseLong(identifier.substring("openBackpack_".length()));

            // Get or create the world
            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(new org.bukkit.WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(org.bukkit.WorldType.FLAT));
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }

            // Get player UUID
            String uuid = p.getUniqueId().toString();

            // Load or assign coordinates from the database
            int x, y, z;
            String coordinates = databaseConfig.getString(uuid);
            if (coordinates != null) {
                String[] coords = coordinates.split(" ");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1]);
                z = Integer.parseInt(coords[2]);
            } else {
                // Assign new coordinates in a chunk-efficient way
                assert world != null;
                int[] newCoords = getNextAvailableCoordinates(world);
                x = newCoords[0];
                y = newCoords[1];
                z = newCoords[2];

                // Save the assigned coordinates to the database
                String newCoordinates = x + " " + y + " " + z;
                databaseConfig.set(uuid, newCoordinates);
                saveDatabaseConfig();
            }

            // Create the chest at the given coordinates if not already present
            Location loc = new Location(world, x, y, z);
            Block block = loc.getBlock();
            if (!(block.getState() instanceof Chest)) {
                block.setType(Material.CHEST);
                Bukkit.getLogger().info("Backpack chest created at " + x + ", " + y + ", " + z);
            }

            Chest chest = (Chest) block.getState();

// Check if the chest already has a name and save its contents before updating
            String currentChestName = chest.getCustomName();
            if (currentChestName != null && !currentChestName.isEmpty()) {
                // Save the existing chest contents to a file based on its current name
                saveChestContents(currentChestName, chest.getInventory().getContents());
            }

// Update the chest name and clear its contents
            chest.setCustomName(String.valueOf(id));
            chest.update(true);
            chest.getInventory().clear();

// Load the new chest contents from the file if available
            ItemStack[] savedContents = loadChestContents(String.valueOf(id));
            if (savedContents != null) {
                chest.getInventory().setContents(savedContents);
                Bukkit.getLogger().info("Loaded backpack contents for chest ID: " + id);
            } else {
                Bukkit.getLogger().info("No previous contents found for chest ID: " + id);
            }

            return x + " " + y + " " + z;
        }

// Tunable displacement to help unstick projectiles
        final double NUDGE_AMOUNT = 0.05;

        if (identifier.startsWith("bounce2_")) {
            // Format: %Archistructure_bounce2_UUID,FACE,PITCH,YAW,VELOCITY%
            String[] parts = identifier.substring("bounce2_".length()).split(",");

            if (parts.length != 5) return "Invalid format!";

            UUID uuid;
            try {
                uuid = UUID.fromString(parts[0]);
            } catch (IllegalArgumentException e) {
                return "Invalid UUID!";
            }

            BlockFace face;
            try {
                face = BlockFace.valueOf(parts[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                return "Invalid face!";
            }

            Entity entity = null;
            for (World world : Bukkit.getWorlds()) {
                entity = world.getEntities().stream()
                        .filter(e -> e.getUniqueId().equals(uuid))
                        .findFirst()
                        .orElse(null);
                if (entity != null) break;
            }

            if (entity == null) return "Entity not found";

            float pitch, yaw;
            double velocityMagnitude;

            try {
                // Handle pitch input
                if (parts[2].equalsIgnoreCase("pitch")) {
                    pitch = entity.getLocation().getPitch();
                } else {
                    pitch = Float.parseFloat(parts[2]);
                }

                // Handle yaw input
                if (parts[3].equalsIgnoreCase("yaw")) {
                    yaw = entity.getLocation().getYaw();
                } else {
                    yaw = Float.parseFloat(parts[3]);
                }

                velocityMagnitude = Double.parseDouble(parts[4]);

            } catch (NumberFormatException e) {
                return "Invalid number!";
            }

            // Convert pitch/yaw to direction vector
            float pitchRad = (float) Math.toRadians(pitch);
            float yawRad = (float) Math.toRadians(yaw);

            double x = -Math.sin(yawRad) * Math.cos(pitchRad);
            double y = -Math.sin(pitchRad);
            double z = Math.cos(yawRad) * Math.cos(pitchRad);
            Vector direction = new Vector(x, y, z).normalize().multiply(velocityMagnitude);

            Vector adjustedVelocity = direction.clone();

            // Reflect based on impact face
            switch (face) {
                case UP:
                case DOWN:
                    adjustedVelocity.setY(-adjustedVelocity.getY());
                    break;
                case NORTH:
                case SOUTH:
                    adjustedVelocity.setZ(-adjustedVelocity.getZ());
                    break;
                case EAST:
                case WEST:
                    adjustedVelocity.setX(-adjustedVelocity.getX());
                    break;
                default:
                    return "Invalid face!";
            }

            // Directional unstick nudge
            Vector nudgeVector = new Vector(face.getModX(), face.getModY(), face.getModZ()).multiply(NUDGE_AMOUNT);
            entity.setVelocity(nudgeVector); // Initial nudge

            // Schedule proper bounce
            Entity finalEntity = entity;
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), () -> {
                Location nudgedLoc = finalEntity.getLocation().add(nudgeVector);
                finalEntity.teleport(nudgedLoc);
                finalEntity.setVelocity(adjustedVelocity);
                finalEntity.setTicksLived(1);
            }, 1L);

            return String.format("%.4f", adjustedVelocity.length());
        }

        
        
        if (identifier.startsWith("checkVelocity_")) {
            String uuidStr = identifier.substring("checkVelocity_".length());

            UUID uuid;
            try {
                uuid = UUID.fromString(uuidStr);
            } catch (IllegalArgumentException e) {
                return "Invalid UUID";
            }

            Entity target = null;

            // Search all worlds for entity with that UUID
            for (World world : Bukkit.getWorlds()) {
                target = world.getEntities().stream()
                        .filter(ent -> ent.getUniqueId().equals(uuid))
                        .findFirst()
                        .orElse(null);
                if (target != null) break;
            }

            if (target == null) {
                return "Entity not found";
            }

            Vector v = target.getVelocity();
            return v.getX() + " " + v.getY() + " " + v.getZ();
        }

// Tunable displacement to help unstick projectiles

        if (identifier.startsWith("bounce_")) {
            // Format: %Archistructure_bounce_UUID,FACE,SCALE%
            String[] parts = identifier.substring("bounce_".length()).split(",");

            if (parts.length != 3) return "Invalid format!";

            UUID uuid;
            try {
                uuid = UUID.fromString(parts[0]);
            } catch (IllegalArgumentException e) {
                return "Invalid UUID!";
            }

            BlockFace face;
            try {
                face = BlockFace.valueOf(parts[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                return "Invalid face!";
            }

            double scale;
            try {
                scale = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                return "Invalid scale!";
            }


            Entity entity = null;
            for (World world : Bukkit.getWorlds()) {
                entity = world.getEntities().stream()
                        .filter(e -> e.getUniqueId().equals(uuid))
                        .findFirst()
                        .orElse(null);
                if (entity != null) break;
            }

            if (entity == null) return "Entity not found";

            Vector originalVelocity = entity.getVelocity();
            Vector adjustedVelocity = originalVelocity.clone();

            // Reflect based on impact face
            switch (face) {
                case UP:
                case DOWN:
                    adjustedVelocity.setY(-adjustedVelocity.getY());
                    break;
                case NORTH:
                case SOUTH:
                    adjustedVelocity.setZ(-adjustedVelocity.getZ());
                    break;
                case EAST:
                case WEST:
                    adjustedVelocity.setX(-adjustedVelocity.getX());
                    break;
                default:
                    return "Invalid face!";
            }

            adjustedVelocity.normalize().multiply(scale);

            // Determine the proper nudge direction from the impact face
            Vector nudgeDirection = new Vector(face.getModX(), face.getModY(), face.getModZ()).multiply(NUDGE_AMOUNT);

            // Apply a small bump in the direction *away* from the block
            entity.setVelocity(nudgeDirection); // will help unstick the entity

            // Apply real bounce in the next tick
            Entity finalEntity = entity;
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), () -> {
                // Slightly offset the location too, in the same direction
                Location nudgedLoc = finalEntity.getLocation().add(nudgeDirection);
                finalEntity.teleport(nudgedLoc);

                finalEntity.setVelocity(adjustedVelocity);
                finalEntity.setTicksLived(1);
            }, 1L);

            return String.format("%.4f", adjustedVelocity.length());
        }


        if (identifier.startsWith("vacuumCleaner_")) {
            String[] parts = identifier.substring("vacuumCleaner_".length()).split(",");
            if (parts.length < 8) {
                return "Invalid format: Expected 8 parameters";
            }

            try {
                // Extract world and coordinates
                World sourceWorld = Bukkit.getWorld(parts[0]);
                int sourceX = Integer.parseInt(parts[1]);
                int sourceY = Integer.parseInt(parts[2]);
                int sourceZ = Integer.parseInt(parts[3]);
                World targetWorld = Bukkit.getWorld(parts[4]);
                int targetX = Integer.parseInt(parts[5]);
                int targetY = Integer.parseInt(parts[6]);
                int targetZ = Integer.parseInt(parts[7]);

                if (sourceWorld == null || targetWorld == null) {
                    return "Invalid world name.";
                }

                Location sourceLocation = new Location(sourceWorld, sourceX, sourceY, sourceZ);
                Location targetLocation = new Location(targetWorld, targetX, targetY, targetZ);

                // Check if target is a chest
                Block targetBlock = targetLocation.getBlock();
                if (!(targetBlock.getState() instanceof Chest chest)) {
                    return "There is no chest at " + targetX + "," + targetY + "," + targetZ + " in " + targetWorld.getName();
                }

                Inventory chestInventory = chest.getInventory(); // Get the chest's inventory

                // Collect dropped items in a 1x1x1 area
                List<Item> itemsToTransfer = new ArrayList<>();
                for (Entity entity : sourceWorld.getNearbyEntities(sourceLocation, 0.7, 0.7, 0.7)) {
                    if (entity instanceof Item itemEntity) {
                        itemsToTransfer.add(itemEntity);
                    }
                }

                if (itemsToTransfer.isEmpty()) {
                    return "No items to vacuum at " + sourceX + "," + sourceY + "," + sourceZ;
                }

                // Insert items one-by-one into the chest
                for (Item itemEntity : itemsToTransfer) {
                    ItemStack itemStack = itemEntity.getItemStack();

                    // Find the first empty slot
                    int emptySlot = chestInventory.firstEmpty();
                    if (emptySlot != -1) {
                        // Place item in chest and remove from world
                        chestInventory.setItem(emptySlot, itemStack);
                        itemEntity.remove();
                    } else {
                        // If chest is full, just delete the item
                        itemEntity.remove();
                    }
                }

                return "Vacuum transfer complete!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error processing vacuum cleaner.";
            }
        }




        if (identifier.startsWith("remoteHopper_")) {
            String[] parts = identifier.substring("remoteHopper_".length()).split(",");
            if (parts.length != 8) {
                return "Invalid format. Use: %Archistructure_remoteHopper_INWorld,InX,InY,InZ,OutWorld,OutX,OutY,OutZ%";
            }

            try {
                // Extract input coordinates
                World inWorld = Bukkit.getWorld(parts[0]);
                int inX = Integer.parseInt(parts[1]);
                int inY = Integer.parseInt(parts[2]);
                int inZ = Integer.parseInt(parts[3]);

                // Extract output coordinates
                World outWorld = Bukkit.getWorld(parts[4]);
                int outX = Integer.parseInt(parts[5]);
                int outY = Integer.parseInt(parts[6]);
                int outZ = Integer.parseInt(parts[7]);

                if (inWorld == null || outWorld == null) {
                    return "§cInvalid world!";
                }

                Block inBlock = inWorld.getBlockAt(inX, inY, inZ);
                Block outBlock = outWorld.getBlockAt(outX, outY, outZ);

                if (!(inBlock.getState() instanceof Hopper inHopper)) {
                    return "§cIN block is not a hopper!";
                }

                if (!(outBlock.getState() instanceof Hopper outHopper)) {
                    return "§cOUT block is not a hopper!";
                }

                Inventory inInventory = inHopper.getInventory();
                Inventory outInventory = outHopper.getInventory();

                // Find the first non-empty slot in the input hopper
                ItemStack itemToTransfer = null;
                int sourceSlot = -1;

                for (int i = 0; i < inInventory.getSize(); i++) {
                    ItemStack item = inInventory.getItem(i);
                    if (item != null && item.getAmount() > 0) {
                        itemToTransfer = item.clone(); // Clone the entire stack
                        sourceSlot = i;
                        break;
                    }
                }

                if (itemToTransfer == null) {
                    return "§cNo items in IN hopper!";
                }

                int transferAmount = itemToTransfer.getAmount(); // Get the entire stack amount

                // Try to add the item to an existing stack first
                for (int i = 0; i < outInventory.getSize(); i++) {
                    ItemStack existingItem = outInventory.getItem(i);

                    if (existingItem != null && existingItem.isSimilar(itemToTransfer)) {
                        int maxStackSize = existingItem.getMaxStackSize();
                        int currentAmount = existingItem.getAmount();

                        if (currentAmount < maxStackSize) {
                            int spaceLeft = maxStackSize - currentAmount;
                            int amountToMove = Math.min(transferAmount, spaceLeft);

                            existingItem.setAmount(currentAmount + amountToMove);
                            transferAmount -= amountToMove;

                            if (transferAmount <= 0) {
                                inInventory.setItem(sourceSlot, null); // Remove stack from input
                                return "§aStack transferred successfully!";
                            }
                        }
                    }
                }

                // If there is still remaining amount to transfer, find an empty slot
                int emptySlot = outInventory.firstEmpty();
                if (emptySlot != -1) {
                    itemToTransfer.setAmount(transferAmount);
                    outInventory.setItem(emptySlot, itemToTransfer);
                    inInventory.setItem(sourceSlot, null); // Remove stack from input
                    return "§aStack transferred successfully!";
                }

                return "§cOUT hopper is full!";
            } catch (NumberFormatException e) {
                return "§cInvalid coordinate format!";
            } catch (Exception e) {
                e.printStackTrace();
                return "§cError transferring item!";
            }
        }

        
        
        if (identifier.startsWith("XRAY-")) {
            String[] args = identifier.substring("XRAY-".length()).split("-");
            if (args.length != 2) {
                return "Invalid format! Use: %Archistructure_XRAY-RADIUS-SECONDS%";
            }

            try {
                int radius = Integer.parseInt(args[0]);
                int duration = Integer.parseInt(args[1]) * 20; // Convert seconds to ticks

                activateXRay(p, radius, duration);
                return "X-Ray v2 Activated!";
            } catch (NumberFormatException e) {
                return "Invalid numbers!";
            }
        }

        if (identifier.equals("eifolderresetperms") && p != null) {
            User user = luckPerms.getPlayerAdapter(Player.class).getUser(p);

            // Skip users with "ei.*" or "ei.itemfolderbypass"
            if (user.getCachedData().getPermissionData().checkPermission("ei.*").asBoolean() ||
                    user.getCachedData().getPermissionData().checkPermission("ei.itemfolderbypass").asBoolean() ||
                    user.getCachedData().getPermissionData().checkPermission("ei.item.*").asBoolean()) {
                return "&aEI Folder-Bypass Detected";
            }

            try {
                // STEP 1: Remove "ei.item.*" unless immutable
                user.getNodes().stream()
                        .filter(node -> node.getKey().startsWith("ei.item.")) // Find all "ei.item.*" permissions
                        .filter(node -> {
                            String itemName = node.getKey().substring("ei.item.".length()); // Extract ITEMNAME
                            String immutablePermission = "ei.immutableitem." + itemName;

                            // Check if they have "ei.immutableitem.ITEMNAME"
                            return !user.getCachedData().getPermissionData().checkPermission(immutablePermission).asBoolean();
                        })
                        .forEach(node -> user.data().remove(node)); // Remove non-immutable permissions

                // STEP 2: Get inherited and direct "ei.itemfolder.*" permissions
                List<String> itemFolderPermissions = user.getNodes().stream()
                        .map(Node::getKey)
                        .filter(key -> key.startsWith("ei.itemfolder."))
                        .map(key -> key.substring("ei.itemfolder.".length()).replace(".", "/")) // Convert to directory paths
                        .toList();

                // STEP 3 & 4: Get valid items and apply "ei.item.*" permissions
                processItemFolderPermissions(user, itemFolderPermissions);

                // STEP 5: Save changes
                luckPerms.getUserManager().saveUser(user);
                return "&aSuccessfully updated EI-Folder permissions.";

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error processing permissions for " + p.getName() + ": " + e.getMessage());
                e.printStackTrace();
                return "&cSomething went wrong with LP EI-Folder: " + e.getMessage();
            }
            




        }

        if (identifier.startsWith("bossbar_")) {
            try {
                if( p == null ) throw new IllegalArgumentException();

                String[] args = identifier.substring("bossbar_".length()).split(",");
                if (args.length < 5) {
                    return "Invalid Format! Use: COLOR,TIME,START,END,TIMESTEP,TEXT,TEXT2";
                }


                // Parse BossBar color
                BarColor color = BarColor.valueOf(args[0].toUpperCase());

                // Parse time (in ticks)
                int durationTicks = Integer.parseInt(args[1]);

                // Parse start & end progress (0.0 - 1.0)
                double startProgress = Math.max(0, Math.min(1, Double.parseDouble(args[2])));
                double endProgress = Math.max(0, Math.min(1, Double.parseDouble(args[3])));

                // Parse timestep (in ticks)
                int timeStepTicks = Integer.parseInt(args[4]);

                // Concatenate text arguments
                List<String> textArgs = Arrays.asList(Arrays.copyOfRange(args, 5, args.length));
                String barText = String.join(" ", textArgs);

                // Create the BossBar
                BossBar bossBar = Bukkit.createBossBar(barText, color, BarStyle.SOLID);
                bossBar.setProgress(startProgress);
                bossBar.addPlayer(p);

                // If TIMESTEP is 0 or -1, keep the bossbar static
                if (timeStepTicks <= 0) {

                    Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), bossBar::removeAll, durationTicks);
                    return "BossBar displayed!";
                }

                // Calculate the amount to update each step
                double stepAmount = (endProgress - startProgress) / (durationTicks / (double) timeStepTicks);

                new BukkitRunnable() {
                    double currentProgress = startProgress;
                    int elapsedTicks = 0;

                    @Override
                    public void run() {
                        if (elapsedTicks >= durationTicks) {
                            bossBar.removeAll();
                            this.cancel();
                            return;
                        }

                        currentProgress = Math.max(0, Math.min(1, currentProgress + stepAmount));
                        bossBar.setProgress(currentProgress);
                        elapsedTicks += timeStepTicks;
                    }
                }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), 0, timeStepTicks);

                // Remove the bossbar after the duration expires
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), bossBar::removeAll, durationTicks);

                return "BossBar displayed!";
            } catch (Exception e) {
                return "failed";
            }
        }
        
        if (identifier.equalsIgnoreCase("fireworkboost")) {
            try {
                if( p != null) {
                    if( !p.isGliding() ) throw new IllegalArgumentException();
                    // Create a Firework ItemStack
                    ItemStack fireworkItem = new ItemStack(Material.FIREWORK_ROCKET);
                    FireworkMeta fireworkMeta = (FireworkMeta) fireworkItem.getItemMeta();
    
                    if (fireworkMeta != null) {
                        fireworkMeta.setPower(1); // Duration of 1
                        fireworkMeta.clearEffects(); // No star effects
                        fireworkItem.setItemMeta(fireworkMeta);
                    }
    
                    // Apply the firework boost
                    p.fireworkBoost(fireworkItem);       
                }

                return "1";
            } catch (IllegalArgumentException e) {
                return "0";
            }

        }


        if (identifier.equalsIgnoreCase("DN")) { // %Archistructure_DN%
            return processBook(p);
        }
        
        
        if (identifier.startsWith("sr72-fly-")) {
            try {
                // Extract parameters from the identifier
                String[] params = identifier.substring("sr72-fly-".length()).split(",");
                if (params.length != 3) {
                    return "Error: Invalid format. Use: %Archistructure_sr72-fly-speed,pitch,BOATUUID%";
                }

                // Parse the parameters
                double speed = Double.parseDouble(params[0]); // Speed
                double pitch = Double.parseDouble(params[1]); // Pitch
                UUID boatUUID = UUID.fromString(params[2]); // Boat UUID

                // Retrieve the boat entity
                Entity entity = Bukkit.getEntity(boatUUID);
                if (!(entity instanceof Boat boat)) {
                    return "Error: No boat found with UUID " + boatUUID;
                }

                // Get the boat's yaw and calculate the direction
                float yaw = boat.getLocation().getYaw();
                double yawRadians = Math.toRadians(yaw);
                double pitchRadians = Math.toRadians(pitch);

                // Compute normalized movement vector
                double x = -Math.sin(yawRadians) * Math.cos(pitchRadians);
                double y = -1 * Math.sin(pitchRadians);
                double z = Math.cos(yawRadians) * Math.cos(pitchRadians);

                Vector velocity = new Vector(x, 0, z).normalize().multiply(speed);
                Vector climb = new Vector( 0, y, 0).multiply(speed / 5 );
                velocity.add(climb);

                // Apply velocity to the boat
                boat.setVelocity(velocity);
                return String.format("Vector(%.3f, %.3f, %.3f)", velocity.getX(), velocity.getY(), velocity.getZ());

            } catch (NumberFormatException e) {
                return "Error: Invalid number format for speed/pitch.";
            } catch (IllegalArgumentException e) {
                return "Error: Invalid UUID format.";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        if (identifier.startsWith("variables-create-")) {
                    try {
                        String[] args = identifier.substring("variables-create-".length()).split(",");

                        if (args.length < 3) {
                            return "Invalid format. Expected at least 3 arguments.";
                        }

                        String arg1 = args[0]; // Name
                        String arg2 = args[1]; // Type
                        String arg3 = args[2]; // Scope
                        String arg4 = args[3]; // Icon


                        List<String> argList = new ArrayList<>(); // Default Value
                        if (args.length > 4) {
                            argList = Arrays.asList(Arrays.copyOfRange(args, 4, args.length)); // Remaining arguments as a list
                        }


                        String formattedArgs = String.join(" ", argList);

                        Variable v1 = new Variable(arg1, "plugins/SCore/variables/" + arg1 + ".yml");
                        v1.getIcon().setValue(Optional.ofNullable(Material.getMaterial(arg4)));
                        switch (arg2) {
                            case "string" -> v1.getType().setValue(Optional.of(VariableType.STRING));
                            case "list" -> v1.getType().setValue(Optional.of(VariableType.LIST));
                            case "number" -> v1.getType().setValue(Optional.of(VariableType.NUMBER));
                            default -> throw new IllegalArgumentException();
                        }

                        if(!formattedArgs.isEmpty()) v1.getDefaultValue().setValue(formattedArgs);

                        if (arg3.equals("global")) v1.getForFeature().setValue(Optional.of(VariableForEnum.GLOBAL));
                        else if (arg3.equals("player")) v1.getForFeature().setValue(Optional.of(VariableForEnum.PLAYER));
                        else throw new IllegalArgumentException();


                        v1.save();
                        VariablesManager.getInstance().addLoadedObject(v1);


                        return "done"; // Return "done" if successful
                    } catch (Exception e) {
                        return "failed: " + e.getClass();
        }
        }

        if (identifier.equalsIgnoreCase("FT-increment")) {
            incrementFTScore(p.getUniqueId());
            return "done"; // Return "done" if successful
        }

        if (identifier.equalsIgnoreCase("FT-leaderboard")) {
            try {
                sendFTLeaderboardViaTellraw(p);
                return "&aLeaderboard Displayed!"; // Success: return empty string
            } catch (Exception e) {
                e.printStackTrace();
                return "&cFailed! Contact an admin."; // Failure message
            }
        }


        if (identifier.startsWith("track_")) {
            String[] parts = identifier.substring("track_".length()).split(",");
            if (parts.length != 4) {
                return "Invalid format. Use: %Archistructure,uuid,targetuuid,speed%";
            }

            try {
                UUID callerUUID = UUID.fromString(parts[0]);
                UUID targetUUID = UUID.fromString(parts[1]);
                double speed = Double.parseDouble(parts[2]);
                UUID launcherUUID = UUID.fromString(parts[3]);


                Entity caller = Bukkit.getEntity(callerUUID);
                Entity target = Bukkit.getEntity(targetUUID);

                if (caller == null || target == null) {
                    return "§c§lMissile Impacted"; // No valid target
                }

                // Check if both entities are in the same world
                if (!caller.getWorld().equals(target.getWorld())) {
                    return "§c§lMissile Impacted."; // No valid target
                }

                // Airburst Mechanic: If within 3 blocks, explode immediately
                double distance = caller.getLocation().distance(target.getLocation());
                if (distance <= 5.0 && caller instanceof Firework && target instanceof Player) {
                    airburstExplode((Firework) caller, target, launcherUUID);
                    return "§c§lAirburst Detonation!";
                }

                // Calculate the interception velocity
                Vector interceptVelocity = calculateInterceptionVelocity(caller, target, speed);
                caller.setVelocity(interceptVelocity);

                // Get target's name (Player name or Entity type)
                String targetName = (target instanceof Player) ? target.getName() : target.getType().name();

                // Format return message
                return String.format("§6§l%s  §7§l| §d§l%.1f", targetName, distance);
            } catch (Exception e) {
                e.printStackTrace();
                return "§c§lMissile Impacted"; // Error case
            }
        }

        if (identifier.startsWith("checkProfession_")) {
            String uuidString = identifier.substring("checkProfession_".length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                Villager villager = (Villager) Bukkit.getEntity(uuid);

                if (villager != null && entity != null ) {
                    return villager.getProfession().toString();
                } else {
                    return "";
                }
            } catch (Exception e) {
                return "";
            }
        }


        if (identifier.startsWith("saveEntity_")) {
            String uuidString = identifier.substring("saveEntity_".length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    EntitySnapshot snapshot = entity.createSnapshot();
                    assert snapshot != null;
                    String nbtData = snapshot.getAsString();
                    String entityId = "entity_" + System.currentTimeMillis();

                    File entityFile = new File(entityStorageDir, entityId + ".nbt");
                    saveNBTToFile(entityFile, nbtData);

                    entity.remove(); // Remove the entity after saving
                    return entityId;
                } else {
                    return "Entity not found";
                }
            } catch (IllegalArgumentException e) {
                return "Invalid UUID";
            }
        }

        if (identifier.startsWith("loadEntity_")) {
            if (identifier.startsWith("loadEntity_location_")) {
                try {
                    // Parse parameters from the identifier
                    String[] parts = identifier.substring("loadEntity_location_".length()).split(",");
                    if (parts.length < 5) {
                        return "Invalid location or UUID format!";
                    }

                    // Extract location parameters
                    String worldName = parts[0];
                    double locX = Double.parseDouble(parts[1]);
                    double locY = Double.parseDouble(parts[2]);
                    double locZ = Double.parseDouble(parts[3]);
                    String entityId = parts[4];


                    // Retrieve the world
                    World world = Bukkit.getWorld(worldName);
                    if (world == null) {
                        return "&cWorld '" + worldName + "' not found!";
                    }

                    // Load the entity data
                    File entityFile = new File(entityStorageDir, entityId + ".nbt");
                    if (!entityFile.exists()) {
                        return "&cEntity with ID '" + entityId + "' not found!";
                    }

                    String nbtData = loadNBTFromFile(entityFile);
                    if (nbtData == null) {
                        return "&cFailed to load entity data!";
                    }

                    // Create a location and summon the entity
                    Location spawnLocation = new Location(world, locX, locY, locZ);
                    EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(nbtData);
                    snapshot.createEntity(spawnLocation);

                    // Optionally delete the file after summoning the entity
                    entityFile.delete();

                    return "&aEntity reintroduced at the specified location!";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "&cAn error occurred while loading the entity!";
                }
            } else {
                String entityId = identifier.substring("loadEntity_".length());
                File entityFile = new File(entityStorageDir, entityId + ".nbt");

                if (entityFile.exists()) {
                    String nbtData = loadNBTFromFile(entityFile);
                    if (nbtData != null) {
                        Location spawnLocation = p.getLocation();
                        EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(nbtData);
                        snapshot.createEntity(spawnLocation);
                        entityFile.delete(); // Remove the saved file after reintroducing
                        return "&cEntity reintroduced into the world!";
                    }
                }
                return "Entity ID not found";
            }
        }


        switch (identifier) {
            case "cycle_playeruuid_head" -> {
                ItemStack mainHandItem = p.getInventory().getItemInMainHand();

                if (mainHandItem.getType() != Material.PLAYER_HEAD) {
                    return "Not holding a player head!";
                }

                SkullMeta skullMeta = (SkullMeta) mainHandItem.getItemMeta();
                UUID currentUUID = null;
                String currentName = null;

                // Get all online player names and sort them alphabetically
                List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                onlinePlayers.sort(Comparator.comparing(Player::getName));

                if (onlinePlayers.isEmpty()) {
                    return "No online players!";
                }

                // Check if the skull has valid player data
                if (skullMeta != null && skullMeta.hasOwner()) {
                    OfflinePlayer currentPlayer = skullMeta.getOwningPlayer();
                    if (currentPlayer != null) {
                        currentPlayer.getUniqueId();
                        currentUUID = currentPlayer.getUniqueId();
                        currentName = currentPlayer.getName();
                    }
                }

                UUID nextUUID;

                nextUUID = onlinePlayers.getFirst().getUniqueId();
                if (currentUUID != null && currentName != null) {
                    // Find the next player in the sorted list

                    for (int i = 0; i < onlinePlayers.size(); i++) {
                        if (onlinePlayers.get(i).getName().equalsIgnoreCase(currentName)) {
                            nextUUID = onlinePlayers.get((i + 1) % onlinePlayers.size()).getUniqueId();
                            break;
                        }
                    }
                } 

                // Update the Player Head's metadata to the new player's UUID
                assert skullMeta != null;
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(nextUUID));
                mainHandItem.setItemMeta(skullMeta);

                return nextUUID.toString(); // Return the new UUID
            }
            case "velocity" -> {
                return p.getVelocity().toString();
            }
            case "copyMainHand" -> {
                dropMainHandCopy(p);
                return "done";
            }
            case "critical" -> {
                boolean falling = p.getFallDistance() > 0 && p.getVelocity().getY() < 0;
                boolean onGround = p.isOnGround(); // DEPRECATED! Remove if necessary

                boolean isClimbing = p.isClimbing();
                boolean inWater = p.isSwimming() || p.isInWater();
                boolean blind = p.hasPotionEffect(PotionEffectType.BLINDNESS);
                boolean slowFalling = p.hasPotionEffect(PotionEffectType.SLOW_FALLING);
                boolean mounted = p.getVehicle() != null;
                boolean horizontalSpeedValid = !p.isSprinting();// && p.getWalkSpeed() >= Math.sqrt(p.getVelocity().getX() * p.getVelocity().getX() + p.getVelocity().getZ() + p.getVelocity().getZ());

                // Unknown how to implement without passing in event. boolean validDamage = p.damage?
                return falling &&
                        //!onGround && 
                        !isClimbing &&
                        !inWater &&
                        !blind &&
                        !slowFalling &&
                        !mounted &&
                        horizontalSpeedValid ?
                        "true" : "" + falling + !onGround + !isClimbing + !inWater + !blind + !slowFalling + !mounted + horizontalSpeedValid;// && p.getWalkSpeed() >= Math.sqrt(p.getVelocity().getX() * p.getVelocity().getX() + p.getVelocity().getZ() + p.getVelocity().getZ());

                // Unknown how to implement without passing in event. boolean validDamage = p.damage?
            }
            case "rayTraceBlock" -> {
                return Objects.requireNonNull(Objects.requireNonNull(p.rayTraceBlocks(30)).getHitBlock()).toString();
            }
        }


        /*
        p.breakBlock(block);
        p.canSee(entity);
        p.getAttribute();
        p.getVelocity();
        p.chat();
        p.getAllowFlight();
        p.getClientViewDistance();
        p.setCompassTarget(); p.getCompassTarget(); TODO make a nice compass playertracker;
        p.getExp();
        p.getLevel();
        p.sendBlockChange() fake a block thing;
        p.openSign();
        p.sendEquipmentChange()
        p.sendExperienceChange();
        p.sendHealthUpdate();
        p.sendBlockChanges();
        p.sendBlockDamage();
        p.sendHurtAnimation();
        p.sendSignChange();
        p.getArrowsInBody();
        p.getCooldown(material);
        p.getNearbyEntities();
        p.getPassengers();
        p.getVehicle();
        p.setArrowsInBody();
        p.isBlocking();
        p.isClimbing();
        p.isDead();
        p.isInWater();
        p.isSwimming();
        p.openEnchanting();
        p.openMerchant();
        p.openInventory();
        p.playHurtAnimation();
        p.setVelocity();
        p.ge
*/

        return null;

    }

    private void saveNBTToFile(File file, String nbtData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(nbtData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadNBTFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder nbtData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                nbtData.append(line);
            }
            return nbtData.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void dropMainHandCopy(Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType() == Material.AIR) {
            player.sendMessage("You're not holding anything!");
            return;
        }

        ItemStack copiedItem = itemInHand.clone();
        Location dropLocation = player.getLocation();
        Item droppedItem = player.getWorld().dropItemNaturally(dropLocation, copiedItem);
        droppedItem.setPickupDelay(20);
    }

    private Vector calculateInterceptionVelocity(Entity caller, Entity target, double speed) {
        Location callerLoc = caller.getLocation();
        Location targetLoc = target.getLocation().add(0, target.getHeight() / 3, 0);

        if( target instanceof Player )  targetLoc = target.getLocation().add(0, ((Player) target).getEyeHeight(), 0);

        Vector targetVelocity = target.getVelocity();

        // Distance between entities
        double distance = callerLoc.distance(targetLoc);

        // Maximum prediction range
        double maxPredictDistance = speed * 20;

        // Calculate estimated time to reach the target
        double relativeSpeedSquared = speed * speed - targetVelocity.lengthSquared();

        if (relativeSpeedSquared > 0) {
            // Calculate time to intercept using a quadratic equation
            Vector relativePosition = targetLoc.toVector().subtract(callerLoc.toVector());

            double a = targetVelocity.dot(targetVelocity) - (speed * speed);
            double b = 2 * targetVelocity.dot(relativePosition);
            double c = relativePosition.dot(relativePosition);

            double discriminant = (b * b) - (4 * a * c);

            if (discriminant >= 0) {
                // Solve for T (time to intercept)
                double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

                double timeToIntercept = Math.max(t1, t2);
                if (timeToIntercept < 0) timeToIntercept = Math.min(t1, t2);

                // Ensure valid interception time
                if (timeToIntercept > 0) {
                    Vector interceptPoint = targetLoc.toVector().add(targetVelocity.clone().multiply(timeToIntercept));

                    // Check for rare "missile stuck in front" case
                    if (isMissileInFrontOfTarget(callerLoc, targetLoc, targetVelocity)) {
                        return targetVelocity.clone().multiply(-1).normalize().multiply(speed); // Reverse movement
                    }

                    // If within max prediction range, move to exact intercept
                    if (distance <= maxPredictDistance) {
                        return interceptPoint.subtract(callerLoc.toVector()).normalize().multiply(speed);
                    }
                }
            }
        }

        // If unable to calculate exact intercept, fallback to best effort prediction
        Vector directionToTarget = targetLoc.toVector().subtract(callerLoc.toVector()).normalize();
        return directionToTarget.multiply(speed);
    }

    /**
     * Determines if the missile is stuck in front of the target's movement direction (within 2-3 degrees).
     */
    private boolean isMissileInFrontOfTarget(Location missileLoc, Location targetLoc, Vector targetVelocity) {
        if (targetVelocity.lengthSquared() == 0) return false; // Target is stationary

        Vector toMissile = missileLoc.toVector().subtract(targetLoc.toVector()).normalize();
        Vector targetDirection = targetVelocity.clone().normalize();

        double angle = Math.toDegrees(Math.acos(toMissile.dot(targetDirection)));

        return angle < 3.0; // Within 3 degrees = missile stuck in front
    }

    private void airburstExplode(Firework firework, Entity target, UUID launcherUUID) {
        if (firework == null || firework.isDead() || !firework.isValid()) return;

        firework.teleport(target.getLocation());
        World world = firework.getWorld();
        Location explosionLocation = firework.getLocation();

        // Retrieve explosion power based on firework stars
        float explosionPower = getFireworkExplosionPower(firework);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!firework.isValid() || firework.isDead()) return;

                // Create the visual firework explosion
                spawnCustomFireworkExplosion(world, explosionLocation);

                // Apply explosion damage
                world.createExplosion(explosionLocation, explosionPower, false, false, firework);

                // Trigger a player hit event (simulate firework explosion hitting a player)
                triggerPlayerHitEvent(launcherUUID, explosionLocation, explosionPower);

                // Remove the original firework
                firework.remove();
            }
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), 1L);
    }

    private void triggerPlayerHitEvent(UUID launcherUUID, Location explosionLocation, float explosionPower) {
        Player launcher = Bukkit.getPlayer(launcherUUID);
        if (launcher == null) return; // Launcher is offline or invalid

        double explosionRadius = 5.0; // Firework explosion hit radius

        for (Entity entity : Objects.requireNonNull(explosionLocation.getWorld()).getNearbyEntities(explosionLocation, explosionRadius, explosionRadius, explosionRadius)) {
            if (entity instanceof Player hitPlayer) {

                // Apply direct damage as if caused by the launcher
                hitPlayer.damage(explosionPower, launcher);

                // Optional: Add custom effects
                hitPlayer.setVelocity(hitPlayer.getVelocity().add(new Vector(0, 0.5, 0))); // Small knockback
            }
        }
    }


    private float getFireworkExplosionPower(Firework firework) {
        FireworkMeta meta = firework.getFireworkMeta();

        // Count the number of firework stars
        int fireworkStars = 0;
        if (meta.hasEffects()) {
            for (FireworkEffect ignored : meta.getEffects()) {
                fireworkStars++;
            }
        }

        // Vanilla firework damage scales with number of stars (0.5 per star)
        return 0.5F * fireworkStars; // Closer to vanilla damage
    }

    private void spawnCustomFireworkExplosion(World world, Location location) {
        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        // Large Ball Effect: Yellow, Orange, Gray with sparkles
        FireworkEffect largeBallEffect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.YELLOW, Color.ORANGE, Color.GRAY)
                .withFlicker()
                .build();

        // Small Ball Effect: Red
        FireworkEffect smallBallEffect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.RED)
                .build();

        // Add both effects
        meta.addEffect(largeBallEffect);
        meta.addEffect(smallBallEffect);
        meta.setPower(1);

        firework.setFireworkMeta(meta);

        // Detonate instantly
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), firework::detonate, 1L);
    }


    private void loadFTLeaderboard() {
        if (!ftLeaderboardFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(ftLeaderboardFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    UUID uuid = UUID.fromString(parts[0]);
                    int score = Integer.parseInt(parts[1]);
                    ftLeaderboard.put(uuid, score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendFTLeaderboardViaTellraw(Player player) {
        try {
            List<Map.Entry<UUID, Integer>> sortedList = new ArrayList<>(ftLeaderboard.entrySet());

            // Sorting: First by score (descending), then by insertion order (first to score stays ahead)
            sortedList.sort((a, b) -> {
                // Higher scores first
                return b.getValue().compareTo(a.getValue());// Maintain insertion order in case of tie
            });

            // Construct the tellraw JSON command
            StringBuilder tellraw = new StringBuilder("[");
            tellraw.append("{\"text\":\"§6-----------------\\n\"},");
            tellraw.append("{\"text\":\"§eFT Leaderboard\\n\"}");

            int rank = 1;
            for (Map.Entry<UUID, Integer> entry : sortedList) {
                if (rank > 20) break;
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entry.getKey());
                String playerName = offlinePlayer.getName() != null ? offlinePlayer.getName() : "Unknown";
                tellraw.append(String.format(",{\"text\":\"§7%d. §a%s - §b%d\\n\"}", rank, playerName, entry.getValue()));
                rank++;
            }

            tellraw.append(",{\"text\":\"§6-----------------\"}]");

            // Execute tellraw command
            String tellrawCommand = String.format("tellraw %s %s", player.getName(), tellraw);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), tellrawCommand);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void activateXRay(Player player, int radius, int duration) {
        World world = player.getWorld();
        Location playerLoc = player.getLocation();
        UUID playerUUID = player.getUniqueId();

        // Store block states instead of just locations
        trackedBlocks.put(playerUUID, new HashSet<>());

        // Scan surrounding blocks
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = world.getBlockAt(
                            playerLoc.getBlockX() + x,
                            playerLoc.getBlockY() + y,
                            playerLoc.getBlockZ() + z
                    );

                    if (shouldTurnToGlass(block)) {
                        // Save the original block data (including state)
                        BlockData originalData = block.getBlockData();
                        trackedBlocks.get(playerUUID).add(block.getLocation());

                        // Send block change to player while preserving state
                        player.sendBlockChange(block.getLocation(), Material.GLASS.createBlockData());

                        // Store the block data in the tracked blocks map
                        trackedBlockData.put(block.getLocation(), originalData);
                    }
                }
            }
        }

        // Restore blocks after duration
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!trackedBlocks.containsKey(playerUUID)) return;

                for (Location loc : trackedBlocks.get(playerUUID)) {
                    BlockData originalData = trackedBlockData.get(loc);

                    // Restore the block data with preserved state
                    player.sendBlockChange(loc, originalData);
                }

                // Cleanup
                trackedBlocks.remove(playerUUID);
                trackedBlockData.clear();
            }
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), duration);
    }


    private boolean shouldTurnToGlass(Block block) {
        return block.getType() != Material.AIR
                && block.getType() != Material.WATER
                && block.getType() != Material.LAVA
                && !oresAndImportantBlocks.contains(block.getType());
    }


    private static List<Vector> matrixToVectors(boolean[][] matrix, int density, double size, double rotation) {
        List<Vector> result = new ArrayList<>();
        int height = matrix.length;
        int width = matrix[0].length;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!matrix[y][x]) continue;
                if (x % density != 0 || y % density != 0) continue;

                double px = (x - width / 2.0) * size;
                double py = -(y - height / 2.0) * size;
                double pz = 0;

                double rx = Math.cos(rotation) * px - Math.sin(rotation) * pz;
                double rz = Math.sin(rotation) * px + Math.cos(rotation) * pz;

                result.add(new Vector(rx, py, rz));
            }
        }
        return result;
    }

    private static boolean[][] renderTextToMatrix(String text) {
        Font font = new Font("Dialog", Font.BOLD, 16);
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

    private static void displayToNearby(Location center, List<Location> locs, Particle particle, String viewDistanceMode) {
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getWorld().equals(center.getWorld())) continue;
                    double dist = player.getLocation().distance(center);
                    if (!viewDistanceMode.equalsIgnoreCase("force") &&
                            !viewDistanceMode.equalsIgnoreCase("normal") &&
                            dist > Integer.parseInt(viewDistanceMode)) continue;

                    for (Location loc : locs) {
                        player.spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    }
                }
            }
        }.runTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")));
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


}