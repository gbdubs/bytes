package test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import smart.PredicateSolver;
import smart.ThreeSATPredicate;

public class TestExhaustiveThreeVariables {

	@Test
	public void testExhaustiveThreeVars(){
		for(int i = 0; i < 8*8*8; i++){
			ThreeSATPredicate tsp = new ThreeSATPredicate(getExpression(i));
			boolean classicSat = tsp.satisfiable() != null;
			boolean fancySat = (new PredicateSolver(tsp)).solve() != null;
			assertTrue(classicSat == fancySat);
		}
	}
	
	public static String getExpression(int i){
		String first = getSubExpression(i / 64);
		String second = getSubExpression((i % 64)/8);
		String third = getSubExpression(i % 8);
		return first + " ^ " + second + " ^ " + third;
	}
	
	public static String getSubExpression(int i){
		String result = "(";
		if (i / 4 == 1){
			result = result + "!";
		}
		result = result + "0 v ";
		if (i % 4 >= 2){
			result = result + "!";
		}
		result = result + "1 v ";
		if (i % 2 == 1){
			result = result + "!";
		}
		result = result + "2)";
		return result;
	}
	
}
