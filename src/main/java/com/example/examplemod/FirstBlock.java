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
        	numGuiLayers = 0;
        	BlockPos myPos;
        	int radius = 1; //0 for 1x1;  1 for 3x3;  2 for 5x5;  3 for 7x7
        	int zOffset, xOffset;
            for(int depth=1; depth<y-1; depth++)
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
            	
		            	for(int typeIndex=0; typeIndex<itemNames.length ; typeIndex++)
		            	{
			            	if (blockName.equals(itemNames[typeIndex]))
			            	{
			            		if(numFound[typeIndex] == 0)
			            		{
			            			sortedItemDepth[typeIndex] = y-newY;
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
            
            for(int index=0; index<numGuiLayers; index++)
            {
            	int itemIndex = layerItemIndexes[index];
            	
	            LOGGER.info(itemNames[itemIndex]+" count="+numFound[itemIndex]+" firstY="+sortedItemDepth[itemIndex]);
            }
            
            int diameter = 1+radius*2;
            splashText = new String(diameter+"x"+diameter+" Scanned");

            displayDuration = 1500;
            
            return true;
        }
        return true;
    }

    public int numItems = 0;
    public final int maxGuiLayers = 10;
    public static int [] sortedItemDepth;
    public static int [] numFound;
    public static String [] sortedItemNames;
    public static String [] sortedItemTextures;
    public static int numGuiLayers = 0;
    public static int displayDuration = 0;
    public static String splashText;
    
    public static int [] layerItemIndexes;
    
    public final static String [] itemNames = 
	{
    	"Diamond Ore",       // 0
    	"Gold Ore",          // 1
    	"Iron Ore",          // 2
    	"Lapis Lazuli Ore",  // 3
    	"Redstone Ore",      // 4
    	"Coal Ore",          // 5
    	"Lava",              // 6
    	"Water",             // 7
//    	"Dirt",              // 8
    	"Air"                // 9
	};

    // from textures/items/items.png
    public final static int [] icons_x = 
	{
		7, //    	"Diamond Ore",       // 0
		7, //    	"Gold Ore",          // 1
		7, //    	"Iron Ore",          // 2
		14,//    	"Lapis Lazuli Ore",  // 3
		8, //    	"Redstone Ore",      // 4
		7, //    	"Coal Ore",          // 5
		12,//    	"Lava",              // 6
		11,//    	"Water",             // 7
//		6, //    	"Dirt",              // 8
		15 //    	"Air"                // 9
	};  
    public final static int [] icons_y = 
	{
		3, //    	"Diamond Ore",       // 0
		2, //    	"Gold Ore",          // 1
		1, //    	"Iron Ore",          // 2
		8, //    	"Lapis Lazuli Ore",  // 3
		3, //    	"Redstone Ore",      // 4
		0, //    	"Coal Ore",          // 5
		4, //    	"Lava",              // 6
		4, //    	"Water",             // 7
//		1, //    	"Dirt",              // 8
		13 //    	"Air"                // 9
	};  

}
