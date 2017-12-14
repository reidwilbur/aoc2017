package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12 {

  static List<Integer> readInputLine(String line) {
    String[] cons = line.split(" <-> ")[1].split(",");

    return Stream.of(cons).map(s -> Integer.valueOf(s.trim())).collect(Collectors.toList());
  }

  List<Integer> connectedNodes(Integer root, List<List<Integer>> adjList) {
    Map<Integer, Boolean> visited = Maps.newHashMap();
    Queue<Integer> toProcess = new ArrayDeque<>();
    toProcess.add(root);
    visited.put(root, true);
    List<Integer> connected = new ArrayList<>();

    while(!toProcess.isEmpty()) {
      int n = toProcess.remove();
      //if (adjList.get(n).size() > 0) {
        connected.add(n);
        adjList.get(n)
            .stream()
            .filter(cn -> !visited.containsKey(cn))
            .forEach(cn -> {
              visited.put(cn, true);
              toProcess.add(cn);
            });
      //}
    }
    return connected;
  }

  List<List<Integer>> connectedComponents(List<List<Integer>> adjList) {
    Map<Integer, Boolean> visited = Maps.newHashMap();
    List<List<Integer>> connected = Lists.newArrayList();

    IntStream.range(0, adjList.size()).forEach(n -> {
      if (!visited.containsKey(n)) {
        if (adjList.get(n).size() > 0) {
          List<Integer> nodes = connectedNodes(n, adjList);
          connected.add(nodes);
          nodes.forEach(cxn -> visited.put(cxn, true));
        }
      }
    });

    return connected;
  }
}
