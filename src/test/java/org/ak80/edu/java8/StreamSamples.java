package org.ak80.edu.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class StreamSamples {

  @Test
  public void createStreamAndTerminate() {
    String[] stringArray = new String[]{"Frodo", "Sam", "Gandalf"};

    Stream<String> arrayStream = Arrays.stream(stringArray);
    assertThat(arrayStream.toArray(), is(stringArray));

    List<String> stringCollection = Arrays.asList(stringArray);

    Stream<String> collectionStream = stringCollection.stream();
    assertThat(collectionStream.collect(Collectors.toList()), is(stringCollection));

    collectionStream = stringCollection.stream();
    assertThat(collectionStream.collect(Collectors.toCollection(ArrayList::new)),is(stringCollection));
  }

  @Test
  public void createParallelStream() {
    String[] stringArray = new String[]{"Frodo", "Sam", "Gandalf"};
    Stream<String> arrayStream = Arrays.stream(stringArray).parallel();

    List<String> stringCollection = Arrays.asList(stringArray);
    Stream<String> collectionStream = stringCollection.parallelStream();
  }

  @Test
  public void streamFromPredefinedRange() {
    Stream<String> names = Stream.of("Frodo", "Sam", "Gandalf");
    assertThat(names.findFirst().get(), is("Frodo"));

    Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);
    assertThat(numbers.min(Integer::compare).get(), is(1));

    IntStream ints = IntStream.range(0, 5);
    assertThat(ints.boxed().max(Integer::compare).get(), is(4));

    LongStream longs = LongStream.range(0, 5);
    assertThat(longs.sum(), is(10L));

    DoubleStream doubles = DoubleStream.of(0.0, 0.1, 0.2);
    assertThat(doubles.count(), is(3L));
  }

  @Test
  public void boxingAndWidening() {
    IntStream ints = IntStream.of(0, 1, 2, 3, 4, 5);
    Stream<Integer> integers = ints.boxed();

    LongStream primitiveLongs = integers.mapToInt((i) -> i.intValue()).asLongStream();
    Stream<Long> longs = primitiveLongs.boxed();

    DoubleStream primitiveDoubles = longs.mapToDouble((l) -> (1.0 * l));
    Stream<Double> doubles = primitiveDoubles.boxed();

    assertThat(doubles.count(), is(6L));
  }

  @Test
  public void infiniteStreams() {
    IntStream ints = IntStream.iterate(0, i -> i + 1);
    assertThat(ints.limit(10).toArray()[9], is(9));

    AtomicInteger atomicInteger = new AtomicInteger(0);
    Stream<Integer> atomicIntegers = Stream.generate(atomicInteger::getAndIncrement);
    assertThat(atomicIntegers.limit(10).toArray()[9], is(9));
  }

  @Test
  public void filterMapReduce() {
    Predicate<String> isHobbit = x -> Arrays.asList("Frodo", "Sam").contains(x);

    Stream<String> names = Stream.of("Frodo", "Sam", "Gandalf");

    Optional<Integer> result = names.filter(isHobbit).map(str -> str.length()).reduce((x, y) -> x + y);
    assertThat(result.get(), is(8));
  }

  @Test
  public void peekIntoFilterMapReduce() {
    Predicate<String> isHobbit = x -> Arrays.asList("Frodo", "Sam").contains(x);

    Stream<String> names = Stream.of("Frodo", "Sam", "Gandalf");

    final StringBuffer buffer = new StringBuffer();

    Stream<String> filteredNames = names.filter(isHobbit);
    Stream<String> peekedStream = filteredNames.peek(str -> buffer.append(str));
    peekedStream.map(str -> str.length()).peek(len -> buffer.append(len)).reduce((x, y) -> x + y);

    assertThat(buffer.toString(), is("Frodo5Sam3"));
  }

  @Test
  public void flatMap() {
    Stream<String> names = Stream.of("Frodo Baggins", "Sam Gamgee", "Gandalf").flatMap(x -> Stream.of(x.split(" ")));

    Optional first = names.sorted(String::compareTo).findFirst();
    assertThat(first.get(), is("Baggins"));
  }

  @Test
  public void forEach() {
    Stream<String> names = Stream.of("Frodo", "Sam", "Gandalf");

    final StringBuffer buffer = new StringBuffer();
    names.forEach(x -> buffer.append(x));

    assertThat(buffer.toString(), is("FrodoSamGandalf"));
  }

  @Test
  public void aggregateFunctions() {
    assertThat(Stream.of(1, 2, 3, 4, 5).count(), is(5L));

    assertThat(Stream.of(1, 2, 3, 4, 5).min(Integer::compare).get(), is(1));
    assertThat(LongStream.of(1, 2, 3, 4, 5).min().getAsLong(), is(1L));

    assertThat(Stream.of(1, 2, 3, 4, 5).max(Integer::compare).get(), is(5));
    assertThat(LongStream.of(1, 2, 3, 4, 5).max().getAsLong(), is(5L));

    assertThat(LongStream.of(1, 2, 3, 4, 5).sum(), is(15L));
  }

  @Test
  public void matches() {
    Predicate<String> isHobbit = x -> Arrays.asList("Frodo", "Sam").contains(x);
    Predicate<String> isDwarf = x -> Arrays.asList("Gimli").contains(x);

    assertThat(Stream.of("Frodo", "Sam", "Gandalf").allMatch(isHobbit), is(false));
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").anyMatch(isHobbit), is(true));
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").noneMatch(isDwarf), is(true));
  }

  @Test
  public void find() {
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").findAny().get(), is(notNullValue()));
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").findFirst().get(), is("Frodo"));
  }

  @Test
  public void sorted() {
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").sorted().toArray()[2], is("Sam"));
  }

  @Test
  public void distinct() {
    assertThat(Stream.of("Frodo", "Frodo", "Sam", "Gandalf", "Sam").distinct().count(), is(3L));
  }

  @Test
  public void skipAndLimit() {
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").limit(2).collect(Collectors.toList()), hasItems("Frodo","Sam"));
    assertThat(Stream.of("Frodo", "Sam", "Gandalf").skip(1).collect(Collectors.toList()), hasItems("Sam","Gandalf"));
  }



  }
