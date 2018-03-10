package pe.maldonado.datastructures.trees;

public class Trie {
	
	private static final int ALPHABET_SIZE = 27;
	private static final int ALPHABET_BASE = 97; // Lets assume that alphabet is only non capital letters (a-z) (char 97 to 122)
	
	private class Node {
		private Node[] children;
		private boolean isEndOfWord;
		
		public Node() {
			this.children = new Node[ALPHABET_SIZE];
			
			for (int i = 0; i < ALPHABET_SIZE; i++) {
				children[i] = null;
			}
			
			this.isEndOfWord = false;
		}
	}
	
	private Node root;
	
	public Trie() {
		
		root = new Node();
		
	}

	public void addWord(String word) {
		
		addWordRecursively(root, word, 0);
		
	}
	
	private void addWordRecursively(Node currentNode, String word, int position) {
		
		// recursiveness exit condition: the word has been completely processed
		if (position == word.length()) {
			return;
		}
		
		// Substract alphabet base from the current char value to get the correct key  
		int key = word.charAt(position) - ALPHABET_BASE;
		
		// create the node if it doesn't exist
		if (currentNode.children[key] == null) {
			currentNode.children[key] = new Node();
		}
		
		// is the last char in the String
		if (position == word.length() - 1) {
			currentNode.children[key].isEndOfWord = true;
		}
		
		addWordRecursively(currentNode.children[key], word, ++position);
	}
	
	public String startsWith(String prefix) {
		
		Node baseNode = findPrefix(root, prefix, 0);   
		
		if (baseNode == null) {
			return null;
		}
				
		return findAlternatives(baseNode, prefix);
	}
	
	private Node findPrefix(Node currentNode, String prefix, int position) {
		
		// recursiveness exit condition: the prefix has been found.
		if (position == prefix.length()) {
			return currentNode;
		}
		
		// Substract alphabet base from the current char value to get the correct key  
		int key = prefix.charAt(position) - ALPHABET_BASE;
		
		// if the key is not found, the provided prefix does not exist in the trie 
		if (currentNode.children[key] == null) {
			return null;
		}

		return findPrefix(currentNode.children[key], prefix, ++position);

	}
	
	private String findAlternatives(Node currentNode, String prefix) {
		
		StringBuilder sb = new StringBuilder();
		
		if (currentNode.isEndOfWord) {
			sb.append("|");
			sb.append(prefix);
		}
		
		for (char i = 0; i < ALPHABET_SIZE; i++) {

			if (currentNode.children[i] != null) {

				char ch = (char)(i + ALPHABET_BASE);
				
				sb.append(findAlternatives(currentNode.children[i], new StringBuilder(prefix).append(ch).toString()));
			}
		}
		
		return sb.toString();
	}
		
	public String toString() {
		
		return drawTree(root);
		
	}
	
	private String drawTree(Node currentNode) {

		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
			
		for (char i = 0; i < ALPHABET_SIZE; i++) {
			
			if (currentNode.children[i] != null) {
				
				sb.append((char)(i + ALPHABET_BASE));
				
				if (currentNode.children[i].isEndOfWord) {
					sb.append("*");
				}
				
				sb.append(drawTree(currentNode.children[i]));
			}
		}
		
		sb.append(")");
		
		return sb.toString();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Trie trie = new Trie();
				
		trie.addWord("analuz");
		trie.addWord("ana");
		trie.addWord("anita");
		trie.addWord("anahi");
		trie.addWord("anamaria");
		trie.addWord("anastasia");
		trie.addWord("anilu");
		trie.addWord("alondra");
		trie.addWord("alfonsina");
		trie.addWord("alessia");
				
		System.out.println(trie.toString());
		System.out.println(trie.startsWith("al"));
		
	}

}
