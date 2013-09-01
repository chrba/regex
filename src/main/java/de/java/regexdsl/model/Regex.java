package de.java.regexdsl.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	private Pattern regex;
	private Map<String, Integer> names;

	public Regex(String regex, Map<String, Integer> names) {
		this.regex = Pattern.compile(regex);
		this.names = names;
	}

	public Match match(final String s) {
		final Matcher matcher = this.regex.matcher(s);
		return new Match(matcher, this.names);
		
	}
}
