package com.wilb0t.aoc;

import java.util.PrimitiveIterator;
import java.util.stream.LongStream;

public class Day15 {
  static final long FACTOR_A = 16807;
  static final long FACTOR_B = 48271;
  static final long DIVISOR = 2147483647;

  int countMatches(int seedA, int seedB, int itrs) {
    int matchCount = 0;
    long valA = seedA;
    long valB = seedB;
    for(int i = 0; i < itrs; i++) {
      valA = (FACTOR_A * valA) % DIVISOR;
      valB = (FACTOR_B * valB) % DIVISOR;
      matchCount += (0xffff & valA) == (0xffff & valB) ? 1 : 0;
    }
    return matchCount;
  }

  int countMatches2(int seedA, int seedB, int itrs) {
    PrimitiveIterator.OfLong streamA = LongStream
        .iterate(seedA, l -> (FACTOR_A * l) % DIVISOR)
        .filter(l -> (l & 0b11) == 0)
        .iterator();

    PrimitiveIterator.OfLong streamB = LongStream
        .iterate(seedB, l -> (FACTOR_B * l) % DIVISOR)
        .filter(l -> (l & 0b111) == 0)
        .iterator();

    int matchCount = 0;
    for(int i = 0; i < itrs; i++) {
      long valA = streamA.next();
      long valB = streamB.next();
      matchCount += (0xffff & valA) == (0xffff & valB) ? 1 : 0;
    }

    return matchCount;
  }
}
