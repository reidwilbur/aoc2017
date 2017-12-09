package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javafx.util.Pair;

public class Day9 {

  Pair<Integer,Integer> process(IntStream stream) {
    Deque<Character> stack = new ArrayDeque<>();

    List<Integer> groups = Lists.newArrayList();
    int garbageCount = stream.map(c -> {
      if (stack.size() == 0) {
        stack.push((char) c);
        groups.add(0);
      } else {
        char top = stack.peek();
        if (top == '{') {
          if (c == '<' || c == '!') {
            stack.push((char) c);
          } else if (c == '}') {
            stack.poll();
            groups.add(stack.size() + 1);
          } else if (c == '{') {
            stack.push((char) c);
          }
        } else if (top == '<') {
          if (c == '>') {
            stack.poll();
          } else if (c == '!') {
            stack.push((char) c);
          } else {
            return 1;
          }
        } else if (top == '!') {
          stack.poll();
        }
      }
      return 0;
      //System.out.println(String.format("%s %s %s", (char)c, stack, groups));
    }).sum();
    return new Pair<>(groups.stream().mapToInt(i -> i).sum(), garbageCount);
  }
}
