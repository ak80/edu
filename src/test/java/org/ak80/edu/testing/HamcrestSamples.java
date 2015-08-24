package org.ak80.edu.testing;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;

public class HamcrestSamples {

  @Test
  public void usingHamcrest() {
    // add identifier
    assertThat("identifier", "foo", equalTo("foo"));

    // core
    assertThat("foo", is(anything("bar")));
    assertThat("foo", is("foo"));
    assertThat("foo", is(equalTo("foo")));

    // logical
    for (int i = 1; i < 4; i++) {
      assertThat(1, anyOf(equalTo(1), equalTo(2), equalTo(3)));
    }
    assertThat("foo", allOf(startsWith("f"), containsString("o"), endsWith("o")));
    assertThat("foo", is(not("bar")));

    // object
    assertThat("foo", equalTo("foo"));
    assertThat(true, hasToString(equalTo("true")));
    assertThat("foo", instanceOf(String.class));
    assertThat("foo", notNullValue());
    assertThat(null, nullValue());
    assertThat("foo", sameInstance("foo"));
    assertThat("foo", is(any(String.class)));

    // beans
    assertThat(new Date(), hasProperty("time"));

    // collection
    List<String> list = Arrays.asList("foo", "bar", "baz");
    assertThat(list, hasItem("foo"));
    assertThat(list, not(hasItem("quzz")));
    assertThat(list, hasItems("bar", "foo"));
    assertThat(list, hasSize(3));
    assertThat(list.toArray(), hasItemInArray("foo"));

    assertThat(list, contains("foo", "bar", "baz"));
    assertThat(list, containsInAnyOrder("foo", "baz", "bar"));

    assertThat(Arrays.asList(), empty());
    assertThat(new Object[0], emptyArray());

    // collection map
    Map<String, String> map = new HashMap<>();
    map.put("foo", "bar");
    assertThat(map, hasEntry("foo", "bar"));
    assertThat(map, hasKey("foo"));
    assertThat(map, hasValue("bar"));

    // number
    assertThat(5.501, closeTo(5.502, 0.01));
    assertThat(5, greaterThan(4));
    assertThat(5, greaterThanOrEqualTo(4));
    assertThat(5, greaterThanOrEqualTo(5));
    assertThat(4, lessThan(5));
    assertThat(4, lessThanOrEqualTo(4));
    assertThat(4, lessThanOrEqualTo(5));

    // text
    assertThat("foo", equalToIgnoringCase("FOO"));
    assertThat("foo", equalToIgnoringWhiteSpace(" foo"));
    assertThat("bar", containsString("a"));
    assertThat("bar", startsWith("b"));
    assertThat("bar", endsWith("r"));
    assertThat("", isEmptyString());
    assertThat(null, isEmptyOrNullString());

  }

  // custom matcher
  private Matcher<Long> isEqualToDateLong(final Long expectedValue) {
    final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS Z");

    return new BaseMatcher<Long>() {
      @Override
      public boolean matches(final Object actualValue) {
        return expectedValue.equals(actualValue);
      }

      @Override
      public void describeTo(final Description description) {
        description.appendText("was ").appendValue(DATE_FORMAT.format(new Date(expectedValue)));
      }

      @Override
      public void describeMismatch(final Object actualValue, final Description description) {
        description.appendText("was ").appendValue(DATE_FORMAT.format(new Date((Long) actualValue)));
      }
    };
  }

}



