package org.simorion.ui.model;

import org.simorion.common.MutableLayer;
import org.simorion.common.Voice;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;

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

	public void setVoice(MutableLayer l, Voice voice);
	
	public void setVelocity(MutableLayer l, byte velocity);
	
	public void setLoopPoint(MutableLayer l, byte loopPoint);
	
	public void setTempo(float beatsPerSecond);
	
	public void setTopmostLayer(int layerID);
	
	public void setLit(int layer, int xLoc, int yLoc);
	
	public void setUnLit(int layer, int xLoc, int yLoc);
	
	public void toggleLit(int layer, int xLoc, int yLoc);
	
	public MutableLayer getCurrentLayer();
	
	public void setLCDDisplay(String text);
	
}
