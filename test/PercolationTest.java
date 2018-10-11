import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PercolationTest {

  private static Percolation percolation;

  @BeforeAll
  private static void setup() {
  }

  @Test
  void open() {
    percolation = new Percolation(3);
    percolation.open(0, 0, false);
    percolation.open(0, 2, false);
    percolation.open(1, 2, false);
    percolation.open(2, 0, false);

    //assertTrue(percolation.isOpen(0, 0));
    // assertTrue(percolation.isOpen(1, 1));

    //assertSame(7, percolation.numberOfOpenSites());

    assertTrue(percolation.percolates());
  }

  @Test
  void isFull() {
  }

  @Test
  void numberOfOpenSites() {
  }

  @Test
  void percolates() {
  }
}