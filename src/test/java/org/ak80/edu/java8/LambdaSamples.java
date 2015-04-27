package org.ak80.edu.java8;

import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LambdaSamples {

  @Test
  public void simpleLambda() {

    Function<Integer, Integer> statementLambda = ((x) -> {
      return x + 1;
    });
    assertThat(statementLambda.apply(4), is(5));

    Function<Integer, Integer> expressionLambda = (x -> x + 1);
    assertThat(expressionLambda.apply(4), is(5));

    Function<Integer, Integer> methodReferenceLambda = this::add;
    assertThat(methodReferenceLambda.apply(4), is(5));

    Function<Integer, Integer> returnedLambda = getFunction(2);
    assertThat(returnedLambda.apply(4), is(6));

    Multiplier passedLambda = ((x, y) -> x * y);
    assertThat(executeMultiplyByTwo(passedLambda, 4), is(8));

  }

  private Integer add(Integer x) {
    return x + 1;
  }

  private Function<Integer, Integer> getFunction(int increment) {
    return x -> x + increment;
  }

  private int executeMultiplyByTwo(Multiplier multiplier, int x) {
    return multiplier.multiply(x, multiplier.getMultiplier());
  }

  @FunctionalInterface
  interface Multiplier {

    int multiply(int x, int y);

    default int getMultiplier() {
      return 2;
    }

  }

}
