package org.simorion.common;

import java.util.Collection;

/**
 * Implementation for a Layer, using an array of WritableRows as the backing
 * 
 * @author Edmund Smith
 */
public class BasicLayer implements Layer {

	WritableRow[] rows;
	Voice voice;
	
	/**
	 * MIDI byte value
	 */
	byte velocity;
	int layerNumber;
	
	/**
	 * Maximum cell index before looping
	 */
	int loopPoint;
	
	BasicLayer(Collection<WritableRow> rows, Voice voice, byte velocity, int layerNumber, int loopPoint) {
		this.rows = rows.toArray(new WritableRow[0]); //Allocates its own array
		//see http://shipilev.net/blog/2016/arrays-wisdom-ancients/ for details
		this.voice = voice;
		this.velocity = velocity;
		this.layerNumber = layerNumber;
		this.loopPoint = loopPoint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLayerNumber() {
		return layerNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Voice getVoice() {
		return voice;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getVelocity() {
		return velocity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLoopPoint() {
		return loopPoint;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row getRow(int i) {
		if(i < 0 || i >= rows.length) {
			throw new RuntimeException("Error: index "+i
					+" is outside the range 0 to "+rows.length);
		}
		return rows[i];
	}

}
