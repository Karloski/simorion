package org.simorion.engine;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

import org.simorion.common.Voice;

/**
 * One stop shop for associating MIDI voice bytes with a Voice object
 *
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
		
		
	public static Synthesizer getSynthesizer() {
		Synthesizer synthesizer = null;
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
		} catch(Exception ex) {
			System.out.println(ex); System.exit(1);
		}
		return synthesizer;
	}
	
	public static final Voice getVoice(int i) {
		System.out.println(getSynthesizer().getDefaultSoundbank().getInstruments()[3].getName());
		if (i < 128) {
			return new Voice(){

				@Override
				public int getMidiVoice() {
					return i;
				}

				@Override
				public String getName() {
					return getSynthesizer().getDefaultSoundbank().getInstruments()[i].getName();
				}
				
			};
		}
		else if(i < 174) {
			return new Voice(){
				@Override
				public int getMidiVoice() {
					return i-93;
				}

				@Override
				public String getName() {
					return allDrumSounds[i-128];
				}
			};
		}
		
		return null;
	}
	
}
