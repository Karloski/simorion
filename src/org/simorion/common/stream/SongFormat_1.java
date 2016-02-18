package org.simorion.common.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.simorion.common.ReadonlyLayer;
import org.simorion.common.ReadonlyRow;
import org.simorion.common.ReadonlySong;
import org.simorion.common.SongBuilder;
/**
 * Initial exploratory attempt at a song serialiser
 * 
 * Format:
 * Version number (1 byte)
 * Layer count (1 byte)
 * Rows per layer (1 byte)
 * Cells per row (1 byte)
 * foreach (1..layer count + 1)
 * 		Layer number (1 byte)
 * 		Layer voice MIDI number (1 byte)
 * 		Layer velocity MIDI number (1 byte)
 * 		Layer loop point (1 byte), 0 for no loop
 * 
 * 	EOF?
 * 
 * @author Edmund Smith
 */

public class SongFormat_1 implements SongFormat {

	/**
	 * Serialised a song into a byte array with the format _1
	 */
	@Override
	public byte[] serialise(ReadonlySong song) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("\u0001\u0016\u0016\u0016".getBytes("UTF-8"));
		for(ReadonlyLayer l : song.getLayers()) {
			byte[] b = new byte[4];
			b[0] = (byte)l.getLayerNumber();
			b[1] = (byte)l.getVoice().getMidiVoice();
			b[2] = (byte)l.getVelocity();
			b[3] = (byte)l.getLoopPoint();
			b[4] = (byte)song.getBPM();
			baos.write(b);
			for(ReadonlyRow r : l.getReadonlyRows()) {
				byte[] b1 = new byte[
				                     r.cellCount()/8+
				                     (r.cellCount()%8==0?0:1)
				                     ];
				for(int c = 0; c < r.cellCount()/8; c++) {
					//int index = c / 8;
					int index = c >> 3;
					//int bit = c % 8;
					int bit = c & 0x7;
					b1[index] |= (r.isLit(c)?1:0)<<bit;
				}
				baos.write(b1);
			}
		}
		return baos.toByteArray();
	}

	/** {@inheritDoc} */
	@Override
	public void deserialise(SongBuilder builder, byte[] data) {
		if(data[0] != (byte)1) {
			throw new RuntimeException("Wrong song format used");
		}
		byte layers = data[1];
		byte rows = data[2];
		byte cells = data[3];
		int offset = 4;
		builder
			.setLayerCount(layers)
			.setRows(rows)
			.setCells(cells);
		for(int layer = 0; layer < rows; layer++) {
			SongBuilder.AddLayer addedLayer = builder.addLayer();
			addedLayer
				.setNumber(data[offset+0])
				.setMIDIVoice(data[offset+1])
				.setVelocity(data[offset+2])
				.setLoopPoint(data[offset+3]);
			offset += 4;
			Iterable<SongBuilder.AddRow> addedRows = 
					addedLayer.addRows(rows);
			for(SongBuilder.AddRow row : addedRows) {
				row.setMask(data[offset]<<8|data[offset+1]);
				offset += 2;
			}	
		}
	}

}
