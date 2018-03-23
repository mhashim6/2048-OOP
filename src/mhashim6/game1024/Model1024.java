package mhashim6.game1024;

import mhashim6.game1024.wrappers.Pearl;

public interface Model1024 {

	Pearl[][] startGame();

	Pearl[][] swipeUp();

	Pearl[][] swipeDown();

	Pearl[][] swipeRight();

	Pearl[][] swipeLeft();

	Pearl[][] undoSwipe();

	void resetGame();

	boolean isGameOver();

	boolean isItVictory();

}
