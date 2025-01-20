package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Water
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final boolean MOVABLE = true;
	public static final EItemType ITEM_TYPE = EItemType.WATER;
	
	public Water(Field field)
	{
		super(field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	public Water(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.WATER.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.WATER_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.WATER_PICKED.toString();
	}
}
