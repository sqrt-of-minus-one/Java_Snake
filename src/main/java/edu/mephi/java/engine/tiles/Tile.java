package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

import javax.swing.*;

public abstract class Tile
{
	private int x, y;
	private Field field;
	
	public Tile(int x, int y, Field field)
	{
		this.x = x;
		this.y = y;
		this.field = field;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Field getField()
	{
		return field;
	}
	
	public Tile getNextTile(EDirection direction)
	{
		return field.getNextTile(this, direction);
	}
	
	public EDirection getDirectionTo(Tile tile)
	{
		if (tile == getNextTile(EDirection.UP))
		{
			return EDirection.UP;
		}
		if (tile == getNextTile(EDirection.DOWN))
		{
			return EDirection.DOWN;
		}
		if (tile == getNextTile(EDirection.LEFT))
		{
			return EDirection.LEFT;
		}
		if (tile == getNextTile(EDirection.RIGHT))
		{
			return EDirection.RIGHT;
		}
		return null;
	}
	
	public abstract ImageIcon getSprite();
}
