package org.simorion.common;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class SoundSystem implements SoundSystemInterface {
	
	private byte[] velocities = new byte[16];
	private int[] voices = new int[16];
	private float tempo = 1000;
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
		this.tempo = 60000/tempo;
	}
	
	public void updateSequence(int layer, int xGrid, int yGrid){
		if (performanceSequence[layer*16 + xGrid][yGrid] == 0) {
			performanceSequence[layer*16 + xGrid][yGrid] = 1;
		}
		else {
			performanceSequence[layer*16 + xGrid][yGrid] = 0;
		}
		System.out.println(performanceSequence[0][0]);
	}
	
	
	
	
	public void play(){
		while (stop == false) {
			for(int currentLayer = 0; currentLayer < 16; currentLayer++) {
				playColumn(performanceSequence[16*currentLayer + currentColumn], voices[currentLayer], velocities[currentLayer]);
			}
			if(loopPoint == 0) {
				currentColumn++;
			}
			else {
				if((currentColumn + 1) >= loopPoint) {
					currentColumn = 0;
				}
				else {
					currentColumn++;
				}
			}
		}		
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
