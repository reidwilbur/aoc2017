package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day21 {

  static public class Rule {
    final List<String> key;
    final List<String> result;

    public Rule(String line) {
      String[] parts = line.split(" => ");
      this.key = Arrays.asList(parts[0].split("/"));
      this.result = Arrays.asList(parts[1].split("/"));
    }

    Optional<List<String>> matches(List<String> tile) {
      if (tile.size() != key.size()) {
        return Optional.empty();
      }
      List<Boolean> tests = IntStream.range(0, 8).mapToObj(i -> true).collect(Collectors.toList());
      int size = tile.size();
      for(int r = 0; r < size; r++) {
        for(int c = 0; c < size; c++) {
          // same
          tests.set(0, tests.get(0) && (tile.get(r).charAt(c)) == key.get(r).charAt(c));
          // hflip
          tests.set(1, tests.get(1) && (tile.get(r).charAt(size - 1 - c)) == key.get(r).charAt(c));
          // vflip
          tests.set(2, tests.get(2) && (tile.get(size - 1 - r).charAt(c)) == key.get(r).charAt(c));
          // rot 180
          tests.set(3, tests.get(3) && (tile.get(size - 1 - r).charAt(size - 1 - c)) == key.get(r).charAt(c));
          // rot 90
          tests.set(4, tests.get(4) && (tile.get(c).charAt(size - 1 - r)) == key.get(r).charAt(c));
          // rot -90
          tests.set(5, tests.get(5) && (tile.get(size - 1 - c).charAt(r)) == key.get(r).charAt(c));
          // rot 90 + flip
          tests.set(6, tests.get(6) && (tile.get(c).charAt(r)) == key.get(r).charAt(c));
          // rot -90 + flip
          tests.set(7, tests.get(7) && (tile.get(size - 1 - c).charAt(size - 1 - r)) == key.get(r).charAt(c));
        }
      }
      return tests.stream().anyMatch(Boolean.TRUE::equals)
          ? Optional.of(result)
          : Optional.empty();
    }
  }

  public List<String> enhance(List<Rule> rules, List<String> tile) {
    int tileSize = (tile.size() % 2 == 0) ? 2 : 3;
    List<List<String>> tiles = divideTile(tile, tileSize).stream()
        .map(subtile ->
          rules.stream()
              .map(r -> r.matches(subtile))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .findFirst()
              .get()
        ).collect(Collectors.toList());

    return joinTiles(tiles);
  }

  List<List<String>> divideTile(List<String> tile, int size) {
    List<List<String>> tiles = new ArrayList<>();
    for(int r = 0; r < tile.size() / size; r++) {
      for(int c = 0; c < tile.size() / size; c++) {
        List<String> newTile = new ArrayList<>();
        for(int i = 0; i < size; i++) {
          newTile.add(tile.get((r*size) + i).substring((c*size), ((c+1)*size)));
        }
        tiles.add(newTile);
      }
    }
    return tiles;
  }

  List<String> joinTiles(List<List<String>> tiles) {
    int size = (int)Math.sqrt(tiles.size());
    List<String> joined = new ArrayList<>();
    for(int i = 0; i < tiles.size(); i++) {
      List<String> tile = tiles.get(i);
      if (i % size == 0) {
        joined.addAll(tile);
      } else {
        for(int j = 0; j < tile.size(); j++) {
          int joinedIdx = (i/size) * tile.size() + j;
          String curr = joined.get(joinedIdx);
          joined.set(joinedIdx, curr + tile.get(j));
        }
      }
    }
    return joined;
  }

  public int litCount(List<Rule> rules, List<String> input, int itrs) {
    List<String> enhanced = input;
    for(int i = 0; i < itrs; i++) {
      enhanced = enhance(rules, enhanced);
    }
    return enhanced.stream()
        .mapToInt(row -> litCount(row, '#'))
        .sum();
  }

  int litCount(String s, char c) {
    int count = 0;
    for(int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == c) {
        count += 1;
      }
    }
    return count;
  }
}
