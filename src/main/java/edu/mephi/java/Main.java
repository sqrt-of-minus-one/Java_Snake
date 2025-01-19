package edu.mephi.java;

import edu.mephi.java.snake.Game;
import edu.mephi.java.snake.ResourceManager;

import javax.swing.*;

public class Main
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Java_Snake");
		ResourceManager.create(8, 32);
		Game game = new Game(20, 20);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}