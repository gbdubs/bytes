package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import smart.SolvingPredicate;
import smart.ThreeSATPredicate;

public class TestNegatedCombination {

	@Test
	public void testNegatedCombination(){
		String[] testCases = {
				"(0 v 1 v 2) ^ (!0 v !1 v !2)", 
				"(0 v 1 v !2) ^ (!0 v !1 v 2)", 
				"(!0 v 1 v !2) ^ (!2 v 1 v !0)",
				"(0 v !1 v !2) ^ (!0 v !1 v !2)",
				"(0 v !3 v !4) ^ (!0 v !3 v !4)"
		};
		// Example 1 : (127) & (254) = 01111111 & 11111110 = 01111110 (126)
		// Example 2 : (247) & (239) = 11110111 & 11101111 = 11100111 (231)
		// Example 3 : (251) & (251) = 11111011 & 11111011 = 11111011 (251)
		// Example 4 : (253) & (254) = 11111101 & 11111110 = 11111100 (252)
		// Example 4 : (253) & (254) = 11111101 & 11111110 = 11111100 (252)
		
		int[] expectedResults = {126, 231, 251, 252, 252};
		int[][] expectedVariables = {{0,1,2},{0,1,2},{0,1,2},{0,1,2},{0,3,4}};		
		
		for(int i = 0; i < testCases.length; i++){
			ThreeSATPredicate tsp = new ThreeSATPredicate(testCases[i]);
			SolvingPredicate sp1 = tsp.subPredicates.get(0).getSolvingPredicate();
			SolvingPredicate sp2 = tsp.subPredicates.get(1).getSolvingPredicate();
			
			SolvingPredicate sp3 = SolvingPredicate.conjoin(sp1, sp2);
	
			assertArrayEquals(expectedVariables[i], sp3.getAlphabet());
			BigInteger biggie = BigInteger.valueOf(expectedResults[i]);
			assertEquals(biggie, sp3.getState());
		}
	}
}
