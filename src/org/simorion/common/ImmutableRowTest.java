package org.simorion.common;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.simorion.engine.BasicRow;

/**
 * Runs tests 100 times with randomised data
 * @author Edmund Smith
 */
@RunWith(Parameterized.class)
public class ImmutableRowTest {

	@Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[100][0]);
    }
    
    private int lit;
    private byte note;
    private ImmutableRow row;
    private MutableRow mut_row;

    public ImmutableRowTest() {
    }
	
	@Before
	public void setUp() throws Exception {
		mut_row = new BasicRow();
		row = mut_row;
		Random rand = new Random();
		lit = rand.nextInt(1 << row.cellCount());
		mut_row.applyXor(lit);
		note = (byte) rand.nextInt(256);
		mut_row.setNote(note);
	}

	@Test
	public final void testIsLit() {
		for(int i = 0; i < row.cellCount(); i++) {
			assertEquals((lit & (1<<i)) != 0, row.isLit(i));
		}
	}

	@Test
	public final void testGetLit() {
		assertEquals(lit, row.getLit());
	}

	@Test
	public final void testGetNote() {
		assertEquals(note, row.getNote());
	}

	@Test
	public final void testCellCount() {
		assertEquals(16, row.cellCount());
	}

}
