package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Orange
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final EItemType ITEM_TYPE = EItemType.ORANGE;
	
	public Orange(Field field)
	{
		super(field, GRAVITY, ITEM_TYPE);
	}
	
	public Orange(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.ORANGE.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.ORANGE_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.ORANGE_PICKED.toString();
	}
}
