# Clean Code - Smells and Heuristics

This list is taken from Robert C. Martin's fantastic book ''Clean Code'', chapter 17. I have tried to add a description and solution to each smell and heuristic in the style used to describe a Refactoring.

## Comments

**C1 Inappropriate Information**: A comment holds information that is better kept in your source code control system or issue tracking system.
  Remove the comment.

**C2 Obsolete Comment**: A comment has gotten old, irrelevant or incorrect.
  Remove the comment or fix it.

**C3 Redundant Comment**: A comment describes something that adequately describes itself.
  Remove the comment.

**C4 Poorly Written Comment**: A comment is poorly written: sloppy wording, wrong grammar, rambling, stating the obvious, too long.
  Fix the comment.

**C5 Commented-Out Code**: A piece of code is commented out.
  Delete the commented out code.

## Environment ==

**E1 Build Requires More Than One Step**: More than one step is required to build the system.
  Use a build system or fix the build.

**E2 Tests Require More Than One Step**: More than one step is required to run all the unit tests.
  Fix the test setup.

## Functions ==

**F1 Too Many Arguments**: A function has more than three arguments or could have less.
  Reduce the number of parameters: create an instance variable, or parameter object, or for diadic maybe pass an argument in the constructor or use inheritance.

**F2 Output Arguments**: A function changes an input argument.
  Let the function only change the state of the object it is called on.

**F3 Flag Arguments**: A function has a boolean flag parameter that controls the functionality.
  Remove it, often it is better to have two well-named functions.

**F4 Dead Function**: A function is not called.
  Delete it.

## General
**G1 Multiple Languages in One Source File**: A source file contains more than one language.
  Keep the number of languages to a minimum. Examples: Java Source with XML, HTML, YAML, JavaDoc, JavaScript...

**G2 Obvious Behaviour Is Unimplemented**: An obvious behaviour is missing from a class or function.
  Implement the missing behaviour.

**G3 Incorrect Behavior at the Boundaries**: The code behaves incorrect in boundary conditions.
  Fix the code or safeguard against the boundary condition .

**G4 Overridden Safeties**: A safety mechanism is turned off, or failing test or a compiler warning is ignored.
  Don't ignore warnings and don't turn of safety mechanisms.

**G5 Duplication**: A piece of code, method, algorithm, switch statement or flow is duplicated.
  Eliminate the duplication with refactorings and design patterns.

**G6 Code at Wrong Level of Abstraction**: Higher level general concepts and lower level specific concepts are not separated, but in the same  class, module or component.
  Separate higher level from lower level concepts by abstractions and put
them in different containers.

**G7 Base Classes Depending On Their Derivatives**: A higher level base class has knowledge of its derivatives.
  Remove the coupling to the derivatives.

**G8 Too Much Information**: An interface is wide and deep and offers a lot of information and many functions. This results in high coupling.
  Reduce the number of methods, instance variables, parameters.

**G9 Dead Code**: A piece of code is never executed.
  Remove it.

**G10 Vertical Separation**: Variable or method declarations are not close too where they are used.
  Move definitions closer to the usage.

**G11 Inconsistency**: A name, naming schema, convention, structure or pattern is  used in different ways.
  Consistently apply the same conventions.

**G12 Clutter**: Code is cluttered with variables that are not used, functions that are not called, constructors with empty implementation or comments that don't add information.
  Remove the clutter.

**G13 Artificial Coupling**: A coupling is artificially created, e.g. through a constant, enum or a static function in a more specific class.
  Move it to the appropriate place.

**G14 Feature Envy**: A method is more interested in the variables and functions of another class.
  Unless it violates principles like SOLID move the method to the other class or ask the other class to do more for the method.

**G15 Selector Arguments**: A boolean flag is used to control the logic of a function.
  Split the function accordingly and iv necessary provide two methods, one for the true and one for the false case.

**G16 Obscured Intent**: The intent of code is obscured by Hungarian notation, magic numbers and so on.
  Make the intent of the code clearly visible.

**G17 Misplaced Responsibility**: "A function, constant or data field is not placed were expected.
  Keeping the ""principle of least surprise"" in mind, move it to a better place.".

**G18 Inappropriate Static**: A function is static, often because a good place for it was not found, but should be nonstatic.
  Move it if necessary and make it nonstatic.

**G19 Use Explanatory Variables**: An algorithm uses lower level data, like intermediate results and return values in a specific context.
  Use variables with descriptive names to hold the data, even if they only exist as an explanation if what the data means in this context.

**G20 Function Names Should Say What They Do**: You need to look into a function implementation because the name does't tell you what it does and how it is used.
  Find a better name and rename the function.

**G21 Understand the Algorithm**: A piece of code seems to do what you want, but you don't know why.
  Take the time to understand the algorithm .

**G22 Make Logical Dependencies Physical**: A module logically depends on another.
  Make the dependency physical.

**G23 Prefer Polymorphism to If/Else or Switch/Case**: There is more than one switch statement for the same type of selection.
  Use polymorhism instead.

**G24 Follow Standard Conventions**: There are standard coding conventions.
  Everyone in the team should follow them.

**G25 Replace Magic Number with Named Constant**: A literal is used, that has a special meaning.
  Replace it with a named constant.

**G26 Be Precise**: A decision has to be made, e.g.: is the first match the only? do you use a float for a currency? you don't expect a concurrent update is likely, do you need transactions? Is protected for all variables enough?
  No! Be precise.

**G27 Structure over Convention**: A structural enforcement is better than mere convention.
  E.g.: A base class enforces subclasses to implement all abstract methods - a switch/case however does not force a specific implementation. .

**G28 Encapsulate Conditionals**: A boolean logic with operators is used as a conditional.
  Extract it to its own method.

**G29 Avoid Negative Conditionals**: A conditional is expressed as a negative.
  Express it as positive.

**G30 Functions Should Do One Thing**: A function is doing more than one thing.
  Convert it into many smaller functions, each of which does one thing.

**G31 Hidden Temporal Coupling**: The order in which functions are called is important, but this is not obvious.
  Enforce the order with a bucket brigade: each function returns a result that the next function needs as a parameter.

**G32 Don't Be Arbitrary**: Code is structured in a certain way.
  Make sure it is obvious why it is done this way.

**G33 Encapsulate Boundary Conditions**: Boundary conditions should be encapsulated.
  For example  ''level + 1'' could be ''nextLevel()''.

**G34 Functions Should Descend Only One Level of Abstraction**:
  All statements within a function should be written at the same level of abstraction, which should be one level below the operation described by the name of the function.

**G35: Keep Configurable Data at High Levels**: You have constant such as a default or configuration value that is known and expected at a high level of abstraction.
  Do not bury it in a low-level function, but pass it as a parameter in if needed
there.

**G36 Avoid Transitive Navigation**: Respect the Law Of Demeter.
  Class A uses B, and B uses C. Then A should not know about C and that B uses it. Avoid train.wrecks().

## Java ==

**J1 Avoid Long Import Lists by using Wildcards**: "You are using two or more classes from a package.
  Import the whole package with ''import package.***

**J2 Don't Inherit Constants**: "You are extending an interface just to get easy access to the Constants defined in it.
  Use static import: ''import static InterfaceWithConst.*''

**J3 Constants versus Enums**: You have public static final fields to define Constants
 Use enum instead, and make use of methods ans fields.

## Names ==
**N1 Choose Descriptive Names**: Choose intention revealing names, and regularly review if they still fit.
  A good name says why s.t. exists, what it does and hoe it is used.

**N2 Choose Names at the Appropriate Level of Abstraction**: Don't pick names that communicate implementation
  choose names that reflect the level of abstraction of the class or function.

**N3 Use Standard Nomenclature Where Possible**: Use standards as a basis for names.
  For example patterns, and other conventions like toString().

**N4 Unambigious Names**: Choose names that are unambiguous.

**N5 Use Long Names for Long Scopes**: The length of a name should be related to the scope.

**N6 Avoid Encodings**: Do not encode type or scope information in names - they are distracting and redundant.

**N7 Names Should Describe Side-Effects**: Don't hide side effects with a name. Don't use a simple verb to describe a function that does more than just that simple action.

## Tests ==

**T1 Insufficient Tests**: Tests are insufficient as long as there are conditions that have not been explored or calculations that have not been validated.

**T2 Use a Coverage Tool**: Use a coverage to to find gaps in your tests.

**T3 Don't Skip Trivial Tests**: They are easy to write and their documentary value is higher than the cost to produce them.

**T4 An Ignored Test Is a Question about an Ambiguity**: If requirements are unclear and a behavioral detail is unclear, we can express this by a test that is annotated with @Ignore or commented out if it wouldn't compile.

**T5 Test Boundary Conditions**: Take special care to test boundary conditions.

**T6 Exhaustively Test Near Bugs**: Bugs tend to congregate. If you find one, test that function exhaustively.

**T7 Patterns of Failure Are Revealing**: Sometimes you can diagnose a problem by finding patterns in the way test cases fail.

**T8 Test Coverage Patterns Can Be Revealing**: Looking at the code that is our is not executed by passing tests gives clues to why the failing tests fail.

**T9 Tests Should Be Fast**: Do what you must to keep tests fast. A slow test is a test that won't get run.

