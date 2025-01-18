package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.AbstractCommand;
import edu.mephi.java.engine.command.AbstractParameter;
import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.Game;
import edu.mephi.java.snake.ResourceManager;
import edu.mephi.java.snake.tiles.Tile;

import javax.swing.*;

public abstract class Command extends
		AbstractCommand<Game, Field, Tile, Command>
{
	public Command(String sprite, AbstractParameter[] parameters, Game game)
	{
		super(sprite, parameters, game);
	}
}
