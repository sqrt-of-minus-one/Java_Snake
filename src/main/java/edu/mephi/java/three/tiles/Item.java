package edu.mephi.java.three.tiles;

import edu.mephi.java.three.Field;

public abstract class Item
	extends Tile
{
	private final boolean gravity;
	
	public Item(Field field, boolean gravity)
	{
		super(field);
		this.gravity = gravity;
	}
	
	public boolean getGravity()
	{
		return gravity;
	}
}
