package edu.mephi.java.snake.tiles;

import edu.mephi.java.engine.AbstractTile;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.Game;
import edu.mephi.java.snake.command.Command;

import javax.swing.*;

public abstract class Tile
	extends AbstractTile<Game, Field, Tile, Command>
{
	private int x, y; // The tile coordinates
	private int lifetime; // The time before the tile disappears (negative if the tile won't disappear
	
	public Tile(int x, int y, Field field, int lifetime)
	{
		super(field);
		this.x = x;
		this.y = y;
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
	
	// Is called by the snake when it moves
	public void onMove()
	{
		if (lifetime >= 0 && --lifetime == 0)
		{
			// Destroy the tile if it's time
			field.setTile(x, y, new Grass(x, y, field));
		}
	}
	
	// Get the neighbour tile
	public Tile getNextTile(EDirection direction)
	{
		return field.getNextTile(this, direction);
	}
	
	// If the tile is neighbour, returns the direction to it
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
	
	// Returns the sprite for the tile
	public abstract ImageIcon getSprite();
}
