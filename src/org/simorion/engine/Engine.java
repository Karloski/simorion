package org.simorion.engine;

import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.SongWriter;
import org.simorion.ui.model.MutableModel;

public interface Engine extends MutableModel {

	
	//TODO
	//public void attach(final SoundSystem s);
	/**
	 * Load a song from the given SongReader using the given format
	 * @param stream The SongReader to receive the Song from
	 * @param format The SongFormat used in serialisation
	 */
	void receiveFromStream(SongReader stream, SongFormat format);
	
	/**
	 * Sends a song to a stream using the given format
	 * @param stream The SongWriter to send the Song to
	 * @param format The SongFormat used in serialisation
	 */
	void sendToStream(SongWriter stream, SongFormat format);
	
	/**
	 * Gets a unique per-instance ID for identification of multiple Simori-ONs
	 * on the same device. Will not change once set.
	 */
	int getInstanceID();
	
}
