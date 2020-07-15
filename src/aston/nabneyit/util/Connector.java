package aston.nabneyit.util;

import java.util.*;

/**
 * Defines the general form of a connector between units in a
 * factory.  A <code>Connector</code> has multiple input units and
 * multiple output units, and its job is to route food from input to output
 * following some strategy.  <p>
 *
 * Strictly speaking, connectors only need to know about the output units,
 * but it is more robust to know about inputs <i>and</i> outputs.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */

public abstract class Connector {
	protected List<Unit> inUnits;
	protected List<Unit> outUnits;

	/**
	 * Creates connector
	 */
	public Connector() {
		inUnits = new ArrayList<Unit>();
		outUnits = new ArrayList<Unit>();
	}

	/**
	 * Adds an input unit to the connector's ordered list of inputs
	 * @param u input unit
	 */
	public void addInputUnit(Unit u) {
		inUnits.add(u);  // Add units in order
	}

	/**
	 * Adds an output unit to the connector's ordered list of outputs
	 * @param u output unit
	 */
	public void addOutputUnit(Unit u) {
		outUnits.add(u); // Add units in order
	}

	/**
	 * Checks that at least one output unit accepts input, otherwise
	 * the connector can't work
	 * @return true if connector has appropriate units
	 */
	public boolean check() {

		for (Unit u : outUnits) {
			if (u.acceptsInput())
				return true;
		}
		return false;
	}

	/**
	 * Transfers product to one of the output units.  This method follows
	 * the strategy of the particular connector type.
	 *
	 * @param p food item to be transferred to one of the output units.
	 */
	public abstract void transferProduct(Product p);

	public String toString() {
		return ("Connector with " + inUnits.size() + " inputs and " +
				outUnits.size() + " outputs");
	}
}
