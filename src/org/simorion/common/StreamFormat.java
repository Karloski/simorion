package org.simorion.common;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.model.MutableModel;

public interface StreamFormat {

	public byte[] toBytes(ImmutableModel m);
	
	public void fromBytes(MutableModel w, byte[] bytes);
	
}
