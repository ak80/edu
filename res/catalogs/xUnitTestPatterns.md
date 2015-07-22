# xUnit Test Patterns

Credit goes to Gerard Meszaros

# Chapter 1. A Brief Tour

The Simplest Test Automation Strategy That Could Possibly Work:
* Development Process: How the process we use to develop our code affects our tests.
* Customer Test: The first test we should write as the ultimate definition of "what done looks like".
* Unit Test: The tests that help our design emerge incrementally and ensure that all our code is tested.
* Design Fo Testability: The patterns that make our design easier to test, thereby reducing the cost of automation. 
* Test Organization: How we can organize our Test Methods and Test Case classes.

## Development Process

Writing tests first gives an agreed-upon definition of what success looks like. So we strive for storytest-driven 
development by first automating a suite of customer tests that verify the functionality provided by the application.

This is augmented with Unit-Tests to ensure all code-paths are covered, or at least all code paths not covered by 
customer tests.

Unit and customer test are separately organized in suites, and unit tests are integrated in the build as smoke tests. 
The motto is: keep the bar green.

TDD ensures that the software is testable in the first place. In unit tests, keep concerns separate and 
when testing the business logic avoid dependency to the database, the network etc.


## Customer Test

Capture the essence of what the customer wants the system to do. 

Scripted, Data-driven or Recorded Tests, although Recorded Tests tend to be Fragile Tests.

Aim for short tests, because long tests are often Obscure Tests. Well written tests can server as
Documentation. Often it is advisable to bypass the UI with Subcutaneous Testing against Service Facades

As a starting point we prefer Fresh Fixture over Shared Fixture (unless it is Immutable), mainly to
guard against Erratic Tests.

## Unit Test

Must be fully automated and self-checking.
 
With Single Condition Tests we strive for Defect Localization. Dont worry about interacting
tests and tear down, by using a Fresh Fixture. 

## Design For Testability

Automated Testing is much simpler with a Layered Architecture, at least separate Business Logic from
Database and User Interface.

Keep complex UI logic out visual classes, use Humble Dialog.

If the application is complex enough or if we are expected to build Components that will be reused
we augment the unit tests with component tests.

## Test Organization

Split Test Classes with too many Test Methods:
* Testcase Class per Feature
* Testcase Class per Fixture

# Chapter 2. Test Smells

## The Project Smells

Some smells noticed on project (manager) level are:
* Too many Production Bugs
* Buggy Tests
* High Test Maintenance Cost
* Developers Not Writing Tests
* Lost Tests


## The Behavior Smells

Are encountered when we compile or run the tests:
* Fragile Tests
* Assertion Roulette
* Erratic Tests (Interacting Tests, Test Run Wars, Unrepeatable Tests/Manual Intervention)
* Frequent Debugging
* Slow Tests

### Fragile Test 

The root causes have four broad categories:
1. Interface Sensitivity: tests are broken by changes to the test programming API or UI
2. Behavior Sensitivity: tests are broken by changes to the behaviour of the SUT - any change should affect 
as few tests as possible
3. Data Sensitivity: tests are broken by changes of data already in the SUT, especially for SUT using databases
4. Context Sensitivity: tests are broken by differences in the environment surrounding the SUT, e.g. dependence 
on time and date, or external devices or systems

Data Sensitivity is a special case of Context Sensitivity and are caused by Fragile Fixture. Behavior Sensitivity
is of course unavoidable, because changing behaviour must break at least one test - otherwise the behavior
was not covered and verified; but the ideal is that only one test breaks.

## The Code Smells

Classic code smells affect also test code. In addition we find smells like:
* Obscure test, when the test does not communicate intent
* Conditional test logic (makes tests complicated)
* Hard Coded Test Data (where does this come from?)
* Hard-to-test Code
* Test Code Duplication
* Test Logic in Production

# Chapter 3. Goals of Test Automation

Testing takes time and effort, and that means it costs money. Writing and maintaining good tests is hard and usually optional, i.e. not what the customer wants.

That means testing must be justified, and the benefits must outweigh the costs.

Costs:
* Test creation
* Test maintenance

Benefits:
* reduced manual testing
* fewer debugging and troubleshooting in later phases, which is more expensive or very bad if problems are not found and fixed before the customer finds them.

The goals of test automation:

* improve quality:
  * tests as (executable) specification forces you to think and helps to find misssing / contradictory requirements
  * provides an understanding of when you are done, thus helps to avoid gold plating
  * bug repellent to find bugs before they are checked in
  * defect localization
* help understand the SUT: black box component tests are effectively describing the requirements
* reduce risk (and not introduce it): act as a safety net and do no harm
* easy to run tests: fully automated, self checking, repeatable (independant, without manual intervention, non erratic)
* easy to write and maintain tests:
* simple: no conditional logic, verify one condition per test
* expressive: use test utility methods (creation / assertion) methods and a higher level language (dsl) to communicate intent
* separation of concerns: e.g. don't test business logic while verifying the user interface
* tests should require minimal maintenance as the system evolves: avoid test overlap, and make them robust

# Chapter 4. Philosophy of Test Automation

Philosophical differences:

* Test after vs. test first
* Tests or examples
* (write) Test-by-test vs. test all-at-once
* Outside-in vs. inside-out (applies independently to design and coding)
* behavior verification vs. state verification
* fixture designed test-by-test vs. big fixture design upfront

# Chapter 5. Principles of Test Automation

* Write the Tests First
* Design for Testability
* Use the Front Door First: prefer round-trip tests through public API with state verification. Overuse of behavior verification with Mocks can lead to Overspecified Software and brittle tesrs
* Communicate Intent: use Test Utility Methods, avoid Conditional Test Logic, think of tests as documentation
* Don't Modify the SUT
* Keep Tests Independent
* Isolate the SUT
* Minimize Test Overlap
* Minimize Untestable Code
* Keep Test Logic Out of Production Code
* Verify Test One Condition per Test
* Test Concerns Separately
* Ensure Commensurate Effort and Responsibility

# Chapter 6. Test Automation Strategy

A decision is strategic if it is "hard to change", i.e. changing it costs a large amount of effort.
 
Common strategic decisions:
* What kinds of tests to automate?
* Which tools to use to automate then?
* How to manage test fixtures?
* How to ensure the system is easily tested and how the tests interact with the SUT?

## Test Categories
There are, roughly spoken, two categories:
* Per-functionality tests (functional tests) to verify the SUTs behavior in response to a certain stimulus
* Cross-functional tests for various aspects that cut across specific functionality

### Per-Functionality Tests
Verify directly observable behavior; either business related or related to operational requirements. Most of these requirements can also be expressed as use cases, features, user stories or test scenarios.

They all can - and should -be automated.

They are further characterized on two dimensions:
* business / user facing or technology facing
* the size of SUT on which they operate

* Customer Tests: business intent (executable specification)
* Component Tests: architect intent (design of the system)
* Unit Tests: developer intent (design of code)

For customer tests there are various automation technologies, the other two are usually automated with a xUnit style test framework.

### Customer Tests

Verify behavior of entire system or application, often correspond to use cases, features or user-stories.

A.k.a. end-user tests, functional tests, acceptance tests.

Although they may be automated by a developer, an end user should be able to recognize the behavior specified. At least hypotherically if he could read the test specification. In other words, customer tests work on the customers level of abstraction.

### Unit Tests

Verify the behavior of a single class or method that is a consequence of a design decision.

They are typically not directly related to a requirement, but support the developer with a design guidance, definition of done and a safety net of regression tests.

### Component Tests

Verify components, groups of classes that collectively provide some service. Although often called "integration tests" this term can also mean s.t. else.

### Fault Insertion Test

Show up at all levels, with different kinda of faults inserted at each level - usually hard to do for customer tests.

## Cross-functional

* Usability Testing: is it pleasurable?
* Exploratory Testing: is it self-consistent?
* Property Testing: is it responsive, secure, scalable?

Property testing is based on special tools, the other two are done manually.

### Property Testing
They test non functional requirements, e.g.:
* Response time tests
* Capacity tests
* Stress tests

Most of them must be automated, for example because a human tester would not be able to create enough load.

### Usability Tests

Verify fitness for purpose and are not automatable

### Exploratory Testing

Determine whether the product is self-consistent and can't be automated

## Tools to Automate

Use either a tool to monitor an interaction and create a Recorded Test from it, or program / define the test to be a Hand-Scripted Test.

## Test Fixture Strategy

The first phase in the four phase test is to create the SUT and everything it depends on and put them into the state required to exercise the SUT. This is the Fixture Setup

We have three options:
1. Transient Fresh Fixture
2. Persistent Fresh Fixture
3. Shared Fixture
