package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

import javax.swing.*;

public abstract class Tile
{
	private int x, y;
	private Field field;
	private int lifetime;
	
	public Tile(int x, int y, Field field, int lifetime)
	{
		this.x = x;
		this.y = y;
		this.field = field;
		this.lifetime = lifetime;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Field getField()
	{
		return field;
	}
	
	public void onMove()
	{
		if (lifetime >= 0 && --lifetime == 0)
		{
			field.setTile(x, y, new Grass(x, y, field));
		}
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
