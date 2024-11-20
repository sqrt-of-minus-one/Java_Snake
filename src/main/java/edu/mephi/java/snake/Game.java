package edu.mephi.java.snake;

import edu.mephi.java.snake.command.Command;
import edu.mephi.java.snake.command.EffectCommand;
import edu.mephi.java.snake.command.SpawnCommand;
import edu.mephi.java.snake.tiles.EDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game
		extends JPanel
{
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	private Field field;
	private boolean gameOver = false;
	private Timer moveTimer;
	private Command command; // The command that is currently being set up
	private boolean commandStarted; // Whether a command is being set up at the moment
	
	private static final int MOVE_TIME_MS = 500; // How often the snake moves
	
	private final JLabel[][] labels; // These labels display the field
	private final JLabel scoreIconLabel; // This label displays the icon of the player score
	private final JLabel[] scoreLabels; // These labels display the player score
	private final JLabel shieldIndicator; // This label displays the shield icon if the shield is active
	private final JLabel[] commandLabels; // These labels display the command that is currently being set up
	
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
				if (!commandStarted) // Playing
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_W, KeyEvent.VK_UP -> field.getSnake().setDirection(EDirection.UP);
						case KeyEvent.VK_S, KeyEvent.VK_DOWN -> field.getSnake().setDirection(EDirection.DOWN);
						case KeyEvent.VK_A, KeyEvent.VK_LEFT -> field.getSnake().setDirection(EDirection.LEFT);
						case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> field.getSnake().setDirection(EDirection.RIGHT);
						case KeyEvent.VK_SPACE -> // Move faster
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
				// Entering a command
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) // Back to game
				{
					resetCommand();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) // Attempt to apply a command
				{
					if (command == null || command.waitingFor() == Command.EWaitingFor.APPLIED)
					{
						// Cancel the entering of a command
						resetCommand();
					}
					else
					{
						command.applyParameter(); // Confirm the parameter
						if (command.waitingFor() == Command.EWaitingFor.COMPLETE) // Apply the command if all the parameters are set
						{
							command.apply();
							updateSprites();
						}
						command.draw(commandLabels); // Draw the command result
					}
				}
				else if (command == null)
				{
					// Specify the command
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_S -> command = new SpawnCommand(Game.this);
						case KeyEvent.VK_E -> command = new EffectCommand(Game.this);
					}
					if (command != null) // Draw the command if it was specified
					{
						command.draw(commandLabels);
						repaint();
					}
				}
				else
				{
					switch (command.waitingFor()) // What parameters is the command expecting
					{
						case APPLIED -> resetCommand(); // The command has been applied => back to game
						case COMPLETE -> // The command is complete => apply it
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
								case KeyEvent.VK_W -> command.setSpawnable(Command.ESpawnable.WALL);
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
		
		// Set up the field labels
		labels = new JLabel[WIDTH][HEIGHT];
		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				labels[x][y] = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
				add(labels[x][y]);
			}
		}
		
		// Set up the score labels
		scoreIconLabel = new JLabel(ResourceManager.getSprite(ESprite.APPLE));
		add(scoreIconLabel);
		scoreLabels = new JLabel[3];
		for (int i = 0; i < scoreLabels.length; i++)
		{
			scoreLabels[i] = new JLabel(ResourceManager.getSprite(ESprite.NUM_ZERO));
			add(scoreLabels[i]);
		}
		
		// Set up the shield label
		shieldIndicator = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
		add(shieldIndicator);
		
		// Set up the command labels
		commandLabels = new JLabel[9];
		for (int i = 0; i < commandLabels.length; i++)
		{
			commandLabels[i] = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
			add(commandLabels[i]);
		}
		
		// Set up the move timer
		moveTimer = new Timer(MOVE_TIME_MS, _ ->
		{
			field.moveSnake();
			
			updateSprites();
			repaint();
		});
		
		restart(); // Launch the game
	}
	
	public Field getField()
	{
		return field;
	}
	
	// Updates the sprite on the field
	public void updateSprite(int x, int y)
	{
		labels[x][y].setIcon(field.getTile(x, y).getSprite());
	}
	
	// Updates all sprites except the command sprites
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
	
	// Start or restart the game
	public void restart()
	{
		gameOver = false;
		field = new Field(WIDTH, HEIGHT, this); // Create a new field
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
		int score = field.getSnake().getLength() - 2; // The score is the snake length excluding its head and its tail
		// Draw the number
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
		if (!gameOver) // Commands can only be applied when the game is not over
		{
			commandStarted = true; // Start the command
			Command.drawEmpty(commandLabels); // Draw the empty command
			repaint();
			moveTimer.stop(); // Pause the game
		}
	}
	
	// Stop entering the command and resume the game
	private void resetCommand()
	{
		command = null;
		commandStarted = false;
		Command.drawNone(commandLabels);
		repaint();
		moveTimer.restart();
	}
}
