package mhashim6.game1024;

public interface Game {

	Tile[][] start();

	Tile[][] swipeUp();

	Tile[][] swipeDown();

	Tile[][] swipeRight();

	Tile[][] swipeLeft();

	Tile[][] undo();

	void reset();

	boolean isGameOver();

	boolean isItVictory();

}
