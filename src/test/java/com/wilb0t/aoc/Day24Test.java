package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day24Test {

  static List<Day24.Comp> input1;
  static List<Day24.Comp> test1;

  Day24 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day24Test.class.getResource("/day24.txt").toURI()),
        Charsets.UTF_8
    ).stream()
        .map(Day24.Comp::new)
        .collect(Collectors.toList());

    test1 = Files.readLines(
        new File(Day24Test.class.getResource("/day24-test.txt").toURI()),
        Charsets.UTF_8
    ).stream()
        .map(Day24.Comp::new)
        .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day24();
  }

  @Test
  public void testGetStrongestBridge_test1() {
    assertThat(testInst.getStrongestBridge(test1), is(31));
  }

  @Test
  public void testGetStrongestBridge_input1() {
    assertThat(testInst.getStrongestBridge(input1), is(1906));
  }

  @Test
  public void testGetStrongestLongestBridge_test1() {
    assertThat(testInst.getStrongestLongestBridge(test1), is(19));
  }

  @Test
  public void testGetStrongestLongestBridge_input1() {
    assertThat(testInst.getStrongestLongestBridge(input1), is(1824));
  }
}
