package edu.mephi.java.snake.tiles;

import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

public class SnakeHead
		extends SnakeTile
{
	private SnakeTile next; // The next snake tile
	private boolean blink; // If the snake is blinking at the moment
	
	public SnakeHead(int x, int y, Field field, EDirection snakeDirection, int length)
	{
		super(x, y, field);
		
		// The coordinates of the next tile
		int nextX = x;
		int nextY = y;
		switch (snakeDirection)
		{
			case UP -> nextY++;
			case DOWN -> nextY--;
			case LEFT -> nextX++;
			case RIGHT -> nextX--;
		}
		
		// The next tile can be either a body or a tail
		if (length > 2)
		{
			next = new SnakeBody(nextX, nextY, field, this, snakeDirection, length - 1);
		}
		else
		{
			next = new SnakeTail(nextX, nextY, field, this);
		}
	}
	
	@Override
	public SnakeTile getPrevious()
	{
		return null;
	}
	
	@Override
	public SnakeTile getNext()
	{
		return next;
	}
	
	@Override
	public void setPrevious(SnakeTile previous)
	{
		throw new RuntimeException("The snake head cannot have a previous tile");
	}
	
	@Override
	public void setNext(SnakeTile next)
	{
		this.next = next;
	}
	
	public void setBlink(boolean blink)
	{
		this.blink = blink;
		getGame().updateFieldSprite(getX(), getY());
		getGame().repaint();
	}
	
	@Override
	public ImageIcon getSprite()
	{
		// Return a failure sign if the game is over
		if (getField().getGame().isGameOver())
		{
			return getResourceManager().getSprite(ECommonSprite.FAILURE);
		}
		
		// Each direction has its own sprite set
		// If the next tile is eatable, the snake opens its mouth ("ready" sprites)
		// The snake can blink
		switch (getDirection())
		{
		case UP:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.UP).getClass()))
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_READY_BLINK_UP : ESprite.SNAKE_HEAD_READY_UP).toString());
			}
			else
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_BLINK_UP : ESprite.SNAKE_HEAD_UP).toString());
			}
		}
		case DOWN:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.DOWN).getClass()))
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_READY_BLINK_DOWN : ESprite.SNAKE_HEAD_READY_DOWN).toString());
			}
			else
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_BLINK_DOWN : ESprite.SNAKE_HEAD_DOWN).toString());
			}
		}
		case LEFT:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.LEFT).getClass()))
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_READY_BLINK_LEFT : ESprite.SNAKE_HEAD_READY_LEFT).toString());
			}
			else
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_BLINK_LEFT : ESprite.SNAKE_HEAD_LEFT).toString());
			}
		}
		case RIGHT:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.RIGHT).getClass()))
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_READY_BLINK_RIGHT : ESprite.SNAKE_HEAD_READY_RIGHT).toString());
			}
			else
			{
				return getResourceManager().getSprite((blink ? ESprite.SNAKE_HEAD_BLINK_RIGHT : ESprite.SNAKE_HEAD_RIGHT).toString());
			}
		}
		}
		throw new RuntimeException("There's something wrong with the snake head direction: it has un unknown value");
	}
	
	public EDirection getDirection()
	{
		// The direction of the head depends on the direction to the next tile
		return next.getDirectionTo(this);
	}
	
	// The tile in front of the snake will be destroyed
	// The possibility of the movement is supposed to be confirmed before this method is called
	// The tail is not moved, which means the snake length will be increased by 1
	public void move(EDirection direction)
	{
		// Move the head to the next tile
		next = new SnakeBody(getX(), getY(), getField(), this, next);
		getField().moveTile(this, direction, next);
	}
}
