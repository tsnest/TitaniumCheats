package ru.mchacked.titanium;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.minecraft.client.Minecraft;
import ru.mchacked.titanium.api.IAddon;

public class AddonLoader {
	
	private URLClassLoader Field7;
	private IAddon[] Field8;
	private HashMap Field9 = new HashMap();
	
	public void Method8() {
		KeyBindSettings as = Titanium.getTitanium().Method42();
		if(!as.Method17()) {
			this.Field9.clear();
			File addonDir = new File(Minecraft.getMinecraft().mcDataDir, as.Method16());
			ArrayList addons = new ArrayList();
			if(!addonDir.exists() || !addonDir.isDirectory()) {
				addonDir.mkdirs();
			}
			
			File[] cl = addonDir.listFiles();
			int addURL = cl.length;
			
			int i;
			for(i = 0; i < addURL; ++i) {
				File i$ = cl[i];
				if(i$.getName().toLowerCase().endsWith(".zip") || i$.getName().toLowerCase().endsWith(".jar")) {
					try {
						ZipFile f = new ZipFile(i$);
						ZipEntry e = f.getEntry("addon.txt");
						if(e != null) {
							System.out.println("Found addon file: " + i$.getName());
							addons.add(i$);
						}
						
						f.close();
					} catch (Exception var13) {
						System.out.println("Failed to parse potentially addon file: " + i$.getName());
						var13.printStackTrace();
					}
				}
			}
			
			System.out.println("Found " + addons.size() + " addon(s)");
			ClassLoader var14 = AddonLoader.class.getClassLoader();
			if(!URLClassLoader.class.isAssignableFrom(var14.getClass())) {
				System.out.println("Current class loader can\'t be casted to URLClassLoader");
			} else {
				this.Field7 = (URLClassLoader)var14;
				
				Method var15;
				try {
					var15 = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
					var15.setAccessible(true);
				} catch (NoSuchMethodException var12) {
					System.out.println("Failed to get method addURL");
					var12.printStackTrace();
					return;
				}
				
				for(i = 0; i < addons.size(); ++i) {
					try {
						var15.invoke(this.Field7, new Object[]{((File)addons.get(i)).toURI().toURL()});
					} catch (Exception var11) {
						System.out.println("Failed to convert java.io.File to java.net.URL or invoke addURL()! Stopping loading");
						var11.printStackTrace();
						return;
					}
				}
				
				this.Field8 = new IAddon[addons.size()];
				i = 0;
				
				Iterator var16 = addons.iterator();
				while(var16.hasNext()) {
					File var17 = (File)var16.next();
					try {
						IAddon var18 = this.Method10(var17);
						this.Field8[i++] = var18;
					} catch (Exception var10) {
						System.out.println("Failed to initialize addon " + var17.getName());
						var10.printStackTrace();
						return;
					}
				}
			}
		}
	}
	
	private String Method9(ZipFile zf, String name) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = zf.getInputStream(zf.getEntry(name));
		byte[] b = new byte[128];
		
		int r;
		while((r = is.read(b)) != -1) {
			baos.write(b, 0, r);
		}
		
		return baos.toString("UTF8");
	}
	
	private IAddon Method10(File f) throws Exception {
		ZipFile zf = new ZipFile(f);
		String mainClass = this.Method9(zf, "addon.txt").trim();
		zf.close();
		Class clz = this.Field7.loadClass(mainClass);
		if(!IAddon.class.isAssignableFrom(clz)) {
			throw new IOException("Failed to load addon: main class do not implement IAddon!");
		} else {
			IAddon add = (IAddon)clz.newInstance();
			add.initialize();
			String id = add.getAddonID();
			if(this.Field9.containsKey(id)) {
				throw new RuntimeException("Addon ID " + id + " is already occupied by " + add);
			} else {
				this.Field9.put(id, add);
				System.out.println("Addon " + add.getAddonID() + " initialized");
				return add;
			}
		}
	}
	
	public IAddon[] Method11() {
		return this.Field8;
	}
	
}
