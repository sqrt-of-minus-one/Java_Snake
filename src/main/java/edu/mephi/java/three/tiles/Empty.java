package edu.mephi.java.three.tiles;

import edu.mephi.java.three.ESprite;
import edu.mephi.java.three.Field;

public class Empty
	extends Tile
{
	public Empty(Field field)
	{
		super(field);
	}
	
	public Empty(int x, int y, Field field)
	{
		super(x, y, field);
	}
	
	@Override
	protected String getPlainSprite()
	{
		return ESprite.EMPTY.toString();
	}
	
	@Override
	protected String getSelectedSprite()
	{
		return ESprite.EMPTY_SELECTED.toString();
	}
	
	@Override
	protected String getPickedSprite()
	{
		return ESprite.EMPTY_PICKED.toString();
	}
}
