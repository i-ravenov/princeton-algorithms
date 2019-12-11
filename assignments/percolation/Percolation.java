/* *****************************************************************************
 *  Name: Percolation.java
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] openSites;

    private WeightedQuickUnionUF unionFind;

    private int dim;

    private int virtualTop;

    private int virtualBottom;

    private int countOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        countOpen = 0;
        this.dim = n;
        virtualTop = 0;
        virtualBottom = dim * dim + 1;
        unionFind = new WeightedQuickUnionUF(dim * dim + 2);
        openSites = new boolean[dim * dim + 2];

        openSites[virtualTop] = true;
        openSites[virtualBottom] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getOneDimIndex(row, col);

        if (openSites[index]) return;

        // union upper Site if it is opened
        if (row == 1) {
            unionFind.union(virtualTop, index);
        }
        else if (openSites[index - dim])
            unionFind.union(index - dim, index);

        // union bottom Site if it is opened
        if (row == dim) {
            unionFind.union(virtualBottom, index);
        }
        else if (openSites[index + dim])
            unionFind.union(index + dim, index);

        // union left Site if it is opened
        if (col > 1 && openSites[index - 1])
            unionFind.union(index, index - 1);

        // union right Site if it is opened
        if (col < dim && openSites[index + 1])
            unionFind.union(index, index + 1);

        openSites[index] = true;
        countOpen++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getOneDimIndex(row, col);
        return openSites[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getOneDimIndex(row, col);
        return unionFind.connected(virtualTop, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.connected(virtualTop, virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private int getOneDimIndex(int row, int col) {
        checkRangeOfIndices(row, col);
        return (row - 1) * dim + col;
    }

    private void checkRangeOfIndices(int row, int col) {
        if (row <= 0
                || col <= 0
                || row > dim
                || col > dim)
            throw new IllegalArgumentException("Attemp to access the cell "
                                                       + "outside of the grid");
    }

}
