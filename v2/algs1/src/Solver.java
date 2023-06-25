import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

// todo : add comparator
public class Solver {
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
    SearchNode lastSearchNode = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        SearchNode firstSearchNode = new SearchNode(initial, 1, null);
        SearchNode firstSearchNodeTwin = new SearchNode(initial.twin(), 1, null);

        pq.insert(firstSearchNode);
        pqTwin.insert(firstSearchNodeTwin);
        Board targetBoard = getTargetBoard(initial.dimension());
        find(targetBoard);
    }

    private void find(Board targetBoard) {
        while (true) {
            SearchNode sn = pq.delMin();
            SearchNode snTwin = pqTwin.delMin();
            if (sn.board.equals(targetBoard)) {
                lastSearchNode = sn;
                return;
            }
            if (snTwin.board.equals(targetBoard)) {
                return;
            }
            for (Board board : sn.board.neighbors()) {
                if (board != sn.previous.board) {
                    pq.insert(new SearchNode(board, sn.moves + 1, sn));
                }
            }
            for (Board board : snTwin.board.neighbors()) {
                if (board != snTwin.previous.board) {
                    pqTwin.insert(new SearchNode(board, snTwin.moves + 1, snTwin));
                }
            }
        }
    }

    private Board getTargetBoard(int dimention) {
        int targetTiles[][] = new int[dimention][dimention];
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                if (i == dimention - 1 && j == dimention - 1) {
                    targetTiles[i][j] = 0;
                }
                targetTiles[i][j] = i * dimention + j + 1;
            }
        }
        return new Board(targetTiles);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return lastSearchNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return lastSearchNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> stack = new Stack();
        stack.push(lastSearchNode.board);
        while (true) {
            SearchNode previousSn = lastSearchNode.previous;
            if (previousSn == null) {
                break;
            }
            stack.push(lastSearchNode.previous.board);
        }
        return stack;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode previous;

        public SearchNode(Board b, int moves, SearchNode previous) {
            this.board = b;
            this.moves = moves;
            this.previous = previous;
        }

        public int compareTo(SearchNode that) {
            int thisVal = this.board.manhattan() + moves();
            int thatVal = that.board.manhattan() + that.moves;
            if (thisVal < thatVal) {
                return -1;
            }
            if (thisVal > thatVal) {
                return -1;
            }

            return 0;
        }


    }

    private class ByMoves implements Comparator<SearchNode> {
        public int compare(SearchNode v, SearchNode w) {
            if (v.moves < w.moves) {
                return -1;
            }
            if (v.moves > w.moves) {
                return 1;
            }
            return 0;
        }
    }


    // test client (see below)
    // todo test
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
