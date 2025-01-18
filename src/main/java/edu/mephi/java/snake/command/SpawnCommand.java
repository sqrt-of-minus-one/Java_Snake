package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.AbstractParameter;
import edu.mephi.java.engine.command.IntegerParameter;
import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Game;
import edu.mephi.java.snake.tiles.*;

import javax.swing.*;

import static edu.mephi.java.snake.ESprite.GRASS;

// Spawn an object of the field
public class SpawnCommand
		extends Command
{
	public static final int SPAWNABLE_PARAMETER_INDEX = 0;
	public static final int X_PARAMETER_INDEX = 1;
	public static final int Y_PARAMETER_INDEX = 2;
	
	// Spawns an object on the field
	// Parameters:
	//   1 (Spawnable). The type of the object that will be spawned
	//   2 (Number). The X coordinate of the object
	//   3 (Number). The Y coordinate of the object
	public SpawnCommand(Game game)
	{
		super(ESprite.APPLE.toString(), new AbstractParameter[]{
				new SpawnableParameter(),
				new IntegerParameter(0, game.getSizeX() - 1, 2),
				new IntegerParameter(0, game.getSizeY() - 1, 2)
		}, game);
	}
	
	@Override
	protected boolean apply_()
	{
		if (isComplete())
		{
			String spawnable = ((SpawnableParameter)getParameter(SPAWNABLE_PARAMETER_INDEX)).getValue();
			int x = ((IntegerParameter)getParameter(X_PARAMETER_INDEX)).getValue();
			int y = ((IntegerParameter)getParameter(Y_PARAMETER_INDEX)).getValue();
			Tile tileToCreate = null;
			
				 if (spawnable.equals(ESprite.GRASS.toString()))		tileToCreate = new Grass(x, y, getGame().getField());
			else if (spawnable.equals(ESprite.APPLE.toString()))		tileToCreate = new Apple(x, y, getGame().getField());
			else if (spawnable.equals(ESprite.ROTTEN_APPLE.toString()))	tileToCreate = new RottenApple(x, y, getGame().getField());
			else if (spawnable.equals(ESprite.REVERSE_PILL.toString()))	tileToCreate = new ReversePill(x, y, getGame().getField());
			else if (spawnable.equals(ESprite.SHIELD.toString()))		tileToCreate = new Shield(x, y, getGame().getField());
			else if (spawnable.equals(ESprite.HAMMER.toString()))		tileToCreate = new Hammer(x, y, getGame().getField());
			else if (spawnable.equals(ESprite.WALL.toString()))			tileToCreate = new Wall(x, y, getGame().getField());
			
			return true;
		}
		return false;
	}
}
