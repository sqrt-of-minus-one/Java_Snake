package edu.mephi.java.snake;

import edu.mephi.java.engine.AbstractGame;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.command.AbstractCommand;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.command.Command;
import edu.mephi.java.snake.tiles.Tile;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Game
		extends AbstractGame<Game, Field, Tile, Command>
{
	private final Timer moveTimer;
	private static final int MOVE_TIME_MS = 500; // How often the snake moves
	
	private enum EStatusLabels
	{
		SCORE_ICON(0),
		SCORE(1),
		SHIELD(3);
		
		public static final int[] STATUS_LABELS_COUNTS = { 1, 3, 1, 1 };
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
	
	public Game(int sizeX, int sizeY)
	{
		super(sizeX, sizeY, ResourceManager.get(), EStatusLabels.STATUS_LABELS_COUNTS);
		getStatusLabels(EStatusLabels.SCORE_ICON.getIndex())[0].setIcon(ResourceManager.get().getSprite(ESprite.APPLE));
		moveTimer = new Timer(MOVE_TIME_MS, _ ->
		{
			Field field = getField();
			if (field != null)
			{
				field.moveSnake();
				updateSprites();
			}
		});
		restart();
	}
	
	public void restart()
	{
		restart(new Field(this));
	}
	
	@Override
	public void lose()
	{
		super.lose();
		moveTimer.stop();
	}
	
	@Override
	public void pause()
	{
		super.pause();
		moveTimer.stop();
	}
	
	@Override
	public void unpause()
	{
		super.unpause();
		moveTimer.restart();
	}
	
	@Override
	public void updateStatus()
	{
		Field field = getField();
		if (field != null)
		{
			drawNumber(
					getStatusLabels(EStatusLabels.SCORE.getIndex()),
					0,
					EStatusLabels.STATUS_LABELS_COUNTS[EStatusLabels.SCORE.getIndex()],
					field.getSnake().getLength() - 2);
			getStatusLabels(EStatusLabels.SHIELD.getIndex())[0].setIcon(ResourceManager.get().getSprite(
					field.getSnake().getShield() > 1 ? ESprite.SHIELD.toString() : ECommonSprite.NOTHING.toString()));
		}
	}
	
	@Override
	protected void controls(KeyEvent event)
	{
		Field field = getField();
		switch (event.getKeyCode())
		{
			case KeyEvent.VK_W, KeyEvent.VK_UP ->
			{
				if (field != null)
				{
					getField().getSnake().setDirection(EDirection.UP);
				}
			}
			case KeyEvent.VK_S, KeyEvent.VK_DOWN ->
			{
				if (field != null)
				{
					getField().getSnake().setDirection(EDirection.DOWN);
				}
			}
			case KeyEvent.VK_A, KeyEvent.VK_LEFT ->
			{
				if (field != null)
				{
					getField().getSnake().setDirection(EDirection.LEFT);
				}
			}
			case KeyEvent.VK_D, KeyEvent.VK_RIGHT ->
			{
				if (field != null)
				{
					getField().getSnake().setDirection(EDirection.RIGHT);
				}
			}
			case KeyEvent.VK_R -> restart();
		}
	}
	
	@Override
	protected AbstractCommand createCommand(KeyEvent event)
	{
		return switch (event.getKeyCode())
		{
			default -> null;
		};
	}
}
