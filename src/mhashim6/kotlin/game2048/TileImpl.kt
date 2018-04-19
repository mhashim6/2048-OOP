package mhashim6.kotlin.game2048

class TileImpl : Tile {

    override var position: Position

    private var value: Int = 0

    internal constructor(position: Position) {
        this.position = position
        value = MIN_VALUE
    }

    internal constructor(tile: Tile) {
        this.position = Position(tile.position)
        value = tile.value()
    }

    override fun value(): Int {
        return value
    }

    override fun x2(): Int {
        value *= 2
        return value
    }

    companion object {
        const val MIN_VALUE = 2
    }
}
