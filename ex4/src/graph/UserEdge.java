package graph;

//Support class to return to the user of a graph its edges
public class UserEdge<T,K>{
	public T start;
	public T end;
	public K weight;
	
	public UserEdge(T s, T e, K w){	//constructor
		start = s;
		end = e;
		weight = w;
	}

}