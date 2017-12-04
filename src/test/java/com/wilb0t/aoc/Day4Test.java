package com.wilb0t.aoc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Day4Test {

  Day4 testInst;

  List<String> input1;

  @Before
  public void setup() throws URISyntaxException, IOException {
    testInst = new Day4();
    input1 = Files.readLines(
        new File(getClass().getResource("/day4.txt").toURI()),
        Charsets.UTF_8
    );
  }

  @Test
  public void testIsValidPassphrase_case1() {
    assertThat(testInst.isValidPassphrase("aa bb cc dd ee"), is(true));
  }

  @Test
  public void testIsValidPassphrase_case2() {
    assertThat(testInst.isValidPassphrase("aa bb cc dd aa"), is(false));
  }

  @Test
  public void testIsValidPassphrase_case3() {
    assertThat(testInst.isValidPassphrase("aa bb cc dd aaa"), is(true));
  }

  @Test
  public void testIsValidPassphase_input1() {
    long valid = input1.stream().map(testInst::isValidPassphrase).filter(b -> b).count();
    assertThat(valid, is(466L));
  }

  @Test
  public void testIsValidPassphraseAnagram_case1() {
    assertThat(testInst.isValidPassphraseAnagram("abcde fghij"), is(true));
  }

  @Test
  public void testIsValidPassphraseAnagram_case2() {
    assertThat(testInst.isValidPassphraseAnagram("abcde xyz ecdab"), is(false));
  }

  @Test
  public void testIsValidPassphraseAnagram_case3() {
    assertThat(testInst.isValidPassphraseAnagram("a ab abc abd abf abj"), is(true));
  }

  @Test
  public void testIsValidPassphraseAnagram_case4() {
    assertThat(testInst.isValidPassphraseAnagram("iiii oiii ooii oooi oooo"), is(true));
  }

  @Test
  public void testIsValidPassphraseAnagram_case5() {
    assertThat(testInst.isValidPassphraseAnagram("oiii ioii iioi iiio"), is(false));
  }

  @Test
  public void testIsValidPassphaseAnagram_input1() {
    long valid = input1.stream().map(testInst::isValidPassphraseAnagram).filter(b -> b).count();
    assertThat(valid, is(251L));
  }
}
