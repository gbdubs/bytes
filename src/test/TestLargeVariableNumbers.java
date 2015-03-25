package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import smart.PredicateSolver;
import smart.RandomPredicateGenerator;
import smart.ThreeSATPredicate;

public class TestLargeVariableNumbers {

	@Test
	public void testRandomCorrectWithTenVariables(){
		int trials = 10000;
		int variables = 8;
		int expressions = 40;
		ThreeSATPredicate tsp = null;
		boolean equal = true;
		int unsat = 0;
		while (equal && trials-- > 0){
			tsp = RandomPredicateGenerator.generateRandomThreeSat(expressions, variables);
			boolean satisfiable = tsp.satisfiable() != null;
			boolean fancySat = (new PredicateSolver(tsp)).solve() != null;
			equal = (satisfiable == fancySat);
			if (!satisfiable) unsat++;
			if (!equal){
				ThreeSATPredicate.PRINT = true;
				System.out.println(tsp);
				System.out.println((new PredicateSolver(tsp)).solve().toString());
				assertTrue(false);
			} 
		}
		System.out.println("ALL GOOD AMIGOS. UNSAT=" + unsat);
		assertTrue(true);
	}
}
