package org.simorion.engine;

import org.simorion.common.MutableRow;

/**
 * Basic implementation of a Row and WritableRow. Is backed by an integer, where
 * each bit corresponds to that cell being on or off
 * 
 * @author Edmund Smith
 */
public class BasicRow implements MutableRow {

	/**
	 * The bitstring representing the cells being on or off
	 */
	int lights;
	/**
	 * The MIDI note number
	 */
	byte note;
	
	public BasicRow() {
		lights = 0;
	}

	public boolean isLit(int cell) {
		return (lights & (1 << cell)) != 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getNote() {
		return note;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLit() {
		return lights;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLit(int cell) {
		lights |= 1 << cell;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUnlit(int cell) {
		lights &= ~(1 << cell);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleLit(int cell) {
		lights ^= 1 << cell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNote(byte note) {
		this.note = note;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void applyMask(int and, int or) {
		lights &= and;
		lights |= or;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void applyXor(int xor) {
		lights ^= xor;
	}

	@Override
	public int cellCount() {
		return 16;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof BasicRow) {
			BasicRow br = (BasicRow) o;
			return br.lights == lights && br.note == note;
		} else return false;
	}
	
	@Override
	public String toString() {
		return "Row { "+Integer.toBinaryString(lights) + ", note = " + note + "}";
	}
	
}
