package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Meat
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final EItemType ITEM_TYPE = EItemType.MEAT;
	
	public Meat(Field field)
	{
		super(field, GRAVITY, ITEM_TYPE);
	}
	
	public Meat(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.MEAT.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.MEAT_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.MEAT_PICKED.toString();
	}
}
