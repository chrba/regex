package de.java.regexdsl.model;

import java.util.HashMap;
import java.util.Map;
import static com.google.common.base.Preconditions.checkNotNull;

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
	 * @param ex a regex, not null.
	 * @return a map containing the names and associated index of each named capturing group, never null.
	 */
	public  Map<String, Integer> traverse(final RegexExpression ex) {
		checkNotNull(ex, "expression must not be null");
		index = 0;
		final Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(final RegexExpression child : ex.getChildren())
		{
			map.putAll(traverse(child, ""));
		}
		
		return map;
	}
		
	
	/**
	 * Recursively traverses the expression tree.
	 * 
	 * @param ex the expression, may contain more child expressions
	 * @param prefix the name prefix to use
	 * @return a map which associates names to indexes, never null
	 */
	private Map<String, Integer> traverse(final RegexExpression ex, final String prefix) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		
		if(ex.getName() != null) {
			map.put(prefix + ex.getName(), this.index);
			this.index += ex.numOfCapturingGroups();
		}
		else {
			this.index += ex.numOfCapturingGroups();
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
