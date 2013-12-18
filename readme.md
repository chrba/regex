Java Regex DSL
=============

Description
-----------------
Regular expressions are a powerfull way to match or extract information from a text in a very short way. But regular expressions can become lenghty and hard to read. This is especially true when you have a lot of capturing groups you want to refere to later in a match. Java regex DSL uses the builder pattern to provide a fluent API which makes  it easier to create large regular expressions by splitting it into reusable named components and extract information from it. 

How to use Java Regex DSL
------------------------------------------

### Basic Usage

The first step is  to create a <code>Regex</code> object that describes the regular expression using <code>RegexBuilder</code>. To create a <code>Regex</code> that matches on a string followed by a number you would write

    Regex regex = RegexBuilder.create()
                         .string("#name1").number("#name2")
                         .build();
    

To match the created <code>Regex</code> against a text, e.g. "foofoofoo1234" use:

    Match match = regex.match("foofoofoo1234");

You can now access the match using the specified names "name1" and "name2":

    match.getByName("name1"); //will result in "foofoofoo"
    match.getByName("name2"); //will result in "1234"


### Groups

You can group expressions by using <code>group()</code>, which takes an optional parameter - the name of the group to access in a match. Every expression following the group will be considered part of the group until the group is closed using <code>end()</code>.  Let's say you want to parse a timestamp of the following format h:m:s.ms you could write

    Regex regex = RegexBuilder.create()
                         .group("#timestamp")
                              .number("#hour").constant(":").number("#min).constant(":").number(#secs).constant(":").number("#ms")
                         .end()
                         .build();

You can then access a match like so

    Match match = regex.match("10:34:22.234");
    match.getByName("timestamp"); //will return the group, i.e. 10:34:22.234
    match.getByName("timestamp->ms") //will return the ms of the group timestamp, i.e. 22.234

You can nest as many groups as you like, as you can see in this non-sence example:


    Regex regex = RegexBuilder.create()
                         .group("#g1")
                            .group("#g2")
                                .group("#g3")
                                   .string("#myString")
                                .end()
                            .end()
                          .end()
                          .build();

     Match match = regex.match("foofoofoo");
     match.getByName("g1->g->g3->myString"); //will return "foofoofoo"

### Optionals

To mark an expression as optional you can use the <code>option()</code>. It is used in exactly the same way as a </code>group</code> expression. 

### Reuse regexes

You can reuse a regex by nesting it into the builder:

    Regex regexToReuse = ... //some regex

    Regex regex = Builder.create()
                          .regex("#reused", regex) //reuse the previously created regex here
                          .build()


### Pattern pass through

To pass through a regular expression use the </code>pattern()</code> expression;

    Regex regex = Builder.create()
                            .pattern("#myPattern", ".*(\d)") //use any pattern you like
                          .build()

### List of supported expressions

<table>
  <tr>
    <th>Expression</th><th>Description</th>
  </tr>
  <tr>
    <td>string(name)</td><td>Matches any word character</td>
  </tr>
  <tr>
    <td>number(name)</td><td>Matches any number, including floats (e.g. 0.2345) </td>
  </tr>
  <tr>
    <td>any()</td><td>Matches any character, including whitespaces</td>
  </tr>
  <tr>
    <td>regex(name, regex)</td><td>Matches the given <code>Regex</code></td>
  </tr>
  <tr>
    <td>pattern(name, pattern)</td><td>Matches the given regular expression</td>
  </tr>
  <tr>
    <td>group(name)</td><td>Starts a group (has to be closed with <code>end()</code>)</td>
  </tr>
  <tr>
    <td>option(name)</td><td>Starts an optional expression (has to be closed with <code>end()</code>)</td>
  </tr>
</table
