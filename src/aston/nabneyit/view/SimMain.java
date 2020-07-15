package aston.nabneyit.view;

import aston.nabneyit.util.*;

public class SimMain {
	
	public static void main(String[] args) {
		double pCheese = 0.01;
		double pSoup;

		int[][] profits = new int[5][5];
		try {
			for (int i = 0; i < 5; i++) {
				pSoup = 0.01;
				for (int j = 0; j < 5; j++) {
					SimSetup sim = new SimSetup(pCheese, pCheese, pSoup);
					LayeredFactory fac = sim.setUp();
					if (!fac.check()) {
						System.err.println("Error in simulation set up");
						System.exit(42);
					}
					for (int t = 0; t < SimSetup.DEFAULT_NUM_TICKS; t++)
						fac.tick();
					System.out.println(sim.report(fac));
					profits[i][j] = fac.totalProcessed()*10 - fac.totalSpoiled()*20;
					pSoup += 0.01;
				}
				pCheese += 0.01;
			}

			String newLine = System.getProperty("line.separator");
			System.out.println("Profit table" + newLine);
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					System.out.print(profits[i][j] + "\t");

				}
			}
			System.out.print(newLine);
		}
		catch (Exception e) {
			System.err.println("Exception " + e);
			System.exit(42);
		}
	}
}

