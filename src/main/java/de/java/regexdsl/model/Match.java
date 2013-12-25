package de.java.regexdsl.model;

import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides access to a regex match. Every match can be accessed by the name
 * of the expression that described the regex. The name is defined
 * by <code>Expression.getName</code>.
 * 
 * @author Christian Bannes
 */
public class Match {
	private final @Nonnull Matcher matcher;
	private final @Nonnull Map<String, Integer> names;

	/**
	 * @param matcher the underlying matcher
	 * @param names the names of the regexes matched to the indexes of the corresponding
	 * 		  capturing group
	 */
	public Match(final @Nonnull Matcher matcher, final @Nonnull Map<String, Integer> names) {
		this.matcher = matcher;
		this.matcher.find();
		this.names = names;
	}

	/**
	 * Returns the match of the regex
	 * 
	 * @return the match of the regex
	 */
	public @Nonnull String getTotal() {
		return this.matcher.group();
	}
	
	/**
	 * Accesses the match of the named regex.
	 * @param name the name of the regex
	 * @return the match, or null
	 */
	public @Nullable String getByName(final String name) {
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

	/**
	 * Attempts to match the entire region against the pattern.
	 * Returns true, if the match succeeds, else false.
	 * 
	 * @return true, if the match succeeds, else false
	 */
	public boolean empty() {
		return !this.matcher.matches();
	}
	
}
