# Java Concurrency in Practice
Credit goes to Brian Goetz

# Chapter 1. A Introduction

## Risks of Threads

* Safety Hazards
* Livenes Hazards
* Performance Hazards

### Safety Hazards
Primarily caused by the fact that, without sufficient synchronization, the ordering of operations in 
multiple threads is unpredictable.

For example, the use of the increment operator (e.g. i++) is not atomic and can cause a lost update when used in 
multiple threads (when a race condition happens).

Without proper synchronization, not only is the order unpredictable, but also the time when a change to a variable by 
one thread becomes "visible" to another is not defined:  Caching may make a change temporarily - or even permanently - 
invisible to another thread.

The remedy is to use synchronized in all reading and writing threads - or in simple cases to use volatile.

### Liveness Hazards

A liveness failure means that a thread or an activity is permanently unable to make progress.

In a sequential program that could be as simple as an accidental infinite loop. In multi threaded code a common form
are dead locks or live locks.

### Performance Hazards

This could be poor service time, responsiveness, throughput, resource consumption, or scalability.

Remember that threads have some runtime overhead, context switches are not for free and are more frequent with many 
threads.

## Threads are Everywhere

Frameworks often use multiple threads and may bring concurrency into an application,bu calling application components.

Thread safety is contagious: If a component may be called from multiple threads, then all code paths that access the 
same state as this component must also be thread safe.

Examples:

* Java Timer
* Servlets and JSP
* RMI
* Swing, AWT and many GUI frameworks

# Chapter 2. Thread Safety

The use of threads and locks are just the mechanics, thread-safe code is about managing access to (shared, mutable) 
state.

An objects state is its data stored in variables (instance or static fields) and may include fields from other 
dependant objects.

Thread safety means protecting data from uncontrolled concurrent access.

When a state variable can be accessed by more than one thread, and one of them might write to it, they all must 
coordinate the access with synchronization. Here, synchronization means the synchronized keyword, for exclusive 
locking, as well as the use of volatile.

There are three ways to allow thread safe access to a state variable:
1. Don't share it
2. Make it immutable
3. Use synchronization for every access

Remember: designing a class to be thread safe is easier than retrofitting it.

Good object-oriented design techniques are the basics in order to achieve thread-safety: encapsulation, 
immutability, clear specification of invariants.

## Definition of Thread Safety

Thread Safety is about correctness. Correctness means a class conforms to its specification, which is defined
by the invariants constraining the state and the postconditions for operations.

A class is thread-safe when:
* it continues to behave correctly when accessed from multiple threads
* it encapsulates its own synchronization so that the caller needs no additional one
* the safety does not depend on scheduling or lucky timing

Stateless objects are always thread-safe.

## Atomicity

Atomicity means an operation executes as a single, indivisible operation. Falsely assuming that an operation 
is atomic means your code is broken. It may work in many circumstances, but can lead to failure, it is a race condition.

### Race Condition

A race condition means the correctness of a computation depends on the relative timing or interleaving of multiple 
threads. It is the possibility of incorrect results kwhen timing is unlucky.

The most common is check-then-act, where a potentially stale observation is used to make a decision what to do next.

### Compound Actions

Some common, often non-atomic compound operations are:
* read-modify-write (e.g. increment a number)
* check-then-act (e.g. lazy initialization)
* a specific form like put-if-absent

Java has atomic variable classes in java.util.concurrent.atomic. These thread-safe objects should preferably be used 
where practical.

## Locking

Using a thread-safe state variable, when an object only has one state variable, or if all state variables are 
independent, may make it thread safe.

If state variables are dependent, they must be updates in a single atomic operation - for which you need locking.

### Intrinsic Locks

Javas built-in mechanism for enforcing atomicity is the synchronized block (which also is important for visibility).

A synchronized block has:

* a reference to an object that will serve as the lock
* a choice block, guarded by the lock

A synchronized method guards the whole method with an intrinsic lock on the object (this),or the Class object 
for static methods. This is also called monitor lock. They act as mutexes (mutual exclusion locks), are reentrant 
by the same thread, and block a thread that wants to acquire the lock.

That means all synchronized blocks guarded by the same lock execute atomically with respect to each other.

Putting synchronized before all methods may lead to serious problems with performance and liveness.

### Reentrancy

Reentrant locks are acquired per-thread and not per-invocation. Each lock has an acquisition count and a reference to 
the owning thread, to allow reentrancy.

### Guarding State with Locks

Holding a lock for the entire duration of a compound action, can make the action atomic. But: all accesses, read and 
write, must be protected by the same lock! If that is true, the variable is "guarded by" that lock.

Only mutable data that will be accessed from multiple threads need to be guarded.

If state variables depend on each other (i.e. for every invariant that involves more than one state variable), all 
the variables involved (in that invariant) must be guarded by the same lock.

## Liveness and Performance

Poor concurrency: the number of simultaneous invocations is limited not by the availability of processing resources, 
but by the structure of the application itself.

Simplicity amd performance are often competing forces; favor simplicity over premature optimization.

Avoid holding locks during lengthy computations or operations at risk of not completing quickly (especially I/O)

# Chapter 3. Sharing Objects

The second aspect of synchronization is memory visibility.

With synchronization we want to prevent a thread from modifying the state of an object while another thread is using it, 
and we want to ensure that the changes made by one thread become visible to other threads.

## Visibility

Unless synchronization is used, there is no guarantee that a reading thread will see a value written by another 
thread on a timely basis, or even at all.

There is no guarantee that operations on one thread will be performed on the order given by the program, as long as the 
reordering is not detectable from the same thread. But it may be visible for other threads!

### Stale data

The statements above mean, that unless synchronization is used every time a variable is accessed, it is possible to see 
a stale value for a variable. A thread can even see a stale value for one variable and an up-to-date vale for another 
although the former was written first.

Stale values can cause serious safety or liveness failures, unexpected exceptions, corrupted data structures, inaccurate
computations, and infinite loops.

### Nonatomic 64-bit Operations

A thread may see stale data for a variable written by another thread without synchronization, but at least it is data 
that has actually been written once. This is called "out-of-thin-air safety". It applies to all variables except 
non-volatile 64-bit numerics, i.e. double and long.

### Locking and Visibility

Locking is as much about mutual exclusion as it is about memory visibility.

### Volatile Variables

The volatile keyword tells the compiler that a variable is shared and that it must not be cached and that operations 
must not be reordered.

Volatile extends visibility, so that when thread A writes to one volatile variable x, and subsequently thread B 
reads x, then the values of all variables that where visible to A prior to working to x also become visible to B 
after reading x. It is strongly recommended not to rely on this property!

A typical use for volatile is with a flag, e.g. for shutdown.

The following criteria must be met, to safely use volatile:

* writes to the variable do not depend on the current value, or it is ensure that only a single thread ever updates 
the value.
* the variable does not participate in invariants with other state variables
* locking is not required for any other reason while the variable if accessed

## Publication and Escape

Publishing an object means making it available to code outside of its current scope.

An object can be published by:
* storing it where other choice can find it
* returning it from a non-private method
* passing it to a method in another class

Publishing internal state variables is often desired, but may compromise encapsulation and make it difficult to 
preserve invariants.

An object that is published when it should not have been is said to have escaped:
* Publishing an object before it is fully constructed can compromise thread safety
* Publishing one object may indirectly publish others (any that is reachable)

Passing an object to an alien method is publishing.

For a class C, an alien method is any method that is not fully specified by C:
* methods in other classes
* overridable methods (not private, not final)

### Safe Construction Practices

If the third reference escapes during construction (even in the last statement) the object is considered not 
properly constructed!

Common mistakes to let "this" escape from the constructor:
* starting a thread (creating is ok)
* registering with a listener or other callback
* calling an overridable instance method

Use a private constructor and a public factor method if you must do one of the above.

## Thread Confinement

Thread Confinement means that date is only accessed from one thread, that way no synchronization is required.

Examples are JDBC Connection and Swing.

Thread Confinement is a convention, and requires correct implementation, it is not a feature of the language.

### Ad-hoc Thread Confinement

Means the responsibility falls entirely on the implementation.

As special case is when using a volatile variable, and it is ensured that a write is only done from one thread to 
prevent race conditions.

Ad-hoc Thread Confinement is fragile and you should prefer one of the alternatives.

### Stack Confinement

In this case, an object can only be reached through local variables. Local variables exist on the 
executing threads stack and are not accessible by other threads.

Also called within-thread or thread-local usage (not to be confused with the ThreadLocal library class).

Using a non-thread-safe object in a within-thread-context is still thread-safe. But be careful that the 
confinement is always guaranteed; which is hard to do, because even with proper documentation it can not be enforced.

### ThreadLocal

A more formal approach where per-thread values can be associated with a value-holding object.

While it can be a first step towars thread safety to turn global variables into ThreadLocal, be careful not to overdo it: like global state, this prevents reuse, makes maintenance and testing harder and creates hidden state.

## Immutability

Immutable objects are always thread-safe.

It is not enough to declare all fields final! An object is immutable if:
* its state cannot be modified after construction
* all its fields are final
* it is properly constructed (i.e. the this reference did not escape during construction)

Immutable objects can still use mutable objects internally to manage their state.

Remember you can "update" state stored in immutable objects by replacing an immutable object by a new one.

### Final Fields

Final fields can't be modified - although the objects they refer to can be modified if they are mutable.

Final has a special semantic in the Java memory model, which makes it possible to guarantee initialization safety.

It is good practice to make all fields final unless they need to be mutable.

#### Immutable Holder Class

When a group of related data items must be acted on atomically, consider creating an immutable holder class for them. 
This is an immutable class that holds your data items.

## Safe Publication

Tbd
