
package aston.nabneyit.model;

/**
 * This class defines the functionality specific to perishable food.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */
public class Perishable extends Food {

	private int spoilTime;

	/**
	 * Creates perishable food item
	 * @param spoilTime defines the number of ticks until the item spoils
	 */
	public Perishable(int spoilTime) {
		super();
		this.spoilTime = spoilTime;
	}

	/**
	 * Determines if the item has spoiled and should be removed
	 */
	public boolean isRemovable() {
		if (waitingTime >= spoilTime)
			return true;
		else
			return false;
	}

	public Food copy() {
		Food c = new Perishable(this.spoilTime);
		return c;
	}

	public String toString() {
		return super.toString() + "; st=" + spoilTime;
	}
}
