package org.simorion;

import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.ImmutableRow;
import org.simorion.common.MutableLayer;
import org.simorion.common.MutableRow;
import org.simorion.common.Song;
import org.simorion.common.StandardSong;

/** nasty bastard class
 * 
 * Treat like a kidneynapping victim: this only exists to gut; rip out its
 * insides and use elsewhere, cause this is disposable as fuck
 * 
 * @author Edmund Smith
 *
 */
public class SoundTest implements Runnable {
	
	public Song song;
	
	public SoundTest(Song s) {
		song = s;
	}

	private Synthesizer synthesizer;
	public Synthesizer getSynthesizer() {
		if(synthesizer == null) try {
			synthesizer = MidiSystem.getSynthesizer();
			// this returns an error in the console but is necessary to make sound
			synthesizer.open();
		} catch(Exception ex) {
			System.out.println(ex); System.exit(1);
		}
		return synthesizer;
	}
	
	public void run() {
		long tick = 0;
		long tickPlusOne = 0;
		long nTicks = -1;
		System.out.println("Running song");
		while (true) {
			try {
				Thread.sleep(10);
				Synthesizer synth = getSynthesizer();
				if(tick > synth.getMicrosecondPosition()) continue;
				else {
					tick = synth.getMicrosecondPosition() + (long)(1000000/song.getTempo()) - synth.getLatency();
					tickPlusOne = tick + (long)(1000000/song.getTempo());
				}
				nTicks++;
				Receiver rcvr = synth.getReceiver();
				// ch0.programChange(song.getLayers().iterator().next().getVoice().getMidiVoice());
				ShortMessage msg = new ShortMessage();
				msg.setMessage(ShortMessage.STOP);
				rcvr.send(msg, -1);
				for (ImmutableLayer layer : song.getLayers()) {
					int midiVoice = layer.getVoice().getMidiVoice();
					int channel;
					if (midiVoice <= 128) { 
				    	Instrument[]  instruments  = synthesizer.getDefaultSoundbank().getInstruments();
					    synthesizer.loadInstrument(instruments[midiVoice]);
					    msg = new ShortMessage();
					    msg.setMessage(ShortMessage.PROGRAM_CHANGE, 1, midiVoice, 0);
					    rcvr.send(msg, tick+layer.getLayerNumber()-synth.getMicrosecondPosition());
					    channel = 1;
				    }
				    else { 
				    	midiVoice -= 93;
				    	msg = new ShortMessage();
					    msg.setMessage(ShortMessage.PROGRAM_CHANGE, 10, midiVoice, 0);
					    rcvr.send(msg, tick+layer.getLayerNumber()-synth.getMicrosecondPosition());
				    	channel = 9;
				    }
					for (ImmutableRow row : layer.getRows()) {
						int loop = layer.getLoopPoint();
						loop = loop == 0 ? 16 : loop;
						int i = (int)(nTicks % loop);
						if(!row.isLit(i)) continue;
						msg = new ShortMessage();
						msg.setMessage(ShortMessage.NOTE_ON, channel, row.getNote(),
								layer.getVelocity());
						rcvr.send(msg, tick + layer.getLayerNumber() - synth.getMicrosecondPosition());
						msg = new ShortMessage();
						msg.setMessage(ShortMessage.NOTE_OFF, channel, row.getNote(), layer.getVelocity());
						rcvr.send(msg, tickPlusOne);
					}
				}
				msg.setMessage(ShortMessage.START);
				rcvr.send(msg, -1);
			} catch (InvalidMidiDataException | MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
