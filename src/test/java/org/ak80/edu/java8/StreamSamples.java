package org.ak80.edu.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamSamples {

  @Test
  public void createStreamAndTerminate() {
    String [] stringArray = new String[]{"Frodo", "Sam", "Gandalf"};

    Stream<String> arrayStream = Arrays.stream(stringArray);
    assertThat(arrayStream.toArray(),is(stringArray));

    List<String> stringCollection = Arrays.asList(stringArray);

    Stream<String> collectionStream = stringCollection.stream();
    assertThat(collectionStream.collect(Collectors.toList()), is(stringCollection));
  }







}
