package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;
import java.lang.ref.WeakReference;

public class SnakeBody
		extends SnakeTile
{
	private WeakReference<SnakeTile> previous;
	private SnakeTile next;
	
	public SnakeBody(int x, int y, Field field, SnakeTile previous, EDirection snakeDirection, int lengthLeft)
	{
		super(x, y, field);
		this.previous = new WeakReference<>(previous);
		int nextX = x;
		int nextY = y;
		switch (snakeDirection)
		{
			case UP -> nextY++;
			case DOWN -> nextY--;
			case LEFT -> nextX++;
			case RIGHT -> nextX--;
		}
		if (lengthLeft > 1)
		{
			next = new SnakeBody(nextX, nextY, field, this, snakeDirection, lengthLeft - 1);
		}
		else
		{
			next = new SnakeTail(nextX, nextY, field, this);
		}
	}
	
	@Override
	public SnakeTile getPrevious()
	{
		return previous.get();
	}
	
	@Override
	public SnakeTile getNext()
	{
		return next;
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(
				switch (getDirection())
				{
					case UP_DOWN -> ESprite.SNAKE_BODY_UP_DOWN;
					case LEFT_RIGHT -> ESprite.SNAKE_BODY_LEFT_RIGHT;
					case UP_RIGHT -> ESprite.SNAKE_BODY_TURN_UP_RIGHT;
					case DOWN_RIGHT -> ESprite.SNAKE_BODY_TURN_DOWN_RIGHT;
					case UP_LEFT -> ESprite.SNAKE_BODY_TURN_UP_LEFT;
					case DOWN_LEFT -> ESprite.SNAKE_BODY_TURN_DOWN_LEFT;
				});
	}
	
	public EDoubleDirection getDirection()
	{
		EDirection next_direction = getDirectionTo(next);
		EDirection previous_direction = getDirectionTo(previous.get());
		return switch (next_direction)
		{
			case UP -> switch (previous_direction)
			{
				case UP -> null;
				case DOWN -> EDoubleDirection.UP_DOWN;
				case LEFT -> EDoubleDirection.UP_LEFT;
				case RIGHT -> EDoubleDirection.UP_RIGHT;
			};
			case DOWN -> switch (previous_direction)
			{
				case UP -> EDoubleDirection.UP_DOWN;
				case DOWN -> null;
				case LEFT -> EDoubleDirection.DOWN_LEFT;
				case RIGHT -> EDoubleDirection.DOWN_RIGHT;
			};
			case LEFT -> switch (previous_direction)
			{
				case UP -> EDoubleDirection.UP_LEFT;
				case DOWN -> EDoubleDirection.DOWN_LEFT;
				case LEFT -> null;
				case RIGHT -> EDoubleDirection.LEFT_RIGHT;
			};
			case RIGHT -> switch (previous_direction)
			{
				case UP -> EDoubleDirection.UP_RIGHT;
				case DOWN -> EDoubleDirection.DOWN_RIGHT;
				case LEFT -> EDoubleDirection.LEFT_RIGHT;
				case RIGHT -> null;
			};
		};
	}
}
