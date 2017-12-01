package com.wilb0t.aoc;

public class Day1 {

  int getSumNext(String digits) {
    int sum = 0;
    for(int i = 0; i < digits.length(); i++) {
      if (digits.charAt(i) == digits.charAt((i + 1) % digits.length())) {
        // utf8/ascii char 48 is digit 0
        sum += digits.charAt(i) - 48;
      }
    }
    return sum;
  }

  int getSumHalfWay(String digits) {
    int sum = 0;
    for(int i = 0; i < digits.length(); i++) {
      if (digits.charAt(i) == digits.charAt((i + (digits.length()/2)) % digits.length())) {
        // utf8/ascii char 48 is digit 0
        sum += digits.charAt(i) - 48;
      }
    }
    return sum;
  }
}
