package com.wilb0t.aoc;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public class Day11 {

  // using cube coords for the hex grid
  static class Point {
    public final int x;
    public final int y;
    public final int z;

    public Point(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    Point add(Point dir) {
      return new Point(dir.x + x, dir.y + y, dir.z + z);
    }
  }

  static final Map<String, Point> DIRS = ImmutableMap.<String, Point>builder()
      .put("se", new Point(1,-1,0))
      .put("ne", new Point(1,0,-1))
      .put("n", new Point(0,1,-1))
      .put("nw", new Point(-1,1,0))
      .put("sw", new Point(-1,0,1))
      .put("s", new Point(0,-1,1))
      .build();

  int getShortestDist(List<String> steps) {
    Point start = new Point(0, 0, 0);
    Point end = steps.stream().reduce(
        start,
        (pos, dir) -> pos.add(DIRS.get(dir)),
        (oldPost, newPos) -> newPos
    );

    return dist(start, end);
  }

  int getMaxDist(List<String> steps) {
    Point start = new Point(0,0,0);
    Point currPos = start;
    int maxDist = 0;
    for(String step : steps) {
      Point newPos = currPos.add(DIRS.get(step));
      int newDist = dist(newPos, start);
      maxDist = newDist > maxDist ? newDist : maxDist;
      currPos = newPos;
    }
    return maxDist;
  }

  int dist(Point p1, Point p2) {
    return (Math.abs(p1.x - p2.x)
            + Math.abs(p1.y - p2.y)
            + Math.abs(p1.z - p2.z)) / 2;
  }
}
