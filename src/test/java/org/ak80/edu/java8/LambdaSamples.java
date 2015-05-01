package org.ak80.edu.java8;

import org.junit.Test;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LambdaSamples {

  @Test
  public void differentSyntaxForLambda() {
    Supplier<Integer> noArguments = () -> 1;

    Function<Integer, Integer> oneArgument = integer -> integer + 1;

    Function<Integer, Integer> multipleStatements = integer -> {
      integer = integer * 1;
      return integer + 1;
    };

    BinaryOperator<Integer> multipleArguments = (int1, int2) -> int1 + int2;

    BinaryOperator<Long> explicitTypes = (Long long1, Long long2) -> long1 + long2;
  }


    @Test
  public void differentUsesForLambda() {

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
