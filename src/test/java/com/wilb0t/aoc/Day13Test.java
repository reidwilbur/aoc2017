package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day13Test {

  static int[] input1;
  static int[] test1;

  Day13 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    Map<Integer, Integer> layers =
        Files.readLines(
            new File(
                Day13Test.class.getResource("/day13.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day13::readLine)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    int maxLayer = layers.keySet().stream().mapToInt(i -> i).sum();
    input1 = new int[maxLayer];
    Arrays.fill(input1, 0);
    layers.forEach((key, value) -> input1[key] = value);

    layers =
        Files.readLines(
            new File(
                Day13Test.class.getResource("/day13-test.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day13::readLine)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    maxLayer = layers.keySet().stream().mapToInt(i -> i).sum();
    test1 = new int[maxLayer];
    Arrays.fill(test1, 0);
    layers.forEach((key, value) -> test1[key] = value);
  }

  @Before
  public void setup() {
    testInst = new Day13();
  }

  @Test
  public void testGetScanline5() {
    assertThat(testInst.getScanLine(5, 0), is(0));
    assertThat(testInst.getScanLine(5, 1), is(1));
    assertThat(testInst.getScanLine(5, 2), is(2));
    assertThat(testInst.getScanLine(5, 3), is(3));
    assertThat(testInst.getScanLine(5, 4), is(4));
    assertThat(testInst.getScanLine(5, 5), is(3));
    assertThat(testInst.getScanLine(5, 6), is(2));
    assertThat(testInst.getScanLine(5, 7), is(1));
    assertThat(testInst.getScanLine(5, 8), is(0));
    assertThat(testInst.getScanLine(5, 9), is(1));
  }

  @Test
  public void testGetScanline4() {
    assertThat(testInst.getScanLine(4, 0), is(0));
    assertThat(testInst.getScanLine(4, 1), is(1));
    assertThat(testInst.getScanLine(4, 2), is(2));
    assertThat(testInst.getScanLine(4, 3), is(3));
    assertThat(testInst.getScanLine(4, 4), is(2));
    assertThat(testInst.getScanLine(4, 5), is(1));
    assertThat(testInst.getScanLine(4, 6), is(0));
    assertThat(testInst.getScanLine(4, 7), is(1));
    assertThat(testInst.getScanLine(4, 8), is(2));
    assertThat(testInst.getScanLine(4, 9), is(3));
  }

  @Test
  public void testGetScanline2() {
    assertThat(testInst.getScanLine(2, 0), is(0));
    assertThat(testInst.getScanLine(2, 1), is(1));
    assertThat(testInst.getScanLine(2, 2), is(0));
    assertThat(testInst.getScanLine(2, 3), is(1));
    assertThat(testInst.getScanLine(2, 4), is(0));
    assertThat(testInst.getScanLine(2, 5), is(1));
    assertThat(testInst.getScanLine(2, 6), is(0));
  }

  @Test
  public void testGetScanline3() {
    assertThat(testInst.getScanLine(3, 0), is(0));
    assertThat(testInst.getScanLine(3, 1), is(1));
    assertThat(testInst.getScanLine(3, 2), is(2));
    assertThat(testInst.getScanLine(3, 3), is(1));
    assertThat(testInst.getScanLine(3, 4), is(0));
    assertThat(testInst.getScanLine(3, 5), is(1));
    assertThat(testInst.getScanLine(3, 6), is(2));
  }

  @Test
  public void testGetSeverity_test1() {
    assertThat(testInst.getSeverity(0,0, test1), is(24));
  }

  @Test
  public void testGetSeverity_input1() {
    assertThat(testInst.getSeverity(0, 0, input1), is(1876));
  }

  @Test
  public void testGetDelay_test1() {
    assertThat(testInst.findLowestDelay(test1), is(10));
  }

  //this takes 6s to run
  //@Test
  public void testGetDelay_input1() {
    assertThat(testInst.findLowestDelay(input1), is(3964778));
  }
}
