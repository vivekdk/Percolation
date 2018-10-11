import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private int[] moves;
  private int n;
  private int trials;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    this.n = n;
    this.trials = trials;
    moves = new int[trials];

    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);

      while (!percolation.percolates()) {
        int randomN1 = StdRandom.uniform(n);
        int randomN2 = StdRandom.uniform(n);

        if (percolation.isFull(randomN1, randomN2)) {
          percolation.open(randomN1, randomN2, false);
          moves[i]++;
        }
      }
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(moves) / (n * n);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(moves) / (n * n);
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mean() - (1.96 * (this.stddev() / Math.sqrt(this.trials)));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean() + (1.96 * (this.stddev() / Math.sqrt(this.trials)));
  }

  public static void main(String[] args) {
    PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    //PercolationStats percolationStats = new PercolationStats(200, 100);
    System.out.println(percolationStats.mean());
    System.out.println(percolationStats.stddev());
    System.out.println(percolationStats.confidenceLo() + "," + percolationStats.confidenceHi());
  }
}
