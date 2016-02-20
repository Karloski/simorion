package org.simorion.common;

/**
 * Modelling a *proper* const-ness system, this is the writable side of the coin
 * @author Edmund Smith
 */
public interface MutableLayer extends ImmutableLayer{

	public MutableRow getRow(int row);
	
	public Iterable<? extends MutableRow> getRows();
	
}
