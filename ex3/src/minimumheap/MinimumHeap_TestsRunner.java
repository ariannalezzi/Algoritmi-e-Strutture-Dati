package minimumheap;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//Class used to run the tests on the MinimumHeap class
public class MinimumHeap_TestsRunner {
	
	//It runs the tests and print the results
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(MinimumHeapTests.class);
		for (Failure failure : result.getFailures()) {
		System.out.println(failure.toString());
		}
  
		System.out.println(result.wasSuccessful());    
	}
  
}