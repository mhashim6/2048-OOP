package mhashim6.game1024;

public interface Tile extends Comparable<Tile> {

	int MIN_VALUE = 2;

	int value();

	int x2();

	Position getPosition();

	void setPosition(Position targetPosition);

	@Override
	default int compareTo(Tile anotherTile) {
		return value() - anotherTile.value();
	}
}
