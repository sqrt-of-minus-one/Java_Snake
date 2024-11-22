package edu.mephi.java.snake;

import edu.mephi.java.engine.AbstractResourceManager;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.exception.ResourceManagerNotInitialisedException;

import javax.swing.*;

// The resource manager allows to get any sprite you need using `getSprite()` method
public class ResourceManager
	extends AbstractResourceManager
{
	public static final String[] TEXTURE_FILES = { "Snake.png" };
	
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
	}
	
	@Override
	protected void initialise()
	{
		addSprite(ECommonSprite.NOTHING.toString(), 0, 2, 7);
		
		addSprite(ECommonSprite.SLASH.toString(), 0, 7, 4);
		addSprite(ECommonSprite.QUESTION.toString(), 0, 6, 4);
		addSprite(ECommonSprite.SUCCESS.toString(), 0, 4, 4);
		addSprite(ECommonSprite.FAILURE.toString(), 0, 5, 4);
		addSprite(ECommonSprite.MINUS.toString(), 0, 7, 3);
		addSprite(ECommonSprite.PAUSE.toString(), 0, 6, 3);
		
		addSprite(ECommonSprite.NUM_ZERO.toString(), 0, 4, 5);
		addSprite(ECommonSprite.NUM_ONE.toString(), 0, 5, 5);
		addSprite(ECommonSprite.NUM_TWO.toString(), 0, 6, 5);
		addSprite(ECommonSprite.NUM_THREE.toString(), 0, 7, 5);
		addSprite(ECommonSprite.NUM_FOUR.toString(), 0, 4, 6);
		addSprite(ECommonSprite.NUM_FIVE.toString(), 0, 5, 6);
		addSprite(ECommonSprite.NUM_SIX.toString(), 0, 6, 6);
		addSprite(ECommonSprite.NUM_SEVEN.toString(), 0, 7, 6);
		addSprite(ECommonSprite.NUM_EIGHT.toString(), 0, 4, 7);
		addSprite(ECommonSprite.NUM_NINE.toString(), 0, 5, 7);
		
		addSprite(ESprite.SNAKE_HEAD_UP.toString(), 0, 0, 0);
		addSprite(ESprite.SNAKE_HEAD_DOWN.toString(), 0, 1, 0);
		addSprite(ESprite.SNAKE_HEAD_LEFT.toString(), 0, 2, 0);
		addSprite(ESprite.SNAKE_HEAD_RIGHT.toString(), 0, 3, 0);
		
		addSprite(ESprite.SNAKE_HEAD_BLINK_UP.toString(), 0, 0, 1);
		addSprite(ESprite.SNAKE_HEAD_BLINK_DOWN.toString(), 0, 1, 1);
		addSprite(ESprite.SNAKE_HEAD_BLINK_LEFT.toString(), 0, 2, 1);
		addSprite(ESprite.SNAKE_HEAD_BLINK_RIGHT.toString(), 0, 3, 1);
		
		addSprite(ESprite.SNAKE_HEAD_READY_UP.toString(), 0, 0, 2);
		addSprite(ESprite.SNAKE_HEAD_READY_DOWN.toString(), 0, 1, 2);
		addSprite(ESprite.SNAKE_HEAD_READY_LEFT.toString(), 0, 2, 2);
		addSprite(ESprite.SNAKE_HEAD_READY_RIGHT.toString(), 0, 3, 2);
		
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_UP.toString(), 0, 0, 3);
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_DOWN.toString(), 0, 1, 3);
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_LEFT.toString(), 0, 2, 3);
		addSprite(ESprite.SNAKE_HEAD_READY_BLINK_RIGHT.toString(), 0, 4, 3);
		
		addSprite(ESprite.SNAKE_BODY_UP_DOWN.toString(), 0, 0, 5);
		addSprite(ESprite.SNAKE_BODY_LEFT_RIGHT.toString(), 0, 1, 5);
		
		addSprite(ESprite.SNAKE_BODY_TURN_UP_RIGHT.toString(), 0, 0, 7);
		addSprite(ESprite.SNAKE_BODY_TURN_DOWN_RIGHT.toString(), 0, 0, 6);
		addSprite(ESprite.SNAKE_BODY_TURN_UP_LEFT.toString(), 0, 1, 7);
		addSprite(ESprite.SNAKE_BODY_TURN_DOWN_LEFT.toString(), 0, 1, 6);
		
		addSprite(ESprite.SNAKE_TAIL_UP.toString(), 0, 0, 4);
		addSprite(ESprite.SNAKE_TAIL_DOWN.toString(), 0, 1, 4);
		addSprite(ESprite.SNAKE_TAIL_LEFT.toString(), 0, 2, 4);
		addSprite(ESprite.SNAKE_TAIL_RIGHT.toString(), 0, 3, 4);
		
		addSprite(ESprite.APPLE.toString(), 0, 4, 0);
		addSprite(ESprite.ROTTEN_APPLE.toString(), 0, 4, 1);
		addSprite(ESprite.REVERSE_PILL.toString(), 0, 5, 0);
		addSprite(ESprite.SHIELD.toString(), 0, 6, 0);
		addSprite(ESprite.HAMMER.toString(), 0, 7, 0);
		
		addSprite(ESprite.GRASS.toString(), 0, 2, 6);
		addSprite(ESprite.WALL.toString(), 0, 3, 7);
		
		super.initialise();
	}
}
