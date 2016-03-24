package org.simorion.engine;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

import org.simorion.common.Voice;

/**
 * One stop shop for associating MIDI voice bytes with a Voice object
 * 
 * When calling getVoice() you can retrieve the voice number and the instrument name
 * Matrix buttons from 0,0 - 15,7 are assigned to the midi instruments (128)
 * Buttons from 0,8 - 15, 11 are assigned to the percussion instruments
 *
 * @author Petar Krstic
 */
 
public class MIDIVoices {
	
	// Drum sounds range from 35-81
		private static String[] allDrumSounds = {"Bass Drum 2", "Bass Drum 1", "Side Stick", "Snare Drum 1",
										  "Hand Clap", "Snare Drum 2", "Low Tom 2", "Closed Hi-hat",
										  "Low Tom 1", "Pedal Hi-hat", "Mid Tom 2", "Open Hi-hat",
										  "Mid Tom 1", "High Tom 2", "Crash Cymbal 1", "High Tom 1", 
										  "Ride Cymbal 1", "Chinese Cymbal", "Ride Bell", "Tambourine",
										  "Splash Cymbal", "Cowbell", "Crash Cymbal 2", "Vibra Slap", 
										  "Ride Cymbal 2", "High Bongo", "Low Bongo", "Mute High Conga",
										  "Open High Conga", "Low Conga", "High Timbale", "Low Timbale",
										  "High Agogo", "Low Agogo", "Cambasa", "Maracas",
										  "Short Whistle", "Long Whistle", "Short Guiro", "Long Guiro",
										  "Claves", "High Wood Block", "Low Wood Block", "Mute Cuica",
										  "Open Cuica", "Mute Triangle", "Open Triangle" };
	
	/**
	 * @return synthesizer 
	 * This sets up the synthesizer from which the soundbank can be loaded
	 * From here you can get the names of instruments given their voice number 
	 */
	public static Synthesizer getSynthesizer() {
		Synthesizer synthesizer = null;
		try {
			synthesizer = MidiSystem.getSynthesizer();
		} catch(Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		return synthesizer;
	}
	
	/**
	 * @param int i - a voice number depending on which matrix button was pressed
	 * @return Voice - contains it's name and voice number
	 * 
	 * This code distinguishes which instrument should be returned when a matrix button is pressed
	 */
	public static final Voice getVoice(final int i) {
		// Passed in is (16*y + x + 1) therefore if i is less than 129 it is a midi instrument
		// If i is between 129-175 then it is a percussion instrument
		// 176-256 is not assigned an instrument
		if (i <= 128) {
			return new Voice(){

				@Override
				public int getMidiVoice() {
					return i;
				}

				@Override
				public String getName() {
					return getSynthesizer().getDefaultSoundbank().getInstruments()[i-1].getName().trim();
				}
			};
		}
		else if(i < 176) {
			return new Voice(){
				@Override
				public int getMidiVoice() {
					return i;
				}
				@Override
				public String getName() {
					return allDrumSounds[i-129];
				}
			};
		}
		else {
			// Matrix buttons which do not have voices assigned to them 
			return new Voice(){
				@Override
				public int getMidiVoice() {
					return -1;
				}

				@Override
				public String getName() {
					return "No Instrument";
					// The LCD display will be set to this so the user can see the error
				}
			};
		}
	}
}
