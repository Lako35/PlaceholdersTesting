package org.example;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.ImmutableList;
import com.sk89q.worldedit.util.formatting.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.damage.DamageSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.projectiles.ProjectileSource;
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
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.ExampleExpansion2.*;


@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {

    // One task per turret UUID
    private static final Map<UUID, BukkitTask> ACTIVE_TURRET_TASKS = new ConcurrentHashMap<>();

    // A deadline (in ms, epoch) for each active turret; further detections extend this
    private static final Map<UUID, Long> ACTIVE_TURRET_DEADLINE_MS = new ConcurrentHashMap<>();
    private final AtomicBoolean demoCmdRegistered = new AtomicBoolean(false);

    private static final ConcurrentHashMap<UUID, VacuumJob> ACTIVE_VACUUMS = new ConcurrentHashMap<>();

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
    private static final Map<UUID, AtomicReference<LivingEntity>> ACTIVE_TURRET_TARGET = new ConcurrentHashMap<>();

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





    private static @Nullable Inventory getShulkerSnapshotInventory(org.bukkit.block.ShulkerBox shulker) {
        try {
            // Works on Spigot for item snapshots
            return shulker.getInventory();
        } catch (NoSuchMethodError | UnsupportedOperationException e) {
            // Some APIs (or older mappings) expose getSnapshotInventory()
            try {
                return (Inventory) org.bukkit.block.ShulkerBox.class
                        .getMethod("getSnapshotInventory")
                        .invoke(shulker);
            } catch (Throwable ignored) {
                return null;
            }
        }
    }

    private static @Nullable ItemStack getStackBySlot(Player p, int slot) {
        if (slot == 40) return p.getInventory().getItemInOffHand();
        if (slot >= 0 && slot < p.getInventory().getSize()) return p.getInventory().getItem(slot);
        return null;
    }


    /** Try PDC with EI namespace first, then fallback to serialized meta->PublicBukkitValues. */
    private static @org.jetbrains.annotations.Nullable String
    getEIidFromKeysOrSerialized(org.bukkit.inventory.ItemStack is) {
        if (is == null || !is.hasItemMeta()) return null;
        org.bukkit.inventory.meta.ItemMeta meta = is.getItemMeta();

        // 1) PersistentDataContainer: executableitems:ei-id
        try {
            org.bukkit.persistence.PersistentDataContainer pdc = meta.getPersistentDataContainer();
            org.bukkit.NamespacedKey eiKey = new org.bukkit.NamespacedKey("executableitems", "ei-id");
            String val = pdc.get(eiKey, org.bukkit.persistence.PersistentDataType.STRING);
            if (val != null) return val;
        } catch (Throwable ignored) {}

        // 2) Fallback: Serialized -> meta -> PublicBukkitValues -> "executableitems:ei-id"
        try {
            java.util.Map<String, Object> ser = is.serialize();
            Object metaObj = ser.get("meta");
            if (metaObj instanceof java.util.Map) {
                @SuppressWarnings("unchecked")
                java.util.Map<String, Object> metaMap = (java.util.Map<String, Object>) metaObj;

                Object pbvObj = metaMap.get("PublicBukkitValues");
                if (pbvObj instanceof java.util.Map) {
                    @SuppressWarnings("unchecked")
                    java.util.Map<String, Object> pbv = (java.util.Map<String, Object>) pbvObj;

                    Object raw = pbv.get("executableitems:ei-id");
                    if (raw != null) return raw.toString();
                }
            }
        } catch (Throwable ignored) {}

        return null;
    }

    public static String stripColors(String input) {
        if (input == null) return null;

        // (?i)           -> case-insensitive
        // [§&]           -> either the section sign (§) or &
        // [0-9A-FK-ORX]  -> any valid MC code character:
        //                   0-9, a-f (colors),
        //                   k-o (formats like obfuscated/bold/etc),
        //                   r (reset),
        //                   x (start of hex color "§x§1§2§3§4§5§6")
        //
        // This also correctly strips hex colors because:
        //   "§x§1§2§3§4§5§6Hello"
        // becomes:
        //   (remove §x) -> "§1§2§3§4§5§6Hello"
        //   (remove §1) -> "§2§3§4§5§6Hello"
        //   ...until -> "Hello"
        return input.replaceAll("(?i)[§&][0-9A-FK-ORX]", "");
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

    public static void ParticleCenterToCenter(UUID uuidA, UUID uuidB) {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin == null) return;

        // Quick upfront validation
        Entity eA0 = Bukkit.getEntity(uuidA);
        Entity eB0 = Bukkit.getEntity(uuidB);
        if (eA0 == null || eB0 == null || !eA0.isValid() || !eB0.isValid()) return;

        final int DURATION_TICKS = 80;     // 4 seconds
        final int MAX_PARTICLES  = 20;     // cap per line

        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (tick++ >= DURATION_TICKS) { cancel(); return; }

                // Live fetch each tick
                Entity a = Bukkit.getEntity(uuidA);
                Entity b = Bukkit.getEntity(uuidB);
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
                int n = Math.max(2, Math.min(MAX_PARTICLES, (int)Math.ceil(len * 3)));
                Vector step = AB.multiply(1.0 / (n - 1));

                // === Color this TICK: whole line uses one color ===
                // ticks 0..78 : green -> yellow -> orange -> red
                // tick 79     : gray tail (final frame)
                org.bukkit.Color bukkitColor;
                float scale = 0.6f; // small dust
                if (tick >= DURATION_TICKS - 6) {
                    bukkitColor = org.bukkit.Color.fromRGB(160,160,160); // final gray line
                } else {
                    double t = (double)tick / (double)(DURATION_TICKS - 6);
                    java.awt.Color c = gyorOverTime(t); // green->yellow->orange->red
                    bukkitColor = org.bukkit.Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue());
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
                if (t < 0) t = 0; if (t > 1) t = 1;

                // stops: 0.0   (0,255,0)   green
                //        0.33  (255,255,0) yellow
                //        0.66  (255,165,0) orange
                //        1.0   (255,0,0)   red
                if (t <= 1.0/3.0) {
                    return lerp(new java.awt.Color(0,255,0),   new java.awt.Color(255,255,0), t/(1.0/3.0));
                } else if (t <= 2.0/3.0) {
                    double u = (t - 1.0/3.0) / (1.0/3.0);
                    return lerp(new java.awt.Color(255,255,0), new java.awt.Color(255,165,0), u);
                } else {
                    double u = (t - 2.0/3.0) / (1.0/3.0);
                    return lerp(new java.awt.Color(255,165,0), new java.awt.Color(255,0,0),   u);
                }
            }
            private java.awt.Color lerp(java.awt.Color a, java.awt.Color b, double t) {
                int r = (int)Math.round(a.getRed()   + (b.getRed()   - a.getRed())   * t);
                int g = (int)Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
                int bl= (int)Math.round(a.getBlue()  + (b.getBlue()  - a.getBlue())  * t);
                return new java.awt.Color(
                        Math.max(0, Math.min(255, r)),
                        Math.max(0, Math.min(255, g)),
                        Math.max(0, Math.min(255, bl))
                );
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }


    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player p, @NotNull String identifier) {

        if (trialCodeCheck(p)) return null;
        // SINGLY NESTED PLACEHOLDER SUPPORT - MUST BE FIRST
        if (identifier.startsWith("parseNested_")) identifier = ExampleExpansion2.parseNested(p, identifier);
        // INSERT HERE // if (checkCompatibility(p, "ProtocolLib")) return "§cProtocol Lib not installed!";

        if (identifier.startsWith("particleLine_")) {
            try {
                String[] parts = identifier.substring("particleLine_".length()).split(",");
                
                if (5 != Integer.parseInt(parts[3])) return "";
                ParticleCenterToCenter(UUID.fromString(parts[0]), UUID.fromString(parts[1]));
                return "Tracking...";
            } catch (Exception e) {
                return "";
            }
        }
        
if (identifier.equals("version")) {
    return "0.0.0";
}

        if (identifier.startsWith("trackImpact3_")){
            String[] parts = identifier.substring("trackImpact3_".length()).split(",");

            UUID launcherUUID = UUID.fromString(parts[0]);
            World world = Bukkit.getWorld(parts[1]);
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);
            int z = Integer.parseInt(parts[4]);

            Location location = new Location(world, x, y, z);
            spawnCustomFireworkExplosion2(world, location);
            triggerPlayerHitEventV4_AOE( launcherUUID, location, 5, null);

            return "§6Impact triggered.";
        }

        if (identifier.startsWith("trackImpact4_")){
            String[] parts = identifier.substring("trackImpact4_".length()).split(",");

            UUID launcherUUID = UUID.fromString(parts[0]);
            UUID targetUUID = UUID.fromString(parts[1]);

            Entity target = Bukkit.getEntity(targetUUID);
            if (target == null) return "§cTarget not found";

            Location location = target.getLocation();
            spawnCustomFireworkExplosion2(target.getWorld(), location);
            triggerPlayerHitEventV4_AOE(launcherUUID, location, 5, target);

            return "§eTarget explosion triggered.";
        }

        if(identifier.startsWith("trackv4-a.1_")) {
            final String prefix = "trackv4-a.1_";

            final String[] parts = identifier.substring(prefix.length()).split(",");
            if (parts.length != 3) {
                return "§cUsage: %Archistructure_trackv4-a.1_CALLERUUID,TARGETUUID,LAUNCHERUUID%";
            }

            try {
                final UUID callerUUID   = UUID.fromString(parts[0]);
                final UUID targetUUID   = UUID.fromString(parts[1]);
                final UUID launcherUUID = UUID.fromString(parts[2]);

                final Entity caller = Bukkit.getEntity(callerUUID);
                final Entity target = Bukkit.getEntity(targetUUID);
                if (caller == null || target == null) return "§c§lMissile Impacted!";
                if (!caller.getWorld().equals(target.getWorld())) return "§c§lMissile Impacted.";

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
                            triggerPlayerHitEventV4_AOE(launcherUUID, c.getLocation(), 5.0, target); // 5-block radius AOE
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





        if( identifier.startsWith("stripColors_")) return stripColors(identifier.substring("stripColors_".length()));

        
        if (identifier.startsWith("countEIinShulker_")) {
            final String[] parts = identifier.substring("countEIinShulker_".length()).split(",");
            if (parts.length != 3) {
                return "§cUsage: %Archistructure_countEIinShulker_targets,SLOTS,EIID%";
            }

            final String target = parts[0].trim().toUpperCase(java.util.Locale.ROOT); // SHULKERS | INVENTORY | BOTH
            final String slotsSpec = parts[1].trim();                                  // "-1", "40", "3:5:8", "-1:3", or "ALL"
            final String eiid = parts[2];                                              // case-sensitive

            if (!target.equals("SHULKERS") && !target.equals("INVENTORY") && !target.equals("BOTH")) {
                return "0";
            }

            // Build deduped slot set
            java.util.Set<Integer> slots = new java.util.HashSet<>();
            final int invSize = p.getInventory().getSize();         // typically includes 0..40 (40=offhand)
            final int mainSlot = p.getInventory().getHeldItemSlot(); // 0..8 (hotbar)

            if (slotsSpec.equalsIgnoreCase("ALL")) {
                for (int i = 0; i < invSize; i++) slots.add(i);
                slots.add(40); // explicit offhand
            } else {
                for (String tok : slotsSpec.split(":")) {
                    tok = tok.trim();
                    if (tok.isEmpty()) continue;
                    try {
                        int slot = Integer.parseInt(tok);
                        slots.add(slot);
                    } catch (NumberFormatException ignored) {}
                }
                // Map -1 → mainhand slot
                if (slots.contains(-1)) {
                    slots.remove(-1);
                    slots.add(mainSlot);
                }
            }

            int total = 0;

// ---- Count inside shulkers located in the selected slots ----
            if (target.equals("SHULKERS") || target.equals("BOTH")) {
                for (int slot : slots) {
                    ItemStack container = getStackBySlot(p, slot);
                    if (container == null || container.getType() == Material.AIR) continue;

                    // Quick material guard helps avoid false positives on some metas
                    if (!container.getType().name().endsWith("SHULKER_BOX")) continue;

                    ItemMeta meta = container.getItemMeta();
                    if (!(meta instanceof BlockStateMeta bsm)) continue;
                    if (!bsm.hasBlockState()) continue;

                    BlockState state = bsm.getBlockState();
                    if (!(state instanceof org.bukkit.block.ShulkerBox shulker)) continue;

                    // Get the snapshot inventory safely across API variants
                    Inventory shulkerInv = getShulkerSnapshotInventory(shulker);
                    if (shulkerInv == null) continue;

                    for (ItemStack is : shulkerInv.getContents()) {
                        if (is == null || is.getType() == Material.AIR) continue;
                        String val = getEIidFromKeysOrSerialized(is);
                        if (val != null && val.equals(eiid)) {
                            total += is.getAmount();
                        }
                    }
                }
            }


            // Count items directly in the specified player slots
            if (target.equals("INVENTORY") || target.equals("BOTH")) {
                for (int slot : slots) {
                    org.bukkit.inventory.ItemStack stack = getStackBySlot(p, slot);
                    if (stack == null || stack.getType() == org.bukkit.Material.AIR) continue;

                    String val = getEIidFromKeysOrSerialized(stack);
                    if (val != null && val.equals(eiid)) {
                        total += stack.getAmount();
                    }
                }
            }

            return String.valueOf(total);
        }

        

        if (identifier.startsWith("vacuum_")) {
            final String[] parts = identifier.substring("vacuum_".length()).split(",");
            if (parts.length != 10) {
                return "§cInvalid format. Use: %Archistructure_vacuum_RANGE,FOV,IGNORESHULKERSTRUEFALSE,MAXENTITIES,INTERVAL,DURATION,THROUGHWALLS,PARTICLE,SEPARATION,PARTICLEINTERVAL%";
            }

            // --- Parse arguments (RANGE + FOV; no RADIUS) ---
            final double RANGE;
            final double FOV_DEGREES;
            final boolean IGNORE_SHULKER_ITEMS;
            final int MAXENTITIES;
            final int INTERVAL_TICKS;
            final int DURATION_TICKS;
            final boolean THROUGH_WALLS;
            final String PARTICLE_INPUT;
            final double SEPARATION;
            final int PARTICLE_INTERVAL_TICKS;

            try {
                RANGE = Double.parseDouble(parts[0]);
                FOV_DEGREES = Double.parseDouble(parts[1]);
                IGNORE_SHULKER_ITEMS = Boolean.parseBoolean(parts[2]);
                MAXENTITIES = Integer.parseInt(parts[3]);
                INTERVAL_TICKS = Integer.parseInt(parts[4]);
                DURATION_TICKS = Integer.parseInt(parts[5]);
                THROUGH_WALLS = Boolean.parseBoolean(parts[6]);
                PARTICLE_INPUT = parts[7];
                SEPARATION = Double.parseDouble(parts[8]);
                PARTICLE_INTERVAL_TICKS = Integer.parseInt(parts[9]);
            } catch (Exception e) {
                return "§cInvalid parameter types.";
            }

            // --- Validate off-hand shulker ---
            ItemStack off = p.getInventory().getItemInOffHand();
            if (off == null || off.getType() == Material.AIR || !off.getType().name().endsWith("SHULKER_BOX")) {
                return "§7(No shulker in offhand; vacuum idle)";
            }
            BlockStateMeta baseMeta = (off.getItemMeta() instanceof BlockStateMeta bsm) ? bsm : null;
            if (baseMeta == null || !(baseMeta.getBlockState() instanceof ShulkerBox baseBox)) {
                return "§7(Offhand item is not a shulker blockstate)";
            }

            final World world = p.getWorld();

            // --- Resolve particle (supports DUST:#RRGGBB<scale>) ---
            final Particle particle;
            final Particle.DustOptions dustOpt;
            {
                Particle tmpParticle;
                Particle.DustOptions tmpDust = null;
                final String u = PARTICLE_INPUT.toUpperCase(Locale.ROOT);
                if (u.startsWith("DUST:")) {
                    try {
                        String hexScale = PARTICLE_INPUT.substring(5);
                        String hex = hexScale.substring(0, 6);
                        float scale = Float.parseFloat(hexScale.substring(6));
                        java.awt.Color c = java.awt.Color.decode("#" + hex);
                        tmpParticle = Particle.DUST;
                        tmpDust = new Particle.DustOptions(
                                Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()), scale);
                    } catch (Exception e) {
                        return "§cInvalid DUST format. Use DUST:#RRGGBB<scale>";
                    }
                } else {
                    try {
                        tmpParticle = Particle.valueOf(u);
                    } catch (Exception e) {
                        return "§cUnknown particle: " + PARTICLE_INPUT;
                    }
                }
                particle = tmpParticle;
                dustOpt = tmpDust;
            }

            // --- Early capacity check (optional, not authoritative because box may change during task) ---
            if (isShulkerFull(baseBox.getInventory())) {
                return "§7(Offhand shulker is full)";
            }

            // ===== Enforce single job per player; reset timer if already active =====
            VacuumJob existing = ACTIVE_VACUUMS.get(p.getUniqueId());
            if (existing != null && !existing.isCancelled()) {
                existing.resetTimer();
                return "§aVacuum timer reset.";
            }

            VacuumJob job = new VacuumJob(
                    getPlaceholderAPI(), p,
                    RANGE, FOV_DEGREES, IGNORE_SHULKER_ITEMS,
                    MAXENTITIES, INTERVAL_TICKS, DURATION_TICKS,
                    THROUGH_WALLS, particle, dustOpt, SEPARATION, PARTICLE_INTERVAL_TICKS
            );
            job.runTaskTimer(getPlaceholderAPI(), 0L, Math.max(1L, INTERVAL_TICKS));
            ACTIVE_VACUUMS.put(p.getUniqueId(), job);

            return "§aVacuum started.";
        }





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

        if (identifier.startsWith("turret_")) {

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

            String[] parts = identifier.substring("turret_".length()).split(",");
            // You access up to parts[19]; require at least 20
            if (parts.length < 20) return "§cInvalid format";

            try {
                String worldName = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int z = Integer.parseInt(parts[3]);
                double radius = Double.parseDouble(parts[4]);
                String projectile = parts[5];
                double speed = Double.parseDouble(parts[6]);
                String targetType = parts[7];
                String ownerUUID = parts[8];
                int lockTime = Integer.parseInt(parts[9]);     // ticks
                int lifespan = Integer.parseInt(parts[10]);    // ticks
                int interval = Integer.parseInt(parts[11]);    // ticks
                double damage = Double.parseDouble(parts[12]);
                Particle particle = Particle.valueOf(parts[13].toUpperCase(Locale.ROOT));
                double spacing = Double.parseDouble(parts[14]);
                boolean predictive = "1".equals(parts[15].trim()) || Boolean.parseBoolean(parts[15]);
                String sound = parts[16];
                float soundDistance = Float.parseFloat(parts[17]);
                float pitch = Float.parseFloat(parts[18]);
                float volume = Float.parseFloat(parts[19]);
                List<String> projectileTags = Arrays.asList(parts).subList(20, parts.length);

                World world = Bukkit.getWorld(worldName);
                if (world == null) return "§cInvalid world";

                final Location base = new Location(world, x + 0.5, y + 1.5, z + 0.5);


                org.bukkit.Location cen = base;
                double r1 = 10;
                String label = "Sentry Gun";


                double r2 = r1 * r1;
 
                final double radiusSq = radius * radius;

                ArmorStand turret = world.getNearbyEntities(base, 2, 2, 2).stream()
                        .filter(ArmorStand.class::isInstance)
                        .map(ArmorStand.class::cast)
                        .filter(e -> e.getScoreboardTags().contains("ArchiTurret"))
                        .findFirst()
                        .orElse(null);
                if (turret == null) return "§cNo turret found";

                // --- multischeduling guard ---
                UUID turretId = turret.getUniqueId();
                BukkitTask running = ACTIVE_TURRET_TASKS.get(turretId);
                long extendMs = Math.max(50L, lockTime * 50L); // ticks -> ms (20t = 1s)
                // --------------------------------

                Set<String> turretTags = turret.getScoreboardTags();

                // Helper: find closest valid target WITH VLOS (used only on acquisition/refresh checks)
                java.util.function.Supplier<LivingEntity> findClosestWithVLOS = () -> {
                    LivingEntity best = null;
                    double bestSq = radiusSq;
                    for (Entity e : world.getNearbyEntities(base, radius, radius, radius)) {
                        if (!(e instanceof LivingEntity le) || e.isDead()) continue;
                        if (!e.getWorld().equals(world)) continue;
                        if (!ExampleExpansionUtils.isValidTargetType(e, targetType, HOSTILE_TYPES, ownerUUID)) continue;
                        if (ExampleExpansionUtils.hasMatchingTag(e, turretTags)) continue;

                        Location mid = le.getLocation().clone().add(0, le.getHeight() / 2.0, 0);
                        double dSq = mid.distanceSquared(base);
                        if (dSq > bestSq) continue;

                        // VLOS check at acquisition/refresh only
                        if (!ExampleExpansionUtils.canSee(base, mid)) continue;

                        best = le;
                        bestSq = dSq;
                    }
                    return best;
                };

                // ---- Decision flow per your spec ----
                if (running != null && !running.isCancelled()) {
                    // Turret is already active: check for a NEW closest valid target IN VLOS
                    LivingEntity nearestVisible = findClosestWithVLOS.get();

                    if (nearestVisible != null) {
                        // Has lock & valid target in VLOS -> reset timer and retarget to the closest valid target
                        ACTIVE_TURRET_DEADLINE_MS.put(turretId, System.currentTimeMillis() + extendMs);
                        ACTIVE_TURRET_TARGET.computeIfAbsent(turretId, k -> new AtomicReference<>())
                                .set(nearestVisible);
                        turretLocks.put(base, nearestVisible.getUniqueId());
                        return nearestVisible.getUniqueId().toString() + "|" + nearestVisible.getVelocity();
                    } else {
                        // Has lock & NO new valid targets in VLOS -> keep firing current lock; DO NOT reset timer
                        return "§7Continuing current lock";
                    }
                }

                // Not active: try to acquire the closest valid target IN VLOS
                LivingEntity acquired = findClosestWithVLOS.get();
                if (acquired == null) {
                    return "§cNo targets found";
                }

                // Lock it and start one repeating task
                turretLocks.put(base, acquired.getUniqueId());
                ACTIVE_TURRET_DEADLINE_MS.put(turretId, System.currentTimeMillis() + extendMs);
                AtomicReference<LivingEntity> currentRef = new AtomicReference<>(acquired);
                ACTIVE_TURRET_TARGET.put(turretId, currentRef);

                BukkitRunnable runner = new BukkitRunnable() {
                    private void cleanupAndCancel() {
                        turretLocks.remove(base);
                        ACTIVE_TURRET_TASKS.remove(turretId);
                        ACTIVE_TURRET_DEADLINE_MS.remove(turretId);
                        ACTIVE_TURRET_TARGET.remove(turretId);
                        cancel();
                    }

                    @Override
                    public void run() {
                        // End when the deadline expires
                        Long deadline = ACTIVE_TURRET_DEADLINE_MS.get(turretId);
                        if (deadline == null || System.currentTimeMillis() >= deadline) {
                            cleanupAndCancel();
                            return;
                        }

                        LivingEntity cur = currentRef.get();
                        if (cur == null || cur.isDead() || !cur.getWorld().equals(world)) {
                            // Current target no longer valid -> end (no mid-fight reacquisition)
                            cleanupAndCancel();
                            return;
                        }

                        // NO VLOS checks during firing; can shoot through blocks
                        Location curMid = cur.getLocation().clone().add(0, cur.getHeight() / 2.0, 0);

                        // Compute velocity
                        Vector velocity;
                        if (predictive) {
                            Location targetLoc = cur.getLocation();
                            targetLoc.setY(targetLoc.getY() + cur.getHeight() / 2.0);
                            Vector R = targetLoc.toVector().subtract(base.toVector());
                            Vector V = cur.getVelocity();
                            UUID targetUUID = cur.getUniqueId();

                            boolean yDrag = Math.abs(V.getY() + 0.0784) < 0.0001;
                            boolean xZero = Math.abs(V.getX()) < 0.0001;
                            boolean zZero = Math.abs(V.getZ()) < 0.0001;

                            if (xZero && zZero) {
                                Location last = lastPositions.get(targetUUID);
                                if (last != null) {
                                    Vector delta = targetLoc.toVector().subtract(last.toVector());
                                    V = delta.multiply(0.3); // tunable
                                } else {
                                    V = new Vector(0, 0, 0);
                                }
                                lastPositions.put(targetUUID, targetLoc.clone());
                            } else {
                                lastPositions.remove(targetUUID);
                                if (yDrag && xZero) V.setY(0);
                            }

                            double Sm = speed;
                            double a = V.dot(V) - Sm * Sm;
                            double b = 2 * R.dot(V);
                            double c = R.dot(R);
                            double disc = b * b - 4 * a * c;

                            if (disc < 0 || a == 0) {
                                velocity = R.normalize().multiply(Sm);
                            } else {
                                double sqrt = Math.sqrt(disc);
                                double t1 = (-b - sqrt) / (2 * a);
                                double t2 = (-b + sqrt) / (2 * a);
                                double t = t1 > 0 ? t1 : (t2 > 0 ? t2 : -1);
                                if (t <= 0) {
                                    velocity = R.normalize().multiply(Sm);
                                } else {
                                    Vector intercept = targetLoc.toVector().add(V.clone().multiply(t));
                                    velocity = intercept.subtract(base.toVector()).normalize().multiply(Sm);
                                }
                            }
                        } else {
                            velocity = curMid.toVector().subtract(base.toVector()).normalize().multiply(speed);
                        }

                        Entity proj = world.spawnEntity(base, EntityType.valueOf(projectile.toUpperCase(Locale.ROOT)));
                        proj.setVelocity(velocity);
                        proj.setCustomNameVisible(false);
                        proj.setSilent(true);
                        proj.setGravity(false);

                        // Use your plugin instance if you have it; keeping PlaceholderAPI per your original
                        Bukkit.getScheduler().runTaskLater(
                                Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                                () -> { if (proj.isValid() && !proj.isDead()) proj.remove(); },
                                lifespan
                        );

                        if (proj instanceof Arrow arrow) arrow.setDamage(damage);

                        if (ownerUUID != null && !ownerUUID.isEmpty()) {
                            try {
                                UUID uuid = UUID.fromString(ownerUUID);
                                ProjectileSource shooter = Bukkit.getPlayer(uuid);
                                if (proj instanceof Projectile p && shooter != null) {
                                    p.setShooter(shooter);
                                }
                            } catch (IllegalArgumentException ignored) { /* bad UUID */ }
                        }

                        proj.setMetadata("ArchistructureTurret",
                                new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), damage));
                        for (String tag : projectileTags) proj.addScoreboardTag(tag);

                        // Beam (no VLOS; purely visual)
                        Vector dir = curMid.toVector().subtract(base.toVector()).normalize();
                        double len = base.distance(curMid);
                        for (double d = 0; d <= len; d += spacing) {
                            Location point = base.clone().add(dir.clone().multiply(d));
                            world.spawnParticle(particle, point, 1);
                        }

                        turret.teleport(
                                turret.getLocation().setDirection(
                                        cur.getLocation().toVector().subtract(turret.getLocation().toVector())
                                )
                        );
                        for (Player t : Bukkit.getOnlinePlayers()) {
                            if (!t.getWorld().equals(world)) continue;
                            if (t.getLocation().distanceSquared(base) <= soundDistance * soundDistance) {
                                t.playSound(base, sound, SoundCategory.AMBIENT, volume, pitch);
                            }
                        }
                    }
                };

                int period = Math.max(1, interval);
                BukkitTask task = runner.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), 0L, period);
                ACTIVE_TURRET_TASKS.put(turretId, task);

                return acquired.getUniqueId().toString() + "|" + acquired.getVelocity();

            } catch (Exception e) {
                e.printStackTrace();
                return "§cNo targets found";
            }
        }

        if (identifier.startsWith("cosmicEnchant_"))  return cosmicEnchant(p, identifier);
        if (identifier.startsWith("spawnFakeEntity_")) return fakeEntitySpawn(identifier);
        if (identifier.startsWith("switcher_")) return switcher(identifier);
        if (identifier.startsWith("trackingCompassv2_")) return trackingCompass(p, identifier);
        
        // DONE BELOW, ABOVE NOT CHECKED
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
        if (identifier.startsWith("echo_")) return identifier.substring("echo_".length());
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
        if (identifier.startsWith("repeat_")) return identifier.substring("repeat_".length());
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