package mhashim6.game1024;

import mhashim6.game1024.Exceptions.GameOverException;

public class Test {

	public static void main(String[] args) {

		Game model = new GameImpl(11, 4);

		long start = System.currentTimeMillis();

		printPearls(model.start());

		int i = 0;
		try { //dude perfect
			for (; ; i++) {
				System.out.println((i + 1) + ")\nUP");
				printPearls(model.swipeUp());

				System.out.println("RIGHT");
				printPearls(model.swipeRight());

				System.out.println("DOWN");
				printPearls(model.swipeDown());

				System.out.println("LEFT");
				printPearls(model.swipeLeft());

				System.out.println("UNDO");
				printPearls(model.undo());
			}
		} catch (GameOverException goe) {
			System.out.println("\n-" + goe.toString());
		} finally {
			long end = System.currentTimeMillis();
			System.out.printf("%d turns in time: %d ms", (i + 1), (end - start));
		}
	}

	private static void printPearls(Tile[][] tiles) {
		int gridSize = tiles.length;
		for (int i = 0; i < gridSize; i++) {
			for (Tile p : tiles[i])
				if (p != null)
					System.out.print("\t" + p.value());
				else
					System.out.print("\t" + 0);

			System.out.println();
		}
		System.out.println("-------------------------------------------");

	}

}
