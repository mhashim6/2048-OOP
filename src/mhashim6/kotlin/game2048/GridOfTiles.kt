package mhashim6.kotlin.game2048

import mhashim6.kotlin.game2048.Position.OrderedSort.DOWN_ORDER
import mhashim6.kotlin.game2048.Position.OrderedSort.LEFT_ORDER
import mhashim6.kotlin.game2048.Position.OrderedSort.RIGHT_ORDER
import mhashim6.kotlin.game2048.Position.OrderedSort.UP_ORDER
import mhashim6.kotlin.game2048.Direction.*
import mhashim6.kotlin.arrays2d.asSequence2D
import mhashim6.kotlin.arrays2d.forEachIndexed2D
import mhashim6.kotlin.arrays2d.withIndex2D
import java.util.*

internal class GridOfTiles constructor(private val x: Int, private val y: Int = x) {

    private var gridOfTiles: Array<Array<Tile?>> = Array(x) { arrayOfNulls<Tile>(y) }
    private var snapshotTiles: Array<Array<Tile?>> = Array(x) { arrayOfNulls<Tile>(y) }

    private val rnd: Random = Random()

    var currentMax = 2
        private set

    fun copy() = gridOfTiles.copyOf()

    /** prepare undo */
    fun takeSnapshot() {
        clearSnapshots()
        gridOfTiles.forEachIndexed2D { i, j, tile: Tile? ->
            if (tile != null)
                snapshotTiles[i][j] = TileImpl(tile)
        }
    }

    /** undo */
    fun useSnapshot() {
        clear()
        snapshotTiles.forEachIndexed2D { i, j, tile: Tile? ->
            if (tile != null)
                gridOfTiles[i][j] = TileImpl(tile)
        }
    }

    fun newTile() {
        if (emptySpaces.isEmpty())
            return

        val emptySpaces = emptySpaces
        val index = rnd.nextInt(emptySpaces.size)
        add(TileImpl(emptySpaces[index]))
    }

    private fun add(tile: Tile?) {
        val pos = tile!!.position

        val row = pos.row
        val col = pos.col
        gridOfTiles[row][col] = tile
    }

    private fun remove(pos: Position) {
        gridOfTiles[pos.row][pos.col] = null
    }

    private fun getTile(pos: Position) = getTile(pos.row, pos.col)

    private fun getTile(row: Int, col: Int) = gridOfTiles[row][col]

    private fun isPositionEmpty(position: Position) = (gridOfTiles[position.row][position.col] == null)
    // ============================================================

    fun migrate(direction: Direction) {
        val positions = filledPositions
        sortByDirection(positions, direction)
        positions.forEach { migrateSingle(it, direction) }
    }

    private fun sortByDirection(positions: MutableList<Position>, direction: Direction) {
        when (direction) {
            UP -> positions.sortBy(UP_ORDER)
            DOWN -> positions.sortBy(DOWN_ORDER)
            RIGHT -> positions.sortBy(RIGHT_ORDER)
            LEFT -> positions.sortBy(LEFT_ORDER)
        }
    }

    private fun migrateSingle(current: Position, direction: Direction) {
        val newPos = getFurthermost(current, direction)
        tryReposition(current, newPos)
    }

    private fun getFurthermost(original: Position, direction: Direction): Position {
        return getFurthermost(original, original, direction)
    }

    private fun getFurthermost(original: Position, currentValid: Position, direction: Direction): Position {
        var row = currentValid.row
        var col = currentValid.col

        when (direction) {
            UP ->
                if (currentValid.row == 0) //minimum row
                    return currentValid //we can't move further.
                else
                    row -= 1

            DOWN ->
                if (currentValid.row == x - 1) //max row
                    return currentValid //we can't move further.
                else
                    row += 1

            RIGHT ->
                if (currentValid.col == y - 1)  //max col
                    return currentValid //we can't move further.
                else
                    col += 1

            LEFT ->
                if (currentValid.col == 0)  //minimum col
                    return currentValid //we can't move further.
                else
                    col -= 1
        }
        val newPos = Position(row, col)

        if (isPositionEmpty(newPos)) { // check for the next position
            return getFurthermost(original, newPos, direction)

        } else if (compareTiles(original, newPos) == 0) //these tiles have the same value
            return newPos

        return currentValid //we can't move further.
    }

    private fun tryReposition(current: Position, target: Position) {
        when {
            current == target -> return
            isPositionEmpty(target) -> reposition(current, target)
            else -> merge(current, target)
        }
    }

    private fun reposition(current: Position, target: Position) {
        val tile = getTile(current)
        remove(current)
        tile!!.position = target
        add(tile)
    }

    private fun merge(pos: Position, target: Position) {
        remove(pos)
        currentMax = getTile(target)!!.x2()
    }

    internal fun clear() {
        gridOfTiles = Array(x) { arrayOfNulls<Tile>(y) }
    }

    private fun clearSnapshots() {
        snapshotTiles = Array(x) { arrayOfNulls<Tile>(y) }
    }
    // ============================================================

    private val emptySpaces: List<Position>
        get() = gridOfTiles.withIndex2D()
                .asSequence()
                .filter { it.value == null }
                .map { Position(it.x, it.y) }
                .toList()

    private val filledPositions: MutableList<Position>
        get() = gridOfTiles.asSequence2D()
                .filter { it != null }
                .map { it!!.position }
                .toMutableList()

    val isMovingPossible: Boolean
        get() {
            if (emptySpaces.isNotEmpty())
                return true

            gridOfTiles.forEachIndexed { i, _ ->
                for (j in 0 until y - 1) {
                    if (compareTiles(i, j, i, j + 1) == 0) //compare neighbors in the same row.
                        return true
                    if (compareTiles(j, i, j + 1, i) == 0) //compare neighbors in the same column.
                        return true
                }
            }
            return false
        }
    // ============================================================

    private fun compareTiles(pos1: Position, pos2: Position): Int? {
        return compareTiles(pos1.row, pos1.col, pos2.row, pos2.col)
    }

    private fun compareTiles(row1: Int, col1: Int, row2: Int, col2: Int): Int? {
        return compareTiles(gridOfTiles[row1][col1], gridOfTiles[row2][col2])
    }

    private fun compareTiles(tile: Tile?, another: Tile?): Int? {
        return tile!!.compareTo(another!!)
    }

}
