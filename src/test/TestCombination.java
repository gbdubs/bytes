package test;

import java.math.BigInteger;

import static org.junit.Assert.*;

import org.junit.Test;

import smart.SolvingPredicate;
import smart.ThreeSATPredicate;

public class TestCombination {

	@Test
	public void testTrivialCombination(){
		String[] testCases = {
				"(0 v 1 v 2) ^ (0 v 1 v 2)", "(3 v 4 v 5) ^ (3 v 4 v 5)", 
				"(8 v 0 v 100) ^ (0 v 100 v 8)", "(10 v 100 v 7) ^ (10 v 100 v 7)" 
		};
		int[][] expectedVariables = {{0,1,2},{3,4,5},{0,8,100},{7,10,100}};
		
		for(int i = 0; i < testCases.length; i++){
		
			ThreeSATPredicate tsp = new ThreeSATPredicate(testCases[i]);
			SolvingPredicate sp1 = tsp.subPredicates.get(0).getSolvingPredicate();
			SolvingPredicate sp2 = tsp.subPredicates.get(1).getSolvingPredicate();
			
			SolvingPredicate sp3 = SolvingPredicate.conjoin(sp1, sp2);
			
			int[] expected = expectedVariables[i];
			assertArrayEquals(expected, sp3.getAlphabet());
			BigInteger biggie = BigInteger.valueOf(127L);
			assertEquals(biggie, sp3.getState());
		}
	}
	
	@Test
	public void testOneVarDifferentCombinations(){
		String[] testCases = {
				"(1 v 3 v 5) ^ (1 v 3 v 5)", "(1 v 3 v 5) ^ (1 v 3 v 6)", 
				"(1 v 3 v 5) ^ (1 v 3 v 4)", "(1 v 3 v 5) ^ (1 v 2 v 3)", 
				"(1 v 3 v 5) ^ (0 v 3 v 5)"
		};
		int[][] expectedVariables = {{1,3,5},{1,3,5,6},{1,3,4,5},{1,2,3,5},{0,1,3,5}};
		
		// Expectation 3 : 01111111
		// Expectation 2 : 01110111 01111111
		// Expectation 3 : 01110111 01111111
		// Expectation 4 : 01011111 01111111
		// Expectation 5 : 00011111 11111111
		int[] expectedValues = {127, 30591, 30591, 24447, 8191};
		
		for(int i = 0; i < testCases.length; i++){
		
			ThreeSATPredicate tsp = new ThreeSATPredicate(testCases[i]);
			SolvingPredicate sp1 = tsp.subPredicates.get(0).getSolvingPredicate();
			SolvingPredicate sp2 = tsp.subPredicates.get(1).getSolvingPredicate();
			
			SolvingPredicate sp3 = SolvingPredicate.conjoin(sp1, sp2);
			
			int[] expected = expectedVariables[i];
			assertArrayEquals(expected, sp3.getAlphabet());
			BigInteger biggie = BigInteger.valueOf(expectedValues[i]);
			assertEquals(biggie, sp3.getState());
		}
	}
	
	@Test
	public void testTwoVarDifferentCombinations(){
		String[] testCases = {
				"(1 v 3 v 5) ^ (0 v 1 v 2)", 
				"(1 v 3 v 5) ^ (0 v 1 v 4)", 
				"(1 v 3 v 5) ^ (0 v 1 v 6)", 
				"(1 v 3 v 5) ^ (2 v 3 v 4)",
				"(1 v 3 v 5) ^ (2 v 5 v 6)",
				"(1 v 3 v 5) ^ (4 v 5 v 6)",
				"(1 v 3 v 5) ^ (3 v 6 v 7)",
		};
		
		int[][] expectedVariables = {{0,1,2,3,5}, {0,1,3,4,5}, {0,1,3,5,6}, {1,2,3,4,5}, {1,2,3,5,6}, {1,3,4,5,6}, {1,3,5,6,7}};
		
		// Expectation 1 : 00110011 01111111 01111111 01111111
		// Expectation 2 : 00110111 00111111 01110111 11111111
		// Expectation 3 : 00110111 01110111 00111111 11111111
		// Expectation 4 : 00011111 01011111 00111111 11111111
		// Expectation 5 : 00010011 11111111 01011111 11111111
		// Expectation 6 + 7 Were not checked by hand.
		long[] expectedValues = {863993727L, 926906367L, 930562047L, 526335999L, 335503359L, 134182911L, 863993727L};
		
		for(int i = 0; i < testCases.length; i++){
		
			ThreeSATPredicate tsp = new ThreeSATPredicate(testCases[i]);
			SolvingPredicate sp1 = tsp.subPredicates.get(0).getSolvingPredicate();
			SolvingPredicate sp2 = tsp.subPredicates.get(1).getSolvingPredicate();
			
			SolvingPredicate sp3 = SolvingPredicate.conjoin(sp1, sp2);
			
			int[] expected = expectedVariables[i];
			assertArrayEquals(expected, sp3.getAlphabet());
			BigInteger biggie = BigInteger.valueOf(expectedValues[i]);
			assertEquals(biggie, sp3.getState());
		}
	}
	
}
