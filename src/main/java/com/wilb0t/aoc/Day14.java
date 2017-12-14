package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {

  private final Function<String, String> hasher;
  private final Function<List<List<Integer>>, List<List<Integer>>> getConComps;

  public Day14(
      Function<String, String> hasher,
      Function<List<List<Integer>>, List<List<Integer>>> getConComps) {
    this.hasher = hasher;
    this.getConComps = getConComps;
  }

  int getCountForHash(String hash) {
    int count = 0;
    for(char c : hash.toCharArray()) {
      count += Integer.bitCount(Integer.valueOf(String.valueOf(c), 16));
    }
    return count;
  }

  static final String[] asciiToBin = new String[]{
    "0000",
    "0001",
    "0010",
    "0011",
    "0100",
    "0101",
    "0110",
    "0111",
    "1000",
    "1001",
    "1010",
    "1011",
    "1100",
    "1101",
    "1110",
    "1111"
  };

  String hashToBinString(String hash) {
    StringBuilder bldr = new StringBuilder();
    for(char c : hash.toLowerCase().toCharArray()) {
      if (c - 97 >= 0) {
        bldr.append(asciiToBin[c - 97 + 0xa]);
      } else {
        bldr.append(asciiToBin[c - 48]);
      }
    }
    return bldr.toString();
  }

  List<List<Integer>> binStringToAdjList(String binString, int stride) {
    List<List<Integer>> adjList = new ArrayList<>(binString.length());
    for(int i = 0; i < binString.length(); i++) {
      char c = binString.charAt(i);
      List<Integer> neighbors = new ArrayList<>(4);
      if (c == '1') {
        neighbors.add(i);

        int up = i - stride;
        if (up >= 0
            && binString.charAt(up) == '1') {
          neighbors.add(up);
        }

        int down = i + stride;
        if (down < binString.length()
            && binString.charAt(down) == '1') {
          neighbors.add(down);
        }

        int left = i - 1;
        if (left / stride == i / stride
            && left > 0
            && binString.charAt(left) == '1') {
          neighbors.add(left);
        }

        int right = i + 1;
        if (right / stride == i / stride
            && right < binString.length()
            && binString.charAt(right) == '1') {
          neighbors.add(right);
        }
      }
      adjList.add(neighbors);
    }
    return adjList;
  }

  int getUsedCount(String key) {
    return IntStream
        .range(0, 128)
        .mapToObj(i -> key + "-" + i)
        .map(hasher)
        .mapToInt(this::getCountForHash)
        .sum();
  }

  int getUsedRegionCount(String key) {
    String binString = IntStream
        .range(0, 128)
        .mapToObj(i -> key + "-" + i)
        .map(hasher)
        .map(this::hashToBinString)
        .collect(Collectors.joining());

    List<List<Integer>> adjList = binStringToAdjList(binString, 128);

    List<List<Integer>> conComps = getConComps.apply(adjList);

    return conComps.size();
  }
}
