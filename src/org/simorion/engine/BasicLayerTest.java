package org.simorion.engine;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.simorion.common.MutableRow;
import org.simorion.common.Voice;

public class BasicLayerTest {

	private BasicLayer layer;
	
	@Before
	public void setUp() {
		layer = new BasicLayer(Arrays.asList((MutableRow)new BasicRow()), new DummyVoice(), (byte)0, 0, 0);
	}
	
	@Test
	public void testLoopPoint() {
		assertEquals(0, layer.getLoopPoint());
		layer.setLoopPoint((byte)19);
		assertEquals(19, layer.getLoopPoint());
	}
	
	@Test
	public void testVelocity() {
		assertEquals(0, layer.getVelocity());
		layer.setVelocity((byte)129); //Over byte.max
		byte b = layer.getVelocity();
		assertEquals(129, (b<0)?b+256:b);
	}
	
	@Test
	public void testVoice() {
		Voice v = new DummyVoice();
		layer.setVoice(v);
		assertEquals(v, layer.getVoice());
	}

}
