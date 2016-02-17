package org.simorion.common;

import java.util.Collection;

public interface Layer extends ReadonlyLayer {

	/**
	 * Get a container representing every cell on a particular row
	 * @param i the row index, indexed from bottom left up
	 * @return the row at index i
	 */
	public Row getRow(int i);

	/**
	 * Get a collection of all rows for iteration
	 * @return
	 */
	public Iterable<? extends Row> getRows();
	
}
