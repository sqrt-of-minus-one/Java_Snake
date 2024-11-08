package edu.mephi.java.engine;

import edu.mephi.java.engine.tiles.EDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO допишите все необходимые сущности для игры
public class Game
		extends JPanel
{
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	private Field field;
	private boolean gameOver = false;
	private Timer moveTimer;
	
	private static final int MOVE_TIME_MS = 500;
	
	private JLabel[][] labels;
	private JLabel scoreIconLabel;
	private JLabel[] scoreLabels;
	private JLabel shieldIndicator;
	
	public Game()
	{
		field = new Field(WIDTH, HEIGHT, this);
		
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
				switch (e.getKeyCode())
				{
					case KeyEvent.VK_W:
					case KeyEvent.VK_UP:
					{
						field.setDirection(EDirection.UP);
						break;
					}
					case KeyEvent.VK_S:
					case KeyEvent.VK_DOWN:
					{
						field.setDirection(EDirection.DOWN);
						break;
					}
					case KeyEvent.VK_A:
					case KeyEvent.VK_LEFT:
					{
						field.setDirection(EDirection.LEFT);
						break;
					}
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
					{
						field.setDirection(EDirection.RIGHT);
						break;
					}
				}
			}
		});
		
		labels = new JLabel[WIDTH][HEIGHT];
		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				labels[x][y] = new JLabel(field.getTile(x, y).getSprite());
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
		updateScore();
		
		shieldIndicator = new JLabel(ResourceManager.getSprite(ESprite.GRASS));
		add(shieldIndicator);
		updateShield();
		
		moveTimer = new Timer(MOVE_TIME_MS, e ->
		{
			field.moveSnake();
			
			updateSprites();
			repaint();
		});
		moveTimer.start();
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
		moveTimer.start();
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
}
