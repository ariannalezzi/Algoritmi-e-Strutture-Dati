package dijkstra;;

//Interface used to implement methods to sum elements of generic type in Dijkstra class
public interface DijkstraIncreaser<T>{
	
	public T increase(T elem1, T elem2);
}