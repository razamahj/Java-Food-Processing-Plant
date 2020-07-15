
package aston.nabneyit.util;

import java.util.*;

/**
 * Defines a factory as a series of layers, with each
 * unit in each layer connected to unit(s) in the next layer.  It
 * assumes that either a single intelligent router is used between all units
 * in each layer {@link #multiConnectLayers} or that the two layers have the
 * same number of units and corresponding units are connected (to a single
 * unit in each case) {@link #directConnectLayers}.
 *
 * @author Ian T. Nabney
 * @version 2.0
 */
public class LayeredFactory {

	private Layer[] layers;
	/** 
	 * Stores connectors between layers.  This 2d array stores an
	 * array of connectors for each layer, as direct connections require
	 * one connector per unit.
	 */
	private Connector[][] connections;

	/**
	 * Creates a layered factory made up of layers and connectors between layers
	 * @param numLayers the number of layers in the factory
	 */
	public LayeredFactory(int numLayers) {
		if (numLayers < 1) {
			throw new IllegalArgumentException("Illegal Size: "+
					numLayers);     
		}

		layers = new Layer[numLayers];
		for (int i = 0; i < numLayers; i++) 
			layers[i] = new Layer();

		connections = new Connector[numLayers-1][];
	}

	/**
	 * Run the factory for a single time step
	 */
	public void tick() {
		for (int i = 0; i < layers.length; i++)
			(layers[i]).tick();
	}

	/**
	 * Make basic checks on viability of layered factory: must be some
	 * units in each layer and that connections are present
	 * @return true if layered factory is OK
	 */
	public boolean check() {
		// Make sure that each layer contains some units
		for (int i = 0; i < layers.length; i++) {
			if ((layers[i]).numUnits() == 0)
				return false;
		}
		// Make sure that there are enough connections and that they all
		// check
		for (int i = 0; i < layers.length - 1; i++) {
			if (connections[i] == null)
				return false;
			for (int j = 0; j < connections[i].length; j++) {
				if (! (connections[i][j].check()) )
					return false;
			}
		}
		// Could have more sophisticated checks; e.g. all units are properly
		// connected up.
		return true;
	}

	/**
	 * Adds a unit to the factory
	 * @param u unit to be added
	 * @param layerIndex index of layer to add unit to
	 */
	public void addUnit(Unit u, int layerIndex) {
		if (layerIndex < 0 || layerIndex >= layers.length)
			throw new IllegalArgumentException("Illegal Index: "+
					layerIndex);     
		(layers[layerIndex]).addUnit(u);
	}

	/**
	 * Returns layer: currently redundant
	 * @param i index of layer
	 * @return specified layer
	 */
	public Layer getLayer(int i) {
		if (i < 0 || i >= layers.length)
			throw new IllegalArgumentException("Illegal Index: "+
					i);     
		return layers[i];
	}

	/**
	 * Connects a specified layer to its following layer using a single
	 * intelligent router.  
	 *
	 * @param i index of first layer in connection
	 * @exception IndexOutOfBoundsException if index is out of range
	 */
	public void multiConnectLayers(int i) {
		if (i < 0 || i >= layers.length-1)
			throw new 
			IndexOutOfBoundsException("Illegal Index in connection: " +
					i);

		IntelligentRouter ir = new IntelligentRouter();

		// Connect all the units in layer i as inputs to the router
		Iterator<Unit> i1 = layers[i].iterator();
		while (i1.hasNext()) {
			Unit u1 = i1.next();
			ir.addInputUnit(u1);
			// Make sure that each unit knows where it is sending food
			u1.connectOutput(ir);
		}

		// Connect all the units in layer i+1 as outputs to the router
		Iterator<Unit> i2 = layers[i+1].iterator();
		while (i2.hasNext()) {
			Unit u2 = i2.next();
			ir.addOutputUnit(u2);
			// Make sure that each unit knows where it is getting food
			// from (not necessary, but comforting to have)
			u2.connectInput(ir);
		}
		connections[i] = new Connector[1];
		connections[i][0] = ir;
	}

	/**
	 * Connects a specified layer to its following layer using one connector
	 * per pair of corresponding units.
	 *
	 * @param i index of first layer in connection
	 * @exception IndexOutOfBoundsException if index is out of range
	 * @exception ConnectionException if the number of units in each layer is
	 * not the same
	 */
	public void directConnectLayers(int i) {
		if (i < 0 || i >= layers.length-1)
			throw new 
			IndexOutOfBoundsException("Illegal Index in connection: " +
					i);

		if (layers[i].numUnits() != layers[i+1].numUnits())
			throw new ConnectionException();

		// Create an array of connectors
		Connector[] cArray = new Connector[layers[i].numUnits()];

		Iterator<Unit> i1 = layers[i].iterator();
		Iterator<Unit> i2 = layers[i+1].iterator();

		int index = 0;
		while (i1.hasNext()) {
			Unit u1 = (Unit) i1.next();
			Unit u2 = (Unit) i2.next();

			// Connect up the units one-by-one
			cArray[index] = new IntelligentRouter();
			cArray[index].addInputUnit(u1);
			cArray[index].addOutputUnit(u2);
			// Units should also know about the connector
			u1.connectOutput(cArray[index]);
			u2.connectInput(cArray[index]);
			index++;
		}
		// Set the correct row of the 2d array to the array of connectors
		connections[i] = cArray;
	}

	public String toString() {
		String newLine = System.getProperty("line.separator");  
		StringBuffer buf = new StringBuffer("Layered Factory" + newLine);

		buf.append("There are " + layers.length + " layers" + newLine);
		for (int i = 0; i < layers.length; i++) {
			buf.append("Layer " + i + newLine);
			buf.append(layers[i].toString());
			buf.append(newLine);
		}

		for (int i = 0; i < connections.length; i++) {
			buf.append("Connections from layer " + i + newLine);
			for (int j = 0; j < connections[i].length; j++) {
				buf.append(connections[i][j]);
				buf.append(newLine);
			}
		}

		return buf.toString();
	}

	/**
	 * Returns the number of items processed in the sinks of factory
	 * @return number of processed products
	 */
	public int totalProcessed() {
		// Assume the final layer contains all the sinks
		// Alternative is to look at all the units and
		// sum over all Sink objects that are found
		int total = 0;
		int lastLayer = layers.length-1;
		Iterator<Unit> i = layers[lastLayer].iterator();
		while (i.hasNext()) {
			Unit u = i.next();
			total += u.numProcessed();
		}
		return total;
	}

	/**
	 * Returns the number of spoiled items in the factory: sums
	 * over all units
	 * @return number of spoiled items
	 */
	public int totalSpoiled() {
		int total = 0;
		for (int n = 0; n < layers.length; n++) {
			Iterator<Unit> i = layers[n].iterator();
			while (i.hasNext()) {
				Unit u = i.next();
				total += u.numSpoiled();
			}
		}
		return total;
	}
}
