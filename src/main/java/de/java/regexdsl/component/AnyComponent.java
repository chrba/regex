package de.java.regexdsl.component;

import javax.annotation.Nonnull;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches any char including whitespaces.
 *  
 * @author Christian Bannes
 */
public class AnyComponent extends SimpleExpression {
	
	@Override
	public @Nonnull String asRegex() {
		return "(?s:.*)";
	}
		
	
}
