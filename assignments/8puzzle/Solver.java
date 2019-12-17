import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private SearchNode end;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode start = new SearchNode(initial);
        MinPQ<SearchNode> openSet = new MinPQ<>();
        openSet.insert(start);

        end = start;

        while (!openSet.isEmpty()) {
            SearchNode minimal = openSet.delMin();

            if (minimal.isGoalNode()) {
                end = minimal;
                break;
            }
            for (SearchNode neighbour : minimal.getAllNeighbours()) {
                openSet.insert(neighbour);
            }
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return end.isGoalNode();
    }

    // min number of moves to solve initial board
    public int moves() {
        return end.getMovesMade();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        List<Board> solution = new ArrayList<>();
        SearchNode temp = end;
        while (temp != null) {
            solution.add(temp.board);
            temp = temp.previousNode;
        }
        Collections.reverse(solution);
        return solution;
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

    private class SearchNode implements Comparable<SearchNode> {

        private SearchNode previousNode;
        private Board board;
        private int movesMade;

        public SearchNode(Board start) {
            this(start, null, 0);
        }

        public SearchNode(Board board, SearchNode searchNode, int moves) {
            this.board = board;
            previousNode = searchNode;
            movesMade = moves;
        }

        public List<SearchNode> getAllNeighbours() {
            List<SearchNode> nodes = new ArrayList<>();
            for (Board next : board.neighbors()) {
                nodes.add(new SearchNode(next, this, movesMade + 1));
            }
            return nodes;
        }

        public boolean isGoalNode() {
            return board.isGoal();
        }

        public int getMovesMade() {
            return movesMade;
        }

        public int compareTo(SearchNode searchNode) {
            return this.board.hamming() + this.movesMade
                    - (searchNode.board.hamming() + searchNode.movesMade);
        }
    }
}