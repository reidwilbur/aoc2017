package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day20Test {

  static List<Day20.Particle> input1;
  static List<Day20.Particle> test1;

  Day20 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Streams.mapWithIndex(
        Files.readLines(
            new File(
                Day20Test.class.getResource("/day20.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream(),
        Day20.Particle::new
    ).collect(Collectors.toList());

    test1 = Streams.mapWithIndex(
        Files.readLines(
            new File(
                Day20Test.class.getResource("/day20-test.txt").toURI()
            ),
            Charsets.UTF_8
        ).stream(),
        Day20.Particle::new
    ).collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day20();
  }

  @Test
  public void testDistSort_test1() {
    assertThat(testInst.distSort(test1, 10000).get(0).id, is(0L));
  }

  @Test
  public void testDistSort_input1() {
    assertThat(testInst.distSort(input1, 10000).get(0).id, is(258L));
  }
}
