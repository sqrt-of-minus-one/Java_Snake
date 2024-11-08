package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.Field;

public abstract class Obstacle extends Tile
{
	private boolean lostOnCollide;
	
	public Obstacle(int x, int y, Field field, boolean lostOnCollide)
	{
		super(x, y, field);
		this.lostOnCollide = lostOnCollide;
	}
	
	public boolean isLostOnCollide()
	{
		return lostOnCollide;
	}
	
	public abstract void collide();
}
