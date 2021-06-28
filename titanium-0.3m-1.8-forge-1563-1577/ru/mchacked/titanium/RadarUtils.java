package ru.mchacked.titanium;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class RadarUtils {
	
	private int Field115 = RenderUtils.Method144();
	private BufferedImage bimg = new BufferedImage(256, 256, 2);
	private BufferedImage bimg1 = new BufferedImage(256, 256, 2);
	private static Color black = new Color(0, 0, 0, 0);
	private static BasicStroke outline = new BasicStroke(10.0F);
	private static Color dark_green = new Color(0, 30, 0, 255);
	private static Color dark_green1 = new Color(0, 60, 0, 255);
	private static Color green = new Color(0, 255, 0, 255);
	
	public RadarUtils() {
		this.Method150();
	}
	
	private void Method149(Graphics2D g, Color col, int radius) {
		g.setColor(col);
		g.drawArc(128 - radius, 128 - radius, radius * 2, radius * 2, 0, 360);
	}
	
	private void Method150() {
		Graphics2D g = this.bimg1.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setBackground(black);
		g.clearRect(0, 0, 256, 256);
		Stroke s = g.getStroke();
		g.setColor(dark_green);
		g.fillArc(5, 5, 246, 246, 0, 360);
		g.setStroke(outline);
		g.setColor(Color.black);
		g.drawArc(5, 5, 246, 246, 0, 360);
		g.setStroke(s);
		this.Method149(g, dark_green1, 3);
		
		for(int i = 32; i < 128; i += 32) {
			this.Method149(g, dark_green1, i);
		}
		
		g.setColor(dark_green1);
		g.drawLine(128, 10, 128, 246);
		g.drawLine(10, 128, 246, 128);
		g.dispose();
	}
	
	public void Method151(int x, int y, int u, int v, int w, int h) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV((double)(x + 0), (double)(y + h), 0.0D, (double)((float)(u + 0) * f), (double)((float)(v + h) * f1));
		worldrenderer.addVertexWithUV((double)(x + w), (double)(y + h), 0.0D, (double)((float)(u + w) * f), (double)((float)(v + h) * f1));
		worldrenderer.addVertexWithUV((double)(x + w), (double)(y + 0), 0.0D, (double)((float)(u + w) * f), (double)((float)(v + 0) * f1));
		worldrenderer.addVertexWithUV((double)(x + 0), (double)(y + 0), 0.0D, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
		tessellator.draw();
	}
	
	private void Method152() {
		Minecraft mc = Minecraft.getMinecraft();
		int[] src = ((DataBufferInt)this.bimg1.getRaster().getDataBuffer()).getData();
		int[] dst = ((DataBufferInt)this.bimg.getRaster().getDataBuffer()).getData();
		System.arraycopy(src, 0, dst, 0, src.length);
		double angle = Math.toRadians((double)(System.currentTimeMillis() / 10L % 360L));
		double sr = 118.0D;
		Graphics2D g = this.bimg.createGraphics();
		g.setColor(green);
		int sx = (int)(sr * Math.cos(angle) + 128.0D);
		int sy = (int)(sr * Math.sin(angle) + 128.0D);
		g.drawLine(128, 128, sx, sy);
		g.setColor(green);
		List pls = mc.theWorld.playerEntities;
		byte maxrad = 64;
		
		Iterator i$ = pls.iterator();
		while(i$.hasNext()) {
			EntityPlayer p = (EntityPlayer)i$.next();
			if(!mc.thePlayer.getName().equals(p.getName())) {
				double dx = p.posX - mc.thePlayer.posX;
				double dz = p.posZ - mc.thePlayer.posZ;
				double r = Math.sqrt(dx * dx + dz * dz);
				if(r <= (double)maxrad) {
					double fi = Math.atan2(dx, dz);
					fi = -(fi + Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
					int nx = 128 + (int)(118.0D * r * Math.cos(fi) / (double)maxrad);
					int ny = 128 + (int)(118.0D * r * Math.sin(fi) / (double)maxrad);
					byte rad = 2;
					g.fillArc(nx - rad, ny - rad, rad * 2, rad * 2, 0, 360);
				}
			}
		}
		
		g.dispose();
	}
	
	public int Method153(int width, int height, int yPos) {
		GL11.glPushMatrix();
		this.Method152();
		RenderUtils.Method145(this.Field115, this.bimg);
		int mul = 2;
		if(height / 2 < yPos + 256 / mul) {
			mul *= 2;
		}
		
		GL11.glScaled(1.0D / (double)mul, 1.0D / (double)mul, 1.0D / (double)mul);
		this.Method151((width - 256 / mul - 8) * mul, yPos * mul, 0, 0, 256, 256);
		GL11.glPopMatrix();
		return 256 / mul + 8;
	}
	
}
