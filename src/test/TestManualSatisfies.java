package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import smart.SolvingPredicate;

public class TestManualSatisfies {

	@Test
	public void testManualSatisfies(){
		int[] alpha = {0, 2, 4};
		boolean[] state = {true, false, true};
		SolvingPredicate sp = new SolvingPredicate(alpha, state);
		
		boolean[][] inputs = {
				{true, false, false, false, false},
				{false, true, false, false, false},
				{true, false, true, true, false},
				{false, false, true, false, false},
				{false, false, true, false, true},
				{false, true, true, true, false},
		};
		boolean[] expectations = {
				true,
				true,
				true,
				false,
				true,
				false
		};
		
		for (int i = 0; i < inputs.length; i++){
			assertEquals(expectations[i], sp.satisfiedBy(inputs[i]));
		}
	}
}
