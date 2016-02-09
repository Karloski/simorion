package org.simorion.common;

public interface Row {

	public boolean isLit(int cell);
	
	/**
	 * Returns the MIDI note for the row
	 * @return positive byte between 0 and 127 inclusive
	 */
	public byte getNote();
	
}
