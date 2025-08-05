package minimumheap;

import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

//Class that implements tests on MinimumHeap Class
public class MinimumHeapTests {
  
  //Class used to test the heap with a user defined class
  class Trial{
	  private int id;
	  private String msg;
	  
	  public Trial(int a, String b){	//constructor
		  id = a;
		  msg = b;
	  }
	  
	  @Override
	  public String toString(){
		  return toString();
	  }
  }
   
  //Class used to compare elemement of Trial class
  class TrialComparator implements Comparator<Trial>{
	   
	   @Override
	   public int compare(Trial t1, Trial t2){
		   Integer a = t1.id;
		   Integer b = t2.id;
		   return a.compareTo(b);
	   }
  }
  
  //Class used to decrease elemement of Trial class
  class TrialDecreaser implements HeapDecreaser<Trial>{
	  
	  public Trial decrease(Trial a){
		  a.id = a.id-1;
		  return a;
	  }
  }
  
  //Class used to compare integers
  class IntegerDecreaser implements HeapDecreaser<Integer>{
	  
	  public Integer decrease(Integer i){
		  i = i-1;
		  return i;
	  }
  }
  
  //Class used to decrease integers
  class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1.compareTo(i2);
    }
  }
  
  private Integer i1, i2, i3, i4;				//Elements used to test the heap with integers
  private Trial t1,t2,t3,t4,t5,t6,t7,t8;		//Elements used to test the heap with Trial class
  private MinimumHeap<Trial> minHeapTrial;		//Heap of elements of the Trial class
  private MinimumHeap<Integer> minHeap;			//Heap of Integers
  
  //Method called before evry test to initialized the heaps and the elements
  @Before
  @SuppressWarnings("deprecation")
  public void createMinHeap(){
    i1 = new Integer(12);
    i2 = new Integer(0);
    i3 = new Integer(-4);
	i4 = new Integer(12);
	
	t1 = new Trial(12, "trial1");
	t2 = new Trial(12, "trial2");
	t3 = new Trial(12, "trial3");
	t4 = new Trial(12, "trial4");
	t5 = new Trial(12, "trial5");
	t6 = new Trial(12, "trial6");
	t7 = new Trial(12, "trial7");
	t8 = new Trial(12, "trial8");
	
    minHeap = new MinimumHeap<Integer>(new IntegerComparator(), new IntegerDecreaser());
	minHeapTrial = new MinimumHeap<Trial>(new TrialComparator(), new TrialDecreaser());
  }
  
  //It checks the size method when the heap is empty
  @Test
  public void checkSizeZero(){
	  assertTrue(minHeap.size()==0);
  }
  
  //It checks the size method when the heap is not empty on integers
  @Test
  public void checkSizeFour(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  assertEquals(4, minHeap.size());
  }
  
  //It checks the rightChild method on integers
  @Test 
  public void i3RightCheck(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  assertEquals(i2, minHeap.rightChild(i3));
  }
  
  //It checks the leftChild method when the result is null on integers
 @Test 
  public void i4Check(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  assertNull(minHeap.leftChild(i4));
  }
  
  //It checks the rightChild method on integers
  @Test 
  public void i3LeftCheck(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  assertSame(i1, minHeap.leftChild(i3));
  }
  
  //It checks the parent method on integers
  @Test 
  public void i4ParentCheck(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  assertEquals(i1, minHeap.parent(i4));
  }
  
  //It checks the decrease method on integers
  @Test 
  public void checkDecrease(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  i4 = minHeap.decrease(i4);
	  assertEquals(i4, minHeap.parent(i1));
  }
  
  //It checks the size method after an extract method is called on integers
   @Test
   public void checkExtractSize(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  Integer x = minHeap.extract();
	  assertEquals(3, minHeap.size());
  }
  
  //It checks the return value of extact method on integers
  @Test
  public void checkExtractValue(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  assertEquals(i3, minHeap.extract());
  }
  
  //It checks the size method after an extract method is called
  @Test
  public void checkExtractNewHeap(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  Integer x = minHeap.extract();
	  assertEquals(i2, minHeap.parent(i1));
  }
  
  //It checks the size method called on the same element more than one time on integers
  @Test
  public void checkSameInsertion(){
	  minHeap.insert(i1);
	  minHeap.insert(i2);
	  minHeap.insert(i3);
	  minHeap.insert(i4);
	  minHeap.insert(i4);
	  assertEquals(4, minHeap.size());
  }
  
  //It checks the insert method after a decrease method on Trial elements
  @Test
  public void checkSameInsertionTrial(){
	  minHeapTrial.insert(t1);
	  minHeapTrial.insert(t2);
	  minHeapTrial.insert(t3);
	  minHeapTrial.insert(t4);
	  t4 = minHeapTrial.decrease(t4);
	  minHeapTrial.insert(t4);
	  assertEquals(4, minHeapTrial.size());
  }
  
  //It checks the decrease and extract method on Trial elements
  @Test 
  public void checkDecreaseTrial(){
	  minHeapTrial.insert(t1);
	  minHeapTrial.insert(t2);
	  minHeapTrial.insert(t3);
	  minHeapTrial.insert(t4);
	  minHeapTrial.insert(t5);
	  minHeapTrial.insert(t6);
	  minHeapTrial.insert(t7);
	  minHeapTrial.insert(t8);
	  minHeapTrial.decrease(t8);
	  assertEquals(t8, minHeapTrial.extract());
  }
  
  
}
  
  