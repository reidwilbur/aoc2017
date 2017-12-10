package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class Day10Test {

  static String input1 = "97,167,54,178,2,11,209,174,119,248,254,0,255,1,64,190";

  static List<Integer> input1Lengths =
      Stream.of(input1.split(","))
          .map(Integer::parseInt).collect(Collectors.toList());

  static List<Integer> input1Values = IntStream.range(0, 256).boxed().collect(Collectors.toList());

  static List<Integer> testLengths = ImmutableList.of(3,4,1,5);

  static List<Integer> testValues = ImmutableList.of(0,1,2,3,4);

  Day10 testInst;

  @Before
  public void setup() {
    testInst = new Day10();
  }

  @Test
  public void testKnotHashRound_testInputs() {
    assertThat(testInst.knotHashRound(testValues, testLengths), is(12));
  }

  @Test
  public void testKnotHashRound_input1() {
    // not 1771 -- too low
    assertThat(testInst.knotHashRound(input1Values, input1Lengths), is(8536));
  }

  @Test
  public void testKnotHash_case1() {
    assertThat(testInst.knotHash(""), is("a2582a3a0e66e6e86e3812dcb672a272"));
  }

  @Test
  public void testKnotHash_case2() {
    assertThat(testInst.knotHash("AoC 2017"), is("33efeb34ea91902bb2f59c9920caa6cd"));
  }

  @Test
  public void testKnotHash_case3() {
    assertThat(testInst.knotHash("1,2,3"), is("3efbe78a8d82f29979031a4aa0b16a9d"));
  }

  @Test
  public void testKnotHash_case4() {
    assertThat(testInst.knotHash("1,2,4"), is("63960835bcdc130f0b66d7ff4f6a5a8e"));
  }

  @Test
  public void testKnotHash_input1() {
    assertThat(testInst.knotHash(input1), is("aff593797989d665349efe11bb4fd99b"));
  }
}
