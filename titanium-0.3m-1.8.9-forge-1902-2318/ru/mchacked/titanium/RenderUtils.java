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
	
}
