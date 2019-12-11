/* *****************************************************************************
 *  Name: PercolationStats.java
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;

    private int numOfTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        threshold = new double[trials];
        numOfTrials = trials;

        double general = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int openedSites = 0;
            while (!perc.percolates()) {
                int row = 1 + StdRandom.uniform(n);
                int col = 1 + StdRandom.uniform(n);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openedSites++;
                }
            }
            threshold[i] = openedSites / general;
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - stddev() * 1.96 / Math.sqrt(numOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + stddev() * 1.96 / Math.sqrt(numOfTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, t);

        double lo = percStats.confidenceLo();
        double hi = percStats.confidenceHi();
        double mean = percStats.mean();
        double stddev = percStats.stddev();
        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = ["
                               + lo + "," + hi + "]");
    }
}
