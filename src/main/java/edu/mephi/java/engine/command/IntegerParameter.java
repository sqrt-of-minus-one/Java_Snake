package edu.mephi.java.engine.command;

import edu.mephi.java.engine.AbstractResourceManager;
import edu.mephi.java.engine.ECommonSprite;
import edu.mephi.java.engine.exception.CommandNoSpaceToDrawException;

import javax.swing.*;
import java.awt.event.KeyEvent;

// The integer command parameter
public class IntegerParameter
	extends AbstractParameter
{
	public static final String TYPE = "Integer";
	private final int min, max; // Minimum and maximum values of the parameter
	private final int digits; // Number of digits to draw the value
	private int value; // The value of the parameter (including sign)
	private boolean negative = false; // Whether the value is negative
	
	IntegerParameter(int min, int max, int digits)
	{
		super(TYPE);
		if (max < min)
		{
			throw new IllegalArgumentException("The maximum cannot be less than the minimum");
		}
		if (max >= Math.pow(10, digits) || min <= -Math.pow(10, digits - 1) + 1)
		{
			throw new IllegalArgumentException("The specified range cannot be represented with the specified number of digits");
		}
		this.min = min;
		this.max = max;
		this.digits = digits;
		value = Math.max(0, min);
	}
	
	IntegerParameter(int digits)
	{
		super(TYPE);
		min = (int)Math.max(Integer.MIN_VALUE, -Math.pow(10, digits - 1) + 1);
		max = (int)Math.min(Integer.MAX_VALUE, Math.pow(10, digits) - 1);
		this.digits = digits;
		this.value = 0;
	}
	
	public int getMin()
	{
		return min;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int getDigits()
	{
		return digits;
	}
	
	public int getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(value);
	}
	
	@Override
	public void input(KeyEvent key)
	{
		if (isConfirmed())
		{
			return;
		}
		switch (key.getKeyCode())
		{
			case KeyEvent.VK_MINUS -> // Swap sign
			{
				negative = !negative;
				checkValue(0);
			}
			case KeyEvent.VK_PLUS -> // Set sign to plus
			{
				negative = false;
				checkValue(0);
			}
			default ->
			{
				int digit = keyToInt(key);
				if (digit >= 0 && digit < 10)
				{
					value *= 10;
					value += (value >= 0 ? digit : -digit);
					checkValue(digit);
				}
			}
		}
	}
	
	@Override
	public boolean confirm()
	{
		setConfirmed();
		return true;
	}
	
	@Override
	public int draw(JLabel[] labels, int startIndex, AbstractResourceManager resourceManager)
	{
		if (startIndex + digits >= labels.length)
		{
			throw new CommandNoSpaceToDrawException("No space to draw the parameter");
		}
		int valueToDraw = Math.abs(value);
		for (int i = digits - 1; i > 0; i--) // Draw, i > 0 is not a mistake, the first digit is drawn separately
		{
			labels[startIndex + i].setIcon(resourceManager.getSprite(ECommonSprite.getNum(valueToDraw % 10)));
			valueToDraw /= 10;
		}
		// Draw either the first digit or the minus sign (if the value is negative)
		labels[startIndex].setIcon(resourceManager.getSprite(
				negative ? ECommonSprite.MINUS : ECommonSprite.getNum(valueToDraw % 10)));
		return startIndex + digits;
	}
	
	// Checks if the value is between min and max, resets it if not
	private void checkValue(int resetTo)
	{
		if (negative ^ value < 0) // If the sign is wrong, fix it
		{
			value = -value;
		}
		if (value < min || value > max) // If the value is out of the allowed range
		{
			if (resetTo >= min && resetTo <= max) // If the resetTo is in the allowed range, use it
			{
				value = resetTo;
			}
			else
			{
				value = Math.max(0, min);
			}
		}
	}
	
	// Converts a pressed key to integer
	private static int keyToInt(KeyEvent key)
	{
		return switch (key.getKeyCode())
		{
			case KeyEvent.VK_0 -> 0;
			case KeyEvent.VK_1 -> 1;
			case KeyEvent.VK_2 -> 2;
			case KeyEvent.VK_3 -> 3;
			case KeyEvent.VK_4 -> 4;
			case KeyEvent.VK_5 -> 5;
			case KeyEvent.VK_6 -> 6;
			case KeyEvent.VK_7 -> 7;
			case KeyEvent.VK_8 -> 8;
			case KeyEvent.VK_9 -> 9;
			default -> -1;
		};
	}
}
