package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.Field;

// The obstacle that the snake can collide with
public abstract class Obstacle extends Tile
{
	private final boolean lostOnCollide; // Is the game over when the snake collides with the object
	
	public Obstacle(Field field, int lifetime, boolean lostOnCollide)
	{
		super(field, lifetime);
		this.lostOnCollide = lostOnCollide;
	}
	
	public Obstacle(int x, int y, Field field, int lifetime, boolean lostOnCollide)
	{
		super(x, y, field, lifetime);
		this.lostOnCollide = lostOnCollide;
	}
	
	public boolean isLostOnCollide()
	{
		return lostOnCollide;
	}
	
	public abstract void collide();
}
