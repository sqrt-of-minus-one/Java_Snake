package edu.mephi.java.engine.command;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Game;
import edu.mephi.java.engine.ResourceManager;
import edu.mephi.java.engine.tiles.*;

import javax.swing.*;

public class SpawnCommand extends Command
{
	public enum EInputPosition
	{
		SPAWNABLE, X, Y, COMPLETE
	}
	
	private ESpawnable spawnable;
	private int x;
	private int y;
	private EInputPosition inputPosition;
	
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
		
		if (getApplyResult() != null)
		{
			labels[1].setIcon(ResourceManager.getSprite(getApplyResult() ? ESprite.SUCCESS : ESprite.FAILURE));
			return;
		}
		
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
		if (inputPosition != EInputPosition.X && inputPosition != EInputPosition.Y)
		{
			return;
		}
		
		if (digit < 0)
		{
			digit = -digit;
		}
		digit %= 10;
		
		switch (inputPosition)
		{
			case X:
			{
				x *= 10;
				x += digit;
				if (x >= getGame().getWidth())
				{
					x = (digit >= getGame().getWidth() ? 0 : digit);
				}
				break;
			}
			case Y:
			{
				y *= 10;
				y += digit;
				if (y >= getGame().getHeight())
				{
					y = (digit >= getGame().getHeight() ? 0 : digit);
				}
				break;
			}
		}
	}
	
	@Override
	public void applyElement()
	{
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
		if (inputPosition == EInputPosition.COMPLETE &&
				x >= 0 && x < getGame().getWidth() &&
				y >= 0 && y < getGame().getHeight() &&
				!SnakeTile.class.isAssignableFrom(getGame().getField().getTile(x, y).getClass()))
		{
			getGame().getField().setTile(x, y, switch (spawnable)
			{
				case GRASS -> new Grass(x, y, getGame().getField());
				case APPLE -> new Apple(x, y, getGame().getField());
				case ROTTEN_APPLE -> new RottenApple(x, y, getGame().getField());
				case REVERSE_PILL -> new ReversePill(x, y, getGame().getField());
				case SHIELD -> new Shield(x, y, getGame().getField());
				case HAMMER -> new Hammer(x, y, getGame().getField());
			});
			return true;
		}
		return false;
	}
}
