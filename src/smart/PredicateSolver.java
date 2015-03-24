package smart;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PredicateSolver {
	
	private SetTree tree;
	private List<Set<Integer>> variableSets;
	private List<SolvingPredicate> solvingPredicates;
	Map<Integer, Integer> counts;
	
	public PredicateSolver(ThreeSATPredicate pred){
		solvingPredicates = new ArrayList<SolvingPredicate>();
		variableSets = new ArrayList<Set<Integer>>();
		counts = new HashMap<Integer, Integer>();
		int max = 0;
		for(SubPredicate sp : pred.subPredicates){
			max = Math.max(max, sp.variables[2]);
		}
		tree = new SetTree(0, null, max);
		for(SubPredicate sp : pred.subPredicates){
			addSolvingPredicate(sp.getSolvingPredicate());
		}
	}
	
	public BigInteger solve(){
		while (solvingPredicates.size() > 1){
			if (ThreeSATPredicate.PRINT) System.out.println("********************************CURRENT STATE**************************************");
			if (ThreeSATPredicate.PRINT) System.out.println(toString());
			int[] toCombine = choosePairToCombine();
			SolvingPredicate b = removeSolvingPredicate(toCombine[1]);
			SolvingPredicate a = removeSolvingPredicate(toCombine[0]);
			SolvingPredicate c = SolvingPredicate.conjoin(a, b);
			addSolvingPredicate(c);
			if (c.getState().equals(BigInteger.ZERO)){
				return BigInteger.ZERO;
			}
			if (ThreeSATPredicate.PRINT) System.out.println("***********************************************************************************");
		}
		return solvingPredicates.get(0).getState();
	}
	
	public String toString(){
		String result = "";
		for(int i = 0; i < solvingPredicates.size(); i++){
			SolvingPredicate sp = solvingPredicates.get(i);
			Set<Integer> set = variableSets.get(i);
			result += sp.getId() + " - " + set.toString() + " - " + sp.getState().intValue() + " - " + Arrays.toString(sp.getState().toByteArray()) + "\n";
			
		}
		return result;
	}
	
	private int[] choosePairToCombine() {
		SetTree st = tree.getDeepestTwoPersonSet();
		List<SolvingPredicate> members = st.getMembers();
		int i = solvingPredicates.indexOf(members.get(0));
		int j = solvingPredicates.indexOf(members.get(1));
		int[] results = new int[2];
		if (i < j){
			results[0] = i; results[1] = j;
		} else {
			results[1] = i; results[0] = j;
		}
		return results;
	}
	
	private void addSolvingPredicate(SolvingPredicate sp){
		solvingPredicates.add(sp);
		Set<Integer> variableSet = sp.getVariableSet();
		variableSets.add(variableSet);
		for (Integer c : variableSet){
			incrementCountMap(c);
		}
		tree.addSolvingPredicate(sp);
	}

	private SolvingPredicate removeSolvingPredicate(int i){
		SolvingPredicate r = solvingPredicates.remove(i);
		Set<Integer> set = variableSets.remove(i);
		for(Integer k : set){
			decrementCountMap(k);
		}
		tree.removeSolvingPredicate(r);
		return r;
	}
	
	private void incrementCountMap(int c){
		if (counts.containsKey(c)){
			counts.put(c, counts.get(c) + 1);
		} else {
			counts.put(c, 1);
		}
	}
	
	private void decrementCountMap(int c){
		if (counts.containsKey(c)){
			counts.put(c, counts.get(c) - 1);
		} 
	}
}
