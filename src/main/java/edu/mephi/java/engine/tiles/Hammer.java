package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

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
