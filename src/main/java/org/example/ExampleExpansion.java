package org.example;


import org.bukkit.block.data.*;
import org.bukkit.plugin.PluginManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.List;





import java.util.stream.Stream;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {

    private final Map<String, List<Map.Entry<String, Integer>>> global1 = new HashMap<>();


    private final File g1;
    private final YamlConfiguration g2;

    private final Map<Location, BukkitTask> g3 = new HashMap<>();
    private final Map<Location, Integer> g4 = new HashMap<>();

    private boolean LuckPerms_Installed = false;
    private boolean GriefPrevention_Installed = false;

    private boolean SCore_Installed = false;
    private int g5;






    private static final boolean g6 = false;


    private final Map<UUID, BukkitTask> g7 = new HashMap<>();

    private static final long g9 = 1000 * 60 * 60; // 1 hour
    private static final File g10 = new File("plugins/Archistructures/particles");


    private final Map<Location, BukkitTask> g11 = new HashMap<>();

    private final File g12;
    private final File g13;
    private final File g14;
    private final File g15;
    private final File g16;
    private final YamlConfiguration g17;
    private final YamlConfiguration g18;
    private final Map<Location, BlockData> g19 = new HashMap<>();
    private final YamlConfiguration g20;



    private final File g21;
    private final Map<UUID, Integer> g22;
    private final LuckPerms g23;

    private final Map<UUID, Set<Location>> g24 = new HashMap<>();
    private final Set<Material> g25 = Set.of(
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS);
    private final Map<Location, BukkitTask> g26 = new HashMap<>();
    private final char[] g27 = new char[] {
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


    private String g28;


    public ExampleExpansion() {

        

        

        PluginManager pm = Bukkit.getPluginManager();



        if (pm.getPlugin("LuckPerms") != null && Objects.requireNonNull(pm.getPlugin("LuckPerms")).isEnabled()) {
            LuckPerms_Installed = true;
        
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
        
        
        
        // INSERT HERE 


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


        return null;
    }
}