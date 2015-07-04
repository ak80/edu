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
tbd