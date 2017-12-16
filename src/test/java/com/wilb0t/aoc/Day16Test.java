package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day16Test {

  static List<Day16.Move> input1;
  static List<Day16.Move> test1 = ImmutableList.of(
      Day16.Move.parse("s1"),
      Day16.Move.parse("x3/4"),
      Day16.Move.parse("pe/b")
  );

  Day16 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    Scanner s = new Scanner(new File(Day11Test.class.getResource("/day16.txt").toURI()))
        .useDelimiter(",");

    ImmutableList.Builder<Day16.Move> bldr = ImmutableList.builder();
    while (s.hasNext()) {
      bldr.add(Day16.Move.parse(s.next()));
    }
    input1 = bldr.build();
  }

  @Before
  public void setup() {
    testInst = new Day16();
  }

  @Test
  public void testDance_test1() {
    char[] init = "abcde".toCharArray();
    testInst.dance(init, test1);
    assertThat(init, is("baedc".toCharArray()));
  }

  @Test
  public void testDance_input1() {
    char[] init = "abcdefghijklmnop".toCharArray();
    testInst.dance(init, input1);
    assertThat(init, is("jkmflcgpdbonihea".toCharArray()));
  }

  @Test
  public void testDanceItr_test1() {
    char[] init = "abcde".toCharArray();
    testInst.danceItr(init, test1, 2);
    assertThat(init, is("ceadb".toCharArray()));
  }

  @Test
  public void testShift() {
    Day16.Spin spin = new Day16.Spin("s7");
    char[] s = "abcd".toCharArray();
    spin.apply(s);
    assertThat(s, is("bcda".toCharArray()));
  }

  // takes over 1 minute to run
  //@Test
  public void testDanceItr_input1() {
    char[] init = "abcdefghijklmnop".toCharArray();
    assertThat(testInst.danceItr(init, input1, 1000000000), is("ajcdefghpkblmion".toCharArray()));
  }
}
