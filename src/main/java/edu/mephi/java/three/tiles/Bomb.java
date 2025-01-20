package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Bomb
	extends Item
{
	public static final boolean GRAVITY = true;
	public static final boolean MOVABLE = true;
	public static final EItemType ITEM_TYPE = EItemType.BOMB;
	
	public Bomb(Field field)
	{
		super(field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	public Bomb(int x, int y, Field field)
	{
		super(x, y, field, GRAVITY, MOVABLE, ITEM_TYPE);
	}
	
	@Override
	public String getPlainSprite()
	{
		return ESprite.BOMB.toString();
	}
	
	@Override
	public String getSelectedSprite()
	{
		return ESprite.BOMB_SELECTED.toString();
	}
	
	@Override
	public String getPickedSprite()
	{
		return ESprite.BOMB_PICKED.toString();
	}
}
