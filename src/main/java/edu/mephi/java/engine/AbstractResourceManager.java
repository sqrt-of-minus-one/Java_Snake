package edu.mephi.java.engine;

import edu.mephi.java.engine.exception.ResourceManagerNoSpriteException;
import edu.mephi.java.engine.exception.ResourceManagerNotInitialisedException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// The resource manager allows to get any sprite you need using `getSprite()` method
// This is the base class; the resource manager of every game should extend it
public abstract class AbstractResourceManager
{
	private final int textureTileSize; // The size of a single tile in a texture
	private final int fieldTileSize; // The size of a single tile on a screen
	private final BufferedImage[] bufferedImages; // The texture images
	
	private boolean initialised = false;
	private final Map<String, ImageIcon> sprites = new HashMap<>();
	
	public int getTextureTileSize()
	{
		return textureTileSize;
	}
	
	public int getFieldTileSize()
	{
		return fieldTileSize;
	}
	
	public boolean isInitialised()
	{
		return initialised;
	}
	
	public ImageIcon getSprite(ECommonSprite sprite)
	{
		return getSprite(sprite.toString());
	}
	
	protected AbstractResourceManager(int textureTileSize, int fieldTileSize, String[] textureFiles)
	{
		this.textureTileSize = textureTileSize;
		this.fieldTileSize = fieldTileSize;
		bufferedImages = new BufferedImage[textureFiles.length];
		try
		{
			for (int i = 0; i < textureFiles.length; i++)
			{
				bufferedImages[i] = ImageIO.read(new File(textureFiles[i]));
			}
		}
		catch (IOException e)
		{
			System.out.println("Error: couldn't open a texture file");
		}
	}
	
	// This method is supposed to be overridden and be invoked in the constructor
	// The overridden method should fill the `sprites` map via the `addSprite()` method and then call the `super.initialise()`
	protected void initialise()
	{
		for (ECommonSprite sprite : ECommonSprite.values())
		{
			if (!sprites.containsKey(sprite.toString()))
			{
				throw new ResourceManagerNoSpriteException("The resource manager doesn't contain one of the necessary sprites (" + sprite.toString() + ")");
			}
		}
		initialised = true;
	}
	
	protected ImageIcon getSprite(String sprite)
	{
		if (!initialised)
		{
			throw new ResourceManagerNotInitialisedException("Attempt to get a sprite from a resource manager that hasn't been initialised");
		}
		if (sprites.containsKey(sprite))
		{
			return sprites.get(sprite);
		}
		else
		{
			return sprites.get(ECommonSprite.QUESTION.toString());
		}
	}
	
	// Extracts a sprite from the texture with the specified index and adds it in the sprites map
	// x & y are set in the tile coordinates, not in pixels (see textureTileSize)
	protected void addSprite(String key, int textureIndex, int x, int y)
	{
		sprites.put(key, createImage(textureIndex, x, y));
	}
	
	// Extracts a sprite from the texture with the specified index
	// x & y are set in the tile coordinates, not in pixels (see textureTileSize)
	private ImageIcon createImage(int textureIndex, int x, int y)
	{
		return new ImageIcon(bufferedImages[textureIndex]
									 .getSubimage(x * textureTileSize, y * textureTileSize, textureTileSize, textureTileSize)
									 .getScaledInstance(fieldTileSize, fieldTileSize, BufferedImage.SCALE_FAST));
	}
}
