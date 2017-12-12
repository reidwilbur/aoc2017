package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day12Test {

  static List<List<Integer>> input1;
  static List<List<Integer>> test1;

  Day12 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 =
        Files.readLines(
            new File(
                Day12Test.class.getResource("/day12.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day12::readInputLine)
            .collect(Collectors.toList());

    test1 =
        Files.readLines(
            new File(
                Day12Test.class.getResource("/day12-test.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream()
            .map(Day12::readInputLine)
            .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day12();
  }

  @Test
  public void getConnectedNodes_test1() {
    assertThat(testInst.connectedNodes(0, test1).size(), is(6));
  }

  @Test
  public void getConnectedNodes_input1() {
    assertThat(testInst.connectedNodes(0, input1).size(), is(152));
  }

  @Test
  public void getConnectedComponents_test1() {
    assertThat(testInst.connectedComponents(test1).size(), is(2));
  }

  @Test
  public void getConnectedComponents_input1() {
    assertThat(testInst.connectedComponents(input1).size(), is(186));
  }
}
