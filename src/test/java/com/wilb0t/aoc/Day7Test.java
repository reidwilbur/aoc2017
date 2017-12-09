package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day7Test {

  static List<Day7.Node> input1;
  static List<Day7.Node> test1;

  Day7 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 =
        Files.readLines(
            new File(
                Day7Test.class.getResource("/day7.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day7.Node::new)
            .collect(Collectors.toList());

    test1 =
        Files.readLines(
            new File(
                Day7Test.class.getResource("/day7-test.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day7.Node::new)
            .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day7();
  }

  @Test
  public void testFindRoot_test1() {
    assertThat(testInst.findRoot(test1), is("tknk"));
  }

  @Test
  public void testFindRoot_input1() {
    assertThat(testInst.findRoot(input1), is("vvsvez"));
  }

  @Test
  public void testFixWeight_test1() {
    assertThat(testInst.fixWeight("tknk", test1), is(60));
  }

  @Test
  public void testFixWeight_input1() {
    assertThat(testInst.fixWeight("vvsvez", input1), is(362));
  }
}
