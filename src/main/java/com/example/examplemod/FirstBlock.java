package com.example.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class FirstBlock extends Block {

    private static final Logger LOGGER = LogManager.getLogger();

	public FirstBlock() 
	{
		super(Properties.create(Material.IRON)
				.sound(SoundType.METAL)
				.hardnessAndResistance(2.0f)
				.lightValue(14));
		setRegistryName("firstblock");

		sortedItemDepth = new int[itemNames.length];
		sortedItemNames = new String[itemNames.length];
		sortedItemTextures = new String[itemNames.length];
		numFound = new int[itemNames.length];
		layerItemIndexes = new int[maxGuiLayers];  // max number on display

	}


    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isRemote) 
        {
        	for(int index=0; index<numFound.length; index++)  // clear out array that prevents dups being 
        	{
        		numFound[index] = 0;
        		sortedItemDepth[index] = 0;
        	}
        	for(int index=0; index<maxGuiLayers; index++)
        	{
        		layerItemIndexes[index] = -1;
        	}
        	
        	LOGGER.info("HELLO from onBlockActivated");
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            
            LOGGER.info("pos = " + x + ","+ y + "," + z );
            
        	int dirtCount = 0;
        	int numGuiLayers = 0;
        	BlockPos myPos;
        	int radius = 1; //0 for 1x1;  1 for 3x3;  2 for 5x5;  3 for 7x7
        	int zOffset, xOffset;
            for(int depth=1; depth<y-1-60; depth++)
            {
            	int newY = y-depth;
            	
                for(xOffset=-radius; xOffset<1+radius ; xOffset++ )
                {
                   for(zOffset=-radius; zOffset<1+radius ; zOffset++ )
                   {
		            	int newX = x+xOffset;
		            	int newZ = z+zOffset;

		            	myPos = new BlockPos(newX,newY,newZ);
		            	BlockState myBlockState = world.getBlockState(myPos);
		            	String blockName = myBlockState.getBlock().getNameTextComponent().getString();
//		            	LOGGER.info("<"+newX+","+newY+","+newZ+"> blockName=" + blockName); // Sand, Dirt, First Block
            	
		            	for(int typeIndex=0; typeIndex<itemNames.length ; typeIndex++)
		            	{
			            	if (blockName.equals(itemNames[typeIndex]))
			            	{
			            		if(numFound[typeIndex] == 0)
			            		{
			            			sortedItemDepth[typeIndex] = newY;
			            			if(numGuiLayers<maxGuiLayers)
			            			{
			            				layerItemIndexes[numGuiLayers] = typeIndex;
			            				numGuiLayers++;
			            			}
	
			            		}
			            		numFound[typeIndex]++;
			            	}
		            	}
                   }
                }
            }
            
//            for(int index=0; index<itemNames.length; index++)
//            {
//            	if ( numFound[index] > 0)
//            	{
//	            	LOGGER.info(itemNames[index]+" count="+numFound[index]+" firstY="+sortedItemDepth[index]);
//            	}
//            }
            for(int index=0; index<numGuiLayers; index++)
            {
            	int itemIndex = layerItemIndexes[index];
            	
	            LOGGER.info(itemNames[itemIndex]+" count="+numFound[itemIndex]+" firstY="+sortedItemDepth[itemIndex]);
            }
            

            
            
            return true;
        }
//        return super.onBlockActivated(state, world, pos, player, hand, result);
        return true;
    }

    public int numItems = 0;
    public final int maxGuiLayers = 10;
    public static int [] sortedItemDepth;
    public static int [] numFound;
    public static String [] sortedItemNames;
    public static String [] sortedItemTextures;
    
    public static int [] layerItemIndexes;
    
    public final String [] itemNames = 
	{
    	"Diamond Ore",
    	"Gold Ore",
    	"Iron Ore",
    	"Lapis Lazuli Ore",
    	"Redstone Ore",
    	"Coal Ore",
    	"Lava",
    	"Water",
    	"Dirt",
    	"Air"
	};

}
