package ru.mchacked.titanium;

import java.util.Iterator;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import ru.mchacked.titanium.api.Hooks;
import ru.mchacked.titanium.api.IMainGui;
import ru.mchacked.titanium.api.IWindow;

public class TGui extends GuiScreen implements IMainGui {
	
	private static int Field59 = 5;
	private boolean Field60;
	private boolean initialized = false;
	private AddonUtils Field64 = new AddonUtils();
	private GuiRenderer Field65;
	private int Field66;
	private int Field67;
	private static String copyright;
	private GuiTextField Field69;
	
	public String Method65() {
		return copyright != null ? copyright : (copyright = "Topic of the rebuild: vk.cc/6MZ2cM");
	}
	
	@Override
	public FontRenderer getFontRenderer() {
		return super.fontRenderer;
	}
	
	public void init() {
		this.showWindow(new WindowMain());
	}
	
	@Override
	public void display() {
		this.Field60 = true;
		Minecraft.getMinecraft().displayGuiScreen(this);
	}
	
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		
		Iterator i$ = this.Field64.Method128().iterator();
		while(i$.hasNext()) {
			GuiRenderer ww = (GuiRenderer)i$.next();
			int w = ww.Field155.getWidth();
			int h = ww.Field155.getHeight();
			if(ww.Field156 + w > super.width) {
				ww.Field156 = super.width - w - 4;
			}
			
			if(ww.Field157 + h > super.height) {
				ww.Field157 = super.height - h - 14;
			}
		}
		
		this.Field69 = new GuiTextField(super.fontRenderer, 4, super.height - 12, super.width - 8, 12);
		this.Field69.setFocused(true);
		this.Field69.setCanLoseFocus(false);
		this.Field69.setEnableBackgroundDrawing(false);
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static boolean Method69(int cx, int cy, int x, int y, int w, int h) {
		return cx > x && cy > y && cx < x + w && cy < y + h;
	}
	
	@Override
	public void drawScreen(int mX, int mY, float par3) {
		if(this.Field65 != null) {
			this.mouseMovedOrUp(mX, mY, -1);
		}
		
		if(!Keyboard.isKeyDown(Titanium.Field17)) {
			this.Field60 = false;
		}
		
		if(!this.initialized) {
			this.init();
			this.initialized = true;
		}
		
		drawRect(2, super.height - 14, super.width - 2, super.height - 2, -2143289344);
		super.fontRenderer.drawString(Titanium.Method46() + " §a [GUI]", 1, 1, 16777215, true);
		GuiRenderer mww = null;
		
		GuiRenderer copy_w;
		Iterator copyright = this.Field64.Method128().iterator();
		while(copyright.hasNext()) {
			copy_w = (GuiRenderer)copyright.next();
			if(Method69(mX, mY, copy_w.Field156, copy_w.Field157, copy_w.Method184(), copy_w.Method185())) {
				mww = copy_w;
				break;
			}
		}
		
		copyright = this.Field64.Method127().iterator();
		while(copyright.hasNext()) {
			copy_w = (GuiRenderer)copyright.next();
			if(copy_w == mww) {
				copy_w.Method188(super.fontRenderer, mX - copy_w.Field156, mY - copy_w.Field157);
			} else {
				copy_w.Method188(super.fontRenderer, -999, -999);
			}
		}
		
		this.Field69.drawTextBox();
		String copyright1 = this.Method65();
		int copy_w1 = super.fontRenderer.getStringWidth(copyright1);
		super.fontRenderer.drawString(copyright1, super.width - copy_w1 - 2, 2, 16777215, true);
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int x = Mouse.getEventX() * super.width / super.mc.displayWidth;
		int y = super.height - Mouse.getEventY() * super.height / super.mc.displayHeight - 1;
		int d = Mouse.getEventDWheel();
		if(d != 0) {
			Iterator i$ = this.Field64.Method127().iterator();
			while(i$.hasNext()) {
				GuiRenderer ww = (GuiRenderer)i$.next();
				if(Method69(x, y, ww.Field156, ww.Field157, ww.Method184(), ww.Method185())) {
					ww.scroll(x - ww.Field156, y - ww.Field157, d);
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		if(!this.Field60) {
			if(par2 != Titanium.Field17 && par2 != 1) {
				if(par2 == 28) {
					Titanium.getTitanium().command(this.Field69.getText());
					this.Field69.setText("");
					super.mc.displayGuiScreen((GuiScreen)null);
				} else {
					this.Field69.textboxKeyTyped(par1, par2);
				}
			} else {
				super.mc.displayGuiScreen((GuiScreen)null);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int xPos, int yPos, int btn) {
		super.mouseClicked(xPos, yPos, btn);
		int close_w = super.fontRenderer.getStringWidth("X");
		
		Iterator i$ = this.Field64.Method128().iterator();
		while(i$.hasNext()) {
			GuiRenderer wnd = (GuiRenderer)i$.next();
			if(wnd.Method189(xPos - wnd.Field156, yPos - wnd.Field157)) {
				this.Field65 = wnd;
				this.Field66 = xPos;
				this.Field67 = yPos;
				break;
			}
			
			if(Method69(xPos, yPos, wnd.Field156, wnd.Field157, wnd.Method184(), wnd.Method185())) {
				wnd.Method186(xPos - wnd.Field156, yPos - wnd.Field157);
				break;
			}
		}
	}
	
	@Override
	protected void mouseMovedOrUp(int xPos, int yPos, int btn) {
		super.mouseMovedOrUp(xPos, yPos, btn);
		if(this.Field65 != null) {
			if(btn < 0) {
				this.Field65.Field156 += xPos - this.Field66;
				this.Field65.Field157 += yPos - this.Field67;
				this.Field66 = xPos;
				this.Field67 = yPos;
			} else {
				if(Hooks.setts("gd")) {
					this.Field65.Field156 = Math.round((float)(this.Field65.Field156 / Field59)) * Field59;
					this.Field65.Field157 = Math.round((float)(this.Field65.Field157 / Field59)) * Field59;
				}
				
				this.Field65 = null;
			}
		}
	}
	
	@Override
	public void showWindow(IWindow w) {
		Iterator ww = this.Field64.Method128().iterator();
		GuiRenderer ww1;
		do {
			if(!ww.hasNext()) {
				GuiRenderer ww2 = new GuiRenderer();
				ww2.Field155 = w;
				ww2.Field156 = (super.width - w.getWidth()) / 2 + (new Random().nextInt(11) - 5);
				ww2.Field157 = (super.height - w.getHeight()) / 2 + (new Random().nextInt(11) - 5);
				this.Field64.add(ww2);
				w.onShow();
				return;
			}
			
			ww1 = (GuiRenderer)ww.next();
		} while(ww1.Field155 != w);
	}
	
	@Override
	public void hideWindow(IWindow w) {
		Iterator it = this.Field64.Method127().iterator();
		GuiRenderer wwc = null;
		while(it.hasNext()) {
			GuiRenderer ww = (GuiRenderer)it.next();
			if(ww.Field155 == w) {
				w.onClose();
				wwc = ww;
			}
		}
		
		if(wwc != null) {
			this.Field64.remove(wwc);
		}
	}
	
	@Override
	public void flush() {
		Iterator i$ = this.Field64.Method127().iterator();
		while(i$.hasNext()) {
			GuiRenderer ww = (GuiRenderer)i$.next();
			ww.Field155.flush();
		}
	}
	
}
