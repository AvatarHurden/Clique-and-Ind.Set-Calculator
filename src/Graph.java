import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Graph {
	
	private List<Node> nodes;
	
	public Graph() {
		nodes = new ArrayList<Node>(); 
	}
	
	public Graph(Graph graph) {
		this.nodes = new ArrayList<Node>(graph.nodes);
	}
	
	/**
	 * Descobre o tamanho do conjunto independente formado por esse grafo ao remover
	 * os vizinhos dos nodos na ordem determinada pela lista passada como parâmetro.
	 * 
	 * @param order para remover os vizinhos
	 * @return tamanho do C.I.
	 */
	public int getIndependentSetSize(List<Node> order) {
		Graph temp = new Graph(this);
		
		for (Node n : order)
			temp.removeNeighbors(n);
		
		return temp.getSize();
	}
	
	public void addNode(Node node) {
		if (!nodes.contains(node))
			nodes.add(node);
	}
	
	public void removeNeighbors(Node node) {
		if (!nodes.contains(node))
			return;
		
		Iterator<Node> iter = nodes.iterator();
		while (iter.hasNext())
			if (node.isNeighbor(iter.next()))
				iter.remove();
	}
	
	public int getSize() {
		return nodes.size();
	}
	
}
