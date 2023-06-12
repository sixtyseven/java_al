import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int numOfOpenSites = 0;
    private final boolean IS_BLOCKED = true;
    private final boolean IS_OPEN = false;

    private WeightedQuickUnionUF openUFTop;
    private WeightedQuickUnionUF openUFBottom;

    private boolean[] site;
    private int sideLen;

    private final int VIRTUAL_TOP_INDEX;
    private final int VIRTUAL_BOTTOM_INDEX;

    private boolean isPercolated = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be bigger than 0.");
        }
        int numTotalItem = n * n;
        openUFTop = new WeightedQuickUnionUF(numTotalItem + 2);
        openUFBottom = new WeightedQuickUnionUF(numTotalItem + 2);
        sideLen = n;

        site = new boolean[numTotalItem + 2];
        for (int i = 0; i < numTotalItem + 2; i++) {
            site[i] = IS_BLOCKED;
        }

        VIRTUAL_TOP_INDEX = numTotalItem + 1;
        VIRTUAL_BOTTOM_INDEX = numTotalItem;

        site[VIRTUAL_TOP_INDEX] = IS_OPEN;
        site[VIRTUAL_BOTTOM_INDEX] = IS_OPEN;
    }

    private void unionBoth(int idx1, int idx2) {
        openUFTop.union(idx1, idx2);
        openUFBottom.union(idx1, idx2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int idx = getSiteIndex(row, col);
        if (site[idx] != IS_OPEN) {
            site[idx] = IS_OPEN;
            numOfOpenSites++;
            if (row == 1) {
                openUFTop.union(idx, VIRTUAL_TOP_INDEX);
            } else {
                int aboveIdx = getSiteIndex(row - 1, col);
                if (isOpen(row - 1, col)) {
                    unionBoth(idx, aboveIdx);
                }
            }

            if (row == sideLen) {
                openUFBottom.union(idx, VIRTUAL_BOTTOM_INDEX);
            } else {
                int belowIdx = getSiteIndex(row + 1, col);
                if (isOpen(row + 1, col)) {
                    unionBoth(idx, belowIdx);
                }
            }

            if (col != sideLen) {
                int rightIdx = getSiteIndex(row, col + 1);
                if (isOpen(row, col + 1)) {
                    unionBoth(idx, rightIdx);
                }
            }

            if (col != 1) {
                int leftIdx = getSiteIndex(row, col - 1);
                if (isOpen(row, col - 1)) {
                    unionBoth(idx, leftIdx);
                }
            }

            if (!isPercolated && isFull(row, col) && openUFBottom.find(idx) == openUFBottom.find(VIRTUAL_BOTTOM_INDEX)) {
                isPercolated = true;
            }
        }
    }

    private int getSiteIndex(int row, int col) {
        validate(row, col);
        return (row - 1) * sideLen + col - 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int idx = getSiteIndex(row, col);
        return site[idx] == IS_OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int idx = getSiteIndex(row, col);
        return openUFTop.find(idx) == openUFTop.find(VIRTUAL_TOP_INDEX);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > sideLen) {
            throw new IllegalArgumentException("row  index " + row + " is not between 1 and " + sideLen);
        }
        if (col < 1 || col > sideLen) {
            throw new IllegalArgumentException("col index " + col + " is not between 1 and " + sideLen);
        }
    }

    public static void main(String[] args) {
        Percolation per = new Percolation(3);
        per.open(1, 1);
        per.open(2, 1);
        per.open(3, 1);
        per.open(3, 3);

        StdOut.println("is percolated:");
        StdOut.println(per.percolates());

        StdOut.println("is Full:");
        StdOut.println(per.isFull(3, 3));

    }
}
