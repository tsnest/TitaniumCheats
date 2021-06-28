package ru.mchacked.titanium;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import ru.mchacked.titanium.api.IWindow;

public abstract class TWindow implements IWindow {
	
	private String caption;
	private int width;
	
	public TWindow(String caption, int width) {
		this.caption = caption;
		this.width = width;
	}
	
	@Override
	public String getCaption() {
		return this.caption;
	}
	
	@Override
	public boolean isCloseable() {
		return true;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return 12 * this.getSize();
	}
	
	@Override
	public void onRender(int x, int y, int mx, int my) {
		FontRenderer fr = Titanium.getTitanium().getTGui().getFontRenderer();
		int s = my / 12;
		if(my < 0) {
			s = -1;
		}
		
		if(mx < 0 || mx > this.getWidth()) {
			s = -1;
		}
		
		for(int i = 0; i < this.getSize(); ++i) {
			if(s == i && this.canHighlight(i)) {
				GuiIngame.drawRect(x, y + s * 12, x + this.getWidth(), y + (s + 1) * 12, 553648127);
			}
			
			for(int p = 0; p < this.Method193(i); ++p) {
				String t = this.getText(i, p);
				int pos = this.getPosition(i, p);
				int tw = pos == 0 ? 0 : fr.getStringWidth(t);
				int rx = 0;
				switch(pos) {
					case 0:
						rx = 2;
						break;
					case 1:
						rx = (this.width - tw) / 2;
						break;
					case 2:
						rx = this.width - tw - 2;
				}
				
				fr.drawString(t, x + rx, y + 12 * i + 2, 16777215);
			}
		}
		
		this.onEntryRender(s);
	}
	
	protected abstract String getText(int var1, int var2);
	
	protected boolean canHighlight(int i) {
		return true;
	}
	
	protected abstract int getSize();
	
	protected void onEntryClick(int i) {  }
	
	protected int getPosition(int i, int rp) {
		return 0;
	}
	
	protected void onEntryRender(int i) {  }
	
	protected int Method193(int i) {
		return 1;
	}
	
	@Override
	public void onClick(int x, int y) {
		int i = y / 12;
		if(i >= 0 && i < this.getSize()) {
			this.onEntryClick(i);
		}
	}
	
	@Override
	public void flush() {  }
	
	@Override
	public void onMouseScroll(int x, int y, int delta) {  }
	
	@Override
	public void onClose() {  }
	
	@Override
	public void onShow() {  }
	
}
