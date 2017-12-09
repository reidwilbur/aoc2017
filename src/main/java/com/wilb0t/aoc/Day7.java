package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {

  static class Node {
    public final int weight;
    public final String name;
    public final List<String> children;
    public List<Integer> subTreeWeights;
    public int totalWeight = 0;

    public Node(String line) {
      String[] parts = line.split(" -> ");
      String[] nameParts = parts[0].split("\\s+");
      this.name = nameParts[0];
      this.weight = Integer.parseInt(nameParts[1].split("[()]")[1]);
      if (parts.length > 1) {
        children = Arrays.stream(parts[1].split(",")).map(String::trim).collect(Collectors.toList());
        subTreeWeights = Lists.newArrayList(IntStream.range(0, children.size()).map(i -> 0).iterator());
      } else {
        children = Collections.emptyList();
        subTreeWeights = Collections.emptyList();
      }
    }

    @Override
    public String toString() {
      return String.format("[Node name=%s weight=%d]", name, weight);
    }
  }

  String findRoot(List<Node> nodes) {
    Map<String, Integer> refCount = Maps.newHashMap();

    nodes.forEach(n -> {
      refCount.put(n.name, refCount.getOrDefault(n.name, 0) + 1);
      n.children.forEach(nc -> refCount.put(nc, refCount.getOrDefault(nc, 0) + 1));
    });

    return refCount.entrySet().stream().filter(e -> e.getValue() == 1).findFirst().get().getKey();
  }

  int fixWeight(String rootName, List<Node> nodes) {
    Map<String, Node> nameMap = nodes.stream().collect(Collectors.toMap(n -> n.name, Function.identity()));
    Node root = nameMap.get(rootName);

    calcTreeWeights(nameMap, root);

    Node badNode = findBadNode(nameMap, root, "").get();

    List<Integer> sortedWeights = root.subTreeWeights.stream().sorted().collect(Collectors.toList());
    int adjust = 0;
    for(int i = 0; i < sortedWeights.size() && adjust == 0; i++) {
      adjust = sortedWeights.get(0) - sortedWeights.get(i);
    }

    System.out.println(sortedWeights);
    System.out.println(String.format("%s %d", badNode, adjust));
    return badNode.weight + adjust;
  }

  Optional<Node> findBadNode(Map<String, Node> nodes, Node n, String indent) {
    System.out.println(indent + n);
    if (n.children.size() == 0) {
      System.out.println(indent + "leaf");
      return Optional.empty();
    }

    if (n.subTreeWeights.stream().allMatch(w -> w.equals(n.subTreeWeights.get(0)))) {
      System.out.println(indent + "Found wrong node");
      return Optional.of(n);
    }

    int badSubtreeIdx = 0;
    int childSize = n.subTreeWeights.size();
    System.out.println(indent + n.subTreeWeights);
    for(int i = 0; i < childSize; i++) {
      if (!n.subTreeWeights.get(i).equals(n.subTreeWeights.get((i + 1) % childSize))
          && !n.subTreeWeights.get(i).equals(n.subTreeWeights.get((i + childSize - 1) % childSize))) {
        badSubtreeIdx = i;
      }
    }
    System.out.println(indent + String.format("%d %s", badSubtreeIdx, n.children.get(badSubtreeIdx)));

    Optional<Node> badNodeOpt = findBadNode(nodes, nodes.get(n.children.get(badSubtreeIdx)), indent + "  ");

    if (badNodeOpt.isPresent()) {
      System.out.println(indent + "Forwarding child node " + badNodeOpt.get());
      return badNodeOpt;
    }

    System.out.println(indent + "All nodes correct in this tree");
    return Optional.empty();
  }

  int calcTreeWeights(Map<String, Node> nodes, Node n) {
    if (n.children.isEmpty()) {
      n.totalWeight = n.weight;
      return n.weight;
    }
    IntStream.range(0, n.children.size()).forEach(i -> {
      Node childNode = nodes.get(n.children.get(i));
      n.subTreeWeights.set(i, calcTreeWeights(nodes, childNode));
    });
    n.totalWeight = n.weight + n.subTreeWeights.stream().mapToInt(i -> i).sum();
    return n.totalWeight;
  }
}
