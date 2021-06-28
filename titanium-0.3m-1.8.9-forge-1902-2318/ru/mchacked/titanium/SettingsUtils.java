package ru.mchacked.titanium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class SettingsUtils {
	
	private NBTTagCompound Field87;
	
	public SettingsUtils() {
		this.clear();
	}
	
	public void clear() {
		this.Field87 = new NBTTagCompound();
	}
	
	public NBTTagCompound Method99() {
		return this.Field87;
	}
	
	public void Method100(File f) {
		if(f.exists()) {
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream(f);
				this.Field87 = CompressedStreamTools.readCompressed(fis);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if(fis != null) {
					try {
						fis.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
	
	public void save(File f) {
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(f);
			CompressedStreamTools.writeCompressed(this.Field87, fos);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
}
