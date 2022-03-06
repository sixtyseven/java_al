import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int[][] site;
    private boolean[][] siteOpen;
    private final WeightedQuickUnionUF ufTopBottom;
    private final WeightedQuickUnionUF ufTop;
    private final int top;
    private final int bottom;
    private int numOfOpenSites;

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
        // plus virtual top
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        // plus virtual top & virtual bottom
        ufTopBottom = new WeightedQuickUnionUF(n * n + 2);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int idxR = row - 1;
        int idxC = col - 1;
        if (!siteOpen[idxR][idxC]) {
            siteOpen[idxR][idxC] = true;
            numOfOpenSites++;
            if (idxR == 0) {
                ufTopBottom.union(top, site[idxR][idxC]);
                ufTop.union(top, site[idxR][idxC]);
            }
            if (idxR == n - 1) {
                ufTopBottom.union(bottom, site[idxR][idxC]);
            }
            if (idxR > 0 && siteOpen[idxR - 1][idxC]) {
                ufTopBottom.union(site[idxR - 1][idxC], site[idxR][idxC]);
                ufTop.union(site[idxR - 1][idxC], site[idxR][idxC]);
            }
            if (idxR < n - 1 && siteOpen[idxR + 1][idxC]) {
                ufTopBottom.union(site[idxR + 1][idxC], site[idxR][idxC]);
                ufTop.union(site[idxR + 1][idxC], site[idxR][idxC]);
            }
            if (idxC > 0 && siteOpen[idxR][idxC - 1]) {
                ufTopBottom.union(site[idxR][idxC - 1], site[idxR][idxC]);
                ufTop.union(site[idxR][idxC - 1], site[idxR][idxC]);
            }
            if (idxC < n - 1 && siteOpen[idxR][idxC + 1]) {
                ufTopBottom.union(site[idxR][idxC + 1], site[idxR][idxC]);
                ufTop.union(site[idxR][idxC + 1], site[idxR][idxC]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        return siteOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);
        return ufTop.find(site[row - 1][col - 1]) == ufTop.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufTopBottom.find(bottom) == ufTopBottom.find(top);
    }

    private void validateInput(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }


    public static void main(String[] args) {

        Percolation per1 = new Percolation(4);
        per1.open(1, 1);
        per1.open(1, 3);


        per1.open(4, 1);
        per1.open(4, 3);
  

    }


}
