package org.example;


import org.bukkit.block.data.*;
import org.bukkit.plugin.PluginManager;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Comparator;
import java.util.List;


import java.nio.file.Files;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "CallToPrintStackTrace", "TextBlockMigration", "unused"})
public class ExampleExpansion extends PlaceholderExpansion {




    private final Map<UUID, BukkitTask> invisTimers = new HashMap<>();
    private final Map<Location, BukkitTask> jesusTimers = new HashMap<>();

    private static final boolean viewChestDebugLogging = false;



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



        if (identifier.startsWith("vertigoHallucination_")) {
            String[] parts = identifier.substring("vertigoHallucination_".length()).split(",");
            if (parts.length != 3) return "§cInvalid format";

            int radius1 = Integer.parseInt(parts[0]);
            int radius2 = Integer.parseInt(parts[1]);
            int durationTicks = Integer.parseInt(parts[2]);

            Location origin = p.getLocation();
            World world = p.getWorld();

            for (Player target : world.getPlayers()) {
                if (p.equals(target) || target.getLocation().distanceSquared(origin) > radius1 * radius1) continue;

                for (int dx = -radius2; dx <= radius2; dx++) {
                    for (int dy = -radius2; dy <= radius2; dy++) {
                        for (int dz = -radius2; dz <= radius2; dz++) {
                            Location loc = origin.clone().add(dx, dy, dz);
                            if (loc.getBlock().getType() == Material.AIR) continue;

                            target.sendBlockChange(loc, Material.AIR.createBlockData());

                            Bukkit.getScheduler().runTaskLater(
                                    Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                                    () -> target.sendBlockChange(loc, loc.getBlock().getBlockData()),
                                    durationTicks
                            );
                        }
                    }
                }
            }
            return "§8Hallucination triggered";
        }


        if (identifier.equals("invis")) {
            UUID uuid = p.getUniqueId();

            // Cancel existing timer if any
            if (invisTimers.containsKey(uuid)) {
                invisTimers.get(uuid).cancel();
            }

            // Hide from all players in world
            for (Player other : p.getWorld().getPlayers()) {
                if (!other.equals(p)) {
                    other.hidePlayer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), p);
                }
            }

            // Schedule re-show after 5 seconds (100 ticks)
            BukkitTask task = Bukkit.getScheduler().runTaskLater(
                    Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                    () -> {
                        for (Player other : p.getWorld().getPlayers()) {
                            if (!other.equals(p)) {
                                other.showPlayer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), p);
                            }
                        }
                        invisTimers.remove(uuid);
                    },
                    100L
            );

            invisTimers.put(uuid, task);
            return "§7Now invisible to others for 5s";
        }

        if (identifier.startsWith("JESUS_")) {
            try {
                int radius = Integer.parseInt(identifier.substring("JESUS_".length()));
                applyJesusEffect(p, radius);
                return "§bWalking on water (" + radius + " block radius)";
            } catch (Exception e) {
                return "§cInvalid radius";
            }
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


        return null;

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

    
}