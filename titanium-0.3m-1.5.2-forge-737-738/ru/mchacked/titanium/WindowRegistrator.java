package ru.mchacked.titanium;

import java.util.HashMap;

import ru.mchacked.titanium.api.IWindow;

public class WindowRegistrator {
	
	private static HashMap windows = new HashMap();
	
	static {
		windows.put("mov", new WindowBase(Titanium.getTitanium().getSettings(), ModuleRegistrator.movement, "Movement", 150));
		windows.put("etc", new WindowBase(Titanium.getTitanium().getSettings(), ModuleRegistrator.other, "Other", 150));
		windows.put("kaura", new WindowBase(Titanium.getTitanium().getSettings(), ModuleRegistrator.killaura, "Kill aura", 100));
		windows.put("esp", new WindowBase(Titanium.getTitanium().getSettings(), ModuleRegistrator.visual, "ESP & Visual", 150));
		windows.put("fsel", new WindowFavoriteEditor());
		windows.put("fav", new WindowFavorites(Titanium.getTitanium().getSettings(), 150));
		windows.put("about", new WindowAbout("About cheat", 150));
		windows.put("addons", new WindowAddons("Loaded addons", 150));
	}
	
	public static IWindow getHashedWindow(String k) {
		return (IWindow)windows.get(k);
	}
	
}
