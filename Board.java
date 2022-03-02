import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tiles[0].length).append('\n');
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
        return 1;
    }

    // number of tiles out of place
    public int hamming() {
        return 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 1;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return this == y;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<>();

        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(tiles);
    }

    public static void main(String[] args) {
        int[][] arr = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };
        Board board = new Board(arr);
        System.out.println(board);

    }
}
