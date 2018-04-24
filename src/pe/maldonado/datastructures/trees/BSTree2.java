package pe.maldonado.datastructures.trees;

// Binary Search Tree (versión recursiva)

public class BSTree2 {
	
	private class Node {
		private Node left;
		private Node right;
		private int key;
		private int value;
	
		public Node(int key, int value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right= null;
		}
	}
	
	private Node root;
	private int nodeCount;
	
	public int count() {
		return nodeCount;
	}
	
	public BSTree2() {
		root = null;
		nodeCount = 0;
	}
	
	// --------------------------------------------------------------------------------
	// ** Inserción **
	// --------------------------------------------------------------------------------
	public void addItem(int key, int value) {
		root = addNode(root, key, value);
	}
	
	private Node addNode(Node currentNode, int key, int value) {
		
		if (currentNode == null) {
			nodeCount++;
			return new Node(key, value);
		}
		
		if (currentNode.key > key) {
			currentNode.left = addNode(currentNode.left, key, value);
		}	
		
		else if (currentNode.key < key ) {
			currentNode.right = addNode(currentNode.right, key, value);
		}
		
		else {
			currentNode.value = value;
		}
		
		return currentNode;
			
	}
	// --------------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------------
	// ** Busqueda **
	// --------------------------------------------------------------------------------
	public int findItem(int key) {
		
		Node foundNode = findNode(root, key);
		
		if (foundNode == null) {
			return -1;
		}
		else {
			return foundNode.value;
		}
		
	}
	
	private Node findNode(Node currentNode, int key) {
		
		if (currentNode == null)
			return null;
		
		if (currentNode.key > key)
			return findNode(currentNode.left, key);
		
		if (currentNode.key < key)
			return findNode(currentNode.right, key);	
		
		return currentNode;
	}
	// --------------------------------------------------------------------------------
	
	public void removeItem(int key) {
		root = removeNode(root, key);
	}
	
	private Node removeNode(Node currentNode, int key) {
			
		if (currentNode == null)
			return null;
		
		if (currentNode.key > key) {
			currentNode.left = removeNode(currentNode.left, key);
		}	
		
		else if (currentNode.key < key ) {
			currentNode.right = removeNode(currentNode.right, key);
		}
		
		else {
			
			/* 
			 * currentNode es el nodo a eliminar.  Se dan 3 casos:
			 * Caso 1: currentNode tiene 1 sólo hijo por la der. Lo reemplazamos por su hijo derecho o por null si tampoco tiene hijo derecho (currentNode es nodo Hoja).
			 * Caso 2: currentNode tiene 1 sólo hijo por la izq. Lo reemplazamos por su hijo izquierdo.
			 * Caso 3: currentNode tiene 2 hijos. Lo reemplazamos por su Sucesor.
			 */
			
			// Caso 1
			if (currentNode.left == null) {
				nodeCount--;
				return currentNode.right;
			}
			
			// Caso 2
			else if (currentNode.right == null) {
				nodeCount--;
				return currentNode.left;
			}
			
			// Caso 3
			else {
						
				// Guardamos la referencia del nodo que se quiere borrar (currentNode) en una variable temp 
				Node temp = currentNode;
				
				// Hacemos que currentNode apunte ahora el nodo mayor de los hijos de la izquierda del nodo que se
				// quiere borrar (referenciado ahora por temp)
				currentNode = findMaximum(temp.left);
				
				// Desconectamos currentNode (que ahora apunta al mayor de los hijos de la izquierda de temp). 
				// Para esto invocamos removeNode a temp, usando el "key" de currentNode
				temp = removeNode(temp, currentNode.key);
				
				// Finalmente reemplazamos temp (que referencia al nodo que deseamos eliminar) con currentNode. Para
				// esto hacemos que los hijos de temp sean ahora hijos de currentNode y hacemos temp = null;
				currentNode.left = temp.left;
				currentNode.right = temp.right;
				temp = null;
			}
		}
		
		return currentNode;
		
	}
	
	private Node findMaximum(Node currentNode) {
		
		if (currentNode == null)
			return null;
		
		if (currentNode.right == null)
			return currentNode;
		
		return findMaximum(currentNode.right);
	}
	
	
	public void printTree() {
		traverseTree(root); 
	}
	
	private void traverseTree(Node currentNode) {
		
		if (currentNode.left != null)
			traverseTree(currentNode.left);
		
		System.out.println(Integer.toString(currentNode.value));
		
		if (currentNode.right != null)
			traverseTree(currentNode.right);

	}
}
