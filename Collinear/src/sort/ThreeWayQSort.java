package sort;

public class ThreeWayQSort {
	
	public static void main(String[] args) {
		Integer[] input = {53, 72, 56, 73, 86, 77, 45, 76, 53, 96  };
		
		sort(input,0,input.length-1);
		
		for(int i=0;i<input.length;i++)
			System.out.print(input[i] + "  ");
		
	}
		
		
	private static void sort(Comparable[] a, int lo, int hi) 
	{ 
	 if (hi <= lo) return; 
	 int lt = lo, gt = hi;
	 Comparable v = a[lo]; 
	 int i = lo; 
	 while (i <= gt) 
	 { 
	 int cmp = a[i].compareTo(v); 
	 if (cmp < 0) exch(a, lt++, i++); 
	 else if (cmp > 0) exch(a, i, gt--); 
	 else i++; 
	 }
	 //commented because we want only one pass	
//	 sort(a, lo, lt - 1); 
//	 sort(a, gt + 1, hi); 
	} 
	
	public static void exch(Comparable[] input, int a, int b){
		Comparable swap = input[a];
		input[a] = input[b];
		input[b] = swap;
	}
}
