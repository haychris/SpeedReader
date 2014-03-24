/*************************************************************************
 * Name: Nayan Bhat
 * Login: nayanb@princeton.edu
 * Precept: P06B
 * 
 * Partner Name: Chris Hay
 * Partner Login: chay@princeton.edu
 * Partner Precept: P06B
 * 
 * Compilation: javac Deque.java
 * Execution: java Deque
 * Dependencies: 
 *
 * Description: 
 * 
 **************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private Node first; //first item in Deque
    private Node last; //last item in Deque
    private int size; //number of items in Deque
    private int numAdded; //keeps track of total number of items added
    
    //inner class for Node Object implementation of Deque
    private class Node
    {
        private Node before; //link backward
        private Node next; //link forward
        private Item item; //Item stored in Node
        //constructor for Node
        Node(Item item)
        {
            this.item = item;
        }
    }
    
    //construct an empty deque
    public Deque()
    {
        //initialize size variable
        size = 0;
    }
    
    //returns true if deque is empty
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    //returns number of elements in deque
    public int size()
    {
        return size;
    }
    
    //adds an element to the beginning of deque
    public void addFirst(Item item)
    {
        //makes sure item is not null object
        checkNull(item);
        //creates a temporary reference to Node in first
        //so that it isn't lost
        Node temp = first;
        //create a new Node to store the new item at beginning
        //of deque
        first = new Node(item);
        //set next pointer to refer to temp
        first.next = temp;
        //if it's the first item inserted, make last point to it
        if (size() == 0)
            last = first;
        // otherwise set a pointer back to the new item from what 
        // was previously first
        else 
            first.next.before = first;
        size++;
        numAdded++;
        
    }
    // adds item to the back of the deque
    public void addLast(Item item)
    {
        // throws an error if item is null
        checkNull(item);
        // if it's the first item, make both last and first point to it
        if (size() == 0)
        {
            last = new Node(item);
            first = last;
        }
        // make the last item point to the node being inserted 
        else
        {
            last.next = new Node(item);
            // make the new node point back to the previous
            last.next.before = last;
            // update last
            last = last.next;
        }
        size++;
        numAdded++;
    }
    
    //checks that inputted item is not null
    private void checkNull(Item item)
    {
        if (item == null)
            throw new NullPointerException();
    }
    
    //checks that there is at least one element in the deque
    private void checkEmpty()
    {
        if (size() == 0)
            throw new java.util.NoSuchElementException();
    }
    
    //removes the first item from the deque
    public Item removeFirst()
    {
        //throw error if deque is empty
        checkEmpty();
        //store item of first in a temporary object
        Item temp = first.item;
        // check if it's not the last item to avoid nullPointerExceptions
        if (first.next != null) 
        {
            //set the pointer back from second node to null (prevent loitering)
            first.next.before = null;
            //set first to point to what was originally the second
            //node
            first = first.next;
        }
        // if it's the last item to remove, set first and last to be null
        else 
        {
            first = null;
            last = null;
        }
        //decrement size by one
        size--;
        //return the item stored in temp
        return temp;
    }
    
    //removes the last element from the deque
    public Item removeLast()
    {
        //throw error if deque is empty
        checkEmpty();
        //store item of last in a temporary object
        Item temp = last.item;
        // check if it's not the last item to avoid nullPointerExceptions
        if(last.before != null)
        {
            //set pointer forward from second to last element to null
            //to prevent loitering
            last.before.next = null;
            //update last
            last = last.before;    
        }
        // if it's the last item to remove, set first and last to be null
        else
        {
            last = null;
            first = null;
        }
        //decrement size by one
        size--;
        //return the item stored in temp
        return temp;
    }
    
    // The Iterator code below was adapted from "Stack.java" 
    // on the booksite: 
    // http://algs4.cs.princeton.edu/13stacks/Stack.java.html
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new ForwardIterator(first);
    }
    // iterates forward through deque
    private class ForwardIterator implements Iterator<Item>
    {
        private Node temp; // current node
        // constructs iterator and sets current to first
        ForwardIterator(Node first)
        {
            temp = first;
        }
        // checks if there's a next item available
        public boolean hasNext()
        {
            return temp != null;
        }
        // throws an error if user tries to remove an item during iteration
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        // returns the next item
        public Item next()
        {
            if (hasNext())
            {
                // stores item in the current node
                Item item = temp.item;
                // updates the current node to the next
                temp = temp.next;
                return item;
            }
            // throws an exception if it reaches the end
            throw new NoSuchElementException();
        }
    }
    // unit tests
    public static void main(String[] args)
    {
        Deque<String> d = new Deque<String>();
        d.addLast("hi");
        d.addLast("Chris");
        d.addFirst("What's");
        for (String e: d)
            StdOut.println(e);
        StdOut.println(d.removeLast());
        StdOut.println(d.removeFirst());
        StdOut.println(d.removeFirst());
        Integer trash = null;
        Deque<Integer> b = new Deque<Integer>();
        for(int i = 0; i < 5; i++) {
            double k = Math.random();
            if (k < 0.5) 
                b.addFirst(i);
            else  
                trash = b.removeFirst();
        }
        for(Integer e: b)
            StdOut.println(e);
    }
    
    
}