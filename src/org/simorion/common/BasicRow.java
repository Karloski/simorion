package org.simorion.common;

/**
 * Basic implementation of a Row and WritableRow. Is backed by an integer, where
 * each bit corresponds to that cell being on or off
 * @author Edmund Smith
 */
public class BasicRow implements Row, WritableRow {

	/**
	 * The bitstring representing the cells being on or off
	 */
	int lights;
	/**
	 * The MIDI note number
	 */
	byte note;
	
	BasicRow() {
		lights = 0;
	}

	public boolean isLit(int cell) {
		return (lights & (1 << cell)) == 1;
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

	@Override
	public int cellCount() {
		return 16;
	}
	
}
