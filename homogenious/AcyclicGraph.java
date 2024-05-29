package com.Stargraphs.homogenious;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Node {
    int value = 0;
}

class Root extends Node{
    ArrayList<Star> children; // Level1 nodes.
    HashSet<Integer> globalSet = new HashSet<Integer>();

    Root(int n, int m) {
        children = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            children.add(new Star(m));
        }
    }
}

class Star extends Node{
    ArrayList<Node> children; // Level2 nodes.

    Star(int m) {
        children = new ArrayList<>();
        for(int i = 0; i < m-1; i++) {
            children.add(new Node());
        }
    }
}

public class AcyclicGraph {
    int n;
    int m;
    int k;
    Set<Integer> edgeLabels;
    Root root;

    AcyclicGraph(int n, int m) {
        this.n = n;
        this.m = m;
        
        this.k = (int) Math.ceil( (n*m + 1) / 2.0);
        this.edgeLabels = new HashSet<>();
        this.root = new Root(n, m);
        System.out.println("Total Number Of Vertices are " +((m*n)+1));
        System.out.println("Max Vertex Labeling value K is " +k);
    }

    //Time complexity = k^2(n*m) times
   public void has() { //Homogeneous Amalgamated Star (has)
       // Level 1
       for(int r = 1; r < k; r++) {    //k times
           root.value = r; // Set the label for root vertex.
           for(Star star: root.children) { //n times
               for(int i = 1; i <=k; i++) { //k times
                   int edgeValue = root.value + i; // At level1 edgeValue is the sum of root node value and i (potential start node value).
                   if(!edgeLabels.contains(edgeValue)) {
                       star.value = i;
                       edgeLabels.add(edgeValue);
                       if(labelChildren(star)) // m-1 times , Check if Level2 can be labelled successfully.
                           break;
                       else {
                           edgeLabels.remove(edgeValue); // Backtracking
                           star.value = 0; // Reset the already assigned value as it is not feasible.
                       }
                   }
               }
               if(star.value == 0) // No value between 1 and k can be assigned to the node.
                   break;
           }
           if(edgeLabels.size() == n*m) {// Check if all the edges were labelled.
               break;
//           ArrayList<Star> new_List= new ArrayList<Star>(root.children);
//           int maxEdgeLabelValue = Integer.MIN_VALUE;
//           for (int edgeValues : edgeLabels) {
//               if (edgeValues > maxEdgeLabelValue) {
//            	   maxEdgeLabelValue = edgeValues;
//               }
//           }
//           edgeLabels.clear();
           
           
           } else {
               // Backtracking
               edgeLabels.clear();
           }
       }
   }

   private boolean labelChildren(Star star) {
       // Level 2
       Set<Integer> local_edge_labels = new HashSet<>(); // Edge labels we assigned in current function call.
       for(Node node: star.children) {
           for(int i = 1; i <= k; i++) {
               int edgeValue = star.value + i; // At level2 edgeValue is the sum of star node value and i (potential leaf node value).
               if(!edgeLabels.contains(edgeValue)) {
                   local_edge_labels.add(edgeValue);
                   node.value = i;
                   edgeLabels.add(edgeValue);
                   break;
               }
               //i = node.value;
           }
           // If no value is assigned to the node, then current labelling is incorrect.
           // Backtracking
           if(node.value == 0) {
               edgeLabels.removeAll(local_edge_labels);
               return false;
           }
       }
       return true;
   }

    public void printGraph() {
        int edgeValue = 0;
        System.out.println("Root: " + root.value);
        for (Star star : root.children) {
            edgeValue = star.value + root.value;
            System.out.println("  └──── Star: " + star.value + " E<" + root.value + "," + star.value + "> EdgeWeight = " + edgeValue);
            for (Node node : star.children) {
                edgeValue = node.value + star.value;
                System.out.println("        └── " + node.value + " E<" + star.value + "," + node.value + "> EdgeWeight = " + edgeValue);
            }
        }
    }
    
    
    public static void main(String[] args) {
    	
    	System.out.print("Do you want to Label Acyclic Graph : ");
    	Scanner scanner = new Scanner(System.in);
    	String input = scanner.next();
    	if(input.matches("yes")) {
    		 System.out.print("Enter a value for n: ");
             int n = scanner.nextInt();
             scanner.nextLine();
             System.out.print("Enter a value for m: ");
             int m = scanner.nextInt();
             scanner.nextLine();
        
        
           
            AcyclicGraph graph = new AcyclicGraph(n, m);
            graph.has();
            graph.printGraph();
    	}else {
    		System.out.println("These is Alogoritham is designed For Acyclic only!");
    	}
    }

}