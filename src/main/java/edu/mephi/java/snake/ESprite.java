package edu.mephi.java.snake;

// The enumeration of all the sprites in the texture (except those that are present in engine.ECommonSprite)
public enum ESprite
{
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
	GRASS, WALL
}
