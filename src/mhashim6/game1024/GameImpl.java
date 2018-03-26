package mhashim6.game1024;

import mhashim6.game1024.Exceptions.GameOverException;

public class GameImpl implements Game {

	private int maxValue;
	private GridOfTiles gridOfTiles;

	private int movesCount;

	private boolean isVictory;

	private boolean isStarted;
	private boolean isGameOver;

	public GameImpl(int power, int gridSize) {
		maxValue = (int) Math.pow(2, power); //11 -> 2048

		gridOfTiles = new GridOfTiles(gridSize);
	}

	@Override
	public Tile[][] start() {
		if (isStarted)
			throw new RuntimeException("The game is already on");
		isStarted = true;

		gridOfTiles.newTile();
		gridOfTiles.newTile();

		return gridOfTiles.get();
	}

	@Override
	public Tile[][] swipeUp() {
		swipe(Direction.UP);
		return gridOfTiles.get();
	}

	@Override
	public Tile[][] swipeDown() {
		swipe(Direction.DOWN);
		return gridOfTiles.get();
	}

	@Override
	public Tile[][] swipeRight() {
		swipe(Direction.RIGHT);
		return gridOfTiles.get();
	}

	@Override
	public Tile[][] swipeLeft() {
		swipe(Direction.LEFT);
		return gridOfTiles.get();
	}

	private void swipe(Direction direction) {
		gridOfTiles.takeSnapshot();

		gridOfTiles.migrate(direction);

		movesCount++;

		checkForVictory(gridOfTiles.getCurrentMax());

		try {
			gridOfTiles.newTile();
		} catch (IllegalArgumentException e) { //no empty spaces.
			checkGameOver();
		}
	}

	@Override
	public Tile[][] undo() {
		gridOfTiles.useSnapshotTiles();
		return gridOfTiles.get();
	}

	@Override
	public void reset() {
		if (isStarted) {
			gridOfTiles.clear();

			isVictory = false;
			isGameOver = false;
			isStarted = false;

			movesCount = 0;
		}
	}

	@Override
	public boolean isGameOver() {
		return isGameOver;
	}

	@Override
	public boolean isItVictory() {
		return isVictory;
	}


	private void checkGameOver() {
		if (!gridOfTiles.isMovingPossible())
			throw new GameOverException("You lost");
	}

	private void checkForVictory(int value) { //TODO
		isVictory = (value >= maxValue);
	}

	public int getMovesCount() {
		return movesCount;
	}
}
