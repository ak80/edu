package org.ak80.edu.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class BulkOperationsSamples {

  @Test
  public void forEach() {
    final List<String> names = Arrays.asList("Frodo", "Sam", "Gandalf");

    StringBuffer joinedExternal = new StringBuffer();
    for(String name : names) {
      joinedExternal.append(name+" ");
    }
    assertThat(joinedExternal.toString().trim(), is("Frodo Sam Gandalf"));

    final StringBuffer joinedInternal = new StringBuffer();
    names.forEach(x -> joinedInternal.append(x+" "));
    assertThat(joinedExternal.toString().trim(), is("Frodo Sam Gandalf"));
  }

  @Test
  public void removeIf() {
    final List<String> names = new ArrayList<>();
    names.addAll(Arrays.asList("Frodo", "Sam", "Gandalf", ""));

    Predicate<String> isEmpty =  String::isEmpty;
    names.removeIf(isEmpty);

    assertThat(names.size(), is(3));
    assertThat(names, hasItems("Frodo", "Sam", "Gandalf"));
  }

  @Test
  public void replaceAll() {
    final List<String> names = new ArrayList<>();
    names.addAll(Arrays.asList("Frodo", "Sam", null, "Gandalf"));

    UnaryOperator<String> defaultSetter =  str -> str == null ? "" : str;
    names.replaceAll(defaultSetter);

    assertThat(names, hasItems("Frodo", "Sam", "Gandalf", ""));
  }

}
