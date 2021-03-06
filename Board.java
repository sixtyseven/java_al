import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int length;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        length = tiles.length;
        this.tiles = clone2DArray(tiles, length);
    }

    private int[][] clone2DArray(int[][] arr, int len) {
        int[][] arrCopy = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                arrCopy[i][j] = arr[i][j];
            }
        }
        return arrCopy;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(length).append('\n');
        for (int[] s1 : tiles) {
            sb.append(" ");
            for (int s2 : s1) {
                sb.append(s2).append(" ");
            }
            sb.append('\n');
        }
        String s = sb.toString();
        return s;
    }

    // board dimension n
    public int dimension() {
        return length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == length - 1 && j == length - 1) {
                    break;
                }
                int expected = i * length + j + 1;
                if (expected != tiles[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int total = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                int diffI = Math.abs(tiles[i][j] / length - i);
                int diffJ = Math.abs((tiles[i][j] - 1) % length - j);
                total = total + diffI + diffJ;

            }
        }
        return total;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board b = (Board) y;
        if (this.dimension() != b.dimension()) {
            return false;
        }

        return this.toString().equals(b.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<>();
        int x = 0, y = 0;
        outerloop:
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                    break outerloop;
                }
            }
        }

        if (x > 0) {
            int[][] n1 = clone2DArray(this.tiles, length);
            n1[x][y] = n1[x - 1][y];
            n1[x - 1][y] = 0;
            list.add(new Board(n1));
        }
        if (y > 0) {
            int[][] n1 = clone2DArray(this.tiles, length);
            n1[x][y] = n1[x][y - 1];
            n1[x][y - 1] = 0;
            list.add(new Board(n1));
        }
        if (x < length - 1) {
            int[][] n1 = clone2DArray(this.tiles, length);
            n1[x][y] = n1[x + 1][y];
            n1[x + 1][y] = 0;
            list.add(new Board(n1));
        }
        if (y < length - 1) {
            int[][] n1 = clone2DArray(this.tiles, length);
            n1[x][y] = n1[x][y + 1];
            n1[x][y + 1] = 0;
            list.add(new Board(n1));
        }


        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(tiles);
    }

    public static void main(String[] args) {
        int[][] arr = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        int[][] arr3 = {
                { 8, 3, 1 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        Board board = new Board(arr);
        Board board2 = new Board(arr);
        Board board3 = new Board(arr3);
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.equals(board2));
        System.out.println(board.equals(board3));
        System.out.println(board.neighbors());

    }


}
