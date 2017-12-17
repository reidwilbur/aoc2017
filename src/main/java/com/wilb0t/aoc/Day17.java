package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class Day17 {

  Map.Entry<Integer, List<Integer>> spin(List<Integer> buff, int itrs, int size) {
    int pos = size % buff.size();
    int lastPos = -1;
    for(int i = 0; i < itrs; i++) {
      buff.add(pos + 1, i + 1);
      lastPos = pos;
      pos = (pos + 1 + size) % buff.size();
      //System.out.println(String.format("%03d %s", pos, buff));
    }
    return new AbstractMap.SimpleEntry<>(buff.get((lastPos + 2) % buff.size()), buff);
  }

  int getIdx1Val(int itrs, int stepSize) {
    int size = 1;
    int pos = stepSize % size;
    int idx1Val = -1;
    for(int i = 0; i < itrs; i++) {
      if (pos == 0) {
        idx1Val = i + 1;
      }
      size += 1;
      pos = (pos + 1 + stepSize) % size;
      //System.out.println(String.format("% 3d %d", pos, idx1Val));
    }
    return idx1Val;
  }
}
