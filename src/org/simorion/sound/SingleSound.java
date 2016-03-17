package org.simorion.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class SingleSound implements PlayableSound {

	byte note, voice, durationInBeats;
	public SingleSound(int note, int voice, int durationInBeats) {
		this.note = (byte)note;
		this.voice = (byte)voice;
		this.durationInBeats = (byte)durationInBeats;
	}
	
	public void play(Synthesizer synth, float tempo) {
	    try {
	    	ShortMessage msg = new ShortMessage();
			msg.setMessage(ShortMessage.NOTE_ON, 1, voice, 0);
			long now = synth.getMicrosecondPosition() - synth.getLatency();
	    	long beat = (long)(1000000/tempo);
		    synth.getReceiver().send(msg, now);
		    //TODO: Turn note off again
		    System.out.println("Played "+ note);
	    } catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
