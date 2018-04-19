package mhashim6.kotlin.game2048

object EmptyTile : Tile {

    override var position: Position = Position.empty
    override fun value() = 0

    override fun x2() = 0
}