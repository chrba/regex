package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Pass through component, any regex pattern can be used.
 * 
 * @author Christian Bannes
 */
public class PatternComponent extends SimpleExpression{
	private final String pattern;

	/**
	 * @param pattern a regex pattern
	 */
	public PatternComponent(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String asRegex() {
		return this.pattern;
	}
}
