package de.java.regexdsl.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the regex that was created using {@link RegexBuilder}.
 * 
 * @author Christian Bannes
 */
public class Regex {
	private Pattern regex;
	private Map<String, Integer> names;
	private ComplexExpression expression;

	/**
	 * Creates a new regex
	 * 
	 * @param expression the regex expression that defines the underlying regular expression
	 * @param names 
	 */
	public Regex(ComplexExpression expression, Map<String, Integer> names) {
		this.regex = Pattern.compile(expression.asRegex());
		this.names = names;
		this.expression = expression;
	}

	/**
	 * Matches the given string to the unserlying regex
	 * 
	 * @param s the string to match
	 * @return a {@link Match} used to access the regex matches by the name of the expressions
	 */
	public Match match(final String s) {
		final Matcher matcher = this.regex.matcher(s);
		return new Match(matcher, this.names);
		
	}
	
	@Override
	public String toString() {
		return this.regex.toString();
	}
	
	/**
	 * Package private, only for use in {@link RegexBuilder}.
	 * 
	 * @return the underlying expression which specifies the regex
	 */
	ComplexExpression getExpression() {
		return this.expression;
	}
}
