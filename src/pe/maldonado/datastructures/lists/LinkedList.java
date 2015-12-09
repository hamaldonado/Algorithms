package pe.maldonado.datastructures.lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList <T> {
	
	class LinkedListIterator implements ListIterator<T> {
    
    	private Node currentNode = null;

        @Override
        public boolean hasNext() {
            return currentNode != tail;
        }

        @Override
        public T next() {
            if (currentNode == null) {
                currentNode = head;
                return currentNode.value;
            }
            if (currentNode.next == null) {
                throw new NoSuchElementException();
            }
            
            currentNode = currentNode.next;
            
            return currentNode.value;
        }

		@Override
		public void add(T value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return currentNode != head;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public T previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(T value) {
			currentNode.value = value;
		}
	}
	
	class Node {
		public T value;
		public Node prev;
		public Node next;
		
		public Node(T value) {
			this.value = value;
			this.prev = null;
			this.next = null;
		}
	}
	
	private Node head;
	private Node tail;
	private int nodeCount;
	
	public LinkedList() {
		head = null;
		tail = head;
		nodeCount = 0;
	}

	private Node findNode(int index) {
		
		Node currentNode;
		int i;
		
		// If index is on the 1st half, move the cursor forward from the 
		// head node to the middle. 
		if (index <= nodeCount / 2) {
			currentNode = head;
			i = 0;
			
			while (i < index) {
				currentNode = currentNode.next;
				i++;
			}
		}

		// If index is on the 2nd half, move the cursor backwards from the 
		// tail node to the middle.
		else {
			currentNode = tail;
			i = nodeCount - 1;
			
			while (i > index) {
				currentNode = currentNode.prev;
				i--;
			}
		}
		
		return currentNode;
	}
	
	public void add(T value) {
		insert(value, nodeCount);
	}
	
	public void insert(T value, int index) {
		
		Node newNode = new Node(value);
		Node currentNode;
				
		// empty list? 
		if (head == null) {
			head = newNode;
			tail = head;
		}
		
		// insert at first position
		else if (index == 0) {	
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		
		// insert at last position
		else if (index >= nodeCount) {	
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		
		// insert anywhere in between
		else {			

			// move to the desired position
			currentNode = findNode(index);
			
			newNode.prev = currentNode.prev;
			newNode.next = currentNode;
			currentNode.prev.next = newNode;
			currentNode.prev = newNode;
		}

		nodeCount++;
	}
		
	public boolean remove(int index) {
		
		Node currentNode;
		
		// empty list? index out of bounds?
		if (head == null || index < 0 || index >= nodeCount) {
			return false;
		}
		
		// remove first?
		if (index == 0) {
			head = head.next;
			head.prev = null;
		}
		
		// remove last?
		else if (index == nodeCount - 1) {
			tail = tail.prev;
			tail.next = null;
		}

		// remove anywhere in between
		else {
			
			// move to the desired position
			currentNode = findNode(index);

			currentNode.prev.next = currentNode.next;
			currentNode.next.prev = currentNode.prev;
		}

		nodeCount--;

		return true;
	}
	
	public T itemAt(int index) {
	
		// empty list? index out of bounds?
		if (head == null || index < 0 || index >= nodeCount) {
			return null;
		}
		
		return findNode(index).value;
	}
	
	public int indexOf(T value) {
		
		Node currentNode = head;
		int index = -1, i = 0;
	
		// move along the list
		while (currentNode != null) {
			
			if (currentNode.value == value) {
				index = i;
				break;
			}
			
			currentNode = currentNode.next;
			i++;
		}
		
		return index;
	}
	
	public int lastIndexOf(T value) {
		
		Node currentNode = tail;
		int index = -1, i = nodeCount - 1;
	
		// move along the list
		while (currentNode != null) {
			
			if (currentNode.value == value) {
				index = i;
				break;
			}
			
			currentNode = currentNode.prev;
			i--;
		}
		
		return index;
	}
	
	public int count() {
		return nodeCount;
	}

	public String toString() {
		
		Node currentNode = head;
		StringBuilder sb = new StringBuilder("[");
		
		// move along the list
		while (currentNode != null) {

			sb.append(currentNode.value.toString());
			
			if (currentNode.next != null) {
				sb.append(", ");
			}
			
			currentNode = currentNode.next;
		}
		
		sb.append("]");
		
		return sb.toString();

	}
	
	public Iterator<T> iterator() {
	    
		// is the list empty?  return an empty Iterator
		if (nodeCount == 0) {
	        return Collections.emptyIterator();
	    }
		else {
			return new LinkedListIterator();
		}
	}
	
	
	/*public static void main(String[] args) {

		LinkedList<Integer> li = new LinkedList<Integer>();
		
		
		System.out.println(li.toString());
		
		li.insert(6, 1);
		li.insert(8, 7);
		li.insert(13, 3);
		
		System.out.println(li.toString());
		
		Iterator<Integer> itr = li.iterator();
	    while(itr.hasNext()) {
	         Integer v = itr.next();
	         System.out.println(v);
	    }
		
	}*/
	
}
