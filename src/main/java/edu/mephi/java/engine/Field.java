package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.*;

public class Field
{
	private final int width;
	private final int height;
	
	private Game game;
	private Tile[][] tiles;
	private Snake snake;
	
	public Field(int width, int height, Game game)
	{
		this.width = width;
		this.height = height;
		this.game = game;
		tiles = new Tile[width][height];
		
		fillGrass();
		createSnake();
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
	
	public void moveSnake()
	{
		snake.move();
	}
	
	public void lose()
	{
		getGame().lose();
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
			snake = new Snake(width / 2, height / 2, this, EDirection.DOWN, 4);
			for (SnakeTile tile = snake.getHead(); tile != null; tile = tile.getNext())
			{
				tiles[tile.getX()][tile.getY()] = tile;
			}
		}
	}
}
