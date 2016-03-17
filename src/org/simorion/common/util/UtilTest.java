package org.simorion.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.simorion.common.util.Util.bitstring;
import static org.simorion.common.util.Util.count;
import static org.simorion.common.util.Util.iterable;
import static org.simorion.common.util.Util.zip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;
import org.simorion.common.util.Util.Pair;

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
		for(@SuppressWarnings("unused") Util.Pair<Integer, Integer> pair : zip(input1, input2)) {
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
			assertFalse(10 == (int)pair.right);
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
			assertFalse(10 == (int)pair.left);
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
	
	@Test
	public void testToIntFromBytesIsId() {
		Random r = new Random();
		for(int i = 0; i < 100; i++) {
			int ri = r.nextInt();
			assertEquals(ri, Util.toInt(Util.toBytes(ri)));
		}
	}
	
	@Test
	public void testToIntFromLT4Bytes() {
		Random r = new Random();
		for(int i = 0; i < 100; i++) {
			int size = r.nextInt(4);
			byte[] bytes = new byte[size];
			int bval = 0;
			for(int j = 0; j < size; j++) {
				int rv = r.nextInt(256);
				bval += rv << (8*j);
				bytes[size-j-1] = (byte)rv;
			}
			assertEquals(bval, Util.toInt(bytes));
		}
	}
	
	@Test
	public void enumerationIterableTest() {
		/*ArrayList<Integer> al = new ArrayList<Integer>();
		Random r = new Random();
		for(int i = 0; i < 100; i++) {
			al.add(r.nextInt());
		}
		Hashtable<Integer, Object> map = new Hashtable<Integer, Object>();
		for(Integer i : al) {
			map.put(i, "");
		}
		for(Pair<Integer, Integer> pair : Util.zip(al, Util.iterable(map.keys()))) {
			assertEquals(pair.left, pair.right);
		}*/
	}
}
