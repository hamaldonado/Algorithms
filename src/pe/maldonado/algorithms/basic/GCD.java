package pe.maldonado.algorithms.basic;

public class GCD {

	static int gcd(int a, int b) {
		
		int hi = (a > b) ? a : b;
		int lo = (a < b) ? a : b;
		int m;
		
		m = hi % lo;
		
		if ( m == 0 ) {
			return lo;
		}
		else {
			return gcd(hi, m);
		}
		
	}
	
	public static void main(String[] args) {

		int a = 81;
		int b = 35;
		
		System.out.print("GCD = " + gcd(a, b));
		
	}
}
