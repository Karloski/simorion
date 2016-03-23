package org.simorion.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.simorion.engine.BasicLayer;

/**
 * 
 * @author Petar Krstic
 *
 *	Series of tests for functionality of the StandardSong class
 */
public class StandardSongTest {
	
	private StandardSong standardSongToTest;
	private StandardSong otherStandardSongToCompare;
	private SongBuilder basicSBForComparing;
	
	@Before
	public void setUp() {
		standardSongToTest = new StandardSong();
		otherStandardSongToCompare = new StandardSong();
		basicSBForComparing = new SongBuilder();
	}
	
	/**
	 *  When instantiating a new StandardSong() the values set to it are;
	 *  "bpm = 60" & "tempo = 1f"
	 */
	@Test
	public void testGetttingFromSS() {
		assertEquals(60, standardSongToTest.getBPM());
		// When comparing floats you must add a delta - a difference that is allowed between the values
		assertEquals(1f, standardSongToTest.getTempo(), 0.0002);
		// Now I will try setting new values and retrieving them
		// BPM
		standardSongToTest.setBPM((byte) 80);
		assertEquals(80, standardSongToTest.getBPM());
		// Tempo(BPS)
		// Changing tempo sets the BPM to "bpm = (byte) (tempo * 60F);"
		standardSongToTest.setTempo(1.25f);
		assertEquals(1.25f, standardSongToTest.getTempo(), 0.0002);
		assertEquals(75, standardSongToTest.getBPM());
		// Equally changing the BPM affects the Tempo that's returned
		standardSongToTest.setBPM((byte) 120);
		assertEquals(2f, standardSongToTest.getTempo(), 0.0002);
		assertEquals(120, standardSongToTest.getBPM());
		// There should be 16 layers in the standard song
		assertEquals(16, standardSongToTest.getLayers().size());
		// When using toString it should return all the values of each layer 
		// Followed by the Tempo
		assertEquals("StandardSong "
				+ "{[BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},"
				+ "BasicLayer {[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,], velocity = 100, loopPoint = 0, voice = Piano 1},], "
				+ "tempo = 2.0}", standardSongToTest.toString());
	}
	
	/**
	 * Testing the setTempo() function
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testAddingInvalidTempos() throws IllegalArgumentException {
		
		assertEquals(1f, standardSongToTest.getTempo(), 0.0002);
		// Test using a negative value
		try {
			standardSongToTest.setTempo(-1.25f);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// Edge case - setting it to 0
		try {
			standardSongToTest.setTempo(0f);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// Setting the value as infinity (or what java deems as infinity)
		try {
			standardSongToTest.setTempo(Integer.MAX_VALUE + 1);
			fail("Should have thrown exception");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	/**
	 * The equals method compares two objects and checks if they're equal
	 * 1.) They must both be instances of StandardSong 
	 * 2.) All layers must be equal
	 * 3.) The BPM must be equal
	 */
	@Test
	public void testEqualsMethod() {
		// Both StandardSong objects are newly instantiated
		assertTrue(standardSongToTest.equals(otherStandardSongToCompare));
		
		// Changing the BPM on one should make them not equal
		standardSongToTest.setBPM((byte)120);
		assertFalse(standardSongToTest.equals(otherStandardSongToCompare));
		// Change it back to the default BPM
		standardSongToTest.setBPM((byte)60);
		assertTrue(standardSongToTest.equals(otherStandardSongToCompare));
		
		// Changing Tempo should also affect the BPM
		standardSongToTest.setTempo(1.25f);
		assertFalse(standardSongToTest.equals(otherStandardSongToCompare));
		// Change it back to default
		standardSongToTest.setTempo(1f);
		assertTrue(standardSongToTest.equals(otherStandardSongToCompare));
		
		// Finally try comparing it with an object which is not an instance of StandardSong
		assertFalse(standardSongToTest.equals(basicSBForComparing));
		
	}

}
