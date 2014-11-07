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
	
	public Graph(List<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
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
	 * Cria um grafo complementar ao grafo original
	 * Deleta os vizinhos existentes e adiciona os vizinhos inexistentes de cada nodo.
	 * 
	 * @param -
	 * @return grafo G' complementar de G
	 */
	public Graph getComplement() {
		Graph temp = new Graph(this);
		
		for(Node n : temp.nodes)
			n.invertNeighbors(temp.nodes);
		
		return temp;
	}
	
	/**
	 * Descobre o conjunto independente formado por esse grafo ao remover
	 * os vizinhos dos nodos na ordem determinada pela lista passada como par�metro.
	 * 
	 * @param order para remover os vizinhos
	 * @return C.I.
	 */
	public Graph getIndependentSet(Node[] order) {
		Graph temp = new Graph(this);
		
		// Itera por todos os n�s, removendo os vizinhos deles
		for (Node n : order)
			temp.removeNeighbors(n);
		
		return temp;
	}
	
	/**
	 * Cria uma lista com os maiores conjuntos independentes
	 * 
	 * @param order para permutar e utilizar na chamada de getIndependentSet
	 * @return lista com todos C.I. maximais
	 */
	public List<Graph> getMaximalIS() {
		List<List<Node>> maximalNodesList = getMaximalISList(nodes);
		List<Graph> maximalGraphs = new ArrayList<Graph>();
		
		for (int i=0; i < maximalNodesList.size(); i++) {
			maximalGraphs.set(i, new Graph(maximalNodesList.get(i)));
			
		}
		
		return maximalGraphs;
	}
	public List<List<Node>> getMaximalISList(List<Node> original) {
		
		
		if (original.size() == 0) { 
			List<List<Node>> result = new ArrayList<List<Node>>();
			result.add(new ArrayList<Node>());
			return result;
	     }
		
		Node firstElement = original.remove(0);
	    List<List<Node>> returnValue = new ArrayList<List<Node>>();
	    List<List<Node>> permutations = getMaximalISList(original);
	    
	    for (List<Node> smallerPermutated : permutations)
	    	for (int i=0; i <= smallerPermutated.size(); i++) {
	    		List<Node> temp = new ArrayList<Node>(smallerPermutated);
		        temp.add(i, firstElement);
		        
		        
		        if (returnValue.get(0).size() < (getIndependentSet(temp.toArray(new Node [temp.size()])).nodes.size())) {
		        	returnValue.clear();
		        	returnValue.add(temp);
		        }
		        
	    	}
	    return returnValue;
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
