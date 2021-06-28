package ru.mchacked.titanium.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.mchacked.titanium.Titanium;
import ru.mchacked.titanium.XrayState;

public class Hooks {
	
	public static boolean xray;
	public static boolean nofall;
	private static long lastTime = 0L;
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void initialize() {
		(new Titanium()).init();
	}
	
	public static void hudRender(FontRenderer fr, int width, int height) {
		Titanium.getTitanium().hudRender(fr, width, height);
	}
	
	public static boolean noFall() {
		return nofall;
	}
	
	public static boolean xray() {
		return xray;
	}
	
	public static boolean xray0(int id, IBlockAccess iba, int x, int y, int z, int state) {
		if(state >= 0 && state <= 5) {
			state = XrayState.Field103[state];
			x += XrayState.Field104[state];
			y += XrayState.Field105[state];
			z += XrayState.Field106[state];
			
			Block block = iba.getBlockState(new BlockPos(x, y, z)).getBlock();
			int meta = block.getMetaFromState(iba.getBlockState(new BlockPos(x, y, z)));
			return Titanium.getTitanium().getXray().isXrayBlock(id, meta);
		} else {
			return Titanium.getTitanium().getXray().isXrayBlock(id, 0);
		}
	}
	
	public static boolean onCommand(String cmd) {
		if(!cmd.startsWith(Titanium.cmdPrefix)) {
			return false;
		} else {
			Titanium.getTitanium().command(cmd.substring(Titanium.cmdPrefix.length()));
			return true;
		}
	}
	
	public static boolean setts(String shortName) {
		Titanium hm = Titanium.getTitanium();
		if(hm == null) {
			return false;
		} else if(hm.getSettings() == null) {
			return false;
		} else {
			return Titanium.getTitanium().getSettings().getBoolean(shortName);
		}
	}
	
	public static boolean speedMiner() {
		return setts("sm");
	}
	
	public static boolean ignoreWeb() {
		return setts("iw");
	}
	
	public static boolean permaSneak() {
		return setts("ps");
	}
	
	public static boolean freecam() {
		return setts("fc");
	}
	
	public static boolean ignoreWater(Entity e) {
		return !setts("if") ? false : e instanceof EntityPlayer;
	}
	
	public static AxisAlignedBB jesus(BlockLiquid bf, int x, int y, int z) {
		return !setts("je") ? null : AxisAlignedBB.fromBounds((double)x + bf.getBlockBoundsMinX(), (double)y + bf.getBlockBoundsMinY(), (double)z + bf.getBlockBoundsMinZ(), (double)x + bf.getBlockBoundsMaxY(), (double)y + bf.getBlockBoundsMaxY(), (double)z + bf.getBlockBoundsMaxZ());
	}
	
	public static float antiAFK(float cur) {
		if(setts("aa") && System.currentTimeMillis() >= lastTime + 1000L) {
			if(cur != 0.0F) {
				lastTime = System.currentTimeMillis();
				return cur;
			} else {
				return (float)Math.sin((double)((System.currentTimeMillis() - lastTime) / 900L));
			}
		} else {
			return cur;
		}
	}
	
	public static boolean invisibleBypass() {
		return setts("ib");
	}
	
	public static void renderESP(RendererLivingEntity rendererLivingEntity, Entity e, double par2, double par4, double par6) {
		if(e instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer)e;
			StringBuilder sb = new StringBuilder();
			if(setts("ehp")) {
				sb.append("HP: ");
				sb.append("--");
			}
			
			if(setts("eap")) {
				if(sb.length() != 0) {
					sb.append("; ");
				}
				
				sb.append("AP: ");
				sb.append(ep.inventory.getTotalArmorValue());
			}
			
			double dist = (double)ep.getDistanceToEntity(mc.thePlayer);
			if(setts("edi")) {
				if(sb.length() != 0) {
					sb.append("; ");
				}
				
				sb.append("DI: ");
				sb.append((int)dist);
			}
			
			if(sb.length() != 0) {
				rendererLivingEntity.renderLivingLabel(ep, sb.toString(), par2, par4 + 0.5D, par6, 64);
			}
		}
	}
	
	public static void autoFish(S12PacketEntityVelocity ev) {
		if(setts("af")) {
			if(mc.thePlayer.getCurrentEquippedItem() != null) {
				if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishingRod) {
					if(ev.func_149411_d() == 0 && ev.func_149409_f() == 0 && ev.func_149410_e() < 0) {
						Entity e = mc.theWorld.getEntityByID(ev.func_149412_c());
						if(e instanceof EntityFishHook) {
							mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement((ItemStack)null));
							mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement((ItemStack)null));
						}
					}
				}
			}
		}
	}
	
	public static boolean alwaysSprint() {
		return setts("as");
	}
	
	public static boolean speedhack() {
		return setts("sh");
	}
	
	public static float speedhack0(float spd, boolean flySpd) {
		if(!speedhack()) {
			return flySpd ? 0.05F : 0.1F;
		} else {
			return flySpd ? 0.1F * (float)Titanium.getTitanium().getSettings().getInt("fls") : 0.2F * (float)Titanium.getTitanium().getSettings().getInt("wls");
		}
	}
	
	public static void renderEntities(float renderPass) {
		Titanium.getTitanium().renderEntities(renderPass);
	}
	
	public static void onLava(Entity e) {
		if(e == mc.thePlayer) {
			if(setts("lh") && !setts("fc")) {
				Titanium.getTitanium().lavaHome();
			}
		}
	}
	
}
