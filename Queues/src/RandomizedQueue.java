import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	
   public RandomizedQueue(){
	   // construct an empty randomized queue
	   
   }
   public boolean isEmpty(){
	   // is the queue empty?
	   
   }
   public int size(){
	   // return the number of items on the queue
	   
   }
   public void enqueue(Item item){
	   // add the item
	   if(item == null)
		   throw new NullPointerException("Added item was null");
   }
   public Item dequeue(){
	   // delete and return a random item
//	   if empty list
//	   throw new java.util.NoSuchElementException("List was empty");
   }
   public Item sample() {
	   // return (but do not delete) a random item
//	   if empty list
//	   throw new java.util.NoSuchElementException("List was empty");
   }
   public Iterator<Item> iterator(){
	   // return an independent iterator over items in random order
	   
   }
   public static void main(String[] args){
	   // unit testing
	   
   }
}