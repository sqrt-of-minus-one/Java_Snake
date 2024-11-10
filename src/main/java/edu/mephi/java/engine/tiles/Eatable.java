package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

// The tile that the snake can eat
public abstract class Eatable
		extends Tile
{
	private final int deltaSize; // How the object affects the snake length. Only the sign of the value matters
	
	public Eatable(int x, int y, Field field, int lifetime, int deltaSize)
	{
		super(x, y, field, lifetime);
		this.deltaSize = deltaSize;
	}
	
	public int getDeltaSize()
	{
		return deltaSize;
	}
	
	// Is called when the snake eats the object
	public abstract void eaten();
}
