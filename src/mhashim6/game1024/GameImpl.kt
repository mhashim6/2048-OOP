package mhashim6.game1024

import mhashim6.game1024.Exceptions.GameOverException

class GameImpl(power: Int, gridSize: Int) : Game {

	val maxValue: Int = Math.pow(2.0, power.toDouble()).toInt() //11 -> 2048

	private val gridOfTiles: GridOfTiles = GridOfTiles(gridSize)

	var movesCount: Int = 0
		private set

	private var isStarted: Boolean = false
	override var isGameOver: Boolean = false
	override var isVictory: Boolean = false

	override fun start(): Array<Array<Tile?>> {
		if (isStarted)
			throw RuntimeException("The game is already on") //TODO better
		isStarted = true

		gridOfTiles.newTile()
		gridOfTiles.newTile()

		return gridOfTiles.get()
	}

	override fun swipeUp(): Array<Array<Tile?>> {
		swipe(Direction.UP)
		return gridOfTiles.get()
	}

	override fun swipeDown(): Array<Array<Tile?>> {
		swipe(Direction.DOWN)
		return gridOfTiles.get()
	}

	override fun swipeRight(): Array<Array<Tile?>> {
		swipe(Direction.RIGHT)
		return gridOfTiles.get()
	}

	override fun swipeLeft(): Array<Array<Tile?>> {
		swipe(Direction.LEFT)
		return gridOfTiles.get()
	}

	private fun swipe(direction: Direction) {
		gridOfTiles.takeSnapshot()

		gridOfTiles.migrate(direction)

		movesCount++

		checkForVictory(gridOfTiles.currentMax)

		try { //TODO better
			gridOfTiles.newTile()
		} catch (e: IllegalArgumentException) { //no empty spaces.
			checkGameOver()
		}
	}

	override fun undo(): Array<Array<Tile?>> {
		gridOfTiles.useSnapshotTiles()
		return gridOfTiles.get()
	}

	override fun reset() {
		if (isStarted) {
			gridOfTiles.clear()

			isVictory = false
			isGameOver = false
			isStarted = false

			movesCount = 0
		}
	}

	private fun checkGameOver() {
		if (!gridOfTiles.isMovingPossible)
			throw GameOverException("You lost")
	}

	private fun checkForVictory(value: Int) { //TODO
		isVictory = value >= maxValue
	}
}
