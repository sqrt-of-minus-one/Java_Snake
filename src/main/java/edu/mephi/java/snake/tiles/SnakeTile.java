package edu.mephi.java.snake.tiles;

import edu.mephi.java.snake.Field;

// The basic class of any snake tile
// The snake tiles are stored as linked list
// There are three types of snake tiles: head, body, and tail
public abstract class SnakeTile extends Tile
{
	// To create a snake, use the constructor of the `SnakeHead` class
	public SnakeTile(int x, int y, Field field)
	{
		super(x, y, field, -1);
	}
	
	// Getters and setters for the neighbour snake tiles
	// Remember that the head cannot have a previous tile, and the tail cannot have a next tile
	public abstract SnakeTile getPrevious();
	public abstract SnakeTile getNext();
	public abstract void setPrevious(SnakeTile previous);
	public abstract void setNext(SnakeTile next);
}
