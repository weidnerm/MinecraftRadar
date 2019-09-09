package com.example.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class RadarRenderer {
	private static Minecraft mc;
    private static FontRenderer fontRenderer;
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation myResourceLocation = new ResourceLocation("examplemod:textures/items/items.png");
    
    RadarRenderer()
    {
    	mc = Minecraft.getInstance();
    	fontRenderer = mc.fontRenderer;
    	
    }
    
	@SubscribeEvent(priority = EventPriority.LOW)
	public void renderMyOverlay(RenderGameOverlayEvent.Pre event) 
	{
		int overlayRow = 0;
		
		if (FirstBlock.displayDuration > 0)
		{
			for(overlayRow=0 ; overlayRow<FirstBlock.numGuiLayers ; overlayRow++)
			{
				int itemInfoIndex = FirstBlock.layerItemIndexes[overlayRow];
		    	
				
				String rowText = FirstBlock.numFound[itemInfoIndex]+" " +FirstBlock.itemNames[itemInfoIndex]+ " "+FirstBlock.sortedItemDepth[itemInfoIndex];
				
				drawStringOnHUD(rowText, 20, 12 + 4 + overlayRow * 16, 0xffffffff);
	
				mc.getTextureManager().bindTexture(myResourceLocation);
				
				int icons_png_x = FirstBlock.icons_x[itemInfoIndex];
				int icons_png_y = FirstBlock.icons_y[itemInfoIndex];
	
				drawTexturedModalRect(2, 12 + 16*overlayRow, icons_png_x*16, icons_png_y*16, 16, 16);
			}
			
			FirstBlock.displayDuration--;
		}
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
