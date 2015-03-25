package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import smart.PredicateSolver;
import smart.RandomPredicateGenerator;
import smart.ThreeSATPredicate;

public class TestSolvingVector {

	@Test
	public void testResultSatisfies(){
		for(int i = 0; i < 1000; i++){
			ThreeSATPredicate tsp = RandomPredicateGenerator.generateRandomThreeSat(6, 6);
			PredicateSolver ps = new PredicateSolver(tsp);
			boolean[] result = ps.solve();
			if (result != null){
				assertTrue(tsp.satisfiedBy(result));
			}
		}
	}
}
