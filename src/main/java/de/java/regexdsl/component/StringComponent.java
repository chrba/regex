package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches any string.
 * 
 * @author Christian Bannes
 */
public class StringComponent extends SimpleExpression {
	@Override
	public String asRegex() {
		return "[a-zA-Z]+";
	}
	
}
