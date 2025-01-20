package edu.mephi.java;

import edu.mephi.java.engine.AbstractGame;
import edu.mephi.java.snake.SnakeGame;
import edu.mephi.java.three.ThreeGame;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
	public static final String[] GAME_NAMES = { "Snake", "Three in row" };
	public static final Scanner SCANNER = new Scanner(System.in);
	
	public static int askInt(String print, int min, int max)
	{
		int result = min - 1;
		do
		{
			System.out.print(print);
			try
			{
				result = SCANNER.nextInt();
			} catch (InputMismatchException _)
			{}
		} while (result < min || result > max);
		return result;
	}
	
	public static void main(String[] args)
	{
		StringBuilder chooseText = new StringBuilder("Choose the game:\n");
		for (int i = 0; i < GAME_NAMES.length; i++)
		{
			chooseText.append((i + 1))
					  .append(". ")
					  .append(GAME_NAMES[i])
					  .append('\n');
		}
		chooseText.append("> ");
		int selectedGame = askInt(chooseText.toString(), 1, GAME_NAMES.length);
		selectedGame--;
		System.out.println("The " + GAME_NAMES[selectedGame] + " is selected");
		System.out.println();
		
		int scaleFactor = askInt("Select the scale factor (4 or 5 is usually optimal): ", 1, 10);
		
		JFrame frame = new JFrame(GAME_NAMES[selectedGame]);
		AbstractGame<?, ?, ?, ?> game = null;
		switch (selectedGame)
		{
			case 0 ->
			{
				edu.mephi.java.snake.ResourceManager.create(8, 8 * scaleFactor);
				game = new SnakeGame(askInt("Set the field size: X = ", 5, 100), askInt("Set the field size: Y = ", 5, 100));
			}
			case 1 ->
			{
				edu.mephi.java.three.ResourceManager.create(8, 8 * scaleFactor);
				game = new ThreeGame(askInt("Set the field size: X = ", 5, 100), askInt("Set the field size: Y = ", 5, 100));
			}
		}
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}