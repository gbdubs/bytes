package test;

import java.math.BigInteger;

import org.junit.Test;
import static org.junit.Assert.*;

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
			boolean fancySatisfiable = ! ps.solve().equals(BigInteger.ZERO);
			if (actuallySatisfiable != fancySatisfiable){
				System.out.println("NOT EQUAL: " + tsp.toString());
				ThreeSATPredicate.PRINT = true;
				System.out.println(ps.solve().toString());
				System.exit(1);
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
			boolean fancySatisfiable = ! ps.solve().equals(BigInteger.ZERO);
			if (actuallySatisfiable != fancySatisfiable){
				System.out.println("NOT EQUAL: " + tsp.toString());
				ThreeSATPredicate.PRINT = true;
				System.out.println(ps.solve().toString());
				System.exit(1);
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
			boolean fancySatisfiable = ! ps.solve().equals(BigInteger.ZERO);
			if (actuallySatisfiable != fancySatisfiable){
				System.out.println("NOT EQUAL: " + tsp.toString());
				ThreeSATPredicate.PRINT = true;
				System.out.println(ps.solve().toString());
				System.exit(1);
			}
			assertTrue(actuallySatisfiable == fancySatisfiable);
		}
	}
}
