package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day18.RCV_REG;
import static com.wilb0t.aoc.Day18.SND_REG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day18Test {

  static List<Day18.Instr> input1;
  static List<Day18.Instr> test1;

  static List<Day18.Instr> input2;
  static List<Day18.Instr> test2;

  Day18 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.lines(new File(Day11Test.class.getResource("/day18.txt").toURI()).toPath())
        .map(l -> Day18.Instr.toInstr(l, false))
        .collect(Collectors.toList());

    test1 = Files.lines(new File(Day11Test.class.getResource("/day18-test.txt").toURI()).toPath())
        .map(l -> Day18.Instr.toInstr(l, false))
        .collect(Collectors.toList());

    input2 = Files.lines(new File(Day11Test.class.getResource("/day18.txt").toURI()).toPath())
        .map(l -> Day18.Instr.toInstr(l, true))
        .collect(Collectors.toList());

    test2 = Files.lines(new File(Day11Test.class.getResource("/day18-test2.txt").toURI()).toPath())
        .map(l -> Day18.Instr.toInstr(l, true))
        .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day18();
  }

  @Test
  public void testExec_test1() {
    Map<String, Long> regFile = testInst.exec(test1);
    assertThat(regFile.get(RCV_REG), is(4L));
  }

  @Test
  public void testExec_input1() {
    Map<String, Long> regFile = testInst.exec(input1);
    assertThat(regFile.get(RCV_REG), is(9423L));
  }

  @Test
  public void testExecConc_test2() {
    Day18.MachineState[] states = testInst.execConcurrent(test2);
    assertThat(states[0].regFile.get(SND_REG), is(3L));
  }

  @Test
  public void testExecConc_input2() {
    Day18.MachineState[] states = testInst.execConcurrent(input2);
    assertThat(states[1].regFile.get(SND_REG), is(7620L));
  }
}
