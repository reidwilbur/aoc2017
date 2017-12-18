package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiFunction;

public class Day18 {

  public static final String IP = "ip";
  public static final String SND_REG = "snd";
  public static final String RCV_REG = "rcv";
  public static final String PID_REG = "p";

  static class MachineState {
    final Map<String, Long> regFile;
    final Queue<Long> in;
    final Queue<Long> out;

    public MachineState(Queue<Long> in, Queue<Long> out) {
      this.regFile = new HashMap<>();
      this.in = in;
      this.out = out;
      regFile.put(IP, 0L);
    }

    public Long getVal(String reg) {
      return regFile.getOrDefault(reg, 0L);
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

  static class ImmReg {
    final boolean isImm;
    final long imm;
    final String reg;

    public ImmReg(String operand) {
      boolean lisimm = false;
      long limm = Long.MIN_VALUE;
      String lreg = null;
      try {
        limm = Long.parseLong(operand);
        lisimm = true;
      } catch (NumberFormatException e) {
        lreg = operand;
        lisimm = false;
      }
      isImm = lisimm;
      imm = limm;
      reg = lreg;
    }

    public long getVal(MachineState state) {
      return isImm ? imm : state.getVal(reg);
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
      state.regFile.put(SND_REG, state.getVal(rx));
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
      state.out.add(state.getVal(rx));
      state.regFile.put(SND_REG, state.getVal(SND_REG) + 1L);
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
    final ImmReg y;
    final BiFunction<Long, Long, Long> op;

    public TwoInput(String line, BiFunction<Long, Long, Long> op) {
      this.line = line;
      String[] parts = line.split(" ");
      rx = parts[1];
      y = new ImmReg(parts[2]);
      this.op = op;
    }

    @Override
    public MachineState exec(MachineState state) {
      long vy = y.getVal(state);
      state.regFile.put(rx, op.apply(state.getVal(rx), vy));
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class Jgz implements Instr {
    final String line;
    final ImmReg x;
    final ImmReg y;

    public Jgz(String line) {
      this.line = line;
      String[] parts = line.split(" ");
      x = new ImmReg(parts[1]);
      y = new ImmReg(parts[2]);
    }

    @Override
    public MachineState exec(MachineState state) {
      long vx = x.getVal(state);
      long vy = y.getVal(state);
      long ipv = (vx > 0L) ? state.regFile.get(IP) + vy - 1 : state.regFile.get(IP);
      state.regFile.put(IP, ipv);
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
        state.regFile.put(RCV_REG, state.getVal(SND_REG));
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
        state.regFile.put(IP, state.regFile.get(IP) - 1L);
      }
      return state;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  Map<String, Long> exec(List<Instr> instrs) {
    MachineState state = new MachineState(null, null);
    while (!state.regFile.containsKey(RCV_REG)) {
      step(state, instrs);
    }
    return state.regFile;
  }

  MachineState[] execConcurrent(List<Instr> instrs) {
    Queue<Long> m0in = new ArrayDeque<>();
    Queue<Long> m1in = new ArrayDeque<>();
    MachineState state0 = new MachineState(m0in, m1in);
    MachineState state1 = new MachineState(m1in, m0in);

    state0.regFile.put(PID_REG, 0L);
    state1.regFile.put(PID_REG, 1L);

    while(!(isBlocked(state0, instrs) && isBlocked(state1, instrs))) {
      step(state0, instrs);
      step(state1, instrs);
    }

    return new MachineState[]{state0, state1};
  }

  boolean isBlocked(MachineState state, List<Instr> instrs) {
    return (instrs.get(state.regFile.get(IP).intValue()) instanceof RcvIn)
        && state.in.size() == 0;
  }

  MachineState step(MachineState state, List<Instr> instrs) {
    instrs.get(state.regFile.get(IP).intValue()).exec(state);
    state.regFile.compute(IP, (reg, val) -> val + 1);
    return state;
  }
}
