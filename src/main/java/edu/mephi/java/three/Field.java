package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractFieldSelect;
import edu.mephi.java.three.command.Command;
import edu.mephi.java.three.tiles.Empty;
import edu.mephi.java.three.tiles.Tile;

public class Field
	extends AbstractFieldSelect<ThreeGame, Field, Tile, Command>
{
	private int score = 0;
	
	public Field(ThreeGame game)
	{
		super(game, true, false);
		fillEmpty();
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void pick()
	{
		switchPicked();
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
