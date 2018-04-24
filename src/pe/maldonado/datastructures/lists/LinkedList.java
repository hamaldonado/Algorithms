package pe.maldonado.datastructures.lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList <T> {
	
	class LinkedListIterator implements ListIterator<T> {
    
    	private Node currentNode = null;
    	private int index = -1;

        @Override
        public boolean hasNext() {
            return currentNode != tail;
        }
        
        @Override
		public boolean hasPrevious() {
			return currentNode != head;
		}

        @Override
		public int nextIndex() {
			return index + 1;
		}

		@Override
		public int previousIndex() {
			return index < 0 ? -1 : index - 1;
		}
        
        @Override
        public T next() {
            
        	// We have reached the end of the list or the list 
        	// is empty (in the case of tail == null) 
            if (currentNode == tail) {
                throw new NoSuchElementException();
            }
            
            // is the first call to next()?
            if (currentNode == null && head != null) {
                currentNode = head;
            }
            else {
            	currentNode = currentNode.next;
            }
            
            index++;
            return currentNode.value;
        }
        
        @Override
		public T previous() {
			
			// We have reached the beginning of the list or the list 
			// is empty (in the case of head == null) 
            if (currentNode == head) {
                throw new NoSuchElementException();
            }
            else {
            	currentNode = currentNode.prev;
            }
            
            index--;
            return currentNode.value;
		}

		@Override
		public void add(T value) {
			
			Node newNode = new Node(value);
			
			// add at first position
			if (currentNode == null) {
				insertNode(head, newNode);
			}
			// add somewhere else
			else {
				insertNode(currentNode.next, newNode);
			}
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
		
		// Empty list? index out of bounds?
		if (head == null || index < 0 || index >= nodeCount) {
			return null;
		}
		
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
	
	private void insertNode(Node currentNode, Node newNode) {
		
		// empty list? 
		if (head == null) {
			head = newNode;
			tail = head;
		}
		
		// insert at first position
		else if (currentNode == head) {	
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		
		// insert at last position
		else if (currentNode == null) {	
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		
		// insert anywhere in between
		else {
			newNode.prev = currentNode.prev;
			newNode.next = currentNode;
			currentNode.prev.next = newNode;
			currentNode.prev = newNode;
		}
		
		nodeCount++;
	}
	
	public void add(T value) {
		insert(value, nodeCount);
	}
	
	public void insert(T value, int index) {
		
		Node newNode = new Node(value);
		Node currentNode = findNode(index);

		insertNode(currentNode, newNode);

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
