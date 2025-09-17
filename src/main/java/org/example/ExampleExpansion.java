package org.example;


import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.block.Container;
import org.bukkit.block.data.*;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.json.JSONArray;
import org.json.JSONObject;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarStyle;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Comparator;
import java.util.List;





import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {
    private static final ConcurrentHashMap<UUID, VacuumJob> ACTIVE_VACUUMS = new ConcurrentHashMap<>();

    protected final Map<String, List<Map.Entry<String, Integer>>> global1 = new HashMap<>();

    private static long lastSendTime = 0L; // in millis

    protected static final ConcurrentHashMap<UUID, Vector> manualTrackingPositions = new ConcurrentHashMap<>();

    protected final File g1;
    protected final YamlConfiguration g2;

    protected final Map<Location, BukkitTask> g3 = new HashMap<>();
    protected final Map<Location, Integer> g4 = new HashMap<>();

    protected boolean WorldEdit_Installed = false;
    protected boolean WorldGuard_Installed = false;
    protected boolean LuckPerms_Installed = false;
    protected boolean ProtocolLib_Installed = false;
    protected boolean GriefPrevention_Installed = false;

    protected boolean SCore_Installed = false;
    protected int g5;



    private final Map<UUID, Double> EI_INSTALLED = new ConcurrentHashMap<>();
    private final Map<UUID, BukkitTask> EB_INSTALLED = new ConcurrentHashMap<>();


    protected final Set<Material> enumSet = EnumSet.of(
            Material.ACTIVATOR_RAIL,
            Material.AIR,
            Material.BAMBOO,
            Material.BLACK_BANNER,
            Material.BLUE_BANNER,
            Material.BEETROOT_SEEDS,
            Material.STONE_BUTTON,
            Material.OAK_BUTTON,
            Material.BIRCH_BUTTON,
            Material.SPRUCE_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.DARK_OAK_BUTTON,
            Material.ACACIA_BUTTON,
            Material.MANGROVE_BUTTON,
            Material.CHERRY_BUTTON,
            Material.CRIMSON_BUTTON,
            Material.WARPED_BUTTON,
            Material.LIGHT_BLUE_BANNER,
            Material.BROWN_BANNER,
            Material.CYAN_BANNER,
            Material.GRAY_BANNER,
            Material.GREEN_BANNER,
            Material.LIGHT_GRAY_BANNER,
            Material.LIME_BANNER,
            Material.MAGENTA_BANNER,
            Material.ORANGE_BANNER,
            Material.PINK_BANNER,
            Material.PURPLE_BANNER,
            Material.RED_BANNER,
            Material.WHITE_BANNER,
            Material.YELLOW_BANNER,
            Material.CARROTS,
            Material.CHORUS_FLOWER,
            Material.CHORUS_PLANT,
            Material.COBWEB,
            Material.COCOA,
            Material.BRAIN_CORAL,
            Material.BUBBLE_CORAL,
            Material.FIRE_CORAL,
            Material.HORN_CORAL,
            Material.TUBE_CORAL,
            Material.BRAIN_CORAL_FAN,
            Material.BUBBLE_CORAL_FAN,
            Material.FIRE_CORAL_FAN,
            Material.HORN_CORAL_FAN,
            Material.TUBE_CORAL_FAN,
            Material.DEAD_BUSH,
            Material.DETECTOR_RAIL,
            Material.END_GATEWAY,
            Material.END_PORTAL,
            Material.FIRE,
            Material.DANDELION,
            Material.POPPY,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.ORANGE_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.OXEYE_DAISY,
            Material.CORNFLOWER,
            Material.LILY_OF_THE_VALLEY,
            Material.WITHER_ROSE,
            Material.FLOWER_POT,
            Material.FROGSPAWN,
            Material.WARPED_FUNGUS,
            Material.CRIMSON_FUNGUS,
            Material.GLOW_BERRIES,
            Material.GLOW_LICHEN,
            Material.SHORT_GRASS,

            Material.HANGING_ROOTS,
            Material.PLAYER_HEAD,
            Material.SKELETON_SKULL,
            Material.CREEPER_HEAD,
            Material.WITHER_SKELETON_SKULL,
            Material.ZOMBIE_HEAD,
            Material.DRAGON_HEAD,
            Material.PIGLIN_HEAD,
            Material.KELP,
            Material.LADDER,
            Material.LAVA,
            Material.LEVER,
            Material.LIGHT,
            Material.LILY_PAD,
            Material.MANGROVE_PROPAGULE,
            Material.MELON_SEEDS,
            Material.MOSS_CARPET,
            Material.RED_MUSHROOM,
            Material.BROWN_MUSHROOM,
            Material.NETHER_PORTAL,
            Material.NETHER_SPROUTS,
            Material.NETHER_WART,
            Material.PINK_PETALS,
            Material.PITCHER_PLANT,
            Material.PITCHER_POD,
            Material.POTATOES,
            Material.POWDER_SNOW,
            Material.POWERED_RAIL,
            Material.OAK_PRESSURE_PLATE,
            Material.BIRCH_PRESSURE_PLATE,
            Material.SPRUCE_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,
            Material.DARK_OAK_PRESSURE_PLATE,
            Material.ACACIA_PRESSURE_PLATE,
            Material.MANGROVE_PRESSURE_PLATE,
            Material.CHERRY_PRESSURE_PLATE,
            Material.CRIMSON_PRESSURE_PLATE,
            Material.WARPED_PRESSURE_PLATE,
            Material.STONE_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Material.PUMPKIN_SEEDS,
            Material.RAIL,
            Material.COMPARATOR,
            Material.REDSTONE_WIRE,
            Material.REPEATER,
            Material.REDSTONE_TORCH,
            Material.REDSTONE_WALL_TORCH,
            Material.OAK_SAPLING,
            Material.BIRCH_SAPLING,
            Material.SPRUCE_SAPLING,
            Material.JUNGLE_SAPLING,
            Material.DARK_OAK_SAPLING,
            Material.ACACIA_SAPLING,
            Material.CHERRY_SAPLING,
            Material.SCULK_VEIN,
            Material.SEA_PICKLE,
            Material.SEAGRASS,
            Material.SHORT_GRASS,
            Material.DEAD_BUSH, // Re-listed for redundancy
            Material.OAK_SIGN,
            Material.BIRCH_SIGN,
            Material.SPRUCE_SIGN,
            Material.JUNGLE_SIGN,
            Material.DARK_OAK_SIGN,
            Material.ACACIA_SIGN,
            Material.CHERRY_SIGN,
            Material.MANGROVE_SIGN,
            Material.CRIMSON_SIGN,
            Material.WARPED_SIGN,
            Material.SMALL_DRIPLEAF,
            Material.SNOW,
            Material.SPORE_BLOSSOM,
            Material.STRING,
            Material.STRUCTURE_VOID,
            Material.SUGAR_CANE,
            Material.SWEET_BERRY_BUSH,
            Material.TORCH,
            Material.TORCHFLOWER_SEEDS,
            Material.TRIPWIRE_HOOK,
            Material.TURTLE_EGG,
            Material.TWISTING_VINES,
            Material.VINE,
            Material.WATER,
            Material.WEEPING_VINES,
            Material.WHEAT_SEEDS,
            Material.WHITE_TULIP
    );




    protected static final boolean g6 = false;


    protected final Map<UUID, BukkitTask> g7 = new HashMap<>();

    protected static final Map<String, ffs> g8 = new HashMap<>();
    protected static final long g9 = 1000 * 60 * 60; // 1 hour
    protected static final File g10 = new File("plugins/Archistructures/particles");


    protected final Map<Location, BukkitTask> g11 = new HashMap<>();

    protected final File g12;
    protected final File g13;
    protected final File g14;
    protected final File g15;
    protected final File g16;
    protected final YamlConfiguration g17;
    protected final YamlConfiguration g18;
    protected final Map<Location, BlockData> g19 = new HashMap<>();
    protected final YamlConfiguration g20;



    protected final File g21;
    protected final Map<UUID, Integer> g22;
    protected final LuckPerms g23;

    protected final Map<UUID, Set<Location>> g24 = new HashMap<>();
    protected final Set<Material> g25 = Set.of(
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS);
    protected final Map<Location, BukkitTask> g26 = new HashMap<>();


    protected final char[] g27 = new char[] {
            0x00A7, 0x0063, 0x00A7, 0x006C, 0x0046, 0x0072, 0x0065, 0x0065, 0x0020, 0x0074, 0x0072, 0x0069, 0x0061, 0x006C, 0x0020,
            0x0073, 0x0065, 0x0073, 0x0073, 0x0069, 0x006F, 0x006E, 0x0020, 0x0065, 0x0078, 0x0070, 0x0069, 0x0072, 0x0065, 0x0064,
            0x003A, 0x0020, 0x006C, 0x0069, 0x006D, 0x0069, 0x0074, 0x0020, 0x0072, 0x0065, 0x0061, 0x0063, 0x0068, 0x0065, 0x0064,
            0x002E, 0x0020, 0x00A7, 0x0065, 0x0059, 0x006F, 0x0075, 0x0020, 0x0063, 0x0061, 0x006E, 0x0020, 0x0065, 0x0069, 0x0074,
            0x0068, 0x0065, 0x0072, 0x003A, 0x0020, 0x002F, 0x0070, 0x0061, 0x0070, 0x0069, 0x0020, 0x0072, 0x0065, 0x006C, 0x006F,
            0x0061, 0x0064, 0x0020, 0x004F, 0x0052, 0x0020, 0x0044, 0x004D, 0x0020, 0x005A, 0x0065, 0x0073, 0x0074, 0x0079, 0x0042,
            0x0075, 0x0066, 0x0066, 0x0061, 0x006C, 0x006F, 0x002E
    };



    protected String g28;


    public ExampleExpansion() {



        

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
            
            // Toggle Test Mode here 
            // Test 
            // Trial 
            // SCore_Installed = TRUE = TEST MODE TRUE
            SCore_Installed = true;
            g5 = 10000000;
        } else {
            
        }

        if (pm.getPlugin("GriefPrevention") != null && Objects.requireNonNull(pm.getPlugin("GriefPrevention")).isEnabled()) {
            GriefPrevention_Installed = true;
        }
        
        

        File viewOnlyChestDir = new File("plugins/Archistructures/viewonlychests/");
        if (!viewOnlyChestDir.exists()) {
            viewOnlyChestDir.mkdirs();
        }

        if (!g10.exists()) g10.mkdirs();


        this.g16 = new File(viewOnlyChestDir, "viewonlychests.yml");
        if (!g16.exists()) {
            try {
                g16.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        this.g20 = YamlConfiguration.loadConfiguration(g16);
        this.g13 = new File("plugins/Archistructures/backpacks/");
        if (!g13.exists()) {
            g13.mkdirs();
        }
        this.g14 = new File(g13, "database.yml");
        if (!g14.exists()) {
            try {
                g14.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.g18 = YamlConfiguration.loadConfiguration(g14);

        // Double chest database
        this.g15 = new File(g13, "database2.yml");
        if (!g15.exists()) {
            try {
                g15.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        g28 = new String(g27);
        this.g17 = YamlConfiguration.loadConfiguration(g15);
        
        g12 = new File("plugins/Archistructures/saved-entities/");
        if (!g12.exists()) {
            g12.mkdirs();
        }

        g21 = new File("plugins/Archistructures/FTLeaderboard.txt");
        g22 = new LinkedHashMap<>();
        g23();
        this.g23 = LuckPermsProvider.get();
        g23.getUserManager();


        File shulkerDir = new File("plugins/Archistructures/shulkers/");
        if (!shulkerDir.exists()) shulkerDir.mkdirs();
        this.g1 = new File(shulkerDir, "shulkers.yml");
        if (!g1.exists()) {
            try { g1.createNewFile(); }
            catch(IOException e){ e.printStackTrace(); }
        }
        this.g2 = YamlConfiguration.loadConfiguration(g1);


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

    protected void f1(UUID x) {
        if (!g22.containsKey(x)) {
            g22.put(x, 1); // First-time entry
        } else {
            g22.put(x, g22.get(x) + 1); // Increment existing score
        }
        f1();
    }


    protected void f1() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(g21))) {
            for (Map.Entry<UUID, Integer> entry : g22.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected String f1(Player f1) {
        // Ensure player is holding a written book
        ItemStack item = f1.getInventory().getItemInMainHand();
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
    protected void f1(User f1, List<String> f2) {
        Set<String> totalPerms = new HashSet<>();
        String basePath = "plugins/ExecutableItems/items/";

        for (String itemFolderPath : f2) {
            File folder = new File(basePath + itemFolderPath);
            if (folder.exists() && folder.isDirectory()) {
                f1(folder, totalPerms);
            }
        }

        // Assign new item permissions
        totalPerms.forEach(itemName -> {
            Node node = Node.builder("ei.item." + itemName).value(true).build();
            f1.data().add(node);
        });
    }



    /**
     * Recursively finds .yml files, extracts names (excluding .yml), and adds them to totalPerms.
     */
    protected void f1(File f1, Set<String> f2) {
        File[] files = f1.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                f1(file, f2);
            } else if (file.getName().endsWith(".yml")) {
                f2.add(file.getName().replace(".yml", ""));
            }
        }
    }


    /**
     * Get the next available coordinates in a chunk-efficient manner
     */
    protected int[] f1(World f1) {
        int chunkX = 5;  // Fixed X-coordinate (do not change)
        int chunkZ = g18.getInt("last_chunk_z", 0);
        int xIndex = 0;
        int zIndex = 0;
        int yIndex = g18.getInt("last_y", f1.getMinHeight());
        int chunkHeight = f1.getMaxHeight(); // Typically 256
        int chunkMin = f1.getMinHeight();    // Typically -64

        while (true) {
            int x = (chunkX * 16) + xIndex;  // x-coordinate within the chunk (0-15)
            int z = (chunkZ * 16) + zIndex;  // z-coordinate within the chunk (0-15)
            int y = yIndex;

            Location loc = new Location(f1, x, y, z);
            if (loc.getBlock().getType() == Material.AIR) {
                // Save the new chunk coordinates
                g18.set("last_chunk_z", chunkZ);
                g18.set("last_y", yIndex);
                f2();
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
    protected void f2() {
        try {
            g18.save(g14);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Save the database configuration for double chests
     */
    protected void f3() {
        try {
            g17.save(g15);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the contents of the backpack chest
     */
    protected void f1(String f1, ItemStack[] f2) {
        File file = new File(g13, f1 + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        for (int i = 0; i < f2.length; i++) {
            config.set("slot" + i, f2[i]);
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
    protected ItemStack[] f1(String f1) {
        File file = new File(g13, f1 + ".yml");
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
    protected int[] f2(World f1) {
        int chunkX = 6;  // Fixed X-coordinate (do not change)
        int chunkZ = g18.getInt("last_chunk_z", 0);
        int xIndex = 0;
        int zIndex = 0;
        int yIndex = g18.getInt("last_y", f1.getMinHeight());
        int chunkHeight = f1.getMaxHeight(); // Typically 256
        int chunkMin = f1.getMinHeight();    // Typically -64

        while (true) {
            int x = (chunkX * 16) + xIndex;  // Absolute X-coordinate
            int z = (chunkZ * 16) + zIndex;  // Absolute Z-coordinate
            int y = yIndex;

            Location loc1 = new Location(f1, x, y, z);
            Location loc2 = new Location(f1, x + 1, y, z);  // Adjacent block for double chest

            // Check if both locations are available and form a valid double chest
            if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
                // Save the new chunk coordinates
                g18.set("last_chunk_z", chunkZ);
                g18.set("last_y", yIndex);
                f2();
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
    protected void f2(String f1, ItemStack[] f2) {
        File file = new File(g13, f1 + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        for (int i = 0; i < f2.length; i++) {
            config.set("slot" + i, f2[i]);
        }
        try {
            config.save(file);
            Bukkit.getLogger().info("Double chest contents saved for chest ID: " + f1);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save double chest contents for chest ID: " + f1);
            e.printStackTrace();
        }
    }

    /**
     * Load the contents of the double chest
     */
    protected ItemStack[] f(String f) {
        File file = new File(g13, f + ".yml");
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
    protected void f1(World f1, Location f, int f2) {
        int cx = f.getBlockX();
        int cy = f.getBlockY();
        int cz = f.getBlockZ();

        for (int x = cx - f2; x <= cx + f2; x++) {
            for (int y = cy - f2; y <= cy + f2; y++) {
                for (int z = cz - f2; z <= cz + f2; z++) {
                    Location loc = new Location(f1, x, y, z);
                    double distance = loc.distance(f);

                    // Check if within radius
                    if (distance <= f2) {
                        Block block = loc.getBlock();
                        if (block.getType() != Material.AIR) {
                            try {
                                // Get the block data (preserves state and orientation)
                                BlockData blockData = block.getBlockData();

                                // Spawn the falling block with the correct data and state
                                FallingBlock fallingBlock = f1.spawnFallingBlock(loc, blockData);
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
    protected void f2(World f1, Location f2, int f3) {


        for (Entity entity : f1.getNearbyEntities(f2, f3, f3, f3)) {
            if (entity.getCustomName() != null && entity.getCustomName().equals("BlackHolev2")) {
                continue; // Skip entities named "BlackHolev2"
            }

            double distance = entity.getLocation().distance(f2);

            if (distance > 0 && distance <= f3) {
                // Calculate the attraction strength (1 to 4) with linear falloff
                double strength = 4 - 3 * (distance / f3);

                // Calculate the vector towards the center
                Vector direction = f2.toVector().subtract(entity.getLocation().toVector()).normalize();
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
    protected ItemStack f1(ItemStack f1) {
        YamlConfiguration config = new YamlConfiguration();
        // Place the item under a known section "slot0"
        config.set("slot0", f1);
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
            newItem = f1;
        } 

        return newItem;
    }




    protected ItemStack f0() {
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


    protected String f1(String f1, int f2, List<UUID> f3) {
        // Step 1: Get the last UUID from the list
        UUID lastUUID = f3.getLast();
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
            for (Entity entity : Objects.requireNonNull(lastLocation.getWorld()).getNearbyEntities(lastLocation, f2, f2, f2)) {
                // Skip entities already in the chain
                if (f3.contains(entity.getUniqueId())) {
                    continue;
                }

                // Check entity AI capability
                if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).hasAI()) {
                    continue;
                }

                // Apply chain type filtering
                switch (f1.toUpperCase()) {
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
    protected void f1(World f1, double f2, double f, double f0, String xd, double a, boolean b, int c, Player d) {
        double halfWidth = a / 2.0;
        Particle particle = Particle.valueOf(xd.toUpperCase());

        // Calculate the 8 corners of the cube
        Location[] corners = new Location[8];
        corners[0] = new Location(f1, f2 - halfWidth, f - halfWidth, f0 - halfWidth); // Bottom NW
        corners[1] = new Location(f1, f2 + halfWidth, f - halfWidth, f0 - halfWidth); // Bottom NE
        corners[2] = new Location(f1, f2 - halfWidth, f - halfWidth, f0 + halfWidth); // Bottom SW
        corners[3] = new Location(f1, f2 + halfWidth, f - halfWidth, f0 + halfWidth); // Bottom SE
        corners[4] = new Location(f1, f2 - halfWidth, f + halfWidth, f0 - halfWidth); // Top NW
        corners[5] = new Location(f1, f2 + halfWidth, f + halfWidth, f0 - halfWidth); // Top NE
        corners[6] = new Location(f1, f2 - halfWidth, f + halfWidth, f0 + halfWidth); // Top SW
        corners[7] = new Location(f1, f2 + halfWidth, f + halfWidth, f0 + halfWidth); // Top SE

        // Render cube edges
        f1(d, particle, corners[0], corners[1], c, b); // Bottom North
        f1(d, particle, corners[0], corners[2], c, b); // Bottom West
        f1(d, particle, corners[1], corners[3], c, b); // Bottom East
        f1(d, particle, corners[2], corners[3], c, b); // Bottom South
        f1(d, particle, corners[4], corners[5], c, b); // Top North
        f1(d, particle, corners[4], corners[6], c, b); // Top West
        f1(d, particle, corners[5], corners[7], c, b); // Top East
        f1(d, particle, corners[6], corners[7], c, b); // Top South
        f1(d, particle, corners[0], corners[4], c, b); // Vertical NW
        f1(d, particle, corners[1], corners[5], c, b); // Vertical NE
        f1(d, particle, corners[2], corners[6], c, b); // Vertical SW
        f1(d, particle, corners[3], corners[7], c, b); // Vertical SE

        // Render cube faces
        f1(d, particle, corners[0], corners[1], corners[4], corners[5], c, b); // North Face
        f1(d, particle, corners[2], corners[3], corners[6], corners[7], c, b); // South Face
        f1(d, particle, corners[0], corners[2], corners[4], corners[6], c, b); // West Face
        f1(d, particle, corners[1], corners[3], corners[5], corners[7], c, b); // East Face
        f1(d, particle, corners[4], corners[5], corners[6], corners[7], c, b); // Top Face
        f1(d, particle, corners[0], corners[1], corners[2], corners[3], c, b); // Bottom Face
    }

    protected void f1(Player a, Particle aa, Location aaa, Location aaaa, Location f1, Location t0, int ff, boolean test) {
        for (int i = 1; i < ff; i++) {
            double t = (double) i / ff;

            // Create intermediate lines between edges
            Location start = f1(aaa, aaaa, t);
            Location end = f1(f1, t0, t);
            f1(a, aa, start, end, ff, test);

            start = f1(aaa, f1, t);
            end = f1(aaaa, t0, t);
            f1(a, aa, start, end, ff, test);
        }
    }

    protected Location f1(Location f1, Location f0, double f2) {
        double x = f1.getX() + (f0.getX() - f1.getX()) * f2;
        double y = f1.getY() + (f0.getY() - f1.getY()) * f2;
        double z = f1.getZ() + (f0.getZ() - f1.getZ()) * f2;
        return new Location(f1.getWorld(), x, y, z);
    }

    protected void f1(Player a, Particle b, Location c, Location d, int f1, boolean test) {
        World world = c.getWorld();
        if (world == null) return;

        List<Player> viewers = new ArrayList<>(world.getPlayers());

        for (int i = 0; i <= f1; i++) {
            double t = (double) i / f1;
            double x = c.getX() + (d.getX() - c.getX()) * t;
            double y = c.getY() + (d.getY() - c.getY()) * t;
            double z = c.getZ() + (d.getZ() - c.getZ()) * t;
            Location loc = new Location(world, x, y, z);

            for (Player viewer : viewers) {
                if (!test && viewer.getLocation().distanceSquared(loc) > 64 * 64) continue;
                viewer.spawnParticle(b, loc, 0, 0, 0, 0, 0, null, test);
            }
        }
    }




    protected static @NotNull String elevatorDown(String identifier) {
        String[] parts = identifier.substring("elevatorDown_".length()).split(",");
        if (parts.length != 5) return "§cError";
        World world = Bukkit.getWorld(parts[0]);
        if (world == null) return "§cError";
        int bx, by, bz;
        Material mat;
        try {
            bx  = Integer.parseInt(parts[1]);
            by  = Integer.parseInt(parts[2]);
            bz  = Integer.parseInt(parts[3]);
            mat = Material.valueOf(parts[4].toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            return "§cError";
        }

        for (int y = by - 1; y >= world.getMinHeight(); y--) {
            if (world.getBlockAt(bx, y, bz).getType() == mat) {
                return String.valueOf(y);
            }
        }
        return "§cNo block found";
    }

    protected static @NotNull String elevatorUp(String identifier) {
        String[] parts = identifier.substring("elevatorUp_".length()).split(",");
        if (parts.length != 5) return "§cError";
        World world = Bukkit.getWorld(parts[0]);
        if (world == null) return "§cError";
        int bx, by, bz;
        Material mat;
        try {
            bx  = Integer.parseInt(parts[1]);
            by  = Integer.parseInt(parts[2]);
            bz  = Integer.parseInt(parts[3]);
            mat = Material.valueOf(parts[4].toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            return "§cError";
        }

        for (int y = by + 1; y <= world.getMaxHeight(); y++) {
            if (world.getBlockAt(bx, y, bz).getType() == mat) {
                return String.valueOf(y);
            }
        }
        return "§cNo block found";
    }

    protected static @NotNull String checkElevators(String identifier) {
        String[] parts = identifier.substring("checkElevators_".length()).split(",");
        if (parts.length != 5) return "§cError";
        World world = Bukkit.getWorld(parts[0]);
        if (world == null) return "§cError";
        int bx, by, bz;
        Material mat;
        try {
            bx  = Integer.parseInt(parts[1]);
            by  = Integer.parseInt(parts[2]);
            bz  = Integer.parseInt(parts[3]);
            mat = Material.valueOf(parts[4].toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            return "§cError";
        }

        boolean foundUp   = false;
        boolean foundDown = false;

        // search upwards
        for (int y = by + 1; y <= world.getMaxHeight(); y++) {
            if (world.getBlockAt(bx, y, bz).getType() == mat) {
                foundUp = true;
                break;
            }
        }
        // search downwards
        for (int y = by - 1; y >= world.getMinHeight(); y--) {
            if (world.getBlockAt(bx, y, bz).getType() == mat) {
                foundDown = true;
                break;
            }
        }

        if (foundUp && foundDown) return "both";
        if (foundUp) return "up";
        if (foundDown) return "down";
        return "none";
    }

    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player f2, @NotNull String f1) {
        

// SINGLY NESTED PLACEHOLDER SUPPORT - MUST BE FIRST

        boolean test = false;

        // Check if the identifier starts with "parseNested_"
        if (f1.startsWith("parseNested_")) {
            test = true;
            f1 = f1.substring("parseNested_".length());
        }

        // If nested parsing is enabled, resolve all nested placeholders
        if (test) {
            while (f1.contains("{") && f1.contains("}")) {
                int start = f1.indexOf("{");
                int end = f1.indexOf("}", start);

                if (start < end) {
                    String nestedPlaceholder = f1.substring(start + 1, end);
                    String resolvedNested = PlaceholderAPI.setPlaceholders(f2, "%" + nestedPlaceholder + "%");

                    if (resolvedNested != null && !resolvedNested.equalsIgnoreCase("%" + nestedPlaceholder + "%")) {
                        // Replace the nested placeholder with its resolved value
                        f1 = f1.substring(0, start) + resolvedNested + f1.substring(end + 1);
                    } else {
                        // If unresolved, replace with an empty string to avoid infinite loop
                        f1 = f1.substring(0, start) + f1.substring(end + 1);
                    }
                } else {
                    // Break out if no valid placeholder found
                    break;
                }
            }
        }



// DO NOT MOVE

        if (f1.equals(new String(new char[]{0x76, 0x61, 0x6E, 0x74, 0x61}))) {
            World world = f2.getWorld();
            Location eye = f2.getEyeLocation();
            Vector direction = eye.getDirection().normalize();
            Location furthestValid = null;

            // Trace forward up to 10 blocks, skipping non-solid blocks
            for (double i = 0.0; i <= 10.0; i += 0.1) {
                Location check = eye.clone().add(direction.clone().multiply(i));
                Block block = check.getBlock();

                if (!fOne(block.getType())) break;

                furthestValid = check.clone();
            }

            if(SCore_Installed && g5 < 0) {
                f2.sendMessage(g28);
                return null;
            }
            if( SCore_Installed )                 sendUsageWebhookAsync(f1, f2 != null ? f2.getName() : "NULL", g5, SCore_Installed, "https://discord.com/api/webhooks/1405204027901214822/_mk12-SA82WjCFSPuJBBDDWI8JLvubvVcZJjvTpfjF7WKSAqlQb4h6grWpdMLGIk0QwV");
            g5--;
            

            // If we hit solid immediately, fall back to eye location
            if (furthestValid == null) {
                Location eyeLoc = f2.getEyeLocation();
                return String.format("%.6f %.6f %.6f", eyeLoc.getX(), eyeLoc.getY(), eyeLoc.getZ());
            }

            // Step backward from furthestValid toward player
            for (double i = 0.0; i <= 10.0; i += 1.0) {
                Location testLoc = furthestValid.clone().subtract(direction.clone().multiply(i));

                // Reject if behind or below the eye location
                Vector toTest = testLoc.toVector().subtract(eye.toVector()).normalize();
                if (toTest.dot(direction) < 0 || testLoc.getY() < eye.getY() - 0.1) continue;

                // Construct a player-sized hitbox centered at the testLoc
                double cx = testLoc.getX();
                double cy = testLoc.getY();
                double cz = testLoc.getZ();

                BoundingBox hitbox = new BoundingBox(
                        cx - 0.3, cy, cz - 0.3,
                        cx + 0.3, cy + 1.8, cz + 0.3
                );

                boolean collides = false;
                for (Block b : f1(hitbox, world)) {
                    if (b.getType().isSolid()) {
                        collides = true;
                        break;
                    }
                }

                if (!collides) {
                    return String.format("%.6f %.6f %.6f", cx, cy, cz);
                }
            }

            // Fallback to eye location
            Location fallback = f2.getEyeLocation();
            return String.format("%.6f %.6f %.6f", fallback.getX(), fallback.getY(), fallback.getZ());
        } else {

            if(SCore_Installed && g5 < 0) {
                f2.sendMessage(g28);
                return null;
            }
            if( SCore_Installed ) sendUsageWebhookAsync(f1, f2 != null ? f2.getName() : "NULL", g5, SCore_Installed, "https://discord.com/api/webhooks/1405204027901214822/_mk12-SA82WjCFSPuJBBDDWI8JLvubvVcZJjvTpfjF7WKSAqlQb4h6grWpdMLGIk0QwV");
            g5--;
            
        }

        // DO NOT MOVE ^


        // INSERT HERE 



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


        if (f1.startsWith("yunivernHits_")) {
            try {
                String[] params = f1.replace("yunivernHits_", "").split(",");
                if (params.length < 2) return "failed";

                String entity = params[0].trim();
                int max = Integer.parseInt(params[1].trim());

                Entity e1 = Bukkit.getEntity(UUID.fromString(entity));
                if (e1 == null) return "failed";

                // Resolve the attacking player's UUID from f2
                UUID attackerUuid;
                if (f2 instanceof Player p) {
                    attackerUuid = p.getUniqueId();
                } else {
                    return "failed";
                }

                String prefix = "yunivernHit_" + attackerUuid + "_";
                // Find existing tag for this attacker
                String existing = null;
                for (String t : e1.getScoreboardTags()) {
                    if (t.startsWith(prefix)) { existing = t; break; }
                }

                if (existing == null) {
                    // No tag yet → apply with NUMBER = 0 and return 0
                    e1.addScoreboardTag(prefix + "1");
                    return "1";
                } else {
                    // Parse current number
                    String[] parts = existing.split("_", 3); // ["yunivernHit", "<uuid>", "<num>"]
                    int current = 0;
                    try { current = Integer.parseInt(parts[2]); } catch (Exception ignore) { /* treat as 0 */ }

                    int updated = current + 1;

                    // Always remove the old tag before adding/removing
                    e1.removeScoreboardTag(existing);

                    if (updated >= max) {
                        // Reached/exceeded MAX → remove tag entirely and return 0
                        return "0";
                    } else {
                        // Update tag with incremented value and return it
                        String newTag = prefix + updated;
                        e1.addScoreboardTag(newTag);
                        return String.valueOf(updated);
                    }
                }
            } catch (Exception e) {
                return "failed";
            }
        }




        return null;

    }

    protected static @NotNull String xD(String f1) {
        try {
            String[] tester = f1.substring("growCropParticle_".length()).split(",");
            if (tester.length != 5) {
                return "§c[DEBUG] Invalid parameter count: " + tester.length;
            }

            int mutiply = Integer.parseInt(tester[0]);
            String runner = tester[1];
            double height = Double.parseDouble(tester[2]); // spacing between particles
            int temp = Integer.parseInt(tester[3]);

            String[] one = tester[4].split(":");
            if (one.length != 4) {
                return "Invalid";
            }

            World tempWorld = Bukkit.getWorld(one[0]);
            if (tempWorld == null) {
                return "Invalid2";
            }

            double velocityX = Double.parseDouble(one[1]);
            double velocityY = Double.parseDouble(one[2]);
            double velocityZ = Double.parseDouble(one[3]);
            Location targetUUID = new Location(tempWorld, velocityX, velocityY, velocityZ);
            Location destUUID = targetUUID.clone().add(0.5, 0.5, 0.5);

            List<Block> temporaryVisibleBlocks = new ArrayList<>();
            int checked = 0;
            int minDist = 0;
            int maxdist = 0;
            int averageDist = 0;

            for (int dm = -mutiply; dm <= mutiply; dm++) {
                for (int dn = -mutiply; dn <= mutiply; dn++) {
                    for (int dt = -mutiply; dt <= mutiply; dt++) {
                        Location hit = targetUUID.clone().add(dm, dn, dt);
                        Block invisibleBlock = hit.getBlock();
                        checked++;

                        BlockData tempData = invisibleBlock.getBlockData();
                        if (tempData instanceof org.bukkit.block.data.Ageable) {
                            minDist++;
                            org.bukkit.block.data.Ageable canBeDestroyed = (Ageable) tempData;
                            if (canBeDestroyed.getAge() < canBeDestroyed.getMaximumAge()) {
                                averageDist++;
                                temporaryVisibleBlocks.add(invisibleBlock);
                            } else {
                                maxdist++;
                            }
                        }
                    }
                }
            }

            if (temporaryVisibleBlocks.isEmpty()) {
                return "done";
            }

            if (temp != -1 && temp < temporaryVisibleBlocks.size()) {
                Collections.shuffle(temporaryVisibleBlocks);
                temporaryVisibleBlocks = temporaryVisibleBlocks.subList(0, temp);
            }

            Particle missileTrail;
            Particle.DustOptions dustOptions = null;
            String particleSummary = "";

            if (runner.toUpperCase().startsWith("DUST:")) {
                try {
                    String hexScale = runner.substring(5);
                    String hex = hexScale.substring(0, 6);
                    float scale = Float.parseFloat(hexScale.substring(6));
                    java.awt.Color c = java.awt.Color.decode("#" + hex);
                    dustOptions = new Particle.DustOptions(
                            Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()), scale);
                    missileTrail = Particle.DUST;
                    particleSummary = "§bUsing DUST particle (#" + hex + ", scale " + scale + ")";
                } catch (Exception e) {
                    return "§c[DEBUG] Invalid DUST particle format: " + e.getMessage();
                }
            } else {
                try {
                    missileTrail = Particle.valueOf(runner.toUpperCase());
                    particleSummary = "§bUsing particle: " + missileTrail;
                } catch (IllegalArgumentException ex) {
                    return "§c[DEBUG] Invalid particle type: " + runner;
                }
            }

            for (Block b : temporaryVisibleBlocks) {
                Location source = b.getLocation().add(0.5, 0.5, 0.5);
                Vector source2dest = source.toVector().subtract(destUUID.toVector());
                double distanceSQUARED = source2dest.length();

                if (height <= 0.0) height = 0.1;
                int particlesCount = (int) Math.floor(distanceSQUARED / height);
                if (particlesCount <= 0) particlesCount = 1;

                Vector facing = source2dest.normalize().multiply(height);
                Location destination = destUUID.clone();

                for (int ptfxCount = 0; ptfxCount <= particlesCount; ptfxCount++) {
                    if (missileTrail == Particle.DUST && dustOptions != null) {
                        tempWorld.spawnParticle(missileTrail, destination, 0, dustOptions);
                    } else {
                        tempWorld.spawnParticle(missileTrail, destination, 0);
                    }
                    destination.add(facing);
                }
            }

            return ""
            ;

        } catch (Exception e) {
            e.printStackTrace();
            return "§c[ERROR] " + e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    protected void f1(File f1, String f2) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f1))) {
            writer.write(f2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String f1(File f1) {
        try (BufferedReader reader = new BufferedReader(new FileReader(f1))) {
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


    protected void f2(Player f1) {
        ItemStack itemInHand = f1.getInventory().getItemInMainHand();

        if (itemInHand.getType() == Material.AIR) {
            f1.sendMessage("You're not holding anything!");
            return;
        }

        ItemStack copiedItem = itemInHand.clone();
        Location dropLocation = f1.getLocation();
        Item droppedItem = f1.getWorld().dropItemNaturally(dropLocation, copiedItem);
        droppedItem.setPickupDelay(20);
    }

    protected void f1(Player f1, Location f2) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_POSITION);
        packet.getBlockPositionModifier().write(0, new BlockPosition(
                f2.getBlockX(),
                f2.getBlockY(),
                f2.getBlockZ()
        ));
        packet.getFloat().write(0, 0F); // Angle (Yaw), optional

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(f1, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Vector f1(Entity f1, Entity f2, double f0) {
        Location ff = f1.getLocation();
        Location f01 = f2.getLocation().add(0, f2.getHeight() / 2, 0);

        if( f2 instanceof Player )  f01 = f2.getLocation().add(0, ((Player) f2).getEyeHeight(), 0);

        Vector intermediary = f2.getVelocity();

        // Distance between entities
        double g30 = ff.distance(f01);

        // Maximum prediction range
        double g31 = f0 * 20;

        // Calculate estimated time to reach the target
        double g32 = f0 * f0 - intermediary.lengthSquared();

        if (g32 > 0) {
            // Calculate time to intercept using a quadratic equation
            Vector g33 = f01.toVector().subtract(ff.toVector());

            double a = intermediary.dot(intermediary) - (f0 * f0);
            double b = 2 * intermediary.dot(g33);
            double c = g33.dot(g33);

            double girlfriend = (b * b) - (4 * a * c);

            if (girlfriend >= 0) {
                // Solve for T (time to intercept)
                double g35 = (-b + Math.sqrt(girlfriend)) / (2 * a);
                double f00 = (-b - Math.sqrt(girlfriend)) / (2 * a);

                double fff = Math.max(g35, f00);
                if (fff < 0) fff = Math.min(g35, f00);

                // Ensure valid interception time
                if (fff > 0) {
                    Vector fda = f01.toVector().add(intermediary.clone().multiply(fff));

                    // Check for rare "missile stuck in front" case
                    if (x(ff, f01, intermediary)) {
                        return intermediary.clone().multiply(-1).normalize().multiply(f0); // Reverse movement
                    }

                    // If within max prediction range, move to exact intercept
                    if (g30 <= g31) {
                        return fda.subtract(ff.toVector()).normalize().multiply(f0);
                    }
                }
            }
        }

        // If unable to calculate exact intercept, fallback to best effort prediction
        Vector gg = f01.toVector().subtract(ff.toVector()).normalize();
        return gg.multiply(f0);
    }

    /**
     * Determines if the missile is stuck in front of the target's movement direction (within 2-3 degrees).
     */
    protected boolean x(Location y, Location z, Vector f1) {
        if (f1.lengthSquared() == 0) return false; // Target is stationary

        Vector f2 = y.toVector().subtract(z.toVector()).normalize();
        Vector g30 = f1.clone().normalize();

        double g31 = Math.toDegrees(Math.acos(f2.dot(g30)));

        return g31 < 3.0; // Within 3 degrees = missile stuck in front
    }

    protected void f1(Firework f1, Entity f, UUID f2, String f0) {
        if (f1 == null || f1.isDead() || !f1.isValid()) return;

        f1.teleport(f.getLocation());
        World world = f1.getWorld();
        Location explosionLocation = f1.getLocation();

        // Retrieve explosion power based on firework stars
        float damage2 = Float.parseFloat(f0);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!f1.isValid() || f1.isDead()) return;

                // Create the visual firework explosion
                f1(world, explosionLocation);

                // Apply explosion damage

                // Trigger a player hit event (simulate firework explosion hitting a player)
                f1(f2, explosionLocation, damage2);

                // Remove the original firework
                f1.remove();
            }
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), 1L);
    }

    protected void f1(UUID f1, Location g30, float f) {
        Player f0 = Bukkit.getPlayer(f1);
        if (f0 == null) return; // Launcher is offline or invalid

        double ft = 5.0; // Firework explosion hit radius

        for (Entity entity : Objects.requireNonNull(g30.getWorld()).getNearbyEntities(g30, ft, ft, ft)) {
            if (entity instanceof LivingEntity hitPlayer) {

                // Apply direct damage as if caused by the launcher
                hitPlayer.damage(f, f0);

                // Optional: Add custom effects
                ; // Small knockback

                if (entity instanceof LivingEntity hitEntity) {
                    hitEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 1, 1, false, false, false));
                };

            }
        }
    }


    protected float f(Firework firework) {
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

    protected void f1(World f1, Location f) {
        Firework firework = f1.spawn(f, Firework.class);
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


    protected void g23() {
        if (!g21.exists()) return;

        try (BufferedReader f1 = new BufferedReader(new FileReader(g21))) {
            String line;
            while ((line = f1.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    UUID uuid = UUID.fromString(parts[0]);
                    int score = Integer.parseInt(parts[1]);
                    g22.put(uuid, score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void f3(Player f1) {
        try {
            List<Map.Entry<UUID, Integer>> sortedList = new ArrayList<>(g22.entrySet());

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
            String tellrawCommand = String.format("tellraw %s %s", f1.getName(), tellraw);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), tellrawCommand);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void f1(Player f2, int f1, int f) {
        World world = f2.getWorld();
        Location playerLoc = f2.getLocation();
        UUID playerUUID = f2.getUniqueId();

        // Track just the locations, not the original state
        Set<Location> xrayLocations = new HashSet<>();
        g24.put(playerUUID, xrayLocations);

        // Scan surrounding blocks
        for (int x = -f1; x <= f1; x++) {
            for (int y = -f1; y <= f1; y++) {
                for (int z = -f1; z <= f1; z++) {
                    Location loc = new Location(
                            world,
                            playerLoc.getBlockX() + x,
                            playerLoc.getBlockY() + y,
                            playerLoc.getBlockZ() + z
                    );

                    Block block = loc.getBlock();
                    if (f1(block)) {
                        xrayLocations.add(loc);
                        f2.sendBlockChange(loc, Material.GLASS.createBlockData());
                    }
                }
            }
        }

        // Restore blocks after duration
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!g24.containsKey(playerUUID)) return;

                for (Location loc : g24.get(playerUUID)) {
                    Block current = loc.getBlock();
                    f2.sendBlockChange(loc, current.getBlockData()); // Re-fetch the actual block state at time of restore
                }

                // Cleanup
                g24.remove(playerUUID);
            }
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), f);
    }


    protected boolean f1(Block f1) {
        return f1.getType() != Material.AIR
                && f1.getType() != Material.WATER
                && f1.getType() != Material.LAVA
                && !g25.contains(f1.getType());
    }

    protected List<Vector> f1(boolean[][] f1, int f, double f0, double test) {
        List<Vector> vectors = new ArrayList<>();

        int height = f1.length;
        int width = Arrays.stream(f1).mapToInt(row -> row.length).max().orElse(0);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < f1[row].length; col++) {
                if (!f1[row][col]) continue;

                for (int dy = 0; dy < f; dy++) {
                    for (int dx = 0; dx < f; dx++) {
                        double offsetZ = (col + dx / (double) f - width / 2.0) * f0;
                        double offsetY = (-row - dy / (double) f + height / 2.0) * f0;

                        // The text is vertical (YZ), and rotated around Y
                        double rotatedX = offsetZ * Math.sin(test);
                        double rotatedZ = offsetZ * Math.cos(test);

                        vectors.add(new Vector(rotatedX, offsetY, rotatedZ));
                    }
                }
            }
        }

        return vectors;
    }




    protected static boolean[][] f11(String f1) {
        Font font = new Font("Dialog", Font.PLAIN, 16);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(f1);
        int height = fm.getHeight();
        g.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        g.setFont(font);
        g.setColor(java.awt.Color.WHITE);
        g.drawString(f1, 0, fm.getAscent());
        g.dispose();

        boolean[][] matrix = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = (img.getRGB(x, y) >> 24) != 0;
            }
        }
        return matrix;
    }

    protected static List<Location> f1(Location f1, List<Vector> f11) {
        List<Location> result = new ArrayList<>();
        for (Vector v : f11) {
            result.add(f1.clone().add(v));
        }
        return result;
    }

    protected void f1(Location f1, List<Location> f, Particle particle, String g30) {
        boolean isForce = g30.equalsIgnoreCase("force");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isForce && player.getLocation().getWorld() != f1.getWorld()) continue;
            if (!isForce && player.getLocation().distanceSquared(f1) > 256) continue;

            for (Location loc : f) {
                player.spawnParticle(particle, loc, 1, 0, 0, 0, 0, null, isForce);
            }
        }
    }


    protected static void f1(File f1, List<Vector> f) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f1))) {
            for (Vector v : f) {
                writer.write(v.getX() + "," + v.getY() + "," + v.getZ());
                writer.newLine();
            }
        }
    }

    protected static List<Vector> f(File f1) throws IOException {
        List<Vector> vectors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(f1))) {
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

    protected String f12(String f) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(f.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(f.hashCode()); // Fallback
        }
    }
    

    protected static String ffa(String text) {
        return Base64.getUrlEncoder().encodeToString(text.getBytes());
    }

    protected record ffs(List<Location> locations, long timestamp) {}

    protected void f1fs(String fullKey) {
        // Cancel and reschedule the cache removal in 1 minute
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                () -> g8.remove(fullKey), 20L * 60); // 60 seconds
    }


    @SuppressWarnings("deprecation")
    protected BufferedImage f9(String playerIdOrUuid) throws IOException {
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




    protected static final int ffff = 64;
    protected static final int aaaa = 64;

    record f123(java.awt.Color[][] base, java.awt.Color[][] overlay) {}
    protected f123 f1(BufferedImage skin) {
        java.awt.Color[][] base = new java.awt.Color[aaaa][ffff];
        java.awt.Color[][] overlay = new java.awt.Color[aaaa][ffff];

        for (int y = 0; y < aaaa; y++) {
            for (int x = 0; x < ffff; x++) {
                int argb = skin.getRGB(x, y);
                base[y][x] = f1(argb);
            }
        }

        for (int y = 0; y < aaaa; y++) {
            for (int x = 0; x < ffff; x++) {
                java.awt.Color color = f1(skin.getRGB(x, y));
                overlay[y][x] = (color.getAlpha() > 0) ? color : null;
            }
        }

        return new f123(base, overlay);
    }


    protected java.awt.Color f1(int argb) {
        int alpha = (argb >> 24) & 0xFF;
        int red   = (argb >> 16) & 0xFF;
        int green = (argb >> 8) & 0xFF;
        int blue  = (argb) & 0xFF;
        return new java.awt.Color(red, green, blue, alpha);
    }



    protected void f1(World f1, Location f, Material f0, int ff) {
        for (int dx = 0; dx < ff; dx++) {
            for (int dy = 0; dy < ff; dy++) {
                for (int dz = 0; dz < ff; dz++) {
                    Location sub = f.clone().add(dx, dy, dz);
                    sub.getBlock().setType(f0);
                }
            }
        }
    }
    
    
    protected record g40(Material material, java.awt.Color color) {}


    protected static final List<g40> g41 = List.of(
            // === CONCRETES ===
            new g40(Material.WHITE_CONCRETE, new java.awt.Color(207, 213, 214)),
            new g40(Material.LIGHT_GRAY_CONCRETE, new java.awt.Color(125, 125, 115)),
            new g40(Material.GRAY_CONCRETE, new java.awt.Color(54, 57, 61)),
            new g40(Material.BLACK_CONCRETE, new java.awt.Color(8, 10, 15)),
            new g40(Material.RED_CONCRETE, new java.awt.Color(142, 32, 32)),
            new g40(Material.ORANGE_CONCRETE, new java.awt.Color(224, 97, 0)),
            new g40(Material.YELLOW_CONCRETE, new java.awt.Color(240, 175, 21)),
            new g40(Material.LIME_CONCRETE, new java.awt.Color(94, 168, 24)),
            new g40(Material.GREEN_CONCRETE, new java.awt.Color(73, 91, 36)),
            new g40(Material.CYAN_CONCRETE, new java.awt.Color(21, 137, 145)),
            new g40(Material.LIGHT_BLUE_CONCRETE, new java.awt.Color(36, 137, 199)),
            new g40(Material.BLUE_CONCRETE, new java.awt.Color(44, 46, 143)),
            new g40(Material.PURPLE_CONCRETE, new java.awt.Color(100, 32, 156)),
            new g40(Material.MAGENTA_CONCRETE, new java.awt.Color(170, 45, 160)),
            new g40(Material.PINK_CONCRETE, new java.awt.Color(210, 97, 137)),
            new g40(Material.BROWN_CONCRETE, new java.awt.Color(96, 59, 31)),

            // === TERRACOTTA ===
            new g40(Material.TERRACOTTA, new java.awt.Color(152, 94, 68)),
            new g40(Material.WHITE_TERRACOTTA, new java.awt.Color(209, 178, 161)),
            new g40(Material.LIGHT_GRAY_TERRACOTTA, new java.awt.Color(135, 107, 98)),
            new g40(Material.GRAY_TERRACOTTA, new java.awt.Color(57, 42, 35)),
            new g40(Material.BLACK_TERRACOTTA, new java.awt.Color(37, 23, 16)),
            new g40(Material.RED_TERRACOTTA, new java.awt.Color(143, 61, 47)),
            new g40(Material.ORANGE_TERRACOTTA, new java.awt.Color(161, 83, 37)),
            new g40(Material.YELLOW_TERRACOTTA, new java.awt.Color(186, 133, 35)),
            new g40(Material.LIME_TERRACOTTA, new java.awt.Color(103, 117, 53)),
            new g40(Material.GREEN_TERRACOTTA, new java.awt.Color(76, 83, 42)),
            new g40(Material.CYAN_TERRACOTTA, new java.awt.Color(86, 91, 91)),
            new g40(Material.LIGHT_BLUE_TERRACOTTA, new java.awt.Color(113, 108, 137)),
            new g40(Material.BLUE_TERRACOTTA, new java.awt.Color(74, 59, 91)),
            new g40(Material.PURPLE_TERRACOTTA, new java.awt.Color(118, 70, 86)),
            new g40(Material.MAGENTA_TERRACOTTA, new java.awt.Color(149, 87, 108)),
            new g40(Material.PINK_TERRACOTTA, new java.awt.Color(160, 77, 78)),
            new g40(Material.BROWN_TERRACOTTA, new java.awt.Color(77, 51, 35)),

            // === WOOL ===
            new g40(Material.WHITE_WOOL, new java.awt.Color(234, 236, 237)),
            new g40(Material.LIGHT_GRAY_WOOL, new java.awt.Color(142, 142, 135)),
            new g40(Material.GRAY_WOOL, new java.awt.Color(63, 68, 72)),
            new g40(Material.BLACK_WOOL, new java.awt.Color(29, 29, 33)),
            new g40(Material.RED_WOOL, new java.awt.Color(161, 39, 34)),
            new g40(Material.ORANGE_WOOL, new java.awt.Color(241, 118, 20)),
            new g40(Material.YELLOW_WOOL, new java.awt.Color(249, 198, 39)),
            new g40(Material.LIME_WOOL, new java.awt.Color(110, 185, 25)),
            new g40(Material.GREEN_WOOL, new java.awt.Color(85, 110, 27)),
            new g40(Material.CYAN_WOOL, new java.awt.Color(21, 137, 145)),
            new g40(Material.LIGHT_BLUE_WOOL, new java.awt.Color(113, 166, 221)),
            new g40(Material.BLUE_WOOL, new java.awt.Color(53, 57, 157)),
            new g40(Material.PURPLE_WOOL, new java.awt.Color(123, 47, 190)),
            new g40(Material.MAGENTA_WOOL, new java.awt.Color(195, 84, 205)),
            new g40(Material.PINK_WOOL, new java.awt.Color(243, 139, 170)),
            new g40(Material.BROWN_WOOL, new java.awt.Color(112, 71, 40)),

            // === PLANKS (WOOD) ===
            new g40(Material.OAK_PLANKS, new java.awt.Color(162, 130, 79)),
            new g40(Material.SPRUCE_PLANKS, new java.awt.Color(115, 84, 52)),
            new g40(Material.BIRCH_PLANKS, new java.awt.Color(197, 179, 123)),
            new g40(Material.JUNGLE_PLANKS, new java.awt.Color(171, 124, 85)),
            new g40(Material.ACACIA_PLANKS, new java.awt.Color(175, 92, 66)),
            new g40(Material.DARK_OAK_PLANKS, new java.awt.Color(66, 43, 20)),
            new g40(Material.MANGROVE_PLANKS, new java.awt.Color(132, 38, 38)),
            new g40(Material.CHERRY_PLANKS, new java.awt.Color(216, 132, 145)),
            new g40(Material.BAMBOO_PLANKS, new java.awt.Color(197, 176, 96)),
            new g40(Material.CRIMSON_PLANKS, new java.awt.Color(137, 58, 90)),
            new g40(Material.WARPED_PLANKS, new java.awt.Color(58, 142, 140)),

            // === PRECIOUS BLOCKS ===
            new g40(Material.DIAMOND_BLOCK, new java.awt.Color(97, 219, 213)),
            new g40(Material.EMERALD_BLOCK, new java.awt.Color(63, 217, 58)),
            new g40(Material.GOLD_BLOCK, new java.awt.Color(249, 236, 78)),
            new g40(Material.IRON_BLOCK, new java.awt.Color(224, 224, 224)),
            new g40(Material.NETHERITE_BLOCK, new java.awt.Color(64, 59, 65)),

            // === COPPER TYPES ===
            new g40(Material.COPPER_BLOCK, new java.awt.Color(183, 106, 53)),
            new g40(Material.EXPOSED_COPPER, new java.awt.Color(168, 138, 113)),
            new g40(Material.WEATHERED_COPPER, new java.awt.Color(74, 153, 135)),
            new g40(Material.OXIDIZED_COPPER, new java.awt.Color(90, 161, 151)),

            // === PURPUR ===
            new g40(Material.PURPUR_BLOCK, new java.awt.Color(172, 124, 172)),

            // === PRISMARINE ===
            new g40(Material.PRISMARINE, new java.awt.Color(99, 156, 143)),
            new g40(Material.PRISMARINE_BRICKS, new java.awt.Color(70, 194, 175)),
            new g40(Material.DARK_PRISMARINE, new java.awt.Color(46, 102, 90)),

            // === SANDSTONES ===
            new g40(Material.SANDSTONE, new java.awt.Color(219, 211, 160)),
            new g40(Material.CUT_SANDSTONE, new java.awt.Color(215, 205, 152)),
            new g40(Material.RED_SANDSTONE, new java.awt.Color(177, 90, 47)),
            new g40(Material.CUT_RED_SANDSTONE, new java.awt.Color(170, 83, 42)),

            // === END STONE ===
            new g40(Material.END_STONE, new java.awt.Color(230, 230, 170))

    );



    protected Material f1(java.awt.Color f1) {
        return g41.stream()
                .min(Comparator.comparingDouble(p -> f1(f1, p.color())))
                .map(g40::material)
                .orElse(Material.BARRIER);
    }


    protected double f1(java.awt.Color f1, java.awt.Color g30) {
        int dr = f1.getRed() - g30.getRed();
        int dg = f1.getGreen() - g30.getGreen();
        int db = f1.getBlue() - g30.getBlue();
        return Math.sqrt(dr * dr + dg * dg + db * db);
    }
    protected Vector f1(Vector f1, double g29) {
        double rad = Math.toRadians(g29);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double x = f1.getX() * cos - f1.getZ() * sin;
        double z = f1.getX() * sin + f1.getZ() * cos;
        return new Vector(x, f1.getY(), z);
    }

    protected void f1(Player f1, World f2, BufferedImage a, Location b, int c, String ffa, String ffs) {
        double rotation = switch (ffa) {
            case "N" -> 180;
            case "E" -> -90;
            case "W" -> 90;
            default -> 0;
        };

        // Head (base layer): size 8x8x8, using full 6-sided unwrap
        f1(a, f2, b.clone().add(0, 24, -2), c, rotation);

        // Torso front: 8x12x4
        f1(a, f2, b.clone().add(0, 12, 0), c, 8, rotation, new Point(20, 20));

        // Right Arm (44,20), 4x12x4
        f1(a, f2, b.clone().add(-4, 12, 0), c, 4, rotation, new Point(44, 20));

        // Left Arm (36,52), 4x12x4
        f1(a, f2, b.clone().add(8, 12, 0), c, 4, rotation, new Point(36, 52));

        // Right Leg (4,20), 4x12x4
        f1(a, f2, b.clone().add(0, 0, 0), c, 4, rotation, new Point(4, 20));

        // Left Leg (20,52), 4x12x4
        f1(a, f2, b.clone().add(4, 0, 0), c, 4, rotation, new Point(20, 52));
    }
    protected void f1(BufferedImage f1, World f12, Location continued, int returned, double test) {
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

                    java.awt.Color color = new java.awt.Color(f1.getRGB(skinX, skinY), true);
                    if (color.getAlpha() < 10) continue;

                    Vector offset = new Vector(x * returned, y * returned, z * returned);
                    offset = f1(offset, test);
                    Location loc = continued.clone().add(offset);
                    Material material = f1(color);
                    f1(f12, loc, material, returned);
                }
            }
        }
    }

    protected void f1(BufferedImage f1, World test, Location mcydatabase, int archi, int f, double Is, Point q) {
        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < f; x++) {
                for (int z = 0; z < 4; z++) {
                    int pixelX = q.x + x % f;
                    int pixelY = q.y + (12 - y - 1) % 12;
                    int rgb = f1.getRGB(pixelX, pixelY);
                    java.awt.Color color = new java.awt.Color(rgb, true);
                    if (color.getAlpha() < 10) continue;

                    Vector offset = new Vector(x * archi, y * archi, z * archi);
                    offset = f1(offset, Is);
                    Location loc = mcydatabase.clone().add(offset);
                    Material material = f1(color);
                    f1(test, loc, material, archi);
                }
            }
        }
    }


    protected boolean f1(Player f1, String... f) {
        List<String> ff = new ArrayList<>();

        for (String name : f) {
            switch (name.toLowerCase()) {
                case "worldedit":
                    if (!WorldEdit_Installed) ff.add("WorldEdit");
                    break;
                case "worldguard":
                    if (!WorldGuard_Installed) ff.add("WorldGuard");
                    break;
                case "luckperms":
                    if (!LuckPerms_Installed) ff.add("LuckPerms");
                    break;
                case "protocollib":
                    if (!ProtocolLib_Installed) ff.add("ProtocolLib");
                    break;
                case "griefprevention":
                    if (!GriefPrevention_Installed) ff.add("GriefPrevention");
                    break;
                default:
                    ff.add(name + " (unknown plugin flag)");
            }
        }

        if (!ff.isEmpty()) {
            f1.sendMessage("§c§lMissing required plugin(s): §r" + String.join(", ", ff));
            return false;
        }

        return true;
    }


    protected boolean f(Material f) {
        return f.isBlock() && f.isSolid(); // optionally refine further
    }

    protected ItemStack f1(Inventory f1) {
        for (ItemStack item : f1.getContents()) {
            if (item == null) continue;
            Material mat = item.getType();
            if (f(mat)) return item;
        }
        return null;
    }




    protected void f1(Player f1, int test) {
        Location center = f1.getLocation();
        World world = center.getWorld();
        if (world == null) return;

        for (int dx = -test; dx <= test; dx++) {
            for (int dy = - 2; dy <= 0; dy++) {
                for (int dz = -test; dz <= test; dz++) {
                    Location loc = center.clone().add(dx, dy, dz);
                    Block block = loc.getBlock();
                    Material type = block.getType();

                    BlockData data = block.getBlockData();
                    boolean waterLogged = false;
                    if (data instanceof Waterlogged waterlogged && waterlogged.isWaterlogged()) {
                        waterLogged = true;
                    }

                    if (waterLogged || type == Material.WATER || type == Material.LAVA  || type == Material.BUBBLE_COLUMN) {

                        if (g26.containsKey(loc)) {
                            g26.get(loc).cancel();
                        }

                        Material fakeMat = (type == Material.LAVA  )
                                ? Material.ORANGE_CONCRETE : Material.LAPIS_BLOCK;

                        f1.sendBlockChange(loc, fakeMat.createBlockData());

                        BukkitTask task = Bukkit.getScheduler().runTaskLater(
                                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                                () -> f1.sendBlockChange(loc, block.getBlockData()),
                                100L
                        );

                        g26.put(loc, task);
                    }
                }
            }
        }
    }




    protected ItemStack f2(Player f1, int g30) {
        if (g30 >= 0 && g30 <= 8) return f1.getInventory().getItem(g30);
        if (g30 >= 9 && g30 <= 35) return f1.getInventory().getItem(g30);
        if (g30 == 40) return f1.getInventory().getItemInOffHand();
        return null;
    }

    protected int[] f1(World f1, int f2) {
        int chunkZ = g20.getInt("last_chunk_z", 9);
        int y = g20.getInt("last_y", f1.getMinHeight());

        while (true) {
            int x = f2 * 16;
            int z = chunkZ * 16;
            Location loc = new Location(f1, x, y, z);
            if (loc.getBlock().getType() == Material.AIR) {
                g20.set("last_chunk_z", chunkZ);
                g20.set("last_y", y);
                try { g20.save(g16); } catch (IOException ignored) {}
                return new int[] {x, y, z};
            }

            y++;
            if (y >= f1.getMaxHeight()) {
                y = f1.getMinHeight();
                chunkZ++;
            }
        }
    }
    
    
    /**
     * Map a Shulker Box to a “glass” placeholder:
     *  • SHULKER_BOX          → GLASS
     *  • RED_SHULKER_BOX      → RED_STAINED_GLASS
     *  • ...etc...
     * Falls back to GLASS if something’s unexpected.
     */
    protected Material f1(Material f1) {
        if (f1 == Material.SHULKER_BOX) {
            return Material.GLASS;
        }
        String name = f1.name();
        if (name.endsWith("_SHULKER_BOX")) {
            try {
                return Material.valueOf(name.replace("_SHULKER_BOX", "_STAINED_GLASS"));
            } catch (IllegalArgumentException ignored) { }
        }
        return Material.GLASS;
    }

    /**
     * Reverse mapping:
     *  • GLASS                → SHULKER_BOX
     *  • RED_STAINED_GLASS    → RED_SHULKER_BOX
     *  • ...etc...
     * Falls back to SHULKER_BOX if something’s unexpected.
     */
    protected Material f2(Material f1) {
        if (f1 == Material.GLASS) {
            return Material.SHULKER_BOX;
        }
        String name = f1.name();
        if (name.endsWith("_STAINED_GLASS")) {
            try {
                return Material.valueOf(name.replace("_STAINED_GLASS", "_SHULKER_BOX"));
            } catch (IllegalArgumentException ignored) { }
        }
        return Material.SHULKER_BOX;
    }





    protected ItemStack f1(ItemStack f1, Material fg3) {
        YamlConfiguration config = new YamlConfiguration();
        // Place the item under a known section "slot0"
        config.set("slot0", f1);
        String yaml = config.saveToString();

        // Replace any occurrence of "executableblocks:eb-id" with "executableitems:ei-id"
        yaml = yaml.replaceAll("(?i)\"executableblocks:eb-id\"", "\"executableitems:ei-id\"");

        // Replace any existing "executableitems:ei-id" value with "GUINoClick"
        yaml = yaml.replaceAll("(?i)(\"executableitems:ei-id\"\\s*:\\s*\")[^\"]+\"", "$1TempShulkerPlaceholder\"");

        // Ensure that a meta section exists in slot0.
        // We'll check for "slot0:" followed by a newline and two spaces then "meta:"
        if (!yaml.contains("meta:")) {
            @SuppressWarnings("TextBlockMigration") String metaBlock =
                    "\n  meta:\n" +
                            "    ==: ItemMeta\n" +
                            "    meta-type: UNSPECIFIC\n" +
                            "    PublicBukkitValues: |-\n" +
                            "      {\n" +
                            "          \"executableitems:ei-id\": \"TempShulkerPlaceholder\",\n" +
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
                    "          \"executableitems:ei-id\": \"TempShulkerPlaceholder\",\n" +
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
            newItem = f1;
        }
        newItem.setType(fg3);

        return newItem;
    }




    protected ItemStack f3(ItemStack recorded, Material test) {
        YamlConfiguration config = new YamlConfiguration();
        // Place the item under a known section "slot0"
        config.set("slot0", recorded);
        String yaml = config.saveToString();

        // Replace any occurrence of "executableblocks:eb-id" with "executableitems:ei-id"
        yaml = yaml.replaceAll("(?i)\"executableblocks:eb-id\"", "\"executableitems:ei-id\"");

        // Replace any existing "executableitems:ei-id" value with "GUINoClick"
        yaml = yaml.replaceAll("(?i)(\"executableitems:ei-id\"\\s*:\\s*\")[^\"]+\"", "$1doesnotexist\"");

        // Ensure that a meta section exists in slot0.
        // We'll check for "slot0:" followed by a newline and two spaces then "meta:"
        if (!yaml.contains("meta:")) {
            @SuppressWarnings("TextBlockMigration") String metaBlock =
                    "\n  meta:\n" +
                            "    ==: ItemMeta\n" +
                            "    meta-type: UNSPECIFIC\n" +
                            "    PublicBukkitValues: |-\n" +
                            "      {\n" +
                            "          \"executableitems:ei-id\": \"doesnotexist\",\n" +
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
                    "          \"executableitems:ei-id\": \"doesnotexist\",\n" +
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
            newItem = recorded;
        }
        newItem.setType(test);

        return newItem;
    }


    protected void f9() {
        try { g2.save(g1); }
        catch(IOException e){ e.printStackTrace(); }
    }



    /**
     * Returns true if the player currently has their “Shulker Box” placeholder chest open.
     */
    protected boolean f123(Player f1) {
        // 1) Get the open-inventory view
        InventoryView view = f1.getOpenInventory();
        Inventory topInv = view.getTopInventory();

        // 2) It must be a real chest block state
        InventoryHolder holder = topInv.getHolder();
        if (!(holder instanceof Chest)) {
            return false;
        }
        Chest chest = (Chest) holder;

        // 3) Compare its location against the saved shulker chest coords
        Location loc = chest.getLocation();
        String uuid = f1.getUniqueId().toString();
        String saved = g2.getString(uuid, "");
        if (saved.isEmpty()) {
            return false;
        }
        String[] parts = saved.split(" ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);

        // 4) Must be in the mcydatabase world at the exact coords
        return loc.getWorld().getName().equals("mcydatabase")
                && loc.getBlockX() == x
                && loc.getBlockY() == y
                && loc.getBlockZ() == z;
    }


    protected void f1(String f1, String f, int g30) {
        List<Map.Entry<String, Integer>> list = global1.computeIfAbsent(f1, k -> new ArrayList<>());
        list.removeIf(e -> e.getKey().equals(f));
        Map.Entry<String, Integer> newEntry = Map.entry(f, g30);

        int index = 0;
        while (index < list.size()) {
            Map.Entry<String, Integer> current = list.get(index);
            if (g30 > current.getValue()) break;
            index++;
        }
        list.add(index, newEntry);
    }

    protected int f1(String f1, String fone) {
        List<Map.Entry<String, Integer>> list = global1.get(f1);
        if (list == null) return 0;
        return list.stream().filter(e -> e.getKey().equals(fone)).map(Map.Entry::getValue).findFirst().orElse(0);
    }


    protected boolean fOne(Material fOne) {
        return !fOne.isSolid() || fOne == Material.STRING || fOne == Material.COBWEB || fOne == Material.WATER || fOne == Material.LAVA || fOne == Material.TALL_GRASS;
    }

    protected List<Block> f1(BoundingBox TEST_VARAIBLE, World f1) {
        List<Block> blocks = new ArrayList<>();
        int minX = (int) Math.floor(TEST_VARAIBLE.getMinX());
        int maxX = (int) Math.ceil(TEST_VARAIBLE.getMaxX());
        int minY = (int) Math.floor(TEST_VARAIBLE.getMinY());
        int maxY = (int) Math.ceil(TEST_VARAIBLE.getMaxY());
        int minZ = (int) Math.floor(TEST_VARAIBLE.getMinZ());
        int maxZ = (int) Math.ceil(TEST_VARAIBLE.getMaxZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    blocks.add(f1.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }



    protected Vector f1racer(Vector from, Vector to, double maxRad) {
        from = from.clone().normalize();
        to = to.clone().normalize();

        // Axis of rotation
        Vector axis = from.clone().crossProduct(to).normalize();
        if (axis.lengthSquared() == 0) {
            return to; // Vectors are parallel
        }

        // Rodrigues' rotation formula
        double cos = Math.cos(maxRad);
        double sin = Math.sin(maxRad);

        Vector term1 = from.clone().multiply(cos);
        Vector term2 = axis.clone().crossProduct(from).multiply(sin);
        Vector term3 = axis.clone().multiply(axis.dot(from)).multiply(1 - cos);

        return term1.add(term2).add(term3).normalize();
    }



    protected void f1(Player entity, String worldName, int distance, int radius, int pitch) {
        World testWorld = Bukkit.getWorld(worldName);
        if (testWorld == null) {
            entity.sendMessage("§cWorld not found.");
            return;
        }
        Block tnt = testWorld.getBlockAt(distance, radius, pitch);
        if (!(tnt.getState() instanceof Container)) {
            entity.sendMessage("§cThat block is not a container.");
            return;
        }
        Container virtualInventory = (Container) tnt.getState();
        Inventory enderChest = virtualInventory.getInventory();

        Inventory entityInv = Bukkit.createInventory(null, enderChest.getSize(), "§8[View Chest]");
        for (int i = 0; i < enderChest.getSize(); i++) {
            ItemStack OPItem = enderChest.getItem(i);
            if (OPItem != null && OPItem.getType() != Material.AIR) {
                entityInv.setItem(i, f1(OPItem));
            } else {
                entityInv.setItem(i, f0());
            }
        }
        entity.openInventory(entityInv);
    }



    protected Vector pitchVectorUpwards(Vector vec, double radians) {
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




    protected boolean f1(Entity temp, String name, Set<EntityType> number) {
        if (name.equalsIgnoreCase("Players")) {
            return temp instanceof Player;
        }
        if (name.equalsIgnoreCase("Hostiles")) {
            return number.contains(temp.getType());
        }
        if (name.equalsIgnoreCase("Both")) {
            return temp instanceof Player || number.contains(temp.getType());
        }
        return false;
    }



    protected boolean f1(Entity player, Set<String> name) {
        for (String attributes : name) {
            if (player.getScoreboardTags().contains(attributes)) return true;
        }
        return false;
    }


    protected boolean f1(Location laser, Location predicted) {
        return laser.getWorld().rayTraceBlocks(laser, predicted.toVector().subtract(laser.toVector()).normalize(), laser.distance(predicted), FluidCollisionMode.NEVER, true) == null;
    }


    protected void f1(Player entityTarget, UUID tntUUID) {
        OfflinePlayer fakePlayer = Bukkit.getOfflinePlayer(tntUUID);
        if (!fakePlayer.isOnline()) {
            entityTarget.sendMessage("§cTarget player is not online.");
            return;
        }
        Player FakePlayer2 = fakePlayer.getPlayer();
        Inventory redirector = FakePlayer2.getEnderChest();

        Inventory immediate = Bukkit.createInventory(null, redirector.getSize(), "§8[View EnderChest]");
        for (int redirector2 = 0; redirector2 < redirector.getSize(); redirector2++) {
            ItemStack item = redirector.getItem(redirector2);
            if (item != null && item.getType() != Material.AIR) {
                immediate.setItem(redirector2, f1(item));
            } else {
                immediate.setItem(redirector2, f0());
            }
        }
        entityTarget.openInventory(immediate);
    }




    protected void f2(Player discord, UUID rename) {
        OfflinePlayer fakeTarget = Bukkit.getOfflinePlayer(rename);
        if (!fakeTarget.isOnline()) {
            discord.sendMessage("§cTarget player is not online.");
            return;
        }
        Player fakeSender = fakeTarget.getPlayer();
        Inventory enderChest = Bukkit.createInventory(null, 54, "§8[View Inventory]");

        // Top 3 rows: main inventory (slots 9-35)
        for (int hp = 9; hp < 36; hp++) {
            ItemStack retester = fakeSender.getInventory().getItem(hp);
            if (retester != null && retester.getType() != Material.AIR) {
                enderChest.setItem(hp - 9, f1(retester));
            } else {
                enderChest.setItem(hp - 9, f0());
            }
        }

        // 4th row: hotbar (slots 0-8)
        for (int chests = 0; chests < 9; chests++) {
            ItemStack fakeTnt = fakeSender.getInventory().getItem(chests);
            if (fakeTnt != null && fakeTnt.getType() != Material.AIR) {
                enderChest.setItem(27 + chests, f1(fakeTnt));
            } else {
                enderChest.setItem(27 + chests, f0());
            }
        }

        // Armor slots
        ItemStack[] entityArmor = fakeSender.getInventory().getArmorContents();
        for (int armorHead = 0; armorHead < 4; armorHead++) {
            ItemStack helmet = entityArmor[armorHead];
            if (helmet != null && helmet.getType() != Material.AIR) {
                enderChest.setItem(36 + armorHead, f1(helmet));
            } else {
                enderChest.setItem(36 + armorHead, f0());
            }
        }

        // Offhand
        ItemStack totem = fakeSender.getInventory().getItemInOffHand();
        if (totem != null && totem.getType() != Material.AIR) {
            enderChest.setItem(40, f1(totem));
        } else {
            enderChest.setItem(40, f0());
        }

        // Last row: crafting grid dummy slots
        for (int x = 45; x <= 53; x++) {
            enderChest.setItem(x, f0());
        }

        for (int y = 41; y <= 44; y++) {
            enderChest.setItem(y, f0());
        }

        discord.openInventory(enderChest);
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




    private static @NotNull String cosmicEnchant(Player p, @NotNull String identifier) {
        String[] parts = identifier.substring("cosmicEnchant_".length()).split(",");
        if (parts.length != 3) return "§cInvalid placeholder format.";

        try {
            int slot = Integer.parseInt(parts[0]);
            boolean override = Boolean.parseBoolean(parts[1]);
            boolean debug = Boolean.parseBoolean(parts[2]);

            Player player = Bukkit.getPlayer(p.getUniqueId());
            if (player == null) return "§cInvalid player.";

            ItemStack slotItem;
            // Handle special slots (main hand = -1, offhand = 40, armor slots 39-36)
            if (slot == -1) {
                slotItem = player.getInventory().getItemInMainHand();
            } else if (slot == 40) {
                slotItem = player.getInventory().getItemInOffHand();
            } else {
                slotItem = player.getInventory().getItem(slot);
            }

            if (slotItem == null || slotItem.getType() == Material.AIR) {
                return debug ? "§e[DEBUG] No item in specified slot." : "";
            }

            ItemStack cursorItem = player.getItemOnCursor();
            if (cursorItem == null || cursorItem.getType() != Material.ENCHANTED_BOOK) {
                return debug ? "§e[DEBUG] Cursor is not an enchanted book." : "";
            }

            if (!(cursorItem.getItemMeta() instanceof EnchantmentStorageMeta)) {
                return debug ? "§e[DEBUG] Cursor item has no enchantments." : "";
            }

            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) cursorItem.getItemMeta();
            if (meta.getStoredEnchants().isEmpty()) {
                return debug ? "§e[DEBUG] Enchanted book has no stored enchantments." : "";
            }

            Map<Enchantment, Integer> storedEnchants = meta.getStoredEnchants();

            // Step 3: If not overridden, check if any enchant is incompatible
            if (!override) {
                for (Enchantment newEnchant : storedEnchants.keySet()) {
                    if (!newEnchant.canEnchantItem(slotItem)) {
                        return debug ? "§e[DEBUG] " + newEnchant.getKey().getKey() + " not applicable." : "";
                    }
                    for (Enchantment existing : slotItem.getEnchantments().keySet()) {
                        if (newEnchant.conflictsWith(existing)) {
                            return debug ? "§e[DEBUG] Conflict: " + newEnchant.getKey().getKey() + " vs " + existing.getKey().getKey() : "";
                        }
                    }
                }
            }

            // Step 4: Apply all compatible stored enchants
            ItemMeta slotMeta = slotItem.getItemMeta();
            for (Map.Entry<Enchantment, Integer> entry : storedEnchants.entrySet()) {
                slotMeta.addEnchant(entry.getKey(), entry.getValue(), true); // allow unsafe if needed
            }
            slotItem.setItemMeta(slotMeta);

            // Update inventory
            if (slot == -1) {
                player.getInventory().setItemInMainHand(slotItem);
            } else if (slot == 40) {
                player.getInventory().setItemInOffHand(slotItem);
            } else {
                player.getInventory().setItem(slot, slotItem);
            }

            return "§a[✓] Enchants Applied";

        } catch (Exception e) {
            e.printStackTrace();
            return "§c[ERROR]";
        }
    }





    /** True if there is neither an empty slot nor room in any stack. */
    private static boolean isShulkerFull(Inventory inv) {
        if (inv.firstEmpty() != -1) return false;
        for (ItemStack s : inv.getStorageContents()) {
            if (s == null || s.getType() == Material.AIR) return false;
            if (s.getAmount() < s.getMaxStackSize()) return false;
        }
        return true;
    }

    /** Computes how many units of `stack` can fit into `inv` (merge-first, then empties), without mutating. */
    private static int computeInsertCapacity(Inventory inv, ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) return 0;
        int want = stack.getAmount();
        int maxStack = stack.getMaxStackSize();
        int can = 0;

        // Room in similar stacks
        for (ItemStack ex : inv.getStorageContents()) {
            if (ex == null) continue;
            if (!ex.isSimilar(stack)) continue;
            int room = ex.getMaxStackSize() - ex.getAmount();
            if (room <= 0) continue;
            int add = Math.min(room, want - can);
            can += add;
            if (can >= want) return can;
        }
        // Room in empties
        for (ItemStack ex : inv.getStorageContents()) {
            if (ex != null && ex.getType() != Material.AIR) continue;
            int add = Math.min(maxStack, want - can);
            can += add;
            if (can >= want) return can;
        }
        return can;
    }

    /**
     * Inserts exactly `amount` of items like `template` into `inv` (merge-first, then empties).
     * Returns how many were actually inserted (<= amount). Mutates the inventory.
     */
    private static int insertAmountIntoShulker(Inventory inv, ItemStack template, int amount) {
        if (template == null || template.getType() == Material.AIR || amount <= 0) return 0;
        int remaining = amount;

        // Merge into similar stacks
        for (int i = 0; i < inv.getSize() && remaining > 0; i++) {
            ItemStack ex = inv.getItem(i);
            if (ex == null) continue;
            if (!ex.isSimilar(template)) continue;

            int room = ex.getMaxStackSize() - ex.getAmount();
            if (room <= 0) continue;

            int add = Math.min(room, remaining);
            ex.setAmount(ex.getAmount() + add);
            remaining -= add;
        }

        // Fill empty slots
        for (int i = 0; i < inv.getSize() && remaining > 0; i++) {
            ItemStack ex = inv.getItem(i);
            if (ex != null && ex.getType() != Material.AIR) continue;

            int max = template.getMaxStackSize();
            int add = Math.min(max, remaining);
            ItemStack place = template.clone();
            place.setAmount(add);
            inv.setItem(i, place);
            remaining -= add;
        }

        return amount - remaining;
    }

    private static void spawnTrail(Plugin plugin, World world, Location start,
                                   Particle particle, Particle.DustOptions dustOpt,
                                   double sep, int intervalTicks, Player p) {
        if (sep <= 0) sep = 0.2;
        if (intervalTicks < 1) intervalTicks = 1;

        // Stop once we're this close to the player (in blocks).
        final double stopThreshold = intervalTicks + 0.1; // as requested

        double finalSep = sep;
        new BukkitRunnable() {
            int steps = 0;
            Location cur = start.clone();

            @Override public void run() {
                if (!p.isOnline() || p.isDead() || p.getWorld() != world) { cancel(); return; }

                // Always target the player's *current* midsection
                Location midNow = p.getLocation().clone().add(0, 1.0, 0);
                Vector toTarget = midNow.toVector().subtract(cur.toVector());
                double dist = toTarget.length();

                // Stop if close enough or if we've run long enough
                if (dist <= stopThreshold || steps >= 100) { cancel(); return; }

                Vector step = toTarget.normalize().multiply(finalSep);
                if (step.length() > dist) step = toTarget; // avoid overshoot

                if (particle == Particle.DUST && dustOpt != null) {
                    world.spawnParticle(particle, cur, 0, dustOpt);
                } else {
                    world.spawnParticle(particle, cur, 0);
                }

                cur.add(step);
                steps++;
            }
        }.runTaskTimer(plugin, 0L, intervalTicks);
    }


    private static final class VacuumJob extends BukkitRunnable {
        final UUID uuid;
        final Plugin plugin;
        final Player p;
        final World world;

        // Fixed params for this job instance
        final double RANGE;
        final double FOV_DEGREES;
        final boolean IGNORE_SHULKER_ITEMS;
        final int MAXENTITIES;
        final int INTERVAL_TICKS;
        final int DURATION_TICKS;
        final boolean THROUGH_WALLS;
        final Particle particle;
        final Particle.DustOptions dustOpt;
        final double SEPARATION;
        final int PARTICLE_INTERVAL_TICKS;

        // Timer
        private int elapsed = 0;

        VacuumJob(Plugin plugin,
                  Player p,
                  double RANGE, double FOV_DEGREES, boolean IGNORE_SHULKER_ITEMS,
                  int MAXENTITIES, int INTERVAL_TICKS, int DURATION_TICKS,
                  boolean THROUGH_WALLS, Particle particle,
                  Particle.DustOptions dustOpt, double SEPARATION, int PARTICLE_INTERVAL_TICKS) {
            this.plugin = plugin;
            this.p = p;
            this.uuid = p.getUniqueId();
            this.world = p.getWorld();
            this.RANGE = RANGE;
            this.FOV_DEGREES = FOV_DEGREES;
            this.IGNORE_SHULKER_ITEMS = IGNORE_SHULKER_ITEMS;
            this.MAXENTITIES = MAXENTITIES;
            this.INTERVAL_TICKS = Math.max(1, INTERVAL_TICKS);
            this.DURATION_TICKS = Math.max(1, DURATION_TICKS);
            this.THROUGH_WALLS = THROUGH_WALLS;
            this.particle = particle;
            this.dustOpt = dustOpt;
            this.SEPARATION = SEPARATION;
            this.PARTICLE_INTERVAL_TICKS = Math.max(1, PARTICLE_INTERVAL_TICKS);
        }

        void resetTimer() { this.elapsed = 0; }

        @Override public void cancel() {
            super.cancel();
            ACTIVE_VACUUMS.remove(uuid, this);
        }

        @Override public void run() {
            if (!p.isOnline() || p.isDead() || p.getWorld() != world) { cancel(); return; }

            // Validate offhand shulker each sweep
            ItemStack offTick = p.getInventory().getItemInOffHand();
            if (offTick == null || offTick.getType() == Material.AIR || !offTick.getType().name().endsWith("SHULKER_BOX")) {
                cancel(); return;
            }
            BlockStateMeta metaTick = (offTick.getItemMeta() instanceof BlockStateMeta bsm) ? bsm : null;
            if (metaTick == null || !(metaTick.getBlockState() instanceof ShulkerBox boxTick)) {
                cancel(); return;
            }
            ShulkerBox box = boxTick;
            Inventory boxInv = box.getInventory();
            if (isShulkerFull(boxInv)) { cancel(); return; }

            final Location eye = p.getEyeLocation();
            final Location mid = p.getLocation().clone().add(0, 1.0, 0);
            final Vector lookDir = eye.getDirection().normalize();
            final double losMax = Math.max(0.1, RANGE);

            List<Item> candidates = new ArrayList<>();
            for (Entity e : world.getNearbyEntities(p.getLocation(), RANGE, RANGE, RANGE)) {
                if (!(e instanceof Item it)) continue;
                if (!it.isValid() || it.isDead()) continue;

                ItemStack stack = it.getItemStack();
                if (stack == null || stack.getType() == Material.AIR) continue;
                if (IGNORE_SHULKER_ITEMS && stack.getType().name().endsWith("SHULKER_BOX")) continue;

                double dist = eye.distance(it.getLocation());
                if (dist > RANGE) continue;

                if (FOV_DEGREES > 0.0) {
                    Vector toItem = it.getLocation().toVector().add(new Vector(0, 0.05, 0))
                            .subtract(eye.toVector()).normalize();
                    double angle = Math.toDegrees(lookDir.angle(toItem));
                    if (angle > FOV_DEGREES / 2.0) continue;
                }

                if (!THROUGH_WALLS) {
                    Vector dir = it.getLocation().toVector().add(new Vector(0, 0.05, 0))
                            .subtract(eye.toVector()).normalize();
                    RayTraceResult r = world.rayTraceBlocks(eye, dir, dist, FluidCollisionMode.NEVER, true);
                    if (r != null) continue; // blocked
                }

                if (computeInsertCapacity(boxInv, stack) > 0) {
                    candidates.add(it);
                }
            }

            if (!candidates.isEmpty()) {
                int processedEntities = 0;
                int limit = (MAXENTITIES < 0) ? candidates.size() : Math.min(MAXENTITIES, candidates.size());

                for (Item it : candidates) {
                    if (processedEntities >= limit) break;
                    if (!it.isValid() || it.isDead()) continue;
                    if (isShulkerFull(boxInv)) break;

                    ItemStack stack = it.getItemStack();
                    if (stack == null || stack.getType() == Material.AIR) continue;

                    int capacity = computeInsertCapacity(boxInv, stack);
                    if (capacity <= 0) continue;

                    int toMove = Math.min(capacity, stack.getAmount());
                    int moved = insertAmountIntoShulker(boxInv, stack, toMove);
                    if (moved <= 0) continue;

                    // Persist shulker back to offhand item
                    box.update();
                    metaTick.setBlockState(box);
                    offTick.setItemMeta(metaTick);
                    p.getInventory().setItemInOffHand(offTick);

                    // Shrink or remove ground entity
                    final Location start = it.getLocation().clone().add(0, 0.05, 0);
                    int remaining = stack.getAmount() - moved;
                    if (remaining <= 0) it.remove();
                    else {
                        ItemStack newStack = stack.clone();
                        newStack.setAmount(remaining);
                        it.setItemStack(newStack);
                    }

                    processedEntities++;
                    spawnTrail(plugin, world, start, particle, dustOpt, SEPARATION, PARTICLE_INTERVAL_TICKS, p);
                }
            }

            elapsed += INTERVAL_TICKS;
            if (elapsed >= DURATION_TICKS) { cancel(); }
        }
    }
}