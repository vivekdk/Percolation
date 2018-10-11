import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;
import java.util.stream.Stream;

public class Percolation {

  private int n;
  private int[][] grid;
  private WeightedQuickUnionUF gridUF;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    grid = new int[n + 1][n];
    gridUF = new WeightedQuickUnionUF((n * n) + 2);
    this.n = n;
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col, boolean virtual) throws IllegalArgumentException {
    grid[row][col] = 1;

    if (!virtual) {
      String[] adj = adjacent(row, col);
      Stream.of(adj)
              .filter(ele -> ele != null)
              .forEachOrdered(ele -> {
                String[] pair = ele.split(",");
                int a = grid[Integer.parseInt(pair[0])][Integer.parseInt(pair[1])];
                if (a == 1)
                  gridUF.union((n * row) + col, (n * Integer.parseInt(pair[0])) + Integer.parseInt(pair[1]));
              });
    }
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) throws IllegalArgumentException {
    return grid[row][col] == 1;
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) throws IllegalArgumentException {
    return !this.isOpen(row, col);
  }

  // number of open sites
  public int numberOfOpenSites() {
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1)
          count++;
      }
    }
    return count;
  }

  // does the system percolate?
  public boolean percolates() {

    // create virtual nodes
    this.open(n, 0, true);
    for (int i = 0; i < n; i++)
      if (this.grid[0][i] == 1)
        this.gridUF.union(i, (n * n));

    this.open(n, 1, true);
    for (int i = 0; i < n; i++)
      if (this.grid[n - 1][i] == 1)
        this.gridUF.union((n * n) + 1, (n * (n-1)) + i);

    return this.gridUF.connected((n * n), (n * n) + 1);
  }

  private String[] adjacent(int row, int col) {
    String[] adj = new String[4];
    if (row > 0)
      adj[0] = (row - 1) + "," + (col);

    if (row < n-1)
      adj[1] = (row + 1) + "," + (col);

    if (col > 0)
      adj[2] = (row) + "," + (col - 1);

    if (col < n-1)
      adj[3] = (row) + "," + (col + 1);

    return adj;
  }

  @Override
  public String toString() {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(this.grid[i][j] + " ");
      }
      System.out.println();
    }
    return "";
  }
}
