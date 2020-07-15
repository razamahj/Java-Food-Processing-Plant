package aston.nabneyit.util;

import java.util.*;


/**
 * Models the arrival of new food items in the factory.  It
 * creates a copy of the type of food stored in the object when it is
 * constructed.  The use of a single random number generator for all
 * sources ensures that the items of food are generated independently.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */

public class Source extends Unit {

	private static final int DEFAULT_SEED = 42;
	private static Random gen = new Random(DEFAULT_SEED);

	protected Product productType;
	protected double genProb;

	/**
	 * Creates a source object
	 * @param f defines the type of product that the source generates
	 * @param genProb probability of generating a product item in a single time step
	 */
	public Source(Product f, double genProb) {
		super();
		init(f, genProb);
	}

	public Source(Product f, double genProb, int seed) {
		super();
		init(f, genProb);
		gen.setSeed(seed);
	}

	/**
	 * Helper method providing common functionality for constructors.
	 */
	private void init(Product f, double genProb) {
		this.productType = f;
		this.genProb = genProb;
	}

	/**
	 * Runs the source for one time step.  Generates a new product
	 * with the appropriate probability.
	 */
	public void tick() {
		double prob = gen.nextDouble();
		// If probability is correct, generate a new piece of food.
		if (prob < genProb) {
			Product newProduct = productType.copy();
			numProcessed++;
			outC.transferProduct(newProduct);
		}
	}

	/**
	 * Checks if this source accepts input.  Must return <code>false</code>
	 * since a source can never accept input from another unit.
	 */
	public boolean acceptsInput() {
		return false;
	}

	/**
	 * Returns length of queue.
	 *
	 * @return 0 since this does not have an input line.
	 */
	public int queueLength() {
		return 0;
	}

	/**
	 * Cannot accept product
	 * @throws InternalError
	 */
	public void inputProduct(Product p) throws InternalError {
		throw new InternalError();
	}

	public String toString() {
		return super.toString() + "; genProb=" + genProb;
	}
}


