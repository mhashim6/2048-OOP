package mhashim6.kotlin.game2048

class Position {

    var row: Int = 0
        private set
    var col: Int = 0
        private set

    constructor(position: Position) : this(position.row, position.col)

    constructor(row: Int, col: Int) {
        testArgs(row, col)
        set(row, col)
    }

    private fun testArgs(row: Int, col: Int) {
        if (row < 0)
            throw IllegalArgumentException(String.format("negative rows are not allowed: %d", row))

        if (col < 0)
            throw IllegalArgumentException(String.format("negative columns are not allowed: %d", col))
    }

    private fun set(row: Int, col: Int) {
        this.row = row
        this.col = col
    }

    override fun equals(other: Any?): Boolean {
        return other is Position && row == other.row && col == other.col
    }

    override fun hashCode(): Int {
        return 31 * row + col
    }

    companion object {
        val empty = Position(0, 0)
    }
}
