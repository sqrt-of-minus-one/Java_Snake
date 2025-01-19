package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The snake loses when it collides with the wall
public class Wall
		extends Obstacle
{
	public static final int LIFETIME = 200;
	public static final boolean LOST_ON_COLLIDE = true;
	
	public Wall(Field field)
	{
		super(field, LIFETIME, LOST_ON_COLLIDE);
	}
	
	public Wall(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME, LOST_ON_COLLIDE);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return getResourceManager().getSprite(ESprite.WALL.toString());
	}
	
	@Override
	public void collide()
	{}
}
