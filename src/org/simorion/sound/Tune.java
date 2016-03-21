package org.simorion.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

/**
 * Representation of a sequence of SingleSounds to form a cohesive tune.
 * It is a data-oriented class, whose play method is intended only for use by
 * the thread in charge of sound.
 * 
 * @author Edmund Smith
 *
 */
public class Tune implements PlayableSound {

	private SingleSound[] sounds;
	
	public Tune(SingleSound... sounds) {
		this.sounds = sounds;
	}
	
	/**
	 * Plays the sequence of sounds described by the Tune object.
	 * Only to be called from the SoundThread, since it has no thread safety
	 * designed in to it, due to it being designed to be called by the sound
	 * thread only.
	 */
	@Override
	public void play(Synthesizer synth) {
	   	    
	    try {
			ShortMessage msg;			
			Receiver rcvr = synth.getReceiver();

			int channel = 2;
			long now = synth.getMicrosecondPosition() - synth.getLatency();
			float beat = 1000000;
			msg = new ShortMessage();
			msg.setMessage(ShortMessage.STOP);
			rcvr.send(msg, now);
			//Practically verbatim from SoundThread's player
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
				msg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, voice, 0);
				rcvr.send(msg, now - 1);
				
				msg.setMessage(ShortMessage.NOTE_ON, channel, s.note, s.velocity);
				rcvr.send(msg, now);

				// Equal to a note off since velocity = 0
				msg.setMessage(ShortMessage.NOTE_ON, channel, s.note, 0);
				rcvr.send(msg, now + (long)(beat * s.durationInSeconds));

				now += beat * s.durationInSeconds;
			}
			msg = new ShortMessage();
			msg.setMessage(ShortMessage.START);
			rcvr.send(msg, now);
		} catch (InvalidMidiDataException e) {
			//This is programmer error, so will never occur :)
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			//If this is thrown, a much larger fish needs to be fried
			e.printStackTrace();
		}
	}

}
