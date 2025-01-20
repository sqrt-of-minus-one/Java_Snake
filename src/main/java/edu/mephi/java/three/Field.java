package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractField;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.three.command.Command;
import edu.mephi.java.three.tiles.Empty;
import edu.mephi.java.three.tiles.Tile;

public class Field
	extends AbstractField<ThreeGame, Field, Tile, Command>
{
	private int selectedX = 0;
	private int selectedY = 0;
	private boolean picked = false;
	private int score = 0;
	
	public Field(ThreeGame game)
	{
		super(game, true, false);
		fillEmpty();
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
	
	public int getScore()
	{
		return score;
	}
	
	public void move(EDirection direction)
	{
		if (!getGame().isGameOver() && !getGame().isPaused())
		{
			switch (direction)
			{
				case EDirection.UP ->
				{
					if (--selectedY < 0)
					{
						selectedY = getSizeY() - 1;
					}
				}
				case EDirection.DOWN ->
				{
					if (++selectedY >= getSizeY())
					{
						selectedY = 0;
					}
				}
				case EDirection.LEFT ->
				{
					if (--selectedX < 0)
					{
						selectedX = getSizeX() - 1;
					}
				}
				case EDirection.RIGHT ->
				{
					if (++selectedX >= getSizeX())
					{
						selectedX = 0;
					}
				}
			}
			getGame().updateField();
		}
	}
	
	public void pick()
	{
		if (!getGame().isGameOver() && !getGame().isPaused())
		{
			picked = !picked;
			getGame().updateFieldSprite(selectedX, selectedY);
			getGame().repaint();
		}
	}
	
	public void fillEmpty()
	{
		for (int x = 0; x < getSizeX(); x++)
		{
			for (int y = 0; y < getSizeY(); y++)
			{
				replaceTile(x, y, new Empty(this));
			}
		}
	}
}
