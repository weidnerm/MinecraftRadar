package com.example.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class RadarRenderer {
	private static Minecraft mc;
    private static FontRenderer fontRenderer;
    private static final Logger LOGGER = LogManager.getLogger();
//    public static final ResourceLocation ICON_VANILLA = AbstractGui.GUI_ICONS_LOCATION; // good
//    public static final ResourceLocation myResourceLocation = new ResourceLocation("minecraft:textures/gui/icons.png"); // good
//    public static final ResourceLocation myResourceLocation = new ResourceLocation("minecraft:textures/gui/widgets.png"); // good
//    public static final ResourceLocation myResourceLocation = new ResourceLocation("minecraft:textures/gui/recipe_button.png"); // good
//    public static final ResourceLocation myResourceLocation = new ResourceLocation("minecraft:textures/block/diamond_ore.png"); // good with 8 8
//    public static final ResourceLocation myResourceLocation = new ResourceLocation("minecraft:textures/item/diamond.png"); //bad
    public static final ResourceLocation myResourceLocation = new ResourceLocation("examplemod:textures/items/items.png");
    
    public static ResourceLocation [] myResourceLocations;

    
    RadarRenderer()
    {
    	mc = Minecraft.getInstance();
    	fontRenderer = mc.fontRenderer;
    	
    	myResourceLocations = new ResourceLocation[11];
    	myResourceLocations[0] = new ResourceLocation("examplemod:textures/items/items.png");
    }
    
	@SubscribeEvent(priority = EventPriority.LOW)
	public void renderMyOverlay(RenderGameOverlayEvent.Pre event) {
		
		int index = 0;
//        LOGGER.info("renderMyOverlay reached" );
		drawStringOnHUD("Testing", 20, 12+4+index*16, 0xffffffff);
		

//		ResourceLocation myResourceLocation = new ResourceLocation("examplemod:textures/items/items.png");
			
		mc.getTextureManager().bindTexture(myResourceLocation);

	    drawTexturedModalRect(2,12+16*index, 7*16, 2*16, 16, 16);
	    
	}
	
	public static void drawStringOnHUD(String string, int xOffset, int yOffset, int color) {

		mc = Minecraft.getInstance();
		fontRenderer = mc.fontRenderer;

		fontRenderer.drawStringWithShadow(string, xOffset, yOffset, color);
	}

	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		mc.ingameGUI.blit(x, y, textureX, textureY, width, height);
	}
}
