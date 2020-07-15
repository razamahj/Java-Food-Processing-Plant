package aston.nabneyit.util;

import java.util.*;

/**
 * Defines the only type of {@link Connector Connector} needed for
 * the coursework.  The strategy is to transfer food to the output unit with
 * the shortest input queue.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */

public class IntelligentRouter extends Connector {

	public IntelligentRouter() {
		super();
	}

	/**
	 * Transfers product to shortest input queue of output list
	 */
	public void transferProduct(Product p) {
		Unit o;
		// Find output unit with smallest queue length
		Iterator<Unit> i = outUnits.iterator();
		int minIndex = 0;
		int index = 0;
		int minLength = -1;
		// Loop over output units, checking only those which accept input.
		// This method assumes that at least one unit accepts input.  For
		// robustness, this should be checked here, but we will assume that
		// the client has done it already.
		while (i.hasNext()) {
			o = (Unit) i.next();
			if (o.acceptsInput() && 
					(minLength == -1 || o.queueLength() < minLength)) {
				minLength = o.queueLength();
				minIndex = index;
			}
			index++;
		}

		if (minLength == -1) 
			throw new InternalError("Connector incorrect");
		// Have found shortest queue, so send food to that unit
		o = (Unit) outUnits.get(minIndex);
		o.inputProduct(p);
	}
}
