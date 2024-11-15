package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

// The rotten apple makes the snake shorter
public class RottenApple
		extends Eatable
{
	public RottenApple(int x, int y, Field field)
	{
		super(x, y, field, 100, -1);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.ROTTEN_APPLE);
	}
	
	@Override
	public void eaten()
	{}
}
