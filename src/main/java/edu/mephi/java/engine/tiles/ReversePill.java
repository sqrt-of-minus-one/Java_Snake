package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

// The reverse pill swaps the snake's head and tail
public class ReversePill
		extends Eatable
{
	public ReversePill(int x, int y, Field field)
	{
		super(x, y, field, 100, 0);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.REVERSE_PILL);
	}
	
	@Override
	public void eaten()
	{
		getField().getSnake().reverse();
	}
}
