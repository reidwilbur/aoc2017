package com.wilb0t.aoc;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2 {

  int getChecksum(List<List<Integer>> rows) {
    return rows.stream()
        .mapToInt(row -> {
          IntSummaryStatistics stats =
              row.stream().collect(Collectors.summarizingInt(Integer::intValue));
          return stats.getMax() - stats.getMin();
        })
        .sum();
  }

  int getDivChecksum(List<List<Integer>> rows) {
    return rows.stream()
        .mapToInt(row -> {
          List<Integer> sorted = row.stream().sorted().collect(Collectors.toList());
          return IntStream.range(0, sorted.size())
              .map(idx -> {
                int divisor = sorted.get(idx);
                return sorted.subList(idx + 1, sorted.size()).stream()
                    .mapToInt(col -> (col % divisor == 0) ? col / divisor : 0)
                    .sum();
              })
              .sum();
        })
        .sum();
  }
}
