package edu.mephi.java.engine.command;

import edu.mephi.java.engine.ESprite;
import edu.mephi.java.engine.Game;
import edu.mephi.java.engine.ResourceManager;

import javax.swing.*;

public class EffectCommand extends Command
{
	public enum EInputPosition
	{
		EFFECT, DURATION, COMPLETE
	}
	
	private EEffect effect;
	private int duration;
	private EInputPosition inputPosition;
	
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
		
		if (getApplyResult() != null)
		{
			labels[1].setIcon(ResourceManager.getSprite(getApplyResult() ? ESprite.SUCCESS : ESprite.FAILURE));
			return;
		}
		
		labels[1].setIcon(ResourceManager.getSprite(ESprite.SHIELD));
		
		switch (inputPosition)
		{
			case COMPLETE:
			case DURATION:
			{
				drawNumber(labels, 3, 4, duration);
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
		if (duration >= 1000)
		{
			duration = digit;
		}
	}
	
	@Override
	public void applyElement()
	{
		inputPosition = switch (inputPosition)
		{
			case EFFECT -> effect != null ? EInputPosition.DURATION : EInputPosition.EFFECT;
			case DURATION, COMPLETE -> EInputPosition.COMPLETE;
		};
	}
	
	@Override
	protected boolean apply_()
	{
		if (inputPosition == EInputPosition.COMPLETE)
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
