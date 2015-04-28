package org.ak80.edu.java8;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class OptionalSamples {

  @Test
  public void optional() {
    Optional<String> stringOptional = Optional.of("foo");
    Optional<String> emptyOptional = Optional.empty();

    assertThat(stringOptional.isPresent(),is(true));
    assertThat(emptyOptional.isPresent(),is(false));

    assertThat(stringOptional.get(),is("foo"));

    assertThat(stringOptional.orElse("bar"),is("foo"));
    assertThat(emptyOptional.orElse("bar"),is("bar"));

    assertThat(stringOptional.orElseGet(() -> "bar"),is("foo"));
    assertThat(emptyOptional.orElseGet(() -> "bar"), is("bar"));

    assertThat(stringOptional.orElseThrow(RuntimeException::new), is("foo"));

    OptionalInt optionalInt = OptionalInt.of(5);
    OptionalLong optionalLong = OptionalLong.of(5L);
    OptionalDouble optionalDouble = OptionalDouble.of(5.0);

  }

  @Test(expected = NoSuchElementException.class)
  public void optionalGetWithException() {
    Optional<String> emptyOptional = Optional.empty();
    assertThat(emptyOptional.get(),(nullValue()));
  }

  @Test(expected = RuntimeException.class)
  public void optionalOrElseTrow() {
    Optional<String> emptyOptional = Optional.empty();
    assertThat(emptyOptional.orElseThrow(RuntimeException::new),(nullValue()));
  }

}
