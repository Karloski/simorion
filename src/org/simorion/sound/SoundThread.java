
package org.simorion.sound;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.ImmutableRow;
import org.simorion.common.Song;
import org.simorion.ui.model.MutableModel;
import org.simorion.ui.view.GUI;

/** Temporary Hack Class
 * 
 * It reads the song layer by layer, row by row, until it gets to
 * isLit(nextRowToPlay). This avoids having a mirror of the data to constantly
 * update. The voice changing stuff is taken from SoundSystem and converted to
 * ShortMessages, which pretty much correspond 1:1 to the method calls anyway.
 * 
 * Synchronization is done through using MIDI's internal microsecond clock;
 * TODO: update engine clock from it
 * Every ~10ms the thread wakes up, checks if the last column has been played,
 * and if so, it schedules the next column to play. Each layer has a 1 us delay
 * to allow for voice changing - I don't know if MIDI messages can race
 * otherwise. The only 'features' left are note length (e.g. Piano terminates,
 * but Accordion stays permanently playing until NOTE_OFF is sent) and possibly
 * fixing any sound/channel overlap errors
 * 
 * @author Petar Krstic
 * @author Edmund Smith
 *
 */
public class SoundThread implements Runnable {
	
	public Song song;
	public MutableModel model;
	private long nTicks = -1;
	private static SoundThread instance;
	
	protected ConcurrentLinkedQueue<PlayableSound> soundsToPlay;
	
	public SoundThread(Song s, MutableModel m) {
		
		if (instance != null) {
			throw new RuntimeException("Cannot have two sound instances!");
		}
		
		instance = this;
		song = s;
		model = m;
		soundsToPlay = new ConcurrentLinkedQueue<PlayableSound>();
	}
	
	public void updateSong(Song s) {
		song = s;
		nTicks = -1;
	}
	
	public void enqueueSound(PlayableSound sound) {
		soundsToPlay.add(sound);
	}
			
	private Synthesizer synthesizer;
	private Synthesizer getSynthesizer() {
		if(synthesizer == null) try {
			synthesizer = MidiSystem.getSynthesizer();
			// this returns an error in the console but is necessary to make sound
			synthesizer.open();
		} catch(Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		return synthesizer;
	}
	
	public void run() {
		long tick = 0;
		long tickPlusOne = 0;
		System.out.println(new File(".").getAbsolutePath());
		System.out.println("Running song");
		
		{	//So synth doesn't bleed through
			Synthesizer synth = getSynthesizer();
			synth.unloadAllInstruments(synth.getDefaultSoundbank());
			Soundbank sb;
			try {
				sb = MidiSystem.getSoundbank(new File("./FluidR3 GM2-2.SF2"));
				System.out.println(sb.toString());
				synth.loadAllInstruments(sb);
				System.out.println("Soundbank Loaded");
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (true) {
			try {
				Thread.sleep(10);
				
				//|| model.getBPM() == 0
				if(!model.isPlaying()) {
					continue;
				}
				
				if(soundsToPlay.size() > 0) {
					PlayableSound s;
					while((s = soundsToPlay.poll()) != null) {
						s.play(getSynthesizer(), song.getTempo());
						System.out.println("Playing note "+s.toString());
					}
				}
				
				Synthesizer synth = getSynthesizer();
				if(tick > synth.getMicrosecondPosition()) continue;
				else {
					tick = synth.getMicrosecondPosition() + (long)(1000000/song.getTempo()) - synth.getLatency();
					tickPlusOne = tick + (long)(1000000/song.getTempo());
				}
				nTicks++;
				model.updateTick((int) nTicks);
				GUI.getInstance().update();
				
				Receiver rcvr = synth.getReceiver();
				// ch0.programChange(song.getLayers().iterator().next().getVoice().getMidiVoice());
				ShortMessage msg = new ShortMessage();
				msg.setMessage(ShortMessage.STOP);
				rcvr.send(msg, -1);
				for (ImmutableLayer layer : song.getLayers()) {
					int midiVoice = layer.getVoice().getMidiVoice();
					int channel;
					if (midiVoice <= 128) { 
				    	Instrument[]  instruments  = synthesizer.getLoadedInstruments();
					    //synthesizer.loadInstrument(instruments[midiVoice]);
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
						if(row.isLit(i)) {
							msg = new ShortMessage();
							msg.setMessage(ShortMessage.NOTE_ON, channel, row.getNote(), layer.getVelocity());
							rcvr.send(msg, tick + layer.getLayerNumber() - synth.getMicrosecondPosition());
						} else {
							msg = new ShortMessage();
							//Equal to a note off since velocity = 0
							msg.setMessage(ShortMessage.NOTE_ON, channel, row.getNote(), 0);
							rcvr.send(msg, tick - layer.getLayerNumber());
						}
					}
				}
				msg.setMessage(ShortMessage.START);
				rcvr.send(msg, -1);
			/*} catch (InvalidMidiDataException | MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
