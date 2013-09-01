package de.java.regexdsl.model;

import java.util.List;

public interface RegexExpression {
	public String asRegex();
	public List<RegexExpression> getChildren();
	public int ignoreCapturingGroups();
	public String getName();
}
