package org.example;


import org.bukkit.block.data.*;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
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
import java.lang.reflect.InvocationTargetException;
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
@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {

    private final Map<String, List<Map.Entry<String, Integer>>> leaderboards = new HashMap<>();



    private boolean WorldEdit_Installed = false;
    private boolean WorldGuard_Installed = false;
    private boolean LuckPerms_Installed = false;
    private boolean ProtocolLib_Installed = false;
    
    
    




    private final boolean trialVersion;
    private int trialNumber;

    private final File entityStorageDir;





    public ExampleExpansion() {
        trialVersion = false;
        trialNumber = 1000;


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




        
        entityStorageDir = new File("plugins/Archistructures/saved-entities/");
        if (!entityStorageDir.exists()) {
            entityStorageDir.mkdirs();
        }



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
        public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        
        if(trialVersion && trialNumber < 0) {
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
        if (identifier.startsWith("leaderboards_")) {
            
            String[] args = identifier.substring("leaderboards_".length()).split(",");
            

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
                    leaderboards.remove(name);
                    return "§aCleared " + name;
                }
                case "INCREMENT" -> {
                    if (user == null) return "§cNo user";
                    updateScore(name, user, getScore(name, user) + param);
                    return "§a+" + param;
                }
                case "DECREMENT" -> {
                    if (user == null) return "§cNo user";
                    updateScore(name, user, getScore(name, user) - param);
                    return "§a-" + param;
                }
                case "SET" -> {
                    if (user == null) return "§cNo user";
                    updateScore(name, user, param);
                    return "§aSet to " + param;
                }
                case "GET" -> {
                    if (args.length < 4) return "§cNo index";
                    List<Map.Entry<String, Integer>> list = leaderboards.get(name);
                    if (list == null || param < 0 || param >= list.size()) return "N/A";
                    return list.get(param).getKey() + ": " + list.get(param).getValue();
                }
            }
            return "§cUnknown action";
            
            
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

    private void updateScore(String boardName, String user, int newScore) {
        List<Map.Entry<String, Integer>> list = leaderboards.computeIfAbsent(boardName, k -> new ArrayList<>());
        list.removeIf(e -> e.getKey().equals(user));
        Map.Entry<String, Integer> newEntry = Map.entry(user, newScore);

        int index = 0;
        while (index < list.size()) {
            Map.Entry<String, Integer> current = list.get(index);
            if (newScore > current.getValue()) break;
            index++;
        }
        list.add(index, newEntry);
    }

    private int getScore(String boardName, String user) {
        List<Map.Entry<String, Integer>> list = leaderboards.get(boardName);
        if (list == null) return 0;
        return list.stream().filter(e -> e.getKey().equals(user)).map(Map.Entry::getValue).findFirst().orElse(0);
    }
}