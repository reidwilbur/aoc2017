package com.wilb0t.aoc;

import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day20 {

  static class Particle {
    final long id;
    final long[] p;
    final long[] v;
    final long[] a;

    @Override
    public String toString() {
      return "Particle{" +
             "id=" + id +
             ", p=" + Arrays.toString(p) +
             ", v=" + Arrays.toString(v) +
             ", a=" + Arrays.toString(a) +
             '}';
    }

    public Particle(String line, long id) {
      this.id = id;
      String[] parts = line.split(">,\\s*");
      p = Arrays.stream(parts[0].substring(3 ).split(","))
          .map(String::trim)
          .mapToLong(Long::valueOf)
          .toArray();
      v = Arrays.stream(parts[1].substring(3).split(","))
          .map(String::trim)
          .mapToLong(Long::valueOf)
          .toArray();
      a = Arrays.stream(parts[2].substring(3, parts[2].length() - 1).split(","))
          .map(String::trim)
          .mapToLong(Long::valueOf)
          .toArray();
    }

    public Particle(long id, long[] p, long[] v, long[] a) {
      this.id = id;
      this.p = p;
      this.v = v;
      this.a = a;
    }

    public Particle atTime(long t) {
      return new Particle(
          this.id,
          new long[]{
              p[0] + (v[0]*t) + (a[0]*t*t/2),
              p[1] + (v[1]*t) + (a[1]*t*t/2),
              p[2] + (v[2]*t) + (a[2]*t*t/2)
          },
          new long[]{
              v[0] + a[0]*t,
              v[1] + a[1]*t,
              v[2] + a[2]*t
          },
          a
      );
    }

    public long dist() {
      return p[0]*p[0] + p[1]*p[1] + p[2]*p[2];
    }

    public Particle step() {
      v[0] += a[0];
      v[1] += a[1];
      v[2] += a[2];
      p[0] += v[0];
      p[1] += v[1];
      p[2] += v[2];
      return this;
    }
  }

  List<Particle> distSort(List<Particle> particles, long t) {
    return particles
        .stream()
        .map(p -> p.atTime(t))
        .sorted(Comparator.comparing(Particle::dist))
        .collect(Collectors.toList());
  }

  List<Particle> simulateCollisions(List<Particle> particles, int steps) {
    List<Particle> simParts = Lists.newArrayList(particles);
    for(int i = 0; i < steps; i++) {
      Map<List<Long>, List<Particle>> grpd = simParts.stream()
          .collect(Collectors.groupingBy(p -> Longs.asList(p.p)));

      simParts = grpd.entrySet().stream()
          .filter(e -> e.getValue().size() == 1)
          .flatMap(e -> e.getValue().stream())
          .map(Particle::step)
          .collect(Collectors.toList());
    }
    return simParts;
  }
}
