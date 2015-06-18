package com.github.kn.knives.parboiled;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;

@BuildParseTree
public class CalculatorParser extends BaseParser<Object> {
    public Rule Expression() {
        Var<Character> op = new Var<Character>();

        return Sequence(
        		WhiteSpace(),
                Term(),
                ZeroOrMore(
                        Sequence(
                                FirstOf("+ ", "- "), op.set(matchedChar()),
                                Term()
                        )
                ),
                EOI
        );
    }
 
    public Rule Term() {
        Var<Character> op = new Var<Character>();

        return Sequence(
                Factor(),
                ZeroOrMore(
                        Sequence(
                                FirstOf("* ", "/ "), op.set(matchedChar()),
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
        return Sequence(OneOrMore(CharRange('0', '9')),  WhiteSpace());
    }
    
    public Rule WhiteSpace() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }

    // we redefine the rule creation for string literals to automatically match trailing whitespace if the string
    // literal ends with a space character, this way we don't have to insert extra whitespace() rules after each
    // character or string literal

    @Override
    protected Rule fromStringLiteral(String string) {
        return string.endsWith(" ") ?
                Sequence(String(string.substring(0, string.length() - 1)), WhiteSpace()) :
                String(string);
    }
}
