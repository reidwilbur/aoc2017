package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class Day17Test {

  private int input1 = 345;
  private int test1 = 3;

  private Day17 testInst;

  @Before
  public void setup() {
    testInst = new Day17();
  }

  @Test
  public void testSpin_test1() {
    assertThat(
        testInst.spin(Lists.newArrayList(0), 3, test1),
        is(new AbstractMap.SimpleEntry<>(1, ImmutableList.of(0, 2, 3, 1)))
    );
  }

  @Test
  public void testSpin_test1_case2() {
    Map.Entry<Integer, List<Integer>> result =
        testInst.spin(Lists.newArrayList(0), 2017, test1);
    assertThat(
        result.getKey(),
        is(638)
    );
  }

  @Test
  public void testSpin_input1() {
    assertThat(
        testInst.spin(Lists.newArrayList(0), 2017, input1).getKey(),
        is(866)
    );
  }

  @Test
  public void testGetIdx1() {
    assertThat(
        testInst.getIdx1Val(2017, test1),
        is(1226)
    );
  }

  @Test
  public void testGetIdx1_input1() {
    assertThat(
        testInst.getIdx1Val(50000000, input1),
        is(11995607)
    );
  }
}
