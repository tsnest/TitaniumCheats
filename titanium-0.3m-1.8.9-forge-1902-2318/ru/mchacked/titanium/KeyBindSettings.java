package ru.mchacked.titanium;

import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;

public class KeyBindSettings {
	
	private NBTTagCompound Field10;
	
	public void Method12(NBTTagCompound nbt) {
		this.Field10 = (NBTTagCompound)nbt.getCompoundTag("Additional").copy();
	}
	
	public void Method13(NBTTagCompound nbt) {
		nbt.setTag("Additional", this.Field10);
	}
	
	public int Method14() {
		if(!this.Field10.hasKey("ActivationKey")) {
			return Titanium.btnMenuActivate;
		} else {
			NBTBase tag = this.Field10.getTag("ActivationKey");
			if(tag instanceof NBTTagInt) {
				return ((NBTTagInt)tag).getInt();
			} else if(tag instanceof NBTTagString) {
				String v = ((NBTTagString)tag).getString();
				try {
					Field e = Keyboard.class.getField(v);
					if((e.getModifiers() & 8) == 0) {
						throw new RuntimeException("Invalid modifier of key constant - not static");
					} else {
						return e.getInt((Object)null);
					}
				} catch (RuntimeException ex) {
					throw ex;
				} catch (Exception ex) {
					throw new RuntimeException("Failed to get key code from string", ex);
				}
			} else {
				throw new RuntimeException("Unknown tag type - Additional.ActivationKey must be either string or integer");
			}
		}
	}
	
	public String Method15() {
		return !this.Field10.hasKey("CommandPrefix")?Titanium.cmdPrefix:this.Field10.getString("CommandPrefix");
	}
	
	public String Method16() {
		return !this.Field10.hasKey("Cheatpacks")?"addons":this.Field10.getString("Cheatpacks");
	}
	
	public boolean Method17() {
		return this.Field10.getBoolean("DisableAddons");
	}
	
}
