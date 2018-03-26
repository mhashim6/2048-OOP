package mhashim6.game1024;

public class TileImpl implements Tile {

	private Position position;

	private int actualValue;
	// ============================================================

	TileImpl(Position position) {
		setPosition(position);
		actualValue = MIN_VALUE;
	}

	TileImpl(Tile p) {
		this.position = new Position(p.getPosition());
		actualValue = p.value();
	}
	// ============================================================

	public int value() {
		return actualValue;
	}

	public int x2() {
		return (actualValue *= 2);
	}
	// ============================================================

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void setPosition(Position target) {
		this.position = target;
	}
	// ============================================================

	@Override
	public boolean equals(Object another) {
		if (!(another instanceof Tile))
			return super.equals(another);

		return compareTo((Tile) another) > 0;
	}
	// ============================================================

}
