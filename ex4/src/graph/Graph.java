package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

//Class used to represent the edges in the graph
class Edge<T,K>{
	public T end;
	public K weight;
	
	public Edge(T b, K c){		//constructor
		end = b;
		weight = c;
	}
	
	
	@Override
	public boolean equals(Object O){
		if(O instanceof Edge){
			@SuppressWarnings("unchecked") Edge<T,K> a = (Edge) O;
			return this.end.equals(a.end);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return end.hashCode();
	}
}

public class Graph<T,K>{
	private ArrayList<T> array = null;							//array of nodes of the graph
	private HashMap<T, LinkedList<Edge<T,K>>> adj = null;		//Hash map of nodes-adjacency list
	private boolean direct;										//It indicates if the graph is direct or not
	
	//Constructor; it accepts a string that indicates the type of the graph
	public Graph(String type){			
		array = new ArrayList<T>();
		adj = new HashMap<T, LinkedList<Edge<T,K>>>();
		if(type.equals("d")){direct = true;
		}else if(type.equals("u")){
			direct = false;
		}else{
			throw new IllegalArgumentException("Graph uncorrectly initialized (type of graph not known)");
		}
	}
	
	
	//It adds a node and create is adjacency list, returning false if the node already exists
	public boolean addNode(T elem){
		if(!containsNode(elem)){
			array.add(elem);
			adj.put(elem, new LinkedList<Edge<T,K>>());
			return true;
		}else{
			return false;
		}
	}
	
	//It adds an edge, also adding the nodes if they do not already exist. If the graph is undirect, it is added also the opposite edge
	public boolean addEdge(T st, T en, K w){
		Edge<T,K> edge = new Edge<T,K>(en, w);
		if(!containsNode(st))addNode(st);
		if(!containsNode(en))addNode(en);
		if(!containsEdge(st, en)){
			LinkedList<Edge<T,K>> list = adj.get(st);
			list.add(edge);
			if(direct != true){
				addEdge(en, st, w);
			}
			return true;
		}
		return false;
	}
	
	//It return the value of direct attribute
	public boolean directEdge(){
		return direct;
	}
	
	//It returns true if the node exists in the graph, false if not
	public boolean containsNode(T node){
		return adj.get(node) != null;
	}
	
	//It returns true if the edge exists in the graph, false if not
	public boolean containsEdge(T st, T en){
		boolean contains = false;
		if(containsNode(st) && containsNode(en)){
			LinkedList<Edge<T,K>> list = adj.get(st);
			contains = list.contains(new Edge<T,K>(en, null)); 
		}
		return contains;
	}
	
	/*It deletes a node from the graph (or return false if the note does not exist), 
	also deleting his adjacency list and alll the edges where the node appears*/
	public boolean deleteNode(T node){
		if(containsNode(node)){
			int j = 0;
			while(j<array.size()){
				if(containsEdge(array.get(j), node)){
					deleteEdge(array.get(j), node);
				}
				j++;
			}
			array.remove(node);
			adj.remove(node);
			return true;
		}
		return false;
	}
	
	//It deletes a particular edge in the graph (and the opposite edge if the graph is undirect)
	public boolean deleteEdge(T st, T en){
		boolean del = false;
		if(containsEdge(st, en)){
			LinkedList<Edge<T,K>> list = adj.get(st);
			del = list.remove(new Edge<T,K>(en, null));
			if(direct != true){
				list = adj.get(en);
				del = del && list.remove(new Edge<T,K>(st, null));
			}
		}
		return del;
	}
	
	//It returns the number of nodes
	public int numNode(){
		return array.size();
	}
	
	//It returns the number of edge (number/2 if the graph is undirect, where every edge is counted twice)
	public int numEdge(){
		int j = 0;
		int count = 0;
		while(j<array.size()){
			LinkedList<Edge<T,K>> list = adj.get(array.get(j));
			count = count + list.size();
			j++;
		}
		if(direct != true)count = count/2;
		return count;
	}
	
	//It returns the weight of a specific edge of the graph (or null if the edge does not exist)
	public K edgeWeight(T st, T en){
		if(containsEdge(st, en)){
			LinkedList<Edge<T,K>> list = adj.get(st);
			int i = 0;
			for(Edge<T,K> e : list){
				if(e.end.equals(en))return e.weight;
				i++;
			}
		}
		return null;
	}
	
	//It returns an ArrayList of all the node of the graph
	public ArrayList<T> allNode(){
		ArrayList<T> res = new ArrayList<T>(numNode());
		int i = 0;
		while(i<array.size()){
			res.add(array.get(i));
			i++;
		}
		return res;
	}
	
		
	//It returns an ArrayList of all the edges of the graph, calling two different methods for the two types of graph
	public ArrayList<UserEdge<T,K>> allEdge(){
		if(direct == true)return allEdgeDirect();
		else return allEdgeUndirect();
		
	}
	
	//It returns all the edge of an undirect graph, using an hashmap to avoid inserting the same edge two times
	private ArrayList<UserEdge<T,K>> allEdgeUndirect(){
		HashMap<T, Integer> map = new HashMap<T, Integer>();
		int i = 0;
		ArrayList<UserEdge<T,K>> res = new ArrayList<UserEdge<T,K>>(numEdge());
		while(i<array.size()){
			T node = array.get(i);
			LinkedList<Edge<T,K>> list = adj.get(node);
			int j = 0;
			for(Edge<T,K> e : list){
				if(map.get(e.end)==null){
					res.add(new UserEdge(node, e.end, e.weight));
				}
			}
			map.put(node, i);
			i++;
		}
		return res;
	}
	
	//It returns all the edge of a direct graph
	private ArrayList<UserEdge<T,K>> allEdgeDirect(){
		int i = 0;
		ArrayList<UserEdge<T,K>> res = new ArrayList<UserEdge<T,K>>(numEdge());
		while(i<array.size()){
			T node = array.get(i);
			LinkedList<Edge<T,K>> list = adj.get(node);
			int j = 0;
			for(Edge<T,K> e : list){
				res.add(new UserEdge(node, e.end, e.weight));
			}
			i++;
		}
		return res;
	}
	
	//It returns an ArrayList of all the adjacent nodes of the given node
	public ArrayList<T> adjNode(T node){
		LinkedList<Edge<T,K>> list = adj.get(node); 
		int i = 0;
		ArrayList<T> res = new ArrayList<T>(list.size());
		for(Edge<T,K> edge : list){
			res.add(edge.end);
		}
		return res;
	}
}

