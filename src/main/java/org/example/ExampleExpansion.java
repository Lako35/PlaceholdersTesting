package org.example;


import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.block.Container;
import org.bukkit.block.data.*;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.BlockStateMeta;
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
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
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

    protected final Map<String, List<Map.Entry<String, Integer>>> global1 = new HashMap<>();


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
            0x00A7, 0x0063, 0x00A7, 0x006C, 0x0059, 0x006F, 0x0075, 0x0020,
            0x0068, 0x0061, 0x0076, 0x0065, 0x0020, 0x0065, 0x0078, 0x0063,
            0x0065, 0x0065, 0x0064, 0x0065, 0x0064, 0x0020, 0x0074, 0x0068,
            0x0065, 0x0020, 0x006C, 0x0069, 0x006D, 0x0069, 0x0074, 0x0020,
            0x006F, 0x0066, 0x0020, 0x0074, 0x0068, 0x0065, 0x0020, 0x0066,
            0x0072, 0x0065, 0x0065, 0x0020, 0x0074, 0x0072, 0x0069, 0x0061,
            0x006C, 0x002E, 0x0020, 0x0043, 0x006F, 0x006E, 0x0073, 0x0069,
            0x0064, 0x0065, 0x0072, 0x0020, 0x0070, 0x0075, 0x0072, 0x0063,
            0x0068, 0x0061, 0x0073, 0x0069, 0x006E, 0x0067, 0x0020, 0x0074,
            0x0068, 0x0065, 0x0020, 0x0066, 0x0075, 0x006C, 0x006C, 0x0020,
            0x0070, 0x0061, 0x0063, 0x006B, 0x0020, 0x0066, 0x0072, 0x006F,
            0x006D, 0x0020, 0x005A, 0x0065, 0x0073, 0x0074, 0x0079, 0x0042,
            0x0075, 0x0066, 0x0066, 0x0061, 0x006C, 0x006F, 0x0020, 0x006F,
            0x0072, 0x0020, 0x0064, 0x006F, 0x0020, 0x002F, 0x0070, 0x0061,
            0x0070, 0x0069, 0x0020, 0x0072, 0x0065, 0x006C, 0x006F, 0x0061,
            0x0064, 0x0020, 0x0074, 0x006F, 0x0020, 0x0073, 0x0074, 0x0069,
            0x0063, 0x006B, 0x0020, 0x0077, 0x0069, 0x0074, 0x0068, 0x0020,
            0x0074, 0x0068, 0x0065, 0x0020, 0x0074, 0x0072, 0x0069, 0x0061,
            0x006C, 0x0020, 0x0076, 0x0065, 0x0072, 0x0073, 0x0069, 0x006F,
            0x006E
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
            SCore_Installed = true;
            g5 = 1000;
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
            g5--;
        }

        // DO NOT MOVE ^


        // INSERT HERE 



        if (f1.startsWith("trackv2.-1_")) {
            String[] parts = f1.substring("trackv2.-1_".length()).split(",");
            if (parts.length != 6) {
                return "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage,trackInterval,trackDuration%. You used" + f1;
            }

            try {
                UUID callerUUID = UUID.fromString(parts[0]);
                UUID targetUUID = UUID.fromString(parts[1]);
                double speed = Double.parseDouble(parts[2]);
                UUID launcherUUID = UUID.fromString(parts[3]);
                int trackInterval = Integer.parseInt(parts[4]);
                int trackDuration = Integer.parseInt(parts[5]);

                Entity caller = Bukkit.getEntity(callerUUID);
                Entity target = Bukkit.getEntity(targetUUID);

                if (caller == null || target == null) {
                    return "§c§lMissile Impacted"; // No valid entities
                }
                if (!caller.getWorld().equals(target.getWorld())) {
                    return "§c§lMissile Impacted."; // Different worlds
                }

                Location locCaller = caller.getLocation();
                Location locTarget = target.getLocation();

                double distance = locCaller.distance(locTarget);

                final Projectile trackedProjectile;
                if (caller instanceof Projectile) {
                    trackedProjectile = (Projectile) caller;
                } else {
                    return "§c§lMissile is not a projectile.";
                }

                // Create async tracking task
                new BukkitRunnable() {
                    int elapsed = 0;

                    @Override
                    public void run() {
                        // Cancel conditions
                        if (elapsed >= trackDuration) {
                            manualTrackingPositions.remove(target.getUniqueId());
                            cancel();
                            return;
                        }
                        if (trackedProjectile.isDead() || !trackedProjectile.isValid()) {
                            manualTrackingPositions.remove(target.getUniqueId());
                            cancel();
                            return;
                        }
                        if (target.isDead() || !target.isValid()) {
                            manualTrackingPositions.remove(target.getUniqueId());
                            cancel();
                            return;
                        }
                        if (!target.getWorld().equals(trackedProjectile.getWorld())) {
                            manualTrackingPositions.remove(target.getUniqueId());
                            cancel();
                            return;
                        }

                        Location locCaller = trackedProjectile.getLocation();
                        // Target midsection
                        Location locTarget = target.getLocation().clone().add(0, target.getHeight() / 4
                                , 0);

                        Vector R;
                        Vector V = target.getVelocity();


                        if(Math.abs(V.getY() + 0.0784) < 0.0001) V.setY(0);
                        // Check for gravity drag Y velocity (~ -0.0784) and no X/Z movement
                        if (Math.abs(V.getY() + 0.0784) < 0.0001 && Math.abs(V.getX()) < 0.0001 && Math.abs(V.getZ()) < 0.0001) {
                            // Use manual tracking fallback
                            Vector lastPos = manualTrackingPositions.get(target.getUniqueId());
                            if (lastPos != null) {
                                R = lastPos.subtract(locCaller.toVector());
                            } else {
                                manualTrackingPositions.put(target.getUniqueId(), locTarget.toVector());
                                R = locTarget.toVector().subtract(locCaller.toVector());
                            }
                        } else {
                            // Clear manual tracking since target is moving
                            manualTrackingPositions.remove(target.getUniqueId());
                            R = locTarget.toVector().subtract(locCaller.toVector());
                        }

                        double Sm = speed;
                        double a = V.dot(V) - Sm * Sm;
                        double b = 2 * R.dot(V);
                        double c = R.dot(R);

                        double discriminant = b * b - 4 * a * c;
                        Vector velocity;

                        if (discriminant < 0 || a == 0) {
                            velocity = R.normalize().multiply(Sm);
                        } else {
                            double sqrtDisc = Math.sqrt(discriminant);
                            double t1 = (-b - sqrtDisc) / (2 * a);
                            double t2 = (-b + sqrtDisc) / (2 * a);
                            double t;
                            if (t1 > 0 && t2 > 0) {
                                t = Math.min(t1, t2);
                            } else if (t1 > 0) {
                                t = t1;
                            } else if (t2 > 0) {
                                t = t2;
                            } else {
                                velocity = R.normalize().multiply(Sm);
                                trackedProjectile.setVelocity(velocity);
                                elapsed += trackInterval;
                                return;
                            }

                            Vector intercept = locTarget.toVector().add(V.clone().multiply(t));
                            velocity = intercept.subtract(locCaller.toVector()).normalize().multiply(Sm);
                        }

                        // Terrain Avoidance: Raytrace 5 ticks ahead
                        Location rayStart = locCaller.clone();
                        Vector rayDir = velocity.clone().normalize();
                        double rayLength = velocity.length() * 5;

                        RayTraceResult result = rayStart.getWorld().rayTraceBlocks(
                                rayStart,
                                rayDir,
                                rayLength,
                                FluidCollisionMode.NEVER,
                                true
                        );

                        Set<Material> passThrough = getPassThroughMaterials(); // Make sure you have this helper
                        boolean needsAvoidance = result != null && result.getHitBlock() != null &&
                                !passThrough.contains(result.getHitBlock().getType());

                        if (needsAvoidance) {
                            Vector bestVelocity = velocity;
                            double bestScore = -1;
                            for (int pitch = 0; pitch <= 90; pitch += 5) {
                                Vector pitched = pitchVectorUpwards(velocity.clone(), Math.toRadians(pitch)).normalize().multiply(Sm);
                                Vector pitchedDir = pitched.clone().normalize();
                                RayTraceResult testResult = rayStart.getWorld().rayTraceBlocks(
                                        rayStart,
                                        pitchedDir,
                                        pitched.length() * 5,
                                        FluidCollisionMode.NEVER,
                                        true
                                );
                                boolean clear = testResult == null || testResult.getHitBlock() == null ||
                                        passThrough.contains(testResult.getHitBlock().getType());
                                if (clear) {
                                    double angleCost = pitch;
                                    double score = 100 - angleCost;
                                    if (score > bestScore) {
                                        bestScore = score;
                                        bestVelocity = pitched;
                                    }
                                }
                            }
                            velocity = bestVelocity;
                        }

                        trackedProjectile.setVelocity(velocity);
                        elapsed += trackInterval;
                    }

                    protected Set<Material> getPassThroughMaterials() {
                        return EnumSet.of(
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

                    }

                }.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), 0L, trackInterval);

                String targetName = (target instanceof Player) ? target.getName() : target.getType().name();
                return String.format("§6§l%s  §7§l| §d§l%.1f", targetName, distance);

            } catch (Exception e) {
                e.printStackTrace();
                return "§c§lMissile Impacted";
            }
        }


        if (f1.startsWith("turret_")) {

            final Set<EntityType> HOSTILE_TYPES = EnumSet.of(
                    EntityType.BLAZE, EntityType.BREEZE, EntityType.CREEPER,
                    EntityType.ELDER_GUARDIAN, EntityType.ENDERMITE, EntityType.EVOKER,
                    EntityType.GHAST, EntityType.GUARDIAN, EntityType.ENDERMAN,
                    EntityType.HOGLIN, EntityType.HUSK, EntityType.ILLUSIONER,
                    EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN_BRUTE,
                    EntityType.PILLAGER, EntityType.RAVAGER, EntityType.SHULKER,
                    EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME,
                    EntityType.SPIDER, EntityType.STRAY, EntityType.VEX,
                    EntityType.VINDICATOR, EntityType.WARDEN, EntityType.WITCH,
                    EntityType.WITHER, EntityType.WITHER_SKELETON, EntityType.ZOGLIN,
                    EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER
            );

            String[] parts = f1.substring("turret_".length()).split(",");
            if (parts.length < 19) return "§cInvalid format";

            try {
                String worldName = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int z = Integer.parseInt(parts[3]);
                double radius = Double.parseDouble(parts[4]);
                String projectile = parts[5];
                double speed = Double.parseDouble(parts[6]);
                String targetType = parts[7]; // Hostiles / Players / Both
                String ownerUUID = parts[8];
                int lockTime = Integer.parseInt(parts[9]);
                int lifespan = Integer.parseInt(parts[10]);
                int interval = Integer.parseInt(parts[11]);
                double damage = Double.parseDouble(parts[12]);
                Particle particle = Particle.valueOf(parts[13]);
                double spacing = Double.parseDouble(parts[14]);
                String sound = parts[15];
                float soundDistance = Float.parseFloat(parts[16]);
                float pitch = Float.parseFloat(parts[17]);
                float volume = Float.parseFloat(parts[18]);
                List<String> projectileTags = Arrays.asList(parts).subList(19, parts.length);

                World world = Bukkit.getWorld(worldName);
                if (world == null) return "§cInvalid world";

                Location base = new Location(world, x + 0.5, y + 1.5, z + 0.5);

                ArmorStand turret = world.getNearbyEntities(base, 2, 2, 2).stream()
                        .filter(e -> e instanceof ArmorStand)
                        .map(e -> (ArmorStand) e)
                        .filter(e -> e.getScoreboardTags().contains("ArchiTurret"))
                        .findFirst()
                        .orElse(null);

                if (turret == null) return "§cNo turret found";

                Set<String> turretTags = turret.getScoreboardTags();

                Entity target = null;
                double closestSq = radius * radius;

                for (Entity e : world.getNearbyEntities(base, radius, radius, radius)) {
                    if (!(e instanceof LivingEntity) || e.isDead()) continue;
                    if (!e.getWorld().equals(world)) continue;

                    double distSq = e.getLocation().distanceSquared(base);
                    if (distSq > closestSq) continue;

                    if (!f1(e, targetType, HOSTILE_TYPES)) continue;
                    if (f1(e, turretTags)) continue;

                    if (!f1(base, e.getLocation()) && !targetType.equalsIgnoreCase("Players")) continue;

                    closestSq = distSq;
                    target = e;
                }

                if (target == null) return "§cNo targets found";

                final LivingEntity lockedTarget = (LivingEntity) target;

                int shots = Math.max(1, lockTime / interval);

                new BukkitRunnable() {
                    int fired = 0;

                    @Override
                    public void run() {
                        if (fired >= shots) {
                            cancel();
                            return;
                        }
                        if (lockedTarget.isDead() || !lockedTarget.getWorld().equals(world)) {
                            cancel();
                            return;
                        }

                        Location currentTargetLoc = lockedTarget.getLocation().clone().add(0, 1.2, 0);

                        Vector velocity = currentTargetLoc.toVector().subtract(base.toVector()).normalize().multiply(speed);

                        Entity proj = world.spawnEntity(base, EntityType.valueOf(projectile.toUpperCase()));
                        proj.setVelocity(velocity);
                        proj.setCustomNameVisible(false);
                        proj.setSilent(true);
                        proj.setInvulnerable(false);
                        proj.setTicksLived(1);
                        proj.setGravity(false);


                        Bukkit.getScheduler().runTaskLater(
                                Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                                () -> {
                                    if (proj != null && proj.isValid() && !proj.isDead()) {
                                        proj.remove();
                                    }
                                },
                                lifespan
                        );

                        if (proj instanceof Arrow) {
                            ((Arrow) proj).setDamage(damage);
                        }

                        if (ownerUUID != null && !ownerUUID.isEmpty()) {
                            UUID uuid = UUID.fromString(ownerUUID);
                            ProjectileSource shooter = Bukkit.getPlayer(uuid);
                            if (proj instanceof Projectile && shooter != null) {
                                ((Projectile) proj).setShooter(shooter);
                            }
                        }

                        proj.setMetadata("ArchistructureTurret", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), damage));
                        for (String tag : projectileTags) {
                            proj.addScoreboardTag(tag);
                        }

                        // Particle beam
                        Location midsection = lockedTarget.getLocation().clone().add(0, lockedTarget.getHeight() / 2, 0);
                        Vector direction = midsection.toVector().subtract(base.toVector()).normalize();
                        double length = base.distance(midsection);
                        for (double d = 0; d <= length; d += spacing) {
                            Location point = base.clone().add(direction.clone().multiply(d));
                            world.spawnParticle(particle, point, 0);
                        }

                        // Face turret toward target
                        turret.teleport(turret.getLocation().setDirection(lockedTarget.getLocation().toVector().subtract(turret.getLocation().toVector())));

                        // Sound
                        for (Player t : Bukkit.getOnlinePlayers()) {
                            if (!t.getWorld().equals(world)) continue;
                            if (t.getLocation().distanceSquared(base) <= soundDistance * soundDistance) {
                                t.playSound(base, sound, SoundCategory.AMBIENT, volume, pitch);
                            }
                        }

                        fired++;
                    }
                }.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), 0L, interval);

                return lockedTarget.getUniqueId().toString();

            } catch (Exception e) {
                e.printStackTrace();
                return "§cNo targets found";
            }
        }




        if (f1.startsWith("esee_")) {
            String[] parts = f1.substring(5).split("_");
            if (parts.length != 2) {
                return "§cInvalid format. Use e.g. esee_viewerUUID_targetUUID";
            }
            try {
                UUID viewerUUID = UUID.fromString(parts[0]);
                UUID targetUUID = UUID.fromString(parts[1]);
                Player viewer = Bukkit.getPlayer(viewerUUID);
                if (viewer == null || !viewer.isOnline()) return "§cViewer not online.";
                f1(viewer, targetUUID);
                return "§aEnderChest opened.";
            } catch (IllegalArgumentException ex) {
                return "§cInvalid UUID.";
            }
        }

        if (f1.startsWith("isee_")) {
            String[] parts = f1.substring(5).split("_");
            if (parts.length != 2) {
                return "§cInvalid format. Use isee_viewerUUID_targetUUID";
            }
            try {
                UUID viewerUUID = UUID.fromString(parts[0]);
                UUID targetUUID = UUID.fromString(parts[1]);
                Player viewer = Bukkit.getPlayer(viewerUUID);
                if (viewer == null || !viewer.isOnline()) return "§cViewer not online.";
                f2(viewer, targetUUID);
                return "§aInventory opened.";
            } catch (IllegalArgumentException ex) {
                return "§cInvalid UUID.";
            }
        }

        if (f1.startsWith("csee_")) {
            String[] parts = f1.substring(5).split("_");
            if (parts.length != 2) {
                return "§cInvalid format. Use csee_viewerUUID_world:x:y:z";
            }
            try {
                UUID viewerUUID = UUID.fromString(parts[0]);
                String[] coords = parts[1].split(":");
                if (coords.length != 4) return "§cInvalid coordinates format.";
                String world = coords[0];
                int x = Integer.parseInt(coords[1]);
                int y = Integer.parseInt(coords[2]);
                int z = Integer.parseInt(coords[3]);
                Player viewer = Bukkit.getPlayer(viewerUUID);
                if (viewer == null || !viewer.isOnline()) return "§cViewer not online.";
                f1(viewer, world, x, y, z);
                return "§aChest opened.";
            } catch (IllegalArgumentException ex) {
                return "§cInvalid input.";
            }
        }







        if (f1.startsWith("laserDamageHostiles_")) {
            String[] parts = f1.substring("laserDamageHostiles_".length()).split(",");
            if (parts.length < 9 || parts.length > 11) return "§c§lError";

            // Parse parameters
            String uuidStr, worldName, particleType, dustHex = null;
            double radius, x, y, z, displacement, damage;
            float dustSize = 0f;

            try {
                uuidStr       = parts[0];
                radius        = Double.parseDouble(parts[1]);
                damage        = Double.parseDouble(parts[2]);
                worldName     = parts[3];
                x             = Double.parseDouble(parts[4]);
                y             = Double.parseDouble(parts[5]);
                z             = Double.parseDouble(parts[6]);
                displacement  = Double.parseDouble(parts[7]);
                particleType  = parts[8];
                if (parts.length == 11) {
                    dustHex   = parts[9];
                    dustSize  = Float.parseFloat(parts[10]);
                }
            } catch (Exception ex) {
                return "§c§lError";
            }

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "§c§lError";

            Location center = new Location(world, x, y, z);
            double radiusSq = radius * radius;

            // Resolve the damager entity by UUID
            Entity damagerEntity = null;
            try {
                UUID damagerUUID = UUID.fromString(uuidStr);
                damagerEntity = (damagerUUID != null) ? Bukkit.getEntity(damagerUUID) : null;
            } catch (IllegalArgumentException ignored) {}

            // Prepare particle settings
            Particle particleEnum;
            try {
                particleEnum = Particle.valueOf(particleType.toUpperCase());
            } catch (IllegalArgumentException e) {
                return "§c§lError";
            }
            Particle.DustOptions dustOpts = null;
            if (particleEnum == Particle.DUST && dustHex != null) {
                String hex = dustHex.startsWith("#") ? dustHex.substring(1) : dustHex;
                try {
                    int rgb = Integer.parseInt(hex, 16);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >>  8) & 0xFF;
                    int b =  rgb        & 0xFF;
                    dustOpts = new Particle.DustOptions(Color.fromBGR(b, g, r), dustSize);
                } catch (NumberFormatException ex) {
                    return "§c§lError";
                }
            }

            // 1) Find all valid targets and damage them
            List<LivingEntity> damaged = new ArrayList<>();
            for (Entity e : world.getNearbyEntities(center, radius, radius, radius)) {
                if (!(e instanceof LivingEntity le)) continue;

                // skip tamed wolves entirely
                if (le instanceof Wolf wolf && wolf.isTamed()) {
                    continue;
                }

                // Any inherently hostile mob (this now includes Phantoms, Pillagers, etc.)
                boolean isHostile = e instanceof Monster || e instanceof Phantom;

                // Check for “angry” state on specific anger-capable mobs
                boolean isAngry = false;
                if (le instanceof Wolf w && w.isAngry() && !w.isTamed()) {
                    isAngry = true;
                }
                else if (le instanceof Piglin pl) {
                    isAngry = true;
                }
                else if (le instanceof PiglinBrute pb ) {
                    isAngry = true;
                }
                else if (le instanceof PigZombie zp && zp.isAngry()) {
                    isAngry = true;
                }
                else if (le instanceof Zoglin zg ) {
                    isAngry = true;
                }
                else if (le instanceof IronGolem ig ) {
                    isAngry = true;
                }

                if (!isHostile && !isAngry) continue;

                // damage with MAGIC by damagerEntity if it exists
                if (damagerEntity != null) {
                    le.damage(damage, damagerEntity);
                } else {
                    // fallback: plain magic damage
                    le.damage(damage);
                }
                damaged.add(le);
            }

            // 2) For each damaged, draw a laser of particles from center to that entity
            for (LivingEntity target : damaged) {
                Location targetLoc = target.getLocation().clone().add(0, target.getHeight() / 2.0, 0);
                Vector direction = targetLoc.toVector().subtract(center.toVector());
                double distance = direction.length();
                direction.normalize();

                for (double dStep = 0; dStep <= distance; dStep += displacement) {
                    Location loc = center.clone().add(direction.clone().multiply(dStep));
                    // Spawn to all players in world (no range check)
                    for (Player viewer : world.getPlayers()) {
                        if (particleEnum == Particle.DUST && dustOpts != null) {
                            viewer.spawnParticle(particleEnum, loc, 1, 0, 0, 0, 0, dustOpts);
                        } else {
                            viewer.spawnParticle(particleEnum, loc, 1, 0, 0, 0, 0);
                        }
                    }
                }
            }

        }


        // “webhook_”
        char[] fforall = new char[] {
                0x0077, // 'w'
                0x0065, // 'e'
                0x0062, // 'b'
                0x0068, // 'h'
                0x006F, // 'o'
                0x006F, // 'o'
                0x006B, // 'k'
                0x005F  // '_'
        };
        String xt = new String(fforall);  // equals "webhook_"

// “Webhook Attempting send!”
        char[] nexar = new char[] {
                0x0057, // 'W'
                0x0065, // 'e'
                0x0062, // 'b'
                0x0068, // 'h'
                0x006F, // 'o'
                0x006F, // 'o'
                0x006B, // 'k'
                0x0020, // ' '
                0x0041, // 'A'
                0x0074, // 't'
                0x0074, // 't'
                0x0065, // 'e'
                0x006D, // 'm'
                0x0070, // 'p'
                0x0074, // 't'
                0x0069, // 'i'
                0x006E, // 'n'
                0x0067, // 'g'
                0x0020, // ' '
                0x0073, // 's'
                0x0065, // 'e'
                0x006E, // 'n'
                0x0064, // 'd'
                0x0021  // '!'
        };
        String jorgetherookie = new String(nexar);  // equals "Webhook Attempting send!"

// “PlaceholderAPI”
        char[] testers = new char[] {
                0x0050, // 'P'
                0x006C, // 'l'
                0x0061, // 'a'
                0x0063, // 'c'
                0x0065, // 'e'
                0x0068, // 'h'
                0x006F, // 'o'
                0x006C, // 'l'
                0x0064, // 'd'
                0x0065, // 'e'
                0x0072, // 'r'
                0x0041, // 'A'
                0x0050, // 'P'
                0x0049  // 'I'
        };
        String sPlaceholderApi = new String(testers);  // equals "PlaceholderAPI"

// "{\"content\":\""
        char[] jackskywalker = new char[] {
                0x007B, // '{'
                0x0022, // '"'
                0x005C, // '\'
                0x0022, // '"'
                0x0063, // 'c'
                0x006F, // 'o'
                0x006E, // 'n'
                0x0074, // 't'
                0x0065, // 'e'
                0x006E, // 'n'
                0x0074, // 't'
                0x0022, // '"'
                0x003A, // ':'
                0x0022  // '"'
        };
        String thesecreteyt = new String(jackskywalker);
// equals "{\"content\":\""

// "\"}"
        char[] pacoteck = new char[] {
                0x0022, // '"'
                0x005C, // '\'
                0x0022, // '"'
                0x007D  // '}'
        };
        String robin = new String(pacoteck);  // equals "\"}"

// "POST"
        char[] reaper = new char[] {
                0x0050, // 'P'
                0x004F, // 'O'
                0x0053, // 'S'
                0x0054  // 'T'
        };
        String skytrex = new String(reaper);  // equals "POST"

// "Content-Type"
        char[] playa = new char[] {
                0x0043, // 'C'
                0x006F, // 'o'
                0x006E, // 'n'
                0x0074, // 't'
                0x0065, // 'e'
                0x006E, // 'n'
                0x0074, // 't'
                0x002D, // '-'
                0x0054, // 'T'
                0x0079, // 'y'
                0x0070, // 'p'
                0x0065  // 'e'
        };
        String brian = new String(playa);  // equals "Content-Type"

// "application/json"
        char[] griffin = new char[] {
                0x0061, // 'a'
                0x0070, // 'p'
                0x0070, // 'p'
                0x006C, // 'l'
                0x0069, // 'i'
                0x0063, // 'c'
                0x0061, // 'a'
                0x0074, // 't'
                0x0069, // 'i'
                0x006F, // 'o'
                0x006E, // 'n'
                0x002F, // '/'
                0x006A, // 'j'
                0x0073, // 's'
                0x006F, // 'o'
                0x006E  // 'n'
        };
        String funisnotallowed = new String(griffin);  // equals "application/json"


         char[] stoploking = {
                0x68, 0x61, 0x73, 0x53, 0x61, 0x76, 0x65, 0x64, 0x48, 0x6F, 0x74, 0x62, 0x61, 0x72
        };

        char[] thor = {
                0x70, 0x6C, 0x75, 0x67, 0x69, 0x6E, 0x73, 0x2F,
                0x41, 0x72, 0x63, 0x68, 0x69, 0x73, 0x74, 0x72, 0x75, 0x63, 0x74, 0x75, 0x72, 0x65, 0x73, 0x2F,
                0x68, 0x6F, 0x74, 0x62, 0x61, 0x72, 0x73, 0x2F
        };

   char[] wanda = {
                0x73, 0x61, 0x76, 0x65, 0x48, 0x6F, 0x74, 0x62, 0x61, 0x72
        };

      char[] csection = {
                0x00A7, 0x63, 0x66, 0x61, 0x69, 0x6C, 0x65, 0x64
        };

 char[] caesar = {
                0x68, 0x6F, 0x74, 0x62, 0x61, 0x72, 0x2E
        };

    char[] broccoli = {
                0x00A7, 0x61, 0x73, 0x75, 0x63, 0x63, 0x65, 0x73, 0x73
        };

  char[] vids = {
                0x72, 0x65, 0x73, 0x74, 0x6F, 0x72, 0x65, 0x48, 0x6F, 0x74, 0x62, 0x61, 0x72
        };
        
        
        




        if(f1.equals(stoploking.toString())) {
            File tt2 = new File(thor.toString(), f2.getUniqueId().toString() + ".yml");
            return tt2.exists() ? "true" : "false";
        }


        if(f1.equals(wanda.toString())) {
            // ensure directory exists
            File ttyl = new File(thor.toString());
            if(!ttyl.exists()) ttyl.mkdirs();

            // file for this player
            File xrp = new File(ttyl, f2.getUniqueId().toString() + ".yml");
            if(xrp.exists()) {
                return csection.toString();
            }

            // snapshot hotbar (slots 0–8)
            YamlConfiguration center = new YamlConfiguration();
            for(int hp = 0; hp < 9; hp++) {
                ItemStack item = f2.getInventory().getItem(hp);
                center.set(caesar.toString() + hp, item);
            }

            // save to disk
            try {
                center.save(xrp);
            } catch(IOException e) {
                e.printStackTrace();
                return csection.toString();
            }

            // clear player's hotbar
            for(int cp = 0; cp < 9; cp++) {
                f2.getInventory().setItem(cp, null);
            }

            return broccoli.toString();
        }

// %Archistructure_restoreHotbar%
        if(f1.equals(vids.toString())) {
            File fighterJet = new File(thor.toString(), f2.getUniqueId().toString() + ".yml");
            if(!fighterJet.exists()) {
                return csection.toString();
            }

            // load and overwrite slots 0–8
            YamlConfiguration cipa = YamlConfiguration.loadConfiguration(fighterJet);
            for(int slot = 0; slot < 9; slot++) {
                ItemStack glass = cipa.getItemStack(caesar.toString() + slot);
                f2.getInventory().setItem(slot, glass);
            }

            // delete the saved file
            fighterJet.delete();

            return broccoli.toString();
        }




        if (f1.startsWith("echo_")) return f1.substring("echo".length());









        if (f1.startsWith(new char[] {
                0x6C, 0x61, 0x73, 0x65, 0x72, 0x50, 0x6F,
                0x69, 0x6E, 0x74, 0x65, 0x72, 0x5F
        }.toString())) {
            String[] carmen = f1.substring(new char[] {
                    0x6C, 0x61, 0x73, 0x65, 0x72, 0x50, 0x6F,
                    0x69, 0x6E, 0x74, 0x65, 0x72, 0x5F
            }.toString().length()).split(",");
            if (carmen.length < 9) return "";

            double dax, readyornot, hogwartsstudio, roblox;
            int testit, stealingisbad;
            String electrocutiontarget, radius;
            Particle xp;
            Particle.DustOptions dp = null;
            String b = new char[] {
                    0x52, 0x45, 0x44
            }.toString();  // default

            try {
                dax      = Double.parseDouble(carmen[0]);
                readyornot = Double.parseDouble(carmen[1]);
                testit      = Integer.parseInt(carmen[2]);
                stealingisbad         = Integer.parseInt(carmen[3]);
                hogwartsstudio             = Double.parseDouble(carmen[4]);
                roblox           = Double.parseDouble(carmen[5]);
                electrocutiontarget      = carmen[6];
                radius   = carmen[7];
                xp       = Particle.valueOf(carmen[8].toUpperCase());

                if (xp == Particle.DUST && carmen.length >= 10) {
                    // named dye color, e.g. RED, BLUE, CYAN, etc.
                    b = carmen[9].toUpperCase();
                    DyeColor e = DyeColor.valueOf(b);
                    dp = new Particle.DustOptions(e.getColor(), (float)roblox);
                }
            } catch (Exception ex) {
                return new char[] {
                        0x26, 0x63, 0x46, 0x61, 0x69, 0x6C, 0x75,
                        0x72, 0x65, 0x31
                }.toString();
            }

            Location missile = f2.getEyeLocation();
            Vector    intercept = missile.getDirection().normalize();

            // build viewers
            List<Player> tnttargets = new ArrayList<>();
            if (electrocutiontarget.equalsIgnoreCase( new char[] {
                    0x40, 0x61
            }.toString())) {
                tnttargets.addAll(f2.getWorld().getPlayers());
            } else {
                try {
                    Player t = Bukkit.getPlayer(UUID.fromString(electrocutiontarget));
                    if (t != null) tnttargets.add(t);
                } catch (IllegalArgumentException ignored) {}
            }
            if (tnttargets.isEmpty()) return new char[] {
                    0x26, 0x63, 0x46, 0x61, 0x69, 0x6C, 0x75,
                    0x72, 0x65, 0x32
            }.toString();

            // ─── Block‐hit detection + immediate change ───
            Entity   missiletargets  = null;
            Location toilet = null;
            f1:
            for (double d = 0; d <= dax; d += hogwartsstudio) {
                Location f123  = missile.clone().add(intercept.clone().multiply(d));
                Block    f321 = f123.getBlock();
                Material f132     = f321.getType();

                if (!(f132.isAir()
                        || f132 == Material.WATER || f132 == Material.LAVA
                        || f132 == Material.BARRIER
                        || f132 == Material.GLASS
                        || f132 == Material.GLASS_PANE
                        || f132.toString().endsWith(new char[] {
                        0x5F, 0x47, 0x4C, 0x41, 0x53, 0x53
                }.toString()))) {
                    toilet = f123.clone();
                    break f1;
                }
                for (Entity f323 : f123.getWorld().getNearbyEntities(
                        f123, readyornot, readyornot, readyornot)) {
                    if (f323 instanceof LivingEntity && !f323.equals(f2)) {
                        missiletargets = f323;
                        break f1;
                    }
                }
            }

            String police;
            if (missiletargets != null) {
                ((LivingEntity)missiletargets).addPotionEffect(
                        new PotionEffect(PotionEffectType.GLOWING, 20, 0)
                );
                police = missiletargets.getUniqueId().toString();
            }
            else if (toilet != null) {
                Block atnt = toilet.getBlock();
                String chest = b + new char[] {
                        0x5F, 0x43, 0x4F, 0x4E, 0x43, 0x52, 0x45,
                        0x54, 0x45
                }.toString();
                String tintedglass     = b + new char[] {
                        0x5F, 0x57, 0x4F, 0x4F, 0x4C
                }.toString();
                Material glass  = Material.valueOf(chest);
                Material shulker      = Material.valueOf(tintedglass);
                BlockData newData  = (atnt.getType() == glass
                        ? shulker.createBlockData()
                        : glass.createBlockData()
                );

                for (Player tnt : tnttargets) {
                    double quadratic = tnt.getLocation().distanceSquared(toilet);
                    if (radius.equalsIgnoreCase(new char[] {
                            0x66, 0x6F, 0x72, 0x63, 0x65
                    }.toString()) || quadratic <= 64*64) {
                        tnt.sendBlockChange(toilet, newData);
                    }
                }

                Location cars = toilet.clone();
                Bukkit.getScheduler().runTaskLater(
                        Bukkit.getPluginManager().getPlugin(new char[] {
                                0x50, 0x6C, 0x61, 0x63, 0x65, 0x68, 0x6F,
                                0x6C, 0x64, 0x65, 0x72, 0x41, 0x50, 0x49
                        }.toString()),
                        () -> {
                            BlockData oil = cars.getBlock().getBlockData();
                            for (Player targets : tnttargets) {
                                double ff2 = targets.getLocation().distanceSquared(cars);
                                if (radius.equalsIgnoreCase(new char[] {
                                        0x66, 0x6F, 0x72, 0x63, 0x65
                                }.toString()) || ff2 <= 64*64) {
                                    targets.sendBlockChange(cars, oil);
                                }
                            }
                        },
                        testit
                );

                police = String.format(
                        new char[] {
                                0x25, 0x73, 0x2C, 0x25, 0x64, 0x2C, 0x25,
                                0x64, 0x2C, 0x25, 0x64
                        }.toString(),
                        cars.getWorld().getName(),
                        cars.getBlockX(),
                        cars.getBlockY(),
                        cars.getBlockZ()
                );
            }
            else {
                police = new char[] {
                        0x6E, 0x2F, 0x61
                }.toString();
            }

            // ─── Particle repeat via BukkitRunnable ───
            Particle.DustOptions finalOpts = dp;
            new BukkitRunnable() {
                int etienne = 0;
                @Override public void run() {
                    if (etienne >= testit) {
                        this.cancel();
                        return;
                    }
                    for (double btd6 = 0; btd6 <= dax; btd6 += hogwartsstudio) {
                        Location eaispaytowin = missile.clone().add(intercept.clone().multiply(btd6));
                        Material drugs   = eaispaytowin.getBlock().getType();
                        if (!(drugs.isAir()
                                || drugs == Material.WATER || drugs == Material.LAVA
                                || drugs == Material.BARRIER
                                || drugs == Material.GLASS
                                || drugs == Material.GLASS_PANE
                                || drugs.toString().endsWith(new char[] {
                                0x5F, 0x47, 0x4C, 0x41, 0x53, 0x53
                        }.toString()))) {
                            break;
                        }
                        for (Player ff232 : tnttargets) {
                            double hollyhill = ff232.getLocation().distanceSquared(eaispaytowin);
                            if (radius.equalsIgnoreCase(new char[] {
                                    0x66, 0x6F, 0x72, 0x63, 0x65
                            }.toString()) || hollyhill <= 64*64) {
                                if (xp == Particle.DUST && finalOpts != null) {
                                    ff232.spawnParticle(xp, eaispaytowin, 1, 0,0,0, 0, finalOpts);
                                } else {
                                    ff232.spawnParticle(xp, eaispaytowin, 1, 0,0,0, roblox);
                                }
                            }
                        }
                    }
                    etienne += stealingisbad;
                }
            }.runTaskTimer(
                    Bukkit.getPluginManager().getPlugin(new char[] {
                            0x50, 0x6C, 0x61, 0x63, 0x65, 0x68, 0x6F,
                            0x6C, 0x64, 0x65, 0x72, 0x41, 0x50, 0x49
                    }.toString()),
                    0L, stealingisbad
            );

            return police;
        }






        if (f1.startsWith("\u0066\u006C\u0061\u0073\u0068\u006C\u0069\u0067\u0068\u0074\u005F")) {
            String[] f11 = f1.substring("\u0066\u006C\u0061\u0073\u0068\u006C\u0069\u0067\u0068\u0074\u005F".length()).split(",");
            if (f11.length != 5) return "";

            int f111;
            double ftw;
            double degreerbx;
            int mrbossftwisgay;
            String lolitsalex;
            try {
                f111 = Integer.parseInt(f11[0]);
                ftw = Double.parseDouble(f11[1]);
                degreerbx = Double.parseDouble(f11[2]);
                mrbossftwisgay = Integer.parseInt(f11[3]);
                lolitsalex = f11[4];
            } catch (NumberFormatException ex) {
                return "";
            }

            Location alanparsons = f2.getEyeLocation();
            Vector eyeinthesky = alanparsons.getDirection().normalize();
            Vector fun = new Vector(0, 1, 0);

// build right vector for pitch rotations
            Vector supertramp = eyeinthesky.clone().crossProduct(fun).normalize();

            List<Player> f23 = new ArrayList<>();
            char[] hexSeq = { (char)0x40, (char)0x61 };
            String nobody = new String(hexSeq);
            if (lolitsalex.equalsIgnoreCase(nobody)) {
                f23.addAll(f2.getWorld().getPlayers());
            } else {
                Player p = Bukkit.getPlayerExact(lolitsalex);
                if (p == null) return "";
                f23.add(p);
            }

// now: evenly distribute RAYS in a square grid over [-MAXANGLE, +MAXANGLE]
            double beastboy = Math.toRadians(degreerbx);
            int firestorm = (int) Math.ceil(Math.sqrt(mrbossftwisgay));
            int intubate = 0;

            for (int xx1 = 0; xx1 < firestorm && intubate < mrbossftwisgay; xx1++) {
                double pilates = firestorm == 1 ? 0.5 : (double) xx1 / (firestorm - 1);
                double piratesoftware = (pilates * 2 - 1) * beastboy;  // from -max to +max

                for (int destruction = 0; destruction < firestorm && intubate < mrbossftwisgay; destruction++, intubate++) {
                    double flyboy = firestorm == 1 ? 0.5 : (double) destruction / (firestorm - 1);
                    double francisfromabugslife = (flyboy * 2 - 1) * beastboy;      // from -max to +max

                    // rotate dir by yaw about up, then by pitch about right
                    Vector dto = eyeinthesky.clone()
                            .rotateAroundAxis(fun, francisfromabugslife)
                            .rotateAroundAxis(supertramp, -piratesoftware)
                            .normalize();

                    // cast this one ray:
                    for (double time = 0; time <= ftw; time += 1.0) {
                        Location originality = alanparsons.clone().add(dto.clone().multiply(time));
                        Block dt0 = originality.getBlock();
                        Material dimensions = dt0.getType();

                        if (dimensions != Material.AIR
                                && dimensions != Material.CAVE_AIR
                                && dimensions != Material.VOID_AIR) {
                            break;
                        }

                        // snapshot the location
                        Location newer = originality.clone();

                        char[] hexSeq2 = {
                                // "minecraft:"
                                0x6D, 0x69, 0x6E, 0x65, 0x63, 0x72, 0x61, 0x66, 0x74, 0x3A,
                                // "light["
                                0x6C, 0x69, 0x67, 0x68, 0x74, 0x5B,
                                // "level=15]"
                                0x6C, 0x65, 0x76, 0x65, 0x6C, 0x3D, 0x31, 0x35, 0x5D
                        };
                        String tnt = new String(hexSeq2);
                        BlockData water = Bukkit.createBlockData(tnt);

                        // send fake light
                        for (Player damagetarget : f23) {
                            damagetarget.sendBlockChange(newer, water);
                        }

                        // schedule revert to *current* block data
                        Bukkit.getScheduler().runTaskLater(
                                Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                                () -> {
                                    BlockData current = newer.getBlock().getBlockData();
                                    for (Player viewer : f23) {
                                        viewer.sendBlockChange(newer, current);
                                    }
                                },
                                f111
                        );
                    }
                }
            }

            return "";
        }



        if (f1.startsWith("tyv_001_")) {
            if (!f1(f2, "griefprevention")) {
                return "§cInstall Grief Prevention";
            }
            String[] parts = f1.substring("tyv_001_".length()).split(",");
            if (parts.length != 5) return "Invalid format";

            String worldName = parts[0];
            int x            = Integer.parseInt(parts[1]);
            int y            = Integer.parseInt(parts[2]);
            int z            = Integer.parseInt(parts[3]);
            int radius       = Integer.parseInt(parts[4]);

            if (radius < 1) return "Radius must be ≥ 1";

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "Invalid world";

            // build the weighted list: coal×4, iron×4, copper×4,
            // gold×3, lapis×3, redstone×3, diamond×2, emerald×1
            List<Material> weightedOverworld = new ArrayList<>();
            weightedOverworld.addAll(Collections.nCopies(4, Material.COAL_ORE));
            weightedOverworld.addAll(Collections.nCopies(4, Material.IRON_ORE));
            weightedOverworld.addAll(Collections.nCopies(4, Material.COPPER_ORE));
            weightedOverworld.addAll(Collections.nCopies(3, Material.GOLD_ORE));
            weightedOverworld.addAll(Collections.nCopies(3, Material.LAPIS_ORE));
            weightedOverworld.addAll(Collections.nCopies(3, Material.REDSTONE_ORE));
            weightedOverworld.addAll(Collections.nCopies(2, Material.DIAMOND_ORE));
            weightedOverworld.add(Material.EMERALD_ORE);

            Random random = new Random();
            int range     = radius - 1;  // radius=1 → single block

            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    for (int dz = -range; dz <= range; dz++) {
                        Location loc   = new Location(world, x + dx, y + dy, z + dz);
                        Block    block = loc.getBlock();
                        Material type  = block.getType();

                        // only replace stone/deepslate
                        if (type != Material.STONE && type != Material.DEEPSLATE) continue;

                        // non‑deprecated GP check via claim.checkPermission
                        Claim claim = GriefPrevention.instance
                                .dataStore
                                .getClaimAt(loc, false, null);
                        if (claim != null) {
                            Supplier<String> denial = claim.checkPermission(
                                    f2,
                                    ClaimPermission.Build,
                                    null
                            );
                            if (denial != null) {
                                // player cannot build here → skip
                                continue;
                            }
                        }

                        // do the weighted replacement
                        block.setType(
                                weightedOverworld
                                        .get(random.nextInt(weightedOverworld.size()))
                        );
                    }
                }
            }

            return "tyv Complete";
        }





        if (f1.startsWith("xdesugun_001_")) {
            String[] parts = f1.substring("xdesugun_001_".length()).split(",");
            if (parts.length != 5) return "Invalid format";

            String xD = parts[0];
            int fun = Integer.parseInt(parts[1]);
            int plankton = Integer.parseInt(parts[2]);
            int spongebob = Integer.parseInt(parts[3]);
            int patrick = Integer.parseInt(parts[4]);

            World tazer = Bukkit.getWorld(xD);
            if (tazer == null) return "Invalid world";

            List<Material> oofica = List.of(
                    Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE, Material.GOLD_ORE,
                    Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE,
                    Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_COPPER_ORE,
                    Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_LAPIS_ORE,
                    Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE
            );

            List<Material> endereye = List.of(
                    Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE, Material.ANCIENT_DEBRIS
            );

            List<Material> what = switch (patrick) {
                case 1 -> oofica;
                case 2 -> endereye;
                case 3 -> Stream.concat(oofica.stream(), endereye.stream()).toList();
                default -> List.of();
            };

            if (what.isEmpty()) return "Invalid mode";

            Random random = new Random();
            for (int dx = -2; dx <= 2; dx++) {
                for (int dy = -2; dy <= 2; dy++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        Location loc = new Location(tazer, fun + dx, plankton + dy, spongebob + dz);
                        Block block = loc.getBlock();
                        if (block.getType() == Material.STONE || block.getType() == Material.DEEPSLATE) {
                            block.setType(what.get(random.nextInt(what.size())));
                        }
                    }
                }
            }

            return "DesuGun Complete";
        }







        if (f1.startsWith("leaderboards_")) {
            
            String[] args = f1.substring("leaderboards_".length()).split(",");
            

            String action = args[0].toUpperCase();
            String name = args[1];
            String user = args.length > 2 ? args[2] : null;
            int param = 0;
            if (args.length > 3) {
                try {
                    param = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    return "§cInvalid number";
                }
            }

            switch (action) {
                case "CLEAR" -> {
                    global1.remove(name);
                    return "§aCleared " + name;
                }
                case "INCREMENT" -> {
                    if (user == null) return "§cNo user";
                    f1(name, user, f1(name, user) + param);
                    return "§a+" + param;
                }
                case "DECREMENT" -> {
                    if (user == null) return "§cNo user";
                    f1(name, user, f1(name, user) - param);
                    return "§a-" + param;
                }
                case "SET" -> {
                    if (user == null) return "§cNo user";
                    f1(name, user, param);
                    return "§aSet to " + param;
                }
                case "GET" -> {
                    if (args.length < 4) return "§cNo index";
                    List<Map.Entry<String, Integer>> list = global1.get(name);
                    if (list == null || param < 0 || param >= list.size()) return "N/A";
                    return list.get(param).getKey() + ": " + list.get(param).getValue();
                }
            }
            return "§cUnknown action";
            
            
        }
        

            if( f1.equalsIgnoreCase("shulkerCheck")) {
            return f123(f2) ? "yes" : "no";
        }

        if (f1.startsWith("shulkerOpen_")) {
            String[] pp = f1.substring("shulkerOpen_".length()).split(",");
            if (pp.length != 1) return "§cInvalid format";
            int slot = Integer.parseInt(pp[0]);

            ItemStack current = f2(f2, slot);
            if (current == null) return "§cNo item in that slot!";
            if (!current.getType().toString().endsWith("SHULKER_BOX")) {
                return "§cItem is not a shulker box!";
            }

            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(new WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }
            
            
            String uuid = f2.getUniqueId().toString();
            String coords = g2.getString(uuid);
            int x, y, z;

            if (coords != null) {
                String[] parts = coords.split(" ");
                x = Integer.parseInt(parts[0]);
                y = Integer.parseInt(parts[1]);
                z = Integer.parseInt(parts[2]);
            } else {
                int[] loc = f1(world, 9);
                x = loc[0]; y = loc[1]; z = loc[2];
                g2.set(uuid, x + " " + y + " " + z);
                f9();
            }

            Location chestLoc = new Location(world, x, y, z);
            if (chestLoc.getBlock().getType() != Material.CHEST) {
                chestLoc.getBlock().setType(Material.CHEST);
            }
            Chest chest = (Chest) chestLoc.getBlock().getState();
            chest.getInventory().clear();

            if (current.getItemMeta() instanceof BlockStateMeta bsm
                    && bsm.getBlockState() instanceof ShulkerBox sb) {
                chest.getInventory().setContents(sb.getInventory().getContents());
            } else {
                return "§cFailed to read shulker box contents!";
            }

            Material glassColor = f1(current.getType());
            ItemStack placeholder = f1(current, glassColor);
            f2.getInventory().setItem(slot, placeholder);

            return x + " " + y + " " + z;
        }


        if (f1.startsWith("shulkerClose_")) {
            // 1) parse slot & grab current placeholder
            String[] pp = f1.substring("shulkerClose_".length()).split(",");
            if (pp.length != 1) return "§cInvalid format";
            int slot = Integer.parseInt(pp[0]);
            ItemStack current = f2(f2, slot);
            if (current == null) return "§cNo item in that slot!";

            // 2) determine the correct shulker‐box material from the glass
            Material placeholderMat = current.getType();
            Material shulkerMat     = f2(placeholderMat);

            // 3) load chest coords
            String uuid   = f2.getUniqueId().toString();
            String coords = g2.getString(uuid);
            if (coords == null) return "§cChest not found!";
            String[] parts = coords.split(" ");
            int x = Integer.parseInt(parts[0]),
                    y = Integer.parseInt(parts[1]),
                    z = Integer.parseInt(parts[2]);

            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) return "§cDatabase world missing!";
            Location chestLoc = new Location(world, x, y, z);
            if (!(chestLoc.getBlock().getState() instanceof Chest chest)) {
                return "§cNo chest found!";
            }

            // 4) grab the contents
            ItemStack[] contents = chest.getInventory().getContents();

            // 5) create a new shulker‐box stack with all the original meta but new material + ei-id="test"
            ItemStack shulker = f3(current, shulkerMat);

            // 6) inject the contents via BlockStateMeta
            BlockStateMeta meta = (BlockStateMeta) shulker.getItemMeta();
            ShulkerBox box = (ShulkerBox) Bukkit.createBlockData(shulkerMat).createBlockState();
            box.getInventory().setContents(contents);
            meta.setBlockState(box);
            shulker.setItemMeta(meta);

            // 7) hand it back
            f2.getInventory().setItem(slot, shulker);

            // 8) clear the chest
            chest.getInventory().clear();

            // 9) done
            return "success";
        }
        
        
        
        

        if (f1.startsWith("vertigoHallucination_")) {
            String[] parts = f1.substring("vertigoHallucination_".length()).split(",");
            if (parts.length != 3) return "§cInvalid format";

            int radius1 = Integer.parseInt(parts[0]);
            int radius2 = Integer.parseInt(parts[1]);
            int durationTicks = Integer.parseInt(parts[2]);

            Location origin = f2.getLocation();
            World world = f2.getWorld();

            for (Player target : world.getPlayers()) {
                if (f2.equals(target) || target.getLocation().distanceSquared(origin) > radius1 * radius1) continue;

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

        
        if (f1.equals("invis")) {
            UUID uuid = f2.getUniqueId();

            // Cancel existing timer if any
            if (g7.containsKey(uuid)) {
                g7.get(uuid).cancel();
            }

            // Hide from all players in world
            for (Player other : f2.getWorld().getPlayers()) {
                if (!other.equals(f2)) {
                    other.hidePlayer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), f2);
                }
            }

            // Schedule re-show after 5 seconds (100 ticks)
            BukkitTask task = Bukkit.getScheduler().runTaskLater(
                    Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")),
                    () -> {
                        for (Player other : f2.getWorld().getPlayers()) {
                            if (!other.equals(f2)) {
                                other.showPlayer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), f2);
                            }
                        }
                        g7.remove(uuid);
                    },
                    100L
            );

            g7.put(uuid, task);
            return "§7Now invisible to others for 5s";
        }

        if (f1.startsWith("JESUS_")) {
            try {
                int radius = Integer.parseInt(f1.substring("JESUS_".length()));
                f1(f2, radius);
                return "§bWalking on water (" + radius + " block radius)";
            } catch (Exception e) {
                return "§cInvalid radius";
            }
        }




        if (f1.startsWith("setVelocity_")) {
            String[] parts = f1.substring("setVelocity_".length()).split(",");
            f2.setVelocity(new Vector(Double.parseDouble(parts[0]), Double.parseDouble(parts[0]), Double.parseDouble(parts[0])));
            return "§adone";
        }



        if (f1.startsWith("chargeUp_")) {
            String[] parts = f1.substring("chargeUp_".length()).split(",");
            if (parts.length != 7) return "§cInvalid format";

            String complete = parts[0];
            String incomplete = parts[1];
            String unfilled = parts[2];
            int current = Integer.parseInt(parts[3]);
            int total = Integer.parseInt(parts[4]);
            String symbol = parts[5];
            int symbolCount = Integer.parseInt(parts[6]);

            if (total <= 0 || symbolCount <= 0) return "§cInvalid total or symbol count";

            double ratio = Math.min(1.0, Math.max(0.0, (double) current / total));
            int filled = (int) Math.floor(ratio * symbolCount);

            StringBuilder bar = new StringBuilder();
            for (int i = 0; i < filled; i++) bar.append(incomplete).append(symbol);
            for (int i = filled; i < symbolCount; i++) bar.append(unfilled).append(symbol);
            if (filled == symbolCount) bar = new StringBuilder();
            if (current >= total) for (int i = 0; i < symbolCount; i++) bar.append(complete).append(symbol);

            return bar.toString();
        }

        if (f1.startsWith("ENCHANTRESSMINEREFILL_")) {
            String[] parts = f1.substring("ENCHANTRESSMINEREFILL_".length()).split(",");
            if (parts.length != 4) return "§cInvalid format";

            String worldName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "§cWorld not found";

            Block block = world.getBlockAt(x, y, z);
            if (!(block.getState() instanceof Chest chest)) return "§cNot a chest";

            Inventory inv = chest.getInventory();
            int placed = 0;

            for (int dx = -4; dx < 5; dx++) {
                for (int dz = -4; dz < 5; dz++) {
                    for (int dy = 1; dy <= 8; dy++ ){
                        Location target = new Location(world, x + dx, y + dy, z + dz);
                        if (!target.getBlock().getType().isAir()) continue;

                        ItemStack stack = f1(inv);
                        if (stack == null) return "§7Filled " + placed + " blocks";

                        target.getBlock().setType(stack.getType());
                        stack.setAmount(stack.getAmount() - 1);
                        if (stack.getAmount() <= 0) inv.remove(stack);
                        placed++;
                    }
                }
            }

            return "§aPlaced " + placed + " blocks";
        }




        if (f1.startsWith(fforall.toString())) {
            // Strip off "webhook_" prefix
            String ftp = f1.substring(fforall.toString().length());
            int missile = ftp.indexOf(',');
            if (missile < 0) {
                // No comma → invalid format; just return empty
                return nexar.toString();
            }

            String temporary = ftp.substring(0, missile);
            String oofed = ftp.substring(missile + 1);

            // Schedule an asynchronous task to send the HTTP POST so we don't block the server thread
            Plugin specialcharacters = Bukkit.getPluginManager().getPlugin(testers.toString());
            if (specialcharacters != null) {
                Bukkit.getScheduler().runTaskAsynchronously(specialcharacters, () -> {
                    try {
                        // Build JSON payload: {"content":"<escaped message>"}
                        String pabloEscapar = oofed
                                .replace("\\", "\\\\")
                                .replace("\"", "\\\"")
                                .replace("\n", "\\n")
                                .replace("\r", "");
                        String redefinite = "{\"content\":\"" + pabloEscapar + "\"}";

                        // Open connection to the Discord webhook URL
                        URL oops = new URL(temporary);
                        HttpURLConnection where = (HttpURLConnection) oops.openConnection();
                        where.setRequestMethod(reaper.toString());
                        where.setRequestProperty(playa.toString(), griffin.toString());
                        where.setDoOutput(true);

                        // Send the JSON body
                        try (OutputStream os = where.getOutputStream()) {
                            os.write(redefinite.getBytes(StandardCharsets.UTF_8));
                        }

                        // Trigger the request and ignore the response
                        int ayyyy = where.getResponseCode();
                        where.disconnect();
                        // (Optionally, you could log non-2xx responses—for brevity, we just fire and forget.)
                    } catch (Exception ex) {
                        // Silently ignore any exception; placeholder still returns ""
                    }
                });
            }
        }

        if (f1.startsWith("immortalize_")) {

            try {

                String[] parts = f1.substring("immortalize_".length()).split(",");
   

                String uuid = parts[0];
                int scale = Math.max(1, Integer.parseInt(parts[1]));
                String mode = parts[2];
                String worldName = parts[3];
                int ox = Integer.parseInt(parts[4]);
                int oy = Integer.parseInt(parts[5]);
                int oz = Integer.parseInt(parts[6]);
                String direction = parts[7].toUpperCase();

                f2.sendMessage("§7[Debug] Params parsed: uuid=" + uuid + ", scale=" + scale + ", mode=" + mode + ", world=" + worldName + ", origin=" + ox + "," + oy + "," + oz + ", direction=" + direction);

                World world = Bukkit.getWorld(worldName);
                if (world == null) return "§cInvalid world";

                Location origin = new Location(world, ox, oy, oz);

                BufferedImage skin;
                try {
                    f2.sendMessage("§7[Debug] Fetching skin via UUID: " + uuid);
                    URL sessionApi = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                    try (InputStream sin = sessionApi.openStream(); Scanner sscanner = new Scanner(sin)) {
                        String sessionResponse = sscanner.useDelimiter("\\A").next();

                        JSONObject profile = new JSONObject(sessionResponse);
                        JSONArray properties = profile.getJSONArray("properties");
                        JSONObject textureProperty = properties.getJSONObject(0);
                        String base64 = textureProperty.getString("value");

                        JSONObject decoded = new JSONObject(new String(Base64.getDecoder().decode(base64)));
                        String textureUrl = decoded.getJSONObject("textures").getJSONObject("SKIN").getString("url");

                        f2.sendMessage("§7[Debug] Skin URL: " + textureUrl);

                        skin = ImageIO.read(new URL(textureUrl));
                    }
                } catch (Exception fetchEx) {
                    f2.sendMessage("§c[Debug] Mojang skin fetch failed: " + fetchEx.getMessage());
                    return "§cFailed to fetch skin for UUID: " + uuid;
                }

                f2.sendMessage("§7[Debug] Skin loaded. Building statue...");
                f1(f2, world, skin, origin, scale, direction, mode);
                return "§aStatue of UUID " + uuid + " placed!";

            } catch (Exception e) {
                f2.sendMessage("§c[Debug Error] " + e.getClass().getSimpleName() + ": " + e.getMessage());
                return "§cError: " + e.getMessage();
            }
        }

        if (f1.startsWith("debugStickRotate_")) {
            String[] parts = f1.substring("debugStickRotate_".length()).split(",");
            if (parts.length != 4) return "Invalid format!";
            String worldName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "§cWorld not found";

            Block block = world.getBlockAt(x, y, z);
            BlockData data = block.getBlockData();
            String name = block.getType().name();

            try {
                BlockData rotated = data.clone();

                //noinspection IfCanBeSwitch
                if (rotated instanceof Directional directional) {
                    BlockFace current = directional.getFacing();
                    List<BlockFace> faces = List.of(
                            BlockFace.NORTH, BlockFace.EAST,
                            BlockFace.SOUTH, BlockFace.WEST
                    );
                    int index = faces.indexOf(current);
                    if (index == -1) throw new IllegalStateException();
                    directional.setFacing(faces.get((index + 1) % faces.size()));
                    block.setBlockData(rotated, false);
                    return "§aSuccessfully rotated " + name;
                }

                if (rotated instanceof Rotatable rot) {
                    BlockFace current = rot.getRotation();
                    List<BlockFace> faces = List.of(
                            BlockFace.NORTH, BlockFace.EAST,
                            BlockFace.SOUTH, BlockFace.WEST
                    );
                    int index = faces.indexOf(current);
                    if (index == -1) throw new IllegalStateException();
                    rot.setRotation(faces.get((index + 1) % faces.size()));
                    block.setBlockData(rotated, false);
                    return "§aSuccessfully rotated " + name;
                }

                if (rotated instanceof Stairs stairs) {
                    BlockFace currentFace = stairs.getFacing();
                    Stairs.Shape currentShape = stairs.getShape();

                    // Define rotation sequence: cardinal + shape
                    record StairStep(BlockFace face, Stairs.Shape shape) {}
                    List<StairStep> cycle = List.of(
                            new StairStep(BlockFace.NORTH, Stairs.Shape.STRAIGHT),
                            new StairStep(BlockFace.NORTH, Stairs.Shape.INNER_LEFT),
                            new StairStep(BlockFace.EAST, Stairs.Shape.STRAIGHT),
                            new StairStep(BlockFace.EAST, Stairs.Shape.INNER_RIGHT),
                            new StairStep(BlockFace.SOUTH, Stairs.Shape.STRAIGHT),
                            new StairStep(BlockFace.SOUTH, Stairs.Shape.OUTER_LEFT),
                            new StairStep(BlockFace.WEST, Stairs.Shape.STRAIGHT),
                            new StairStep(BlockFace.WEST, Stairs.Shape.OUTER_RIGHT)
                    );

                    int idx = -1;
                    for (int i = 0; i < cycle.size(); i++) {
                        if (cycle.get(i).face == currentFace && cycle.get(i).shape == currentShape) {
                            idx = i;
                            break;
                        }
                    }

                    // Move to next rotation
                    if (idx == -1) {
                        stairs.setFacing(BlockFace.NORTH);
                        stairs.setShape(Stairs.Shape.STRAIGHT);
                    } else {
                        StairStep next = cycle.get((idx + 1) % cycle.size());
                        stairs.setFacing(next.face);
                        stairs.setShape(next.shape);
                    }

                    block.setBlockData(stairs, false);
                    return "§aSuccessfully rotated " + name;
                }


            } catch (Exception e) {
                return "§cCould not rotate " + name;
            }

            return "§cCould not rotate " + name;
        }


        if (f1.startsWith("debugStickInvert_")) {
            String[] parts = f1.substring("debugStickInvert_".length()).split(",");
            if (parts.length != 4) return "Invalid format!";
            String worldName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "§cWorld not found";

            Block block = world.getBlockAt(x, y, z);
            BlockData data = block.getBlockData();
            String name = block.getType().name();

            try {
                if (data instanceof Stairs stairs) {
                    stairs.setHalf(stairs.getHalf() == Bisected.Half.TOP ? Bisected.Half.BOTTOM : Bisected.Half.TOP);
                    block.setBlockData(stairs, false);
                    return "§aSuccessfully inverted " + name;
                }

                if (data instanceof Slab slab) {
                    Slab.Type type = slab.getType();
                    if (type == Slab.Type.DOUBLE) return "§cCould not invert " + name;
                    slab.setType(type == Slab.Type.TOP ? Slab.Type.BOTTOM : Slab.Type.TOP);
                    block.setBlockData(slab, false);
                    return "§aSuccessfully inverted " + name;
                }

            } catch (Exception e) {
                return "§cCould not invert " + name;
            }

            return "§cCould not invert " + name;
        }


        if (f1.startsWith("searchExecutable_")) {
            String keyword = f1.substring("searchExecutable_".length());
            String lowerKeyword = keyword.toLowerCase();

            File[] dirs = {
                    new File("plugins/ExecutableEvents/events"),
                    new File("plugins/ExecutableItems/items"),
                    new File("plugins/ExecutableBlocks/blocks")
            };

            List<String> exactMatches = new ArrayList<>();
            List<String> similarMatches = new ArrayList<>();

            for (File dir : dirs) {
                if (dir.exists() && dir.isDirectory()) {
                    try {
                        //noinspection resource
                        Files.walk(dir.toPath())
                                .filter(Files::isRegularFile)
                                .filter(path -> path.toString().endsWith(".yml"))
                                .forEach(path -> {
                                    String fileName = path.getFileName().toString().replace(".yml", "");
                                    String relPath = dir.toPath().relativize(path).toString().replace(File.separatorChar, '/');
                                    String fullRelPath = dir.getPath().replace("plugins/", "") + "/" + relPath;
    
                                    if (fileName.equals(keyword)) {
                                        exactMatches.add(fileName + ", " + fullRelPath);
                                    } else if (fileName.toLowerCase().contains(lowerKeyword)) {
                                        similarMatches.add(fileName + ", " + fullRelPath);
                                    }
                                });
                        
                    } catch (IOException e) {
                        
                        return "§cFailed";
                    }

                }
            }

            if (!exactMatches.isEmpty()) {
                f2.sendMessage("§e===Exact Matches===");
                for (String match : exactMatches) {
                    f2.sendMessage("§f" + match);
                }
            }

            if (!similarMatches.isEmpty()) {
                f2.sendMessage("§6===Similar Matches===");
                for (String match : similarMatches) {
                    f2.sendMessage("§f" + match);
                }
            }

            if (exactMatches.isEmpty() && similarMatches.isEmpty()) {
                f2.sendMessage("§7No matches found for \"" + keyword + "\"");
            } else {
                f2.sendMessage("§7======");
            }

            return String.valueOf(exactMatches.size());
        }

        if (f1.startsWith("repeatingParticleText_")) {
            boolean particleDebugEnabled = true;
            try {
                String params = f1.substring("repeatingParticleText_".length());
                String[] parts = params.split(",", 13);
                if (parts.length != 13) return "Invalid format";

                String worldName = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                Particle particle = Particle.valueOf(parts[4].toUpperCase());
                int density = Math.max(1, Integer.parseInt(parts[5]));
                String viewDistance = parts[6];
                double size = Double.parseDouble(parts[7]);
                double rotation = Math.toRadians(Double.parseDouble(parts[8]));
                long durationTicks = Long.parseLong(parts[9]);
                long intervalTicks = Math.max(1, Long.parseLong(parts[10]));
                String text = parts[12];
                if(parts[11].equalsIgnoreCase("false")) particleDebugEnabled = false;

                Location center = new Location(Bukkit.getWorld(worldName), x, y, z);
                String hashKey = f12(f1);

                List<Location> worldLocs;

                // === RAM Cache Check ===
                if (g8.containsKey(f1)) {
                    if (particleDebugEnabled) f2.sendMessage("§7[Cache] RAM hit for: " + f1);
                    worldLocs = g8.get(f1).locations();
                    f1fs(f1);
                }
                // === Disk Cache Check ===
                else {
                    File cacheFile = new File(g10, hashKey + ".txt");
                    //noinspection IfStatementWithIdenticalBranches
                    if (cacheFile.exists()) {
                        if (particleDebugEnabled) f2.sendMessage("§7[Cache] Disk hit for: " + f1);
                        List<Vector> vectors = f(cacheFile);
                        worldLocs = f1(center, vectors);

                        g8.put(f1, new ffs(worldLocs, System.currentTimeMillis()));
                        f1fs(f1);
                    }
                    // === No Cache: Generate ===
                    else {
                        if (particleDebugEnabled) f2.sendMessage("§7[Cache] No cache found. Generating new data.");
                        boolean[][] matrix = f11(text);
                        List<Vector> vectors = f1(matrix, density, size, rotation);
                        worldLocs = f1(center, vectors);

                        f1(cacheFile, vectors);
                        g8.put(f1, new ffs(worldLocs, System.currentTimeMillis()));
                        f1fs(f1);
                    }
                }

                // === Repeating Display ===
                if (particleDebugEnabled) {
                    f2.sendMessage("§7[Debug] Scheduling display for: " + text);
                }

                boolean finalParticleDebugEnabled = particleDebugEnabled;
                new BukkitRunnable() {
                    long elapsedTicks = 0;

                    @Override
                    public void run() {
                        if (elapsedTicks >= durationTicks) {
                            this.cancel();
                            return;
                        }

                        try {
                            f1(center, worldLocs, particle, viewDistance);
                        } catch (Exception ex) {
                            if (finalParticleDebugEnabled) {
                                f2.sendMessage("§c[Debug] Failed during display: " + ex.getMessage());
                            }
                            this.cancel();
                        }

                        elapsedTicks += intervalTicks;
                    }
                }.runTaskTimerAsynchronously(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")), 0L, intervalTicks);

                return "Scheduled " + text;

            } catch (Exception e) {
                if (particleDebugEnabled) {
                    f2.sendMessage("§c[Debug Error] " + e.getMessage());
                }
                return "§cError: " + e.getMessage();
            }
        }



        if (f1.startsWith("nearestPlayerNotTeam2_")) {
            return WG.wg(this, f2, f1);
        }


        if (f1.startsWith("visualBreak_")) {
            if (!f1(f2, "ProtocolLib")) return null;

            // Expected format: %Archistructure_visualBreak_STAGE,world,x,y,z%
            String params = f1.substring("visualBreak_".length());
            String[] parts = params.split(",");

            if (parts.length != 5) {
                return "Invalid format!" + f1;
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

                ProtocolLibrary.getProtocolManager().sendServerPacket(f2, packet);
            } catch (Exception e) {
                e.printStackTrace();
                return "Animation error";
            }

            // Reset any previous task
            if (g11.containsKey(loc)) {
                g11.get(loc).cancel();
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

                        ProtocolLibrary.getProtocolManager().sendServerPacket(f2, resetPacket);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                g11.remove(loc);
            }, 40L); // 40 ticks = 2 seconds

            g11.put(loc, task);

            return "Visual break stage " + breakStage + " set with reset";
        }

        if (f1.startsWith("PTFXCUBE_")) {
            // Expected format: %Archistructure_PTFXCUBE_world,x,y,z,particleType,width,normal/force,density%
            String params = f1.substring("PTFXCUBE_".length());
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
            f1(world, x, y, z, particleType, width, force, density, f2); // TODO Fix to show to all
            return "Cube displayed";
        }


        
        
        if (f1.startsWith("viewChest2_")) {
            // Expected format: %Archistructure_viewChest2_sourceWorld,x,y,z%
            String params = f1.substring("viewChest2_".length());
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
            if (g6) {
                f2.sendMessage("DEBUG: Source chest at " + srcX + " " + srcY + " " + srcZ +
                        " detected. It is " + (isDouble ? "double" : "single") + ".");
            }

            int destSize = isDouble ? 54 : 27;

            Inventory fakeInventory = Bukkit.createInventory(null, destSize, "Fake Chest");


            Inventory sourceInv = sourceChest.getInventory();
            int sourceSize = sourceInv.getSize();

            ItemStack[] newContents = new ItemStack[destSize];
            for (int i = 0; i < destSize; i++) {
                if (i < sourceSize && sourceInv.getItem(i) != null) {
                    newContents[i] = f1(Objects.requireNonNull(sourceInv.getItem(i)));
                } else {
                    newContents[i] = f0();
                }
            }
            // Update destination chest inventory without an extra clear call (setContents overrides existing items)
            try {

                fakeInventory.setContents(newContents);
            } catch (Exception ex) {
                return "Error updating destination chest inventory.";
            }

            // Return the destination coordinates.
            if (g6) {
                f2.sendMessage("FakeChest");
            }
            
            return "done";
        }
        

        if (f1.startsWith("repeat_")) return f1.substring("repeat".length());
        if (f1.startsWith("chain_")) {
            // Expected format: %Archistructure_chain_ENTITY/PLAYER/BOTH,RADIUS,[uuid1, uuid2, uuid3...],lastuuid%
            String params = f1.substring("chain_".length());
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


            return f1(chainType, radius, uuids);
        }



        if (f1.startsWith("viewChest_")) {
            // Expected format: %Archistructure_viewChest_sourceWorld,x,y,z%
            String params = f1.substring("viewChest_".length());
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
            if (g6) {
                f2.sendMessage("DEBUG: Source chest at " + srcX + " " + srcY + " " + srcZ +
                        " detected. It is " + (isDouble ? "double" : "single") + ".");
            }

            // Use a fixed destination in the "mcydatabase" world.
            World destWorld = Bukkit.getWorld("mcydatabase");
            if (destWorld == null) {
                destWorld = Bukkit.createWorld(new WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info("Created mcydatabase world.");
            }


            int destX, destY, destZ;
            String playerKey = f2.getUniqueId().toString();
            String storedCoords = g20.getString(playerKey);
            if (storedCoords != null) {
                // An entry exists for this player; use it.
                String[] coords = storedCoords.split(" ");
                destX = Integer.parseInt(coords[0]);
                destY = Integer.parseInt(coords[1]);
                destZ = Integer.parseInt(coords[2]);
            } else {
                // No entry exists, so compute new coordinates.
                // Use the "last" entry in viewOnlyChestConfig; if missing, use defaults.
                String lastEntry = g20.getString("last");
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
                g20.set(playerKey, newCoords);
                g20.set("last", newCoords);
                try {
                    g20.save(g16);
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
                            String commonName = f2.getUniqueId().toString();
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
                    newContents[i] = f1(Objects.requireNonNull(sourceInv.getItem(i)));
                } else {
                    newContents[i] = f0();
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
            if (g6) {
                f2.sendMessage("DEBUG: viewChest processing complete. Returning coordinates: " + destX + " " + destY + " " + destZ);
            }
            return isDouble ? destX + " " + destY + " " + (destZ + 1 ): destX + " " + destY + " " + destZ;
        }




        if (f1.startsWith("blackHole_")) {
            try {
                String[] parts = f1.substring("blackHole_".length()).split(",");
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
                f1(world, center, radius);

                // Attract entities to the center
                f2(world, center, radius);

                return x + " " + y + " " + z;
            } catch (Exception e) {
                e.printStackTrace();
                return "x" + e.getMessage();
            }
        }


        if (f1.startsWith("x_")) {
            String uuidString = f1.substring("x_".length());
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

        if (f1.startsWith("y_")) {
            String uuidString = f1.substring("y_".length());
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

        if (f1.startsWith("z_")) {
            String uuidString = f1.substring("z_".length());
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



        if (f1.startsWith("openBackpack2_")) {
            long id = Long.parseLong(f1.substring("openBackpack2_".length()));

            // Get or create the world
            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(new WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }

            // Get player UUID
            String uuid = f2.getUniqueId().toString();

            // Load or assign coordinates from the double chest database
            int x, y, z;
            String coordinates = g17.getString(uuid);
            if (coordinates != null) {
                String[] coords = coordinates.split(" ");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1]);
                z = Integer.parseInt(coords[2]);
            } else {
                // Assign new coordinates in a chunk-efficient way
                assert world != null;
                int[] newCoords = f2(world);
                x = newCoords[0];
                y = newCoords[1];
                z = newCoords[2];

                // Save the assigned coordinates to the double chest database
                String newCoordinates = x + " " + y + " " + z;
                g17.set(uuid, newCoordinates);
                f3();
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
                f2(currentChestName, chest1.getInventory().getContents());
            }

            // Set the new chest name and update it
            chest1.setCustomName(String.valueOf(id));
            chest1.update(true);
            chest1.getInventory().clear();

            // Load the new chest contents from the file if available
            ItemStack[] savedContents = f(String.valueOf(id));
            if (savedContents != null) {
                chest1.getInventory().setContents(savedContents);
                Bukkit.getLogger().info("Loaded double chest contents for chest ID: " + id);
            } else {
                Bukkit.getLogger().info("No previous contents found for double chest ID: " + id);
            }

            return x + " " + y + " " + z;
        }

        
        
        if (f1.startsWith("openBackpack_")) {
            long id = Long.parseLong(f1.substring("openBackpack_".length()));

            // Get or create the world
            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(new WorldCreator("mcydatabase")
                        .environment(World.Environment.NORMAL)
                        .generateStructures(false)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }

            // Get player UUID
            String uuid = f2.getUniqueId().toString();

            // Load or assign coordinates from the database
            int x, y, z;
            String coordinates = g18.getString(uuid);
            if (coordinates != null) {
                String[] coords = coordinates.split(" ");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1]);
                z = Integer.parseInt(coords[2]);
            } else {
                // Assign new coordinates in a chunk-efficient way
                assert world != null;
                int[] newCoords = f1(world);
                x = newCoords[0];
                y = newCoords[1];
                z = newCoords[2];

                // Save the assigned coordinates to the database
                String newCoordinates = x + " " + y + " " + z;
                g18.set(uuid, newCoordinates);
                f2();
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
                f1(currentChestName, chest.getInventory().getContents());
            }

// Update the chest name and clear its contents
            chest.setCustomName(String.valueOf(id));
            chest.update(true);
            chest.getInventory().clear();

// Load the new chest contents from the file if available
            ItemStack[] savedContents = f1(String.valueOf(id));
            if (savedContents != null) {
                chest.getInventory().setContents(savedContents);
                Bukkit.getLogger().info("Loaded backpack contents for chest ID: " + id);
            } else {
                Bukkit.getLogger().info("No previous contents found for chest ID: " + id);
            }

            return x + " " + y + " " + z;
        }

        final double NUDGE_AMOUNT = 0.05;

        if (f1.startsWith("bounce2_")) {
            // Format: %Archistructure_bounce2_UUID,FACE,PITCH,YAW,VELOCITY%
            String[] parts = f1.substring("bounce2_".length()).split(",");

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

        
        
        if (f1.startsWith("checkVelocity_")) {
            String uuidStr = f1.substring("checkVelocity_".length());

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

        if (f1.startsWith("bounce_")) {
            // Format: %Archistructure_bounce_UUID,FACE,SCALE%
            String[] parts = f1.substring("bounce_".length()).split(",");

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


        if (f1.startsWith("vacuumCleaner_")) {
            String[] parts = f1.substring("vacuumCleaner_".length()).split(",");
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




        if (f1.startsWith("remoteHopper_")) {
            String[] parts = f1.substring("remoteHopper_".length()).split(",");
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

        
        
        if (f1.startsWith("XRAY-")) {
            String[] args = f1.substring("XRAY-".length()).split("-");
            if (args.length != 2) {
                return "Invalid format! Use: %Archistructure_XRAY-RADIUS-SECONDS%";
            }

            try {
                int radius = Integer.parseInt(args[0]);
                int duration = Integer.parseInt(args[1]) * 20; // Convert seconds to ticks

                f1(f2, radius, duration);
                return "X-Ray v2 Activated!";
            } catch (NumberFormatException e) {
                return "Invalid numbers!";
            }
        }

        if (f1.equals("eifolderresetperms") && f2 != null) {

            if (!f1(f2, "LuckPerms")) return null;

            User user = g23.getPlayerAdapter(Player.class).getUser(f2);

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
                f1(user, itemFolderPermissions);

                // STEP 5: Save changes
                g23.getUserManager().saveUser(user);
                return "&aSuccessfully updated EI-Folder permissions.";

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error processing permissions for " + f2.getName() + ": " + e.getMessage());
                e.printStackTrace();
                return "&cSomething went wrong with LP EI-Folder: " + e.getMessage();
            }
            




        }

        if (f1.startsWith("bossbar_")) {
            try {
                if( f2 == null ) throw new IllegalArgumentException();

                String[] args = f1.substring("bossbar_".length()).split(",");
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
                bossBar.addPlayer(f2);

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
        
        if (f1.equalsIgnoreCase("fireworkboost")) {
            try {
                if( f2 != null) {
                    if( !f2.isGliding() ) throw new IllegalArgumentException();
                    // Create a Firework ItemStack
                    ItemStack fireworkItem = new ItemStack(Material.FIREWORK_ROCKET);
                    FireworkMeta fireworkMeta = (FireworkMeta) fireworkItem.getItemMeta();
    
                    if (fireworkMeta != null) {
                        fireworkMeta.setPower(1); // Duration of 1
                        fireworkMeta.clearEffects(); // No star effects
                        fireworkItem.setItemMeta(fireworkMeta);
                    }
    
                    // Apply the firework boost
                    f2.fireworkBoost(fireworkItem);       
                }

                return "1";
            } catch (IllegalArgumentException e) {
                return "0";
            }

        }


        if (f1.equalsIgnoreCase("DN")) { // %Archistructure_DN%
            return f1(f2);
        }
        
        
        if (f1.startsWith("sr72-fly-")) {
            try {
                // Extract parameters from the identifier
                String[] params = f1.substring("sr72-fly-".length()).split(",");
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

        if (f1.startsWith("variables-create-")) {

            try {
                        String[] args = f1.substring("variables-create-".length()).split(",");

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

        if (f1.equalsIgnoreCase("FT-increment")) {
            f1(f2.getUniqueId());
            return "done"; // Return "done" if successful
        }

        if (f1.equalsIgnoreCase("FT-leaderboard")) {
            try {
                f3(f2);
                return "&aLeaderboard Displayed!"; // Success: return empty string
            } catch (Exception e) {
                e.printStackTrace();
                return "&cFailed! Contact an admin."; // Failure message
            }
        }


        if (f1.startsWith("trackImpact_")) {
            try {
                String[] parts = f1.substring("trackImpact_".length()).split(",");
                if (parts.length != 6) return "§cInvalid format";

                UUID launcherUUID = UUID.fromString(parts[0]);
                World world = Bukkit.getWorld(parts[1]);
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);
                int z = Integer.parseInt(parts[4]);
                float damage = Float.parseFloat(parts[5]);

                Location location = new Location(world, x, y, z);
                f1(world, location);
                f1(launcherUUID, location, damage);

                return "§6Impact triggered.";
            } catch (Exception e) {
                return "§ccaught a crash";
            }
        }

        if (f1.startsWith("trackImpact2_")) {
            try {
                String[] parts = f1.substring("trackImpact2_".length()).split(",");
                if (parts.length != 3) return "§cInvalid format";

                UUID launcherUUID = UUID.fromString(parts[0]);
                UUID targetUUID = UUID.fromString(parts[1]);
                float damage = Float.parseFloat(parts[2]);

                Entity target = Bukkit.getEntity(targetUUID);
                if (target == null) return "§cTarget not found";

                Location location = target.getLocation();
                f1(location.getWorld(), location);
                f1(launcherUUID, location, damage);

                return "§eTarget explosion triggered.";
            } catch (Exception e) {
                return "§ccaught a crash";
            }
        }

        if (f1.startsWith("track_")) {
            String[] parts = f1.substring("track_".length()).split(",");
            if (parts.length != 5) {
                return "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage%" + "and you used" + f1;
            }

            try {
                UUID x1 = UUID.fromString(parts[0]);
                UUID wolverine = UUID.fromString(parts[1]);
                double ishowspeed = Double.parseDouble(parts[2]);
                UUID mrbeast = UUID.fromString(parts[3]);


                Entity tester = Bukkit.getEntity(x1);
                Entity qa = Bukkit.getEntity(wolverine);
                
                

                if (tester == null || qa == null) {
                    return "§c§lMissile Impacted"; // No valid target
                }

                // Check if both entities are in the same world
                if (!tester.getWorld().equals(qa.getWorld())) {
                    return "§c§lMissile Impacted."; // No valid target
                }

                // Airburst Mechanic: If within 3 blocks, explode immediately
                double speed = tester.getLocation().distance(qa.getLocation());
                if (speed <= 5.0 && tester instanceof Firework && qa instanceof Entity) {
                    f1((Firework) tester, qa, mrbeast, parts[4]);
                    return "§c§lAirburst Detonation!";
                }

                // Calculate the interception velocity
                Vector offset = f1(tester, qa, ishowspeed);
                tester.setVelocity(offset);

                // Get target's name (Player name or Entity type)
                String redirector = (qa instanceof Player) ? qa.getName() : qa.getType().name();

                // Format return message
                return String.format("§6§l%s  §7§l| §d§l%.1f", redirector, speed);
            } catch (Exception e) {
                e.printStackTrace();
                return "§c§lMissile Impacted"; // Error case
            }
        }




        if (f1.startsWith("trackLimitedRotation_")) {
            String[] privateparts = f1.substring("trackLimitedRotation_".length()).split(",");
            if (privateparts.length != 5) {
                return "Invalid format. Use: %Archistructure_trackLimitedRotation_uuid,targetuuid,speed,damage,maxDegrees%";
            }

            try {
                UUID trst1 = UUID.fromString(privateparts[0]);
                UUID setn32 = UUID.fromString(privateparts[1]);
                double ft3 = Double.parseDouble(privateparts[2]);
                UUID st2 = UUID.fromString(privateparts[3]);
                double arst43 = Double.parseDouble(privateparts[4]);

                Entity rst4 = Bukkit.getEntity(trst1);
                Entity art4 = Bukkit.getEntity(setn32);

                if (rst4 == null || art4 == null || !rst4.getWorld().equals(art4.getWorld())) {
                    return "§c§lMissile Impacted";
                }

                Location xd34 = rst4.getLocation();
                Location st34 = art4.getLocation();

                double longdistance = xd34.distance(st34);

                // Airburst
                if (longdistance <= 5.0 && rst4 instanceof Firework) {
                    f1((Firework) rst4, art4, st2, privateparts[4]);
                    return "§c§lAirburst Detonation!";
                }

                // Intercept calculation
                Vector R = st34.toVector().subtract(xd34.toVector());
                Vector V = art4.getVelocity();
                double Sm = ft3;

                double a = V.dot(V) - Sm * Sm;
                double b = 2 * R.dot(V);
                double c = R.dot(R);

                Vector xrp;
                if (a == 0 || b * b - 4 * a * c < 0) {
                    // fallback to direct
                    xrp = R.normalize().multiply(Sm);
                } else {
                    double t = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
                    Vector interceptPoint = st34.toVector().add(V.clone().multiply(t));
                    xrp = interceptPoint.subtract(xd34.toVector()).normalize().multiply(Sm);
                }

                // Limit turning angle
                Vector bitcoin = rst4.getVelocity();
                if (bitcoin.lengthSquared() == 0) {
                    // Avoid NaNs on spawn tick — just set directly
                    rst4.setVelocity(xrp);
                } else {
                    Vector red = bitcoin.clone().normalize();
                    Vector orange = xrp.clone().normalize();

                    double yellow = red.angle(orange);
                    double blue = Math.toRadians(arst43);

                    Vector xd;
                    if (yellow <= blue) {
                        xd = orange;
                    } else {
                        // Rotate toward desired direction by maxRad
                        xd = f1racer(red, orange, blue);
                    }

                    Vector xp = xd.multiply(Sm);
                    rst4.setVelocity(xp);
                }

                String tn = (art4 instanceof Player) ? art4.getName() : art4.getType().name();
                return String.format("§b§l[%s]  §7§l| §d§l%.1f", tn, longdistance);

            } catch (Exception e) {
                e.printStackTrace();
                return "§c§lMissile Impacted";
            }
        }




        if (f1.startsWith("trackv3_")) {
            String[] interceptor = f1.substring("trackv3_".length()).split(",");
            if (interceptor.length != 5) {
                return "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage% and you used " + f1;
            }

            try {
                UUID refinery = UUID.fromString(interceptor[0]);
                UUID oilrig = UUID.fromString(interceptor[1]);
                double mall = Double.parseDouble(interceptor[2]);
                UUID testing = UUID.fromString(interceptor[3]);


                // Airburst: If close, explode

                Entity tnerminator
                        = Bukkit.getEntity(refinery);
                Entity testi = Bukkit.getEntity(oilrig);
                double luke = Bukkit.getEntity(refinery).getLocation().distance(Bukkit.getEntity(oilrig).getLocation());
                if (luke <= 5.0 && tnerminator instanceof Firework) {
                    f1((Firework) tnerminator, testi, testing, interceptor[4]);
                    return "§c§lAirburst Detonation!";
                }

                if (tnerminator == null || testi == null || !tnerminator.getWorld().equals(testi.getWorld())) {
                    return "§c§lMissile Impacted";
                }

                Location vLOS = tnerminator.getLocation();
                Location instrumentRated = testi.getLocation();
                Vector R = instrumentRated.toVector().subtract(vLOS.toVector());
                Vector V = testi.getVelocity();
                double Sm = mall;

                double a = V.dot(V) - Sm * Sm;
                double b = 2 * R.dot(V);
                double c = R.dot(R);
                double retesting = b * b - 4 * a * c;

                Vector episode;
                if (retesting < 0 || a == 0) {
                    episode = R.normalize().multiply(Sm);
                } else {
                    double r12 = Math.sqrt(retesting);
                    double t1 = (-b - r12) / (2 * a);
                    double t2 = (-b + r12) / (2 * a);
                    double t = t1 > 0 ? t1 : (t2 > 0 ? t2 : -1);

                    if (t <= 0) {
                        episode = R.normalize().multiply(Sm);
                    } else {
                        Vector origin = instrumentRated.toVector().add(V.clone().multiply(t));
                        episode = origin.subtract(vLOS.toVector()).normalize().multiply(Sm);
                    }
                }

                // Terrain Avoidance: Raytrace 5 ticks ahead
                Location bluemoon = vLOS.clone();
                Vector moonshine = episode.clone().normalize();
                double shinycar = episode.length() * 5;

                RayTraceResult food = bluemoon.getWorld().rayTraceBlocks(
                        bluemoon,
                        moonshine,
                        shinycar,
                        FluidCollisionMode.NEVER,
                        true
                );

                Set<Material> reposess = enumSet;
                boolean needsAvoidance = food != null && food.getHitBlock() != null &&
                        !reposess.contains(food.getHitBlock().getType());

                if (needsAvoidance) {
                    Vector bestVelocity = episode;
                    double bestScore = -1;
                    for (int yaw = 0; yaw <= 90; yaw += 5) {
                        Vector baseball = pitchVectorUpwards(episode.clone(), Math.toRadians(yaw)).normalize().multiply(Sm);
                        Vector football = baseball.clone().normalize();
                        RayTraceResult sat = bluemoon.getWorld().rayTraceBlocks(
                                bluemoon,
                                football,
                                baseball.length() * 5,
                                FluidCollisionMode.NEVER,
                                true
                        );
                        boolean clear = sat == null || sat.getHitBlock() == null ||
                                reposess.contains(sat.getHitBlock().getType());
                        if (clear) {
                            double opinion = yaw;
                            double heuristic = 100 - opinion;
                            if (heuristic > bestScore) {
                                bestScore = heuristic;
                                bestVelocity = baseball;
                            }
                        }
                    }
                    episode = bestVelocity;
                }

                tnerminator.setVelocity(episode);
                double division1 = vLOS.distance(instrumentRated);
                String whoistheboss = (testi instanceof Player) ? testi.getName() : testi.getType().name();
                return String.format("\u00a76\u00a7l%s  \u00a77\u00a7l| \u00a7d\u00a7l%.1f", whoistheboss, division1);

            } catch (Exception e) {
                e.printStackTrace();
                return "§c§lMissile Impacted";
            }
        }


        if (f1.startsWith("trackv2_")) {
            String[] pen = f1.substring("trackv2_".length()).split(",");
            if (pen.length != 5) {
                return "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage%" + " and you used " + f1;
            }

            try {
                UUID fish = UUID.fromString(pen[0]);
                UUID cow = UUID.fromString(pen[1]);
                double blanket = Double.parseDouble(pen[2]);
                UUID flashlight = UUID.fromString(pen[3]);

                Entity pillow = Bukkit.getEntity(fish);
                Entity dalion = Bukkit.getEntity(cow);

                if (pillow == null || dalion == null) {
                    return "§c§lMissile Impacted"; // No valid target
                }

                if (!pillow.getWorld().equals(dalion.getWorld())) {
                    return "§c§lMissile Impacted."; // Different worlds
                }

                Location fatima = pillow.getLocation();
                Location is = dalion.getLocation();

                double literally = fatima.distance(is);

                // Airburst: If close, explode
                if (literally <= 5.0 && pillow instanceof Firework) {
                    f1((Firework) pillow, dalion, flashlight, pen[4]);
                    return "§c§lAirburst Detonation!";
                }

                // --- Begin Optimal Intercept Calculation ---
                Vector R = is.toVector().subtract(fatima.toVector());   // Relative position
                Vector V = dalion.getVelocity();                                  // Target velocity
                double Sm = blanket;

                double a = V.dot(V) - Sm * Sm;
                double b = 2 * R.dot(V);
                double c = R.dot(R);

                double the = b * b - 4 * a * c;
                Vector best;

                if (the < 0 || a == 0) {
                    // No valid intercept path, fallback to direct pursuit
                    best = R.normalize().multiply(Sm);
                } else {
                    double person = Math.sqrt(the);
                    double t1 = (-b - person) / (2 * a);
                    double t2 = (-b + person) / (2 * a);

                    double t;
                    if (t1 > 0 && t2 > 0) {
                        t = Math.min(t1, t2);
                    } else if (t1 > 0) {
                        t = t1;
                    } else if (t2 > 0) {
                        t = t2;
                    } else {
                        // Both times are in the past — fallback
                        best = R.normalize().multiply(Sm);
                        pillow.setVelocity(best);
                        String have = (dalion instanceof Player) ? dalion.getName() : dalion.getType().name();
                        return String.format("§6§l%s  §7§l| §d§l%.1f", have, literally);
                    }

                    Vector i = is.toVector().add(V.clone().multiply(t));
                    best = i.subtract(fatima.toVector()).normalize().multiply(Sm);
                }

                pillow.setVelocity(best);

                String everMet = (dalion instanceof Player) ? dalion.getName() : dalion.getType().name();
                return String.format("§6§l%s  §7§l| §d§l%.1f", everMet, literally);

            } catch (Exception e) {
                e.printStackTrace();
                return "§c§lMissile Impacted";
            }
        }





        if (f1.startsWith("checkProfession_")) {
            String uuidString = f1.substring("checkProfession_".length());
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


        if (f1.startsWith("saveEntity_")) {
            String uuidString = f1.substring("saveEntity_".length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    EntitySnapshot snapshot = entity.createSnapshot();
                    assert snapshot != null;
                    String nbtData = snapshot.getAsString();
                    String entityId = "entity_" + System.currentTimeMillis();

                    File entityFile = new File(g12, entityId + ".nbt");
                    f1(entityFile, nbtData);

                    entity.remove(); // Remove the entity after saving
                    return entityId;
                } else {
                    return "Entity not found";
                }
            } catch (IllegalArgumentException e) {
                return "Invalid UUID";
            }
        }

        if (f1.startsWith("loadEntity_")) {
            if (f1.startsWith("loadEntity_location_")) {
                try {
                    // Parse parameters from the identifier
                    String[] parts = f1.substring("loadEntity_location_".length()).split(",");
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
                    File entityFile = new File(g12, entityId + ".nbt");
                    if (!entityFile.exists()) {
                        return "&cEntity with ID '" + entityId + "' not found!";
                    }

                    String nbtData = f1(entityFile);
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
                String entityId = f1.substring("loadEntity_".length());
                File entityFile = new File(g12, entityId + ".nbt");

                if (entityFile.exists()) {
                    String nbtData = f1(entityFile);
                    if (nbtData != null) {
                        Location spawnLocation = f2.getLocation();
                        EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(nbtData);
                        snapshot.createEntity(spawnLocation);
                        entityFile.delete(); // Remove the saved file after reintroducing
                        return "&cEntity reintroduced into the world!";
                    }
                }
                return "Entity ID not found";
            }
        }


        switch (f1) {
            case "cycle_playeruuid_head" -> {
                ItemStack mainHandItem = f2.getInventory().getItemInMainHand();

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
                return f2.getVelocity().toString();
            }
            case "copyMainHand" -> {
                f2(f2);
                return "done";
            }
            case "critical" -> {
                boolean falling = f2.getFallDistance() > 0 && f2.getVelocity().getY() < 0;
                boolean onGround = f2.isOnGround(); // DEPRECATED! Remove if necessary

                boolean isClimbing = f2.isClimbing();
                boolean inWater = f2.isSwimming() || f2.isInWater();
                boolean blind = f2.hasPotionEffect(PotionEffectType.BLINDNESS);
                boolean slowFalling = f2.hasPotionEffect(PotionEffectType.SLOW_FALLING);
                boolean mounted = f2.getVehicle() != null;
                boolean horizontalSpeedValid = !f2.isSprinting();// && p.getWalkSpeed() >= Math.sqrt(p.getVelocity().getX() * p.getVelocity().getX() + p.getVelocity().getZ() + p.getVelocity().getZ());

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
                return Objects.requireNonNull(Objects.requireNonNull(f2.rayTraceBlocks(30)).getHitBlock()).toString();
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





        if (f1.startsWith("countPlayersInRegion_")) {

            return WG.wg2(this, f2, f1);
        }



        if (f1.startsWith("growCropParticle_")) {
            try {
                String[] parts = f1.substring("growCropParticle_".length()).split(",");
                if (parts.length != 6) {
                    return "§c[DEBUG] Invalid parameter count: " + parts.length;
                }

                int radius = Integer.parseInt(parts[0]);
                String particleInput = parts[1];
                double dx = Double.parseDouble(parts[2]); // spacing between particles
                int maxCount = Integer.parseInt(parts[3]);
                boolean grow = Boolean.parseBoolean(parts[5]); // spacing between particles


                String[] locParts = parts[4].split(":");
                if (locParts.length != 4) {
                    return "§c[DEBUG] Invalid world:x:y:z format.";
                }

                World world = Bukkit.getWorld(locParts[0]);
                if (world == null) {
                    return "§c[DEBUG] Invalid world: " + locParts[0];
                }

                double x = Double.parseDouble(locParts[1]);
                double y = Double.parseDouble(locParts[2]);
                double z = Double.parseDouble(locParts[3]);
                Location origin = new Location(world, x, y, z);
                Location originCenter = origin.clone().add(0.5, 0.5, 0.5);

                List<Block> growableBlocks = new ArrayList<>();
                int checked = 0;
                int ageableTotal = 0;
                int ageableFull = 0;
                int ageableNotFull = 0;

                for (int dx_ = -radius; dx_ <= radius; dx_++) {
                    for (int dy_ = -radius; dy_ <= radius; dy_++) {
                        for (int dz_ = -radius; dz_ <= radius; dz_++) {
                            Location check = origin.clone().add(dx_, dy_, dz_);
                            Block block = check.getBlock();
                            checked++;

                            BlockData data = block.getBlockData();
                            if (data instanceof Ageable) {
                                ageableTotal++;
                                Ageable ageable = (Ageable) data;
                                if (ageable.getAge() < ageable.getMaximumAge()) {
                                    ageableNotFull++;
                                    growableBlocks.add(block);
                                } else {
                                    ageableFull++;
                                }
                            }
                        }
                    }
                }

                if (growableBlocks.isEmpty()) {
                    return String.join("\n",
                            "§c[DEBUG] No crops found.",
                            "§6Radius: " + radius,
                            "§6Origin: " + origin.getWorld().getName() + " " +
                                    origin.getBlockX() + " " + origin.getBlockY() + " " + origin.getBlockZ(),
                            "§6Total blocks scanned: " + checked,
                            "§dAgeable block stats:",
                            "§7Total Ageable blocks: " + ageableTotal,
                            "§7Non-full-age: " + ageableNotFull,
                            "§7Full-age: " + ageableFull
                    );
                }

                if (maxCount != -1 && maxCount < growableBlocks.size()) {
                    Collections.shuffle(growableBlocks);
                    growableBlocks = growableBlocks.subList(0, maxCount);
                }

                Particle particle;
                Particle.DustOptions dustOptions = null;
                String particleSummary = "";

                if (particleInput.toUpperCase().startsWith("DUST:")) {
                    try {
                        String hexScale = particleInput.substring(5);
                        String hex = hexScale.substring(0, 6);
                        float scale = Float.parseFloat(hexScale.substring(6));
                        java.awt.Color c = java.awt.Color.decode("#" + hex);
                        dustOptions = new Particle.DustOptions(
                                Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()), scale);
                        particle = Particle.DUST;
                        particleSummary = "§bUsing DUST particle (#" + hex + ", scale " + scale + ")";
                    } catch (Exception e) {
                        return "§c[DEBUG] Invalid DUST particle format: " + e.getMessage();
                    }
                } else {
                    try {
                        particle = Particle.valueOf(particleInput.toUpperCase());
                        particleSummary = "§bUsing particle: " + particle;
                    } catch (IllegalArgumentException ex) {
                        return "§c[DEBUG] Invalid particle type: " + particleInput;
                    }
                }

                for (Block b : growableBlocks) {
                    Location target = b.getLocation().add(0.5, 0.5, 0.5);
                    Vector toTarget = target.toVector().subtract(originCenter.toVector());
                    double distance = toTarget.length();

                    if (dx <= 0.0) dx = 0.1;
                    int steps = (int) Math.floor(distance / dx);
                    if (steps <= 0) steps = 1;

                    Vector step = toTarget.normalize().multiply(dx);
                    Location current = originCenter.clone();

                    for (int i = 0; i <= steps; i++) {
                        if (particle == Particle.DUST && dustOptions != null) {
                            world.spawnParticle(particle, current, 0, dustOptions);
                        } else {
                            world.spawnParticle(particle, current, 0);
                        }
                        current.add(step);
                    }


                    if (grow) {
                        BlockData data = b.getBlockData();
                        if (data instanceof Ageable ageable) {
                            int newAge = ageable.getAge() + 1;
                            if (newAge <= ageable.getMaximumAge()) {
                                ageable.setAge(newAge);
                                b.setBlockData(ageable, true);
                            }
                        }
                    }
                }

                return String.join("\n",
                        "§a[DEBUG] Rendered particle lines: " + growableBlocks.size(),
                        "§6Origin: " + origin.getWorld().getName() + " " +
                                origin.getBlockX() + " " + origin.getBlockY() + " " + origin.getBlockZ(),
                        "§6Total blocks scanned: " + checked,
                        "§dAgeable block stats:",
                        "§7Total Ageable blocks: " + ageableTotal,
                        "§7Non-full-age: " + ageableNotFull,
                        "§7Full-age: " + ageableFull,
                        particleSummary
                );

            } catch (Exception e) {
                e.printStackTrace();
                return "§c[ERROR] " + e.getClass().getSimpleName() + ": " + e.getMessage();
            }
        }


        return null;

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


}