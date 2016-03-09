package org.simorion.engine;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Petar
 * Simple testing of the getVoice method - not enough time before submission for more intensive testing
 */
public class MIDIVoicesTest {

	@Test
	public void testReturningVoice() {
		assertEquals("Acoustic Grand Piano", MIDIVoices.getVoice(1).getName());
		assertEquals("No Instrument", MIDIVoices.getVoice(200).getName());
		assertEquals("Snare Drum 1", MIDIVoices.getVoice(131).getName());
	}

}
