package mhashim6.game2048

import mhashim6.game2048.Direction.*
import mhashim6.kotlin.arrays2d.asSequence2D
import mhashim6.kotlin.arrays2d.forEachIndexed2D
import mhashim6.kotlin.arrays2d.matrix
import mhashim6.kotlin.arrays2d.withIndex2D
import java.util.*

internal class EngineImpl constructor(private val x: Int, private val y: Int = x) : Engine {

    private var matrix = emptyMatrix.copyOf()
    private var snapshot = emptyMatrix.copyOf()

    private val rnd: Random = Random()

    override var currentMax = 2
        private set

    override val copy
        get() = matrix.copyOf()

    override fun takeSnapshot() {
        resetSnapshots()
        snapshot = matrix.copyOf()
    }

    override fun useSnapshot() {
        resetGrid()
        matrix = snapshot.copyOf()
    }

    override fun spawn() {
        val positions = vacancies
        if (positions.isEmpty())
            return

        val index = rnd.nextInt(positions.size)
        add(TileImpl(positions[index]))
    }

    private fun add(tile: Tile) {
        val pos = tile.position
        matrix[pos.row][pos.col] = tile
    }

    private fun remove(pos: Position) {
        matrix[pos.row][pos.col] = EmptyTile
    }

    private fun getTile(pos: Position) = getTile(pos.row, pos.col)

    private fun getTile(row: Int, col: Int) = matrix[row][col]

    private fun isPositionEmpty(pos: Position) = (matrix[pos.row][pos.col] === EmptyTile)
    // ============================================================

    override fun migrate(direction: Direction) {
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
        tile.position = target
        add(tile)
    }

    private fun merge(pos: Position, target: Position) {
        remove(pos)
        val mergedValue = getTile(target).x2()
        if (mergedValue > currentMax)
            currentMax = mergedValue
    }

    override fun reset() {
        resetGrid()
        resetSnapshots()
    }

    private fun resetGrid() {
        matrix = emptyMatrix.copyOf()
    }

    private fun resetSnapshots() {
        snapshot = emptyMatrix.copyOf()
    }
    // ============================================================

    private val vacancies: List<Position>
        get() = matrix.withIndex2D()
                .asSequence()
                .filter { it.value === EmptyTile }
                .map { Position(it.x, it.y) }
                .toList()

    private val filledPositions: MutableList<Position>
        get() = matrix.asSequence2D()
                .filter { it !== EmptyTile }
                .map { it.position }
                .toMutableList()

    override val isMovingPossible: Boolean
        get() {
            if (vacancies.isNotEmpty())
                return true

            matrix.forEachIndexed { i, _ ->
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

    private fun compareTiles(pos1: Position, pos2: Position): Int {
        return compareTiles(pos1.row, pos1.col, pos2.row, pos2.col)
    }

    private fun compareTiles(row1: Int, col1: Int, row2: Int, col2: Int): Int {
        return compareTiles(matrix[row1][col1], matrix[row2][col2])
    }

    private fun compareTiles(tile: Tile, another: Tile): Int {
        return tile.compareTo(another)
    }

    companion object {

        val emptyMatrix = matrix<Tile>(4, 4) { i, j ->
            EmptyTile.apply { position = Position(i, j) }
        }

        /**the smallest row first*/
        val UP_ORDER = { pos: Position -> pos.row }

        /**the biggest row first*/
        val DOWN_ORDER = { pos: Position -> -pos.row }

        /**the biggest column first*/
        val RIGHT_ORDER = { pos: Position -> -pos.col }

        /**the smallest column first*/
        val LEFT_ORDER = { pos: Position -> pos.col }
    }

}
