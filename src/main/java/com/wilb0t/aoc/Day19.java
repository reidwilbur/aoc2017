package com.wilb0t.aoc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day19 {

  enum Dir {
    D, L, U, R;

    Dir[] getTurnDirs() {
      Dir[] vals = values();
      int o = ordinal();
      return new Dir[]{ vals[(o + vals.length - 1) % vals.length], vals[(o + 1) % vals.length] };
    }
  }

  static class Pos {
    final int row;
    final int col;

    public Pos(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public Pos(Pos p) {
      this(p.row, p.col);
    }

    public Pos next(Dir d) {
      switch (d) {
        case D: return new Pos(row + 1, col);
        case L: return new Pos(row, col - 1);
        case U: return new Pos(row - 1, col);
        case R: return new Pos(row, col + 1);
      }
      throw new RuntimeException("Bad dir " + d);
    }

    char getMapVal(List<String> map) {
      return map.get(row).charAt(col);
    }

    boolean isValid(List<String> map) {
      return (row >= 0 && row < map.size())
             && (col >= 0 && col < map.get(row).length());
    }

    @Override
    public String toString() {
      return String.format("{Pos (%d,%d)}", row, col);
    }
  }

  static final Set<Character> LETTERS = IntStream.range('A', 'Z' + 1)
      .mapToObj(i -> (char)i)
      .collect(Collectors.toSet());

  static final Set<Character> PATH_CHARS = Stream.concat(Stream.of('|', '-', '+'), LETTERS.stream())
      .collect(Collectors.toSet());

  String getLetters(List<String> map) {
    Pos p = new Pos(0, map.get(0).indexOf('|'));
    Dir dir = Dir.D;

    StringBuilder lettersBldr = new StringBuilder();
    while (p.isValid(map)) {
      char c = p.getMapVal(map);
      //System.out.print(c + " ");
      if (c == '|' || c == '-') {
        p = p.next(dir);
        //System.out.println(p);
      } else if (c == '+') {
        Pos cp = new Pos(p);
        //System.out.println(p);
        dir = Stream.of(dir.getTurnDirs()).filter(d -> PATH_CHARS.contains(cp.next(d).getMapVal(map))).findFirst().get();
        p = p.next(dir);
      } else if (LETTERS.contains(c)) {
        lettersBldr.append(c);
        p = p.next(dir);
        //System.out.println(p);
      } else {
        return lettersBldr.toString();
      }
    }
    return lettersBldr.toString();
  }


}
