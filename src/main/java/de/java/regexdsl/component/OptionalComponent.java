package de.java.regexdsl.component;


import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import de.java.regexdsl.model.ComplexExpression;

/**
 * Makes an expression optional and associates a name to 
 * the contained expression.
 * 
 * @author Christian Bannes
 */
public class OptionalComponent extends ComplexExpression {

	/**
	 * Creates an optional expression with the given name.
	 * 
	 * @param name the name, not null.
	 */
	public OptionalComponent(final @Nonnull String name) {
		super(checkNotNull(name, "name must not be null"));
	}
	
	@Override
	public @Nonnull String asRegex() {
		final String groups = this.groups();
		return "(" + groups + ")?"; 
	}
 
}
