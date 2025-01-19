package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The hammer builds a wall
public class Hammer
		extends Eatable
{
	public static final int LIFETIME = 100;
	public static final int DELTA_SIZE = 0;
	
	public Hammer(Field field)
	{
		super(field, LIFETIME, DELTA_SIZE);
	}
	
	public Hammer(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME, DELTA_SIZE);
	}
	
	@Override
	public String getSprite()
	{
		return ESprite.HAMMER.toString();
	}
	
	@Override
	public void eaten()
	{
		getField().generateWall();
	}
}
