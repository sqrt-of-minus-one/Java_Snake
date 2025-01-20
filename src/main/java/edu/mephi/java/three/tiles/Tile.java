package edu.mephi.java.three.tiles;

import edu.mephi.java.engine.AbstractTileSelect;
import edu.mephi.java.three.Field;
import edu.mephi.java.three.ThreeGame;
import edu.mephi.java.three.command.Command;

public abstract class Tile
	extends AbstractTileSelect<ThreeGame, Field, Tile, Command>
{
	public Tile(Field field)
	{
		super(field);
	}
	
	public Tile(int x, int y, Field field)
	{
		super(x, y, field);
	}
}
