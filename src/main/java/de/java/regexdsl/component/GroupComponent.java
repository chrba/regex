package de.java.regexdsl.component;

import static com.google.common.base.Preconditions.checkNotNull;

import de.java.regexdsl.model.ComplexExpression;
import de.java.regexdsl.model.RegexExpression;

/**
 * Creates a named capturing group.
 * 
 * @author Christian Bannes
 */
public class GroupComponent extends ComplexExpression {
	
	/**
	 * Creates a group component containing the given regex expression.
	 * 
	 * @param regexExpression the regex expression, not null.
	 * @param name the group name, not null.
	 */
	public GroupComponent(RegexExpression regexExpression, final String name) {
		super(checkNotNull(name, "name must not be null"));
		checkNotNull(regexExpression, "regexExpression must not be null");
		this.add(regexExpression);
	}

	/**
	 * Creates an empty group component
	 * @param name the name of the capturing group, not null.
	 */
	public GroupComponent(final String name) {
		super(checkNotNull(name, "name must not be null"));
	}
	

	@Override
	public String asRegex() {
		final String groups = this.groups();
		return "(" + groups + ")";
	}
}
