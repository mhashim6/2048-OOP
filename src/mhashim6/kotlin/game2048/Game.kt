package mhashim6.kotlin.game2048

interface Game {

    val movesCount: Int

    fun start(): Array<Array<Tile?>>

    fun swipeUp(): Array<Array<Tile?>>

    fun swipeDown(): Array<Array<Tile?>>

    fun swipeRight(): Array<Array<Tile?>>

    fun swipeLeft(): Array<Array<Tile?>>

    fun undo(): Array<Array<Tile?>>

    fun reset()

}
