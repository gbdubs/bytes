package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import smart.PredicateSolver;
import smart.RandomPredicateGenerator;
import smart.ThreeSATPredicate;

public class TestRandomFiveVariables {

	@Test
	public void testSmallLengthSatisfiability(){
		for(int i = 0; i < 1000; i++){
			ThreeSATPredicate tsp = RandomPredicateGenerator.generateRandomThreeSat(3, 5);
			PredicateSolver ps = new PredicateSolver(tsp);
			boolean actuallySatisfiable = tsp.satisfiable() != null;
			boolean fancySatisfiable = ps.solve() != null;
			if (actuallySatisfiable != fancySatisfiable){
				System.out.println("NOT EQUAL: " + tsp.toString());
				ThreeSATPredicate.PRINT = true;
				ps = new PredicateSolver(tsp);
				System.out.println(ps.solve());
				ThreeSATPredicate.PRINT = false;
				assertTrue(false);
			}
			assertTrue(actuallySatisfiable == fancySatisfiable);
		}
	}
	
	@Test
	public void testMediumLengthSatisfiability(){
		for(int i = 0; i < 1000; i++){
			ThreeSATPredicate tsp = RandomPredicateGenerator.generateRandomThreeSat(10, 5);
			PredicateSolver ps = new PredicateSolver(tsp);
			boolean actuallySatisfiable = tsp.satisfiable() != null;
			boolean fancySatisfiable = ps.solve() != null;
			if (actuallySatisfiable != fancySatisfiable){
				System.out.println("NOT EQUAL: " + tsp.toString());
				ThreeSATPredicate.PRINT = true;
				System.out.println(ps.solve().toString());
				ThreeSATPredicate.PRINT = false;
				assertTrue(false);
			}
			assertTrue(actuallySatisfiable == fancySatisfiable);
		}
	}
	
	@Test
	public void testLongLengthSatisfiability(){
		for(int i = 0; i < 1000; i++){
			ThreeSATPredicate tsp = RandomPredicateGenerator.generateRandomThreeSat(20, 5);
			PredicateSolver ps = new PredicateSolver(tsp);
			boolean actuallySatisfiable = tsp.satisfiable() != null;
			boolean fancySatisfiable = ps.solve() != null;
			if (actuallySatisfiable != fancySatisfiable){
				System.out.println("NOT EQUAL: " + tsp.toString());
				ThreeSATPredicate.PRINT = true;
				System.out.println(ps.solve().toString());
				ThreeSATPredicate.PRINT = false;
				assertTrue(false);
			}
			assertTrue(actuallySatisfiable == fancySatisfiable);
		}
	}
}
