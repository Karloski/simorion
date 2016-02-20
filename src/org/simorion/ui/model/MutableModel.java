package org.simorion.ui.model;

import org.simorion.common.MutableLayer;
import org.simorion.common.Voice;

/**
 * Class allowing a Model to be written to for the middle-end, primarily
 * for Stream use. Not for use within the View section of code.
 * @author Edmund Smith
 */
public interface MutableModel extends ImmutableModel {

	public void setVoice(MutableLayer l, Voice voice);
	
	public void setVelocity(MutableLayer l, int velocity);
	
	public void setLoopPoint(MutableLayer l, int loopPoint);
	
	public void setTempo(float beatsPerSecond);
	
	void setLit(int layer, int xLoc, int yLoc);
	
	void setUnLit(int layer, int xLoc, int yLoc);
	
	void toggleLit(int layer, int xLoc, int yLoc);
	
}
