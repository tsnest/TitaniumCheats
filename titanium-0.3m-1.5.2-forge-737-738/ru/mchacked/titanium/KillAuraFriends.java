package ru.mchacked.titanium;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class KillAuraFriends {
	
	private HashSet friendHash = new HashSet();
	
	public boolean Method26(String s) {
		return this.friendHash.contains(s);
	}
	
	public String[] Method27() {
		return (String[])this.friendHash.toArray(new String[this.friendHash.size()]);
	}
	
	public void Method28(String u) {
		if(!this.friendHash.contains(u)) {
			this.friendHash.add(u);
		}
	}
	
	public void Method29(String u) {
		this.friendHash.remove(u);
	}
	
	public void Method30(NBTTagCompound nbt) {
		NBTTagList list = nbt.getTagList("NoKillAura");
		for(int i = 0; i < list.tagCount(); ++i) {
			NBTBase b = list.tagAt(i);
			if(b instanceof NBTTagString) {
				String s = ((NBTTagString)b).data;
				this.Method28(s);
			}
		}
	}
	
	public void Method31(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		Iterator i$ = this.friendHash.iterator();
		while(i$.hasNext()) {
			String s = (String)i$.next();
			list.appendTag(new NBTTagString((String)null, s));
		}
		
		nbt.setTag("NoKillAura", list);
	}
	
}
