package com.wilb0t.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day25 {

  static class Tape {
    final Map<Integer, Boolean> vals;
    int pos;

    public Tape() {
      vals = new HashMap<>();
      pos = 0;
    }

    Tape left() {
      pos -= 1;
      return this;
    }

    Tape right() {
      pos += 1;
      return this;
    }

    boolean read() {
      return vals.getOrDefault(pos, false);
    }

    Tape write(boolean val) {
      vals.put(pos, val);
      return this;
    }
  }

  public static class State {
    final String name;
    final Function<Tape, String> onZero;
    final Function<Tape, String> onOne;

    public State(List<String> lines) {
      List<String> stateVals = lines.stream()
          .map(l -> {
            String[] parts = l.split(" ");
            String val = parts[parts.length - 1];
            return val.substring(0, val.length() - 1);
          })
          .collect(Collectors.toList());

      name = stateVals.get(0);
      onZero = createFn(stateVals.subList(2, 5));
      onOne = createFn(stateVals.subList(6, 9));
    }

    Function<Tape, String> createFn(List<String> stateVals) {
      boolean write = stateVals.get(0).equals("1");
      Consumer<Tape> advance = stateVals.get(1).equals("right") ? Tape::right : Tape::left;
      String nextState = stateVals.get(2);

      return (tape) -> {
        tape.write(write);
        advance.accept(tape);
        return nextState;
      };
    }

    String next(Tape t) {
      return t.read()
             ? onOne.apply(t)
             : onZero.apply(t);
    }
  }

  public int diagChecksum(String startState, Map<String, State> states, Tape tape, long itrs) {
    Stream.iterate(
        states.get(startState),
        s -> {
          String nextState = s.next(tape);
          return states.get(nextState);
        })
        .skip(itrs)
        .findFirst();
    return tape.vals.values().stream().mapToInt(b -> b ? 1 : 0).sum();
  }
}
