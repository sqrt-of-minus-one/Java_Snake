package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.*;

public class Field
{
	private final int width;
	private final int height;
	
	private Tile[][] tiles;
	private Snake snake;
	
	public Field(int width, int height)
	{
		this.width = width;
		this.height = height;
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
	
	public Tile getTile(int x, int y)
	{
		return tiles[x][y];
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
