package org.simorion.common.util;

import static org.junit.Assert.*;
import static org.simorion.common.util.Util.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testZipPositiveEqualLengths() {
		Iterable<Integer> input1 = Arrays.asList(0,1,2,3,4);
		Iterable<Integer> input2 = Arrays.asList(5,6,7,8,9);
		for(Util.Pair<Integer, Integer> pair : zip(input1, input2)) {
			assertEquals((int)pair.left+5, (int)pair.right);
		}
		assertEquals(Arrays.asList(0,1,2,3,4), input1);
		assertEquals(Arrays.asList(5,6,7,8,9), input2);
	}
	
	@Test
	public void testZipZeroLengths() {
		Iterable<Integer> input1 = Arrays.asList();
		Iterable<Integer> input2 = Arrays.asList();
		for(Util.Pair<Integer, Integer> pair : zip(input1, input2)) {
			fail("Should not iterate");
		}
		assertEquals(Arrays.asList(), input1);
		assertEquals(Arrays.asList(), input2);
	}
	
	@Test
	public void testZipPositiveUnequalLengths1() {
		Iterable<Integer> input1 = Arrays.asList(0,1,2,3,4);
		Iterable<Integer> input2 = Arrays.asList(5,6,7,8,9,10);
		for(Util.Pair<Integer, Integer> pair : zip(input1, input2)) {
			assertEquals((int)pair.left+5, (int)pair.right);
			assertNotEquals(10, (int)pair.right);
		}
		assertEquals(Arrays.asList(0,1,2,3,4), input1);
		assertEquals(Arrays.asList(5,6,7,8,9,10), input2);
	}
	
	@Test
	public void testZipPositiveUnequalLengths2() {
		Iterable<Integer> input1 = Arrays.asList(0,1,2,3,4);
		Iterable<Integer> input2 = Arrays.asList(5,6,7,8,9,10);
		for(Util.Pair<Integer, Integer> pair : zip(input2, input1)) {
			assertEquals((int)pair.left, (int)pair.right+5);
			assertNotEquals(10, (int)pair.left);
		}
		assertEquals(Arrays.asList(0,1,2,3,4), input1);
		assertEquals(Arrays.asList(5,6,7,8,9,10), input2);
	}
	
	@Test
	public void testBitstrings() {
		assertEquals(32, count(bitstring(0)));
		assertEquals(64, count(bitstring(0L)));
		
		int example = 0x51D4B4CB;//0b01010001110101001011010011001011;
		Iterator<Boolean> it = bitstring(example).iterator();
		
		assertTrue(it.next());
		assertTrue(it.next());
		assertFalse(it.next());
		assertTrue(it.next());
		assertFalse(it.next());
		assertFalse(it.next());
		assertTrue(it.next());
		assertTrue(it.next());
		assertFalse(it.next());
		assertFalse(it.next());
		//etc.
	}
	
	@Test
	public void testArrayIterable() {
		Integer[] ints = {0,1,2,3,4,5};
		int cmp = 0;
		for(Integer i : Util.iterable(ints)) {
			assertEquals(cmp++, (int)i);
		}
		
		Integer[] ints2 = {5,4,3,2,1,0};
		for(Pair<Integer, Integer> pair : zip(iterable(ints), iterable(ints2))) {
			assertEquals(5, pair.left + pair.right);
		}
		
	}

}
