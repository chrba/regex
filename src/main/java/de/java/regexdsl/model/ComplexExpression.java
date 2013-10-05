package de.java.regexdsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An expression which consists of child expressions. 
 * 
 * @author Christian Bannes
 */
public abstract class ComplexExpression implements RegexExpression {
	private final List<RegexExpression> children = new ArrayList<RegexExpression>();
	private final String name;
	
	/**
	 * @param name the name of this expression. 
	 */
	public ComplexExpression(final String name) {
		this.name = name;
	}
	
	/**
	 * Adds a child to this expression
	 * 
	 * @param childExpression
	 */
	protected void add(final RegexExpression childExpression)
	{
		this.children.add(childExpression);
	}
	
	@Override
	public String getName() {
		if(this.name == null) return null;
		return this.name.startsWith("#")?  this.name.substring(1) : this.name;
	}
	
	/**
	 * Retuns the child expressions as string
	 * 
	 * @return the child expressions, never null
	 */
	protected String groups() {
		final StringBuilder builder = new StringBuilder();
		for(final RegexExpression child : this.children) {
			builder.append(child.asRegex());
		}
		return builder.toString();
	}

	@Override
	public List<RegexExpression> getChildren() {
		return Collections.unmodifiableList(this.children);
	}

	@Override
	public int numOfCapturingGroups() {
		return 1;
	}

}
