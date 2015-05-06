package org.ak80.edu.exercises.java8lambda;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import org.ak80.edu.exercises.java8lambda.domain.Album;
import org.ak80.edu.exercises.java8lambda.domain.Artist;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Exercises from "Java 8 Lambdas" by Richard Warburton"
 */
public class Chapter03 {

  @Test
  public void exercise01() {

    // Implement a function that adds up numbers, i.e. int addUp(Stream<Integer> numbers)
    Function<Stream<Integer>,Integer> addUp = numbers -> numbers.reduce((x,y) -> x+y).get();

    Stream<Integer> numbers = Stream.of(0, 1, 2, 3, 4, 5);
    assertThat(addUp.apply(numbers),is(15));

    // Implement a function that takes in artists and returns a list of strings with their names and places of origin
    Function<List<Artist>,List<String>> getNamesAndOrigin = artists ->
        artists.stream()
            .map(art -> String.format("%s (%s)", art.getName(), art.getOrigin()))
            .collect(Collectors.toList());

    List<String> namesAndOrigin = getNamesAndOrigin.apply(Artist.getAllArtists());
    assertThat(namesAndOrigin.get(0),is("The Headbangers (Moscow)"));
    assertThat(namesAndOrigin.get(1),is("Gartenzwerge (Bottrop)"));
    assertThat(namesAndOrigin.get(2), is("The Bavarian Brass Buffalo (München)"));

    namesAndOrigin.stream().forEach(System.out::println);

    // Implement a function that takes in albums and returns a list of albums with at most three tracks
    Function<List<Album>,List<Album>> filterAlbumsWithAtMostThreeTracks = albums ->
        albums.stream()
            .filter(album -> album.getTracks().size() <= 3)
            .collect(Collectors.toList());

    List<Album> albumsWithAtMostThreeTracks = filterAlbumsWithAtMostThreeTracks.apply(Artist.getAllAlbums());
    Consumer<Album> verifyAtMostThreeTracks = album -> assertThat(album.getTracks().size(), lessThanOrEqualTo(3));
    albumsWithAtMostThreeTracks.forEach(verifyAtMostThreeTracks);

    albumsWithAtMostThreeTracks.stream().forEach(System.out::println);

  }

  @Test
  public void exercise02() {
    /*
     Convert this code sample from using external iteration to internal iteration
     int totalMembers = 0;
     for (Artist artist : artists) {
        Stream<Artist> members = artist.getMembers();
        totalMembers += members.count();
     }
    */
    List<Artist> artists = Artist.getAllArtists();

    int totalMembers = 0;
    for (Artist artist : artists) {
      Stream<Artist> members = artist.getMembers();
      totalMembers += members.count();
    }
    int totalMembersExpected = totalMembers;

    totalMembers = (int) artists.stream().flatMap(artist -> artist.getMembers()).count();

    assertThat(totalMembers, is(totalMembersExpected));
  }

  @Test
  public void exercise06() {
    /*
     Count the number of lowercase letters in a string. Hint: look at the chars() function in String
     */

    Function<String,Long> countLowerCaseLetters = string ->
        string.chars()
          .filter(character -> Character.isLowerCase(character))
          .count();


    assertThat(countLowerCaseLetters.apply("Hello"), is(4L));
    assertThat(countLowerCaseLetters.apply("CamelCase"), is(7L));
  }

  @Test
  public void exercise07() {
    /*
     Find the String with the largest number of lowercase letters from a List<String>. You can return an
     Optional<String> to account for the empty list case.
     */

    IntPredicate isLowercase = ch -> Character.isLowerCase(ch);
    Function<String,Integer> getLowercaseLettersCount = string -> (int) string.chars().filter(isLowercase).count();
    Comparator<String> compareByLowercaseNumbers = (str1, str2) -> Integer.compare(getLowercaseLettersCount.apply(str1),getLowercaseLettersCount.apply(str2));
    Function<Stream<String>, Optional<String>> findString = strings ->
      strings.max(compareByLowercaseNumbers);

    List<String> list = Arrays.asList("Hello", "CamelCase", "Foo", "EXTRALONGWORD");
    assertThat(findString.apply(list.stream()).get(), is("CamelCase"));
    list = Collections.emptyList();
    assertThat(findString.apply(list.stream()).isPresent(), is(false));
  }

}
