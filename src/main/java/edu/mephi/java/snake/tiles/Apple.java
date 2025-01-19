package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The apple makes the snake longer
public class Apple
		extends Eatable
{
	public static final int LIFETIME = -1;
	public static final int DELTA_SIZE = 1;
	
	public Apple(Field field)
	{
		super(field, LIFETIME, DELTA_SIZE);
	}
	
	public Apple(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME, DELTA_SIZE);
	}
	
	@Override
	public String getSprite()
	{
		return ESprite.APPLE.toString();
	}
	
	@Override
	public void eaten()
	{
		// Create another apple when eaten
		getField().generateFood();
	}
}
