package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day24 {

  static class Comp {
    final int p0;
    final int p1;

    public Comp(String line) {
      String[] parts = line.split("/");
      p0 = Integer.valueOf(parts[0]);
      p1 = Integer.valueOf(parts[1]);
    }

    boolean hasPortVal(int pVal) {
      return p0 == pVal || p1 == pVal;
    }

    int portVal(int pNum) {
      if (pNum == 0) {
        return p0;
      } else if (pNum == 1) {
        return p1;
      }
      throw new RuntimeException(String.format("No port with idx %d : %s", pNum, this));
    }

    int portNum(int pVal) {
      if (pVal == p0) {
        return 0;
      } else if (pVal == p1) {
        return 1;
      }
      throw new RuntimeException(String.format("No port with value %d : %s", pVal, this));
    }

    int strength() {
      return p0 + p1;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Comp comp = (Comp) o;
      return p0 == comp.p0 &&
             p1 == comp.p1;
    }

    @Override
    public int hashCode() {
      return Objects.hash(p0, p1);
    }

    @Override
    public String toString() {
      return p0 + "/" + p1;
    }
  }

  static class State {
    final List<Comp> bridge;
    final int portNum;
    final List<Comp> comps;

    public State(List<Comp> bridge, int portNum, List<Comp> comps) {
      this.bridge = bridge;
      this.portNum = portNum;
      this.comps = comps;
    }

    List<State> next() {
      if (comps.isEmpty()) {
        return Collections.emptyList();
      }
      Comp head = bridge.get(bridge.size() - 1);
      List<Comp> nexts = comps.stream().filter(c -> c.hasPortVal(head.portVal(portNum))).collect(Collectors.toList());
      List<State> nextStates = new ArrayList<>();
      nexts.forEach(c -> {
        List<Comp> rest = comps.stream().filter(o -> !o.equals(c)).collect(Collectors.toList());
        List<Comp> b = Lists.newArrayList(bridge);
        b.add(c);
        int nextPortNum = (c.portNum(head.portVal(portNum)) + 1) % 2;
        nextStates.add(new State(b, nextPortNum, rest));
      });
      return nextStates;
    }
  }

  public int getStrongestBridge(List<Comp> comps) {
    return getAllBridges(comps).stream()
        .map(b -> b.stream().mapToInt(Comp::strength).sum())
        .sorted(Comparator.reverseOrder())
        .findFirst()
        .get();
  }

  public List<List<Comp>> getAllBridges(List<Comp> comps) {
    List<List<Comp>> bridges = new ArrayList<>();
    List<Comp> heads = comps.stream().filter(c -> c.p0 == 0).collect(Collectors.toList());

    Queue<State> toProcess = new ArrayDeque<>();
    heads.forEach(c -> {
      List<Comp> rest = comps.stream().filter(o -> !o.equals(c)).collect(Collectors.toList());
      toProcess.add(new State(Lists.newArrayList(c), 1, rest));
    });

    while(!toProcess.isEmpty()) {
      State s = toProcess.poll();
      List<State> ns = s.next();
      if (ns.isEmpty()) {
        bridges.add(s.bridge);
      } else {
        toProcess.addAll(ns);
      }
    }

    return bridges;
  }

  public int getStrongestLongestBridge(List<Comp> comps) {
    return getAllBridges(comps).stream()
        .map(b -> Lists.newArrayList(b.size(), b.stream().mapToInt(Comp::strength).sum()))
        .sorted(Comparator.comparing((List<Integer> l) -> l.get(0)).thenComparing(l -> l.get(1)).reversed())
        .findFirst()
        .get()
        .get(1);
  }
}
