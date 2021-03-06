# Working Effectively with Legacy Code

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
 
## I don't have much time and I need to change it

### Sprout Method
A common technique for adding a new feature to a method you can't (yet) bring under test 
is Sprout Method: You add a new method instead of inlining the feature, in order to bring the
new method under test. 

If you have trouble instantiating the class that own the method, try passing null for the parameters
in the constructor or make the sprout method static. Or use Sprout Class.

### Sprout Class
Use Sprout Class, when you need to add a new feature and you can't bring the class under test or if
you wan't to add a new responsibility to a class.


### Wrap Method
When you need to add new behaviour, that needs to happen at the same time when an existing method 
is called, use Wrap Method: Replace the method with a new one, rename it and call it from the new 
wrapper. Now you can add other calls to the wrapper.

### Wrap Class
To use wrap class, first extract an interface and implement it. The wrapper gets the original class 
passed as an argument to the constructor. The wrapper delegates to the original and can add 
behaviour before or after the call to the original method. Basically this is the decorator pattern.

Prefer Wrap Class to Sprout Class when:
1. The new behaviour is completely independent and you don't want to pollute the existing class
2. The class has grown too large and you can't stand to make it worse

### Keep in mind
The result of a Sprout or Wrap often makes the design worse, when you start out. But this will allow
you to take code under test. Then you can refactor. They are starting points for improvements.

## It takes forever to make a change

Two factors contribute to long change time:
1. Understanding
2. Lag time, the time waiting for (test) feedback, due to long build or test times.

The causes of long lag time are
* Dependencies that make build time too long
* Dependencies that make it hard to bring something under test

Reduce build time: bring a class in a test harness; look at its dependencies and extract interfaces from 
concrete classes and work with them. Put interfaces and classes into different packages to make this distinction
explicit.

### The Dependency Inversion Principle
Higher-level modules should depend on abstractions instead of lower-level modules.

Depend on interfaces and abstract classes. Put them in the same package as the client that uses them, or in a
separate package, but not the same as the implementing class.

Now you can edit classes that implement the interface, add new ones and so on. All without impacting the code
that uses the interface. This can also reduce build time and allows to create Fakes for testing

## How Do I Add A Feature

### TDD Algorithm

The TDD algorithm is:

1. Write a failing test case
2. Get it to compile
3. Make it pass
4. Remove duplication
5. Repeat

and for Legacy Code:

0. Get the class under test
1. Write a failing test case
2. Get it to compile
3. Make it pass (try not to change existing code)
4. Remove duplication
5. Repeat

### Liskov substitution principle

Objects of subclasses should be substitutable for objects of their superclasses.

A client should be able to use objects of a subclass without having to know that they are. This is a matter of semantics.

Use these rules of thumb to try to avoid breaking the LSP:
1. Whenever possible, avoid overriding concrete methods.
2. If you do, see if you can call the super method from the overriding method.

Prefer normalized hierarchies: No class has more than one implementation of a method. That means no class has a method that overrides a concrete (non+abstract) method inherited from a superclass.

## I can't get this class into a test harness

### Common Problems

Common problems when you want to get a class into a test harness:
* Objects of the class can't be created easily
* The test harness won't easily build with the class in it
* The constructor we need to use has bad side effects
* Significant work happens in the constructor and we need to sense it

The problem may boil down to these:
* Irritating Parameter
* Hidden Dependency in Constructor
* Irritating Global Dependency
* Onion Parameter
* Aliased Parameter

#### Irritating Parameter

An Irritating Parameter is an object that is passed to the constructor or a method of the class
you want to to bring under test. It is irritating, because you can't (easily) or don't want to 
create it, for example a database or network connection.

Solutions:
* Extract Interface and Create Fake
* Pass Null and Subclass and Override Method

With "Pass Null and Subclass and Override Method" you pass null in the constructor and override the method
that uses the parameter.

#### Hidden Dependency in Constructor

Solutions:
* Parametrize Constructor (may be problematic if a lot of objects are created)
* Extract and Override Getter
* Extract and Override Factory Method
* Supersede Instance Variable

#### Irritating Global Dependency
A dependency on a global variable, e.g. a Singleton or a class with static fields.

Global variables are usually irritating because for tests you need to find out which ones are used
and then set them up

Solutions, that sometimes are enough:
* Parametrize Constructor
* Parametrize Method
* Extract and Override Call

You can also deal with Singletons in this way:
* Introduce static setter: Add a new static method to replace the singleton
* Add a static method to just reset the instance variable to null
* Don't use Singleton

If you are worried that the Static Setter may be used in Production, include a check in the build
and allow it only for test code

Singletons are used when:
1. You model the real world, and there is only one of these things
2. Having more than one of these things could be a serious problem
3. Having two of these things would waste resources
4. The bad reason: you want a global variable

#### Onion Parameter

In order to set up an object, you need to pass another object in the constructor. If that object itself is created
by passing other objects in, which are created by passing objects in that are created ... and so on ... that is an 
Onion Parameter

Solutions:
* Pass Null
* Extract Interface and Use Fake
  
#### Aliased Parameter

Using Extract Interface and Create Fake may not be easy, if a parameter is part of a class hierarchy, because you 
may need recreate the hierarchy with interfaces.

As an alternative, use Subclass and Override Method

### Construction Test

A construction test is a test case you write with the goal of finding out if you can instantiate the object. 
Start simple, no assertions and optimistically just try to create all objects needed to be passed to the
constructor of the class you want to get under test

## I can't run this method in a test harness

Problems:
* Hidden Method, e.g. it's private
* Parameters are hard to construct
* Bad Side Effects, e.g. DB Calls
* We need to sense through

Workarounds for testing a method for a class that is hard to bring into a test harness:
* Expose Static Method
* Break Out Method Object

### Testing a private method

You need to test private method:
* First ask why? Test (indirectly) through a public method, and use the private method as it is used in the code
* Make it public

Making a private method public for test purposes may bother you, because:
1. The method is just an utility; it isn't something clients would care about
2. If clients use the method, they could adversely affect results fom other methods on the class

The solution is: move is to a new class and use this in your original. Now you can test it, but no one can affect
the instance in production because it is hidden in the original.

Should you subvert access protection through reflection to get access to private methods? This is a sort of a cheat
and prevents you from noticing just how bad code is.

### Dealing with final / sealed classes

You can't use Extract Interface with final / sealed classes. Try to:
* use Adapt Parameter
* Create a new Interface to use in the Method and implement a production version that wraps the original parameter

### Dealing with unpleasant side effects

Use Extract Method, to separate UI handling, DB calls and business logic and apply command/query separation

### Command / Query Separation

Is a design principle: A method should be either:
* a command - that causes something to happen, that ultimate changes something, or
* a query - that returns information without changing something. It must be safe too call query multiple times in a row

## I need to make a change, what methods should I test?

### Characterization testing

The simple approach is, to write characterization tests for each method we are going to change

A characterization test: is a test we write around yet untested code, for which we want to preserver behavior
when we are going to change things.

### Propagation of effects (changed to classes)

Changes to classes that cause effects propagate in three basic ways:
1. Return values used by the caller
2. Modifications of objects passed as parameter that are used later
3. Modification of static or global data that is used later

Keep in mind, that when you make changes and look at all clients of a class, that the class might have subclasses or
a superclass and that these also have clients that might be affected!

Use this heuristic to identify effects of change:

1. Look for affected methods, start with the method you want to change
    * if it returns a value, look at it's callers
    * if it modifies any values, look at all methods that use these values, and at the methods that use these methods
    * make sure to check superclass and subclasses
    
2. From affected methods, identify changes to variables
    * check their parameters: see if they or any object that their methods return are used by the code you want to change
    * check if they modify global variables or static data

Use effect sketches, small hand-drawn sketches that show what variables and method return values can 
be affected by software changes. Can be useful when you are trying to decide where to write tests.


## I Need to Make Many Changes in One Area. Do I Have to Break Dependencies for All Classes Involved?

Guidelines for choosing an Interception Point:
* It should be close to the change
* The farther it is away, the higher the risk that your analysis is wrong.
* You can test if the Interception Point works, by making alterations at the Change Point on purpose
* The best Interception Point, is the public method you are going to test
* Higher-level change points are characterizing a bigger area of code, which could mean you have to do
 less dependency breaking - and cover a bigger for refactoring
 
A pinch point is a special interception point. A narrowing in an effect sketch, a place where tests against
a couple of methods can detect many changes in methods. A pinch point is a natural encapsulation boundary, a narrow
funnel for all of the effects for a large piece of code.

If there are too many interception points you need to cover, you are maybe trying to do too much in one change. Or
you just have to write individual tests for all individual changes as close as you can.

Pinch point traps happen, when you are letting unit tests slowly grow into mini-integration tests. Break down the
tested classes to allow smaller, independent tests.

## I Need to Make a Change, but I Don't Know What Test to Write

A guideline on what to test: Think of a test as a documentation: what would be important for the reader? What would 
you like to know about the class if you had never seen it? Start with the some easy cases, the basic purposes
of the class, the most important ones. Then move on to more complex cases and special cases.

Do Targeted Testing: Write tests that cover what you are going to change.

### Characterization tests
Characterization tests are not focused on finding existing bugs. They are about expected behavior:
* During development of a new feature, a test specifies behavior 
* Later the test helps to preserves behavior when other changes are made
So we are putting in a mechanism to find bugs later, when we change something and they show up
as differences from the systems current behavior.
 
A Characterization Test is a test that characterizes actual behavior of a piece of code. It documents
 what is and not what should be.

How to write a characterization test:
* Put a piece of code in a test harness
* Write an assertion, that you know will fail
* Let the test run and let the failure tell you what the behavior is
* Change the test (assertion) so that it expects the actual behavior

Some hints:
* Write the for Tangled pieces of code and hard to get logic
* Maybe use a sensing variable
* Make a list of things that could go wrong, try to trigger them from test
* Use extreme values in input
* Look for invariants (conditions that are always true during method lifetime), try to write tests for them

If you find a bug during implementation of a characterization test: If the system or feature with the bug in it
has never been deployed yet, then fix it. Otherwise you must be careful not to break something else, someone might
already depend on that bug.

### Sensing Variables

A Sensing Variable is a tool during refactoring. It is a variable put in part of production code; the Sensing Variable 
will hold a value, you want to know about, that is otherwise not accessible because it is buried deep down in the code.
It could also be a flag value that you set, depending on the code branch that is executed. The sensing variable is 
removed after refactoring and must not stay in the code!

When want to verify the execution of a branch through a test, make sure there is not another way to make the test pass,
that you overlooked. Use a sensing variable or check with a debugger to make sure.

### Heuristic for Characterization Tests

The heuristic is:

1. Write tests for the area that will change, as many cases that you need to understand the behavior
2. Write Targeted Tests for things you are going to change
3. If you will extract or move, write tests that will verify the existence of thee behavior, and that it is properly
connected.

## Dependencies on Libraries

Avoid littering your code with direct dependencies on third party libraries, to avoid lock-in.

## My Application is all API Calls

TBD 15


# Dependency Breaking Techniques
tbd