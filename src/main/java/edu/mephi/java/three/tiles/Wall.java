package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Wall
	extends Item
{
	public static final boolean GRAVITY = false;
	public static final boolean MOVABLE = false;
	public static final EItemType ITEM_TYPE = null;
	
	public Wall(Field field)
	{
		super(field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	public Wall(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.WALL.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.WALL_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.WALL_PICKED.toString();
	}
}
