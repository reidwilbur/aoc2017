package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day5Test {

  Day5 testInst;
  static List<Integer> input1;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 =
        Files.readLines(
            new File(
                Day5Test.class.getResource("/day5.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());
  }

  @Before
  public void setup() throws Exception {
    testInst = new Day5();
  }

  @Test
  public void testCountJumps_case1() {
    assertThat(testInst.countJumps(ImmutableList.of(0,3,0,1,-3)), is(5));
  }

  @Test
  public void testCountJumps_input1() {
    assertThat(testInst.countJumps(input1), is(336905));
  }

  @Test
  public void testCountJumps2_case1() {
    assertThat(testInst.countJumps2(ImmutableList.of(0,3,0,1,-3)), is(10));
  }

  @Test
  public void testCountJumps2_input1() {
    assertThat(testInst.countJumps2(input1), is(21985262));
  }
}
