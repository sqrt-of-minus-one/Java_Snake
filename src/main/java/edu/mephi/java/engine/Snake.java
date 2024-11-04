package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.EDirection;
import edu.mephi.java.engine.tiles.SnakeHead;
import edu.mephi.java.engine.tiles.SnakeTail;
import edu.mephi.java.engine.tiles.SnakeTile;

public class Snake
{
	private SnakeHead head;
	private SnakeTail tail;
	private Field field;
	private EDirection headDirection;
	private EDirection moveDirection;
	
	public Snake(int x, int y, Field field, EDirection direction, int length)
	{
		this.field = field;
		headDirection = direction;
		moveDirection = direction;
		
		head = new SnakeHead(x, y, field, direction, length);
		
		SnakeTile tile = head.getNext();
		while (tile.getNext() != null)
		{
			tile = tile.getNext();
		}
		tail = (SnakeTail)tile;
	}
	
	public SnakeHead getHead()
	{
		return head;
	}
	
	public SnakeTail getTail()
	{
		return tail;
	}
}
