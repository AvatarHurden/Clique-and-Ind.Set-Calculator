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
	
	public String toString(boolean printNeighbors) {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		
		if (printNeighbors) {
			builder.append(" (");
			for (Integer i : neighbors) {
				builder.append(i);
			
				if (neighbors.indexOf(i) != neighbors.size() - 1)
					builder.append(", ");
			}
		builder.append(")");
		}
		
		return builder.toString();
	}
}
