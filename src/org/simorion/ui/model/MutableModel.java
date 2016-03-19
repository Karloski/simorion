package org.simorion.ui.model;

import org.simorion.common.MutableLayer;
import org.simorion.common.Song;
import org.simorion.common.Voice;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;
import org.simorion.sound.PlayableSound;

/**
 * Class allowing a Model to be written to for the middle-end, primarily
 * for Stream use. Not for use within the View section of code.
 * @author Edmund Smith
 */
public interface MutableModel extends ImmutableModel {

	/**
	 * Serialises a stream into the model, overwriting the previous state
	 * @param stream The stream to serialise data from
	 * @param format The format for serialising the data
	 */
	public void receiveFromStream(SongReader stream, SongFormat format);

	/**
	 * Sets the voice for a given mutable layer
	 * @param l The layer to set the voice of
	 * @param voice The new voice to give the layer
	 */
	public void setVoice(MutableLayer l, Voice voice);
	
	/**
	 * Sets the velocity for a given layer to the corresponding MIDI byte
	 * @param l The layer whose velocity is to be set
	 * @param velocity The MIDI byte representing velocity
	 */
	public void setVelocity(MutableLayer l, byte velocity);
	
	/**
	 * Sets the loop point for the given layer. 0 means full length loops
	 * @param l The layer being set
	 * @param loopPoint The loop point to set to
	 */
	public void setLoopPoint(MutableLayer l, byte loopPoint);
	
	/**
	 * Set the tempo for the song in beats per second
	 * @param beatsPerSecond
	 */
	public void setTempo(float beatsPerSecond);
	
	/**
	 * Move a layer to the top of the pile, making it the displayed/interactive
	 * layer
	 * @param layerID The layer index, starting from 0
	 */
	public void setTopmostLayer(int layerID);
	
	/**
	 * Light a cell in a given layer
	 * @param layer
	 * @param xLoc
	 * @param yLoc
	 */
	public void setLit(int layer, int xLoc, int yLoc);
	
	/**
	 * Unlight a cell in a given layer
	 * @param layer
	 * @param xLoc
	 * @param yLoc
	 */
	public void setUnLit(int layer, int xLoc, int yLoc);
	
	/**
	 * Toggle the state of a cell in a given layer
	 * @param layer
	 * @param xLoc
	 * @param yLoc
	 */
	public void toggleLit(int layer, int xLoc, int yLoc);
	
	/**
	 * Get a mutable reference to the topmost layer
	 */
	public MutableLayer getCurrentLayer();
	
	/**
	 * Update the text displayed to the LCD screen
	 * @param text The new LCD text
	 */
	public void setLCDDisplay(String text);
	
	/**
	 * Update the current tick/column location
	 * @param tick The current column to play.
	 */
	public void updateTick(int tick);
	
	/**
	 * Gets the current song as a Song object
	 */
	public Song getSong();
	
	/**
	 * Reverts the model back to an empty state, as if it had been re-constructed.
	 */
	public void reset();

	/**
	 * Sets the beats per minute to this value, treated as unsigned.
	 * @param b
	 */
	public void setBPM(byte bpm);
	
	/**
	 * Tells the sound thread to enqueue a sound to play immediately, via
	 * a message-passing-like interface
	 * @param sound The sound to play
	 */
	public void enqueueSound(PlayableSound sound);
	
	/**
	 * Tells the engine to start playing the song
	 */
	public void startPlaying();
	
	/**
	 * Tells the engine to stop playing the song
	 */
	public void stopPlaying();
}
