package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static com.wilb0t.aoc.Day22.Dir;
import static com.wilb0t.aoc.Day22.State;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

public class Day22Test {

  static final int GRID_SIZE = 5001;

  Day22 testInst;

  static boolean[][] createGrid(String resName) throws Exception {
    boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
    List<boolean[]> rows = Files.readLines(
        new File(Day22Test.class.getResource(resName).toURI()),
        Charsets.UTF_8
    ).stream()
        .map(Day22Test::parse)
        .collect(Collectors.toList());
    int start = (grid.length - rows.size()) / 2;
    IntStream.range(start, start + rows.size())
        .forEach(i -> grid[i] = rows.get(i - start));
    return grid;
  }

  static boolean[] parse(String line) {
    boolean[] row = new boolean[GRID_SIZE];
    int start = (row.length - line.length()) / 2;
    for(int i = 0; i < line.length(); i++) {
      row[start + i] = line.charAt(i) == '#';
    }
    return row;
  }

  static State[][] createStateGrid(String resName) throws Exception {
    State[][] grid = new State[GRID_SIZE][GRID_SIZE];
    for(int i = 0; i < GRID_SIZE; i++) {
      Arrays.fill(grid[i], State.C);
    }
    List<State[]> rows = Files.readLines(
        new File(Day22Test.class.getResource(resName).toURI()),
        Charsets.UTF_8
    ).stream()
        .map(Day22Test::parseState)
        .collect(Collectors.toList());
    int start = (grid.length - rows.size()) / 2;
    IntStream.range(start, start + rows.size())
        .forEach(i -> grid[i] = rows.get(i - start));
    return grid;
  }

  static State[] parseState(String line) {
    State[] row = new State[GRID_SIZE];
    Arrays.fill(row, State.C);
    int start = (row.length - line.length()) / 2;
    for(int i = 0; i < line.length(); i++) {
      row[start + i] = line.charAt(i) == '#' ? State.I : State.C;
    }
    return row;
  }

  @Before
  public void setup() {
    testInst = new Day22();
  }

  @Test
  public void testDirLeft() {
    assertThat(Dir.D.left(), is(Dir.R));
    assertThat(Dir.L.left(), is(Dir.D));
    assertThat(Dir.U.left(), is(Dir.L));
    assertThat(Dir.R.left(), is(Dir.U));
  }

  @Test
  public void testDirRight() {
    assertThat(Dir.D.right(), is(Dir.L));
    assertThat(Dir.L.right(), is(Dir.U));
    assertThat(Dir.U.right(), is(Dir.R));
    assertThat(Dir.R.right(), is(Dir.D));
  }

  @Test
  public void testSim_test1() throws Exception {
    boolean[][] grid = createGrid("/day22-test.txt");
    assertThat(testInst.sim(grid, 7), is(5));
  }

  @Test
  public void testSim_test2() throws Exception {
    boolean[][] grid = createGrid("/day22-test.txt");
    assertThat(testInst.sim(grid, 70), is(41));
  }

  @Test
  public void testSim_test3() throws Exception {
    boolean[][] grid = createGrid("/day22-test.txt");
    assertThat(testInst.sim(grid, 10000), is(5587));
  }

  @Test
  public void testSim_input1() throws Exception {
    boolean[][] grid = createGrid("/day22.txt");
    assertThat(testInst.sim(grid, 10000), is(5447));
  }

  @Test
  public void testSimState_test1() throws Exception {
    State[][] grid = createStateGrid("/day22-test.txt");
    assertThat(testInst.simState(grid, 100), is(26));
  }

  @Test
  public void testSimState_test2() throws Exception {
    State[][] grid = createStateGrid("/day22-test.txt");
    assertThat(testInst.simState(grid, 10000000), is(2511944));
  }

  @Test
  public void testSimState_test3() throws Exception {
    State[][] grid = createStateGrid("/day22.txt");
    assertThat(testInst.simState(grid, 10000000), is(2511705));
  }
}
