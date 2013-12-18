package de.java.regexdsl.component;

import javax.annotation.Nonnull;

import de.java.regexdsl.model.ComplexExpression;

/**
 * This component is always used as root containing all the other 
 * components. 
 * 
 * @author Christian Bannes
 */
public class RootComponent extends ComplexExpression{
	/**
	 * Creates the root component.
	 */
	public RootComponent() {
		super(null);
	}

	@Override
	public @Nonnull String asRegex() {
		return this.groups();
	}
	
	@Override
	public int numOfCapturingGroups() {
		return 0;
	}
}
