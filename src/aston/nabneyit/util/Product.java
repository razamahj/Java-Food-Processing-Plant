package aston.nabneyit.util;

/**
 * Defines interface that any product used in the factory must implement
 * @author IanNabney
 *
 */
public interface Product {
	/**
	 * Defines what happens to the product in a single time step
	 */
	public abstract void tick();
	/**
	 * Decides if the product is no longer fit to be operated on
	 * @return true if product should be removed from the system
	 */
	public abstract boolean isRemovable();
	/**
	 * Clones a product.  This method is used in sources.
	 * @return deep copy of the product
	 */
	public abstract Product copy();
	/**
	 * Returns string representation of product
	 */
	public abstract String toString();
}
