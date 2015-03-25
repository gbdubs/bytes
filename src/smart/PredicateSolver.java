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
	private Map<Integer, SolvingPredicate> lastPredicateStanding;
	
	public static void main(String[] args){
		ThreeSATPredicate.PRINT = true;
		ThreeSATPredicate tsp = RandomPredicateGenerator.generateRandomThreeSat(6, 5);
		System.out.println(tsp.toString());
		PredicateSolver ps = new PredicateSolver(tsp);
		ps.solve();
	}
	
	public PredicateSolver(ThreeSATPredicate pred){
		solvingPredicates = new ArrayList<SolvingPredicate>();
		variableSets = new ArrayList<Set<Integer>>();
		definedOn = new HashMap<Integer, Set<SolvingPredicate>>();
		lastPredicateStanding = new HashMap<Integer, SolvingPredicate>();
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
		Map<Integer, Boolean> mapping = new HashMap<Integer, Boolean>();
		SolvingPredicate sp = solvingPredicates.get(0);
		int[] finalAlphabet = sp.getAlphabet();
		int solution = sp.getState().getLowestSetBit();
		int twoPow = 2;
		for(int i = 0; i < finalAlphabet.length; i++){
			mapping.put(i, (solution % twoPow != 0));
			twoPow = twoPow * 2;
		}
		for (int variable : lastPredicateStanding){
			
		}
		
		
		return null;
	}
	
	public void checkForCollapsable(){
		for (int v : definedOn.keySet()){
			Set<SolvingPredicate> sps = definedOn.get(v);
			if (sps.size() == 1){
				SolvingPredicate alone = sps.iterator().next();
				if (alone.getAlphabet().length > 1){
					if (ThreeSATPredicate.PRINT) System.out.println("***************** COLLAPSING "+alone.getId()+" ******************");
					lastPredicateStanding.put(v, alone);
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
