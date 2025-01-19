package edu.mephi.java.engine;

import edu.mephi.java.engine.command.AbstractCommand;

import javax.swing.*;
import java.lang.ref.WeakReference;

//
public abstract class AbstractTile<
		Game extends AbstractGame<Game, Field, Tile, Command>,
		Field extends AbstractField<Game, Field, Tile, Command>,
		Tile extends AbstractTile<Game, Field, Tile, Command>,
		Command extends AbstractCommand<Game, Field, Tile, Command>>
{
	private final WeakReference<Field> field;
	private int x = 0, y = 0;
	private boolean alive = false; // Whether the tile is currently on the field
	
	public AbstractTile(Field field)
	{
		this.field = new WeakReference<>(field);
	}
	public AbstractTile(int x, int y, Field field)
	{
		this.field = new WeakReference<>(field);
		this.x = x;
		this.y = y;
	}
	
	public Field getField()
	{
		return field.get();
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Game getGame()
	{
		return getField().getGame();
	}
	
	public AbstractResourceManager getResourceManager()
	{
		return getField().getGame().getResourceManager();
	}
	
	// Returns the sprite for the tile
	public abstract String getSprite();
	
	// Is supposed to be invoked only by the Field class
	protected void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected void setAlive(boolean alive)
	{
		this.alive = alive;
	}
}
