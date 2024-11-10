package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.*;

import java.util.Random;

public class Field
{
	public static final double BONUS_PROB = .4; // The probability of an extra object generation
	
	private final int width;
	private final int height;
	
	private final Game game;
	private final Tile[][] tiles; // The tiles of the field
	private Snake snake;
	private final Random random; // The random number generator
	
	public Field(int width, int height, Game game)
	{
		this.width = width;
		this.height = height;
		this.game = game;
		tiles = new Tile[width][height];
		random = new Random();
		
		fillGrass();
		createSnake();
		generateFood();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public Snake getSnake()
	{
		return snake;
	}
	
	public Tile getTile(int x, int y)
	{
		return tiles[x][y];
	}
	
	// Copies the tile; you should remove or replace the old tile manually
	// Doesn't change the coordinates stored in the tile object
	public void moveTile(int fromX, int fromY, int toX, int toY)
	{
		tiles[toX][toY] = tiles[fromX][fromY];
	}
	
	// Doesn't set the coordinates stored in the tile object
	public void setTile(int x, int y, Tile tile)
	{
		tiles[x][y] = tile;
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
	
	// Returns the neighbour tile in the specified direction
	public Tile getNextTile(Tile tile, EDirection direction)
	{
		return switch (direction)
		{
			case UP		-> getTile(tile.getX(), tile.getY() == 0 ? height - 1 : tile.getY() - 1);
			case DOWN	-> getTile(tile.getX(), tile.getY() == height - 1 ? 0 : tile.getY() + 1);
			case LEFT	-> getTile(tile.getX() == 0 ? width - 1 : tile.getX() - 1, tile.getY());
			case RIGHT	-> getTile(tile.getX() == width - 1 ? 0 : tile.getX() + 1, tile.getY());
		};
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
	
	// Replaces a random grass tile with the specified tile
	private void replaceRandomGrass(Tile tileToReplace)
	{
		int x, y;
		do
		{
			x = random.nextInt(width);
			y = random.nextInt(height);
		} while (!Grass.class.isAssignableFrom(tiles[x][y].getClass()));
		tileToReplace.setXY(x, y);
		tiles[x][y] = tileToReplace;
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
