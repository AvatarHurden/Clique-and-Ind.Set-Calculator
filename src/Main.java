import java.util.ArrayList;
import java.util.List;


public class Main {
	
	public static void main(String args[]) {
		
		Node node1 = new Node(1, 2);
		Node node2 = new Node(2, 1, 3, 4);
		Node node3 = new Node(3, 2);
		Node node4 = new Node(4, 2);
		Node node5 = new Node(5);
		
		Graph graph = new Graph();
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		graph.addNode(node5);
		
		List<Node> order = new ArrayList<Node>();
		order.add(node2);
		order.add(node1);
		order.add(node3);
		order.add(node4);
		order.add(node5);
		
		System.out.println(graph.getSize());
		System.out.println(graph.getIndependentSetSize(order));
		System.out.println(graph.getSize());
	}
	
}
