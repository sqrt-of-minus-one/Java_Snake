package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The empty tile
public class Grass
		extends Tile
{
	public Grass(int x, int y, Field field)
	{
		super(x, y, field, -1);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.GRASS);
	}
}
