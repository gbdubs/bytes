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
			ThreeSATPredicate tsp = new ThreeSATPredicate(getExpressionNumber(i));
			boolean classicSat = tsp.satisfiable() != null;
			boolean fancySat = ! (new PredicateSolver(tsp)).solve().equals(BigInteger.ZERO);
			assertTrue(classicSat == fancySat);
		}
	}
	
	public static String getExpressionNumber(int i){
		String first = getSubExpressionNumber(i / 64);
		String second = getSubExpressionNumber((i % 64)/8);
		String third = getSubExpressionNumber(i % 8);
		return first + " ^ " + second + " ^ " + third;
	}
	
	public static String getSubExpressionNumber(int i){
		String result = "(";
		if (i / 4 == 1){
			result = result + "!";
		}
		result = result + "0 v ";
		if (i / 2 >= 2){
			result = result + "!";
		}
		result = result + "1 v ";
		if (i % 2 == 0){
			result = result + "!";
		}
		result = result + "2)";
		return result;
	}
	
}
