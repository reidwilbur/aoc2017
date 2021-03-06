package com.wilb0t.aoc;

import com.google.common.math.LongMath;
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

    public MachineState(long a) {
      this.regFile = new HashMap<>();
      regFile.put(IP, 0L);
      IntStream.range('a', 'i').forEach(i -> regFile.put(String.valueOf((char)i), 0L));
      regFile.put("a", a);
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

  public long exec(List<Instr> instrs, MachineState state) {
    while (state.regFile.get(IP) < instrs.size()) {
      step(state, instrs);
    }
    return state.regFile.get("h");
  }

  MachineState step(MachineState state, List<Instr> instrs) {
    instrs.get(state.regFile.get(IP).intValue()).exec(state);
    state.regFile.compute(IP, (reg, val) -> val + 1);
    return state;
  }

  public long part2(long aval) {
    long a, b, c, d, e, f, g, h = 0;

    a = aval;

    b = 67;
    c = b;

    if (a != 0) {
      b *= 100;
      b += 100000;
      c = b;
      c += 17000;
    }

    do {
      // impls below were doing a really slow prime check on the value of b
      // using guava fast prime check impl instead
      f = LongMath.isPrime(b) ? 1 : 0;

      // converted the flat code to for loops to make more sense
      //for(long didx = 2; didx != b; didx++) {
      //  for (long eidx = 2; eidx != b; eidx++) {
      //    if ((didx * eidx) == b) {
      //      f = 0;
      //    }
      //  }
      //}

      // this was my initial txlation of the assembly to java
      //d = 2;
      //do {
      //  //e = 2;
      //  //do {
      //  //  //g = d;
      //  //  //g *= e;
      //  //  //g -= b;
      //  //  //
      //  //  //if (g == 0) {
      //  //  //  f = 0;
      //  //  //}
      //  //  if ((d * e) == b) {
      //  //    f = 0;
      //  //  }
      //  //  e += 1;
      //  //  //g = e;
      //  //  //g -= b;
      //  //  //} while(g != 0);
      //  //} while (e != b);
      //  d += 1;
      //  //  g = d;
      //  //  g -= b;
      //  //} while (g != 0);
      //} while (d != b);

      if (f == 0) {
        h += 1;
      }
      //g = b;
      //g -= c;
      if (b == c) {
        return h;
      }
      b += 17;
    } while(true);
  }
}
