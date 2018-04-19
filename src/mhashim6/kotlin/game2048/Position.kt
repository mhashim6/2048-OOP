package mhashim6.kotlin.game2048

data class Position(val row: Int = 0, val col: Int = 0) {

    constructor(position: Position) : this(position.row, position.col)

    companion object {
        val empty = Position(0, 0)
    }
}
