package edu.mephi.java.snake;

import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.tiles.*;

import javax.swing.*;

// This class is designed for easier control of the snake
// The snake itself is stores as linked list of `SnakeTile`
public class Snake
{
	private final SnakeHead head;
	private final SnakeTail tail;
	private final Field field;
	private int length; // The length of the snake including the head and the tail
	private int shield; // The number of movements before the shield stops working (0 if there's no shield)
	private EDirection headDirection; // The direction of the head sprite
	private EDirection moveDirection; // The direction where the snake is going to move next time
	private EDirection nextMoveDirection; // The direction for the movement after the next one
	private Timer blinkTimer;
	private Timer unblinkTimer;
	private static final int BLINK_TIME_MS = 7000; // How often the snake blinks
	private static final int UNBLINK_TIME_MS = 500; // How fast the snake opens its eyes after blinking
	
	public Snake(int x, int y, Field field, EDirection direction, int length)
	{
		// The snake cannot be shorter than 2 and longer than the size of the field
		if (length < 2)
		{
			length = 2;
		}
		else if (length > (direction == EDirection.LEFT || direction == EDirection.RIGHT ? field.getWidth() : field.getHeight()))
		{
			length = (direction == EDirection.LEFT || direction == EDirection.RIGHT ? field.getWidth() : field.getHeight());
		}
		
		this.field = field;
		headDirection = direction;
		moveDirection = direction;
		nextMoveDirection = direction;
		this.length = length;
		shield = 0;
		
		// Create the snake
		head = new SnakeHead(x, y, field, direction, length);
		
		// Find the tail
		SnakeTile tile = head.getNext();
		while (tile.getNext() != null)
		{
			tile = tile.getNext();
		}
		tail = (SnakeTail)tile;
		
		// Setup blinking
		blinkTimer = new Timer(BLINK_TIME_MS, _ ->
		{
			unblinkTimer.start();
			head.setBlink(true);
		});
		blinkTimer.start();
		
		unblinkTimer = new Timer(UNBLINK_TIME_MS, _ -> head.setBlink(false));
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
	
	// Returns the length including the head and the tail
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
			// Set the next move direction
			moveDirection = direction;
			nextMoveDirection = moveDirection;
		}
		else if (moveDirection != headDirection)
		{
			// Set the direction for the move after the next one
			nextMoveDirection = direction;
		}
	}
	
	public void setShield(int shield)
	{
		this.shield = shield;
	}
	
	public void move()
	{
		// The movement depends on what the tile in front of the snake is
		Tile nextTile = field.getNextTile(head, moveDirection);
		if (Eatable.class.isAssignableFrom(nextTile.getClass())) // If eatable, eat it
		{
			Eatable nextEatable = (Eatable)nextTile;
			move_(nextEatable.getDeltaSize());
			nextEatable.eaten();
		}
		else if (Obstacle.class.isAssignableFrom(nextTile.getClass())) // If obstacle, collide with it
		{
			Obstacle nextObstacle = (Obstacle)nextTile;
			nextObstacle.collide();
			if (nextObstacle.isLostOnCollide()) // Should the snake lose
			{
				if (shield > 0)
				{
					// The snake doesn't lose if it has a shield
					move_(0);
					shield = 0; // Reset the shield
				}
				else
				{
					// Lose if the snake doesn't have a shield
					field.getGame().lose();
				}
			}
		}
		else if (SnakeBody.class.isAssignableFrom(nextTile.getClass()))
			// If the tile in front of the snake is the tail, then nothing bad is going to happen
			// because the tail will also move
		{
			if (shield > 0)
			{
				// If the snake has a shield, it eats its tail, but doesn't lose
				SnakeBody nextBody = (SnakeBody)nextTile;
				for (SnakeTile i = nextBody.getNext(); i != null; i = i.getNext())
				{
					// Destroy snake tiles up to the tail
					field.setTile(i.getX(), i.getY(), new Grass(i.getX(), i.getY(), field));
					length--;
				}
				// Move tail in front of the head
				field.setTile(nextBody.getX(), nextBody.getY(), tail);
				tail.setXY(nextBody.getX(), nextTile.getY());
				tail.setPrevious(nextBody.getPrevious());
				nextBody.getPrevious().setNext(tail);
				shield = 0; // Reset the shield
				move_(0);
			}
			else
			{
				// Lose if the snake doesn't have a shield
				field.getGame().lose();
			}
		}
		else // If there's nothing in front of the snake, just move
		{
			move_(0);
		}
		
		if (shield > 0)
		{
			shield--;
		}
	}
	
	// Swap the head and the tail
	public void reverse()
	{
		// Swap the head and the tail tiles
		int newHeadX = tail.getX(), newHeadY = tail.getY();
		int newTailX = head.getX(), newTailY = head.getY();
		field.setTile(newTailX, newTailY, tail);
		field.setTile(newHeadX, newHeadY, head);
		head.setXY(newHeadX, newHeadY);
		tail.setXY(newTailX, newTailY);
		
		SnakeTile tmp = head.getNext();
		if (tmp != tail)
		{
			// Swap the `next` and the `previous` pointers of the head and the tail
			head.setNext(tail.getPrevious());
			tail.setPrevious(tmp);
			
			// Swap the pointers of the body segments
			for (SnakeTile i = tail.getPrevious(); i != head; i = i.getPrevious())
			{
				tmp = i.getNext();
				i.setNext(i.getPrevious() == head ? tail : i.getPrevious());
				i.setPrevious(tmp == tail ? head : tmp);
			}
		}
		
		// Set up the direction
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
		// The length can only be changed by 1
		if (deltaSize >= 1)
		{
			// Increase the length
			deltaSize = 1;
			length++;
		}
		else if (deltaSize <= -1)
		{
			// Decrease the length
			if (length < 3)
			{
				// Lose if the snake is too short
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
			// Remove the tile
			field.setTile(newTail.getX(), newTail.getY(), new Grass(newTail.getX(), newTail.getY(), field));
			newTail = newTail.getPrevious();
		}
		// Move the tail
		oldTail.setXY(newTail.getX(), newTail.getY());
		oldTail.setPrevious(newTail.getPrevious());
		oldTail.getPrevious().setNext(oldTail);
		field.setTile(newTail.getX(), newTail.getY(), oldTail);
	}
	
	private void move_(int deltaSize)
	{
		if (length < 4)
		{
			// If the snake is short, we must move the head first to make it longer and then move the tail
			moveHead();
			moveTail(deltaSize);
		}
		else
		{
			// If the snake is long, we must move the tail first, because the tail segment may be in front of the head
			moveTail(deltaSize);
			moveHead();
		}
		headDirection = head.getDirection();
		moveDirection = nextMoveDirection;
	}
}
