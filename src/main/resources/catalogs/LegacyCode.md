Credit goes to Michael Feathers

# The Mechanics Of Change

## Changing Software

There are four reasons to change software:

1. Adding a feature
2. Fixing a bug
3. Improving design
4. Optimizing Resource Usage

Behaviour is the core aspect of software, and it can be:
* preserved
* changed
* added

All changes done to software need to preserve behaviour:
* either completely (with refactoring and performance improvements)
* or to the most part (doing bug fixes or adding a feature)
Thus, the greatest challenge in software development ist that even when changing primary features, a very large
area of behaviour has to be preserved.

Refactoring is: improving the internal design without changing the external behaviour

The three things that can change when working with a (software) system

1. Structure
2. Functionality
3. Resource usage

In order to mitigate risk when changing software, ask yourself three questions:

1. What changes do we have to make?
2. How will we know that we've done them correctly?
3. How will we know that we haven't broken anything?

Legacy code, in the context of the book, is:
* Code without tests
* Code with high coupling
* Code that was never refactored

## Working with feedback

Changes are made to a system in two ways:

1. Edit and Pray:
 * Carefully plan changes
 * Make change
 * Poke around in the running system, to see if change was enabled - and to make sure you didn't break anything
2. Cover and Modify
 * Cover the piece of choice (where you want to make the change) with good tests
 * Make the change and get feedback from tests (was the change enabled? does everything else still work?)
 
There are two ways to use tests:

1. Show correctness
1. Detect (unintended) change

Definitions: 
* A unit test is an isolated test of the smallest individual components.
* A test harness is code that we write to exercise some piece of software and the code that is needed to run it.

While large tests (integration test) do have their justification, unit test are still necessary, 
because with large tests:
* Error are harder to localize
* Execution time is much longer
* Coverage of newly added functions may be harder to achieve than with unit tests

Unit test run fast (should be around 1/100th of a second. 1/10th is too slow). Unit tests help us localize problems.

The legacy code dilemma: When we change code, we should have tests in place. To put tests in place, we have often 
have to change code!

A common workaround, if a class is hard to test, may be to test a class that uses it.

When you break dependencies in legacy code, some break cleanly. Others end up looking less that ideal, maybe even 
worse than before. Until they are cleanly refactored, you have to suspend you sense of aesthetics a bit.

The algorithm for changing legacy code is:
1. Identify change points
2. Find the test points
3. Break dependencies
4. Write tests
5. Make changes and refactor

Dependency problems manifest in testing:
* an object is difficult to instantiate in the test harness
* a method is difficult to run in the test harness

The three key concepts in legacy work:
1. Sensing
1. Separation
1. Seams

## Sensing and Separation

We need to break dependencies for testing in order to do Sensing and Separation.

Definitions:
* Sensing is verifying the internal state change of an object or a collaborator after a method execution.
* Separation is breaking up classes and decoupling classes in order to get a class under test.
* A Fake object impersonates some collaborator or class. It supports the same interface and in addition supports 
Sensing, so you can verify which methods have been called with which arguments
* A Mock object is a Fake, that performs verification internally. You specify the expected method call with 
arguments to the Mock, execute the function to test, and call verify on the Mock.

## The Seam Model

A Seam is a place where you cn alter behavior in you program, without editing (changing) source code in that place. 
The decision to use one behavior or the other is done at the Enabling Point.

There are:
* Preprocessor Seams: at build time, a preprocessor decided which behaviour to include in the compile
* Link Seams: At runtime, the decision of how to resolve a reference is made 
(e.g. to production or test version). This is done with setting a library / class path
* Object Seams: by subclassing and overriding methods of a production class you can vary or 
replace behaviour

## Tools

In order to effectively work with legacy code you need:
* IDE
* Automated refactoring tool
* Build System
* Framework for mocking
* Test Harness / Unit Testing Tool
* Integration Testing Tool

# Changing Software
tbd

# Dependency Breaking Techniques
tbd