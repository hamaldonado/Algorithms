package pe.maldonado.datastructures.trees;

public class AVLTree {
	
	private class Node {
		private Node parent;
		private Node left;
		private Node right;
		private int height;
		private int key;
		private int value;
	
		public Node(Node parent, int key, int value) {
			this.key = key;
			this.value = value;
			this.height = 0;
			this.parent = parent;
			this.left = null;
			this.right= null;
		}
		
	}
	
	private Node root;
	private int nodeCount;
	
	public int count() {
		return nodeCount;
	}
	
	public int getHeight() {
		return height(root);
	}
	
	public AVLTree() {
		root = null;
		nodeCount = 0;
	}
	
	private int height(Node n) {
		return (n == null) ? -1 : n.height;
	}
	
	private int balanceFactor(Node n) {
		
		if (n == null) {
			return 0;
		}
		
		return height(n.left) - height(n.right);
	
	}
	
	private int max(int a, int b) {
		return a >= b ? a : b;
	}
	
	public void addItem(int key, int value) {
		
		Node currentNode;
		int status = 0;  // 0 = No Añadido, 1 = Añadido, 2 = Ya Existia 
		
		if (root == null) { 
			root = new Node(null, key, value);
			return;
		}
		
		// Añadimos el Nuevo Nodo. Ubicamos el lugar de inserción recorriendo el arbol desde la raiz hacia abajo.
		currentNode = root;
		while (status == 0) {
			if (key < currentNode.key) {
				if (currentNode.left == null) {
					currentNode.left = new Node(currentNode, key, value);
					nodeCount++;
					status = 1;
				}
				else {
					currentNode = currentNode.left;
				}
			}
			else if (key > currentNode.key) {
				if (currentNode.right == null) {
					currentNode.right = new Node(currentNode, key, value);
					nodeCount++;
					status = 1;
				}
				else {
					currentNode = currentNode.right;
				}
			}
			else {
				// El elemento a insertar ya estaba en el árbol
				status = 2;
			}			
		}
		
		// Si se llegó a añadir un Nodo, actualizamos las alturas de los nodos afectados y rebalanceamos el árbol de ser necesario
		if (status == 1) {
			balanceTree(currentNode);
		}
		
	}

	private void balanceTree(Node node) {
				
		Node currentNode = node;
		
		// Recorremos el árbol desde currentNode hacia arriba para actualizar las alturas en los 
		// nodos afectados por la inserción o la eliminación, así como realizar las operaciones de balanceo correspondientes
		while (currentNode != null) { 
						
			currentNode.height = max(height(currentNode.left), height(currentNode.right)) + 1; 
							
			/* Verificamos si el sub-árbol cuya raiz es currentNode está desbalanceado y aplicamos rotaciones 
			 * para balancearlo.  Para esto verificamos el BalanceFactor de currentNode:
			 * 	- Si BalanceFactor == +2, el sub-árbol está desbalanceado por la izquierda.
			 * 	- Si BalanceFactor == -2, el sub-árbol está desbalanceado por la derecha.
			 */
			if (balanceFactor(currentNode) == 2) {
				
				// El sub-árbol está desbalanceado por la izquierda.  El siguiente paso es verificar si el desbalance
				// viene por la rama izquierda o por la rama la derecha del hijo izquierdo de currentNode.
				if (balanceFactor(currentNode.left) > 0) {	
			
					// El desbalance viene por la rama izquierda del hijo izquierdo de currentNode
					currentNode = rotateNodeRight(currentNode);
					
				}
				else if (balanceFactor(currentNode.left) < 0) {
					
					// El desbalance viene por la rama derecha del hijo izquierdo de currentNode
					currentNode = rotateNodeLeftRight(currentNode);
				}
		
				// Vemos si el sub-árbol a partir de currentNode estaba anclado por la derecha o por 
				// la izquierda del nodo padre y actualizamos los enlaces
				if (currentNode.parent != null) {
					if(currentNode.parent.left == currentNode.right) {
						currentNode.parent.left = currentNode;
					} 
					else {
						currentNode.parent.right = currentNode;
					}
				}
				
			}
			else if (balanceFactor(currentNode) == -2) {
				
				// El sub-árbol está desbalanceado por la derecha.  El siguiente paso es verificar si el desbalance
				// viene por la rama izquierda o por la rama la derecha del hijo derecho de currentNode.
				if (balanceFactor(currentNode.right) > 0) {
					
					// El desbalance viene por la rama izquierda del hijo derecho de currentNode
					currentNode = rotateNodeRightLeft(currentNode);
				}
				else if (balanceFactor(currentNode.right) < 0) {
					
					// El desbalance viene por la rama derecha del hijo derecho de currentNode
					currentNode = rotateNodeLeft(currentNode);
				}
				
				// Vemos si el subarbol a partir de currentNode estaba anclado por la derecha o por 
				// la izquierda del nodo padre y actualizamos los enlaces
				if (currentNode.parent != null) {
					if (currentNode.parent.left == currentNode.left) {
						currentNode.parent.left = currentNode;
					} 
					else {
						currentNode.parent.right = currentNode;
					}
				}	
				
			}
							
			// Si el padre de currentNode es null, hemos llegado a la raiz del arbol. Apuntamos root a la nueva raiz
			if (currentNode.parent == null)
				root = currentNode;
			
			currentNode = currentNode.parent;
		
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
		
		Node successor, temp, currentNode = root;
		
		while (currentNode != null) {
			
			if (key < currentNode.key) {
				currentNode = currentNode.left;
			}
			else if (key > currentNode.key) {
				currentNode = currentNode.right;
			}
			else {
				
				/* 
				 * currentNode es el nodo a eliminar.  Se dan 3 casos:
				 * Caso 1: currentNode tiene 1 sólo hijo por la der. Lo reemplazamos por su hijo derecho o por null si tampoco 
				 *         tiene hijo derecho (currentNode es nodo Hoja).
				 * Caso 2: currentNode tiene 1 sólo hijo por la izq. Lo reemplazamos por su hijo izquierdo.
				 * Caso 3: currentNode tiene 2 hijos. Lo reemplazamos por su Sucesor.
				 */
				
				// Caso 1
				if (currentNode.left == null) {
					if (currentNode == currentNode.parent.left) {
						currentNode.parent.left = currentNode.right;
					}
					else {
						currentNode.parent.right = currentNode.right;
					}
					
					// Actualizamos el enlace hacia el nuevo padre
					if (currentNode.right != null) {
						currentNode.right.parent = currentNode.parent;
					}
					
					// Guardamos una referencia al padre actual de currentNode pues será el punto desde donde iniciaremos el 
					// proceso de actualización de alturas y rebalanceo correspondiente, para mantener el arbol balanceado 
					// luego de la eliminación.
					temp = currentNode.parent;
				}
				
				// Caso 2
				else if (currentNode.right == null) {
					 
					if (currentNode == currentNode.parent.left) {
						currentNode.parent.left = currentNode.left; 
					}
					else {
						currentNode.parent.right = currentNode.left;
					}
					
					// Actualizamos el enlace hacia el nuevo padre
					if (currentNode.left != null) {
						currentNode.left.parent = currentNode.parent;
					}
					
					// Guardamos una referencia al padre actual de currentNode pues será el punto desde donde iniciaremos el 
					// proceso de actualización de alturas y rebalanceo correspondiente, para mantener el arbol balanceado 
					// luego de la eliminación.
					temp = currentNode.parent;
				}
				
				// Caso 3
				else {

					// Encontramos el sucesor del nodo a eliminar (currentNode) y lo desacoplamos del arbol
					successor = removeSuccessor(currentNode);
					
					// Guardamos una referencia al padre actual del sucesor pues será el punto desde donde iniciaremos el 
					// proceso de actualización de alturas y rebalanceo correspondiente, para mantener el arbol balanceado 
					// luego de la eliminación.
					temp = successor.parent;
					
					// Hacemos que los hijos de currentNode sean ahora los hijos del sucesor
					successor.left = currentNode.left;
					successor.right = currentNode.right;
					
					// Hacemos que los hijos de currentNode reconozcan a Successor como su nuevo padre
					if (currentNode.left != null)
						currentNode.left.parent = successor;
					
					if (currentNode.right != null)
						currentNode.right.parent = successor;
					
					
					// Hacemos que el padre de currentNode sea ahora el padre del sucessor.
					successor.parent = currentNode.parent;
					
					if (currentNode == root) {
						root = successor;
					}
					else if (currentNode == currentNode.parent.left) {
						currentNode.parent.left = successor;
					}
					else {
						currentNode.parent.right = successor;
					}
					
				}
				
				// Eliminamos currentNode
				currentNode = null;
				nodeCount--;
				
				// Actualizamos las alturas de los nodos afectados y rebalanceamos el árbol de ser necesario a partir 
				// del punto de inicio de balanceo identificado (referenciado en temp)
				balanceTree(temp);
			}
		}
	}
		
	private Node removeSuccessor(Node node) {
		
		Node currentNode = node;
		
		if (currentNode == null || currentNode.right == null) {
			return null;
		}
		
		currentNode = currentNode.right;
						
		while (currentNode.left != null) {
			currentNode = currentNode.left;
		}
		
		// Desacoplamos currentNode del Arbol, haciendo que el padre de currentNode sea ahora el padre de los hijos derechos de currentNode
		if (currentNode == currentNode.parent.left) {
			currentNode.parent.left = currentNode.right; 
		}
		else {
			currentNode.parent.right = currentNode.right;
		}
		
		if (currentNode.right != null) {
			currentNode.right.parent = currentNode.parent;
		}
		
		// Devolvemos el nodo desacoplado
		return currentNode;
	}
	
	private Node rotateNodeLeft(Node m) {
		
		Node n = m.right;
		
		n.parent = m.parent;
		
		m.right = n.left;
		if (m.right != null)
			m.right.parent = m;
				
		n.left = m;
		m.parent = n;
		
		m.height = max(height(m.left), height(m.right)) + 1; 
		n.height = max(height(n.left), height(n.right)) + 1; 
		
		return n;
	}
	
	private Node rotateNodeRight(Node m) {
		
		Node n = m.left;
		
		n.parent = m.parent;
		
		m.left = n.right;
		if (m.left != null)
			m.left.parent = m;
		
		n.right = m;
		m.parent = n;
				
		m.height = max(height(m.left), height(m.right)) + 1; 
		n.height = max(height(n.left), height(n.right)) + 1; 
		
		return n;
	}
	
	private Node rotateNodeLeftRight(Node m) {
		
		m.left = rotateNodeLeft(m.left);
		m.left.parent = m;
		
		return rotateNodeRight(m);
	}
	
	private Node rotateNodeRightLeft(Node m) {
		
		m.right = rotateNodeRight(m.right);
		m.right.parent = m;
		
		return rotateNodeLeft(m);
	}	
		
	public void printTree() {
		traverseTree(root);
	}
	
	public String toString() {
		return drawTree(root);
	}
	
	private void traverseTree(Node currentNode) {
		
		if (currentNode.left != null)
			traverseTree(currentNode.left);
		
		System.out.println(Integer.toString(currentNode.value));
		
		if (currentNode.right != null)
			traverseTree(currentNode.right);
	}
	
	private String drawTree(Node currentNode) {
		
		if (currentNode == null) {
			return new String("nil");
		}

		StringBuilder sb = new StringBuilder(Integer.toString(currentNode.value));
		
		/*sb.append("<");
		sb.append(Integer.toString(currentNode.height));
		sb.append(">");*/
		
		sb.append("(");
		sb.append(drawTree(currentNode.left));
		sb.append(", ");
		sb.append(drawTree(currentNode.right));
		sb.append(")");
		
		return sb.toString();
	}

}
