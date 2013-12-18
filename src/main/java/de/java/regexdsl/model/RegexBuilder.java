package de.java.regexdsl.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import de.java.regexdsl.component.AnyComponent;
import de.java.regexdsl.component.ConstantComponent;
import de.java.regexdsl.component.RootComponent;
import de.java.regexdsl.component.GroupComponent;
import de.java.regexdsl.component.NumberComponent;
import de.java.regexdsl.component.OptionalComponent;
import de.java.regexdsl.component.PatternComponent;
import de.java.regexdsl.component.StringComponent;

/**
 * A builder that defines a DSL to create a {@link Regex}.
 * 
 * @author Christian Bannes
 */
public class RegexBuilder {
	final @Nonnull LinkedList<ComplexExpression> list;
	final @Nonnull List<String> names;
	
	
	/**
	 * Starts a new builder
	 */
	private RegexBuilder() {
		this.list =  new LinkedList<ComplexExpression>();
		this.names = new ArrayList<String>();;
		list.add(new RootComponent());
	}
	
	/**
	 * Creates a builder to continue creating the regex
	 *  
	 * @param list a list of already created regex expressions
	 * @param names the names of the regex expressions
	 */
	private RegexBuilder(final @Nonnull LinkedList<ComplexExpression> list, final @Nonnull List<String> names) {
		this.list = list;
		this.names = names;
	}


	/**
	 * Creates a new <code>RegexBuilder</code>
	 * 
	 * @return the newly created <code>RegexBuilder</code>.
	 */
	public static RegexBuilder create() {
		return new RegexBuilder();
	}
	
	/**
	 * Matches any String
	 * 
	 * @return the builder
	 */
	public RegexBuilder string() {
		return addComponent(new StringComponent());
	}
	
	/**
	 * Matches any String, the match can be accessed by the given name 
	 * @param name the name to access the match
	 * 
	 * @return the builder
	 */
	public RegexBuilder string(final String name) {
		return addNamedComponent(new StringComponent(), name);
	}
	
	/**
	 * Matches any char
	 * 
	 * @return the builder
	 */
	public RegexBuilder any() {
		return addComponent(new AnyComponent());
	}
	
	/**
	 * Starts a group. Any regex that is following will be added to this group until
	 * {{@link #end()} is called. The match of the group can be accessed by the given name.
	 * 
	 * @param name the name to access the match
	 * @return the builder
	 */
	public RegexBuilder group(final String name) {
		this.list.add(new GroupComponent(name));
		this.names.add(name);
		return new RegexBuilder(this.list, this.names);
	}

	/**
	 * Matches any number including floats, e.g. 12345 or 0.1234
	 * 
	 * @return the builder
	 */
	public RegexBuilder number() {
		return addComponent(new NumberComponent());
	}
	
	/**
	 * Can be used to pass through any regular expression 
	 * 
	 * @param pattern a java regular expression
	 * @return the builder
	 */
	public RegexBuilder pattern(final String pattern) {
		return addComponent(new PatternComponent(pattern));
	}
	
	/**
	 * Can be used to pass through any regular expression. The match can be accessed
	 * with the given name. 
	 * 
	 * @param name the name to access the match
	 * @param pattern a java regular expression
	 * @return the builder
	 */

	public RegexBuilder pattern(final String name, final String pattern) {
		return addNamedComponent(new PatternComponent(pattern), name);
	}
	
	/**
	 * Used to provide a regex that was created previously using this builder.
	 * 
	 * @param name the name to access the regex match
	 * @param regex the regex
	 * @return the builder
	 */
	public RegexBuilder regex(final String name, final Regex regex) {
		return addNamedComponent(regex.getExpression(), name);
	}	
	
	/**
	 * Matches any number including floats, e.g. 12345 or 0.1234. 
	 * 
	 * @param name the name to access the match 
	 * @return the builder
	 */
	public RegexBuilder number(final @Nonnull String name) {
		final ComplexExpression expression = this.list.peekLast();
		expression.add(new GroupComponent(new NumberComponent(), name));
		this.names.add(name);
		//to ignore the capturing group..
		this.names.add(String.valueOf(new Random().nextInt()));
		
		return new RegexBuilder(this.list, this.names);
	}

	
	/**
	 * Build the regex
	 * 
	 * @return the regex
	 */
	public Regex build() {
		if(this.list.size() != 1) 
			throw new IllegalStateException(this.list.size()-1 + " open expressions found!");
		
		final ComplexExpression expression = this.list.pollLast();
		//final String regex = expression.asRegex();
		
		final BreadthFirstTraversal traversal = new BreadthFirstTraversal();
		final Map<String, Integer> names = traversal.traverse(expression);
		
		
		return new Regex(expression, names);
	}
	

	/**
	 * Matches the given string
	 * 
	 * @param constant some constant string
	 * @return the builder
	 */
	public RegexBuilder constant(final @Nonnull String constant) {
		return addComponent(new ConstantComponent(constant));
	}


	/**
	 * Must be called to close a complex expression (e.g.{@link #group(String)}.
	 * 
	 * @return the builder
	 */
	public RegexBuilder end() {
		if(this.list.size() <= 1)
			throw new IllegalStateException("Cannot call end(), no opening component found!");
		final ComplexExpression last = this.list.pollLast();
		final ComplexExpression newLast = this.list.peekLast();
		newLast.add(last);
		return new RegexBuilder(this.list, this.names);
	}


	/**
	 * Starts an optional regex. Any regex that is following will optional until
	 * {{@link #end()} is called. The match of the optional regex can be accessed by the given name.
	 * 
	 * @param name the name to access the match
	 * @return the builder
	 */
	public RegexBuilder optional(final @Nonnull String name) {
		this.list.add(new OptionalComponent(name));
		this.names.add(name);
		return new RegexBuilder(this.list, this.names);
	}
	
	
	private RegexBuilder addNamedComponent(final @Nonnull RegexExpression ex, final @Nonnull String name) {
		final ComplexExpression expression = this.list.peekLast();
		expression.add(new GroupComponent(ex, name));
		this.names.add(name);
		return new RegexBuilder(this.list, this.names);
	}
	
	private RegexBuilder addComponent(final @Nonnull RegexExpression ex) {
		final ComplexExpression expression = this.list.peekLast();
		expression.add(ex);
		return new RegexBuilder(this.list, this.names);
	}
	

	
}
