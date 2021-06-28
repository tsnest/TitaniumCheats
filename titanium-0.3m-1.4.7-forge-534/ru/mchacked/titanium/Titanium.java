package ru.mchacked.titanium;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.mchacked.titanium.api.Hooks;
import ru.mchacked.titanium.api.IAddon;
import ru.mchacked.titanium.api.IKeyBind;

public class Titanium implements IKeyBind {
	
	public static String cmdPrefix = "`";
	public static String version = "0.3m";
	public static int btnMenuActivate = 41;
	public static int Field17 = 0;
	private static Titanium titanium;
	public static int WHITE = 16777215;
	private static Minecraft mc = Minecraft.getMinecraft();
	private static HashMap cmdsHash = new HashMap();
	private static File settingsFile = new File(mc.mcDataDir, "t1tankas.dat");
	private static AtomicBoolean init = new AtomicBoolean(false);
	private SettingsUtils settingsUtils;
	private KeyBind keybind;
	private TGui tgui;
	private Settings settings;
	private Timer timer;
	private RadarUtils Field34;
	private Xray xray;
	private Nuker nuker;
	private Timer nukerTimer;
	private RenderEnabledModules rem;
	private Favorites favorites;
	private KillAuraFriends kaFriends;
	private EspUtils espUtils;
	private Flooder Flooder;
	private AddonLoader aloader;
	private KeyBindSettings kbs;
	private static String copyright;
	private List Field46 = new ArrayList();
	private boolean Field47;
	private boolean Field48;
	private boolean Field49;
	private boolean Field50;
	private double Field51;
	private double Field52;
	private double Field53;
	private float Field54;
	private float Field55;
	private long lastTime = 0L;
	private int Field57 = 0;
	
	static {
		cmdsHash.put("xray", "xr");
		cmdsHash.put("fly", "fl");
		cmdsHash.put("fullbright", "fb");
		cmdsHash.put("freecam", "fc");
		cmdsHash.put("nofall", "nf");
		cmdsHash.put("antiafk", "aa");
		cmdsHash.put("sneak", "ps");
	}
	
	public static Titanium getTitanium() {
		return titanium;
	}
	
	public Titanium() {
		if(titanium != null) {
			throw new RuntimeException("Wut?");
		} else {
			titanium = this;
		}
	}
	
	public Settings getSettings() {
		return this.settings;
	}
	
	public Xray getXray() {
		return this.xray;
	}
	
	public Favorites Method35() {
		return this.favorites;
	}
	
	public TGui getTGui() {
		return this.tgui;
	}
	
	public KeyBind Method37() {
		return this.keybind;
	}
	
	public KillAuraFriends Method38() {
		return this.kaFriends;
	}
	
	public Nuker Method39() {
		return this.nuker;
	}
	
	public Flooder Method40() {
		return this.Flooder;
	}
	
	public AddonLoader Method41() {
		return this.aloader;
	}
	
	public KeyBindSettings Method42() {
		return this.kbs;
	}
	
	public void registerBind(int key, IKeyBind target) {
		this.keybind.Method20(key, target);
	}
	
	public void init() {
		if(!init.getAndSet(true)) {
			this.keybind = new KeyBind();
			this.tgui = new TGui();
			this.settingsUtils = new SettingsUtils();
			this.settings = new Settings();
			this.xray = new Xray();
			this.nuker = new Nuker();
			this.rem = new RenderEnabledModules();
			this.favorites = new Favorites();
			this.kaFriends = new KillAuraFriends();
			this.espUtils = new EspUtils();
			this.Flooder = new Flooder();
			this.kbs = new KeyBindSettings();
			this.aloader = new AddonLoader();
			this.timer = new Timer(true);
			this.timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					Titanium.this.Method58();
				}
			}, 30000L, 10000L);
			this.nukerTimer = new Timer(true);
			this.nukerTimer.scheduleAtFixedRate(this.nuker, 100L, 10L);
			this.settingsLoader();
			Field17 = this.kbs.Method14();
			cmdPrefix = this.kbs.Method15();
			this.keybind.Method20(Field17, this);
			Commands.register();
			this.Field34 = new RadarUtils();
			this.Method47();
			this.aloader.Method8();
			this.Method60();
			if(mc.gameSettings.gammaSetting > 1.0F) {
				this.settings.setValue("fb", Boolean.valueOf(true));
			}
		}
	}
	
	public static String Method46() {
		if(copyright != null) {
			return copyright;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("§eTitanium cheats v");
			sb.append(version);
			sb.append("§c [McHacked]");
			return copyright = sb.toString();
		}
	}
	
	private void Method47() {
		InputStream apis = Titanium.class.getResourceAsStream("/loadapis.txt");
		if(apis != null) {
			try {
				BufferedReader e = new BufferedReader(new InputStreamReader(apis));
				
				String line;
				while((line = e.readLine()) != null) {
					try {
						Class t = Class.forName(line);
						Method m = t.getMethod("cheatmain", new Class[0]);
						m.invoke((Object)null, new Object[0]);
					} catch (Throwable ex) {
						System.out.println("Failed to load API class " + line);
						ex.printStackTrace();
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					apis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public String Method48() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("[%d, %d, %d]", new Object[]{Integer.valueOf((int)this.mc.thePlayer.posX), Integer.valueOf((int)(this.mc.thePlayer.posY - 1.600000023841858D)), Integer.valueOf((int)this.mc.thePlayer.posZ)}));
		if(this.Flooder.Method79() != 0) {
			sb.append(" §eFlood: ");
			sb.append(this.Flooder.Method79());
		}
		
		return sb.toString();
	}
	
	public void hudRender(FontRenderer fr, int width, int height) {
		if(this.Field47 && mc.gameSettings.gammaSetting < 12.0F) {
			mc.gameSettings.gammaSetting = 12.0F;
		}
		
		fr.drawString(Method46(), 1, 1, this.WHITE, true);
		fr.drawString(this.Method48(), 1, fr.FONT_HEIGHT + 1, this.WHITE, true);
		this.keybind.Method19(Keyboard.getEventKey());
		this.keybind.Method19(Field17);
		if(mc.thePlayer != null) {
			++this.Field57;
			if(this.Field57 % 20 == 0 && this.getSettings().getBoolean("st")) {
				mc.thePlayer.stepHeight = 10.0F;
			}
			
			this.Method51();
			this.Method53();
			this.Method52(fr, width, height);
			if(this.Field49) {
				this.rem.Method4(fr);
			}
			
			IAddon[] arr$ = this.aloader.Method11();
			int len$ = arr$.length;
			for(int i$ = 0; i$ < len$; ++i$) {
				IAddon a = arr$[i$];
				a.onHUDrender(fr, width, height);
			}
			
			if(Hooks.setts("do") && mc.thePlayer.isInWater()) {
				mc.thePlayer.jump();
			}
		}
	}
	
	public void renderEntities(float f) {
		this.espUtils.Method97(f);
	}
	
	private void Method51() {
		if(Hooks.setts("ka")) {
			float kaRadius = 4.0F;
			this.Field46.clear();
			this.Field46.addAll(this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.thePlayer, this.mc.thePlayer.boundingBox.expand((double)kaRadius, (double)kaRadius, (double)kaRadius)));
			
			Iterator i$ = this.Field46.iterator();
			while(i$.hasNext()) {
				Entity e = (Entity)i$.next();
				if(!e.isDead) {
					boolean attack = false;
					if(Hooks.setts("kap") && e instanceof EntityPlayer && !this.mc.thePlayer.getEntityName().equals(e.getEntityName()) && !this.kaFriends.Method26(e.getEntityName())) {
						attack = true;
					}
					
					if(Hooks.setts("kam") && e instanceof EntityMob) {
						attack = true;
					}
					
					if(Hooks.setts("kaa") && e instanceof EntityAnimal) {
						attack = true;
					}
					
					if(attack) {
						mc.thePlayer.swingItem();
						mc.playerController.attackEntity(mc.thePlayer, e);
					}
				}
			}
		}
	}
	
	private void Method52(FontRenderer fr, int width, int height) {
		int yPos = fr.FONT_HEIGHT + 4;
		if(Hooks.setts("eradar")) {
			yPos += this.Field34.Method153(width, height, yPos);
			
			if(Hooks.setts("enear")) {
				List players = this.mc.theWorld.playerEntities;
				
				Iterator i$ = players.iterator();
				while(i$.hasNext()) {
					EntityPlayer ep = (EntityPlayer)i$.next();
					if(!mc.thePlayer.getEntityName().equalsIgnoreCase(ep.getEntityName())) {
						double dist = (double)mc.thePlayer.getDistanceToEntity(ep);
						String st = String.format("§6%s §%c[%.1f]", new Object[]{ep.getEntityName(), Character.valueOf((char)(dist < 16.0D?(dist < 5.0D?'c':'e'):'a')), Double.valueOf(dist)});
						int w = fr.getStringWidth(st);
						fr.drawString(st, width - w - 2, yPos, this.WHITE, true);
						yPos += fr.FONT_HEIGHT;
					}
				}
			}
		}
	}
	
	private void Method53() {
		if(this.Field48 || this.Field50) {
			EntityClientPlayerMP ep = mc.thePlayer;
			ep.motionX = 0.0D;
			ep.motionY = 0.0D;
			ep.motionZ = 0.0D;
			int baseSpeed = this.getSettings().getInt("fls") + 1;
			double vect = (double)ep.rotationYaw + 90.0D;
			boolean focus = mc.inGameHasFocus;
			boolean slow = focus && Keyboard.isKeyDown(29);
			boolean space = focus && mc.gameSettings.keyBindJump.pressed;
			boolean shift = focus && mc.gameSettings.keyBindSneak.pressed;
			boolean keyW = focus && mc.gameSettings.keyBindForward.pressed;
			boolean keyA = focus && mc.gameSettings.keyBindLeft.pressed;
			boolean keyS = focus && mc.gameSettings.keyBindBack.pressed;
			boolean keyD = focus && mc.gameSettings.keyBindRight.pressed;
			if(space) {
				ep.motionY += (double)((slow?0.3F:0.85F) * (float)baseSpeed * 0.5F);
			}
			
			if(shift) {
				ep.motionY -= (double)((slow?0.3F:0.85F) * (float)baseSpeed * 0.5F);
			}
			
			if(keyA) {
				vect -= 90.0D;
				if(keyW) {
					vect += 45.0D;
				}
				
				if(keyS) {
					vect -= 45.0D;
				}
			} else if(keyD) {
				vect += 90.0D;
				if(keyW) {
					vect -= 45.0D;
				}
				
				if(keyS) {
					vect += 45.0D;
				}
			} else if(keyS) {
				vect += 180.0D;
			}
			
			float speed = (float)baseSpeed * 0.5F;
			if(slow) {
				speed /= 4.0F;
			}
			
			if(keyW || keyA || keyS || keyD) {
				ep.motionX = Math.cos(Math.toRadians(vect)) / 1.5D * (double)speed;
				ep.motionZ = Math.sin(Math.toRadians(vect)) / 1.5D * (double)speed;
			}
			
			if(Hooks.setts("flb") && this.Field48) {
				ep.motionY -= 0.05D;
			}
		}
	}
	
	public void Method54(String key, Object val) {
		if(val instanceof Boolean) {
			this.Method55(key, ((Boolean)val).booleanValue());
		}
	}
	
	private void Method55(String key, boolean value) {
		this.rem.Method2(this.settings, key, value);
		if(key.equalsIgnoreCase("mn")) {
			this.Field49 = value;
		} else if(key.equalsIgnoreCase("xr")) {
			Hooks.xray = value;
			mc.renderGlobal.loadRenderers();
		} else if(key.equalsIgnoreCase("fb")) {
			mc.gameSettings.gammaSetting = value ? 30.0F : 1.0F;
			this.Field47 = value;
		} else if(key.equalsIgnoreCase("fl")) {
			this.Field48 = value;
		} else if(key.equalsIgnoreCase("nf")) {
			Hooks.nofall = value;
		} else if(key.equalsIgnoreCase("fc")) {
			this.Field50 = value;
			if(value) {
				EntityClientPlayerMP eo = mc.thePlayer;
				this.Field51 = eo.posX;
				this.Field52 = eo.posY;
				this.Field53 = eo.posZ;
				this.Field54 = eo.rotationPitch;
				this.Field55 = eo.rotationYaw;
				EntityOtherPlayerMP eo1 = new EntityOtherPlayerMP(mc.theWorld, eo.getEntityName());
				eo1.setPositionAndRotation(this.Field51, this.Field52 - 1.600000023841858D, this.Field53, this.Field55, this.Field54);
				if(this.mc.thePlayer.isRiding()) {
					eo1.ridingEntity = mc.thePlayer.ridingEntity;
					eo1.ridingEntity.riddenByEntity = eo1;
					mc.thePlayer.ridingEntity = null;
				} else {
					eo1.ridingEntity = null;
				}
				
				mc.theWorld.addEntityToWorld(-1, eo1);
				mc.thePlayer.capabilities.allowFlying = true;
				mc.thePlayer.noClip = Hooks.setts("fcn");
			} else {
				EntityOtherPlayerMP eo2 = (EntityOtherPlayerMP)mc.theWorld.getEntityByID(-1);
				mc.theWorld.removeEntityFromWorld(-1);
				mc.thePlayer.setPositionAndRotation(this.Field51, this.Field52, this.Field53, this.Field55, this.Field54);
				mc.thePlayer.sendMotionUpdates();
				mc.thePlayer.capabilities.allowFlying = this.mc.thePlayer.capabilities.isCreativeMode;
				mc.thePlayer.noClip = false;
				if(eo2 != null) {
					if(eo2.ridingEntity != null) {
						mc.thePlayer.ridingEntity = eo2.ridingEntity;
						mc.thePlayer.ridingEntity.riddenByEntity = mc.thePlayer;
					}
				}
			}
		} else if(key.equalsIgnoreCase("fcn")) {
			if(this.Field50) {
				mc.thePlayer.noClip = value;
			}
		} else if(key.equalsIgnoreCase("st")) {
			if(mc.thePlayer != null) {
				mc.thePlayer.stepHeight = value ? 10.0F : 0.5F;
			}
		}
	}
	
	public void onKeyEvent(int key, boolean state) {
		if(key == Field17 && state) {
			if(mc.currentScreen == null) {
				this.tgui.display();
			}
		}
	}
	
	public void lavaHome() {
		if(System.currentTimeMillis() - this.lastTime >= 1000L) {
			this.lastTime = System.currentTimeMillis();
			mc.thePlayer.sendChatMessage("/home");
		}
	}
	
	public void Method58() {
		this.settingsUtils.clear();
		NBTTagCompound root = this.settingsUtils.Method99();
		NBTTagCompound core = new NBTTagCompound();
		this.settings.init(core);
		this.xray.init(core);
		this.kaFriends.Method31(core);
		this.keybind.Method25(core);
		this.favorites.Method123(core);
		this.kbs.Method13(root);
		root.setCompoundTag("Core", core);
		NBTTagCompound addons = new NBTTagCompound();
		
		IAddon[] arr$ = this.aloader.Method11();
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			IAddon a = arr$[i$];
			NBTTagCompound ac = new NBTTagCompound();
			a.saveConfig(ac);
			addons.setCompoundTag(a.getAddonID(), ac);
		}
		
		root.setCompoundTag("Addons", addons);
		this.settingsUtils.save(settingsFile);
	}
	
	public void settingsLoader() {
		this.settingsUtils.Method100(settingsFile);
		NBTTagCompound root = this.settingsUtils.Method99();
		NBTTagCompound core = root.getCompoundTag("Core");
		
		try {
			this.settings.loadSettings(core);
			this.xray.loadBlocks(core);
			this.kaFriends.Method30(core);
			this.keybind.Method24(core);
			this.favorites.Method122(core);
			this.kbs.Method12(root);
		} catch(Exception ex) {
			System.out.println("Exception while parsing config!");
			ex.printStackTrace();
		}
	}
	
	public void Method60() {
		NBTTagCompound addons = this.settingsUtils.Method99().getCompoundTag("Addons");
		
		IAddon[] arr$ = this.aloader.Method11();
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			IAddon a = arr$[i$];
			a.loadConfig(addons.getCompoundTag(a.getAddonID()));
		}
	}
	
	private void echo(String message) {
		mc.thePlayer.addChatMessage(message);
	}
	
	public void command(String cmd) {
		try {
			this.commandValidator(cmd);
		} catch (Throwable t) {
			this.echo("§cException: " + t.getClass().getCanonicalName() + ": " + t.getMessage());
		}
	}
	
	private void commandValidator(String cmd) {
		String[] sp = cmd.split(" ");
		if(sp.length != 0) {
			sp[0] = sp[0].toLowerCase().trim();
			if(cmdsHash.containsKey(sp[0])) {
				sp[0] = (String)cmdsHash.get(sp[0]);
			}
			
			try {
				Commands.Method1(sp);
			} catch (RuntimeException ex) {
				if(ex.getMessage() == null) {
					throw ex;
				} else if(ex.getMessage().equals("!NOCMD")) {
					this.echo("§cНеизвестная команда");
				} else {
					throw ex;
				}
			}
		}
	}
	
}
