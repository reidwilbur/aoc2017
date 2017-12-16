package com.wilb0t.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 {

  interface Move {
    String apply(String prgms);

    static Move parse(String line) {
      if (line.charAt(0) == 's') {
        return new Spin(line);
      } else if (line.charAt(0) == 'x') {
        return new Exch(line);
      } else if (line.charAt(0) == 'p') {
        return new Part(line);
      } else {
        throw new RuntimeException("bad times " + line);
      }
    }
  }

  static class Spin implements Move {
    final int amt;

    public Spin(String line) {
      this.amt = Integer.valueOf(line.substring(1));
    }

    @Override
    public String apply(String prgmsStr) {
      StringBuilder bldr = new StringBuilder(prgmsStr.length());
      int ofs = amt % prgmsStr.length();
      int start = prgmsStr.length() - ofs;
      for(int i = start; i < start + prgmsStr.length(); i++) {
        bldr.append(prgmsStr.charAt(i % prgmsStr.length()));
      }
      return bldr.toString();
    }

    @Override
    public String toString() {
      return "Spin{" +
             "amt=" + amt +
             '}';
    }
  }

  static class Exch implements Move {
    final int p0;
    final int p1;

    public Exch(String line) {
      String[] parts = line.substring(1).split("/");
      p0 = Integer.valueOf(parts[0]);
      p1 = Integer.valueOf(parts[1]);
    }

    @Override
    public String apply(String prgmsStr) {
      StringBuilder bldr = new StringBuilder(prgmsStr);
      char tmp = prgmsStr.charAt(p0);
      bldr.setCharAt(p0, prgmsStr.charAt(p1));
      bldr.setCharAt(p1, tmp);
      return bldr.toString();
    }

    @Override
    public String toString() {
      return "Exch{" +
             "p0=" + p0 +
             ", p1=" + p1 +
             '}';
    }
  }

  static class Part implements Move {
    final char p0;
    final char p1;

    public Part(String line) {
      String[] parts = line.substring(1).split("/");
      p0 = parts[0].charAt(0);
      p1 = parts[1].charAt(0);
    }

    @Override
    public String apply(String prgmsStr) {
      int i0 = -1;
      int i1 = -1;
      for(int idx = 0; i0 == -1 || i1 == -1; idx++) {
        if (prgmsStr.charAt(idx) == p0) { i0 = idx; }
        if (prgmsStr.charAt(idx) == p1) { i1 = idx; }
      }
      StringBuilder bldr = new StringBuilder(prgmsStr);
      char tmp = prgmsStr.charAt(i0);
      bldr.setCharAt(i0, prgmsStr.charAt(i1));
      bldr.setCharAt(i1, tmp);
      return bldr.toString();
    }

    @Override
    public String toString() {
      return "Part{" +
             "p0=" + p0 +
             ", p1=" + p1 +
             '}';
    }
  }

  String dance(String prgms, List<Move> moves) {
    return moves.stream()
        .reduce(
            prgms,
            (p, m) -> m.apply(p),
            (oldS, newS) -> newS
        );
  }

  static class Pair {
    public final int idx;
    public final String s;

    private Pair(int idx, String s) {
      this.idx = idx;
      this.s = s;
    }

    static Pair of(int idx, String s) {
      return new Pair(idx, s);
    }
  }

  String danceItr(String init, List<Move> moves, int itrs) {
    Map<String, Pair> cache = new HashMap<>();
    for(int i = 0; i < itrs; i++) {
      if (cache.containsKey(init)) {
        // once we have hit the cache we have all the permutations
        // can stop executing and just look up the final value
        int idx = (itrs % cache.size()) - 1;
        return cache.entrySet().stream()
            .filter(e -> e.getValue().idx == idx)
            .findFirst()
            .get()
            .getValue().s;
      } else {
        String newPrgms = dance(init, moves);
        cache.put(init, Pair.of(i, newPrgms));
        init = newPrgms;
      }
    }
    return init;
  }
}
