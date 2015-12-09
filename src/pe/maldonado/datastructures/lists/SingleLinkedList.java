package pe.maldonado.datastructures.lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleLinkedList <T> {
	
	class SingleLinkedListIterator implements Iterator<T> {
    
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
	}
	
	class Node {
		public T value;
		public Node next;
		
		public Node(T value) {
			this.value = value;
			this.next = null;
		}
	}
	
	private Node head;
	private Node tail;
	private int nodeCount;
	
	public SingleLinkedList() {
		head = null;
		tail = head;
		nodeCount = 0;
	}
	
	public void add(T value) {
		insert(value, nodeCount);
	}
	
	public void insert(T value, int index) {
		
		Node currentNode, newNode = new Node(value);
		int i = 0;
		
		// is the list empty? 
		if (head == null) {
			head = newNode;
			tail = head;
		}
		else if (index == 0) {	// insert at first position
			
			newNode.next = head;
			head = newNode;
			
		}
		else if (index >= nodeCount) {	// insert at last position
			
			tail.next = newNode;
			tail = newNode;
			
		}
		else {			// insert anywhere in between

			currentNode = head;
			
			// move to the previous node
			while (i < index - 1) {
				currentNode = currentNode.next;
				i++;
			}
			
			newNode.next = currentNode.next;
			currentNode.next = newNode;
			
		}

		nodeCount++;
	}
	
	public boolean remove(int index) {
		
		Node currentNode = head;
		int i = 0;
		
		// empty list? index out of bounds?
		if (head == null || index < 0 || index >= nodeCount) {
			return false;
		}
		
		// remove first?
		if (index == 0) {
			head = head.next;
		}
		else {
			
			// move to the previous node
			while (i < index - 1) {
				currentNode = currentNode.next;
				i++;
			}
			
			// if the node to be removed is last item on the list, the new "tail" would be the previous node
			if (currentNode.next == tail) {
				tail = currentNode;
			}
			
			currentNode.next = currentNode.next.next;

		}

		nodeCount--;

		return true;
	}
	
	public T itemAt(int index) {
		
		Node currentNode = head;
		int i = 0;
	
		// empty list? index out of bounds?
		if (head == null || index < 0 || index >= nodeCount) {
			return null;
		}
		
		// move to the previous node
		while (i < index) {
			currentNode = currentNode.next;
			i++;
		}
	
		return currentNode.value;
	}

	private int findIndex(T value, boolean findLast) {
		
		Node currentNode = head;
		int index = -1, i = 0;
	
		// move along the list
		while (currentNode != null) {
			
			if (currentNode.value == value) {
				index = i;
				if (!findLast) {
					break;
				}
			}
			
			currentNode = currentNode.next;
			i++;
		}
		
		return index;
		
	}

	public int indexOf(T value) {
		
		return findIndex(value, false);

	}
	
	public int lastIndexOf(T value) {
		
		return findIndex(value, true);
		
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
			return new SingleLinkedListIterator();
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
