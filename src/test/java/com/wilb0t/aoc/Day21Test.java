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

public class Day21Test {

  static List<Day21.Rule> input1;
  static List<Day21.Rule> test1;

  Day21 testInst;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day21Test.class.getResource("/day21.txt").toURI()),
        Charsets.UTF_8
    ).stream()
        .map(Day21.Rule::new)
        .collect(Collectors.toList());

    test1 = Files.readLines(
        new File(Day21Test.class.getResource("/day21-test.txt").toURI()),
        Charsets.UTF_8
    ).stream()
        .map(Day21.Rule::new)
        .collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day21();
  }

  @Test
  public void testRuleMatches_RotMinus90() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        "#..",
        "#.#",
        "##.");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleMatches_Rot90() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        ".##",
        "#.#",
        "..#");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleMatches_Rot180() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        "###",
        "#..",
        ".#.");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleMatches_Hflip() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        ".#.",
        "#..",
        "###");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleMatches_Vflip() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        "###",
        "..#",
        ".#.");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleNotMatches_FlipRot() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        "..#",
        "#.#",
        ".##");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleNotMatches_DiffPatSameSize() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        ".##",
        "#.#",
        ".##");
    assertFalse(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleNotMatches_DiffSize() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");
    List<String> sq = ImmutableList.of(
        "##",
        "#.");
    assertFalse(r.matches(sq).isPresent());
  }

  @Test
  public void testRuleMatches_same() {
    Day21.Rule r = new Day21.Rule(".#./..#/### => .#./..#/###");

    List<String> sq = ImmutableList.of(
        ".#.",
        "..#",
        "###");
    assertTrue(r.matches(sq).isPresent());
  }

  @Test
  public void testEnhance() {
    List<String> tile = ImmutableList.of(".#.", "..#", "###");
    List<String> expTile = ImmutableList.of("#..#", "....", "....", "#..#");
    assertThat(testInst.enhance(test1, tile), is(expTile));

    tile = expTile;
    expTile = ImmutableList.of("##.##.","#..#..","......","##.##.","#..#..","......");
    assertThat(testInst.enhance(test1, tile), is(expTile));
  }

  @Test
  public void testLitCount_test1() {
    List<String> tiles = ImmutableList.of(".#.", "..#", "###");
    assertThat(testInst.litCount(test1, tiles, 2), is(12));
  }

  @Test
  public void testLitCount_input1() {
    List<String> tile = ImmutableList.of(".#.", "..#", "###");
    assertThat(testInst.litCount(input1, tile, 5), is(125));
  }

  // takes 4.2s to run...
  //@Test
  public void testLitCount_input1_2() {
    List<String> tile = ImmutableList.of(".#.", "..#", "###");
    assertThat(testInst.litCount(input1, tile, 18), is(1782917));
  }
}
