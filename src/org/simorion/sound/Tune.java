package org.simorion.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class Tune implements PlayableSound {

	private SingleSound[] sounds;
	
	public Tune(SingleSound... sounds) {
		this.sounds = sounds;
	}
	
	@Override
	public void play(Synthesizer synth, float tempo) {
	   	    
	    try {
			ShortMessage msg;			
			Receiver rcvr = synth.getReceiver();

			int channel = 2;
			long now = synth.getMicrosecondPosition() - synth.getLatency();
			float beat = 1000000 / tempo;
			msg = new ShortMessage();
			msg.setMessage(ShortMessage.STOP);
			rcvr.send(msg, now);
			for (SingleSound s : sounds) {
				int voice = s.voice;
				if (s.voice <= 128) {
					// Channel 2 so that the underlying player's NOTE_OFF
					// signals
					// don't turn off our separate sounds
					channel = 2;
					
				} else {
					voice -= 93;
					channel = 9;
				}
				msg = new ShortMessage();
				msg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, voice, 0);
				rcvr.send(msg, now - 1);
				
				msg = new ShortMessage();
				msg.setMessage(ShortMessage.NOTE_ON, channel, s.note, s.velocity);
				rcvr.send(msg, now);

				msg = new ShortMessage();
				// Equal to a note off since velocity = 0
				msg.setMessage(ShortMessage.NOTE_ON, channel, s.note, 0);
				rcvr.send(msg, now + (long)(beat * s.durationInBeats));

				now += beat * s.durationInBeats;
			}
			msg = new ShortMessage();
			msg.setMessage(ShortMessage.START);
			rcvr.send(msg, now);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
