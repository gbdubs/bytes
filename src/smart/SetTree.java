package smart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetTree<T> {

	private SetTree<T> parent;
	private int level;
	private int numLevels;
	private SetTree<T> definedChild;
	private SetTree<T> undefinedChild;
	private Set<T> members;
	
	public SetTree(int level, SetTree<T> parent, int numLevels){
		this.level = level;
		this.parent = parent;
		this.members = new HashSet<T>();
		this.numLevels = numLevels;
	}
	
	public void addItem(T sp){
		members.add(sp);
		if (level < numLevels){
			Set<Integer> variableSet = getIntegerSet(sp);
			if (variableSet.contains(level)){
				if (definedChild == null){
					definedChild = new SetTree<T>(level + 1, this, numLevels);
				}
				definedChild.addItem(sp);
			} else {
				if (undefinedChild == null){
					undefinedChild = new SetTree<T>(level + 1, this, numLevels);
				}
				undefinedChild.addItem(sp);
			}
		}
	}
	
	public void removeItem(T sp){
		members.remove(sp);
		
		if (this.members.size() == 0){
			if (this.parent != null){
				if (this.parent.definedChild == this){
					this.parent.definedChild = null;
				} else if (this.parent.undefinedChild == this){
					this.parent.undefinedChild = null;
				}
			}
		}
		
		if (level == numLevels){
			return;
		}
		
		SetTree<T> child;
		Set<Integer> integerSet = getIntegerSet(sp);
		if (integerSet.contains(level)){
			child = definedChild;
		} else {
			child = undefinedChild;
		}
		child.removeItem(sp);
	}
	
	public SetTree<T> getDeepestTwoMemberTree(){
		SetTree<T> definedResult = null;
		if (definedChild != null){
			definedResult = this.definedChild.getDeepestTwoMemberTree();
		}
		SetTree<T> undefinedResult = null;
		if (undefinedChild != null){
			undefinedResult = this.undefinedChild.getDeepestTwoMemberTree();
		}
		if (definedResult != null && undefinedResult != null){
			if (definedResult.level > undefinedResult.level){
				return definedResult;
			} else {
				return undefinedResult;
			}
		} else if (definedResult != null || undefinedResult != null){
			if (definedResult != null){
				return definedResult;
			} else {
				return undefinedResult;
			}
		} else {
			if (members.size() >= 2){
				return this;
			}
		}
		return null;
	}
	
	public List<T> getMembers(){
		return new ArrayList<T>(members);
	}

	private Set<Integer> getIntegerSet(T object){
		if (object instanceof SolvingPredicate){
			SolvingPredicate sp = (SolvingPredicate) object;
			return sp.getVariableSet();
		} else if (object instanceof int[]){
			int[] array = (int[]) object;
			Set<Integer> set = new HashSet<Integer>();
			for (int i : array){
				set.add(i);
			}
			return set;
		} else {
			return null;
		}
	}
}
