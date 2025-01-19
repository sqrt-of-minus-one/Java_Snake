package edu.mephi.java.snake;

import edu.mephi.java.engine.AbstractField;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.command.Command;
import edu.mephi.java.snake.tiles.*;

import java.util.List;

// The field of the Snake game
public class Field
	extends AbstractField<SnakeGame, Field, Tile, Command>
{
	public static final double BONUS_PROB = .4; // The probability of an extra object generation
	
	private Snake snake;
	
	public Field(SnakeGame game)
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
		if (isGameOver() || getGame().isPaused())
		{
			return;
		}
		
		snake.move();
		for (List<Tile> tiles : getTiles())
		{
			for (Tile tile : tiles)
			{
				tile.onSnakeMove();
			}
		}
	}
	
	// Creates an apple in a random point; may also create an extra object
	public void generateFood()
	{
		replaceRandom(Grass.class, new Apple(0, 0, this));
		if (SnakeGame.RANDOM.nextDouble() <= BONUS_PROB)
		{
			generateBonus();
		}
	}
	
	// Creates a wall in a random point
	public void generateWall()
	{
		replaceRandom(Grass.class, new Wall(0, 0, this));
	}
	
	// Creates a random extra object in a random point
	private void generateBonus()
	{
		replaceRandom(Grass.class,
				switch (SnakeGame.RANDOM.nextInt(4))
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
		for (int i = 0; i < getTiles().size(); i++)
		{
			for (int j = 0; j < getTiles().get(i).size(); j++)
			{
				replaceTile(i, j, new Grass(this));
			}
		}
	}
	
	// Creates a snake and places it on the field
	private void createSnake()
	{
		if (snake == null)
		{
			snake = new Snake(getSizeX() / 2, getSizeY() / 2, this, EDirection.RIGHT, 2);
			for (SnakeTile tile = snake.getHead(); tile != null; tile = tile.getNext())
			{
				replaceTile(tile.getX(), tile.getY(), tile);
			}
		}
	}
}
