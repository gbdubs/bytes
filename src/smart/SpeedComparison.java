package smart;

import java.util.Arrays;

public class SpeedComparison {

	public static void main(String[] args){
		speedTest();
	}
	
	public static void speedTest(){	
		int[] numVariables = {15, 20, 25, 30};
		int[] numExpressions = {50, 100, 200, 400};
	
		int batches = 100;
		int perBatch = 100;
		
		RandomPredicateGenerator rpg = new RandomPredicateGenerator(numExpressions, numVariables, batches * perBatch);
		
		int[][] timing = new int[numVariables.length][numExpressions.length];
		for(int i = 0; i < numVariables.length; i++){
			for(int j = 0; j < numExpressions.length; j++){
				for(int batch = 0; batch < batches; batch++){
					ThreeSATPredicate[] preds = new ThreeSATPredicate[perBatch];
					boolean[][] results = new boolean[perBatch][numVariables[i]];
					for(int k = 0; k < perBatch; k++){
						preds[k] = rpg.read();
					}
					long l = System.currentTimeMillis();
					for(int k = 0; k < perBatch; k++){
						PredicateSolver ps = new PredicateSolver(preds[k]);
						results[k] = ps.solve();
					}
					timing[i][j] += (int) (System.currentTimeMillis() - l);
					for (int k = 0; k < perBatch; k++){
						if (results[k] != null){
							if (!preds[k].satisfiedBy(results[k])){
								System.err.println("NOT SATISFIED!!!! " + preds[k].toString() + " " + Arrays.toString(results[k]));
							}
						}
					}
				}
				System.out.println(Arrays.deepToString(timing));
			}	
		}
	}
	
	/*              IMPORTANT RESULTS (N = 10,000)
	
	               E X P R E S S I O N S
	   
	             50    100     200     400
	V          
    A      7    2672    3749    9283   15747 
    R      8    2344    3990    8179   16748 
    I      9    2497    4610    9140   18400 
    A     10    2915    4975    9918   21555 
    B     11    3292    5610   12591   24232 
    L     12    3834    6184   12115   25255 
    E     13    3634    6584   13226   28681 
    S     14    4477    9869   15667   31599
	      15    6866    8455   16442   33657
	      20    6657   10501   21045   44542
	    
	      		^
	 			|
	 	   	   ???
	RAW:   
[[2672, 3749, 9283, 15747], [2344, 3990, 8179, 16748], [2497, 4610, 9140, 18400], [2915, 4975, 9918, 21555], [3292, 5610, 12591, 24232], [3834, 6184, 12115, 25255], [3634, 6584, 13226, 28681], [4477, 9869, 15667, 31599]]
	 */
	
}
