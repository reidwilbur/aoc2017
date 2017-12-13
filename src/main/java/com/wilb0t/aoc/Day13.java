package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.Map;

public class Day13 {

  static Map.Entry<Integer, Integer> readLine(String line) {
    String[] parts = line.split(": ");
    return new AbstractMap.SimpleEntry<>(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
  }

  // 5  0 1 2 3 4 3 2 1 0 1 2
  // 4  0 1 2 3 2 1 0 1 2
  // 3  0 1 2 1 0 1 2 1 0
  // 2  0 1 0 1 0 1
  int getScanLine(int size, int timeStep) {
    if (size == 1) return 0;
    int seqLen = size + size - 2;
    int pos = timeStep % seqLen;
    return (pos < size) ? pos : (size - 1) - (pos - size + 1);
  }

  int getSeverity(int sevOffset, int timeStepOffset, int[] layers) {
    int sev = 0;
    int limit = layers.length - 1;
    for(int timeStep = -1; timeStep < limit; timeStep++) {
      int layer = timeStep + 1;
      int size = layers[layer];
      if (size > 0) {
        int nextScanLine = getScanLine(size, timeStep + 1 + timeStepOffset);
        int lsev = nextScanLine == 0 ? (layer + sevOffset) * size : 0;
        sev += lsev;
      }
    }
    return sev;
  }

  int findLowestDelay(int[] layers) {
    int delay = -1;
    for(int ofs = 0; delay == -1; ofs++) {
      int sev = getSeverity(1, ofs, layers);
      delay = sev == 0 ? ofs : -1;
    }
    return delay;
  }
}
