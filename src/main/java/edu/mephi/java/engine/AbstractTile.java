package edu.mephi.java.engine;

import edu.mephi.java.engine.command.AbstractCommand;

import javax.swing.*;
import java.lang.ref.WeakReference;

public abstract class AbstractTile<
		Game extends AbstractGame<Game, Field, Tile, Command>,
		Field extends AbstractField<Game, Field, Tile, Command>,
		Tile extends AbstractTile<Game, Field, Tile, Command>,
		Command extends AbstractCommand<Game, Field, Tile, Command>>
{
	private final WeakReference<Field> field;
	
	public AbstractTile(Field field)
	{
		this.field = new WeakReference<>(field);
	}
	
	public Field getField()
	{
		return field.get();
	}
	
	// Returns the sprite for the tile
	public abstract ImageIcon getSprite();
}
