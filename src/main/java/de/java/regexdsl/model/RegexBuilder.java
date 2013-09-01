package de.java.regexdsl.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.java.regexdsl.component.ConstantComponent;
import de.java.regexdsl.component.ContainerComponent;
import de.java.regexdsl.component.GroupComponent;
import de.java.regexdsl.component.NumberComponent;
import de.java.regexdsl.component.OptionalComponent;
import de.java.regexdsl.component.StringComponent;

//not immutable!
public class RegexBuilder {
	final LinkedList<ComplexExpression> list;
	final List<String> names;
	
	
	private RegexBuilder() {
		this.list =  new LinkedList<ComplexExpression>();
		this.names = new ArrayList<String>();;
		list.add(new ContainerComponent());
	}
	
	

	public RegexBuilder(LinkedList<ComplexExpression> list, List<String> names) {
		this.list = list;
		this.names = names;
	}


	public static RegexBuilder create() {
		return new RegexBuilder();
	}
	
	public RegexBuilder string() {
		return addComponent(new StringComponent());
	}
	public RegexBuilder string(final String name) {
		return addNamedComponent(new StringComponent(), name);
	}
	
	public RegexBuilder group(final String name) {
		this.list.add(new GroupComponent(name));
		this.names.add(name);
		return new RegexBuilder(this.list, this.names);
	}

	public RegexBuilder number() {
		return addComponent(new NumberComponent());
	}
	
	public RegexBuilder number(final String name) {
		final ComplexExpression expression = this.list.peekLast();
		expression.add(new GroupComponent(new NumberComponent(), name));
		this.names.add(name);
		//to ignore the capturing group..
		this.names.add(String.valueOf(new Random().nextInt()));
		
		return new RegexBuilder(this.list, this.names);
	}
	
	private RegexBuilder addNamedComponent(final RegexExpression ex, final String name) {
		final ComplexExpression expression = this.list.peekLast();
		expression.add(new GroupComponent(ex, name));
		this.names.add(name);
		return new RegexBuilder(this.list, this.names);
	}
	
	private RegexBuilder addComponent(final RegexExpression ex) {
		final ComplexExpression expression = this.list.peekLast();
		expression.add(ex);
		return new RegexBuilder(this.list, this.names);
	}
	
	public Regex build() {
		if(this.list.size() != 1) 
			throw new IllegalStateException(this.list.size()-1 + " open expressions found!");
		
		final ComplexExpression expression = this.list.pollLast();
		final String regex = expression.asRegex();
		
		final BreadthFirstTraversal traversal = new BreadthFirstTraversal();
		final Map<String, Integer> names = traversal.traverse(expression);
		
		
		return new Regex(regex, names);
	}
	



	public RegexBuilder constant(String constant) {
		return addComponent(new ConstantComponent(constant));
	}


	public RegexBuilder end() {
		if(this.list.size() <= 1)
			throw new IllegalStateException("Cannot call end(), no opening component found!");
		final ComplexExpression last = this.list.pollLast();
		final ComplexExpression newLast = this.list.peekLast();
		newLast.add(last);
		return new RegexBuilder(this.list, this.names);
	}


	public RegexBuilder optional(String name) {
		this.list.add(new OptionalComponent(name));
		this.names.add(name);
		return new RegexBuilder(this.list, this.names);
	}
	

	
}
