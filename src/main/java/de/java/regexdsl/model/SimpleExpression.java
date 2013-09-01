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
		return s2.length() - s1.length();
	}
	
}
