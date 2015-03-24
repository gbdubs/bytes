package test;

import static org.junit.Assert.*;

import org.junit.Test;

import smart.SolvingPredicate;
import smart.SubPredicate;
import smart.ThreeSATPredicate;

public class TestPredicateNumberSystem {

	@Test
	public void testAffirmativesInOrder(){
		
		ThreeSATPredicate basic = new ThreeSATPredicate("(0 v 1 v 2)");
		assertEquals(basic.subPredicates.size(), 1);
		SubPredicate sp = basic.subPredicates.get(0);
		
		boolean[] affirmativesExpected = {true, true, true};
		int[] varsExpected = {0, 1, 2};
		assertArrayEquals(varsExpected, sp.variables);
		assertBooleanArrayEquals(affirmativesExpected, sp.affirmatives);
		
		SolvingPredicate solving = sp.getSolvingPredicate();
		assertArrayEquals(varsExpected, solving.getAlphabet());
		int state = (solving.getState()).intValue();
		assertEquals(127, state);
	}
	
	@Test
	public void testAffirmativesOutOfOrder(){
		
		ThreeSATPredicate basic = new ThreeSATPredicate("(2 v 1 v 0)");
		assertEquals(basic.subPredicates.size(), 1);
		SubPredicate sp = basic.subPredicates.get(0);
		
		boolean[] affirmativesExpected = {true, true, true};
		int[] varsExpected = {0, 1, 2};
		assertArrayEquals(varsExpected, sp.variables);
		assertBooleanArrayEquals(affirmativesExpected, sp.affirmatives);
		
		SolvingPredicate solving = sp.getSolvingPredicate();
		assertArrayEquals(varsExpected, solving.getAlphabet());
		int state = (solving.getState()).intValue();
		assertEquals(127, state);
	}
	
	@Test
	public void testAffirmativesNotInSequence(){
		
		ThreeSATPredicate basic = new ThreeSATPredicate("(1188 v 0 v 13)");
		assertEquals(basic.subPredicates.size(), 1);
		SubPredicate sp = basic.subPredicates.get(0);
		
		boolean[] affirmativesExpected = {true, true, true};
		int[] varsExpected = {0, 13, 1188};
		assertArrayEquals(varsExpected, sp.variables);
		assertBooleanArrayEquals(affirmativesExpected, sp.affirmatives);
		
		SolvingPredicate solving = sp.getSolvingPredicate();
		assertArrayEquals(varsExpected, solving.getAlphabet());
		int state = (solving.getState()).intValue();
		assertEquals(127, state);
	}

	@Test
	public void testNetativesInOrder(){
	
		ThreeSATPredicate basic = new ThreeSATPredicate("(!0 v 1 v !2)");
		assertEquals(basic.subPredicates.size(), 1);
		SubPredicate sp = basic.subPredicates.get(0);
			
		boolean[] affirmativesExpected = {false, true, false};
		int[] varsExpected = {0, 1, 2};
		assertArrayEquals(varsExpected, sp.variables);
		assertBooleanArrayEquals(affirmativesExpected, sp.affirmatives);
			
		SolvingPredicate solving = sp.getSolvingPredicate();
		assertArrayEquals(varsExpected, solving.getAlphabet());
		int state = (solving.getState()).intValue();
		assertEquals(251, state);
	}
	
	@Test
	public void testNetativesOutOfOrder(){
	
		ThreeSATPredicate basic = new ThreeSATPredicate("(!1532 v !1000 v 2)");
		assertEquals(basic.subPredicates.size(), 1);
		SubPredicate sp = basic.subPredicates.get(0);
			
		boolean[] affirmativesExpected = {true, false, false};
		int[] varsExpected = {2, 1000, 1532};
		assertArrayEquals(varsExpected, sp.variables);
		assertBooleanArrayEquals(affirmativesExpected, sp.affirmatives);
			
		SolvingPredicate solving = sp.getSolvingPredicate();
		assertArrayEquals(varsExpected, solving.getAlphabet());
		int state = (solving.getState()).intValue();
		assertEquals(253, state);
	}
	
	@Test
	public void testErrorCases(){
	
		ThreeSATPredicate basic = new ThreeSATPredicate("(!0 v !1 v 4)");
		assertEquals(basic.subPredicates.size(), 1);
		SubPredicate sp = basic.subPredicates.get(0);
			
		boolean[] affirmativesExpected = {false, false, true};
		int[] varsExpected = {0, 1, 4};
		assertArrayEquals(varsExpected, sp.variables);
		assertBooleanArrayEquals(affirmativesExpected, sp.affirmatives);
			
		SolvingPredicate solving = sp.getSolvingPredicate();
		assertArrayEquals(varsExpected, solving.getAlphabet());
		int state = (solving.getState()).intValue();
		assertEquals(239, state);
	}
	
	private void assertBooleanArrayEquals(boolean[] expected, boolean[] actual) {
		assertEquals(expected.length, actual.length);
		for(int i = 0; i < expected.length; i++){
			assertEquals(true, expected[i] == actual[i]);
		}
	}	
}
