Narrative:
In order to play,
As a jbehave engineer,
I want to test stack

GivenStories: path/to/precondition2.story,
              path/to/preconditionN.story

Scenario: stack is first-in last-out order

Given a new stack of books

When push a book twilight to the stack
And push a book new moon to the stack
And pop a book from the stack

Then the top book is twilight