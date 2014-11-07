package main;


public abstract class Permutations {
	
	public void permutations(int[] a) {
	    permutation(a, 0);
	}

	public void permutation(int[] arr, int pos){
	    if(arr.length - pos == 1)
	        usePermutation(arr.clone());
	    else
	        for(int i = pos; i < arr.length; i++){
	            swap(arr, pos, i);
	            permutation(arr, pos+1);
	            swap(arr, pos, i);
	        }
	}

	public void swap(int[] arr, int pos1, int pos2){
		int h = arr[pos1];
	    arr[pos1] = arr[pos2];
	    arr[pos2] = h;
	}
	
	public abstract void usePermutation(int[] per);
	
}
