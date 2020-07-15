
package aston.nabneyit.util;

import java.util.*;

/**
 * Implements a general machine class that carries out a
 * process of some timespan on the first item in the queue that represents
 * its input line.  Once a piece of food is finished, it is passed on to
 * a {@link Connector Connector} that takes it to the next unit in the factory.
 * <p>
 * A machine may jam with a probability specified in the constructor.  If this
 * happens it can do nothing for a time (also specified in the constructor)
 * and any food item that it is processing is spoiled.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */
public class Machine extends Unit {

	protected Queue<Product> q;
	protected Product currentItem = null;
	protected final int procTime;
	protected int procTimeLeft = 0;

	// Variables associated with jamming
	private static final int DEFAULT_SEED = 1729;
	private static Random gen = new Random(DEFAULT_SEED);
	protected double jamProb;
	protected int jamTime;
	protected boolean isJammed = false;
	protected int jamTimeLeft = 0;

	/**
	 * Creates machine with its input queue
	 * @param procTime time spent to process an item
	 * @param jamProb probability that machine jams in a single time step
	 * @param jamTime length of time that the machine remains jammed for
	 */
	public Machine(int procTime, double jamProb, int jamTime) {
		super();
		this.procTime = procTime;
		this.jamProb = jamProb;
		this.jamTime = jamTime;
		q = new LinkedList<Product>();
	}

	/**
	 * Runs the machine for a single time step
	 */
	public void tick() {
		// Update all the food waiting in the queue; take care to
		// remove spoiled items
		Iterator<Product> i = q.iterator();
		while (i.hasNext()) {
			Product f = (Product) i.next();
			if (f.isRemovable()) {
				i.remove();
				numSpoiled++;
			}
			else {
				f.tick();
			}
		}

		// If the machine is jammed, reduce jammed time left
		if (isJammed) {
			if (--jamTimeLeft < 0) {
				isJammed = false;
			}
		}

		else {
			// The machine is not jammed
			double pJam = gen.nextDouble();
			if (pJam < jamProb) {
				// Now it is jammed
				isJammed = true;
				jamTimeLeft = jamTime;
				// Throw away food being processed
				currentItem = null;
				numSpoiled++;
			}
			else{
				// Determine if it is time to take something off the queue
				if (procTimeLeft <= 0 && !q.isEmpty()) {
					procTimeLeft = procTime; 
					currentItem = q.remove();
				}

				// Update processing time left
				if (procTimeLeft > 0) 
					procTimeLeft--;
				// Process current item
				if (currentItem != null) {
					currentItem.tick();
					if (procTimeLeft == 0) {
						// The current item is finished, so send it on
						outC.transferProduct(currentItem);
						currentItem = null;
					}
				}
			}
		}
	}

	/**
	 * Machines always accept input
	 */
	public boolean acceptsInput() {
		return true;
	}

	/**
	 * Returns the length of the input queue
	 * @return the number of items in the queue
	 */
	public int queueLength() {
		return q.size();
	}

	/**
	 * Places a product in the input queue
	 * @param p product to be operated on by machine
	 */
	public void inputProduct(Product p) {
		q.add(p);
		numEntered++;
	}

	public String toString() {
		return super.toString() + "; q=" + q.toString();
	}
}
