package edu.mephi.java.engine;

import javax.swing.*;
import java.lang.ref.WeakReference;

public abstract class AbstractTile
{
	private final WeakReference<AbstractField> field;
	
	public AbstractTile(AbstractField field)
	{
		this.field = new WeakReference<>(field);
	}
	
	public AbstractField getField()
	{
		return field.get();
	}
	
	// Returns the sprite for the tile
	public abstract ImageIcon getSprite();
}
