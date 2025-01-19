package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.AbstractCommand;
import edu.mephi.java.engine.command.AbstractParameter;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.SnakeGame;
import edu.mephi.java.snake.tiles.Tile;

public abstract class Command extends
		AbstractCommand<SnakeGame, Field, Tile, Command>
{
	public Command(String sprite, AbstractParameter[] parameters, SnakeGame game)
	{
		super(sprite, parameters, game);
	}
}
