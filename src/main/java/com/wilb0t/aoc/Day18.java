package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiFunction;

public class Day18 {

  static class MachineState {
    final Map<String, Long> regFile;
    final Queue<Long> in;
    final Queue<Long> out;

    public MachineState(Map<String, Long> regFile, Queue<Long> in, Queue<Long> out) {
      this.regFile = regFile;
      this.in = in;
      this.out = out;
    }
  }

  interface Instr {
    MachineState exec(MachineState state);

    static Instr toInstr(String line, boolean useInOut) {
      if (line.startsWith("set")) {
        return new TwoInput(line, (x, y) -> y);
      } else if (line.startsWith("add")) {
        return new TwoInput(line, (x, y) -> x + y);
      } else if (line.startsWith("mul")) {
        return new TwoInput(line, (x, y) -> x * y);
      } else if (line.startsWith("mod")) {
        return new TwoInput(line, (x, y) -> x % y);
      } else if (line.startsWith("jgz")) {
        return new Jgz(line);
      } else if (line.startsWith("snd")) {
        return (useInOut) ? new SndOut(line) : new Snd(line);
      } else if (line.startsWith("rcv")) {
        return (useInOut) ? new RcvIn(line) : new Rcv(line);
      }
      throw new RuntimeException("bad times " + line);
    }
  }

  static class Snd implements Instr {
    final String line;
    final String rx;

    public Snd(String line) {
      this.line = line;
      this.rx = line.split(" ")[1];
    }

    @Override
    public MachineState exec(MachineState state) {
      state.regFile.put("snd", state.regFile.getOrDefault(rx, 0L));
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class SndOut implements Instr {
    final String line;
    final String rx;

    public SndOut(String line) {
      this.line = line;
      this.rx = line.split(" ")[1];
    }

    @Override
    public MachineState exec(MachineState state) {
      state.out.add(state.regFile.getOrDefault(rx, 0L));
      state.regFile.put("snd", state.regFile.getOrDefault("snd", 0L) + 1L);
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class TwoInput implements Instr {
    final String line;
    final String rx;
    final Optional<String> ry;
    final Optional<Long> immy;
    final BiFunction<Long, Long, Long> op;

    public TwoInput(String line, BiFunction<Long, Long, Long> op) {
      this.line = line;
      String[] parts = line.split(" ");
      this.rx = parts[1];
      Optional<String> lry = Optional.empty();
      Optional<Long> lvy = Optional.empty();
      try {
        lvy = Optional.of(Long.valueOf(parts[2]));
      } catch (NumberFormatException nfe) {
        lry = Optional.of(parts[2]);
      }
      immy = lvy;
      ry = lry;
      this.op = op;
      if (!(immy.isPresent() || ry.isPresent())) {
        throw new RuntimeException("bad parse " + line);
      }
    }

    @Override
    public MachineState exec(MachineState state) {
      long vy = immy.orElseGet(() -> state.regFile.getOrDefault(ry.get(), 0L));
      state.regFile.put(
          rx,
          op.apply(state.regFile.getOrDefault(rx, 0L), vy)
      );
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class Jgz implements Instr {
    final String line;
    final Optional<String> rx;
    final Optional<Long> immx;
    final Optional<String> ry;
    final Optional<Long> immy;

    public Jgz(String line) {
      this.line = line;
      String[] parts = line.split(" ");

      Optional<String> lrx = Optional.empty();
      Optional<Long> lvx = Optional.empty();
      try {
        lvx = Optional.of(Long.valueOf(parts[1]));
      } catch (NumberFormatException nfe) {
        lrx = Optional.of(parts[1]);
      }
      immx = lvx;
      rx = lrx;

      Optional<String> lry = Optional.empty();
      Optional<Long> lvy = Optional.empty();
      try {
        lvy = Optional.of(Long.valueOf(parts[2]));
      } catch (NumberFormatException nfe) {
        lry = Optional.of(parts[2]);
      }
      immy = lvy;
      ry = lry;
    }

    @Override
    public MachineState exec(MachineState state) {
      long vx = immx.orElseGet(() -> state.regFile.getOrDefault(rx.get(), 0L));
      long vy = immy.orElseGet(() -> state.regFile.getOrDefault(ry.get(), 0L));
      long ipv = (vx > 0L) ? state.regFile.get("ip") + vy - 1 : state.regFile.get("ip");
      state.regFile.put("ip", ipv);
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class Rcv implements Instr {
    final String line;
    final String rx;

    public Rcv(String line) {
      this.line = line;
      this.rx = line.split(" ")[1];
    }

    @Override
    public MachineState exec(MachineState state) {
      if (state.regFile.getOrDefault(rx, 0L) != 0L) {
        state.regFile.put("rcv", state.regFile.getOrDefault("snd", -1L));
      }
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class RcvIn implements Instr {
    final String line;
    final String rx;

    public RcvIn(String line) {
      this.line = line;
      this.rx = line.split(" ")[1];
    }

    @Override
    public MachineState exec(MachineState state) {
      if (state.in.size() > 0) {
        state.regFile.put(rx, state.in.poll());
      } else {
        state.regFile.put("ip", state.regFile.get("ip") - 1L);
      }
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  Map<String, Long> exec(List<Instr> instrs) {
    MachineState state = new MachineState(new HashMap<>(), null, null);
    state.regFile.put("ip", 0L);
    while (!state.regFile.containsKey("rcv")) {
      //System.out.println(instrs.get(state.regFile.get("ip").intValue()) + " " + state.regFile);
      instrs.get(state.regFile.get("ip").intValue()).exec(state);
      //System.out.println(state.regFile);
      state.regFile.put("ip", state.regFile.get("ip") + 1);
      //System.out.println(state.regFile);
      //System.out.println();
    }
    return state.regFile;
  }

  MachineState[] execConcurrent(List<Instr> instrs) {
    Queue<Long> m0in = new ArrayDeque<>();
    Queue<Long> m1in = new ArrayDeque<>();
    MachineState state0 = new MachineState(new HashMap<>(), m0in, m1in);
    MachineState state1 = new MachineState(new HashMap<>(), m1in, m0in);

    state0.regFile.put("p", 0L);
    state0.regFile.put("ip", 0L);

    state1.regFile.put("p", 1L);
    state1.regFile.put("ip", 0L);

    while(!(isBlocked(state0, instrs) && isBlocked(state1, instrs))) {
      step(state0, instrs);
      step(state1, instrs);
    }

    return new MachineState[]{state0, state1};
  }

  boolean isBlocked(MachineState state, List<Instr> instrs) {
    return (instrs.get(state.regFile.get("ip").intValue()) instanceof RcvIn)
        && state.in.size() == 0;
  }

  MachineState step(MachineState state, List<Instr> instrs) {
    instrs.get(state.regFile.get("ip").intValue()).exec(state);
    state.regFile.put("ip", state.regFile.get("ip") + 1);
    return state;
  }
}
