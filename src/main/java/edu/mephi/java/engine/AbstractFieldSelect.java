package edu.mephi.java.engine;

import edu.mephi.java.engine.command.AbstractCommand;

public abstract class AbstractFieldSelect<
		Game extends AbstractGame<Game, Field, Tile, Command>,
		Field extends AbstractFieldSelect<Game, Field, Tile, Command>,
		Tile extends AbstractTileSelect<Game, Field, Tile, Command>,
		Command extends AbstractCommand<Game, Field, Tile, Command>>
	extends AbstractField<Game, Field, Tile, Command>
{
	private int selectedX = 0;
	private int selectedY = 0;
	private boolean picked = false;
	
	public AbstractFieldSelect(Game game, boolean loopedX, boolean loopedY)
	{
		super(game, loopedX, loopedY);
	}
	
	public int getSelectedX()
	{
		return selectedX;
	}
	
	public int getSelectedY()
	{
		return selectedY;
	}
	
	public Tile getSelectedTile()
	{
		return getExactTile(selectedX, selectedY);
	}
	
	public boolean isPicked()
	{
		return picked;
	}
	
	public void move(EDirection direction)
	{
		int oldX = selectedX, oldY = selectedY;
		switch (direction)
		{
			case UP ->
			{
				if (--selectedY < 0)
				{
					selectedY = isLoopedY() ? getSizeY() - 1 : 0;
				}
			}
			case DOWN ->
			{
				if (++selectedY >= getSizeY())
				{
					selectedY = isLoopedY() ? 0 : getSizeY() - 1;
				}
			}
			case LEFT ->
			{
				if (--selectedX < 0)
				{
					selectedX = isLoopedX() ? getSizeX() - 1 : 0;
				}
			}
			case RIGHT ->
			{
				if (++selectedX >= getSizeX())
				{
					selectedX = isLoopedX() ? 0 : getSizeX() - 1;
				}
			}
		}
		if (getGame() != null)
		{
			getGame().updateFieldSprite(oldX, oldY);
			getGame().updateFieldSprite(selectedX, selectedY);
			getGame().repaint();
		}
	}
	
	protected void setSelectedXY(int x, int y)
	{
		int oldX = selectedX, oldY = selectedY;
		if (x >= 0 && x < getSizeX() && y >= 0 && y < getSizeY())
		{
			selectedX = x;
			selectedY = y;
			if (getGame() != null)
			{
				getGame().updateFieldSprite(oldX, oldY);
				getGame().updateFieldSprite(selectedX, selectedY);
				getGame().repaint();
			}
		}
	}
	
	protected void setPicked(boolean picked)
	{
		this.picked = picked;
		if (getGame() != null)
		{
			getGame().updateFieldSprite(selectedX, selectedY);
			getGame().repaint();
		}
	}
	
	protected void switchPicked()
	{
		picked = !picked;
		if (getGame() != null)
		{
			getGame().updateFieldSprite(selectedX, selectedY);
			getGame().repaint();
		}
	}
}
