package edu.mephi.java.three.command;

import edu.mephi.java.engine.command.AbstractCommand;
import edu.mephi.java.engine.command.AbstractParameter;
import edu.mephi.java.three.Field;
import edu.mephi.java.three.ThreeGame;
import edu.mephi.java.three.tiles.Tile;

public abstract class Command
	extends AbstractCommand<ThreeGame, Field, Tile, Command>
{
	public Command(String sprite, AbstractParameter[] parameters, ThreeGame game)
	{
		super(sprite, parameters, game);
	}
}
