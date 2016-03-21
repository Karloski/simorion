package org.simorion.common.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.ImmutableRow;
import org.simorion.common.ImmutableSong;
import org.simorion.common.SongBuilder;
/**
 * Song serialiser with backwards compatibility
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
 * 		foreach (1..row count + 1)
 * 			Row note (1 byte)
 * 			Row lit cells (2 bytes)
 * 
 * @author Edmund Smith
 */

public class SongFormat_1 implements SongFormat {

	/** {@inheritDoc} */
	@Override
	public byte[] serialise(ImmutableSong song) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("\u0001\u0010\u0010\u0010".getBytes("UTF-8"));
		baos.write(song.getBPM());
		for(ImmutableLayer l : song.getLayers()) {
			baos.write(l.getLayerNumber());
			baos.write(l.getVoice().getMidiVoice());
			baos.write(l.getVelocity());
			baos.write(l.getLoopPoint());
			for(ImmutableRow r : l.getRows()) {
				baos.write(r.getNote());
				baos.write((r.getLit()&0xff00) >> 8);
				baos.write(r.getLit()&0xff);
			}
		}
		return baos.toByteArray();
	}

	/** {@inheritDoc} */
	@Override
	public void deserialise(SongBuilder builder, byte[] data) throws UnsupportedSongFormatException, InsufficientSongDataException {
		if(data.length < 1) {
			throw new InsufficientSongDataException("Data is empty");
		}
		if(data[0] != getFormatID()) {
			throw new UnsupportedSongFormatException("Found format id "+data[0]+", when expecting "+getFormatID());
		}
		if(data.length < 5) {
			throw new InsufficientSongDataException("Not enough data, length = "+data.length);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		bais.read(); //Bypass the version byte at data[0]
		byte layers = (byte)bais.read();
		byte rows = (byte)bais.read();
		byte cells = (byte)bais.read();
		byte bpm = (byte) bais.read();
		builder
			.setLayerCount(layers)
			.setRows(rows)
			.setCells(cells)
			.setBPM(bpm);
		for(int layer = 0; layer < layers; layer++) {
			SongBuilder.AddLayer addedLayer = builder.addLayer();
			addedLayer
				.setNumber(bais.read())
				.setMIDIVoice(bais.read())
				.setVelocity(bais.read())
				.setLoopPoint(bais.read());
			Iterable<SongBuilder.AddRow> addedRows = 
					addedLayer.addRows(rows);
			for(SongBuilder.AddRow row : addedRows) {
				row.note = (byte)bais.read();
				row.setMask(bais.read() << 8 | bais.read());
			}	
		}
	}

	@Override
	public String getFormatName() {
		//Everyone knows vets make the best violinists
		//Very few others can make cats bow to their every chirp
		return "Vetinary";
	}

	@Override
	public byte getFormatID() {
		return 1;
	}

}
