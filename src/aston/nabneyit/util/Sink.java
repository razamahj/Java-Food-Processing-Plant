
package aston.nabneyit.util;

import java.util.*;

/**
 * Defines a sink for the factory.  This is a unit which food
 * is sent to when all processing on it is finished.  Therefore, the number 
 * of units processed by all such units is the total number of saleable items
 * from the factory.  Sinks only need an input queue in case they are
 * connected to more than one unit.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */
public class Sink extends Unit {

	protected Queue<Product> q;

	/**
	 * Creates a sink object
	 */
	public Sink() {
		super();
		q = new LinkedList<Product>();
	}

	/**
	 * Runs the sink for a single time step
	 */
	public void tick() {
		// Sinks are simple; just count the food in the queue and remove
		// it all
		while (!q.isEmpty()) {
			numProcessed++;
			q.remove();
		}
	}

	/**
	 * Sinks always accept inputs
	 */
	public boolean acceptsInput() {
		return true;
	}

	public int queueLength() {
		return q.size();
	}

	public void inputProduct(Product p) {
		q.add(p);
		numEntered++;
	}

	public String toString() {
		return super.toString() + "; numDeleted=" + numProcessed;
	}
}
