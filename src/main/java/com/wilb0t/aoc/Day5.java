package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Day5 {

  int countJumps(List<Integer> instrs) {
    return exec(instrs, (relAddr) -> relAddr + 1);
  }

  int countJumps2(List<Integer> instrs) {
    return exec(instrs, (relAddr) -> (relAddr >= 3) ? relAddr - 1 : relAddr + 1);
  }

  int exec(List<Integer> instrs, Function<Integer, Integer> jumpUpdate) {
    List<Integer> linstrs = new ArrayList<>(instrs);
    int ip = 0;
    int jumps = 0;
    while (ip < instrs.size()) {
      int relAddr = linstrs.get(ip);
      linstrs.set(ip, jumpUpdate.apply(relAddr));
      ip += relAddr;
      jumps += 1;
    }
    return jumps;
  }
}
