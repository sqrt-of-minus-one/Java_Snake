package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class SnakeHead
		extends SnakeTile
{
	private SnakeTile next;
	private boolean blink;
	
	public SnakeHead(int x, int y, Field field, EDirection snakeDirection, int length)
	{
		super(x, y, field);
		int nextX = x;
		int nextY = y;
		switch (snakeDirection)
		{
			case UP -> nextY++;
			case DOWN -> nextY--;
			case LEFT -> nextX++;
			case RIGHT -> nextX--;
		}
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
	
	public boolean getBlink()
	{
		return blink;
	}
	
	public void setBlink(boolean blink)
	{
		this.blink = blink;
		getField().getGame().updateSprite(getX(), getY());
		getField().getGame().repaint();
	}
	
	@Override
	public ImageIcon getSprite()
	{
		switch (getDirection())
		{
		case UP:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.UP).getClass()))
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_READY_BLINK_UP : ESprite.SNAKE_HEAD_READY_UP);
			}
			else
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_BLINK_UP : ESprite.SNAKE_HEAD_UP);
			}
		}
		case DOWN:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.DOWN).getClass()))
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_READY_BLINK_DOWN : ESprite.SNAKE_HEAD_READY_DOWN);
			}
			else
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_BLINK_DOWN : ESprite.SNAKE_HEAD_DOWN);
			}
		}
		case LEFT:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.LEFT).getClass()))
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_READY_BLINK_LEFT : ESprite.SNAKE_HEAD_READY_LEFT);
			}
			else
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_BLINK_LEFT : ESprite.SNAKE_HEAD_LEFT);
			}
		}
		case RIGHT:
		{
			if (Eatable.class.isAssignableFrom(getNextTile(EDirection.RIGHT).getClass()))
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_READY_BLINK_RIGHT : ESprite.SNAKE_HEAD_READY_RIGHT);
			}
			else
			{
				return ResourceManager.getSprite(blink ? ESprite.SNAKE_HEAD_BLINK_RIGHT : ESprite.SNAKE_HEAD_RIGHT);
			}
		}
		}
		throw new RuntimeException("There's something wrong with the snake head direction: it has un unknown value");
	}
	
	public EDirection getDirection()
	{
		return next.getDirectionTo(this);
	}
	
	public void move(EDirection direction)
	{
		Tile nextTile = getNextTile(direction);
		getField().moveTile(getX(), getY(), nextTile.getX(), nextTile.getY());
		
		next = new SnakeBody(getX(), getY(), getField(), this, next);
		setXY(nextTile.getX(), nextTile.getY());
		getField().setTile(next.getX(), next.getY(), next);
	}
}
