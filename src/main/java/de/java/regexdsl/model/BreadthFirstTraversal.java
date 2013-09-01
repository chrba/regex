package de.java.regexdsl.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A regular expression with its capturing groups can be seen as a tree. The index of a capturing group
 * is determined by the position in the tree. This class traverses the regex tree in a breadth first manner
 * to find the capturing groupo index and associates it with the name of the capturing group.
 * 
 * @author Christian Bannes
 */
public class BreadthFirstTraversal {
	private int index;
	
	/**
	 * Traverses the regex tree in a beadth first manner to find the index
	 * for each named capturing group.
	 * 
	 * @param ex a regex 
	 * @return a map containing the names and associated index of each named capturing group.
	 */
	public  Map<String, Integer> traverse(final RegexExpression ex) {
		index = 0;
		final Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(final RegexExpression child : ex.getChildren())
		{
			map.putAll(traverse(child, ""));
		}
		
		return map;
	}
		
	
	private Map<String, Integer> traverse(final RegexExpression ex, final String prefix) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		
		if(ex.getName() != null) {
			map.put(prefix + ex.getName(), this.index);
			this.index += ex.ignoreCapturingGroups() + 1;
		}
		else {
			this.index += ex.ignoreCapturingGroups();
		}
		
		
		if (ex.getChildren().size() == 0) return map;
		
		for(final RegexExpression child : ex.getChildren())
		{
			if(ex.getName() != null)
				map.putAll(traverse(child, prefix + ex.getName() + "->"));
			else 
				map.putAll(traverse(child, prefix));
		}
		
		return map;
	}
}
