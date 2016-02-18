package org.simorion.common.util;

import java.util.Iterator;

/**
 * Slices represent a subsection of an existing array, with very quick creation
 * and small footprint as long as the backing array exists. The slice itself is
 * immutable, although the backing array is not.
 * @author Edmund Smith
 *
 * @param <T> The element type of the array being sliced
 */
public class Slice<T> implements Iterable<T> {
	private final T[] backing;
	private final int start, end;
	
	public Slice(final T[] arr, final int start, final int end) {
		this.backing = arr;
		this.start = start;
		this.end = end;
	}
	
	public Slice(final T[] arr, final int start) {
		this(arr, start, arr.length);
	}
	
	public T get(int i) {
		return backing[start+i];
	}
	
	/**
	 * Creates a new iterator on every call, so that each iterator is individual.
	 * Each iterator cannot modify the slice, although it can mutate the backing
	 * array.
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>(){
			int position = start;
			
			/** {@inheritDoc } */
			@Override
			public boolean hasNext() {
				return position < end;
			}

			/** {@inheritDoc } */
			@Override
			public T next() {
				return backing[position++];
			}

			/** {@inheritDoc } */
			@Override
			public void remove() {
				throw new RuntimeException("This is unexpected");				
			}
		};
	}
}
