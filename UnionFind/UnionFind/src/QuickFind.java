
public class QuickFind implements Unite{
	private int[] id;
	
	public QuickFind(int N){
		id = new int[N];
		for(int i=0;i<N;i++){
			id[i]=i;
		}
	}
	
	public boolean find(int p, int q){
		return id[p] == id[q];
	}
	
	public void unite(int p, int q){
		if(!find(p,q)){
			int pid=id[p];
			for(int i=0;i<id.length;i++){
				if(id[i] == pid)
					id[i] = id[q];
			}
		}
	}
	
	public String toString(){
		String result = new String();
		for(int i=0;i<id.length;i++){
			result += id[i] + " ";
		}
		return "The result is: \n" + result;
	}
}
