package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

// The reverse pill swaps the snake's head and tail
public class ReversePill
		extends Eatable
{
	public static final int LIFETIME = 100;
	public static final int DELTA_SIZE = 0;
	
	public ReversePill(Field field)
	{
		super(field, LIFETIME, DELTA_SIZE);
	}
	
	public ReversePill(int x, int y, Field field)
	{
		super(x, y, field, LIFETIME, DELTA_SIZE);
	}
	
	@Override
	public String getSprite()
	{
		return ESprite.REVERSE_PILL.toString();
	}
	
	@Override
	public void eaten()
	{
		getField().getSnake().reverse();
	}
}
