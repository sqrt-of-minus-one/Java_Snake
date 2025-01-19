package edu.mephi.java.snake;

import edu.mephi.java.engine.AbstractGame;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.EDirection;
import edu.mephi.java.snake.command.Command;
import edu.mephi.java.snake.command.EffectCommand;
import edu.mephi.java.snake.command.SpawnCommand;
import edu.mephi.java.snake.tiles.Tile;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class SnakeGame
		extends AbstractGame<SnakeGame, Field, Tile, Command>
{
	private final Timer moveTimer;
	private static final int MOVE_TIME_MS = 500; // How often the snake moves
	
	private enum EStatusLabels
	{
		// Indices
		SCORE(0),
		SHIELD(1);
		
		public static final int[] STATUS_LABELS_COUNTS = { 4, 4 };
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
	
	public SnakeGame(int sizeX, int sizeY)
	{
		super(sizeX, sizeY, ResourceManager.get(), EStatusLabels.STATUS_LABELS_COUNTS);
		getStatusLabels(EStatusLabels.SCORE.getIndex())[0].setIcon(ResourceManager.get().getSprite(ESprite.APPLE));
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
					1, EStatusLabels.STATUS_LABELS_COUNTS[EStatusLabels.SCORE.getIndex()],
					field.getSnake().getLength() - 2);
			if (field.getSnake().getShield() > 0)
			{
				getStatusLabels(EStatusLabels.SHIELD.getIndex())[0].setIcon(ResourceManager.get().getSprite(ESprite.SHIELD));
				drawNumber(
						getStatusLabels(EStatusLabels.SHIELD.getIndex()),
						1, EStatusLabels.STATUS_LABELS_COUNTS[EStatusLabels.SHIELD.getIndex()],
						field.getSnake().getShield());
			}
			else
			{
				for (JLabel label : getStatusLabels(EStatusLabels.SHIELD.getIndex()))
				{
					label.setIcon(ResourceManager.get().getSprite(ECommonSprite.NOTHING));
				}
			}
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
			case KeyEvent.VK_SPACE ->
			{
				if (field != null)
				{
					getField().moveSnake();
					updateSprites();
					moveTimer.restart();
				}
			}
			case KeyEvent.VK_R -> restart();
		}
	}
	
	@Override
	protected Command createCommand(KeyEvent event)
	{
		return switch (event.getKeyCode())
		{
			case KeyEvent.VK_S -> new SpawnCommand(this);
			case KeyEvent.VK_E -> new EffectCommand(this);
			default -> null;
		};
	}
}
