# TDD, where did it all go wrong

Credit goes to Ian Cooper, see https://vimeo.com/68375232

These are some quickly done notes from the video. Many things are left our or shortened, and without the video won't make much sense. Go watch it, and make your own notes.

 - [Questions to ask](#)
 - [Targets of test](#)
 - [Zen Of TTD](#)
 - [What isolation means](#)
 - [Red/Green/Refactor](#)
 - [Refactor](#)
 - [Test pyramid](#)
 - [Hexagonal Architecture](#)
 - [Gears](#)
 - [ATDD](#)
 - [BDD](#)
 - [Mocks](#)
 - [Test builder pattern](#)


## Questions to ask

* Why do many developers don't want to write tests? They can't all be idiots!
* Why do tests break when we change implementation details, or merely refactor?
* Why do we have so many tests?
* Why are lean folks abandoning test first?

The short answer is, we are doing TDD wrong, Kent Beck tried to teach us how but we got him wrong. 
We should all return to his book "Test Driven Development: By Example".

## Targets of test

* Test from outside in
* Test the external API, the surface
* Test behavior, not implementation
* Test the domain model, not the user interface

## Zen Of TTD

Avoid testing implementation details, test behaviors

* A test-case per class approach is not in the spirit of TDD
* Test outside-in
* Only write tests to cover implementation details when you need to better understand the refactoring

Kent Beck: "When we write a test, we imagine a perfect interface for our operation. We are telling ourselves 
a story about how the operation will look from the outside."

## What isolation means

According to Beck, a unit test is a test in isolation. Actually that just means: no side effect (to other tests). It is not about: tests only one module. Its the test that is
Isolated, not the thing that is tested.

Explicitly writing tests that target one method is not TDD. Its wrong to say: just have one class under test and everything else ia just mocked out.

Why?

That would lead to overspecification, mocks would dig to deep in the implementation details. It breaks as soon as you change implementation because the mocks break!

What do we mock?

Only mock things that prevent your test from beeing isolated. A bit more on this later

## Red/Green/Refactor

or 

1. Write a test
2. Make it compile
3. Run to see that it fails
4. Make it run
5. Remove duplication

### Sinful green

Why are we allowed to be sinful in the green phase?

We cant think about solving a problem and at the same time engineer it well.
The goal is to get to solutions fast, write a big blob of code that just solves the problem.

### Refactor

After green, do refactor:
* Now is the time for clean cide
* SOLID, DRY ...
* Check for smells
* refactor to patterns

But, don't add new tests! You do only safe refactorings. If yoy would run code coverage, after refactoring, then everything should still be covered. If not, you introduced a new behavior!

If you add tests at this point, you are coupling the test to implementation details. This will break in the future when you change details while refactoring

## Test pyramid

The bulk of tests should be unit test. Have some integration tests to test e.g. with database, to verify that hibernate mappings are ok etc.

Only have few UI tests and tests for external interface tests (eg REST API etc), to see that widgets work and are hooked up correctly.

See also ice-cream cone anti-pattern.

## Hexagonal Architecture

Use Ports and Adapter, the hexagonal architecture. The ports can be ingoing (explicit contracts) and outgoing (implicit contracts, eg database).

Integration tests happen between port and adapter. System tests on the outside. We unit test the port, that is our API, our interface to the code.

## Gears

Are yoy missing the exploratory benefit of TDD? Feeling your way to a solution... Do yoy need fine grained feedback? The answer is the gears concept!

Most of the time we work like described, but you can shift down! When you need more feedback and control, then go back to you old way of doing TDD with APIs.

Be prepared that the tests you write now is coupled to implementation and won't express the behaviour. Maybe throw them away afterwards! You still have higher level tests covering your behavior.

The argument is either:
* you are testing inside the app, not the ports. The tests are coupled. The maintenance will be high. Throw away!
* the fact that you needed these tests to explore, and find your way through the code, suggests that they may be useful in the future. In that case, make clear (in comments) that these tests are bound to a particular implementation and that you should throw them away if you are about to change the implementation.

## ATDD

Work with customer to define behaviour. Capture with specs like Fitnesse, Cucumber etc

Criticism:
* Kent Beck: usually customer not available (early on). But with only having unit test there is the danger that they devolve to only developer tests (ie testing API instead of behavior).
* James Shore: customers never looked at our Fitnesse ATDD tests, they were slow, hard to write, devs hated them!

So, work closely with customer to get to the level were you could implement them but actually do it with unit tests.

## BDD

BDD starts out with the criticism and observations as this talk, takes it further.

But, key issue of this talk is about how we write our unit tests


## Mocks

Don't mock internals, mock public classes. If you mock internals you couple implementation details to your mocks. But internals must be free to change with refactoring. Dont mock adapters, mock ports


## Test builder pattern
Use th (test) builder pattern to stamp out objects you need. You can have specific methods to add properties that are significant for your test
    