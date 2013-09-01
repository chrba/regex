package de.java.regexdsl.model;

import java.util.HashMap;
import java.util.Map;

public class BreadthFirstTraversal {
	private int index;
	
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
		
		if(ex.getName() == null) { 
			this.index += ex.ignoreCapturingGroups();
			return map;
		}
		
		map.put(prefix + ex.getName(), this.index);
		this.index += ex.ignoreCapturingGroups() + 1;
		
		
		if (ex.getChildren().size() == 0) return map;
		
		for(final RegexExpression child : ex.getChildren())
		{
			map.putAll(traverse(child, prefix + ex.getName() + "->"));
		}
		
		return map;
	}
}
