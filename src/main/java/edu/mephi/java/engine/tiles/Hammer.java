package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class Hammer
		extends Eatable
{
	public Hammer(int x, int y, Field field)
	{
		super(x, y, field);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.HAMMER);
	}
	
	@Override
	public void eaten()
	{}
}
