package edu.mephi.java.three.tiles;

import edu.mephi.java.three.Field;

import java.util.List;

public abstract class Item
	extends Tile
{
	// Three objects in a row will be destroyed if they have a common type
	public enum EItemType
	{
		APPLE, ORANGE, BANANA, CUCUMBER, WATER, MEAT, BOMB
	}
	
	private final boolean gravity;
	private final EItemType itemType;
	
	public Item(Field field, boolean gravity, EItemType itemType)
	{
		super(field);
		this.gravity = gravity;
		this.itemType = itemType;
	}
	
	public Item(int x, int y, Field field, boolean gravity, EItemType itemType)
	{
		super(x, y, field);
		this.gravity = gravity;
		this.itemType = itemType;
	}
	
	public boolean getGravity()
	{
		return gravity;
	}
	
	public EItemType getItemType()
	{
		return itemType;
	}
}
