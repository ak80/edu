package org.ak80.edu.java8;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class ComparatorsSamples {

  List<Hobbit> hobbits = getHobbits();

  Hobbit frodo = hobbits.get(0);
  Hobbit sam = hobbits.get(1);
  Hobbit pippin = hobbits.get(2);
  Hobbit merry = hobbits.get(3);

  @Test
  public void comparator() {

    Comparator<Hobbit> compareByName1 = (hobbit1, hobbit2) ->
        hobbit1.getName().compareTo(hobbit2.getName());

    assertThat(compareByName1.compare(frodo,sam),lessThan(0));
    assertThat(compareByName1.compare(frodo,frodo),is(0));
    assertThat(compareByName1.compare(sam,frodo),greaterThan(0));

    Comparator<Hobbit> compareByName2 = Comparator.comparing( hobbit -> hobbit.getName() );
    Comparator<Hobbit> compareByName3 = Comparator.comparing( Hobbit::getName );

  }

  @Test
  public void thenComparing() {
    Comparator<Hobbit> compareByName = Comparator.comparing( Hobbit::getName );
    Comparator<Hobbit> compareByAge = Comparator.comparingInt(Hobbit::getAge);

    Comparator<Hobbit> compareByNameThenByAge = compareByAge.thenComparing(compareByAge);
  }

  @Test
  public void comparingPrimitives() {
    Comparator<Hobbit> compareByAge1 = Comparator.comparingInt(Hobbit::getAge);
    Comparator<Hobbit> compareByAge2 = Comparator.comparingLong(Hobbit::getAge);
    Comparator<Hobbit> compareByAge3 = Comparator.comparingDouble(Hobbit::getAge);
  }

  @Test
  public void compareOrder() {
    Comparator<Hobbit> compareByName = Comparator.comparing(Hobbit::getName);

    Comparator<Hobbit> ascending = compareByName;
    Comparator<Hobbit> descending = compareByName.reversed();
  }

  @Test
  public void handleNull() {
    Comparator<Hobbit> compareByName = Comparator.comparing( Hobbit::getName );

    Comparator<Hobbit> nullFirst = Comparator.nullsFirst(compareByName);
    Comparator<Hobbit> nullLast = Comparator.nullsLast(compareByName);
  }



  public List<Hobbit> getHobbits() {
    List<Hobbit> hobbits = new ArrayList<>();
    hobbits.add(new Hobbit("Frodo", "Baggins", 50));
    hobbits.add(new Hobbit("Samwise", "Gamgee", 38));
    hobbits.add(new Hobbit("Meriadoc", "Brandybuck", 36));
    hobbits.add(new Hobbit("Peregrin", "Took", 28));
    return hobbits;
  }


  class Hobbit {
    private String surname;
    private String name;
    private int age;

    public Hobbit(String name, String surname, int age) {
      this.name = name;
      this.surname = surname;
      this.age = age;
    }

    public String getSurname() {
      return surname;
    }

    public String getName(){
      return name;
    }

    public int getAge() {
      return age;
    }

  }
}
