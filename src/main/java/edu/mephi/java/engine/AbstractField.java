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
	
	public boolean isGameOver()
	{
		return getGame().isGameOver();
	}
	
	public List<List<Tile>> getTiles()
	{
		return tiles;
	}
	
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
	
	// Returns the tile with the exact coordinates without any checks
	public Tile getExactTile(int x, int y)
	{
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
	
	public Tile getNextTile(Tile tile, EDirection direction)
	{
		if (tile == null || tile.getField() != this || !tile.isAlive())
		{
			return null;
		}
		
		return getNextTile(tile.getX(), tile.getY(), direction);
	}
	
	public void swapTiles(int fromX, int fromY, int toX, int toY)
	{
		Tile from = getExactTile(fromX, fromY);
		Tile to = getExactTile(toX, toY);
		setTile(fromX, fromY, to);
		setTile(toX, toY, from);
		if (from != null)
		{
			from.setXY(toX, toY);
		}
		if (to != null)
		{
			to.setXY(fromX, fromY);
		}
	}
	
	public boolean swapTiles(Tile from, Tile to)
	{
		if (from != null && from.getField() == this && from.isAlive() &&
			  to != null &&   to.getField() == this &&   to.isAlive())
		{
			swapTiles(from.getX(), from.getY(), to.getX(), to.getY());
			return true;
		}
		return false;
	}
	
	public boolean replaceTile(int x, int y, Tile newTile)
	{
		if (newTile != null && newTile.getField() == this && !newTile.isAlive())
		{
			Tile oldTile = getExactTile(x, y);
			if (oldTile != null)
			{
				oldTile.setAlive(false);
			}
			setTile(x, y, newTile);
			newTile.setXY(x, y);
			newTile.setAlive(true);
			return true;
		}
		return false;
	}
	
	public boolean replaceTile(Tile oldTile, Tile newTile)
	{
		if (oldTile != null && oldTile.getField() == this && oldTile.isAlive())
		{
			return replaceTile(oldTile.getX(), oldTile.getY(), newTile);
		}
		return false;
	}
	
	public boolean moveTile(int fromX, int fromY, int toX, int toY, Tile newTile)
	{
		if (newTile != null && newTile.getField() == this && !newTile.isAlive())
		{
			Tile to = getExactTile(toX, toY);
			Tile from = getExactTile(fromX, fromY);
			setTile(toX, toY, from);
			setTile(fromX, fromY, newTile);
			
			newTile.setXY(fromX, fromY);
			newTile.setAlive(true);
			if (from != null)
			{
				from.setXY(toX, toY);
			}
			if (to != null)
			{
				to.setAlive(false);
			}
			return true;
		}
		return false;
	}
	
	public boolean moveTile(Tile from, Tile to, Tile newTile)
	{
		if (from != null && from.getField() == this && from.isAlive() &&
			  to != null &&   to.getField() == this &&   to.isAlive())
		{
			return moveTile(from.getX(), from.getY(), to.getX(), to.getY(), newTile);
		}
		return false;
	}
	
	public boolean moveTile(int fromX, int fromY, EDirection direction, Tile newTile)
	{
		return moveTile(getExactTile(fromX, fromY), direction, newTile);
	}
	
	public boolean moveTile(Tile from, EDirection direction, Tile newTile)
	{
		return moveTile(from, getNextTile(from, direction), newTile);
	}
	
	// Replaces a random tile of the tileClass to replaceTo
	public void replaceRandom(Class<? extends Tile> tileClass, Tile newTile)
	{
		int x, y;
		do
		{
			// Not the best approach… I hope I'll change this one day
			x = Game.RANDOM.nextInt(sizeX);
			y = Game.RANDOM.nextInt(sizeY);
		} while (!tileClass.isAssignableFrom(tiles.get(x).get(y).getClass()));
		replaceTile(x, y, newTile);
	}
	
	protected void setTile(int x, int y, Tile tile)
	{
		tiles.get(x).set(y, tile);
	}
}
