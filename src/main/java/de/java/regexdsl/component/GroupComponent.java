package de.java.regexdsl.component;

import de.java.regexdsl.model.ComplexExpression;
import de.java.regexdsl.model.RegexExpression;

/**
 * Creates a named capturing group.
 * 
 * @author Christian Bannes
 */
public class GroupComponent extends ComplexExpression {
	
	public GroupComponent(RegexExpression regexExpression, final String name) {
		super(name);
		this.add(regexExpression);
	}

	/**
	 * @param name the name of the capturing group
	 */
	public GroupComponent(final String name) {
		super(name);
	}
	

	@Override
	public String asRegex() {
		final String groups = this.groups();
		return "(" + groups + ")";
	}
}
