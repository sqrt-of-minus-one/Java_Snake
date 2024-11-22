package edu.mephi.java.snake;

import edu.mephi.java.engine.AbstractField;
import edu.mephi.java.engine.AbstractGame;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.tiles.*;

import java.util.Random;

// The field of the Snake game
public class Field
	extends AbstractField
{
	public static final double BONUS_PROB = .4; // The probability of an extra object generation
	
	private Snake snake;
	
	public Field(AbstractGame game)
	{
		super(game, true, true);
		
		fillGrass();
		createSnake();
		generateFood();
	}
	
	public Snake getSnake()
	{
		return snake;
	}
	
	public void moveSnake()
	{
		// The snake can't move when the game is over
		if (game.isGameOver())
		{
			return;
		}
		
		snake.move();
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				tiles[x][y].onMove();
			}
		}
	}
	
	// Creates an apple in a random point; may also create an extra object
	public void generateFood()
	{
		replaceRandomGrass(new Apple(0, 0, this));
		if (random.nextDouble() <= BONUS_PROB)
		{
			generateBonus();
		}
	}
	
	// Creates a wall in a random point
	public void generateWall()
	{
		replaceRandomGrass(new Wall(0, 0, this));
	}
	
	// Creates a random extra object in a random point
	private void generateBonus()
	{
		replaceRandomGrass(
				switch (random.nextInt(4))
				{
					case 0 -> new RottenApple(0, 0, this);
					case 1 -> new ReversePill(0, 0, this);
					case 2 -> new Hammer(0, 0, this);
					case 3 -> new Shield(0, 0, this);
					default -> new Grass(0, 0, this);
				});
	}
	
	// Fills the whole field with the grass
	private void fillGrass()
	{
		for (int i = 0; i < tiles.length; i++)
		{
			for (int j = 0; j < tiles[i].length; j++)
			{
				tiles[i][j] = new Grass(i, j, this);
			}
		}
	}
	
	// Creates a snake and places it on the field
	private void createSnake()
	{
		if (snake == null)
		{
			snake = new Snake(width / 2, height / 2, this, EDirection.RIGHT, 2);
			for (SnakeTile tile = snake.getHead(); tile != null; tile = tile.getNext())
			{
				tiles[tile.getX()][tile.getY()] = tile;
			}
		}
	}
}
