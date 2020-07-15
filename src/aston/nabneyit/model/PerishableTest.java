package aston.nabneyit.model;


import static org.junit.Assert.*;

import org.junit.*;

public class PerishableTest {
	private static final int spoilTime = 40;
	private Perishable p1;

	@Before
	public void setUp() throws Exception {
		p1 = new Perishable(spoilTime);
	}

	@Test
	public void testRemovable() {
		assertFalse(p1.isRemovable());
		
		for (int i = 0; i < spoilTime; i++) {
			p1.tick();
		}
		
		assertTrue(p1.isRemovable());
	}
}
