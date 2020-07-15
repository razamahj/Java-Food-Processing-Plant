
package aston.nabneyit.model;

/**
 * Defines the functionality specific to non-perishable food.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */
public class NonPerishable extends Food {

	/**
	 * Creates non-perishable food object
	 */
	public NonPerishable() {
		super();
	}

	/**
	 * Non-perishable food is never removable since it doesn't spoil
	 */
	public boolean isRemovable() {
		return false;
	}

	public Food copy() {
		Food c = new NonPerishable();
		return c;
	}

	public String toString() {
		return super.toString() + "; n-p";
	}
}
