package de.java.regexdsl.model;

import java.util.List;

/**
 * Represents a regular expression. A regular expression can be seen as a tree where capturing groups 
 * make up nodes and regexes without any capturing groups (or only with capturing groups we want to ignore) 
 * make up the leafs. This interface represents a node or a leaf. 
 * 
 * For leafs the method {{@link #getChildren()}} must return an empty list.
 * 
 * @author Christian Bannes
 */
public interface RegexExpression {
	/** 
	 * The regular expression as string 
	 * @return the regex string, never null.
	 **/
	public String asRegex();

	/** 
	 * The children represent capturing groups of interest of this regex.
	 * 
	 * @return the children, never null.  
	 * 
	 **/
	public List<RegexExpression> getChildren();
	
	/** 
	 * The number of capturing groups of this expression, without counting
	 * the capturing groups of the child expressions.
	 * 
	 * @return the number of capturing groups
	 */
	public int numOfCapturingGroups();
	
	/**
	 * The name used to access the capturing group of this expression.
	 * 
	 * @return the name, may be null
	 */
	public String getName();
}
