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
	private int lifetime; // The time before the tile disappears (negative if the tile won't disappear
	
	public Tile(Field field, int lifetime)
	{
		super(field);
		this.lifetime = lifetime;
	}
	
	public Tile(int x, int y, Field field, int lifetime)
	{
		super(x, y, field);
		this.lifetime = lifetime;
	}
	
	// Is called by the snake when it moves
	public void onSnakeMove()
	{
		if (lifetime >= 0 && --lifetime == 0)
		{
			// Destroy the tile if it's time
			getField().replaceTile(this, new Grass(getX(), getY(), getField()));
		}
	}
	
	// Get the neighbour tile
	public Tile getNextTile(EDirection direction)
	{
		return getField().getNextTile(this, direction);
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
}
