package org.example;


import org.bukkit.block.data.*;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.RayTraceResult;
import org.json.JSONArray;
import org.json.JSONObject;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
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
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Comparator;
import java.util.List;


import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {
    private final ConcurrentHashMap<UUID, String> lastStatusByProjectile = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, BukkitTask> activeTrackers = new ConcurrentHashMap<>();
Plugin plugin;


    private static final java.util.concurrent.ConcurrentMap<String, UUID> ACTIVE_MISSILES =
            new java.util.concurrent.ConcurrentHashMap<>();

    private static String missileKey(UUID launcher, UUID target) {
        return launcher.toString() + ":" + target.toString();
    }

    private static long lastSendTime = 0L; // in millis

    protected final Set<Material> enumSet = EnumSet.of(Material.ACTIVATOR_RAIL, Material.AIR, Material.BAMBOO, Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BEETROOT_SEEDS, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.BIRCH_BUTTON, Material.SPRUCE_BUTTON, Material.JUNGLE_BUTTON, Material.DARK_OAK_BUTTON, Material.ACACIA_BUTTON, Material.MANGROVE_BUTTON, Material.CHERRY_BUTTON, Material.CRIMSON_BUTTON, Material.WARPED_BUTTON, Material.LIGHT_BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER, Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_GRAY_BANNER, Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER, Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER, Material.CARROTS, Material.CHORUS_FLOWER, Material.CHORUS_PLANT, Material.COBWEB, Material.COCOA, Material.BRAIN_CORAL, Material.BUBBLE_CORAL, Material.FIRE_CORAL, Material.HORN_CORAL, Material.TUBE_CORAL, Material.BRAIN_CORAL_FAN, Material.BUBBLE_CORAL_FAN, Material.FIRE_CORAL_FAN, Material.HORN_CORAL_FAN, Material.TUBE_CORAL_FAN, Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.END_GATEWAY, Material.END_PORTAL, Material.FIRE, Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY, Material.WITHER_ROSE, Material.FLOWER_POT, Material.FROGSPAWN, Material.WARPED_FUNGUS, Material.CRIMSON_FUNGUS, Material.GLOW_BERRIES, Material.GLOW_LICHEN, Material.SHORT_GRASS,  Material.HANGING_ROOTS, Material.PLAYER_HEAD, Material.SKELETON_SKULL, Material.CREEPER_HEAD, Material.WITHER_SKELETON_SKULL, Material.ZOMBIE_HEAD, Material.DRAGON_HEAD, Material.PIGLIN_HEAD, Material.KELP, Material.LADDER, Material.LAVA, Material.LEVER, Material.LIGHT, Material.LILY_PAD, Material.MANGROVE_PROPAGULE, Material.MELON_SEEDS, Material.MOSS_CARPET, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.NETHER_PORTAL, Material.NETHER_SPROUTS, Material.NETHER_WART, Material.PINK_PETALS, Material.PITCHER_PLANT, Material.PITCHER_POD, Material.POTATOES, Material.POWDER_SNOW, Material.POWERED_RAIL, Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.MANGROVE_PRESSURE_PLATE, Material.CHERRY_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.PUMPKIN_SEEDS, Material.RAIL, Material.COMPARATOR, Material.REDSTONE_WIRE, Material.REPEATER, Material.REDSTONE_TORCH, Material.REDSTONE_WALL_TORCH, Material.OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.JUNGLE_SAPLING, Material.DARK_OAK_SAPLING, Material.ACACIA_SAPLING, Material.CHERRY_SAPLING, Material.SCULK_VEIN, Material.SEA_PICKLE, Material.SEAGRASS, Material.SHORT_GRASS, Material.DEAD_BUSH,  Material.OAK_SIGN, Material.BIRCH_SIGN, Material.SPRUCE_SIGN, Material.JUNGLE_SIGN, Material.DARK_OAK_SIGN, Material.ACACIA_SIGN, Material.CHERRY_SIGN, Material.MANGROVE_SIGN, Material.CRIMSON_SIGN, Material.WARPED_SIGN, Material.SMALL_DRIPLEAF, Material.SNOW, Material.SPORE_BLOSSOM, Material.STRING, Material.STRUCTURE_VOID, Material.SUGAR_CANE, Material.SWEET_BERRY_BUSH, Material.TORCH, Material.TORCHFLOWER_SEEDS, Material.TRIPWIRE_HOOK, Material.TURTLE_EGG, Material.TWISTING_VINES, Material.VINE, Material.WATER, Material.WEEPING_VINES, Material.WHEAT_SEEDS, Material.WHITE_TULIP );
    private enum SpeedMode { ADD, MUL }

    private boolean WorldEdit_Installed = false;
    private boolean WorldGuard_Installed = false;
    private boolean LuckPerms_Installed = false;
    private boolean ProtocolLib_Installed = false;




    private static final boolean viewChestDebugLogging = false;


    private final Map<UUID, BukkitTask> invisTimers = new HashMap<>();

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
    private final Map<Location, BukkitTask> jesusTimers = new HashMap<>();


    public ExampleExpansion() {
        trialVersion = false;
        trialNumber = 1000;
plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");


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
        World world = start.getWorld();
        if (world == null) return;

        List<Player> viewers = new ArrayList<>(world.getPlayers());

        for (int i = 0; i <= density; i++) {
            double t = (double) i / density;
            double x = start.getX() + (end.getX() - start.getX()) * t;
            double y = start.getY() + (end.getY() - start.getY()) * t;
            double z = start.getZ() + (end.getZ() - start.getZ()) * t;
            Location loc = new Location(world, x, y, z);

            for (Player viewer : viewers) {
                if (!force && viewer.getLocation().distanceSquared(loc) > 64 * 64) continue;
                viewer.spawnParticle(particle, loc, 0, 0, 0, 0, 0, null, force);
            }
        }
    }




    protected static void spawnCustomFireworkExplosion2(World world, Location location) {
        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        // Large Ball Effect: Yellow, Orange, Gray with sparkles
        FireworkEffect largeBallEffect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.fromRGB(211, 211, 211), Color.AQUA, Color.fromRGB(57,68,188))
                .withFlicker()
                .build();

        // Small Ball Effect: Red
        FireworkEffect smallBallEffect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor( Color.WHITE)
                .build();

        // Add both effects
        meta.addEffect(largeBallEffect);
        meta.addEffect(smallBallEffect);
        meta.setPower(1);

        firework.setFireworkMeta(meta);

        // Detonate instantly
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), firework::detonate, 1L);
    }

    // AOE SONIC_BOOM damage (copy of your area hit behavior with V4 rules)
// - center: explosion/impact location
// - radius: e.g. 5.0 (your proximity radius)
// - caller: projectile or thing that caused it (used as damager for non-players)
// - launcherUUID: player who fired it (used as damager for players)
    // AOE SONIC_BOOM damage (V4 rules) with "always damage target if provided" semantics.
// - caller: projectile or source entity (used as damager for NON-players)
// - launcherUUID: player who fired it (used as damager for PLAYERS)
// - center/radius: AOE center and radius
// - target: if not null and living, ALWAYS damaged (any world / any distance), then AOE applies to others
    private static void triggerPlayerHitEventV4_AOE(
            @org.jetbrains.annotations.Nullable UUID launcherUUID,
            @org.jetbrains.annotations.NotNull Location center,
            double radius,
            @org.jetbrains.annotations.Nullable Entity target) {

        final Player launcher = (launcherUUID != null) ? Bukkit.getPlayer(launcherUUID) : null;

        // Helper to resolve a launcher name for command args
        final String launcherName = (launcher != null)
                ? launcher.getName()
                : (launcherUUID != null
                ? (Bukkit.getOfflinePlayer(launcherUUID).getName() != null
                ? Bukkit.getOfflinePlayer(launcherUUID).getName()
                : launcherUUID.toString())
                : "unknown");

        // Track already-damaged entities to avoid double hits (e.g., target also in AOE)
        final java.util.HashSet<UUID> damaged = new java.util.HashSet<>();

        // 1) ALWAYS damage the explicit target first (if provided and is a LivingEntity), regardless of world/position
        if (target instanceof LivingEntity tgt && target.isValid() && !target.isDead()) {
            final org.bukkit.damage.DamageSource.Builder dsb =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.SONIC_BOOM);
            final org.bukkit.damage.DamageSource.Builder dsb2 =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.STARVE);

            if (tgt instanceof Player) {
                // Players: damager = launcher (player) if available
                final Entity damager = launcher;
                if (damager != null) dsb.withDirectEntity(damager).withCausingEntity(damager);

                final double maxHp = tgt.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double amount = 0.60 * maxHp + 10.0;
                tgt.damage(amount, dsb.build());

                // Run console trigger for the player target
                final String victimName = ((Player) tgt).getName();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "ei run-custom-trigger trigger:Stinger81Hit player:" + launcherName + " " + victimName);
            } else {
                // Non-players: damager = launcher (still acceptable per your V4 semantics)
                final Entity damager = launcher;
                if (damager != null) dsb2.withDirectEntity(damager).withCausingEntity(damager);

                final double maxHp = tgt.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double amount = 0.5 * maxHp + 200.0;
                tgt.damage(amount, dsb2.build());
            }

            damaged.add(tgt.getUniqueId());
        }

        // 2) AOE for everyone else near the center (same-world), excluding already-damaged target
        final World world = center.getWorld();
        if (world == null) return;

        final double r2 = radius * radius;

        for (Entity e : world.getNearbyEntities(center, radius, radius, radius)) {
            if (!(e instanceof LivingEntity le)) continue;
            if (!le.isValid() || le.isDead()) continue;
            if (damaged.contains(e.getUniqueId())) continue; // skip target if it was already hit
            if (e.getLocation().distanceSquared(center) > r2) continue;

            final org.bukkit.damage.DamageSource.Builder dsb =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.SONIC_BOOM);


            if (le instanceof Player) {
                // Players: damager = launcher (player) if available
                final Entity damager = launcher;
                if (damager != null) dsb.withDirectEntity(damager).withCausingEntity(damager);

                final double maxHp = le.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double amount = 0.60 * maxHp + 10.0;
                le.damage(amount, dsb.build());

                // Run console trigger for each player in radius

                final String victimName = ((Player) le).getName();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "ei run-custom-trigger trigger:Stinger81Hit player:" + launcherName + " " + victimName);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "minecraft:w ei run-custom-trigger trigger:Stinger81Hit player:" + launcherName + " " + victimName);

            } else {
                // Non-players: damager = launcher (kept consistent with your V4 variant)
                final Entity damager = launcher;
                if (damager != null) dsb.withDirectEntity(damager).withCausingEntity(damager);

                final double maxHp = le.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double amount = 0.33 * maxHp + 300;
                le.damage(amount, dsb.build());
            }
        }
    }

    private static Vector safeNorm(Vector v) {
        double len = v.length();
        if (len < 1e-9) return new Vector(0, 0, 0);
        return v.multiply(1.0 / len);
    }
    private void applyArmorDurabilityDamage(@org.jetbrains.annotations.NotNull Player p,
                                            int amount,
                                            boolean DestroyArmor) {
        if (amount <= 0) return; // nothing to do

        ItemStack[] armor = p.getInventory().getArmorContents();
        boolean changed = false;

        for (int i = 0; i < armor.length; i++) {
            ItemStack piece = armor[i];
            if (piece == null || piece.getType() == Material.AIR) continue;

            int maxDurability = piece.getType().getMaxDurability();
            if (maxDurability <= 0) continue; // non-damageable item, just in case

            // Bukkit durability = "damage used": 0 = brand new, maxDurability = about to break
            int currentDamage = piece.getDurability();
            int newDamage = currentDamage + amount; // add more wear

            if (newDamage >= maxDurability) {
                if (DestroyArmor) {
                    // Break the armor piece completely
                    armor[i] = null;
                    changed = true;
                    continue;
                } else {
                    // Leave it hanging on with 1 durability
                    newDamage = maxDurability - 1;
                }
            }

            if (newDamage < 0) {
                // If someone ever passes a negative amount by mistake, clamp
                newDamage = 0;
            }

            piece.setDurability((short) newDamage);
            armor[i] = piece;
            changed = true;
        }

        if (changed) {
            p.getInventory().setArmorContents(armor);
            p.updateInventory();
        }
    }




    private void triggerHybridHitEvent_AOEEntities_KeepPlayerBaseLogic(
            @org.jetbrains.annotations.Nullable UUID launcherUUID,
            @org.jetbrains.annotations.NotNull Location center,
            double radius,
            @org.jetbrains.annotations.Nullable Entity target,
            int amount,
            boolean DestroyArmor) {

        final Player launcher = (launcherUUID != null) ? Bukkit.getPlayer(launcherUUID) : null;

        // Resolve launcher name for commands (same style you use elsewhere)
        final String launcherName = (launcher != null)
                ? launcher.getName()
                : (launcherUUID != null
                ? (Bukkit.getOfflinePlayer(launcherUUID).getName() != null
                ? Bukkit.getOfflinePlayer(launcherUUID).getName()
                : launcherUUID.toString())
                : "unknown");

        // Track single-hit guarantees
        final java.util.HashSet<UUID> damaged = new java.util.HashSet<>();

        // 1) If target is provided and LIVING: always hit it once (any world/position)
        if (target instanceof LivingEntity tgt && target.isValid() && !target.isDead()) {
            if (tgt instanceof Player p) {
                // ---- KEEP YOUR BASE PLAYER LOGIC (unchanged except armor handling) ----
                final String victimName = p.getName();

                // stingerhit with launcher name
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "ee run-custom-trigger trigger:stingerhit " + victimName + " " + launcherName);

                if (!hasTwoArmorEquipped(p)) {
                    // Instead of breaking all armor, apply durability damage to *all* equipped armor
                    applyArmorDurabilityDamage(p, amount, DestroyArmor);

                    // stingerkillbypasstotems
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "ee run-custom-trigger trigger:stingerkillbypasstotems " + victimName + " " + launcherName);

                } else {
                    // stingerhit2
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "ee run-custom-trigger trigger:stingerhit2 " + victimName + " " + launcherName);

                    // Again, apply durability damage to all equipped armor (including Elytra)
                    applyArmorDurabilityDamage(p, amount, DestroyArmor);
                }

                // Tiny knock-up (matches your base "flavor")
                p.setVelocity(p.getVelocity().add(new Vector(0, 0.5, 0)));

            } else {
                // ---- NON-PLAYER TARGET (V4 "target" rule you posted) ----
                final org.bukkit.damage.DamageSource.Builder dsb =
                        org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.STARVE);
                if (launcher != null) dsb.withDirectEntity(launcher).withCausingEntity(launcher);

                final double maxHp = tgt.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double amountDamage = 0.5 * maxHp + 30; // your target-nonplayer formula
                tgt.damage(amountDamage, dsb.build());
            }

            damaged.add(tgt.getUniqueId());
        }

        // 2) AOE damage for everyone else (NON-PLAYERS ONLY) in center world within radius
        final World world = center.getWorld();
        if (world == null) return;

        final double r2 = radius * radius;

        for (Entity e : world.getNearbyEntities(center, radius, radius, radius)) {
            if (!(e instanceof LivingEntity le)) continue;
            if (!le.isValid() || le.isDead()) continue;
            if (damaged.contains(e.getUniqueId())) continue; // avoid re-hitting the explicit target
            if (le instanceof Player) continue;              // do NOT touch players in the AOE
            if (e.getLocation().distanceSquared(center) > r2) continue;

            // ---- NON-PLAYER AOE RULE (your V4 AOE usual damage) ----
            final org.bukkit.damage.DamageSource.Builder dsb =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.SONIC_BOOM);
            if (launcher != null) dsb.withDirectEntity(launcher).withCausingEntity(launcher);

            final double maxHp = le.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            final double amountDamage = 0.33 * maxHp + 20; // your "usual" AOE non-player rule
            le.damage(amountDamage, dsb.build());
        }
    }





    public static void ParticleCenterToCenter(UUID launcherUUID, UUID targetUUID) {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin == null) return;

        // Quick upfront validation for initial player/target
        Entity eA0 = Bukkit.getEntity(launcherUUID);
        Entity eB0 = Bukkit.getEntity(targetUUID);
        if (eA0 == null || eB0 == null || !eA0.isValid() || !eB0.isValid()) return;

        final int DURATION_TICKS = 80;     // 4 seconds (only for non-missile mode)
        final int MAX_PARTICLES  = 25;     // cap per line
        final String key         = missileKey(launcherUUID, targetUUID);

        new BukkitRunnable() {
            int tick = 0;
            boolean everHadMissile = false;

            @Override
            public void run() {
                // Look for an active missile for this launcher/target pair
                UUID missileUUID = ACTIVE_MISSILES.get(key);
                Entity missile = null;
                boolean missileActive = false;
                if (missileUUID != null) {
                    missile = Bukkit.getEntity(missileUUID);
                    if (missile != null && missile.isValid() && !missile.isDead()) {
                        missileActive = true;
                        everHadMissile = true;
                    } else {
                        // Clean up stale mapping
                        ACTIVE_MISSILES.remove(key, missileUUID);
                    }
                }

                // Lifetime control:
                // - Normal mode: honor DURATION_TICKS.
                // - Missile mode: ignore DURATION_TICKS, run while missile exists.
                // - Once we had a missile and it’s gone, stop.
                if (!missileActive) {
                    if (everHadMissile) {
                        cancel();
                        return;
                    }
                    if (tick++ >= DURATION_TICKS) {
                        cancel();
                        return;
                    }
                } else {
                    // missileActive: no tick++ or time limit
                }

                // Live fetch each tick
                Entity a; // start of line
                Entity b; // end of line (always the target)
                b = Bukkit.getEntity(targetUUID);

                if (missileActive) {
                    a = missile;
                } else {
                    a = Bukkit.getEntity(launcherUUID);
                }

                if (a == null || b == null || !a.isValid() || !b.isValid()) { cancel(); return; }

                // If either is a player and offline, stop
                if (a instanceof Player pa && !pa.isOnline()) { cancel(); return; }
                if (b instanceof Player pb && !pb.isOnline()) { cancel(); return; }

                if (!a.getWorld().equals(b.getWorld())) { cancel(); return; }
                World w = a.getWorld();

                // Centers (mid-height)
                double ha = 0.5, hb = 0.5;
                try { ha = a.getHeight() * 0.5; } catch (Throwable ignored) {}
                try { hb = b.getHeight() * 0.5; } catch (Throwable ignored) {}
                Location LA = a.getLocation().add(0, ha, 0);
                Location LB = b.getLocation().add(0, hb, 0);

                Vector AB = LB.toVector().subtract(LA.toVector());
                double len = AB.length();
                if (len < 1e-6) return;

                // Choose particle count for this frame (cap at MAX_PARTICLES, min 2)
                int n = Math.max(2, Math.min(MAX_PARTICLES, (int) Math.ceil(len * 3)));
                Vector step = AB.multiply(1.0 / (n - 1));

                // === Color and size ===
                org.bukkit.Color bukkitColor;
                float scale = 0.6f; // same as before

                if (missileActive) {
                    // Missile present → aqua, no gradient
                    bukkitColor = org.bukkit.Color.fromRGB(0, 255, 255); // aqua
                } else {
                    // Original time-based gradient: Green → Yellow → Orange → Red
                    if (tick >= DURATION_TICKS - 6) {
                        bukkitColor = org.bukkit.Color.fromRGB(160, 160, 160); // final gray line
                    } else {
                        double t = (double) tick / (double) (DURATION_TICKS - 6);
                        java.awt.Color c = gyorOverTime(t); // green->yellow->orange->red
                        bukkitColor = org.bukkit.Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue());
                    }
                }

                Particle.DustOptions dust = new Particle.DustOptions(bukkitColor, scale);

                // Render line (force = true)
                Location cur = LA.clone();
                for (int i = 0; i < n; i++) {
                    w.spawnParticle(Particle.DUST, cur, 1, 0, 0, 0, 0.0, dust, true);
                    cur.add(step);
                }
            }

            // Time-based gradient: Green → Yellow → Orange → Red
            private java.awt.Color gyorOverTime(double t) {
                // clamp
                if (t < 0) t = 0;
                if (t > 1) t = 1;

                // stops: 0.0   (0,255,0)   green
                //        0.33  (255,255,0) yellow
                //        0.66  (255,165,0) orange
                //        1.0   (255,0,0)   red
                if (t <= 1.0 / 3.0) {
                    return lerp(new java.awt.Color(0, 255, 0), new java.awt.Color(255, 255, 0), t / (1.0 / 3.0));
                } else if (t <= 2.0 / 3.0) {
                    double u = (t - 1.0 / 3.0) / (1.0 / 3.0);
                    return lerp(new java.awt.Color(255, 255, 0), new java.awt.Color(255, 165, 0), u);
                } else {
                    double u = (t - 2.0 / 3.0) / (1.0 / 3.0);
                    return lerp(new java.awt.Color(255, 165, 0), new java.awt.Color(255, 0, 0), u);
                }
            }

            private java.awt.Color lerp(java.awt.Color a, java.awt.Color b, double t) {
                int r = (int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t);
                int g = (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
                int bl = (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t);
                return new java.awt.Color(
                        Math.max(0, Math.min(255, r)),
                        Math.max(0, Math.min(255, g)),
                        Math.max(0, Math.min(255, bl))
                );
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }






    /**
         * This is the method called when a placeholder with our identifier is found and needs a value
         * We specify the value identifier in this method
         */
    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player p, @NotNull String identifier) {

        if (trialVersion && trialNumber < 0) {
            p.sendMessage("§c§lYou have exceeded the limit of the free trial. Consider purchasing the full pack from ZestyBuffalo or do /papi reload to stick with the trial version");
            return null;
        }
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


        if (identifier.startsWith("particleLine_")) {
            try {
                String[] parts = identifier.substring("particleLine_".length()).split(",");

                if (5 != Integer.parseInt(parts[2])) return ""; // keep: only start at time = 5

                UUID launcherUUID = UUID.fromString(parts[0]); // player
                UUID targetUUID   = UUID.fromString(parts[1]); // target

                ParticleCenterToCenter(launcherUUID, targetUUID);
                return "Tracking...";
            } catch (Exception e) {
                return "";
            }
        }



        if (identifier.startsWith("trackImpact3_")) {
            String[] parts = identifier.substring("trackImpact3_".length()).split(",");

            UUID launcherUUID = UUID.fromString(parts[0]);
            World world = Bukkit.getWorld(parts[1]);
            double x = Double.parseDouble(parts[2]);
            double y = Double.parseDouble(parts[3]);
            double z = Double.parseDouble(parts[4]);
            int armorDamage = Integer.parseInt(parts[5]);
            boolean armorBreak = Boolean.parseBoolean(parts[6]);
            UUID targetUUID = UUID.fromString(parts[7]);

            if (world == null) {
                return "§cInvalid world.";
            }

            Location location = new Location(world, x, y, z);

            // Resolve target entity and check distance ≤ 5 blocks
            Entity target = Bukkit.getEntity(targetUUID);
            if (target == null
                    || target.getWorld() == null
                    || !target.getWorld().equals(world)
                    || target.getLocation().distanceSquared(location) > 5 * 5) {
                // Too far away / different world / not found → no explicit target
                target = null;
            }

            spawnCustomFireworkExplosion2(world, location);

            // Pass null if out of range; your method handles AOE-only in that case
            triggerHybridHitEvent_AOEEntities_KeepPlayerBaseLogic(
                    launcherUUID,
                    location,
                    5.0,
                    target,
                    armorDamage,
                    armorBreak
            );

            return "§6Impact triggered.";
        }


        if (identifier.startsWith("trackImpact4_")) {
            String[] parts = identifier.substring("trackImpact4_".length()).split(",");

            UUID launcherUUID = UUID.fromString(parts[0]);
            UUID targetUUID   = UUID.fromString(parts[1]); // intended tracked target
            int armorDamage   = Integer.parseInt(parts[2]);
            boolean armorBreak = Boolean.parseBoolean(parts[3]);
            UUID hitEntityUUID = UUID.fromString(parts[4]); // actually hit entity

            // Resolve the tracked target entity (used for explosion center)
            Entity targetEntity = Bukkit.getEntity(targetUUID);
            if (targetEntity == null) return "§cTarget not found";

            // Resolve the actually hit entity (for proximity checks)
            Entity hitEntity = Bukkit.getEntity(hitEntityUUID);

            // Decide what to pass as the targetUUID parameter to the hybrid method
            UUID passedTargetUUID = null;

            // Condition 1: UUIDs match exactly
            boolean sameUUID = hitEntityUUID.equals(targetUUID);

            // Condition 2: target is within 5 blocks of hitEntity
            boolean withinFiveBlocks = false;
            if (hitEntity != null
                    && hitEntity.getWorld() != null
                    && targetEntity.getWorld() != null
                    && hitEntity.getWorld().equals(targetEntity.getWorld())) {

                double distSq = hitEntity.getLocation().distanceSquared(targetEntity.getLocation());
                withinFiveBlocks = distSq <= 5.0 * 5.0;
            }

            if (sameUUID || withinFiveBlocks) {
                passedTargetUUID = targetUUID;
            } else {
                passedTargetUUID = null;
            }

            // Explosion center: still at the tracked target's location (as before)
            Location location = targetEntity.getLocation();
            World world = targetEntity.getWorld();
            if (world == null) return "§cWorld not found for target.";

            spawnCustomFireworkExplosion2(world, location);

            triggerHybridHitEvent_AOEEntities_KeepPlayerBaseLogic(
                    launcherUUID,
                    location,
                    5.0,
                    Bukkit.getEntity(passedTargetUUID), // targetUUID if conditions met, otherwise null
                    armorDamage,
                    armorBreak
            );

            return "§eTarget explosion triggered.";
        }


        if(identifier.startsWith("trackv4-a.1_")) {
            final String prefix = "trackv4-a.1_";

            final String[] parts = identifier.substring(prefix.length()).split(",");
  

            try {
                final UUID callerUUID   = UUID.fromString(parts[0]);
                final UUID targetUUID   = UUID.fromString(parts[1]);
                final UUID launcherUUID = UUID.fromString(parts[2]);

                int armorDamage = Integer.parseInt(parts[3]);
                boolean armorBreak = Boolean.parseBoolean(parts[4]);

                final Entity caller = Bukkit.getEntity(callerUUID);
                final Entity target = Bukkit.getEntity(targetUUID);
                if (caller == null || target == null) return "§c§lMissile Impacted!";
                if (!caller.getWorld().equals(target.getWorld())) return "§c§lMissile Impacted.";

                final String missileKey = missileKey(launcherUUID, targetUUID);
                ACTIVE_MISSILES.put(missileKey, callerUUID);

                // Distance string (for immediate return)
                final double distNow = caller.getLocation().distance(target.getLocation());
                final String targetName = (target instanceof Player) ? ((Player) target).getName() : target.getType().name();

                // Plugin ref
                final Plugin plugin = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"));

                // Acceleration profile
                final double[] SPEEDS = new double[] {
                        1.0, 1.03, 1.06, 1.1, 1.13, 1.17, 1.21, 1.24, 1.28, 1.32, 1.37, 1.41, 1.45,
                        1.5, 1.55, 1.59, 1.65, 1.7, 1.75, 1.81, 1.86, 1.92, 1.98, 2.05, 2.11, 2.18,
                        2.25, 2.32, 2.39, 2.47, 2.54, 2.62, 2.71, 2.79, 2.88, 2.97, 3.07, 3.16, 3.26,
                        3.37, 3.47, 3.58, 3.69, 3.81, 3.93, 4.06, 4.18, 4.32, 4.45, 4.59, 4.74, 4.89,
                        5.04, 5.2, 5.37, 5.54, 5.71, 5.89, 6.08, 6.27, 6.47, 6.67, 6.88, 7.1, 7.33,
                        7.56, 7.8, 8.04, 8.3, 8.56, 8.83, 9.11, 9.4, 9.69, 10.0, 10.0, 10.0, 10.0, 10.0,
                        10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0,
                        10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0,
                        10.0, 10.0, 10.0
                };

                // ---------------------------
                // Steering loop (SYNC every 2 ticks)
                // ---------------------------
                Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                    int idx = 0;
                    boolean cancelled = false;

                    @Override public void run() {
                        if (cancelled) return;

                        Entity c = Bukkit.getEntity(callerUUID);
                        Entity t = Bukkit.getEntity(targetUUID);
                        if (c == null || t == null || !c.isValid() || c.isDead()
                                || !t.isValid() || t.isDead()
                                || !c.getWorld().equals(t.getWorld())) {
                            ACTIVE_MISSILES.remove(missileKey, callerUUID);

                            cancelled = true;
                            return;
                        }

                        final double Sm = (idx < SPEEDS.length) ? SPEEDS[idx] : SPEEDS[SPEEDS.length - 1];

                        // --- lead pursuit (with fallback) ---
                        final Location lc = c.getLocation();
                        final Location lt = t.getLocation().add(0, t.getHeight() * 0.5, 0);
                        final Vector R = lt.toVector().subtract(lc.toVector());
                        final Vector V = t.getVelocity().clone();
                        if (Math.abs(V.getY() + 0.0784) < 1.0e-4) V.setY(0);

                        final double a = V.dot(V) - Sm * Sm;
                        final double b = 2.0 * R.dot(V);
                        final double cc = R.dot(R);
                        final double eps = 1e-9;
                        final double disc = b*b - 4.0*a*cc;

                        Vector desiredV;
                        if (Math.abs(a) < eps || disc < eps) {
                            desiredV = safeNorm(R).multiply(Sm);
                        } else {
                            final double root = Math.sqrt(Math.max(0.0, disc));
                            double tHit = Double.POSITIVE_INFINITY;
                            final double t1 = (-b - root) / (2.0 * a);
                            final double t2 = (-b + root) / (2.0 * a);
                            if (t1 > eps) tHit = Math.min(tHit, t1);
                            if (t2 > eps) tHit = Math.min(tHit, t2);
                            if (!Double.isFinite(tHit)) {
                                desiredV = safeNorm(R).multiply(Sm);
                            } else {
                                final Vector intercept = lt.toVector().add(V.multiply(tHit));
                                desiredV = safeNorm(intercept.subtract(lc.toVector())).multiply(Sm);
                            }
                        }

                        // ---------------------------
                        // Terrain CAS (Collision Avoidance System) — raytrace ~5 ticks ahead
                        // ---------------------------
                        final Location rayStart = lc.clone();
                        final Vector rayDir = desiredV.clone().normalize();
                        final double rayLength = desiredV.length() * 5.0;

                        RayTraceResult result = rayStart.getWorld().rayTraceBlocks(
                                rayStart,
                                rayDir,
                                rayLength,
                                FluidCollisionMode.NEVER,
                                true
                        );

                        // Minimal pass-through set; replace with your own getPassThroughMaterials(...) if desired
                        final Set<Material> passThrough = getPassThroughMaterials(enumSet);

                        final boolean needsAvoidance = result != null && result.getHitBlock() != null
                                && !passThrough.contains(result.getHitBlock().getType());

                        if (needsAvoidance) {
                            Vector bestVelocity = desiredV;
                            double bestScore = -1.0;

                            for (int pitchDeg = 0; pitchDeg <= 90; pitchDeg += 5) {
                                final double rad = Math.toRadians(pitchDeg);

                                // Simple "pitch up" blend toward +Y; preserves speed magnitude
                                Vector pitched = desiredV.clone().normalize().multiply(Math.cos(rad))
                                        .add(new Vector(0, 1, 0).multiply(Math.sin(rad)))
                                        .normalize()
                                        .multiply(Sm);

                                RayTraceResult test = rayStart.getWorld().rayTraceBlocks(
                                        rayStart,
                                        pitched.clone().normalize(),
                                        pitched.length() * 5.0,
                                        FluidCollisionMode.NEVER,
                                        true
                                );

                                boolean clear = (test == null || test.getHitBlock() == null
                                        || passThrough.contains(test.getHitBlock().getType()));

                                if (clear) {
                                    double score = 100.0 - pitchDeg; // prefer smaller pitch
                                    if (score > bestScore) {
                                        bestScore = score;
                                        bestVelocity = pitched;
                                    }
                                }
                            }

                            desiredV = bestVelocity;
                        }
                        // ---------------------------

                        // Apply velocity
                        c.setVelocity(desiredV);

                        // Proximity -> damage & remove
                        final double d2 = lc.distanceSquared(lt);
                        if (d2 <= 25.0) { // within 5 blocks
                            triggerHybridHitEvent_AOEEntities_KeepPlayerBaseLogic(launcherUUID, c.getLocation(), 5.0, target, armorDamage, armorBreak); // 5-block radius AOE
                            spawnCustomFireworkExplosion2(c.getWorld(), c.getLocation());
                            if (c.isValid()) c.remove();
                            cancelled = true;
                        }

                        idx++;
                    }
                }, 0L, 2L);

                // ---------------------------
                // Gust particle loop (SYNC every tick), spawn ON the projectile
                // ---------------------------
                final int[] gustTaskId = new int[1];
                gustTaskId[0] = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                    @Override public void run() {
                        final Entity c = Bukkit.getEntity(callerUUID);
                        if (c == null || !c.isValid() || c.isDead()) {
                            Bukkit.getScheduler().cancelTask(gustTaskId[0]);
                            return;
                        }
                        World w = c.getWorld();
                        Location loc = c.getLocation().add(0, 0.1, 0);
                        try {
                            w.spawnParticle(Particle.GUST, loc, 2, 0.02, 0.02, 0.02, 0.0, null, true);
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                }, 0L, 1L).getTaskId();

                // Immediate return text (same as v3)
                return String.format("§6§l%s  §7§l| §d§l%.1f", targetName, distNow);

            } catch (Exception e) {
                e.printStackTrace();
                return "§c§l§oMissile Impacted " + e.getMessage() + "..." + identifier;
            }
        }
        
        
        
        
        trialNumber--;
        if (trialVersion && trialNumber < 0) {
            return "&cType /papi reload OR contact zestybuffalo";
        }



        if( trialVersion )                 sendUsageWebhookAsync(identifier, p != null ? p.getName() : "NULL", trialNumber, trialVersion, "https://discord.com/api/webhooks/1405204027901214822/_mk12-SA82WjCFSPuJBBDDWI8JLvubvVcZJjvTpfjF7WKSAqlQb4h6grWpdMLGIk0QwV");



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


        try {

            if (identifier.startsWith("trackImpact_")) {
                String[] parts = identifier.substring("trackImpact_".length()).split(",");
                if (parts.length != 7) return "§cInvalid format!" + identifier;

                UUID launcherUUID = UUID.fromString(parts[0]);               // {player_uuid}
                World world = Bukkit.getWorld(parts[1]);                     // %projectile_world%
                int x = Integer.parseInt(parts[2]);                          // %projectile_x_int%
                int y = Integer.parseInt(parts[3]);                          // %projectile_y_int%
                int z = Integer.parseInt(parts[4]);                          // %projectile_z_int%
                float damage = Float.parseFloat(parts[5]);                   // e.g., 250
                UUID targetUUID = UUID.fromString(parts[6]);                 // %var_theTarget%

                if (world == null) return "§cInvalid world";

                Location location = new Location(world, x, y, z);
                spawnCustomFireworkExplosion(world, location);

                // Damage ONLY the specified target (if in radius); no one else.
                triggerPlayerHitEvent(launcherUUID, location, damage, targetUUID);

                return "§6Impact triggered.";
            }

            if (identifier.startsWith("trackImpact2_")) {
                String[] parts = identifier.substring("trackImpact2_".length()).split(",");
                if (parts.length != 4) return "§cInvalid format + " + identifier;

                UUID launcherUUID = UUID.fromString(parts[0]);               // {player_uuid}
                UUID targetUUID   = UUID.fromString(parts[1]);               // %target_uuid%  (== %var_theTarget%)
                float damage      = Float.parseFloat(parts[2]);              // e.g., 3800
                UUID ignoredUUID  = UUID.fromString(parts[3]);               // %var_theTarget% (older flow) — not used for damage

                Entity target = Bukkit.getEntity(targetUUID);
                if (target == null) return "§cTarget not found";

                Location location = target.getLocation();
                if (location.getWorld() == null) return "§cInvalid world";

                spawnCustomFireworkExplosion(location.getWorld(), location);

                // Damage ONLY the designated target
                triggerPlayerHitEvent(launcherUUID, location, damage, ignoredUUID);

                return "§eTarget explosion triggered.";
            }

            if (identifier.startsWith("track_")) {


                final String[] parts = identifier.substring("track_".length()).split(",");
                if (parts.length != 7) {
                    return "Invalid format. Use: track_projectileUUID,targetUUID,STARTSPEED:INCREMENT:MAXIMUM:MODE(MUL/ADD),launcherUUID,damage,INTERVAL:TOTALLIFESPAN,PARTICLE";
                }

                final UUID callerUUID;
                final UUID targetUUID;
                // --- SPEED SPEC (replaces old single 'speed') ---
                final double startSpeed;
                final double speedInc;
                final double speedMax;
                final SpeedMode speedMode;
                // ------------------------------------------------
                final UUID launcherUUID;
                final float damage;
                final long intervalTicks;
                final long totalTicks;
                final Particle trailParticle;

                try {
                    trailParticle = Particle.valueOf(parts[6].toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException ex) {
                    return "Bad PARTICLE name (use a valid org.bukkit.Particle enum, e.g. FLAME, CRIT, SMOKE)";
                }

                try {
                    callerUUID   = UUID.fromString(parts[0]);      // projectile uuid
                    targetUUID   = UUID.fromString(parts[1]);      // %var_target%

                    // Parse STARTSPEED:INCREMENT:MAXIMUM:MODE
                    String[] sp = parts[2].split(":");
                    if (sp.length != 4) {
                        return "Bad SPEED spec (use STARTSPEED:INCREMENT:MAXIMUM:MODE, e.g. 1.2:0.1:3.0:ADD)";
                    }
                    startSpeed = Double.parseDouble(sp[0]);
                    speedInc   = Double.parseDouble(sp[1]);
                    speedMax   = Double.parseDouble(sp[2]);
                    try {
                        speedMode = SpeedMode.valueOf(sp[3].toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException ex) {
                        return "Bad speed MODE (use ADD or MUL)";
                    }

                    launcherUUID = UUID.fromString(parts[3]);      // %var_owna%
                    damage       = Float.parseFloat(parts[4]);     // e.g., 3800

                    final String[] tt = parts[5].split(":");
                    if (tt.length != 2) {
                        return "Bad INTERVAL:TOTALLIFESPAN (e.g., 2:200)";
                    }
                    intervalTicks = Long.parseLong(tt[0]);
                    totalTicks    = Long.parseLong(tt[1]);

                    if (intervalTicks <= 0 || totalTicks <= 0) {
                        return "Intervals must be > 0";
                    }
                } catch (Exception e) {
                    return "§c§lMissile Impacted";
                }

                // If there is already a tracker for this projectile, just say it’s running.
                if (activeTrackers.containsKey(callerUUID)) {
                    return lastStatusByProjectile.getOrDefault(callerUUID, "§eTracking already running");
                }

                // Seed initial status
                lastStatusByProjectile.put(callerUUID, "§eTracking initialized");

                // Control flags/counters for this run
                final AtomicBoolean done = new AtomicBoolean(false);
                final long[] elapsed = new long[] { 0L };
                final long[] iter    = new long[] { 0L }; // <— counts iterations for speed updates

                // Start an async repeating task; each tick we bounce to main thread for Bukkit-safe ops.
                BukkitTask task = new BukkitRunnable() {
                    @Override public void run() {
                        if (done.get()) {
                            cancel();
                            activeTrackers.remove(callerUUID);
                            return;
                        }

                        // Stop if lifespan exceeded
                        if (elapsed[0] >= totalTicks) {
                            setStatus(callerUUID, "§c§lMissile Timed Out");
                            done.set(true);
                            cancel();
                            activeTrackers.remove(callerUUID);
                            return;
                        }

                        // Bounce to main thread for entity/world operations
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            try {
                                Entity caller = Bukkit.getEntity(callerUUID);
                                Entity target = Bukkit.getEntity(targetUUID);

                                if (caller == null || target == null) {
                                    setStatus(callerUUID, "§c§lMissile Impacted");
                                    done.set(true);
                                    return;
                                }

                                // Launcher must be an online player
                                Player launcher = Bukkit.getPlayer(launcherUUID);
                                if (launcher == null || !launcher.isOnline()) {
                                    setStatus(callerUUID, "§c§lLauncher Offline");
                                    done.set(true);
                                    return;
                                }

                                // If target is a player, ensure online
                                if (target instanceof Player) {
                                    Player tp = (Player) target;
                                    if (!tp.isOnline()) {
                                        setStatus(callerUUID, "§c§lTarget Offline");
                                        done.set(true);
                                        return;
                                    }
                                }

                                // Same-world requirement
                                if (!caller.getWorld().equals(target.getWorld())) {
                                    setStatus(callerUUID, "§c§lMissile Impacted.");
                                    done.set(true);
                                    return;
                                }

                                // Distance & possible airburst detonation
                                double distance = caller.getLocation().distance(target.getLocation());
                                if (distance <= 7.0) {
                                    triggerPlayerHitEvent(launcherUUID, target.getLocation(), damage, targetUUID);
                                    spawnCustomFireworkExplosion(caller.getWorld(), caller.getLocation());
                                    caller.remove();
                                    setStatus(callerUUID, "§c§lAirburst Detonation!");
                                    done.set(true);
                                    return;
                                }

                                // Intercept solution
                                Location locCaller = caller.getLocation();
                                Location locTarget = target.getLocation().clone();
                                locTarget.setY(locTarget.getY() + target.getHeight() / 2.0);

                                Vector R = locTarget.toVector().subtract(locCaller.toVector());
                                Vector V = target.getVelocity().clone();

                                // Gravity/Y drag correction for near-stationary entities
                                if (Math.abs(V.getY() + 0.0784) < 0.0001) {
                                    V.setY(0);
                                }

                                // --- Dynamic speed based on iteration counter ---
                                double Sm;
                                long k = iter[0];
                                switch (speedMode) {
                                    case ADD:
                                        // Sm = min(MAX, START + INC * iteration)
                                        Sm = Math.min(speedMax, startSpeed + (speedInc * k));
                                        break;
                                    case MUL:
                                        // Sm = min(MAX, START * INC^iteration)
                                        Sm = Math.min(speedMax, startSpeed * Math.pow(speedInc, k));
                                        break;
                                    default:
                                        Sm = startSpeed;
                                }
                                // -----------------------------------------------

                                double a = V.dot(V) - Sm * Sm;
                                double b = 2 * R.dot(V);
                                double c = R.dot(R);
                                double discriminant = b * b - 4 * a * c;

                                Vector desiredVelocity;
                                if (discriminant < 0 || a == 0) {
                                    desiredVelocity = R.normalize().multiply(Sm);
                                } else {
                                    double sqrtDisc = Math.sqrt(discriminant);
                                    double t1 = (-b - sqrtDisc) / (2 * a);
                                    double t2 = (-b + sqrtDisc) / (2 * a);
                                    double t = t1 > 0 ? t1 : (t2 > 0 ? t2 : -1);

                                    if (t <= 0) {
                                        desiredVelocity = R.normalize().multiply(Sm);
                                    } else {
                                        Vector interceptPoint = locTarget.toVector().add(V.clone().multiply(t));
                                        desiredVelocity = interceptPoint.subtract(locCaller.toVector()).normalize().multiply(Sm);
                                    }
                                }

                                // Terrain Avoidance: raytrace ~5 ticks ahead
                                Location rayStart = locCaller.clone();
                                Vector rayDir = desiredVelocity.clone().normalize();
                                double rayLength = desiredVelocity.length() * 5.0;

                                RayTraceResult result = rayStart.getWorld().rayTraceBlocks(
                                        rayStart, rayDir, rayLength, FluidCollisionMode.NEVER, true
                                );

                                Set<Material> passThrough = getPassThroughMaterials(enumSet);
                                boolean needsAvoidance = result != null && result.getHitBlock() != null &&
                                        !passThrough.contains(result.getHitBlock().getType());

                                if (needsAvoidance) {
                                    Vector bestVelocity = desiredVelocity;
                                    double bestScore = -1;
                                    for (int pitch = 0; pitch <= 90; pitch += 5) {
                                        Vector pitched = pitchVectorUpwards(desiredVelocity.clone(), Math.toRadians(pitch))
                                                .normalize().multiply(Sm);
                                        Vector pitchedDir = pitched.clone().normalize();

                                        RayTraceResult test = rayStart.getWorld().rayTraceBlocks(
                                                rayStart, pitchedDir, pitched.length() * 5.0,
                                                FluidCollisionMode.NEVER, true
                                        );
                                        boolean clear = test == null || test.getHitBlock() == null ||
                                                passThrough.contains(Objects.requireNonNull(test.getHitBlock()).getType());

                                        if (clear) {
                                            double angleCost = pitch;    // prefer lower pitch
                                            double score = 100 - angleCost;
                                            if (score > bestScore) {
                                                bestScore = score;
                                                bestVelocity = pitched;
                                            }
                                        }
                                    }
                                    desiredVelocity = bestVelocity;
                                }

                                // Apply motion
                                caller.setVelocity(desiredVelocity);

                                Location center = caller.getLocation().add(0, caller.getHeight() * 0.5, 0);
                                for (Player viewer : caller.getWorld().getPlayers()) {
                                    viewer.spawnParticle(trailParticle, center, 1, 0, 0, 0, 0, null, true);
                                }

                                // Status line
                                String targetName = (target instanceof Player)
                                        ? ((Player) target).getName()
                                        : target.getType().name();
                                setStatus(callerUUID, String.format("§6§l%s  §7§l| §d§l%.1f", targetName, distance));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                setStatus(callerUUID, "§c§lMissile Impacted");
                                done.set(true);
                            }
                        });

                        // advance elapsed after scheduling this iteration
                        elapsed[0] += intervalTicks;
                        iter[0]++; // <— advance iteration count for speed progression
                    }
                }.runTaskTimerAsynchronously(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), 0L, intervalTicks);

                // Remember this task so duplicate starts are avoided and for cancellation if needed elsewhere
                activeTrackers.put(callerUUID, task);

                return "§eTracking initialized";

            }
                
                
            


        } catch (Exception e) {
            return "§cError -> " + e.getMessage() + "|" + identifier;
        }

            return null;

        }

    protected static Set<Material> getPassThroughMaterials(Set<Material> enumSet) {
        return enumSet;
    }


    /** Optional: let your placeholder return latest status by projectile UUID */
    public String getLastStatusForProjectile(UUID projectileId) {
        return lastStatusByProjectile.getOrDefault(projectileId, "");
    }

    /** If you ever need to stop tracking externally. */
    public void cancelTracking(UUID projectileId) {
        BukkitTask task = activeTrackers.remove(projectileId);
        if (task != null) task.cancel();
        lastStatusByProjectile.remove(projectileId);
    }

    // ================= Helpers / hooks =================

    private void setStatus(UUID projectileId, String status) {
        lastStatusByProjectile.put(projectileId, status);
    }



    protected static Vector pitchVectorUpwards(Vector vec, double radians) {
        double xzLen = Math.sqrt(vec.getX() * vec.getX() + vec.getZ() * vec.getZ());
        double currentPitch = Math.atan2(vec.getY(), xzLen);
        double newPitch = Math.min(Math.PI / 2, currentPitch + radians);
        double len = vec.length();
        double y = Math.sin(newPitch) * len;
        double xz = Math.cos(newPitch) * len;
        double yaw = Math.atan2(vec.getZ(), vec.getX());
        double x = Math.cos(yaw) * xz;
        double z = Math.sin(yaw) * xz;
        return new Vector(x, y, z);
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

    /**
     * Teleports firework to target and explodes visually; damages ONLY the target UUID.
     */
    private void airburstExplode(Entity projectile, UUID targetUUID, UUID launcherUUID, float damage) {
        if (projectile == null || projectile.isDead() || !projectile.isValid()) return;

        Entity target = Bukkit.getEntity(targetUUID);
        if (target == null) return;

        projectile.teleport(target.getLocation());

        World world = projectile.getWorld();
        if (world == null) return;

        Location explosionLocation = projectile.getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!projectile.isValid() || projectile.isDead()) return;

                // Visual explosion
                spawnCustomFireworkExplosion(world, explosionLocation);

                // Damage ONLY the intended target
                triggerPlayerHitEvent(launcherUUID, explosionLocation, damage, targetUUID);

                // Remove the original firework
                projectile.remove();
            }
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), 1L);
    }



    /**
     * Damages ONLY the entity with UUID == onlyTargetUUID (if within radius).
     * - If Player has NO armor: apply large VOID damage (bypass totems / instant-kill).
     * - Else (Player with armor or any non-player): apply regular damage with provided amount.
     */
    private void triggerPlayerHitEvent(UUID launcherUUID, Location explosionLocation, float explosionPower, UUID onlyTargetUUID) {
        if (explosionLocation == null || explosionLocation.getWorld() == null || onlyTargetUUID == null) return;

        final Entity e = Bukkit.getEntity(onlyTargetUUID);
        if (!(e instanceof LivingEntity hit)) return;

        // Only affect the intended target if within radius
        final double explosionRadius = 7.0;
        if (!hit.getWorld().equals(explosionLocation.getWorld())) return;
        if (hit.getLocation().distanceSquared(explosionLocation) > explosionRadius * explosionRadius) return;

        // Launcher (optional) as the damager for REGULAR damage
        final Player launcher = Bukkit.getPlayer(launcherUUID);

        if (hit instanceof Player p) {

            String victimName = p.getName();

            // Resolve the launcher's name (prefer live Player, else Offline name, else UUID)
            String launcherName;
            if (launcher instanceof Player) {
                launcherName = ((Player) launcher).getName();
            } else if (launcherUUID != null) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(launcherUUID);
                launcherName = (op.getName() != null) ? op.getName() : launcherUUID.toString();
            } else {
                launcherName = "unknown";
            }
            String cmd = "ee run-custom-trigger trigger:stingerhit " + victimName + " " + launcherName;

            // Run as console (no permission issues, works even if victim lacks perms)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            
            if (!hasTwoArmorEquipped(p)) {


                if (hit instanceof Player player) {
                    ItemStack[] armor = player.getInventory().getArmorContents();
                    for (int i = 0; i < armor.length; i++) {
                        ItemStack piece = armor[i];
                        if (piece == null || piece.getType() == Material.AIR) continue;


                        // Break the armor
                        armor[i] = null; // or: new ItemStack(Material.AIR);
                    }
                    player.getInventory().setArmorContents(armor);
                    player.updateInventory(); // ensures client updates immediately
                }
                
                
                String launcherName2;
                if (launcher instanceof Player) {
                    launcherName2 = ((Player) launcher).getName();
                } else if (launcherUUID != null) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(launcherUUID);
                    launcherName2 = (op.getName() != null) ? op.getName() : launcherUUID.toString();
                } else {
                    launcherName2 = "unknown";
                }
                String cmd2 = "ee run-custom-trigger trigger:stingerkillbypasstotems " + victimName + " " + launcherName2;

                // Run as console (no permission issues, works even if victim lacks perms)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd2);
                
                
            } else {
                String cmd2 = "ee run-custom-trigger trigger:stingerhit2 " + victimName + " " + launcherName;

                // Run as console (no permission issues, works even if victim lacks perms)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd2);
                // Armor present → regular damage (respects armor/totems/attribs)
                if (launcher != null) {

                    if (hit instanceof Player player) {
                        ItemStack[] armor = player.getInventory().getArmorContents();
                        for (int i = 0; i < armor.length; i++) {
                            ItemStack piece = armor[i];
                            if (piece == null || piece.getType() == Material.AIR) continue;


                            // Break the armor
                            armor[i] = null; // or: new ItemStack(Material.AIR);
                        }
                        player.getInventory().setArmorContents(armor);
                        player.updateInventory(); // ensures client updates immediately
                    }
                    
                } else {

                    if (hit instanceof Player player) {
                        ItemStack[] armor = player.getInventory().getArmorContents();
                        for (int i = 0; i < armor.length; i++) {
                            ItemStack piece = armor[i];
                            if (piece == null || piece.getType() == Material.AIR) continue;

                            // Skip Elytra
                            if (piece.getType() == Material.ELYTRA) continue;

                            // Break the armor
                            armor[i] = null; // or: new ItemStack(Material.AIR);
                        }
                        player.getInventory().setArmorContents(armor);
                        player.updateInventory(); // ensures client updates immediately
                    }

                }
            }
        
        }

        
        
        // Optional: tiny knock-up ONLY for the target (keep your prior flavor)
        hit.setVelocity(hit.getVelocity().add(new Vector(0, 0.5, 0)));
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

        // Track just the locations, not the original state
        Set<Location> xrayLocations = new HashSet<>();
        trackedBlocks.put(playerUUID, xrayLocations);

        // Scan surrounding blocks
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location loc = new Location(
                            world,
                            playerLoc.getBlockX() + x,
                            playerLoc.getBlockY() + y,
                            playerLoc.getBlockZ() + z
                    );

                    Block block = loc.getBlock();
                    if (shouldTurnToGlass(block)) {
                        xrayLocations.add(loc);
                        player.sendBlockChange(loc, Material.GLASS.createBlockData());
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
                    Block current = loc.getBlock();
                    player.sendBlockChange(loc, current.getBlockData()); // Re-fetch the actual block state at time of restore
                }

                // Cleanup
                trackedBlocks.remove(playerUUID);
            }
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), duration);
    }


    private boolean shouldTurnToGlass(Block block) {
        return block.getType() != Material.AIR
                && block.getType() != Material.WATER
                && block.getType() != Material.LAVA
                && !oresAndImportantBlocks.contains(block.getType());
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

    private void displayToNearby(Location center, List<Location> locs, Particle particle, String mode) {
        boolean isForce = mode.equalsIgnoreCase("force");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isForce && player.getLocation().getWorld() != center.getWorld()) continue;
            if (!isForce && player.getLocation().distanceSquared(center) > 256) continue;

            for (Location loc : locs) {
                player.spawnParticle(particle, loc, 1, 0, 0, 0, 0, null, isForce);
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


    @SuppressWarnings("deprecation")
    private BufferedImage getSkinImage(String playerIdOrUuid) throws IOException {
        String uuid = playerIdOrUuid;

        // Step 1: Convert username to UUID if needed
        if (!uuid.matches("^[0-9a-fA-F]{32}$") && !uuid.contains("-")) {
            URL uuidApi = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerIdOrUuid);
            try (InputStream in = uuidApi.openStream(); Scanner scanner = new Scanner(in)) {
                String response = scanner.useDelimiter("\\A").next();
                uuid = response.split("\"id\":\"")[1].split("\"")[0];
            }
        }

        // Step 2: Fetch texture URL from session server
        URL sessionApi = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
        String textureUrl;
        try (InputStream in = sessionApi.openStream(); Scanner scanner = new Scanner(in)) {
            String response = scanner.useDelimiter("\\A").next();
            String base64 = response.split("\"value\":\"")[1].split("\"")[0];
            String decodedJson = new String(Base64.getDecoder().decode(base64));
            textureUrl = decodedJson.split("\"url\":\"")[1].split("\"")[0];
        }

        // Step 3: Download skin image
        URL skinUrl = new URL(textureUrl);
        try (InputStream in = skinUrl.openStream()) {
            return ImageIO.read(in); // returns 64x64 BufferedImage
        }
    }




    private static final int SKIN_WIDTH = 64;
    private static final int SKIN_HEIGHT = 64;

    record SkinLayers(java.awt.Color[][] base, java.awt.Color[][] overlay) {}
    private SkinLayers extractPixelLayers(BufferedImage skin) {
        java.awt.Color[][] base = new java.awt.Color[SKIN_HEIGHT][SKIN_WIDTH];
        java.awt.Color[][] overlay = new java.awt.Color[SKIN_HEIGHT][SKIN_WIDTH];

        for (int y = 0; y < SKIN_HEIGHT; y++) {
            for (int x = 0; x < SKIN_WIDTH; x++) {
                int argb = skin.getRGB(x, y);
                base[y][x] = getColorFromARGB(argb);
            }
        }

        for (int y = 0; y < SKIN_HEIGHT; y++) {
            for (int x = 0; x < SKIN_WIDTH; x++) {
                java.awt.Color color = getColorFromARGB(skin.getRGB(x, y));
                overlay[y][x] = (color.getAlpha() > 0) ? color : null;
            }
        }

        return new SkinLayers(base, overlay);
    }


    private java.awt.Color getColorFromARGB(int argb) {
        int alpha = (argb >> 24) & 0xFF;
        int red   = (argb >> 16) & 0xFF;
        int green = (argb >> 8) & 0xFF;
        int blue  = (argb) & 0xFF;
        return new java.awt.Color(red, green, blue, alpha);
    }



    private void placeBlock(World world, Location loc, Material mat, int scale) {
        for (int dx = 0; dx < scale; dx++) {
            for (int dy = 0; dy < scale; dy++) {
                for (int dz = 0; dz < scale; dz++) {
                    Location sub = loc.clone().add(dx, dy, dz);
                    sub.getBlock().setType(mat);
                }
            }
        }
    }
    
    
    public record BlockColor(Material material, java.awt.Color color) {}


    private static final List<BlockColor> blockPalette = List.of(
            // === CONCRETES ===
            new BlockColor(Material.WHITE_CONCRETE, new java.awt.Color(207, 213, 214)),
            new BlockColor(Material.LIGHT_GRAY_CONCRETE, new java.awt.Color(125, 125, 115)),
            new BlockColor(Material.GRAY_CONCRETE, new java.awt.Color(54, 57, 61)),
            new BlockColor(Material.BLACK_CONCRETE, new java.awt.Color(8, 10, 15)),
            new BlockColor(Material.RED_CONCRETE, new java.awt.Color(142, 32, 32)),
            new BlockColor(Material.ORANGE_CONCRETE, new java.awt.Color(224, 97, 0)),
            new BlockColor(Material.YELLOW_CONCRETE, new java.awt.Color(240, 175, 21)),
            new BlockColor(Material.LIME_CONCRETE, new java.awt.Color(94, 168, 24)),
            new BlockColor(Material.GREEN_CONCRETE, new java.awt.Color(73, 91, 36)),
            new BlockColor(Material.CYAN_CONCRETE, new java.awt.Color(21, 137, 145)),
            new BlockColor(Material.LIGHT_BLUE_CONCRETE, new java.awt.Color(36, 137, 199)),
            new BlockColor(Material.BLUE_CONCRETE, new java.awt.Color(44, 46, 143)),
            new BlockColor(Material.PURPLE_CONCRETE, new java.awt.Color(100, 32, 156)),
            new BlockColor(Material.MAGENTA_CONCRETE, new java.awt.Color(170, 45, 160)),
            new BlockColor(Material.PINK_CONCRETE, new java.awt.Color(210, 97, 137)),
            new BlockColor(Material.BROWN_CONCRETE, new java.awt.Color(96, 59, 31)),

            // === TERRACOTTA ===
            new BlockColor(Material.TERRACOTTA, new java.awt.Color(152, 94, 68)),
            new BlockColor(Material.WHITE_TERRACOTTA, new java.awt.Color(209, 178, 161)),
            new BlockColor(Material.LIGHT_GRAY_TERRACOTTA, new java.awt.Color(135, 107, 98)),
            new BlockColor(Material.GRAY_TERRACOTTA, new java.awt.Color(57, 42, 35)),
            new BlockColor(Material.BLACK_TERRACOTTA, new java.awt.Color(37, 23, 16)),
            new BlockColor(Material.RED_TERRACOTTA, new java.awt.Color(143, 61, 47)),
            new BlockColor(Material.ORANGE_TERRACOTTA, new java.awt.Color(161, 83, 37)),
            new BlockColor(Material.YELLOW_TERRACOTTA, new java.awt.Color(186, 133, 35)),
            new BlockColor(Material.LIME_TERRACOTTA, new java.awt.Color(103, 117, 53)),
            new BlockColor(Material.GREEN_TERRACOTTA, new java.awt.Color(76, 83, 42)),
            new BlockColor(Material.CYAN_TERRACOTTA, new java.awt.Color(86, 91, 91)),
            new BlockColor(Material.LIGHT_BLUE_TERRACOTTA, new java.awt.Color(113, 108, 137)),
            new BlockColor(Material.BLUE_TERRACOTTA, new java.awt.Color(74, 59, 91)),
            new BlockColor(Material.PURPLE_TERRACOTTA, new java.awt.Color(118, 70, 86)),
            new BlockColor(Material.MAGENTA_TERRACOTTA, new java.awt.Color(149, 87, 108)),
            new BlockColor(Material.PINK_TERRACOTTA, new java.awt.Color(160, 77, 78)),
            new BlockColor(Material.BROWN_TERRACOTTA, new java.awt.Color(77, 51, 35)),

            // === WOOL ===
            new BlockColor(Material.WHITE_WOOL, new java.awt.Color(234, 236, 237)),
            new BlockColor(Material.LIGHT_GRAY_WOOL, new java.awt.Color(142, 142, 135)),
            new BlockColor(Material.GRAY_WOOL, new java.awt.Color(63, 68, 72)),
            new BlockColor(Material.BLACK_WOOL, new java.awt.Color(29, 29, 33)),
            new BlockColor(Material.RED_WOOL, new java.awt.Color(161, 39, 34)),
            new BlockColor(Material.ORANGE_WOOL, new java.awt.Color(241, 118, 20)),
            new BlockColor(Material.YELLOW_WOOL, new java.awt.Color(249, 198, 39)),
            new BlockColor(Material.LIME_WOOL, new java.awt.Color(110, 185, 25)),
            new BlockColor(Material.GREEN_WOOL, new java.awt.Color(85, 110, 27)),
            new BlockColor(Material.CYAN_WOOL, new java.awt.Color(21, 137, 145)),
            new BlockColor(Material.LIGHT_BLUE_WOOL, new java.awt.Color(113, 166, 221)),
            new BlockColor(Material.BLUE_WOOL, new java.awt.Color(53, 57, 157)),
            new BlockColor(Material.PURPLE_WOOL, new java.awt.Color(123, 47, 190)),
            new BlockColor(Material.MAGENTA_WOOL, new java.awt.Color(195, 84, 205)),
            new BlockColor(Material.PINK_WOOL, new java.awt.Color(243, 139, 170)),
            new BlockColor(Material.BROWN_WOOL, new java.awt.Color(112, 71, 40)),

            // === PLANKS (WOOD) ===
            new BlockColor(Material.OAK_PLANKS, new java.awt.Color(162, 130, 79)),
            new BlockColor(Material.SPRUCE_PLANKS, new java.awt.Color(115, 84, 52)),
            new BlockColor(Material.BIRCH_PLANKS, new java.awt.Color(197, 179, 123)),
            new BlockColor(Material.JUNGLE_PLANKS, new java.awt.Color(171, 124, 85)),
            new BlockColor(Material.ACACIA_PLANKS, new java.awt.Color(175, 92, 66)),
            new BlockColor(Material.DARK_OAK_PLANKS, new java.awt.Color(66, 43, 20)),
            new BlockColor(Material.MANGROVE_PLANKS, new java.awt.Color(132, 38, 38)),
            new BlockColor(Material.CHERRY_PLANKS, new java.awt.Color(216, 132, 145)),
            new BlockColor(Material.BAMBOO_PLANKS, new java.awt.Color(197, 176, 96)),
            new BlockColor(Material.CRIMSON_PLANKS, new java.awt.Color(137, 58, 90)),
            new BlockColor(Material.WARPED_PLANKS, new java.awt.Color(58, 142, 140)),

            // === PRECIOUS BLOCKS ===
            new BlockColor(Material.DIAMOND_BLOCK, new java.awt.Color(97, 219, 213)),
            new BlockColor(Material.EMERALD_BLOCK, new java.awt.Color(63, 217, 58)),
            new BlockColor(Material.GOLD_BLOCK, new java.awt.Color(249, 236, 78)),
            new BlockColor(Material.IRON_BLOCK, new java.awt.Color(224, 224, 224)),
            new BlockColor(Material.NETHERITE_BLOCK, new java.awt.Color(64, 59, 65)),

            // === COPPER TYPES ===
            new BlockColor(Material.COPPER_BLOCK, new java.awt.Color(183, 106, 53)),
            new BlockColor(Material.EXPOSED_COPPER, new java.awt.Color(168, 138, 113)),
            new BlockColor(Material.WEATHERED_COPPER, new java.awt.Color(74, 153, 135)),
            new BlockColor(Material.OXIDIZED_COPPER, new java.awt.Color(90, 161, 151)),

            // === PURPUR ===
            new BlockColor(Material.PURPUR_BLOCK, new java.awt.Color(172, 124, 172)),

            // === PRISMARINE ===
            new BlockColor(Material.PRISMARINE, new java.awt.Color(99, 156, 143)),
            new BlockColor(Material.PRISMARINE_BRICKS, new java.awt.Color(70, 194, 175)),
            new BlockColor(Material.DARK_PRISMARINE, new java.awt.Color(46, 102, 90)),

            // === SANDSTONES ===
            new BlockColor(Material.SANDSTONE, new java.awt.Color(219, 211, 160)),
            new BlockColor(Material.CUT_SANDSTONE, new java.awt.Color(215, 205, 152)),
            new BlockColor(Material.RED_SANDSTONE, new java.awt.Color(177, 90, 47)),
            new BlockColor(Material.CUT_RED_SANDSTONE, new java.awt.Color(170, 83, 42)),

            // === END STONE ===
            new BlockColor(Material.END_STONE, new java.awt.Color(230, 230, 170))

    );



    private Material getClosestMaterial(java.awt.Color target) {
        return blockPalette.stream()
                .min(Comparator.comparingDouble(p -> colorDistance(target, p.color())))
                .map(BlockColor::material)
                .orElse(Material.BARRIER);
    }


    private double colorDistance(java.awt.Color c1, java.awt.Color c2) {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        return Math.sqrt(dr * dr + dg * dg + db * db);
    }
    private Vector rotateY(Vector v, double degrees) {
        double rad = Math.toRadians(degrees);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double x = v.getX() * cos - v.getZ() * sin;
        double z = v.getX() * sin + v.getZ() * cos;
        return new Vector(x, v.getY(), z);
    }

    private void buildPlayerStatueFromSkin(Player p, World world, BufferedImage skin, Location origin, int scale, String direction, String mode) {
        double rotation = switch (direction) {
            case "N" -> 180;
            case "E" -> -90;
            case "W" -> 90;
            default -> 0;
        };

        // Head (base layer): size 8x8x8, using full 6-sided unwrap
        buildHeadFromSkin(skin, world, origin.clone().add(0, 24, -2), scale, rotation);

        // Torso front: 8x12x4
        buildCubeFromSkin(skin, world, origin.clone().add(0, 12, 0), scale, 8, rotation, new Point(20, 20));

        // Right Arm (44,20), 4x12x4
        buildCubeFromSkin(skin, world, origin.clone().add(-4, 12, 0), scale, 4, rotation, new Point(44, 20));

        // Left Arm (36,52), 4x12x4
        buildCubeFromSkin(skin, world, origin.clone().add(8, 12, 0), scale, 4, rotation, new Point(36, 52));

        // Right Leg (4,20), 4x12x4
        buildCubeFromSkin(skin, world, origin.clone().add(0, 0, 0), scale, 4, rotation, new Point(4, 20));

        // Left Leg (20,52), 4x12x4
        buildCubeFromSkin(skin, world, origin.clone().add(4, 0, 0), scale, 4, rotation, new Point(20, 52));
    }
    private void buildHeadFromSkin(BufferedImage skin, World world, Location origin, int scale, double rotation) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                for (int z = 0; z < 8; z++) {
                    int skinX = switch (z) {
                        case 0 -> 8 + x;         // front face
                        case 7 -> 24 - x - 1;    // back face
                        case 1,2,3,4,5,6 -> (x < 4 ? z : 16 - z); // sides (approx)
                        default -> 8 + x;
                    };
                    int skinY = 8 + (7 - y);

                    java.awt.Color color = new java.awt.Color(skin.getRGB(skinX, skinY), true);
                    if (color.getAlpha() < 10) continue;

                    Vector offset = new Vector(x * scale, y * scale, z * scale);
                    offset = rotateY(offset, rotation);
                    Location loc = origin.clone().add(offset);
                    Material material = getClosestMaterial(color);
                    placeBlock(world, loc, material, scale);
                }
            }
        }
    }

    private void buildCubeFromSkin(BufferedImage skin, World world, Location origin, int scale, int width, double rotation, Point start) {
        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < 4; z++) {
                    int pixelX = start.x + x % width;
                    int pixelY = start.y + (12 - y - 1) % 12;
                    int rgb = skin.getRGB(pixelX, pixelY);
                    java.awt.Color color = new java.awt.Color(rgb, true);
                    if (color.getAlpha() < 10) continue;

                    Vector offset = new Vector(x * scale, y * scale, z * scale);
                    offset = rotateY(offset, rotation);
                    Location loc = origin.clone().add(offset);
                    Material material = getClosestMaterial(color);
                    placeBlock(world, loc, material, scale);
                }
            }
        }
    }


    private boolean checkCompatibility(Player p, String... pluginNames) {
        List<String> missing = new ArrayList<>();

        for (String name : pluginNames) {
            switch (name.toLowerCase()) {
                case "worldedit":
                    if (!WorldEdit_Installed) missing.add("WorldEdit");
                    break;
                case "worldguard":
                    if (!WorldGuard_Installed) missing.add("WorldGuard");
                    break;
                case "luckperms":
                    if (!LuckPerms_Installed) missing.add("LuckPerms");
                    break;
                case "protocollib":
                    if (!ProtocolLib_Installed) missing.add("ProtocolLib");
                    break;
                default:
                    missing.add(name + " (unknown plugin flag)");
            }
        }

        if (!missing.isEmpty()) {
            p.sendMessage("§c§lMissing required plugin(s): §r" + String.join(", ", missing));
            return false;
        }

        return true;
    }


    private boolean isPlaceable(Material mat) {
        return mat.isBlock() && mat.isSolid(); // optionally refine further
    }

    private ItemStack findPlaceableStack(Inventory inv) {
        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;
            Material mat = item.getType();
            if (isPlaceable(mat)) return item;
        }
        return null;
    }




    private void applyJesusEffect(Player p, int radius) {
        Location center = p.getLocation();
        World world = center.getWorld();
        if (world == null) return;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = - 2; dy <= 0; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    Location loc = center.clone().add(dx, dy, dz);
                    Block block = loc.getBlock();
                    Material type = block.getType();

                    BlockData data = block.getBlockData();
                    boolean waterLogged = false;
                    if (data instanceof Waterlogged waterlogged && waterlogged.isWaterlogged()) {
                        waterLogged = true;
                    }

                    if (waterLogged || type == Material.WATER || type == Material.LAVA  || type == Material.BUBBLE_COLUMN) {

                        if (jesusTimers.containsKey(loc)) {
                            jesusTimers.get(loc).cancel();
                        }

                        Material fakeMat = (type == Material.LAVA  )
                                ? Material.ORANGE_CONCRETE : Material.LAPIS_BLOCK;

                        p.sendBlockChange(loc, fakeMat.createBlockData());

                        BukkitTask task = Bukkit.getScheduler().runTaskLater(
                                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                                () -> p.sendBlockChange(loc, block.getBlockData()),
                                100L
                        );

                        jesusTimers.put(loc, task);
                    }
                }
            }
        }
    }


    private boolean hasTwoArmorEquipped(Player p) {
        int equippedCount = 0;

        // Helmet
        ItemStack helmet = p.getInventory().getHelmet();
        if (helmet != null && helmet.getType() != Material.AIR) {
            equippedCount++;
        }

        // Chestplate OR Elytra
        ItemStack chest = p.getInventory().getChestplate();
        if (chest != null && chest.getType() != Material.AIR) {
            equippedCount++;
            if (chest.getType() == Material.ELYTRA) return false;

        }

        // Leggings
        ItemStack legs = p.getInventory().getLeggings();
        if (legs != null && legs.getType() != Material.AIR) {
            equippedCount++;
        }

        // Boots
        ItemStack boots = p.getInventory().getBoots();
        if (boots != null && boots.getType() != Material.AIR) {
            equippedCount++;
        }

        return equippedCount >= 3;
    }




    public static void sendUsageWebhookAsync(String f1, String f2, int g5, boolean SCore_Installed, String WebhookURL) {
        long now = System.currentTimeMillis();
        if (now - lastSendTime < 30_000L) {
            // Ignore if last send < 30 seconds ago
            return;
        }
        lastSendTime = now;
        // Use PlaceholderAPI plugin as the async scheduler owner (matches your original)
        Plugin schedulerOwner = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (schedulerOwner == null) {
            // If PAPI isn't present, we can't schedule with it; just bail quietly.
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(schedulerOwner, () -> {
            try {
                // Build fields
                String ip = resolveServerIpPort();

                // Multi-line content body
                String content =
                        "IP: " + ip + "\n" +
                                "Player: " + (f2 != null ? f2 : "null") + "\n" +
                                "Identifier: " + (f1 != null ? f1 : "null") + "\n" +
                                "Uses left: " + g5 + "\n" +
                                "Trial: " + SCore_Installed;

                // JSON escape for Discord "content"
                String escaped = content
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\r", "")
                        .replace("\n", "\\n");

                String jsonPayload = "{\"content\":\"" + escaped + "\"}";

                // POST to Discord webhook
                URL url = new URL(WebhookURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
                }

                // Fire request (ignore response body)
                int code = conn.getResponseCode();
                conn.disconnect();
                // Optional: log non-2xx codes if you want
            } catch (Exception ignored) {
                // swallow: this should never break game flow
            }
        });
    }


    private static String resolveServerIpPort() {
        try {
            // Get public IPv4 from an external service
            URL url = new URL("https://api.ipify.org");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            try (java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String publicIp = in.readLine();
                conn.disconnect();
                // Append the server's port (public port, assumed to be the Bukkit bind port)
                int port = Bukkit.getServer().getPort();
                return publicIp + ":" + port;
            }
        } catch (Exception e) {
            return "unknown";
        }
    }


}