package org.ak80.edu.exercises.java8lambda;

import javafx.scene.input.DataFormat;
import org.junit.Test;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Exercises from "Java 8 Lambdas" by Richard Warburton"
 */
public class Chapter02 {

  /**
   * Create a Thread Safe DateFormatter with the Factory from ThreadLocal that prints dates like "01-JAN-1970"
   */
  @Test
  public void exercise02() throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY");
    ThreadLocal<DateFormatter> dateFormatter = ThreadLocal.withInitial(() -> new DateFormatter(dateFormat));

    String formattedString = dateFormatter.get().valueToString(new Date(0));

    assertThat(formattedString,is("01-Jan-1970"));
  }



}
