package ru.mchacked.titanium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import ru.mchacked.titanium.api.IWindow;

public class WindowMain extends TWindow {
	
	private ArrayList Field128 = new ArrayList();
	private ArrayList Field129 = new ArrayList();
	private int count;
	private static ConcurrentHashMap Field131 = new ConcurrentHashMap();
	private static WindowMain Field132 = null;
	
	public WindowMain() {
		super("Main", 100);
		Field132 = this;
		this.put("mov", "Movement");
		this.put("kaura", "Kill aura settings");
		this.put("esp", "ESP & Visual");
		this.put("etc", "Other");
		this.put("fav", "Favorites");
		this.put("fsel", "Edit favorites");
		
		Iterator i$ = Field131.keySet().iterator();
		while(i$.hasNext()) {
			String s = (String)i$.next();
			this.Method159((IWindow)Field131.get(s), s);
		}
		
		this.count = this.Field129.size();
	}
	
	public static void Method158(IWindow w, String title) {
		if(Field132 == null) {
			Field131.put(title, w);
		} else {
			Field132.Method159(w, title);
		}
	}
	
	private synchronized void Method159(IWindow w, String title) {
		this.Field128.add(w);
		this.Field129.add(title);
		++this.count;
	}
	
	private void put(String k, String t) {
		this.Method159(WindowRegistrator.getHashedWindow(k), t);
	}
	
	@Override
	public boolean isCloseable() {
		return false;
	}
	
	@Override
	protected String getText(int i, int rp) {
		return i == this.count ? "About..." : (String)this.Field129.get(i);
	}
	
	@Override
	protected int getSize() {
		return this.count + 1;
	}
	
	@Override
	protected void onEntryClick(int i) {
		TGui gui = Titanium.getTitanium().getTGui();
		if(i == this.count) {
			gui.showWindow(WindowRegistrator.getHashedWindow("about"));
			gui.flush();
		} else {
			gui.showWindow((IWindow)this.Field128.get(i));
			gui.flush();
		}
	}
	
}
