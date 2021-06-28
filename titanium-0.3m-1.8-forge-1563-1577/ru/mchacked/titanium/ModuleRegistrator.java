package ru.mchacked.titanium;

import java.util.HashMap;

public enum ModuleRegistrator {
	
	XRAY("XRAY", 0, "xr", "X-Ray", (String)null, 1, false, true, new ButtonValue(false)),
	FLYHACK("FLYHACK", 1, "fl", "Flyhack", (String)null, 1, true, false, new ButtonValue(false)),
	FLYBYPASS("FLYBYPASS", 2, "flb", "Fly bypass", (String)null, 1, true, true, new ButtonValue(false)),
	FULLBRIGHT("FULLBRIGHT", 3, "fb", "Fullbright", (String)null, 1, false, true, new ButtonValue(false)),
	NOFALL("NOFALL", 4, "nf", "Nofall", (String)null, 1, true, true, new ButtonValue(false)),
	SPEEDMINING("SPEEDMINING", 5, "sm", "Speed mining", (String)null, 1, true, true, new ButtonValue(false)),
	IGNOREWEB("IGNOREWEB", 6, "iw", "Ignore web", (String)null, 1, false, true, new ButtonValue(false)),
	SPEEDHACK("SPEEDHACK", 7, "sh", "Speedhack", (String)null, 1, true, true, new ButtonValue(false)),
	SHSPEED("SHSPEED", 8, "wls", "Walk speed", (String)null, 2, false, true, new ButtonValueInt(1, 10, 1)),
	FLSPEED("FLSPEED", 9, "fls", "Fly speed", (String)null, 2, false, true, new ButtonValueInt(1, 10, 1)),
	PERMASNEAK("PERMASNEAK", 10, "ps", "Perma sneak", (String)null, 1, true, true, new ButtonValue(false)),
	FREECAM("FREECAM", 11, "fc", "Freecam", (String)null, 1, false, false, new ButtonValue(false)),
	FREECAM_NOCLIP("FREECAM_NOCLIP", 12, "fcn", "Freecam noclip", (String)null, 1, false, true, new ButtonValue(false)),
	IGNOREFLUID("IGNOREFLUID", 13, "if", "Ignore fluid", (String)null, 1, true, true, new ButtonValue(false)),
	STEP("STEP", 14, "st", "Step", (String)null, 1, true, true, new ButtonValue(false)),
	KA_ENABLE("KA_ENABLE", 15, "ka", "Enable", "Kill aura: Enable", 1, true, true, new ButtonValue(false)),
	KA_MOBS("KA_MOBS", 16, "kam", "Mobs", "Kill aura: Mobs", 1, false, true, new ButtonValue(false)),
	KA_PLAYERS("KA_PLAYERS", 17, "kap", "Players", "Kill aura: Players", 1, false, true, new ButtonValue(false)),
	KA_ANIMALS("KA_ANIMALS", 18, "kaa", "Animals", "Kill aura: Animals", 1, false, true, new ButtonValue(false)),
	ANTIAFK("ANTIAFK", 19, "aa", "Anti AFK", (String)null, 1, false, false, new ButtonValue(false)),
	E_NEAR("E_NEAR", 20, "enear", "Near players", "ESP: Near players", 1, false, true, new ButtonValue(false)),
	E_RADAR("E_RADAR", 21, "eradar", "Radar", "ESP: Radar", 1, false, true, new ButtonValue(false)),
	E_HP("E_HP", 22, "ehp", "Show HP", "ESP: HP", 1, false, true, new ButtonValue(false)),
	E_AP("E_AP", 23, "eap", "Show armor", "ESP: AP", 1, false, true, new ButtonValue(false)),
	E_DI("E_DI", 24, "edi", "Show distance", "ESP: DI", 1, false, true, new ButtonValue(false)),
	INVISIBLEBYPASS("INVISIBLEBYPASS", 25, "ib", "Invisiblity bypass", (String)null, 1, true, true, new ButtonValue(false)),
	AUTOFISH("AUTOFISH", 26, "af", "Auto fish", (String)null, 1, true, false, new ButtonValue(false)),
	ALWAYSSPRINT("ALWAYSSPRINT", 27, "as", "Always sprint", (String)null, 1, true, true, new ButtonValue(false)),
	DOLPHIN("DOLPHIN", 28, "do", "Dolphin", (String)null, 1, true, true, new ButtonValue(false)),
	MENU("MENU", 29, "mn", "Active cheats", (String)null, 1, false, true, new ButtonValue(false)),
	SNAPTOGRID("SNAPTOGRID", 30, "gd", "Snap to grid", (String)null, 1, false, true, new ButtonValue(false)),
	LAVAHOME("LAVAHOME", 31, "lh", "Lava /home", (String)null, 1, true, true, new ButtonValue(false)),
	CHESTDETECTOR("CHESTDETECTOR", 32, "cd", "Chest detector", (String)null, 1, false, true, new ButtonValue(false)),
	PLAYERDETECTOR("PLAYERDETECTOR", 33, "pd", "Player detector", (String)null, 1, false, true, new ButtonValue(false)),
	JESUS("JESUS", 34, "je", "Jesus", (String)null, 1, true, true, new ButtonValue(false));
	
	public static final ModuleRegistrator[] movement = new ModuleRegistrator[]{FLYHACK, FLYBYPASS, FREECAM, FREECAM_NOCLIP, ALWAYSSPRINT, PERMASNEAK, SPEEDHACK, SHSPEED, FLSPEED, STEP, DOLPHIN, JESUS};
	public static final ModuleRegistrator[] killaura = new ModuleRegistrator[]{KA_ENABLE, KA_MOBS, KA_PLAYERS, KA_ANIMALS};
	public static final ModuleRegistrator[] visual = new ModuleRegistrator[]{XRAY, FULLBRIGHT, CHESTDETECTOR, PLAYERDETECTOR, E_NEAR, E_RADAR, E_HP, E_AP, E_DI, MENU};
	public static final ModuleRegistrator[] other = new ModuleRegistrator[]{NOFALL, SPEEDMINING, IGNOREWEB, IGNOREFLUID, INVISIBLEBYPASS, AUTOFISH, ANTIAFK, LAVAHOME};
	
	public static final ModuleRegistrator[][] categories = new ModuleRegistrator[][]{movement, killaura, visual, other};
	
	public String key;
	public String shortName;
	public String fullName;
	public int buttonType;
	public boolean displayed;
	public boolean stored;
	public ButtonValueA buttonValue;
	private static HashMap<String, ModuleRegistrator> modulesHash = new HashMap<String, ModuleRegistrator>();
	
	static {
		for(ModuleRegistrator m : values()) {
			modulesHash.put(m.key, m);
		}
	}
	
	private ModuleRegistrator(String cname, int cnumber, String key, String shortName, String fullName, int buttonType, boolean displayed, boolean stored, ButtonValueA buttonValue) {
		this.key = key;
		this.shortName = shortName;
		this.fullName = fullName == null ? shortName : fullName;
		this.buttonType = buttonType;
		this.displayed = displayed;
		this.stored = stored;
		this.buttonValue = buttonValue;
	}
	
	public static ModuleRegistrator getValue(String s) {
		return (ModuleRegistrator)modulesHash.get(s);
	}
	
	public String getStatus(Object o) {
		switch(this.buttonType) {
			case 1:
				return ((Boolean)o).booleanValue() ? "§aON" : "§cOFF";
			case 2:
				return String.valueOf(((Integer)o).intValue());
			default:
				return null;
		}
	}
	
	public Object getType(String txt) throws IllegalArgumentException {
		switch(this.buttonType) {
			case 1:
				if("true".equalsIgnoreCase(txt)) {
					return Boolean.TRUE;
				} else {
					if("false".equalsIgnoreCase(txt)) {
						return Boolean.FALSE;
					}
					
					throw new IllegalArgumentException("Unknown state of boolean property: " + txt);
				}
			case 2:
				try {
					return Integer.decode(txt);
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException("Unknown state of integer property: " + txt);
				}
			default:
				return null;
		}
	}
}
