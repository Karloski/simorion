package org.simorion.sound;

import javax.sound.midi.Synthesizer;

/**
 * SoundSystem to play all the sounds, updates a multi-dimensional array whenever a performance mode note is pressed
 * You can also set all the voices and velocities for each layer and update it here
 * 
 * @author Petar Krstic
 *
 */
public interface SoundSystemInterface {
	
	/**
	 * Given a layer number will update the velocity for the particular layer
	 * 
	 * @param layer - the layer for which you want to set it's velocity
	 * @param velocity - the velocity for the layer
	 */
	public void setVelocities(int layer, byte velocity);
	
	/**
	 * Sets the voice for a particular layer
	 * 
	 * @param layer - the layer you are currently setting the voice for
	 * @param voiceInt - the voice number from the matrix keys - >128 is percussion
	 */
	public void setVoices(int layer, int voiceInt);
	
	/**
	 * Globally sets the loop point for the system
	 * 
	 * @param loopPoint - Column that you should loop around
	 */
	public void setLoopPoint(byte loopPoint);
	
	/**
	 * Globally set the loop speed for the system - bpm
	 * 
	 * @param tempo - the speed at which the system plays each column
	 */
	public void setLoopSpeed(float tempo);
	
	/**
	 * Whenever a button is pressed or dragged across in performance mode;
	 * The Sequence will be updated taking into account the layer and position of the button that has just been pressed
	 * 
	 * @param layer - The current layer the user is on
	 * @param xGrid - The Column the button is in
	 * @param yGrid - The Row the button is in
	 */
	public void updateSequence(int layer, int xGrid, int yGrid);
	
	/**
	 * Used to hold down a note for a given velocity
	 * 
	 * @param ms - How long to delay the system for in miliseconds
	 */
	public void delay(int ms);
	
	/**
	 * Gets the synthesizer from which all the sounds will play
	 * @return - A synthesizer 
	 */
	public Synthesizer getSynthesizer();
	
	/**
	 * Use the Sequence to loop around each layer and call playColumn() 
	 * This plays the current column the clock hand is at
	 */
	public void play();
	
	/** 
	 * Given a column from a layer and it's voice/velocity - play all the notes in the column which are lit
	 * Being lit is symbolised by being a "1"
	 * 
	 * @param column - An array consisting of 1/0's which symbolise the current clock hand column for a layer
	 * @param voiceID - The voice for that layer
	 * @param velocity - The velocity for that layer
	 */
	public void playColumn(int[] column, int voiceID, byte velocity);
	
	/**
	 * Stop playing the system when Off is pressed
	 */
	public void stop();
}