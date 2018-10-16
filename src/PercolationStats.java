import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double PERCENTILE = 1.96;

  private final int[] moves;
  private final int n;
  private final int trials;

  private double mn;
  private double stdDev;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n < 0 || trials <= 0) {
      throw new IllegalArgumentException("invalid input");
    }

    this.n = n;
    this.trials = trials;
    moves = new int[trials];

    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);

      while (!percolation.percolates()) {
        int randomN1 = StdRandom.uniform(1, n + 1);
        int randomN2 = StdRandom.uniform(1, n + 1);

        if (!percolation.isOpen(randomN1, randomN2)) {
          percolation.open(randomN1, randomN2);
          moves[i]++;
        }
      }
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    this.mn = StdStats.mean(moves) / (n * n);
    return this.mn;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    this.stdDev = StdStats.stddev(moves) / (n * n);
    return this.stdDev;
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mn - (PERCENTILE * (this.stdDev / Math.sqrt(this.trials)));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mn + (PERCENTILE * (this.stdDev / Math.sqrt(this.trials)));
  }

  public static void main(String[] args) {
    if (args != null && args.length == 2) {
      PercolationStats percolationStats
              = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      System.out.println(percolationStats.mean());
      System.out.println(percolationStats.stddev());
      System.out.println(percolationStats.confidenceLo() + "," + percolationStats.confidenceHi());
    }
  }
}
