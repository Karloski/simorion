
package org.simorion.common.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 
 * Standard utilities hold-all class
 * 
 * @author Edmund Smith
 */
public final class Util {

	/**
	 * Substitute for array literals
	 * @param ts The elements of the array
	 * @return The array containing the elements
	 */
	public static <T> T[] asArray(T...ts) {
		return ts;
	}
	
	/**
	 * Simple conversion function
	 * @param cs The Character[] to convert
	 * @return cs as a byte[]
	 */
	public static byte[] asByteArray(Character[] cs) {
		return cs.toString().getBytes();
	}
	
	/**
	 * Simple 2-Tuple, with public final fields.
	 * 
	 * @author Edmund Smith
	 *
	 * @param <L> Type of the left hand object
	 * @param <R> Type of the right hand object
	 */
	public static class Pair<L,R> { 
		public final L left;
		public final R right;
		public Pair(L l, R r) {
			left = l;
			right = r;
		}
	}
	
	/**
	 * Zips together two iterables into a single pair iterable. Continues for
	 * as long as both iterables can supply new elements.
	 * Example:
	 * <code>
	 * for(Pair&lt;String, Integer&gt; p : zip(strList, intSet)) { ... }
	 * </code>
	 * @param <L> Type of the left hand iterable's elements, and of the left 
	 * elements in the pairs
	 * @param <R> Type of the right hand iterable's elements, and of the right
	 * elements in the pairs
	 * @param left The left hand iterable
	 * @param right The right hand iterable
	 * @return Iterable of pairs, where 0th pair.left = left.0th element,
	 * 0th pair.right = right.0th element, etc.
	 */
	public static <L,R> Iterable<Pair<L,R>> zip(
			final Iterable<L> left,
			final Iterable<R> right) {
		return new Iterable<Pair<L,R>>() {

			@Override
			public Iterator<Pair<L, R>> iterator() {
				return new Iterator<Pair<L,R>>() {

					Iterator<L> l = left.iterator();
					Iterator<R> r = right.iterator();
										
					@Override
					public boolean hasNext() {
						return l.hasNext() && r.hasNext();
					}

					@Override
					public Pair<L, R> next() {
						return new Pair<L, R>(l.next(), r.next());
					}

					@Override
					public void remove() {
						l.remove();
						r.remove();
					}
				};
			}
		};
	}
	
	/**
	 * Dirty function to find the length of an iterable when it has no size()
	 * or length
	 * @param it The iterable to find the size of
	 * @return The number of elements in it
	 */
	public static <T> int count(final Iterable<T> it) {
		int count = 0;
		for(T t : it) count++;
		return count;
	}
	
	/**
	 * Iterates over a bitstring of a certain length
	 * @param l The bitstring
	 * @param length The length of the bitstring
	 * @return Iterable representing the bitstring
	 */
	private static Iterable<Boolean> bitstringImpl(final long l, final int length) {
		return new Iterable<Boolean>() {

			@Override
			public Iterator<Boolean> iterator() {
				return new Iterator<Boolean>() {
					int offset = 0;
					
					@Override
					public boolean hasNext() {
						return offset < length;
					}

					@Override
					public Boolean next() {
						return (l & (1 << (offset++))) != 0;
					}

					@Override
					public void remove() {
						offset++;
					}
					
				};
			}
			
		};
	}
	
	/**
	 * Iterates over a bitstring stored in a long
	 * @param l The bitstring
	 * @return Iterable of the bitstring
	 */
	public static Iterable<Boolean> bitstring(final long l) {
		return bitstringImpl(l, 64);
	}
	

	/**
	 * Iterates over a bitstring stored in an int
	 * @param l The bitstring
	 * @return Iterable of the bitstring
	 */
	public static Iterable<Boolean> bitstring(final int i) {
		return bitstringImpl(i, 32);
	}
	
	/**
	 * Creates an iterable over an array, akin to a slice over the whole array
	 * @param arr The array to iterate
	 * @return An iterable over the array
	 */
	public static <T> Iterable<T> iterable(final T... arr) {
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				
				return new Iterator<T>() {
					
					int offset = 0;
					
					@Override
					public boolean hasNext() {
						return offset < arr.length;
					}

					@Override
					public T next() {
						return arr[offset++];
					}

					@Override
					public void remove() {
						throw new RuntimeException("UnexpectedOperation");
					}
					
				};
			}
			
		};
	}
	
	/**
	 * Wraps an Enumeration&lt;T&gt; into an Iterable&lt;T&gt;
	 * @param iter The enumeration to wrap
	 * @return The enumeration in an interable
	 */
	public static <T> Iterable<T> iterable(final Enumeration<T> iter) {
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					@Override
					public boolean hasNext() {
						return iter.hasMoreElements();
					}

					@Override
					public T next() {
						return iter.nextElement();
					}

					@Override
					public void remove() {
						throw new RuntimeException("UnexpectedOperation");
					}
					
				};
			}
			
		};
	}
	
	/**
	 * Turns in int into a big-endian byte array representing the int.
	 * @param i The int to convert
	 * @return The int as a byte[4]
	 */
	public static byte[] toBytes(int i) {
		return new byte[] { 
			(byte)((i&0xff000000) >> 24),
			(byte)((i&0x00ff0000) >> 16),
			(byte)((i&0x0000ff00) >>  8),
			(byte)((i&0x000000ff) >>  0)
		};
	}
	
	/**
	 * COnverts a big-endian byte array into an int
	 * @param b The byte array
	 * @return The int the byte array represents
	 */
	public static int toInt(byte[] b) {
		switch(b.length) {
			case 0: return 0;
			case 1: return b[0];
			case 2: return b[0]<<8|b[1];
			case 3: return b[0]<<16|b[1]<<8|b[2];
			default: return b[0]<<24|b[1]<<16|b[2]<<8|b[3];
		}
	}

}
