package de.java.regexdsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ComplexExpression implements RegexExpression {
	private final List<RegexExpression> children = new ArrayList<RegexExpression>();
	private final String name;
	
	public ComplexExpression(final String name) {
		this.name = name;
	}
	
	protected void add(final RegexExpression childExpression)
	{
		this.children.add(childExpression);
	}
	

	public String getName() {
		return this.name.startsWith("#")?  this.name.substring(1) : this.name;
	}
	
	
	//@Override
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

	
	public int ignoreCapturingGroups() {
		return 0;
	}

}
