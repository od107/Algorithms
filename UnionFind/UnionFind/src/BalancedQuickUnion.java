
public class BalancedQuickUnion implements Unite{
	private int[] id, sz;
	
	public BalancedQuickUnion(int N){
		id = new int[N];
		sz = new int[N];
		for(int i=0;i<N;i++){
			id[i]=i;
			sz[i]=1;
		}
	}
	
	private int root(int i){
		while(i!=id[i])
			i = id[i];
		return i;
	}
	
	public boolean find(int p, int q){
		return id[p] == id[q];
	}
	
	public void unite(int p, int q){
		if(!find(p,q)){
			int i = root(p);
			int j = root(q);
			if(sz[i]<sz[j]){
				id[i]= j;
				sz[j]+=sz[i];
			}
			else{
				id[j]= i;
				sz[i]+=sz[j];				
			}
		}
	}
	
	public String toString(){
		String result = new String();
		String size = new String();
		for(int i=0;i<id.length;i++){
			result += id[i] + " ";
			size += sz[i] + " ";
		}
		return "The result is: \n" + result + "\nSize is: \n" + size;
	}
}
