package main;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Graph {
	
	private List<Node> nodes;
	
	public Graph() {
		nodes = new ArrayList<Node>(); 
	}
	
	/**
	 * Cria um clone do grafo passado como par�metro
	 */
	public Graph(Graph graph) {
		this.nodes = new ArrayList<Node>(graph.nodes);
	}
	
	/**
	 * Verifica se o grafo cont�m o n� com o valor especificado.
	 */
	public boolean containsNode(int value) {
		for (Node node : nodes)
			if (node.getValue() == value)
				return true;
		// Caso nenhum n� presente tenha o valor dado, retorna falso.
		return false;
	}
	
	/**
	 * Se o grafo cont�m o n� pedido, retorna-o. Caso contr�rio, retorna null;
	 */
	public Node getNode(int value) {
		for (Node node : nodes)
			if (node.getValue() == value)
				return node;
		return null;
	}
	
	/**
	 * Retorna se existe liga��o entre os nodos passados. Caso algum dos nodos n�o fa�a parte
	 * do grafo, retorna false
	 */
	public boolean hasConnection(int value1, int value2) {
		if (!containsNode(value1) || !containsNode(value2))
			return false;
		
		return getNode(value1).isNeighbor(value2);
	}
	
	/**
	 * Descobre o conjunto independente formado por esse grafo ao remover
	 * os vizinhos dos nodos na ordem determinada pela lista passada como par�metro.
	 * 
	 * @param order para remover os vizinhos
	 * @return tamanho do C.I.
	 */
	public Graph getIndependentSet(Node[] order) {
		Graph temp = new Graph(this);
		
		// Itera por todos os n�s, removendo os vizinhos deles
		for (Node n : order)
			temp.removeNeighbors(n);
		
		return temp;
	}
	
	public void addNode(Node node) {
		// Adiciona o Nodo apenas se ele n�o existe no grafo
		if (!nodes.contains(node))
			nodes.add(node);
	}
	
	public void removeNeighbors(Node node) {
		// Caso n�o posua o nodo em quest�o, n�o faz nada
		if (!nodes.contains(node))
			return;
		
		// Itera por todos os nodos do grafo, removendo-os caso sejam vizinhos
		// do par�metro
		Iterator<Node> iter = nodes.iterator();
		while (iter.hasNext())
			if (node.isNeighbor(iter.next()))
				iter.remove();
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public int getSize() {
		return nodes.size();
	}
	
	public String toString() {
		return toString(true);
	}
	
	/**
	 * Cria uma representa��o em String do grafo, formada pela representa��o em String~
	 * dos nodos
	 * 
	 * @param neighbors - Se os vizinhos de cada nodo devem ser impressos
	 */
	public String toString(boolean neighbors) {
		// String builder facilita a cria��o de strings, permitindo colocar novos trechos no
		// final		
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		
		for (Node n : nodes) {
			builder.append(n.toString(neighbors));
			// N�o coloca v�rgula depois do �ltimo nodo
			if (nodes.indexOf(n) != nodes.size() - 1)
				builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}
	
}