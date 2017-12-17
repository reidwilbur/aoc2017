package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class Day17 {

  Map.Entry<Integer, List<Integer>> spin(List<Integer> buff, int itrs, int size) {
    int pos = size % buff.size();
    int lastPos = -1;
    for(int i = 0; i < itrs; i ++) {
      if ((i & 0x3ffff) == 0) { System.out.println(i); }
      buff.add(pos + 1, i + 1);
      lastPos = pos;
      pos = (pos + 1 + size) % buff.size();
    }
    return new AbstractMap.SimpleEntry<>(buff.get((lastPos + 2) % buff.size()), buff);
  }

}
