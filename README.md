# Amulgamated_Star_Graph
# Vertex k-Labeling

This project implements algorithms for solving the Vertex k-Labeling problem, a fundamental problem in graph theory with applications in areas such as channel assignment, frequency allocation, and graph coloring.

## Problem Definition

Given an undirected graph G = (V, E) and a positive integer k, the Vertex k-Labeling problem aims to assign labels from the set {0, 1, 2, ..., k} to the vertices of the graph such that no two adjacent vertices share the same label. The objective is to find the smallest value of k for which a valid labeling exists.

## Algorithms

This project provides two algorithmic approaches for solving the Vertex k-Labeling problem:

### 1. Greedy Algorithm

The greedy algorithm is a heuristic-based approach that assigns labels to vertices in a specific order, making the locally optimal choice at each step. The implementation is based on the Welsh-Powell algorithm, which sorts vertices in descending order of their degrees and assigns the smallest possible label to each vertex.

**Time Complexity:** O(V log V + E), where V is the number of vertices and E is the number of edges.

**Limitations:**
These greedy approch is bounded for some limited n values.
On Furthure Development the limitation will be overcome.

### 2. Backtracking Algorithm

The backtracking algorithm is an exhaustive search technique that explores all possible label assignments to find a valid solution. It systematically constructs candidates for the solutions and abandons each candidate ("backtracks") as soon as it determines that the candidate cannot possibly be completed to a valid solution.

**Time Complexity:**  O (V^4)., where k is the maximum label value and V is the number of vertices. (Exponential time complexity in the worst case)

## Usage

1. Clone the repository
2. Navigate to the project directory
3. Run the desired algorithm with an input
   Ex: n = 8 and m = 3
