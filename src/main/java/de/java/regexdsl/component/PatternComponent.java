package de.java.regexdsl.component;


import static com.google.common.base.Preconditions.checkNotNull;
import de.java.regexdsl.model.SimpleExpression;

/**
 * Pass through component, any regex pattern can be used.
 * 
 * @author Christian Bannes
 */
public class PatternComponent extends SimpleExpression{
	private final String pattern;

	/**
	 * @param pattern a regex pattern, not null.
	 */
	public PatternComponent(final String pattern) {
		checkNotNull(pattern, "pattern must not be null");
		this.pattern = pattern;
	}

	@Override
	public String asRegex() {
		return this.pattern;
	}
}
