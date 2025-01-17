package edu.mephi.java.engine;

import edu.mephi.java.engine.command.AbstractCommand;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractField<
		Game extends AbstractGame<Game, Field, Tile, Command>,
		Field extends AbstractField<Game, Field, Tile, Command>,
		Tile extends AbstractTile<Game, Field, Tile, Command>,
		Command extends AbstractCommand<Game, Field, Tile, Command>>
{
	private final WeakReference<Game> game;
	private final int sizeX, sizeY; // The field size
	private final boolean loopedX, loopedY; // If the field is looped, we can move behind its edge and appear from the opposite one
	
	private final List<List<Tile>> tiles; // 1st index is X, 2nd index is Y
	
	public AbstractField(Game game, boolean loopedX, boolean loopedY)
	{
		this.game = new WeakReference<>(game);
		sizeX = game.getSizeX();
		sizeY = game.getSizeY();
		this.loopedX = loopedX;
		this.loopedY = loopedY;
		tiles = new ArrayList<>(sizeX);
		for (int x = 0; x < sizeX; x++)
		{
			List<Tile> column = new ArrayList<>(sizeY);
			for (int y = 0; y < sizeY; y++)
			{
				column.add(null);
			}
			tiles.add(column);
		}
	}
	
	public Game getGame()
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
	public Tile getTile(int x, int y)
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
		
		return tiles.get(x).get(y);
	}
	
	public Tile getNextTile(int x, int y, EDirection direction)
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
		Tile tmp = tiles.get(fromX).get(fromY);
		tiles.get(fromX).set(fromY, tiles.get(toX).get(toY));
		tiles.get(toX).set(toY, tmp);
	}
	
	// Replaces a random tile of the tileClass to replaceTo
	public void replaceRandom(Class<Tile> tileClass, Tile replaceTo)
	{
		int x, y;
		do
		{
			// Not the best approach… I hope I'll change this one day
			x = Game.RANDOM.nextInt(sizeX);
			y = Game.RANDOM.nextInt(sizeY);
		} while (!tileClass.isAssignableFrom(tiles.get(x).get(y).getClass()));
		tiles.get(x).set(y, replaceTo);
	}
}
