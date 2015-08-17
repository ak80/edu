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

Partially constructed object: 
If during construction a reference to the new object escapes, it may appear to be in an inconsistent state to another thread. 

A not so obvious example is to assign a new object to a public variable: 
public myPublic = new SomeClass();

This is an example of an object that is not properly published.

When constructing an object first all default values are written by the constructor of Object, before any subclass constructors run.

### Immutable Objects and Initalization Safety

The Java Memory Model offers a special guarantee: immutaable objects can safely be used by any other thread, even when synchronization was not used to publish them!

### Safe Publishing Idioms

Mutable objects need to be safely published, which usually means to use synchronization in the publishing and consuming thread.

Safe publishing means: both the reference to the object as well as the state of it must be made visible at the same time.

A properly constructed object can be safely published by:
* Initializing an object reference from a static initializer
* storing a reference to it into a volatile field or AtomicReference
* storing a reference to it into a final field of a properly constructed object
* storing a reference to it into a field that is properly guarded by a lock

### Thread-safe Collections

The thread-safe collections guarantee safe publishing, for:
* a key or value in a HashTable, SynchronizedMap, ConcurrentMap
* an element in a Vector, CopyOnWriteArrayList, CopyOnWriteArraySet, SynchronizedList, SynchronizedSet
* an element in a BlockingQueue or ConcurrentLinkedQueue

Future and Exchanger also provide safe publication.

### Effectively Immutable Objects

Objects that are not technically immutable, but whose state will not be modified after publication are called effectively immutable.

After construction they are treated, by convention, as if they were immutable.

Safely published effectively immutable objects can be used safely by any thread without additional synchronization.

### Mutable Objects

Synchronization must be used to publish a mutable object, as well as every time the object is accessed.

### Publication Requirements

They depend on the mutability:
* Immutable objects: can be published through any mechanism
* Effectively immutable objects: must be safely published
* Mutable objects: must be safely published and must either be thread-safe or guarded by a lock.

### Sharing Objects Safely

In order to safely use a shared an object, the developer must understand how to use it: do you need to acquire a lock? Are you allowed to modify state?

The most useful policies for sharing are:
* Thread-confined: the object is exclusively owned by and confined to one thread; the thread can modify it
* Shared read-only: immutable and effectively immutable objects can be freely shared between and used by multiple threads without additional synchronization. They must not be modified!
* Shared thread-safe: the object performs synchronization internally, so it can be shared and used by multiple threads without additional synchronization.
* Guarded: a guarded object must only be accessed with a specific lock held

# Chapter 4. Composing Objects

## Designing a Thread-safe Class

Encapsulation makes it possible to determine that a class is thread-safe without having to examine the entire program.

The design process includes:
* Identify the variables that form the object's state
* Identify the invariants that constrain the state variables
* Establish a policy for managing concurrent access to the object's state

The synchronization policy defines how an object coordinates access to its state without violating its invariants out postconditions. It specifies how it uses things like immutability, thread confinement and locking. It must be documented!

### Gathering Synchronization Requirements

The state space of a variable is the range of possible states it can take on.

Constrains need to be properly protected. For example:
* state constrains define certain states as invalid (invalid values). This creates a encapsulation requirement
* state transition constrains define states that are not valid as a next state, based on the current state. This always implies a compound action
* there may be constrains, where state of two or more variables depend on each other, which again implies a compound action

These constraints are derived from invariants and post-conditions

### State-dependent Operations

Derived from state-based preconditions,e.g. for executing a remove operation on a queue, the queue must be non-empty.

In a single-threaded program, an operation has no choice but to fail if the precondition is not true. In a multi-threaded it could wait until the precondition becomes true.

The low level operations for this are wait() and notify(). It is often easier to use synchronizers like BlockingQueue or Semaphore.

### State Ownership

Ownership is not expressed in a programming language. Often it is simple: an object encapsulates the state it owns, and owns the state it encapsulates. But once you publish a reference to a mutable object, you no longer have exclusive control; at best its now shares ownership.

## Instance Confinement

If an object is completely encapsulated in another, and does not escape, it is "instance confined". It's easy to manage and reason about.

## Java Monitor Pattern

An object encapsulates all is mutable state and guards it with the objects own intrinsic lock.

If the intrinsic lock is used, another client could acquire it, which could lead to deadlocks. A private lock prevents this, but then a client could not participate in locking.

A common idiom to publish state is to copy mutable data and publishing the copy.

## Delegating Thread Safety

A composite made entirely of thread safe classes may be thread safe itself. But that is not guaranteed.

If all state variables are thread safe or immutable and independent, and if there are no invalid state transitions, then the thread safety can be delegated.

But if invariants relate between state variables or if there are constrains on the state transitions then compound actions are involved, and the class must use its own locking to make them atomic.

### Publishing underlying state variables

If a state variables is thread safe or immutable and independent of other state variables, and if there are no invalid state transitions, then it can be safely published.

## Adding Functionality to Existing Thread-safe Classes

The safest way to add a new atomic operation to a thread safe class is to modify the original class.

Alternatives are, to extend the class if it was designed for extensions. But then the synchronisation policy is distributed over multiple files, which is more fragile.

The third alternative, is to place the extension code in a helper class. Then use the same lock as the original class! This is called external locking out client side locking. This is likewise fragile.

A better alternative is composition

### Composition

Create a wrapper that implements the interface of the class you want to extend and holds an instance, to which it delegates all operations. Then add you own operation. Use the monitor pattern to synchronize all access.

## Documenting synchronization policies

Document a classes:
* thread safety guarantees for its clients
* synchronization policy for its maintainers

# Chapter 5. Building blocks

## Synchronized Collections

Vector, Hashtable (both considered depracated) and synchronized wrapper classes created by Collections.synchronizedXxx.

They are thread safe but sonetimes compound actions may need additional client-side locking. Common compound actions are e.g. iteration, navigation, put-if-absent. While they are technically thread-safe without client-side locking, they mat not behave as expected, when concurrently accessed.

The synchronized collections use a lock on the object itself; this can be used for client-side locking.

Iterators of synchronized collections are "fail-fast iterators", they maintain a collection count and if it changes throw a ConcurrentModificationException. This is not fool proof, but often good enough. You can prevent this with locking, but that usually is not performant and may even lead to deadlocks. The alternative would be to clone the collection, and iterate over the clone.

### Hidden Iteration

Under the good iteration of used on Collections by toString(), equals(), hashCode()

## Concurrent Collections

Introduced with Java 5.

A new ConcurrentMap interface with compound actions for replace and conditional put or remove and ConcurrentHashMap. 

Also CopyOnWriteArrayList.

Further there are two new types, Queue and BlockingQueue.

Implementations are e.g. ConcurrentLinkedQueue (traditional FIFO) or the non-concurrent PriorityQueue.

Added with Java 6 are ConcurrentSkipListMap and ConcurrentSkipListSet, the concurrent replacements for synchronized SortedMap and SortedSet

### ConcurrentHashMap

Uses a fine grained locking mechanism called " lock striping". This provides much better performance than with SynchronizedMap. But client-side locking is not supported.

Like the other concurrent collections, there is no need to lock during iteration, and iterators won't throw ConcurrentModificationException. They are weakly consistent: tolerates concurrent modification, traverses elements as they existed when the iterator was constructex and may reflect modifications to the collection.

Some operations have been weakened: size() or isEmpty() may not always be up to date, due to the nature of concurrent access this is usually not a problem.

### CopyOnWriteArrayList

A concurrent replacement for a synchronized List. Offers better concurrency for common situations, eliminates need to lock during iteration .

Every time the collection is modified, a new copy is published.

A good use case is for registering listeners or event handlers. We expect small numbers, rare modification but manu iterations.

## Blocking Queues and the Producer-Consumer Pattern

They offer put() and take(), which may block, or offer() and pull() with timeout.

LinkedBlockingQueue and ArrayBlockingQueue are FIFO queues,similar to LinkedList and ArrayList but with better concurrent performance than a synchronized List.

PriorityBlockingQueue is priority order (uses a Comparator or natural ordering if elements implement comparable).

SynchronousQueue not a queue, because it has no storage space but hands off elements directly.

### Serial Thread Confinement

The blocking queues in java.util.concurrent implement safe publishing from producer thread to a consumer thread. This can be seen as handing off ownership, allowing serial thread confinement.

### Deques and Work Stealing

Since Java 6: Deque and BlockingDeque, intended for "work stealing": if a consumer exhausts its queue it can steal work from the tail of a queue of  another consumer.

A deque is a double ended queue with efficient insertion and removal at both ends.

This works well when consumers are also producers.

## Blocking and Interruptable Methods

Threads may block for multiple reasons, e.g. waiting for I/O, waiting for a lock or to wake up from sleep()

The states are BLOCKED, WAITING, TIMED_ WAITING.

When a method can throe InterruptedException it means that the method is blocking and that it can be interrupted. For example put() and take() of BlockingQueue, or Thread.sleep().

Each Thread had a boolean property that represents its interrupted status; the interrupt() method sets it and the status can be queried. 

Use Thread.currentThread() for getting the current thread.

Interruption is a cooperative, and not a forced, mechanism.

When you write library code and call a blocking method that can throw InterruptedException, you must decide how to handle:
* propagate either by not catching or by rethrowing
* call interrupt() again to restore the status

Don't just swallow the interrupt! 

## Synchronizers

A synchronizer is any object that coordinates the control flow of threads based on its state. BlockingQueues can be synchronizers; others are semaphores, barriers and latches.

They decide based on their own internal state, whether a thread arriving at the synchronizer should wait or be allowed to pass.

### Latches

Delays the progress of threads until it reaches a terminal state. Until that state is reached, all threada have to wait, once that state is reached, the gate states open.

### FutureTask

Implements a Future, a computation with a result and implements a Callable (a Runnable with a result).

It is in one of three states:
1. waiting
2. running
3. completed

Completed is normal completion, failurea or cancellation.

When get() is called and the FutureTask is not conpleted, the get() blocks. Otherwise it returns the result or throws an exception.

The Throwable thrown by get() can be:
* a checked exception from Callable
* a RuntimeException
* anm8mim
It is advisable to check which it is with instance of and cast it for rethrow.

Be careful when caching a Future: if it is canceled or throws an error, your cache gets polluted.

### Semaphores

Are used to limit access, by dispensing a preconfigured number of permits. If no permit is left, the operation for acquring a permit, acquire() blocks until a permit has been returned with release(). It is possible to use a timeout with acquire().

A semaphore with an initial count of one can be used as a mutex.

Note that:
* release() "creates" a permit and so it is possible to increase the initial count.
* there is no association to threads and the one that acquires a permit doesn't have to be the same that returns it.

### Barriers

Barriers block a group of thread until they are all at the barrier. Latches are waiting for events, bartiers are waiting for other threads.

CyclicBarrier is used to rendevouz a fixed number of threads repeatedly at a barrier point. Threads call await() at the barrier point and block until all threads arrived. When a call to await() times out or the blocked Thread is interrupted, then the barrier is broken and outstanding calls to await() will terminate with BrokenBarrierException.

Exchanger is a two-party barrier, where two threads exchange data at the barrier point.

# Summary Part I

* Reduce mutables: all concurrency issues boil down to coordinating access to mutable state. Use final and immutable objects
* Use encapsulation, to manage complexity
* Guard each mutable with a lock, guard all variables in an invariant with the same lock.
* Hold locks during compound actions
* Include thread-safety in the design process and document your synchronization policy.
