package minimumheap;

//Interface used to implements specific classe to decrease elements of the MinimumHeap
public interface HeapDecreaser<T>{
	
	public T decrease(T elem);
}
