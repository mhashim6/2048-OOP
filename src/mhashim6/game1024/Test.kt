package mhashim6.game1024

import mhashim6.game1024.exceptions.GameOverException

fun main(args: Array<String>) {

	val model = GameImpl(11, 4)

	val start = System.currentTimeMillis()

	printPearls(model.start())

	var i = 0
	try {
		while (true) {
			println("${i + 1})\nUP")
			printPearls(model.swipeUp())

			println("RIGHT")
			printPearls(model.swipeRight())

			println("DOWN")
			printPearls(model.swipeDown())

			println("LEFT")
			printPearls(model.swipeLeft())

			println("UNDO")
			printPearls(model.undo())
			i++
		}
	} catch (goe: GameOverException) {
		println("\n-" + goe.toString())
	} finally {
		val end = System.currentTimeMillis()
		System.out.printf("%d turns in time: %d ms", i + 1, end - start)
	}
}

private fun printPearls(tiles: Array<Array<Tile?>>) {

	tiles.forEach { arrayOfTiles ->
		arrayOfTiles.forEach { tile ->
			if (tile != null)
				print("\t" + tile.value())
			else
				print("\t" + 0)
		}
		println()
	}
	println("-------------------------------------------")
}