package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Banana
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final EItemType ITEM_TYPE = EItemType.BANANA;
	
	public Banana(Field field)
	{
		super(field, GRAVITY, ITEM_TYPE);
	}
	
	public Banana(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.BANANA.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.BANANA_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.BANANA_PICKED.toString();
	}
}
