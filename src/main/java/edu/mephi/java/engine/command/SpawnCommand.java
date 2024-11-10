package edu.mephi.java.engine.command;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Game;
import edu.mephi.java.engine.ResourceManager;
import edu.mephi.java.engine.tiles.*;

import javax.swing.*;

// Spawn an object of the field
public class SpawnCommand extends Command
{
	// The status of the command
	public enum EInputPosition
	{
		SPAWNABLE,	// Waiting for the object type to be specified
		X,			// Waiting for the x coordinate to be specified
		Y,			// Waiting for the y coordinate to be specified
		COMPLETE	// The command is complete
	}
	
	private ESpawnable spawnable; // The object type
	private int x; // The x coordinate
	private int y; // The y coordinate
	private EInputPosition inputPosition; // The current status
	
	// Spawns an object on the field
	// Parameters:
	//   1 (Spawnable). The type of the object that will be spawned
	//   2 (Number). The X coordinate of the object
	//   3 (Number). The Y coordinate of the object
	public SpawnCommand(Game game)
	{
		super(game);
		
		spawnable = null;
		x = 0;
		y = 0;
		inputPosition = EInputPosition.SPAWNABLE;
	}
	
	@Override
	public EWaitingFor waitingFor()
	{
		// If the command has been applied
		if (getApplyResult() != null)
		{
			return EWaitingFor.APPLIED;
		}
		
		return switch (inputPosition)
		{
			case SPAWNABLE -> EWaitingFor.SPAWNABLE;
			case X, Y -> EWaitingFor.NUMBER;
			case COMPLETE -> EWaitingFor.COMPLETE;
		};
	}
	
	@Override
	public void draw(JLabel[] labels)
	{
		drawEmpty(labels);
		
		// If the command has been applied, draw the result
		if (getApplyResult() != null)
		{
			labels[1].setIcon(ResourceManager.getSprite(getApplyResult() ? ESprite.SUCCESS : ESprite.FAILURE));
			return;
		}
		
		// The command icon
		labels[1].setIcon(ResourceManager.getSprite(ESprite.APPLE));
		
		switch (inputPosition)
		{
			case COMPLETE:
			case Y:
			{
				drawNumber(labels, 6, 7, y);
				// fallthrough
			}
			case X:
			{
				drawNumber(labels, 3, 4, x);
				// fallthrough
			}
			case SPAWNABLE:
			{
				labels[2].setIcon(ResourceManager.getSprite(
						switch (spawnable)
						{
							case null -> ESprite.QUESTION;
							case GRASS -> ESprite.GRASS;
							case APPLE -> ESprite.APPLE;
							case ROTTEN_APPLE -> ESprite.ROTTEN_APPLE;
							case REVERSE_PILL -> ESprite.REVERSE_PILL;
							case SHIELD -> ESprite.SHIELD;
							case HAMMER -> ESprite.HAMMER;
							case WALL -> ESprite.WALL;
						}));
				// fallthrough
			}
		}
	}
	
	@Override
	public void setSpawnable(ESpawnable spawnable)
	{
		this.spawnable = spawnable;
	}
	
	@Override
	public void setEffect(EEffect effect)
	{}
	
	public void addDigit(int digit)
	{
		// The digit cannot be added if the command doesn't expect a number
		if (inputPosition != EInputPosition.X && inputPosition != EInputPosition.Y)
		{
			return;
		}
		
		if (digit < 0)
		{
			digit = -digit;
		}
		digit %= 10;
		
		// What parameter is being specified
		switch (inputPosition)
		{
			case X:
			{
				// Add the digit to the x
				x *= 10;
				x += digit;
				if (x >= getGame().getField().getWidth()) // The x coordinate must be less than the field width
				{
					x = (digit >= getGame().getField().getWidth() ? 0 : digit);
				}
				break;
			}
			case Y:
			{
				// Add the digit to the y
				y *= 10;
				y += digit;
				if (y >= getGame().getField().getHeight()) // The y coordinate must be less than the field height
				{
					y = (digit >= getGame().getField().getHeight() ? 0 : digit);
				}
				break;
			}
		}
	}
	
	@Override
	public void applyParameter()
	{
		// Expect the next parameter of mark the command as complete
		inputPosition = switch (inputPosition)
		{
			case SPAWNABLE -> spawnable != null ? EInputPosition.X : EInputPosition.SPAWNABLE;
			case X -> EInputPosition.Y;
			case Y, COMPLETE -> EInputPosition.COMPLETE;
		};
	}
	
	@Override
	protected boolean apply_()
	{
		if (inputPosition == EInputPosition.COMPLETE && // Only complete commands can be applied
				x >= 0 && x < getGame().getField().getWidth() && // The x coordinate must be less than the field width
				y >= 0 && y < getGame().getField().getHeight() && // The y coordinate must be less than the field height
				!SnakeTile.class.isAssignableFrom(getGame().getField().getTile(x, y).getClass())) // The snake tiles cannot be replaced
		{
			// Spawn an object
			getGame().getField().setTile(x, y, switch (spawnable)
			{
				case GRASS -> new Grass(x, y, getGame().getField());
				case APPLE -> new Apple(x, y, getGame().getField());
				case ROTTEN_APPLE -> new RottenApple(x, y, getGame().getField());
				case REVERSE_PILL -> new ReversePill(x, y, getGame().getField());
				case SHIELD -> new Shield(x, y, getGame().getField());
				case HAMMER -> new Hammer(x, y, getGame().getField());
				case WALL -> new Wall(x, y, getGame().getField());
			});
			return true;
		}
		return false;
	}
}
