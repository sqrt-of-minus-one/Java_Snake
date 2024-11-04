package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

public abstract class Eatable
		extends Tile
{
	public Eatable(int x, int y, Field field)
	{
		super(x, y, field);
	}
	
	public abstract void eaten();
}
