package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Day15Test {

  Day15 testInst;

  @Before
  public void setup() {
    testInst = new Day15();
  }

  @Test
  public void testCountMatches_test1() {
    assertThat(testInst.countMatches(65, 8921, 5), is(1));
  }

  @Test
  public void testCountMatches_test2() {
    assertThat(testInst.countMatches(65, 8921, 40000000), is(588));
  }

  @Test
  public void testCountMatches_input1() {
    assertThat(testInst.countMatches(699, 124, 40000000), is(600));
  }

  @Test
  public void testCountMatches2_test1() {
    assertThat(testInst.countMatches2(65, 8921, 1056), is(1));
  }

  @Test
  public void testCountMatches2_test2() {
    assertThat(testInst.countMatches2(65, 8921, 5000000), is(309));
  }

  @Test
  public void testCountMatches2_input1() {
    assertThat(testInst.countMatches2(699, 124, 5000000), is(313));
  }
}
