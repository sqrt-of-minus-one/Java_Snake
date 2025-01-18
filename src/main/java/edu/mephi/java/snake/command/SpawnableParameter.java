package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.ListParameter;
import edu.mephi.java.snake.ESprite;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class SpawnableParameter
	extends ListParameter
{
	public static final String TYPE = "Spawnable";
	private static final Map<Integer /*keyCode*/, Value> VALUES = new HashMap<>();
	
	static
	{
		VALUES.put(KeyEvent.VK_G, new Value(ESprite.GRASS.toString(), ESprite.GRASS.toString()));
		VALUES.put(KeyEvent.VK_A, new Value(ESprite.APPLE.toString(), ESprite.APPLE.toString()));
		VALUES.put(KeyEvent.VK_R, new Value(ESprite.ROTTEN_APPLE.toString(), ESprite.ROTTEN_APPLE.toString()));
		VALUES.put(KeyEvent.VK_Z, new Value(ESprite.REVERSE_PILL.toString(), ESprite.REVERSE_PILL.toString()));
		VALUES.put(KeyEvent.VK_S, new Value(ESprite.SHIELD.toString(), ESprite.SHIELD.toString()));
		VALUES.put(KeyEvent.VK_H, new Value(ESprite.HAMMER.toString(), ESprite.HAMMER.toString()));
		VALUES.put(KeyEvent.VK_W, new Value(ESprite.WALL.toString(), ESprite.WALL.toString()));
	}
	
	SpawnableParameter()
	{
		super(TYPE, VALUES);
	}
}
