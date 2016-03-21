package org.simorion.common;

/**
 * Representation for a MIDI Voice, which has a byte id and queryable attributes
 */
public interface Voice {

	public int getMidiVoice();
	
	public String getName();
	
}
