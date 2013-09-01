package de.java.regexdsl.component;

import de.java.regexdsl.model.ComplexExpression;
import de.java.regexdsl.model.RegexExpression;

public class GroupComponent extends ComplexExpression {
	
	public GroupComponent(RegexExpression regexExpression, final String name) {
		super(name);
		this.add(regexExpression);
	}

	public GroupComponent(final String name) {
		super(name);
	}
	

	@Override
	public String asRegex() {
		final String groups = this.groups();
		return "(" + groups + ")";
	}
}
