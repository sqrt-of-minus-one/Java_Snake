package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractGame;
import edu.mephi.java.engine.EDirection;
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
		drawNumber(getStatusLabels(EStatusLabels.SCORE.getIndex()),
				   1, getStatusLabels(EStatusLabels.SCORE.getIndex()).length,
				   getField() != null ? getField().getScore() : 0);
	}
	
	@Override
	protected void controls(KeyEvent event)
	{
		if (!isGameOver() && !isPaused())
		{
			switch (event.getKeyCode())
			{
				case KeyEvent.VK_UP, KeyEvent.VK_W -> getField().move(EDirection.UP);
				case KeyEvent.VK_DOWN, KeyEvent.VK_S -> getField().move(EDirection.DOWN);
				case KeyEvent.VK_LEFT, KeyEvent.VK_A -> getField().move(EDirection.LEFT);
				case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> getField().move(EDirection.RIGHT);
				case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> getField().pick();
			}
		}
	}
	
	@Override
	protected Command createCommand(KeyEvent event)
	{
		return null;
	}
}
