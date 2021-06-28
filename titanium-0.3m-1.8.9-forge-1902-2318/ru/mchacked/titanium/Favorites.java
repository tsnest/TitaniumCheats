package ru.mchacked.titanium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class Favorites {
	
	private ArrayList Field98 = new ArrayList();
	
	public ModuleRegistrator[] Method119() {
		return (ModuleRegistrator[])this.Field98.toArray(new ModuleRegistrator[0]);
	}
	
	public Set asSet() {
		HashSet r = new HashSet();
		r.addAll(this.Field98);
		return r;
	}
	
	public void Method121(Set vals) {
		this.Field98.clear();
		
		ModuleRegistrator[][] arr$ = ModuleRegistrator.categories;
		int len$ = arr$.length;
		for(int i$ = 0; i$ < len$; ++i$) {
			ModuleRegistrator[] ss = arr$[i$];
			ModuleRegistrator[] arr$1 = ss;
			int len$1 = ss.length;
			for(int i$1 = 0; i$1 < len$1; ++i$1) {
				ModuleRegistrator s = arr$1[i$1];
				if(!this.Field98.contains(s) && vals.contains(s)) {
					this.Field98.add(s);
				}
			}
		}
	}
	
	public void Method122(NBTTagCompound nbt) {
		NBTTagList list = nbt.getTagList("Favorites", 1);
		this.Field98.clear();
		
		for(int i = 0; i < list.tagCount(); ++i) {
			NBTBase b = (NBTBase)list.tagList.get(i);
			if(b instanceof NBTTagString) {
				String s = ((NBTTagString)b).getString();
				if(!this.Field98.contains(s)) {
					this.Field98.add(ModuleRegistrator.getValue(s));
				}
			}
		}
	}
	
	public void Method123(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		Iterator i$ = this.Field98.iterator();
		while(i$.hasNext()) {
			ModuleRegistrator p = (ModuleRegistrator)i$.next();
			list.appendTag(new NBTTagString(p.key));
		}
		
		nbt.setTag("Favorites", list);
	}
	
}
