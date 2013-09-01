package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches any number including floating point numbers, e.g 
 * 12345 or 0.1234
 * 
 * @author Christian Bannes
 */
public class NumberComponent extends SimpleExpression {

	@Override
	public String asRegex() {
		return "\\d+(\\.\\d+)?";
	}

}
