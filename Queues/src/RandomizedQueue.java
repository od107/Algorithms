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
		else if(tail == capacity - 1)
			resize(capacity); // moves the content back to the front
		assert check();

	}
	public Item dequeue(){
		// delete and return a random item
		// use StdRandom.uniform(N)
		if(isEmpty())
			throw new NoSuchElementException("List was empty");
		
		Item item;
		int itemNbr = StdRandom.uniform(size()) + head;
		
		if(itemNbr == tail-1)
			item = q[--tail];
		else if(itemNbr == head)
			item = q[head++];
		else{
			item = q[itemNbr];
			q[itemNbr] = q[--tail]; //move last item to empty spot
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
		// return an independent iterator over items in random order
		// put here a shuffled copy of the queue
	
		return new ArrayIterator();
	}

	private class ArrayIterator implements Iterator<Item>{
		private Item[] copy;
		private int current = head;
		
		public ArrayIterator(){
			Item[] copy = (Item[])(new Object[size()]);
			for(int i=0;i<capacity; i++)
				copy[i] = q[i];
			StdRandom.shuffle(copy, head, tail);
		}
		public boolean hasNext()  { return current != tail;                    }
		public void remove()      { throw new UnsupportedOperationException();  }

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();

			return copy[++current];
		}
	}
	
	 private boolean check() {

	       if (capacity == 1) {
	           if (head != tail) return false;
	       }
	       else if(head == tail) return false;
	       else if(tail > capacity - 1) return false;
	       else if(capacity < tail-head) return false;
	       else if (tail-head < capacity / 4) return false;

	       // check internal consistency of size
//	       int numberOfItems = 0;
//	       for(Item i : q)
//	    	   numberOfItems++;
//	       if (numberOfItems != size()) return false;

	       return true;
	   }
	public static void main(String[] args){
		// unit testing
	      RandomizedQueue<String> s = new RandomizedQueue<String>();
	       while (!StdIn.isEmpty()) {
	           String item = StdIn.readString();
	           
	           if (item.equals("-")) StdOut.print(s.dequeue() + " ");
	           else if (item.equals("s")) StdOut.print(s.sample() + " ");
	           else s.enqueue(item);

	       } 
	       StdOut.println("(" + s.size() + " left on random queue)");
	   }
	
}