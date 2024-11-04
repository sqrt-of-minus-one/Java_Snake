package edu.mephi.java.engine;

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
		implements ActionListener
{
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	private Field field;
	private boolean gameOver = false;
	private Timer timer;
	
	private JLabel[][] labels;
	
	public Game()
	{
		field = new Field(WIDTH, HEIGHT);
		
		setLayout(new GridLayout(HEIGHT, WIDTH, 0, 0));
		setPreferredSize(new Dimension(WIDTH * ResourceManager.FIELD_TILE_SIZE, HEIGHT * ResourceManager.FIELD_TILE_SIZE));
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				System.out.println("You pressed " + e.getKeyChar() + " key!");
			}
		});
		
		labels = new JLabel[WIDTH][HEIGHT];
		for (int y = 0; y < WIDTH; y++)
		{
			for (int x = 0; x < HEIGHT; x++)
			{
				labels[x][y] = new JLabel(field.getTile(x, y).getSprite());
				add(labels[x][y]);
			}
		}
		
		timer = new Timer(2000, this);
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
	}
	
	public void restart()
	{
		timer.start();
		repaint();
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
}
