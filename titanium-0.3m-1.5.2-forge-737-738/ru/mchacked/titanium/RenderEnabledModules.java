package ru.mchacked.titanium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.client.gui.FontRenderer;

public class RenderEnabledModules {
	
	private ArrayList Field1 = new ArrayList();
	private ArrayList Field2 = new ArrayList();
	private HashSet Field3 = new HashSet();
	private int Field4 = 0;
	
	public RenderEnabledModules() {
		ModuleRegistrator[] arr$ = ModuleRegistrator.values();
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			ModuleRegistrator p = arr$[i$];
			if(p.displayed) {
				this.Field3.add(p.key);
			}
		}
	}
	
	public void Method2(Settings s, String key, boolean value) {
		if(this.Field3.contains(key)) {
			if(!value) {
				int i = this.Field1.indexOf(key);
				if(i < 0) {
					return;
				}
				
				this.Field1.remove(i);
				this.Field2.remove(i);
			} else {
				this.Field1.add(key);
				this.Field2.add(s.getName(key));
			}
			
			this.Field4 = 0;
		}
	}
	
	private void Method3(FontRenderer r) {
		this.Field4 = 0;
		
		Iterator i$ = this.Field1.iterator();
		while(i$.hasNext()) {
			String s = (String)i$.next();
			int w = r.getStringWidth("[" + s + "] ");
			if(w > this.Field4) {
				this.Field4 = w;
			}
		}
	}
	
	public void Method4(FontRenderer fr) {
		if(this.Field4 == 0) {
			this.Method3(fr);
		}
		
		for(int i = 0; i < this.Field1.size(); ++i) {
			int y = 20 + i * fr.FONT_HEIGHT;
			fr.drawString("§7[" + ((String)this.Field1.get(i)).toUpperCase() + "] ", 2, y, 16777215, true);
			fr.drawString("§e" + (String)this.Field2.get(i), 2 + this.Field4, y, 16777215, true);
		}
	}
	
}
