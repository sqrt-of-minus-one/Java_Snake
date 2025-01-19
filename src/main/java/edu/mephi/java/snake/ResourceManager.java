package edu.mephi.java.snake;

import edu.mephi.java.engine.AbstractResourceManager;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.exception.ResourceManagerNotInitialisedException;

import javax.swing.*;

// The resource manager allows to get any sprite you need using `getSprite()` method
public class ResourceManager
	extends AbstractResourceManager
{
	public static final int COMMON_TEXTURE_FILE_INDEX = 0;
	public static final int SNAKE_TEXTURE_FILE_INDEX = 1;
	public static final String[] TEXTURE_FILES = { "res/Common.png", "res/Snake.png" };
	
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
		addSprite(ECommonSprite.NOTHING.toString(),					COMMON_TEXTURE_FILE_INDEX,	6, 2);
		
		addSprite(ECommonSprite.SLASH.toString(),					COMMON_TEXTURE_FILE_INDEX, 7, 1);
		addSprite(ECommonSprite.QUESTION.toString(), 				COMMON_TEXTURE_FILE_INDEX, 6, 1);
		addSprite(ECommonSprite.SUCCESS.toString(),					COMMON_TEXTURE_FILE_INDEX, 6, 0);
		addSprite(ECommonSprite.FAILURE.toString(),					COMMON_TEXTURE_FILE_INDEX, 7, 0);
		addSprite(ECommonSprite.MINUS.toString(),					COMMON_TEXTURE_FILE_INDEX, 5, 0);
		addSprite(ECommonSprite.PAUSE.toString(),					COMMON_TEXTURE_FILE_INDEX, 7, 2);
		addSprite(ECommonSprite.DOTS.toString(),					COMMON_TEXTURE_FILE_INDEX, 5, 1);
		
		addSprite(ECommonSprite.NUM_ZERO.toString(),				COMMON_TEXTURE_FILE_INDEX, 0, 0);
		addSprite(ECommonSprite.NUM_ONE.toString(),					COMMON_TEXTURE_FILE_INDEX, 1, 0);
		addSprite(ECommonSprite.NUM_TWO.toString(),					COMMON_TEXTURE_FILE_INDEX, 2, 0);
		addSprite(ECommonSprite.NUM_THREE.toString(),				COMMON_TEXTURE_FILE_INDEX, 3, 0);
		addSprite(ECommonSprite.NUM_FOUR.toString(),				COMMON_TEXTURE_FILE_INDEX, 4, 0);
		addSprite(ECommonSprite.NUM_FIVE.toString(),				COMMON_TEXTURE_FILE_INDEX, 0, 1);
		addSprite(ECommonSprite.NUM_SIX.toString(),					COMMON_TEXTURE_FILE_INDEX, 1, 1);
		addSprite(ECommonSprite.NUM_SEVEN.toString(),				COMMON_TEXTURE_FILE_INDEX, 2, 1);
		addSprite(ECommonSprite.NUM_EIGHT.toString(),				COMMON_TEXTURE_FILE_INDEX, 3, 1);
		addSprite(ECommonSprite.NUM_NINE.toString(),				COMMON_TEXTURE_FILE_INDEX, 4, 1);
		
		addSprite(ESprite.SNAKE_HEAD_UP.toString(),					SNAKE_TEXTURE_FILE_INDEX, 0, 0);
		addSprite(ESprite.SNAKE_HEAD_DOWN.toString(),				SNAKE_TEXTURE_FILE_INDEX, 1, 0);
		addSprite(ESprite.SNAKE_HEAD_LEFT.toString(),				SNAKE_TEXTURE_FILE_INDEX, 2, 0);
		addSprite(ESprite.SNAKE_HEAD_RIGHT.toString(),				SNAKE_TEXTURE_FILE_INDEX, 3, 0);
		
		addSprite(ESprite.SNAKE_HEAD_BLINK_UP.toString(),			SNAKE_TEXTURE_FILE_INDEX, 0, 1);
		addSprite(ESprite.SNAKE_HEAD_BLINK_DOWN.toString(),			SNAKE_TEXTURE_FILE_INDEX, 1, 1);
		addSprite(ESprite.SNAKE_HEAD_BLINK_LEFT.toString(),			SNAKE_TEXTURE_FILE_INDEX, 2, 1);
		addSprite(ESprite.SNAKE_HEAD_BLINK_RIGHT.toString(),		SNAKE_TEXTURE_FILE_INDEX, 3, 1);
		
		addSprite(ESprite.SNAKE_HEAD_READY_UP.toString(),			SNAKE_TEXTURE_FILE_INDEX, 0, 2);
		addSprite(ESprite.SNAKE_HEAD_READY_DOWN.toString(),			SNAKE_TEXTURE_FILE_INDEX, 1, 2);
		addSprite(ESprite.SNAKE_HEAD_READY_LEFT.toString(),			SNAKE_TEXTURE_FILE_INDEX, 2, 2);
		addSprite(ESprite.SNAKE_HEAD_READY_RIGHT.toString(),		SNAKE_TEXTURE_FILE_INDEX, 3, 2);
		
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_UP.toString(),		SNAKE_TEXTURE_FILE_INDEX, 0, 3);
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_DOWN.toString(),	SNAKE_TEXTURE_FILE_INDEX, 1, 3);
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_LEFT.toString(),	SNAKE_TEXTURE_FILE_INDEX, 2, 3);
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_RIGHT.toString(),	SNAKE_TEXTURE_FILE_INDEX, 4, 3);
		
		addSprite(ESprite.SNAKE_HEAD_DEAD_UP.toString(),			SNAKE_TEXTURE_FILE_INDEX, 0, 4);
		addSprite(ESprite.SNAKE_HEAD_DEAD_DOWN.toString(),			SNAKE_TEXTURE_FILE_INDEX, 1, 4);
		addSprite(ESprite.SNAKE_HEAD_DEAD_LEFT.toString(),			SNAKE_TEXTURE_FILE_INDEX, 2, 4);
		addSprite(ESprite.SNAKE_HEAD_DEAD_RIGHT.toString(),			SNAKE_TEXTURE_FILE_INDEX, 3, 4);
		
		addSprite(ESprite.SNAKE_BODY_UP_DOWN.toString(),			SNAKE_TEXTURE_FILE_INDEX, 2, 7);
		addSprite(ESprite.SNAKE_BODY_LEFT_RIGHT.toString(),			SNAKE_TEXTURE_FILE_INDEX, 3, 7);
		
		addSprite(ESprite.SNAKE_BODY_TURN_UP_RIGHT.toString(),		SNAKE_TEXTURE_FILE_INDEX, 0, 7);
		addSprite(ESprite.SNAKE_BODY_TURN_DOWN_RIGHT.toString(),	SNAKE_TEXTURE_FILE_INDEX, 0, 6);
		addSprite(ESprite.SNAKE_BODY_TURN_UP_LEFT.toString(),		SNAKE_TEXTURE_FILE_INDEX, 1, 7);
		addSprite(ESprite.SNAKE_BODY_TURN_DOWN_LEFT.toString(),		SNAKE_TEXTURE_FILE_INDEX, 1, 6);
		
		addSprite(ESprite.SNAKE_TAIL_UP.toString(),					SNAKE_TEXTURE_FILE_INDEX, 2, 6);
		addSprite(ESprite.SNAKE_TAIL_DOWN.toString(),				SNAKE_TEXTURE_FILE_INDEX, 3, 6);
		addSprite(ESprite.SNAKE_TAIL_LEFT.toString(),				SNAKE_TEXTURE_FILE_INDEX, 2, 5);
		addSprite(ESprite.SNAKE_TAIL_RIGHT.toString(),				SNAKE_TEXTURE_FILE_INDEX, 3, 5);
		
		addSprite(ESprite.APPLE.toString(),							SNAKE_TEXTURE_FILE_INDEX, 4, 0);
		addSprite(ESprite.ROTTEN_APPLE.toString(),					SNAKE_TEXTURE_FILE_INDEX, 4, 1);
		addSprite(ESprite.REVERSE_PILL.toString(),					SNAKE_TEXTURE_FILE_INDEX, 5, 0);
		addSprite(ESprite.SHIELD.toString(),						SNAKE_TEXTURE_FILE_INDEX, 6, 0);
		addSprite(ESprite.HAMMER.toString(),						SNAKE_TEXTURE_FILE_INDEX, 7, 0);
		addSprite(ESprite.SURPRISE.toString(),						SNAKE_TEXTURE_FILE_INDEX, 5, 1);
		
		addSprite(ESprite.GRASS.toString(),							SNAKE_TEXTURE_FILE_INDEX, 0, 5);
		addSprite(ESprite.WALL.toString(),							SNAKE_TEXTURE_FILE_INDEX, 4, 4);
		
		super.initialise();
	}
}
