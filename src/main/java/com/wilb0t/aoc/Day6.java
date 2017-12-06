package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day6 {

  Map.Entry<Integer, Integer> distinctRedists(List<Integer> banks) {
    Map<String, Integer> perms = Maps.newHashMap();
    List<Integer> mBanks = Lists.newArrayList(banks);
    int maxBank = getMaxBank(banks);
    int redistCount = 0;
    while (!perms.containsKey(permKey(mBanks))) {
      perms.put(permKey(mBanks), redistCount);
      //System.out.println(mBanks + " maxbank: " + maxBank);
      redist(maxBank, mBanks);
      redistCount += 1;
      //System.out.println(mBanks + " redistCount: " + redistCount);
      maxBank = getMaxBank(mBanks);
    }
    return new AbstractMap.SimpleEntry<>(redistCount, redistCount - perms.get(permKey(mBanks)));
  }

  String permKey(List<Integer> banks) {
    return banks.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  void redist(int bankIdx, List<Integer> banks) {
    int blocks = banks.get(bankIdx);
    banks.set(bankIdx, 0);
    for(int i = 0; i < blocks; i++) {
      int curBankIdx = (i + bankIdx + 1) % banks.size();
      banks.set(curBankIdx, banks.get(curBankIdx) + 1);
    }
  }

  int getMaxBank(List<Integer> banks) {
    int max = 0;
    for(int i = 1; i < banks.size(); i++) {
      if (banks.get(i) > banks.get(max)) {
        max = i;
      }
    }
    return max;
  }
}
