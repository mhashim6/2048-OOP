package mhashim6.game1024;

import java.util.Comparator;

public class Position {

	//the smallest row first
	public static final Comparator<Position> UP_ORDER = (pos1, pos2) -> {
		int row1 = pos1.getRow();
		int row2 = pos2.getRow();

		return (row1 - row2);
	};

	//the biggest row first
	public static final Comparator<Position> DOWN_ORDER = (pos1, pos2) -> {
		int row1 = pos1.getRow();
		int row2 = pos2.getRow();

		return -(row1 - row2);
	};

	//the biggest column first
	public static final Comparator<Position> RIGHT_ORDER = (pos1, pos2) -> {
		int col1 = pos1.getCol();
		int col2 = pos2.getCol();

		return -(col1 - col2);
	};

	//the smallest column first
	public static final Comparator<Position> LEFT_ORDER = (pos1, pos2) -> {
		int col1 = pos1.getCol();
		int col2 = pos2.getCol();

		return (col1 - col2);
	};

	private int row;
	private int col;
	// ============================================================

	public Position(int row, int col) {
		testArgs(row, col);
		set(row, col);
	}

	public Position(Position position) {
		set(position.row, position.col);
	}
	// ============================================================

	private void testArgs(int row, int col) {
		if (row < 0)
			throw new IllegalArgumentException(String.format("negative rows are not allowed: %d", row));

		if (col < 0)
			throw new IllegalArgumentException(String.format("negative columns are not allowed: %d", col));
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	private void set(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean equals(Object another) {
		return another instanceof Position && row == ((Position) another).getRow() && col == ((Position) another).getCol();
	}
}
