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
    assertThat(testInst.dance("abcde", test1), is("baedc"));
  }

  @Test
  public void testDance_input1() {
    assertThat(testInst.dance("abcdefghijklmnop", input1), is("jkmflcgpdbonihea"));
  }

  @Test
  public void testDanceItr_test1() {
    assertThat(testInst.danceItr("abcde", test1, 2), is("ceadb"));
  }

  @Test
  public void testShift() {
    Day16.Spin spin = new Day16.Spin("s7");
    assertThat(spin.apply("abcd"), is("bcda"));
  }

  @Test
  public void testDanceItr_input1() {
    assertThat(
        testInst.danceItr("abcdefghijklmnop", input1, 1000000000),
        is("ajcdefghpkblmion")
    );
  }
}
