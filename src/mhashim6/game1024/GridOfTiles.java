package mhashim6.game1024;

import java.util.*;

public class GridOfTiles {

	private Tile[][] gridOfTiles;

	private int x;
	private int y;

	private Random rnd;

	private int maxValue = 2;

	private Tile[][] snapshotTiles; // for undo

	GridOfTiles(int size) {
		this(size, size);
	}

	GridOfTiles(int rows, int cols) {
		this.x = rows;
		this.y = cols;

		gridOfTiles = new Tile[x][y];
		snapshotTiles = new Tile[x][y];

		rnd = new Random();
	}

	final Tile[][] get() {
		return Arrays.copyOf(gridOfTiles, x);
	}

	void takeSnapshot() {
		clearSnapshots();
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				Tile tile = gridOfTiles[i][j];
				if (tile != null)
					snapshotTiles[i][j] = new TileImpl(tile);
			}
	}

	void useSnapshotTiles() {
		clear();
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				Tile tile = snapshotTiles[i][j];
				if (tile != null)
					gridOfTiles[i][j] = new TileImpl(tile);
			}
	}

	void newTile() {
		List<Position> emptySpaces = getEmptySpaces();
		int index = rnd.nextInt(emptySpaces.size());
		add(new TileImpl(emptySpaces.get(index)));
	}

	void add(Tile tile) {
		Position pos = tile.getPosition();

		int row = pos.getRow();
		int col = pos.getCol();
		gridOfTiles[row][col] = tile;
	}

	private void removeByPos(Position pos) {
		gridOfTiles[pos.getRow()][pos.getCol()] = null;
	}

	private Tile getTile(Position pos) {
		return getTile(pos.getRow(), pos.getCol());
	}

	private Tile getTile(int row, int col) {
		return gridOfTiles[row][col];
	}
	// ============================================================

	private List<Position> getEmptySpaces() {
		List<Position> emptySpaces = new ArrayList<>();

		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++)
				if (gridOfTiles[i][j] == null)
					emptySpaces.add(new Position(i, j));

		return emptySpaces;
	}

	private List<Position> getFilledPositions() {
		List<Position> positions = new ArrayList<>();

		for (int i = 0; i < x; i++)
			for (Tile t : gridOfTiles[i]) {
				if (t != null)
					positions.add(t.getPosition());
			}
		return positions;
	}

	private boolean isPositionEmpty(Position position) {
		return gridOfTiles[position.getRow()][position.getCol()] == null;
	}
	// ============================================================

	void migrate(Direction direction) {
		List<Position> filledPositions = getFilledPositions();
		sortByDirection(filledPositions, direction);

		for (Position pos : filledPositions)
			migrateSingle(pos, direction);
	}

	private void sortByDirection(List<Position> positions, Direction direction) {
		switch (direction) {

			case UP:
				positions.sort(Position.UP_ORDER);
				break;

			case DOWN:
				positions.sort(Position.DOWN_ORDER);
				break;

			case RIGHT:
				positions.sort(Position.RIGHT_ORDER);
				break;

			case LEFT:
				positions.sort(Position.LEFT_ORDER);
				break;
		}
	}

	private void migrateSingle(Position current, Direction direction) {
		Position newPos = getFurthermost(current, direction);
		tryRepositionTile(current, newPos);
	}

	private Position getFurthermost(Position original, Direction direction) {
		return getFurthermost(original, original, direction);
	}

	private Position getFurthermost(Position original, Position currentValid, Direction direction) {
		int row = currentValid.getRow();
		int col = currentValid.getCol();

		switch (direction) {
			case UP:
				if (currentValid.getRow() == 0) //minimum row
					return currentValid; //we can't move further.
				else
					row -= 1;
				break;

			case DOWN:
				if (currentValid.getRow() == (x - 1)) //max row
					return currentValid; //we can't move further.
				else
					row += 1;
				break;

			case RIGHT:
				if (currentValid.getCol() == (y - 1)) //max col
					return currentValid; //we can't move further.
				else
					col += 1;
				break;

			case LEFT:
				if (currentValid.getCol() == 0) //minimum row
					return currentValid; //we can't move further.
				else
					col -= 1;
				break;
		}

		Position newPosition = new Position(row, col);
		if (isPositionEmpty(newPosition)) { // test for the next location
			return getFurthermost(original, newPosition, direction);
		}

		else if (compareTiles(original, newPosition) == 0)//these pearls have the same value
			return newPosition;

		return currentValid; //we can't move further.
	}

	private void tryRepositionTile(Position current, Position target) {
		if (current.equals(target))
			return; //can't move this pearl.

		else if (isPositionEmpty(target)) //this is the farthest place our pearl can go to.
			repositionTile(current, target);

		else //there's another tile with the same value as our pearl, so, we will merge them.
			merge(current, target);
	}

	private void repositionTile(Position current, Position target) {
		Tile tile = getTile(current);
		removeByPos(current);
		tile.setPosition(target);
		add(tile);
	}

	private void merge(Position pos, Position target) {
		removeByPos(pos);
		maxValue = getTile(target).x2();
	}

	final int getCurrentMax() {
		return maxValue;
	}

	final boolean isMovingPossible() {
		for (int i = 0; i < x; i++)
			for (int k = 0; k < y - 1; k++) {
				if (compareTiles(i, k, i, (k + 1)) == 0) //compare neighbors in the same row.
					return true;
				if (compareTiles(k, i, (k + 1), i) == 0) //compare neighbors in the same column.
					return true;
			}
		return false;
	}

	final void clear() {
		gridOfTiles = new Tile[x][y];
	}

	private void clearSnapshots() {
		snapshotTiles = new Tile[x][y];
	}
	// ============================================================

	private int compareTiles(Position pos1, Position pos2) {
		return compareTiles(pos1.getRow(), pos1.getCol(), pos2.getRow(), pos2.getCol());
	}

	private int compareTiles(int row1, int col1, int row2, int col2) {
		return compareTiles(gridOfTiles[row1][col1], gridOfTiles[row2][col2]);
	}

	private int compareTiles(Tile tile, Tile another) {
		return tile.compareTo(another);
	}

}
