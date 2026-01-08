package org.example;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.*;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.EulerAngle;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
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
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.ExampleExpansion2.*;


@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {
    
    // Globals

    private static final java.util.concurrent.ConcurrentHashMap<UUID, org.bukkit.scheduler.BukkitTask> TRACKV4A2_STEER
            = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<UUID, org.bukkit.scheduler.BukkitTask> TRACKV4A2_LINE
            = new java.util.concurrent.ConcurrentHashMap<>();

    // missile -> target mapping (what you asked)
    private static final java.util.concurrent.ConcurrentHashMap<UUID, UUID> TRACKV4A2_MISSILE_TO_TARGET
            = new java.util.concurrent.ConcurrentHashMap<>();

    private static final class ArmedTrack {
        final UUID launcherId;
        volatile UUID targetId;
        volatile long armedAtMs;

        ArmedTrack(UUID launcherId, UUID targetId) {
            this.launcherId = launcherId;
            this.targetId = targetId;
            this.armedAtMs = System.currentTimeMillis();
        }
    }
    private static final java.util.concurrent.ConcurrentHashMap<UUID, ArmedTrack> STINGER_ARMED = new java.util.concurrent.ConcurrentHashMap<>();

    // launcher -> prelaunch line task (ONLY ONE at a time)
    private static final java.util.concurrent.ConcurrentHashMap<UUID, org.bukkit.scheduler.BukkitTask> STINGER_PRELAUNCH_LINE = new java.util.concurrent.ConcurrentHashMap<>();

    // missile -> missile line task (ONE PER MISSILE)
    private static final java.util.concurrent.ConcurrentHashMap<UUID, org.bukkit.scheduler.BukkitTask> STINGER_MISSILE_LINES = new java.util.concurrent.ConcurrentHashMap<>();

    // missile -> target mapping (explicitly requested)
    private static final java.util.concurrent.ConcurrentHashMap<UUID, UUID> STINGER_MISSILE_TO_TARGET = new java.util.concurrent.ConcurrentHashMap<>();

    // Optional: persist last full lock even after cleanup()
    private static final java.util.concurrent.ConcurrentHashMap<UUID, UUID> STINGER_LAST_FULL_LOCK_PERSIST = new java.util.concurrent.ConcurrentHashMap<>();
    
    
    
    private static final java.util.concurrent.ConcurrentHashMap<String, java.util.Set<java.util.UUID>> ACTIVE_MISSILES_MULTI =
            new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<java.util.UUID, java.util.UUID> STINGER_LAST_FULL_LOCK =
            new java.util.concurrent.ConcurrentHashMap<>();

    private enum StingerMode { PLAYERSONLY, HOSTILESONLY, ENTITIES, ALL }

    private static final class StingerLockState {
        final UUID playerId;

        // doppler state
        float dopplerPitch = 0f;
        int   lastBeepTick = 0;

        // Config (updated on each placeholder parse)
        volatile StingerMode mode = StingerMode.ALL;
        volatile int intervalTicks = 5;
        volatile int nIntervals = 4;
        volatile double range = 64.0;
        volatile boolean passThruBlocks = true;
        volatile boolean doppler = true;
        volatile double fovDeg = 45.0;

        // Expiry (pings update this)
        volatile long expiresAtTick = 0;

        // Runtime
        volatile UUID currentTargetId = null;
        volatile UUID lastFullLockId = null;

        // Queue of "switched targets"
        final java.util.ArrayDeque<UUID> switchedQueue = new java.util.ArrayDeque<>();
        final int maxQueue = 16;

        // Lock progress
        int progressTicks = 0;          // 0..60 for 3 seconds
        boolean fullyLocked = false;

        // Sound scheduling
        int localTick = 0;              // increments each LOCK task tick
        int nextBeepAtLocalTick = 0;

        // Reveal-glow bookkeeping
        UUID lastRevealTargetPlayerId = null;
        boolean revealOn = false;
        int nextRevealAtLocalTick = 0; // <--- ADD (refresh glow every few ticks)

        // For “lock lost” gray line
        org.bukkit.Location lastTargetLoc = null;

        StingerLockState(UUID playerId) { this.playerId = playerId; }

        // ---- NEW ----
        private void resetLockAndDoppler() {
            progressTicks = 0;
            fullyLocked = false;

            dopplerPitch = 0f;
            lastBeepTick = localTick;
            nextBeepAtLocalTick = localTick; // allow immediate beep on new target
        }

        /** Use this instead of assigning currentTargetId directly. */
        void setTarget(org.bukkit.entity.Entity e) {
            if (e == null) return;
            UUID nid = e.getUniqueId();

            if (java.util.Objects.equals(currentTargetId, nid)) {
                // keep lastTargetLoc fresh, but DO NOT reset ramp
                lastTargetLoc = e.getLocation().clone();
                return;
            }

            currentTargetId = nid;
            lastTargetLoc = e.getLocation().clone();
            resetLockAndDoppler();
        }

        /** Use this instead of "currentTargetId = null". */
        void clearTarget(org.bukkit.Location lastKnown) {
            if (lastKnown != null) lastTargetLoc = lastKnown.clone();
            currentTargetId = null;
            resetLockAndDoppler();
        }

        void pushSwitched(UUID id) {
            if (id == null) return;
            if (switchedQueue.contains(id)) return;
            switchedQueue.addLast(id);
            while (switchedQueue.size() > maxQueue) switchedQueue.pollFirst();
        }
    }


    private static final java.util.concurrent.ConcurrentHashMap<UUID, StingerLockState> STINGER_LOCKS = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<UUID, org.bukkit.scheduler.BukkitTask> STINGER_LOCK_TASKS = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<UUID, org.bukkit.scheduler.BukkitTask> STINGER_RESET_QUEUES = new java.util.concurrent.ConcurrentHashMap<>();




    protected final YamlConfiguration g18;

    public  final Set<EntityType> HMsetnoy2un2yundt ;

    private static final Map<UUID, BukkitTask> tyto2uny2uf4ntdoy2utd = new ConcurrentHashMap<>();

    public static final String k2tdfwfktdfdw = "Invalid world";

    private final java.util.Map<java.util.UUID, org.bukkit.scheduler.BukkitTask> burstTasks = new java.util.concurrent.ConcurrentHashMap<>();

    private  final String f112 = "🛂";
    private /*static*/ final String lpkb6 =
            "MIICITANBgkqhkiG9w0BAQEFAAOCAg4AMIICCQKCAgBnVM3heeCdB097Nt5U5XFttJTSdnen80VhmzxZg0DRJrr5A//l3xDh2+T+FOjddUPsixcd+Fwu48J2a54MjmFu3gv67d0479vKgZksbO8Rk2XiLyXPLJ9ChFPAagXTuCqIau8mfzazSFwzY6exHnTzOpU4cFUQDMEeurM3bUPQAPYWK4cPkS2Px14AV8+XzgIqKlra9u/BVDT0K/+8owzrsA+tEBg7IXOaTSnME0kC0nPSfkPV+Qhx7TumyMQjbjG7B8YhLG+GEozChdLUMzwIK6SwKCENT2XGGxHxdSre9BfdFkhyRBs3g1tz/Zew5MA9h0rU/DDEKhbq41ya1o9VInfBLXlsc52sqwmiMHGYJ9HnKxnf2ppbAu7NYmo4Z9njQyKq0yt3ERmL+BZQPLOb8f+s5ONbxWi1lN7NF9JmkpBOZcwMVsms0AFckjIwLSEkPCldFdOthascfj8NDyrg6WAXhPgu5/85yE2UH7LeaLP9nnoCJoJltUUQ9BFp45jcLhrOPxr5USNtpODOIhT/AQjWZptAxnA1AHKEt/J8Mu0ylKta5Fm4JKaXOTHlo1WuMNPCtPsx5VUBv2L+ZaQAagPNjLJlwa2MoDJ7m1m2Ams7VJNe3OUrBT3D3xBvbaclR6eVhw/3Jncdk4UWV70gcGP71VTnbGpJOtj9WzQJ/QIDAQAB";
    public /*static*/ final long dnkpydunpdyupfnd = 1000L;
    public /*static*/ final String WWW_ANARCHISTRUCTURE_COM = "www.anarchistructure.com";
    public /*static*/ final String DENTISTRY_ANARCHISTRUCTURE_COM = "dentistry.anarchistructure.com";
    public /*static*/ final String TLS = "TLS";
    public /*static*/ final String ppi = "PlaceholderAPI";
    public /*static*/ final String salcv = "Starting Async License Validation";
    public /*static*/ final String ytuwfndyunwfd = "https://www.anarchistructure.com/PiLightSpeed";
    public /*static*/ final String dyunwfydunwfd = "GET";
    public /*static*/ final int tyunwfdwfd = 5000;
    public /*static*/ final int INT = 200;
    public /*static*/ final String duwnfdyuwnfd = "FORBIDDEN";
    public /*static*/ final boolean arsdienwdhw = false;
    public /*static*/ final int INT3 = 1;
    public /*static*/ final boolean NEW_VALUE1 = true;
    public /*static*/ final String tlk = "tolkien";
    public /*static*/ final String RSA_ECB_PKCS_1_PADDING = "RSA/ECB/PKCS1Padding";
    public /*static*/ final String NEW_VALUE_3 = "x";
    public /*static*/ final String piarc = "plugins/Archistructures";
    public /*static*/ final String cfgy = "config.yml";
    public /*static*/ final String test32 = "Death Note";
    public /*static*/ final String ymhawb = "You must hold a writable book!";
    public /*static*/ final String nw = "Nothing written!";
    public /*static*/ final int I = 0;
    public /*static*/ final String m23 = "§m";
    public /*static*/ final String ymo = "You may only add ONE entry at a time!";
    public /*static*/ final String R = "§r ";
    public /*static*/ final String er = "Entity ";
    public /*static*/ final String er2 = " is not online!";
    public /*static*/ final String ind = " is not damageable!";
    public /*static*/ final String te = "Target eliminated: ";
    public /*static*/ final String rsm = "Returning success message...";
    public /*static*/ final String yhe = "&6&lYou have eliminated ";
    public /*static*/ final String peei = "plugins/ExecutableItems/items/";
    public /*static*/ final String ei = "ei.item.";
    public /*static*/ final String yt = ".yml";
    public /*static*/ final String nst = "";
    public /*static*/ final int xm = 5;
    public /*static*/ final String LAST_CHUNK_Z = "last_chunk_z";
    public /*static*/ final String LAST_Y = "last_y";
    public /*static*/ final int INT4 = 16;
    public /*static*/ final String SLOT = "slot";
    public /*static*/ final int INT5 = 27;
    public /*static*/ final int xtxtxt = 6;
    public /*static*/ final String dccs = "Double chest contents saved for chest ID: ";
    public /*static*/ final String fts = "Failed to save double chest contents for chest ID: ";
    public /*static*/ final int INT6 = 54;
    public /*static*/ final String corporatesecurity = "ANVIL";
    public /*static*/ final String Gradspecisoauce = "Failed to convert block at ";
    public /*static*/ final String fastfood = ": ";
    public /*static*/ final String privateinvestigator = "BlackHolev2";
    public /*static*/ final int INT7 = 4;
    public /*static*/ final int ccp = 3;
    public /*static*/ final String steak = "slot0";
    public /*static*/ final String gusfring = "PublicBukkitValues: |-";
    public /*static*/ final String ehmantrout = "ENTITY";
    public /*static*/ final String amiunderarrest = "PLAYER";
    public /*static*/ final String oraminot = "BOTH";
    public /*static*/ final int suppose = 7;
    public /*static*/ final int mill2 = 2;
    public /*static*/ final int drain = 64;
    public /*static*/ final String ed = "elevatorDown_";
    public /*static*/ final String some = "§cError";
    public /*static*/ final String keep = ",";
    public /*static*/ final int low = 10;
    public /*static*/ final String twentythreedegrees = "Dual Elevators";
    public /*static*/ final String nbf = "§cNo block found";
    public /*static*/ final double wife = 1.0e-6;
    public /*static*/ final String complicated = "elevatorUp_";
    public /*static*/ final String basketball = "checkElevators_";
    public /*static*/ final String bt = "both";
    public /*static*/ final String upt = "up";
    public /*static*/ final String soalp = "down";
    public /*static*/ final String tmc = "none";
    public /*static*/ final String excepthomer = "trackEntity_";
    public /*static*/ final String iou = "§cInvalid or unloaded entity.";
    public /*static*/ final String dontwantmargetoknow = "§cInvalid player.";
    public /*static*/ final String lessofaman = "§aTracking entity ";
    public /*static*/ final String ballet = "§cError tracking entity.";
    public /*static*/ final String magellan = "§cNo entity tracked.";
    public /*static*/ final String dance = "minecraft:";
    public /*static*/ final String nationalanthems = "unknown";
    public /*static*/ final String cigarrette = "ei run-custom-trigger trigger:Stinger81Hit player:";
    public /*static*/ final double debtsoff = 0.5;
    public /*static*/ final double callitof = 200.0;
    public /*static*/ final String clicks = "minecraft:w ei run-custom-trigger trigger:Stinger81Hit player:";
    public /*static*/ final double emotionalthermo = 0.33;
    public /*static*/ final int somature = 300;
    public /*static*/ final int dontvanish = 80;
    public /*static*/ final int shapeyou = 25;
    public /*static*/ final double tsr = 1.0;
    public /*static*/ final float whoasked = 0.6f;// same as before
    public /*static*/ final float biological = whoasked;
    public /*static*/ final double whocantreceive = 3.0;
    public /*static*/ final int costofgrowinguptoofast = 255;
    public /*static*/ final double whatkindofadulthood = 2.0;
    public /*static*/ final int nobodysrescuingyou = 165;
    public /*static*/ final long ihearyounow = 0L;
    public /*static*/ final long healing = 1L;
    public /*static*/ final String toescortyou = "WHITELIST:";
    public /*static*/ final String outtahere = "BLACKLIST:";
    public /*static*/ final String ost = "or";
    public /*static*/ final String naltextc = "§cNot allowed to execute this command";
    public /*static*/ final double getsshoes = 1e-9;
    public /*static*/ final String pn = "parseNested_";
    public /*static*/ final double esetawftawft = 0.0;
    public /*static*/ final double doyouknow = 10.0;
    public /*static*/ final double thepeoplewholive = 0.1;
    public /*static*/ final String whurl = "https://discord.com/api/webhooks/1434279057297506507/hYzGetkkgDbwfJvj1-TEJioRWVNT8wjY0_Hmyp_iT2WWybypfQ8n7ok9aJUfREjNWqdC";
    public /*static*/ final String dontwannawatchurcat = "NULL";
    public /*static*/ final String kindastoppls = "%.6f %.6f %.6f";
    public /*static*/ final double okacyyool = 0.3;
    public /*static*/ final String ihatethisguy = "JrRequiem";
    public /*static*/ final String coffedown = "jrrequest";
    public /*static*/ final String assaultobstrc = "plugins/ExecutableItems";
    public /*static*/ final String yourlastname = "items";
    public /*static*/ final String pickme = "zestybuffalo_";
    public /*static*/ final String specifically = "§cUsage: %Archistructure_zestybuffalo_PLAYER,Message%";
    public /*static*/ final String sulrred = "§cPlayer not found";
    public /*static*/ final String tryingtofigure = "§aSent to ";
    public /*static*/ final String nomotivation = "zestybuffalo2_";
    public /*static*/ final String bopabfunpa = "§aBuffed ";
    public /*static*/ final String whynotofficerlent = "aidenDash_";
    public /*static*/ final String dontlikepolice = "§cUsage: %Archistructure_aidenDash_baseplayer,target,Power%";
    public /*static*/ final String holdonasec = "§cInvalid power";
    public /*static*/ final String isitbiased = "§cBase player not found";
    public /*static*/ final String groupofindividuals = "§cTarget player not found";
    public /*static*/ final String ifellvictimized = "§cInvalid direction";
    public /*static*/ final String trsts = "aidenDash3_";
    public /*static*/ final String goingtojail = "§aDashed ";
    public /*static*/ final String seatbelton = "§cUsage: %Archistructure_aidenDash3_POWER,PLAYER,TAG%";
    public /*static*/ final String norstand = "§cNo armor stand with tag '";
    public /*static*/ final String takecareofcat = "aidenDash4_";
    public /*static*/ final String thoughtaboutthat = "§cThis placeholder requires a player context";
    public /*static*/ final String dontcommitcrime = "§cUsage: %Archistructure_aidenDash4_power,TAG,SEARCHRADIUS, YSHIFT%";
    public /*static*/ final String unharmed = "§cSEARCHRADIUS must be > 0";
    public /*static*/ final String provenguilty = "§cInvalid SEARCHRADIUS";
    public /*static*/ final String inACOURT = "§eArmor stand found, but no players within ";
    public /*static*/ final String oflaw = " blocks to dash.";
    public /*static*/ final String murdercase = " player(s) to tag '";
    public /*static*/ final String malecacuasion = "aidenDash2_";
    public /*static*/ final String boetcher = "§cUsage: %Archistructure_aidenDash2_power,TAGNAME%";
    public /*static*/ final String specialty = "§aDashed towards §e";
    public /*static*/ final String heisenburg = " §7with power §b";
    public /*static*/ final String heisenburgformercook = "debug_";
    public /*static*/ final String hippiedippy = "Hello!";
    public /*static*/ final String thisbabby = "§cHello!";
    public /*static*/ final String cos = "CONSOLE";
    public /*static*/ final String pullingfiles = "Gravity Gun";
    public /*static*/ final String galeboet = "score run-player-command player:";
    public /*static*/ final String whopaid = " EICOOLDOWN ";
    public /*static*/ final String nobody = " GravGun 1 false grabrealblock";
    public /*static*/ final String whodid = " GravGun 1 false activator0";
    public /*static*/ final String pushmore = " GravGun 1 false GrabEntity";
    public /*static*/ final String noaddress = " GravGun 1 false GrabPlayer";
    public /*static*/ final String corporatelawyer = "score variables set player Grav false  ";
    public /*static*/ final String vrickwall = "launched target";
    public /*static*/ final String madrigalelectro = " GravGun 1 false dropentityplayer";
    public /*static*/ final String hanoveregerm = " GravGun 1 false launchentityplayer";
    public /*static*/ final String tsrt = "-";
    public /*static*/ final String foothold = "score variables set player Grav true  ";
    public /*static*/ final String mericanfastfood = "score variables set player GravTarget ";
    public /*static*/ final String pooloshermanos = "target";
    public /*static*/ final String sowhat = " ";
    public /*static*/ final String wherehislabwas = " GravGun 1 false LaunchBLOCK";
    public /*static*/ final String apartmentsrtsas = " GravGun 1 false dropBlock";
    public /*static*/ final String crzyidea = "Grabbed";
    public /*static*/ final String foayhup = "detected";
    public /*static*/ final String napking = "minecraft:data merge entity @e[type=falling_block,tag=GravityGun";
    public /*static*/ final String fermented = ",limit=1] {NoGravity:0b}";
    public /*static*/ final String lentilbread = "score variables set player Grav false ";
    public /*static*/ final String finechickinjoint = "cmder_";
    public /*static*/ final String meetingsomeone = "Commander";
    public /*static*/ final String gusfrang = "cmder requires 1 argument like: admin,op,user";
    public /*static*/ final String rstat = "admin";
    public /*static*/ final String dfprdhyfp = "console";
    public /*static*/ final String fwypdohf3yrpdhunf = "op";
    public /*static*/ final String yowdghwa34yd = "opuser";
    public /*static*/ final String reaching = "user";
    public /*static*/ final String oafudnwpfydu = "player";
    public /*static*/ final String colonel = "Unknown cmder mode: ";
    public /*static*/ final String sanders = " (use admin/console, op/opuser, user/player)";
    public /*static*/ final String fourteencalls = "Commander admin page ";
    public /*static*/ final String notanswerignphone = " dispatch error: ";
    public /*static*/ final String needatpoliceoffcicer = " fallback error: ";
    public /*static*/ final String whatelesuwannaknow = "§6Command Output: §d";
    public /*static*/ final String nedambulanec = "Commander op page ";
    public /*static*/ final String taowyfdh = "Commander user page ";
    public /*static*/ final String whatsmyaddress = "&6&lCommand pages executed.";
    public /*static*/ final String yesmaaam = "changeEIOwner_";
    public /*static*/ final String threeunitsenroute = "§cUsage: %Archistructure_changeEIOwner,PLAYER,SLOT,NEWOWNER%";
    public /*static*/ final String tafopd = "nope";
    public /*static*/ final int ydtfhwpdylh = 40;
    public /*static*/ final String tywfnty = "score";
    public /*static*/ final String dplufirh3 = "owneruuid";
    public /*static*/ final String fobyufhpbyurhpbf = "particleLine_";
    public /*static*/ final String tyh34dl = "PTFX Line";
    public /*static*/ final String udlohlyp3whdoyplwd = "Tracking...";
    public /*static*/ final String opdh34yudhn = "trackImpact3_";
    public /*static*/ final String doyupn3wdyu3pnd = "Stinger 8.1";
    public /*static*/ final String y34hodnyu234 = "§6Impact triggered.";
    public /*static*/ final String onienod34 = "trackImpact4_";
    public /*static*/ final String nienoy24 = "§cTarget not found";
    public /*static*/ final String illcallhim = "§eTarget explosion triggered.";
    public /*static*/ final String trcfpd243 = "trackv4-a.1_";
    public /*static*/ final String ndeo3poiewnd4p3 = "§cUsage: %Archistructure_trackv4-a.1_CALLERUUID,TARGETUUID,LAUNCHERUUID%";
    public /*static*/ final String iepfndioepwndoyfupadnfpd = "§c§lMissile Impacted!";
    public /*static*/ final double childingdanger = 1.0e-4;
    public /*static*/ final double seenher = 0.0784;
    public /*static*/ final double aintnobody = 4.0;
    public /*static*/ final double thisistherecording = 5.0;
    public /*static*/ final double tdrsitrtomychd = 100.0;
    public /*static*/ final double stoppedrcying = 25.0;
    public /*static*/ final long startingthehouse = 2L;
    public /*static*/ final double imtakingher = 0.02;
    public /*static*/ final String cryingiwenftipfwnt = "§6§l%s  §7§l| §d§l%.1f";
    public /*static*/ final String righttobehrere = "§c§l§oMissile Impacted ";
    public /*static*/ final String callmewhatever = "checkEI_";
    public /*static*/ final String blackandwhitedocs = "EI Scanner";
    public /*static*/ final String sufficentprobablycause = "§cUse: %Archistructure_checkEI_PLAYER,SLOT,VARNAME%  OR  %Archistructure_checkEI_blockworld,blockx,blocky,blockz,slot,varname%";
    public /*static*/ final String getoffthebike = "stripColors_";
    public /*static*/ final String iantgoinnowhere = "invChecker_";
    public /*static*/ final String abuseofn911 = "failed - invalid entity";
    public /*static*/ final String noreason = "Inventory Checker";
    public /*static*/ final String takeitout = "Fishing Rod Proof of Concept";
    public /*static*/ final String trstwft = "trackedEntityX";
    public /*static*/ final String yntdy4u3 = "trackedEntityY";
    public /*static*/ final String heshurtpls = "trackedEntityZ";
    public /*static*/ final String hurtingmyarmsag = "trackedEntityWORLD";
    public /*static*/ final String plsgetoff = "vacuum_";
    public /*static*/ final String hurtingarmtsnwit = "§cInvalid format. Use: %Archistructure_vacuum_RANGE,FOV,IGNORESHULKERSTRUEFALSE,MAXENTITIES,INTERVAL,DURATION,THROUGHWALLS,PARTICLE,SEPARATION,PARTICLEINTERVAL%";
    public /*static*/ final String svacum = "ShulkerVacuum";
    public /*static*/ final String hurtingmesyr = "§cInvalid parameter types.";
    public /*static*/ final String foolishness = "SHULKER_BOX";
    public /*static*/ final String ouydnop3yundoypufna = "§7(No shulker in offhand; vacuum idle)";
    public /*static*/ final String einoienoiendw3pf = "§7(Offhand item is not a shulker blockstate)";
    public /*static*/ final String thatsathreatdyu = "DUST:";
    public /*static*/ final String puthandsebhind = "§cInvalid DUST format. Use DUST:#RRGGBB<scale>";
    public /*static*/ final String sendanotherunit = "§cUnknown particle: ";
    public /*static*/ final String handsbehindback = "§7(Offhand shulker is full)";
    public /*static*/ final String gonnagettazed = "§aVacuum timer reset.";
    public /*static*/ final String stoprsietnsr = "§aVacuum started.";
    public /*static*/ final String oundg324yutdng4 = "cosmicEnchant_";
    public /*static*/ final String dienfwopiednpf = "Drag'n'Drop Enchanter";
    public /*static*/ final String eindo3ie42ndoi34 = "raminecartBoost_";
    public /*static*/ final String iendie2nd = "§cInvalid format";
    public /*static*/ final String ieanrdowienfdf = "§cInvalid location";
    public /*static*/ final String wiodtnowiupd = "§cInvalid world";
    public /*static*/ final String wifednow34und43wd = "Minecart Rail Booster";
    public /*static*/ final String odunyuw4dn4 = "§cNo minecart found nearby";
    public /*static*/ final String don34uynp3d = "§aBoost applied to ";
    public /*static*/ final String goingtojailwfitdnwofduyn4w = "§cError processing boost";
    public /*static*/ final String youdnoy3wupdnoy3wupdn = "RED";
    public /*static*/ final int oiwfndtoyu42nd24 = 9;
    public /*static*/ final String tn243oyudnt42fuytdn = "&cFailure1";
    public /*static*/ final String eingdoi3e4ndg34 = "@a";
    public /*static*/ final String ongo34ungdo3iu4ndg4gdw4 = "_GLASS";
    public /*static*/ final String pdifnoian4 = "_CONCRETE";
    public /*static*/ final String oendinpdoinpd = "_WOOL";
    public /*static*/ final String ieodnoie4ndw3f4 = "force";
    public /*static*/ final String ietonwifandt4u3nd34dw3 = "block%s,%d,%d,%d";
    public /*static*/ final String tneoaiwfndtowui4nd4fdt = "n/a";
    public /*static*/ final String itenwoipwfnaidun4w = "trackv2.-1_";
    public /*static*/ final String ipendfofpiwdnoo43iwund = "Stinger Missile System";
    public /*static*/ final String tneeiond4 = "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage,trackInterval,trackDuration%. You used";
    public /*static*/ final String kdienwfp = "§c§lMissile Impacted";
    public /*static*/ final String midenwyudp4 = "§c§lMissile Impacted.";
    public /*static*/ final String wfudnofypundap = "§c§lMissile is not a projectile.";
    public /*static*/ final int wdnywfundywfudn = 90;
    public /*static*/ final int wyundywufndywufpndywuf = 100;
    public /*static*/ final String tuywnfydunwfyudn = "turret_";
    public /*static*/ final String wtdyuwnfdyunwf = "Sentry Gun Turret";
    public /*static*/ final int odafuwidnowfidun = 20;
    public /*static*/ final double wfopdwfnodowfin = 1.5;
    public /*static*/ final String wfodwfdkywf4d = "Sentry Gun";
    public /*static*/ final String iersdienrodyfwunpd = "ArchiTurret";
    public /*static*/ final String twoiaefndoiwfudnfoyudn = "§cNo turret found";
    public /*static*/ final long dtnowifepdnaowifudn = 50L;
    public /*static*/ final String ofwuandouwfynd = "|";
    public /*static*/ final String towfyuandoywfun = "§7Continuing current lock";
    public /*static*/ final String wofudnaoywfudn = "§cNo targets found";
    public /*static*/ final String wdynuwfyudnywufand = "ArchistructureTurret";
    public /*static*/ final String fdfopudnyafupdn = "esee_";
    public /*static*/ final String fwpdonufpdyufanpd = "§cInvalid format. Use e.g. esee_viewerUUID_targetUUID";
    public /*static*/ final String wyfudnoawfyudno = "_";
    public /*static*/ final String op4ydhnfaoypudn = "§cViewer not online.";
    public /*static*/ final String ydfnfpoyrudn = "Endersee";
    public /*static*/ final String pwoyfdunowypfuadn = "§aEnderChest opened.";
    public /*static*/ final String wfuyntdywfupadh = "§cInvalid UUID.";
    public /*static*/ final String yfpwduhywuhdp = "isee_";
    public /*static*/ final String pdonfapyudn = "§cInvalid format. Use isee_viewerUUID_targetUUID";
    public /*static*/ final String pdyunpfwyd = "Invsee";
    public /*static*/ final String dyfpnwoyufpn = "§aInventory opened.";
    public /*static*/ final String fydnayfpudhn = "csee_";
    public /*static*/ final String wfduynwfydaun = "§cInvalid format. Use csee_viewerUUID_world:x:y:z";
    public /*static*/ final String duynwyfpaudnwfp = "§cInvalid coordinates format.";
    public /*static*/ final String wfdunyunda = ":";
    public /*static*/ final String wdpyunwpfydunpd = "ChestSee";
    public /*static*/ final String yuwndyuwpdn = "§aChest opened.";
    public /*static*/ final String wfydunwfyudn = "§cInvalid input.";
    public /*static*/ final String wfydnywfpudn = "laserDamageHostiles_";
    public /*static*/ final String wyfudnywfud = "§c§lError";
    public /*static*/ final float idwfpndywud = 0f;
    public /*static*/ final int yuwfndgyuwfnd = 8;
    public /*static*/ final int yfpdunwyupnd = 11;
    public /*static*/ final String wyufodnhawyfudn = "Crucifix";
    public /*static*/ final String reaidntarisedn = "#";
    public /*static*/ final String dwyufndywfudn = "true";
    public /*static*/ final String wfydpunwfyudnwfd = "false";
    public /*static*/ final String dn3owyaufpdnfwd = "echo_";
    public /*static*/ final String fypodunfarypudna = "Laser Pointer";
    public /*static*/ final String wyufdnywufdn = "Flashlight";
    public /*static*/ final String wyudnwfydun = "tyv_001_";
    public /*static*/ final String ywudnywufd = "xdesugun_001_";
    public /*static*/ final String wyfundwf = "leaderboards_";
    public /*static*/ final String yuwfndoywfudn = "shulkerCheck";
    public /*static*/ final String yduwfdg = "shulkerOpen_";
    public /*static*/ final String ywfpdnoywfudnowfyudn = "shulkerClose_";
    public /*static*/ final String fwdyunwfydunfwd = "mcydatabase";
    public /*static*/ final String wfdyunwfd = "§cDatabase world missing!";
    public /*static*/ final String podfyunwfpdyunwfp = "success";
    public /*static*/ final String fypudnofpyudn = "vertigoHallucination_";
    public /*static*/ final String ywufdnywufnd = "Vertigo Hallucination";
    public /*static*/ final String odyun4wyunwpf = "Remote Shulker Box";
    public /*static*/ final String doyuwfndayuwnfdoyfwuanad = "invis";
    public /*static*/ final String wofduynwfdyun = "Invisibility 2.0";
    public /*static*/ final String wfuydnywfund = "§7Now invisible to others for 5s";
    public /*static*/ final String opfdyunfapoydunfpd = "JESUS_";
    public /*static*/ final String dwoyuawfdhoywufdh = "Jesus Boots";
    public /*static*/ final String wfdionaywufdnawf = "§bWalking on water (";
    public /*static*/ final String wfdywfudhwfy = " block radius)";
    public /*static*/ final String wfdoyunawfpdyunwfp = "§cInvalid radius";
    public /*static*/ final String wfpodyuw = "setVelocity_";
    public /*static*/ final String rsydun = "§adone";
    public /*static*/ final String wydhwypd = "chargeUp_";
    public /*static*/ final String wdfywunda = "ChargeUp";
    public /*static*/ final String wfdyunowfaydun = "§cInvalid total or symbol count";
    public /*static*/ final String wyfundywpfudn = "ENCHANTRESSMINEREFILL_";
    public /*static*/ final String awfodyunwyupdnwfoydun = "immortalize_";
    public /*static*/ final String ywufdnywfuoadn = "debugStickRotate_";
    public /*static*/ final String ywfudnoawfypdunwfyoudnwfp = "debugStickInvert_";
    public /*static*/ final String ywfpaudnoywfudnowyufdn = "searchExecutable_";
    public /*static*/ final String ywudnoayfwudn = "ExecutableSearch";
    public /*static*/ final String ywfdnwyfudn = "plugins/ExecutableEvents/events";
    public /*static*/ final String wfdyuwfdyu = "plugins/ExecutableItems/items";
    public /*static*/ final String ydhuyawfpdh = "plugins/ExecutableBlocks/blocks";
    public /*static*/ final String wyudnywufodn = ", ";
    public /*static*/ final String wfydunwofyudanfwd = "plugins/";
    public /*static*/ final String wfydunaowfydun = "/";
    public /*static*/ final String wfyutdnwyfaudnwfydoun = "§cFailed";
    public /*static*/ final String wyufdnaywfudnwfda = "§e===Exact Matches===";
    public /*static*/ final String wfpydunaowfyudn = "§f";
    public /*static*/ final String wfydunwafydunfwaydun = "§6===Similar Matches===";
    public /*static*/ final String ydunwfdunwfdwfdunwfdyun = "§7======";
    public /*static*/ final String wyufdnaowyufdnawoyfudn = "repeatingParticleText_";
    public /*static*/ final int yufdaywfudnwfd = 13;
    public /*static*/ final String wfypdnawofyudnwfpa = "Invalid format";
    public /*static*/ final int fdhkypfwd = 12;
    public /*static*/ final String wfkdgywfpdawfyp = "Repeating Particle Text";
    public /*static*/ final String dwkfydufwdfw = "§7[Cache] RAM hit for: ";
    public /*static*/ final String kwdfyufwd = ".txt";
    public /*static*/ final String dywfadbkwyfudb = "§7[Cache] Disk hit for: ";
    public /*static*/ final String wfydunwyfudnwfdyun = "§7[Cache] No cache found. Generating new data.";
    public /*static*/ final String wfydutnwfyudnwf = "§7[Debug] Scheduling display for: ";
    public /*static*/ final String wduynawyfpudnaw = "§c[Debug] Failed during display: ";
    public /*static*/ final String wadywfundfy = "Scheduled ";
    public /*static*/ final String wdyunwfyudn = "§c[Debug Error] ";
    public /*static*/ final String wdyuwfndywufn = "§cError: ";
    public /*static*/ final String wdyunawfydunfwd = "nearestPlayerNotTeam2_";
    public /*static*/ final String wfydnwyfuad = "visualBreak_";
    public /*static*/ final String dhnfpwyadun = "ProtocolLib";
    public /*static*/ final String wyufdanywfudn = "Any Block Breaker";
    public /*static*/ final String dyunwypfudnawfdyun = "Invalid format!";
    public /*static*/ final String wdayuwfnd = "Stage must be 1–10";
    public /*static*/ final String kwafpdkwfpad = "Invalid number!";
    public /*static*/ final String wfduynwyaufnd = "World not found!";
    public /*static*/ final String wfytkdawfdwpfdun = "Animation error";
    public /*static*/ final long dkwfyduwfndt = 40L;
    public /*static*/ final String fydunaofpdunafpwd = "Visual break stage ";
    public /*static*/ final String pdyunawfdyunwf = " set with reset";
    public /*static*/ final String wyfduanwfoydunwfd = "PTFXCUBE_";
    public /*static*/ final String wfopdyaufhdfpdf = "viewChest2_";
    public /*static*/ final String owdyaunwfpydu = "Chest GUI";
    public /*static*/ final String dawyfudnowfd = "Invalid format! Use: %Archistructure_viewChest2_sourceWorld,x,y,z%";
    public /*static*/ final String awufdwfda = "Invalid coordinates!";
    public /*static*/ final String dywufndoywuand = "Source world not found!";
    public /*static*/ final String taywfuntarosdulf = "No chest found at source location!";
    public /*static*/ final String wkfdwya = "DEBUG: Source chest at ";
    public /*static*/ final String pydaunfw = " detected. It is ";
    public /*static*/ final String dyanuowfd = "double";
    public /*static*/ final String wfktdyawfd = "single";
    public /*static*/ final String wyfdunafwd = ".";
    public /*static*/ final String dy87duh = "Fake Chest";
    public /*static*/ final String wdypfauwdnwfpd = "Error updating destination chest inventory.";
    public /*static*/ final String wktyfwutnwaft = "FakeChest";
    public /*static*/ final String dkfiaphd = "done";
    public /*static*/ final String wfpondyaunpdpw = "chain_";
    public /*static*/ final String fpkdyafpd = "ChainStunV2";
    public /*static*/ final String dokyfupkdoapdf = "viewChest_";
    public /*static*/ final String fpydnwfypudnfpd = "Invalid format! Use: %Archistructure_viewChest_sourceWorld,x,y,z%";
    public /*static*/ final String wydkawyfpudawfd = "Created mcydatabase world.";
    public /*static*/ final String aoienrstd = "last";
    public /*static*/ final String tyabfydkfpydnpfd = "DEBUG: viewChest processing complete. Returning coordinates: ";
    public /*static*/ final String wyndaoyfwpudnfpd = "blackHole_";
    public /*static*/ final int tyafndydpfw = 30;
    public /*static*/ final String fysoadunofwypdun = "BlackHole";
    public /*static*/ final String yrsndoawyfudn = "x_";
    public /*static*/ final String swyadunwfoydun = "y_";
    public /*static*/ final String naoyfdundfw = "z_";
    public /*static*/ final String owayfudny34hyfundywfudw = "openBackpack2_";
    public /*static*/ final String fudnoaywfudhoywafld = "Backpack";
    public /*static*/ final String fwaodhwfypdh = "Created the mcydatabase world.";
    public /*static*/ final String fydnoafypudkopnbkdf = "Double chest created at ";
    public /*static*/ final String foaybkfpoyubhl = "Loaded double chest contents for chest ID: ";
    public /*static*/ final String pfwydoakfopydhfpwoyd = "No previous contents found for double chest ID: ";
    public /*static*/ final String yofapdnoyfpubdhfyp = "openBackpack_";
    public /*static*/ final String iedn4dn8394d = "Backpack chest created at ";
    public /*static*/ final String oi2n43g234g = "Loaded backpack contents for chest ID: ";
    public /*static*/ final String enhien4g = "No previous contents found for chest ID: ";
    public /*static*/ final double kiek4d = 0.05;
    public /*static*/ final String yfnvdyfupbd = "bounce2_";
    public /*static*/ final String kdin34d34dn = "checkVelocity_";
    public /*static*/ final String ky3dnk34npdk3yp4ndky34pdnk3 = "vacuumCleaner_";
    public /*static*/ final String kyntkyfuwnd = "Invalid format: Expected 8 parameters";
    public /*static*/ final String kifwpnevdkiwpfend = "Vacuum Cleaner";
    public /*static*/ final String ywntvyoafupbnf = "remoteHopper_";
    public /*static*/ final String pdnofypundafopd = "Remote Hopper";
    public /*static*/ final String fpondafypudnopfyudn = "Invalid format. Use: %Archistructure_remoteHopper_INWorld,InX,InY,InZ,OutWorld,OutX,OutY,OutZ%";
    public /*static*/ final String dnofpadn4 = "§cInvalid world!";
    public /*static*/ final String dnoi43end = "§cIN block is not a hopper!";
    public /*static*/ final String niend4 = "§cOUT block is not a hopper!";
    public /*static*/ final String notiefpnd4 = "§cNo items in IN hopper!";
    public /*static*/ final String osiaenoiwfend4 = "§aStack transferred successfully!";
    public /*static*/ final String teianfoipedn4 = "§cOUT hopper is full!";
    public /*static*/ final String io2e4npoie2nt = "§cInvalid coordinate format!";
    public /*static*/ final String ioidenipofaedn4 = "§cError transferring item!";
    public /*static*/ final String io3iedn3i4p = "XRAY-";
    public /*static*/ final String iopidqen34i = "X-Ray";
    public /*static*/ final String wifadndgn43gd = "Invalid format! Use: %Archistructure_XRAY-RADIUS-SECONDS%";
    public /*static*/ final String io34igeng = "X-Ray v2 Activated!";
    public /*static*/ final String io43gdie34ngd = "Invalid numbers!";
    public /*static*/ final String yunyundg54h45 = "eifolderresetperms";
    public /*static*/ final String iendoi34nd = "bossbar_";
    public /*static*/ final String ifendioafepnd34fd = "Invalid Format! Use: COLOR,TIME,START,END,TIMESTEP,TEXT,TEXT2";
    public /*static*/ final String idenpoaidn34 = "BossBar displayed!";
    public /*static*/ final String iepdn3idn34d = "failed";
    public /*static*/ final String ioandi3n4dy43udn3pfd = "fireworkboost";
    public /*static*/ final String fipdenao3idn34ydun = "Super Rocket";
    public /*static*/ final String pd3ipdn34d = "1";
    public /*static*/ final String wfonvdawfydunp = "0";
    public /*static*/ final String fpiednfpden3 = "DN";
    public /*static*/ final String fdon34d43d = "sr72-fly-";
    public /*static*/ final String ifpndoi3nd34 = "SR-72";
    public /*static*/ final String nodyu3pnd43d = "Error: Invalid format. Use: %Archistructure_sr72-fly-speed,pitch,BOATUUID%";
    public /*static*/ final String wfnitfwtd = "Error: No boat found with UUID ";
    public /*static*/ final String nydufpnbfpbfpb = "Vector(%.3f, %.3f, %.3f)";
    public /*static*/ final String yfpdnofayundfpd = "Error: Invalid number format for speed/pitch.";
    public /*static*/ final String fydnofyapudnfpd = "Error: Invalid UUID format.";
    public /*static*/ final String opdnd34d = "Error: ";
    public /*static*/ final String ipfnedfipdnpd = "variables-create-";
    public /*static*/ final String fypdnafoypudn = "FT-increment";
    public /*static*/ final String fpdnafpduyn = "FT-leaderboard";
    public /*static*/ final String ypdn3oypdun = "trackImpact_";
    public /*static*/ final String nyduk34 = "trackImpact2_";
    public /*static*/ final String y34udky3d = "track_";
    public /*static*/ final String ydu3k4ybd3pd = "trackLimitedRotation_";
    public /*static*/ final String ypfuwnvwfpd = "Invalid format. Use: %Archistructure_trackLimitedRotation_uuid,targetuuid,speed,damage,maxDegrees%";
    public /*static*/ final String y4dnyfwudn = "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage%";
    public /*static*/ final String finovayfupbnyfpubhofpubnfp = "Invalid format. Use: %Archistructure,uuid,targetuuid,speed,damage% and you used ";
    public /*static*/ final String fpybunyu43nb = "trackv3_";
    public /*static*/ final String pyndy3upnbd = "§c§lAirburst Detonation!";
    public /*static*/ final String ypu3bnyfpubn = "trackv2_";
    public /*static*/ final String ywofpdnvoypu = "checkProfession_";
    public /*static*/ final String yvunoyup3 = "saveEntity_";
    public /*static*/ final String nytuwfntf = "entity_";
    public /*static*/ final String wkyvuwftnwfyutn = ".nbt";
    public /*static*/ final String yuntunyun342 = "Entity not found";
    public /*static*/ final String yunduyn4 = "Invalid UUID";
    public /*static*/ final String vkdyunt = "loadEntity_";
    public /*static*/ final String ktyunt32 = "loadEntity_location_";
    public /*static*/ final String ydny32u4nd24d = "Invalid location or UUID format!";
    public /*static*/ final String ntynu23nt = "' not found!";
    public /*static*/ final String vyf3b3 = "&cWorld '";
    public /*static*/ final String t2u3nty23unt = "&cEntity with ID '";
    public /*static*/ final String yfwunvtyn = "&cFailed to load entity data!";
    public /*static*/ final String ytunyun4 = "&aEntity reintroduced at the specified location!";
    public /*static*/ final String ydnyu3n4d = "&cAn error occurred while loading the entity!";
    public /*static*/ final String ny2u4ndt23d = "&cEntity reintroduced into the world!";
    public /*static*/ final String uydn2yu3nd23 = "Entity ID not found";
    public /*static*/ final String ydny3udno34yduhn3d = "cycle_playeruuid_head";
    public /*static*/ final String yndoyund4 = "Player Cycle";
    public /*static*/ final String udny3u4nd = "Not holding a player head!";
    public /*static*/ final String dny324d = "No online players!";
    public /*static*/ final String yutn23 = "velocity";
    public /*static*/ final String dy3und3u4d = "copyMainHand";
    public /*static*/ final String tyun2 = "critical";
    public /*static*/ final String ydun2d = "rayTraceBlock";
    public /*static*/ final String ytun23yutn = "countPlayersInRegion_";
    public /*static*/ final String tyn23yunt32t = "growCropParticle_";
    public /*static*/ final String ydtun34ydun = "§c[DEBUG] Invalid parameter count: ";
    public /*static*/ final String tnyu2nt23 = "Invalid";
    public /*static*/ final String ydn2ydun = "Crop Totem";
    public /*static*/ final String dny34und = "§c[DEBUG] Invalid DUST particle format: ";
    public /*static*/ final String vyn3vy3v = "§c[DEBUG] Invalid particle type: ";
    public /*static*/ final String wyunvywufntd = "You're not holding anything!";
    public /*static*/ final String dyo3npd3d = "worldedit";
    public /*static*/ final String dyn2yudn24 = "WorldEdit";
    public /*static*/ final String dyun3ydun3d = "worldguard";
    public /*static*/ final String y2un34yudn234d = "WorldGuard";
    public /*static*/ final String tyun2t = "luckperms";
    public /*static*/ final String kd3nd = "LuckPerms";
    public /*static*/ final String dny2udt = "protocollib";
    public /*static*/ final String ntyu2n3t = "griefprevention";
    public /*static*/ final String kyut2n2u3nt = "GriefPrevention";
    public /*static*/ final String yntu2nt = " (unknown plugin flag)";
    public /*static*/ final String nyup23np = "§c§lMissing required plugin(s): §r";
    public /*static*/ final String tn2yu3tn2 = "_SHULKER_BOX";
    public /*static*/ final String tn2ytn23t = "_STAINED_GLASS";
    public /*static*/ final String ytnfyu3ndt34f = "§cWorld not found.";
    public /*static*/ final String tuyn2y3utn2t = "§cThat block is not a container.";
    public /*static*/ final String tyun23utyn23t = "§8[View Chest]";
    public /*static*/ final String tyu23nt23t = "Players";
    public /*static*/ final String tyn2fnt2 = "Hostiles";
    public /*static*/ final String knde43d = "Both";
    public /*static*/ final String ynvkypn3v3 = "§cTarget player is not online.";
    public /*static*/ final String kientien2t = "§8[View EnderChest]";
    public /*static*/ final String tyu23ntyu23nt = "§8[View Inventory]";
    public /*static*/ final String tynwdy2d = "Content-Type";
    public /*static*/ final String uydn3yund = "application/json";
    public /*static*/ final String yuntyun2t = "POST";
    public /*static*/ final String dyn3ydun = "Player: ";
    public /*static*/ final String ydn3yudn34d = "Identifier: ";
    public /*static*/ final String dyu42ndyu432nd = "Uses left: ";
    public /*static*/ final String fuytn2yudt = "Is it demo-pack?: ";
    public /*static*/ final String yundt2yud = "IP: ";
    public /*static*/ final String y3udny3u4nd34d = "https://api.ipify.org";
    public /*static*/ final String yundyu34nd = "§cInvalid placeholder format.";
    public /*static*/ final String yufdtnoyupnd3d = "§e[DEBUG] No item in specified slot.";
    public /*static*/ final String udn3y4udn34ydu = "§e[DEBUG] Cursor is not an enchanted book.";
    public /*static*/ final String yudnyu43nd = "§e[DEBUG] Cursor item has no enchantments.";
    public /*static*/ final String ydn3y4d = "§e[DEBUG] Enchanted book has no stored enchantments.";
    public /*static*/ final String y4dun3y4udn34 = "§e[DEBUG] ";
    public /*static*/ final String dyu24ndyu24nd2d = "§e[DEBUG] Conflict: ";
    public /*static*/ final String dyn34ydun3d4 = "§a[✓] Enchants Applied";
    public /*static*/ final double udnyupndpd = 0.2;
    public /*static*/ final String do3ndyu3pnd3d = "item";
    public /*static*/ final String dnpyuadnyfpudnfpdfpd = "§x§F§F§0§0§0§0*§x§F§F§4§0§0§0*§x§F§F§8§0§0§0*§x§F§F§C§0§0§0*§x§F§F§F§F§0§0*§x§8§0§F§F§0§0*§x§0§0§F§F§0§0*§x§0§0§C§0§F§F*§x§0§0§8§0§F§F*§x§0§0§0§0§F§F*§x§8§0§0§0§F§F*§x§F§F§0§0§F§F*§x§F§F§0§0§0§0*§x§F§F§4§0§0§0*§x§F§F§8§0§0§0*§x§F§F§C§0§0§0*§x§F§F§F§F§0§0*§x§8§0§F§F§0§0*§x§0§0§F§F§0§0*§x§0§0§C§0§F§F*§x§0§0§8§0§F§F*§x§0§0§0§0§F§F*§x§8§0§0§0§F§F*§x§F§F§0§0§F§F*§x§F§F§0§0§0§0*§x§F§F§4§0§0§0*§x§F§F§8§0§0§0*§x§F§F§C§0§0§0*§x§F§F§F§F§0§0*§x§8§0§F§F§0§0*§x§0§0§F§F§0§0*§x§0§0§C§0§F§F*§x§0§0§8§0§F§F*§x§0§0§0§0§F§F*§x§8§0§0§0§F§F*§x§F§F§0§0§F§F*";
    public /*static*/ final String dy3updnyapudny34undh = "§7The §b";
    public /*static*/ final String dnyu3nd3d = "§7 is created by §6@ZestyBuffalo§7 - Part of the §dExecutables Variety Pack§7.";
    public /*static*/ final String dk3ed34dyu3nd = "§7If you want this on your own server, then message him on Discord!";
    public /*static*/ final String yudyu2dnyu42dn2d42d = "§7or contact him at §azestybuffaloevp@diepio.org §7!";
    public /*static*/ final String tiewntien42 = "§x§F§F§0§0§F§F*§x§8§0§0§0§F§F*§x§0§0§0§0§F§F*§x§0§0§8§0§F§F*§x§0§0§C§0§F§F*§x§0§0§F§F§0§0*§x§8§0§F§F§0§0*§x§F§F§F§F§0§0*§x§F§F§C§0§0§0*§x§F§F§8§0§0§0*§x§F§F§4§0§0§0*§x§F§F§0§0§0§0*§x§F§F§0§0§F§F*§x§8§0§0§0§F§F*§x§0§0§0§0§F§F*§x§0§0§8§0§F§F*§x§0§0§C§0§F§F*§x§0§0§F§F§0§0*§x§8§0§F§F§0§0*§x§F§F§F§F§0§0*§x§F§F§C§0§0§0*§x§F§F§8§0§0§0*§x§F§F§4§0§0§0*§x§F§F§0§0§0§0*§x§F§F§0§0§F§F*§x§8§0§0§0§F§F*§x§0§0§0§0§F§F*§x§0§0§8§0§F§F*§x§0§0§C§0§F§F*§x§0§0§F§F§0§0*§x§8§0§F§F§0§0*§x§F§F§F§F§0§0*§x§F§F§C§0§0§0*§x§F§F§8§0§0§0*§x§F§F§4§0§0§0*§x§F§F§0§0§0§0*§x§F§F§0§0§F§F*§x§8§0§0§0§F§F*§x§0§0§0§0§F§F*§x§0§0§8§0§F§F*§x§0§0§C§0§F§F*§x§0§0§F§F§0§0*§x§8§0§F§F§0§0*§x§F§F§F§F§0§0*§x§F§F§C§0§0§0*§x§F§F§8§0§0§0*§x§F§F§4§0§0§0*§x§F§F§0§0§0§0*";
    public /*static*/ final String y23u4ndyu3n4d = "minecraft:say §6§lYour temporary t§6§lrial of the Executables Variety Pack has ended! You may still use the items but Please contact @Zestybuffalo or join his discord at §dhttps://discord.gg/gf94ynF6NK §6or §azestybuffalo@diepio.org§6.";
    public /*static*/ final String dy2un4dy2u4nd4d = "plugins/Archistructures/viewonlychests/";
    public /*static*/ final String un324yudn243yudn = "viewonlychests.yml";
    public /*static*/ final String u4ndy24und24yd = "plugins/Archistructures/backpacks/";
    public /*static*/ final String yudn3wypu4ndh = "database.yml";
    public /*static*/ final String dyu2n4dyun2f4 = "database2.yml";
    public /*static*/ final String y24und = "plugins/Archistructures/saved-entities/";
    public /*static*/ final String y2u4dhyu2nd = "plugins/Archistructures/FTLeaderboard.txt";
    public /*static*/ final String yn23yund2y3udn = "plugins/Archistructures/shulkers/";
    public /*static*/ final String ydun2ydun23d = "shulkers.yml";
    public /*static*/ final String n234oyudnop3yudnwd = "ei run-custom-trigger player:";
    public /*static*/ final String tno2y3u4ntoy2u4nt2 = " trigger:ArchiWardenSpirit2 slot:-1 ";
    public /*static*/ final String d2ny4udn2yu43dn2tf = "§aYour Variety Pack Has Been Verified!";
    public /*static*/ final String dnoty3unoy3bkdty3pdt = "wardenCharge_";
    public /*static*/ final String pnoyu2ktyl43 = "§cInvalid total or symbol count";
    public /*static*/ final String oy43udnoy3kpldfttv = "Warden Sword";




    public /* static */ final String toywuanftwyft = "backpackCheck";
    public /* static */ final String to23nyutn2fy3ut = "mcydatabase";
    public /* static */ final String tony23untyquwfnt = "true";
    public /* static */ final String to2i3ufnk2w = "false";
    public /* static */ final String ton2y3uftno2yfwun = "velocityY";
    public /* static */ final String to2myutn2y4udn2wfptw = "shulkerOpen2";
    public /* static */ final String t2ofyutny2fu4tdn = "too quick";
    public /* static */ final String to2nyfutkworyt = "_";
    public /* static */ final String mt2ftowtt = "§cInvalid format";
    public /* static */ final String t2fktowrsetn = "§cInvalid slot";
    public /* static */ final String tk2tfuynwyu = "mcydatabase";
    public /* static */ final String tko2y4utnoyUFT = "Created the mcydatabase world.";
    public /* static */ final String t2oynoyuD = "§cFailed to load mcydatabase world!";
    public /* static */ final String dok23ipudhyfurnydunfp = "none";
    public /* static */ final String k2yo4utyunhOYUndoyun = "PlaceholderAPI";
    public /* static */ final String tkoy2untoyun23fytunt = "[Archistructure] Plugin instance is null; cannot schedule shulker watcher.";
    public /* static */ final String cpienoien3i = "SHULKER_BOX";
    public /* static */ final String toky4ukoftfpv = "material";
    public /* static */ final String oenoeinyunuione = "name";
    public /* static */ final String neionioenoieneionienin = "lore";
    public /* static */ final String kfikyfuwntyouwfnt = "executableitems:ei-id";
    public /* static */ final String tkoinyunyunyunfwytunoyuwnftwft = "TempShulkerPlaceholder";
    public /* static */ final String koy42unoywunfc = "last_chunk_z";
    public /* static */ final String to2utnyunoyunst = "last_y";
    public /* static */ final String pk23bkptbktwfdfb = "existing";
    public /* static */ final String a3f4dtkyuwfdk = "§cNo item in that slot!";
    public /* static */ final String ncdot = "§cItem is not a shulker box!";
    public /* static */ final String kmartrtrsdt = "x";
    public /* static */ final String mdknpkep3nd = "y";
    public /* static */ final String mtmmmtf = "z";
    public /* static */ final String mt2mmtm234t = "§cFailed to create chest!";
    public /* static */ final String mmco2mcmm23mt = "§cFailed to read shulker box contents!";
    public /* static */ final String mt2mmt23ptrt = "new";



    public /* static */ final String ty2ufntyu2nfd = "plugins/Archistructures/shulkers/";
    public /* static */ final String r12yftnhyoafhtdoyl = "shulkers.yml";
    public /* static */ final String yfodunwy3u4dny43udn = "getBlock_";
    public /* static */ final String o2ny4unvyu3wdnawpfd = "getBlockHasuno_";
    public /* static */ final String n2oyunt2yuwfdht = "fakeGlowing_";
    public /* static */ static final String t2y3ftunyuondyuhyhl = "1.19.3";
    public /* static */ final String t2ofutno2yfuwdhvnwypuvd = "done";
    public /* static */ final String tno2yfutnyuwfnt = "ei give ";
    public /* static */ final String tyotun2foywuht = "done";
    public /* static */ final String to2nfwyuthnvwyfuv = "zestybuffalo4_";
    public /* static */ final String voypkuwbvt = "zestybuffalo3_";
    public /* static */ final String dnodyuh249dlfw = "lodestone2_";
    public /* static */ final String otn2yfutho2yflvdfw = "§cBad Args";
    public /* static */ final String kgot24intoy2fuwtn = "§cPlayer Not Found";
    public /* static */ final String tn2oty2ufhvdtywavh = "Tracker Compass v2";
    public /* static */ final String tno2yudnto2y4udn2d = "§cBad Slot";
    public /* static */ final String odtn2y4fdlhy3dh = "§cEntity Not Found";
    public /* static */ final String tno2y4dt2h4d = "§cNo Compass Detected";
    public /* static */ final String tk2yodtu = "archistructurenotfound";
    public /* static */ final String tno2ydh2y4fuldh = "§cWorld Mismatch!";
    
    
    
    
    
    
    // launcherUUID + ":" + targetUUID  -> projectile UUID
    private static final java.util.concurrent.ConcurrentMap<String, UUID> ACTIVE_MISSILES =
            new java.util.concurrent.ConcurrentHashMap<>();

    private static String missileKey(UUID launcher, UUID target) {
        return launcher.toString() + ":" + target.toString();
    }
    private static final Map<UUID, BukkitTask> ACTIVE_SHULKER_TASKS = new ConcurrentHashMap<>();


    // One task per turret UUID
    private static final Map<UUID, BukkitTask> ACTIVE_TURRET_TASKS = new ConcurrentHashMap<>();

    // A deadline (in ms, epoch) for each active turret; further detections extend this
    private static final Map<UUID, Long> ACTIVE_TURRET_DEADLINE_MS = new ConcurrentHashMap<>();
    private final AtomicBoolean demoCmdRegistered = new AtomicBoolean(false);

    private static final ConcurrentHashMap<UUID, VacuumJob> ACTIVE_VACUUMS = new ConcurrentHashMap<>();
    private static final java.util.Set<org.bukkit.Material> PASS_THROUGH = initPassThrough();

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
    protected final Map<UUID, Set<Location>> trackedBlocks = new HashMap<>();
    protected final Set<Material> oresAndImportantBlocks = Set.of( Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE, Material.ANCIENT_DEBRIS);
    protected final Map<Location, BukkitTask> jesusTimers = new HashMap<>();
    protected final Map< UUID, Map<Location, BukkitTask> > jesusTimers2 = new ConcurrentHashMap<>();
    protected record CachedParticleData(List<Location> locations, long timestamp) {}
    protected static final int SKIN_HEIGHT = 64;
    record SkinLayers(java.awt.Color[][] base, java.awt.Color[][] overlay) {}
    private static final Map<UUID, AtomicReference<LivingEntity>> ACTIVE_TURRET_TARGET = new ConcurrentHashMap<>();
    protected final File g14;
    protected final File g13;


    private static java.util.Set<org.bukkit.Material> initPassThrough() {
        java.util.EnumSet<org.bukkit.Material> s = java.util.EnumSet.of(
                Material.ACTIVATOR_RAIL, Material.AIR, Material.BAMBOO, Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BEETROOT_SEEDS, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.BIRCH_BUTTON, Material.SPRUCE_BUTTON, Material.JUNGLE_BUTTON, Material.DARK_OAK_BUTTON, Material.ACACIA_BUTTON, Material.MANGROVE_BUTTON, Material.CHERRY_BUTTON, Material.CRIMSON_BUTTON, Material.WARPED_BUTTON, Material.LIGHT_BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER, Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_GRAY_BANNER, Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER, Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER, Material.CARROTS, Material.CHORUS_FLOWER, Material.CHORUS_PLANT, Material.COBWEB, Material.COCOA, Material.BRAIN_CORAL, Material.BUBBLE_CORAL, Material.FIRE_CORAL, Material.HORN_CORAL, Material.TUBE_CORAL, Material.BRAIN_CORAL_FAN, Material.BUBBLE_CORAL_FAN, Material.FIRE_CORAL_FAN, Material.HORN_CORAL_FAN, Material.TUBE_CORAL_FAN, Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.END_GATEWAY, Material.END_PORTAL, Material.FIRE, Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY, Material.WITHER_ROSE, Material.FLOWER_POT, Material.FROGSPAWN, Material.WARPED_FUNGUS, Material.CRIMSON_FUNGUS, Material.GLOW_BERRIES, Material.GLOW_LICHEN, Material.SHORT_GRASS, Material.HANGING_ROOTS, Material.PLAYER_HEAD, Material.SKELETON_SKULL, Material.CREEPER_HEAD, Material.WITHER_SKELETON_SKULL, Material.ZOMBIE_HEAD, Material.DRAGON_HEAD, Material.PIGLIN_HEAD, Material.KELP, Material.LADDER, Material.LAVA, Material.LEVER, Material.LIGHT, Material.LILY_PAD, Material.MANGROVE_PROPAGULE, Material.MELON_SEEDS, Material.MOSS_CARPET, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.NETHER_PORTAL, Material.NETHER_SPROUTS, Material.NETHER_WART, Material.PINK_PETALS, Material.PITCHER_PLANT, Material.PITCHER_POD, Material.POTATOES, Material.POWDER_SNOW, Material.POWERED_RAIL, Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.MANGROVE_PRESSURE_PLATE, Material.CHERRY_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.PUMPKIN_SEEDS, Material.RAIL, Material.COMPARATOR, Material.REDSTONE_WIRE, Material.REPEATER, Material.REDSTONE_TORCH, Material.REDSTONE_WALL_TORCH, Material.OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.JUNGLE_SAPLING, Material.DARK_OAK_SAPLING, Material.ACACIA_SAPLING, Material.CHERRY_SAPLING, Material.SCULK_VEIN, Material.SEA_PICKLE, Material.SEAGRASS, Material.SHORT_GRASS, Material.DEAD_BUSH, Material.OAK_SIGN, Material.BIRCH_SIGN, Material.SPRUCE_SIGN, Material.JUNGLE_SIGN, Material.DARK_OAK_SIGN, Material.ACACIA_SIGN, Material.CHERRY_SIGN, Material.MANGROVE_SIGN, Material.CRIMSON_SIGN, Material.WARPED_SIGN, Material.SMALL_DRIPLEAF, Material.SNOW, Material.SPORE_BLOSSOM, Material.STRING, Material.STRUCTURE_VOID, Material.SUGAR_CANE, Material.SWEET_BERRY_BUSH, Material.TORCH, Material.TORCHFLOWER_SEEDS, Material.TRIPWIRE_HOOK, Material.TURTLE_EGG, Material.TWISTING_VINES, Material.VINE, Material.WATER, Material.WEEPING_VINES, Material.WHEAT_SEEDS, Material.WHITE_TULIP// ...
        );
        

        // Strongly recommended: include the other air variants explicitly
        s.add(org.bukkit.Material.CAVE_AIR);
        s.add(org.bukkit.Material.VOID_AIR);

        // Future-proof hook (won’t crash compile): add by string if/when you want later
        // addIfExists(s, "SOME_NEW_BLOCK");

        // Runtime safety-net: if any are solid, drop them.
        s.removeIf(m -> m == null || m.isSolid());

        return java.util.Collections.unmodifiableSet(s);
    }

    private static void addIfExists(java.util.Set<org.bukkit.Material> set, String name) {
        org.bukkit.Material m = org.bukkit.Material.matchMaterial(name);
        if (m != null) set.add(m);
    }
    public ExampleExpansion() {
        this.g13 = new File(u4ndy24und24yd);
        if (!g13.exists()) {
            g13.mkdirs();
        }
        this.g14 = new File(g13, yudn3wypu4ndh);
        if (!g14.exists()) {
            try {
                g14.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.g18 = YamlConfiguration.loadConfiguration(g14); //databaseconfig

        EnumSet<EntityType> ktoyfuntyufnpsrvc = EnumSet.noneOf(EntityType.class);

        for (EntityType type : EntityType.values()) {
            Class<? extends Entity> kcoyufwhoyunpwcsr;
            try {
                kcoyufwhoyunpwcsr = type.getEntityClass();
            } catch (Throwable ignored) {
                // If some fork/version ever breaks this call, just skip safely
                continue;
            }

            if (kcoyufwhoyunpwcsr == null) continue;

            if (Enemy.class.isAssignableFrom(kcoyufwhoyunpwcsr)) {
                ktoyfuntyufnpsrvc.add(type);
            }
        }
        HMsetnoy2un2yundt = ktoyfuntyufnpsrvc;

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




    /**
     * Ensure there is a single watcher task for this player's shulker chest.
     * The watcher runs every 5 seconds (100 ticks).
     *
     * Logic:
     *  - If player is offline → **just cancel task, do NOT finalize, do NOT touch 'active'**.
     *  - If player online:
     *      - If chest UI is open (for this player & coords) → keep waiting.
     *      - If chest UI is NOT open → finalize once and cancel.
     */
    private static void ensureShulkerWatcher(ExampleExpansion exampleExpansion,
                                             UUID uuid,
                                             Location chestLoc,
                                             YamlConfiguration shulkerConfig) {

        if (ACTIVE_SHULKER_TASKS.containsKey(uuid)) {
            return;
        }

        final Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (plugin == null) {
            Bukkit.getLogger().warning("[Archistructure] Plugin instance is null; cannot schedule shulker watcher.");
            return;
        }

        final long periodTicks = 100L; // 5 seconds

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Player target = Bukkit.getPlayer(uuid);

            // --- OFFLINE: ONLY cancel the task, do NOT finalize, do NOT change 'active' ---
            if (target == null || !target.isOnline()) {
                BukkitTask t = ACTIVE_SHULKER_TASKS.remove(uuid);
                if (t != null) {
                    t.cancel();
                }
                return;
            }

            boolean openNow = isShulkerBoxOpenx(shulkerConfig, target);

            if (openNow) {
                // UI still open → wait for next tick
                return;
            }

            // UI closed while player is online → finalize once
            finalizeShulker(exampleExpansion, uuid, chestLoc);

            BukkitTask t = ACTIVE_SHULKER_TASKS.remove(uuid);
            if (t != null) {
                t.cancel();
            }
        }, periodTicks, periodTicks);

        ACTIVE_SHULKER_TASKS.put(uuid, task);
    }





    /**
     * Rebuild the shulker box item from the mcydatabase chest contents and
     * ADD it to the player's inventory (no replacement).
     *
     * Uses:
     *  - material, name, lore, x, y, z from shulkerDatabaseConfig[uuid]
     *  - chest contents at chestLoc
     */
    private static void finalizeShulker(ExampleExpansion exampleExpansion,
                                        UUID uuid,
                                        Location chestLoc) {


        World world = chestLoc.getWorld();
        if (world == null) {
            return;
        }

        Block block = chestLoc.getBlock();
        if (!(block.getState() instanceof Chest chest)) {
            return;
        }

        ItemStack[] contents = chest.getInventory().getContents();

        Player p = Bukkit.getPlayer(uuid);
        if (p == null || !p.isOnline()) {
            return;
        }

        YamlConfiguration shulkerConfig = exampleExpansion.shulkerDatabaseConfig;
        String uuidKey = uuid.toString();
        ConfigurationSection sec = shulkerConfig.getConfigurationSection(uuidKey);

        if (sec != null) { 
            sec.set("active", false);
            saveShulkerConfig(shulkerConfig, exampleExpansion.shulkerDatabaseFile);
         
        }

        // Defaults
        String materialName = sec != null ? sec.getString("material", "SHULKER_BOX") : "SHULKER_BOX";
        String displayName = sec != null ? sec.getString("name", null) : null;
        List<String> lore = sec != null ? sec.getStringList("lore") : null;


        Material mat = Material.matchMaterial(materialName);
        if (mat == null || !mat.name().endsWith("SHULKER_BOX")) {
            mat = Material.SHULKER_BOX;
        }

        // Create base shulker item with name & lore
        
        
        
        
        
        ItemStack shulker = new ItemStack(mat);
        ItemMeta im = shulker.getItemMeta();
        if (im != null) {
            if (displayName != null && !displayName.isEmpty()) {
                im.setDisplayName(displayName);
            }
            if (lore != null && !lore.isEmpty()) {
                im.setLore(lore);
            }
            shulker.setItemMeta(im);
        }

        // Inject contents via BlockStateMeta
        ItemMeta meta = shulker.getItemMeta();
        if (meta instanceof BlockStateMeta bsm) {
            org.bukkit.block.ShulkerBox box =
                    (org.bukkit.block.ShulkerBox) Bukkit.createBlockData(mat).createBlockState();
            box.getInventory().setContents(contents);
            bsm.setBlockState(box);
            shulker.setItemMeta(bsm);
        } else {
        }

        // ADD to inventory (no replacement)
        Inventory inv = p.getInventory();

        int placeholderSlot = -1;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (isTempShulkerPlaceholder(stack)) {
                placeholderSlot = i;
                break;
            }
        }



        if (placeholderSlot >= 0) {
            inv.setItem(placeholderSlot, shulker);
        } else {
            // Fallback: no placeholder found → add item normally
            HashMap<Integer, ItemStack> leftover = inv.addItem(shulker);
            if (!leftover.isEmpty()) {
                for (ItemStack l : leftover.values()) {
                    world.dropItemNaturally(chestLoc.clone().add(0.5, 1.0, 0.5), l);
                }
            } else {
            }
        }

        // Clear chest contents (always)
        chest.getInventory().clear();

    }





    private static boolean isTempShulkerPlaceholder(@Nullable ItemStack stack) {
        if (stack == null || stack.getType().isAir()) return false;

        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc == null) return false;

        NamespacedKey key = NamespacedKey.fromString("executableitems:ei-id");
        if (key == null) {
            return false;
        }

        String value = pdc.get(key, PersistentDataType.STRING);
        boolean result = "TempShulkerPlaceholder".equalsIgnoreCase(value);


        return result;
    }



    protected static boolean isShulkerBoxOpen(YamlConfiguration shulkerDatabaseConfig, Player player) {
        InventoryView view = player.getOpenInventory();
        Inventory topInv = view.getTopInventory();

        InventoryHolder holder = topInv.getHolder();

        if (!(holder instanceof Chest chest)) {
            return false;
        }

        Location loc = chest.getLocation();
        String uuidKey = player.getUniqueId().toString();
        ConfigurationSection sec = shulkerDatabaseConfig.getConfigurationSection(uuidKey);

        if (sec == null) {
            return false;
        }

        if (!sec.contains("x") || !sec.contains("y") || !sec.contains("z")) {
            return false;
        }

        int x = sec.getInt("x");
        int y = sec.getInt("y");
        int z = sec.getInt("z");



        boolean sameWorld = loc.getWorld() != null && loc.getWorld().getName().equals("mcydatabase");
        boolean sameCoords = loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z;
        boolean result = sameWorld && sameCoords;


        return result;
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
                // Todo change maxhp?
                final double maxHp = 20 ; //tgt.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getBaseValue();
                final double amount = 0.60 * maxHp + 10.0;
                tgt.damage(amount, dsb.build());
                // Run console trigger for the player target
                final String victimName = ((Player) tgt).getName();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "ei run-custom-trigger trigger:Stinger82Hit player:" + launcherName + " " + victimName);
            } else {
                // Non-players: damager = launcher (still acceptable per your V4 semantics)
                final Entity damager = launcher;
                if (damager != null) dsb2.withDirectEntity(damager).withCausingEntity(damager);
                // Todo change maxhp?
                launcher.sendMessage("§dYou have struck §6" + (tgt.getName() != null ? tgt.getName() : tgt.getType() ) + "§d with Stinger 8.2");

                final double maxHp = 20 ; //tgt.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getBaseValue();
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
                // Todo change maxhp?
                final double maxHp = 20 ; //le.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getBaseValue();
                final double amount = 0.60 * maxHp + 10.0;
                le.damage(amount, dsb.build());
                // Run console trigger for each player in radius

                final String victimName = ((Player) le).getName();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "ei run-custom-trigger trigger:Stinger82Hit player:" + launcherName + " " + victimName);
            } else {
                // Non-players: damager = launcher (kept consistent with your V4 variant)
                final Entity damager = launcher;
                if (damager != null) dsb.withDirectEntity(damager).withCausingEntity(damager);
                // Todo change maxhp?
                final double maxHp = 20 ; // le.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getBaseValue();
                final double amount = 0.33 * maxHp + 300;
                launcher.sendMessage("§dYou have struck §6" + (le.getName() != null ? le.getName() : le.getType() ) + "§d with Stinger 8.2");
                le.damage(amount, dsb.build());
            }
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
                    bukkitColor = org.bukkit.Color.fromRGB(191, 119, 246); // aqua
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
    
    // Helper Methods Helpers

    private static @org.jetbrains.annotations.NotNull String onTrackV4A2(
            org.bukkit.entity.Player p,
            String argsRaw
    ) {
        if (p == null || !p.isOnline()) return "none";

        // argsRaw = "%projectile_uuid%" (ONLY)
        final UUID missileUUID;
        try {
            missileUUID = UUID.fromString(argsRaw.trim());
        } catch (Exception e) {
            return "§cUsage: %Archistructure_trackv4-a.2_%projectile_uuid%%";
        }

        // Find last locked target for THIS player (persisted, never cleared)
        UUID targetUUID = STINGER_LAST_FULL_LOCK_PERSIST.get(p.getUniqueId());
        if (targetUUID == null) {
            // fallback (if you still keep StingerLockState around)
            StingerLockState st = STINGER_LOCKS.get(p.getUniqueId());
            if (st != null) targetUUID = st.lastFullLockId;
        }
        if (targetUUID == null) return "none";

        // Link missile -> target
        TRACKV4A2_MISSILE_TO_TARGET.put(missileUUID, targetUUID);

        // Start/replace tracking + particle line
        startTrackV4A2(p.getUniqueId(), missileUUID, targetUUID);

        // Return same style string as a.1 (name | distance-now)
        org.bukkit.entity.Entity missile = org.bukkit.Bukkit.getEntity(missileUUID);
        org.bukkit.entity.Entity target  = org.bukkit.Bukkit.getEntity(targetUUID);
        if (missile == null || target == null) return "§c§lMissile Impacted!";
        double distNow = missile.getLocation().distance(target.getLocation());
        String targetName = (target instanceof org.bukkit.entity.Player tp) ? tp.getName() : target.getType().name();
        return String.format("§6§l%s  §7§l| §d§l%.1f", targetName, distNow);
    }

    private static void startTrackV4A2(UUID launcherUUID, UUID missileUUID, UUID targetUUID) {
        final org.bukkit.plugin.Plugin plugin = java.util.Objects.requireNonNull(
                org.bukkit.Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                "PlaceholderAPI plugin ref missing"
        );

        // kill any previous tasks for THIS missile (re-fired parse)
        org.bukkit.scheduler.BukkitTask oldSteer = TRACKV4A2_STEER.remove(missileUUID);
        if (oldSteer != null) oldSteer.cancel();
        org.bukkit.scheduler.BukkitTask oldLine = TRACKV4A2_LINE.remove(missileUUID);
        if (oldLine != null) oldLine.cancel();

        // --- same accel profile you had ---
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
        org.bukkit.scheduler.BukkitTask steerTask = org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int idx = 0;
            int lifeTicks = 0; // safety TTL if you want it
            @Override public void run() {
                lifeTicks += 2;

                org.bukkit.entity.Entity c = org.bukkit.Bukkit.getEntity(missileUUID);
                org.bukkit.entity.Entity t = org.bukkit.Bukkit.getEntity(targetUUID);

                if (c == null || t == null || !c.isValid() || c.isDead()
                        || !t.isValid() || t.isDead()
                        || !c.getWorld().equals(t.getWorld())) {
                    stopTrackV4A2(missileUUID);
                    return;
                }

                // Optional TTL safety; comment if you don’t want it
                if (lifeTicks >= 20 * 12) { // 12s
                    stopTrackV4A2(missileUUID);
                    return;
                }

                final double Sm = (idx < SPEEDS.length) ? SPEEDS[idx] : SPEEDS[SPEEDS.length - 1];

                // --- lead pursuit (with fallback) ---
                final org.bukkit.Location lc = c.getLocation();
                final org.bukkit.Location lt = t.getLocation().add(0, t.getHeight() * 0.5, 0);
                final org.bukkit.util.Vector R = lt.toVector().subtract(lc.toVector());
                final org.bukkit.util.Vector V = t.getVelocity().clone();
                if (Math.abs(V.getY() + 0.0784) < 1.0e-4) V.setY(0);

                final double a = V.dot(V) - Sm * Sm;
                final double b = 2.0 * R.dot(V);
                final double cc = R.dot(R);
                final double eps = 1e-9;
                final double disc = b*b - 4.0*a*cc;

                org.bukkit.util.Vector desiredV;
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
                        final org.bukkit.util.Vector intercept = lt.toVector().add(V.multiply(tHit));
                        desiredV = safeNorm(intercept.subtract(lc.toVector())).multiply(Sm);
                    }
                }

                // ---------------------------
                // Terrain CAS (Collision Avoidance System)
                // ---------------------------
                final org.bukkit.Location rayStart = lc.clone();
                final org.bukkit.util.Vector rayDir = desiredV.clone().normalize();
                final double rayLength = desiredV.length() * 5.0;

                org.bukkit.util.RayTraceResult result = rayStart.getWorld().rayTraceBlocks(
                        rayStart,
                        rayDir,
                        rayLength,
                        org.bukkit.FluidCollisionMode.NEVER,
                        true
                );

                final Set<Material> passThrough = PASS_THROUGH;

                final boolean needsAvoidance = result != null && result.getHitBlock() != null
                        && !passThrough.contains(result.getHitBlock().getType());

                if (needsAvoidance) {
                    org.bukkit.util.Vector bestVelocity = desiredV;
                    double bestScore = -1.0;

                    for (int pitchDeg = 0; pitchDeg <= 90; pitchDeg += 5) {
                        final double rad = Math.toRadians(pitchDeg);

                        org.bukkit.util.Vector pitched = desiredV.clone().normalize().multiply(Math.cos(rad))
                                .add(new org.bukkit.util.Vector(0, 1, 0).multiply(Math.sin(rad)))
                                .normalize()
                                .multiply(Sm);

                        org.bukkit.util.RayTraceResult test = rayStart.getWorld().rayTraceBlocks(
                                rayStart,
                                pitched.clone().normalize(),
                                pitched.length() * 5.0,
                                org.bukkit.FluidCollisionMode.NEVER,
                                true
                        );

                        boolean clear = (test == null || test.getHitBlock() == null
                                || passThrough.contains(test.getHitBlock().getType()));

                        if (clear) {
                            double score = 100.0 - pitchDeg;
                            if (score > bestScore) {
                                bestScore = score;
                                bestVelocity = pitched;
                            }
                        }
                    }

                    desiredV = bestVelocity;
                }

                // Apply velocity
                c.setVelocity(desiredV);

                // Proximity -> damage & remove
                final double d2 = lc.distanceSquared(lt);
                if (d2 <= 25.0) { // within 5 blocks
                    spawnCustomFireworkExplosion2(c.getWorld(), c.getLocation());
                    triggerPlayerHitEventV4_AOE(launcherUUID, c.getLocation(), 5.0, t);
                    if (c.isValid()) c.remove();
                    stopTrackV4A2(missileUUID);
                    return;
                }

                idx++;
            }
        }, 0L, 2L);

        TRACKV4A2_STEER.put(missileUUID, steerTask);

        // ---------------------------
        // Purple tracking line loop (SYNC every tick)
        // ---------------------------
        org.bukkit.scheduler.BukkitTask lineTask = org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            org.bukkit.entity.Entity c = org.bukkit.Bukkit.getEntity(missileUUID);
            org.bukkit.entity.Entity t = org.bukkit.Bukkit.getEntity(targetUUID);

            if (c == null || t == null || !c.isValid() || c.isDead()
                    || !t.isValid() || t.isDead()
                    || !c.getWorld().equals(t.getWorld())) {
                stopTrackV4A2(missileUUID);
                return;
            }

            drawPurpleLineWorld(c, t);
        }, 0L, 1L);

        TRACKV4A2_LINE.put(missileUUID, lineTask);
    }

    private static void stopTrackV4A2(UUID missileUUID) {
        org.bukkit.scheduler.BukkitTask t1 = TRACKV4A2_STEER.remove(missileUUID);
        if (t1 != null) t1.cancel();
        org.bukkit.scheduler.BukkitTask t2 = TRACKV4A2_LINE.remove(missileUUID);
        if (t2 != null) t2.cancel();
        TRACKV4A2_MISSILE_TO_TARGET.remove(missileUUID);
    }
    
    
    private static void disableStingerAllFor(UUID pid) {
        // stop lock system tasks
        org.bukkit.scheduler.BukkitTask q = STINGER_RESET_QUEUES.remove(pid);
        if (q != null) q.cancel();
        org.bukkit.scheduler.BukkitTask t = STINGER_LOCK_TASKS.remove(pid);
        if (t != null) t.cancel();

        // stop prelaunch handoff line
        stopPrelaunchLine(pid);
        STINGER_ARMED.remove(pid);

        // clear lock state (but DO NOT wipe persisted last lock)
        StingerLockState st = STINGER_LOCKS.remove(pid);
        org.bukkit.entity.Player shooter = org.bukkit.Bukkit.getPlayer(pid);
        if (st != null && shooter != null) removeRevealIfAny(shooter, st);
    }


    private static void armPrelaunchTrackLine(UUID launcherUUID, UUID targetUUID) {
        STINGER_ARMED.put(launcherUUID, new ArmedTrack(launcherUUID, targetUUID));

        // Cancel any existing prelaunch line (only 1 allowed)
        org.bukkit.scheduler.BukkitTask old = STINGER_PRELAUNCH_LINE.remove(launcherUUID);
        if (old != null) old.cancel();

        final org.bukkit.plugin.Plugin plugin = java.util.Objects.requireNonNull(
                org.bukkit.Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                "PlaceholderAPI plugin ref missing"
        );

        // Prelaunch line expires quickly if no projectile is launched
        final long startMs = System.currentTimeMillis();
        final long ttlMs   = 1200; // ~1.2s; tweak if you want longer “arming” visuals

        org.bukkit.scheduler.BukkitTask task = org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            final org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayer(launcherUUID);
            final ArmedTrack at = STINGER_ARMED.get(launcherUUID);

            if (pl == null || !pl.isOnline() || at == null || at.targetId == null) {
                stopPrelaunchLine(launcherUUID);
                return;
            }

            // TTL
            if (System.currentTimeMillis() - startMs > ttlMs) {
                stopPrelaunchLine(launcherUUID);
                return;
            }

            final org.bukkit.entity.Entity tgt = org.bukkit.Bukkit.getEntity(at.targetId);
            if (tgt == null || !tgt.isValid() || tgt.isDead() || !tgt.getWorld().equals(pl.getWorld())) {
                stopPrelaunchLine(launcherUUID);
                return;
            }

            // Draw purple line (VISIBLE ONLY TO LAUNCHER)
            drawPurpleLineToViewer(pl, pl, tgt);

        }, 0L, 1L);

        STINGER_PRELAUNCH_LINE.put(launcherUUID, task);
    }

    private static void stopPrelaunchLine(UUID launcherUUID) {
        org.bukkit.scheduler.BukkitTask t = STINGER_PRELAUNCH_LINE.remove(launcherUUID);
        if (t != null) t.cancel();
        // keep STINGER_ARMED entry until attach or disableStinger; your call
    }


    private static void attachMissileTrackLine(UUID missileUUID, UUID targetUUID, UUID launcherUUID) {
        // Handoff: stop the prelaunch line (player->target)
        stopPrelaunchLine(launcherUUID);

        // Explicit mapping (requested)
        STINGER_MISSILE_TO_TARGET.put(missileUUID, targetUUID);

        // If somehow called twice for same missile, cancel old task
        org.bukkit.scheduler.BukkitTask old = STINGER_MISSILE_LINES.remove(missileUUID);
        if (old != null) old.cancel();

        final org.bukkit.plugin.Plugin plugin = java.util.Objects.requireNonNull(
                org.bukkit.Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                "PlaceholderAPI plugin ref missing"
        );

        org.bukkit.scheduler.BukkitTask task = org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            final org.bukkit.entity.Entity missile = org.bukkit.Bukkit.getEntity(missileUUID);
            final org.bukkit.entity.Entity tgt    = org.bukkit.Bukkit.getEntity(targetUUID);

            if (missile == null || tgt == null || !missile.isValid() || missile.isDead()
                    || !tgt.isValid() || tgt.isDead()
                    || !missile.getWorld().equals(tgt.getWorld())) {
                stopMissileLine(missileUUID);
                return;
            }

            // Draw purple line (WORLD visible; matches your existing style)
            drawPurpleLineWorld(missile, tgt);

        }, 0L, 1L);

        STINGER_MISSILE_LINES.put(missileUUID, task);
    }

    private static void stopMissileLine(UUID missileUUID) {
        org.bukkit.scheduler.BukkitTask t = STINGER_MISSILE_LINES.remove(missileUUID);
        if (t != null) t.cancel();
        STINGER_MISSILE_TO_TARGET.remove(missileUUID);
    }


    private static void drawPurpleLineToViewer(org.bukkit.entity.Player viewer, org.bukkit.entity.Entity from, org.bukkit.entity.Entity to) {
        final org.bukkit.Location a = from.getLocation().clone();
        final org.bukkit.Location b = to.getLocation().clone();
        try { a.add(0, from.getHeight() * 0.5, 0); } catch (Throwable ignored) {}
        try { b.add(0, to.getHeight() * 0.5, 0); } catch (Throwable ignored) {}

        org.bukkit.util.Vector ab = b.toVector().subtract(a.toVector());
        double len = ab.length();
        if (len < 1e-6) return;

        int n = Math.max(2, Math.min(25, (int) Math.ceil(len * 3)));
        org.bukkit.util.Vector step = ab.multiply(1.0 / (n - 1));

        org.bukkit.Color c = org.bukkit.Color.fromRGB(191, 119, 246); // purple
        org.bukkit.Particle.DustOptions dust = new org.bukkit.Particle.DustOptions(c, 0.6f);

        org.bukkit.Location cur = a.clone();
        for (int i = 0; i < n; i++) {
            viewer.spawnParticle(org.bukkit.Particle.DUST, cur, 1, 0, 0, 0, 0.0, dust);
            cur.add(step);
        }
    }

    private static void drawPurpleLineWorld(org.bukkit.entity.Entity from, org.bukkit.entity.Entity to) {
        final org.bukkit.World w = from.getWorld();
        final org.bukkit.Location a = from.getLocation().clone();
        final org.bukkit.Location b = to.getLocation().clone();
        try { a.add(0, from.getHeight() * 0.5, 0); } catch (Throwable ignored) {}
        try { b.add(0, to.getHeight() * 0.5, 0); } catch (Throwable ignored) {}

        org.bukkit.util.Vector ab = b.toVector().subtract(a.toVector());
        double len = ab.length();
        if (len < 1e-6) return;

        int n = Math.max(2, Math.min(25, (int) Math.ceil(len * 3)));
        org.bukkit.util.Vector step = ab.multiply(1.0 / (n - 1));

        org.bukkit.Color c = org.bukkit.Color.fromRGB(191, 119, 246); // purple
        org.bukkit.Particle.DustOptions dust = new org.bukkit.Particle.DustOptions(c, 0.6f);

        org.bukkit.Location cur = a.clone();
        for (int i = 0; i < n; i++) {
            w.spawnParticle(org.bukkit.Particle.DUST, cur, 1, 0, 0, 0, 0.0, dust, true);
            cur.add(step);
        }
    }




    private static void disableStinger(@org.jetbrains.annotations.Nullable org.bukkit.entity.Player p) {
        // If we have a player context -> disable ONLY that player's lock/reset tasks.
        if (p != null) {
            cleanup(p.getUniqueId());
            return;
        }

        // No player context (console parse / global call) -> disable EVERYTHING stinger-lock related.
        disableAllStingerLocks();
    }

    private static void disableAllStingerLocks() {
        // 1) Cancel reset queues
        for (org.bukkit.scheduler.BukkitTask t : new java.util.ArrayList<>(STINGER_RESET_QUEUES.values())) {
            try { t.cancel(); } catch (Throwable ignored) {}
        }
        STINGER_RESET_QUEUES.clear();

        // 2) Cancel lock tasks
        for (org.bukkit.scheduler.BukkitTask t : new java.util.ArrayList<>(STINGER_LOCK_TASKS.values())) {
            try { t.cancel(); } catch (Throwable ignored) {}
        }
        STINGER_LOCK_TASKS.clear();

        // 3) Remove reveal glow + clear states
        for (java.util.UUID pid : new java.util.HashSet<>(STINGER_LOCKS.keySet())) {
            StingerLockState st = STINGER_LOCKS.get(pid);
            org.bukkit.entity.Player shooter = org.bukkit.Bukkit.getPlayer(pid);
            if (st != null && shooter != null && shooter.isOnline()) {
                // remove viewer-only glow from the locked player, if any
                removeRevealIfAny(shooter, st);
            }
        }
        STINGER_LOCKS.clear();

        // IMPORTANT: We intentionally do NOT clear ACTIVE_MISSILES here.
        // That map is for missile particle pass-off (trackv4 / ParticleCenterToCenter), not lock mode.
    }

    private static @org.jetbrains.annotations.NotNull String onStingerLockParse(
            org.bukkit.entity.Player p,
            String argsRaw
    ) {
        // argsRaw: MODE`INTERVAL`#INTERVALS`RANGE`PASSTHRU`DOPPLER`FOV
        final String[] a = argsRaw.split("`", -1);
        if (a.length < 7) {
            return "§cUsage: %Archistructure_stingerLock_MODE`INTERVAL`#INTERVALS`RANGE`PASSTHRU`DOPPLER`FOV%";
        }

        StingerMode mode;
        try { mode = StingerMode.valueOf(a[0].trim().toUpperCase(java.util.Locale.ROOT)); }
        catch (Exception e) { mode = StingerMode.ALL; }

        int interval = parseIntSafe(a[1], 5, 1, 40);
        int nIntervals = parseIntSafe(a[2], 4, 1, 40);
        double range = parseDoubleSafe(a[3], 64.0, 1.0, 256.0);
        boolean passThru = parseBoolSafe(a[4], true);
        boolean doppler = parseBoolSafe(a[5], true);
        double fov = parseDoubleSafe(a[6], 45.0, 5.0, 180.0);

        final UUID pid = p.getUniqueId();
        final StingerLockState st = STINGER_LOCKS.computeIfAbsent(pid, StingerLockState::new);

        // Update config for the running LOCK task to pick up immediately
        st.mode = mode;
        st.intervalTicks = interval;
        st.nIntervals = nIntervals;
        st.range = range;
        st.passThruBlocks = passThru;
        st.doppler = doppler;
        st.fovDeg = fov;

        // Ensure lock task exists
        ensureLockTaskRunning(p);

        // Restart reset queue (this is the thing you clarified)
        restartResetQueue(p, interval, nIntervals);

        // Return: locked name/type or Not Locked
        if (st.fullyLocked && st.currentTargetId != null) {
            org.bukkit.entity.Entity e = org.bukkit.Bukkit.getEntity(st.currentTargetId);
            if (e instanceof org.bukkit.entity.Player tp) return "§c" + tp.getName();
            if (e != null) return e.getType().name();
        }
        if( st.currentTargetId != null ) return "§6Locking...";
        return "§aNot Locked";
    }

    private static @org.jetbrains.annotations.NotNull String onStingerChangeLock(org.bukkit.entity.Player p) {
        StingerLockState st = STINGER_LOCKS.get(p.getUniqueId());
        if (st == null) return "none";

        UUID cur = st.currentTargetId;
        if (cur != null) {
            st.pushSwitched(cur);
        }

        // Clear lock + restart stopwatch + reset doppler (via clearLock -> st.clearTarget)
        clearLock(p, st, true);
        return "ok";
    }

    private static void ensureMutualReveal(org.bukkit.entity.Player shooter, StingerLockState st, org.bukkit.entity.Player target) {
        // Refresh every 5 ticks (tweak to 7 if you want)
        if (st.localTick < st.nextRevealAtLocalTick) return;
        st.nextRevealAtLocalTick = st.localTick + 5;

        // If target changed, remove old reveal first
        if (st.revealOn && st.lastRevealTargetPlayerId != null && !st.lastRevealTargetPlayerId.equals(target.getUniqueId())) {
            removeRevealIfAny(shooter, st);
        }

        // Target sees shooter glowing (lock reveal)
        tyounwfydtuhk2foypbdvhp2y3ldb(target, shooter, true);

        // Shooter sees target glowing (your lock highlight)
        tyounwfydtuhk2foypbdvhp2y3ldb(shooter, target, true);

        st.lastRevealTargetPlayerId = target.getUniqueId();
        st.revealOn = true;
    }
// ===============================
// Tasks
// ===============================

    private static void ensureLockTaskRunning(org.bukkit.entity.Player p) {
        final UUID pid = p.getUniqueId();
        if (STINGER_LOCK_TASKS.containsKey(pid)) return;

        final org.bukkit.plugin.Plugin plugin = java.util.Objects.requireNonNull(
                org.bukkit.Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                "PlaceholderAPI plugin ref missing"
        );

        org.bukkit.scheduler.BukkitTask task = org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            StingerLockState st = STINGER_LOCKS.get(pid);
            org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayer(pid);

            if (st == null || pl == null || !pl.isOnline()) {
                cleanup(pid);
                return;
            }

            st.localTick++;

            // Expiry: if no reset pings happened recently, expire
            if (st.expiresAtTick > 0 && st.localTick > st.expiresAtTick) {
                // expire quietly
                cleanup(pid);
                return;
            }

            // Every tick: use current FOV (direction) + drive lock timer
            final boolean doScan = (st.intervalTicks <= 1) || (st.localTick % st.intervalTicks == 0);

            if (doScan) {
                // Validate existing target LOS/FOV, else reacquire
                UUID cur = st.currentTargetId;

                org.bukkit.entity.Entity curEnt = (cur != null) ? org.bukkit.Bukkit.getEntity(cur) : null;
                if (!isValidTargetFor(pl, st, curEnt)) {
                    if (curEnt != null) st.lastTargetLoc = curEnt.getLocation().clone();
                    clearLock(pl, st, true); // we'll update clearLock below to call st.clearTarget(...)
                    // reacquire below
                }

                if (st.currentTargetId == null) {
                    org.bukkit.entity.Entity next = pickTarget(pl, st);
                    if (next != null) {
                        st.setTarget(next); // resets progress + dopplerPitch + beep schedule
                    }
                }

            }

            // Render + sound + completion
            if (st.currentTargetId != null) {
                
                org.bukkit.entity.Entity tgt = org.bukkit.Bukkit.getEntity(st.currentTargetId);
                if (!isValidTargetFor(pl, st, tgt)) {
                    if (tgt != null) st.lastTargetLoc = tgt.getLocation().clone();
                    // gray “lost” line once
                    drawLostLineOnce(pl, st);
                    clearLock(pl, st, true);
                    return;
                }

                // Reveal glow does NOT require full lock (as long as we currently have a valid target)
                if (tgt instanceof org.bukkit.entity.Player lockedPlayer) {
                    ensureMutualReveal(pl, st, lockedPlayer);
                } else {
                    // If we were previously revealing a player but now target isn't a player
                    removeRevealIfAny(pl, st);
                }
                

                // progress toward full lock (3 seconds = 60 ticks)
                if (!st.fullyLocked) {
                    st.progressTicks++;
                    if (st.progressTicks >= 60) {
                        st.progressTicks = 60;
                        st.fullyLocked = true;

                        // remember last full lock
                        st.lastFullLockId = st.currentTargetId;
                        if (st.currentTargetId != null) {
                            STINGER_LAST_FULL_LOCK.put(st.playerId, st.currentTargetId);
                            STINGER_LAST_FULL_LOCK_PERSIST.put(pl.getUniqueId(), st.currentTargetId);
                        }

                        // apply reveal glow: locked PLAYER sees shooter glowing
                        if (tgt instanceof org.bukkit.entity.Player lockedPlayer) {
                            // viewer = lockedPlayer, target = shooter (pl)
                            // Your helper: tyounwfydtuhk2foypbdvhp2y3ldb(viewer, target, glowing)

                            st.lastRevealTargetPlayerId = lockedPlayer.getUniqueId();
                            st.revealOn = true;
                        }
                    }
                } else {
                    // keep reveal active “as long as target is locked”
                    if (tgt instanceof org.bukkit.entity.Player lockedPlayer) {
                        if (!lockedPlayer.getUniqueId().equals(st.lastRevealTargetPlayerId) || !st.revealOn) {

                            st.lastRevealTargetPlayerId = lockedPlayer.getUniqueId();
                            st.revealOn = true;
                        }
                    }
                }

                // particle line to viewer only
                drawLockLine(pl, tgt, st);

                // doppler sound (viewer only)
                if (st.doppler) maybePlayDoppler(pl, st);
            } else {
                // no target: if we had reveal on, remove it
                removeRevealIfAny(pl, st);
            }

        }, 0L, 1L);

        STINGER_LOCK_TASKS.put(pid, task);
    }

    private static void restartResetQueue(org.bukkit.entity.Player p, int intervalTicks, int nIntervals) {
        final UUID pid = p.getUniqueId();
        org.bukkit.scheduler.BukkitTask old = STINGER_RESET_QUEUES.remove(pid);
        if (old != null) old.cancel();

        final org.bukkit.plugin.Plugin plugin = java.util.Objects.requireNonNull(
                org.bukkit.Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                "PlaceholderAPI plugin ref missing"
        );

        // This queue runs for INTERVAL * nIntervals ticks, pinging expiry each INTERVAL.
        org.bukkit.scheduler.BukkitTask q = org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int runs = 0;
            @Override public void run() {
                StingerLockState st = STINGER_LOCKS.get(pid);
                if (st == null) { cleanup(pid); return; }

                // Ping: expire if no ping for INTERVAL*2 ticks
                st.expiresAtTick = st.localTick + (intervalTicks * 2);

                runs++;
                if (runs >= nIntervals) {
                    org.bukkit.scheduler.BukkitTask me = STINGER_RESET_QUEUES.remove(pid);
                    if (me != null) me.cancel();
                }
            }
        }, 0L, Math.max(1L, intervalTicks));

        STINGER_RESET_QUEUES.put(pid, q);
    }

// ===============================
// Targeting rules
// ===============================

    private static org.bukkit.entity.Entity pickTarget(org.bukkit.entity.Player pl, StingerLockState st) {
        
        
        final org.bukkit.Location eye = pl.getEyeLocation();
        final org.bukkit.World w = eye.getWorld();
        if (w == null) return null;

        final double r = st.range;
        final java.util.Collection<org.bukkit.entity.Entity> near =
                w.getNearbyEntities(eye, r, r, r, e -> e instanceof org.bukkit.entity.LivingEntity && e.isValid() && !e.isDead());

        final org.bukkit.scoreboard.Team myTeam = getTeam(pl);

        java.util.List<org.bukkit.entity.Entity> nonTeamPlayers = new java.util.ArrayList<>();
        java.util.List<org.bukkit.entity.Entity> players = new java.util.ArrayList<>();
        java.util.List<org.bukkit.entity.Entity> hostiles = new java.util.ArrayList<>();
        java.util.List<org.bukkit.entity.Entity> passives = new java.util.ArrayList<>();

        for (org.bukkit.entity.Entity e : near) {
            if (isLockoutActive(pl.getUniqueId(), e.getUniqueId())) continue;


            if (e.getUniqueId().equals(pl.getUniqueId())) continue;
            if (!isAllowedByMode(st.mode, e)) continue;
            if (!inFov(pl, e, st.fovDeg)) continue;
            if (!hasAllowedLineOfSight(pl, e, st.passThruBlocks)) continue;

            // ignore queue unless we must reuse
            if (st.switchedQueue.contains(e.getUniqueId())) continue;

            if (e instanceof org.bukkit.entity.Player pe) {
                if (myTeam != null) {
                    org.bukkit.scoreboard.Team his = getTeam(pe);
                    if (his == null || !his.equals(myTeam)) nonTeamPlayers.add(e);
                    else players.add(e);
                } else {
                    // “NO-TEAM => skip the non-team bucket”
                    players.add(e);
                }
            } else if (isHostile(e)) hostiles.add(e);
            else passives.add(e);
        }

        org.bukkit.entity.Entity chosen =
                firstOrNull(nonTeamPlayers, players, hostiles, passives);

        // If all candidates were in the switchedQueue, pop one and allow it
        if (chosen == null) {
            UUID freed = st.switchedQueue.pollFirst();
            if (freed != null) {
                org.bukkit.entity.Entity e = org.bukkit.Bukkit.getEntity(freed);
                if (e != null && isAllowedByMode(st.mode, e) && inFov(pl, e, st.fovDeg) && hasAllowedLineOfSight(pl, e, st.passThruBlocks)) {
                    chosen = e;
                }
            }
        }

        return chosen;
    }

    private static boolean isValidTargetFor(org.bukkit.entity.Player pl, StingerLockState st, org.bukkit.entity.Entity e) {
        if (e == null) return false;

        if (!e.isValid() || e.isDead()) return false;
        if (!e.getWorld().equals(pl.getWorld())) return false;
        if (!(e instanceof org.bukkit.entity.LivingEntity)) return false;
        if (isLockoutActive(pl.getUniqueId(), e.getUniqueId())) return false;

        if (!isAllowedByMode(st.mode, e)) return false;
        if (!inFov(pl, e, st.fovDeg)) return false;
        return hasAllowedLineOfSight(pl, e, st.passThruBlocks);
    }

    private static boolean isAllowedByMode(StingerMode mode, org.bukkit.entity.Entity e) {
        switch (mode) {
            case PLAYERSONLY:  return e instanceof org.bukkit.entity.Player;
            case HOSTILESONLY: return !(e instanceof org.bukkit.entity.Player) && isHostile(e);
            case ENTITIES:     return !(e instanceof org.bukkit.entity.Player);
            case ALL:          return true;
        }
        return true;
    }

    private static boolean isHostile(org.bukkit.entity.Entity e) {
        return (e instanceof org.bukkit.entity.Monster)
                || (e instanceof org.bukkit.entity.Slime)
                || (e instanceof org.bukkit.entity.Phantom)
                || (e instanceof org.bukkit.entity.Shulker);
    }

    private static boolean inFov(org.bukkit.entity.Player pl, org.bukkit.entity.Entity e, double fovDeg) {
        final org.bukkit.Location eye = pl.getEyeLocation();
        final org.bukkit.util.Vector dir = eye.getDirection().normalize();

        org.bukkit.Location t = e.getLocation().clone();
        try { t.add(0, e.getHeight() * 0.5, 0); } catch (Throwable ignored) {}

        final org.bukkit.util.Vector to = t.toVector().subtract(eye.toVector()).normalize();
        double dot = dir.dot(to);
        dot = Math.max(-1.0, Math.min(1.0, dot));
        double ang = Math.toDegrees(Math.acos(dot));
        return ang <= (fovDeg * 0.5);
    }

    private static boolean hasAllowedLineOfSight(org.bukkit.entity.Player pl, org.bukkit.entity.Entity e, boolean passThru) {
        org.bukkit.Location eye = pl.getEyeLocation();
        org.bukkit.Location tgt = e.getLocation().clone();
        try { tgt.add(0, e.getHeight() * 0.5, 0); } catch (Throwable ignored) {}

        org.bukkit.util.Vector dir = tgt.toVector().subtract(eye.toVector());
        double len = dir.length();
        if (len < 0.0001) return true;
        dir.normalize();

        org.bukkit.World w = eye.getWorld();
        if (w == null) return false;

        org.bukkit.util.RayTraceResult rr = w.rayTraceBlocks(
                eye, dir, len,
                org.bukkit.FluidCollisionMode.NEVER,
                true // hit fluids? (this just means "ignorePassableBlocks" in some impls; depends on server)
        );

        if (rr == null || rr.getHitBlock() == null) return true;

        org.bukkit.Material m = rr.getHitBlock().getType();

        // If passThru=false: only pure air types are allowed
        if (!passThru) {
            return isAirLike(m);
        }

        // passThru=true: allow non-solid/passable-like
        return isPassThrough(m);
    }

    private static boolean isPassThrough(org.bukkit.Material m) {
        if (isAirLike(m)) return true;
        // allow many non-solid/passables
        if (!m.isSolid()) return true;

        // explicitly allow some common “passable but weird” blocks if needed
        return m == org.bukkit.Material.WATER
                || m == org.bukkit.Material.BUBBLE_COLUMN
                || m == org.bukkit.Material.COBWEB
                || m == org.bukkit.Material.STRING;
    }

    private static boolean isAirLike(org.bukkit.Material m) {
        return m == org.bukkit.Material.AIR || m == org.bukkit.Material.CAVE_AIR || m == org.bukkit.Material.VOID_AIR;
    }

// ===============================
// Visual/audio
// ===============================

    private static void drawLockLine(org.bukkit.entity.Player viewer, org.bukkit.entity.Entity tgt, StingerLockState st) {
        org.bukkit.Location a = viewer.getEyeLocation().clone();
        org.bukkit.Location b = tgt.getLocation().clone();
        try { b.add(0, tgt.getHeight() * 0.5, 0); } catch (Throwable ignored) {}

        org.bukkit.util.Vector ab = b.toVector().subtract(a.toVector());
        double len = ab.length();
        if (len < 0.001) return;

        int maxParticles = 25; // your spec
        int n = Math.max(2, Math.min(maxParticles, (int) Math.ceil(len * 3)));
        org.bukkit.util.Vector step = ab.multiply(1.0 / (n - 1));

        // Color:
        org.bukkit.Color c;
        float size = 0.45f;

        if (st.fullyLocked) {
            c = org.bukkit.Color.fromRGB(0, 255, 255); // aqua
        } else {
            double t = Math.max(0.0, Math.min(1.0, st.progressTicks / 60.0));
            int r = (int) Math.round(0 + (255 - 0) * t);
            int g = (int) Math.round(255 + (0 - 255) * t);
            c = org.bukkit.Color.fromRGB(clamp255(r), clamp255(g), 0);
        }

        org.bukkit.Particle.DustOptions dust = new org.bukkit.Particle.DustOptions(c, size);

        org.bukkit.Location cur = a.clone();
        for (int i = 0; i < n; i++) {
            viewer.spawnParticle(org.bukkit.Particle.DUST, cur, 1, 0, 0, 0, 0.0, dust);
            cur.add(step);
        }
    }

    private static void drawLostLineOnce(org.bukkit.entity.Player viewer, StingerLockState st) {
        if (st.lastTargetLoc == null) return;
        org.bukkit.Location a = viewer.getEyeLocation().clone();
        org.bukkit.Location b = st.lastTargetLoc.clone();

        org.bukkit.util.Vector ab = b.toVector().subtract(a.toVector());
        double len = ab.length();
        if (len < 0.001) return;

        int n = Math.max(2, Math.min(25, (int) Math.ceil(len * 3)));
        org.bukkit.util.Vector step = ab.multiply(1.0 / (n - 1));

        org.bukkit.Particle.DustOptions dust = new org.bukkit.Particle.DustOptions(org.bukkit.Color.fromRGB(160,160,160), 0.45f);
        org.bukkit.Location cur = a.clone();
        for (int i = 0; i < n; i++) {
            viewer.spawnParticle(org.bukkit.Particle.DUST, cur, 1, 0, 0, 0, 0.0, dust);
            cur.add(step);
        }
    }

    private static void maybePlayDoppler(Player viewer, StingerLockState st) {
        final int REQUIRED_LOCK_TICKS = 60; // 3s
        final boolean locked = st.progressTicks >= REQUIRED_LOCK_TICKS;

        // Beep period: ramps faster over time, but NEVER reaches 2-tick spam unless locked
        double t = Math.max(0.0, Math.min(1.0, st.progressTicks / (double) REQUIRED_LOCK_TICKS));

        int period;
        if (locked) {
            period = 2;
        } else {
            // 8 -> 4 ticks linearly
            period = (int) Math.round(8 + (3 - 8) * t);
            period = Math.max(3, Math.min(8, period));
        }


        if (st.localTick < st.nextBeepAtLocalTick) return;
        st.nextBeepAtLocalTick = st.localTick + period;

        // --- Pitch ramp (time-based) ---
        // This is your "threshold" (2 - x). Pick a smaller preMax = slower climb.
        final float preMax = 1.6f; // <- tweak: 1.4 slower, 1.7 faster
        final float pitchPerTick = preMax / REQUIRED_LOCK_TICKS; // linear ramp over 3s

        int dt = st.localTick - st.lastBeepTick;
        if (dt < 0) dt = 0;
        st.lastBeepTick = st.localTick;

        if (!locked) {
            st.dopplerPitch = Math.min(preMax, st.dopplerPitch + pitchPerTick * dt);
        } else {
            // hard step ONLY when actually locked
            st.dopplerPitch = 2.0f;
        }

        viewer.playSound(viewer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, st.dopplerPitch);
    }

    private static void clearLock(org.bukkit.entity.Player shooter, StingerLockState st, boolean removeReveal) {
        // capture last loc before we drop the id
        org.bukkit.Location lastLoc = st.lastTargetLoc;
        if (st.currentTargetId != null) {
            org.bukkit.entity.Entity e = org.bukkit.Bukkit.getEntity(st.currentTargetId);
            if (e != null) lastLoc = e.getLocation().clone();
        }

        // reset target + lock progress + doppler (THIS fixes the pitch carryover)
        st.clearTarget(lastLoc);

        if (removeReveal) removeRevealIfAny(shooter, st);
    }


    private static void removeRevealIfAny(org.bukkit.entity.Player shooter, StingerLockState st) {
        if (st.revealOn && st.lastRevealTargetPlayerId != null) {
            org.bukkit.entity.Player viewer = org.bukkit.Bukkit.getPlayer(st.lastRevealTargetPlayerId);
            if (viewer != null && viewer.isOnline()) {
                tyounwfydtuhk2foypbdvhp2y3ldb(viewer, shooter, false);
                tyounwfydtuhk2foypbdvhp2y3ldb(shooter, viewer, false);

            }
        }
        st.revealOn = false;
        st.lastRevealTargetPlayerId = null;
    }

    private static void cleanup(UUID pid) {
        // cancel reset queue
        org.bukkit.scheduler.BukkitTask q = STINGER_RESET_QUEUES.remove(pid);
        if (q != null) q.cancel();

        // cancel lock task
        org.bukkit.scheduler.BukkitTask t = STINGER_LOCK_TASKS.remove(pid);
        if (t != null) t.cancel();

        // remove state (also remove glow if needed)
        StingerLockState st = STINGER_LOCKS.remove(pid);
        org.bukkit.entity.Player shooter = org.bukkit.Bukkit.getPlayer(pid);
        if (st != null && shooter != null) {
            removeRevealIfAny(shooter, st);
        }
    }

// ===============================
// Helpers
// ===============================

    private static final java.util.concurrent.ConcurrentHashMap<String, Long> STINGER_LOCKOUT_UNTIL_MS =
            new java.util.concurrent.ConcurrentHashMap<>();

    private static String lockoutKey(java.util.UUID shooter, java.util.UUID target) {
        return shooter.toString() + "|" + target.toString();
    }

    private static boolean isLockoutActive(java.util.UUID shooter, java.util.UUID target) {
        Long until = STINGER_LOCKOUT_UNTIL_MS.get(lockoutKey(shooter, target));
        return until != null && until > System.currentTimeMillis();
    }

    private static void addLockoutTicks(java.util.UUID shooter, java.util.UUID target, int ticks) {
        if (ticks <= 0) return;
        long ms = System.currentTimeMillis() + (long) ticks * 50L;
        STINGER_LOCKOUT_UNTIL_MS.put(lockoutKey(shooter, target), ms);
    }


    private static int parseIntSafe(String s, int def, int min, int max) {
        try {
            int v = Integer.parseInt(s.trim());
            return Math.max(min, Math.min(max, v));
        } catch (Exception e) { return def; }
    }
    private static double parseDoubleSafe(String s, double def, double min, double max) {
        try {
            double v = Double.parseDouble(s.trim());
            return Math.max(min, Math.min(max, v));
        } catch (Exception e) { return def; }
    }
    private static boolean parseBoolSafe(String s, boolean def) {
        if (s == null) return def;
        String v = s.trim().toLowerCase(java.util.Locale.ROOT);
        if (v.equals("true") || v.equals("1") || v.equals("yes")) return true;
        if (v.equals("false") || v.equals("0") || v.equals("no")) return false;
        return def;
    }
    private static int clamp255(int v) { return Math.max(0, Math.min(255, v)); }

    @SafeVarargs
    private static org.bukkit.entity.Entity firstOrNull(java.util.List<org.bukkit.entity.Entity>... lists) {
        for (java.util.List<org.bukkit.entity.Entity> l : lists) if (l != null && !l.isEmpty()) return l.get(0);
        return null;
    }

    private static org.bukkit.scoreboard.Team getTeam(org.bukkit.entity.Player p) {
        try {
            org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
            if (sb == null) return null;
            return sb.getEntryTeam(p.getName());
        } catch (Throwable ignored) { return null; }
    }

    private void tkoy2udy24und(
            Player tyunotyunwfytun,
            org.bukkit.entity.LivingEntity tywfuntyuf2nt,
            Location tywufntyunwftw,
            double E, int F, int G,
            int tywfutnywukd
    ) {
        final World w = tywufntyunwftw.getWorld();
        if (w == null) {
            return;
        }

  

        new BukkitRunnable() {
            int wd3wpfdypu3nd = 0;
            Location tkywfkdyuwfd = tywufntyunwftw.clone();

            @Override public void run() {

                // ---- Shooter offline? ----
                if (!tyunotyunwfytun.isOnline()) {
                    cancel();
                    return;
                }

                // ---- Target validity checks ----
                if (tywfuntyuf2nt == null) {
                    cancel();
                    return;
                }

                if (tywfuntyuf2nt instanceof Player) {
                    Player pT = (Player) tywfuntyuf2nt;
                    if (!pT.isOnline()) {
                        cancel();
                        return;
                    }
                } else {
                    if (tywfuntyuf2nt.isDead() || !tywfuntyuf2nt.isValid()) {
                        cancel();
                        return;
                    }
                }

                if (wd3wpfdypu3nd >= G) {
                    cancel();
                    return;
                }

                // ---- World check ----
                if (tywfuntyuf2nt.getWorld() == null || !tkywfkdyuwfd.getWorld().equals(tywfuntyuf2nt.getWorld())) {
                    cancel();
                    return;
                }

                // ---- Aim at mid-height (height/2) ----
                Location tywfunMid = tywfuntyuf2nt.getLocation().clone()
                        .add(0, Math.max(0.0, tywfuntyuf2nt.getHeight() / 2.0), 0);

                Vector tyuwkdyoukfdb = tywfunMid.toVector().subtract(tkywfkdyuwfd.toVector());
                double kyukfcyuwf = tyuwkdyoukfdb.length();

                // ---- hit check ----
                if (kyukfcyuwf <= (E + 1.0)) {


                    String oyuwnftoysuwr = (tywfuntyuf2nt instanceof Player)
                            ? ((Player) tywfuntyuf2nt).getName()
                            : tywfuntyuf2nt.getUniqueId().toString();

                    if( tywfuntyuf2nt instanceof Player) Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            "ei run-custom-trigger trigger:ArchiWardenSpirit player:"
                                    + tyunotyunwfytun.getName()
                                    + " slot:-1 "
                                    + oyuwnftoysuwr
                    ); else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), n234oyudnop3yudnwd +  tyunotyunwfytun.getName() + tno2y3u4ntoy2u4nt2 + oyuwnftoysuwr);


                    cancel();
                    return;
                }

                Vector ktyukfyu2fd = tyuwkdyoukfdb.normalize();

                // Move toward target
                tkywfkdyuwfd.add(ktyukfyu2fd.multiply(E));

                // AQUA dust size 2, FORCE
                Particle.DustOptions aqua = new Particle.DustOptions(Color.AQUA, 2.0f);
                w.spawnParticle(Particle.DUST, tkywfkdyuwfd, 1, 0, 0, 0, 0, aqua, true);

                

                wd3wpfdypu3nd += Math.max(1, F);
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), 0L, Math.max(1, F));
    }


    private boolean ist(Player tywkftyukwf, Player twysuktywfuktfwtu) {
        try {
            if (tywkftyukwf == null || twysuktywfuktfwtu == null) return false;

            Scoreboard wtkyfutwfutn = Objects.requireNonNull(Bukkit.getScoreboardManager())
                    .getMainScoreboard();

            Team ktwysfutkywuftsr = wtkyfutwfutn.getEntryTeam(tywkftyukwf.getName());
            if (ktwysfutkywuftsr == null) return false;

            return ktwysfutkywuftsr.hasEntry(twysuktywfuktfwtu.getName());
        } catch (Exception ignored) {
            return false;
        }
    }

    private org.bukkit.entity.LivingEntity fnepir2(
            Location tynofwyuadn,
            Player tnyfuwndyufd,
            double ywfntyowufntdsw,
            boolean tnywsuontywunft
    ) {
        World w = tynofwyuadn.getWorld();
        if (w == null) return null;

        double tykwftwyfuntwt = ywfntyowufntdsw * ywfntyowufntdsw;

        org.bukkit.entity.LivingEntity fwytkwfyudt = null;
        double tkwyftkyuwftn = Double.MAX_VALUE;

        int tywfktyufwt = 0;
        int tywufktyukwft = 0;
        int twfyuntywuftn = 0;

        int tywfdn_enemyEnt_scanned = 0;
        int tywfdn_enemyEnt_inRange = 0;

        // ---- Pass 1: enemy players (same logic as before) ----
        for (Player tywufntoywunt : w.getPlayers()) {
            if (tywufntoywunt == null || !tywufntoywunt.isOnline()) continue;
            tywfktyufwt++;

            if (tywufntoywunt.getUniqueId().equals(tnyfuwndyufd.getUniqueId())) continue;

            Location twyywfutnwyitko = tywufntoywunt.getLocation();
            double twkywftuwfntsryt = twyywfutnwyitko.distanceSquared(tynofwyuadn);

            if (twkywftuwfntsryt > tykwftwyfuntwt) continue;
            tywufktyukwft++;

            if (ist(tnyfuwndyufd, tywufntoywunt)) {
                twfyuntywuftn++;
                continue;
            }

            if (twkywftuwfntsryt < tkwyftkyuwftn) {
                tkwyftkyuwftn = twkywftuwfntsryt;
                fwytkwfyudt = tywufntoywunt;
            }
        }

        // ---- Pass 2: hostile mobs via your global set ----
        for (org.bukkit.entity.LivingEntity tywfdn_ent : w.getLivingEntities()) {
            if (tywfdn_ent == null) continue;
            if (tywfdn_ent instanceof Player) continue;

            tywfdn_enemyEnt_scanned++;

            if (tywfdn_ent.isDead() || !tywfdn_ent.isValid()) continue;

            if (HMsetnoy2un2yundt == null || !HMsetnoy2un2yundt.contains(tywfdn_ent.getType())) {
                continue;
            }

            Location tywfdn_loc = tywfdn_ent.getLocation();
            double tywfdn_d2 = tywfdn_loc.distanceSquared(tynofwyuadn);

            if (tywfdn_d2 > tykwftwyfuntwt) continue;
            tywfdn_enemyEnt_inRange++;

            if (tywfdn_d2 < tkwyftkyuwftn) {
                tkwyftkyuwftn = tywfdn_d2;
                fwytkwfyudt = tywfdn_ent;
            }
        }


        return fwytkwfyudt;
    }



    private void sws(
            Player asetnwoft,
            int pydhy3pudhp, // kept for compatibility
            double dy3unpdyu3pd, int thkyufdyu3fhd, double duywfpndyunwpd, int ky3ukfdyu3pnd,
            double wyduny3wufnpd, int dy3unfdyuwnd, int ky3npdyu3ndf,
            double dykfdyuk3d3pd
    ) {

        // final int DURATION_TICKS = 20 * 30;
        // final int HOMING_SPAWN_PERIOD = 20; // every 1s
        // final int MAX_HOMINGS = 30;

        final int dvykpkvy3plb = 20 * 30;
        final int vkwyfpukb = 20; // every 1s
        final int wvkyuwfpkyudf = 30;



        final AtomicReference<Location> yvunyp3unvb =
                new AtomicReference<>(asetnwoft.getLocation().clone().add(0, dy3unpdyu3pd, 0));
        final AtomicInteger tyunkyucf2 = new AtomicInteger(0);

        // ---- Orbit task ----
        BukkitRunnable tyunyfu3nt3pd = new BukkitRunnable() {
            int tmyuf2nd = 0;

            @Override public void run() {
                if (!asetnwoft.isOnline()) { cancel(); return; }
                if (tmyuf2nd >= dvykpkvy3plb) { cancel(); return; }

                double tnyukydu = Math.max(1, thkyufdyu3fhd);
                double tywufktyu2kfd =
                        (2.0 * Math.PI) * ((tmyuf2nd / Math.max(1, ky3ukfdyu3pnd)) % tnyukydu) / tnyukydu;

                Location kyvukbwpdbds = asetnwoft.getLocation().add(0, dy3unpdyu3pd, 0);
                Location ysukdwyufkdwp = kyvukbwpdbds.clone().add(
                        Math.cos(tywufktyu2kfd) * duywfpndyunwpd,
                        0,
                        Math.sin(tywufktyu2kfd) * duywfpndyunwpd
                );

                yvunyp3unvb.set(ysukdwyufkdwp);

                asetnwoft.getWorld().spawnParticle(
                        Particle.SCULK_SOUL,
                        ysukdwyufkdwp,
                        1, 0, 0, 0, 0,
                        null, true
                );

                tmyuf2nd += Math.max(1, ky3ukfdyu3pnd);
            }
        };

        tyunyfu3nt3pd.runTaskTimer(
                Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                0L,
                Math.max(1, ky3ukfdyu3pnd)
        );

        // ---- Homing spawn task ----
        BukkitRunnable spawnTask = new BukkitRunnable() {
            int t = 0;

            @Override public void run() {
                try {
                    if (!asetnwoft.isOnline()) { cancel(); return; }
                    if (t >= dvykpkvy3plb) { cancel(); return; }

                    if (tyunkyucf2.get() >= wvkyuwfpkyudf) {
     
                        t += vkwyfpukb;
                        return;
                    }

                    Location tkyu2kd32 = yvunyp3unvb.get();
                    if (tkyu2kd32 == null || tkyu2kd32.getWorld() == null) {
                        t += vkwyfpukb;
                        return;
                    }



                    // ✅ UPDATED: players + hostile mobs
                    org.bukkit.entity.LivingEntity my2und2y3udn =
                            fnepir2(tkyu2kd32, asetnwoft, dykfdyuk3d3pd, true);

                    if (my2und2y3udn != null) {
                        int idx = tyunkyucf2.incrementAndGet();

    

                        // ✅ UPDATED: homing now accepts LivingEntity too
                        tkoy2udy24und(
                                asetnwoft,
                                my2und2y3udn,
                                tkyu2kd32,
                                wyduny3wufnpd,
                                dy3unfdyuwnd,
                                ky3npdyu3ndf,
                                idx
                        );
                    } else {
  
                    }

                    t += vkwyfpukb;

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        };

        spawnTask.runTaskTimer(
                Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                0L,
                vkwyfpukb
        );

        // ---- Cleanup ----
        Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("PlaceholderAPI"),
                () -> {
                    try { tyunyfu3nt3pd.cancel(); } catch (Exception ignored) {}
                    try { spawnTask.cancel(); } catch (Exception ignored) {}
                },
                dvykpkvy3plb
        );
    }


    private static class ModeSpec {
        final String untieit; // admin/console/op/opuser/user/player
        final Set<String> geffre; // lower-case command names
        final Set<String> policetape; // lower-case words/command names

        ModeSpec(String bmt, Set<String> wltp, Set<String> blpt) {
            this.untieit = bmt;
            this.geffre = wltp;
            this.policetape = blpt;
        }
    }

    /**
     * Extract the "command name" from the command string.
     * This is the first token before whitespace.
     */
    private  String carefullysetsdown(String hiscoffee) {
        if (hiscoffee == null) return nst;

        String shaved = hiscoffee.trim();
        if (shaved.isEmpty()) return nst;

        // First token
        String[] onyourface = shaved.split("\\s+", mill2);
        String shecameoutandthrew = onyourface[I];

        // Remove leading slash if somehow present here
        if (shecameoutandthrew.startsWith(wfydunaowfydun)) shecameoutandthrew = shecameoutandthrew.substring(INT3);

        return shecameoutandthrew;
    }

    private  boolean youwillgetarrested(String bish, Set<String> standardsofsmth) {
        if (bish == null) return arsdienwdhw;
        String whythishappen = bish.toLowerCase();

        // support namespaced commands
        String rightnextto = whythishappen.contains(wfdunyunda) ? whythishappen.substring(whythishappen.indexOf(':') + INT3) : whythishappen;

        return standardsofsmth.contains(whythishappen) || standardsofsmth.contains(rightnextto);
    }

    private  boolean idltm(ModeSpec goinsideurhouse, Player gooutnow, String sitdownorelse, String nomorewarnings) {
        String entertaining = carefullysetsdown(nomorewarnings);

        // 1) Whitelist check (if present)
        if (!goinsideurhouse.geffre.isEmpty()) {
            if (!youwillgetarrested(entertaining, goinsideurhouse.geffre)) {
                gooutnow.sendMessage(naltextc);
                return arsdienwdhw;
            }
        }
        // If whitelist not present -> allow all

        // 2) Blacklist name check
        if (!goinsideurhouse.policetape.isEmpty()) {
            if (youwillgetarrested(entertaining, goinsideurhouse.policetape)) {
                gooutnow.sendMessage(naltextc);
                return arsdienwdhw;
            }

            // 3) Blacklist word boundary scan across the full page
            String gtfoms = sitdownorelse.toLowerCase();
            for (String bad : goinsideurhouse.policetape) {
                if (bad == null || bad.isBlank()) continue;
                if (thereesanindividual(gtfoms, bad)) {
                    gooutnow.sendMessage(naltextc);
                    return arsdienwdhw;
                }
            }
        }

        return NEW_VALUE1;
    }

    /**
     * Checks if `word` appears in `text` with boundary markers on BOTH sides.
     * Boundaries include: '<', '+', whitespace, '>', ':', BOF, EOF.
     */
    private  boolean thereesanindividual(String whodoessmth, String nooooo) {
        if (whodoessmth == null || nooooo == null) return arsdienwdhw;
        if (nooooo.isEmpty()) return arsdienwdhw;

        int audiovideorecorded = I;
        while (true) {
            int isearchx = whodoessmth.indexOf(nooooo, audiovideorecorded);
            if (isearchx < I) return arsdienwdhw;

            int takea = isearchx - INT3;
            int seatfor = isearchx + nooooo.length();

            boolean meor = (takea < I) || tpc(whodoessmth.charAt(takea));
            boolean areyouseriuos = (seatfor >= whodoessmth.length()) || tpc(whodoessmth.charAt(seatfor));

            if (meor && areyouseriuos) return NEW_VALUE1;

            audiovideorecorded = isearchx + INT3;
        }
    }

    private  boolean tpc(char c) {
        if (Character.isWhitespace(c)) return NEW_VALUE1;
        return c == '<' || c == '+' || c == '>' || c == ':' || c == ' ';
    }

    /**
     * Splits the modesArg by commas, but ignores commas inside (...) blocks.
     */
    private  List<String> escortyouout(String whatidowrong) {
        List<String> recordingme = new ArrayList<>();
        if (whatidowrong == null) return recordingme;

        StringBuilder pushtheboundaries = new StringBuilder();
        int whatdontyoulike = I;

        for (int canyoutellme = I; canyoutellme < whatidowrong.length(); canyoutellme++) {
            char c = whatidowrong.charAt(canyoutellme);

            if (c == '(') whatdontyoulike++;
            if (c == ')') whatdontyoulike = Math.max(I, whatdontyoulike - INT3);

            if (c == ',' && whatdontyoulike == I) {
                recordingme.add(pushtheboundaries.toString());
                pushtheboundaries.setLength(I);
            } else {
                pushtheboundaries.append(c);
            }
        }

        if (pushtheboundaries.length() > I) recordingme.add(pushtheboundaries.toString());
        return recordingme;
    }

    private  List<ModeSpec> soicanimprove(String modesArg) {
        List<ModeSpec> seemchildsh = new ArrayList<>();

        for (String thisnice : escortyouout(modesArg)) {
            if (thisnice == null) continue;
            String abouttwohours = thisnice.trim();
            if (abouttwohours.isEmpty()) continue;

            String touchingtape;
            String pushingitagain = null;

            int deescalate = abouttwohours.indexOf('(');
            if (deescalate >= I && abouttwohours.endsWith(")")) {
                touchingtape = abouttwohours.substring(I, deescalate).trim().toLowerCase();
                pushingitagain = abouttwohours.substring(deescalate + INT3, abouttwohours.length() - INT3).trim();
            } else {
                touchingtape = abouttwohours.toLowerCase();
            }

            Set<String> doestgetit = new HashSet<>();
            Set<String> theend = new HashSet<>();

            if (pushingitagain != null && !pushingitagain.isBlank()) {
                notcoming(pushingitagain, doestgetit, theend);
            }

            seemchildsh.add(new ModeSpec(touchingtape, doestgetit, theend));
        }

        return seemchildsh;
    }


    /**
     * Parses filter text that may contain WHITELIST:... and/or BLACKLIST:...
     * Example bodies:
     *  - "WHITELIST:gamemode,tp"
     *  - "BLACKLIST:test,one"
     *  - "WHITELIST:gamemode,tp OR BLACKLIST:test,one"
     */
    private  void notcoming(String crossthepolice, Set<String> gonnaget, Set<String> arrestednow) {
        String illbehappy = crossthepolice.toUpperCase(Locale.ROOT);

        int bltfy = illbehappy.indexOf(toescortyou);
        int fywfi = illbehappy.indexOf(outtahere);

        if (bltfy >= I) {
            int idgafts = bltfy + toescortyou.length();
            int aintnecessary = (fywfi > idgafts) ? fywfi : crossthepolice.length();
            String nolmal = crossthepolice.substring(idgafts, aintnecessary);
            donttry(nolmal, gonnaget);
        }

        if (fywfi >= I) {
            int specificreason = fywfi + outtahere.length();
            int leaveit = (bltfy > specificreason) ? bltfy : crossthepolice.length();
            String uglass = crossthepolice.substring(specificreason, leaveit);
            donttry(uglass, arrestednow);
        }
    }

    private  void donttry(String uglayf, Set<String> ishegay) {
        if (uglayf == null) return;
        String[] whythatupsetyou = uglayf.split(keep);
        for (String evenifiwas : whythatupsetyou) {
            if (evenifiwas == null) continue;
            String idonliku = evenifiwas.trim().toLowerCase();
            if (idonliku.isEmpty()) continue;
            if (idonliku.equals(ost)) continue; // tolerate "OR" glue text
            ishegay.add(idonliku);
        }
    }


    // launcherUUID + ":" + targetUUID  -> projectile UUID
    private  final java.util.concurrent.ConcurrentMap<String, UUID> hypervigilance =
            new java.util.concurrent.ConcurrentHashMap<>();

    private  String survivalfeeling(UUID launcher, UUID target) {
        return launcher.toString() + wfdunyunda + target.toString();
    }
    public  void whoasked(UUID launcherUUID, UUID targetUUID) {
        final Plugin atwhatcost = Bukkit.getPluginManager().getPlugin(ppi);
        if (atwhatcost == null) return;

        // Quick upfront validation for initial player/target
        Entity grewup = Bukkit.getEntity(launcherUUID);
        Entity toofast = Bukkit.getEntity(targetUUID);
        if (grewup == null || toofast == null || !grewup.isValid() || !toofast.isValid()) return;

        final int senceofwork = dontvanish;     // 4 seconds (only for non-missile mode)
        final int adultlife  = shapeyou;     // cap per line
        final String survivalpatterns         = survivalfeeling(launcherUUID, targetUUID);

        new BukkitRunnable() {
            int slowlikyllingyou = I;
            boolean supportadults = arsdienwdhw;

            @Override
            public void run() {
                // Look for an active missile for this launcher/target pair
                UUID parentification = hypervigilance.get(survivalpatterns);
                Entity peacekeep = null;
                boolean protector = arsdienwdhw;
                if (parentification != null) {
                    peacekeep = Bukkit.getEntity(parentification);
                    if (peacekeep != null && peacekeep.isValid() && !peacekeep.isDead()) {
                        protector = NEW_VALUE1;
                        supportadults = NEW_VALUE1;
                    } else {
                        // Clean up stale mapping
                        hypervigilance.remove(survivalpatterns, parentification);
                    }
                }

                // Lifetime control:
                // - Normal mode: honor DURATION_TICKS.
                // - Missile mode: ignore DURATION_TICKS, run while missile exists.
                // - Once we had a missile and it’s gone, stop.
                if (!protector) {
                    if (supportadults) {
                        cancel();
                        return;
                    }
                    if (slowlikyllingyou++ >= senceofwork) {
                        cancel();
                        return;
                    }
                } else {
                    // missileActive: no tick++ or time limit
                }

                // Live fetch each tick
                Entity peace; // start of line
                Entity safety; // end of line (always the target)
                safety = Bukkit.getEntity(targetUUID);

                if (protector) {
                    peace = peacekeep;
                } else {
                    peace = Bukkit.getEntity(launcherUUID);
                }

                if (peace == null || safety == null || !peace.isValid() || !safety.isValid()) { cancel(); return; }

                // If either is a player and offline, stop
                if (peace instanceof Player pa && !pa.isOnline()) { cancel(); return; }
                if (safety instanceof Player pb && !pb.isOnline()) { cancel(); return; }

                if (!peace.getWorld().equals(safety.getWorld())) { cancel(); return; }
                World performswell = peace.getWorld();

                // Centers (mid-height)
                double ha = debtsoff, hb = debtsoff;
                try { ha = peace.getHeight() * debtsoff; } catch (Throwable ignored) {}
                try { hb = safety.getHeight() * debtsoff; } catch (Throwable ignored) {}
                Location deepdown = peace.getLocation().add(I, ha, I);
                Location somethinghurts = safety.getLocation().add(I, hb, I);

                Vector stillintheree = somethinghurts.toVector().subtract(deepdown.toVector());
                double screamingsilently = stillintheree.length();
                if (screamingsilently < wife) return;

                // Choose particle count for this frame (cap at MAX_PARTICLES, min 2)
                int morecomplex = Math.max(mill2, Math.min(adultlife, (int) Math.ceil(screamingsilently * ccp)));
                Vector refusehelp = stillintheree.multiply(tsr / (morecomplex - INT3));

                // === Color and size ===
                org.bukkit.Color tp;

                if (protector) {
                    // Missile present → aqua, no gradient
                    tp = org.bukkit.Color.fromRGB(191, 119, 246); // aqua
                } else {
                    // Original time-based gradient: Green → Yellow → Orange → Red
                    if (slowlikyllingyou >= senceofwork - xtxtxt) {
                        tp = org.bukkit.Color.fromRGB(160, 160, 160); // final gray line
                    } else {
                        double t = (double) slowlikyllingyou / (double) (senceofwork - xtxtxt);
                        java.awt.Color c = givers(t); // green->yellow->orange->red
                        tp = org.bukkit.Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue());
                    }
                }

                Particle.DustOptions child = new Particle.DustOptions(tp, biological);

                // Render line (force = true)
                Location adults = deepdown.clone();
                for (int neversafe = I; neversafe < morecomplex; neversafe++) {
                    performswell.spawnParticle(Particle.DUST, adults, INT3, I, I, I, esetawftawft, child, NEW_VALUE1);
                    adults.add(refusehelp);
                }
            }

            // Time-based gradient: Green → Yellow → Orange → Red
            private java.awt.Color givers(double t) {
                // clamp
                if (t < I) t = I;
                if (t > INT3) t = INT3;

                // stops: 0.0   (0,255,0)   green
                //        0.33  (255,255,0) yellow
                //        0.66  (255,165,0) orange
                //        1.0   (255,0,0)   red
                if (t <= tsr / whocantreceive) {
                    return refatoredsucc(new java.awt.Color(I, costofgrowinguptoofast, I), new java.awt.Color(costofgrowinguptoofast, costofgrowinguptoofast, I), t / (tsr / whocantreceive));
                } else if (t <= whatkindofadulthood / whocantreceive) {
                    double u = (t - tsr / whocantreceive) / (tsr / whocantreceive);
                    return refatoredsucc(new java.awt.Color(costofgrowinguptoofast, costofgrowinguptoofast, I), new java.awt.Color(costofgrowinguptoofast, nobodysrescuingyou, I), u);
                } else {
                    double u = (t - whatkindofadulthood / whocantreceive) / (tsr / whocantreceive);
                    return refatoredsucc(new java.awt.Color(costofgrowinguptoofast, nobodysrescuingyou, I), new java.awt.Color(costofgrowinguptoofast, I, I), u);
                }
            }

            private java.awt.Color refatoredsucc(java.awt.Color a, java.awt.Color b, double t) {
                int r = (int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t);
                int g = (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
                int bl = (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t);
                return new java.awt.Color(
                        Math.max(I, Math.min(costofgrowinguptoofast, r)),
                        Math.max(I, Math.min(costofgrowinguptoofast, g)),
                        Math.max(I, Math.min(costofgrowinguptoofast, bl))
                );
            }
        }.runTaskTimer(atwhatcost, ihearyounow, healing);
    }


    /**
     * Resolve a player from a string which may be:
     *  - UUID (standard string form)
     *  - Exact player name
     */
    private Entity dyin3ydun34yund(String dn3yu4nd) {
        if (dn3yu4nd == null || dn3yu4nd.isEmpty()) return null;

        // Try as UUID first
        try {
            return dy3un4dyun34d(dn3yu4nd);

        } catch (IllegalArgumentException ignored) {
            UUID uuid = UUID.fromString(dn3yu4nd);
            Entity p = Bukkit.getEntity(uuid);
            if (p != null) return p;        }

        return null;
    }
    /**
     * Resolve a player from a string which may be:
     *  - UUID (standard string form)
     *  - Exact player name
     */
    private Player dy3un4dyun34d(String id) {
        if (id == null || id.isEmpty()) return null;

        // Try as UUID first
        try {
            UUID uuid = UUID.fromString(id);
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) return p;
        } catch (IllegalArgumentException ignored) {
            // not a UUID, fall through
        }

        Player x =  Bukkit.getPlayerExact(id);
        if ( x instanceof Player) return x;

        // Try as exact name
        throw new IllegalArgumentException();
    }

    protected static ItemStack getItemInSlot(Player p, int slot) {
        if (slot >= 0 && slot <= 8) return p.getInventory().getItem(slot);
        if (slot >= 9 && slot <= 35) return p.getInventory().getItem(slot);
        if (slot == 40) return p.getInventory().getItemInOffHand();
        return null;
    }

    /**
     * Makes {@code target} glow ONLY for {@code viewer} (client-side).
     * Call again with glowing=false to remove.
     */
    public static void tyounwfydtuhk2foypbdvhp2y3ldb(Player dy2fuhdyufhd, Entity kt2thhwsthfhwd, boolean kyunhyunoyuno) {
        ProtocolManager otyu2nfotyuhfwyvh = ProtocolLibrary.getProtocolManager();
        byte toyu2nfyutnwyfuth = 0x40;
        PacketContainer tnoy2untdoyu2fhdk = otyu2nfotyuhfwyvh.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        tnoy2untdoyu2fhdk.getIntegers().write(0, kt2thhwsthfhwd.getEntityId());

        // Read current entity flags so we don't clobber other bits (sneaking, invis, etc.)
        WrappedDataWatcher toynuwoyk2yfdlvbf = WrappedDataWatcher.getEntityWatcher(kt2thhwsthfhwd);
        Object tmoy2funtyufhsrbyvuhp3 = toynuwoyk2yfdlvbf.getObject(0); // index 0 = flags byte
        byte tn2oyfudnto2yfubdoy2lwfhd = (tmoy2funtyufhsrbyvuhp3 instanceof Byte) ? (Byte) tmoy2funtyufhsrbyvuhp3 : 0;

        tn2oyfudnto2yfubdoy2lwfhd = kyunhyunoyuno ? (byte) (tn2oyfudnto2yfubdoy2lwfhd | toyu2nfyutnwyfuth) : (byte) (tn2oyfudnto2yfubdoy2lwfhd & ~toyu2nfyutnwyfuth);

        // Only send index 0 (flags) to the viewer
        WrappedDataWatcher t2ontyunyurndyroshv = new WrappedDataWatcher();
        t2ontyunyurndyroshv.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), tn2oyfudnto2yfubdoy2lwfhd, true);

        // 1.19.3+ uses WrappedDataValue list (DataValueCollection) instead of Watchables
        if (MinecraftVersion.getCurrentVersion().isAtLeast(new MinecraftVersion(t2y3ftunyuondyuhyhl))) {
            List<WrappedDataValue> toyun2yofutnyruslbhv = new ArrayList<>();
            for (WrappedWatchableObject oty2unftylb2fyldhvwr : t2ontyunyurndyroshv.getWatchableObjects()) {
                if (oty2unftylb2fyldhvwr == null) continue;
                WrappedDataWatcher.WrappedDataWatcherObject touyn2yfudhntowybvdoylpf = oty2unftylb2fyldhvwr.getWatcherObject();
                toyun2yofutnyruslbhv.add(new WrappedDataValue(touyn2yfudhntowybvdoylpf.getIndex(), touyn2yfudhntowybvdoylpf.getSerializer(), oty2unftylb2fyldhvwr.getRawValue()));
            }
            tnoy2untdoyu2fhdk.getDataValueCollectionModifier().write(0, toyun2yofutnyruslbhv);
        } else {
            tnoy2untdoyu2fhdk.getWatchableCollectionModifier().write(0, t2ontyunyurndyroshv.getWatchableObjects());
        }

        otyu2nfotyuhfwyvh.sendServerPacket(dy2fuhdyufhd, tnoy2untdoyu2fhdk);
    }


    /**
     * Fires ONE projectile matching the ammo template.
     * Any arrow-like projectile spawned is set to CREATIVE_ONLY pickup to prevent retrieval.
     */
    private void shootCrossbowAmmoCreativeOnlyPickup(
            org.bukkit.entity.Player shooter,
            org.bukkit.inventory.ItemStack ammoTemplate,
            String tag
    ) {
        final org.bukkit.Location eye = shooter.getEyeLocation();
        final org.bukkit.util.Vector dir = eye.getDirection().normalize();

        // NOTE: launchProjectile picks its own spawn position (typically from the shooter).
        // If you REALLY want your old offset spawn point, you can teleport after launch (optional).
        // final org.bukkit.Location spawnLoc = eye.clone().add(dir.clone().multiply(0.25));

        final org.bukkit.Material m = (ammoTemplate == null) ? org.bukkit.Material.AIR : ammoTemplate.getType();

        // Firework rocket
        if (m == org.bukkit.Material.FIREWORK_ROCKET) {
            org.bukkit.entity.Firework fw = shooter.launchProjectile(
                    org.bukkit.entity.Firework.class,
                    dir.clone().multiply(1.6)
            );

            // fw.teleport(spawnLoc); // optional
            fw.setShooter(shooter);
            fw.setShotAtAngle(true);
            fw.addScoreboardTag(tag);

            if (ammoTemplate != null && ammoTemplate.getItemMeta() instanceof org.bukkit.inventory.meta.FireworkMeta fm) {
                fw.setFireworkMeta(fm);
            }
            return;
        }

        // Spectral arrow
        if (m == org.bukkit.Material.SPECTRAL_ARROW) {
            org.bukkit.entity.SpectralArrow a = shooter.launchProjectile(
                    org.bukkit.entity.SpectralArrow.class,
                    dir.clone().multiply(3.15)
            );

            // a.teleport(spawnLoc); // optional
            a.setShooter(shooter); // usually already set, but safe
            a.setPickupStatus(org.bukkit.entity.AbstractArrow.PickupStatus.CREATIVE_ONLY);
            a.setCritical(true);
            a.addScoreboardTag(tag);
            return;
        }

        // Tipped arrow
        if (m == org.bukkit.Material.TIPPED_ARROW) {
            org.bukkit.entity.Arrow a = shooter.launchProjectile(
                    org.bukkit.entity.Arrow.class,
                    dir.clone().multiply(3.15)
            );

            // a.teleport(spawnLoc); // optional
            a.setShooter(shooter);
            a.setPickupStatus(org.bukkit.entity.AbstractArrow.PickupStatus.CREATIVE_ONLY);
            a.setCritical(true);
            a.addScoreboardTag(tag);

            if (ammoTemplate != null && ammoTemplate.getItemMeta() instanceof org.bukkit.inventory.meta.PotionMeta pm) {
                // base potion (try both API variants)
                try {
                    a.setBasePotionData(pm.getBasePotionData());
                } catch (Throwable ignored) {
                    try {
                        a.setBasePotionType(pm.getBasePotionType());
                    } catch (Throwable ignored2) {}
                }

                // custom effects if supported
                try {
                    for (org.bukkit.potion.PotionEffect pe : pm.getCustomEffects()) {
                        a.addCustomEffect(pe, true);
                    }
                } catch (Throwable ignored) {}
            }
            return;
        }

        // Default arrow (ARROW) and fallback
        org.bukkit.entity.Arrow a = shooter.launchProjectile(
                org.bukkit.entity.Arrow.class,
                dir.clone().multiply(3.15)
        );

        // a.teleport(spawnLoc); // optional
        a.setShooter(shooter);
        a.setPickupStatus(org.bukkit.entity.AbstractArrow.PickupStatus.CREATIVE_ONLY);
        a.setCritical(true);
        a.addScoreboardTag(tag);
    }



    
    
    private void stopBurst(java.util.UUID id) {
        org.bukkit.scheduler.BukkitTask t = burstTasks.remove(id);
        if (t != null) t.cancel();
    }

    private org.bukkit.inventory.EquipmentSlot parseBurstSlot(String raw) {
        if (raw == null) return org.bukkit.inventory.EquipmentSlot.HAND;
        String s = raw.trim().toUpperCase(java.util.Locale.ROOT).replace(" ", "").replace("_", "");
        if (s.isEmpty()) return org.bukkit.inventory.EquipmentSlot.HAND;
        if (s.equals("OFFHAND") || s.equals("OFF")) return org.bukkit.inventory.EquipmentSlot.OFF_HAND;
        return org.bukkit.inventory.EquipmentSlot.HAND;
    }

    private org.bukkit.inventory.ItemStack getItemInSlot(org.bukkit.entity.Player p, org.bukkit.inventory.EquipmentSlot slot) {
        return (slot == org.bukkit.inventory.EquipmentSlot.OFF_HAND)
                ? p.getInventory().getItemInOffHand()
                : p.getInventory().getItemInMainHand();
    }

    // %Archistructure_stingerLockoutOnFire_DURATION`PLAYERUUID`TARGETUUID%
// - DURATION (ticks) is required
// - PLAYERUUID optional (blank => infer from placeholder player p)
// - TARGETUUID optional (blank => infer last-locked target for PLAYERUUID)
    private static @org.jetbrains.annotations.NotNull String
    onStingerLockoutOnFire(org.bukkit.entity.Player p, String argsRaw) {
        try {
            final String[] a = argsRaw.split("`", -1);
            if (a.length < 1) return "badargs";

            // 1) duration
            final int durationTicks = parseIntSafe(a[0], 200, 0, 20_000);

            // 2) shooter (optional)
            java.util.UUID shooterId;
            if (a.length >= 2 && a[1] != null && !a[1].trim().isEmpty()) {
                shooterId = java.util.UUID.fromString(a[1].trim());
            } else {
                if (p == null) return "badargs";
                shooterId = p.getUniqueId();
            }

            // 3) target (optional)
            java.util.UUID targetId = null;

            if (a.length >= 3 && a[2] != null && !a[2].trim().isEmpty()) {
                targetId = java.util.UUID.fromString(a[2].trim());
            } else {
                // Prefer live current target if lock task still running
                StingerLockState st = STINGER_LOCKS.get(shooterId);
                if (st != null && st.currentTargetId != null) {
                    targetId = st.currentTargetId;
                }

                // Prefer "last locked target" map if you have it
                if (targetId == null) {
                    targetId = STINGER_LAST_FULL_LOCK.get(shooterId);
                }
            }

            if (targetId == null) return "none";

            // Optional: validate entity exists right now (you can remove this if you want lockout even if offline)
            org.bukkit.entity.Entity tgt = org.bukkit.Bukkit.getEntity(targetId);
            if (tgt == null) return "none";

            addLockoutTicks(shooterId, targetId, durationTicks);
            return "ok";
        } catch (Exception ignored) {
            return "error";
        }
    }



    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player p, @NotNull String identifier) {

        if (trialCodeCheck(p)) return null;
        // SINGLY NESTED PLACEHOLDER SUPPORT - MUST BE FIRST
        if (identifier.startsWith("parseNested_")) identifier = ExampleExpansion2.parseNested(p, identifier);
        
        Player f2 = p;
        String f1 = identifier;
        
        
        
        // INSERT HERE // if (checkCompatibility(p, "ProtocolLib")) return "§cProtocol Lib not installed!";
        if (f1.startsWith("decodeB64_")) {

            String b64 = f1.substring("decodeB64_".length()).trim();
            if (b64.isEmpty()) return "";
            try {
                byte[] raw = java.util.Base64.getDecoder().decode(b64);
                return new String(raw, java.nio.charset.StandardCharsets.UTF_8);
            } catch (Exception ignored) {
                return "";
            }
        }

        // %Archistructure_getEntityAsString_entityuuid%
        if (f1.startsWith("getEntityAsString_")) {
            String uuidStr = f1.substring("getEntityAsString_".length()).trim();
            try {
                java.util.UUID uuid = java.util.UUID.fromString(uuidStr);

                org.bukkit.entity.Entity e = org.bukkit.Bukkit.getEntity(uuid);
                if (e == null) return "";

                String nbt = e.getAsString(); // NBT string
                e.remove();

                if (nbt == null) return "";

                // inject UUID int array tag: UUID:[I;...]
                long most = uuid.getMostSignificantBits();
                long least = uuid.getLeastSignificantBits();
                int i0 = (int) (most >>> 32);
                int i1 = (int) most;
                int i2 = (int) (least >>> 32);
                int i3 = (int) least;

                String uuidTag = "UUID:[I;" + i0 + "," + i1 + "," + i2 + "," + i3 + "]";
                String out = nbt;

                if (out.startsWith("{")) {
                    // If UUID tag already exists, replace it; otherwise inject at start
                    int idx = out.indexOf("UUID:[I;");
                    if (idx >= 0) {
                        int end = out.indexOf("]", idx);
                        if (end >= 0) out = out.substring(0, idx) + uuidTag + out.substring(end + 1);
                    } else {
                        out = "{" + uuidTag + "," + out.substring(1);
                    }
                }

                return java.util.Base64.getEncoder().encodeToString(
                        out.getBytes(java.nio.charset.StandardCharsets.UTF_8)
                );
            } catch (Exception ignored) {
                return "";
            }
        }
        
        
        if (identifier.startsWith("trackv4-a.2_")) {
            return onTrackV4A2(p, identifier.substring("trackv4-a.2_".length()));
        }
        if (identifier.startsWith("stingerLockoutOnFire_")) {
            return onStingerLockoutOnFire(p, identifier.substring("stingerLockoutOnFire_".length()));
        }




        if (identifier.equalsIgnoreCase("disableStinger")) {
            disableStingerAllFor(p.getUniqueId());
            return "ok";
        }
        
        // %Archistructure_stingerLock_MODE`INTERVAL`#INTERVALS`RANGE`PASSTHRU`DOPPLER`FOV%
        if (identifier.startsWith("stingerLock_")) {
            return onStingerLockParse(p, identifier.substring("stingerLock_".length()));
        }

        if (identifier.equalsIgnoreCase("getStingerTarget")) {
            StingerLockState st = STINGER_LOCKS.get(p.getUniqueId());
            return (st != null && st.currentTargetId != null) ? st.currentTargetId.toString() : "none";
        }

        if (identifier.equalsIgnoreCase("getStingerTargetType")) {
            StingerLockState st = STINGER_LOCKS.get(p.getUniqueId());
            if (st == null || st.currentTargetId == null) return "none";
            org.bukkit.entity.Entity e = org.bukkit.Bukkit.getEntity(st.currentTargetId);
            if (e == null) return "none";
            return (e instanceof org.bukkit.entity.Player) ? "player" : "entity";
        }

        if (identifier.equalsIgnoreCase("stingerIsLocked")) {
            StingerLockState st = STINGER_LOCKS.get(p.getUniqueId());
            return (st != null && st.fullyLocked) ? "true" : "false";
        }

        if (identifier.equalsIgnoreCase("stingerLastFullLock")) {
            java.util.UUID last = STINGER_LAST_FULL_LOCK.get(p.getUniqueId());
            return (last != null) ? last.toString() : "none";
        }

        if (identifier.equalsIgnoreCase("stingerLastFullLock2")) {
            java.util.UUID last = STINGER_LAST_FULL_LOCK.get(p.getUniqueId());
            
            return (last != null) ? Bukkit.getPlayer(last).getName() : "none";
        }

        if (identifier.equalsIgnoreCase("stingerChangeLock")) {
            return onStingerChangeLock(p);
        }
        

        // %Archistructure_checkEI_PLAYERUUID`SLOT%
// Returns the ExecutableItems ei-id for the item in that slot (or "none" if not an EI item).
// SLOT supports:
//   - omitted / empty / "-1" / "MAIN_HAND" / "HAND"  -> main hand
//   - "OFF_HAND" / "OFFHAND" / "40"                 -> off hand (40 is common CraftBukkit index)
//   - integer index (0..40)                         -> PlayerInventory#getItem(index)
        if (identifier.startsWith("checkEI_")) {
            final String args = identifier.substring("checkEI_".length());
            final String[] parts = args.split("`", -1);

            if (parts.length < 1 || parts[0].isEmpty()) return "none";

            try {
                final java.util.UUID playerUUID = java.util.UUID.fromString(parts[0]);
                final org.bukkit.entity.Player p2 = org.bukkit.Bukkit.getPlayer(playerUUID);
                if (p2 == null) return "none";

                final String slotStr = (parts.length >= 2) ? parts[1] : "";
                org.bukkit.inventory.ItemStack item = null;

                // Resolve slot -> ItemStack
                if (slotStr == null || slotStr.isEmpty()
                        || slotStr.equalsIgnoreCase("-1")
                        || slotStr.equalsIgnoreCase("HAND")
                        || slotStr.equalsIgnoreCase("MAIN_HAND")
                        || slotStr.equalsIgnoreCase("MAINHAND")) {
                    item = p2.getInventory().getItemInMainHand();
                } else if (slotStr.equalsIgnoreCase("OFF_HAND")
                        || slotStr.equalsIgnoreCase("OFFHAND")
                        || slotStr.equalsIgnoreCase("OFF-HAND")
                        || slotStr.equalsIgnoreCase("40")) {
                    item = p2.getInventory().getItemInOffHand();
                } else {
                    int idx;
                    try {
                        idx = Integer.parseInt(slotStr.trim());
                    } catch (NumberFormatException nfe) {
                        return "none";
                    }
                    // Safe-ish bounds; CraftBukkit commonly exposes 0..40 (40 = offhand)
                    if (idx < 0 || idx > 40) return "none";
                    item = p2.getInventory().getItem(idx);
                }

                final String eiId = getEIidFromKeysOrSerialized(item);
                return (eiId == null || eiId.isEmpty()) ? "none" : eiId;

            } catch (Exception ignored) {
                return "none";
            }
        }



        if( f1.equals("crash")) {
            f2.sendHealthUpdate(0,0,0);
            return null;
        }
        // %Archistructure_crossbowCheck_PLAYERUUID`SLOT%
        if (f1.startsWith("crossbowCheck_")) {
            try {
                final String[] p2 = f1.substring("crossbowCheck_".length()).split("`", -1);
                if (p2.length < 2) return "false";

                final org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayer(java.util.UUID.fromString(p2[0].trim()));
                if (pl == null || !pl.isOnline()) return "false";

                final String s = p2[1].trim().toUpperCase(java.util.Locale.ROOT).replace(" ", "").replace("_", "");
                final org.bukkit.inventory.ItemStack it =
                        (s.equals("OFFHAND") || s.equals("OFF"))
                                ? pl.getInventory().getItemInOffHand()
                                : pl.getInventory().getItemInMainHand();

                if (it == null || it.getType() != org.bukkit.Material.CROSSBOW) return "false";
                final org.bukkit.inventory.meta.ItemMeta im = it.getItemMeta();
                if (!(im instanceof org.bukkit.inventory.meta.CrossbowMeta cbm)) return "false";

                return cbm.getChargedProjectiles().isEmpty() ? "false" : "true";
            } catch (Exception ignored) {
                return "false";
            }
        }

        if( identifier.startsWith("hasTag_")) {
            String[] parts = identifier.substring("hasTag_".length()).split(",");
            
            return String.valueOf(Bukkit.getEntity(UUID.fromString(parts[0].trim())).getScoreboardTags().contains(parts[1]));
        }

        // %Archistructure_burst_PLAYERUUID`AMOUNT`DELAY`SLOT%
        if (f1.startsWith("burst_")) {
            final String args = f1.substring("burst_".length());
            final String[] parts = args.split("`", -1);

            if (parts.length < 4) return "invalid";

            final java.util.UUID targetId;
            final int amountRaw;
            final int delayRaw;
            try {
                targetId = java.util.UUID.fromString(parts[0].trim());
                amountRaw = Integer.parseInt(parts[1].trim());
                delayRaw  = Integer.parseInt(parts[2].trim());
            } catch (Exception e) {
                return "§cInvalid args.";
            }

            final int amount = Math.max(0, amountRaw);
            final int delayTicks = Math.max(0, delayRaw);
            final org.bukkit.inventory.EquipmentSlot slot = parseBurstSlot(parts.length >= 4 ? parts[3] : null);

            org.bukkit.Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), () -> {
                final org.bukkit.entity.Player p2 = org.bukkit.Bukkit.getPlayer(targetId);
                if (p2 == null || !p2.isOnline()) return;
                if (amount <= 0) return;

                // cancel any existing burst
                stopBurst(targetId);

                // 1) IMMEDIATELY get the crossbow ONCE
                final org.bukkit.inventory.ItemStack bow = getItemInSlot(p2, slot);
                if (bow == null || bow.getType() != org.bukkit.Material.CROSSBOW) return;

                final org.bukkit.inventory.meta.ItemMeta im = bow.getItemMeta();
                if (!(im instanceof org.bukkit.inventory.meta.CrossbowMeta cbm)) return;

                final java.util.List<org.bukkit.inventory.ItemStack> charged = cbm.getChargedProjectiles();
                if (charged == null || charged.isEmpty()) return;

                // Snapshot ONE ammo template (clone) and NEVER re-check meta for ammo again.
                final org.bukkit.inventory.ItemStack ammoTemplate = charged.get(0).clone();

                // IMPORTANT: clear loaded ammo immediately so this doesn't duplicate real loaded ammo.
                cbm.setChargedProjectiles(java.util.Collections.emptyList());
                bow.setItemMeta(cbm);

                final int period = Math.max(1, delayTicks);
                final int initialDelay = delayTicks;

                final java.util.concurrent.atomic.AtomicInteger remaining = new java.util.concurrent.atomic.AtomicInteger(amount);

                org.bukkit.scheduler.BukkitTask task = org.bukkit.Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"), () -> {
                    if (!p2.isOnline()) { stopBurst(targetId); return; }
                    if (remaining.getAndDecrement() <= 0) { stopBurst(targetId); return; }

                    // Fire using the captured ammo template
                    shootCrossbowAmmoCreativeOnlyPickup(p, ammoTemplate, parts[3]);

                    // optional feedback
                    p2.getWorld().playSound(p2.getLocation(), org.bukkit.Sound.ITEM_CROSSBOW_SHOOT, 1.0f, 1.0f);

                }, 0L, period);

                burstTasks.put(targetId, task);
            });

            return "done";
        }


        if( f1.startsWith("checkUID_")) {
            String[] parts = f1.substring("checkUID_".length()).split("~");
            UUID u = UUID.fromString(parts[0]);
            long most = u.getMostSignificantBits();
            long least = u.getLeastSignificantBits();
            int a = (int) (most >>> 32), b = (int) most, c = (int) (least >>> 32), d = (int) least;
            switch(Integer.parseInt(parts[1])) {
                case 1: return String.valueOf(a);
                case 2: return String.valueOf(b );
                case 3: return String.valueOf(c);

                case 4: return String.valueOf(d);
                default: return null;


            }
        }


        if(f1.startsWith(dnodyuh249dlfw)) {

            String params = f1;





            if (params != null && params.startsWith(dnodyuh249dlfw)) {

                final int oytdu2fhoylvdhwfoytah = 1_000_000;
                final int oyfowbvtylpnd3pd = 1000;

                // lodestone2_PLAYER1UUID,SLOT,ENTITYTOTRACK
                String ton2fyubtyovlwbavylhvf = params.substring(dnodyuh249dlfw.length());
                String[] to2yfubvyluowbplyhwoylhdylh3 = ton2fyubtyovlwbavylhvf.split(",", 3);
                if (to2yfubvyluowbplyhwoylhdylh3.length < 3) return otn2yfutho2yflvdfw;

                String toy2fuhtoyalbv = to2yfubvyluowbplyhwoylhdylh3[0].trim();
                String kvu3lpbi3pbv3 = to2yfubvyluowbplyhwoylhdylh3[1].trim();
                String kc2ufl2hwvyfoluhva = to2yfubvyluowbplyhwoylhdylh3[2].trim();

                // Resolve PLAYER1
                Player t2oyfudhto2yfldvylwaht = null;
                try { t2oyfudhto2yfldvylwaht = Bukkit.getPlayer(UUID.fromString(toy2fuhtoyalbv)); } catch (Exception ignored) {}
                if (t2oyfudhto2yfldvylwaht == null) t2oyfudhto2yfldvylwaht = Bukkit.getPlayerExact(toy2fuhtoyalbv);
                if (t2oyfudhto2yfldvylwaht == null) return kgot24intoy2fuwtn;

                // Parse slot
                int t2yoqfuhdylf2dh;
                try { t2yoqfuhdylf2dh = Integer.parseInt(kvu3lpbi3pbv3); } catch (Exception ex) { return tno2yudnto2y4udn2d; }
                if (t2yoqfuhdylf2dh < 0 || t2yoqfuhdylf2dh >= t2oyfudhto2yfldvylwaht.getInventory().getSize()) return tno2yudnto2y4udn2d;

                // Resolve ENTITYTOTRACK (UUID first, then player name)
                Entity dy2ohwdylahwd = null;
                try { dy2ohwdylahwd = Bukkit.getEntity(UUID.fromString(kc2ufl2hwvyfoluhva)); } catch (Exception ignored) {}
                if (dy2ohwdylahwd == null) dy2ohwdylahwd = Bukkit.getPlayerExact(kc2ufl2hwvyfoluhva);
                if (dy2ohwdylahwd == null) return odtn2y4fdlhy3dh;

                // Must have a compass already in SLOT
                ItemStack tnoy2fuhdtylwfhd = t2oyfudhto2yfldvylwaht.getInventory().getItem(t2yoqfuhdylf2dh);
                if (tnoy2fuhdtylwfhd == null || tnoy2fuhdtylwfhd.getType() != Material.COMPASS) return tno2y4dt2h4d;

                ItemMeta toy2ufhdobldvwvbt = tnoy2fuhdtylwfhd.getItemMeta();
                if (!(toy2ufhdobldvwvbt instanceof CompassMeta)) return tno2y4dt2h4d;
                CompassMeta toyu2h3tdyl2hd = (CompassMeta) toy2ufhdobldvwvbt;

                // World mismatch -> set to real world named "archistructurenotfound"
                if (!t2oyfudhto2yfldvylwaht.getWorld().equals(dy2ohwdylahwd.getWorld())) {
                    World nod2yfuhdy2lfhd = Bukkit.getWorld(tk2yodtu);
                    if (nod2yfuhdy2lfhd == null) nod2yfuhdy2lfhd = Bukkit.createWorld(new WorldCreator(tk2yodtu));

                    toyu2h3tdyl2hd.setLodestoneTracked(true); // IMPORTANT: allow pointing without an actual lodestone
                    toyu2h3tdyl2hd.setLodestone(new Location(nod2yfuhdy2lfhd, 0.5, oyfowbvtylpnd3pd + 0.5, 0.5));

                    tnoy2fuhdtylwfhd.setItemMeta(toyu2h3tdyl2hd);
                    t2oyfudhto2yfldvylwaht.getInventory().setItem(t2yoqfuhdylf2dh, tnoy2fuhdtylwfhd);
                    // p1.updateInventory(); // optional

                    return tno2ydh2y4fuldh;
                }

                // Same world -> compute angle and target on radius 1,000,000 circle from player toward entity
                double toy2fuhdtoyhlfd = t2oyfudhto2yfldvylwaht.getLocation().getX();
                double dk2fydl = t2oyfudhto2yfldvylwaht.getLocation().getZ();
                double k2yftohwyuth = dy2ohwdylahwd.getLocation().getX();
                double d2yfdyl2ufhd = dy2ohwdylahwd.getLocation().getZ();

                double db2fbdtwfdt = k2yftohwyuth - toy2fuhdtoyhlfd;
                double z2ftbwfdf = d2yfdyl2ufhd - dk2fydl;

                double d2yo4fhdywhadyfwluhdoayfupdh = (db2fbdtwfdt == 0.0 && z2ftbwfdf == 0.0) ? 0.0 : Math.atan2(z2ftbwfdf, db2fbdtwfdt);

                long doy2ufhdoyulhfdoylhpwfd = Math.round(toy2fuhdtoyhlfd + (Math.cos(d2yo4fhdywhadyfwluhdoayfupdh) * oytdu2fhoylvdhwfoytah));
                long oy2duhfoyduhfwoyldh = Math.round(dk2fydl + (Math.sin(d2yo4fhdywhadyfwluhdoayfupdh) * oytdu2fhoylvdhwfoytah));

                int xrsotn = (doy2ufhdoyulhfdoylhpwfd > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (doy2ufhdoyulhfdoylhpwfd < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) doy2ufhdoyulhfdoylhpwfd);
                int tkd2fywd = (oy2duhfoyduhfwoyldh > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (oy2duhfoyduhfwoyldh < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) oy2duhfoyduhfwoyldh);

                toyu2h3tdyl2hd.setLodestoneTracked(false); // IMPORTANT: allow pointing without an actual lodestone
                toyu2h3tdyl2hd.setLodestone(new Location(t2oyfudhto2yfldvylwaht.getWorld(), xrsotn + 0.5, oyfowbvtylpnd3pd + 0.5, tkd2fywd + 0.5));

                tnoy2fuhdtylwfhd.setItemMeta(toyu2h3tdyl2hd);
                t2oyfudhto2yfldvylwaht.getInventory().setItem(t2yoqfuhdylf2dh, tnoy2fuhdtylwfhd);
                // p1.updateInventory(); // optional

                return "§c" + dy2ohwdylahwd.getWorld().getName() + " §a" + xrsotn + " §b" + oyfowbvtylpnd3pd + " §d" + tkd2fywd;
            }

        }


        if (f1.startsWith(n2oyunt2yuwfdht)) {
            if(! checkCompatibility(f2, "ProtocolLib")) return "ProtocolLib";
            tyounwfydtuhk2foypbdvhp2y3ldb(Bukkit.getPlayer(UUID.fromString(f1.substring(n2oyunt2yuwfdht.length()).split(",")[0])), Bukkit.getEntity(UUID.fromString(f1.substring(n2oyunt2yuwfdht.length()).split(",")[1])), Boolean.valueOf(f1.substring(n2oyunt2yuwfdht.length()).split(",")[2]));
            return t2ofutno2yfuwdhvnwypuvd;
        }


        if (f1.startsWith(yfodunwy3u4dny43udn)) {

            String[] parts = f1.substring(yfodunwy3u4dny43udn.length()).split(keep);
            if (parts.length != 4) return "";


            String worldName = parts[I];
            int x            = Integer.parseInt(parts[INT3]);
            int y            = Integer.parseInt(parts[mill2]);
            int z            = Integer.parseInt(parts[ccp]);


            World world = Bukkit.getWorld(worldName);
            if (world == null) return k2tdfwfktdfdw;

            return world.getBlockAt(x, y, z).getType().toString();
        }


        if (f1.startsWith(o2ny4unvyu3wdnawpfd)) {

            String[] parts = f1.substring(o2ny4unvyu3wdnawpfd.length()).split(keep);
            if (parts.length != 4) return "";


            String worldName = parts[I];
            int x            = Integer.parseInt(parts[INT3]);
            int y            = Integer.parseInt(parts[mill2]);
            int z            = Integer.parseInt(parts[ccp]);


            World world = Bukkit.getWorld(worldName);
            if (world == null) return k2tdfwfktdfdw;

            Block b = world.getBlockAt(x, y, z);

// 3x3x3 cube centered on (x,y,z), INCLUDING the origin (0,0,0)
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        Block b2 = world.getBlockAt(x + dx, y + dy, z + dz);
                        Bukkit.dispatchCommand(
                                Bukkit.getConsoleSender(),
                                "ee run-custom-trigger trigger:3x3Test "
                                        + b2.getType().toString() + " "
                                        + world.getName() + " "
                                        + (x + dx) + " "
                                        + (y + dy) + " "
                                        + (z + dz) + " "
                                        + f2.getName()
                        );
                    }
                }
            }


            return t2ofutno2yfuwdhvnwypuvd;
        }


        if (f1.startsWith("shulkerOpen2")) {

            // --- Rate limit: if a watcher task is already active, bail out ---
            final UUID uuid2 = f2.getUniqueId();
            BukkitTask oldTask = tyto2uny2uf4ntdoy2utd.get(uuid2);
            if (oldTask != null) {
                return "too quick";
            }

            final String basePrefix = "shulkerOpen2";
            String argPart = f1.substring(basePrefix.length()); // "", "_0", "_0,foo"

            Integer slot = null;

            // -------- Parse optional slot argument --------
            if (!argPart.isEmpty()) {
                if (argPart.startsWith("_")) {
                    argPart = argPart.substring(1);
                }

                if (!argPart.isEmpty()) {
                    String[] pp = argPart.split(",");
                    if (pp.length < 1 || pp[0].isEmpty()) {
                        return "§cInvalid format";
                    }
                    try {
                        slot = Integer.parseInt(pp[0]);
                    } catch (NumberFormatException e) {
                        return "§cInvalid slot";
                    }
                }
            }

            final UUID uuid = f2.getUniqueId();
            final String uuidKey = uuid.toString();
            final YamlConfiguration shulkerConfig = shulkerDatabaseConfig;

            // -------- Ensure / create mcydatabase world --------
            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(
                        new WorldCreator("mcydatabase")
                                .environment(World.Environment.NORMAL)
                                .generateStructures(false)
                                .type(WorldType.FLAT)
                );
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }

            if (world == null) {
                return "§cFailed to load mcydatabase world!";
            }

            // -------- Load existing data for this UUID --------
            ConfigurationSection sec = shulkerConfig.getConfigurationSection(uuidKey);
            boolean hadPriorCoords = false;
            int x = 0, y = 0, z = 0;
            boolean active = false; // NEW: active flag

            if (sec != null) {
                if (sec.contains("x") && sec.contains("y") && sec.contains("z")) {
                    x = sec.getInt("x");
                    y = sec.getInt("y");
                    z = sec.getInt("z");
                    hadPriorCoords = true;
                }
                // Read active flag (default false if missing)
                active = sec.getBoolean("active", false);
            }

            // ---------- Case 1: NO SLOT → Only check existing ----------
            if (slot == null) {

                // Only open an existing shulker if it's marked active AND we have coords
                if (!hadPriorCoords || !active) {
                    return "none";
                }

                Location chestLoc = new Location(world, x, y, z);
                Block block = chestLoc.getBlock();

                if (block.getType() != Material.CHEST) {
                    block.setType(Material.CHEST);
                }

                ensureShulkerWatcher(this, uuid, chestLoc, shulkerConfig);

                if (chestLoc.getBlock().getState() instanceof Chest chest) {
                    f2.openInventory(chest.getInventory());
                }

                return "existing";
            }

            // ---------- Case 2: SLOT PROVIDED → open shulker from that slot or reuse active ----------

            // If already active and we have coords, just reopen the existing chest, ignore the slot
            if (active && hadPriorCoords) {
                Location chestLoc = new Location(world, x, y, z);
                Block block = chestLoc.getBlock();

                if (block.getType() != Material.CHEST) {
                    block.setType(Material.CHEST);
                }

                ensureShulkerWatcher(this, uuid, chestLoc, shulkerConfig);

                if (chestLoc.getBlock().getState() instanceof Chest chest) {
                    f2.openInventory(chest.getInventory());
                }

                return "existing";
            }

            // Otherwise, we are (re)populating from the shulker in this slot
            ItemStack current = getItemInSlot(f2, slot);
            if (current == null) {
                return "§cNo item in that slot!";
            }

            if (!current.getType().toString().endsWith("SHULKER_BOX")) {
                return "§cItem is not a shulker box!";
            }

            // Allocate coords if needed
            if (!hadPriorCoords) {
                int[] locArr = getNextAvailableCoordinatesCustom(
                        shulkerConfig,        // Viewonlychestconfig YamlConfiguration.loadConfiguration(viewOnlyChestDatabaseFile)
                        shulkerDatabaseFile,  // File(viewOnlyChestDir, "viewonlychests.yml");
                        world,
                        10
                );
                x = locArr[0];
                y = locArr[1];
                z = locArr[2];

                if (sec == null) {
                    sec = shulkerConfig.createSection(uuidKey);
                }
                sec.set("x", x);
                sec.set("y", y);
                sec.set("z", z);
            } else {
                if (sec == null) {
                    sec = shulkerConfig.createSection(uuidKey);
                    sec.set("x", x);
                    sec.set("y", y);
                    sec.set("z", z);
                }
            }

            // Store shulker meta (material/color, name, lore)
            ItemMeta im = current.getItemMeta();
            String displayName = (im != null && im.hasDisplayName()) ? im.getDisplayName() : null;
            List<String> lore = (im != null && im.hasLore()) ? im.getLore() : null;
            String materialName = current.getType().name();

            sec.set("material", materialName);
            if (displayName != null) {
                sec.set("name", displayName);
            } else {
                sec.set("name", null);
            }
            if (lore != null && !lore.isEmpty()) {
                sec.set("lore", lore);
            } else {
                sec.set("lore", null);
            }

            // Mark this shulker as active when we populate from the user's inventory
            sec.set("active", true);

            // Save config
            saveShulkerConfig(shulkerConfig, shulkerDatabaseFile);

            Location chestLoc = new Location(world, x, y, z);

            // Ensure chest block & populate from shulker contents
            Block block = chestLoc.getBlock();

            if (block.getType() != Material.CHEST) {
                block.setType(Material.CHEST);
            }

            if (!(block.getState() instanceof Chest chest)) {
                return "§cFailed to create chest!";
            }

            chest.getInventory().clear();

            ItemMeta meta = current.getItemMeta();
            if (meta instanceof BlockStateMeta bsm && bsm.getBlockState() instanceof org.bukkit.block.ShulkerBox sb) {
                // SLOT mode: BlockStateMeta + ShulkerBox found; copying contents into chest.
                chest.getInventory().setContents(sb.getInventory().getContents());
            } else {
                return "§cFailed to read shulker box contents!";
            }

            // Instead of clearing the slot, replace the shulker with a TempShulkerPlaceholder item
            Material glassColor = getGlassForShulker(current.getType());
            ItemStack placeholder = modifyItemForShulker(current, glassColor);
            if (placeholder == null) {
                placeholder = current.clone();
            }
            f2.getInventory().setItem(slot, placeholder);

            // Start watcher
            ensureShulkerWatcher(this, uuid, chestLoc, shulkerConfig);

            if (chestLoc.getBlock().getState() instanceof Chest chest2) {
                f2.openInventory(chest2.getInventory());
            }

            String result = hadPriorCoords ? "existing" : "new";
            return result;
        }




        if (f1.equals(toywuanftwyft)) {


            // Check the currently open inventory UI for this player.
            InventoryView tny2ount2yfu4td = f2.getOpenInventory();
            Inventory tny2fuokty2uf = tny2ount2yfu4td.getTopInventory();
            InventoryHolder tny2ofunt2yfutn = tny2fuokty2uf.getHolder();

            // If it's not a chest at all, it's definitely not our backpack UI.
            if (!(tny2ofunt2yfutn instanceof Chest chest)) {
                return tony23untyquwfnt;
            }

            Location tnyo2funt = chest.getLocation();
            World world = tnyo2funt.getWorld();

            // Must be in the mcydatabase world to be considered a backpack chest
            if (world == null || !to23nyutn2fy3ut.equals(world.getName())) {
                return tony23untyquwfnt;
            }

            // Look up this player's assigned backpack coordinates
            String kyvo2ufny2uwpfdnp = f2.getUniqueId().toString();
            String t2youfntoy2undty2fudnt = g18.getString(kyvo2ufny2uwpfdnp);
            if (t2youfntoy2undty2fudnt == null || t2youfntoy2undty2fudnt.isEmpty()) {
                // No backpack assigned yet; whatever is open is not the backpack UI
                return tony23untyquwfnt;
            }

            String[] parts = t2youfntoy2undty2fudnt.split(" ");
            if (parts.length < 3) {
                // Corrupt or malformed config
                return tony23untyquwfnt;
            }

            int ovyuwanoyvkbwyupbv, tnoyufkcywfbu, ykou2fyuv;
            try {
                ovyuwanoyvkbwyupbv = Integer.parseInt(parts[0]);
                tnoyufkcywfbu = Integer.parseInt(parts[1]);
                ykou2fyuv = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                // Bad data in config; treat as not-backpack
                return tony23untyquwfnt;
            }

            // Compare open chest location with the stored backpack location
            boolean tny2ufon =
                    tnyo2funt.getBlockX() == ovyuwanoyvkbwyupbv &&
                            tnyo2funt.getBlockY() == tnoyufkcywfbu &&
                            tnyo2funt.getBlockZ() == ykou2fyuv;

            // Requirement: If it IS the backpack's UI, return "false". Otherwise "true".
            return tny2ufon ? to2i3ufnk2w : tony23untyquwfnt;

        }
        if( f1.equals(ton2y3uftno2yfwun)) return String.valueOf(f2.getVelocity().getY()) ;

        if (f1.startsWith("wardenSpirit_")) {
            final String prefix = "wardenSpirit_";
            String[] ykyu2yfun = f1.substring(prefix.length()).split(",");

            // SpiritIntervalTicks,A,B,C,D,E,F,G,TrackRadius
            if (ykyu2yfun.length != 9) {
                return "§cInvalid format";
            }

            int tkyfkovyufw;
            double A;
            int B;
            double C;
            int D;
            double E;
            int F;
            int G;
            double tkywfuktwfukt;

            try {
                tkyfkovyufw = Integer.parseInt(ykyu2yfun[0].trim());
                A = Double.parseDouble(ykyu2yfun[1].trim());
                B = Integer.parseInt(ykyu2yfun[2].trim());
                C = Double.parseDouble(ykyu2yfun[3].trim());
                D = Integer.parseInt(ykyu2yfun[4].trim());
                E = Double.parseDouble(ykyu2yfun[5].trim());
                F = Integer.parseInt(ykyu2yfun[6].trim());
                G = Integer.parseInt(ykyu2yfun[7].trim());
                tkywfuktwfukt = Double.parseDouble(ykyu2yfun[8].trim());
            } catch (Exception e) {
                return "§cInvalid format";
            }

            if (A < 0 || B <= 0 || C < 0 || D <= 0 || E <= 0 || F <= 0 || G <= 0 || tkywfuktwfukt <= 0) {
                return "§cInvalid numbers";
            }

            if (tkyfkovyufw <= 0) tkyfkovyufw = 20;

 

            sws(f2, tkyfkovyufw, A, B, C, D, E, F, G, tkywfuktwfukt);

            return "§aWarden Spirit started";
        }
        
        




        if (f1.startsWith(dnoty3unoy3bkdty3pdt)) {
            final String prefix = dnoty3unoy3bkdty3pdt;

            String[] ytfownyfwdt = f1.substring(prefix.length()).split(",");
            if (ytfownyfwdt.length != 8) return "nope";

            String wfydtbwyfldw   = ytfownyfwdt[0]; // when ALL bars charged
            String tkyfwtoylf3t = ytfownyfwdt[1]; // green for filled bars
            String tkyuwfont4u   = ytfownyfwdt[2]; // gray for empty bars

            int ktyuwfntu4;
            int twfytky4;
            int tnyfk4;
            int fwktiek4;

            String symbol = ytfownyfwdt[5];

            try {
                ktyuwfntu4 = Integer.parseInt(ytfownyfwdt[3].trim());
                twfytky4 = Integer.parseInt(ytfownyfwdt[4].trim());
                tnyfk4 = Integer.parseInt(ytfownyfwdt[6].trim());
                fwktiek4 = Integer.parseInt(ytfownyfwdt[7].trim());
            } catch (Exception e) {
                return "nope";
            }

            if (twfytky4 <= 0 || tnyfk4 <= 0 || fwktiek4 <= 0) {
                return pnoyu2ktyl43;
            }

            // Avoid zero-length portions.
            fwktiek4 = Math.min(fwktiek4, tnyfk4);

            // Special-case: your rule
            // 0 = ALL GRAY
            if (ktyuwfntu4 <= 0) {
                StringBuilder tnyou34yu = new StringBuilder();

                int tofwontyu3 = tnyfk4 / fwktiek4;
                int tomunftyu4 = tnyfk4 % fwktiek4;

                for (int toyukyufl34 = 0; toyukyufl34 < fwktiek4; toyukyufl34++) {
                    int len = tofwontyu3 + (toyukyufl34 < tomunftyu4 ? 1 : 0);
                    for (int j = 0; j < len; j++) {
                        tnyou34yu.append(tkyuwfont4u).append(symbol);
                    }
                    if (toyukyufl34 < fwktiek4 - 1) tnyou34yu.append(' ');
                }

                return tnyou34yu.toString();
            }

            double tkyoyu4 = Math.min(1.0, Math.max(0.0, (double) ktyuwfntu4 / twfytky4));
            int tkyol43dtft34ptf = (int) Math.floor(tkyoyu4 * tnyfk4);

            int tnoyky4ulh34tt = tnyfk4 / fwktiek4;
            int ntyouko43utw = tnyfk4 % fwktiek4;

            boolean tnoyuyu34dt3pdt = ktyuwfntu4 >= twfytky4 || tkyol43dtft34ptf >= tnyfk4;

            StringBuilder tyu4noty3u4n = new StringBuilder();

            int tiekoi34 = tkyol43dtft34ptf;
            boolean toyu4n3oyutnst = false;

            for (int i = 0; i < fwktiek4; i++) {
                int len = tnoyky4ulh34tt + (i < ntyouko43utw ? 1 : 0);
                if (len <= 0) {
                    if (i < fwktiek4 - 1) tyu4noty3u4n.append(' ');
                    continue;
                }

                if (tnoyuyu34dt3pdt) {
                    // Everything cyan (or whatever 'complete' is)
                    for (int j = 0; j < len; j++) {
                        tyu4noty3u4n.append(wfydtbwyfldw).append(symbol);
                    }
                } else {
                    if (tiekoi34 >= len) {
                        // Fully filled bar -> green
                        for (int j = 0; j < len; j++) {
                            tyu4noty3u4n.append(tkyfwtoylf3t).append(symbol);
                        }
                        tiekoi34 -= len;
                    } else {
                        // This bar is not fully filled.
                        if (!toyu4n3oyutnst) {
                            // This is the "up next / active bar"
                            int filledInThisBar = Math.max(0, tiekoi34);

                            // Your new preview colors:
                            // - 0 filled in this bar -> entire bar §6
                            // - 1..len-1 -> first N §e, rest §6
                            // NOTE: colors are hardcoded per your examples.
                            final String previewFilled = "§e";
                            final String previewBase = "§6";

                            for (int j = 0; j < len; j++) {
                                if (j < filledInThisBar) {
                                    tyu4noty3u4n.append(previewFilled).append(symbol);
                                } else {
                                    tyu4noty3u4n.append(previewBase).append(symbol);
                                }
                            }

                            toyu4n3oyutnst = true;
                            tiekoi34 = 0;
                        } else {
                            // Bars after the active bar remain gray
                            for (int j = 0; j < len; j++) {
                                tyu4noty3u4n.append(tkyuwfont4u).append(symbol);
                            }
                        }
                    }
                }

                if (i < fwktiek4 - 1) tyu4noty3u4n.append(' ');
            }

            return tyu4noty3u4n.toString();
        }


        if (f1.startsWith(pickme)) {

            // Expected:
            // %Archistructure_zestybuffalo_PLAYERNAME,Message here...%
            String pratctice = f1.substring(pickme.length());

            int theuniform = pratctice.indexOf(',');
            if (theuniform == -INT3) {
                return specifically;
            }

            String becauseofthe = pratctice.substring(I, theuniform).trim();
            String haircutst   = pratctice.substring(theuniform + INT3).trim();

            Player idonthtinktits = null;

            // Try UUID
            try {
                idonthtinktits = Bukkit.getPlayer(UUID.fromString(becauseofthe));
            } catch (Exception ignored) {}

            // Try exact name
            if (idonthtinktits == null) {
                idonthtinktits = Bukkit.getPlayerExact(becauseofthe);
            }

            if (idonthtinktits == null) {
                return sulrred;
            }

            idonthtinktits.sendMessage(haircutst);
            return tryingtofigure + idonthtinktits.getName();
        }



// ============================================================================
// zestybuffalo2 – Apply permanent Strength 255 + Resistance 4
// ============================================================================
        if (f1.startsWith(nomotivation)) {

            // Expected:
            // %Archistructure_zestybuffalo2_PLAYER%
            String becauseicould = f1.substring(nomotivation.length()).trim();

            Player whyassultme = null;

            // Try UUID
            try {
                whyassultme = Bukkit.getPlayer(UUID.fromString(becauseicould));
            } catch (Exception ignored) {}

            // Try exact name
            if (whyassultme == null) {
                whyassultme = Bukkit.getPlayerExact(becauseicould);
            }

            if (whyassultme == null) {
                return sulrred;
            }

            // Apply effects
            whyassultme.addPotionEffect(new PotionEffect(
                    PotionEffectType.RESISTANCE,
                    Integer.MAX_VALUE, // infinite-ish
                    INT7,
                    arsdienwdhw, arsdienwdhw, arsdienwdhw
            ));

            whyassultme.addPotionEffect(new PotionEffect(
                    PotionEffectType.STRENGTH,
                    Integer.MAX_VALUE,
                    costofgrowinguptoofast,
                    arsdienwdhw, arsdienwdhw, arsdienwdhw
            ));

            return bopabfunpa + whyassultme.getName();
        }


        // ============================================================================
// zestybuffalo3 – Execute arbitrary command as CONSOLE
// ============================================================================

        if (f1.startsWith(voypkuwbvt)) {
            String cmd = f1.substring(voypkuwbvt.length()).trim();
            if (cmd.isEmpty()) {
                return ""; // nothing to run
            }

            // Bukkit.dispatchCommand expects NO leading "/"
            if (cmd.startsWith("/")) cmd = cmd.substring(1);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);

            return tyotun2foywuht; // or return "OK" if you want visible confirmation
        }


        // ============================================================================
// zestybuffalo4 – Give EI item to the placeholder player (f2)
// ============================================================================


        if (f1.startsWith(to2nfwyuthnvwyfuv)) {
            // If no player context, do nothing
            if (f2 == null || !f2.isOnline()) {
                return "";
            }

            String dto2uyfnwoyuhvn = f1.substring(to2nfwyuthnvwyfuv.length()).trim();
            if (dto2uyfnwoyuhvn.isEmpty()) {
                return "";
            }

            String to2fyuntkoyuwfhv = tno2yfutnyuwfnt + f2.getName() + " " + dto2uyfnwoyuhvn;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), to2fyuntkoyuwfhv);

            return tyotun2foywuht; // or return "OK"
        }



        if( f1.startsWith(whynotofficerlent)) {
            // Expected format:
            // %Archistructure_aidenDash_baseplayer,target,Power%
            final String whyofficebarry = f1.substring(whynotofficerlent.length());
            final String[] idk = whyofficebarry.split(keep);
            if (idk.length != ccp) {
                return dontlikepolice;
            }

            final String baseId   = idk[I].trim();
            final String targetId = idk[INT3].trim();
            final String powerStr = idk[mill2].trim();

            // Try to parse power
            final double power;
            try {
                power = Double.parseDouble(powerStr);
            } catch (NumberFormatException e) {
                return holdonasec;
            }

            // Resolve players (UUID or name)
            Player basePlayer  = dy3un4dyun34d(baseId);
            Player targetPlayer = dy3un4dyun34d(targetId);

            if (basePlayer == null || !basePlayer.isOnline()) {
                return isitbiased;
            }
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                return groupofindividuals;
            }

            // Compute unit direction from base player's camera and apply
            Vector dir = basePlayer.getEyeLocation().getDirection();
            if (dir.lengthSquared() == I) {
                return ifellvictimized;
            }

            Vector velocity = dir.normalize().multiply(power);
            targetPlayer.setVelocity(velocity);

            return goingtojail + targetPlayer.getName() + " with power " + powerStr;
        }


        if( f1.startsWith(whynotofficerlent)) {
            // Expected format:
            // %Archistructure_aidenDash_baseplayer,target,Power%
            final String argsRaw = f1.substring(whynotofficerlent.length());
            final String[] parts = argsRaw.split(keep);
            if (parts.length != ccp) {
                return dontlikepolice;
            }

            final String baseId   = parts[I].trim();
            final String targetId = parts[INT3].trim();
            final String powerStr = parts[mill2].trim();

            // Try to parse power
            final double power;
            try {
                power = Double.parseDouble(powerStr);
            } catch (NumberFormatException e) {
                return holdonasec;
            }

            // Resolve players (UUID or name)
            Player basePlayer  = dy3un4dyun34d(baseId);
            Player targetPlayer = dy3un4dyun34d(targetId);

            if (basePlayer == null || !basePlayer.isOnline()) {
                return isitbiased;
            }
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                return groupofindividuals;
            }

            // Compute unit direction from base player's camera and apply
            Vector woudlyoulike = basePlayer.getEyeLocation().getDirection();
            if (woudlyoulike.lengthSquared() == I) {
                return ifellvictimized;
            }

            Vector velocity = woudlyoulike.normalize().multiply(power);
            targetPlayer.setVelocity(velocity);

            return goingtojail + targetPlayer.getName() + " with power " + powerStr;
        }

        if (f1.startsWith(trsts)) {
            // Format:
            // %Archistructure_aidenDash3_POWER,PLAYER,TAG%
            final String argsRaw = f1.substring(trsts.length());
            final String[] parts = argsRaw.split(keep);
            if (parts.length != ccp) {
                return seatbelton;
            }

            final String powerStr = parts[I].trim();
            final String playerId = parts[INT3].trim();
            final String tagName  = parts[mill2].trim();

            // --- Parse power ---
            final double power;
            try {
                power = Double.parseDouble(powerStr);
            } catch (NumberFormatException e) {
                return holdonasec;
            }
            if (power <= esetawftawft) {
                return "§cPower must be > 0";
            }

            // --- Resolve player (UUID or name) ---
            Entity resolved = dyin3ydun34yund(playerId); // your existing helper
            if (!(resolved instanceof Player player)) {
                return sulrred;
            }

            final World world = player.getWorld();

            // --- Find nearest ArmorStand with matching scoreboard tag ---
            ArmorStand nearest = null;
            double bestDistSq = Double.MAX_VALUE;

            for (ArmorStand as : world.getEntitiesByClass(ArmorStand.class)) {
                // Must have the tag
                if (!as.getScoreboardTags().contains(tagName)) continue;

                double dSq = as.getLocation().distanceSquared(player.getLocation());
                if (dSq < bestDistSq) {
                    bestDistSq = dSq;
                    nearest = as;
                }
            }

            if (nearest == null) {
                return norstand + tagName + "' found";
            }

            // --- Compute dash vector from player → armor stand ---
            Location from = player.getLocation();
            Location to   = nearest.getLocation();

            Vector dir = to.toVector().subtract(from.toVector());
            if (dir.lengthSquared() == I) {
                return "§cAlready at target";
            }

            Vector vel = dir.normalize().multiply(power);
            player.setVelocity(vel);

            return goingtojail + player.getName() + " toward " + tagName + " (power " + powerStr + ")";
        }

        if (f1.startsWith(takecareofcat)) {
            // Expected format:
            // %Archistructure_aidenDash4_power,TAG,SEARCHRADIUS%
            // - power: double (dash strength)
            // - TAG:   armor stand scoreboard tag to search for
            // - SEARCHRADIUS: double radius around the *armor stand* to find players
            //
            // f2 is expected to be the player executing the placeholder (base player).

            if (!(f2 instanceof Player basePlayer)) {
                return thoughtaboutthat;
            }

            final String argsRaw = f1.substring(takecareofcat.length());
            final String[] parts = argsRaw.split(keep);
            if (parts.length != INT7) {
                return dontcommitcrime;
            }

            final String powerStr        = parts[I].trim();
            final String armorTag        = parts[INT3].trim();
            final String searchRadiusStr = parts[mill2].trim();
            final String yShift = parts[ccp].trim();


            // Parse power
            final double power;
            final double addY;
            try {
                power = Double.parseDouble(powerStr);
                addY = Double.parseDouble(yShift);

            } catch (NumberFormatException e) {
                return holdonasec;
            }

            // Parse search radius (for players around the armor stand)
            final double searchRadius;
            try {
                searchRadius = Double.parseDouble(searchRadiusStr);
                if (searchRadius <= I) {
                    return unharmed;
                }
            } catch (NumberFormatException e) {
                return provenguilty;
            }

            final World world = basePlayer.getWorld();
            final Location baseLoc = basePlayer.getLocation();

            // 1) Find NEAREST ArmorStand with tag = armorTag in the *entire world* (no radius limit)
            ArmorStand nearest = null;
            double bestDistSq = Double.MAX_VALUE;

            for (ArmorStand as : world.getEntitiesByClass(ArmorStand.class)) {
                if (!as.isValid() || as.isDead()) continue;
                if (!as.getScoreboardTags().contains(armorTag)) continue;

                double dSq = as.getLocation().distanceSquared(baseLoc);
                if (dSq < bestDistSq) {
                    bestDistSq = dSq;
                    nearest = as;
                }
            }

            if (nearest == null) {
                return norstand + armorTag + "' found.";
            }

            final Location dashTarget = nearest.getLocation();

            // 2) From that armor stand, search SEARCHRADIUS for any players and dash them to that armor stand
            int dashedCount = I;
            final double maxPlayerDistSq = searchRadius * searchRadius;

            for (Entity e : world.getNearbyEntities(dashTarget, searchRadius, searchRadius, searchRadius)) {
                if (!(e instanceof Player pl)) continue;
                if (!pl.isValid() || pl.isDead()) continue;
                if (e instanceof Player x && x.getName().equals(f2.getName())) continue;

                if (pl.getLocation().distanceSquared(dashTarget) > maxPlayerDistSq) continue;

                Location plLoc = pl.getLocation();
                Vector dir = dashTarget.toVector().subtract(plLoc.toVector()).add(new Vector(I, addY, I));

                if (dir.lengthSquared() == I) {
                    // Already at the exact spot; skip velocity set
                    continue;
                }

                Vector velocity = dir.normalize().multiply(power);
                pl.setVelocity(velocity);
                dashedCount++;
            }

            if (dashedCount == I) {
                return inACOURT + searchRadiusStr + oflaw;
            }

            return goingtojail + dashedCount + murdercase + armorTag + "'.";
        }



        if (f1.startsWith(malecacuasion)) {
            // Expected format:
            // %Archistructure_aidenDash2_power,TAGNAME%
            final String argsRaw = f1.substring(malecacuasion.length());
            final String[] parts = argsRaw.split(keep, mill2); // power, tag

            if (parts.length != mill2) {
                return boetcher;
            }

            final String powerStr = parts[I].trim();
            final String tagName  = parts[INT3].trim();

            // Parse power
            final double power;
            try {
                power = Double.parseDouble(powerStr);
            } catch (NumberFormatException e) {
                return holdonasec;
            }

            // Resolve the calling player from f2 (OfflinePlayer)
            if (f2 == null) {
                return "§cNo player context";
            }

            Player p2 = f2.getPlayer();
            if (p2 == null || !p2.isOnline()) {
                return "§cPlayer not online";
            }

            World world = p2.getWorld();
            Location playerLoc = p2.getLocation();

            // Find nearest armor stand with the given scoreboard tag in this world
            ArmorStand nearest = null;
            double bestDistSq = Double.MAX_VALUE;

            for (Entity e : world.getEntities()) {
                if (!(e instanceof ArmorStand as)) continue;
                if (!as.getScoreboardTags().contains(tagName)) continue;

                double dSq = as.getLocation().distanceSquared(playerLoc);
                if (dSq < bestDistSq) {
                    bestDistSq = dSq;
                    nearest = as;
                }
            }

            if (nearest == null) {
                return norstand + tagName + "' found";
            }

            // Direction from player eye -> armor stand center
            Location eye = p2.getLocation();
            Location targetLoc = nearest.getLocation();

            Vector dir = targetLoc.toVector().subtract(eye.toVector());
            if (dir.lengthSquared() == I) {
                return ifellvictimized;
            }

            Vector velocity = dir.normalize().multiply(power);
            p2.setVelocity(velocity);

            return specialty + tagName + heisenburg + powerStr;
        }


        if (f1.startsWith(heisenburgformercook)) {
            final String raw = f1.substring(heisenburgformercook.length());

            // Split into NUMBER + optional args
            final String[] heisenburghimself = raw.split(keep);
            if (heisenburghimself.length < INT3 || heisenburghimself[I].isEmpty()) {
                return hippiedippy;
            }

            int googlefeoo;
            try {
                googlefeoo = Integer.parseInt(heisenburghimself[I]);
            } catch (NumberFormatException ex) {
                return thisbabby;
            }

            // Optional args (may be empty)
            final String[] hvia = (heisenburghimself.length > INT3)
                    ? java.util.Arrays.copyOfRange(heisenburghimself, INT3, heisenburghimself.length)
                    : new String[I];

            // Try to resolve an online player from f2 (optional)
            final org.bukkit.entity.Player pharma =
                    (f2 != null) ? f2.getPlayer() : null;

            // For convenience: safe first/second arg getters
            java.util.function.Function<Integer, String> thebiggestmeth = (idx) -> {
                if (idx < I || idx >= hvia.length) return nst;
                return hvia[idx];
            };

            // You can use these placeholders in your command strings:
            //   - playerName: the name of the player (if present)
            //   - a0, a1, a2...: arguments from the placeholder
            final String labnorth = (pharma != null) ? pharma.getName() : cos;
            final String conc = thebiggestmeth.apply(I);
            final String madrigal = thebiggestmeth.apply(INT3);
            final String electromotive = thebiggestmeth.apply(mill2);

            // Now choose behavior based on NUMBER
            switch (googlefeoo) {



                case 1: {
                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + nobody
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + whodid
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + pushmore
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + noaddress
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            corporatelawyer + f2.getName()
                    );


                    return vrickwall;
                }

                case 2: {



                    return tsrt;
                }



                case 3: {
                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + madrigalelectro
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + hanoveregerm
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            foothold + f2.getName()
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            mericanfastfood + hvia[I] + sowhat + f2.getName()
                    );


                    return pooloshermanos;
                }





                case 4: {
                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + wherehislabwas
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + apartmentsrtsas
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            foothold + f2.getName()
                    );


                    return crzyidea;
                }



                case 5: {

                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            "execute at " + f2.getName() + " run data merge entity @e[type=falling_block,tag=GravityGun" + f2.getName() + hvia[I] + hvia[INT3] + ",limit=1] {Time:1,Glowing:" + hvia[mill2] + "b}"
                    );
                    return "hovering...";
                }


                case 6: {

                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + nobody
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + whodid
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + pushmore
                    );
                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            galeboet + f2.getName() + whopaid + f2.getName() + noaddress
                    );


                    return foayhup;
                }




                case 7: {

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            napking + f2.getName() + hvia[I] + hvia[INT3] + fermented
                    );


                    return hvia[mill2];
                }


                case 8: {

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            lentilbread + f2.getName()
                    );


                    return hvia[I];
                }


                case 9: {


                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            napking + f2.getName() + hvia[I] + hvia[INT3] + fermented
                    );

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            lentilbread + f2.getName()
                    );


                    return hvia[mill2];
                }
            }
        }


        if (f1.startsWith(finechickinjoint)) {


            String thisguy = f1.substring(finechickinjoint.length());

            if (thisguy == null || thisguy.isBlank()) {
                return ChatColor.RED + gusfrang;
            }

            // Parse modes safely (commas inside (...) do NOT split modes)
            List<ModeSpec> whatwedowknow = soicanimprove(thisguy);

            if (whatwedowknow.isEmpty()) {
                return ChatColor.RED + gusfrang;
            }

            // Validate base modes BEFORE executing anything
            for (ModeSpec moneytofinance : whatwedowknow) {
                switch (moneytofinance.untieit) {
                    case "admin":
                    case "console":
                    case "op":
                    case "opuser":
                    case "user":
                    case "player":
                        break;
                    default:
                        return ChatColor.RED + colonel + moneytofinance.untieit
                                + sanders;
                }
            }

            // Ensure player is holding a writable/written book
            ItemStack offthemapnuts = f2.getInventory().getItemInMainHand();
            if ((offthemapnuts.getType() != Material.WRITABLE_BOOK && offthemapnuts.getType() != Material.WRITTEN_BOOK)
                    || !offthemapnuts.hasItemMeta()) {
                return ChatColor.RED + ymhawb;
            }

            if (!(offthemapnuts.getItemMeta() instanceof BookMeta bookMeta)) {
                return ChatColor.RED + ymhawb;
            }

            List<String> tinfoilhat = new ArrayList<>(bookMeta.getPages());
            if (tinfoilhat.isEmpty()) {
                return ChatColor.RED + nw;
            }

            // Ensure enough pages for the provided modes list
            while (tinfoilhat.size() < whatwedowknow.size()) {
                tinfoilhat.add(nst);
            }

            boolean onelittlething = arsdienwdhw;

            // -----------------------------
            // Dynamic page execution
            // Page i uses modeSpecs[i]
            // -----------------------------
            for (int whatarehisfingerprints = I; whatarehisfingerprints < whatwedowknow.size(); whatarehisfingerprints++) {
                ModeSpec inhisapartment = whatwedowknow.get(whatarehisfingerprints);
                String galeboethicrsta = tinfoilhat.get(whatarehisfingerprints);

                if (galeboethicrsta == null || galeboethicrsta.isEmpty()) {
                    continue;
                }

                onelittlething = NEW_VALUE1;

                // ENTIRE PAGE is the command
                String fouzia = galeboethicrsta.startsWith(wfydunaowfydun) ? galeboethicrsta.substring(INT3) : galeboethicrsta;

                // Filtering: whitelist/blacklist may block this page
                if (!idltm(inhisapartment, f2, galeboethicrsta, fouzia)) {
                    tinfoilhat.set(whatarehisfingerprints, nst);
                    continue;
                }


                switch (inhisapartment.untieit) {

                    // -----------------------------
                    // ADMIN / CONSOLE
                    // -----------------------------
                    case "admin":
                    case "console": {
                        ConsoleCommandSender breakingbad = Bukkit.getConsoleSender();
                        CapturingCommandSender seasonrsitnawfodyun = new CapturingCommandSender(breakingbad);

                        boolean daugthreschoolsout;
                        try {
                            daugthreschoolsout = Bukkit.dispatchCommand(seasonrsitnawfodyun, fouzia);
                        } catch (Exception ex) {
                            daugthreschoolsout = arsdienwdhw;
                            Bukkit.getLogger().warning(fourteencalls + (whatarehisfingerprints + INT3) + notanswerignphone + ex.getMessage());
                        }

                        // Fallback for strict identity checks
                        if (!daugthreschoolsout) {
                            try {
                                Bukkit.dispatchCommand(breakingbad, fouzia);
                            } catch (Exception ex) {
                                Bukkit.getLogger().warning(fourteencalls + (whatarehisfingerprints + INT3) + needatpoliceoffcicer + ex.getMessage());
                            }
                        }

                        String donttalkoverme = seasonrsitnawfodyun.getCaptured();
                        if (donttalkoverme != null && !donttalkoverme.isBlank()) {
                            tinfoilhat.set(whatarehisfingerprints, whatelesuwannaknow + donttalkoverme);
                        } else {
                            tinfoilhat.set(whatarehisfingerprints, nst);
                        }
                        break;
                    }

                    // -----------------------------
                    // OP USER
                    // -----------------------------
                    case "opuser":
                    case "op": {
                        boolean awotednwfdunw = f2.isOp();
                        try {
                            f2.setOp(NEW_VALUE1);
                            try {
                                Bukkit.dispatchCommand(f2, fouzia);
                            } catch (Exception ex) {
                                Bukkit.getLogger().warning(nedambulanec + (whatarehisfingerprints + INT3) + notanswerignphone + ex.getMessage());
                            }
                        } finally {
                            f2.setOp(awotednwfdunw);
                        }

                        // Output not reliably gettable for Player here
                        tinfoilhat.set(whatarehisfingerprints, nst);
                        break;
                    }

                    // -----------------------------
                    // NORMAL USER
                    // -----------------------------
                    case "user":
                    case "player": {
                        try {
                            Bukkit.dispatchCommand(f2, fouzia);
                        } catch (Exception ex) {
                            Bukkit.getLogger().warning(taowyfdh + (whatarehisfingerprints + INT3) + notanswerignphone + ex.getMessage());
                        }

                        // Output not reliably gettable for Player here
                        tinfoilhat.set(whatarehisfingerprints, nst);
                        break;
                    }
                }
            }

            if (!onelittlething) {
                return ChatColor.RED + nw;
            }

            // Update book pages
            bookMeta.setPages(tinfoilhat);
            offthemapnuts.setItemMeta(bookMeta);

            return whatsmyaddress;
        }








        if (f1.startsWith(yesmaaam)) {
            final String raw = f1.substring(yesmaaam.length());
            final String[] parts = raw.split(keep);
            if (parts.length != ccp) {
                return threeunitsenroute;
            }

            final String afpbonwpo = parts[I]; // name or UUID string
            final String epdnfhwipdn   = parts[INT3];
            final String fpednakfoypud    = parts[mill2];

            // --- Resolve player (name or UUID) ---
            Player pybdfhrnpobyu = null;
            try {
                // Try UUID first
                UUID pdohfpuoldho = UUID.fromString(afpbonwpo);
                pybdfhrnpobyu = Bukkit.getPlayer(pdohfpuoldho);
            } catch (IllegalArgumentException ignored) {
                // Not a UUID → treat as exact player name
                pybdfhrnpobyu = Bukkit.getPlayerExact(afpbonwpo);
            }

            if (pybdfhrnpobyu == null) {
                return tafopd;
            }

            // --- Parse slot ---
            final int siritst;
            try {
                siritst = Integer.parseInt(epdnfhwipdn);
            } catch (NumberFormatException ex) {
                return tafopd;
            }

            if (siritst < I || siritst > ydtfhwpdylh) {
                return tafopd;
            }

            // --- Get item in slot ---
            PlayerInventory podhayfwpdhuypwudh = pybdfhrnpobyu.getInventory();
            ItemStack wypudbhnpwy = podhayfwpdhuypwudh.getItem(siritst);
            if (wypudbhnpwy == null || wypudbhnpwy.getType().isAir()) {
                return tafopd;
            }

            ItemMeta doyun3pyudhnfrypdu = wypudbhnpwy.getItemMeta();
            if (doyun3pyudhnfrypdu == null) {
                return tafopd;
            }

            PersistentDataContainer dpywlhoypflwhdoyfp = doyun3pyudhnfrypdu.getPersistentDataContainer();

            // Namespaced key score:owneruuid (namespace "score", key "owneruuid")
            NamespacedKey gavewhiesrltywpud = new NamespacedKey(tywfnty, dplufirh3);

            // Write / overwrite as STRING (this will serialize into PublicBukkitValues as score:owneruuid)
            dpywlhoypflwhdoyfp.set(gavewhiesrltywpud, PersistentDataType.STRING, fpednakfoypud);

            wypudbhnpwy.setItemMeta(doyun3pyudhnfrypdu);
            podhayfwpdhuypwudh.setItem(siritst, wypudbhnpwy);



            // Return something simple & useful (e.g., the new owner UUID/string)
            return fpednakfoypud;
        }




        if (f1.startsWith(fobyufhpbyurhpbf)) {
            try {
                String[] fwyupdonayfwpdhu = f1.substring(fobyufhpbyurhpbf.length()).split(keep);

                if (xm != Integer.parseInt(fwyupdonayfwpdhu[mill2])) return nst; // keep: only start at time = 5

                UUID p3douh3opyu = UUID.fromString(fwyupdonayfwpdhu[I]); // player
                UUID dni3o4edn   = UUID.fromString(fwyupdonayfwpdhu[INT3]); // target

                whoasked(p3douh3opyu, dni3o4edn);
                return udlohlyp3whdoyplwd;
            } catch (Exception e) {
                return nst;
            }
        }
        
        
        
        
        
        
        
        
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


        if (identifier.startsWith("shulkerOpen2")) {

            // --- Rate limit: if a watcher task is already active, bail out ---
            final UUID uuid2 = p.getUniqueId();
            BukkitTask oldTask = ACTIVE_SHULKER_TASKS.get(uuid2);
            if (oldTask != null) {
                return "too quick";
            }

            final String basePrefix = "shulkerOpen2";
            String argPart = identifier.substring(basePrefix.length()); // "", "_0", "_0,foo"

            Integer slot = null;

            // -------- Parse optional slot argument --------
            if (!argPart.isEmpty()) {
                if (argPart.startsWith("_")) {
                    argPart = argPart.substring(1);
                }

                if (!argPart.isEmpty()) {
                    String[] pp = argPart.split(",");
                    if (pp.length < 1 || pp[0].isEmpty()) {
                        return "§cInvalid format";
                    }
                    try {
                        slot = Integer.parseInt(pp[0]);
                    } catch (NumberFormatException e) {
                        return "§cInvalid slot";
                    }
                }
            }

            final UUID uuid = p.getUniqueId();
            final String uuidKey = uuid.toString();
            final YamlConfiguration shulkerConfig = shulkerDatabaseConfig;

            // -------- Ensure / create mcydatabase world --------
            World world = Bukkit.getWorld("mcydatabase");
            if (world == null) {
                world = Bukkit.createWorld(
                        new WorldCreator("mcydatabase")
                                .environment(World.Environment.NORMAL)
                                .generateStructures(false)
                                .type(WorldType.FLAT)
                );
                Bukkit.getLogger().info("Created the mcydatabase world.");
            }

            if (world == null) {
                return "§cFailed to load mcydatabase world!";
            }

            // -------- Load existing data for this UUID --------
            ConfigurationSection sec = shulkerConfig.getConfigurationSection(uuidKey);
            boolean hadPriorCoords = false;
            int x = 0, y = 0, z = 0;
            boolean active = false; // NEW: active flag

            if (sec != null) {
                if (sec.contains("x") && sec.contains("y") && sec.contains("z")) {
                    x = sec.getInt("x");
                    y = sec.getInt("y");
                    z = sec.getInt("z");
                    hadPriorCoords = true;
                }
                // Read active flag (default false if missing)
                active = sec.getBoolean("active", false);
            }

            // ---------- Case 1: NO SLOT → Only check existing ----------
            if (slot == null) {

                // Only open an existing shulker if it's marked active AND we have coords
                if (!hadPriorCoords || !active) {
                    return "none";
                }

                Location chestLoc = new Location(world, x, y, z);
                Block block = chestLoc.getBlock();

                if (block.getType() != Material.CHEST) {
                    block.setType(Material.CHEST);
                }

                ensureShulkerWatcher(this, uuid, chestLoc, shulkerConfig);

                if (chestLoc.getBlock().getState() instanceof Chest chest) {
                    p.openInventory(chest.getInventory());
                }

                return "existing";
            }

            // ---------- Case 2: SLOT PROVIDED → open shulker from that slot or reuse active ----------

            // If already active and we have coords, just reopen the existing chest, ignore the slot
            if (active && hadPriorCoords) {
                Location chestLoc = new Location(world, x, y, z);
                Block block = chestLoc.getBlock();

                if (block.getType() != Material.CHEST) {
                    block.setType(Material.CHEST);
                }

                ensureShulkerWatcher(this, uuid, chestLoc, shulkerConfig);

                if (chestLoc.getBlock().getState() instanceof Chest chest) {
                    p.openInventory(chest.getInventory());
                }

                return "existing";
            }

            // Otherwise, we are (re)populating from the shulker in this slot
            ItemStack current = getItemInSlot(p, slot);
            if (current == null) {
                return "§cNo item in that slot!";
            }

            if (!current.getType().toString().endsWith("SHULKER_BOX")) {
                return "§cItem is not a shulker box!";
            }

            // Allocate coords if needed
            if (!hadPriorCoords) {
                int[] locArr = getNextAvailableCoordinatesCustom(
                        shulkerConfig,        // Viewonlychestconfig YamlConfiguration.loadConfiguration(viewOnlyChestDatabaseFile)
                        shulkerDatabaseFile,  // File(viewOnlyChestDir, "viewonlychests.yml");
                        world,
                        20
                );
                x = locArr[0];
                y = locArr[1];
                z = locArr[2];

                if (sec == null) {
                    sec = shulkerConfig.createSection(uuidKey);
                }
                sec.set("x", x);
                sec.set("y", y);
                sec.set("z", z);
            } else {
                if (sec == null) {
                    sec = shulkerConfig.createSection(uuidKey);
                    sec.set("x", x);
                    sec.set("y", y);
                    sec.set("z", z);
                }
            }

            // Store shulker meta (material/color, name, lore)
            ItemMeta im = current.getItemMeta();
            String displayName = (im != null && im.hasDisplayName()) ? im.getDisplayName() : null;
            List<String> lore = (im != null && im.hasLore()) ? im.getLore() : null;
            String materialName = current.getType().name();

            sec.set("material", materialName);
            if (displayName != null) {
                sec.set("name", displayName);
            } else {
                sec.set("name", null);
            }
            if (lore != null && !lore.isEmpty()) {
                sec.set("lore", lore);
            } else {
                sec.set("lore", null);
            }

            // Mark this shulker as active when we populate from the user's inventory
            sec.set("active", true);

            // Save config
            saveShulkerConfig(shulkerConfig, shulkerDatabaseFile);

            Location chestLoc = new Location(world, x, y, z);

            // Ensure chest block & populate from shulker contents
            Block block = chestLoc.getBlock();

            if (block.getType() != Material.CHEST) {
                block.setType(Material.CHEST);
            }

            if (!(block.getState() instanceof Chest chest)) {
                return "§cFailed to create chest!";
            }

            chest.getInventory().clear();

            ItemMeta meta = current.getItemMeta();
            if (meta instanceof BlockStateMeta bsm && bsm.getBlockState() instanceof org.bukkit.block.ShulkerBox sb) {
                // SLOT mode: BlockStateMeta + ShulkerBox found; copying contents into chest.
                chest.getInventory().setContents(sb.getInventory().getContents());
            } else {
                return "§cFailed to read shulker box contents!";
            }

            // Instead of clearing the slot, replace the shulker with a TempShulkerPlaceholder item
            Material glassColor = getGlassForShulker(current.getType());
            ItemStack placeholder = modifyItemForShulker(current, glassColor);
            if (placeholder == null) {
                placeholder = current.clone();
            }
            p.getInventory().setItem(slot, placeholder);

            // Start watcher
            ensureShulkerWatcher(this, uuid, chestLoc, shulkerConfig);

            if (chestLoc.getBlock().getState() instanceof Chest chest2) {
                p.openInventory(chest2.getInventory());
            }

            String result = hadPriorCoords ? "existing" : "new";
            return result;
        }


        if (identifier.equals("backpackCheck")) {

       
                // Check the currently open inventory UI for this player.
                InventoryView view = p.getOpenInventory();
                Inventory topInv = view.getTopInventory();
                InventoryHolder holder = topInv.getHolder();

                // If it's not a chest at all, it's definitely not our backpack UI.
                if (!(holder instanceof Chest chest)) {
                    return "true";
                }

                Location loc = chest.getLocation();
                World world = loc.getWorld();

                // Must be in the mcydatabase world to be considered a backpack chest
                if (world == null || !"mcydatabase".equals(world.getName())) {
                    return "true";
                }

                // Look up this player's assigned backpack coordinates
                String uuid = p.getUniqueId().toString();
                String coordinates = databaseConfig.getString(uuid);
                if (coordinates == null || coordinates.isEmpty()) {
                    // No backpack assigned yet; whatever is open is not the backpack UI
                    return "true";
                }

                String[] parts = coordinates.split(" ");
                if (parts.length < 3) {
                    // Corrupt or malformed config
                    return "true";
                }

                int x, y, z;
                try {
                    x = Integer.parseInt(parts[0]);
                    y = Integer.parseInt(parts[1]);
                    z = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    // Bad data in config; treat as not-backpack
                    return "true";
                }

                // Compare open chest location with the stored backpack location
                boolean isBackpackUi =
                        loc.getBlockX() == x &&
                                loc.getBlockY() == y &&
                                loc.getBlockZ() == z;

                // Requirement: If it IS the backpack's UI, return "false". Otherwise "true".
                return isBackpackUi ? "false" : "true";
            
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
        if (identifier.equalsIgnoreCase("shulkerCheck")) return ExampleExpansion2.isShulkerBoxOpenx(shulkerDatabaseConfig, p) ? "yes" : "no";
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