package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The hammer builds a wall
public class Hammer
		extends Eatable
{
	public Hammer(int x, int y, Field field)
	{
		super(x, y, field, 100, 0);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.HAMMER);
	}
	
	@Override
	public void eaten()
	{
		getField().generateWall();
	}
}
