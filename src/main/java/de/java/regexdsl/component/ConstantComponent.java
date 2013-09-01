package de.java.regexdsl.component;

import de.java.regexdsl.model.SimpleExpression;

/**
 * Matches the constant expression, escapes special characters contained in the constant 
 * expression.
 * 
 * @author Christian Bannes
 */
public class ConstantComponent extends SimpleExpression {

	private final static String[] metaChars = {"?", "$", ".", "+", "^", "(", ")", "[", "]"};
	private final String constant;

	/**
	 * @param constant the constant expression
	 */
	public ConstantComponent(final String constant) {
		String result = constant.replaceAll("\\\\", "\\\\\\\\");
		
		for(final String metaChar : metaChars)
		{
			result = result.replaceAll("\\" + metaChar , "\\\\" + metaChar);
		}
		this.constant = result;
	}
	
	@Override
	public String asRegex() {
		return constant;
	}

}
