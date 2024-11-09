package edu.mephi.java.engine.command;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Game;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public abstract class Command
{
	public enum EWaitingFor
	{
		APPLIED, COMPLETE, SPAWNABLE, EFFECT, NUMBER
	}
	
	public enum ESpawnable
	{
		GRASS, APPLE, ROTTEN_APPLE, REVERSE_PILL, SHIELD, HAMMER
	}
	
	public enum EEffect
	{
		SHIELD
	}
	
	private Game game;
	private Boolean applyResult;
	
	public Command(Game game)
	{
		this.game = game;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public Boolean getApplyResult()
	{
		return applyResult;
	}
	
	public boolean apply()
	{
		if (applyResult == null)
		{
			applyResult = apply_();
		}
		return applyResult;
	}
	
	public static void drawEmpty(JLabel[] labels)
	{
		drawNone(labels);
		labels[0].setIcon(ResourceManager.getSprite(ESprite.SLASH));
	}
	
	public static void drawNone(JLabel[] labels)
	{
		for (JLabel label : labels)
		{
			label.setIcon(ResourceManager.getSprite(ESprite.GRASS));
		}
	}
	
	public static void drawNumber(JLabel[] labels, int from, int to, int number)
	{
		for (int i = to; i >= from; i--)
		{
			labels[i].setIcon(ResourceManager.getSprite(ESprite.getNum(number % 10)));
			number /= 10;
		}
	}
	
	public abstract EWaitingFor waitingFor();
	public abstract void draw(JLabel[] labels);
	public abstract void setSpawnable(ESpawnable spawnable);
	public abstract void setEffect(EEffect effect);
	public abstract void addDigit(int digit);
	public abstract void applyElement();
	protected abstract boolean apply_();
}
