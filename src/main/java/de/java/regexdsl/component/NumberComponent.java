package de.java.regexdsl.component;

import javax.annotation.Nonnull;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches any number including floating point numbers, e.g 
 * 12345 or 0.1234
 * 
 * @author Christian Bannes
 */
public class NumberComponent extends SimpleExpression {

	@Override
	public @Nonnull String asRegex() {
		return "\\d+(\\.\\d+)?";
	}

}
