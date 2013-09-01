package de.java.regexdsl.model;

import junit.framework.Assert;

import org.junit.Test;

public class RegexBuilderTest {
	
	@Test
	public void noMatch() {
		final Regex regex = RegexBuilder
				.create()
				.string("#str1")
				.build();
	
		final Match match = regex.match("12345");
		Assert.assertEquals(null, match.get("str1"));
		
	}
	
	@Test
	public void simpleString() {
		final Regex regex = RegexBuilder
					.create()
					.string("#str1")
					.build();
		
		final Match match = regex.match("this is a test");
		Assert.assertEquals("this", match.get("str1"));
	}
	
	@Test
	public void simpleNumber() {
		final Regex regex = RegexBuilder
					.create()
					.number("#num1")
					.build();
		
		final Match match = regex.match("this 1234.5678 is a test");
		Assert.assertEquals("1234.5678", match.get("num1"));
	}
	
	@Test
	public void numbersAndConstants() {
		final Regex regex = RegexBuilder.create()
					.constant("Times: user=")
					.number("#num1")
					.constant(" sys=")
					.number("#num2")
					.constant(", real=")
					.number("#num3")
					.constant(" secs")
					.build();
		
		final Match match = regex.match("Times: user=0.78 sys=0.14, real=0.06 secs");
		
		Assert.assertEquals("0.78", match.get("num1"));
		Assert.assertEquals("0.14", match.get("num2"));
		Assert.assertEquals("0.06", match.get("num3"));
	}
	
	@Test
	public void namedGroups() {
		final String s = "2170358K->188201K, 2170358K->188201K";
		
		final Regex regex = RegexBuilder.create()
				.group("#first")
					.number("#from").constant("K->").number("#to").constant("K")
				.end()	
				.constant(", ")
				.group("#second")
					.number("#from").constant("K->").number("#to").constant("K")
				.end().build();
		
		final Match match = regex.match(s);
		Assert.assertEquals("2170358", match.get("first->from"));
		Assert.assertEquals("188201", match.get("first->to"));
		Assert.assertEquals("2170358", match.get("second->from"));
		Assert.assertEquals("188201", match.get("second->to"));
		
	}
	
	@Test
	public void optionals() {
		final String s = "12345:6789";
		final String s2 = "6789";
		
		final Regex regex = RegexBuilder.create()
				.optional("#opt")
					.number("#num1")
					.constant(":")
				.end()
				.number("#num2")
				.build();
		
		final Match match = regex.match(s);
		Assert.assertEquals("12345", match.get("opt->num1"));
		Assert.assertEquals("6789", match.get("num2"));
		
		final Match match2 = regex.match(s2);
		Assert.assertEquals(null, match2.get("opt->num1"));
		Assert.assertEquals("6789", match2.get("num2"));
	}

	
	@Test
	public void miltipleBlocks1() {
		final String s = "12:34:56:78";
		final Regex regex = RegexBuilder.create()
				.group("#g1")
					.group("#g2")
						.number("#num1")
					.end()
				.end()
				.build();
		
		final Match match = regex.match(s);
		Assert.assertEquals("12", match.get("g1->g2->num1"));
	}
	
	@Test
	public void miltipleBlocks2() {
		final String s = "12:34:56:78";
		final Regex regex = RegexBuilder.create()
				.group("#g1")
					.group("#g2")
						.number("#num1")
						.constant(":")
						.number("#num2")
					.end()
					.constant(":")
					.group("#g3")
						.number("#num1")
						.constant(":")
						.number("#num2")
					.end()
				.end()
				.build();
		
		final Match match = regex.match(s);
		Assert.assertEquals("56", match.get("g1->g3->num1"));
	}
}
