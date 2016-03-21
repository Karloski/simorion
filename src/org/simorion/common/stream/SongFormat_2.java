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
* Serialiser with support for custom scales
* 
* Format:
* Version number (1 byte)
* Layer count (1 byte)
* Rows per layer (1 byte)
* Cells per row (1 byte)
* foreach (1..row count + 1)
* 		Row note (1 byte)
* foreach (1..layer count + 1)
* 		Layer number (1 byte)
* 		Layer voice MIDI number (1 byte)
* 		Layer velocity MIDI number (1 byte)
* 		Layer loop point (1 byte), 0 for no loop
* 
* @author Edmund Smith
*/

public class SongFormat_2 implements SongFormat {

	/** {@inheritDoc} */
	@Override
	public byte[] serialise(ImmutableSong song) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("\u0002\u0010\u0010\u0010".getBytes("UTF-8"));
		baos.write(song.getBPM());
		for(ImmutableRow r : song.getLayers().iterator().next().getRows()) {
			baos.write(r.getNote());
		}
		for (ImmutableLayer l : song.getLayers()) {
			baos.write(l.getLayerNumber());
			baos.write(l.getVoice().getMidiVoice());
			baos.write(l.getVelocity());
			baos.write(l.getLoopPoint());
			for (ImmutableRow r : l.getRows()) {
				baos.write(r.getNote());
				baos.write((r.getLit() & 0xff00) >> 8);
				baos.write(r.getLit() & 0xff);
			}
		}
		return baos.toByteArray();
	}

	/** {@inheritDoc} */
	@Override
	public void deserialise(SongBuilder builder, byte[] data)
			throws UnsupportedSongFormatException, InsufficientSongDataException {
		if (data.length < 1) {
			throw new InsufficientSongDataException("Data is empty");
		}
		if (data[0] != getFormatID()) {
			throw new UnsupportedSongFormatException(
					"Found format id " + data[0] + ", when expecting " + getFormatID());
		}
		if (data.length < 5) {
			throw new InsufficientSongDataException("Not enough data, length = " + data.length);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		bais.read(); // Bypass the version byte at data[0]
		byte layers = (byte) bais.read();
		byte rows = (byte) bais.read();
		byte cells = (byte) bais.read();
		byte bpm = (byte) bais.read();
		builder.setLayerCount(layers).setRows(rows).setCells(cells).setBPM(bpm);
		byte[] scale = new byte[rows];
		
		try {
			bais.read(scale);
		} catch (IOException e) {
			e.printStackTrace();
			throw new InsufficientSongDataException("Not enough data, length = " + data.length);
		}
		
		for (int layer = 0; layer < layers; layer++) {
			SongBuilder.AddLayer addedLayer = builder.addLayer();
			addedLayer.setNumber(bais.read()).setMIDIVoice(bais.read()).setVelocity(bais.read())
					.setLoopPoint(bais.read());
			Iterable<SongBuilder.AddRow> addedRows = addedLayer.addRows(rows);
			for (SongBuilder.AddRow row : addedRows) {
				row.note = (byte) bais.read();
				row.setMask(bais.read() << 8 | bais.read());
			}
		}
		for(SongBuilder.AddLayer layer : builder.layers) {
			for(int row = 0; row < rows; row++) {
				layer.getRows().get(row).note = scale[row];
			}
		}
	}

	@Override
	public String getFormatName() {
		//Unlike so many of our wishful workforce, this class was immediately
		//rendered redundant as soon as I noticed Format_1 preserved the scales
		//(which I had forgotten to document, leading me to think it didn't).
		//I promptly created this, only to wonder how the backwards-compatible
		//Format_1 songs also retained their scale. Moral of the story?
		//Bad docs are worse than no docs, they will happily dash one's
		//codesmanship for the crime of trusting them
		return "OrganDream";
	}

	@Override
	public byte getFormatID() {
		return 2;
	}
}
