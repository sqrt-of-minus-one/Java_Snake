package edu.mephi.java.engine.command;

import edu.mephi.java.engine.AbstractResourceManager;

import javax.swing.*;
import java.awt.event.KeyEvent;

// The base class for the command parameters
public abstract class AbstractParameter
{
	private final String type; // The name of the parameter type
	private boolean confirmed = false; // After setting a value, you need to confirm it
	
	public AbstractParameter(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean isConfirmed()
	{
		return confirmed;
	}
	
	protected void setConfirmed()
	{
		confirmed = true;
	}
	
	// Get the value of the parameter as string
	@Override
	public abstract String toString();
	
	// Input from the keyboard
	public abstract void input(KeyEvent key);
	
	// Try to confirm the parameter value (Enter is pressed)
	public abstract boolean confirm();
	
	// Draw the value of the parameter on the labels
	// Returns the first index of the `labels` where nothing has been drawn
	public abstract int draw(JLabel[] labels, int startIndex, AbstractResourceManager resourceManager);
}
