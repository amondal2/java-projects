package coursera_algorithms;

import java.util.concurrent.ThreadLocalRandom;
import edu.princeton.cs.algs4.*;

public class Percolation {
	private boolean[][] grid;
	private WeightedQuickUnionUF qf;
	int topSite = 0; // virtual node
	int bottomSite; // virtual node
	private int size;

	public Percolation(int n) {
		// check if dimensions are valid, return new boolean array
		size = n;
		bottomSite = size * size + 1;
		qf = new WeightedQuickUnionUF(size * size + 2); // nxn + 2 virtual nodes
		if (n <= 0) {
			throw new java.lang.IllegalArgumentException();
		} else {
			grid = new boolean[n][n];
		}
	}

	public void open(int row, int column) {
		// set the value
		grid[row - 1][column - 1] = true;
		// check where in the grid the node is, connect adjacent depending on
		// location

		// connect to virtual top site
		if (row == 1) {
			qf.union(getIndex(row, column), topSite);
		}

		// connect to virtual bottom site
		if (row == size) {
			qf.union(getIndex(row, column), bottomSite);
		}

		// connect to the left
		if (column > 1 && isOpen(row, column - 1)) {
			qf.union(getIndex(row, column), getIndex(row, column - 1));
		}

		// connect to the right

		if (column < size && isOpen(row, column + 1)) {
			qf.union(getIndex(row, column), getIndex(row, column + 1));
		}

		// connect up
		if (row > 1 && isOpen(row - 1, column)) {
			qf.union(getIndex(row, column), getIndex(row - 1, column));
		}

		// connect down
		if (row < size && isOpen(row + 1, column)) {
			qf.union(getIndex(row, column), getIndex(row + 1, column));
		}

	}

	public boolean isOpen(int row, int column) {
		return grid[row - 1][column - 1];
	}

	public boolean isFull(int row, int column) throws IndexOutOfBoundsException {
		try {
			return qf.connected(topSite, getIndex(row, column));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int numberOfOpenSites() {
		int count = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.grid[i][j] == true) {
					count++;
				}
			}
		}
		return count;
	}

	public boolean percolates() {
		return qf.connected(topSite, bottomSite);
	}

	private int getIndex(int row, int column) {
		return size * (row - 1) + column;
	}

	private static void percolationStats(int n, int T) {

		double sum = 0;
		for (int i = 0; i < T; i++) {

			Percolation percolationTest = new Percolation(n);
			double openSites = 0;
			double totalSites = n * n;
			while (!percolationTest.percolates()) {
				// generate random site to select
				int randomNumRow = ThreadLocalRandom.current().nextInt(1, n + 1);
				int randomNumCol = ThreadLocalRandom.current().nextInt(1, n + 1);
				if (!percolationTest.isOpen(randomNumRow, randomNumCol)) {
					percolationTest.open(randomNumRow, randomNumCol);
					openSites++;
				}

			}
			sum = sum + (openSites / totalSites);
		}
		System.out.println("Number of trials: " + T);
		System.out.println("Number of sites: " + n*n);
		System.out.println("After " + T + " iterations, the mean percolation threshold is: " + (sum/T));
		System.out.println("\n");
	}

	public static void main(String[] args) {
		System.out.println(">>Testing Monte Carlo simulation of Percolation threshold");
		percolationStats(50, 10);
		percolationStats(200, 100);
		percolationStats(2, 1000);
		percolationStats(2, 10000);
		percolationStats(100, 100);

	}

}
