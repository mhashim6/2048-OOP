package mhashim6.game1024

import mhashim6.game1024.exceptions.GameOverException
import mhashim6.game1024.Direction.*


class GameImpl(power: Int, gridSize: Int) : Game {
	private val gridOfTiles: GridOfTiles = GridOfTiles(gridSize)

	private val maxValue: Int = Math.pow(2.0, power.toDouble()).toInt() //11 -> 2048

	private var movesCount: Int = 0
	override fun movesCount() = movesCount

	private var isStarted: Boolean = false

	override fun start(): Array<Array<Tile?>> {
		if (isStarted)
			throw RuntimeException("The game is already on") //TODO better
		isStarted = true

		gridOfTiles.newTile()
		gridOfTiles.newTile()

		return gridOfTiles.copy()
	}

	override fun swipeUp(): Array<Array<Tile?>> {
		swipe(UP)
		return gridOfTiles.copy()
	}

	override fun swipeDown(): Array<Array<Tile?>> {
		swipe(DOWN)
		return gridOfTiles.copy()
	}

	override fun swipeRight(): Array<Array<Tile?>> {
		swipe(RIGHT)
		return gridOfTiles.copy()
	}

	override fun swipeLeft(): Array<Array<Tile?>> {
		swipe(LEFT)
		return gridOfTiles.copy()
	}

	private fun swipe(direction: Direction) {
		gridOfTiles.takeSnapshot()
		gridOfTiles.migrate(direction)

		movesCount++

		updateState()
		gridOfTiles.newTile()
	}

	private fun updateState() {
		movesCount++

		if (isVictory(gridOfTiles.currentMax))
			throw GameOverException("You Win!")

		if (!gridOfTiles.isMovingPossible)
			throw GameOverException("You Lose!")
	}

	override fun undo(): Array<Array<Tile?>> {
		gridOfTiles.useSnapshot()
		return gridOfTiles.copy()
	}

	override fun reset() {
		if (isStarted) {
			gridOfTiles.clear()

			isStarted = false

			movesCount = 0
		}
	}

	private fun isVictory(value: Int) = value >= maxValue
}
