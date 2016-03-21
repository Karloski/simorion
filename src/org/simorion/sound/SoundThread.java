
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
import org.simorion.ui.view.CrashScreen;
import org.simorion.ui.view.GUI;

/** 
 * It reads the song layer by layer, row by row, until it gets to
 * isLit(nextRowToPlay). This avoids having a mirror of the data to constantly
 * update. The voice changing stuff is taken from SoundSystem and converted to
 * ShortMessages, which pretty much correspond 1:1 to the method calls anyway.
 * 
 * Synchronization is done through using MIDI's internal microsecond clock;
 * every ~10ms the thread wakes up, checks if the last column has been played,
 * and if so, it schedules the next column to play. Each layer has a 1 us delay
 * to allow for voice changing - I don't know if MIDI messages can race
 * otherwise. The only 'features' left are note length (e.g. Piano terminates,
 * but Accordion stays permanently playing until NOTE_OFF is sent) and possibly
 * fixing any sound/channel overlap errors
 * 
 * @author Edmund Smith
 * @author Petar Krstic
 *
 */
public class SoundThread implements Runnable {
	
	public Song song;
	public MutableModel model;
	private long nTicks = -1;
	private static SoundThread instance;
	private Synthesizer synthesizer;
	
	protected ConcurrentLinkedQueue<PlayableSound> soundsToPlay;
	
	public SoundThread(Song s, MutableModel m) {
		
		if (instance != null) {
			throw new RuntimeException("Cannot have two sound instances!");
		}
		
		instance = this;
		song = s;
		model = m;
		soundsToPlay = new ConcurrentLinkedQueue<PlayableSound>();
		
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
		} catch (MidiUnavailableException e) {
			System.err.println("Error opening the sound system: Currently Unavailable");
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		loadSoundbank();
	}
	
	/**
	 * Loads the FluidR3 public domain soundbank for use. Thanks to the authors!
	 * @author Edmund Smith
	 */
	private void loadSoundbank() {
		synthesizer.unloadAllInstruments(synthesizer.getDefaultSoundbank());
		try {
			Soundbank sb = MidiSystem.getSoundbank(new File("./FluidR3 GM2-2.SF2"));
			System.out.println(sb.toString());
			synthesizer.loadAllInstruments(sb);
			System.out.println("Soundbank Loaded");
		} catch (Exception e) {
			synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
			System.err.println("Error loading sound bank");
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the current song to play from, in case of e.g. restarting.
	 * Is thread safe due to atomic reference assignment in Java, combined with
	 * the fact that this completely disregards the previous state, alleviating
	 * any need for a CAS operation. 
	 * 
	 * To be called by any thread. May race if two threads call simultaneously,
	 * which is why only one thread (the Engine thread) should ever call it.
	 * @param s
	 */
	public void updateSong(Song s) {
		song = s;
		nTicks = -1;
	}
	
	/**
	 * Enqueues a sound for play by the Sound Thread. Thread safe through use
	 * of concurrent-access-aware containers. The Sound Thread periodically
	 * (~10ms) checks the queue, then plays any sounds it finds there.
	 *  
	 * @param sound The sound to enqueue.
	 */
	public void enqueueSound(PlayableSound sound) {
		soundsToPlay.add(sound);
	}
	
	/**
	 * The main player loop. Every 10 ms, if the next tick isn't yet queued, it
	 * queues the next tick of song to be played. It also checks and plays any
	 * sounds it finds in the multi-pub/single(this)-sub concurrent sound queue
	 * 
	 * @author Edmund Smith (Midi messaging, threading)
	 * @author Petar Krstic (Voice changing, percussion)
	 */
	public void run() {
		long tick = 0;
		long tickPlusOne = 0;
		
		while (true) {
			try {
				Thread.sleep(10);
				
<<<<<<< HEAD
				//Don't play when stopped
=======
				//|| model.getBPM() == 0
>>>>>>> 790a395d76cc91e523fe96d6d6f7aa9d887c48c5
				if(!model.isPlaying()) {
					continue;
				}
				
				//Empty the sound queue
				if(soundsToPlay.size() > 0) {
					PlayableSound s;
					while((s = soundsToPlay.poll()) != null) {
						s.play(synthesizer, song.getTempo());
					}
				}
				
				//If the next tick has already been enqueued
				if(tick > synthesizer.getMicrosecondPosition()) continue;
				
				//Run the next tick
				tick = synthesizer.getMicrosecondPosition() + (long)(1000000/song.getTempo()) - synthesizer.getLatency();
				tickPlusOne = tick + (long)(1000000/song.getTempo());
				nTicks++;
				
				//Tell the model the new tick
				model.updateTick((int) (nTicks % 720720));
				
				//Update the GUI, since this will have updated the clock hand
				GUI.getInstance().update();
				
				Receiver rcvr = synthesizer.getReceiver();
				ShortMessage msg = new ShortMessage();
				
				//Pause the system
				msg.setMessage(ShortMessage.STOP);
				//I want it done yesterday!
				rcvr.send(msg, -1);
				
				for (ImmutableLayer layer : song.getLayers()) {
					int midiVoice = layer.getVoice().getMidiVoice();
					int channel;
					//Change to the voice for this layer
					if (midiVoice <= 128) { 
						//No adjustment needed for most instruments
					    msg.setMessage(ShortMessage.PROGRAM_CHANGE, 1, midiVoice, 0);
					    rcvr.send(msg, tick+layer.getLayerNumber()-synthesizer.getMicrosecondPosition());
					    channel = 1;
				    }
				    else { 
				    	//Adjust for percussion
				    	midiVoice -= 93;
					    msg.setMessage(ShortMessage.PROGRAM_CHANGE, 10, midiVoice, 0);
					    rcvr.send(msg, tick+layer.getLayerNumber()-synthesizer.getMicrosecondPosition());
				    	channel = 9;
				    }
					for (ImmutableRow row : layer.getRows()) {
						int loop = layer.getLoopPoint();
						//Can't mod 0
						loop = loop == 0 ? 16 : loop;
						
						//Get current clock hand column
						int i = (int)(nTicks % loop);
						if(row.isLit(i)) {
							msg = new ShortMessage();
							msg.setMessage(ShortMessage.NOTE_ON, channel, row.getNote(), layer.getVelocity());
							rcvr.send(msg, tick + layer.getLayerNumber() - synthesizer.getMicrosecondPosition());
						} else {
							msg = new ShortMessage();
							//Equal to a note off since velocity = 0
							msg.setMessage(ShortMessage.NOTE_ON, channel, row.getNote(), 0);
							rcvr.send(msg, tick - layer.getLayerNumber());
						}
					}
				}
				//Restart the system
				msg.setMessage(ShortMessage.START);
				//You stopped? You should have restarted an hour ago!
				rcvr.send(msg, -1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
