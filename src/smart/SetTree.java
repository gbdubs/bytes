package smart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetTree {

	private SetTree parent;
	private int level;
	private int numLevels;
	private SetTree definedChild;
	private SetTree undefinedChild;
	private Set<SolvingPredicate> members;
	
	public SetTree(int level, SetTree parent, int numLevels){
		this.level = level;
		this.parent = parent;
		this.members = new HashSet<SolvingPredicate>();
		this.numLevels = numLevels;
	}
	
	public void addSolvingPredicate(SolvingPredicate sp){
		members.add(sp);
		if (level < numLevels){
			if (sp.getVariableSet().contains(level)){
				if (definedChild == null){
					definedChild = new SetTree(level + 1, this, numLevels);
				}
				definedChild.addSolvingPredicate(sp);
			} else {
				if (undefinedChild == null){
					undefinedChild = new SetTree(level + 1, this, numLevels);
				}
				undefinedChild.addSolvingPredicate(sp);
			}
		}
	}
	
	public void removeSolvingPredicate(SolvingPredicate sp){
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
		
		SetTree child;
		if (sp.getVariableSet().contains(level)){
			child = definedChild;
		} else {
			child = undefinedChild;
		}
		child.removeSolvingPredicate(sp);
	}
	
	public SetTree getDeepestTwoPersonSet(){
		SetTree definedResult = null;
		if (definedChild != null){
			definedResult = this.definedChild.getDeepestTwoPersonSet();
		}
		SetTree undefinedResult = null;
		if (undefinedChild != null){
			undefinedResult = this.undefinedChild.getDeepestTwoPersonSet();
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
	
	public List<SolvingPredicate> getMembers(){
		return new ArrayList<SolvingPredicate>(members);
	}
}
