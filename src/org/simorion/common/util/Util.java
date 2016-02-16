
package org.simorion.common.util;

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
}
