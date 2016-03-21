package org.simorion.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

/**
 * Representation of a single note in a given voice and velocity. It can be 
 * combined with others using the {@link Tune} class. As a data-oriented class,
 * the single play method is intended only for use by the sound thread.
 * 
 * @see Tune
 * @author Edmund Smith
 *
 */
public class SingleSound implements PlayableSound {

	int note, voice, velocity;
	float durationInSeconds;

	public SingleSound(int voice, int note, float durationInSeconds, int velocity) {
		this.note = note;
		this.voice = voice;
		this.durationInSeconds = durationInSeconds;
		this.velocity = velocity;
	}

	public SingleSound(int voice, int note, float durationInBeats) {
		this(voice, note, durationInBeats, 80);
	}

	/**
	 * Plays this single note to the synthesizer. Only to be called from the
	 * Sound Thread, since it has no thread safety awareness, due to being
	 * designed to only be called from the sound thread. 
	 */
	@Override
	public void play(Synthesizer synth) {
		try {
			ShortMessage msg;			
			Receiver rcvr = synth.getReceiver();

			int channel;
			long now = synth.getMicrosecondPosition() - synth.getLatency();
			float beat = 1000000;

			if (voice <= 128) {
				msg = new ShortMessage();
				//Channel 2 so that the underlying player's NOTE_OFF signals
				//don't turn off our separate sounds
				msg.setMessage(ShortMessage.PROGRAM_CHANGE, 2, voice, 0);
				rcvr.send(msg, now - 1);
				channel = 2;
			} else {
				voice -= 93;
				msg = new ShortMessage();
				msg.setMessage(ShortMessage.PROGRAM_CHANGE, 10, voice, 0);
				rcvr.send(msg, now - 1);
				channel = 9;
			}
			
			msg = new ShortMessage();
			msg.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);
			rcvr.send(msg, now);

			msg = new ShortMessage();
			// Equal to a note off since velocity = 0
			msg.setMessage(ShortMessage.NOTE_ON, channel, note, 0);
			rcvr.send(msg, now + (long)(beat * durationInSeconds));
			
		} catch (InvalidMidiDataException e) {
			//This is Programmer-defined, so in practice ought to be impossible
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			//If this fails, a much larger problem has reared, so this would be
			//like extinguishing a burning building by closing a fire door, can
			//be left unhandled
			e.printStackTrace();
		}
	}

}
