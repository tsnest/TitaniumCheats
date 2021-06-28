package ru.mchacked.titanium;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtils {
	
	public static synchronized int Method144() {
		return GLAllocation.generateTextureNames();
	}
	
	public static void Method145(int id, BufferedImage data) {
		Minecraft.getMinecraft().renderEngine.setupTexture(data, id);
	}
	
	public static void Method147(AxisAlignedBB aabb) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		tessellator.draw();
	}
	
}
