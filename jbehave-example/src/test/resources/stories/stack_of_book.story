Narrative:
I want to test stack

Scenario: stack is first-in last-out order

Given a new stack of books

When push a book twilight to the stack
And push a book new moon to the stack
And pop a book from the stack

Then the top book is twilight

Scenario: stack keep count correctly

Given a new stack of books

When push a book twilight to the stack
And push a book new moon to the stack

Then the stack has 2 books

Scenario: stack keep count correctly after pop

Given a new stack of books

When push a book twilight to the stack
And pop a book from the stack

Then the stack has 0 books

Scenario: initial count of a stack should be 0

Given a new stack of books

Then the stack has 0 books