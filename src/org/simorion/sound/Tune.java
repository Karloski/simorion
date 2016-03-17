package org.simorion.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class Tune implements PlayableSound {

	private SingleSound[] sounds;
	
	public Tune(SingleSound... sounds) {
		
	}
	
	@Override
	public void play(Synthesizer synth, float tempo) {
	    try {
	    	long now = synth.getMicrosecondPosition() - synth.getLatency();
	    	long beat = (long)(1000000/tempo);
	    	int offset = 0;
	    	for(SingleSound s : sounds) {
	    		ShortMessage msgOn = new ShortMessage();
				msgOn.setMessage(ShortMessage.NOTE_ON, 1, s.voice, 0);
			    synth.getReceiver().send(msgOn, now + (beat * offset));
			    offset += s.durationInBeats;
			    
			    ShortMessage msgOff = new ShortMessage();
				msgOff.setMessage(ShortMessage.NOTE_OFF, 1, s.voice, 0);
			    synth.getReceiver().send(msgOff, now + (beat * offset));
		    }
	    } catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
