package minimumheap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

//Class used to wrap the elements given to the heap (used to override equals method, using reference equality)
class Wrap<T>{
	public T value;
	
	public Wrap(T elem){
		value = elem;
	}
	
	@Override 
	public boolean equals(Object O){
		if(O instanceof Wrap){
			@SuppressWarnings("unchecked") Wrap<T> a = (Wrap<T>) O; 
			return this.value==a.value;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return value.hashCode();
	}
}

public class MinimumHeap<T>{
	private ArrayList<T> array = null;				//array of the elements in the heap
	private Comparator<T> comparator = null;		//object used to compare elements
	private HashMap<Wrap<T>, Integer> hash = null;	//hash used to obtain the array index of an element in O(1)
	private HeapDecreaser<T> dec = null;			//object used to decrease elements
	
	
	public MinimumHeap(Comparator<T> comparator, HeapDecreaser<T> decreaser){ //constructor
		this.array = new ArrayList<T>();
        this.comparator = comparator;
		this.hash = new HashMap<Wrap<T>, Integer>();
		this.dec = decreaser;
	}
	
	//It swaps two element of the heap
	private void swap(T elem1, T elem2){
		Integer i = hash.get(new Wrap<T>(elem1));
		Integer j = hash.get(new Wrap<T>(elem2));
		(this.array).set(i, elem2);
		(this.array).set(j, elem1);
		(this.hash).put(new Wrap<T>(elem2), i);
		(this.hash).put(new Wrap<T>(elem1), j);
	}
	
	//It returns the size of the heap
	public int size(){
		return (this.array).size();
	}
	
	//It returns the parent of the element if it exists, if not null
	public T parent(T elem){
		Integer i = hash.get(new Wrap<T>(elem));
		if(i!=null){
			int j = ((i+1)/2) -1;
			if(j>=0){
				return this.array.get(j);
			}
		}
		return null;
	}
	
	//It returns the left child of the element if it exists, if not null
	public T leftChild(T elem){
		Integer i = hash.get(new Wrap<T>(elem));
		if(i!=null){
			int j = 2*i +1;
			if(j<this.size()){
				return this.array.get(j);
			}
		}
		return null;
	}
	
	//It returns the right child of the element if it exists, if not null
	public T rightChild(T elem){
		Integer i = hash.get(new Wrap<T>(elem));
		if(i!=null){
			int j = 2*i +2;
			if(j<this.size()){
				return this.array.get(j);
			}
		}
		return null;
	}
	
	//It inserts a new element in the heap, if it is not already in the heap (in this case it returns false)
	public boolean insert(T elem){  
		if(hash.get(new Wrap<T>(elem))==null){
			(this.array).add(elem);
			(this.hash).put(new Wrap<T>(elem), array.size()-1);
			while(parent(elem)!=null && (this.comparator).compare(elem, parent(elem)) < 0){
				swap(elem, parent(elem));
			}
			return true;
		}
		return false;
	}
	
	//It extracts the first element of the heap (the minimum) and calls a method to rebuild the heap 
	public T extract(){
		if(size()==0){
			return null;
		}else{
			T elem = array.get(0);
			(this.array).set(0, (this.array).get(this.size()-1));
			hash.put(new Wrap<T>(array.get(0)), 0);
			hash.remove(new Wrap<T>(elem));
			(this.array).remove(this.size()-1);
			if(size()>0)this.heapify(0);
			return elem;
		}
	}
	
	//Support method to calculate the minimum of two elements
	private T min(T elem1, T elem2){
		if(elem1 == null && elem2 !=null)return elem2;
		else if(elem1 !=null && elem2==null)return elem1;
		else if(elem1 == null && elem2 == null)return elem1;
		else if(comparator.compare(elem1,elem2)<=0)return elem1;
		else return elem2;
	}
	
	//Method that rebuilts the heap after an extraction
	private void heapify(int i){
		T elem = array.get(i);
		int m = hash.get(new Wrap<T>(min(elem,min(rightChild(elem), leftChild(elem)))));
		if(m!=i){
			swap(elem, array.get(m));
			heapify(m);
		}
	}
	
	//It decrease an elements of the heap and it rebuilts the heap if necessary and return the decreased element
	public T decrease(T elem){
		Integer i = hash.get(new Wrap<T>(elem));
		if(i == null){
			return null;
		}
		hash.remove(new Wrap<T>(elem));
		elem = dec.decrease(elem);
		array.set(i,elem);
		hash.put(new Wrap<T>(elem), i);
		while(parent(elem)!=null && comparator.compare(parent(elem), elem)>0){
			swap(elem, parent(elem));
		}
		return elem;
	}
	
	//It returns true if the element is already in the heap
	public boolean contains(T elem){
		return hash.get(new Wrap<T>(elem))!=null;
	}
	
}

