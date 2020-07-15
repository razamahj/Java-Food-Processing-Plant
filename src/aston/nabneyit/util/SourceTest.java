package aston.nabneyit.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aston.nabneyit.model.Food;
import aston.nabneyit.model.NonPerishable;
import aston.nabneyit.model.Perishable;

public class SourceTest {
	
	private Source s1;
	private Source s2;
	private Sink sink = new Sink();
	private double probPerish = 0.1;
	private Food foodArray[];
	private Connector connect1 = new IntelligentRouter();
//	private Connector connect2 = new IntelligentRouter();

	@Before
	public void setUp() throws Exception {

		foodArray = new Food[3];
		foodArray[0] = new Perishable(40);
		foodArray[1] = new Perishable(45);
		foodArray[2] = new NonPerishable();
		s1 = new Source(foodArray[0], probPerish, 42);
		s2 = new Source(foodArray[2], 0.0, 42);
		connect1.addInputUnit(s1);
		s1.connectOutput(connect1);
		connect1.addInputUnit(s2);
		s2.connectOutput(connect1);
		connect1.addOutputUnit(sink);
		connect1.addOutputUnit(sink);
		sink.connectInput(connect1);
	}

	@Test
	public void testBasics() {
		assertTrue(!s1.acceptsInput());
		assertTrue(!s2.acceptsInput());
		assertEquals(0, s1.queueLength());
		assertEquals(0, s2.queueLength());
	}
	
	@Test
	public void testTick() {

		int numIterations = 10000;
		// Generate a lot of food
		for (int i = 0; i < numIterations; i++) {
			s1.tick();
			s2.tick();
		}
		assertEquals(0, s2.numProcessed());
		// Assert number generated to be within 10% of its expected value.
		assertTrue(s1.numProcessed() < numIterations*probPerish*1.1 &&
				s1.numProcessed() > numIterations*probPerish*0.9);
	}

}
