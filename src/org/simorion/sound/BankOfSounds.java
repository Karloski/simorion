package org.simorion.sound;

public class BankOfSounds {

	public static final PlayableSound GOOD_SOUND = new Tune(new SingleSound(3, 46, 1),new SingleSound(3, 48, 2));
	public static final PlayableSound BAD_SOUND = new Tune(new SingleSound(3, 80, 0.25f), new SingleSound(3, 38, 1));
	
}
