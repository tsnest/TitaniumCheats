package ru.mchacked.titanium;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;

public class Settings implements ISettings {
	
	private HashMap values = new HashMap();
	
	public Object getValue(String key) {
		if(!this.values.containsKey(key)) {
			ModuleRegistrator p = ModuleRegistrator.getValue(key);
			this.values.put(key, this.getModuleStatus(p));
		}
		
		return this.values.get(key);
	}
	
	public void setValue(String key, Object o) {
		this.values.put(key, o);
		Titanium.getTitanium().Method54(key, o);
	}
	
	private Object getModuleStatus(ModuleRegistrator p) {
		switch(p.buttonType) {
			case 1:
				return Boolean.valueOf(((ButtonValue)p.buttonValue).def);
			case 2:
				return Integer.valueOf(((ButtonValueInt)p.buttonValue).def);
			default:
				return null;
		}
	}
	
	public boolean getBoolean(String key) {
		return ((Boolean)this.getValue(key)).booleanValue();
	}
	
	public int getInt(String key) {
		return ((Integer)this.getValue(key)).intValue();
	}
	
	public Object setCmd(String key) {
		ModuleRegistrator p = ModuleRegistrator.getValue(key);
		Object v = this.values.get(key);
		switch(p.buttonType) {
			case 1:
				v = Boolean.valueOf(!((Boolean)v).booleanValue());
				break;
			case 2:
				int i = ((Integer)v).intValue();
				ButtonValueInt s = (ButtonValueInt)p.buttonValue;
				i += s.step;
				if(i > s.end) {
					i = s.start;
				}
				
				v = Integer.valueOf(i);
		}
		
		this.setValue(key, v);
		return v;
	}
	
	public boolean isCmdExists(String key) {
		return ModuleRegistrator.getValue(key) != null;
	}
	
	public String getShortName(String key) {
		return ModuleRegistrator.getValue(key).shortName;
	}
	
	public String getName(String key) {
		return ModuleRegistrator.getValue(key).fullName;
	}
	
	public void loadSettings(NBTTagCompound nbt) {
		NBTTagCompound keys = nbt.getCompoundTag("Cheats");
		
		ModuleRegistrator[] arr$ = ModuleRegistrator.values();
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			ModuleRegistrator p = arr$[i$];
			if(p.stored) {
				if(!keys.hasKey(p.key)) {
					this.setValue(p.key, this.getModuleStatus(p));
				} else {
					switch(p.buttonType) {
						case 1:
							this.setValue(p.key, Boolean.valueOf(keys.getBoolean(p.key)));
							break;
						case 2:
							this.setValue(p.key, Integer.valueOf(keys.getInteger(p.key)));
					}
				}
			}
		}
	}
	
	public void init(NBTTagCompound nbt) {
		NBTTagCompound keys = new NBTTagCompound();
		
		ModuleRegistrator[] arr$ = ModuleRegistrator.values();
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			ModuleRegistrator p = arr$[i$];
			if(p.stored) {
				Object v = this.values.get(p.key);
				if(v == null) {
					v = this.getModuleStatus(p);
				}
				
				switch(p.buttonType) {
					case 1:
						keys.setBoolean(p.key, ((Boolean)v).booleanValue());
						break;
					case 2:
						keys.setInteger(p.key, ((Integer)v).intValue());
				}
			}
		}
		
		nbt.setCompoundTag("Cheats", keys);
	}
	
	public int getType(String key) {
		return ModuleRegistrator.getValue(key).buttonType;
	}
	
}
