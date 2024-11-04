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
	
	private static Map<ESprite, ImageIcon> sprites;
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
		sprites.put(ESprite.GRASS, createImage(4, 3, false));
		
		sprites.put(ESprite.SNAKE_HEAD_UP, createImage(1, 0, false));
		sprites.put(ESprite.SNAKE_HEAD_DOWN, createImage(0, 0, false));
		sprites.put(ESprite.SNAKE_HEAD_LEFT, createImage(2, 0, true));
		sprites.put(ESprite.SNAKE_HEAD_RIGHT, createImage(2, 0, false));
		
		sprites.put(ESprite.SNAKE_HEAD_BLINK_UP, createImage(1, 1, false));
		sprites.put(ESprite.SNAKE_HEAD_BLINK_DOWN, createImage(0, 1, false));
		sprites.put(ESprite.SNAKE_HEAD_BLINK_LEFT, createImage(2, 1, true));
		sprites.put(ESprite.SNAKE_HEAD_BLINK_RIGHT, createImage(2, 1, false));
		
		sprites.put(ESprite.SNAKE_HEAD_READY_UP, createImage(1, 2, false));
		sprites.put(ESprite.SNAKE_HEAD_READY_DOWN, createImage(0, 2, false));
		sprites.put(ESprite.SNAKE_HEAD_READY_LEFT, createImage(2, 2, true));
		sprites.put(ESprite.SNAKE_HEAD_READY_RIGHT, createImage(2, 2, false));
		
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_UP, createImage(1, 3, false));
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_DOWN, createImage(0, 3, false));
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_LEFT, createImage(2, 3, true));
		sprites.put(ESprite.SNAKE_HEAD_READY_BLINK_RIGHT, createImage(2, 3, false));
		
		sprites.put(ESprite.SNAKE_BODY_UP_DOWN, createImage(3, 0, false));
		sprites.put(ESprite.SNAKE_BODY_LEFT_RIGHT, createImage(3, 1, false));
		
		sprites.put(ESprite.SNAKE_BODY_TURN_UP_RIGHT, createImage(3, 2, false));
		sprites.put(ESprite.SNAKE_BODY_TURN_DOWN_RIGHT, createImage(3, 3, false));
		sprites.put(ESprite.SNAKE_BODY_TURN_UP_LEFT, createImage(3, 2, true));
		sprites.put(ESprite.SNAKE_BODY_TURN_DOWN_LEFT, createImage(3, 3, true));
		
		sprites.put(ESprite.SNAKE_TAIL_UP, createImage(4, 1, false));
		sprites.put(ESprite.SNAKE_TAIL_DOWN, createImage(4, 0, false));
		sprites.put(ESprite.SNAKE_TAIL_LEFT, createImage(4, 2, true));
		sprites.put(ESprite.SNAKE_TAIL_RIGHT, createImage(4, 2, false));
		
		sprites.put(ESprite.APPLE, createImage(5, 0, false));
		sprites.put(ESprite.ROTTEN_APPLE, createImage(6, 0, false));
		sprites.put(ESprite.REVERSE_PILL, createImage(5, 1, false));
		sprites.put(ESprite.SHIELD, createImage(5, 2, false));
		sprites.put(ESprite.HAMMER, createImage(6, 1, false));
		sprites.put(ESprite.WALL, createImage(5, 3, false));
	}
	
	private static ImageIcon createImage(int x, int y, boolean mirror)
	{
		return new ImageIcon(bufferedImage.getSubimage(x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
										  .getScaledInstance(FIELD_TILE_SIZE, mirror ? -FIELD_TILE_SIZE : FIELD_TILE_SIZE, Image.SCALE_FAST));
	}
}
