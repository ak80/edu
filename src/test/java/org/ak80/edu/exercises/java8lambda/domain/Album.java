package org.ak80.edu.exercises.java8lambda.domain;


import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Album {

  private final String name;
  private final List<Track> tracks = new ArrayList<>();

  public Album(String name) {
    this.name = name;
  }

  public void addTrack(String name) {
    tracks.add(new Track(name));
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public String toString() {
    return name + getTracks().stream().map(track -> track.getName()).collect(joining(", ","[","]"));
  }
}
