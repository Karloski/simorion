package org.simorion.common;

public class BasicRow implements Row, WritableRow {

	int lights;
	byte note;
	
	BasicRow() {
		lights = 0;
	}

	public boolean isLit(int cell) {
		return (lights & (1 << cell)) == 1;
	}

	@Override
	public byte getNote() {
		return note;
	}

	@Override
	public void setLit(int cell) {
		lights |= 1 << cell;
	}
	
	@Override
	public void setUnlit(int cell) {
		lights &= ~(1 << cell);
	}
	
	@Override
	public void toggleLit(int cell) {
		lights ^= 1 << cell;
	}

	@Override
	public void setNote(byte note) {
		this.note = note;
	}
	
	@Override
	public void applyMask(int and, int or) {
		lights &= and;
		lights |= or;
	}
	
}
