package aston.nabneyit.view;

//Import application packages
import aston.nabneyit.util.LayeredFactory;


//Import general packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Main GUI for customer queuing simulator.
 *
 * @author Ian T. Nabney
 * @version 20-Apr-2006
 *
 */

public class GUImain {
	
	// Convenient to have base windows available everywhere within this class
	private JFrame mainFrame;
	private JFrame reportFrame;
	
	private LabelledSlider timeSlider;
	private LabelledSlider soupSlider;
	private LabelledSlider cheeseSlider;

	private int simLength = SimSetup.DEFAULT_NUM_TICKS;
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		final GUImain gm = new GUImain();
		SimSetup.formatter.setMaximumFractionDigits(2);
	}
	
	/**
	 * Constructs GUI and attaches listeners
	 */
	private GUImain() {
		
		double pSoup = 0.01;
		double pCheese = 0.01;
		// Step 1: create the components
		JButton runButton = new JButton();
		JButton quitButton = new JButton();
		
		// Use scaled and labelled sliders for input
		timeSlider = new LabelledSlider("Simulation length: ", simLength,
				1, SimSetup.DEFAULT_NUM_TICKS, 1);
		soupSlider = new LabelledSlider("Probability of soup: ", pSoup, 1, 5, 100);
		cheeseSlider = new LabelledSlider("Probability of cheese: ", pCheese, 1, 5, 100);
			
		// Step 2: set the properties of the components
		runButton.setText("Run");
		runButton.setToolTipText("Run simulation.");
		quitButton.setText("Quit");
		quitButton.setToolTipText("Quit simulation.");
		timeSlider.setToolTipText("Set length of simulation (in ticks).");
		soupSlider.setToolTipText("Set probability of generating soup.");
		soupSlider.setMajorTickSpacing(1);
		cheeseSlider.setToolTipText("Set probability of generating cheese.");
		cheeseSlider.setMajorTickSpacing(1);
		
		// Step 3: create containers to hold the components
		mainFrame = new JFrame("Food Factory Simulation");
		
		JPanel commandBox = new JPanel();
		JPanel sliderBox = new JPanel();
		sliderBox.setBorder(BorderFactory.createEtchedBorder());
		
		// Step 4: specify LayoutManagers
		mainFrame.getContentPane().setLayout(new BorderLayout());
		commandBox.setLayout(new BorderLayout());
		sliderBox.setLayout(new BorderLayout());
		
		// Step 5: add components to containers
		commandBox.add(runButton, BorderLayout.WEST);
		commandBox.add(quitButton, BorderLayout.EAST);
	
		sliderBox.add(timeSlider, BorderLayout.NORTH);
		sliderBox.add(soupSlider, BorderLayout.CENTER);
		sliderBox.add(cheeseSlider, BorderLayout.SOUTH);

		mainFrame.add(sliderBox, BorderLayout.NORTH);
		mainFrame.add(commandBox, BorderLayout.SOUTH);
		
		// Step 6: arrange to handle events in the user interface
		mainFrame.setDefaultCloseOperation(
				WindowConstants.DO_NOTHING_ON_CLOSE);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitApp();
			}
		});    
		
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitApp();
			}
		});
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSimulation();
			}
		});
		
		// Step 7: Display the GUI
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	// Helper methods to provide functionality in actions.
	
	/**
	 * Displays dialog box for user to confirm exit
	 */
	private void exitApp() {
		// Display confirmation dialog before exiting application
		int response = JOptionPane.showConfirmDialog(mainFrame, 
				"Do you really want to quit?",
				"Quit?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		
		// Don't quit
	}
	
	/**
	 * Reads the values from the GUI and then runs the simulation
	 */
	private void runSimulation() {
		
		
		try {
			int time = (int)(Math.round(timeSlider.getValue()));
			double pCheese = cheeseSlider.getValue();
			double pSoup = soupSlider.getValue();
			
			// Create the correct junction
			SimSetup sim = new SimSetup(pCheese, pCheese, pSoup);
			LayeredFactory fac = sim.setUp();
			if (!fac.check()) {
				throw new Exception("Problem with setting up simulation.");
			}
			for (int t = 0; t < time; t++)
				fac.tick();
		

			// Create area to display results in
			reportFrame = new JFrame("Report");
			JTextArea reportText = new JTextArea(20, 20);
			JScrollPane reportPane = new JScrollPane(reportText);
			JButton closeButton = new JButton("Close");
			reportFrame.getContentPane().setLayout(new BorderLayout());
			reportFrame.getContentPane().add(reportPane, 
					BorderLayout.NORTH);
			reportFrame.getContentPane().add(closeButton, 
					BorderLayout.SOUTH);
			reportFrame.setDefaultCloseOperation(
					WindowConstants.DISPOSE_ON_CLOSE);
			
			reportText.setText(sim.report(fac));
			
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reportFrame.dispose();
				}
			});
			
			reportFrame.pack();
			reportFrame.setVisible(true);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, 
					"Problem creating or running simulation." +
					e.getMessage());
		}
		
	}
}

