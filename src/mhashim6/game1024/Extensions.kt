package mhashim6.game1024

fun <T> Array<Array<T>>.forEach2d(action: (T) -> Unit) {
	for (array in this)
		for (item in array)
			action(item)
}

fun <T> Array<Array<T>>.forEachIndexed2d(action: (i: Int, j: Int, T) -> Unit) {
	for (i in 0 until this.size)
		for (j in 0 until this[i].size)
			action(i, j, this[i][j])
}

