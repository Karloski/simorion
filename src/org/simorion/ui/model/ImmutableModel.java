package org.simorion.ui.model;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.ImmutableSong;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongWriter;

public interface ImmutableModel {

	/**
	 * Serialise an ImmutableModel to a stream
	 * @param stream The SongWriter to send the song to
	 * @param format The format for serialising the ImmutableModel
	 */
	public void sendToStream(SongWriter stream, SongFormat format);
	
	/**
	 * Get the current tempo in beats per second
	 * @return the BPS of the song
	 */
	public float getTempo();
	
	/**
	 * Get a reference to the layer currently being manipulated
	 * @return Layer being manipulated
	 */
	public ImmutableLayer getCurrentLayer();
	
	/**
	 * Get the index of the layer currently being manipulated
	 * @return the layer's index
	 */
	public int getCurrentLayerId();
	
	/**
	 * Get the layer at index i
	 * @param i The index of the layer
	 * @return layer number i
	 */
	public ImmutableLayer getLayer(int i);
	
	//720720 is the lowest common multiple of [1..16] inclusive
	/**
	 * Gets the current tick number, which increments on every beat. It wraps
	 * at 720720, going back to 0.
	 * @return The current tick modulo 720720
	 */
	public int getTick();
	
	/**
	 * Gets the model's current display message
	 * @return
	 */
	public String getLCDDisplay();
	
	/**
	 * Gets a unique identifying ID for the instance for use in inter-device
	 * communication
	 * @return
	 */
	public int getInstanceID();
	
	/**
	 * Reference to the song being played
	 * @return The currently playing song
	 */
	public ImmutableSong getSong();
	
	/**
	 * Get the tempo of the current song being played in BPM
	 * @return The BPM of the song
	 */
	public byte getBPM();
	
	/**
	 * Gets whether the engine is currently playing music
	 * @return
	 */
	public boolean isPlaying();
	
}
