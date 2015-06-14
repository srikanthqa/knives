package com.github.kn.knives;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

public class CalculatorParserTest {

	@Test
	public void testParse() {
		String input = "1+2";
		CalculatorParser parser = Parboiled.createParser(CalculatorParser.class);
		ParsingResult<Object> result = new ReportingParseRunner<Object>(parser.Expression()).run(input);
		String parseTreePrintOut = ParseTreeUtils.printNodeTree(result); 
		System.out.println(parseTreePrintOut);
	}
}
