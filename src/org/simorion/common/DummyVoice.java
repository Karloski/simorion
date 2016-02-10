package org.simorion.common;

/**
 * Dummy Voice implementation, with the same identical responses
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
