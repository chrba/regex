package de.java.regexdsl.component;

import javax.annotation.Nonnull;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches any string.
 * 
 * @author Christian Bannes
 */
public class StringComponent extends SimpleExpression {
	@Override
	public @Nonnull String asRegex() {
		return "[a-zA-Z]+";
	}
	
}
