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
tdb


# Changing Software

# Dependency Breaking Techniques