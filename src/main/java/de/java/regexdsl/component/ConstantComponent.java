package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

public class ConstantComponent extends SimpleExpression {

	private String constant;

	public ConstantComponent(final String constant) {
		this.constant = constant;
	}
	
	@Override
	public String asRegex() {
		return constant;
	}

}
