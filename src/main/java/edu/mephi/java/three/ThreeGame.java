package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractGame;
import edu.mephi.java.three.command.Command;
import edu.mephi.java.three.tiles.Tile;

import java.awt.event.KeyEvent;

public class ThreeGame
	extends AbstractGame<ThreeGame, Field, Tile, Command>
{
	private enum EStatusLabels
	{
		// Indices
		SCORE(0);
		
		public static final int[] STATUS_LABELS_COUNTS = { 4 };
		private final int index;
		
		EStatusLabels(int index)
		{
			this.index = index;
		}
	
		public int getIndex()
		{
			return index;
		}
	}
	
	public ThreeGame(int sizeX, int sizeY)
	{
		super(sizeX, sizeY, ResourceManager.get(), EStatusLabels.STATUS_LABELS_COUNTS);
		getStatusLabels(EStatusLabels.SCORE.getIndex())[0].setIcon(ResourceManager.get().getSprite(ESprite.SPARK_PICKED));
		restart();
	}
	
	public void restart()
	{
		restart(new Field(this));
	}
	
	@Override
	public void updateStatus()
	{
	
	}
	
	@Override
	protected void controls(KeyEvent event)
	{
	
	}
	
	@Override
	protected Command createCommand(KeyEvent event)
	{
		return null;
	}
}
