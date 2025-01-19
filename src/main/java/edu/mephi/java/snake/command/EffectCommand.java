package edu.mephi.java.snake.command;

import edu.mephi.java.engine.command.AbstractParameter;
import edu.mephi.java.engine.command.IntegerParameter;
import edu.mephi.java.snake.ESprite;
import edu.mephi.java.snake.SnakeGame;

// Apply an effect for the snake
public class EffectCommand extends Command
{
	public static final int EFFECT_PARAMETER_INDEX = 0;
	public static final int DURATION_PARAMETER_INDEX = 1;
	
	// Applies an effect for the snake
	// Parameters:
	//   1 (Effect). The type of the effect that will be applied
	//   2 (Number). The duration of the effect in snake movements
	public EffectCommand(SnakeGame game)
	{
		super(ESprite.SHIELD.toString(), new AbstractParameter[] {
				new EffectParameter(),
				new IntegerParameter(3)
		}, game);
	}
	
	@Override
	protected boolean apply_()
	{
		if (isComplete())
		{
			String effect = ((EffectParameter)getParameter(EFFECT_PARAMETER_INDEX)).getValue();
			int duration = ((IntegerParameter)getParameter(DURATION_PARAMETER_INDEX)).getValue();
			
			if (effect.equals(ESprite.SHIELD.toString()))
			{
				getGame().getField().getSnake().setShield(duration);
			}
			
			return true;
		}
		return false;
	}
}
