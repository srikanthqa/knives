package com.github.kn.knives;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class CalculatorParser extends BaseParser<Object> {
    public Rule Expression() {
        return Sequence(
                Term(),
                ZeroOrMore(
                        Sequence(
                                FirstOf('+', '-'),
                                Term()
                        )
                )
        );
    }
 
    public Rule Term() {
        return Sequence(
                Factor(),
                ZeroOrMore(
                        Sequence(
                                FirstOf('*', '/'),
                                Factor()
                        )
                )
        );
    }
 
    public Rule Factor() {
        return FirstOf(
                Number(),
                Sequence('(', Expression(), ')')
        );
    }
 
    public Rule Number() {
        return OneOrMore(CharRange('0', '9'));
    }
}
