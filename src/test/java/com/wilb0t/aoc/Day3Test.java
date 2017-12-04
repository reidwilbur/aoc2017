package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class Day3Test {

  private Day3 testInst;

  @Before
  public void setup() {
    testInst = new Day3();
  }

  @Test
  public void testGetSteps_addres1() {
    assertThat(
        testInst.getSteps(1),
        is(0)
    );
  }

  @Test
  public void testGetSteps_address2() {
    assertThat(
        testInst.getSteps(2),
        is(1)
    );
  }

  @Test
  public void testGetSteps_address3() {
    assertThat(
        testInst.getSteps(3),
        is(2)
    );
  }

  @Test
  public void testGetSteps_address8() {
    assertThat(
        testInst.getSteps(8),
        is(1)
    );
  }

  @Test
  public void testGetSteps_address9() {
    assertThat(
        testInst.getSteps(9),
        is(2)
    );
  }

  @Test
  public void testGetSteps_address10() {
    assertThat(
        testInst.getSteps(10),
        is(3)
    );
  }

  @Test
  public void testGetSteps_address11() {
    assertThat(
        testInst.getSteps(11),
        is(2)
    );
  }

  @Test
  public void testGetSteps_address12() {
    assertThat(
        testInst.getSteps(12),
        is(3)
    );
  }

  @Test
  public void testGetSteps_address23() {
    assertThat(
        testInst.getSteps(23),
        is(2)
    );
  }

  @Test
  public void testGetSteps_address25() {
    assertThat(
        testInst.getSteps(25),
        is(4)
    );
  }

  @Test
  public void testGetSteps_address26() {
    assertThat(
        testInst.getSteps(26),
        is(5)
    );
  }

  @Test
  public void testGetSteps_address27() {
    assertThat(
        testInst.getSteps(27),
        is(4)
    );
  }

  @Test
  public void testGetSteps_address28() {
    assertThat(
        testInst.getSteps(28),
        is(3)
    );
  }

  @Test
  public void testGetSteps_address29() {
    assertThat(
        testInst.getSteps(29),
        is(4)
    );
  }

  @Test
  public void testGetSteps_address30() {
    assertThat(
        testInst.getSteps(30),
        is(5)
    );
  }

  @Test
  public void testGetSteps_address48() {
    assertThat(
        testInst.getSteps(48),
        is(5)
    );
  }

  @Test
  public void testGetSteps_address49() {
    assertThat(
        testInst.getSteps(49),
        is(6)
    );
  }

  @Test
  public void testGetSteps_address50() {
    assertThat(
        testInst.getSteps(50),
        is(7)
    );
  }

  @Test
  public void testGetSteps_address1024() {
    assertThat(
        testInst.getSteps(1024),
        is(31)
    );
  }

  @Test
  public void testGetSteps_input1() {
    assertThat(
        testInst.getSteps(289326),
        is(419)
    );
  }

  @Test
  public void testStressTest_case1() {
    assertThat(
        testInst.stressTest(i -> i > 1),
        is(2)
    );
  }

  @Test
  public void testStressTest_case2() {
    assertThat(
        testInst.stressTest(i -> i > 2),
        is(4)
    );
  }

  @Test
  public void testStressTest_case3() {
    assertThat(
        testInst.stressTest(i -> i > 4),
        is(5)
    );
  }

  @Test
  public void testStressTest_case4() {
    assertThat(
        testInst.stressTest(i -> i > 5),
        is(10)
    );
  }

  @Test
  public void testStressTest_case5() {
    assertThat(
        testInst.stressTest(i -> i > 10),
        is(11)
    );
  }

  @Test
  public void testStressTest_case6() {
    assertThat(
        testInst.stressTest(i -> i > 747),
        is(806)
    );
  }

  @Test
  public void testStressTest_input1() {
    assertThat(
        testInst.stressTest(i -> i > 289326),
        is(295229)
    );
  }
}
