package pe.maldonado.algorithms.basic;

import java.util.Scanner;

public class Permutation {

	static void permute(int[] a, int n) {
		
		int i, temp;
		
		if (n == 1) {
			System.out.println(printPattern(a));
			return;
		}
		
		for (i = 0; i < n; i++) {
			
			permute(a, n - 1);
			
			if (n % 2 == 0) {
				temp = a[i];
				a[i] = a[n-1];
				a[n-1] = temp;
			}
			else {
				temp = a[0];
				a[0] = a[n-1];
				a[n-1] = temp;
			}
		}
		
	}
	
	static String printPattern(int[] a) {
		
		int i;
		String pattern = "";
		
		for (i = 0; i < a.length - 1; i++ ) {
			
			if (a[i] < a[i+1]) {
				pattern += "L";
			}
			else {
				pattern += "G";
			}
		}
		
		return pattern;
		
	}

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
	    
		int i;
	    int[] a = {0, 1, 2, 3};
	    
	    permute(a, a.length);
	     
	    in.close();

	}
	
	
	
	
}
