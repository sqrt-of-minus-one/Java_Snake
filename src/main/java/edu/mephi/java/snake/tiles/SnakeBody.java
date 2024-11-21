package edu.mephi.java.snake.tiles;

import edu.mephi.java.engine.EDirection;
import edu.mephi.java.engine.EDoubleDirection;
import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;
import java.lang.ref.WeakReference;

// The snake tile which is neither a head nor the tail
public class SnakeBody
		extends SnakeTile
{
	private WeakReference<SnakeTile> previous; // The previous snake tile
	private SnakeTile next; // The next snake tile
	
	// This constructor is used when the snake is created via the `SnakeHead` constructor
	public SnakeBody(int x, int y, Field field, SnakeTile previous, EDirection snakeDirection, int lengthLeft)
	{
		super(x, y, field);
		this.previous = new WeakReference<>(previous);
		
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
		if (lengthLeft > 1)
		{
			next = new SnakeBody(nextX, nextY, field, this, snakeDirection, lengthLeft - 1);
		}
		else
		{
			next = new SnakeTail(nextX, nextY, field, this);
		}
	}
	
	// Create a new body segment for the snake that already exists
	// The `previous` pointer of the next tile is changed automatically
	// The `next` pointer of the previous tile is not changed
	public SnakeBody(int x, int y, Field field, SnakeTile previous, SnakeTile next)
	{
		super(x, y, field);
		this.previous = new WeakReference<>(previous);
		this.next = next;
		next.setPrevious(this);
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
	public void setPrevious(SnakeTile previous)
	{
		this.previous = new WeakReference<>(previous);
	}
	
	@Override
	public void setNext(SnakeTile next)
	{
		this.next = next;
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
		// In order to get the direction of the body segment, we need to check the positions of the neighbour tiles
		EDirection next_direction = getDirectionTo(next); // Where is the next tile
		EDirection previous_direction = getDirectionTo(previous.get()); // Where is the previous tile
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
