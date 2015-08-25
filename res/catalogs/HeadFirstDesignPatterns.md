# Head First Design Patterns
Credit goes to Elisabeth Freeman and Eric Freeman

- [Head First Design Patterns](#head-first-design-patterns)
- [Basics](#basics)
- [Design Principles](#design-principles)
    - [Guidelines for the Dependency Inversion Principle](#guidelines-for-the-dependency-inversion-principle)
    - [Guidelines for Principle of Least Knowledge](#guidelines-for-principle-of-least-knowledge)
- [Patterns](#patterns)
  - [Strategy](#strategy)
  - [Observer](#observer)
    - [Push Observer](#push-observer)
    - [Pull Observer](#pull-observer)
  - [Decorator](#decorator)
  - [Factory Method](#factory-method)
    - [Variation](#variation)
    - [Simple Factory](#simple-factory)
  - [Abstract Factory](#abstract-factory)
  - [Singleton](#singleton)
  - [Command](#command)
  - [Undo](#undo)
    - [MacroCommand](#macrocommand)
    - [Null Object](#null-object)
    - [Lambda FTW](#lambda-ftw)
  - [Adapter](#adapter)
    - [Variations](#variations)
  - [Façade](#faade)
  - [Template Method](#template-method)
  - [Iterator](#iterator)
    - [Variation](#variation-1)
  - [Composite](#composite)
  - [State](#state)
  - [Proxy](#proxy)
    - [Remote Proxy](#remote-proxy)
- [Compound patterns](#compound-patterns)
- [Real World](#real-world)
- [Leftover](#leftover)
  - [Bridge](#bridge)
  - [Builder](#builder)
  - [Chain Of Responsibility](#chain-of-responsibility)

# Basics
* Abstraction
* Encapsulation
* Polymorphism
* Inheritance

# Design Principles

* Identify the aspects of your application that vary and separate them from what stays the same.
* Program to an interface (supertype), not an implementation (subtype).
* Favor composition over inheritance.
* Strive for loosely coupled designs between objects that interact.
* Classes should be open for extension, but closed for modification.
* Depend upon abstractions. Do not depend upon concrete classes.
* Principle of Least Knowledge: talk only to your immediate friends.
* The Hollywood Principle Don’t call us, we’ll call you.
* A class should have only one reason to change.

### Guidelines for the Dependency Inversion Principle

The following guidelines should be applied with common sense, and not blindly. The more something is likely to change, the more important the D.I.P. is. For example there is nothing wrong with instantiating concrete String objects.

No variable should hold a reference to a concrete class. If you use new, you’ll be holding a reference to a concrete class.

No class should derive from a concrete class. Derive from an abstraction, like an interface or an abstract class. 

No method should override an implemented method of any of its base classes.

### Guidelines for Principle of Least Knowledge

A.k.a Law of Demeter

May result in lots of wrapping and delegation. 

Avoid "train wrecks"!

From a Method m in Object O, only call:
* The object O itself 
* Objects passed in as a parameter to m
* Any object the method m creates or instantiates 
* Any components of O (in instance fields)

Apply "tell, don't ask". Don't get object P from object O in order to do s.t. with P. Instead ask O to do something, which internally will do with P what you wanted.

# Patterns

## Strategy
The Strategy Pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

interface Behavior {
  void do();
}

class Client {
  private Behavior behavior;

  public Client(Behavior behavior) {
    this.behavior = behavior;
  }

  void perform() {
    behavior.do();
   }

}

The Client works with the Behavior interface. A concrete subclass can be injected in the constructor or with a setter, maybe even changed at runtime. It can also come from a factory.


## Observer
The Observer Pattern defines a one-to-many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically.

### Push Observer

interface Subject
  registerObserver(Observer o)
  removeObserver(Observer o)
  notifyObserver()

interface Observer
  // push data
  update(Object data1, Object data2 ...)

### Pull Observer

interface Subject
  registerObserver(Observer o)
  removeObserver(Observer o)
  notifyObserver()
  // pull data
  Object getData1()
  Object getData2()

interface Observer
  update()

## Decorator

The Decorator Pattern attaches additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

// if possible implement interface
class Decorator extends Decoratee {
  private final Decoratee decoratee;

  public Decorator(Decoratee decoratee) {
    this.decoratee = decoratee;
  }

  @Overides
  public void delegated() {
    decoratee.delegated();
  }

  @Overrides
  public int decorated() {
    return decoratee.decorated * 2;
  }

}

## Factory Method

The Factory Method Pattern defines an interface for creating an object, but lets subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

interface Product { }
class ConcreteProduct implements Product { }

abstract class Client {
  abstract Product create();
  void doStuff() {
    Product product = create();
    // ...
  }
}

class ConcreteCreator extends Creator {
  Product create() {
    return new ConcreteProduct();
  }
}

### Variation

The abstract factory method may not be abstract but can be a default.

The abstract factory method can be parameterized and take an argument, even a type indicator (e.g. an enum) as a parameter.

### Simple Factory

Don't confuse with Simple Factory, a concrete object with a concrete create method. Not a real Design Pattern, but almost as good.

## Abstract Factory

The Abstract Factory Pattern provides an interface for creating families of related or dependent objects without specifying their concrete classes.

interface ProductA { }

interface ProductB { }

interface AbstractFactory {
  ProductA getA();
  ProductB getB();
}

class Client{}
  private AbstractFactory factory;

  Client(AbstractFactory factory) {
    this.factory = factory!
  }

  void do() {
    prepare(factory.getA());
    add(factory.getB());
  }
}

Note: Concrete Factories often use Factory Method to implement creation their products.

## Singleton

The Singleton Pattern ensures a class has only one instance, and provides a global point of access to it.

class Singleton() {
  private static Singleton instance;
  private Singleton() { };
  
  public static Singleton getInstance() {
    if (instance == null)  }
       instance = new Singleton();
    }
    return instance;
  }
}

Warning: code with lazy instantiation is not thread safe. Solutions:
* use synchronized getInstance() 
* use eager instantiation and assign new instance on field declaration.
* use double checkec locking, with volatile instance field and synchronized(Singleton.class)

## Command

The Command Pattern encapsulates a request as an object, thereby letting you parameterize other objects with different requests, queue or log requests, and support undoable operations.

public interface Command {
  public void execute();
}

public class ConcreteCommand implements Command {
  private Receiver receiver;

  public void execute() {
    Data data = getData();
    receiver.doSomething(data);
  }
}

public class Invoker() {
  private Command command;

  public void doSomething() {
    command.execute();
  }
}

Variations: Command can implement the actions itself, or work with a receiver. The call to de receiver can be dumb, or parameterized.

Remember: Command can be used to do undo and work well with Queues and suppports Log and Replay.


## Undo

For undo, each Command must also implement a undo() method. Then keep track of the last command, or have a stack of the last cimmands

### MacroCommand

A MacroCommand has a list of Command objects and implements Command itself. 

When execute() is called on the MacroCommand, it executes each of the Commands from its list.

### Null Object

Implements an interface, like Command, but is effectivelly a placeholder for null. I.e. as a NullCommand does nothing, but avoids passing null around.

Not a real pattern as such.

### Lambda FTW

Get rid of all the concrete Command classes with Lambda expressions, as references or as method expressions 

setCommand( () -> { receiver.action(); );
or
setCommand( receiver::action());

Of course, this does not work with undo(),because the Interface must only have one method.

## Adapter

The Adapter Pattern converts the interface of a class into another interface the clients expect. Adapter lets classes work together that couldn’t otherwise because of incompatible interfaces.

public interface Target {
  void request();
}

public class Adapter implements Target {
  private Adaptee adaptee;

  void request() {
    adaptee.specificRequest();
  }
}

### Variations

Adapter (and Facade) take an object and make it look like s.t. else - whereas Decorator makes it look the same but adds Functionality

Create a two-way-adaper to act as adapted and keep original.

Adapter usually wraps one object; see also Facade

## Façade

public class Facade {
  private SubSystem1 sub1;
  private SuvSystem2 sub2;

  public void doComplexStuff() {
    sub1.init();
    sub2.prepare();
    sub1.doSomething();
    sub2.work();
  }

The Facade Pattern provides a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

## Template Method

The Template Method Pattern defines the skeleton of an algorithm in a method, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm’s structure.


public abstract class Template() {
  
  public void extraStep() {
    // do stuff
  }

  public abstract void templatedStep1();

  public abstract void templatedStep2();

  public void optionalHook() {  }

  public boolean doExtraHook() {
    return true;
  }

  public void work()  {
    
    templatedStep1();
    optionalHook();
    templateStep2()
    if (doExtraHook()) {
       extraStep();
    }
  }

}

## Iterator

The Iterator Pattern provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

public interface Iterator<T> {

  public boolean hasNext();
 
  public T getNext();

}

### Variation

The Composite Iterator, iterates over all elements in a tree of composites

Null Iterator, another variation of Null Object.

## Composite

The Composite Pattern allows you to compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions

public interface Composite {
  void add(Composite composite);
  void remove(Composite composite);
  List<Composite> getChildren();
  void operation();
}

public class Leaf implements Composite { }
public class Component implements Composite { }

The idea is now that operation(), when called on a Component, will iterate over its children and call operate on them.

Leaf will also inherit the add(), remove() and getChildren() methods.

Leaf can of course have additional methods that make sense, as can Component. Composite can also be an abstract class and provide default implementation for methods. Sometimes the best default is to throw an UnsupportedOperationException.

Note that Composite violates the SRP: it manages a hierarchy and provides behavior for each item. This is done to ease handling and make it transparent. Typical trade off situation.

Sometimes it makes sense for a child to store a reference to the parent.

Sometimes caching can make sense.

## State

The State Pattern allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

public interface State {
  void startEngine();
  void drive();
  void halt();
  void engineOff();
}

public class Car {
  State engineOff = new StateOff(this);
  State readyToDrive = new StateReady(this);
  State driving = new StateDriving(this);

  State state = engineOff();

  void setState(State state) {
    this.state = state; 
  }
 
  void start() {
    state.startEngine()
  }
  
  void drive() {
    state.drive() 
  }

  void halt() {
    state.halt();
  }

 void engineOff() {
  state.engineOff():
  }

  void stop()  {
    state.halt();
    state.engineOff();
  }

}

public class StateDrive implements State {
  private Car car;

  public StateDrive(Car car) {
    this.car = car;
  }
  void startEngine() {
    System.out.println("Already started");
  }
  void drive() {
    System.out.println("driving along");
  }
  void halt() {
    System.out.println("Halting");
    car.setState(car.geRreadyToDriveState());
    
  void engineOff() {
    throw new IllegalStateException("Can't stop engine while driving");
  }
}


}
How to apply:

* Define a State interface that contains a method for every action
* Implement a State class for every state, responsible for the behavior in the corresponding state
* The client (Context) has a field for each state and provides getters. 
* When the State classes handles transitions, the Context has a field for the current state and provides a setter. The disadvantage is, this creates dependencis between the State classes
* If the transitions are static and simple the Context can handle them.
* You could make State an abstract class and provide defaults for common states.

## Proxy

The Proxy Pattern provides a surrogate or placeholder for another object to control access to it.

Types:

* a remote proxy controls access to a remote object.
* a virtual proxy controls access to a resource that is expensive to create.
* a protection proxy controls access to a resource based on access rights.

And more things are possibl,e.g. caching, reference counting, lazy loading, copy-on-write, synchronized access and so on. 

Works well with Javas Dynamic Proxy from the reflection package.


### Remote Proxy
A remote proxy acts as a local representative to a remote object. It’s an object that you can call local methods on and have them forwarded on to the remote object.

# Compound patterns

Patterns are often used together and combined within the same design solution. A compound pattern combines two or more patterns into a solution that solves a recurring or general problem.

The Model View Controller Pattern (MVC) is a compound pattern consisting of the Observer (to publish changes in view), Strategy (controller)  and Composite patterns.

# Real World

A Pattern is a solution to a problem in a context.

Pattern Categories: Creational, Behavioral, and Structural.

# Leftover Patterns

## Bridge
Use the Bridge Pattern to vary not only your implementations, but also your abstractions.

## Builder

Builder Use the Builder Pattern to encapsulate the construction of a product and allow it to be constructed in steps.

## Chain Of Responsibility

Use the Chain of Responsibility Pattern when you want to give more than one object a chance to handle a request.
