package org.simorion.common;

/**
 * Immutable view of the Row interface, with no escaping mutable references
 * 
 * @author Edmund Smith
 */
public interface ImmutableRow {

	public boolean isLit(int cell);
	
	/**
	 * Returns the MIDI note for the row
	 * @return positive byte between 0 and 127 inclusive
	 */
	public byte getNote();

	/**
	 * Gets the number of cells in the row
	 * @return
	 */
	public int cellCount();

}
