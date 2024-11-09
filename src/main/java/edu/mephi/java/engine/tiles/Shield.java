package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

// The shield temporarily makes the snake (almost) invulnerable
public class Shield
		extends Eatable
{
	public static final int SHIELD_DURATION = 30; // The movements before the shield stops protecting the snake
	
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
		// Apply the shield
		getField().getSnake().setShield(SHIELD_DURATION);
	}
}
