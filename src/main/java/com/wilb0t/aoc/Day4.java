package com.wilb0t.aoc;

import com.google.common.primitives.Chars;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day4 {

  boolean isValidPassphrase(String passphrase) {
    return Arrays.stream(passphrase.split("\\s"))
        .collect(Collectors.toMap(
            Function.identity(),
            w -> 1,
            (c, one) -> c + 1
        )).values().stream().allMatch(c -> c == 1);
  }

  boolean isValidPassphraseAnagram(String passphrase) {
    Map<String, Integer> counts = Arrays.stream(passphrase.split("\\s"))
        .map(s -> Chars
            .asList(s.toCharArray())
            .stream()
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.joining())
        )
        .collect(Collectors.toMap(
            Function.identity(),
            w -> 1,
            (c, one) -> c + 1
        ));

    return counts.values().stream().allMatch(c -> c == 1);
  }
}
