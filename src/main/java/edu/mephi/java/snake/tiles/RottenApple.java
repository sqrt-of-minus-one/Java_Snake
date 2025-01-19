package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The rotten apple makes the snake shorter
public class RottenApple
		extends Eatable
{
	public static final int LIFETIME = 100;
	public static final int DELTA_SIZE = -1;
	
	public RottenApple(Field field)
	{
		super(field, LIFETIME, DELTA_SIZE);
	}
	
	public RottenApple(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME, DELTA_SIZE);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return getResourceManager().getSprite(ESprite.ROTTEN_APPLE.toString());
	}
	
	@Override
	public void eaten()
	{}
}
