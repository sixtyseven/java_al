import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private static final int unAssigned = -1;
    private int emptySlotRowIdx;
    private int emptySlotColIdx;
    private int manhattanVal = unAssigned;
    private int hammingVal = unAssigned;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (tiles[i][j] == 0) {
                    emptySlotRowIdx = i;
                    emptySlotColIdx = j;
                }
            }
        }
    }

    private int calManhattan() {
        int val = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != 0) {
                    int xExpectedVal = (tiles[i][j] - 1) / tiles.length;
                    int yExpectedVal = (tiles[i][j] - 1) % tiles.length;
                    if (xExpectedVal != i) {
                        val += Math.abs((xExpectedVal) - i);
                    }
                    if (yExpectedVal != j) {
                        val += Math.abs((yExpectedVal) - j);
                    }
                }
            }
        }
        return val;
    }

    private int calHamming() {
        int val = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != 0) {
                    int xExpectedVal = (tiles[i][j] - 1) / tiles.length;
                    int yExpectedVal = (tiles[i][j] - 1) % tiles.length;
                    if (xExpectedVal != i || yExpectedVal != j) {
                        val++;
                    }
                }
            }
        }
        return val;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder(tiles.length + "\r\n");

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                s.append(" ").append(tiles[i][j]);
            }
            s.append("\r\n");
        }

        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingVal == unAssigned) {
            hammingVal = calHamming();
        }
        return hammingVal;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanVal == unAssigned) {
            manhattanVal = calManhattan();
        }
        return manhattanVal;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0 || hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (this == y) {
            return true;
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) {
            return false;
        }

        if (hamming() != that.hamming()) {
            return false;
        }

        if (this.manhattan() != that.manhattan()) {
            return false;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return getNeighbourBoards();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int targetRowIdx = (emptySlotRowIdx + 1) % this.dimension();
        int[][] twinTiles = new int[this.dimension()][this.dimension()];

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }

        if (this.dimension() == 2 || emptySlotRowIdx == this.dimension() - 1) {
            int temp = twinTiles[targetRowIdx][0];
            twinTiles[targetRowIdx][0] = twinTiles[targetRowIdx][1];
            twinTiles[targetRowIdx][1] = temp;
        } else {
            int temp = twinTiles[this.dimension() - 1][this.dimension() - 2];
            twinTiles[this.dimension() - 1][this.dimension() - 2] = twinTiles[this.dimension() - 1][this.dimension()
                    - 3];
            twinTiles[this.dimension() - 1][this.dimension() - 3] = temp;
        }

        return new Board(twinTiles);
    }

    private Bag<Board> getNeighbourBoards() {
        Bag<Board> neighbourBoards = new Bag<>();
        if (emptySlotRowIdx != 0) {
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotColIdx == j) {
                        neighbourTiles[i][j] = tiles[i - 1][j];
                    } else if (emptySlotRowIdx == i + 1 && emptySlotColIdx == j) {
                        neighbourTiles[i][j] = 0;
                    } else {
                        neighbourTiles[i][j] = tiles[i][j];
                    }
                }
            }
            neighbourBoards.add(new Board(neighbourTiles));
        }
        if (emptySlotRowIdx != tiles.length - 1) {
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotColIdx == j) {
                        neighbourTiles[i][j] = tiles[i + 1][j];
                    } else if (emptySlotRowIdx == i - 1 && emptySlotColIdx == j) {
                        neighbourTiles[i][j] = 0;
                    } else {
                        neighbourTiles[i][j] = tiles[i][j];
                    }
                }
            }
            neighbourBoards.add(new Board(neighbourTiles));
        }
        if (emptySlotColIdx != 0) {
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotColIdx == j) {
                        neighbourTiles[i][j] = tiles[i][j - 1];
                    } else if (emptySlotRowIdx == i && emptySlotColIdx == j + 1) {
                        neighbourTiles[i][j] = 0;
                    } else {
                        neighbourTiles[i][j] = tiles[i][j];
                    }
                }
            }
            neighbourBoards.add(new Board(neighbourTiles));
        }
        if (emptySlotColIdx != tiles.length - 1) {
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotColIdx == j) {
                        neighbourTiles[i][j] = tiles[i][j + 1];
                    } else if (emptySlotRowIdx == i && emptySlotColIdx == j - 1) {
                        neighbourTiles[i][j] = 0;
                    } else {
                        neighbourTiles[i][j] = tiles[i][j];
                    }
                }
            }
            neighbourBoards.add(new Board(neighbourTiles));
        }

        return neighbourBoards;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] tiles = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        // Board boardA = new Board(tiles);
        // assert boardA.hamming() == 2;
        // assert boardA.manhattan() == 4;
        // assert boardA.dimension() == 3;
        // assert !boardA.isGoal();
        //
        // int[][] tilesB = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        // Board boardB = new Board(tilesB);
        // assert boardB.isGoal();
        // StdOut.println("seems good");

        // int[][] tilesB = {{1, 2, 3}, {7, 5, 6}, {0, 4, 8}};
        // Board boardB = new Board(tilesB);
        // StdOut.println(boardB.twin());
        int[][] tilesC = { { 1, 2, 3 }, { 0, 7, 6 }, { 5, 4, 8 } };
        Board boardC = new Board(tilesC);
        StdOut.println(boardC.manhattan());
        StdOut.println(boardC.hamming());
        StdOut.println(boardC.twin());
        // Iterable<Board> snBoardNeighbors = boardC.neighbors();
        // for (Board board : snBoardNeighbors) {
        // if (board.equals(boardB)) {
        // break;
        // }
        // StdOut.println(board);
        // }

    }
}
