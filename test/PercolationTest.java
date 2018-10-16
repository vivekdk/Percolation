import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PercolationTest {

  private static Percolation percolation;

  @Test
  void percolates() throws Exception{
    Arrays.stream(new File(getResourceAsStream("fixtures").toURI()).listFiles())
            .forEach(file -> {
              percolation = null;
              try {
                Files.lines(file.toPath())
                        .forEachOrdered(line -> {
                          line = line.trim();
                          String[] numbers = line.split("\\s+");
                          if (numbers.length == 1 && numbers[0].trim().length() > 0){
                            System.out.println(numbers[0] + "--" + file.getName());
                            percolation = new Percolation(Integer.parseInt(numbers[0]));
                            assertTrue(percolation.numberOfOpenSites() == 0);
                          }
                          else if (numbers.length == 2){
                            percolation.open(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
                            percolation.percolates();
                            System.out.println(percolation.isFull(2,6));

                          }
                        });
                // System.out.println(percolation.numberOfOpenSites() == percolation.n);
                //System.out.println("Open sites" + "----" + percolation.numberOfOpenSites() + "---" + percolation.);
                percolation.toString();
//                percolation.open (7,5);
                assertTrue (percolation.percolates());
              }
              catch (IOException e){

              }

            });
  }

  private URL getResourceAsStream(String resource) {
    return getClass().getResource(resource);
  }

  private ClassLoader getContextClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}