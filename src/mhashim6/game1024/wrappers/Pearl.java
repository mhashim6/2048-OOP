package mhashim6.game1024.wrappers;

public interface Pearl extends Comparable<Pearl> {

	public final static int MIN_VALUE = 2;

	public int value();
	// ============================================================

	public int doubleTheValue();
	// ============================================================

	public Location getLocation();
	// ============================================================

	public int getRoot();
	// ============================================================

	@Override
	default int compareTo(Pearl anotherPearl) {
		return value() - anotherPearl.value();
	}

}
