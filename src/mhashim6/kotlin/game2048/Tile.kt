package mhashim6.kotlin.game2048

interface Tile : Comparable<Tile> {

    var position: Position

    fun value(): Int

    fun x2(): Int

    override fun compareTo(other: Tile): Int {
        return value() - other.value()
    }
}
