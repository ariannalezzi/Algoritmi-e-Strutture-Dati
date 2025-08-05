package graph;

import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;


//Class used to test the Graph
public class GraphTests{
	private Integer i1, i2, i3, i4;				//Elements of the graph
	private Integer w12, w23, w24;				//Weight of edges of the graph
	private Graph<Integer, Integer> graphD;		//Direct graph
	private Graph<Integer, Integer> graphU;		//Undirect graph

	//Method called before every test
	@Before
	public void createGraph(){
		i1 = 1;
		i2 = 2;
		i3 = 3;
		i4 = 4;
		w12 = 12;
		w23 = 20;
		w24 = 7;
		
		graphD = new Graph<>("d");
		graphU = new Graph<>("u");
	}
	
	//It checks containsNode method on direct graph
	@Test
	public void addNodeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		assertEquals(true, graphD.containsNode(i4));
	}
	
	//It checks containsNode method on undirect graph
	@Test
	public void addNodeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		assertEquals(true, graphU.containsNode(i4));
	}
	
	//It checks deleteNode method on direct graph
	@Test
	public void deleteNodeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i2, i4, w24);
		graphD.addEdge(i1, i2, w12);
		graphD.deleteNode(i2);
		assertEquals(false, graphD.deleteNode(i2));
	}
	
	//It checks deleteNode method on undirect graph
	@Test
	public void deleteNodeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i2, i4, w24);
		graphU.addEdge(i1, i2, w12);
		graphU.deleteNode(i2);
		assertEquals(0, graphU.numEdge());
	}
	
	//It checks addEdge method on undirect graph
	@Test
	public void addEdgeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		assertEquals(true, graphU.containsEdge(i2, i1));
	}
	
	
	//It checks addEdge method on direct graph
	@Test
	public void addEdgeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		assertEquals(false, graphD.containsEdge(i2, i1));
	}
	
	//It checks numEdge method on undirect graph
	@Test
	public void numEdgeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		graphU.addEdge(i2, i3, w23);
		graphU.addEdge(i2, i4, w24);
		graphU.addEdge(i4, i2, w24);		
		assertEquals(3, graphU.numEdge());
	}
	
	//It checks numEdge method on direct graph
	@Test
	public void numEdgeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		graphD.addEdge(i2, i3, w23);
		graphD.addEdge(i2, i4, w24);
		graphD.addEdge(i4, i2, w24);
		assertEquals(4, graphD.numEdge());
	}
	
	//It checks edgeWeight method on undirect graph
	@Test
	public void edgeWeightU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		graphU.addEdge(i2, i3, w23);
		graphU.addEdge(i2, i4, w24);	
		assertEquals(w24, graphU.edgeWeight(i4, i2));
	}
	
	//It checks edgeWeight method on direct graph
	@Test
	public void edgeWeightD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		graphD.addEdge(i2, i3, w23);
		graphD.addEdge(i2, i4, w24);
		assertEquals(null, graphD.edgeWeight(i4, i2));
	}
	
	//It checks deleteEdge method on undirect graph
	@Test
	public void deleteEdgeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		graphU.addEdge(i2, i3, w23);
		graphU.addEdge(i2, i4, w24);
		graphU.addEdge(i4, i2, w24);
		graphU.deleteEdge(i4,i2);
		assertEquals(false, graphU.deleteEdge(i2, i4));
	}
	
	
	//It checks deleteEdge method on direct graph
	@Test
	public void deleteEdgeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		graphD.addEdge(i2, i4, w23);
		graphD.addEdge(i4, i2, w24);
		graphD.deleteEdge(i2,i4);
		assertEquals(true, graphD.containsEdge(i4, i2));
	}
	
	//It checks allEdge method on direct graph
	@Test
	public void allEdgeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		graphD.addEdge(i2, i3, w23);
		graphD.addEdge(i2, i4, w24);
		ArrayList<UserEdge<Integer,Integer>> array = graphD.allEdge();
		assertEquals(3, array.size());
	}
	
	//It checks allEdge method on undirect graph
	@Test
	public void allEdgeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		graphU.addEdge(i2, i3, w23);
		graphU.addEdge(i2, i4, w24);
		ArrayList<UserEdge<Integer,Integer>> array = graphU.allEdge();
		assertEquals(3, array.size());
	}
	
	//It checks adjNode method on direct graph
	@Test
	public void adjNodeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		graphD.addEdge(i2, i1, w12);
		graphD.addEdge(i2, i4, w24);
		ArrayList<Integer> array = graphD.adjNode(i2);
		assertEquals(2, array.size());
	}
	
	//It checks adjNode method on undirect graph
	@Test
	public void adjNodeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		graphU.addEdge(i2, i3, w23);
		graphU.addEdge(i2, i4, w24);
		ArrayList<Integer> array = graphU.adjNode(i2);
		assertEquals(true, array.contains(i1));
	}
	
	//It checks allNode method on direct graph
	@Test
	public void allNodeD(){
		graphD.addNode(i1);
		graphD.addNode(i2);
		graphD.addNode(i3);
		graphD.addNode(i4);
		graphD.addEdge(i1, i2, w12);
		graphD.addEdge(i2, i3, w23);
		graphD.addEdge(i2, i4, w24);
		ArrayList<Integer> array = graphD.allNode();
		assertEquals(4, array.size());
	}
	
	//It checks allNode method on undirect graph
	@Test
	public void allNodeU(){
		graphU.addNode(i1);
		graphU.addNode(i2);
		graphU.addNode(i3);
		graphU.addNode(i4);
		graphU.addEdge(i1, i2, w12);
		graphU.addEdge(i2, i3, w23);
		graphU.addEdge(i2, i4, w24);
		ArrayList<Integer> array = graphU.allNode();
		assertEquals(4, array.size());
	}
}
