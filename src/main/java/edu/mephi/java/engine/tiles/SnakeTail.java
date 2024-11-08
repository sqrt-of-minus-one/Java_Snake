package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

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
		return previous.get().getDirectionTo(this);
	}
}
