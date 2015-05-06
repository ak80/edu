package org.ak80.edu.java8;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Some additions to the interface Map
 */
public class MapInterfaceAdditions {

  private ConcurrentHashMap<String,String> hashMap;

  @Before
  public void fillMap() {
    hashMap = new ConcurrentHashMap<>();
    hashMap.put("Frodo","Hobbit");
    hashMap.put("Sam","Hobbit");
    hashMap.put("Gandalf","Wizard");
    hashMap.put("Legolas","Elf");
  }



  @Test
  public void newMethods() {

    // get value for key, if not present the default is returned
    assertThat(hashMap.getOrDefault("Frodo","Person"),is("Hobbit"));
    assertThat(hashMap.getOrDefault("Merry", "Person"),is("Person"));

    // put value if key not present, returns the value present before the put
    assertThat(hashMap.putIfAbsent("Merry", "Hobbit"),is(nullValue()));
    assertThat(hashMap.putIfAbsent("Merry", "Person"),is("Hobbit"));
    assertThat(hashMap.get("Merry"),is("Hobbit"));

    // replace a value only if the key exists, returns the value before the put
    assertThat(hashMap.replace("Pippin", "Person"),is(nullValue()));
    assertThat(hashMap.put("Pippin", "Person"), is(nullValue()));
    assertThat(hashMap.get("Pippin"),is("Person"));
    assertThat(hashMap.replace("Pippin", "OldValue", "Hobbit"), is(false));
    assertThat(hashMap.get("Pippin"),is("Person"));
    assertThat(hashMap.replace("Pippin", "Person", "Hobbit"), is(true));
    assertThat(hashMap.get("Pippin"), is("Hobbit"));

    // if the key is not present, execute a function to calculate the value and put it under they key
    hashMap.computeIfAbsent("Gimli", it -> it.equals("Gimli") ? "Dwarf" : "unkown");
    assertThat(hashMap.get("Gimli"), is("Dwarf"));
    hashMap.computeIfAbsent("Pippin", it -> it.equals("Gimli") ? "Dwarf" : "unkown");
    assertThat(hashMap.get("Pippin"), is("Hobbit"));

    // if the key is present, execute a function to calculate a new value to put  under they key
    hashMap.put("Boromir", "Person");
    hashMap.computeIfPresent("Boromir", (key, val) -> key.equals("Boromir") ? "Human" : "unknown");
    assertThat(hashMap.get("Boromir"), is("Human"));
    hashMap.computeIfPresent("Elrond", (key, val) -> key.equals("Elrond") ? "Elf" : "unknown");
    assertThat(hashMap.containsKey("Elrond"), is(false));
  }

  @Test
  public void merge() {
    hashMap.merge("Frodo", "hero", String::concat);
    assertThat(hashMap.get("Frodo"),is("Hobbithero"));

    hashMap.merge("Gimli", "hero", String::concat);
    assertThat(hashMap.get("Gimli"),is("hero"));
  }

  @Test
  public void bulkOperations() {
    StringBuilder keyValueCollector = new StringBuilder();
    hashMap.forEach((key, value) -> keyValueCollector.append(key + " " + value));
    assertThat(keyValueCollector.toString(),is("Frodo HobbitGandalf WizardSam HobbitLegolas Elf"));

    int estimatedNumberOfElementsAsParallelismThreshold = hashMap.size();
    int t = estimatedNumberOfElementsAsParallelismThreshold;

    StringBuilder keyCollector = new StringBuilder();
    hashMap.forEachKey(t, key -> keyCollector.append(key));

    assertThat(keyCollector.toString(), containsString("Frodo"));
    assertThat(keyCollector.toString(), containsString("Gandalf"));
    assertThat(keyCollector.toString(), containsString("Sam"));
    assertThat(keyCollector.toString(), containsString("Legolas"));

    StringBuilder valueCollector = new StringBuilder();
    hashMap.forEachValue(t, value -> valueCollector.append(value));

    assertThat(valueCollector.toString(), containsString("Hobbit"));
    assertThat(valueCollector.toString(), containsString("Wizard"));
    assertThat(valueCollector.toString(), containsString("Elf"));

  }

}
