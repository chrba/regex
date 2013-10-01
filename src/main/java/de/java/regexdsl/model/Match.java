package de.java.regexdsl.model;

import java.util.Map;
import java.util.regex.Matcher;

public class Match {
	private final Matcher matcher;
	private final Map<String, Integer> names;

	public Match(Matcher matcher, Map<String, Integer> names) {
		this.matcher = matcher;
		this.matcher.find();
		this.names = names;
	}

	public String getTotal() {
		return this.matcher.group();
	}
	
	public String getByName(final String name) {
		if(!this.names.containsKey(name))
			throw new IllegalArgumentException("The specified name does not exist: " + name);
		
		final int index = this.names.get(name);
		try {
			return this.matcher.group(index+1);
		}
		catch(IllegalStateException e) {
			return null;
		}
	}
	
}
