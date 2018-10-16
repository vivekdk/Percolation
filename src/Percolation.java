import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private static final int ADJ_SITES = 4;

  private final int n;
  private boolean[][] grid;
  private final WeightedQuickUnionUF gridUF;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("number cannot be less than 1");
    }
    grid = new boolean[n][n];
    gridUF = new WeightedQuickUnionUF((n * n) + 2);
    this.n = n;
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
    row = row - 1;
    col = col - 1;
    if (row < 0 || row >= n || col < 0 || col >= n) {
      throw new IllegalArgumentException("invalid input ");
    }
    grid[row][col] = true;

    if (row < n) {
      String[] adj = adjacent(row, col);
      for (int i = 0; i < adj.length; i++) {
        if (adj[i] != null) {
          String[] pair = adj[i].split(",");
          boolean a = grid[Integer.parseInt(pair[0])][Integer.parseInt(pair[1])];
          if (a) {
            gridUF.union((n * row) + col,
                    (n * Integer.parseInt(pair[0])) + Integer.parseInt(pair[1]));
          }
        }
      }
    }
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
    row = row - 1;
    col = col - 1;
    if (row < 0 || row >= n || col < 0 || col >= n) {
      throw new IllegalArgumentException("invalid input ");
    }

    return grid[row][col];
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) {
    if (isOpen(row, col)) {
      row = row - 1;
      col = col - 1;
      for (int i = 0; i < n; i++) {
        if (this.gridUF.connected((row * n) + col, i)) {
          return true;
        }
      }
    }
    return false;
  }

  // number of open sites
  public int numberOfOpenSites() {
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j])
          count++;
      }
    }
    return count;
  }

  // does the system percolate?
  public boolean percolates() {

    // create virtual nodes
    // this.open(n+1, 1);
    for (int i = 1; i <= n; i++)
      if (this.isOpen(1, i))
        this.gridUF.union(i - 1, (n * n));

    // this.open(n+1, 2);
    for (int i = 1; i <= n; i++)
      if (this.isOpen(n, i))
        this.gridUF.union((n * n) + 1, (n * (n - 1)) + (i - 1));

    return this.gridUF.connected((n * n), (n * n) + 1);
  }

  private String[] adjacent(int row, int col) {
    String[] adj = new String[ADJ_SITES];
    if (row > 0)
      adj[0] = (row - 1) + "," + (col);

    if (row < n - 1)
      adj[1] = (row + 1) + "," + (col);

    if (col > 0)
      adj[2] = (row) + "," + (col - 1);

    if (col < n - 1)
      adj[3] = (row) + "," + (col + 1);

    return adj;
  }

  @Override
  public String toString() {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print((this.grid[i][j] ? 1 : 0) + " ");
      }
      System.out.println();
    }
    return "";
  }
}
