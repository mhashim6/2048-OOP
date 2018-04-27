package mhashim6.game2048

import mhashim6.game2048.exceptions.GameOverException
import mhashim6.game2048.Direction.*


class GameImpl(power: Int, gridSize: Int) : Game {
    private val grid: GridOfTiles = GridOfTiles(gridSize)

    private val maxValue: Int = Math.pow(2.0, power.toDouble()).toInt() //11 -> 2048

    override var movesCount: Int = 0
        private set

    private var isStarted: Boolean = false

    override fun start(): Array<Array<Tile>> {
        if (isStarted)
            throw RuntimeException("The game is already on") //TODO better
        isStarted = true

        grid.newTile()
        grid.newTile()

        return grid.copy()
    }

    override fun swipeUp(): Array<Array<Tile>> {
        swipe(UP)
        return grid.copy()
    }

    override fun swipeDown(): Array<Array<Tile>> {
        swipe(DOWN)
        return grid.copy()
    }

    override fun swipeRight(): Array<Array<Tile>> {
        swipe(RIGHT)
        return grid.copy()
    }

    override fun swipeLeft(): Array<Array<Tile>> {
        swipe(LEFT)
        return grid.copy()
    }

    private fun swipe(direction: Direction) {
        grid.takeSnapshot()
        grid.migrate(direction)

        movesCount++

        updateState()
        grid.newTile()
    }

    private fun updateState() {
        movesCount++

        if (isVictory(grid.currentMax))
            throw GameOverException("You Win!")

        if (!grid.isMovingPossible)
            throw GameOverException("You Lose!")
    }

    override fun undo(): Array<Array<Tile>> {
        grid.useSnapshot()
        return grid.copy()
    }

    override fun reset() {
        if (isStarted) {
            grid.reset()

            isStarted = false

            movesCount = 0
        }
    }

    private fun isVictory(value: Int) = value >= maxValue
}
