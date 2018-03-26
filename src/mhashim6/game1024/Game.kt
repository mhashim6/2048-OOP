package mhashim6.game1024

interface Game {

	var isGameOver: Boolean

	var isVictory: Boolean

	fun start(): Array<Array<Tile?>>

	fun swipeUp(): Array<Array<Tile?>>

	fun swipeDown(): Array<Array<Tile?>>

	fun swipeRight(): Array<Array<Tile?>>

	fun swipeLeft(): Array<Array<Tile?>>

	fun undo(): Array<Array<Tile?>>

	fun reset()

}
