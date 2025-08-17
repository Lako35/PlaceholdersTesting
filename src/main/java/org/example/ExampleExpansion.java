package org.example;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.ImmutableList;
import com.sk89q.worldedit.util.formatting.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.dialog.Dialog;
import org.bukkit.block.data.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.EulerAngle;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {
    private final AtomicBoolean demoCmdRegistered = new AtomicBoolean(false);


    private final Map<UUID, Double> originalMinecartSpeeds = new ConcurrentHashMap<>();
    private final Map<UUID, BukkitTask> minecartResetTasks = new ConcurrentHashMap<>();
    protected static final ConcurrentHashMap<UUID, Vector> manualTrackingPositions = new ConcurrentHashMap<>();
    protected final Map<UUID, Location> lastPositions = new ConcurrentHashMap<>();
    protected final Map<Location, UUID> turretLocks = new ConcurrentHashMap<>();
    protected final Map<Location, BukkitRunnable> activeLodestones = new ConcurrentHashMap<>();
    protected final Set<Material> enumSet = EnumSet.of(Material.ACTIVATOR_RAIL, Material.AIR, Material.BAMBOO, Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BEETROOT_SEEDS, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.BIRCH_BUTTON, Material.SPRUCE_BUTTON, Material.JUNGLE_BUTTON, Material.DARK_OAK_BUTTON, Material.ACACIA_BUTTON, Material.MANGROVE_BUTTON, Material.CHERRY_BUTTON, Material.CRIMSON_BUTTON, Material.WARPED_BUTTON, Material.LIGHT_BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER, Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_GRAY_BANNER, Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER, Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER, Material.CARROTS, Material.CHORUS_FLOWER, Material.CHORUS_PLANT, Material.COBWEB, Material.COCOA, Material.BRAIN_CORAL, Material.BUBBLE_CORAL, Material.FIRE_CORAL, Material.HORN_CORAL, Material.TUBE_CORAL, Material.BRAIN_CORAL_FAN, Material.BUBBLE_CORAL_FAN, Material.FIRE_CORAL_FAN, Material.HORN_CORAL_FAN, Material.TUBE_CORAL_FAN, Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.END_GATEWAY, Material.END_PORTAL, Material.FIRE, Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY, Material.WITHER_ROSE, Material.FLOWER_POT, Material.FROGSPAWN, Material.WARPED_FUNGUS, Material.CRIMSON_FUNGUS, Material.GLOW_BERRIES, Material.GLOW_LICHEN, Material.SHORT_GRASS,  Material.HANGING_ROOTS, Material.PLAYER_HEAD, Material.SKELETON_SKULL, Material.CREEPER_HEAD, Material.WITHER_SKELETON_SKULL, Material.ZOMBIE_HEAD, Material.DRAGON_HEAD, Material.PIGLIN_HEAD, Material.KELP, Material.LADDER, Material.LAVA, Material.LEVER, Material.LIGHT, Material.LILY_PAD, Material.MANGROVE_PROPAGULE, Material.MELON_SEEDS, Material.MOSS_CARPET, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.NETHER_PORTAL, Material.NETHER_SPROUTS, Material.NETHER_WART, Material.PINK_PETALS, Material.PITCHER_PLANT, Material.PITCHER_POD, Material.POTATOES, Material.POWDER_SNOW, Material.POWERED_RAIL, Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.MANGROVE_PRESSURE_PLATE, Material.CHERRY_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.PUMPKIN_SEEDS, Material.RAIL, Material.COMPARATOR, Material.REDSTONE_WIRE, Material.REPEATER, Material.REDSTONE_TORCH, Material.REDSTONE_WALL_TORCH, Material.OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.JUNGLE_SAPLING, Material.DARK_OAK_SAPLING, Material.ACACIA_SAPLING, Material.CHERRY_SAPLING, Material.SCULK_VEIN, Material.SEA_PICKLE, Material.SEAGRASS, Material.SHORT_GRASS, Material.DEAD_BUSH,  Material.OAK_SIGN, Material.BIRCH_SIGN, Material.SPRUCE_SIGN, Material.JUNGLE_SIGN, Material.DARK_OAK_SIGN, Material.ACACIA_SIGN, Material.CHERRY_SIGN, Material.MANGROVE_SIGN, Material.CRIMSON_SIGN, Material.WARPED_SIGN, Material.SMALL_DRIPLEAF, Material.SNOW, Material.SPORE_BLOSSOM, Material.STRING, Material.STRUCTURE_VOID, Material.SUGAR_CANE, Material.SWEET_BERRY_BUSH, Material.TORCH, Material.TORCHFLOWER_SEEDS, Material.TRIPWIRE_HOOK, Material.TURTLE_EGG, Material.TWISTING_VINES, Material.VINE, Material.WATER, Material.WEEPING_VINES, Material.WHEAT_SEEDS, Material.WHITE_TULIP );
    protected final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    protected final AtomicInteger nextFakeEntityId = new AtomicInteger(200_000);
    protected final Map<UUID,Integer> cloneEntityIds   = new ConcurrentHashMap<>();
    protected final Random random = new Random();
    protected final Set<UUID> activeDice = ConcurrentHashMap.newKeySet();
    protected static final EulerAngle[] DICE_FACES = new EulerAngle[] {new EulerAngle(0, 0, 0), new EulerAngle(Math.PI/2, 0, 0), new EulerAngle(-Math.PI/2, 0, 0), new EulerAngle(Math.PI, 0, 0), new EulerAngle(0, Math.PI/2, 0), new EulerAngle(0, -Math.PI/2, 0)};
    protected final Map<String, List<Map.Entry<String, Integer>>> leaderboards = new HashMap<>();
    protected final File shulkerDatabaseFile;
    protected final YamlConfiguration shulkerDatabaseConfig;
    protected final Map<Location, BukkitTask> jesus2Timers = new HashMap<>();
    protected final Map<Location, Integer> shulkerEntityIds = new HashMap<>();
    protected boolean WorldEdit_Installed = false;
    protected boolean WorldGuard_Installed = false;
    protected boolean LuckPerms_Installed = false;
    protected boolean ProtocolLib_Installed = false;
    protected boolean GriefPrevention_Installed = false;
    protected final Map<UUID, BukkitTask> resetTasks = new ConcurrentHashMap<>();
    protected static final boolean viewChestDebugLogging = false;
    protected final Map<UUID, BukkitTask> invisTimers = new HashMap<>();
    protected static final Map<String, CachedParticleData> memoryCache = new HashMap<>();
    protected static final long EXPIRY_TIME = 1000 * 60 * 60; // 1 hour
    protected static final File PARTICLE_DIR = new File("plugins/Archistructures/particles");
    protected final boolean trialVersion;
    protected int trialNumber;
    protected final Map<Location, BukkitTask> visualBreakTimers = new HashMap<>();
    protected final File entityStorageDir;
    protected final File backpackDir;
    protected final File databaseFile;
    protected final File doubleDatabaseFile;
    protected final File viewOnlyChestDatabaseFile;
    protected final YamlConfiguration doubleDatabaseConfig;
    protected final YamlConfiguration databaseConfig;
    protected final Map<Location, BlockData> trackedBlockData = new HashMap<>();
    protected final YamlConfiguration viewOnlyChestConfig;
    protected final File ftLeaderboardFile;
    protected final Map<UUID, Integer> ftLeaderboard;
    protected final LuckPerms luckPerms;
    protected final Map<UUID, Set<Location>> trackedBlocks = new HashMap<>();
    protected final Set<Material> oresAndImportantBlocks = Set.of( Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE, Material.ANCIENT_DEBRIS);
    protected final Map<Location, BukkitTask> jesusTimers = new HashMap<>();
    protected final Map< UUID, Map<Location, BukkitTask> > jesusTimers2 = new ConcurrentHashMap<>();
    protected record CachedParticleData(List<Location> locations, long timestamp) {}
    protected static final int SKIN_HEIGHT = 64;
    record SkinLayers(java.awt.Color[][] base, java.awt.Color[][] overlay) {}
    
    public ExampleExpansion() {
        trialVersion = false;
        trialNumber = 1000;
        PluginManager pm = Bukkit.getPluginManager();
        try { if (pm.getPlugin("WorldEdit") != null && Objects.requireNonNull(pm.getPlugin("WorldEdit")).isEnabled()) {WorldEdit_Installed = true;}} catch (Exception e ) {}
        try {if (pm.getPlugin("WorldGuard") != null && Objects.requireNonNull(pm.getPlugin("WorldGuard")).isEnabled()) {WorldGuard_Installed = true;}        } catch (Exception e ) {}
        try {if (pm.getPlugin("LuckPerms") != null && Objects.requireNonNull(pm.getPlugin("LuckPerms")).isEnabled()) {LuckPerms_Installed = true;}        } catch (Exception e ) {}
        try { if (pm.getPlugin("ProtocolLib") != null && Objects.requireNonNull(pm.getPlugin("ProtocolLib")).isEnabled()) {ProtocolLib_Installed = true;}        } catch (Exception e ) {}
        try {if (pm.getPlugin("GriefPrevention") != null && Objects.requireNonNull(pm.getPlugin("GriefPrevention")).isEnabled()) {GriefPrevention_Installed = true;}        } catch (Exception e ) {}
        File viewOnlyChestDir = new File("plugins/Archistructures/viewonlychests/");
        if (!viewOnlyChestDir.exists()) viewOnlyChestDir.mkdirs();
        if (!PARTICLE_DIR.exists()) PARTICLE_DIR.mkdirs();
        this.viewOnlyChestDatabaseFile = new File(viewOnlyChestDir, "viewonlychests.yml");
        if (!viewOnlyChestDatabaseFile.exists()) {try {viewOnlyChestDatabaseFile.createNewFile();} catch (IOException e) {e.printStackTrace();}}
        this.viewOnlyChestConfig = YamlConfiguration.loadConfiguration(viewOnlyChestDatabaseFile);
        this.backpackDir = new File("plugins/Archistructures/backpacks/");
        if (!backpackDir.exists()) backpackDir.mkdirs();
        this.databaseFile = new File(backpackDir, "database.yml");
        if (!databaseFile.exists()) {try {databaseFile.createNewFile();} catch (IOException e) {e.printStackTrace();}}
        this.databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);
        this.doubleDatabaseFile = new File(backpackDir, "database2.yml");
        if (!doubleDatabaseFile.exists()) { try {doubleDatabaseFile.createNewFile();} catch (IOException e) {e.printStackTrace();}}
        this.doubleDatabaseConfig = YamlConfiguration.loadConfiguration(doubleDatabaseFile);
        entityStorageDir = new File("plugins/Archistructures/saved-entities/");
        if (!entityStorageDir.exists()) {entityStorageDir.mkdirs();}
        ftLeaderboardFile = new File("plugins/Archistructures/FTLeaderboard.txt");
        ftLeaderboard = new LinkedHashMap<>();
        ExampleExpansion2.loadFTLeaderboard(ftLeaderboard, ftLeaderboardFile);
        this.luckPerms = LuckPermsProvider.get();
        luckPerms.getUserManager();
        File shulkerDir = new File("plugins/Archistructures/shulkers/");
        if (!shulkerDir.exists()) shulkerDir.mkdirs();
        this.shulkerDatabaseFile = new File(shulkerDir, "shulkers.yml");
        if (!shulkerDatabaseFile.exists()) {try { shulkerDatabaseFile.createNewFile(); } catch(IOException e){ e.printStackTrace(); }}
        this.shulkerDatabaseConfig = YamlConfiguration.loadConfiguration(shulkerDatabaseFile);
    }

    @Override
    public boolean canRegister() {return true;}
    @Override
    public @NotNull String getAuthor() {return "Archistructure";}
    @Override
    public @NotNull String getIdentifier() {return "Archistructure";}
    @Override
    public @NotNull String getVersion() {return "1.0.0";}
    @Override
    @SuppressWarnings({"Overridden", "UnstableApiUsage", "deprecation"})
    public String getPlugin() {return null;}
    
    
    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player p, @NotNull String identifier) {

        if (trialCodeCheck(p)) return null;
        // SINGLY NESTED PLACEHOLDER SUPPORT - MUST BE FIRST
        if (identifier.startsWith("parseNested_")) identifier = ExampleExpansion2.parseNested(p, identifier);
        // INSERT HERE // if (checkCompatibility(p, "ProtocolLib")) return "§cProtocol Lib not installed!";
        if (identifier.equals("demoScreen")) {
            Runnable task = () -> {
                if (demoCmdRegistered.compareAndSet(false, true)) {
                    Plugin owner = papiPlugin();
                    if (owner == null) {
                        if (p != null) p.sendMessage("§cPlaceholderAPI not found as a plugin.");
                        return;
                    }
                    p.sendMessage("debug1");

                    registerRuntimeCommandSimple(owner, "stupid");

                    // Inspect mappings
                    try {
                        CommandMap map = getCommandMap();
                        Field f = map.getClass().getDeclaredField("knownCommands");
                        f.setAccessible(true);
                        @SuppressWarnings("unchecked")
                        Map<String, Command> known = (Map<String, Command>) f.get(map);

                        String prefix = owner.getName().toLowerCase(java.util.Locale.ROOT);
                        String nsKey = prefix + ":stupid";
                        boolean plain = known.containsKey("stupid");
                        boolean namespaced = known.containsKey(nsKey);

                        if (p != null) {
                            p.sendMessage("§7registered: plain=" + plain + " ns=" + namespaced);
                            p.sendMessage("§7Try: " + (plain ? "/stupid" : "") + (namespaced ? ("  /" + nsKey) : ""));
                        }
                    } catch (Exception ignored) {}

                    // Push command tree to clients
                    resyncCommands();

                    // Paper-only: updateCommands() (call via reflection so it's safe on Spigot)
                    if (p != null) {
                        try { p.getClass().getMethod("updateCommands").invoke(p); } catch (Throwable ignored) {}
                    }

                    // Optional: improve chat typing suggestions (chat box, not slash UI)
                    if (p != null) p.addCustomChatCompletions(java.util.List.of("stupid"));
                } else {
                    if (p != null) p.sendMessage("debug2");
                }
            };

            if (Bukkit.isPrimaryThread()) task.run();
            else Bukkit.getScheduler().runTask(papiPlugin(), task);

            return "test";
        }


        if (identifier.equals("demoScreen2")) {     // unregister /stupid
            Runnable task = () -> {
                try {
                    CommandMap map = getCommandMap();
                    unregisterIfPresent(map, "stupid");
                    resyncCommands();               // refresh client command tree
                    demoCmdRegistered.set(false);
                    if (p != null) p.sendMessage("§a/stupid command unregistered.");
                } catch (Exception e) {
                    if (p != null) p.sendMessage("§cFailed to unregister: " + e.getMessage());
                }
            };

            if (Bukkit.isPrimaryThread()) task.run();
            else Bukkit.getScheduler().runTask(papiPlugin(), task);
            p.updateCommands();

            return "";
        }

        
        if (identifier.startsWith("raminecartBoost_")) {
            String[] args = identifier.substring("raminecartBoost_".length()).split(",");
            if (args.length != 3) return "§cInvalid format";

            try {
                String[] locParts = args[0].split(":");
                if (locParts.length != 4) return "§cInvalid location";

                World world = Bukkit.getWorld(locParts[0]);
                if (world == null) return "§cInvalid world";

                double x = Double.parseDouble(locParts[1]);
                double y = Double.parseDouble(locParts[2]);
                double z = Double.parseDouble(locParts[3]);
                double newSpeed = Double.parseDouble(args[1]);
                long duration = Long.parseLong(args[2]);

                Location center = new Location(world, x, y, z);

                // Find nearest minecart within 2 blocks
                Minecart closest = world.getNearbyEntities(center, 2, 2, 2).stream()
                        .filter(e -> e instanceof Minecart)
                        .map(e -> (Minecart) e)
                        .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(center)))
                        .orElse(null);

                if (closest == null) return "§cNo minecart found nearby";

                UUID id = closest.getUniqueId();

                // Save original speed if not already stored
                originalMinecartSpeeds.putIfAbsent(id, closest.getMaxSpeed());

                // Apply new speed
                closest.setMaxSpeed(newSpeed);

                // Clear any existing reset task
                if (minecartResetTasks.containsKey(id)) {
                    minecartResetTasks.get(id).cancel();
                    minecartResetTasks.remove(id);
                }

                // Schedule reset if duration is not -1
                if (duration != -1) {
                    BukkitTask resetTask = Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), () -> {
                        Double original = originalMinecartSpeeds.remove(id);
                        if (original != null && closest.isValid()) {
                            closest.setMaxSpeed(original);
                        }
                        minecartResetTasks.remove(id);
                    }, duration);

                    minecartResetTasks.put(id, resetTask);
                }

                return "§aBoost applied to " + id;
            } catch (Exception e) {
                e.printStackTrace();
                return "§cError processing boost";
            }
        }

        
        
/*

        if (identifier.startsWith("minecartSpeed_")) {
            try {
                String[] parts = identifier.substring("minecartSpeed_".length()).split(",");
                if (parts.length != 2) return "§cInvalid format";

                double newSpeed = Double.parseDouble(parts[0]);
                UUID uuid = UUID.fromString(parts[1]);
                Entity e = Bukkit.getEntity(uuid);

                if (e == null || !e.isInsideVehicle()) {
                    return "§cInvalid target or not in vehicle";
                }

                Vehicle vehicle = (Vehicle) e.getVehicle();
                if (!(vehicle instanceof Minecart)) {
                    return "§cEntity is not in a minecart";
                }

                Minecart minecart = (Minecart) vehicle;
                double originalSpeed = minecart.getMaxSpeed();

                minecart.setMaxSpeed(newSpeed);

                return originalSpeed + " | " + newSpeed;

            } catch (Exception ex) {
                ex.printStackTrace();
                return "§cError: " + ex.getClass().getSimpleName();
            }
        }
*/

        if (identifier.startsWith("fakeGlow_")) {
            if (p == null) return null;

            String[] parts = identifier.substring("fakeGlow_".length()).split(",");
            if (parts.length != 2) return "§cInvalid args";

            try {
                UUID srcUUID = UUID.fromString(parts[0]);
                UUID targetUUID = UUID.fromString(parts[1]);

                Player observer = Bukkit.getPlayer(srcUUID);     // viewer
                Entity glowingTarget = Bukkit.getEntity(targetUUID);  // glowing entity

                if (observer == null || glowingTarget == null) return "§cEntity not found";

                // Create entity metadata packet
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                packet.getIntegers().write(0, glowingTarget.getEntityId());

                WrappedDataWatcher watcher = new WrappedDataWatcher();
                WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);

                // Metadata index 0 is entity flags. Bitmask: 0x40 = glowing
                byte flags = 0x40;

                // Set bitmask flag
                watcher.setObject(0, byteSerializer, flags);

                // Set data
                packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

                // Send packet only to the viewer
                ProtocolLibrary.getProtocolManager().sendServerPacket(observer, packet);
                return "§aGlow applied.";
            } catch (Exception ex) {
                ex.printStackTrace();
                return "§cError: " + ex.getMessage();
            }
        }

       
        if(identifier.equals("test2")) {
            if (p == null) return null;
            Location loc = p.getLocation().getBlock().getLocation().subtract(0,1,0); // Snap to block
            loc.getBlock().setType(Material.POWERED_RAIL);
            return "success";
        }
        
        
        if (identifier.equalsIgnoreCase("test1")) {
            BlockData data = Bukkit.createBlockData(Material.LODESTONE);
            p.sendBlockChange(p.getLocation().getBlock().getLocation(), data);
            return "§aSent lodestone block at your feet.";
        }

        if (identifier.startsWith("cosmicEnchant_"))  return cosmicEnchant(p, identifier);
        if (identifier.startsWith("spawnFakeEntity_")) return fakeEntitySpawn(identifier);
        if (identifier.startsWith("switcher_")) return switcher(identifier);
        if (identifier.startsWith("trackingCompassv2_")) return trackingCompass(p, identifier);
        
        // DONE BELOW, ABOVE NOT CHECKED
        if (identifier.startsWith("turret_"))  return ExampleExpansion2.handleTurret(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), identifier, turretLocks, lastPositions);
        if (identifier.startsWith("esee_")) return ExampleExpansion2.getString37(this, identifier);
        if (identifier.startsWith("isee_")) return ExampleExpansion2.getString36(this, identifier);
        if (identifier.startsWith("csee_")) return ExampleExpansion2.getString(this, identifier);
        if (identifier.startsWith("checkIsRealBoat_")) return ExampleExpansion2.getString35(identifier);
        if (identifier.startsWith("countPlayersInRegion_")) return ExampleExpansion2.getString(this, p, identifier);
        if (identifier.startsWith("growCropParticle_")) return ExampleExpansion2.getString(identifier);
        if (identifier.startsWith("trackLimitedRotation_")) return ExampleExpansion2.getString1(this, identifier);
        if (identifier.startsWith("trackv2_")) return ExampleExpansion2.getString2(this, identifier);
        if (identifier.startsWith("trackv3_")) return ExampleExpansion2.getString3(this, identifier);
        if (identifier.startsWith("fakeGlowing_")) return ExampleExpansion2.getString1(p, identifier);
        if (identifier.startsWith("checkElevators_")) return ExampleExpansion2.getString4(identifier);
        if (identifier.startsWith("elevatorUp_")) return ExampleExpansion2.getString5(identifier);
        if (identifier.startsWith("elevatorDown_")) return ExampleExpansion2.getString6(identifier);
        if (identifier.startsWith("lightning_")) return ExampleExpansion2.getString7(this, identifier);
        if (identifier.equals("hasSavedHotbar")) return ExampleExpansion2.getString(p);
        if (identifier.equals("saveHotbar")) return ExampleExpansion2.getString1(p);
        if (identifier.equals("restoreHotbar")) return ExampleExpansion2.getString2(p);
        if (identifier.startsWith("webhook_")) { String x = ExampleExpansion2.getString8(identifier); if (x != null) return x; }
        if (identifier.startsWith("laserDamageHostiles_")) {  String x = ExampleExpansion2.getString9(identifier); if (x != null) return x; }
        if (identifier.startsWith("fakeExplode_")) return ExampleExpansion2.getString10(this, identifier);
        if (identifier.startsWith("echo_")) return identifier.substring("echo".length());
        if (identifier.startsWith("clearBundle")) return ExampleExpansion2.getString2(p, identifier);
        if (identifier.startsWith("checkBundle_")) return ExampleExpansion2.getString3(p, identifier);
        if (identifier.startsWith("dice_")) return ExampleExpansion2.getString4(this, p, identifier);
        if (identifier.startsWith("thunderbird_")) return ExampleExpansion2.getString5(this, p, identifier);
        if (identifier.startsWith("hoverEject_")) return ExampleExpansion2.getString11(identifier);
        if (identifier.equals("horseHealth")) return ExampleExpansion2.getString3(p);
        if (identifier.equals("horseTotems")) return ExampleExpansion2.getString4(p);
        if (identifier.equals("checkAutoTransmission")) return ExampleExpansion2.getString5(p);
        if (identifier.startsWith("laserPointer_")) return ExampleExpansion2.getString6(p, identifier);
        if (identifier.startsWith("flashlight_")) return ExampleExpansion2.getString7(p, identifier);
        if (identifier.startsWith("tyv_001_")) return ExampleExpansion2.getString8(this, p, identifier);
        if (identifier.startsWith("recoveryTrack_")) return ExampleExpansion2.getString9(p, identifier);
        if (identifier.startsWith("xdesugun_001_")) return ExampleExpansion2.getString12(identifier);
        if (identifier.equals("vanta")) return ExampleExpansion2.getString6(this, p);
        if (identifier.startsWith("leaderboards_")) return ExampleExpansion2.getString13(this, identifier);
        if (identifier.equalsIgnoreCase("shulkerCheck")) return ExampleExpansion2.isShulkerBoxOpen(shulkerDatabaseConfig, p) ? "yes" : "no";
        if (identifier.startsWith("shulkerOpen_")) return ExampleExpansion2.getString10(this, p, identifier);
        if (identifier.startsWith("shulkerClose_")) return ExampleExpansion2.getString11(this, p, identifier);
        if (identifier.startsWith("vertigoHallucination_")) return ExampleExpansion2.getString12(p, identifier);
        if (identifier.equals("invis")) return ExampleExpansion2.getString7(invisTimers, p);
        if (identifier.startsWith("JESUS_")) return ExampleExpansion2.getString13(this, p, identifier);
        if (identifier.startsWith("setVelocity_")) return ExampleExpansion2.getString14(p, identifier);
        if (identifier.startsWith("chargeUp_")) return ExampleExpansion2.getString14(identifier);
        if (identifier.startsWith("ENCHANTRESSMINEREFILL_")) return ExampleExpansion2.getString15(this, identifier);
        if (identifier.startsWith("immortalize_")) return ExampleExpansion2.getString15(this, p, identifier);
        if (identifier.startsWith("debugStickRotate_")) return ExampleExpansion2.getString16(identifier);
        if (identifier.startsWith("debugStickInvert_")) return ExampleExpansion2.getString17(identifier);
        if (identifier.startsWith("searchExecutable_")) return ExampleExpansion2.getString16(p, identifier);
        if (identifier.startsWith("trackv2.-1_")) return ExampleExpansion2.getString18(this, identifier);
        if (identifier.startsWith("repeatingParticleText_")) return ExampleExpansion2.getString17(this, p, identifier);
        if (identifier.startsWith("nearestPlayerNotTeam2_")) return ExampleExpansion2.getString18(this, p, identifier);
        if (identifier.startsWith("nearestPlayerNotTeam3_")) return ExampleExpansion2.getString19(this, p, identifier);
        if (identifier.startsWith("visualBreak_")) return ExampleExpansion2.getString20(this, p, identifier);
        if (identifier.startsWith("PTFXCUBE_")) return ExampleExpansion2.getString19(this, identifier);
        if (identifier.startsWith("viewChest2_")) return ExampleExpansion2.getString21(this, p, identifier);
        if (identifier.startsWith("repeat_")) return identifier.substring("repeat".length());
        if (identifier.startsWith("chain_")) return ExampleExpansion2.getString20(this, identifier);
        if (identifier.startsWith("viewChest_")) return ExampleExpansion2.getString22(this, p, identifier);
        if (identifier.startsWith("blackHole_")) return ExampleExpansion2.getString21(this, identifier);
        if (identifier.startsWith("x_")) return ExampleExpansion2.getString22(identifier);
        if (identifier.startsWith("y_")) return ExampleExpansion2.getString23(identifier);
        if (identifier.startsWith("z_")) return ExampleExpansion2.getString24(identifier);
        if (identifier.startsWith("openBackpack2_")) return ExampleExpansion2.getString23(this, p, identifier);
        if (identifier.startsWith("openBackpack_")) return ExampleExpansion2.getString24(this, p, identifier);
        if (identifier.startsWith("bounce2_")) return ExampleExpansion2.getString(identifier, 0.05);
        if (identifier.startsWith("checkVelocity_")) return ExampleExpansion2.getString25(identifier);
        if (identifier.startsWith("bounce_")) return ExampleExpansion2.getString1(identifier, 0.05);
        if (identifier.startsWith("vacuumCleaner_")) return ExampleExpansion2.getString26(identifier);
        if (identifier.startsWith("remoteHopper_")) return ExampleExpansion2.getString27(identifier);
        if (identifier.startsWith("XRAY-")) return ExampleExpansion2.getString25(this, p, identifier);
        if (identifier.equals("eifolderresetperms") && p != null) return ExampleExpansion2.getString8(this, p);
        if (identifier.startsWith("bossbar_")) return ExampleExpansion2.getString26(p, identifier);
        if (identifier.equalsIgnoreCase("fireworkboost")) return ExampleExpansion2.getString9(p);
        if (identifier.equalsIgnoreCase("DN")) return ExampleExpansion2.processBook(p);
        if (identifier.startsWith("sr72-fly-")) return ExampleExpansion2.getString28(identifier);
        if (identifier.startsWith("variables-create-")) return ExampleExpansion2.getString29(identifier);
        if (identifier.equalsIgnoreCase("FT-increment")) { ExampleExpansion2.incrementFTScore(ftLeaderboard, ftLeaderboardFile, p.getUniqueId()); return "done"; }
        if (identifier.equalsIgnoreCase("FT-leaderboard")) return ExampleExpansion2.getString10(this, p);
        if (identifier.startsWith("trackImpact_")) return ExampleExpansion2.getString30(this, identifier);
        if (identifier.startsWith("trackImpact2_")) return ExampleExpansion2.getString31(this, identifier);
        if (identifier.startsWith("track_")) return ExampleExpansion2.getString32(this, identifier);
        if (identifier.startsWith("checkProfession_")) return ExampleExpansion2.getString33(identifier);
        if (identifier.startsWith("saveEntity_")) return ExampleExpansion2.getString34(this, identifier);
        if (identifier.startsWith("loadEntity_")) return ExampleExpansion2.getString27(this, p, identifier);
        switch (identifier) {
            case "cycle_playeruuid_head" -> {return ExampleExpansion2.getString11(p);}
            case "velocity" -> {return p.getVelocity().toString();}
            case "copyMainHand" -> {ExampleExpansion2.dropMainHandCopy(p);return "done";}
            case "critical" -> {return ExampleExpansion2.getString12(p);}
            case "rayTraceBlock" -> {return Objects.requireNonNull(Objects.requireNonNull(p.rayTraceBlocks(30)).getHitBlock()).toString();}
            case "rayTraceBlockCoords" -> {return Objects.requireNonNull(Objects.requireNonNull("" + p.rayTraceBlocks(30).getHitPosition().getBlockX() + " " + p.rayTraceBlocks(30).getHitPosition().getBlockY() + " " + p.rayTraceBlocks(30).getHitPosition().getBlockZ()  ));}
        }
        return null;
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

    private boolean trialCodeCheck(Player p) {
        if (trialVersion && trialNumber < 0) {
            p.sendMessage("§c§lYou have exceeded the limit of the free trial. Consider purchasing the full pack from ZestyBuffalo or do /papi reload to stick with the trial version");
            return true;
        }
        trialNumber--;
        return false;
    }

    protected @NotNull String trackingCompass(Player p, @NotNull String identifier) {
        String[] parts = identifier.substring("trackingCompassv2_".length()).split(",");
        if (parts.length != 3) return "§cInvalid format";

        try {
            UUID targetUUID = UUID.fromString(parts[0]);
            int time = Integer.parseInt(parts[1]);
            int slot = Integer.parseInt(parts[2]);

            Player target = Bukkit.getPlayer(targetUUID);
            if (target == null || !target.isOnline()) {
                return "§cFailed - Player offline";
            }

            Player compassHolder = (p.isOnline()) ? p.getPlayer() : null;
            if (compassHolder == null) {
                return "§cFailed - No requester";
            }

            ItemStack compassItem = ExampleExpansion2.getCompassInSlot(compassHolder, slot);
            if (compassItem == null || compassItem.getType() != Material.COMPASS) {
                return "§cFailed - Not holding compass";
            }

            if (!compassHolder.getWorld().equals(target.getWorld())) {
                CompassMeta meta = (CompassMeta) compassItem.getItemMeta();
                meta.setLodestone(null);
                meta.setLodestoneTracked(false);
                meta.setDisplayName("§cInvalid - Different World");
                compassItem.setItemMeta(meta);
                return "§cFailed - Different world";
            }

            Location lodestoneLoc = new Location(
                    target.getWorld(),
                    target.getLocation().getBlockX(),
                    target.getWorld().getMinHeight(),
                    target.getLocation().getBlockZ()
            );

            if (activeLodestones.containsKey(lodestoneLoc)) {
                activeLodestones.get(lodestoneLoc).cancel();
            }

            // Place the lodestone block
            lodestoneLoc.getBlock().setType(Material.LODESTONE);

            // Schedule revert
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    lodestoneLoc.getBlock().setType(Material.BEDROCK);
                    activeLodestones.remove(lodestoneLoc);
                }
            };
            task.runTaskLater(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), time);
            activeLodestones.put(lodestoneLoc, task);

            // Update compass meta
            CompassMeta meta = (CompassMeta) compassItem.getItemMeta();
            meta.setLodestone(lodestoneLoc);
            meta.setLodestoneTracked(true);
            compassItem.setItemMeta(meta);

            return "§aSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            return "§cFailed - Error";
        }
    }

    protected static String switcher(@NotNull String identifier) {
        String[] parts = identifier.substring("switcher_".length()).split(",");
        if (parts.length < 2) {
            return "§cInvalid format";
        }

        String current = parts[0];
        List<String> options = Arrays.asList(parts).subList(1, parts.length);

        int index = options.indexOf(current);
        if (index == -1) {
            // Not found, return first option
            return options.get(0);
        }

        // Compute next index (wrap around)
        int nextIndex = (index + 1) % options.size();
        return options.get(nextIndex);
    }

    protected static String fakeEntitySpawn(@NotNull String identifier) {
        String[] parts = identifier.substring("spawnFakeEntity_".length()).split(",");
        if (parts.length != 4) return "§cInvalid format";

        String playerName = parts[0];
        String entityTypeName = parts[1];
        String[] posParts = parts[2].split(":");
        String[] rotParts = parts[3].split(":");

        if (posParts.length != 4 || rotParts.length != 2) return "§cInvalid location/rotation";

        Player viewer = Bukkit.getPlayer(playerName);
        if (viewer == null) return "§cPlayer not online";

        World world = Bukkit.getWorld(posParts[0]);
        if (world == null) return "§cWorld not found";

        double x = Double.parseDouble(posParts[1]);
        double y = Double.parseDouble(posParts[2]);
        double z = Double.parseDouble(posParts[3]);
        float pitch = Float.parseFloat(rotParts[0]);
        float yaw = Float.parseFloat(rotParts[1]);

        EntityType entityType;
        try {
            entityType = EntityType.valueOf(entityTypeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "§cInvalid entity type";
        }

        UUID fakeUUID = UUID.randomUUID();
        int entityId = (int) (Integer.MAX_VALUE - Math.random() * Integer.MAX_VALUE);

        PacketContainer packet;
        int i = 0;
        try {
            // Build and send spawn packet
            packet = ProtocolLibrary.getProtocolManager()
                    .createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
            i++;
            packet.getIntegers().write(0, entityId);
            i++;
            packet.getUUIDs().write(0, fakeUUID);
            i++;
            packet.getEntityTypeModifier().write(0, entityType);
            i++;
            packet.getDoubles().write(0, x).write(1, y).write(2, z);
            i++;
            packet.getBytes().write(0, (byte) ((yaw % 360) * 256 / 360))
                    .write(1, (byte) ((pitch % 360) * 256 / 360));
            i++;
        } catch (Exception e) {
            return "§cFailed to create packet: " + i;
        }
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(viewer, packet);
            return fakeUUID.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "§cFailed to send packet";
        }
    }
    protected boolean checkCompatibility(Player p, String... pluginNames) {
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
                case "griefprevention":
                    if (!GriefPrevention_Installed) missing.add("GriefPrevention");
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



    private static void registerRuntimeCommandSimple(Plugin owner, String name) {
        String lower = name.toLowerCase(java.util.Locale.ROOT);
        try {
            CommandMap commandMap = getCommandMap();

            // Wipe any previous entries for safety
            unregisterIfPresent(commandMap, lower);

            // Build a plain Command (no PluginCommand)
            Command cmd = new Command(lower) {
                @Override
                public boolean execute(CommandSender sender, String label, String[] args) {
                    if (sender instanceof Player pl) pl.sendMessage("You ran /" + label + " 🎉");
                    else sender.sendMessage("Players only.");
                    return true;
                }

                @Override
                public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                    return java.util.Arrays.asList("one", "two", "three");
                }
            };
            cmd.setDescription("Runtime command (expansion)");

            String fallbackPrefix = owner.getName().toLowerCase(java.util.Locale.ROOT);

            // Try normal registration first
            boolean ok = commandMap.register(fallbackPrefix, cmd);

            // Inspect knownCommands
            Field f = commandMap.getClass().getDeclaredField("knownCommands");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Command> known = (Map<String, Command>) f.get(commandMap);

            String nsKey = fallbackPrefix + ":" + lower;
            boolean hasPlain = known.get(lower) == cmd;
            boolean hasNs    = known.get(nsKey) == cmd;

            // If neither mapping exists (or only namespaced and you want plain too), force-insert
            if (!hasPlain) {
                // Only add plain if there is no collision
                if (!known.containsKey(lower)) {
                    known.put(lower, cmd);
                    hasPlain = true;
                }
            }
            if (!hasNs) {
                known.put(nsKey, cmd);
                hasNs = true;
            }

            owner.getLogger().info("[expansion] /" + lower + " registered ok=" + ok
                    + " plain=" + hasPlain + " ns=" + hasNs);

        } catch (Exception e) {
            owner.getLogger().warning("registerRuntimeCommandSimple failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    private static CommandMap getCommandMap() throws Exception {
        Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        f.setAccessible(true);
        return (CommandMap) f.get(Bukkit.getServer());
    }

    @SuppressWarnings("unchecked")
    private static void unregisterIfPresent(CommandMap commandMap, String name) throws Exception {
        Field f = commandMap.getClass().getDeclaredField("knownCommands");
        f.setAccessible(true);
        Map<String, Command> known = (Map<String, Command>) f.get(commandMap);

        String lower = name.toLowerCase(java.util.Locale.ROOT);

        // Try to locate the primary command instance (by plain or namespaced key)
        Command target = known.get(lower);
        if (target == null) {
            for (Map.Entry<String, Command> e : known.entrySet()) {
                if (e.getKey().endsWith(":" + lower)) {
                    target = e.getValue();
                    break;
                }
            }
        }

        final Command finalTarget = target;

        known.entrySet().removeIf(e -> {
            String key = e.getKey();
            Command cmd = e.getValue();

            boolean sameInstance = (finalTarget != null && cmd == finalTarget);
            boolean keyMatches = key.equalsIgnoreCase(lower) || key.endsWith(":" + lower);

            boolean aliasMatch = false;
            for (String alias : cmd.getAliases()) {
                String a = alias.toLowerCase(java.util.Locale.ROOT);
                if (key.equalsIgnoreCase(a) || key.endsWith(":" + a)) {
                    aliasMatch = true;
                    break;
                }
            }

            return sameInstance || keyMatches || aliasMatch;
        });
    }


    private static void resyncCommands() {
        try {
            Bukkit.getServer().getClass().getMethod("syncCommands").invoke(Bukkit.getServer());
        } catch (Throwable ignored) {}
        for (Player pl : Bukkit.getOnlinePlayers()) {
            try { pl.getClass().getMethod("updateCommands").invoke(pl); } catch (Throwable ignored) {}
        }
    }
    private static Plugin papiPlugin() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
    }


}