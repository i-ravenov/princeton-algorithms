import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {


    private final int[][] board;
    private final int[][] goal;

    private final int sz;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        sz = tiles.length;
        board = new int[sz][sz];
        goal = new int[sz][sz];
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                goal[i][j] =  j + i*sz + 1;
                board[i][j] = tiles[i][j];
            }
        }
        goal[sz-1][sz-1] = 0;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(sz);
        sb.append('\n');
        for (int[] row : board) {
            for (int i : row) {
                sb.append(i + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return sz;
    }

    // number of tiles out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (board[i][j] == 0) {
                    continue;
                }

                if (board[i][j] != goal[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int m = 0;

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                int tile = board[i][j];
                int goalI;
                int goalJ;
                if (tile == 0) {
                    goalI = i;
                    goalJ = j;
                } else {
                    goalI = (tile - 1) / sz;
                    goalJ = (tile - 1) % sz;
                }
                m += Math.abs(i - goalI) + Math.abs(j - goalJ);
            }
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (getClass() != y.getClass()) {
            return false;
        }

        Board yBoard = (Board) y;
        int ySize = yBoard.sz;
        if (this.sz != ySize) {
            return false;
        }

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if(yBoard.board[i][j] != board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbours = new ArrayList<>();
        int blank = findBlank();
        int blankI = blank/sz;
        int blankJ = blank%sz;

        if (blankI - 1 >= 0) {
            int upTile = (blankI - 1)*sz + blankJ;
            neighbours.add(switchTiles(upTile, blank));
        }

        if (blankI + 1 < sz) {
            int lowTile = (blankI + 1)*sz + blankJ;
            neighbours.add(switchTiles(lowTile, blank));
        }

        if (blankJ - 1 >= 0) {
            int leftTile = blankI*sz + blankJ - 1;
            neighbours.add(switchTiles(leftTile, blank));
        }

        if (blankJ + 1 < sz) {
            int rightTile = blankI*sz + blankJ + 1;
            neighbours.add(switchTiles(rightTile, blank));
        }

        return neighbours;
    }

    private int findBlank() {
        int blank = -1;
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (board[i][j] == 0) {
                    blank = sz*i + j;
                }
            }
        }
        return blank;
    }

    private Board switchTiles(int tile1, int tile2) {
        int[][] switched = new int[sz][sz];

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                switched[i][j] = board[i][j];
            }
        }

        int temp = switched[tile1/sz][tile1%sz];
        switched[tile1/sz][tile1%sz] = switched[tile2/sz][tile2%sz];
        switched[tile2/sz][tile2%sz] = temp;

        return new Board(switched);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int randTile1;
        int randTile2;
        do {
            randTile1 = StdRandom.uniform(sz*sz);
            randTile2 = StdRandom.uniform(sz*sz);
        } while (randTile1 == randTile2 || board[randTile1/sz][randTile1%sz] == 0
                                        || board[randTile2/sz][randTile2%sz] == 0);

        return switchTiles(randTile1, randTile2);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // read in the board specified in the filename
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board b = new Board(tiles);
        StdOut.println(b.toString());
        StdOut.println(b.manhattan());
        StdOut.println(b.hamming());
        StdOut.println(b.isGoal());

        for (Board nghbr : b.neighbors()) {
            StdOut.println();
            StdOut.println(nghbr);
        }
    }
}