package org.simorion.engine;

import org.simorion.common.Voice;

/**
 * Dummy Voice implementation, with the same identical responses
 * 
 * @author Edmund Smith
 */
public class DummyVoice implements Voice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMidiVoice() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "Dummy grande";
	}

	
	
}
