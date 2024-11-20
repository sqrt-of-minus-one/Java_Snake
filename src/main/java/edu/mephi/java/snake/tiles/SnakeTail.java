package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.Field;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;
import java.lang.ref.WeakReference;

public class SnakeTail
		extends SnakeTile
{
	private WeakReference<SnakeTile> previous;
	
	public SnakeTail(int x, int y, Field field, SnakeTile previous)
	{
		super(x, y, field);
		this.previous = new WeakReference<>(previous);
	}
	
	@Override
	public SnakeTile getPrevious()
	{
		return previous.get();
	}
	
	@Override
	public SnakeTile getNext()
	{
		return null;
	}
	
	@Override
	public void setPrevious(SnakeTile previous)
	{
		this.previous = new WeakReference<>(previous);
	}
	
	@Override
	public void setNext(SnakeTile next)
	{
		throw new RuntimeException("The snake tail cannot have a next tile");
	}
	
	@Override
	public ImageIcon getSprite()
	{
		// The sprite depends on the direction of the tail
		return ResourceManager.getSprite(
				switch (getDirection())
				{
					case UP -> ESprite.SNAKE_TAIL_UP;
					case DOWN -> ESprite.SNAKE_TAIL_DOWN;
					case LEFT -> ESprite.SNAKE_TAIL_LEFT;
					case RIGHT -> ESprite.SNAKE_TAIL_RIGHT;
				});
	}
	
	public EDirection getDirection()
	{
		// The direction of the tail depends on where the previous tile is
		// (previous.get() should not be null)
		return previous.get().getDirectionTo(this);
	}
}
