package main;
import java.util.ArrayList;
import java.util.List;

import GUI.GraphFrame;


public class Main {
	
	public static void main(String args[]) {
		
		Node node1 = new Node(1, 3, 9);
		Node node2 = new Node(2, 4, 6);
		Node node3 = new Node(3, 1, 9);
		Node node4 = new Node(4, 2, 9);
		Node node5 = new Node(5, 9);
		Node node6 = new Node(6, 2, 8);
		Node node7 = new Node(7, 9);
		Node node8 = new Node(8, 6, 9);
		Node node9 = new Node(9, 1, 3, 4, 5, 7, 8);
		Node node10 = new Node(10);
		Node node11 = new Node(11, 1, 3, 4, 5, 7, 8);
		Node node12 = new Node(12, 1, 3, 4, 5, 7, 8);
		
		Graph graph = new Graph();
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		graph.addNode(node5);
		graph.addNode(node6);
		graph.addNode(node7);
		graph.addNode(node8);
		graph.addNode(node9);
		graph.addNode(node10);
		
		List<Node> order = new ArrayList<Node>();
		order.add(node1);
		order.add(node2);
		order.add(node3);
		order.add(node4);
		order.add(node5);
		order.add(node6);
		//order.add(node7);
		//order.add(node8);
		//order.add(node9);
		//order.add(node10);
		
		
		Permutations per = new Permutations();
		
		System.out.println("Starting");
		
		ArrayList<Graph> allMaximals = graph.getMaximalIS();
		System.out.println(allMaximals);
		
//		ArrayList<Node[]> permutaions = (per.permutations(order.toArray(new Node[]{})));
		
		System.out.println("Permuted");
		
		Graph largest = null;
//		for (Node[] array : permutaions) {
//			Graph temp = graph.getIndependentSet(array);
//			if (largest == null || largest.getSize() < temp.getSize())
//				largest = temp;
//		}
		
//		System.out.println(largest.toString(false));
		
		new GraphFrame();
	}
	
}
