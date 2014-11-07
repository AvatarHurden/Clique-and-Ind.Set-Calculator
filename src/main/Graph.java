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
	 * Cria um clone do grafo passado como parâmetro
	 */
	public Graph(Graph graph) {
		this.nodes = new ArrayList<Node>(graph.nodes);
	}
	
	public Graph(List<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}
	
	/**
	 * Verifica se o grafo contém o nó com o valor especificado.
	 */
	public boolean containsNode(int value) {
		for (Node node : nodes)
			if (node.getValue() == value)
				return true;
		// Caso nenhum nó presente tenha o valor dado, retorna falso.
		return false;
	}
	
	/**
	 * Se o grafo contém o nó pedido, retorna-o. Caso contrário, retorna null;
	 */
	public Node getNode(int value) {
		for (Node node : nodes)
			if (node.getValue() == value)
				return node;
		return null;
	}
	
	/**
	 * Retorna se existe ligação entre os nodos passados. Caso algum dos nodos não faça parte
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
	 * os vizinhos dos nodos na ordem determinada pela lista passada como parâmetro.
	 * 
	 * @param order para remover os vizinhos
	 * @return C.I.
	 */
	public Graph getIndependentSet(int[] order) {
		Graph temp = new Graph(this);
		
		// Itera por todos os nós, removendo os vizinhos deles
		for (int n : order)
			temp.removeNeighbors(n);
		
		return temp;
	}
	
	public List<Graph> getMaximumIndependentSets() {
		int[] array = new int[nodes.size()];
		for (int i = 0; i < nodes.size(); i++)
			array[i] = nodes.get(i).getValue();
		
		List<Graph> maxs = new ArrayList<Graph>();
		
		new Permutations() {
			
			int maxSize = 0;
			
			@Override
			public void usePermutation(int[] per) {
				Graph temp = getIndependentSet(per);
				if (temp.getSize() > maxSize) {
					maxs.clear();
					maxSize = temp.getSize();
				}
				
				boolean toAdd = true;
				for (Graph g : maxs)
					if (g.containsSameNodes(temp))
						toAdd = false;
				
				if (toAdd && temp.getSize() >= maxSize)
					maxs.add(temp);
			}
		}.permutations(array);
		
		return maxs;
	}
	
	
	/**
	 * Retorna uma lista de cliques a partir dos C.I. maximais do grafo original invertido
	 * 
	 * @return lista com todos cliques
	 */
	public List<Graph> getCliques() {
		Graph invGraph = this.getComplement();
		List<Graph> cliques = invGraph.getMaximalIS();
		return cliques;
	}
	
	
	public void addNode(Node node) {
		// Adiciona o Nodo apenas se ele não existe no grafo
		if (!nodes.contains(node))
			nodes.add(node);
	}
	
	public void removeNeighbors(int node) {
		// Caso não posua o nodo em questão, não faz nada
		if (!nodes.contains(getNode(node)))
			return;
		
		// Itera por todos os nodos do grafo, removendo-os caso sejam vizinhos
		// do parâmetro
		Iterator<Node> iter = nodes.iterator();
		while (iter.hasNext())
			if (getNode(node).isNeighbor(iter.next()))
				iter.remove();
	}
	
	public void removeNeighbors(Node node) {
		// Caso não posua o nodo em questão, não faz nada
		if (!nodes.contains(node))
			return;
		
		// Itera por todos os nodos do grafo, removendo-os caso sejam vizinhos
		// do parâmetro
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
	
	public boolean containsSameNodes(Graph g) {
		for (Node n : nodes)
			if (!g.containsNode(n.getValue()))
				return false;
		for (Node n : g.nodes)
			if (!containsNode(n.getValue()))
				return false;
		return true;
	}
	
	/**
	 * Cria uma representação em String do grafo, formada pela representação em String~
	 * dos nodos
	 * 
	 * @param neighbors - Se os vizinhos de cada nodo devem ser impressos
	 */
	public String toString(boolean neighbors) {
		// String builder facilita a criação de strings, permitindo colocar novos trechos no
		// final		
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		
		for (Node n : nodes) {
			builder.append(n.toString(neighbors));
			// Não coloca vírgula depois do último nodo
			if (nodes.indexOf(n) != nodes.size() - 1)
				builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}	
	
}
