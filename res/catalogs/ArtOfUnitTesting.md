# The Art Of Unit Testing
Credit goes to Roy Osherove

- [Chapter 1](#)
- [Chapter 2](#)
- [Chapter 3](#)
- [Chapter 4](#)
- [Chapter 8](#)
- [Chapter 9](#)

# Chapter 1

A Unit of Work: The sum of all actions, that happen between the call of a public method and the recognizable result, during test.

Results of a Unit Of Work are:
1. The method returns a value
2. There is a recognizable state change, that can be detected with public methods
3. There is a call to an external system that doesn't return a value or that we can't control (eg logging system)

# Chapter 2
The naming scheme for test classes: [Classname]Tests

The naming scheme for test cases:
*_[UnitOfWorkName]_[Scenario]_[ExpectedResult]
* [TestedMethod_Scenario_Behavior]

For example:
* UserLogin_IsInvalidUser_ReturnsFalse()
* UserLogin_IsValidCredentials_ReturnsTrue()

Testing of results can be:
1. Value-based testing: Check the return value
2. State-Based Testing: Check that the behavior has changed due to internal state changes
3. Interaction Testing: Check method calls to another object

#Chapter 3

A stub is introduced by extracting the interface and implementing the stub.

The difference is: 
* Stubs are there to help you break dependencies. There are no asserts against stubs!
* Mocks are there to help you verify (assert) interaction.

Fakes are test doubles, patterns used to imitate things:
* Stubs: A controlled substitute for a dependency (Collaborator)
* Mocks: A controlled substitute that can be used to do verification

Inject Interface-based Seams:
Pass and save for later
 through constructor
 through setter
Pass before the method you test:
  as parameter
  as factory class
  as local factory method (inherit and override in test)

# Chapter 4

Verify only one of: return value or state change or interaction!
Use only one Mock per test!

# Chapter 8

Good unit tests are:
* trustable
* maintainable
* readable

Trustable means:
* No bugs in tests, reliable tests
* No logic in tests (conditions/loops)
* Test only one concern
* Separate Unit from Integration tests
* Do Code Reviews

Maintainable means:
* Test public contract only
* Remove duplication (DRY)
* Tests must be isolated

Private or protected methods, that you need to test, are handled one of these ways:
* Make public
* Extract into new class
* Make static

What is a smell, that tests are not isolated?
* Tests are ordered / building on each other
* Tests calling other tests
* Tests sharing in-memory state without resetting it
* Integration test using external resources without roll back

# Chapter 9

The values of code integrity:
* Automatic Builds
* Continuous Integration
* Unit Testing and Test-driven Design
* Code Consistency based on agreed Quality Standards
* Bug Fixing, Test Fixing, Build Fixing with high priority

Start writing tests:
* Find the most used parts
* Find the most complex parts
* Find the parts with a history of bugs

Remember that 20% of code are responsible for 80% of bugs

Use these dimensions to decide where to start adding tests and refactor legacy code: Logical Complexity vs Coupling/Degree of dependency


There are two approaches for a selection strategy (that tells you where to begin with unit testing and refactoring):
* Easy first (low complexity, low coupling)
* Hard first (high complexity, high coupling)

Write integration tests, before you refactor legacy code!