package edu.mephi.java.engine;

import java.lang.ref.WeakReference;

public abstract class AbstractField
{
	private final WeakReference<AbstractGame> game;
	private final int sizeX, sizeY; // The field size
	private final boolean loopedX, loopedY; // If the field is looped, we can move behind its edge and appear from the opposite one
	
	private final AbstractTile[][] tiles; // 1st index is X, 2nd index is Y
	
	public AbstractField(AbstractGame game, boolean loopedX, boolean loopedY)
	{
		this.game = new WeakReference<>(game);
		sizeX = game.getSizeX();
		sizeY = game.getSizeY();
		this.loopedX = loopedX;
		this.loopedY = loopedY;
		tiles = new AbstractTile[sizeX][sizeY];
	}
	
	public AbstractGame getGame()
	{
		return game.get();
	}
	
	public int getSizeX()
	{
		return sizeX;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}
	
	public boolean isLoopedX()
	{
		return loopedX;
	}
	
	public boolean isLoopedY()
	{
		return loopedY;
	}
	
	// Is called by the game when lost
	public void onLose()
	{}
	
	// If the field is looped, the coordinates can be out of the field range
	public AbstractTile getTile(int x, int y)
	{
		if (x < 0)
		{
			if (!loopedX) return null;
			x %= sizeX;
			x += sizeX;
		}
		else if (x >= sizeX)
		{
			if (!loopedX) return null;
			x %= sizeX;
		}
		
		if (y < 0)
		{
			if (!loopedY) return null;
			y %= sizeY;
			y += sizeY;
		}
		else if (y >= sizeY)
		{
			if (!loopedY) return null;
			y %= sizeY;
		}
		
		return tiles[x][y];
	}
	
	public AbstractTile getNextTile(int x, int y, EDirection direction)
	{
		return switch (direction)
		{
			case UP		-> getTile(x, y - 1);
			case DOWN	-> getTile(x, y + 1);
			case LEFT	-> getTile(x - 1, y);
			case RIGHT	-> getTile(x + 1, y);
		};
	}
	
	public void swapTiles(int fromX, int fromY, int toX, int toY)
	{
		AbstractTile tmp = tiles[fromX][fromY];
		tiles[fromX][fromY] = tiles[toX][toY];
		tiles[toX][toY] = tmp;
	}
	
	// Replaces a random tile of the tileClass to replaceTo
	public void replaceRandom(Class<AbstractTile> tileClass, AbstractTile replaceTo)
	{
		int x, y;
		do
		{
			// Not the best approach… I hope I'll change this one day
			x = AbstractGame.RANDOM.nextInt(sizeX);
			y = AbstractGame.RANDOM.nextInt(sizeY);
		} while (!tileClass.isAssignableFrom(tiles[x][y].getClass()));
		tiles[x][y] = replaceTo;
	}
}
