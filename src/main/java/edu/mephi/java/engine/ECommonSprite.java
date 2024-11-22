package edu.mephi.java.engine;

// The sprites listed in this enumeration must present in all games
public enum ECommonSprite
{
	NOTHING,
	
	// Characters
	SLASH, QUESTION, SUCCESS, FAILURE, MINUS, PAUSE,
	
	// Numbers
	NUM_ZERO, NUM_ONE, NUM_TWO, NUM_THREE, NUM_FOUR, NUM_FIVE, NUM_SIX, NUM_SEVEN, NUM_EIGHT, NUM_NINE;
	
	// Convert an integer to the corresponding sprite
	public static ECommonSprite getNum(int num)
	{
		// Wow, I didn't even know you can use switch like that in Java; it's so convenient!
		// I wish there was something like that in C++
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
