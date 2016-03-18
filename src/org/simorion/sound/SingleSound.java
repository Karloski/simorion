package org.simorion.sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import org.simorion.common.ImmutableRow;

public class SingleSound implements PlayableSound {

	int note, voice, velocity;
	float durationInBeats;

	public SingleSound(int voice, int note, float durationInBeats, int velocity) {
		this.note = note;
		this.voice = voice;
		this.durationInBeats = durationInBeats;
		this.velocity = velocity;
	}

	public SingleSound(int voice, int note, float durationInBeats) {
		this(voice, note, durationInBeats, 80);
	}

	public void play(Synthesizer synth, float tempo) {
		try {
			ShortMessage msg;			
			Receiver rcvr = synth.getReceiver();

			int channel;
			long now = synth.getMicrosecondPosition() - synth.getLatency();
			float beat = 1000000 / tempo;

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
			rcvr.send(msg, now + (long)(beat * durationInBeats));
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
