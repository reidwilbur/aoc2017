package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day18.RCV_REG;
import static com.wilb0t.aoc.Day18.SND_REG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
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
  public void testExec_input1() {
    assertThat(testInst.mulCount(input1), is(4225));
  }
}
