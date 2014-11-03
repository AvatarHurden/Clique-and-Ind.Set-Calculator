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
		this.neighbors = new ArrayList<Integer>(Arrays.asList(neighbors));
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isNeighbor(int neighbor) {
		return neighbors.contains(neighbor);
	}
	
	public boolean isNeighbor(Node neighbor) {
		return neighbors.contains(neighbor.getValue());
	}
}
