package pt.iul.ista.poo.farm.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.iul.ista.poo.farm.objects.Cabage;
import pt.iul.ista.poo.utils.Point2D;

public class CabageTest {

	Cabage c;

	@Before
	public void setUp() {
		c = new Cabage(new Point2D(0,0));
	}

	@Test
	public void testIsRipe() {
		for(int i = 0; i<10; i++){
			c.timeAdvance();
		}
		assertEquals(c.isRipe(),true);
	}

	@Test
	public void testIsRotten() {
		for(int i = 0; i<30; i++){
			c.timeAdvance();
		}
		assertEquals(c.isRotten(),true);
	}

}
