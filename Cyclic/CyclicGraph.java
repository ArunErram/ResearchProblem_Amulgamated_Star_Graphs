package com.Stargraphs.Cyclic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CyclicGraph {
    int n;
    int m;
    int k;
    Set<Integer> edgeLabels;
    Root root;
    
    
    CyclicGraph(int n, int m) {
        this.n = n;
        this.m = m;
        this.k = (int) Math.ceil( (n*m + n + 1) / 2.0)+ (n);
        
        
        //this.k = (n*m + n + 1);
        //this.k = (int) ((int) Math.ceil( (n*m + n + 2) / 2.0) + Math.log(n)) ;

        
        //minimizing the K value AMAP
        if(n > 3 && m ==3) 
        	k = k - (n-2);
        if(n > 3 && m >3) 
        	k = k - (m+1);
        
        System.out.println("Max Vertex Labeling value K is " +k);
        this.edgeLabels = new HashSet<>();
        this.root = new Root(n, m);
    }
    
    class Node {
        int value = 0;
        // Star stores the edge value of Root to Star.
        // Leaf stores the edge value of Star to Leaf.
        int edgeValue = 0;
    }

    class Root extends Node{
        ArrayList<Star> children; // Level 1 nodes.

        Root(int n, int m) {
            children = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                children.add(new Star(m));
            }
            for(int i = 0; i < n; i++) {
                int prevIndex = (i - 1 + n) % n;
                int nextIndex = (i + 1) % n;
                children.get(i).prev = children.get(prevIndex);
                children.get(i).next = children.get(nextIndex);
            }
        }
    }

    class Star extends Node{
        ArrayList<Leaf> children; // Level 2 nodes.
        Star prev; // for calculating the edge connected with the previous star.
        Star next; // for passing the recursion to the next star.
        boolean visited;
        int cycleEdgeValue;
        Set<Integer> starEdgeLabels;

        Star(int m) {
            children = new ArrayList<>();
            visited = false;
            starEdgeLabels = new HashSet<>();
            for(int i = 0; i < m-1; i++) {
                children.add(new Leaf());
            }
        }

        public boolean labelChildren(Set<Integer> edgeLabels, int k) {
            // Check whether a value can be assigned to a cycle edge.
        	//check for prevofPrev too
            if(this.prev.visited) {
                int cycleEdgeValue = this.value + this.prev.value;
                if(edgeLabels.contains(cycleEdgeValue)) {
                    return false;
                } else {
                    edgeLabels.add(cycleEdgeValue);
                    this.starEdgeLabels.add(cycleEdgeValue);
                }
            }

            for(Leaf node: this.children) {
                for(int i = 1; i < k; i++) {
                    int edgeValue = this.value + i; // At level2 edgeValue is the sum of star node value and i (potential leaf node value).
                    if(!edgeLabels.contains(edgeValue)) {
                        this.starEdgeLabels.add(edgeValue);
                        node.value = i;
                        edgeLabels.add(edgeValue);
                        break;
                    }
                }
                // If no value is assigned to the node, then current labelling is incorrect.
                // Backtracking
                if(node.value == 0) {
                    edgeLabels.removeAll(this.starEdgeLabels);
                    this.starEdgeLabels.clear();
                    for(Leaf n: this.children)
                        n.value = 0;
                    return false;
                } else {
                    node.edgeValue = node.value + this.value;
                }
            }

            return true;
        }

        public void clearLabels(Set<Integer> edgeLabels) {
            edgeLabels.removeAll(this.starEdgeLabels);
            this.starEdgeLabels.clear();
            for(Leaf node: this.children)
                node.value = 0;
        }
    }

    class Leaf extends Node {

    }

    

   public void has() {
	   
       for(int r = 1; r < k; r++) {
    	   
           root.value = r;
           if(labelStars(root.children.get(0)))
               break;
           else {
               //Backtracking
               edgeLabels.clear();
           }
       }
   }

   private boolean labelStars(Star star) {
        // Base condition
        if(star.visited) {
            // Check the last edge.
            int edgeValue = star.value + star.prev.value;
            // Add last edge value to edgeLabels. If it is not a duplicate, return true, else return false.
            return edgeLabels.add(edgeValue);
        }

        star.visited = true;
        for(int i = 1; i <k; i++) { //k times
            int rootEdgeValue = root.value + i;
            if(!edgeLabels.contains(rootEdgeValue)) {
                edgeLabels.add(rootEdgeValue);
                star.value = i;
                if(star.labelChildren(edgeLabels, k) && labelStars(star.next)) {
                    star.edgeValue = star.value + root.value;
                    star.cycleEdgeValue = star.value + star.prev.value;
                    return true;
                }
                else {
                    edgeLabels.remove(rootEdgeValue);
                    star.value = 0;
                    star.clearLabels(edgeLabels);
                }
            }
        }
        star.visited = false;
        return false;
   }

    private boolean isValidGraph() {
        // Vertex validation
        for(Star star: root.children) {
            if(star.value == 0 || star.value > k)
                return false;
            for(Leaf node: star.children) {
                if(node.value == 0)
                    return false;
            }
        }
        // Edge validation
        if(edgeLabels.size() != n*m + n)
            return false;

        return true;
    }

   public void printGraph() {
        System.out.println("Root: " + root.value);
        for (Star star : root.children) {
            System.out.println("  └── Star: " + star.value + " E<" + root.value + "," + star.value + "> EdgeWeight = " + star.edgeValue);
            System.out.println("      └── " + "CE<" + star.value + "," + star.prev.value + "> l = " + star.cycleEdgeValue);
            for (Leaf node : star.children) {
                System.out.println("      └── " + node.value + " E<" + star.value + "," + node.value + "> EdgeWeight = " + node.edgeValue);
            }
        }
//        System.out.println("Edge labels: " + edgeLabels);
//        if(isValidGraph()) {
//            System.out.println("Successfully labelled all the edges.");
//            System.out.println("Total number of edges labelled: " + edgeLabels.size());
//        } else {
//            System.out.println("Failed to label the graph for k: " + k);
//        }
   }
   
   public static void main(String[] args) {
	   
	System.out.print("Do you want to Label Cyclic Graph : ");
   	Scanner scanner = new Scanner(System.in);
   	String input = scanner.next();
   	if(input.matches("yes")) {
   		 System.out.print("Enter a value for n: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter a value for m: ");
            int m = scanner.nextInt();
            scanner.nextLine();
       
       	
          
            CyclicGraph graph = new CyclicGraph(n, m);
            graph.has();
            graph.printGraph();
   	}else {
   		System.out.println("These is Alogoritham is designed For Cyclic only!");
   	}
       
      
   }
}
