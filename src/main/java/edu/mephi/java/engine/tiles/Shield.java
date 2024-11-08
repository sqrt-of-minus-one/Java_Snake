package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class Shield
		extends Eatable
{
	public static final int SHIELD_DURATION = 25;
	
	public Shield(int x, int y, Field field)
	{
		super(x, y, field, 100, 0);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.SHIELD);
	}
	
	@Override
	public void eaten()
	{
		getField().getSnake().setShield(SHIELD_DURATION);
	}
}
