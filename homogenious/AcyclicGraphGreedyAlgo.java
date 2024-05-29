package com.Stargraphs.homogenious;

import java.util.*;


public class AcyclicGraphGreedyAlgo {
	
	public static class Edge{
		int nxtNode;
		int weight;
		
		public Edge(int W,int nextNode ) {
			this.weight  = W;
			this.nxtNode = nextNode;
		}

		public int getNxtNode() {
			return nxtNode;
		}

		public void setNxtNode(int nxtNode) {
			this.nxtNode = nxtNode;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
		
	}
	
	
	int V;
	static HashMap<Integer, ArrayList<Edge>> adj[];
	AcyclicGraphGreedyAlgo(int v){
		this.V = v;
		adj = new HashMap[v];
		
		for(int i=0; i< v; i++) {
			ArrayList<Edge> list=new ArrayList<>();
			adj[i] = new HashMap<>();
			adj[i].put(i+1, list);
			
		}
	}
    
    // Function to generate the edge irregular k-labeling for a homogeneous amalgamated star graph
    public  void generateEdgeLabeling(int curentNode, int V, int n,int m, int k) {
    	
    	Set<Integer> weightSet = new HashSet<>();
    	int edgeCount = n-2;
    	int labelLevl = 0;
    	int currentNode1Count = 0;
    	
    	HashMap<Integer, Integer> labelLevelmap= new HashMap<Integer, Integer>();
    	
    	// make these to n 
    	for(int i=0 ;i<= V; i++) {
    		
    		adj = fill(i,adj,n, k, curentNode, weightSet, edgeCount,currentNode1Count);
    		//Base Condition to be applied 
    		if(curentNode == 1 && currentNode1Count == 0) {
    			adj[i].get(curentNode).add(new Edge(5, 4));
//    			adj[3].get(4).add(new Edge(5, curentNode));
    			weightSet.add(5);
    			
    			adj[i].get(curentNode).add(new Edge(2, 1));
    			adj[1].clear();
    			ArrayList<Edge> labellist1 = new ArrayList<>();
//    			labellist1.add(new Edge(2, curentNode));
    			adj[1].put(1,labellist1);
    			weightSet.add(2);
    			currentNode1Count++;
    	    	
    		}
    		ArrayList<Edge> listlabel_level= adj[0].get(1);
    		
    		labelLevelmap = getCurrentNode(listlabel_level, labelLevl,n ,V,adj,k,weightSet);
    		for (Map.Entry<Integer, Integer> entry : labelLevelmap.entrySet()) {
                curentNode = entry.getValue();
                labelLevl = entry.getKey();
    		}
    		labelLevelmap.clear();
    		
    		edgeCount = m - 1;
    		if(currentNode1Count == 1 && curentNode == 1) {
    			i = curentNode - 1;
    		}else {
    			i = curentNode - 2;
    		}
    	
    	}			
	}
     
    static HashMap<Integer, ArrayList<Edge>>[] fill(int i,HashMap<Integer, ArrayList<Edge>>[] adj,int n,int k , int cN, Set<Integer> weightSet,int edgeCount,int  currentNode1Count) {
    	int count =0;
    	
    	if(n > 4 && edgeCount == n-2) {
    		k = k-1;
    	}
    	
    	for(int j=k; j>=1; j--) {
	    		
	    		if(count < edgeCount) {
	    			int nextLabel = j;
		    		int weight = cN + nextLabel;
		    		
		    		if(isValid(weight, weightSet)  ) {
		    			
		    			if(adj[i].get(cN).isEmpty()) {
		    				ArrayList<Edge> labellist1 = new ArrayList<>();
		    				adj[i].put(cN, labellist1);
		    			}
		    			
		    			Edge edge=new  Edge(weight, nextLabel);
		    			adj[i].get(cN).add(edge);
		    			
		    			
		    			//if Undirected
//		    			if(cN == 1) {
//		    			Edge edge1=new  Edge(weight, cN);
//		    			
//		    			adj[nextLabel-1].get(nextLabel).add(edge1);
//		    			}
//		    			j = nextLabel;
		    			
		    			
		    			count++;
//		    			currentNode1Count++; 
		    		}
		    		else if (isValid(weight, weightSet) && currentNode1Count == 1) {
		    			
		    			Edge edge=new  Edge(weight, nextLabel);
		    			
		    			adj[1].get(cN).add(edge);	
					}
		    	}else{
    					break;
    				}
	    		}
	    		return adj;
	    	}
	    	
    public  HashMap<Integer, Integer>  getCurrentNode(ArrayList<Edge> labelList,int labelLevel,int n,int v,HashMap<Integer, ArrayList<Edge>> adj[],int k, Set<Integer> weightSet) {
		HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
		int currentNode=0;
		if(labelLevel < n) {
			
			 currentNode= labelList.get(labelLevel).getNxtNode();
    		
			 labelLevel = labelLevel+1;
			 map.put(labelLevel, currentNode);	
		}
		else {
			
			currentNode = v+2;
			labelLevel = labelLevel+1;
			
			map.put(labelLevel,currentNode );
		}
		return map;
		
		
	}
   public static boolean isValid(int weight, Set<Integer> weightSet) {
    	
    	
    	boolean isVaid = true;
    	
    	if(weightSet.contains(weight)) {
    		isVaid = false;
    	}else {
    		weightSet.add(weight);
    	}
    	
    	return isVaid;
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
          // Maximum degree of the homogeneous amalgamated star graph
        int V = m*n + 1;  // Range of labels (1 to k)
        
        int  k = (int) Math.ceil(V / 2.0);
//          k = k-1;
        
        AcyclicGraphGreedyAlgo graphsWeightedTestCode=new AcyclicGraphGreedyAlgo(V);
        
        graphsWeightedTestCode.generateEdgeLabeling(1,V,n,m,k);
        
        System.out.println("The maximum labeling value is (K) : "+k);
        System.out.println("{");
        for(int i=0; i<V; i++) {
        	
        	for (Map.Entry<Integer, ArrayList<Edge>> entry : adj[i].entrySet()) {
                int key = entry.getKey();
                ArrayList<Edge> edges = entry.getValue();
               
                if(!edges.isEmpty()) {
                	System.out.print(key + " = [");
                for (Edge edge : edges) {
                    System.out.print("<" +edge.getWeight() + "," + edge.getNxtNode() +">");

                }
            	System.out.println("] ,");

            }
        }
        	
        }
        System.out.println("}");
        }
    }
}
    















        
        
        
    


