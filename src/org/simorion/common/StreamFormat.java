package org.simorion.common;

import org.simorion.ui.model.Model;
import org.simorion.ui.model.WritableModel;

public interface StreamFormat {

	public byte[] toBytes(Model m);
	
	public void fromBytes(WritableModel w, byte[] bytes);
	
}
