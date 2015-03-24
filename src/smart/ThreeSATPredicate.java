package smart;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ThreeSATPredicate {

	public static boolean PRINT = false;
	
	public static final char AND = '^';
	public static final String AND_REGEX = "\\^";
	public static final char OR = 'v';
	public static final String OR_REGEX = "v";

	public List<SubPredicate> subPredicates;
	public int numVariables;
	
	public ThreeSATPredicate(String s){
		subPredicates = new ArrayList<SubPredicate>();
		String[] sps = s.split(AND_REGEX);
		numVariables = 0;
		for(String sp : sps){
			SubPredicate sub = new SubPredicate(sp);
			subPredicates.add(sub);
			for(Integer c : sub.variables){
				numVariables = Math.max(c, numVariables);
			}
		}
		numVariables++;
	}
	
	public String toString(){
		String result = "";
		for (SubPredicate sp : subPredicates){
			result = result + " "+AND+" ("+sp.toString()+")";
		}
		return result.substring(3);
	}
	
	public boolean[] satisfiable(){
		long max = (long) Math.pow(2, numVariables);
		for(long i = 0; i < max; i++){
			boolean[] vector = generateValueVector(i, numVariables);
			if (this.evaluateClassically(vector)){
				return vector;
			}
		}
		return null;
	}

	private boolean evaluateClassically(boolean[] vector) {
		for (SubPredicate sp : subPredicates){
			if (!sp.evaluateClassically(vector)){
				return false;
			}
		}
		return true;
	}

	private static boolean[] generateValueVector(long k, int numVariables) {
		
		boolean[] result = new boolean[numVariables];
		long divisor = (long) Math.pow(2, numVariables - 1);
		for(int i = 0; i < numVariables; i++){
			result[i] = (k / divisor) > 0;
			k = k % divisor;
			divisor /= 2;
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args){
		/*ThreeSATPredicate.PRINT = true;
		ThreeSATPredicate tsp = new ThreeSATPredicate("(!2 v !3 v !4) ^ (!0 v !1 v !3) ^ (2 v !3 v !4)");
		PredicateSolver ps = new PredicateSolver(tsp);
		ps.solve();
		*/
		testCorrect();
	}
	
	public static void testCorrect(){
		
		
	}
	
	public static void test3(){	
		int[] numVariables = {40, 160, 640, 2560};//, 10240, 40960, 163840, 655360, 2621440, 10485760, 41943040, 83886080, 33554432, 134217728, 536870912};
		int[] numExpressions = {50, 100, 200, 400, 800, 1600, 3200};
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
	
	public static void test2(){	
		int[] numVariables = {40, 160, 640, 2560};//, 10240, 40960, 163840, 655360, 2621440, 10485760, 41943040, 83886080, 33554432, 134217728, 536870912};
		int[] numExpressions = {50, 100, 200, 400, 800, 1600, 3200};
		int iterations = 10000;
		
		int[][] timing = new int[numVariables.length][numExpressions.length];
		for(int i = 0; i < numVariables.length; i++){
			int nVars = numVariables[i];
			for(int j = 0; j < numExpressions.length; j++){
				int nExpr = numExpressions[j];
				ThreeSATPredicate[] preds = new ThreeSATPredicate[iterations];
				for(int k = 0; k < iterations; k++){
					preds[k] = RandomPredicateGenerator.generateRandomThreeSat(nExpr, nVars);
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
	
	public static void test(){
		int nv = 200;
		int n = 10;
		int batches = 10;
		int x = 2;
		boolean doRegular = true;
		long[][] time = new long[2][nv+1];
		for(int v = 3; v <= nv; v++){
			for(int batch = 0; batch < batches; batch++){
				List<ThreeSATPredicate> randomPredicates = new ArrayList<ThreeSATPredicate>();
				for(int i = 0; i < n/batches; i++){
					
					randomPredicates.add(RandomPredicateGenerator.generateRandomThreeSat(x, v));
				}
				boolean[] classic = new boolean[n/batches];
				boolean[] fancy = new boolean[n/batches];
				long l = System.currentTimeMillis();
				if (doRegular){
					for (int i = 0; i < n/batches; i++){
						boolean[] result = randomPredicates.get(i).satisfiable();
						classic[i] = (result != null);
					}
					time[0][v] += System.currentTimeMillis() - l;
				}
				l = System.currentTimeMillis();
				for (int i = 0; i < n/batches; i++){
					PredicateSolver ps = new PredicateSolver(randomPredicates.get(i));
					fancy[i] = !ps.solve().equals(BigInteger.ZERO);
				}
				time[1][v] += System.currentTimeMillis() - l;
				for(int i = 0; i < n/batches; i++){
					if (classic[i] != fancy[i] && doRegular) System.out.println("DISAGREE: " + randomPredicates.get(i));
				}
				System.out.println("Batch " + batch + " complete.");
			}
			if (time[0][v] > 10000){
				doRegular = false;
			}
			System.out.println("V = " + v);	
			System.out.println("TIME COMPARISON");
			System.out.println("Classic: " + Arrays.toString(time[0]));
			System.out.println("Fancy:   " + Arrays.toString(time[1]));
		}
	}
}
