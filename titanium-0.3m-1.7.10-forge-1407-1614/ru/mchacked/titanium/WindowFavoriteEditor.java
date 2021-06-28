package ru.mchacked.titanium;

import java.util.ArrayList;
import java.util.HashSet;

public class WindowFavoriteEditor extends TWindow {
	
	private int Field136;
	private int Field137;
	private ArrayList Field138 = new ArrayList();
	private HashSet Field139 = new HashSet();
	private boolean Field141 = false;
	
	public WindowFavoriteEditor() {
		super("Favorite edit", 150);
		this.Field139.clear();
		this.Field138.clear();
		
		ModuleRegistrator[][] arr$ = ModuleRegistrator.categories;
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			ModuleRegistrator[] ss = arr$[i$];
			ModuleRegistrator[] arr$1 = ss;
			int len$1 = ss.length;
			for(int i$1 = 0; i$1 < len$1; ++i$1) {
				ModuleRegistrator k = arr$1[i$1];
				if(!this.Field138.contains(k.key)) {
					this.Field138.add(k);
				}
			}
		}
		
		this.Field136 = 0;
		this.Field137 = this.Field138.size() - 8;
	}
	
	@Override
	protected String getText(int i, int rp) {
		return i == 0?"§" + (this.Field136 == 0?'8':'e') + "^^  UP  ^^":(i == 1?"":(i < 10?this.Method164(i - 2 + this.Field136):(i == 10?"":(i == 11?"§" + (this.Field136 < this.Field137?'e':'8') + "vv DOWN vv":(i == 12?"§aSave":(i == 13?"§cCancel":""))))));
	}
	
	private String Method164(int i) {
		ModuleRegistrator p = (ModuleRegistrator)this.Field138.get(i);
		boolean act = this.Field139.contains(p);
		return "§" + (act?'a':'8') + p.fullName;
	}
	
	@Override
	protected int getPosition(int i, int rp) {
		return i == 0?1:(i >= 11?1:0);
	}
	
	@Override
	protected boolean canHighlight(int i) {
		return i != 1 && i != 10;
	}
	
	@Override
	protected int getSize() {
		return 14;
	}
	
	@Override
	protected void onEntryClick(int i) {
		if(i == 0) {
			this.Field136 = Math.max(this.Field136 - 2, 0);
		}
		
		if(i == 11) {
			this.Field136 = Math.min(this.Field136 + 2, this.Field137);
		}
		
		if(i == 12) {
			Titanium.getTitanium().Method35().Method121(this.Field139);
			WindowRegistrator.getHashedWindow("fav").flush();
		}
		
		if(i == 13) {
			this.Field139.clear();
			this.Field139.addAll(Titanium.getTitanium().Method35().asSet());
		}
		
		if(i > 1 && i < 10) {
			int rI = i - 2 + this.Field136;
			ModuleRegistrator p = (ModuleRegistrator)this.Field138.get(rI);
			if(this.Field139.contains(p)) {
				this.Field139.remove(p);
			} else {
				this.Field139.add(p);
			}
		}
	}
	
	@Override
	public void onMouseScroll(int x, int y, int delta) {
		this.Field136 = Math.max(0, Math.min(this.Field136 + delta / -120, this.Field137));
	}
	
	@Override
	public void flush() {
		super.flush();
		this.Field139.clear();
		this.Field139.addAll(Titanium.getTitanium().Method35().asSet());
	}
	
	@Override
	public void onShow() {
		if(!this.Field141) {
			this.Field141 = true;
			this.flush();
		}
	}
	
}
