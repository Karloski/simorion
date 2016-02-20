package org.simorion.engine;

import org.simorion.common.Song;
import org.simorion.ui.model.MutableModel;

public interface Engine extends MutableModel {

	
	//TODO
	//public void attach(final SoundSystem s);
	
	public void load(final Song s);
	
	public Song getSong();
	
}
