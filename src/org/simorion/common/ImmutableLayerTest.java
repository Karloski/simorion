package org.simorion.common;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.simorion.common.util.Util;
import org.simorion.engine.BasicLayer;
import org.simorion.engine.BasicRow;
import org.simorion.engine.MIDIVoices;

/**
 * Test the ImmutableLayer interface with random data, 100 times over.
 * @author Edmund Smith
 */
@RunWith(Parameterized.class)
public class ImmutableLayerTest {

	private MutableLayer mut_layer;
	private ImmutableLayer layer;
	
	private MutableRow[] rows; 
	private int layerNumber;
	private int loopPoint;
	private byte velocity;
	private Voice voice;
	
	@Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[100][0]);
    }

    public ImmutableLayerTest() {
    }

	@Before
	public final void setUp() {
		Random rand = new Random();
		rows = Util.asArray(new BasicRow());
		rows[0].applyXor(rand.nextInt(1 << rows[0].cellCount()));
		layerNumber = rand.nextInt();
		loopPoint = rand.nextInt(256);
		velocity = (byte)rand.nextInt(256);
		voice = MIDIVoices.getVoice(rand.nextInt(256));
		mut_layer = new BasicLayer(Arrays.asList(rows), voice, velocity, layerNumber, loopPoint);
		layer = mut_layer;
	}
	
	@Test
	public final void testGetLayerNumber() {
		assertEquals(layerNumber, layer.getLayerNumber());
	}

	@Test
	public final void testGetVoice() {
		assertEquals(voice, layer.getVoice());
	}

	@Test
	public final void testGetVelocity() {
		assertEquals(velocity, layer.getVelocity());
	}

	@Test
	public final void testGetLoopPoint() {
		assertEquals(loopPoint, layer.getLoopPoint());
	}

	@Test
	public final void testGetRow() {
		assertEquals(rows[0], layer.getRow(0));
	}

	@Test
	public final void testGetRows() {
		assertEquals(Arrays.asList(rows), layer.getRows());
	}

}
