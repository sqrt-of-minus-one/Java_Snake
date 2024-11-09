package edu.mephi.java.engine;

import edu.mephi.java.engine.command.Command;
import edu.mephi.java.engine.command.EffectCommand;
import edu.mephi.java.engine.command.SpawnCommand;
import edu.mephi.java.engine.tiles.EDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// TODO допишите все необходимые сущности для игры
public class Game
		extends JPanel
{
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	private Field field;
	private boolean gameOver = false;
	private Timer moveTimer;
	private Command command;
	private boolean commandStarted;
	
	private static final int MOVE_TIME_MS = 500;
	
	private JLabel[][] labels;
	private JLabel scoreIconLabel;
	private JLabel[] scoreLabels;
	private JLabel shieldIndicator;
	private JLabel[] commandLabels;
	
	public Game()
	{
		command = null;
		commandStarted = false;
		setLayout(new GridLayout(HEIGHT + 1, WIDTH, 0, 0));
		setPreferredSize(new Dimension(WIDTH * ResourceManager.FIELD_TILE_SIZE, (HEIGHT + 1) * ResourceManager.FIELD_TILE_SIZE));
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (!commandStarted)
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_W, KeyEvent.VK_UP -> field.setDirection(EDirection.UP);
						case KeyEvent.VK_S, KeyEvent.VK_DOWN -> field.setDirection(EDirection.DOWN);
						case KeyEvent.VK_A, KeyEvent.VK_LEFT -> field.setDirection(EDirection.LEFT);
						case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> field.setDirection(EDirection.RIGHT);
						case KeyEvent.VK_SPACE ->
						{
							field.moveSnake();
							updateSprites();
							repaint();
							moveTimer.restart();
						}
						case KeyEvent.VK_R -> restart();
						case KeyEvent.VK_SLASH -> startCommand();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					resetCommand();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if (command == null)
					{
						resetCommand();
					}
					else if (command.waitingFor() == Command.EWaitingFor.APPLIED)
					{
						resetCommand();
					}
					else
					{
						command.applyElement();
						if (command.waitingFor() == Command.EWaitingFor.COMPLETE)
						{
							command.apply();
							updateSprites();
						}
						command.draw(commandLabels);
					}
				}
				else if (command == null)
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_S -> command = new SpawnCommand(Game.this);
						case KeyEvent.VK_E -> command = new EffectCommand(Game.this);
					}
					if (command != null)
					{
						command.draw(commandLabels);
						repaint();
					}
				}
				else
				{
					switch (command.waitingFor())
					{
						case APPLIED -> resetCommand();
						case COMPLETE ->
						{
							switch (e.getKeyCode())
							{
								case KeyEvent.VK_ENTER -> command.apply();
							}
						}
						case SPAWNABLE ->
						{
							switch (e.getKeyCode())
							{
								case KeyEvent.VK_G -> command.setSpawnable(Command.ESpawnable.GRASS);
								case KeyEvent.VK_A -> command.setSpawnable(Command.ESpawnable.APPLE);
								case KeyEvent.VK_R -> command.setSpawnable(Command.ESpawnable.ROTTEN_APPLE);
								case KeyEvent.VK_Z -> command.setSpawnable(Command.ESpawnable.REVERSE_PILL);
								case KeyEvent.VK_S -> command.setSpawnable(Command.ESpawnable.SHIELD);
								case KeyEvent.VK_H -> command.setSpawnable(Command.ESpawnable.HAMMER);
								case KeyEvent.VK_ENTER -> command.applyElement();
							}
						}
						case EFFECT ->
						{
							switch (e.getKeyCode())
							{
								case KeyEvent.VK_S -> command.setEffect(Command.EEffect.SHIELD);
							}
						}
						case NUMBER ->
						{
							switch (e.getKeyCode())
							{
								case KeyEvent.VK_0 -> command.addDigit(0);
								case KeyEvent.VK_1 -> command.addDigit(1);
								case KeyEvent.VK_2 -> command.addDigit(2);
								case KeyEvent.VK_3 -> command.addDigit(3);
								case KeyEvent.VK_4 -> command.addDigit(4);
								case KeyEvent.VK_5 -> command.addDigit(5);
								case KeyEvent.VK_6 -> command.addDigit(6);
								case KeyEvent.VK_7 -> command.addDigit(7);
								case KeyEvent.VK_8 -> command.addDigit(8);
								case KeyEvent.VK_9 -> command.addDigit(9);
								case KeyEvent.VK_ENTER -> command.applyElement();
							}
							if (command.waitingFor() == Command.EWaitingFor.COMPLETE)
							{
								command.apply();
							}
						}
					}
					if (command != null)
					{
						command.draw(commandLabels);
						repaint();
					}
				}
			}
		});
		
		labels = new JLabel[WIDTH][HEIGHT];
		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				labels[x][y] = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
				add(labels[x][y]);
			}
		}
		
		scoreIconLabel = new JLabel(ResourceManager.getSprite(ESprite.APPLE));
		add(scoreIconLabel);
		scoreLabels = new JLabel[3];
		for (int i = 0; i < scoreLabels.length; i++)
		{
			scoreLabels[i] = new JLabel(ResourceManager.getSprite(ESprite.NUM_ZERO));
			add(scoreLabels[i]);
		}
		
		shieldIndicator = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
		add(shieldIndicator);
		
		commandLabels = new JLabel[9];
		for (int i = 0; i < commandLabels.length; i++)
		{
			commandLabels[i] = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
			add(commandLabels[i]);
		}
		
		moveTimer = new Timer(MOVE_TIME_MS, e ->
		{
			field.moveSnake();
			
			updateSprites();
			repaint();
		});
		
		restart();
	}
	
	public Field getField()
	{
		return field;
	}
	
	public void updateSprite(int x, int y)
	{
		labels[x][y].setIcon(field.getTile(x, y).getSprite());
	}
	
	public void updateSprites()
	{
		for (int x = 0; x < WIDTH; x++)
		{
			for (int y = 0; y < HEIGHT; y++)
			{
				updateSprite(x, y);
			}
		}
		updateScore();
		updateShield();
	}
	
	public void restart()
	{
		gameOver = false;
		field = new Field(WIDTH, HEIGHT, this);
		moveTimer.restart();
		
		updateSprites();
		updateScore();
		updateShield();
		repaint();
	}
	
	public void lose()
	{
		gameOver = true;
		moveTimer.stop();
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	private void updateScore()
	{
		int score = field.getSnake().getLength() - 2;
		for (int i = scoreLabels.length - 1; i >= 0; i--)
		{
			scoreLabels[i].setIcon(ResourceManager.getSprite(ESprite.getNum(score % 10)));
			score /= 10;
		}
	}
	
	private void updateShield()
	{
		shieldIndicator.setIcon(ResourceManager.getSprite(field.getSnake().getShield() > 1 ? ESprite.SHIELD : ESprite.GRASS));
	}
	
	private void startCommand()
	{
		if (!gameOver)
		{
			commandStarted = true;
			Command.drawEmpty(commandLabels);
			repaint();
			moveTimer.stop();
		}
	}
	
	private void resetCommand()
	{
		command = null;
		commandStarted = false;
		Command.drawNone(commandLabels);
		repaint();
		moveTimer.restart();
	}
}
