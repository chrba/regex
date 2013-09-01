package de.java.regexdsl.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	private Pattern regex;
	private Map<String, Integer> names;
	private ComplexExpression expression;

	public Regex(ComplexExpression expression, Map<String, Integer> names) {
		this.regex = Pattern.compile(expression.asRegex());
		this.names = names;
		this.expression = expression;
	}

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
