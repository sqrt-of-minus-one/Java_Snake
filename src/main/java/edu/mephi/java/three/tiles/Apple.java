package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

import java.util.ArrayList;
import java.util.List;

public class Apple
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final EItemType ITEM_TYPE = EItemType.APPLE;
	
	public Apple(Field field)
	{
		super(field, GRAVITY, ITEM_TYPE);
	}
	
	public Apple(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.APPLE.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.APPLE_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.APPLE_PICKED.toString();
	}
}
