package com.wilb0t.aoc;

public class Day22 {

  enum State {
    C("."), W("W"), I("#"), F("F");

    State next() {
      State[] ords = values();
      return ords[(ordinal() + 1) % ords.length];
    }

    private final String s;

    State(String s) {
      this.s = s;
    }

    @Override
    public String toString() {
      return s;
    }
  }

  enum Dir {
    D, L, U, R;

    Dir left() {
      Dir[] ords = values();
      return ords[(ordinal() + ords.length - 1) % ords.length];
    }

    Dir right() {
      Dir[] ords = values();
      return ords[(ordinal() + 1) % ords.length];
    }

    Dir reverse() {
      Dir[] ords = values();
      return ords[(ordinal() + 2) % ords.length];
    }
  }

  static class Carrier {
    int r;
    int c;
    Dir dir;

    public Carrier(int r, int c, Dir dir) {
      this.r = r;
      this.c = c;
      this.dir = dir;
    }

    public void step() {
      switch (dir) {
        case D: r += 1; break;
        case L: c -= 1; break;
        case R: c += 1; break;
        case U: r -= 1; break;
      }
    }
  }

  public int simStep(boolean[][] grid, Carrier c) {
    if (grid[c.r][c.c]) {
      c.dir = c.dir.right();
    } else {
      c.dir = c.dir.left();
    }
    grid[c.r][c.c] = !grid[c.r][c.c];
    int ret = grid[c.r][c.c] ? 1 : 0;
    c.step();
    return ret;
  }

  public int sim(boolean[][] grid, int itrs) {
    Carrier c = new Carrier((grid.length / 2), (grid.length / 2), Dir.U);
    int count = 0;
    //printGrid(grid, c);
    for(int i = 0; i < itrs; i++) {
      count += simStep(grid, c);
      //printGrid(grid, c);
    }
    return count;
  }

  void printGrid(boolean[][] grid, Carrier cr) {
    for(int r = 2501 - 20; r < 2501 + 21; r++) {
      for(int c = 2501 - 20; c < 2501 + 21; c++) {
        if (r == cr.r && c == cr.c) {
          System.out.print('o');
        } else {
          System.out.print(grid[r][c] ? '#' : '.');
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  public int simStateStep(State[][] grid, Carrier c) {
    switch(grid[c.r][c.c]) {
      case C: c.dir = c.dir.left(); break;
      case W: break;
      case I: c.dir = c.dir.right(); break;
      case F: c.dir = c.dir.reverse(); break;
    }
    grid[c.r][c.c] = grid[c.r][c.c].next();
    int ret = grid[c.r][c.c] == State.I ? 1 : 0;
    c.step();
    return ret;
  }

  public int simState(State[][] grid, int itrs) {
    Carrier c = new Carrier((grid.length / 2), (grid.length / 2), Dir.U);
    int count = 0;
    //printGrid(grid, c);
    for(int i = 0; i < itrs; i++) {
      count += simStateStep(grid, c);
      //printGrid(grid, c);
    }
    return count;
  }

  void printGrid(State[][] grid, Carrier cr) {
    for(int r = 2501 - 20; r < 2501 + 21; r++) {
      for(int c = 2501 - 20; c < 2501 + 21; c++) {
        if (r == cr.r && c == cr.c) {
          System.out.print('o');
        } else {
          System.out.print(grid[r][c]);
        }
      }
      System.out.println();
    }
    System.out.println();
  }
}
