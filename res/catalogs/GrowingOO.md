# Object-Oriente​d Software, Guided by Tests

Credit goes to Steve Freeman, Nat Pryce.

## 01 What is the point of TDD?

The best approach to handle the uncertainty Software Development, and the fact 
that you constantly broaden you understanding and constantly need to reevaluate and change what you have done,
is to:

* use empirical feedback, apply it to learn about the system
* apply that learning back to the system

TDD is not only unit tests:

* Acceptance tests: Does the whole system wo

### The value of TDD
TDD gives feedback on both, the quality of implementation (does it work) as well as the quality of design (is it well structured)

TDD gives a double benefit:

1. While you write tests, it clarifies understanding and promotes loose coupling. The result is a 
regression test suite and an up to date, executable description
1. While running tests, you detect errors while the context is fresh in you mend. It let's us know when we 
have done enough of a feature (discourages "gold plating")

Listen to the tests: When you code makes it difficult to write tests, the tests are telling you something about your
design.

### Repeated cycles

Work in repeated cycles of activity. Each cycle adds new features and gets feedback about the quantity 
and quality of the work already done.

Incremental and Iterative Development:

* Incremental development builds a system feature by feature. The system is always integrated and always ready for deployment
* Iterative development is the progressive refinement of the implementation of features in response to feedback until they are good enough

#### Adding features
When you want to add a new feature, write a end-to-end acceptance test first, which exercises the 
functionality we want to build. Underneath the guidance of this end-to-end test, to repeated TDD cycles. 
These new acceptance tests are not part of the automatically run tests during build yet - keep them separate
until the feature is implemented.

###  Technical foundation
The two technical foundations to grow a system reliably while coping with unanticipated changes are:

1. Constant and automated testing to catch regression errors, so we can add new new features without breaking existing ones.
1. Keep code as simple as possible so it's easier to understand and modify. Simplicity takes effort, so we constantly refactor

#### Automatic build

* Triggered by check in to a source repository
* Compiles and unit-test the code
* integrates and packages the system
* performs production-like deployment
* exercises the system through its external access points for end to end acceptance tests

### TDD cycle

The TDD cycle is:

* Write a test
* Write some code to get it working
* Refactor the code to be as simple an implementation of the tested feature as possible
* Repeat

Golden rule: Never write new functionality without a failing test.

### Test levels

* Acceptance tests ask: "Does the whole system work?"
* Integration tests ask: "Does our code work against code we can't change?"
* Unit tests ask: "Do our objects do the right thing, are they convenient to work with?"

### Quality

* External quality: how well does the system meet the needs of its customers and users
* Internal quality: how well does the system meet the needs ot its developers and administrators

### Coupling and Cohesion

* Coupling: If elements are coupled, then a change in one forces the other to change.
* Cohesion: The cohesion of an element is a measure of whether its responsibilities form a meaningful unit. 

## 02 Test-Driven Development?
tbd
