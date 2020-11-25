/* *****************************************************************************
 *  Name:              Rujing Li
 *  Last modified:     Nov/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    final int n;
    // final QuickFindUF sites;
    final WeightedQuickUnionUF sites;
    private boolean[][] grid;
    private int numOfOpenSites = 0;

    // TODO: private helper method of converting 2d to 1d

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException(
                "n of the grid should be a positive integer.");
        this.n = n;
        // creates grid to record open/blocked status
        grid = new boolean[n][n];
        // for (boolean[] row : grid)
        //     for (boolean col : row)
        //         System.out.println(col);

        // creates sites for the grid, contains two virtual sites
        // sites = new QuickFindUF(n * n + 2);
        sites = new WeightedQuickUnionUF(n * n + 2);
        // union all in top rows to the first virtual site
        for (int i = 0; i < n; i++)
            sites.union(n * n, i);
        // union all in bottom rows to the second virtual site
        for (int i = n * (n - 1); i < n * n; i++)
            sites.union(n * n + 1, i);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > n || col > n) throw new IllegalArgumentException(
                "Row or col entered should not exceed the bounds of the grid.");
        grid[row - 1][col - 1] = true;
        int curr = (row - 1) * n + col - 1;
        int left = (row - 1) * n + col - 2;
        int right = (row - 1) * n + col;
        int up = (row - 2) * n + col - 1;
        int down = row * n + col - 1;
        if (col != 1 && isOpen(row, col - 1)) sites.union(curr, left);
        if (col != n && isOpen(row, col + 1)) sites.union(curr, right);
        if (row != 1 && isOpen(row - 1, col)) sites.union(curr, up);
        if (row != n && isOpen(row + 1, col)) sites.union(curr, down);
        numOfOpenSites++;
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > n || col > n) throw new IllegalArgumentException(
                "Row or col entered should not exceed the bounds of the grid.");
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > n || col > n) throw new IllegalArgumentException(
                "Row or col entered should not exceed the bounds of the grid.");
        // is the site connected to the first virtual site?
        return sites.find((row - 1) * n + col - 1) == sites.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.find(n * n) == sites.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation myPer = new Percolation(4);
        System.out.println(myPer.isOpen(2, 1));
        myPer.open(3, 1);
        System.out.println(myPer.isOpen(3, 1));
        System.out.println(myPer.numberOfOpenSites());
        myPer.open(1, 1);
        myPer.open(2, 1);
        System.out.println(myPer.isFull(2, 1));
        myPer.open(3, 1);
        myPer.open(4, 2);
        System.out.println(myPer.percolates());

    }
}
