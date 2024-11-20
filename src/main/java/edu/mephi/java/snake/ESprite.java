package edu.mephi.java.snake;

// The enumberation of all the sprites in the texture
public enum ESprite
{
	GRASS,
	
	// Snake
	SNAKE_HEAD_UP,				SNAKE_HEAD_DOWN,				SNAKE_HEAD_LEFT,				SNAKE_HEAD_RIGHT,
	SNAKE_HEAD_BLINK_UP,		SNAKE_HEAD_BLINK_DOWN,			SNAKE_HEAD_BLINK_LEFT,			SNAKE_HEAD_BLINK_RIGHT,
	SNAKE_HEAD_READY_UP,		SNAKE_HEAD_READY_DOWN,			SNAKE_HEAD_READY_LEFT,			SNAKE_HEAD_READY_RIGHT,
	SNAKE_HEAD_READY_BLINK_UP,	SNAKE_HEAD_READY_BLINK_DOWN,	SNAKE_HEAD_READY_BLINK_LEFT,	SNAKE_HEAD_READY_BLINK_RIGHT,
	SNAKE_BODY_UP_DOWN,											SNAKE_BODY_LEFT_RIGHT,
	SNAKE_BODY_TURN_UP_RIGHT,	SNAKE_BODY_TURN_DOWN_RIGHT,		SNAKE_BODY_TURN_UP_LEFT,		SNAKE_BODY_TURN_DOWN_LEFT,
	SNAKE_TAIL_UP,				SNAKE_TAIL_DOWN,				SNAKE_TAIL_LEFT,				SNAKE_TAIL_RIGHT,
	
	// Eatables
	APPLE,
	ROTTEN_APPLE,
	REVERSE_PILL,
	SHIELD,
	HAMMER,
	
	// Obstacles
	WALL,
	
	// Numbers
	NUM_ZERO, NUM_ONE, NUM_TWO, NUM_THREE, NUM_FOUR, NUM_FIVE, NUM_SIX, NUM_SEVEN, NUM_EIGHT, NUM_NINE,
	
	// Command characters
	SUCCESS, FAILURE, QUESTION, SLASH;
	
	// Convert an integer to the corresponding sprite with the number
	public static ESprite getNum(int num)
	{
		return switch (num)
		{
			case 0 -> NUM_ZERO;
			case 1 -> NUM_ONE;
			case 2 -> NUM_TWO;
			case 3 -> NUM_THREE;
			case 4 -> NUM_FOUR;
			case 5 -> NUM_FIVE;
			case 6 -> NUM_SIX;
			case 7 -> NUM_SEVEN;
			case 8 -> NUM_EIGHT;
			case 9 -> NUM_NINE;
			default -> QUESTION;
		};
	}
}
