Credit goes to Uncle Bob, Robert C. Martin

# Ground Rules
 * Always leave the code cleaner than you found it
 * Getting code to work and making it clean are different activities, but are equally important

# Principles

 * KISS: Keep It Simple, Stupid
 * You Ain't Gonna Need It (YAGNI)
 * Optimize for readability first, Premature optimization is evil

 * DRY: Don't Repeat Yourself
 * Single Level of Abstraction (SLA)
 * Single Responsibility Principle (SRP)
 * Separation of Concerns (SoC)

 * Interface Segregation Principle (ISP)
 * Dependency Inversion Principle (DIP)
 * Information Hiding Principle
 * Open Closed Principle
 * Tell, don't ask
 * Law of Demeter
 * Design and Implementation do not overlap

 * Favor Composition over Inheritance (FCoI)
 * Liskov Substitution Principle (LSP)
 * Principle of Least Astonishment
 * Implementation mirrors design

# Classes and Modules

## Principles
 * Single Responsibility Principle: Is there only one reason for the class to change?
 * Open-Closed Principle: Is the module  both open for extension and closed for modification?
 * Dependency Inversion Principle: Does high-level modules depend on low-level modules?
 * High cohesion: Do elements / code belong together?
 * Low coupling: Are dependencies kept low?
 * Is data properly encapsulated?
   * Hide as much as possible
   * Only expose what is necessary and as abstract as possible
   * Don't generate getters and setters by default
 * Is the object not hybrid, but
   * a real object, properly encapsulated
   * or a data transfer object data structure with no functionality?
   * or maybe an active record (DTO with save() and find())?
 * Do you avoid public variables unless:
   * they are static final
   * or for pure data transfer objects
 * Is there a short description in about 25 words without "and", "or", "if", "but"?
 * Are you using as much instance variables a possible in each method?
 * Did you consider extracting one or more classes if the instance variable count is too high?

## Naming
 * Is the name a noun or noun phrase?
 * Does the name describe the class responsibility?
 * Did you avoid the weasel words "Processor", "Manager", "Super"?
 * Is the name expressive and intention revealing? Why it exists, what it does, how it is used?
 * Is the naming consistent with other classes?
 * Are all instance variables prefixed with an underscore if this is convention?

## Sizing, Formatting and Ordering
 * Is the class smaller than 200 to 500 lines of code?
 * Is there a blank line between package declaration and imports and the class definition?
 * Are all variables declared at the top?
 * Did you declare variables in the order public static followed by private static followed by private?
 * Are all instance variables prefixed with an underscore if this is convention?
 * Is there a blank line between functions?
 * Are variables that belong together defined together?
 * Are public methods ordered by importance and grouped by conceptual affinity?
 * Are public methods followed by their  private utility methods?
 * Are package, import and class without indentation?
 * Is there one level of indentation for fields and methods?

# Methods

## Rules
 * Is it small?
 * Does it only one thing?
 * Does it not repeat itself (DRY)?
 * Does it operate on one level of abstraction and not multiple?
 * Is your method free of side effects? Does it not change input parameters, global or class variables that are not the main and only purpose of a function and thus are unexpected?
 * Is command query separation  respected, does it either do something or answer something?
 * Is the law of Demeter respected? Do you avoid train wrecks?
   * A method f of a class C should only call the methods of these:
     * C
     * An object created by f
     * An object passed as argument to f
     * An object in instance variable of C

## Naming
 * Is the name a verb or verb phrase?
 * Is the name expressive and intention revealing? Why it exists, what it does, how it is used?
 * Is the naming consistent with other methods?

## Parameters
 * Can you reduce the number of parameters with a parameter object or creating instance variables?
 * Don't use more than three parameters!
 * Can you avoid a triadic function dyadic, by passing in the Constructor or through inheritance from an often passed object?
 * Can you make dyadic function monadic?
 * Don't use output parameters!
 * Can you replace a flag value (Boolean switch) by making two functions?

## Error Handling
 * Do you use exception instead of return codes?
 * Can you extract a try/catch block into its own function?
 * Are using unchecked exceptions? Or checked exception only for an error that can be recovered?
 * Is your exception telling you where the error occurred?
 * Is your exception telling you what the code tried to do
 * If a method throws many exceptions, do you use a wrapper to catch then and return only one that is more relevant? Or can you create a super class for all of them?
 * Is your exception a special case, that requires special handling? Then use the special case pattern?

## Sizing, Formatting and Ordering
 * Are there less than 100 – 120 characters per line?
 * Are local variables defined at the top of the function?
 * Is there a space between operators?
 * Is there no space between a function name and opening braces?
 * Is there one space after the comma that separates a parameter?

## Code Principles
 * Can you move switch statements into a factory and use polymorphism?
 * Does a switch statement only switch on fields from the class itself?
 * Can you extract code blocks in loops or if/else statements into a method?
 * Is the code block in loops or if/else statements as short as possible?
 * Can you extract a complex conditions into a method?
 * Is there one additional level of indentation for each block?
 * Are there only one or two levels of indentation?
 * Do you avoid passing and returning null?
 * Do you throw an exception or use the special case pattern (null object) instead of returning null?
 * Do you try to use structured programming without being fanatic:
    * No goto
    * If sensible only one entry point
    * If sensible only one exit point (only one return, no break or continue)

# Variables

 * Is the name expressive and intention revealing? Why it exists, what it does, how it is used?
 * Is the naming consistent with other variables?
	

# Comments

 * Every time you think "I better comment this", first thank about how to clean it up, make it more expressive
 * If the comment is for a name, then rename the class or method or variable
 * If the comment explains code, extract the code into its own, well named method
 * If the comment it is a change log, remove it and use a source code control system
 * Remove other bad comments:
   * Noise or mumbling with no added value
   * Private API Javadoc
   * Journal or change log
   * Commented out code
   * Position markers
   * Brace end markers
 * The comment is allowed if it is:
   * legal reference (to another file, not the whole text)
   * clarification or explanation of intent
   * warning of consequences
   * a public API Javadoc

# Boundaries
 * Can you use a wrapper to reduce a complex API and only provide the methods you need?
 * Can you write "learning tests" for a new API or third party library?
 * Do you control boundaries?
   * Have only a few places to call them
   * Create a wrapper or adapter

# Testing

## The three laws of TDD
 1. You may not write production code until you have written a failing unit test
 1. You may not write more of a unit test than is sufficient to fail, and not compiling is failing
 1. You may not write more production code than is sufficient to pass the currently failing test

## Principles
 * Is your test code as clean as production code?
 * Is the only difference in terms of performance?
 * Do you structure tests with the build-operate-check or given-when-then pattern?
 * Do you use a testing API, a special language or set of functions to write tests?
 * Do you keep the number of asserts per test low, either on assert per test or asserting only one concept per test?
 * Do avoid duplication of the given and when parts:
   * with the template method
   * or by putting them into the @Before

## FIRST
 * Fast
 * Independent
 * Self-validating
 * Repeatable
 * Timely

# Systems Startup
 * Did you separate construction (startup) from use (runtime logic)?
   * Factories
   * Separation of main
   * Dependency Injection?
 * Do you have a consistent global strategy for resolving dependencies?
 * Are you handling cross-cutting concerns with AOP?
 * Are you using a Domain Specific Language if applicable?


# Design

## Kent Becks rules for design
 1. Runs all the tests
 1. Contains no duplication
 1. Expresses the intent of the programmer
 1. Minimizes the number of classes and methods
 1. The rules are given in order of importance

## Principles
 * Use TDD
 * KISS
 * Make no excuse for writing bad code
 * Don't write code ahead for flexibility and requirements base on guess work. Evolve properly with TDD and Refactoring
 * Use design patters and idioms
 * Use refactoring to
   * introduce levels of abstraction
   * increase cohesion
   * decrease coupling
   * modularize systems
   * shrink functions and classes
   * choose better names
 * Remove duplication with the template method
 * Try to respect the Liskov Substitution Principle

# Concurrency

Do you have one of the misconception:
 * Always improves performance
 * Design does not change
 * No issues in container like web or EJB

Did you respect the ground rules?
 * Keep concurrent code away from non-concurrent code and clearly separated
 * Limit access to shared data

## Principles
 * Do you avoid synchronized by using copies of shared data if possible?
 * Do you use a thread-safe library? Thread-safe collection?
 * Do you have a solid shutdown logic?

## Testing
 * Do you run your tests frequently and long with different configurations, on different platforms and with more threads than processors?
 * Do you track down every fluke in testing instead of ignoring it as cosmic rays?
 * Do you have test objects that are slow, medium and fast running or is your code instrumented for yielding / waiting in a random manner?



