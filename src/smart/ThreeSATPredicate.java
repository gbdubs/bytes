package smart;
import java.util.ArrayList;
import java.util.List;


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
	
	public boolean satisfiedBy(boolean[] vector){
		for(SubPredicate sp : subPredicates){
			if (! sp.getSolvingPredicate().satisfiedBy(vector)){
				return false;
			}
		}
		return true;
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
}
