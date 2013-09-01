package de.java.regexdsl.component;

import de.java.regexdsl.model.ComplexExpression;

/**
 * Makes an expression optional and associates a name to 
 * the contained expression.
 * 
 * @author Christian Bannes
 */
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
