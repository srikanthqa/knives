package com.github.knives.java.pattern;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;


public class PatternTest {

	@Test
	public void testQuoteReplace() {
		assertEquals("Uq4*Sc2$","test".replaceAll("test", Matcher.quoteReplacement("Uq4*Sc2$")));
	}

}
