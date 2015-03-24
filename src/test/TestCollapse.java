package test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import smart.SolvingPredicate;

public class TestCollapse {

	@Test
	public void testMultipleCase(){
		byte[][] originals = {
				{(byte) 0xFF, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5F, (byte) 0xB2, (byte) 0x4C},
				{(byte) 0xFF, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5F, (byte) 0xB2, (byte) 0x4C},
				{(byte) 0xFF, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5F, (byte) 0xB2, (byte) 0x4C}
		};

		int[][] originalAlphabet = {
				{0, 1, 2, 3, 4, 5},
				{0, 1, 2, 3, 4, 5}, 
				{0, 1, 2, 3, 4, 5}	
		};
		
		int[] toRemove = {3, 4, 5};
		
		byte[][] expected = {
				{(byte) 0xFF, (byte) 0xB7, (byte) 0x5f, (byte) 0xFE},
				{(byte) 0xFF, (byte) 0xF7, (byte) 0xB2, (byte) 0x5F},
				{(byte) 0xFF, (byte) 0x5F, (byte) 0xB7, (byte) 0xFE}
		};
		
		for (int i = 0; i < originals.length; i++){
			byte[] expect = expected[i];
			byte[] original = originals[i];
			byte[] actual = SolvingPredicate.collapseMultipleCase(original, (int) Math.pow(2, i));
			assertArrayEquals(expect, actual);
		}
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
	
	@Test
	public void testEightCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5f, (byte) 0xB2, (byte) 0x4C}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		int[] toRemove = {3};
		
		byte[][] expected = {
				{(byte) 0xff, (byte) 0xB7, (byte) 0x5f, (byte) 0xFE}	
		};
		
		for (int i = 0; i < originals.length; i++){
			byte[] expect = expected[i];
			byte[] original = originals[i];
			byte[] actual = SolvingPredicate.collapseEightCase(original);
			assertArrayEquals(expect, actual);
		}
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
	
	@Test
	public void testFourCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5f, (byte) 0xB2, (byte) 0x2C}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		int[] toRemove = {2};
		
		byte[][] expected = {
				{(byte) 0xf5, (byte) 0x7B, (byte) 0x0F, (byte) 0xBE}	
		};
		
		for (int i = 0; i < originals.length; i++){
			byte[] expect = expected[i];
			byte[] original = originals[i];
			byte[] actual = SolvingPredicate.collapseFourCase(original);
			assertArrayEquals(expect, actual);
		}
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
	
	@Test
	public void testTwoCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5f, (byte) 0xB2, (byte) 0x2C}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		int[] toRemove = {1};
		
		byte[][] expected = {
				{(byte) 0xF5, (byte) 0xFE, (byte) 0x07, (byte) 0xEB}	
		};
		
		for (int i = 0; i < originals.length; i++){
			byte[] expect = expected[i];
			byte[] original = originals[i];
			byte[] actual = SolvingPredicate.collapseTwoCase(original);
			assertArrayEquals(expect, actual);
		}
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
	
	@Test
	public void testOneCase(){
		byte[][] originals = {
				{(byte) 0xFF, (byte) 0x55, (byte) 0x37, (byte) 0xB2, (byte) 0x00, (byte) 0x5f, (byte) 0xB2, (byte) 0x2C}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		int[] toRemove = {0};
		
		byte[][] expected = {
				{(byte) 0xFF, (byte) 0x7D, (byte) 0x0F, (byte) 0xD6}	
		};
		
		for (int i = 0; i < originals.length; i++){
			byte[] expect = expected[i];
			byte[] original = originals[i];
			byte[] actual = SolvingPredicate.collapseOneCase(original);
			assertArrayEquals(expect, actual);
		}
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expectation = new BigInteger(1,expected[i]);
			SolvingPredicate sp = new SolvingPredicate(originalAlphabet[i], new BigInteger(1, originals[i]));
			BigInteger result = sp.collapseOnVariable(toRemove[i]).getState();
			assertEquals(expectation, result);
		}
	}
}
