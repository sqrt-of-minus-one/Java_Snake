package edu.mephi.java.engine.command;

import edu.mephi.java.engine.*;
import edu.mephi.java.engine.exception.CommandNoSpaceToDrawException;
import edu.mephi.java.engine.AbstractGame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.lang.ref.WeakReference;

// The base class for all the commands
// A command can have some parameters (see the `AbstractParameter` class)
// Before the command can be applied, all its parameters must be set (`setParameter()`) and confirmed (`confirmParameter()`)
public abstract class AbstractCommand<
		Game extends AbstractGame<Game, Field, Tile, Command>,
		Field extends AbstractField<Game, Field, Tile, Command>,
		Tile extends AbstractTile<Game, Field, Tile, Command>,
		Command extends AbstractCommand<Game, Field, Tile, Command>>
{
	// Possible statuses of the command
	public enum EStatus
	{
		WAITING,	// Is waiting for parameters
		COMPLETE,	// Is complete and ready to be applied
		APPLIED,	// Has been applied
	}
	
	private final String sprite; // The icon of the command
	private final AbstractParameter[] parameters;
	private int currentParameterIndex = 0; // The index of the parameter that is waiting to be set
	private final WeakReference<Game> game;
	private Boolean result = null; // null if the command hasn't been applied yet
	
	// After creating a command, its parameters should be specified
	public AbstractCommand(String sprite, AbstractParameter[] parameters, Game game)
	{
		this.sprite = sprite;
		this.parameters = parameters;
		this.game = new WeakReference<>(game);
	}
	
	public Game getGame()
	{
		return game.get();
	}
	
	public Boolean getResult()
	{
		return result;
	}
	
	// Applies the command
	// The result is returned, you can access it later via the `getResult()` method
	// Do not override this method, override apply_() instead
	public boolean apply()
	{
		if (getStatus() == EStatus.COMPLETE)
		{
			result = apply_();
		}
		return result;
	}
	
	public EStatus getStatus()
	{
		 if (result == null)
		 {
			 return isComplete() ? EStatus.COMPLETE : EStatus.WAITING;
		 }
		 return EStatus.APPLIED;
	}
	
	public boolean isComplete()
	{
		return currentParameterIndex >= parameters.length;
	}
	
	public String getNextParameterType()
	{
		if (currentParameterIndex < parameters.length)
		{
			return parameters[currentParameterIndex].getType();
		}
		return null;
	}
	
	public void setParameter(KeyEvent key)
	{
		if (currentParameterIndex < parameters.length)
		{
			parameters[currentParameterIndex].input(key);
		}
	}
	
	public boolean confirmParameter()
	{
		if (currentParameterIndex < parameters.length)
		{
			if (parameters[currentParameterIndex].confirm())
			{
				currentParameterIndex++;
				return true;
			}
		}
		return false;
	}
	
	// Draw the slash, the command icon, and the parameters
	// If the command has been applied, draws the result
	// Return the index of the first label where nothing has been drawn
	public int draw(JLabel[] labels, int startIndex, AbstractResourceManager resourceManager)
	{
		if (startIndex + 2 < labels.length)
		{
			throw new CommandNoSpaceToDrawException("No space to draw the command icon");
		}
		labels[startIndex++].setIcon(resourceManager.getSprite(
				result == null ? ECommonSprite.SLASH :
						result ? ECommonSprite.SUCCESS : ECommonSprite.FAILURE));
		labels[startIndex++].setIcon(resourceManager.getSprite(sprite));
		for (int i = 0; i <= currentParameterIndex && i < parameters.length; i++)
		{
			startIndex = parameters[i].draw(labels, ++startIndex, resourceManager);
		}
		Game strongGame = game.get();
		if (strongGame != null)
		{
			strongGame.repaint();
		}
		return startIndex;
	}
	
	protected AbstractParameter getParameter(int index)
	{
		return parameters[index];
	}
	
	protected abstract boolean apply_();
}
