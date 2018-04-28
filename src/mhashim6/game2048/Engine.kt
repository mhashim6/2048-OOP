package mhashim6.game2048

interface Engine {

    /**spawns a new tile in a random vacant position.*/
    fun spawn()

    /** prepares undo.*/
    fun takeSnapshot()

    /**migrates all tile in the provided direction.*/
    fun migrate(direction: Direction)

    /** undo to last move.*/
    fun useSnapshot()

    /**resets the grid to the initial state.*/
    fun reset()

    /**a copy of the internal matrix.*/
    val copy: Array<Array<Tile>>

    /**highest value of tile in the grid.*/
    val currentMax: Int

    /**true if there's any vacant spaces, or merge-able tiles.*/
    val isMovingPossible: Boolean
}