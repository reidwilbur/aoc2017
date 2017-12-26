package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.base.Functions;
import com.google.common.io.Files;
import java.io.File;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day25Test {

  static long input1Itrs;
  static Map<String, Day25.State> input1;
  static long test1Itrs;
  static Map<String, Day25.State> test1;

  Day25 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    Map.Entry<Long, Map<String, Day25.State>> e = parseFile("/day25.txt");
    input1Itrs = e.getKey();
    input1 = e.getValue();

    e = parseFile("/day25-test.txt");
    test1Itrs = e.getKey();
    test1 = e.getValue();
  }

  static Map.Entry<Long, Map<String, Day25.State>> parseFile(String resName) throws Exception {
    List<String> lines = Files.readLines(
        new File(Day25Test.class.getResource(resName).toURI()),
        Charsets.UTF_8
    );
    Long itrs = Long.valueOf(lines.get(1).split(" ")[5]);
    List<String> stateLines = lines.subList(3, lines.size());
    Map<String, Day25.State> states = IntStream
        .range(0, stateLines.size() / 10)
        .mapToObj(i -> new Day25.State(stateLines.subList(i*10, i*10 + 9)))
        .collect(Collectors.toMap(s -> s.name, Functions.identity()));
    return new AbstractMap.SimpleEntry<>(itrs, states);
  }

  @Before
  public void setup() {
    testInst = new Day25();
  }

  @Test
  public void testDiagChecksum_test1() {
    assertThat(testInst.diagChecksum("A", test1, new Day25.Tape(), test1Itrs), is(3));
  }

  @Test
  public void testDiagChecksum_input1() {
    assertThat(testInst.diagChecksum("A", input1, new Day25.Tape(), input1Itrs), is(3099));
  }
}
