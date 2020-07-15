
package aston.nabneyit.model;

import aston.nabneyit.util.*;
/**
 * Defines the common functionality of food items.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */

public abstract class Food implements Product {

	protected int waitingTime = 0;
	protected int ID;
	protected static int foodID = 0;

	/**
	 * Creates food item with unique ID
	 */
	public Food() {
		ID = foodID++;
	}

	/**
	 * Increments waiting time
	 */
	public void tick() {
		waitingTime++;
	}

	/**
	 * Defines whether food item should be removed from system
	 * due to spoilage
	 */
	public abstract boolean isRemovable();

	/**
	 * Clones food item
	 */
	public abstract Food copy();

	/**
	 * Provides string representation
	 */
	public String toString() {
		return "(" + ID + "); wt=" + waitingTime;
	}
}
