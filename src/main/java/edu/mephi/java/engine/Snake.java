package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Snake
{
	private SnakeHead head;
	private SnakeTail tail;
	private Field field;
	private EDirection headDirection;
	private EDirection moveDirection;
	private Timer blinkTimer;
	private Timer unblinkTimer;
	private static final int BLINK_TIME_MS = 5000;
	private static final int UNBLINK_TIME_MS = 500;
	
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
		
		blinkTimer = new Timer(BLINK_TIME_MS, e ->
		{
			unblinkTimer.start();
			head.setBlink(true);
		});
		blinkTimer.start();
		
		unblinkTimer = new Timer(UNBLINK_TIME_MS, e -> head.setBlink(false));
		unblinkTimer.setRepeats(false);
	}
	
	public SnakeHead getHead()
	{
		return head;
	}
	
	public SnakeTail getTail()
	{
		return tail;
	}
	
	public void setDirection(EDirection direction)
	{
		if (direction != headDirection.getOpposite())
		{
			moveDirection = direction;
		}
	}
	
	public void move()
	{
		Tile nextTile = field.getNextTile(head, moveDirection);
		if (Eatable.class.isAssignableFrom(nextTile.getClass()))
		{
			Eatable nextEatable = (Eatable)nextTile;
			move_(nextEatable.getDeltaSize());
			nextEatable.eaten();
		}
		else if (Obstacle.class.isAssignableFrom(nextTile.getClass()))
		{
			Obstacle nextObstacle = (Obstacle)nextTile;
			nextObstacle.collide();
			if (nextObstacle.isLostOnCollide())
			{
				lose();
			}
		}
		else if (SnakeBody.class.isAssignableFrom(nextTile.getClass()))
		{
			lose();
		}
		else
		{
			move_(0);
		}
	}
	
	public void lose()
	{
		field.lose();
	}
	
	private void move_(int deltaSize)
	{
		head.move(moveDirection);
		
		SnakeTile oldTail = tail;
		SnakeTile newTail = tail;
		for (int i = 0; i < 1 - deltaSize; i++)
		{
			field.setTile(tail.getX(), tail.getY(), new Grass(tail.getX(), tail.getY(), field));
			newTail = tail.getPrevious();
		}
		oldTail.setXY(newTail.getX(), newTail.getY());
		oldTail.setPrevious(newTail.getPrevious());
		oldTail.getPrevious().setNext(oldTail);
		field.setTile(newTail.getX(), newTail.getY(), oldTail);
		
		headDirection = head.getDirection();
	}
}
