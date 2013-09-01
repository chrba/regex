package de.java.regexdsl.component;

import de.java.regexdsl.model.ComplexExpression;

public class OptionalComponent extends ComplexExpression {

	
	public OptionalComponent(final String name) {
		super(name);
	}
	
	@Override
	public String asRegex() {
		final String groups = this.groups();
		return "(" + groups + ")?"; 
	}
 
}
