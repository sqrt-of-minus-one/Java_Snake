package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

public abstract class Eatable
		extends Tile
{
	private int deltaSize;
	
	public Eatable(int x, int y, Field field, int lifetime, int deltaSize)
	{
		super(x, y, field, lifetime);
		this.deltaSize = deltaSize;
	}
	
	public int getDeltaSize()
	{
		return deltaSize;
	}
	
	public abstract void eaten();
}
