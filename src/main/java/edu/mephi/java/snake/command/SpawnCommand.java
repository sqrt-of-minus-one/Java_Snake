package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.AbstractParameter;
import edu.mephi.java.engine.command.IntegerParameter;
import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.SnakeGame;
import edu.mephi.java.snake.tiles.*;

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
	public SpawnCommand(SnakeGame game)
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
		if (!isComplete())
		{
			return false;
		}
		
		String spawnable = ((SpawnableParameter)getParameter(SPAWNABLE_PARAMETER_INDEX)).getValue();
		int x = ((IntegerParameter)getParameter(X_PARAMETER_INDEX)).getValue();
		int y = ((IntegerParameter)getParameter(Y_PARAMETER_INDEX)).getValue();
		if (SnakeTile.class.isAssignableFrom(getGame().getField().getExactTile(x, y).getClass()))
		{
			return false;
		}
		
		Tile tileToCreate = null;
			 if (spawnable.equals(ESprite.GRASS.toString()))		tileToCreate = new Grass(getGame().getField());
		else if (spawnable.equals(ESprite.APPLE.toString()))		tileToCreate = new Apple(getGame().getField());
		else if (spawnable.equals(ESprite.ROTTEN_APPLE.toString()))	tileToCreate = new RottenApple(getGame().getField());
		else if (spawnable.equals(ESprite.REVERSE_PILL.toString()))	tileToCreate = new ReversePill(getGame().getField());
		else if (spawnable.equals(ESprite.SHIELD.toString()))		tileToCreate = new Shield(getGame().getField());
		else if (spawnable.equals(ESprite.HAMMER.toString()))		tileToCreate = new Hammer(getGame().getField());
		else if (spawnable.equals(ESprite.WALL.toString()))			tileToCreate = new Wall(getGame().getField());
		
		getGame().getField().replaceTile(x, y, tileToCreate);
		
		return true;
	}
}
