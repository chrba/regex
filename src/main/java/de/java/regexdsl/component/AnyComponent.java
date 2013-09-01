package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches any char including whitespaces.
 *  
 * @author Christian Bannes
 */
public class AnyComponent extends SimpleExpression {
	@Override
	public String asRegex() {
		return "(?s:.*)";
	}
	
}
