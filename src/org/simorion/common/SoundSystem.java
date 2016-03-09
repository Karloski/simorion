package org.simorion.common;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class SoundSystem implements SoundSystemInterface {
	
	private byte[] velocities = {60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60};
	// If no voice has been set and is at -1 then the loop will ignore playing that layer
	private int[] voices = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	private int tempo = 1000;
	private byte loopPoint = 0;
	int[][] performanceSequence = new int[256][16];
	private int currentColumn = 0;
	private boolean stop = false;
	
	
	private SoundSystem() {}
	
	private static SoundSystem instance;
	
	public static SoundSystem getInstance() {
		if (instance  == null) instance = new SoundSystem();
		return instance;
	}
	
	public void setVelocities(int layer, byte velocity) {
		velocities[layer] = velocity;
	}
	
	public void setVoices(int layer, int voiceInt){
		voices[layer] = voiceInt;
	}
	
	public void setLoopPoint(byte loopPoint){
		this.loopPoint = loopPoint;
	}
	
	public void setLoopSpeed(float tempo){
		// Not sure if the tempo is already in bpm when passed in as a parameter
		this.tempo = Math.round(60000/tempo);
	}
	
	public void updateSequence(int layer, int xGrid, int yGrid){
		// Updates the sequence whenever a button is lit on a specific layer
		if (performanceSequence[layer*16 + xGrid][yGrid] == 0) {
			performanceSequence[layer*16 + xGrid][yGrid] = 1;
		}
		else {
			performanceSequence[layer*16 + xGrid][yGrid] = 0;
		}
	}
	
	public void delay(int ms) {
		// This was in the workshop, should be necessary? O.o 
		try {
			Thread.sleep(ms);
		} catch(Exception ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	public Synthesizer getSynthesizer() {
		Synthesizer synthesizer = null;
		try {
			synthesizer = MidiSystem.getSynthesizer();
			// this returns an error in the console but is necessary to make sound
			synthesizer.open();
		} catch(Exception ex) {
			System.out.println(ex); System.exit(1);
		}
		return synthesizer;
	}
	
	public void play(){
		// Until the off button is pressed and stop() is called
		while (stop == false) {
			for(int currentLayer = 0; currentLayer < 16; currentLayer++) {
				// Go through all the layers 
				if (voices[currentLayer] != -1) {
					// only if a voice has been set, play the current column the loop point is at
					// pass through the layers voice and velocity for that layers column
					playColumn(performanceSequence[16*currentLayer + currentColumn], voices[currentLayer], velocities[currentLayer]);
				}
			}
			
			// updating the current column that needs to be played from the loop point
			if(loopPoint == 0) {
				// go to next column, later CC is reset to 0 if it reaches 16
				currentColumn++;
			}
			else {
				// currentColumn gets reset if it reaches loop point 
				// (or if loop point gets changed mid song to something lower than currentColumn)
				if((currentColumn + 1) >= loopPoint) {
					currentColumn = 0;
				}
				else {
					currentColumn++;
				}
			}
			if (currentColumn == 16) { currentColumn = 0; }
		}		
	}
	
	public void playColumn(int[] column, int voiceID, byte velocity) {
		// if voiceID > 128 then you are playing percussion and you need voiceID - 93
		// otherwise the voice is an instrument
		// column consists of 0/1's, play any 1's with the voiceID/Velocity/Tempo
		Synthesizer synthesizer = getSynthesizer();
	    MidiChannel[] midiChannels = synthesizer.getChannels();
	    int channel;
	    
	    if (voiceID <= 128) { 
	    	Instrument[]  instruments  = synthesizer.getDefaultSoundbank().getInstruments();
		    synthesizer.loadInstrument(instruments[voiceID]);
		    midiChannels[1].programChange(voiceID);
		    channel = 1;
	    }
	    else { 
	    	voiceID -= 93;
	    	midiChannels[9].programChange(voiceID);
	    	channel = 9;
	    }
	    
	    for (int i=0; i<16; i++) {
	    	if (column[i] == 1) {
	    		// 10*(i+1) is pitch
	    		midiChannels[channel].noteOn(10*(i+1), velocity);
	    	}
	    }
	    delay(10*tempo);
	    for (int i=0; i<16; i++) {
	    	if (column[i] == 1) {
	    		midiChannels[channel].noteOff(10*(i+1), velocity);
	    	}
	    } 
	}
	
	public void stop() {
		stop = true;
	}
}
