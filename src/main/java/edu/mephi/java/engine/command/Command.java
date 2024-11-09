package edu.mephi.java.engine.command;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Game;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

// The command (cheat) that can be applied to the game
public abstract class Command
{
	// Possible statuses of the command
	public enum EWaitingFor
	{
		APPLIED,	// The command has been applied
		COMPLETE,	// The command is complete and ready to be applied
		SPAWNABLE,	// The command is waiting for the spawnable to be specified
		EFFECT,		// The command is waiting for the effect to be specified
		NUMBER		// The command is waiting for the number to be specified
	}
	
	// Possible spawnable objects
	public enum ESpawnable
	{
		GRASS, APPLE, ROTTEN_APPLE, REVERSE_PILL, SHIELD, HAMMER
	}
	
	// Possible effects
	public enum EEffect
	{
		SHIELD
	}
	
	private final Game game;
	private Boolean applyResult; // null if the command has not been applied
	
	// After creating a command, its parameters should be specified
	// Use the `waitingFor()` method to find out what type of parameter is expected at the moment
	// When all the parameters are specified, `waitingFor() == COMPETE`; this means that the command is ready to be applied via the `apply()` method
	public Command(Game game)
	{
		this.game = game;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	// null if the command has not been applied
	public Boolean getApplyResult()
	{
		return applyResult;
	}
	
	// Applies the command
	// Returns `true` if the command has been applied successfully
	// You can also find out if the command has been applied successfully via the `getApplyResult()` method
	public boolean apply()
	{
		if (applyResult == null)
		{
			applyResult = apply_();
		}
		return applyResult;
	}
	
	// Draws the slash character (empty command) on the labels
	public static void drawEmpty(JLabel[] labels)
	{
		drawNone(labels);
		labels[0].setIcon(ResourceManager.getSprite(ESprite.SLASH));
	}
	
	// Clears the labels
	public static void drawNone(JLabel[] labels)
	{
		for (JLabel label : labels)
		{
			label.setIcon(ResourceManager.getSprite(ESprite.GRASS));
		}
	}
	
	// Draws the specified number on the specified position of the labels
	public static void drawNumber(JLabel[] labels, int from, int to, int number)
	{
		for (int i = to; i >= from; i--)
		{
			labels[i].setIcon(ResourceManager.getSprite(ESprite.getNum(number % 10)));
			number /= 10;
		}
	}
	
	// Returns the current status of the command (what type of parameter is expected)
	public abstract EWaitingFor waitingFor();
	
	// Draws the current state of the command on the labels
	public abstract void draw(JLabel[] labels);
	
	// Specifies the spawnable parameter
	// Don't forget to call the `applyParameter()` for confirmation
	public abstract void setSpawnable(ESpawnable spawnable);
	
	// Specifies the effect parameter
	// Don't forget to call the `applyParameter()` for confirmation
	public abstract void setEffect(EEffect effect);
	
	// The number parameters are specified digit-by-digit
	// E.g. the number 134 can be specified like that:
	//		addDigit(1);
	//		addDigit(3);
	//		addDigit(4);
	//		applyParameter();
	// Don't forget to call the `applyParameter()` for confirmation
	public abstract void addDigit(int digit);
	
	// Confirms the specified parameter
	public abstract void applyParameter();
	
	protected abstract boolean apply_();
}
