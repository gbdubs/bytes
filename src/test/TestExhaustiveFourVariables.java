package test;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import smart.PredicateSolver;
import smart.ThreeSATPredicate;

public class TestExhaustiveFourVariables {

	@Test
	public void testExhaustiveFourVarsThreeExprs(){
		for(int i = 0; i < 32*32*32; i++){
			ThreeSATPredicate tsp = new ThreeSATPredicate(getExpression(i));
			boolean classicSat = tsp.satisfiable() != null;
			boolean fancySat = ! (new PredicateSolver(tsp)).solve().equals(BigInteger.ZERO);
			assertTrue(classicSat == fancySat);
		}
	}
	
	public static String getExpression(int i){
		String first = getSubExpression(i / (32*32));
		String second = getSubExpression((i % (32*32))/32);
		String third = getSubExpression(i % 32);
		return first + " ^ " + second + " ^ " + third;
	}
	
	public static String getSubExpression(int i){
		int ommitted = i / 8;
		i = i % 8;
		int k = 0;
		if (ommitted == k) k++;
		String result = "(";
		if (i / 4 == 1){
			result = result + "!";
		}
		result = result + k + " v ";
		if (i % 4 >= 2){
			result = result + "!";
		}
		k++;
		if (ommitted == k) k++;
		result = result + k + " v ";
		if (i % 2 == 1){
			result = result + "!";
		}
		k++;
		if (ommitted == k) k++;
		result = result + k +")";
		return result;
	}
	
}
