package pe.maldonado.algorithms.basic;

public class Fibonacci {
	
	static void getFibonacciSequence(int[] sequence, int n) {
		
		if (n <= 0) 
			return;
	
		sequence[0] = 0;
		
		if (n == 1)
			return;
			
		sequence[1] = 1;
		
		for (int i = 2; i < n; i++) {
			sequence[i] = sequence [i-1] + sequence[i-2];
		}
						
	}
	
	public static void main(String[] args) {

		int n = 15;
		int[] a = new int[n];
		
		getFibonacciSequence(a, n);
		
		for (int i = 0; i < a.length; i++) {
			System.out.print(Integer.toString(a[i]) + " ");
		}
	}
}
