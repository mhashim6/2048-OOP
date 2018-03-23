package mhashim6.game1024.wrappers;

import java.util.ArrayList;
import java.util.Random;

import mhashim6.game1024.Exceptions.GameOverExeption;
import mhashim6.game1024.wrappers.Location.PlaceHolder;

public class BasicGameModel {

	protected enum Navigation {
		UP, DOWN, RIGHT, LEFT;
	}

	//	private int	 root;
	private int	 maxValue;
	public final int gridSize = Location.MAX_ROWS;

	private GridOfPearls	    gridOfPearls;
	private Random		    rnd;
	private ArrayList<Location> locations;
	private ArrayList<Pearl>    oldPearls;

	private boolean isVictory;
	// ============================================================

	protected BasicGameModel(int root) {
		//		this.root = root;
		maxValue = (int) Math.pow(root, 11); //2 -> 2048

		gridOfPearls = new GridOfPearls(gridSize);
		rnd = new Random();
		locations = new ArrayList<>();
		oldPearls = new ArrayList<>();

	}
	// ============================================================

	protected final void start() {
		reset();
		addPearl(createPearl());
		addPearl(createPearl());
	}
	// ============================================================

	private final PearlImplementation createPearl() {
		Location[] emptySpaces = gridOfPearls.getEmptySpaces();
		int index = rnd.nextInt(emptySpaces.length);
		return new PearlImplementation(emptySpaces[index]);
	}

	// ============================================================

	private final void addPearl(Pearl pearl) {
		gridOfPearls.addPearl(pearl);
		addLocationtoTheList(pearl.getLocation());
	}

	private final void addLocationtoTheList(Location loc) {
		locations.add(loc);
	}
	// ============================================================

	private final void removePearlByLocation(Location loc) {
		replaceLocationWithPlaceHolder(loc);
	}

	private final void replaceLocationWithPlaceHolder(Location loc) {
		locations.set(locations.indexOf(loc), new PlaceHolder());
	}

	private final void cleanUpPlaceHolders() {
		locations.removeIf(loc -> loc instanceof PlaceHolder);

	}
	// ============================================================

	protected void swipe(Navigation navigation) {
		updateUndo();

		sortLocationsByNavigation(navigation);
		
		for (Location location : locations)
			swipeLocation(location, navigation);

		cleanUpPlaceHolders();

		try {
			addPearl(createPearl());
		}
		catch (IllegalArgumentException e) { //no empty spaces.
			checkGameOver();
		}
	}

	private final void swipeLocation(Location location, Navigation navigation) {
		Location newLocation = getFurtherestLocationFor(location, navigation);
		tryRepositionPearl(location, newLocation);
	}
	// ============================================================

	private final Location getFurtherestLocationFor(Location originalLocation, Navigation navigation) {
		return getFurtherestLocationFor(originalLocation, originalLocation, navigation);
	}

	private final Location getFurtherestLocationFor(Location originalLocation, Location currentValidLocation,
			Navigation navigation) {
		int row = currentValidLocation.getRow();
		int col = currentValidLocation.getColumn();

		switch (navigation) {
		case UP:
			if (currentValidLocation.isRowAtMin())
				return currentValidLocation; //you can't move further.
			else
				row -= 1;
			break;

		case DOWN:
			if (currentValidLocation.isRowAtMax())
				return currentValidLocation; //you can't move further.
			else
				row += 1;
			break;

		case RIGHT:
			if (currentValidLocation.isColAtMax())
				return currentValidLocation; //you can't move further.
			else
				col += 1;
			break;

		case LEFT:
			if (currentValidLocation.isColAtMin())
				return currentValidLocation; //you can't move further.
			else
				col -= 1;
			break;
		}

		Location newLocation = new Location(row, col);
		if (gridOfPearls.isThisLocationEmpty(newLocation)) {
			// test for the next location
			return getFurtherestLocationFor(originalLocation, newLocation, navigation);
		}

		else if (gridOfPearls.comparePearlsByLocation(originalLocation, newLocation) == 0)//these pearls have the same value
			return newLocation;

		return currentValidLocation; //you can't move more.
	}
	// ============================================================

	private final void tryRepositionPearl(Location location, Location targetLocation) {
		if (location.equals(targetLocation))
			return; //can't move this pearl.

		else if (gridOfPearls.isThisLocationEmpty(targetLocation)) //this is the farthest place our pearl can go to.
			gridOfPearls.repositionPearl(location, targetLocation);

		else //there's another pearl with the same value as our pearl, so, we will double it's value and remove ours
			mergePearls(location, targetLocation);
	}

	private final void mergePearls(Location location, Location targetLocation) {
		removePearlByLocation(location);
		checkForVictory(gridOfPearls.mergePearls(location, targetLocation));
	}
	// ============================================================

	private final void updateUndo() {
		oldPearls.clear();
		Pearl[][] temp = gridOfPearls.get();
		for (int i = 0; i < gridSize; i++)
			for (Pearl p : temp[i])
				if (p != null)
					oldPearls.add(new PearlImplementation(p));
		temp = null;
	}

	protected final void undo() {
		locations.clear();
		gridOfPearls.clear();

		//insert the old pearls into the grid.
		for (Pearl p : oldPearls) {
			addPearl(p);
		}
	}
	// ============================================================

	protected final void reset() {
		locations.clear();
		oldPearls.clear();
		gridOfPearls.clear();

		isVictory = false;
	}
	// ============================================================

	protected final Pearl[][] getGridOfPearls() {
		return gridOfPearls.get();
	}

	protected final boolean isVictory() {
		return isVictory;
	}
	// ============================================================

	private final void sortLocationsByNavigation(Navigation navigation) {
		switch (navigation) {

		case UP:
			locations.sort(Location.UP_ORDER);
			break;

		case DOWN:
			locations.sort(Location.DOWN_ORDER);
			break;

		case RIGHT:
			locations.sort(Location.RIGHT_ORDER);
			break;

		case LEFT:
			locations.sort(Location.LEFT_ORDER);
			break;
		}
	}
	// ============================================================

	private final void checkForVictory(int value) {
		if (!isVictory) //if we have won already before this move
			isVictory = (value >= maxValue);
	}

	private void checkGameOver() {
		if (!gridOfPearls.isMovingPossible())
			throw new GameOverExeption("You lost");
	}

	// ============================================================

}
