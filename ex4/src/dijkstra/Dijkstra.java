package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import minimumheap.*;
import graph.Graph;

//Internal class used to store each node with its information in the heap used by Dijkstra Alghoritm
class Node<T,K>{
	public T vertex;
	public K dist;
	public T parent;
	
	public Node(T v, K d, T p){		//constructor
		vertex = v;
		dist = d;
		parent = p;
	}
}

//Class used to compare nodes in the heap
class NodeComparator<T,K> implements Comparator<Node<T,K>>{
	private Comparator<K> comp;									//Object of the class used to compare the "dist" attribute of the nodes
	
	public NodeComparator(Comparator<K> c){					//Constructor
		comp = c;
	}
	
	@Override
	public int compare(Node<T,K> n1, Node<T,K> n2){		//Nodes comparison is based on attribute dist comparison
		return comp.compare(n1.dist, n2.dist);
	}
	
}

//Class used to decrease nodes in the heap
class NodeDecreaser<T,K> implements HeapDecreaser<Node<T,K>>{
	public K diff;												//Object used to set the new dist value of a node
	
	//Based on how Dijkstra Alghoritm works, dist is set to the value of diff only when dist> diff, so this is always a decrease
	@Override	  
	public Node<T,K> decrease(Node<T,K> n){						
		n.dist = diff;
		return n;
	}
}

//Class used to implements the alghoritm
public class Dijkstra<T,K>{
	private Graph<T,K> graph;					//Graph on which applicate the alghoritm
	private T source;							//Source of the graph
	private HashMap<T, Node<T,K>> map;			//HashMam that associates to every vertex of the graph its object of the Node class
	private MinimumHeap<Node<T,K>> heap;		//MinimumHeap used by the Dijkstra alghoritm
	private NodeDecreaser<T,K> dec;				//Object used to decrease elements in the heap
	private NodeComparator<T,K> comp;			//Object used to compare elements in the heap
	private K min;								//Minimum value of the generic type K (used to initialize the Nodes)
	private K max;								//Maximum value of the generic type K (used to initialize the Nodes)
	private DijkstraIncreaser<K> inc;			//Object used to sum elements of generic type K
	private Comparator<K> weightcomp;			/*Object used to compare elements of generic type K 
												(It's also internally used by NodeDecreaser)*/
	
	//Constructor. It needs the graph, the source and object used to work with the generic type K (min, max, comparator and increaser)
	public Dijkstra(Graph<T,K> g, T s, Comparator<K> c, K mi, K ma, DijkstraIncreaser<K> i){
		graph = g;
		source = s;
		map = new HashMap<T, Node<T,K>>();
		dec = new NodeDecreaser<T,K>();
		weightcomp = c;
		comp = new NodeComparator<T,K>(weightcomp);
		heap = new MinimumHeap<Node<T,K>>(comp, dec);
		min = mi;
		max = ma;
		inc = i;
	}
	
	//It calls a method to initialized the nodes and than it calculates the dijkstra alghoritm
	public DijkstraResult<T,K> dijkstra(){
		init();
		while(heap.size()>0){
			Node<T,K> u = heap.extract();
			ArrayList<T> adj = graph.adjNode(u.vertex);
			for(int j = 0; j<adj.size(); j++){
				Node<T,K> v = map.get(adj.get(j));
				if((heap.contains(v)==true) && (weightcomp.compare(inc.increase(u.dist ,graph.edgeWeight(u.vertex,v.vertex)), v.dist)<0)){
					dec.diff = inc.increase(u.dist ,graph.edgeWeight(u.vertex,v.vertex));
					heap.decrease(v);
					v.parent = u.vertex;
				} 
			}
		}
		return return_map();
	}
	
	/*The distance attribute of evry node is set to the K max value, except for the Source Node, that is set to the min value.
	Every node is inserted in the heap and in the hash map*/
	private void init(){
		ArrayList<T> array = graph.allNode();
		int i = 0;
		while(i<array.size()){
			T vertex;
			Node<T,K> node;
			if(array.get(i).equals(source)){
				vertex = array.get(i);
				node = new Node<T,K>(vertex, min, null);
			}else{
				vertex = array.get(i);
				node = new Node<T,K>(vertex, max, null);
			}
			map.put(vertex, node);
			heap.insert(node);
			i++;
		}
	}	
	
	//It creates and initializes the return object of the alghoritm (class DijkstraResult)
	private DijkstraResult<T, K> return_map(){
		ArrayList<T> array = graph.allNode();
		DijkstraResult<T,K> res = new DijkstraResult<T,K>();
		int i = 0;
		while(i<array.size()){
			Node<T,K> a = map.get(array.get(i));
			res.addResult(a.vertex, a.dist, a.parent);
			i++;
		}
		return res;
	}
	
}