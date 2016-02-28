package org.simorion.engine;

import java.util.Arrays;
import java.util.Collection;

import org.simorion.common.MutableLayer;
import org.simorion.common.MutableRow;
import org.simorion.common.Voice;
import org.simorion.common.util.Util;
import org.simorion.common.util.Util.Pair;

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
	
	public BasicLayer(Collection<MutableRow> rows, Voice voice, byte velocity, int layerNumber, int loopPoint) {
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

	@Override
	public void setLoopPoint(byte loopPoint) {
		this.loopPoint = loopPoint;
	}

	@Override
	public void setVelocity(byte velocity) {
		this.velocity = (byte) velocity;
	}

	@Override
	public void setVoice(Voice v) {
		voice = v;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof BasicLayer) {
			BasicLayer bl = (BasicLayer) o;
			for(Pair<MutableRow, MutableRow> pair : Util.zip(
					Util.iterable(rows), Util.iterable(bl.rows))) {
				if(!pair.left.equals(pair.right)) return false;
			}
			return (voice == bl.voice &&
					velocity == bl.velocity &&
					layerNumber == bl.layerNumber &&
					loopPoint == bl.loopPoint);
		} else return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BasicLayer {[");
		for(MutableRow row : rows) {
			sb.append(row.toString());
			sb.append(",");
		}
		sb
			.append("], velocity = ").append(velocity)
			.append(", loopPoint = ").append(loopPoint)
			.append(", voice = ").append(voice.toString())
			.append("}");
		return sb.toString();
	}

}
