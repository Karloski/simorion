package org.simorion.engine;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Petar Krstic
 * Simple testing of the getVoice method
 */
public class MIDIVoicesTest {

	@Test
	public void testReturningVoice() {
		// You cannot ever do getVoice(0)
		assertEquals("Piano 1", MIDIVoices.getVoice(1).getName());
		
		// Try an instrument in the mid range - 77 should be Bottle Blow
		assertEquals("Bottle Blow", MIDIVoices.getVoice(77).getName());
		
		// 128 should be the last instrument - Gun Shot
		assertEquals("Gun Shot", MIDIVoices.getVoice(128).getName());
		
		// 129 should be the first drum sound - Bass Drum 2
		assertEquals("Bass Drum 2", MIDIVoices.getVoice(129).getName());
		
		// Try another mid range drum - 131 is Side Stick
		assertEquals("Side Stick", MIDIVoices.getVoice(131).getName());
		
		// Try the last drum sound - Open Triangle (175)
		assertEquals("Open Triangle", MIDIVoices.getVoice(175).getName());
		
		// Anything over 175 should be "No Instrument"
		assertEquals("No Instrument", MIDIVoices.getVoice(176).getName());
		assertEquals("No Instrument", MIDIVoices.getVoice(200).getName());
	}
	
	@Test 
	public void testReturningVoiceID() {
		// Anything under 176 should have the same MidiVoice as what has been set
		// >176 is no instrument and should return -1
		assertEquals(1, MIDIVoices.getVoice(1).getMidiVoice());
		assertEquals(70, MIDIVoices.getVoice(70).getMidiVoice());
		assertEquals(175, MIDIVoices.getVoice(175).getMidiVoice());
		
		assertEquals(-1, MIDIVoices.getVoice(176).getMidiVoice());
		assertEquals(-1, MIDIVoices.getVoice(211).getMidiVoice());
	}

}