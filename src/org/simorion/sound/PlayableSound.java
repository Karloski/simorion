package org.simorion.sound;

import javax.sound.midi.Synthesizer;

/**
 * A simple interface for playable notes, either in a sequence or individually.
 * 
 * @author Edmund Smith
 */
public interface PlayableSound {

	/**
	 * Play the PlayableSound with the given Synthesizer. Only for use from the
	 * sound thread.
	 * @param synth The Synthesizer to be played on.
	 */
	void play(Synthesizer synth);
	
}
