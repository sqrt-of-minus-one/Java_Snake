package edu.mephi.java.engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager
{
	public static final String TEXTURE_FILE = "Snake.png";
	public static final int TEXTURE_TILE_SIZE = 8;
	public static final int FIELD_TILE_SIZE = 64;
	
	private static final Map<ESprite, ImageIcon> sprites;
	private static BufferedImage bufferedImage;
	
	public static ImageIcon getSprite(ESprite sprite)
	{
		return sprites.get(sprite);
	}
	
	static
	{
		try
		{
			bufferedImage = ImageIO.read(new File(TEXTURE_FILE));
		}
		catch (IOException e)
		{
			System.out.println("Error: couldn't open a texture file");
		}
		
		sprites = new HashMap<>();
		sprites.put(ESprite.GRASS, createImage(2, 7));
		
		sprites.put(ESprite.SNAKE_HEAD_UP, createImage(0, 0));
		sprites.put(ESprite.SNAKE_HEAD_DOWN, createImage(1, 0));
		sprites.put(ESprite.SNAKE_HEAD_LEFT, createImage(2, 0));
		sprites.put(ESprite.SNAKE_HEAD_RIGHT, createImage(3, 0));
		
		sprites.put(ESprite.SNAKE_HEAD_BLINK_UP, createImage(0, 1));
		sprites.put(ESprite.SNAKE_HEAD_BLINK_DOWN, createImage(1, 1));
		sprites.put(ESprite.SNAKE_HEAD_BLINK_LEFT, createImage(2, 1));
		sprites.put(ESprite.SNAKE_HEAD_BLINK_RIGHT, createImage(3, 1));
		
		sprites.put(ESprite.SNAKE_HEAD_READY_UP, createImage(0, 2));
		sprites.put(ESprite.SNAKE_HEAD_READY_DOWN, createImage(1, 2));
		sprites.put(ESprite.SNAKE_HEAD_READY_LEFT, createImage(2, 2));
		sprites.put(ESprite.SNAKE_HEAD_READY_RIGHT, createImage(3, 2));
		
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_UP, createImage(0, 3));
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_DOWN, createImage(1, 3));
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_LEFT, createImage(2, 3));
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_RIGHT, createImage(4, 3));
		
		sprites.put(ESprite.SNAKE_BODY_UP_DOWN, createImage(0, 5));
		sprites.put(ESprite.SNAKE_BODY_LEFT_RIGHT, createImage(1, 5));
		
		sprites.put(ESprite.SNAKE_BODY_TURN_UP_RIGHT, createImage(0, 7));
		sprites.put(ESprite.SNAKE_BODY_TURN_DOWN_RIGHT, createImage(0, 6));
		sprites.put(ESprite.SNAKE_BODY_TURN_UP_LEFT, createImage(1, 7));
		sprites.put(ESprite.SNAKE_BODY_TURN_DOWN_LEFT, createImage(1, 6));
		
		sprites.put(ESprite.SNAKE_TAIL_UP, createImage(0, 4));
		sprites.put(ESprite.SNAKE_TAIL_DOWN, createImage(1, 4));
		sprites.put(ESprite.SNAKE_TAIL_LEFT, createImage(2, 4));
		sprites.put(ESprite.SNAKE_TAIL_RIGHT, createImage(3, 4));
		
		sprites.put(ESprite.APPLE, createImage(4, 0));
		sprites.put(ESprite.ROTTEN_APPLE, createImage(4, 1));
		sprites.put(ESprite.REVERSE_PILL, createImage(5, 0));
		sprites.put(ESprite.SHIELD, createImage(6, 0));
		sprites.put(ESprite.HAMMER, createImage(7, 0));
		sprites.put(ESprite.WALL, createImage(3, 7));
		
		sprites.put(ESprite.NUM_ZERO, createImage(4, 5));
		sprites.put(ESprite.NUM_ONE, createImage(5, 5));
		sprites.put(ESprite.NUM_TWO, createImage(6, 5));
		sprites.put(ESprite.NUM_THREE, createImage(7, 5));
		sprites.put(ESprite.NUM_FOUR, createImage(4, 6));
		sprites.put(ESprite.NUM_FIVE, createImage(5, 6));
		sprites.put(ESprite.NUM_SIX, createImage(6, 6));
		sprites.put(ESprite.NUM_SEVEN, createImage(7, 6));
		sprites.put(ESprite.NUM_EIGHT, createImage(4, 7));
		sprites.put(ESprite.NUM_NINE, createImage(5, 7));
		
		sprites.put(ESprite.SUCCESS, createImage(4, 4));
		sprites.put(ESprite.FAILURE, createImage(5, 4));
		sprites.put(ESprite.QUESTION, createImage(6, 4));
		sprites.put(ESprite.SLASH, createImage(7, 4));
	}
	
	private static ImageIcon createImage(int x, int y)
	{
		return new ImageIcon(bufferedImage.getSubimage(x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
										  .getScaledInstance(FIELD_TILE_SIZE, FIELD_TILE_SIZE, Image.SCALE_FAST));
	}
}
