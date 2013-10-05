package de.java.regexdsl.model;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple expression that does not contain any children. That means that
 * either this expression does not have any capturing groups or 
 * the capturing groups will be ignored (i.e. not be accessed). 
 *   
 * @author Christian Bannes
 */
public abstract class SimpleExpression implements RegexExpression {


	/**
	 * @return always null
	 */
	@Override
	public String getName() {
		return null;
	}
	
	/**
	 * A simple expression does not have children per definition. For expressions 
	 * with children use a {@link ComplexExpression}.
	 * 
	 * @return an empty list
	 */
	@Override
	public List<RegexExpression> getChildren() {
		return Collections.emptyList();
	}


	/**
	 * Counts and returns the number of capturing groups of this regex expression.
	 * @return the number of capturing groups
	 */
	public int numOfCapturingGroups() {
		final int potentialGroups = countMatches(this.asRegex(), "\\("); //capturing groups start with braces
		final int embeddedFlagExp = countMatches(this.asRegex(), "\\(\\?"); //embedded flag expressions don't build up capturing groups
		final int escapedBraces = countMatches(this.asRegex(), "\\\\\\("); //dont count escaped braces as capturing groups
		
		return potentialGroups - embeddedFlagExp - escapedBraces;
	}
	
	/**
	 * Counts the number of matches of the regex in the given string
	 * @param s the string
	 * @param regex the regex to match
	 * @return the number of matches
	 */
	private int countMatches(final String s, final String regex) {
		final Matcher m = Pattern.compile(regex).matcher(s);
		int count = 0;
		while(m.find())
			count++;
		
		return count;
	}
	
	
}
