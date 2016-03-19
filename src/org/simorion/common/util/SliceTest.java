package org.simorion.common.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class SliceTest {

	@Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[100][0]);
    }
    
    private Integer[] arr;
	
    @Before
    public void setUp() {
    	
        Random r = new Random();
        for(int i = 0; i < 100; i++) {
        	arr = new Integer[r.nextInt(1024)+128];
			for (int j = 0; j < arr.length; j++) {
				arr[j] = r.nextInt();
			}
		}
	}
    
    
    @Test
    public void testSliceStarting() {
    	int i = new Random().nextInt(arr.length);
    	Iterable<Integer> slice = Util.slice(arr, i);
    	for(Integer si : slice) {
    		assertEquals(arr[i++], si);
    	}
    }
    
    @Test
    public void testSliceStartingEnding() {
    	int i = new Random().nextInt(arr.length/2);
    	int j = new Random().nextInt(arr.length/2);
    	Iterable<Integer> slice = Util.slice(arr, i, i+j);
    	for(Integer si : slice) {
    		assertEquals(arr[i++], si);
    		assertTrue(j --> 0);
    	}
    }
    
    
}
