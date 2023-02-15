import java.io.*;

// Class DelivA does the work for deliverable DelivA of the Prog340

public class DelivA {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivA( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		System.out.println("There are " + g.nodeList.size() + " nodes");
		System.out.println("There are " + g.edgeList.size() + " edges");
		output.println("There are " + g.nodeList.size() + " nodes");
		output.println("There are " + g.edgeList.size() + " edges");
		int max = 1;
		for(Node n:g.getNodeList()){
			max = Math.max(max, n.getOutgoingEdges().size());
		}
		for(Node n:g.getNodeList()){
			if(n.getOutgoingEdges().size() == max){
				System.out.println("Node " + n.getAbbrev() + " has the Maximum number of edges at " + max + " outgoing edges");
				output.println("Node " + n.getAbbrev() + " has the Maximum number of edges at " + max + " outgoing edges");
			}
		}
		int maxEdgeLength = 0;
		for(Edge e : g.getEdgeList()){
			if(e.getDist() > maxEdgeLength){
				maxEdgeLength = e.getDist();
			}
		}
		for(Edge e : g.getEdgeList()){
			if(e.getDist() == maxEdgeLength){
				System.out.println("The longest edge has a value of " + maxEdgeLength + " and runs from " + e.getHead().getAbbrev() + " to " + e.getTail().getAbbrev());
				output.println("The longest edge has a value of " + maxEdgeLength + " and runs from " + e.getHead().getAbbrev() + " to " + e.getTail().getAbbrev());
			}
		}
		int minEdgeLength = 0;
		int mimEdgeLength = maxEdgeLength;
		for(Edge e : g.getEdgeList()){
			if(e.getDist() < mimEdgeLength){
				minEdgeLength = e.getDist();
			}
		}
		for(Edge e : g.getEdgeList()){
			if(e.getDist() == minEdgeLength){
				System.out.println("The shortest edge has a value of " + minEdgeLength + " and runs from " + e.getHead().getAbbrev() + " to " + e.getTail().getAbbrev());
				output.println("The shortest edge has a value of " + minEdgeLength + " and runs from " + e.getHead().getAbbrev() + " to " + e.getTail().getAbbrev());
			}
		}
		output.flush();
	}

}
