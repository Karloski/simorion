package org.simorion.common;

public class DummyVoice implements Voice {

	@Override
	public int getMidiVoice() {
		return 0;
	}

	@Override
	public String getName() {
		return "Dummy grande";
	}

	
	
}
