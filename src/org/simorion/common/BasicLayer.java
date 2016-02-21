package org.simorion.common;

import java.util.Arrays;
import java.util.Collection;

/**
 * Implementation for a Layer, using an array of WritableRows as the backing
 * 
 * @author Edmund Smith
 */
public class BasicLayer implements ReadonlyLayer, Layer, WritableLayer {

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
	
	/**
	 * Current output of the LCD.
	 */
	String lcdMessage;
	
	BasicLayer(Collection<WritableRow> rows, Voice voice, byte velocity, int layerNumber, int loopPoint, String lcdMessage) {
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReadonlyRow getReadonlyRow(int i) {
		return getRow(i);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<? extends ReadonlyRow> getReadonlyRows() {
		return Arrays.asList(rows);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<? extends Row> getRows() {
		return Arrays.asList(rows);
	}

	@Override
	public WritableRow getWritableRow(int row) {
		return rows[row];
	}

	@Override
	public String getLCDMessage() {
		return lcdMessage;
	}

}