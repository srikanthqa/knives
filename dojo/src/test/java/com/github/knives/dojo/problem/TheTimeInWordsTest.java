package com.github.knives.dojo.problem;

import static org.junit.Assert.*;

import org.junit.Test;

public class TheTimeInWordsTest {

	@Test
	public void test() {
		assertEquals("five o' clock", TheTimeInWords.convert(5, 00));
		assertEquals("one minute past five", TheTimeInWords.convert(5, 01));
		assertEquals("ten minutes past five", TheTimeInWords.convert(5, 10));
		assertEquals("half past five", TheTimeInWords.convert(5, 30));
		assertEquals("twenty minutes to six", TheTimeInWords.convert(5, 40));
		assertEquals("quarter to six", TheTimeInWords.convert(5, 45));
		assertEquals("thirteen minutes to six", TheTimeInWords.convert(5, 47));
		assertEquals("twenty eight minutes past five", TheTimeInWords.convert(5, 28));
		
		assertEquals("one minute to six", TheTimeInWords.convert(5, 59));
		assertEquals("quarter past five", TheTimeInWords.convert(5, 15));
	}

}
