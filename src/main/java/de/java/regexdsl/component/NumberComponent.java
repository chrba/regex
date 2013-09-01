package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

public class NumberComponent extends SimpleExpression {

	@Override
	public String asRegex() {
		return "\\d+(\\.\\d+)?";
	}

}
