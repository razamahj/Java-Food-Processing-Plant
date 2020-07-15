package aston.nabneyit.util;

import java.util.*;

/**
 * Groups some units into a layer; this is a convenient way
 * of structuring the factory needed for the coursework.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */

public class Layer {

	private List<Unit> units;

	/**
	 * Creates layer object and initialises list of units that it contains
	 */
	public Layer() {
		units = new ArrayList<Unit>();
	}

	/**
	 * Add a unit to the layer
	 * @param u
	 */
	public void addUnit(Unit u) {
		units.add(u);
	}

	/**
	 * Number of units in layer
	 * @return number of units
	 */
	public int numUnits() {
		return units.size();
	}

	/**
	 * Run the layer for a single time step
	 */
	public void tick() {
		for (Unit u : units) {
			u.tick();
		}
	}

	/**
	 * Returns an iterator for the units stored in this layer.
	 *
	 * @see Iterator
	 */
	public Iterator<Unit> iterator() {
		return units.iterator();
	}

	public String toString() {
		String newLine = System.getProperty("line.separator");  
		StringBuffer buf = new StringBuffer("Layer" + newLine);

		buf.append(units.toString());
		return buf.toString();
	}
}
