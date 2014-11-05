package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Node {
	
	private int value;
	private List<Integer> neighbors;
	
	public Node() {}
	
	public Node(int value, Integer... neighbors) {
		setValue(value);
		setNeighbors(neighbors);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setNeighbors(Integer[] neighbors) {
		// Transforma o array em List
		this.neighbors = new ArrayList<Integer>(Arrays.asList(neighbors));
	}
	
	public int getValue() {
		return value;
	}
	
	public List<Integer> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Retorna se um nodo é vizinho deste, dado seu valor
	 */
	public boolean isNeighbor(int neighbor) {
		return neighbors.contains(neighbor);
	}
	
	public boolean isNeighbor(Node neighbor) {
		return isNeighbor(neighbor.getValue());
	}
	
	/**
	 * Constroi uma representação em String do Nodo, formada pelo seu valor e,
	 * possivelmente, seus vizinhos.
	 * 
	 * @param printNeighbors - Se os vizinhos devem ser impresos
	 */
	public String toString(boolean printNeighbors) {
		// String builder facilita a criação de strings, permitindo colocar novos trechos no
		// final
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		
		if (printNeighbors) {
			builder.append(" (");
			for (Integer i : neighbors) {
				builder.append(i);
				// Não coloca vírgula depois do último vizinho
				if (neighbors.indexOf(i) != neighbors.size() - 1)
					builder.append(", ");
			}
			builder.append(")");
		}
		
		return builder.toString();
	}
}
