package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.*;

import javax.swing.*;

public class Snake
{
	private SnakeHead head;
	private SnakeTail tail;
	private Field field;
	private int length;
	private int shield;
	private EDirection headDirection;
	private EDirection moveDirection;
	private EDirection nextMoveDirection;
	private Timer blinkTimer;
	private Timer unblinkTimer;
	private static final int BLINK_TIME_MS = 5000;
	private static final int UNBLINK_TIME_MS = 500;
	
	public Snake(int x, int y, Field field, EDirection direction, int length)
	{
		this.field = field;
		headDirection = direction;
		moveDirection = direction;
		nextMoveDirection = direction;
		this.length = length;
		shield = 0;
		
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
	
	public int getLength()
	{
		return length;
	}
	
	public int getShield()
	{
		return shield;
	}
	
	public void setDirection(EDirection direction)
	{
		if (direction != headDirection && direction != headDirection.getOpposite())
		{
			moveDirection = direction;
			nextMoveDirection = moveDirection;
		}
		else if (moveDirection != headDirection)
		{
			nextMoveDirection = direction;
		}
	}
	
	public void setShield(int shield)
	{
		this.shield = shield;
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
				if (shield > 0)
				{
					move_(0);
					shield = 0;
				}
				else
				{
					field.getGame().lose();
				}
			}
		}
		else if (SnakeBody.class.isAssignableFrom(nextTile.getClass()))
		{
			if (shield > 0)
			{
				SnakeBody nextBody = (SnakeBody)nextTile;
				for (SnakeTile i = nextBody.getNext(); i != null; i = i.getNext())
				{
					field.setTile(i.getX(), i.getY(), new Grass(i.getX(), i.getY(), field));
					length--;
				}
				field.setTile(nextBody.getX(), nextBody.getY(), tail);
				tail.setXY(nextBody.getX(), nextTile.getY());
				tail.setPrevious(nextBody.getPrevious());
				nextBody.getPrevious().setNext(tail);
				shield = 0;
				move_(0);
			}
			else
			{
				field.getGame().lose();
			}
		}
		else
		{
			move_(0);
		}
		
		if (shield > 0)
		{
			shield--;
		}
	}
	
	public void reverse()
	{
		int newHeadX = tail.getX(), newHeadY = tail.getY();
		int newTailX = head.getX(), newTailY = head.getY();
		field.setTile(newTailX, newTailY, tail);
		field.setTile(newHeadX, newHeadY, head);
		head.setXY(newHeadX, newHeadY);
		tail.setXY(newTailX, newTailY);
		
		SnakeTile tmp = head.getNext();
		if (tmp != tail)
		{
			head.setNext(tail.getPrevious());
			tail.setPrevious(tmp);
			
			for (SnakeTile i = tail.getPrevious(); i != head; i = i.getPrevious())
			{
				tmp = i.getNext();
				i.setNext(i.getPrevious() == head ? tail : i.getPrevious());
				i.setPrevious(tmp == tail ? head : tmp);
			}
		}
		
		headDirection = head.getDirection();
		moveDirection = headDirection;
		nextMoveDirection = moveDirection;
	}
	
	private void moveHead()
	{
		head.move(moveDirection);
	}
	
	private void moveTail(int deltaSize)
	{
		if (deltaSize >= 1)
		{
			deltaSize = 1;
			length++;
		}
		else if (deltaSize <= -1)
		{
			if (length < 3)
			{
				field.getGame().lose();
				deltaSize = 0;
			}
			else
			{
				deltaSize = -1;
				length--;
			}
		}
		
		SnakeTile oldTail = tail;
		SnakeTile newTail = tail;
		for (int i = 0; i < 1 - deltaSize; i++)
		{
			field.setTile(newTail.getX(), newTail.getY(), new Grass(newTail.getX(), newTail.getY(), field));
			newTail = newTail.getPrevious();
		}
		oldTail.setXY(newTail.getX(), newTail.getY());
		oldTail.setPrevious(newTail.getPrevious());
		oldTail.getPrevious().setNext(oldTail);
		field.setTile(newTail.getX(), newTail.getY(), oldTail);
	}
	
	private void move_(int deltaSize)
	{
		if (length < 4)
		{
			moveHead();
			moveTail(deltaSize);
		}
		else
		{
			moveTail(deltaSize);
			moveHead();
		}
		headDirection = head.getDirection();
		moveDirection = nextMoveDirection;
		nextMoveDirection = moveDirection;
	}
}
