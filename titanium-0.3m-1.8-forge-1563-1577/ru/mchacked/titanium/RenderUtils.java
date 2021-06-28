package ru.mchacked.titanium;

import java.awt.image.BufferedImage;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtils {
	
	public static synchronized int Method144() {
		return TextureUtil.glGenTextures();
	}
	
	public static void Method145(int id, BufferedImage data) {
		TextureUtil.uploadTextureImage(id, data);
	}
	
	public static void Method147(AxisAlignedBB aabb) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawing(3);
		worldrenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		worldrenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
		worldrenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		tessellator.draw();
		worldrenderer.startDrawing(3);
		worldrenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		worldrenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		worldrenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		tessellator.draw();
		worldrenderer.startDrawing(1);
		worldrenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		worldrenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
		worldrenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		worldrenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		worldrenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
		worldrenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		tessellator.draw();
	}
	
}
