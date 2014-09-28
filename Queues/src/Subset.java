
public class Subset {

	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		
		int k = Integer.parseInt(args[0]);
		
		// read N input strings
		for(int i=1;i<args.length;i++){
			// put into randomQueue
			rq.enqueue(args[i]);
		}
		
		for(int i=0;i<k;i++){
			StdOut.println(rq.dequeue());
		}

	}

}
