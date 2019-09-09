package com.example.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RadarRenderer {
	private static Minecraft mc;
    private static FontRenderer fontRenderer;
    private static final Logger LOGGER = LogManager.getLogger();

    RadarRenderer()
    {
    	mc = Minecraft.getInstance();
    	fontRenderer = mc.fontRenderer;
    }
    
	@SubscribeEvent(priority = EventPriority.LOW)
	public void renderMyOverlay(RenderGameOverlayEvent.Pre event) {
		
//        LOGGER.info("renderMyOverlay reached" );
		drawStringOnHUD("Testing", 20, 16, 0xffffffff);
        
	}
	
	public static void drawStringOnHUD(String string, int xOffset, int yOffset, int color) {

    	mc = Minecraft.getInstance();
    	fontRenderer = mc.fontRenderer;

		fontRenderer.drawStringWithShadow(string, xOffset, yOffset, color);
	}

}
