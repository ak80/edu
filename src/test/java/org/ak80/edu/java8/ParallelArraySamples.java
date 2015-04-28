package org.ak80.edu.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ParallelArraySamples {

  @Test
  public void parallelArrayOperations() {

    final int[] unsortedNums = { 5, 1, 10, 7, 2, 9, 8, 4, 6, 3 };
    Arrays.parallelSort(unsortedNums);
    assertThat(unsortedNums[0], is(1));
    assertThat(unsortedNums[9], is(10));

    final int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    IntUnaryOperator intOp = i -> numbers[i] * 2;
    Arrays.setAll(numbers, intOp);
    assertThat(numbers[0], is(2));
    assertThat(numbers[9], is(20));

    final String[] names = { "Frodo", "Sam", "Merry", "Pippin"};
    IntFunction<? super String> stringOp = i -> names[i].substring(0,1);
    Arrays.setAll(names,stringOp);
    assertThat(names[0],is("F"));
    assertThat(names[1], is("S"));
    assertThat(names[2], is("M"));
    assertThat(names[3], is("P"));

    final int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    IntBinaryOperator faculty = (x,y) -> x * y;
    Arrays.parallelPrefix(nums, faculty);
    assertThat(nums[0], is(1 * 1));
    assertThat(nums[1], is(nums[0] * 2));
    assertThat(nums[9], is(nums[8] * 10));

  }



}
