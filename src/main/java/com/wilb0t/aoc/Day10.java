package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

  static final int VAL_LEN = 256;
  static final int DENSE_INPUT_LEN = 16;
  static final int HASH_ROUNDS = 64;

  int knotHashRound(List<Integer> values, List<Integer> lengths) {
    int pos = 0;
    int skipSize = 0;
    List<Integer> mValues = Lists.newArrayList(values);
    for(int length : lengths) {
      //printItr(mValues, pos, length);
      reverse(mValues, pos, length);
      //printItr(mValues, pos, length);
      pos += length + skipSize;
      skipSize += 1;
      //System.out.println();
    }
    return mValues.get(0) * mValues.get(1);
  }

  public String knotHash(String input) {
    String lengths = input + String.valueOf(new char[]{17, 31, 73, 47, 23});

    List<Integer> values = IntStream.range(0, VAL_LEN).boxed().collect(Collectors.toList());
    Map.Entry<Integer, Integer> initPosSkip = new AbstractMap.SimpleEntry<>(0, 0);
    IntStream
        .range(0, HASH_ROUNDS)
        .boxed()
        .reduce(
            initPosSkip,
            (posSkip, i) -> knotHashRound(values, lengths, posSkip.getKey(), posSkip.getValue()),
            (oldElm, newElm) -> newElm
        );

    return IntStream
        .range(0, VAL_LEN / DENSE_INPUT_LEN)
        .map(i -> denseHash(values.subList(i*DENSE_INPUT_LEN, (i+1)*DENSE_INPUT_LEN)))
        .mapToObj(i -> String.format("%02x", i))
        .collect(Collectors.joining());
  }

  int denseHash(List<Integer> values) {
    return values.stream().reduce(0, (acc, val) -> acc ^ val);
  }

  Map.Entry<Integer, Integer> knotHashRound(List<Integer> values, String lengths, int pos, int skipSize) {
    for(int l = 0; l < lengths.length(); l++) {
      int length = lengths.charAt(l);
      reverse(values, pos, length);
      pos += length + skipSize;
      skipSize += 1;
    }
    return new AbstractMap.SimpleEntry<>(pos, skipSize);
  }

  void reverse(List<Integer> values, int pos, int length) {
    for(int i = 0; i < (length / 2); i++) {
      int frontIdx = (pos + i) % values.size();
      int backIdx = (pos + length - 1 - i) % values.size();
      int temp = values.get(backIdx);
      values.set(backIdx, values.get(frontIdx));
      values.set(frontIdx, temp);
    }
  }

  void printItr(List<Integer> values, int pos, int length) {
    int frontIdx = pos % values.size();
    int backIdx = (pos + length - 1) % values.size();
    StringBuilder bldr = new StringBuilder();
    for(int idx = 0; idx < values.size(); idx++) {
      String idxVal = values.get(idx).toString();
      if (idx == pos % values.size()) {
        idxVal = "[" + idxVal + "]";
      }
      if (idx == frontIdx) {
        idxVal = "(" + idxVal;
      }
      if (idx == backIdx) {
        idxVal = idxVal + ")";
      }
      bldr.append(idxVal).append(",");
    }
    System.out.println(bldr.toString());
  }

}
