package edu.mephi.java.engine;

import edu.mephi.java.engine.command.AbstractCommand;

public abstract class AbstractTileSelect<
		Game extends AbstractGame<Game, Field, Tile, Command>,
		Field extends AbstractFieldSelect<Game, Field, Tile, Command>,
		Tile extends AbstractTileSelect<Game, Field, Tile, Command>,
		Command extends AbstractCommand<Game, Field, Tile, Command>>
	extends AbstractTile<Game, Field, Tile, Command>
{
	public AbstractTileSelect(Field field)
	{
		super(field);
	}
	
	public AbstractTileSelect(int x, int y, Field field)
	{
		super(x, y, field);
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
