package edu.mephi.java.three;

import edu.mephi.java.engine.AbstractFieldSelect;
import edu.mephi.java.three.command.Command;
import edu.mephi.java.three.tiles.*;

import java.util.Arrays;

public class Field
	extends AbstractFieldSelect<ThreeGame, Field, Tile, Command>
{
	public static final int WALLS_NUMBER = 10;
	
	public static final int APPLE_PROBABILITY_INDEX = 0;
	public static final int ORANGE_PROBABILITY_INDEX = 1;
	public static final int BANANA_PROBABILITY_INDEX = 2;
	public static final int CUCUMBER_PROBABILITY_INDEX = 3;
	public static final int WATER_PROBABILITY_INDEX = 4;
	public static final int MEAT_PROBABILITY_INDEX = 5;
	public static final int BOMB_PROBABILITY_INDEX = 6;
	public static final double[] ITEM_PROBABILITIES = new double[7];
	public static final double TOTAL_ITEM_PROBABILITY;
	
	static
	{
		ITEM_PROBABILITIES[APPLE_PROBABILITY_INDEX] = 3;
		ITEM_PROBABILITIES[ORANGE_PROBABILITY_INDEX] = 2;
		ITEM_PROBABILITIES[BANANA_PROBABILITY_INDEX] = 1;
		ITEM_PROBABILITIES[CUCUMBER_PROBABILITY_INDEX] = 1.5;
		ITEM_PROBABILITIES[WATER_PROBABILITY_INDEX] = 1.5;
		ITEM_PROBABILITIES[MEAT_PROBABILITY_INDEX] = 2;
		ITEM_PROBABILITIES[BOMB_PROBABILITY_INDEX] = .1;
		TOTAL_ITEM_PROBABILITY = Arrays.stream(ITEM_PROBABILITIES).sum();
	}
	
	private int score = 0;
	
	public Field(ThreeGame game)
	{
		super(game, true, false);
		fillEmpty();
		generateWalls(WALLS_NUMBER);
		game.startGravity();
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void pick()
	{
		switchPicked();
	}
	
	public void fillEmpty()
	{
		for (int x = 0; x < getSizeX(); x++)
		{
			for (int y = 0; y < getSizeY(); y++)
			{
				replaceTile(x, y, new Empty(this));
			}
		}
	}
	
	public void generateWalls(int wallsNumber)
	{
		for (int i = 0; i < wallsNumber; i++)
		{
			replaceRandom(Empty.class, new Wall(this));
		}
	}
	
	public boolean fallAndFill()
	{
		boolean needToContinue = false;
		for (int y = getSizeY() - 2; y >= 0; y--)
		{
			for (int x = 0; x < getSizeX(); x++)
			{
				Tile down = getExactTile(x, y + 1);
				if (Empty.class.isAssignableFrom(down.getClass()))
				{
					Tile tile = getExactTile(x, y);
					if (Item.class.isAssignableFrom(tile.getClass()) && ((Item)tile).getGravity())
					{
						swapTiles(tile, down);
						if (y + 2 < getSizeY() && Empty.class.isAssignableFrom(getExactTile(x, y + 2).getClass()))
						{
							needToContinue = true;
						}
					}
				}
			}
		}
		
		for (int x = 0; x < getSizeX(); x++)
		{
			Tile tile = getExactTile(x, 0);
			if (Empty.class.isAssignableFrom(tile.getClass()))
			{
				Item newItem = null;
				do
				{
					newItem = getRandomItem();
				} while (x > 1 &&
						Item.class.isAssignableFrom(getExactTile(x - 1, 0).getClass()) &&
						((Item)getExactTile(x - 1, 0)).getItemType() == newItem.getItemType() &&
						Item.class.isAssignableFrom(getExactTile(x - 2, 0).getClass()) &&
						((Item)getExactTile(x - 2, 0)).getItemType() == newItem.getItemType() ||
						
						Item.class.isAssignableFrom(getExactTile(x, 1).getClass()) &&
						((Item)getExactTile(x, 1)).getItemType() == newItem.getItemType() &&
						Item.class.isAssignableFrom(getExactTile(x, 2).getClass()) &&
						((Item)getExactTile(x, 2)).getItemType() == newItem.getItemType());
				replaceTile(tile, newItem);
				needToContinue = true;
			}
		}
		
		System.out.println(needToContinue);
		return needToContinue;
	}
	
	private Item getRandomItem()
	{
		double random = ThreeGame.RANDOM.nextDouble(TOTAL_ITEM_PROBABILITY);
		if (random < ITEM_PROBABILITIES[APPLE_PROBABILITY_INDEX])
		{
			return new Apple(this);
		}
		random -= ITEM_PROBABILITIES[APPLE_PROBABILITY_INDEX];
		if (random < ITEM_PROBABILITIES[ORANGE_PROBABILITY_INDEX])
		{
			return new Orange(this);
		}
		random -= ITEM_PROBABILITIES[ORANGE_PROBABILITY_INDEX];
		if (random < ITEM_PROBABILITIES[BANANA_PROBABILITY_INDEX])
		{
			return new Banana(this);
		}
		random -= ITEM_PROBABILITIES[BANANA_PROBABILITY_INDEX];
		if (random < ITEM_PROBABILITIES[CUCUMBER_PROBABILITY_INDEX])
		{
			return new Cucumber(this);
		}
		random -= ITEM_PROBABILITIES[CUCUMBER_PROBABILITY_INDEX];
		if (random < ITEM_PROBABILITIES[WATER_PROBABILITY_INDEX])
		{
			return new Water(this);
		}
		random -= ITEM_PROBABILITIES[WATER_PROBABILITY_INDEX];
		if (random < ITEM_PROBABILITIES[MEAT_PROBABILITY_INDEX])
		{
			return new Meat(this);
		}
		random -= ITEM_PROBABILITIES[MEAT_PROBABILITY_INDEX];
		if (random < ITEM_PROBABILITIES[BOMB_PROBABILITY_INDEX])
		{
			return new Bomb(this);
		}
		random -= ITEM_PROBABILITIES[BOMB_PROBABILITY_INDEX];
		return new Apple(this);
	}
}
