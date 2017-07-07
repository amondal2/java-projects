# java-projects
Projects from Coursera's Algorithms course

Percolation.java
Given an n x n matrix of "sites", the algorithm uses union-find to calculate the probability threshold at which a system will percolate. Each site has a probability p of being open. With this information, we first assume all of the sites are closed, and then select one site at random to open. We iterate until there is a path from the "top" node of the matrix to the "bottom" node of the matrix. Then, we run a Monte Carlo simulation to see the average probability threshold p_star at which the system tends to percolate. More information can be found at: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
