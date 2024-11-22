package edu.mephi.java.engine;

import edu.mephi.java.engine.command.AbstractCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public abstract class AbstractGame
	extends JPanel
{
	public static final Random RANDOM = new Random();
	
	private final int sizeX, sizeY; // The size of the field
	private AbstractField field;
	private boolean gameOver = false;
	private AbstractCommand command = null; // The command that is currently being set up
	private boolean commandStarted = false; // Whether a command is being set up at the moment
	private boolean paused = false; // Whether the game is currenly paused
	
	private final AbstractResourceManager resourceManager;
	
	// The window contains three areas:
	private final JLabel[][] fieldLabels; // The main area is the game field itself
	private final JLabel[][] statusLabels; // The status area shows information like player score, health etc.
	private final JLabel[] commandLabels; // The command area is used when a command is being typed
	
	// statusTilesCounts contains information about how many tiles are needed for each status datum
	// E.g. if we want to display the player score in 4 tiles and then the player health in 3 tiles, then statusTilesCounts = { 3, 4 }
	// Set statusTilesCounts = { 4, 1, 3 } if you want to have a 1-tile gap between the score and the health
	public AbstractGame(int sizeX, int sizeY, AbstractResourceManager resourceManager, int[] statusTilesCounts)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.resourceManager = resourceManager;
		
		// The list describing the rows of the status data
		List<StatusRow> statusRows = calculateStatusRows(sizeX, statusTilesCounts);
		
		setLayout(new GridLayout(sizeY + statusRows.size() + 1, sizeX, 0, 0));
		setPreferredSize(new Dimension(sizeX * resourceManager.getFieldTileSize(),
									   (sizeY + statusRows.size() + 1) * resourceManager.getFieldTileSize()));
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (!commandStarted)
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_SLASH -> startCommand();
						case KeyEvent.VK_ESCAPE -> switchPause();
						default -> controls(e);
					}
				}
				// Entering a command
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) // Back to game
				{
					resetCommand();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) // Confirm a parameter or apply a command
				{
					if (command == null || command.getStatus() == AbstractCommand.EStatus.APPLIED)
					{
						// Cancel the entering of a command
						resetCommand();
					}
					else
					{
						command.confirmParameter(); // Confirm the parameter
						if (command.getStatus() == AbstractCommand.EStatus.COMPLETE) // Apply the command if the confirmed parameter was the last one
						{
							command.apply();
							updateSprites();
						}
						command.draw(commandLabels, 0, resourceManager);
					}
				}
				else if (command == null)
				{
					// Specify the command
					command = createCommand(e);
					if (command != null)
					{
						command.draw(commandLabels, 0, resourceManager);
					}
				}
				else
				{
					switch (command.getStatus())
					{
						case WAITING -> command.setParameter(e);
						case COMPLETE -> // If the command is complete, apply it
						{
							if (e.getKeyCode() == KeyEvent.VK_ENTER)
							{
								command.apply();
								updateSprites();
							}
						}
						case APPLIED -> resetCommand(); // if the command has been applied, go back to game
					}
					if (command != null)
					{
						command.draw(commandLabels, 0, resourceManager);
					}
				}
			}
		});
		
		// Create a field area
		fieldLabels = new JLabel[sizeX][sizeY];
		for (int y = 0; y < sizeY; y++)
		{
			for (int x = 0; x < sizeX; x++)
			{
				fieldLabels[x][y] = new JLabel(resourceManager.getSprite(ECommonSprite.NOTHING));
				add(fieldLabels[x][y]);
			}
		}
		
		// Create a status area
		statusLabels = new JLabel[statusTilesCounts.length][];
		if (!statusRows.isEmpty()) // Create it only if there is something to display
		{
			ListIterator<StatusRow> statusRowIterator = statusRows.listIterator();
			StatusRow currentRow = statusRowIterator.next(); // Doesn't throw: we have checked that statusRows is not empty
			for (int i = 0; i < statusTilesCounts.length; i++) // For each status datum
			{
				if (i >= currentRow.endIndex) // If the status datum should be places in a new row
				{
					// Fill the rest of the row with empty tiles
					for (int j = 0; j < currentRow.emptyTiles; j++)
					{
						add(new JLabel(resourceManager.getSprite(ECommonSprite.NOTHING)));
					}
					// Doesn't throw: the last element should have endIndex == statusTilesCounts.length, which can't be reached in the loop
					currentRow = statusRowIterator.next();
				}
				statusLabels[i] = new JLabel[statusTilesCounts[i]];
				for (int j = 0; j < statusLabels[i].length; j++)
				{
					statusLabels[i][j] = new JLabel(resourceManager.getSprite(ECommonSprite.NOTHING));
					add(statusLabels[i][j]);
				}
			}
			for (int j = 0; j < currentRow.emptyTiles; j++) // Fill the rest of the last row with empty tiles
			{
				add(new JLabel(resourceManager.getSprite(ECommonSprite.NOTHING)));
			}
		}
		
		// Create a command area
		commandLabels = new JLabel[sizeX];
		for (int i = 0; i < sizeX; i++)
		{
			commandLabels[i] = new JLabel(resourceManager.getSprite(ECommonSprite.NOTHING));
			add(commandLabels[i]);
		}
	}
	
	public int getSizeX()
	{
		return sizeX;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}
	
	public AbstractField getField()
	{
		return field;
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	public AbstractResourceManager getResourceManager()
	{
		return resourceManager;
	}
	
	// Start of restart the game, and set a new field
	public void restart(AbstractField field)
	{
		this.field = field;
		gameOver = false;
	}
	
	public void lose()
	{
		gameOver = true;
		if (field != null)
		{
			field.onLose();
		}
	}
	
	public void switchPause()
	{
		if (paused)
		{
			unpause();
		}
		else
		{
			pause();
		}
	}
	
	public void pause()
	{
		if (!paused)
		{
			drawEmptyCommand(resourceManager.getSprite(ECommonSprite.PAUSE));
			paused = true;
		}
	}
	
	public void unpause()
	{
		if (paused)
		{
			drawEmptyCommand(resourceManager.getSprite(ECommonSprite.NOTHING));
			paused = false;
		}
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public void updateSprites()
	{
		updateField();
		updateStatus();
	}
	
	public void updateField()
	{
		for (int x = 0; x < sizeX; x++)
		{
			for (int y = 0; y < sizeY; y++)
			{
				fieldLabels[x][y].setIcon(field.getTile(x, y).getSprite());
			}
		}
	}
	
	public abstract void updateStatus();
	
	protected JLabel[] getStatusLabels(int index)
	{
		return statusLabels[index];
	}
	
	protected abstract void controls(KeyEvent event);
	
	protected abstract AbstractCommand createCommand(KeyEvent event);
	
	// Returns the list with the information about rows of status data
	private static List<StatusRow> calculateStatusRows(int sizeX, int[] statusTilesCounts)
	{
		List<StatusRow> rows = new LinkedList<>();
		int currentX = 0; // How many tile have been put in the current row
		boolean hasAny = false; // If there has been any status data
		for (int i = 0; i < statusTilesCounts.length; i++)
		{
			if (statusTilesCounts[i] > 0)
			{
				hasAny = true;
			}
			if (currentX + statusTilesCounts[i] > sizeX) // If the current row doesn't have enough space for a new datum, we need to create a new row
			{
				rows.add(new StatusRow(i, sizeX - currentX));
				currentX = statusTilesCounts[i];
			}
			else // This row has enough space for a new datum
			{
				currentX += statusTilesCounts[i];
			}
		}
		if (hasAny)
		{
			rows.add(new StatusRow(statusTilesCounts.length, sizeX - currentX));
		}
		return rows;
	}
	
	private void startCommand()
	{
		if (!gameOver && !commandStarted)
		{
			pause();
			commandStarted = true;
			drawEmptyCommand(resourceManager.getSprite(ECommonSprite.SLASH));
		}
	}
	
	private void resetCommand()
	{
		command = null;
		commandStarted = false;
		unpause();
	}
	
	// Fills the command labels with ECommonSprite.NOTHING
	// The first command label is filled by `firstSprite`
	// Calls the `repaint()` method
	private void drawEmptyCommand(ImageIcon firstSprite)
	{
		commandLabels[0].setIcon(firstSprite);
		for (int i = 1; i < commandLabels.length; i++)
		{
			commandLabels[i].setIcon(resourceManager.getSprite(ECommonSprite.NOTHING));
		}
		repaint();
	}
	
	// Describes a row of the status data
	// endIndex is the index of the first datum that should be placed on the next row
	// emptyTiles if the number of empty tiles in the end of the row
	private record StatusRow(int endIndex, int emptyTiles)
	{}
}
