package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day3 {

  /*
     1,   9,  25,  49,  81,   121
     1, 3*3, 5*5, 7*7, 9*9, 11*11, ...

     37 36  35  34  33  32 31
     38 17  16  15  14  13 30
     39 18   5   4   3  12 29
     40 19   6   1   2  11 28 53
     41 20   7   8   9  10 27 52
     42 21  22  23  24  25 26 51
     43 44  45  46  47  48 49 50

   */
  int getSteps(int address) {
    if (address == 1) return 0;

    int shellDist = 0;
    int width = 1;
    for(; width*width < address; width += 2) {
      shellDist += 1;
    }
    int posInSide = (address - (width-2)*(width-2)) % (width-1);
    int distToSideMidpoint = Math.abs((width / 2) - posInSide);
    return distToSideMidpoint + shellDist;
  }

  static char[] dirs = new char[]{'r', 'u', 'l', 'd'};

  int stressTest(Predicate<Integer> test) {
    // stream of direction chars
    // r u l d r u l d
    Iterator<Character> dirItr = IntStream
        .iterate(0, i -> i + 1)
        .boxed()
        .map(i -> dirs[i % dirs.length])
        .iterator();

    // zip the two sequences below
    // 1 1 2 2 3 3 4 4 ...  (number of steps to take in each direction)
    // r u l d r u l d ...  (direction for the steps)
    Stream<Map.Entry<Integer, Character>> pathStream = IntStream
        .iterate(1, (i) -> i + 1)
        .flatMap(i -> IntStream.of(i, i))
        .boxed()
        .map(i -> new AbstractMap.SimpleEntry<>(i, dirItr.next()));

    // map to memoize the computed values, indexed on (r,c) position
    // relative to center/initial position
    Map<Map.Entry<Integer, Integer>, Integer> vals = new HashMap<>();
    // center value is 1
    vals.put(new AbstractMap.SimpleEntry<>(0,0), 1);

    // accumulator for position in spiral (r,c)
    int[] curPos = new int[]{0,0};

    // process the path steps, generating values for each cell
    return pathStream.flatMap(pathSeg -> {
      int steps = pathSeg.getKey();
      char dir = pathSeg.getValue();
      // create the values for all the steps in this section of the path
      return IntStream.range(0, steps)
          .boxed()
          .map(i -> {
            // update current position based on direction
            switch (dir) {
              case 'r':
                curPos[1] += 1;
                break;
              case 'u':
                curPos[0] -= 1;
                break;
              case 'l':
                curPos[1] -= 1;
                break;
              case 'd':
                curPos[0] += 1;
                break;
              default:
                throw new RuntimeException("bad times");
            }
            // calculate the value for the this cell based on adjacent cells,
            // up, down, left, right and diagnols
            // cells that haven't been calculated are defaulted to 0
            int val =
                vals.getOrDefault(  new AbstractMap.SimpleEntry<>(curPos[0],     curPos[1] + 1), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0] + 1, curPos[1] + 1), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0] + 1, curPos[1]), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0] + 1, curPos[1] - 1), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0],     curPos[1] - 1), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0] - 1, curPos[1] - 1), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0] - 1, curPos[1]), 0)
                + vals.getOrDefault(new AbstractMap.SimpleEntry<>(curPos[0] - 1, curPos[1] + 1), 0);
            vals.put(new AbstractMap.SimpleEntry<>(curPos[0], curPos[1]), val);
            //System.out.println(
            //    String.format("(%d,%d) %d",
            //    curPos[0], curPos[1], vals.get(new AbstractMap.SimpleEntry<>(curPos[0], curPos[1])))
            //);
            return val;
          });
    })
        .filter(test)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("shit broke"));
  }
}
