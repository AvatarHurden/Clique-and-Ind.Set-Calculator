import java.util.List;


public class Graph {
	
	List<Node> nodes;
	
	public Graph() {};
	
	public void addNode(Node node) {
		if (!nodes.contains(node))
			nodes.add(node);
	}
	
}
