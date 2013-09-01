package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

public class StringComponent extends SimpleExpression {
	@Override
	public String asRegex() {
		return "[a-zA-Z]+";
	}
	
}
