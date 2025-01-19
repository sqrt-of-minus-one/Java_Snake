package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The empty tile
public class Grass
		extends Tile
{
	public static final int LIFETIME = -1;
	
	public Grass(Field field)
	{
		super(field, LIFETIME);
	}
	
	public Grass(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME);
	}
	
	@Override
	public String getSprite()
	{
		return ESprite.GRASS.toString();
	}
}
