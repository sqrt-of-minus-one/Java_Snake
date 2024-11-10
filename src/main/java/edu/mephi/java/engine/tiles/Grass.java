package edu.mephi.java.engine.tiles;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Field;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

// The empty tile
public class Grass
		extends Tile
{
	public Grass(int x, int y, Field field)
	{
		super(x, y, field, -1);
	}
	
	@Override
	public ImageIcon getSprite()
	{
		return ResourceManager.getSprite(ESprite.GRASS);
	}
}
