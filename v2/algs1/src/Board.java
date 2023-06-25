import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private int emptySlotRowIdx;
    private int emptySlotColIdx;
    private final int NOT_ASSIGNED = -1;
    private int manhattanVal = NOT_ASSIGNED;
    private int hammingVal = NOT_ASSIGNED;

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

    // string representation of this board
    public String toString() {
        String s = tiles.length + "%n";

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                s = s + " " + tiles[i][j];
            }
            s = s + "%n";
        }

        return s;
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingVal != NOT_ASSIGNED) {
            return hammingVal;
        }

        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != i * this.tiles.length + j - 1) {
                    count++;
                }
            }
        }
        hammingVal = count;
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanVal != NOT_ASSIGNED) {
            return manhattanVal;
        }
        int distance = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int xVal = tiles[i][j] % tiles.length - 1;
                int yVal = tiles[i][j] / tiles.length;
                if (xVal != i) {
                    distance += Math.abs((xVal) - i);
                }
                if (yVal != j) {
                    distance += Math.abs((yVal) - j);
                }
            }
        }
        manhattanVal = distance;
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int xVal = tiles[i][j] % tiles.length - 1;
                int yVal = tiles[i][j] / tiles.length;
                if (xVal != i) {
                    return false;
                }
                if (yVal != j) {
                    return false;
                }
            }
        }
        return true;
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
                if (i == targetRowIdx && j == 0) {
                    twinTiles[i][j] = tiles[i][1];
                } else if (i == targetRowIdx && j == 1) {
                    twinTiles[i][j] = tiles[i][0];
                } else {
                    twinTiles[i][j] = tiles[i][j];
                }
            }
        }

        return new Board(twinTiles);
    }


    private Bag<Board> getNeighbourBoards() {
        Bag<Board> neighbourBoards = new Bag();
        int boardsIdx = 0;
        if (emptySlotRowIdx != 0) {
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotRowIdx == j) {
                        neighbourTiles[i][j] = tiles[i - 1][j];
                    } else if (emptySlotRowIdx == i - 1 && emptySlotRowIdx == j) {
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
                    if (emptySlotRowIdx == i && emptySlotRowIdx == j) {
                        neighbourTiles[i][j] = tiles[i + 1][j];
                    } else if (emptySlotRowIdx == i + 1 && emptySlotRowIdx == j) {
                        neighbourTiles[i][j] = 0;
                    } else {
                        neighbourTiles[i][j] = tiles[i][j];
                    }
                }
            }
            neighbourBoards.add(new Board(neighbourTiles));
        }
        if (emptySlotColIdx != 0) { // todo
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotRowIdx == j) {
                        neighbourTiles[i][j] = tiles[i][j - 1];
                    } else if (emptySlotRowIdx == i && emptySlotRowIdx == j - 1) {
                        neighbourTiles[i][j] = 0;
                    } else {
                        neighbourTiles[i][j] = tiles[i][j];
                    }

                }
            }
            neighbourBoards.add(new Board(neighbourTiles));
        }
        if (emptySlotRowIdx != 0) {
            int[][] neighbourTiles = new int[tiles.length][tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (emptySlotRowIdx == i && emptySlotRowIdx == j) {
                        neighbourTiles[i][j] = tiles[i][j + 1];
                    } else if (emptySlotRowIdx == i && emptySlotRowIdx == j + 1) {
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
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board boardA = new Board(tiles);
        Board boardB = new Board(tiles);
        boolean isEq = boardA.equals(boardB);
        if (isEq) {
            StdOut.println("is equal");
        } else {
            StdOut.println("is not equal");
        }
    }

}
