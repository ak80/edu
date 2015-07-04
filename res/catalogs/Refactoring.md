# Refactoring

- [List of Refactorings](#list-of-refactorings)
  - [Composing Methods](#composing-methods)
  - [Moving Features Between Objects](#moving-features-between-objects)
  - [Organizing Data](#organizing-data)
  - [Simplifying Conditional Expressions](#simplifying-conditional-expressions)
  - [Making Method Calls Simpler](#making-method-calls-simpler)
  - [Dealing with Generalization](#dealing-with-generalization)
  - [Big Refactorings](#big-refactorings)
- [Code Smells](#code-smells)
  - [List of Smells](#list-of-smells)
  - [Remedies](#remedies)

# List of Refactorings 
The Refactorings. Credit goes to Martin Fowler and all the people he gives credit to for this

## Composing Methods

**Extract Method**: You have a  code fragment that can be grouped together.
  Turn the fragment into a method whose name explains the purpose of the method.

**Inline Method**: A method's body is just as clear as its name.
  Put the method's body into the body of the callers and remove the method.

**Inline Temp**: You have a temp that is assigned once with a simple expression, and the temp is getting in the way of other refactorings.
  Replace all references to that temp with the expression.

**Replace Temp with Query**: You are using a temporary variable to hold the result of an expression.
  Extract the expression into a method. Replace all references to the temp with the new method. The new method can be used in other methods.

**Introduce Explaining Variable**: You have a complicated expression.
  Put the result of the expression, or parts of the expression, in a temporary variable with a name that explains the purpose.

**Split Temporary Variable**:You have a temporary variable assigned to more than once, but it is not a loop variable nor a collecting temporary variable.
  Make a separate temporary variable for each assignment

**Remove Assignments to Parameters**:The code assigns to a parameter.
  Use a temporary variable instead. Note: this is about assigning a parameter foo to refer to another object - not about doing something with the object referenced by foo.

**Replace Method with Method Object**: You have a long method that uses local variables in such a way that you can not apply Extract Method.
  Turn the method into its own object so that all the local variables become fields of that object. You can then decompose the method into other methods on the same object.

**Substitute Algorithm**: You want to replace an algorithm with one that is clearer.
  Replace the body of the method with the new algorithm.

## Moving Features Between Objects

**Move method**: A method is, or will be, using or used more features of another class than the class on which it is defined.
  Create a new method with a similar body in the class that uses it most. Either turn the old method into a simple delegation, or remove it altogether.

**Move field**: A field is, or will be, used by another class more than the class on which it is defined.
  Create a new field in the target class, and change all its users.

**Extract Class**: You have one class doing work that should be done by two.
  Create a new class and move the relevant field and methods from the old class into the new class.

**Inline class**: A class isn't doing very much.
  Move all of its features into another class and delete it.

**Hide Delegate**: A client is calling a delegate class of an object.
  Create method on the server to give the delegate.

**Remove Middle Man**: A class is doing too much simple delegation.
  Get the client to call the delegate directly.

**Introduce foreign method**: A server class you are using needs an additional method, but you can't modify the class.
  Create a method in the client class with an instance of the server class as its first argument.

**Introduce Local Extensions**: A server class you are using need several additional method, buy you can't modify the class.
  Create a new class that contains these extra methods. Make this extension class a subclass or a wrapper of the original.


## Organizing Data

**Self Encapsulate Fields**: You are accessing a field directly, but the coupling to the field is beginning awkward.
  Create getting and setting methods for the field and user only those to access the field.

**Replace Data Value with Object**: You have a data item that needs additional data or behavior.
  Turn the data item into an object.

**Change Value to Reference**: You have a class with many equal instances that you want to replace with a single object.
  Turn the object into a reference object.

**Change Reference to Value**: You have a reference object that is small, immutable, and awkward to manage.
  Turn it into a value object.

**Replace Array with Object**: You have an array in which certain elements mean different things.
  Replace the array with an object that has a field for each element.

**Duplicate Observed Data**: You have domain data available only in a GUI control, and domain methods need access.
  Copy the date into a domain object. Set up an observer to synchronize the two pieces of data.

**Change Unidirectional Association to Bidirectional**: You have two classes that need each to use reach other's features, but there is only a one-way link.
  Add back pointers, and change modifiers to update both sets.

**Change Bidirectional Association to Unidirectional**: You have a two-way association but one class no longer needs features from the other.
  Drop the unneeded end of the association.

**Replace Magic Number with Symbolic Constant**: You have a literal number with a particular meaning.
  Create a constant, name it after the meaning, and replace the number with it.

**Encapsulate Field**: There is a public field.
  Make it private and provide accessors.

**Encapsulate Collection**: A method returns a collection.
  Make it return a read-only view and provide add/remove methods.

**Replace Record with Data Class**: You need to interface with a record structure in a traditional programming environment.
  Make a dumb data object for the record.

**Replace Type Code with Class**: A class has a numeric type code that does not affect its behavior.
  Replace the number with a new class.

**Replace Type Code with Subclasses**: You have an immutable type code that affects the behavior of a class.
  Replace the type code with subclasses.

**Replace Type Code with State/Strategy**: You have a type code that affects the behavior of a class, but you cannot use subclassing.
  Replace the type code with a state object.

**Replace Subclass with Field**: You have subclasses that vary only in method that return constant data.
  Change the methods to superclass fields and eliminate the subclasses.

## Simplifying Conditional Expressions

**Decompose Conditional**: You have a complicated conditional (if-then-else) statement.
  Extract methods from the condition, then part, and else-parts.

**Consolidate Conditional Expression**: You have a sequence of conditional tests with the same result.
  Combine them into a single conditional expression and extract it.

**Consolidate Duplicate Conditional Fragments**: The same fragment of code is in all branches of conditional expression.
  Move it outside of the expression.

**Remove Control Flag**: You have a variable that is acting as a control flag for a series of boolean expressions.
  Use break or return instead.

**Replace Nested Condition with Guard Clauses**: A method has conditional behavior that does not make clear the normal path of execution.
  Use guard clauses for all special cases.

**Replace Conditional with Polymorphism**: You have a conditional that chooses different behavior on the type of an object.
  Move each leg of the conditional to an overriding method in a subclass. Make the original method abstract.

**Introduce Null Object**: You have repeated checks for a null value.
  Replace the null value with a null object.

**Introduce Assertion**: A section of code assumes something about the state of the program.
  Make the assumption explicit with an assertion.

## Making Method Calls Simpler

**Rename Method**: The name of the method does not reveal its purpose.
  Change the name of the method.

**Add parameter**: A method needs more information from its caller.
  Add a parameter for am object that can pass on this information.

**Remove Parameter**: A parameter is no longer use by the method body.
  Remove it.

**Separate Query from Modifier**: You have a method that returns a value but also changes the state of an object.
  Create two methods, one for the query and one for the modification.

**Parametrize Method**: Several methods do similar things but with different values contained in the method body.
  Create one method that uses a parameter for the different values.

**Replace Parameter with Explicit Methods**: You have a method that runs different code depending on the values of an enumerated parameter.
  Create a separate method for each value of the parameter.

**Preserve Whole Object**: You are getting several values from an object and passing these values as parameters in a method call.
  Send whole object instead.

**Replace Parameter with Method**: An object invokes a method, then passes the result as a parameter for a method. The receiver can also invoke this method.
  Remove the parameter and let the receiver invoke the method.

**Introduce Parameter Object**: You have a group of parameters that naturally go together.
  Replace them with an object.

**Remove Setting Method**: A field should be set at creation time and never altered.
  Remove any setting method for that field.

**Hide Method**: A method is not used by any other class.
  Make the method private.

**Replace Constructor with Factory Method**: You want to do more than simple construction when you create an object.vReplace the constructor with a factory method.

**Encapsulate Downcast**: A method returns an object that needs to be downcasted by its callers.
  Move the downcast to within the method.

**Replace Error Code With Exception**: A method returns a special code to indicate an error.
  Throw an exception instead.

**Replace Exception with Test**: You are throwing an exception on a condition the caller could have checked first.
  Change the caller to make the test first.

## Dealing with Generalization

**Pull Up Field**: Two subclasses have the same field.
  Move the field to the superclass.

**Pull Up Method** :You have methods with identical results on subclasses.
  Move them to the superclass.

**Pull Up Constructor Body**: You have constructors on subclasses with mostly identical bodies.
  Create a superclass constructor. call this from the subclass methods."

**Push Down Method**: Behavior on a superclass is relevant only for some of its subclasses.
  Move it to those subclasses.

**Push Down Field**: A field is used only by some subclasses.
  Move the field to those subclasses.

**Extract Superclass**: You have two coarse with similar features.
  Create a superclass and move the common features to the superclass.

**Extract Interface**: Several clients use the same subset of a class's interface, or two classes have part of their interfaces in common.
  Extract the subset into an interface.

**Collapse Hierarchy**: A superclass and a subclass are not very different.
  Merge them together.

**Form Template Method**: You have two methods in subclasses that perform similar steps in the same order, yet the steps are different.
  Get the steps into method with the same signature, so that the original method behind the same. Then you can pull them up.

**Replace Inheritance with Delegation**: A subclass uses only part of a superclasses interface or does not want to inherit data.
  Create a field for the superclass, adjust the methods to delegate to the superclass, and remove the subclassing.

**Replace Delegation with Inheritance**: You're using Delegation and are often writing many simple delegations for the entire interface.
  Make the delegating class a subclass of the delegate.

## Big Refactorings

**Tease Apart Inheritance**: You have an inheritance hierarchy that is doing two jobs at once.
  Create two hierarchies and use delegation to invoke one from the other.

**Convert Procedural Design to Objects**: You have code written a procedural style.
  Turn the data records into objects, break up the behavior, and move the behavior to the objects.

**Separate Domain from Presentation**: You have GUI classes that contain domain logic.
  Separate the domain logic into separate domain classes.

**Extract Hierarchy**: You have a class that is doing too much work, at least on part through many conditional statements.
  Create a hierarchy of classes in which each subclass represents a special case.
  
# Code Smells

The code smells. Credit goes to Martin Fowler and all the people he gives credit to for this

## List of Smells

 * **Duplicated Code**: The same code structure is in more than one place.
 * **Long Method**: A Method is too long, has too many comments, has complex conditionals, mixes level of abstraction.
 * **Large Class**: A class is too large, has many instance variables, has duplicated code.
 * **Long Parameter List**: A method has many parameters.
 * **Divergent Change**: One class is commonly changed in different ways for different reasons.
 * **Shotgun Surgery**: One reason for change results in a lot of little changes to a lot of different classes.
 * **Feature Envy**: A method seems more interested in a class other than the one it is actually in. The most common is data envy. Strategy, Visitor and other patterns violate this.
 * **Data Clumps**: Data items are found together over and over again. This can be as fields in a couple of classes or parameters in many method signatures.
 * **Primitive Obsession**: Excessive use of primitive types.
 * **Switch Statements**: Switch statements on type codes and values that direct behaviour -  instead of polymorphism. Especially if there are multiple switches on the same thing in different places.
 * **Parallel Inheritance Hierarchies**: Every time you make a change to a subclass of one class, you also have to make it to a subclass of another.
 * **Lazy Class**: A class isn't doing enough to pay off. Maybe the class was downsized with refactoring or it was intended for planned changes that never happened.
 * **Speculative Generality**: A class has been over-engineered to be fit for all possible extensions -that will never be needed.
 * **Temporary Field**: An instance variable is only set and used in a rare case. Often done instead of passing it as a method parameter when calling internal methods.
 * **Message Chains**: A sequence of getThis() calls chained together or a series of temps. Also known as train wreck.
 * **Middle Man**: A class is doing more delegation to another than it does on its own. Wrappers especially to control boundaries or simplify a complex API are often justified.
 * **Inappropriate Intimacy**: Classes that are too involved in each others private details.
 * **Alternative Classes with Different Interfaces**: Methods with different signatures do the same thing but have different names.
 * **Incomplete Library Class**: A method is missing from a library class.
 * **Data Class**: A class with public fields that are manipulated all over the place. It needs to become a proper, encapsulated object.
 * **Refused Bequest**: An inherited method is not applicable for a subclass. Refusing implementations is often justifiable, interfaces almost never.
 * **Comments**: Comments that are an excuse for bad code.

## Remedies

 * **Duplicated Code**: Extract Method. Extract Method, Pull Up Method. Extract Method, Form Template Method / Substitute Algorithm. Extract Class.
 * **Long Method**: Extract Method, use Replace Temp with Query, use Introduce Parameter Object, use Preserve Whole Object. Decompose Conditional.
 * **Large Class**: Extract Subclass. Extract Class. use Duplicate Observed Data.
 * **Long Parameter List**: Replace Parameter with Method. Preserve Whole Object. Introduce Parameter Object.
 * **Divergent Change**: Extract Class.
 * **Shotgun Surgery**: Move Method. Move Field. 
 * **Feature Envy**: Move Method. Extract Method.
 * **Data Clumps**: Extract Class. Introduce Parameter Object. Preserve Whole Object.
 * **Primitive Obsession**: Replace Data Value with Object. Replace Type Code with Class. Replace Type Code with Subclass. Replace Type Code with State / Strategy. use Extract Class, use Introduce Parameter Object, use Replace Array with Object
 * **Switch Statements**: Extract Method, Move Method, Replace Type Code with State / Strategy, Replace Conditional with Polymorphism. use Replace Parameter with Explicit Methods, use Introduce Null Object
 * **Parallel Inheritance Hierarchies**: Move Method. Mov Field.
 * **Lazy Class**: Collapse History. Inline Class.
 * **Speculative Generality**: Collapse History. Inline Class. Remove Parameter. Rename Method.
 * **Temporary Field**:Extract Class. Introduce Null Object.
 * **Message Chains**: Hide Delegate. Extract Method. Move Method.
 * **Middle Man**: Remove Middleman. Inline Method. Replace Delegation with Inheritance.
 * **Inappropriate Intimacy**: Move Method. Move Field. Change Bidirectional Association to Unidirectional. Hide Delegate. Replace Inheritance with Delegation
 * **Alternative Classes with Different Interfaces**: Rename Method. Move Method. Extract Superclass. 
 * **Incomplete Library Class**: Introduce Foreign Method. Introduce Local Extension.
 * **Data Class**: Encapsulate Fields. Encapsulate Collection. Remove Setting Method. use Move Method, use Extract Method, use Hide Method.
 * **Refused Bequest**: Push Down Method. Push Down Field. use Replace Inheritance with Delegation.
 * **Comments**: Extract Method. Rename Method. Introduce Assertion.

