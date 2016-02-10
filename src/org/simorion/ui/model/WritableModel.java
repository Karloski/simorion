package org.simorion.ui.model;

import org.simorion.common.Layer;
import org.simorion.common.Voice;

/**
 * Class allowing a Model to be written to for the middle-end, primarily
 * for Stream use. Not for use within the MVC section of code.
 * @author Edmund Smith
 */
public interface WritableModel extends Model {

	public void setVoice(Layer l, Voice voice);
	
	public void setVelocity(Layer l, int velocity);
	
	public void setLoopPoint(Layer l, int loopPoint);
	
	public void setTempo(float beatsPerSecond);
	
}
