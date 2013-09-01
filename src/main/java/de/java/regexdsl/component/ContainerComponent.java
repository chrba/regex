package de.java.regexdsl.component;

import de.java.regexdsl.model.ComplexExpression;

public class ContainerComponent extends ComplexExpression{
	public ContainerComponent() {
		super(null);
	}


	
	@Override
	public String asRegex() {
		return this.groups();
	}
}
