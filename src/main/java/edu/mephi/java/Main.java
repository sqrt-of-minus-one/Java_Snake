package edu.mephi.java;

import edu.mephi.java.engine.Game;

import javax.swing.*;

public class Main
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Java_Snake");
		Game game = new Game();
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}