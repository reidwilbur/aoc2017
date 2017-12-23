package com.wilb0t.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Day23 {

  public static final String IP = "ip";

  static class MachineState {
    final Map<String, Long> regFile;

    public MachineState() {
      this.regFile = new HashMap<>();
      regFile.put(IP, 0L);
      IntStream.range('a', 'i').forEach(i -> regFile.put(String.valueOf((char)i), 0L));
    }

    public Long getVal(String reg) {
      return regFile.get(reg);
    }
  }

  interface Instr {
    MachineState exec(MachineState state);

    boolean isMul();

    static Instr toInstr(String line) {
      if (line.startsWith("set")) {
        return new TwoInput(line, (x, y) -> y);

      } else if (line.startsWith("sub")) {
        return new TwoInput(line, (x, y) -> x - y);

      } else if (line.startsWith("mul")) {
        return new TwoInput(line, (x, y) -> x * y);

      } else if (line.startsWith("jnz")) {
        return new Jnz(line);

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

    @Override
    public boolean isMul() {
      return line.startsWith("mul");
    }
  }

  static class Jnz implements Instr {
    final String line;
    final ImmReg x;
    final ImmReg y;

    public Jnz(String line) {
      this.line = line;
      String[] parts = line.split(" ");
      x = new ImmReg(parts[1]);
      y = new ImmReg(parts[2]);
    }

    @Override
    public MachineState exec(MachineState state) {
      long vx = x.getVal(state);
      long vy = y.getVal(state);
      long ipv = (vx != 0L) ? state.regFile.get(IP) + vy - 1 : state.regFile.get(IP);
      state.regFile.put(IP, ipv);
      return state;
    }

    @Override
    public String toString() {
      return line;
    }

    @Override
    public boolean isMul() {
      return false;
    }
  }

  public int mulCount(List<Instr> instrs) {
    int count = 0;
    MachineState state = new MachineState();
    while (state.regFile.get(IP) < instrs.size()) {
      count += (instrs.get(state.regFile.get(IP).intValue()).isMul()) ? 1 : 0;
      step(state, instrs);
    }
    return count;
  }

  MachineState step(MachineState state, List<Instr> instrs) {
    instrs.get(state.regFile.get(IP).intValue()).exec(state);
    state.regFile.compute(IP, (reg, val) -> val + 1);
    return state;
  }
}
