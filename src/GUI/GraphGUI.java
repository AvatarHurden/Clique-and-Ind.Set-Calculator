package GUI;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Graph;
import main.Node;

public class GraphGUI {
	
	private int value = 0;
	
	private List<NodeGUI> nodes;
	private List<EdgeGUI> edges;

	public GraphGUI() {
		nodes = new ArrayList<NodeGUI>();
		edges = new ArrayList<EdgeGUI>();
	}
	
	public void enableSubGraph(Graph graph) {
		for (NodeGUI node : nodes)
			if (!graph.containsNode(node.getValue()))
				node.setEnabled(false);
		
		for (EdgeGUI edge : edges)
			if (!graph.hasConnection(edge.getStart().getValue(), edge.getEnd().getValue()))
				edge.setEnabled(false);
	}
	
	/**
	 * Cria um novo GraphGUI, removendo elementos que não estejam no grafo passado como
	 * parâmetro
	 */
	public GraphGUI deriveGraphGUI(Graph graph) {
		GraphGUI graphGUI = new GraphGUI();
		
		for (NodeGUI node : nodes)
			if (graph.containsNode(node.getValue()))
				graphGUI.addNode(node);
		
		for (EdgeGUI edge : edges)
			if (graph.hasConnection(edge.getStart().getValue(), edge.getEnd().getValue()))
				graphGUI.addEdge(edge);
				
		return graphGUI;
	}
	
	/**
	 * Cria um novo Nodo na posição especificada
	 */
	public NodeGUI createNode(Point point, Graphics g) {
		NodeGUI node = new NodeGUI(point.x, point.y, g);
		node.setValue(++value);
		nodes.add(node);
		return node;
	}
	
	/**
	 * Adiciona o nodo à lista, caso não o possua
	 */
	public void addNode(NodeGUI node) {
		if (!nodes.contains(node))
			nodes.add(node);
	}
	
	/**
	 * Adiciona a aresta à lista, caso não a possua
	 */
	public void addEdge(EdgeGUI edge) {
		if (!edges.contains(edge))
			edges.add(edge);	
	}
	
	/**
	 * Desenha todos os elementos do grafo
	 */
	public void drawGraph() {
		for (GraphElement e : edges)
			e.paint();
		for (GraphElement e : nodes) 
			e.paint();
	}
	
	public void moveToPoint(GraphElement elem, Point p) {
		if (elem instanceof NodeGUI)
			for (EdgeGUI edge : edges)
				if (edge.isEdgeOf((NodeGUI) elem))
					edge.erase();
		
		elem.moveToPoint(p);
	}
	
	/**
	 * Coloca (ou remove) a aura no elemento especificado
	 */
	public void setHovered(GraphElement elem, boolean isHovered) {
		if (elem != null)
			elem.setHovered(isHovered);
		// Caso o elemento seja uma aresta, pinta o grafo de novo para colocar os nodos no topo
		if (elem instanceof EdgeGUI)
			drawGraph();
	}
	
	public void setSelected(GraphElement elem, boolean isSelected) {
		if (elem != null)
			elem.setSelected(isSelected);
		if (elem instanceof NodeGUI)
			for (EdgeGUI edge : edges)
				if (edge.isEdgeOf((NodeGUI) elem))
					edge.setSelected(isSelected);
		drawGraph();
	}
	
	public void delete(GraphElement elem) {
		if (elem instanceof NodeGUI) {
			Iterator<EdgeGUI> iter = edges.iterator();
			EdgeGUI edge;
			while (iter.hasNext())
				if ((edge = iter.next()).isEdgeOf((NodeGUI) elem)) {
					iter.remove();
					edge.erase();
				}
		}
		if (elem != null) {
			elem.erase();
			if (elem instanceof NodeGUI)
				nodes.remove(elem);
			else
				edges.remove(elem);
		}
		//drawGraph();
	}
	
	/**
	 * Retorna o elemento mais próximo do ponto pedido. Caso não exista nenhum elemento
	 * a uma distância menor do que 35, retorna null.
	 * 
	 * @param checkEdges - Se as arestas devem ser consideradas elementos
	 */
	public GraphElement getClosest(Point point, boolean checkEdges) {
		double distance = 35;
		GraphElement closest = null;
		
		for (NodeGUI node : nodes)
			// Se a distância for menor do que a menor anterior, substitui os valores
			if (node.distance(point) < distance) {
				distance = node.distance(point);
				closest = node;
			}
		
		if (checkEdges)
			for (EdgeGUI edge : edges)
				if (edge.distance(point) < distance) {
					distance = edge.distance(point);
					closest = edge;
				}
		
		return closest;
	}
	
	/**
	 * Gera o grafo propriamente dito, removendo informações sobre posição e outras coisas
	 */
	public Graph toGraph() {
		Graph graph = new Graph();
		
		// Para todos os nós do grafo, ...
		for (NodeGUI node : nodes) {
			Node n = new Node();
			// define seu valor, ...
			n.setValue(node.getValue());
			List<Integer> neighbors = new ArrayList<Integer>();
			// verifica todos as arestas que pertencem a ele, ...
			for (EdgeGUI edge : edges)
				if (edge.isEdgeOf(node))
					// adicionando seus vizinhos
					neighbors.add(edge.getNeihgbor(node).getValue());
			n.setNeighbors(neighbors.toArray(new Integer[]{}));
			
			graph.addNode(n);
		}
		
		return graph;
	}
	
}
