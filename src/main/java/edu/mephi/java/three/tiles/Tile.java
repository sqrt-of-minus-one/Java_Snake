package edu.mephi.java.three.tiles;

import edu.mephi.java.engine.AbstractTile;
import edu.mephi.java.three.Field;
import edu.mephi.java.three.ThreeGame;
import edu.mephi.java.three.command.Command;

public abstract class Tile
	extends AbstractTile<ThreeGame, Field, Tile, Command>
{
	public Tile(Field field)
	{
		super(field);
	}
	
	public boolean isSelected()
	{
		return isAlive() &&
				getField().getSelectedX() == getX() &&
				getField().getSelectedY() == getY();
	}
	
	@Override
	public String getSprite()
	{
		if (isSelected())
		{
			return getField().isPicked() ? getPickedSprite() : getSelectedSprite();
		}
		return getPlainSprite();
	}
	
	protected abstract String getPlainSprite();
	protected abstract String getSelectedSprite();
	protected abstract String getPickedSprite();
}
