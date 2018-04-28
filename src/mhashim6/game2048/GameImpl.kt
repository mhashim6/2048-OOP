package mhashim6.game2048

import mhashim6.game2048.exceptions.GameOverException
import mhashim6.game2048.Direction.*


class GameImpl(power: Int, gridSize: Int) : Game {
    private val engine: Engine = EngineImpl(gridSize)

    private val maxValue: Int = Math.pow(2.0, power.toDouble()).toInt() //11 -> 2048

    override var movesCount: Int = 0
        private set

    private var isStarted: Boolean = false

    override fun start(): Array<Array<Tile>> {
        if (isStarted)
            throw RuntimeException("The game is already on") //TODO better
        isStarted = true

        engine.spawn()
        engine.spawn()

        return engine.copy
    }

    override fun swipeUp(): Array<Array<Tile>> {
        swipe(UP)
        return engine.copy
    }

    override fun swipeDown(): Array<Array<Tile>> {
        swipe(DOWN)
        return engine.copy
    }

    override fun swipeRight(): Array<Array<Tile>> {
        swipe(RIGHT)
        return engine.copy
    }

    override fun swipeLeft(): Array<Array<Tile>> {
        swipe(LEFT)
        return engine.copy
    }

    private fun swipe(direction: Direction) {
        engine.takeSnapshot()
        engine.migrate(direction)

        movesCount++

        updateState()
        engine.spawn()
    }

    private fun updateState() {
        movesCount++

        if (isVictory(engine.currentMax))
            throw GameOverException("You Win!")

        if (!engine.isMovingPossible)
            throw GameOverException("You Lose!")
    }

    override fun undo(): Array<Array<Tile>> {
        engine.useSnapshot()
        return engine.copy
    }

    override fun reset() {
        if (isStarted) {
            engine.reset()

            isStarted = false

            movesCount = 0
        }
    }

    private fun isVictory(value: Int) = value >= maxValue
}
