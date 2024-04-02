package DSA.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;



public class HomogeniousAmalgamatedStart {
	
	
	class Edge{
		int label;
		int weight;
		
		public Edge(int w, int l) {
			// TODO Auto-generated constructor stub
			this.weight =  w;
			this.label = l;
			
		}

		public int getLabel() {
			return label;
		}

		public void setLabel(int label) {
			this.label = label;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
			
		
	}
    private int numVertices;
    private HashMap<Integer, List<Integer>> adjacencyList;
    private  static ArrayList<HashMap<Integer, ArrayList<Edge>>> adj;

    public HomogeniousAmalgamatedStart(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new HashMap<>();
        this.adj = new ArrayList<HashMap<Integer,ArrayList<Edge>>>();
        
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.put(i, new ArrayList<>());
            
            adj.add(new HashMap<Integer, ArrayList<Edge>>());
        }
    }

    public HashMap<Integer, List<Integer>> addEdge(int src, int dest) {
        adjacencyList.get(src).add(dest);
        adjacencyList.get(dest).add(src);
        
        return adjacencyList;
    }
    
    
    public  void generateALabeledGraph(int currentNode, int k, int n) {
    	
    	Queue<Integer> queue = new LinkedList<>(); // Create a queue for BFS
    	boolean[] visited = new boolean[numVertices]; // Tracking  the visited Nodes
    	Set<Integer> weightSet = new HashSet<Integer>(); // Maintaing Unique weights
    	
    	
    	
    	queue.add(currentNode);

		adj.get(currentNode).put(1, new ArrayList<Edge>());

    	while(!queue.isEmpty()) {
    		int maxLabel = k;
    		
    		
    		currentNode = queue.poll();
//    		if(currentNode == 0) {
//        		maxLabel = maxLabel -1;
//        		}
    		if (!visited[currentNode]) {
    				visited[currentNode] = true;
					
    				for(int neighbour : adjacencyList.get(currentNode)) {
    					if (!visited[neighbour]) {
    						queue.add(neighbour);
					
					
						for(int i = maxLabel ;i>=1; i--) {
							int nextLabel = i;
							int cN = currentNodeOfNewGraph(adj.get(currentNode));
							int weight = nextLabel +  cN;
							
							//till the length of coressponding adj's i
							//if currentNode is 0 then base condtion here and lencount increment
							if(currentNode == 0 && neighbour == n-1) {
								
								adj.get(currentNode).get(cN).add(new Edge(5, 4));
								
								if(adj.get(neighbour).isEmpty()) {
					    			adj.get(neighbour).put(4, new ArrayList<Edge>());
					    		}
								adj.get(neighbour).get(4).add(new Edge(5, cN));
								weightSet.add(5);
								maxLabel = i - 1;
								 
								break;
								}
							
							if(currentNode == 0 && neighbour == n) {
								
								adj.get(currentNode).get(cN).add(new Edge(2, 1));
								
								if(adj.get(neighbour).isEmpty()) {
					    			adj.get(neighbour).put(1, new ArrayList<Edge>());
					    		}
								adj.get(neighbour).get(1).add(new Edge(2, cN));
								
								weightSet.add(2);
								maxLabel = i - 1;
								 
								break;
								
								}
							
							
							if(isValid(weight, weightSet)) {
								
								//if key not exists
								if(adj.get(currentNode).isEmpty()) {
				    			adj.get(currentNode).put(cN, new ArrayList<Edge>());
				    			}
								if(adj.get(neighbour).isEmpty()) {
					    			adj.get(neighbour).put(nextLabel, new ArrayList<Edge>());
					    		}
								
								Edge edge=new  Edge(weight, nextLabel);
								adj.get(currentNode).get(cN).add(edge);
								
								Edge edge1=new  Edge(weight, cN);
								adj.get(neighbour).get(nextLabel).add(edge1);
								
								maxLabel = i - 1;
								 
								break;
								 
							}
						
					
						}
    				}
    			
    			}
    		}
    	}
    	
    	
   }
    
    
    public static int currentNodeOfNewGraph(HashMap<Integer, ArrayList<Edge>> adj) {
    	int labelLevlCurrentNode = 0;
    	for (Map.Entry<Integer, ArrayList<Edge>> entry : adj.entrySet()) {
           
    		labelLevlCurrentNode = entry.getKey();
		}
    	
    	return labelLevlCurrentNode;
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
    	 
    	 public static int getNeighbour( List<Integer> adj) {
    		 int neighbour =0;
    		 
    		 for(Integer list:adj) {
    			 neighbour = list;
    		 }
    		 
    		 return neighbour;
    		 
    	 }

    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Integer vertex : entry.getValue()) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }
    
    public void labelGraph(int n, int k) {
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Integer vertex : entry.getValue()) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int m = 3;
        int n = 8;
        
        int numVertices = m*n+1; //number of vertices.
        
        int  k = (int) Math.ceil(numVertices / 2.0);
        HomogeniousAmalgamatedStart graph = new HomogeniousAmalgamatedStart(numVertices);

        
        for(int i=0; i<=0; i++) {
        	for(int j=1 ; j<=n ;j++) {
        		 graph.addEdge(i, j);
        	}
        		
        }
        int sub = n+1;
        for(int i=1; i<=n; i++) {
        	int countj =0;
        	
        		for(int j= sub  ; j<=numVertices ;j++) {
        			if(countj <= m-2) {
        				graph.addEdge(i, j);
            		countj++;
            		sub =j;
	            	}else {
	            		sub +=1;
	            		break;
	            	}
        	}
        		
        }

        // Printing the graph
//        graph.printGraph();
        
        	graph.generateALabeledGraph(0, k, n);
        	
        	 for(int i=0; i<numVertices; i++) {
            	for (Map.Entry<Integer, ArrayList<Edge>> entry : adj.get(i).entrySet()) {
                     int key = entry.getKey();
                   ArrayList<Edge> edges = entry.getValue();
                     if(!edges.isEmpty()) {
                     	System.out.print( key + " = [ ");
                     for (Edge edge : edges) {
                        System.out.print("<" +edge.getWeight() + "," + edge.getLabel() +">");
                    }
                 	System.out.println(" ]");

                }
            }
        }
    }
}

