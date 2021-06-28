package ru.mchacked.titanium;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import ru.mchacked.titanium.api.IAddon;

public class WindowAddons extends TWindow {
	
	private IAddon[] Field142 = Titanium.getTitanium().Method41().Method11();
	private int Field143 = -1;
	private long lastTime = 0L;
	
	public WindowAddons(String caption, int width) {
		super(caption, width);
	}
	
	@Override
	protected String getText(int i, int rp) {
		return this.Field142.length == 0 ? "§7<no addons installed>" : this.Field142[i].getShortName();
	}
	
	@Override
	protected int getPosition(int i, int rp) {
		return this.Field142.length == 0 ? 1 : 0;
	}
	
	@Override
	protected boolean canHighlight(int i) {
		return this.Field142.length != 0;
	}
	
	@Override
	protected void onEntryRender(int i) {
		if(this.Field142.length != 0) {
			if(this.Field143 != i) {
				this.Field143 = i;
				this.lastTime = System.currentTimeMillis();
			}
		}
	}
	
	@Override
	protected void onEntryClick(int i) {
		if(this.Field142.length != 0) {
			this.Field142[i].onInfoClick();
		}
	}
	
	@Override
	public void onRender(int x, int y, int mx, int my) {
		super.onRender(x, y, mx, my);
		if(this.Field143 >= 0 && this.Field143 < this.Field142.length) {
			if(System.currentTimeMillis() - this.lastTime >= 1000L) {
				byte hb = 2;
				int hx = x + mx + 5;
				int hy = y + my + 5;
				FontRenderer fr = Titanium.getTitanium().getTGui().getFontRenderer();
				String longDescr = this.Field142[this.Field143].getFullName();
				List split = fr.listFormattedStringToWidth(longDescr, 150);
				int hw = 0;
				int hh = fr.FONT_HEIGHT * split.size();
				
				Iterator cy = split.iterator();
				while(cy.hasNext()) {
					String i$ = (String)cy.next();
					int s = fr.getStringWidth(i$);
					if(s > hw) {
						hw = s;
					}
				}
				
				TGui.drawRect(hx, hy, hx + hw + hb * 2, hy + hh + hb * 2, -1610612736);
				
				int cy1 = hy + hb;
				for(Iterator i$1 = split.iterator(); i$1.hasNext(); cy1 += fr.FONT_HEIGHT) {
					String s1 = (String)i$1.next();
					fr.drawString(s1, hx + hb, cy1, 16777215);
				}
			}
		}
	}
	
	@Override
	protected int getSize() {
		return this.Field142.length == 0 ? 1 : this.Field142.length;
	}
	
}
