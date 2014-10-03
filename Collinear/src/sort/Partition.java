package sort;
import java.util.Comparator;

//partitions the input according to quick sort
//expand this to also deal with chars
//and give a decent output per step

public class Partition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] input = {56, 89, 20, 41, 88, 79, 25, 49, 63, 97, 75, 55};
//		String[] input = {"A", "B", "B", "A","B", "B", "A", "B", "A", "A", "A", "A"};

		int i=0;
		int lo=0;
		int hi = input.length -1;
		int j = hi + 1;
		while(true){
			while(less(input[++i],input[lo]))
				if (i==hi) break;

			while(less(input[lo], input[--j]))
				if(j == lo) break;

			if(i >= j) break;

			exch(input, i , j);
		}
		// couple extra lines
		exch(input, lo, j);
		
		for(int t=0;t<input.length;t++)
			System.out.print(input[t] + " ");
	}

	public static void exch(Comparable[] input, int a, int b){
		Comparable swap = input[a];
		input[a] = input[b];
		input[b] = swap;
	}

	public static boolean less(Comparable a,Comparable b){
		return a.compareTo(b) < 0;
	}

	public static boolean isSorted(Comparable[] a){
		for(int i=1; i<a.length ; i++)
			if(less(a[i],a[i-1])) 
				return false;
		return true;
	}

}
	


