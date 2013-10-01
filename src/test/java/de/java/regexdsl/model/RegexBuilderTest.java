package de.java.regexdsl.model;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

public class RegexBuilderTest {
	
	@Test
	public void noMatch() {
		final Regex regex = RegexBuilder
				.create()
				.string("#str1")
				.build();
	
		final Match match = regex.match("12345");
		Assert.assertEquals(null, match.getByName("str1"));
		
	}
	
	@Test
	public void shouldMatchWholeNumber() {
		final String s = "abcdefg 12345";
		final Regex regex = RegexBuilder
				.create()
				.any()
				.pattern("\\b")
				.number("#num")
				.build();
	
		final Match match = regex.match(s);
		Assert.assertEquals("12345", match.getByName("num"));
	}
	
	@Test
	public void constant() {
		final Regex regex = RegexBuilder
				.create()
				.group("#c")
					.constant("abcde")
				.end()
				.build();
	
		final Match match = regex.match("abcde");
		Assert.assertEquals("abcde", match.getByName("c"));
	}
	
	
	@Test
	public void constantBraces() {
		final Regex regex = RegexBuilder
				.create()
					.constant("(")
					.number("#num")
					.constant(")")
				.build();
	
		final Match match = regex.match("(12345)");
		Assert.assertEquals("12345", match.getByName("num"));
	}
	
	@Test
	public void simpleString() {
		final Regex regex = RegexBuilder
					.create()
					.string("#str1")
					.build();
		
		final Match match = regex.match("this is a test");
		Assert.assertEquals("this", match.getByName("str1"));
	}
	
	@Test
	public void simpleNumber() {
		final Regex regex = RegexBuilder
					.create()
					.number("#num1")
					.build();
		
		final Match match = regex.match("this 1234.5678 is a test");
		Assert.assertEquals("1234.5678", match.getByName("num1"));
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
		
		Assert.assertEquals("0.78", match.getByName("num1"));
		Assert.assertEquals("0.14", match.getByName("num2"));
		Assert.assertEquals("0.06", match.getByName("num3"));
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
		Assert.assertEquals("2170358", match.getByName("first->from"));
		Assert.assertEquals("188201", match.getByName("first->to"));
		Assert.assertEquals("2170358", match.getByName("second->from"));
		Assert.assertEquals("188201", match.getByName("second->to"));
		
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
		Assert.assertEquals("12345", match.getByName("opt->num1"));
		Assert.assertEquals("6789", match.getByName("num2"));
		
		final Match match2 = regex.match(s2);
		Assert.assertEquals(null, match2.getByName("opt->num1"));
		Assert.assertEquals("6789", match2.getByName("num2"));
	}

	
	@Test
	public void grouping() {
		final String s = "12:34:56:78";
		final Regex regex = RegexBuilder.create()
				.group("#g1")
					.number("#num1")
					.constant(":")
					.number("#num2")
				.end()
				.constant(":")
				.group("#g2")
					.number("#num1")
					.constant(":")
					.number("#num2")
				.end()
				.build();
		
		final Match match = regex.match(s);
		Assert.assertEquals("12:34", match.getByName("g1"));
		Assert.assertEquals("56:78", match.getByName("g2"));
	}
	
	@Test
	public void miltipleBlocks() {
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
		Assert.assertEquals("56", match.getByName("g1->g3->num1"));
	}
	
	@Test
	public void anyComponent() {
		final String s = "this is a test \n kjjd jh cjhad kkjh :10";
		
		final Regex regex = RegexBuilder.create()
				.constant("this is a test").any()
				.constant(":")
				.number("#num")
				.build();
		
		final Match match = regex.match(s);
		Assert.assertEquals("10", match.getByName("num"));
		
	}
	
	
	
	@Test
	public void complexExpression() {
		final String s = "2012-11-14T20:41:13.255+0100: 32.211: [GC 32.211: [ParNew\n" +
		                  "Desired survivor size 268435456 bytes, new threshold 15 (max 15)\n" +
		                  "- age   1:   93114784 bytes,   93114784 total\n" +
		                  "- age   2:   70731768 bytes,  163846552 total\n" +
		                  ": 2170358K->188201K(2621440K), 0.0623980 secs] 2170358K->188201K(8912896K), 0.0625050 secs] [Times: user=0.78 sys=0.14, real=0.06 secs]\n"; 
		                            
		
		
		final Regex regex = RegexBuilder.create()
				.group("#date")
					.number().constant("-").number().constant("-").number()
				.end()
				.constant("T")
				.group("#time")
					.number().constant(":").number().constant(":").number()
				.end()
				.constant("+").number().constant(": ")
				.number("#startupTime")
				.pattern(".*")
				.constant("ParNew\nDesired survivor size ").number("#desiredSurvivor").any()
				.constant(": ").number("#heap").constant("K->").number("#heap2").constant("K(")
				.build();
		
		final Match match = regex.match(s);
		
		
		Assert.assertEquals("2012-11-14", match.getByName("date"));
		Assert.assertEquals("20:41:13.255", match.getByName("time"));
		Assert.assertEquals("32.211", match.getByName("startupTime"));
		Assert.assertEquals("2170358", match.getByName("heap"));
		Assert.assertEquals("188201", match.getByName("heap2"));
	}
	
	@Test
	public void regex() {
		final String s = "12:13:14 und 15:16:17";
		
		//create a pattern that can be used multiple times
		final Regex time = RegexBuilder.create()
				.number("#num1").constant(":").number("#num2").constant(":").number("#num3")
				.build();
		
		//use the pattern
		final Regex regex = RegexBuilder.create()
				.regex("#time1", time).constant(" und ").regex("#time2", time)
				.build();
		
		final Match match = regex.match(s);
		
		Assert.assertEquals("12:13:14", match.getByName("time1"));
		Assert.assertEquals("15:16:17", match.getByName("time2"));
	}
	
	@Test
	public void pattern() {
		final Regex regex = RegexBuilder.create()
				.pattern("#number", "^\\d\\.\\d{2}")
				.build();
		
		final Match match1 = regex.match("2.34");
		final Match match2 = regex.match("22.34"); //should not match
		
		Assert.assertEquals("2.34", match1.getByName("number"));
		Assert.assertEquals(null, match2.getByName("number"));
	}
	
	@Test
	@Ignore
	public void test()
	{
		final String s = "xyz (? and so on";
		final String res = s.replaceAll("\\(\\?", "");
		
	}
}
