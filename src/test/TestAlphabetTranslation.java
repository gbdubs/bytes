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
				{(byte) 0xff, (byte) 0xff, (byte) 0x33, (byte) 0x33, (byte) 0x0F, (byte) 0x0F, (byte) 0xCF, (byte) 0xFC}	
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
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xBE}	
		};
		
		int[][] originalAlphabet = {
				{0, 2, 3, 4, 5}	
		};
		
		int[][] targetAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		byte[][] expected = {
				{(byte) 0xff, (byte) 0xff, (byte) 0x55, (byte) 0x55, (byte) 0x0F, (byte) 0x5F, (byte) 0xAF, (byte) 0xFA}	
		};
		
		for(int i = 0; i < originals.length; i++){
			assertArrayEquals(expected[i], SolvingPredicate.repeatTwoCase(originals[i]));
		}
		for (int i = 0; i < originals.length; i++){
			BigInteger expect = new BigInteger(1, expected[i]);
			BigInteger original = new BigInteger(1, originals[i]);
			BigInteger result = SolvingPredicate.transposeAlphabetOneVariable(originalAlphabet[i], original, targetAlphabet[i]);
			assertEquals(expect, result);
		}
	}
	
	@Test
	public void testFourCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xBE}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 3, 4, 5}	
		};
		
		int[][] targetAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		byte[][] expected = {
				{(byte) 0xff, (byte) 0xff, (byte) 0x55, (byte) 0x55, (byte) 0x33, (byte) 0x77, (byte) 0xBB, (byte) 0xEE}	
		};
		
		for(int i = 0; i < originals.length; i++){
			assertArrayEquals(expected[i], SolvingPredicate.repeatFourCase(originals[i]));
		}
		for (int i = 0; i < originals.length; i++){
			BigInteger expect = new BigInteger(1, expected[i]);
			BigInteger original = new BigInteger(1, originals[i]);
			BigInteger result = SolvingPredicate.transposeAlphabetOneVariable(originalAlphabet[i], original, targetAlphabet[i]);
			assertEquals(expect, result);
		}
	}
	
	@Test
	public void testEightCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xBE}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2, 4, 5}	
		};
		
		int[][] targetAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		byte[][] expected = {
				{(byte) 0xff, (byte) 0xff, (byte) 0x55, (byte) 0x55, (byte) 0x37, (byte) 0x37, (byte) 0xBE, (byte) 0xBE}	
		};
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expect = new BigInteger(1, expected[i]);
			BigInteger original = new BigInteger(1, originals[i]);
			BigInteger result = SolvingPredicate.transposeAlphabetOneVariable(originalAlphabet[i], original, targetAlphabet[i]);
			assertEquals(expect, result);
		}
	}
	
	@Test
	public void testDuplicateCase(){
		byte[][] originals = {
				{(byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xBE}	
		};
		
		int[][] originalAlphabet = {
				{0, 1, 2, 3, 5}	
		};
		
		int[][] targetAlphabet = {
				{0, 1, 2, 3, 4, 5}	
		};
		
		byte[][] expected = {
				{(byte) 0xff, (byte) 0x55, (byte) 0xff, (byte) 0x55, (byte) 0x37, (byte) 0xBE, (byte) 0x37, (byte) 0xBE}	
		};
		
		for (int i = 0; i < originals.length; i++){
			BigInteger expect = new BigInteger(1, expected[i]);
			BigInteger original = new BigInteger(1, originals[i]);
			BigInteger result = SolvingPredicate.transposeAlphabetOneVariable(originalAlphabet[i], original, targetAlphabet[i]);
			assertEquals(expect, result);
		}
	}
	
}
