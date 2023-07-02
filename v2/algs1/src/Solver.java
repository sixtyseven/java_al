import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode lastSearchNode = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        find(initial);
    }

    private void find(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode firstSearchNode = new SearchNode(initial, 0, null);
        pq.insert(firstSearchNode);

        SearchNode sn;

        while (!pq.isEmpty()) {
            sn = pq.delMin();

            if (sn.board.isGoal()) {
                lastSearchNode = sn;
                // solved
                return;
            }

            Iterable<Board> snBoardNeighbors = sn.board.neighbors();

            SearchNode previousNode;
            for (Board board : snBoardNeighbors) {
                previousNode = sn.previous;

                if (previousNode != null) {
                    if (board.equals(previousNode.board)) {
                        continue;
                    }
                }

                if (board.hamming() == 2 && board.twin().isGoal()) {
                    // not solvalbe
                    return;
                }

                pq.insert(new SearchNode(board, sn.moves + 1, sn));
            }
        }
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
        ResizingArrayStack<Board> boards = new ResizingArrayStack<>();
        boards.push(lastSearchNode.board);
        SearchNode previousSn = lastSearchNode.previous;
        while (previousSn != null) {
            boards.push(previousSn.board);
            previousSn = previousSn.previous;
        }
        return boards;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode previous;
        int manhattanVal;

        public SearchNode(Board b, int moves, SearchNode previous) {
            this.board = b;
            this.moves = moves;
            this.previous = previous;
            this.manhattanVal = b.manhattan();
        }

        public int compareTo(SearchNode that) {
            int thisVal = this.manhattanVal + moves;
            int thatVal = that.manhattanVal + that.moves;
            if (thisVal < thatVal) {
                return -1;
            }
            if (thisVal > thatVal) {
                return 1;
            }

            return 0;
        }
    }

    // test client (see below)
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
