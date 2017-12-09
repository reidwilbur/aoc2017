package com.wilb0t.aoc;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day8 {

  static final BiFunction<Integer, Integer, Integer> INC_OP = (val, adj) -> val + adj;
  static final BiFunction<Integer, Integer, Integer> DEC_OP = (val, adj) -> val - adj;

  static final BiPredicate<Integer, Integer> GT  = (left, right) -> left > right;
  static final BiPredicate<Integer, Integer> GTE = (left, right) -> left >= right;
  static final BiPredicate<Integer, Integer> LT  = (left, right) -> left < right;
  static final BiPredicate<Integer, Integer> LTE = (left, right) -> left <= right;
  static final BiPredicate<Integer, Integer> EQ  = Integer::equals;
  static final BiPredicate<Integer, Integer> NEQ = (left, right) -> !left.equals(right);

  static class Instruction {
    public final String reg;
    public final Function<Integer, Integer> op;
    public final int adj;
    public final String creg;
    public final Predicate<Integer> cond;
    public final int amt;

    public Instruction(String line) {
      String[] parts = line.split("\\s+");
      reg = parts[0];
      adj = Integer.parseInt(parts[2]);
      op = parts[1].equals("inc")
           ? (val) -> INC_OP.apply(val, adj)
           : (val) -> DEC_OP.apply(val, adj);
      creg = parts[4];
      amt = Integer.parseInt(parts[6]);
      switch(parts[5]) {
        case ">":  cond = (val) -> GT.test(val, amt);  break;
        case ">=": cond = (val) -> GTE.test(val, amt); break;
        case "<":  cond = (val) -> LT.test(val, amt);  break;
        case "<=": cond = (val) -> LTE.test(val, amt); break;
        case "==": cond = (val) -> EQ.test(val, amt);  break;
        case "!=": cond = (val) -> NEQ.test(val, amt); break;
        default: throw new RuntimeException("bad instr");
      }
    }
  }

  int getLargestFinalValue(List<Instruction> instrs) {
    Map<String, Integer> regFile = Maps.newHashMap();

    instrs.forEach(instr -> {
      int cval = regFile.getOrDefault(instr.creg, 0);
      if (instr.cond.test(cval)) {
        int rval = regFile.getOrDefault(instr.reg, 0);
        regFile.put(instr.reg, instr.op.apply(rval));
      }
    });

    return regFile.values().stream().sorted(Comparator.reverseOrder()).findFirst().get();
  }

  int getLargestExecValue(List<Instruction> instrs) {
    Map<String, Integer> regFile = Maps.newHashMap();

    int largestVal = 0;

    for (Instruction instr : instrs) {
      int cval = regFile.getOrDefault(instr.creg, 0);
      if (instr.cond.test(cval)) {
        int rval = regFile.getOrDefault(instr.reg, 0);
        regFile.put(instr.reg, instr.op.apply(rval));
        largestVal = rval > largestVal ? rval : largestVal;
      }
    }

    return largestVal;
  }
}
