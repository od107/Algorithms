
public class Exercise {

	public static void main(String[] args) {
		// ex1
		QuickFind ex1 = new QuickFind(10);
		int[] opgave = { 8,0,3,2,7,2,9,6,4,8,7,6}; 

		
		for(int i=0;i<opgave.length;i+=2){
			ex1.unite(opgave[i],opgave[i+1]);
		}
		
		System.out.println("exercise 1: \n" + ex1);
		
		// ex2
		BalancedQuickUnion ex2 = new BalancedQuickUnion(10);
		int[] opgave2 = { 0,9,5,7,6,3,2,8,9,7,4,8,6,8,8,5,6,1};
		
		System.out.println("\nexercise 2:");
		for(int i=0;i<opgave2.length;i+=2){
			ex2.unite(opgave2[i],opgave2[i+1]);
			System.out.println(ex2);
		}
		
//		System.out.println(ex2);
	}
	
}
