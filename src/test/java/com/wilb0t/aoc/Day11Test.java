package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day11Test {

  static List<String> input1;

  Day11 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    Scanner s = new Scanner(new File(Day11Test.class.getResource("/day11.txt").toURI()))
        .useDelimiter(",");

    ImmutableList.Builder<String> bldr = ImmutableList.builder();
    while (s.hasNext()) {
      bldr.add(s.next());
    }
    input1 = bldr.build();
  }

  @Before
  public void setup() {
    testInst = new Day11();
  }

  @Test
  public void testGetShortestDist_case1 () {
    assertThat(testInst.getShortestDist(ImmutableList.of("ne", "ne", "ne")), is(3));
  }

  @Test
  public void testGetShortestDist_case2 () {
    assertThat(testInst.getShortestDist(ImmutableList.of("ne", "ne", "sw", "sw")), is(0));
  }

  @Test
  public void testGetShortestDist_case3 () {
    assertThat(testInst.getShortestDist(ImmutableList.of("ne", "ne", "s", "s")), is(2));
  }

  @Test
  public void testGetShortestDist_case4 () {
    assertThat(testInst.getShortestDist(ImmutableList.of("se", "sw", "se", "sw", "sw")), is(3));
  }

  @Test
  public void testGetShortestDist_input1() {
    assertThat(testInst.getShortestDist(input1), is(805));
  }

  @Test
  public void testGetMaxDist_input1() {
    assertThat(testInst.getMaxDist(input1), is(1535));
  }
}
