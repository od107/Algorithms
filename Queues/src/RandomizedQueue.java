import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private int head, tail, capacity;

	public RandomizedQueue(){
		// construct an empty randomized queue
		head = 0;
		tail = 0;
		capacity = 1;
		q = (Item[]) new Object[capacity];
		
		assert check();

	}
	public boolean isEmpty(){
		// is the queue empty?
		return head == tail;
	}
	public int size(){
		// return the number of items on the queue
		return tail - head;
	}
	public void enqueue(Item item){
		// add the item
		if(item == null)
			throw new NullPointerException("Added item was null");
		q[tail++] = item;
		if(size() == capacity)
			resize(capacity * 2);
		else if(tail == capacity)
			resize(capacity); // moves the content back to the front
		assert check();

	}
	public Item dequeue(){
		// delete and return a random item
		// use StdRandom.uniform(N)
		if(isEmpty())
			throw new NoSuchElementException("List was empty");
		
		
		int itemNbr = StdRandom.uniform(size()) + head;
		Item item = q[itemNbr];
		q[itemNbr] = null;

		if(itemNbr == tail-1)
			tail--;
		else if(itemNbr == head)
			head++;
		else{
			q[itemNbr] = q[tail-1]; //move last item to empty spot
			q[--tail] = null;
		}
			
		if(size() <= capacity / 4)
			resize(capacity / 2);
		else if(isEmpty()){ //set pointers back to beginning, saves on resizes
			head = 0;
			tail = 0;
		}
		assert check();
		return item;


	}
	public Item sample() {
		// return (but do not delete) a random item
		if(isEmpty())
			throw new NoSuchElementException("List was empty");

		return q[StdRandom.uniform(size()) + head];
	}

	private void resize(int cap){
		Item[] copy = (Item[])(new Object[cap]);
		int size = size();
		for(int i=0;i<size;i++){
			copy[i] = q[head + i];
		}
		q = copy;
		head = 0;
		tail = size;
		capacity = cap;
		assert check();
	}
	public Iterator<Item> iterator(){

		return new ArrayIterator();
	}

	private class ArrayIterator implements Iterator<Item>{

		private int current;
		private Item[] copy;
		
		public ArrayIterator(){
			copy = (Item[]) new Object[size()];
			for(int i=0;i<size();i++){
				copy[i]=q[head+i];
			}
			StdRandom.shuffle(copy);
		}
		
		public boolean hasNext()  { return (current) != copy.length;                    }
		public void remove()      { throw new UnsupportedOperationException();  }

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();

			return copy[current++];
		}
	}
	
	 private boolean check() {

//	       if (q[tail] != null) return false;
	        if (capacity == 1 && head != tail) return false;
	       else if(capacity != 1 && head == tail) return false;
	       else if(tail > capacity) return false;
	       else if(capacity < tail-head) return false;
	       else if (tail-head < capacity / 4) return false;

	       return true;
	   }
	public static void main(String[] args){
		// unit testing

	      //manual input testing
//		RandomizedQueue<String> s = new RandomizedQueue<String>();
//	       while (!StdIn.isEmpty()) {
//	           String item = StdIn.readString();
//	           
//	           if (item.equals("-")) StdOut.print(s.dequeue() + " ");
//	           else if (item.equals("s")) StdOut.print(s.sample() + " ");
//	           else s.enqueue(item);
//
//	       } 
	       
	       //make randomized testing
//	       RandomizedQueue<Integer> s = new RandomizedQueue<Integer>();
//	       int count=0;
//	       for(int i=0;i<100000;i++){
//	    	   double decision = StdRandom.uniform();
//	    	   if(decision<0.1){
//	    		   s.enqueue(count++);
//	    		   System.out.println("enqueing " + count);
//	    	   }
//	    	   else if(0.1 <= decision && decision < 0.2){
//	    		   if(s.isEmpty()){
//	    			   i--;
//	    		   }
//	    		   else
//	    			   System.out.println("dequeing " + s.dequeue());
//	    	   }
//	    	   else if(0.2 <= decision)
//	    		   if(s.isEmpty()){
//	    			   i--;
//	    		   }
//	    		   else
//	    		   System.out.println("sampling " + s.sample());
//	       }
	       
	       //test iterator
	       int count=0;
	       RandomizedQueue<Integer> s = new RandomizedQueue<Integer>();
	       for (int i=0;i<500;i++){
	    	   s.enqueue(count++);
	       }
	       Iterator<Integer> iter = s.iterator();
	       Iterator<Integer> iter2 = s.iterator(); 
	       
	       while(iter.hasNext()){
	    	   System.out.println("output nbr: " + count-- + "\t" + iter.next());
	    	   System.out.println("2nd iterator: " +  "\t" + iter2.next());
	       }
	   }
	
}