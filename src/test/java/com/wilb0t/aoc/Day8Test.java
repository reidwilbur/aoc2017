package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day8Test {

  static List<Day8.Instruction> input1;
  static List<Day8.Instruction> test1;

  Day8 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 =
        Files.readLines(
            new File(
                Day8Test.class.getResource("/day8.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day8.Instruction::new)
            .collect(Collectors.toList());

    test1 =
        Files.readLines(
            new File(
                Day8Test.class.getResource("/day8-test.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day8.Instruction::new)
            .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day8();
  }

  @Test
  public void testGetLargestFinalValue_test1() {
    assertThat(testInst.getLargestFinalValue(test1), is(1));
  }

  @Test
  public void testGetLargestFinalValue_input1() {
    assertThat(testInst.getLargestFinalValue(input1), is(5946));
  }

  @Test
  public void testGetLargestExecValue_test1() {
    assertThat(testInst.getLargestExecValue(test1), is(10));
  }

  @Test
  public void testGetLargestExecValue_input1() {
    assertThat(testInst.getLargestExecValue(input1), is(6026));
  }
}
