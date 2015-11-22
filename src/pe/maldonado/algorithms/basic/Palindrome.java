package pe.maldonado.algorithms.basic;

public class Palindrome {
	
	static boolean isPalindrome(String s) {
		
		for (int i = 0, j = s.length()-1; i < j; i++, j--) {
			if (s.charAt(i) != s.charAt(j)) {
				return false;
			}
		}

		return true;
	}
	
	public static void main(String[] args) {

		String s = "AVIVA";
		
		if (isPalindrome(s)) {
			System.out.print(s + " is a palindrome.");
		}
		else {
			System.out.print(s + " is not a palindrome.");
		}

	}
	
}
