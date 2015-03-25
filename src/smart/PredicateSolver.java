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
	private Map<Integer, Set<SolvingPredicate>> definedOn;
	private List<SolvingPredicate> lastPredicateStanding;
	private List<Integer> lastCorrespondingVariable;
	
	public static void main(String[] args){
		ThreeSATPredicate.PRINT = true;
		ThreeSATPredicate tsp = RandomPredicateGenerator.generateRandomThreeSat(6, 5);
		System.out.println(tsp.toString());
		PredicateSolver ps = new PredicateSolver(tsp);
		boolean[] solution = ps.solve();
		System.out.println("SOLUTION: " + Arrays.toString(solution));
	}
	
	public PredicateSolver(ThreeSATPredicate pred){
		solvingPredicates = new ArrayList<SolvingPredicate>();
		variableSets = new ArrayList<Set<Integer>>();
		definedOn = new HashMap<Integer, Set<SolvingPredicate>>();
		lastPredicateStanding = new ArrayList<SolvingPredicate>();
		lastCorrespondingVariable = new ArrayList<Integer>();
		int max = 0;
		for(SubPredicate sp : pred.subPredicates){
			max = Math.max(max, sp.variables[2]);
		}
		tree = new SetTree(0, null, max);
		for(SubPredicate sp : pred.subPredicates){
			addSolvingPredicate(sp.getSolvingPredicate());
		}
	}
	
	public boolean[] solve(){
		while (solvingPredicates.size() > 1){
			if (ThreeSATPredicate.PRINT) System.out.println("********************************CURRENT STATE**************************************");
			if (ThreeSATPredicate.PRINT) System.out.println(toString());
			checkForCollapsable();
			int[] toCombine = choosePairToCombine();
			SolvingPredicate b = removeSolvingPredicate(toCombine[1]);
			SolvingPredicate a = removeSolvingPredicate(toCombine[0]);
			SolvingPredicate c = SolvingPredicate.conjoin(a, b);
			addSolvingPredicate(c);
			if (c.getState().equals(BigInteger.ZERO)){
				return null;
			}
			if (ThreeSATPredicate.PRINT) System.out.println("***********************************************************************************");
		}
		return backtrackSolution();
	}
	
	private boolean[] backtrackSolution(){
		
		SolvingPredicate sp = solvingPredicates.get(0);
		int[] finalAlphabet = sp.getAlphabet();
		int numVariables = getHighestVariableNumber() + 1;
		boolean[] solvingVector = new boolean[numVariables];
		
		
		int solution = sp.getState().getLowestSetBit();
		int twoPow = (int) Math.pow(2, finalAlphabet.length - 1);
		for(int i = finalAlphabet.length - 1; i >= 0; i--){
			solvingVector[finalAlphabet[i]] = (solution / twoPow == 0);
			solution = solution % twoPow;
			twoPow = twoPow / 2;
		}
		
		for (int i = lastCorrespondingVariable.size() - 1; i >= 0; i--){
			int variable = lastCorrespondingVariable.get(i);
			boolean[] tempFalse = solvingVector.clone();
			tempFalse[variable] = false;
			boolean[] tempTrue = solvingVector.clone();
			tempTrue[variable] = true;
			
			SolvingPredicate alone = lastPredicateStanding.get(i);
			if (alone.satisfiedBy(tempTrue)){
				solvingVector[variable] = true;
			} else if (alone.satisfiedBy(tempFalse)){
				solvingVector[variable] = false;
			} else {
				System.out.println("HOUSON WE HAVE A PROBLEM. ONE OF THESE SHOULD SOLVE IT.");
			}
		}
		
		return solvingVector;
	}
	
	private int getHighestVariableNumber() {
		int max = 0;
		for (int i : definedOn.keySet()){
			max = Math.max(i, max);
		}
		return max;
	}

	public void checkForCollapsable(){
		for (int v : definedOn.keySet()){
			Set<SolvingPredicate> sps = definedOn.get(v);
			if (sps.size() == 1){
				SolvingPredicate alone = sps.iterator().next();
				if (alone.getAlphabet().length > 1){
					if (ThreeSATPredicate.PRINT) System.out.println("***************** COLLAPSING "+alone.getId()+" ******************");
					lastPredicateStanding.add(alone);
					lastCorrespondingVariable.add(v);
					SolvingPredicate collapsed = alone.collapseOnVariable(v);
					this.removeSolvingPredicate(alone);
					this.addSolvingPredicate(collapsed);
					if (ThreeSATPredicate.PRINT) System.out.println("***************** COLLAPSED INTO "+collapsed.getId()+" ******************");
				}
			}
		}
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
			Set<SolvingPredicate> sps = definedOn.get(c);
			if (sps == null) sps = new HashSet<SolvingPredicate>();
			sps.add(sp);
			definedOn.put(c, sps);
		}
		tree.addSolvingPredicate(sp);
	}

	private SolvingPredicate removeSolvingPredicate(SolvingPredicate toRemove){
		int index = solvingPredicates.indexOf(toRemove);
		return removeSolvingPredicate(index);
	}
	
	private SolvingPredicate removeSolvingPredicate(int i){
		SolvingPredicate sp = solvingPredicates.remove(i);
		Set<Integer> variableSet = variableSets.remove(i);
		for(Integer c : variableSet){
			Set<SolvingPredicate> sps = definedOn.get(c);
			if (sps != null){ 
				sps.remove(sp);
				definedOn.put(c, sps);
			}
		}
		tree.removeSolvingPredicate(sp);
		return sp;
	}

}
