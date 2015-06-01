package org.ak80.edu.exercises.java8lambda.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Artist implements Comparable<Artist> {
  private final String name;
  private final Set<Artist> members = new TreeSet<>();
  private final String origin;
  private final List<Album> albums = new ArrayList<>();


  public Artist(String name, String origin) {
    this.name = name;
    this.origin = origin;
  }

  public String getName() {
    return name;
  }

  public String getOrigin() {
    return origin;
  }

  public void addMember(String name) {
    members.add(new Artist(name,origin));
  }

  public void addAlbum(Album album) {
    albums.add(album);
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public static List<Artist> getAllArtists() {
    List<Artist> artists = new ArrayList<>();

    Artist headbangers= new Artist("The Headbangers","Moscow");
    headbangers.addMember("Ivan the Gruelsome");
    headbangers.addMember("Igor");
    headbangers.addMember("Irina");
    artists.add(headbangers);

    Album bangingAroundTheWorld = new Album("Banging Around The World");
    bangingAroundTheWorld.addTrack("Moscow Shot");
    bangingAroundTheWorld.addTrack("Russian Escapade");
    headbangers.addAlbum(bangingAroundTheWorld);

    Artist gartenzwerge= new Artist("Gartenzwerge","Bottrop");
    gartenzwerge.addMember("Günni Kologe");
    gartenzwerge.addMember("Manni Plaste");
    gartenzwerge.addMember("Wulf");
    gartenzwerge.addMember("Adam");
    artists.add(gartenzwerge);

    Album derGartenIstMein = new Album("Der Garten ist mein Refugium");
    derGartenIstMein.addTrack("Intro");
    derGartenIstMein.addTrack("Der Garten ist mein Refugium");
    derGartenIstMein.addTrack("Tod der Erde");
    derGartenIstMein.addTrack("Max, der Maulwurf");
    gartenzwerge.addAlbum(derGartenIstMein);

    Artist buffalos= new Artist("The Bavarian Brass Buffalo","München");
    buffalos.addMember("Biff");
    buffalos.addMember("Baff");
    buffalos.addMember("Buff");
    artists.add(buffalos);

    Album buffaloDance = new Album("Buffalo, tanz!");
    buffaloDance.addTrack("Buffalo, tanz!");
    buffaloDance.addTrack("Buffalos For Life");
    buffaloDance.addTrack("Vier kleine Buffalos");
    buffalos.addAlbum(buffaloDance);

    return artists;
  }

  public static List<Album> getAllAlbums() {
    return getAllArtists().stream().flatMap( artist -> artist.getAlbums().stream() ).collect(Collectors.toList());
  }


  public Stream<Artist> getMembers() {
    return members.stream();
  }

  @Override
  public int compareTo(Artist o) {
    return o.name.compareTo(this.getName());
  }
}
