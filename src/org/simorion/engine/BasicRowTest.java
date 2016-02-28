package org.simorion.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BasicRowTest {

	private BasicRow row;
	
	@Before
	public void setUp() {
		row = new BasicRow();
	}
	
	@Test
	public void testAndMask() {
		row.lights = -1;
		row.applyMask(0xf0f0f080, 0);
		assertEquals(0xf0f0f080, row.lights);
		row.applyMask(0x0f0f0f08, 0);
		assertEquals(0, row.lights);
	}
	
	@Test
	public void testOrMask() {
		row.lights = 0;
		row.applyMask(-1, 0x137f8421);
		assertEquals(0x137f8421, row.lights);
		row.applyMask(-1, -0x137f8422);
		assertEquals(-1, row.lights);
	}
	
	@Test
	public void testIsLit() {
		row.lights = 0xf3;
		assertTrue(row.isLit(0));
		assertTrue(row.isLit(1));
		assertFalse(row.isLit(2));
		assertFalse(row.isLit(3));
		assertTrue(row.isLit(4));
		assertTrue(row.isLit(5));
		assertTrue(row.isLit(6));
		assertTrue(row.isLit(7));
	}
	
	@Test
	public void testSetLitUnlit() {
		row.setLit(0);
		row.setLit(3);
		row.setLit(5);
		assertEquals(0x29, row.lights);
		row.setLit(0);
		assertEquals(0x29, row.lights);
		row.setUnlit(0);
		assertEquals(0x28, row.lights);
		row.setUnlit(5);
		row.setUnlit(3);
		assertEquals(0, row.lights);
	}
	
	@Test
	public void testGetLit() {
		row.lights = 0x63636363;
		assertEquals(0x63636363, row.getLit());
		row.lights = 0x1f1f1f1f;
		assertEquals(0x1f1f1f1f, row.getLit());
	}
	
	@Test
	public void testToggleLit() {
		row.lights = 0;
		for(int i = 0; i < 16; i++) row.toggleLit(i*2+1);
		assertEquals(0xaaaaaaaa, row.lights);
		for(int i = 0; i < 8; i++) row.toggleLit(i*4+1);
		assertEquals(0x88888888, row.lights);
	}

}
