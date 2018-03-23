package mhashim6.game1024.wrappers;

import java.util.Arrays;

final class GridOfPearls {

	private Pearl[][] gridOfPearls;
	private int	  gridSize;

	protected GridOfPearls(int gridSize) {
		this.gridSize = gridSize;
		gridOfPearls = new Pearl[gridSize][gridSize];
	}
	// ============================================================

	protected final Pearl[][] get() {
		return Arrays.copyOf(gridOfPearls, gridSize);
	}
	// ============================================================

	protected final Location[] getEmptySpaces() {
		Location[] emptySpaces = new Location[getEmptySpacesSize()];

		int index = 0;
		for (int i = 0; i < gridSize; i++)
			for (int j = 0; j < gridSize; j++)
				if (gridOfPearls[i][j] == null) {
					emptySpaces[index] = new Location(i, j);
					index++;
				}
		return emptySpaces;
	}

	private int getEmptySpacesSize() {
		int size = 0;
		for (int i = 0; i < gridSize; i++)
			for (Pearl p : gridOfPearls[i]) {
				if (p == null)
					size++;
			}
		return size;
	}
	// ============================================================

	protected final void addPearl(Pearl p) {
		int row = p.getLocation().getRow();
		int col = p.getLocation().getColumn();
		gridOfPearls[row][col] = p;
	}
	// ============================================================

	protected final void removePearl(Location loc) {
		gridOfPearls[loc.getRow()][loc.getColumn()] = null;
	}
	// ============================================================

	protected final boolean isThisLocationEmpty(Location location) {
		return gridOfPearls[location.getRow()][location.getColumn()] == null;
	}
	// ============================================================

	protected void repositionPearl(Location locationOfThePearl, Location targetLocation) {
		Pearl p = getPearlByLocation(locationOfThePearl);
		removePearl(locationOfThePearl);

		locationOfThePearl.set(targetLocation.getRow(), targetLocation.getColumn());
		addPearl(p);
	}

	protected int mergePearls(Location location1, Location targetLocation) {
		removePearl(location1);
		return getPearlByLocation(targetLocation).doubleTheValue();
	}
	// ============================================================

	protected final Pearl getPearlByLocation(Location location) {
		int row = location.getRow();
		int col = location.getColumn();

		return gridOfPearls[row][col];
	}
	// ============================================================

	protected final boolean isMovingPossible() {
		for (int i = 0; i < gridSize; i++)
			for (int k = 0; k < gridSize - 1; k++) {
				if (comparePearlsByLocation(i, k, i, (k + 1)) == 0) //compare neighbors in the same row.
					return true;
				if (comparePearlsByLocation(k, i, (k + 1), i) == 0) //compare neighbors in the same column.
					return true;
			}

		return false;
	}
	// ============================================================

	/**
	 * to tell if the owners of these locations have the same value or not.
	 */
	protected final int comparePearlsByLocation(Location location1, Location location2) {
		return comparePearlsByLocation(location1.getRow(), location1.getColumn(), location2.getRow(),
				location2.getColumn());
	}

	protected final int comparePearlsByLocation(int row1, int col1, int row2, int col2) {
		return gridOfPearls[row1][col1].compareTo(gridOfPearls[row2][col2]);
	}
	// ============================================================

	protected final void clear() {
		for (int i = 0; i < gridSize; i++)
			for (int j = 0; j < gridSize; j++)
				gridOfPearls[i][j] = null;
	}
	// ============================================================

}