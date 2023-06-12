import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private boolean[][] siteOpen;
    private final WeightedQuickUnionUF ufTopBottom;
    private final WeightedQuickUnionUF ufTop;
    private final int top;
    private final int bottom;
    private int numOfOpenSites;

    // creates n-by-n grid, with all sites initially sited
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        siteOpen = new boolean[n][n];
        numOfOpenSites = 0;
        top = n * n;
        bottom = top + 1;

        // plus virtual top
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        // plus virtual top & virtual bottom
        ufTopBottom = new WeightedQuickUnionUF(n * n + 2);

    }

    private int getSitePosition(int row, int col) {
        return row * n + col;
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
                ufTopBottom.union(top, getSitePosition(idxR, idxC));
                ufTop.union(top, getSitePosition(idxR, idxC));
            }
            if (idxR == n - 1) {
                ufTopBottom.union(bottom, getSitePosition(idxR, idxC));
            }
            if (idxR > 0 && siteOpen[idxR - 1][idxC]) {
                ufTopBottom.union(getSitePosition(idxR - 1, idxC), getSitePosition(idxR, idxC));
                ufTop.union(getSitePosition(idxR - 1, idxC), getSitePosition(idxR, idxC));
            }
            if (idxR < n - 1 && siteOpen[idxR + 1][idxC]) {
                ufTopBottom.union(getSitePosition(idxR + 1, idxC), getSitePosition(idxR, idxC));
                ufTop.union(getSitePosition(idxR + 1, idxC), getSitePosition(idxR, idxC));
            }
            if (idxC > 0 && siteOpen[idxR][idxC - 1]) {
                ufTopBottom.union(getSitePosition(idxR, idxC - 1), getSitePosition(idxR, idxC));
                ufTop.union(getSitePosition(idxR, idxC - 1), getSitePosition(idxR, idxC));
            }
            if (idxC < n - 1 && siteOpen[idxR][idxC + 1]) {
                ufTopBottom.union(getSitePosition(idxR, idxC + 1), getSitePosition(idxR, idxC));
                ufTop.union(getSitePosition(idxR, idxC + 1), getSitePosition(idxR, idxC));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        return siteOpen[row - 1][col - 1];
    }

    // is the sitetodo (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);
        return ufTop.find(getSitePosition(row - 1, col - 1)) == ufTop.find(top);
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
            throw new IllegalArgumentException("row column");
        }
    }


    public static void main(String[] args) {

        Percolation per1 = new Percolation(1);
        per1.open(1, 1);


    }


}
