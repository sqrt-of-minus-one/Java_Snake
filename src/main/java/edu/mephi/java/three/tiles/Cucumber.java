package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Cucumber
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final boolean MOVABLE = true;
	public static final EItemType ITEM_TYPE = EItemType.CUCUMBER;
	
	public Cucumber(Field field)
	{
		super(field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	public Cucumber(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.CUCUMBER.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.CUCUMBER_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.CUCUMBER_PICKED.toString();
	}
}
