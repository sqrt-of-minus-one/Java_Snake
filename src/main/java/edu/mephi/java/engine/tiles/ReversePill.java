package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class ReversePill
		extends Eatable
{
	public ReversePill(int x, int y, Field field)
	{
		super(x, y, field);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.REVERSE_PILL);
	}
	
	@Override
	public void eaten()
	{}
}
