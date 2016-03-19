package org.simorion.sound;

import javax.sound.midi.Synthesizer;

public interface PlayableSound {

	public void play(Synthesizer synth, float tempo);
	
}
