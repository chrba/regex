package de.java.regexdsl.model;

import java.util.List;

/**
 * Represents a regular expression. A regular expression can be seen as a tree where capturing groups 
 * make up nodes and regexes without any capturing groups make up the leafs. This interface represents
 * a node or a leaf. For leafs the method {{@link #getChildren()}} must return an empty list.
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
	 * The children of this regex, must be empty for leafs. The children represent the capturing groups
	 * of this regex.
	 * 
	 * @return the children, never null.  
	 **/
	public List<RegexExpression> getChildren();
	
	/** 
	 * 
	 * @return
	 */
	public int ignoreCapturingGroups();
	public String getName();
}
