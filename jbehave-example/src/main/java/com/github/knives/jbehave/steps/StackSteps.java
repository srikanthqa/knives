package com.github.knives.jbehave.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.Stack;

import static org.junit.Assert.assertEquals;


public class StackSteps {

    private Stack<String> stack;

    @Given("a new stack of books")
    public void givenANewStack() {
        stack = new Stack<String>();
    }

    @When("push a book ${title} to the stack")
    public void whenPushABook(String title) {
        stack.push(title);
    }

    @When("pop a book from the stack")
    public void whenPopABook() {
        stack.pop();
    }

    @Then("the top book is ${title}")
    public void thenVerifyTopBook(String title) {
        assertEquals(title, stack.peek());
    }

    @Then("the stack has ${count} books")
    public void thenVerifyNumberOfBook(int numBook) {
        assertEquals(numBook, stack.size());
    }
}
