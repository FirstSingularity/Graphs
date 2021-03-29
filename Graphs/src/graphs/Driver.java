package graphs;

import tests.TestingSupport;

public class Driver {

	public static void main(String[] args) {
		Graph<Integer> graph = new Graph<>();
		
		String[] vertices = new String[] { "A", "B", "C", "D", "E", "F", "G" };
		for (int i = 0; i < vertices.length; i++) {
			graph.addVertex(vertices[i], 5);
		}
		
		graph.addDirectedEdge("A", "B", 1);
		graph.addDirectedEdge("B", "A", 1);
		graph.addDirectedEdge("A", "E", 1);
		graph.addDirectedEdge("E", "A", 1);
		graph.addDirectedEdge("B", "E", 1);
		graph.addDirectedEdge("E", "B", 1);
		graph.addDirectedEdge("E", "D", 1);
		graph.addDirectedEdge("D", "E", 1);
		graph.addDirectedEdge("B", "F", 1);
		graph.addDirectedEdge("F", "B", 1);
		graph.addDirectedEdge("F", "C", 1);
		graph.addDirectedEdge("C", "F", 1);
		graph.addDirectedEdge("C", "G", 1);
		graph.addDirectedEdge("G", "C", 1);
		graph.addDirectedEdge("E", "G", 1);
		graph.addDirectedEdge("G", "E", 1);
		
		StringBuffer results = new StringBuffer();
		PrintCallBack<Integer> printCallBack = new PrintCallBack<Integer>();
		graph.doDepthFirstSearch("A", printCallBack);
		results.append(printCallBack.getResult());
		
		results.append(TestingSupport.hardCodingPrevention);
		
		System.out.println(results.toString());
	}

}
