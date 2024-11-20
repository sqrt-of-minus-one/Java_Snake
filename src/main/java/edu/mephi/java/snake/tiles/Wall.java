package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The snake loses when it collides with the wall
public class Wall
		extends Obstacle
{
	public Wall(int x, int y, Field field)
	{
		super(x, y, field, 200, true);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.WALL);
	}
	
	@Override
	public void collide()
	{}
}
