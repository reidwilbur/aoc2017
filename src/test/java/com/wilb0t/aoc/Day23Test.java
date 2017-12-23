package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day23Test {

  static List<Day23.Instr> input1;

  Day23 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.lines(new File(Day11Test.class.getResource("/day23.txt").toURI()).toPath())
        .map(Day23.Instr::toInstr)
        .collect(Collectors.toList());
  }

  @Before
  public void setup(){
    testInst = new Day23();
  }

  @Test
  public void testMulCount_input1() {
    assertThat(testInst.mulCount(input1), is(4225));
  }

  @Test
  public void testPart2_aIsZero() {
    Day23.MachineState state = new Day23.MachineState(0L);
    testInst.exec(input1, state);
    long h = state.regFile.get("h");
    assertThat(testInst.part2(0), is(h));
  }

  @Test
  public void testPart2_aIsOne() {
    assertThat(testInst.part2(1), is(905L));
  }
}
