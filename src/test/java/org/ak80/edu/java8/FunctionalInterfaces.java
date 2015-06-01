package org.ak80.edu.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Some important functional interfaces
 */
public class FunctionalInterfaces {
  private int temporaryNumber = 0;

  @Test
  public void importantFunctionalInterfaces() {

    Predicate<Integer> isEvenNumber = number -> (number % 2) == 0;
    assertThat(isEvenNumber.test(5), is(false));

    Consumer<Integer> setTemporaryNumber = number ->  temporaryNumber = number;
    setTemporaryNumber.accept(5);
    assertThat(temporaryNumber,is(5));

    Function<Integer,String> toHex = number -> Integer.toHexString(number);
    assertThat(toHex.apply(256), is("100"));

    Supplier<String> helloWorld = () -> "helloWorld";
    assertThat(helloWorld.get(), is("helloWorld"));

    UnaryOperator<String> trim = string -> string.length() >= 10 ? string.substring(0,7).concat("...") : string;
    assertThat(trim.apply("Berlin"),is("Berlin"));
    assertThat(trim.apply("Düsseldorf"),is("Düsseld..."));

    BinaryOperator<Integer> multiply = (number1, number2) -> number1 * number2;
    assertThat(multiply.apply(5,4),is(20));

  }

}
