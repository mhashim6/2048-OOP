package mhashim6.game1024.wrappers;

public class PearlImplementation implements Pearl {

	private Location location;

	private int actualValue;
	// ============================================================

	public PearlImplementation(Location location) {
		this.location = location;
		actualValue = MIN_VALUE;
	}

	public PearlImplementation(Pearl p) {
		this.location = new Location(p.getLocation());
		actualValue = p.value();
	}
	// ============================================================

	public int value() {
		return actualValue;
	}
	// ============================================================

	public int doubleTheValue() {
		return (actualValue *= 2);
	}
	// ============================================================

	public Location getLocation() {
		return location;
	}
	// ============================================================

	public int getRoot() {
		return MIN_VALUE;
	}
	// ============================================================

	@Override
	public boolean equals(Object anotherPearl) {
		if (!(anotherPearl instanceof Pearl))
			return super.equals(anotherPearl);

		return compareTo((Pearl) anotherPearl) == 1;
	}
	// ============================================================

}
