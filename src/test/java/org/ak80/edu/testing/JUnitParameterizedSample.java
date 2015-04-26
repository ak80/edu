package org.ak80.edu.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class JUnitParameterizedSample {

  @Parameterized.Parameters(name = "{index}: toUpperCase({0})={1}")
  public static Iterable<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"foo", "FOO"}, {"bar", "BAR"}
    });
  }

  private String input;
  private String expected;

  public JUnitParameterizedSample(String input, String expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void test() {
    assertThat(expected, is(input.toUpperCase()));
  }

}
