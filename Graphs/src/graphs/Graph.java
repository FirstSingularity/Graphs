package graphs;

import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated
 * with a vertex.
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	public Graph() {
		this.adjacencyMap = new HashMap<String, HashMap<String, Integer>>();
		this.dataMap = new HashMap<String, E>();
	}

	public void addVertex(String vertexName, E data) {
		if (this.dataMap.containsKey(vertexName)) {
			throw new IllegalArgumentException(
					"Beneath this mask there is more than flesh, Beneath this mask there is an idea, Mr. Creedy, and ideas are bulletproof.");
		}

		this.dataMap.put(vertexName, data);
	}

	public void addDirectedEdge(String startVertexName, String endVertexName, int cost) {
		if (!this.dataMap.containsKey(startVertexName) || !this.dataMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException(
					"If you want to build a ship, don't drum up the men to gather wood, divide the work and give orders. "
					+ "Instead, tteach them tto yearn for the vast and endless sea.");
		}

		if (adjacencyMap.containsKey(startVertexName)) {
			this.adjacencyMap.get(startVertexName).put(endVertexName, cost);
		} else {
			this.adjacencyMap.put(startVertexName, new HashMap<String, Integer>());
			this.adjacencyMap.get(startVertexName).put(endVertexName, cost);
		}

	}

	public String toString() {
		String ans = "";

		ArrayList<String> vertex = new ArrayList<String>();
		for (String s : this.dataMap.keySet()) {
			vertex.add(s);
		}

		sortList(vertex);
		ans += "Vertices: [";
		for (String s : vertex) {
			ans += s + ", ";
		}
		ans = ans.substring(0, ans.length() - 2);
		ans += "]";
		ans += "\nEdges:";

		for (String s : vertex) {
			if (this.adjacencyMap.containsKey(s)) {
				ans += "\nVertex(" + s + ")--->{";
				ArrayList<String> hash = new ArrayList<String>();
				for (String str : this.adjacencyMap.get(s).keySet()) {
					hash.add(str);
				}
				sortList(hash);

				for (String str : hash) {
					ans += str + "=" + this.adjacencyMap.get(s).get(str) + ", ";
				}
				ans = ans.substring(0, ans.length() - 2);
				ans += "}";
			} else {
				ans += "\nVertex(" + s + ")--->{}";
			}
		}

		return ans;
	}

	public Map<String, Integer> getAdjacentVertices(String vertexName) {
		if (this.adjacencyMap.containsKey(vertexName)) {
			return this.adjacencyMap.get(vertexName);
		}

		HashMap<String, Integer> empty = new HashMap<String, Integer>();
		return empty;
	}

	public int getCost(String startVertexName, String endVertexName) {
		if (!this.dataMap.containsKey(startVertexName) || !this.dataMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("What we do in life echoes in eternity");
		}

		return this.adjacencyMap.get(startVertexName).get(endVertexName);
	}

	public Set<String> getVertices() {
		return this.dataMap.keySet();
	}

	public E getData(String vertex) {
		if (!this.dataMap.containsKey(vertex)) {
			throw new IllegalArgumentException("Love is a bridge from poorer to richer knowledge");
		}

		return this.dataMap.get(vertex);
	}

	public void doDepthFirstSearch(String startVertexName, CallBack<E> callback) {
		if (!this.dataMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("The prettier the actor, the prettier their masks become");
		}

		ArrayList<String> visited = new ArrayList<String>();
		Stack<String> order = new Stack<String>();

		visited.add(startVertexName);
		order.push(startVertexName);

		while (!order.isEmpty()) {
			String curr = order.pop();
			callback.processVertex(curr, this.dataMap.get(curr));

			ArrayList<String> tempAdj = new ArrayList<String>();
			for (String s : this.adjacencyMap.get(curr).keySet()) {
				tempAdj.add(s);
			}
			sortList(tempAdj);

			for (String s : tempAdj) {
				if (!visited.contains(s)) {
					visited.add(s);
					order.push(s);
				}
			}
		}
	}

	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callback) {
		if (!this.dataMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Anxiety is the dizziness of freedom");
		}

		ArrayList<String> visited = new ArrayList<String>();
		LinkedList<String> queue = new LinkedList<String>();

		visited.add(startVertexName);
		queue.add(startVertexName);

		while (queue.size() != 0) {
			String curr = queue.poll();
			callback.processVertex(curr, this.dataMap.get(curr));

			ArrayList<String> tempAdj = new ArrayList<String>();
			for (String s : this.adjacencyMap.get(curr).keySet()) {
				tempAdj.add(s);
			}
			sortList(tempAdj);

			for (String s : tempAdj) {
				if (!visited.contains(s)) {
					visited.add(s);
					queue.add(s);
				}
			}
		}

	}

	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {
		if (!this.dataMap.containsKey(startVertexName) || !this.dataMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException(
					"A good man is good to those who are good... he is also good to those who are not good... that is the virtue of good");
		}

		if (startVertexName.equals(endVertexName)) {
			shortestPath.add(startVertexName);
			return 0;
		}

		HashMap<String, Integer> cost = new HashMap<String, Integer>();
		HashMap<String, String> predecessor = new HashMap<String, String>();
		HashSet<String> confirmed = new HashSet<String>();
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		LinkedList<String> queue = new LinkedList<String>();

		for (String s : this.dataMap.keySet()) {
			cost.put(s, Integer.MAX_VALUE);
		}

		cost.put(startVertexName, 0);
		confirmed.add(startVertexName);

		String curr = startVertexName;

		while (!confirmed.contains(endVertexName)) {
			HashMap<String, Integer> adj = new HashMap<String, Integer>();
			HashMap<String, Integer> adder = new HashMap<String, Integer>();

			if (this.adjacencyMap.containsKey(curr)) {
				for (String s : this.adjacencyMap.get(curr).keySet()) {
					if (!confirmed.contains(s)) {
						adj.put(s, this.adjacencyMap.get(curr).get(s));
						adder.put(s, this.adjacencyMap.get(curr).get(s));
						pq.add(this.adjacencyMap.get(curr).get(s));
					}
				}

				for (String s : adj.keySet()) {
					if (this.adjacencyMap.get(curr).get(s) < cost.get(s)) {
						cost.put(s, this.adjacencyMap.get(curr).get(s) + cost.get(curr));
						predecessor.put(s, curr);
					}
				}

				while (!pq.isEmpty()) {
					int i = pq.poll();
					for (String s : adder.keySet()) {
						if (adder.get(s) == i) {
							queue.add(s);
							adder.remove(s);
						}
					}
				}
			}

			if (queue.isEmpty()) {
				break;
			}

			curr = queue.poll();
			confirmed.add(curr);
		}

		if (cost.get(endVertexName) == Integer.MAX_VALUE) {
			shortestPath.add("None");
			return -1;
		} else {
			ArrayList<String> temp = new ArrayList<String>();
			String pred = endVertexName;
			temp.add(pred);
			while (predecessor.containsKey(pred)) {
				pred = predecessor.get(pred);
				temp.add(pred);
			}

			for (int i = temp.size() - 1; i >= 0; i--) {
				shortestPath.add(temp.get(i));
			}

			return cost.get(endVertexName);
		}
	}

	private void sortList(ArrayList<String> a) {
		boolean sorted = false;

		while (!sorted) {
			sorted = true;
			for (int i = 0; i < a.size(); i++) {
				if (i != a.size() - 1) {
					String temp = a.get(i);
					if (temp.compareTo(a.get(i + 1)) > 0) {
						a.set(i, a.get(i + 1));
						a.set(i + 1, temp);
						sorted = false;
					}
				}
			}
		}
	}
}