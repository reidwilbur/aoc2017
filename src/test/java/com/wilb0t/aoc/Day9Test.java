package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day9Test {

  static String input1;

  Day9 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 =
        Files.readFirstLine(
            new File(
                Day9Test.class.getResource("/day9.txt").toURI()
            ),
            Charsets.UTF_8
        );
  }

  @Before
  public void setup() {
    testInst = new Day9();
  }

  @Test
  public void testGetTotalGroupScore_case1() {
    assertThat(testInst.process("{}".chars()).getKey(), is(1));
  }

  @Test
  public void testGetTotalGroupScore_case2() {
    assertThat(testInst.process("{{{}}}".chars()).getKey(), is(6));
  }

  @Test
  public void testGetTotalGroupScore_case3() {
    assertThat(testInst.process("{{},{}}".chars()).getKey(), is(5));
  }

  @Test
  public void testGetTotalGroupScore_case4() {
    assertThat(testInst.process("{{{},{},{{}}}}".chars()).getKey(), is(16));
  }

  @Test
  public void testGetTotalGroupScore_case5() {
    assertThat(testInst.process("{<a>,<a>,<a>,<a>}".chars()).getKey(), is(1));
  }

  @Test
  public void testGetTotalGroupScore_case6() {
    assertThat(testInst.process("{{<ab>},{<ab>},{<ab>},{<ab>}}".chars()).getKey(), is(9));
  }

  @Test
  public void testGetTotalGroupScore_case7() {
    assertThat(testInst.process("{{<!!>},{<!!>},{<!!>},{<!!>}}}".chars()).getKey(), is(9));
  }

  @Test
  public void testGetTotalGroupScore_case8() {
    assertThat(testInst.process("{{<a!>},{<a!>},{<a!>},{<ab>}}".chars()).getKey(), is(3));
  }

  @Test
  public void testGetTotalGroupScore_input1() {
    assertThat(testInst.process(input1.chars()).getKey(), is(12505));
  }

  @Test
  public void testGetGarbageCount() {
    assertThat(testInst.process(input1.chars()).getValue(), is(6671));
  }
}
