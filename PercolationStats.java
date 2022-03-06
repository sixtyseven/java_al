import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double meanVal;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double[] fractions = new double[trials];
        for (int i = 0; i < trials; i++) {
            fractions[i] = opendSitesInTrial(n) * 1.0 / n / n;
        }
        meanVal = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
        confidenceLo = meanVal - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi = meanVal + (1.96 * stddev) / Math.sqrt(trials);

    }

    private int opendSitesInTrial(int n) {
        Percolation per = new Percolation(n);
        while (!per.percolates()) {
            int ranRow = StdRandom.uniform(n) + 1;
            int ranCol = StdRandom.uniform(n) + 1;
            if (!per.isOpen(ranCol, ranRow)) {
                per.open(ranCol, ranRow);
            }
        }
        int openSitesCount = per.numberOfOpenSites();
        return openSitesCount;
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int firstArg = Integer.parseInt(args[0]);
        int secondArg = Integer.parseInt(args[1]);
        PercolationStats perStats = new PercolationStats(firstArg, secondArg);
        System.out.println("mean = " + perStats.mean());
        System.out.println("stddev = " + perStats.stddev());
        System.out.println(
                "95% confidence interval  = [" + perStats.confidenceLo() + ", " + perStats
                        .confidenceHi()
                        + ']');
    }

}
