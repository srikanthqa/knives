package com.github.kn.knives.parboiled;

import java.util.Arrays;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import com.github.kn.knives.parboiled.CalculatorParser;

public class CalculatorParserTest {

	@Test
	public void testParse() {
		String input = "1+2";
		CalculatorParser parser = Parboiled.createParser(CalculatorParser.class);
		ParsingResult<Object> result = new ReportingParseRunner<Object>(parser.Expression()).run(input);
		String parseTreePrintOut = ParseTreeUtils.printNodeTree(result); 
		System.out.println(parseTreePrintOut);
	}
	
	@Test
	public void testParseWithSpace() {
		// NOTE: space in front of '1'
		// NOTE: no space behind of '1'
		String input = "1 + 2 ";
		CalculatorParser parser = Parboiled.createParser(CalculatorParser.class);
		ParsingResult<Object> result = new ReportingParseRunner<Object>(parser.Expression()).run(input);
		result.parseErrors
			.stream()
			.forEach(it -> System.out.println(it.getClass() 
					+ " " 
					+ it.getStartIndex() 
					+ " " 
					+ it.getEndIndex() 
					+ " " 
					+ it.getErrorMessage()));
		
		String parseTreePrintOut = ParseTreeUtils.printNodeTree(result); 
		System.out.println(parseTreePrintOut);
	}
}
