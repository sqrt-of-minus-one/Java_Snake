package edu.mephi.java.engine.command;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Game;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

// Apply an effect for the snake
public class EffectCommand extends Command
{
	// The status of the command
	public enum EInputPosition
	{
		EFFECT,		// Waiting for the effect type to be specified
		DURATION,	// Waiting for the effect duration to be specified
		COMPLETE	// The command is complete
	}
	
	private EEffect effect; // The effect type
	private int duration; // The duration of the effect
	private EInputPosition inputPosition; // The current status
	
	// Applies an effect for the snake
	// Parameters:
	//   1 (Effect). The type of the effect that will be applied
	//   2 (Number). The duration of the effect in snake movements
	public EffectCommand(Game game)
	{
		super(game);
		
		effect = null;
		duration = 0;
		inputPosition = EInputPosition.EFFECT;
	}
	
	@Override
	public EWaitingFor waitingFor()
	{
		// If the command has been applied
		if (getApplyResult() != null)
		{
			return EWaitingFor.APPLIED;
		}
		
		return switch (inputPosition)
		{
			case EFFECT -> EWaitingFor.EFFECT;
			case DURATION -> EWaitingFor.NUMBER;
			case COMPLETE -> EWaitingFor.COMPLETE;
		};
	}
	
	@Override
	public void draw(JLabel[] labels)
	{
		drawEmpty(labels);
		
		// If the command has been applied, draw the result
		if (getApplyResult() != null)
		{
			labels[1].setIcon(ResourceManager.getSprite(getApplyResult() ? ESprite.SUCCESS : ESprite.FAILURE));
			return;
		}
		
		// The command icon
		labels[1].setIcon(ResourceManager.getSprite(ESprite.SHIELD));
		
		switch (inputPosition)
		{
			case COMPLETE:
			case DURATION:
			{
				drawNumber(labels, 3, 5, duration);
				// Fallthrough
			}
			case EFFECT:
			{
				labels[2].setIcon(ResourceManager.getSprite(
						switch (effect)
						{
							case null -> ESprite.QUESTION;
							case SHIELD -> ESprite.SHIELD;
						}
				));
			}
		}
	}
	
	@Override
	public void setSpawnable(ESpawnable spawnable)
	{}
	
	@Override
	public void setEffect(EEffect effect)
	{
		this.effect = effect;
	}
	
	@Override
	public void addDigit(int digit)
	{
		// The digit cannot be added if the command doesn't expect a number
		if (inputPosition != EInputPosition.DURATION)
		{
			return;
		}
		
		if (digit < 0)
		{
			digit = -digit;
		}
		digit %= 10;
		
		duration *= 10;
		duration += digit;
		if (duration >= 1000) // The maximum duration is 1000 movements
		{
			duration = digit;
		}
	}
	
	@Override
	public void applyParameter()
	{
		// Expect the next parameter of mark the command as complete
		inputPosition = switch (inputPosition)
		{
			case EFFECT -> effect != null ? EInputPosition.DURATION : EInputPosition.EFFECT;
			case DURATION, COMPLETE -> EInputPosition.COMPLETE;
		};
	}
	
	@Override
	protected boolean apply_()
	{
		if (inputPosition == EInputPosition.COMPLETE) // Only complete commands can be applied
		{
			switch (effect)
			{
				case SHIELD -> getGame().getField().getSnake().setShield(duration);
			}
			return true;
		}
		return false;
	}
}
