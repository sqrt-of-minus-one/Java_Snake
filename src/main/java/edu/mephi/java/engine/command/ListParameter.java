package edu.mephi.java.engine.command;

import edu.mephi.java.engine.AbstractResourceManager;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.exception.CommandNoSpaceToDrawException;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Map;

// The command parameter that can have one of the specified values
public abstract class ListParameter
	extends AbstractParameter
{
	public record Value(String value, String sprite)
	{}
	
	private final Map<Integer /*keyCode*/, Value> values; // Possible parameter values
	private Value value = null; // The current parameter value
	
	public ListParameter(String type, Map<Integer /*keyCode*/, Value> values)
	{
		super(type);
		this.values = values;
	}
	
	@Override
	public String toString()
	{
		return value.value;
	}
	
	@Override
	public void input(KeyEvent key)
	{
		if (isConfirmed())
		{
			return;
		}
		Value val = values.get(key.getKeyCode());
		if (val != null)
		{
			value = val;
		}
	}
	
	@Override
	public boolean confirm()
	{
		if (value != null)
		{
			setConfirmed();
			return true;
		}
		return false;
	}
	
	@Override
	public int draw(JLabel[] labels, int startIndex, AbstractResourceManager resourceManager)
	{
		if (startIndex >= labels.length)
		{
			throw new CommandNoSpaceToDrawException("No space to draw the parameter");
		}
		labels[startIndex].setIcon(resourceManager.getSprite(
				value == null ? ECommonSprite.QUESTION.toString() : value.sprite));
		return startIndex + 1;
	}
}
