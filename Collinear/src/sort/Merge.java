package sort;

public class Merge {
	
	private static Comparable[] aux;
	private static int count;

	public void mergesort(int[] input, int steps){
		int length = input.length;
		int count = 0;
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] input = {56, 89, 20, 41, 88, 79, 25, 49, 63, 97, 75, 55};
		
		count=0;
		int length = input.length;
		aux = new Integer[length];
		sort(input,aux,0,length-1);
		
		for(int i=0;i<length;i++)
			System.out.print(input[i] + "  ");
	}

	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
	{
		assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
		assert isSorted(a, mid+1, hi); // precondition: a[mid+1..hi] sorted
		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) 
		{
			if (i > mid) a[k] = aux[j++];
			else if (j > hi) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
		assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted

		count++;
		if(count == 7){
			for(int r=0;r<a.length;r++){
				System.out.print(a[r] + "  ");
			}
			System.out.println(" ");
			}
	}
	
	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
	{
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid+1, hi);
		merge(a, aux, lo, mid, hi);
	}
	public static void sort(Comparable[] a)
	{
		aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
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
	
	public static boolean isSorted(Comparable[] a, int lo, int hi){
		for(int i=lo+1; i<hi; i++)
			if(less(a[i],a[i-1])) 
				return false;
		return true;
	}
}