
package aston.nabneyit.util;


/**
 * Defines the abstract base class for the processing units in a Factory.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */
public abstract class Unit {

	protected int ID;
	protected static int unitID = 0;

	protected Connector inC = null;
	protected Connector outC = null;

	protected int numSpoiled = 0;
	protected int numProcessed = 0;
	protected int numEntered = 0;

	/**
	 * Constructs a unit with a unique ID.
	 */
	public Unit() {
		ID = unitID++;
	}

	/**
	 * Runs this unit for a single simulated time step
	 */
	public abstract void tick();

	/** 
	 * Checks whether this unit accepts input from other units.
	 *
	 * @return <code>true</code> if this unit accepts input from other units.
	 */
	public abstract boolean acceptsInput();

	/**
	 * Returns the number of food items waiting in this unit.
	 *
	 * @return number of food items waiting in this unit.
	 */
	public abstract int queueLength();

	/**
	 * Takes in a food item for processing.
	 *
	 * @param p Food item taken in.
	 */
	public abstract void inputProduct(Product p);

	/**
	 * Returns the number of items that have spoiled in this unit.
	 *
	 * @return number of items that have spoiled in this unit.
	 */
	public int numSpoiled() {
		return numSpoiled;
	}

	/**
	 * Returns the number of items that this unit has processed.
	 *
	 * @return number of items that this unit has processed.
	 */
	public int numProcessed() {
		return numProcessed;
	}

	/**
	 * Returns the number of items that this unit has accepted.
	 *
	 * @return number of items that this unit has accepted.
	 */
	public int numEntered() {
		return numEntered;
	}

	/**
	 * Connects the specified {@link Connector Connector} as an input to
	 * this unit.
	 *
	 * @param c connected as input to this unit.
	 */
	public void connectInput(Connector c) {
		inC = c;
	}

	/**
	 * Connects the specified {@link Connector Connector} as an output from
	 * this unit.
	 *
	 * @param c connected as output from this unit.
	 */
	public void connectOutput(Connector c) {
		outC = c;
	}

	/**
	 * Returns a string representation of this unit.  This consists of the
	 * unit's ID in round brackets <tt>"()"</tt>.
	 *
	 * @return a string representation of this unit.
	 */
	public String toString() {
		return "Unit (" + ID + ")";
	}
}
