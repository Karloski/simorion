package org.simorion.sound;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class SoundSystem implements SoundSystemInterface {
	
	// The default velocity for each layer is 60
	private byte[] velocities = {60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60};
	
	// Default Voice for each layer is -1 - Layer does not get played if voice is -1
	private int[] voices = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	
	// Default tempo and loop point is 0
	private float tempo = 60;
	private byte loopPoint = 0;
	
	// Performance sequence stores the current state of every button on every state
	// The first 16 ints in 256 are the 16 columns in layer 0
	// Therefore [1][5] is the 6th button in layer 0, column 1
	int[][] performanceSequence = new int[256][16];
	// stop is changed to true when Simorion is turned off
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
		this.tempo = tempo;
	}
	
	public void updateSequence(int layer, int xGrid, int yGrid){
		// Updates the sequence whenever a button is lit on a specific layer
		// If the button has just been lit then set that value to 1
		// Otherwise change it back to 0
		if (performanceSequence[layer*16 + xGrid][yGrid] == 0) {
			performanceSequence[layer*16 + xGrid][yGrid] = 1;
		}
		else {
			performanceSequence[layer*16 + xGrid][yGrid] = 0;
		}		
	}
	
	public void delay(int ms) {
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
		// Loop through every layer - play the current column where the clock hand is
		// Pass into playColumn the column of 1/0's and the velocity/voice for that layer
	}
	
	public void playColumn(int[] column, int voiceID, byte velocity) {
		// if voiceID > 128 then you are playing percussion and you need voiceID - 93
		// otherwise the voice is an instrument
		// column consists of 0/1's, play any 1's with the voiceID/Velocity/Tempo
		 
	}
	
	public void stop() {
		stop = true;
	}
}