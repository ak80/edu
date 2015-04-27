package org.ak80.edu.java8;


import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PredicateSamples {

  @Test
  public void testPredicates() {
    Predicate<String> isNull = str -> str == null;
    Predicate<String> isEmpty = String::isEmpty;

    assertTrue(isNull.test(null));
    assertTrue(isEmpty.test(""));

    Predicate<String> isVoid = isNull.or(isEmpty);

    assertTrue(isVoid.test(null));
    assertTrue(isVoid.test(""));


    Predicate<String> isDefined = isNull.negate().and(isEmpty.negate());

    assertTrue(isDefined.test("test"));
    assertTrue(!isDefined.test(null));
    assertTrue(!isDefined.test(""));

  }

  @Test
  public void unaryOperator() {
    UnaryOperator<String> defaultSetter = str -> str == null ? "" : str;

    assertThat(defaultSetter.apply(null), CoreMatchers.notNullValue());
  }


}
