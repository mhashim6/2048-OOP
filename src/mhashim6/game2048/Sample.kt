package mhashim6.game2048

import mhashim6.game2048.exceptions.GameOverException

fun main(args: Array<String>) {

    val game = GameImpl(power = 11, gridSize = 4)

    val start = System.currentTimeMillis()

    printTiles(game.start())

    var i = 0
    try {
        while (true) {
            println("${i + 1})\nUP")
            printTiles(game.swipeUp())

            println("RIGHT")
            printTiles(game.swipeRight())

            println("DOWN")
            printTiles(game.swipeDown())

            println("LEFT")
            printTiles(game.swipeLeft())

            println("UNDO")
            printTiles(game.undo())
            i++
        }
    } catch (goe: GameOverException) {
        println("\n-" + goe.toString())
    } finally {
        val end = System.currentTimeMillis()
        System.out.printf("%d turns in time: %d ms", i + 1, end - start)
    }
}

private fun printTiles(tiles: Array<Array<Tile>>) {
    tiles.forEach { arrayOfTiles ->
        arrayOfTiles.forEach { tile ->
            print("\t" + tile.value())
        }
        println()
    }
    println("-------------------------------------------")
}