package smart;
import java.math.BigInteger;


public class SubPredicate {
	
	public int[] variables;
	public boolean[] affirmatives;
	
	public BigInteger state;
	
	public SubPredicate(String description){
		while (description.contains("(")){
			int start = description.indexOf("(") + 1;
			int end = description.lastIndexOf(")");
			description = description.substring(start, end).trim();
		}
		String[] subExpressions = description.split(ThreeSATPredicate.OR_REGEX);
		variables = new int[subExpressions.length];
		affirmatives = new boolean[subExpressions.length];
		for(int i = 0; i < subExpressions.length; i++){
			String s = subExpressions[i].trim();
			if (s.contains("!")){
				affirmatives[i] = false;
				s = s.substring(s.indexOf('!')+1).trim();
			} else {
				affirmatives[i] = true;
			}
			variables[i] = Integer.parseInt(s);
		}
		sortVariables();
	}
	
	public SolvingPredicate getSolvingPredicate(){
		return new SolvingPredicate(variables, affirmatives);
	}
	
	public String toString(){
		String result = "";
		for (int i = 0; i < variables.length; i++){
			String subExpression = "" + variables[i];
			if (! affirmatives[i]){
				subExpression = "!" + subExpression;
			} 
			result = result +" "+ ThreeSATPredicate.OR +" "+ subExpression;
		}
		result = result.substring(3);
		return result;
	}
	
	// SORTS VARIABLES IN EXPRESSION
	
	private void sortVariables(){
		int sortedPivot = 0;
		while (!sorted(variables)){
			int toSwap = minimumOfSubArray(variables, sortedPivot);
			swap(variables, toSwap, sortedPivot);
			swap(affirmatives, toSwap, sortedPivot);
			sortedPivot++;
		}
	}
	
	private static void swap(boolean[] array, int i, int j){
		boolean c = array[i];
		array[i] = array[j];
		array[j] = c;
	}
	
	private static void swap(int[] array, int i, int j){
		int c = array[i];
		array[i] = array[j];
		array[j] = c;
	}
	
	private static int minimumOfSubArray(int[] vars, int startAt){
		int minimum = 100000000;
		int location = -1;
		for (int i = startAt; i < vars.length; i++){
			if (vars[i] < minimum){
				minimum = vars[i];
				location = i;
			}
		}
		return location;
	}
	
	private static boolean sorted(int[] vars){
		int last = -100000;
		for (int i = 0; i < vars.length; i++) {
			if (vars[i] < last){
				return false;
			}
			last = vars[i];
		}
		return true;
	}

	public boolean evaluateClassically(boolean[] vector) {
		for(int i = 0; i < variables.length; i++){
			int var = variables[i];
			boolean expected = affirmatives[i];
			int index = var;
			if (vector[index] == expected){
				return true;
			}
		}
		return false;
	}
	
}
