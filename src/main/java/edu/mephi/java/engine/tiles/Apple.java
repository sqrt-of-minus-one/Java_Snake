package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class Apple
		extends Eatable
{
	public Apple(int x, int y, Field field)
	{
		super(x, y, field, 1);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.APPLE);
	}
	
	@Override
	public void eaten()
	{
		getField().generateFood();
	}
}
