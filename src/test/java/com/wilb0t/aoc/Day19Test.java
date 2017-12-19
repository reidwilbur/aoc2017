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

public class Day19Test {

  static List<String> input1;
  static List<String> test1;

  Day19 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.lines(new File(Day11Test.class.getResource("/day19.txt").toURI()).toPath())
        .collect(Collectors.toList());

    test1 = Files.lines(new File(Day11Test.class.getResource("/day19-test.txt").toURI()).toPath())
        .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day19();
  }

  @Test
  public void testGetLetters_test1() {
    assertThat(testInst.getLetters(test1).getKey(), is("ABCDEF"));
  }

  @Test
  public void testGetLetters_input1() {
    assertThat(testInst.getLetters(input1).getKey(), is("FEZDNIVJWT"));
  }

  @Test
  public void testGetLetters2_test1() {
    assertThat(testInst.getLetters(test1).getValue(), is(38));
  }

  @Test
  public void testGetLetters2_input1() {
    assertThat(testInst.getLetters(input1).getValue(), is(17200));
  }
}
