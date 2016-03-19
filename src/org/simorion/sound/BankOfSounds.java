package org.simorion.sound;

public class BankOfSounds {

	public static final PlayableSound GOOD_SOUND = new Tune(new SingleSound(3, 80, 0.25f),new SingleSound(3, 85, 2f));
	public static final PlayableSound BAD_SOUND = new Tune(new SingleSound(3, 80, 0.25f), new SingleSound(3, 38, 1));
	
}
