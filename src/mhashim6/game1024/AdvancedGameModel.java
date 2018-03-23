package mhashim6.game1024;

import mhashim6.game1024.Exceptions.GameOverExeption;
import mhashim6.game1024.wrappers.BasicGameModel;
import mhashim6.game1024.wrappers.Pearl;

public final class AdvancedGameModel extends BasicGameModel implements Model1024 {

    private boolean isStarted;
    private boolean isGameOver;
    // ============================================================

    public AdvancedGameModel() {
        super(2);
    }
    // ============================================================

    @Override
    public final synchronized Pearl[][] startGame() {
        if (isStarted)
            throw new RuntimeException("The game is already on");

        start();
        isStarted = true;
        return getGridOfPearls();

    }
    // ============================================================

    @Override
    public final Pearl[][] swipeUp() {
        swipe(Navigation.UP);
        return getGridOfPearls();

    }
    // ============================================================

    @Override
    public final Pearl[][] swipeDown() {
        swipe(Navigation.DOWN);
        return getGridOfPearls();

    }
    // ============================================================

    @Override
    public final Pearl[][] swipeRight() {
        swipe(Navigation.RIGHT);
        return getGridOfPearls();

    }
    // ============================================================

    @Override
    public final Pearl[][] swipeLeft() {
        swipe(Navigation.LEFT);
        return getGridOfPearls();

    }
    // ============================================================

    @Override
    protected final synchronized void swipe(Navigation navigation) {
        if (isGameOver)
            throw new GameOverExeption(null);

        try {
            super.swipe(navigation);
            if (isVictory())
                throw new GameOverExeption("You won!");
        } catch (GameOverExeption goe) {
            isGameOver = true;
            throw goe;
        }
    }
    // ============================================================

    @Override
    public final synchronized Pearl[][] undoSwipe() {
        super.undo();
        return getGridOfPearls();
    }
    // ============================================================

    @Override
    public final synchronized boolean isGameOver() {
        return isGameOver;
    }
    // ============================================================

    @Override
    public final synchronized boolean isItVictory() {
        return isVictory();
    }
    // ============================================================

    @Override
    public final synchronized void resetGame() {
        if (isStarted) {
            super.reset();
            isStarted = false;
            isGameOver = false;
        }
    }
}
