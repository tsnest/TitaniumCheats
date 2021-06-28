package ru.mchacked.titanium;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import ru.mchacked.titanium.api.IKeyBind;

public class KeyBind {
	
	private boolean[] Field11 = new boolean[256];
	private IKeyBind[] Field12 = new IKeyBind[256];
	private String[] Field13 = new String[26];
	
	public KeyBind() {
		for(int i = 0; i < 256; ++i) {
			this.Field12[i] = null;
			this.Field11[i] = false;
			if(i < 26) {
				this.Field13[i] = null;
			}
		}
	}
	
	private boolean Method18(int num) {
		if(Minecraft.getMinecraft().currentScreen != null) {
			return false;
		} else if(Keyboard.isKeyDown(num) != this.Field11[num]) {
			boolean state = Keyboard.isKeyDown(num);
			this.Field11[num] = state;
			return state;
		} else {
			return false;
		}
	}
	
	public void Method19(int key) {
		if(this.Method18(key)) {
			IKeyBind bind = this.Field12[key];
			if(bind != null) {
				bind.onKeyEvent(key, this.Field11[key]);
			}
			
			String kn = Keyboard.getKeyName(key);
			if(kn != null && kn.length() == 1) {
				int n = kn.toLowerCase().charAt(0) - 97;
				if(n >= 0 && n < 26) {
					String cmd = this.Field13[n];
					if(cmd != null) {
						Titanium.getTitanium().command(cmd);
					}
				}
			}
		}
	}
	
	public void Method20(int key, IKeyBind bind) {
		this.Field12[key] = bind;
	}
	
	public void Method22(char key, String command) {
		key = Character.toLowerCase(key);
		if(key >= 97 && key <= 122) {
			this.Field13[key - 97] = command;
		}
	}
	
	public void Method23(char key) {
		key = Character.toLowerCase(key);
		if(key >= 97 && key <= 122) {
			this.Field13[key - 97] = null;
		}
	}
	
	public void Method24(NBTTagCompound nbt) {
		NBTTagCompound binds = nbt.getCompoundTag("Binds");
		for(char c = 97; c <= 122; ++c) {
			String s = String.valueOf(c);
			if(binds.hasKey(s)) {
				this.Field13[c - 97] = binds.getString(s);
			}
		}
	}
	
	public void Method25(NBTTagCompound nbt) {
		NBTTagCompound binds = new NBTTagCompound();
		for(char c = 97; c <= 122; ++c) {
			if(this.Field13[c - 97] != null) {
				binds.setString(String.valueOf(c), this.Field13[c - 97]);
			}
		}
		
		nbt.setCompoundTag("Binds", binds);
	}
	
}
