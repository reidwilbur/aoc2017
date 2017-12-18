package com.wilb0t.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class Day18 {

  interface Instr {
    Map<String, Long> exec(Map<String, Long> regFile);

    static Instr toInstr(String line) {
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
        return new Snd(line);
      } else if (line.startsWith("rcv")) {
        return new Rcv(line);
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
    public Map<String, Long> exec(Map<String, Long> regFile) {
      regFile.put("snd", regFile.getOrDefault(rx, 0L));
      return regFile;
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
    public Map<String, Long> exec(Map<String, Long> regFile) {
      long vy = immy.orElseGet(() -> regFile.getOrDefault(ry.get(), 0L));
      regFile.put(
          rx,
          op.apply(regFile.getOrDefault(rx, 0L), vy)
      );
      return regFile;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  static class Jgz implements  Instr {
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
    public Map<String, Long> exec(Map<String, Long> regFile) {
      long vx = immx.orElseGet(() -> regFile.getOrDefault(rx.get(), 0L));
      long vy = immy.orElseGet(() -> regFile.getOrDefault(ry.get(), 0L));
      long ipv = (vx > 0L) ? regFile.get("ip") + vy - 1 : regFile.get("ip");
      regFile.put("ip", ipv);
      return regFile;
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
    public Map<String, Long> exec(Map<String, Long> regFile) {
      if (regFile.getOrDefault(rx, 0L) != 0L) {
        regFile.put("rcv", regFile.getOrDefault("snd", -1L));
      }
      return regFile;
    }

    @Override
    public String toString() {
      return line;
    }
  }

  Map<String, Long> exec(List<Instr> instrs) {
    Map<String, Long> regFile = new HashMap<>();
    regFile.put("ip", 0L);
    while (!regFile.containsKey("rcv")) {
      System.out.println(instrs.get(regFile.get("ip").intValue()) + " " + regFile);
      instrs.get(regFile.get("ip").intValue()).exec(regFile);
      System.out.println(regFile);
      regFile.put("ip", regFile.get("ip") + 1);
      System.out.println(regFile);
      System.out.println();
    }
    return regFile;
  }
}
