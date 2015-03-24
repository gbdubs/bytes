package test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import smart.SolvingPredicate;

public class TestAlphabetTranslation {

	@Test
	public void testOneCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x33, (byte) 0xBE}	
		};
		
		int[][] originalAlphabet = {
				{1, 2, 3, 4, 5}	
		};
		
		int[][] targetAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		byte[][] expected = {
				{(byte) 0xff, (byte) 0xff, (byte) 0x33, (byte) 0x33, (byte) 0xf0, (byte) 0xf0, (byte) 0xfc, (byte) 0xcf}	
		};
		
		for(int i = 0; i < originals.length; i++){
			assertArrayEquals(expected[i], SolvingPredicate.repeatOneCase(originals[i]));
		}
		for (int i = 0; i < originals.length; i++){
			BigInteger expect = new BigInteger(1, expected[i]);
			BigInteger original = new BigInteger(1, originals[i]);
			BigInteger result = SolvingPredicate.transposeAlphabetOneVariable(originalAlphabet[i], original, targetAlphabet[i]);
			assertEquals(expect, result);
		}
	}
	
	@Test
	public void testTwoCase(){
		
	}
	
	@Test
	public void testFourCase(){
		
	}
	
	@Test
	public void testEightCase(){
		
	}
	
	@Test
	public void testDuplicateCase(){
		
	}
	
}
