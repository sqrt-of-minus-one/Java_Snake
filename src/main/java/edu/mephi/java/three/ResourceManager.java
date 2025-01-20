package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractResourceManager;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.exception.ResourceManagerNotInitialisedException;

import javax.swing.*;

public class ResourceManager
	extends AbstractResourceManager
{
	public static final int COMMON_TEXTURE_FILE_INDEX = 0;
	public static final int THREE_TEXTURE_FILE_INDEX = 1;
	public static final String[] TEXTURE_FILES = { "res/Common.png", "res/Three.png" };
	
	private static ResourceManager resourceManager;
	
	public static ResourceManager create(int textureTileSize, int fieldTileSize)
	{
		if (resourceManager == null)
		{
			resourceManager = new ResourceManager(textureTileSize, fieldTileSize);
		}
		return resourceManager;
	}
	
	public static ResourceManager get()
	{
		if (resourceManager == null)
		{
			throw new ResourceManagerNotInitialisedException("Attempt to get a resource manager (Snake) without creating one");
		}
		return resourceManager;
	}
	
	public ImageIcon getSprite(ESprite sprite)
	{
		return getSprite(sprite.toString());
	}
	
	private ResourceManager(int textureTileSize, int fieldTileSize)
	{
		super(textureTileSize, fieldTileSize, TEXTURE_FILES);
		initialise();
	}
	
	@Override
	protected void initialise()
	{
		addSprite(ECommonSprite.NOTHING.toString(), 	COMMON_TEXTURE_FILE_INDEX, 6, 2);
		
		addSprite(ECommonSprite.SLASH.toString(),		COMMON_TEXTURE_FILE_INDEX, 7, 1);
		addSprite(ECommonSprite.QUESTION.toString(), 	COMMON_TEXTURE_FILE_INDEX, 6, 1);
		addSprite(ECommonSprite.SUCCESS.toString(),		COMMON_TEXTURE_FILE_INDEX, 6, 0);
		addSprite(ECommonSprite.FAILURE.toString(),		COMMON_TEXTURE_FILE_INDEX, 7, 0);
		addSprite(ECommonSprite.MINUS.toString(),		COMMON_TEXTURE_FILE_INDEX, 5, 0);
		addSprite(ECommonSprite.PAUSE.toString(),		COMMON_TEXTURE_FILE_INDEX, 7, 2);
		addSprite(ECommonSprite.DOTS.toString(),		COMMON_TEXTURE_FILE_INDEX, 5, 1);
		
		addSprite(ECommonSprite.NUM_ZERO.toString(),	COMMON_TEXTURE_FILE_INDEX, 0, 0);
		addSprite(ECommonSprite.NUM_ONE.toString(),		COMMON_TEXTURE_FILE_INDEX, 1, 0);
		addSprite(ECommonSprite.NUM_TWO.toString(),		COMMON_TEXTURE_FILE_INDEX, 2, 0);
		addSprite(ECommonSprite.NUM_THREE.toString(),	COMMON_TEXTURE_FILE_INDEX, 3, 0);
		addSprite(ECommonSprite.NUM_FOUR.toString(),	COMMON_TEXTURE_FILE_INDEX, 4, 0);
		addSprite(ECommonSprite.NUM_FIVE.toString(),	COMMON_TEXTURE_FILE_INDEX, 0, 1);
		addSprite(ECommonSprite.NUM_SIX.toString(),		COMMON_TEXTURE_FILE_INDEX, 1, 1);
		addSprite(ECommonSprite.NUM_SEVEN.toString(),	COMMON_TEXTURE_FILE_INDEX, 2, 1);
		addSprite(ECommonSprite.NUM_EIGHT.toString(),	COMMON_TEXTURE_FILE_INDEX, 3, 1);
		addSprite(ECommonSprite.NUM_NINE.toString(),	COMMON_TEXTURE_FILE_INDEX, 4, 1);
		
		addSprite(ESprite.EMPTY.toString(),				THREE_TEXTURE_FILE_INDEX, 0, 0);
		addSprite(ESprite.EMPTY_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 0, 1);
		addSprite(ESprite.EMPTY_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 0, 2);
		
		addSprite(ESprite.SPARK.toString(),				THREE_TEXTURE_FILE_INDEX, 7, 0);
		addSprite(ESprite.SPARK_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 7, 1);
		addSprite(ESprite.SPARK_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 7, 2);
		
		addSprite(ESprite.APPLE.toString(),				THREE_TEXTURE_FILE_INDEX, 1, 0);
		addSprite(ESprite.APPLE_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 1, 1);
		addSprite(ESprite.APPLE_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 1, 2);
		
		addSprite(ESprite.ORANGE.toString(),			THREE_TEXTURE_FILE_INDEX, 2, 0);
		addSprite(ESprite.ORANGE_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 2, 1);
		addSprite(ESprite.ORANGE_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 2, 2);
		
		addSprite(ESprite.BANANA.toString(),			THREE_TEXTURE_FILE_INDEX, 3, 0);
		addSprite(ESprite.BANANA_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 3, 1);
		addSprite(ESprite.BANANA_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 3, 2);
		
		addSprite(ESprite.CUCUMBER.toString(),			THREE_TEXTURE_FILE_INDEX, 4, 0);
		addSprite(ESprite.CUCUMBER_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 4, 1);
		addSprite(ESprite.CUCUMBER_PICKED.toString(),	THREE_TEXTURE_FILE_INDEX, 4, 2);
		
		addSprite(ESprite.WATER.toString(),				THREE_TEXTURE_FILE_INDEX, 5, 0);
		addSprite(ESprite.WATER_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 5, 1);
		addSprite(ESprite.WATER_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 5, 2);
		
		addSprite(ESprite.MEAT.toString(),				THREE_TEXTURE_FILE_INDEX, 6, 0);
		addSprite(ESprite.MEAT_SELECTED.toString(),		THREE_TEXTURE_FILE_INDEX, 6, 1);
		addSprite(ESprite.MEAT_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 6, 2);
		
		addSprite(ESprite.BOMB.toString(),				THREE_TEXTURE_FILE_INDEX, 0, 3);
		addSprite(ESprite.BOMB_SELECTED.toString(),		THREE_TEXTURE_FILE_INDEX, 0, 4);
		addSprite(ESprite.BOMB_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 0, 5);
		
		addSprite(ESprite.BOMB_HOT.toString(),			THREE_TEXTURE_FILE_INDEX, 1, 3);
		addSprite(ESprite.BOMB_HOT_SELECTED.toString(),	THREE_TEXTURE_FILE_INDEX, 1, 4);
		addSprite(ESprite.BOMB_HOT_PICKED.toString(),	THREE_TEXTURE_FILE_INDEX, 1, 5);
		
		addSprite(ESprite.FIRE.toString(),				THREE_TEXTURE_FILE_INDEX, 2, 3);
		addSprite(ESprite.FIRE_SELECTED.toString(),		THREE_TEXTURE_FILE_INDEX, 2, 4);
		addSprite(ESprite.FIRE_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 2, 5);
		
		addSprite(ESprite.WALL.toString(),				THREE_TEXTURE_FILE_INDEX, 7, 3);
		addSprite(ESprite.WALL_SELECTED.toString(),		THREE_TEXTURE_FILE_INDEX, 7, 4);
		addSprite(ESprite.WALL_PICKED.toString(),		THREE_TEXTURE_FILE_INDEX, 7, 5);
		
		super.initialise();
	}
}
