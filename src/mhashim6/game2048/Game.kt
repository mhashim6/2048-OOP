package mhashim6.game2048

interface Game {

    fun start(): Array<Array<Tile>>

    fun swipeUp(): Array<Array<Tile>>

    fun swipeDown(): Array<Array<Tile>>

    fun swipeRight(): Array<Array<Tile>>

    fun swipeLeft(): Array<Array<Tile>>

    fun undo(): Array<Array<Tile>>

    fun reset()

    val movesCount: Int
}
