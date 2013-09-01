package de.java.regexdsl.model;

import java.util.Collections;
import java.util.List;

public abstract class SimpleExpression implements RegexExpression{


	public String getName() {
		return null;
	}
	
	@Override
	public List<RegexExpression> getChildren() {
		return Collections.emptyList();
	}


	public int ignoreCapturingGroups() {
		final String s1 = this.asRegex().replaceAll("\\(", "");
		final String s2 = this.asRegex();
		final String s3 = this.asRegex().replaceAll("\\(\\?", ""); //embedded flag expression don't build up capturing groups!
		
		final int groups = s2.length() - s1.length();
		final int embeddedFlagExp = (s2.length() - s3.length()) / 2;
		return groups - embeddedFlagExp;//s2.length() - s1.length(); //+ s3.length() / 2;
	}
	
	
	
}
