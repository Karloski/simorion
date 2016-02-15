package org.simorion.common;

/**
 * Modelling a *proper* const-ness system, this is the writable side of the coin
 * @author Edmund Smith
 */
public interface WritableLayer extends Layer{

	public WritableRow getWritableRow(int row);
	
}
