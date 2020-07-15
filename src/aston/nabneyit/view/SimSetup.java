package aston.nabneyit.view;


import aston.nabneyit.model.*;
import aston.nabneyit.util.*;
import java.text.NumberFormat;

public class SimSetup {
	// Public constants
	public static final int DEFAULT_NUM_TICKS = 7200;
	// Formatter to give consistent output
	public static final NumberFormat formatter = 
		NumberFormat.getInstance();

	// Simulation constants
	private static final int cheeseSpoilTime = 45;
	private static final int blueCheeseSpoilTime = 40;
	private static final int cheeseProcessTime = 20;
	private static final int soupProcessTime = 22;
	private static final int packTime = 10;

	private static final int jamTime = 60;
	private static final double jamProb = 0.001;

	private static final int sourceSeed = 42;

	private double pCheese;
	private double pBlueCheese;
	private double pSoup;

	public SimSetup(double pCheese, double pBlueCheese,
			double pSoup) {
		this.pCheese = pCheese;
		this.pBlueCheese = pBlueCheese;
		this.pSoup = pSoup;
	}

	public LayeredFactory setUp() {

		LayeredFactory fac = new LayeredFactory(4);

		// Create source food items
		Perishable cheese = new Perishable(cheeseSpoilTime);
		Perishable blueCheese = new Perishable(blueCheeseSpoilTime);


		NonPerishable soup = new NonPerishable();

		// Create Source objects
		Source cSource = new Source(cheese, pCheese, sourceSeed);
		Source bcSource = new Source(blueCheese, pBlueCheese);
		Source sSource = new Source(soup, pSoup);

		// Add sources to first layer
		fac.addUnit(cSource, 0);
		fac.addUnit(bcSource, 0);
		fac.addUnit(sSource, 0);

		// Create processing machines
		Machine cProcMachine = new Machine(cheeseProcessTime,
				jamProb, jamTime);
		Machine bcProcMachine = new Machine(cheeseProcessTime,
				jamProb, jamTime);
		Machine sProcMachine = new Machine(soupProcessTime,
				jamProb, jamTime);

		// Add processing machines to second layer
		fac.addUnit(cProcMachine, 1);
		fac.addUnit(bcProcMachine, 1);
		fac.addUnit(sProcMachine, 1);

		// Create packing machines and add to third layer
		for (int i = 0; i < 1; i++) {
			Machine packMachine = new Machine(packTime, jamProb, jamTime);
			fac.addUnit(packMachine, 2);
		}

		// Create sinks and add to last layer
		for (int i = 0; i < 1; i++) {
			Sink s = new Sink();
			fac.addUnit(s, 3);
		}

		// Now connect layers
		fac.directConnectLayers(0);
		fac.multiConnectLayers(1);
		fac.directConnectLayers(2);

		return fac;
	}

	public String report(LayeredFactory f) {
		StringBuffer buf = new StringBuffer("");
		String newLine = System.getProperty("line.separator");

		buf.append("pCheese = " + pCheese + "; pSoup = " + pSoup + newLine);
		buf.append("Number of items packed = " + f.totalProcessed() + newLine);
		buf.append("Number of items spoiled = " + f.totalSpoiled() + newLine);
		buf.append("Total profit made = " + (f.totalProcessed()*10 -
				f.totalSpoiled()*20));

		buf.append(newLine);
		return buf.toString();
	}

}
