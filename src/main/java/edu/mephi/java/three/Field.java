package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractField;
import edu.mephi.java.three.command.Command;
import edu.mephi.java.three.tiles.Empty;
import edu.mephi.java.three.tiles.Tile;

public class Field
	extends AbstractField<ThreeGame, Field, Tile, Command>
{
	private int selectedX = 0;
	private int selectedY = 0;
	private boolean picked = false;
	
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
