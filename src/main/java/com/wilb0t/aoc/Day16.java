package com.wilb0t.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 {

  interface Move {
    void apply(char[] prgms);

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
    public void apply(char[] prgms) {
      int l = prgms.length;
      int ofs = amt % l;
      reverse(prgms, 0, l - 1);
      reverse(prgms, 0, ofs - 1);
      reverse(prgms, ofs, l - 1);
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
    public void apply(char[] prgms) {
      swap(prgms, p0, p1);
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
    public void apply(char[] prgms) {
      int i0 = -1;
      int i1 = -1;
      for(int idx = 0; i0 == -1 || i1 == -1; idx++) {
        if (prgms[idx] == p0) { i0 = idx; }
        if (prgms[idx] == p1) { i1 = idx; }
      }
      swap(prgms, i0, i1);
    }
  }

  static void reverse(char[] cs, int left, int right) {
    while(left < right) {
      swap(cs, left, right);
      left++;
      right--;
    }
  }

  static void swap(char[] cs, int p0, int p1) {
    char tmp = cs[p0];
    cs[p0] = cs[p1];
    cs[p1] = tmp;
  }

  char[] dance(char[] prgms, List<Move> moves) {
    for(int i = 0; i < moves.size(); i++) {
      moves.get(i).apply(prgms);
    }
    return prgms;
  }

  char[] danceItr(char[] init, List<Move> moves, int itrs) {
    Map<String, String> cache = new HashMap<>();
    for(int i = 0; i < itrs; i++) {
      if ((i & 0x3ff) == 0) System.out.println(i);
      if (cache.containsKey(String.valueOf(init))) {
        init = cache.get(String.valueOf(init)).toCharArray();
      } else {
        cache.put(String.valueOf(init), String.valueOf(dance(init, moves)));
      }
    }
    return init;
  }
}
