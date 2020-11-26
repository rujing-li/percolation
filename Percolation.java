/* *****************************************************************************
 *  Name:              Rujing Li
 *  Last modified:     Nov/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // the course grader only allows private variables
    private final int n;
    // final QuickFindUF sites;
    private final WeightedQuickUnionUF sites;
    private boolean[][] grid;
    private int numOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException(
                "n of the grid should be a positive integer.");
        this.n = n;
        // creates grid to record open/blocked status
        grid = new boolean[n][n];

        // creates sites for the grid, contains three virtual sites(two for top row, one for bottom row)
        // sites = new QuickFindUF(n * n + 2);
        sites = new WeightedQuickUnionUF(n * n + 2);
    }

    // private helper method of converting 2d index to 1d
    private int convtTo1d(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    private boolean validate(int row, int col) {
        return (row < n || col < n || row > 0 || col > 0);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col)) throw new IllegalArgumentException(
                "Row or col entered should be positive and not exceed the bounds of the grid.");
        if (grid[row - 1][col - 1]) return;
        grid[row - 1][col - 1] = true;
        int curr = convtTo1d(row, col);
        int left = convtTo1d(row, col - 1);
        int right = convtTo1d(row, col + 1);
        int up = convtTo1d(row - 1, col);
        int down = convtTo1d(row + 1, col);
        if (col != 1 && isOpen(row, col - 1)) sites.union(curr, left);
        if (col != n && isOpen(row, col + 1)) sites.union(curr, right);
        if (row != 1 && isOpen(row - 1, col)) sites.union(curr, up);
        if (row != n && isOpen(row + 1, col)) sites.union(curr, down);
        // union to the first virtual site if in top row
        if (row == 1) sites.union(n * n, curr);
        // union to the second virtual site if in bottom row
        if (row == n) sites.union(n * n + 1, curr);
        numOfOpenSites++;
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) throw new IllegalArgumentException(
                "Row or col entered should be positive and not exceed the bounds of the grid.");
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) throw new IllegalArgumentException(
                "Row or col entered should be positive and not exceed the bounds of the grid.");
        // is the site connected to the first virtual site?
        return sites.find(convtTo1d(row, col)) == sites.find(n * n);
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
