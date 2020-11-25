/* *****************************************************************************
 *  Name:              Rujing Li
 *  Last modified:     Nov/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int T) {
        if (n < 0)
            throw new IllegalArgumentException("n of the grid should be a positive integer.");
        if (T < 0)
            throw new IllegalArgumentException(
                    "number of trails should be a positive integer.");
        trials = new double[T];
        for (int i = 0; i < T; ++i) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates())
                perc.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            trials[i] = (double) (perc.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int numOfTrials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, numOfTrials);
        System.out.printf("%-25s = %f%n", "mean", percStats.mean());
        System.out.printf("%-25s = %f%n", "stddev", percStats.stddev());
        System.out.printf("%-25s = [%f, %f]%n", "95% confidence interval", percStats.confidenceLo(),
                          percStats.confidenceHi());
        System.out.printf("n = %d, trials = %d, time taken = %.5f seconds%n", n, numOfTrials,
                          timer.elapsedTime());

    }


}
