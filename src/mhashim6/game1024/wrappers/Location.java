package mhashim6.game1024.wrappers;

import java.util.Comparator;

public class Location {

	public static final Comparator<Location> UP_ORDER    = new Comparator<Location>() {
								     //the smallest row first

								     @Override
								     public int compare(Location loc1, Location loc2) {
									     int row1 = loc1.getRow();
									     int row2 = loc2.getRow();

									     return (row1 - row2);
								     }

							     };
	public static final Comparator<Location> DOWN_ORDER  = new Comparator<Location>() {
								     //the biggest row first

								     @Override
								     public int compare(Location loc1, Location loc2) {
									     int row1 = loc1.getRow();
									     int row2 = loc2.getRow();

									     return -(row1 - row2);
								     }

							     };
	public static final Comparator<Location> RIGHT_ORDER = new Comparator<Location>() {
								     //the biggest column first

								     @Override
								     public int compare(Location loc1, Location loc2) {
									     int col1 = loc1.getColumn();
									     int col2 = loc2.getColumn();

									     return -(col1 - col2);
								     }
							     };
	public static final Comparator<Location> LEFT_ORDER  = new Comparator<Location>() {
								     //the smallest column first

								     @Override
								     public int compare(Location loc1, Location loc2) {
									     int col1 = loc1.getColumn();
									     int col2 = loc2.getColumn();

									     return (col1 - col2);
								     }

							     };

	public static final int	MAX_ROWS = 4;
	public static final int	MAX_COLS = 4;

	private int row;
	private int col;

	// ============================================================

	private Location() { //place holder.

	}

	public Location(int row, int col) {
		testArgs(row, col);
		set(row, col);
	}

	public Location(Location location) {
		set(location.row, location.col);
	}
	// ============================================================

	private final void testArgs(int row, int col) {
		if (row < 0 || row > (MAX_ROWS - 1))
			throw new IllegalArgumentException(String.format("entered row: %d\nallowed range : %d : %d",
					row, 0, (MAX_ROWS - 1)));

		if (col < 0 || col > (MAX_COLS - 1))
			throw new IllegalArgumentException(String.format("entered column: %dallowed range : %d : %d",
					col, 0, (MAX_COLS - 1)));
	}
	// ============================================================

	public int getRow() {
		return row;
	}
	// ============================================================

	public boolean isRowAtMax() { //the bottom
		return row == (MAX_ROWS - 1);
	}
	// ============================================================

	public boolean isRowAtMin() { //the surface
		return row == 0;
	}
	// ============================================================

	public void setRow(int row) {
		this.row = row;
	}
	// ============================================================

	public int getColumn() {
		return col;
	}
	// ============================================================

	public boolean isColAtMax() { //the right
		return col == (MAX_COLS - 1);
	}
	// ============================================================

	public boolean isColAtMin() { //the left
		return col == 0;
	}
	// ============================================================

	public void setCol(int col) {
		this.col = col;
	}
	// ============================================================

	public void set(int row, int col) {
		this.row = row;
		this.col = col;
	}
	// ============================================================

	@Override
	public boolean equals(Object anotherLocation) {
		if (row != ((Location) anotherLocation).getRow() || col != ((Location) anotherLocation).getColumn())
			return false;

		return true;
	}
	// ============================================================

	public static class PlaceHolder extends Location {

		@Override
		public final int getRow() {
			return -11; //random
		}

		@Override
		public final int getColumn() {
			return -11; //random
		}
	}

}
