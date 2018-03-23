package mhashim6.game1024.UI;

import javafx.scene.control.Label;
import mhashim6.game1024.wrappers.Location;
import mhashim6.game1024.wrappers.Pearl;

public class VPearl extends Label implements Pearl { //TODO

	private Location location;

	private int actualValue;

	public VPearl(Location location) {
		super();

		this.location = new Location(location);
		actualValue = MIN_VALUE;
		setText(String.valueOf(actualValue));
	}

	public VPearl(Pearl p) {
		this(p.getLocation());
		actualValue = p.value();
	}

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
