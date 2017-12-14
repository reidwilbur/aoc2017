package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Day14Test {

  static String input1 = "hwlqcszp";
  static String test1 = "flqrgnkx";

  Day14 testInst;
  Day10 hasher;
  Day12 conComps;

  @Before
  public void setup() {
    hasher = new Day10();
    conComps = new Day12();
    testInst = new Day14(
        hasher::knotHash,
        conComps::connectedComponents
    );
  }

  @Test
  public void testGetUsedCount_test1() {
    assertThat(testInst.getUsedCount(test1), is(8108));
  }

  @Test
  public void testGetUsedCount_input1() {
    assertThat(testInst.getUsedCount(input1), is(8304));
  }

  @Test
  public void testGetUsedRegionCount_test1() {
    assertThat(testInst.getUsedRegionCount(test1), is(1242));
  }

  @Test
  public void testGetUsedRegionCount_input1() {
    assertThat(testInst.getUsedRegionCount(input1), is(1018));
  }

  @Test
  public void testHashToBinString() {
    assertThat(
        testInst.hashToBinString("0123456789abcdef"),
        is("0000000100100011010001010110011110001001101010111100110111101111")
    );
  }

  @Test
  public void binStringToAdjList_case1() {
    String binString =
          "10001"
        + "10011"
        + "01000";
    assertThat(testInst.binStringToAdjList(binString, 5), is(
        new ImmutableList.Builder<List<Integer>>()
            .add(ImmutableList.of(0, 5))
            .add(ImmutableList.of())
            .add(ImmutableList.of())
            .add(ImmutableList.of())
            .add(ImmutableList.of(4, 9))
            .add(ImmutableList.of(5, 0))
            .add(ImmutableList.of())
            .add(ImmutableList.of())
            .add(ImmutableList.of(8, 9))
            .add(ImmutableList.of(9, 4, 8))
            .add(ImmutableList.of())
            .add(ImmutableList.of(11))
            .add(ImmutableList.of())
            .add(ImmutableList.of())
            .add(ImmutableList.of())
            .build()
    ));
  }
}
