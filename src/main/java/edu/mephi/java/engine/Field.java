package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.*;

import java.util.Random;

public class Field
{
	public static final double BONUS_PROB = .4;
	
	private final int width;
	private final int height;
	
	private Game game;
	private Tile[][] tiles;
	private Snake snake;
	private Random random;
	
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
	
	public void moveTile(int fromX, int fromY, int toX, int toY)
	{
		tiles[toX][toY] = tiles[fromX][fromY];
	}
	
	public void setTile(int x, int y, Tile tile)
	{
		tiles[x][y] = tile;
	}
	
	public void moveSnake()
	{
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
	
	public void setDirection(EDirection direction)
	{
		snake.setDirection(direction);
	}
	
	public void generateFood()
	{
		replaceRandomGrass(new Apple(0, 0, this));
		if (random.nextDouble() <= BONUS_PROB)
		{
			generateBonus();
		}
	}
	
	public void generateWall()
	{
		replaceRandomGrass(new Wall(0, 0, this));
	}
	
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
