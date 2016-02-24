package org.simorion.engine;

import java.util.Arrays;
import java.util.Collection;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.MutableLayer;
import org.simorion.common.MutableRow;
import org.simorion.common.Voice;

/**
 * Implementation for a Layer, using an array of WritableRows as the backing
 * 
 * @author Edmund Smith
 */
public class BasicLayer implements MutableLayer {

	MutableRow[] rows;
	Voice voice;
	
	/**
	 * MIDI byte value
	 */
	byte velocity;
	public int layerNumber;
	
	/**
	 * Maximum cell index before looping
	 */
	int loopPoint;
	
	/**
	 * Current output of the LCD.
	 */
	String lcdMessage;
	
	public BasicLayer(Collection<MutableRow> rows, Voice voice, byte velocity, int layerNumber, int loopPoint, String lcdMessage) {
		this.rows = rows.toArray(new MutableRow[0]); //Allocates its own array
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
	public MutableRow getRow(int i) {
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
	public Iterable<? extends MutableRow> getRows() {
		return Arrays.asList(rows);
	}

	@Override
	public String getLCDMessage() {
		return lcdMessage;
	}

}
