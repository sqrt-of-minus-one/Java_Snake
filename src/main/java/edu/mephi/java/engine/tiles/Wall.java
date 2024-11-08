package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class Wall
		extends Obstacle
{
	public Wall(int x, int y, Field field)
	{
		super(x, y, field, true);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.WALL);
	}
	
	@Override
	public void collide()
	{
	}
}
