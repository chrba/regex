package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

public class PatternComponent extends SimpleExpression{
	private final String pattern;

	public PatternComponent(final String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String asRegex() {
		return this.pattern;
	}
}
