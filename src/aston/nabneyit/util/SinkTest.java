package aston.nabneyit.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aston.nabneyit.model.*;

public class SinkTest {
	private Food foodArray[];
	private Sink s1, s2;

	@Before
	public void setUp() throws Exception {
		s1 = new Sink();
		s2 = new Sink();
		foodArray = new Food[3];
		foodArray[0] = new Perishable(40);
		foodArray[1] = new Perishable(45);
		foodArray[2] = new NonPerishable();
		for (int i = 0; i < foodArray.length; i++)
			s2.inputProduct(foodArray[i]);
	}

	@Test
	public  void testTick() {
		s2.tick();
		assertEquals(foodArray.length, s2.numProcessed());
		assertEquals(foodArray.length, s2.numEntered());
		assertEquals(0, s2.numSpoiled());
	}

	@Test
	public  void testAcceptsInput() {
		assertTrue(s1.acceptsInput());
		assertTrue(s2.acceptsInput());
	}

	@Test
	public  void testQueueLength() {
		assertEquals(0, s1.queueLength());

		assertEquals(foodArray.length, s2.queueLength());
	}

}
