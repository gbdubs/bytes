package test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import smart.SolvingPredicate;

public class TestSingleByteConversions {

	@Test
	public void testSingleByteCollapse(){
		byte[][] originals = {
				{(byte) 0x85}, {(byte) 0x85}, {(byte) 0x85},
				{(byte) 0xD4}, {(byte) 0xD4}, {(byte) 0xD4},
				{(byte) 0x91}, {(byte) 0x91}, {(byte) 0x91}
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2}, {0, 1, 2}, {0, 1, 2},
				{0, 1, 2}, {0, 1, 2}, {0, 1, 2},
				{0, 1, 2}, {0, 1, 2}, {0, 1, 2}
		};
		
		int[] toRemove = {
				0, 1, 2,
				0, 1, 2,
				0, 1, 2
		};
		
		byte[][] expected = {
				{(byte) 0x0B},{(byte) 0x09},{(byte) 0x0D},
				{(byte) 0x0E},{(byte) 0x0D},{(byte) 0x0D},
				{(byte) 0x0D},{(byte) 0x0D},{(byte) 0x09}		
		};
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
	
	@Test
	public void testCollapseToSingleVariable(){
		byte[][] originals = {
				{(byte) 0x08}, {(byte) 0x0D}, {(byte) 0x0A}, {(byte) 0x05},
				{(byte) 0x08}, {(byte) 0x0D}, {(byte) 0x0A}, {(byte) 0x05},
		};
		
		int[][] originalAlphabet = {
				{0, 1}, {0, 1}, {0, 1}, {0, 1},
				{0, 1}, {0, 1}, {0, 1}, {0, 1}
		};
		
		int[] toRemove = {
				0, 0, 0, 0,
				1, 1, 1, 1
		};
		
		byte[][] expected = {
				{(byte) 0x02},{(byte) 0x03},{(byte) 0x03},{(byte) 0x03},
				{(byte) 0x02},{(byte) 0x03},{(byte) 0x02},{(byte) 0x01}
		};
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
	
	@Test
	public void testSingleByteReplication(){
		byte[][] originals = {
				{(byte) 0x0D}, {(byte) 0x04}, {(byte) 0x06}, {(byte) 0x03}, {(byte) 0x02}, {(byte) 0x01},
				{(byte) 0x0D}, {(byte) 0x04}, {(byte) 0x06}, {(byte) 0x03}, {(byte) 0x02}, {(byte) 0x01},
				{(byte) 0x0D}, {(byte) 0x04}, {(byte) 0x06}, {(byte) 0x03}, {(byte) 0x02}, {(byte) 0x01}
		};
		
		int[][] originalAlphabet = {
				{1, 3}, {1, 3}, {1, 3}, {1, 3}, {1, 3}, {1, 3},
				{1, 3}, {1, 3}, {1, 3}, {1, 3}, {1, 3}, {1, 3},
				{1, 3}, {1, 3}, {1, 3}, {1, 3}, {1, 3}, {1, 3}
		};
		
		int[][] newAlphabet = {
				{0, 1, 3}, {0, 1, 3}, {0, 1, 3}, {0, 1, 3}, {0, 1, 3}, {0, 1, 3},
				{1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3},
				{1, 3, 4}, {1, 3, 4}, {1, 3, 4}, {1, 3, 4}, {1, 3, 4}, {1, 3, 4}
		};
		
		byte[][] expected = {
				{(byte) 0xF3},{(byte) 0x30},{(byte) 0x3C},{(byte) 0x0F},{(byte) 0x0C},{(byte) 0x03},
				{(byte) 0xF5},{(byte) 0x50},{(byte) 0x5A},{(byte) 0x0F},{(byte) 0x0A},{(byte) 0x05},
				{(byte) 0xDD},{(byte) 0x44},{(byte) 0x66},{(byte) 0x33},{(byte) 0x22},{(byte) 0x11}		
		};
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			BigInteger result = SolvingPredicate.transposeAlphabet(originalAlphabet[i], new BigInteger(1, originals[i]), newAlphabet[i]);
			assertEquals(expectation, result);
		}
	}
	
}
