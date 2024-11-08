package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

public abstract class SnakeTile extends Tile
{
	public SnakeTile(int x, int y, Field field)
	{
		super(x, y, field, -1);
	}
	
	public abstract SnakeTile getPrevious();
	public abstract SnakeTile getNext();
	public abstract void setPrevious(SnakeTile previous);
	public abstract void setNext(SnakeTile next);
}
