package edu.mephi.java.engine.tiles;

public enum EDirection
{
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	public EDirection getOpposite()
	{
		return switch (this)
		{
			case UP -> DOWN;
			case DOWN -> UP;
			case LEFT -> RIGHT;
			case RIGHT -> LEFT;
		};
	}
}
