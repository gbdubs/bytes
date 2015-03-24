package smart;

import java.util.Arrays;

public class SpeedComparison {

	public static void main(String[] args){
		speedTest();
	}
	
	public static void speedTest(){	
		int[] numVariables = {5, 6, 7, 8, 9, 10};
		int[] numExpressions = {50, 100, 200, 400};
		int iterations = 1000;
		
		RandomPredicateGenerator rpg = new RandomPredicateGenerator(numExpressions, numVariables, iterations);
		
		int[][] timing = new int[numVariables.length][numExpressions.length];
		for(int i = 0; i < numVariables.length; i++){
			for(int j = 0; j < numExpressions.length; j++){
				ThreeSATPredicate[] preds = new ThreeSATPredicate[iterations];
				for(int k = 0; k < iterations; k++){
					preds[k] = rpg.read();
				}
				long l = System.currentTimeMillis();
				for(int k = 0; k < iterations; k++){
					PredicateSolver ps = new PredicateSolver(preds[k]);
					ps.solve();
				}
				timing[i][j] = (int) (System.currentTimeMillis() - l);
				System.out.println(Arrays.deepToString(timing));
			}	
		}
	}
	
}
