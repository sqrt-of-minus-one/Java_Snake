package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.ListParameter;
import edu.mephi.java.snake.ESprite;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class EffectParameter
	extends ListParameter
{
	public static final String TYPE = "Effect";
	private static final Map<Integer /*keyCode*/, ListParameter.Value> VALUES = new HashMap<>();
	
	static
	{
		VALUES.put(KeyEvent.VK_S, new ListParameter.Value(ESprite.SHIELD.toString(), ESprite.SHIELD.toString()));
	}
	
	EffectParameter()
	{
		super(TYPE, VALUES);
	}
}
