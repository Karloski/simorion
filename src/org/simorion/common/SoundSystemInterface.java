package org.simorion.common;

import javax.sound.midi.Sequence;
import javax.sound.midi.Synthesizer;

public interface SoundSystemInterface {
	
	public void setVelocities(int layer, byte velocity);
	
	public void setVoices(int layer, int voiceInt);
	
	public void setLoopPoint(byte loopPoint);
	
	public void setLoopSpeed(float tempo);
	
	public void updateSequence(int layer, int xGrid, int yGrid);
	
	public void delay(int ms);
	
	public Synthesizer getSynthesizer();
	
	public void play();
	
	public void playColumn(int[] column, int voiceID, byte velocity);
	
	public void stop();
}
