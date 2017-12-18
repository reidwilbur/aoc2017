package com.wilb0t.aoc;

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

  Day18 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.lines(new File(Day11Test.class.getResource("/day18.txt").toURI()).toPath())
        .map(Day18.Instr::toInstr)
        .collect(Collectors.toList());

    test1 = Files.lines(new File(Day11Test.class.getResource("/day18-test.txt").toURI()).toPath())
        .map(Day18.Instr::toInstr)
        .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day18();
  }

  @Test
  public void testExec_test1() {
    Map<String, Long> regFile = testInst.exec(test1);
    assertThat(regFile.get("rcv"), is(4L));
  }

  @Test
  public void testExec_input1() {
    Map<String, Long> regFile = testInst.exec(input1);
    assertThat(regFile.get("rcv"), is(9423L));
  }
}
