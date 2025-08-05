package graphusage;

import minimumheap.*;
import graph.Graph;
import dijkstra.*;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.util.Comparator;
import java.io.IOException;

//Class used to compare floats in the Dijkstra class 
class FloatComparator implements Comparator<Float>{
	
	@Override
	public int compare(Float f1, Float f2){
		return Float.compare(f1,f2);
	}
}

//Class used to sum floats in the Dijkstra class 
class FloatIncreaser implements DijkstraIncreaser<Float>{
	
	@Override
	public Float increase(Float f1, Float f2){
		return f1+f2;
	}
}

//Class used to calculate the minimum path between a source and a specific destination (String with float distances)
public class GraphUsage{
	
	//It creates the graph reading edges from an input file .csv passed by argument of the main
	private static Graph<String,Float> createGraph(String filepath){
		Graph<String, Float> graph = new Graph<>("u");
		Path inputFilePath = Paths.get(filepath);
		try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath)){
			String line = null;
			while((line = fileInputReader.readLine()) != null){
				String [] lineElements = line.split(",");
				graph.addEdge(lineElements[0], lineElements[1], Float.parseFloat(lineElements[2]));
			}
		}catch(IOException e){e.printStackTrace();}
		return graph;
	}
	
	//It prints the path from the source to the destination using the information about the nodes parents provided by Dijkstra Alghoritm
	private static void printPath(Graph<String, Float> graph, DijkstraResult<String,Float> res, String target){
		String parent = res.getParent(target);
		ArrayList<String> array = new ArrayList<String>();
		array.add(target);
		while(parent != null){
			array.add(parent);
			parent = res.getParent(parent);			
		}
		System.out.println("Path: ");
		for(int i = array.size()-1; i>0; i--){
			System.out.print(array.get(i) + " - ");
		}
		System.out.println(array.get(0));
	}
	
	/*It calls the method to create the graph, it create an object of the Dijkstra class to use the alghoritm and it 
	calls the method to print information about the minimum path between the source and the destination*/
	public static void main(String[] args){
		if (args.length < 3){
			System.out.println("Usage: ");
			System.out.println("First argument indicates the csv file that will be used to create the Graph. ");
			System.out.println("Second argument indicates the source for the Dijkstra Alghoritm. ");
			System.out.print("Third argument indicates a specific Node of the graph. ");
			System.out.println("The programm will print on the standard output the minimum path of the specified node.");
			System.out.println("Example: java -jar Graph.jar graph.csv source target");			
		}else{
			Graph<String, Float> graph = createGraph(args[0].toLowerCase());
			@SuppressWarnings("deprecation")
			Dijkstra<String, Float> d = new Dijkstra<String, Float>(graph, args[1].toLowerCase(), new FloatComparator(), new Float(0), new Float(Float.MAX_VALUE), new FloatIncreaser());
			DijkstraResult<String, Float> res = d.dijkstra();
			System.out.println("Cammino minimo da " + args[1] + " a " + args[2] + ": " + (res.getDistance(args[2].toLowerCase()))/1000 + " Km");
			printPath(graph, res, args[2].toLowerCase());	
		}
	}
			
}
