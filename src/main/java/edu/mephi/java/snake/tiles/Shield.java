package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The shield temporarily makes the snake (almost) invulnerable
public class Shield
		extends Eatable
{
	public static final int LIFETIME = 100;
	public static final int DELTA_SIZE = 0;
	public static final int SHIELD_DURATION = 30; // The movements before the shield stops protecting the snake
	
	public Shield(Field field)
	{
		super(field, LIFETIME, DELTA_SIZE);
	}
	
	public Shield(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME, DELTA_SIZE);
	}
	
	@Override
	public String getSprite()
	{
		return ESprite.SHIELD.toString();
	}
	
	@Override
	public void eaten()
	{
		// Apply the shield
		getField().getSnake().setShield(SHIELD_DURATION);
	}
}
