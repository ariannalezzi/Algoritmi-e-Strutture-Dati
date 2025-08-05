package dijkstra;

import java.util.HashMap;

//Class used to store the informations of each node calculated by Dikstra Alghoritm
class Result<T,K>{
	public K dist;
	public T parent;
	
	public Result(K d, T p){
		dist = d;
		parent = p;
	}
}
	
//Class used to obtain the information calculated by Dijkstra Alghortim
public class DijkstraResult<T,K>{
	private HashMap<T,Result<T,K>> map;		//Internal HashMap that associate evry node to its object of the class Result
	
	//constructor
	public DijkstraResult(){
		map = new HashMap<T,Result<T,K>>();
	}
	
	//Set values calculated by Dijkstra for a node
	public void addResult(T key, K dist, T parent){
		map.put(key, new Result<T,K>(dist, parent));
	}
	
	//It returns the parent of a node in the minimum path
	public T getParent(T elem){
		return (map.get(elem)).parent;
	}
	
	//It returns the weight of the minimum path from the source to the given node
	public K getDistance(T elem){
		return (map.get(elem)).dist;
	}
}