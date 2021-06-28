package ru.mchacked.titanium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import ru.mchacked.titanium.api.Hooks;

public class EspUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	private long lastTime = 0L;
	private TileEntity[] Field85 = new TileEntity[0];
	private Entity[] Field86 = new Entity[0];
	
	public double Method81(double x, double y, double z) {
		double dx = x - mc.thePlayer.posX;
		double dy = y - mc.thePlayer.posY;
		double dz = z - mc.thePlayer.posZ;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public float Method82(double x, double y, double z) {
		return this.Method83(this.Method81(x, y, z));
	}
	
	public float Method83(double d) {
		return d > 10.0D?1.0F:(float)Math.sqrt((10.0D - d) * 4.0D + 1.0D);
	}
	
	public float Method84(double d) {
		return d > 10.0D?1.0F:(d < 0.5D?0.0F:(float)((d - 0.5D) / 9.5D));
	}
	
	public float Method85(double x, double y, double z) {
		return this.Method84(this.Method81(x, y, z));
	}
	
	private AxisAlignedBB Method86(double x1, double y1, double z1, double x2, double y2, double z2) {
		return AxisAlignedBB.getAABBPool().getAABB(x1, y1, z1, x2, y2, z2);
	}
	
	private boolean Method88(TileEntityChest tec) {
		int id = tec.getWorldObj().getBlockId(tec.xCoord, tec.yCoord, tec.zCoord);
		if(id >= 0 && id <= 4095) {
			Block b = Block.blocksList[id];
			return b != null && b instanceof BlockChest?((BlockChest)b).chestType != 0:false;
		} else {
			return false;
		}
	}
	
	private void Method89(TileEntityChest tec) {
		if(tec.adjacentChestXPos == null && tec.adjacentChestZPosition == null) {
			Tessellator.instance.setColorOpaque(255, 255, 255);
			int x = tec.xCoord;
			int y = tec.yCoord;
			int z = tec.zCoord;
			AxisAlignedBB aabb = this.Method86((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
			if(tec.adjacentChestXNeg != null) {
				aabb = aabb.addCoord(-1.0D, 0.0D, 0.0D);
			}
			
			if(tec.adjacentChestZNeg != null) {
				aabb = aabb.addCoord(0.0D, 0.0D, -1.0D);
			}
			
			GL11.glLineWidth(this.Method82((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D));
			GL11.glColor4f(1.0F, this.Method88(tec)?0.6F:1.0F, 0.0F, this.Method85((double)x, (double)y, (double)z));
			RenderUtils.Method147(aabb);
		}
	}
	
	private void Method90(TileEntity tec) {
		Tessellator.instance.setColorOpaque(255, 255, 255);
		int x = tec.xCoord;
		int y = tec.yCoord;
		int z = tec.zCoord;
		AxisAlignedBB aabb = this.Method86((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
		GL11.glLineWidth(this.Method82((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D));
		GL11.glColor4f(0.0F, 1.0F, 1.0F, this.Method85((double)x, (double)y, (double)z));
		RenderUtils.Method147(aabb);
	}
	
	private void Method91(TileEntityMobSpawner tem) {
		Tessellator.instance.setColorOpaque(255, 255, 255);
		int x = tem.xCoord;
		int y = tem.yCoord;
		int z = tem.zCoord;
		double d = this.Method81((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D);
		AxisAlignedBB aabb = this.Method86((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
		GL11.glLineWidth(this.Method83(d));
		GL11.glColor4f(1.0F, 0.3F, 0.3F, this.Method84(d));
		RenderUtils.Method147(aabb);
		if(d < 32.0D && d > 4.0D) {
			MobSpawnerBaseLogic msbl = tem.getSpawnerLogic();
			String mobID = msbl.getEntityNameToSpawn();
			float a = this.Method84(d);
			if(mobID.equals("Skeleton")) {
				GL11.glColor4f(0.6F, 0.6F, 0.6F, a);
			} else if(mobID.equals("Spider")) {
				GL11.glColor4f(0.2F, 0.2F, 0.2F, a);
			} else if(mobID.equals("Zombie")) {
				GL11.glColor4f(0.0F, 0.7F, 0.2F, a);
			} else {
				if(!mobID.equals("CaveSpider")) {
					return;
				}
				
				GL11.glColor4f(0.0F, 0.3F, 0.6F, a);
			}
			
			RenderUtils.Method147(aabb.expand(-0.20000000298023224D, -0.20000000298023224D, -0.20000000298023224D));
		}
	}
	
	private void Method92(EntityMinecart emc) {
		Tessellator.instance.setColorOpaque(255, 255, 255);
		double x = emc.posX;
		double y = emc.posY;
		double z = emc.posZ;
		double r = 0.5D;
		GL11.glLineWidth(this.Method82(x, y, z));
		GL11.glColor3f(0.6F, 1.0F, 0.0F);
		RenderUtils.Method147(this.Method86(x - r, y - r, z - r, x + r, y + r, z + r));
	}
	
	private void Method93(float f) {
		TileEntity e;
		if(System.currentTimeMillis() - this.lastTime > 2000L) {
			ArrayList arr$ = new ArrayList();
			List len$ = mc.theWorld.loadedTileEntityList;
			Iterator i$ = len$.iterator();
			while(i$.hasNext()) {
				e = (TileEntity)i$.next();
				if(e instanceof TileEntityChest) {
					arr$.add(e);
				}
				
				if(e instanceof TileEntityMobSpawner) {
					arr$.add(e);
				}
				
				if(e.getClass().getCanonicalName().startsWith("cpw.mods.ironchest.TileEntity")) {
					arr$.add(e);
				}
			}
			
			ArrayList var11 = new ArrayList();
			List var13 = mc.theWorld.loadedEntityList;
			
			Iterator i$1 = var13.iterator();
			while(i$1.hasNext()) {
				Entity e1 = (Entity)i$1.next();
				if(e1 instanceof EntityMinecartChest) {
					var11.add(e1);
				}
			}
			
			this.lastTime = System.currentTimeMillis();
			Arrays.fill(this.Field85, (Object)null);
			Arrays.fill(this.Field86, (Object)null);
			this.Field85 = (TileEntity[])arr$.toArray(this.Field85);
			this.Field86 = (Entity[])var11.toArray(this.Field86);
		}
		
		TileEntity[] var8 = this.Field85;
		int var10 = var8.length;
		
		int var12;
		for(var12 = 0; var12 < var10; ++var12) {
			e = var8[var12];
			if(e != null) {
				if(e instanceof TileEntityChest) {
					this.Method89((TileEntityChest)e);
				}
				
				if(e instanceof TileEntityMobSpawner) {
					this.Method91((TileEntityMobSpawner)e);
				}
				
				if(e.getClass().getCanonicalName().startsWith("cpw.mods.ironchest.TileEntity")) {
					this.Method90(e);
				}
			}
		}
		
		Entity[] var9 = this.Field86;
		var10 = var9.length;
		
		for(var12 = 0; var12 < var10; ++var12) {
			Entity var14 = var9[var12];
			if(var14 != null && var14 instanceof EntityMinecart) {
				this.Method92((EntityMinecart)var14);
			}
		}
	}
	
	private void Method94(float f) {
		Iterator i$ = mc.theWorld.playerEntities.iterator();
		while(i$.hasNext()) {
			Object e = i$.next();
			if(e instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer)e;
				if(!ep.username.equalsIgnoreCase(mc.getSession().getUsername())) {
					double d = this.Method81(ep.posX, ep.posY, ep.posZ);
					float a = this.Method84(d);
					GL11.glColor4f(1.0F, 0.3F, 0.3F, a);
					GL11.glLineWidth(this.Method83(d));
					RenderUtils.Method147(ep.boundingBox);
				}
			}
		}
	}
	
	private void Method95(float f) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(8512);
		EntityClientPlayerMP ep = mc.thePlayer;
		double xx = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)f;
		double yy = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)f;
		double zz = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)f;
		GL11.glTranslated(-xx, -yy, -zz);
		GL11.glDisable(2896);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
	}
	
	private void Method96(float f) {
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	public void Method97(float f) {
		if(mc.thePlayer != null) {
			if(mc.theWorld != null) {
				this.Method95(f);
				if(Hooks.setts("cd")) {
					this.Method93(f);
				}
				
				if(Hooks.setts("pd")) {
					this.Method94(f);
				}
				
				this.Method96(f);
			}
		}
	}
	
}
