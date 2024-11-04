package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

public abstract class SnakeTile extends Tile
{
	public SnakeTile(int x, int y, Field field)
	{
		super(x, y, field);
	}
	
	public abstract SnakeTile getPrevious();
	public abstract SnakeTile getNext();
}
