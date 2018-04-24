package pe.maldonado.datastructures.trees;

// Binary Search Tree

public class BSTree {
	
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
	
	public BSTree() {
		root = null;
		nodeCount = 0;
	}
	
	public void addItem(int key, int value) {
		
		Node currentNode;
		boolean added = false;
		
		if (root == null) { 
			root = new Node(key, value);
		}
		else {
			currentNode = root;
			
			while (!added) {
				if (key < currentNode.key) {
					if (currentNode.left == null) {
						currentNode.left = new Node(key, value);
						nodeCount++;
						added = true;
					}
					else {
						currentNode = currentNode.left;
					}
				}
				else if (key > currentNode.key) {
					if (currentNode.right == null) {
						currentNode.right = new Node(key, value);
						nodeCount++;
						added = true;
					}
					else {
						currentNode = currentNode.right;
					}
				}
				else {
					// El elemento a insertar ya estaba en el árbol
					added = true;
				}			
			}
		}
	}
		
	public int findItem(int key) {
		
		Node currentNode = root;
			
		while (currentNode != null) {
			if (key == currentNode.key) {
				return currentNode.value;
			}
			
			if (key < currentNode.key) {
				currentNode = currentNode.left;
			}
			else {
				currentNode = currentNode.right;
			}
		}
		
		return -1;
	}
		
	public void removeItem(int key) {
		
		Node successor, parent = root, currentNode = root;
		
		while (currentNode != null) {
			
			if (key < currentNode.key) {
				parent = currentNode;
				currentNode = currentNode.left;
			}
			else if (key > currentNode.key) {
				parent = currentNode;
				currentNode = currentNode.right;
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
					if (currentNode == parent.left) {
						parent.left = currentNode.right; 
					}
					else {
						parent.right = currentNode.right;
					}
				}
				
				// Caso 2
				else if (currentNode.right == null) {
					 
					if (currentNode == parent.left) {
						parent.left = currentNode.left; 
					}
					else {
						parent.right = currentNode.left;
					}
				}
				
				// Caso 3
				else {

					// Encontramos el sucesor del nodo a eliminar (currentNode) y lo desacoplamos del arbol
					successor = removeSuccessor(currentNode);
					
					// Hacemos que los hijos de currentNode sean ahora los hijos del sucesor
					successor.left = currentNode.left;
					successor.right = currentNode.right;
					
					// Hacemos que el padre de currentNode sea ahora el padre del sucessor.
					if (currentNode == root) {
						root = successor;
					}
					else if (currentNode == parent.left) {
						parent.left = successor;
					}
					else {
						parent.right = successor;
					}
					
				}
				
				// Eliminamos currentNode
				currentNode = null;
				nodeCount--;			
			}
		}
	}
		
	private Node removeSuccessor(Node node) {
		
		Node parent, currentNode = node;
		
		if (currentNode == null || currentNode.right == null) {
			return null;
		}
		
		parent = currentNode;
		currentNode = currentNode.right;
						
		while (currentNode.left != null) {
			parent = currentNode;
			currentNode = currentNode.left;
		}
		
		// Desacoplamos currentNode del Arbol, haciendo que el padre de currentNode sea ahora el padre de los hijos derechos de currentNode
		if (currentNode == parent.left) {
			parent.left = currentNode.right; 
		}
		else {
			parent.right = currentNode.right;
		}
		
		return currentNode;
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
