package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Day6Test {

  static final List<Integer> input1 = ImmutableList.of(2,8,8,5,4,2,3,1,5,5,1,2,15,13,5,14);

  Day6 testInst;

  @Before
  public void setup() {
    testInst = new Day6();
  }

  @Test
  public void testDistinctRedist_case1() {
    assertThat(testInst.distinctRedists(Arrays.asList(0, 2, 7, 0)).getKey(), is(5));
  }

  @Test
  public void testDistinctRedist_input1() {
    assertThat(testInst.distinctRedists(input1).getKey(), is(3156));
  }

  @Test
  public void testDistinctRedist2_case1() {
    assertThat(testInst.distinctRedists(Arrays.asList(0, 2, 7, 0)).getValue(), is(4));
  }

  @Test
  public void testDistinctRedist2_input1() {
    assertThat(testInst.distinctRedists(input1).getValue(), is(1610));
  }
}
