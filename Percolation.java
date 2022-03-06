import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int[][] site;
    private boolean[][] siteOpen;
    private int numOfOpenSites;
    private final WeightedQuickUnionUF uf;
    private final int top;
    private final int bottom;

    // creates n-by-n grid, with all sites initially siteed
    public Percolation(int n) {
        if (n <= 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        site = new int[n][n];
        siteOpen = new boolean[n][n];
        numOfOpenSites = 0;
        top = n * n;
        bottom = top + 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                site[i][j] = i * n + j;
            }
        }
        uf = new WeightedQuickUnionUF(bottom + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        int idxR = row - 1;
        int idxC = col - 1;
        if (!siteOpen[idxR][idxC]) {
            siteOpen[idxR][idxC] = true;
            numOfOpenSites++;
            if (idxR == 0) {
                uf.union(top, site[idxR][idxC]);
            }
            if (idxR == n - 1) {
                uf.union(bottom, site[idxR][idxC]);
            }
            if (idxR > 0 && siteOpen[idxR - 1][idxC]) {
                uf.union(site[idxR - 1][idxC], site[idxR][idxC]);
            }
            if (idxR < n - 1 && siteOpen[idxR + 1][idxC]) {
                uf.union(site[idxR + 1][idxC], site[idxR][idxC]);
            }
            if (idxC > 0 && siteOpen[idxR][idxC - 1]) {
                uf.union(site[idxR][idxC - 1], site[idxR][idxC]);
            }
            if (idxC < n - 1 && siteOpen[idxR][idxC + 1]) {
                uf.union(site[idxR][idxC + 1], site[idxR][idxC]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return siteOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return uf.find(site[row - 1][col - 1]) == uf.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(bottom) == uf.find(top);
    }

 
}
