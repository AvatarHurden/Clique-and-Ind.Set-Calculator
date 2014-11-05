package main;
import java.util.ArrayList;
import java.util.List;


public class Permutations {
	
	public ArrayList<Node[]> permutations(Node[] a) {
	    ArrayList<Node[]> ret = new ArrayList<Node[]>();
	    permutation(a, 0, ret);
	    return ret;
	}

	public void permutation(Node[] arr, int pos, ArrayList<Node[]> list){
	    if(arr.length - pos == 1)
	        list.add(arr.clone());
	    else
	        for(int i = pos; i < arr.length; i++){
	            swap(arr, pos, i);
	            permutation(arr, pos+1, list);
	            swap(arr, pos, i);
	        }
	}

	public void swap(Node[] arr, int pos1, int pos2){
		Node h = arr[pos1];
	    arr[pos1] = arr[pos2];
	    arr[pos2] = h;
	}
	
}
