import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import GUI.GraphFrame;


public class Main {
	
	public static void main(String args[]) {
		
		Node node1 = new Node(1, 2);
		Node node2 = new Node(2, 1, 3, 4);
		Node node3 = new Node(3, 2, 4);
		Node node4 = new Node(4, 2, 3);
		Node node5 = new Node(5);
		
		Graph graph = new Graph();
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		graph.addNode(node5);
		
		List<Node> order = new ArrayList<Node>();
		order.add(node1);
		order.add(node2);
		order.add(node3);
		order.add(node4);
		order.add(node5);
		
		System.out.println(graph.toString());
		System.out.println(graph.getIndependentSet(order).toString());
		System.out.println(graph.toString(false));
		
		new GraphFrame();
	}
	
}
