import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node first,last; //top and bottom
	private int N; // size
	
	private class Node{
		private Item item;
		private Node next;
		private Node previous; //needed to implement removelast in constant time
	}
   public Deque(){
	   // construct an empty deque
	   first=null;
	   last=null;
	   N = 0;   
	   
	   assert check();
   }
   public boolean isEmpty(){
	   // is the deque empty?
	   return N == 0;
	   // or first == null && last == 0; ?
   }
   public int size(){
	   // return the number of items on the deque
//	   int count = 0;
//	   Node node = first;
//	   while(node.next!=last){
//		   node = node.next;
//		   count++;
//	   }
//	   return count;	
	   return N;
   }
   public void addFirst(Item item){
	   // insert the item at the front
	   if(item == null)
		   throw new NullPointerException("Added item was null");
	   Node oldfirst = first;
	   first = new Node();
	   first.item = item;
	   first.next = oldfirst;
	   if(isEmpty())
		   last = first;
	   else
		   oldfirst.previous = first;
	   N++;
	   assert check();
   }
   public void addLast(Item item){
	   // insert the item at the end
	   if(item == null)
		   throw new NullPointerException("Added item was null");
	   Node oldlast = last;
	   last = new Node();
	   last.item = item;
	   last.next = null;
	   if(isEmpty())
		   first = last; // 1 element in queue
	   else{
		   oldlast.next = last;
		   last.previous=oldlast;
	   }
	   N++;
	   assert check();
   }
   public Item removeFirst(){
	   // delete and return the item at the front
	   if(isEmpty())
		   throw new NoSuchElementException("List was empty");
	   Item item = first.item;
	   first = first.next; // not yet memory cleaning here : loitering
	   N--;
	   if(isEmpty()){
		   first = null;
		   last = null;
	   }
	   else
		   first.previous = null;
	   assert check();
	   return item;
   }
   public Item removeLast(){
	   // delete and return the item at the end
	   // this method does not exist in original stack and queue
	   if(isEmpty())
		   throw new NoSuchElementException("List was empty");
	   Item item = last.item;
	   last = last.previous;
	   N--;
	   if(isEmpty()){
		   first = null;
		   last = null;
	   }
	   else
		   last.next = null;
	   assert check();
	   return item;
   }
   public Iterator<Item> iterator()  {
	   // return an iterator over items in order from front to end
	   return new ListIterator();
   }       
   
   private class ListIterator implements Iterator<Item>{
       private Node current = first;
       public boolean hasNext()  { return current != null;                     }
       public void remove()      { throw new UnsupportedOperationException();  }

       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = current.item;
           current = current.next; 
           return item;
       }
   }
   
   private boolean check() {

       if (N == 0) {
           if (first != null) return false;
           if (last != null) return false;
       }
       else if (N == 1) {
           if (first == null)      return false;
           if (first.next != null) return false;
           if (last == null) return false;
           if (last.previous != null) return false;
       }
       else {
           if (first.next == null) return false;
           if (last.previous == null) return false;
       }

       // check internal consistency of instance variable N
       int numberOfNodes = 0;
       for (Node x = first; x != null; x = x.next) {
           numberOfNodes++;
       }
       if (numberOfNodes != N) return false;

       return true;
   }
   
   public static void main(String[] args){
	   // unit testing
       Deque<String> s = new Deque<String>();
       while (!StdIn.isEmpty()) {
           String item = StdIn.readString();
           if (!item.equals("-")) s.addLast(item);
           else if (!s.isEmpty()) StdOut.print(s.removeFirst() + " ");
       }
       StdOut.println("(" + s.size() + " left on deque)");
   }
   
}
