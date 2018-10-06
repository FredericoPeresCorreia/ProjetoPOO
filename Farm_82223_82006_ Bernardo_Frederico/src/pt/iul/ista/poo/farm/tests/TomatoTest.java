package pt.iul.ista.poo.farm.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.utils.Point2D;

public class TomatoTest {
	
	Tomato t;

	@Before
	public void setUp() {
		t = new Tomato(new Point2D(0,0));
		t.setTakenCareOf(true);
	}

	@Test
	public void testIsRipe() {
		for(int i = 0; i<15; i++) {
			t.timeAdvance();
		}
		assertEquals(t.isRipe(),true);
	}

	@Test
	public void testIsRotten() {
		for(int i = 0; i<25; i++) {
			t.timeAdvance();
		}
		assertEquals(t.isRotten(),true);
	}
}