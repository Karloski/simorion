package org.simorion.sound;

/**
 * Simple collection of pre-defined sounds that are played for specific events
 * 
 * @author Edmund Smith
 * @author Karl Brown
 */
public class BankOfSounds {

	public static final PlayableSound GOOD_SOUND = new Tune(new SingleSound(3, 80, 0.25f),new SingleSound(3, 85, 2f));
	public static final PlayableSound BAD_SOUND = new Tune(new SingleSound(3, 80, 0.25f), new SingleSound(3, 38, 1));
	public static final PlayableSound ON_SOUND = new Tune(
			new SingleSound(3, 60, 0.25f), 
			new SingleSound(3, 60, 0.25f), 
			new SingleSound(3, 62, 0.25f),
			new SingleSound(3, 59, 0.25f),
			new SingleSound(3, 67, 0.25f));
	public static final PlayableSound OFF_SOUND = new Tune(
			 			new SingleSound(3, 48, 0.25f),
			 			new SingleSound(3, 48, 0.25f), 
			 			new SingleSound(3, 48, 0.25f),
			 			new SingleSound(3, 50, 0.25f),
			 			new SingleSound(3, 48, 0.25f));
}
